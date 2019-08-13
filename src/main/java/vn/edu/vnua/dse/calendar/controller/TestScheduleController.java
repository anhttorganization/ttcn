package vn.edu.vnua.dse.calendar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vnua.dse.calendar.co.ScheduleCreate;
import vn.edu.vnua.dse.calendar.co.ScheduleResult;
import vn.edu.vnua.dse.calendar.common.AppConstant;
import vn.edu.vnua.dse.calendar.crawling.ExamEventDetails;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleCalendar;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.CalendarConstant;
import vn.edu.vnua.dse.calendar.model.Calendar;
import vn.edu.vnua.dse.calendar.model.CalendarDetail;
import vn.edu.vnua.dse.calendar.model.Event;
import vn.edu.vnua.dse.calendar.model.User;
import vn.edu.vnua.dse.calendar.repository.CalendarDetailRepository;
import vn.edu.vnua.dse.calendar.repository.CalendarRepository;
import vn.edu.vnua.dse.calendar.repository.EventRepository;
import vn.edu.vnua.dse.calendar.repository.UserRepository;
import vn.edu.vnua.dse.calendar.service.AuthorizationService;
import vn.edu.vnua.dse.calendar.service.TestScheduleService;
import vn.edu.vnua.dse.calendar.service.UserDetailsServiceImpl;

@Controller
public class TestScheduleController {

	@Autowired
	CalendarRepository calendarRepository;

	@Autowired
	CalendarDetailRepository calendarDetailRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	APIWrapper aPIWrapper;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TestScheduleService testScheduleService;

	@RequestMapping(value = "/testschedule/create", method = RequestMethod.GET)
	public String Create(Model model) throws FileNotFoundException, IOException {
		if (AuthorizationService.isauthorized() != null) {
			return "redirect:" + AuthorizationService.isauthorized();
		}
		model.addAttribute("scheduleCreate", new ScheduleCreate());

		return "testschedule/create";
	}

	@RequestMapping(value = "/testschedule/create", method = RequestMethod.POST)
	public String Create(@ModelAttribute("scheduleCreate") ScheduleCreate scheduleCreate, Model model,
			RedirectAttributes ra) throws NoSuchAlgorithmException, IOException, ParseException {
		String studentId = scheduleCreate.getStudentId();
		// 1. Kiểm tra xem lịch thi với mã sinh viên đã được thêm chưa?
		User user = UserDetailsServiceImpl.getUser();

		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		Optional<List<Calendar>> calenOptional = calendarRepository.findByStudentIdAndTypeAndUserId(studentId, true,
				user.getId());
		// Nếu không có lịch với mã sinh viên được thêm
		if (!calenOptional.isPresent()) {
			ScheduleResult<List<GoogleEvent>> result = addCalendar(studentId);
			addFlashAtributes(ra, result);
			return "redirect:/testschedule/create";
		}

		// Nếu đã có lịch với mã sinh viên truyền vào
		List<Calendar> calendars = calenOptional.get();
		Calendar calendar = filterCalendar(calendars);
		if (calendar == null) {
			ScheduleResult<List<GoogleEvent>> result = addCalendar(studentId);
			addFlashAtributes(ra, result);
			return "redirect:/testschedule/create";
		}

		// lấy các calendarDetail của lịch thi
		Optional<CalendarDetail> detailOptional = calendarDetailRepository.findByCalendar(calendar);
		// vi khong co học kỳ nên detail luon co mot
		if (!detailOptional.isPresent()) {
			ra.addFlashAttribute("error", "Có lỗi xảy ra!");
			return "redirect:/testschedule/create";
		}

		CalendarDetail calendarDetail = detailOptional.get();
		handlingChanges(ra, calendarDetail, studentId, calendar);

		return "redirect:/testschedule/create";
	}

	/**
	 * @param calendars
	 * @return
	 * @throws IOException
	 */
	private Calendar filterCalendar(List<Calendar> calendars) throws IOException {
		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		ArrayList<Calendar> results = new ArrayList<>();
		for (Calendar calendar : calendars) { // tìm số calendars tồn tại trên cả db và google
			if (calendar != null) {
				String calendarId = calendar.getCalendarId(); // kiểm tra xem calendar còn trên calendar list cua nguoi
				GoogleCalendar googleCalendar = aPIWrapper.findCalendarInCalendarList(calendarId);
				// Nếu lịch nào chỉ có trong db mà không có trên GGCalendar thì xóa trong db
				if (googleCalendar == null) {
//					results.remove(calendar);
					calendarRepository.delete(calendar);
				} else {
					results.add(calendar);
				}

			}
		}
		if (results.size() == 0) {
			return null;
		}
		// giu lai mot calendar
		for (int i = 1; i < results.size(); i++) {
			calendarRepository.delete(results.get(i));
		}
		Calendar calendar = results.get(0);
		return calendar;
	}

	/**
	 * @param ra
	 * @param calendarDetail
	 * @param studentId
	 */
	private void handlingChanges(RedirectAttributes ra, CalendarDetail calendarDetail, String studentId,
			Calendar calendar) {
		// TODO Auto-generated method stub
		try {
			ScheduleResult<List<GoogleEvent>> newEvents = ExamEventDetails.getEventsFromSchedule(studentId);

			if (!newEvents.isStatus()) {
				ra.addFlashAttribute("error", newEvents.getMessage());
				return;
			}

			String newHash = newEvents.getScheduleHash();
			String oldHash = calendarDetail.getScheduleHash();
			// Nếu lịch không thay đổi
			if (newHash.equals(oldHash)) {
				ra.addFlashAttribute("info", "Lịch thi đã tồn tại!");
				return;
			}
			// Nếu lịch thay đổi
			Set<Event> oldEvents = calendarDetail.getEvents();
			for (Event oldEvent : oldEvents) {
				aPIWrapper.deleteEvent(calendar.getCalendarId(), oldEvent.getEventId());// xoa tren
				eventRepository.delete(oldEvent);
			}

			updateCalendar(calendar, newEvents.getResult(), newHash);
			calendarDetailRepository.delete(calendarDetail.getId());
			// thong bao cap nhat thoi khoa bieu thanh cong
			ra.addFlashAttribute("success", "Cập nhật thay đổi lịch thi thành công!");
			return;
		} catch (NoSuchAlgorithmException | IOException | ParseException e) {
			ra.addFlashAttribute("error", "Có lỗi xảy ra!");
			return;
		}

	}

	/**
	 * @param ra
	 * @param result
	 */
	private void addFlashAtributes(RedirectAttributes ra, ScheduleResult<List<GoogleEvent>> result) {
		// TODO Auto-generated method stub
		if (result.isStatus()) {
			if (result.getResult().size() > 0) {
				ra.addFlashAttribute("success", "Thêm lịch thành công!");
			} else {
				ra.addFlashAttribute("info", result.getMessage());
			}
		} else {
			ra.addFlashAttribute("error", result.getMessage());
		}
	}

	private ScheduleResult<List<GoogleEvent>> addCalendar(String studentId)
			throws IOException, NoSuchAlgorithmException, ParseException {
		// lay thoi khoa bieu
		ScheduleResult<List<GoogleEvent>> result = ExamEventDetails.getEventsFromSchedule(studentId);
		if (result.isStatus()) {
			// tao calendar
			String summary = AppConstant.TEST_SCHEDULE_SUMMARY + studentId;
			aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
			GoogleCalendar ggcalen = aPIWrapper.insertCalendar(summary, CalendarConstant.TIME_ZONE);

			User user = UserDetailsServiceImpl.getUser();
			List<GoogleEvent> ggEvents = result.getResult();
			if (ggEvents.size() > 0) {
				// lấy lịch thi
				Set<String> eventIds = testScheduleService.insert(ggcalen.getId(), ggEvents);

				// tao list event
				if (eventIds.size() > 0) {
					HashSet<Event> events = new HashSet<>();
					for (String eventId : eventIds) {
						Event e = new Event(eventId);
						events.add(e);
					}
					// tao calendarDetail
					String scheduleHash = result.getScheduleHash();
					CalendarDetail calendarDetail = new CalendarDetail(scheduleHash, events);

					// tao calendar
					Calendar calendar = new Calendar(user, studentId, ggcalen.getId(), true, calendarDetail);

					// save
					calendarRepository.save(calendar);
				}
			}

		}
		// tra ve message
		return result;
	}

	// Khi da co calendar
//	private String updateCalendar(Calendar calendar, String studentId)
//			throws NoSuchAlgorithmException, IOException, ParseException {
//		ScheduleResult<Set<String>> result = testScheduleService.insert(calendar.getCalendarId(), studentId);
//
//		HashSet<String> eventIds;
//		if (result.isStatus()) {
//			eventIds = (HashSet<String>) result.getResult();
//			if (eventIds.size() > 0) {
//				HashSet<Event> events = new HashSet<>();
//				for (String eventId : eventIds) {
//					Event e = new Event(eventId);
//					events.add(e);
//				}
//
//				CalendarDetail calendarDetail = new CalendarDetail(SubjectEventDetails.scheduleHash, events);
//				// cập nhật calendar
//				calendar.addCalendarDetails(calendarDetail);
//
//				calendarRepository.save(calendar);
//			}
//
//		}
//
//		return result.getMessage();
//	}

	// khi calendar thay doi
	private void updateCalendar(Calendar calendar, List<GoogleEvent> newEvents, String newHash) throws IOException {
		HashSet<String> eventIds;
		eventIds = (HashSet<String>) testScheduleService.insert(calendar.getCalendarId(), newEvents);

		// insert list event, calendardetail, calendar
		if (eventIds.size() > 0) {
			HashSet<Event> events = new HashSet<>();
			for (String eventId : eventIds) {
				Event e = new Event(eventId);
				events.add(e);
			}
			CalendarDetail calendarDetail = new CalendarDetail(newHash, events);
			// cập nhật calendar
			calendar.addCalendarDetails(calendarDetail);

			calendarRepository.save(calendar);
		}
	}
}
