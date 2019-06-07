package vn.edu.vnua.dse.calendar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.vnua.dse.calendar.co.BaseResult;
import vn.edu.vnua.dse.calendar.co.ScheduleCreate;
import vn.edu.vnua.dse.calendar.co.ScheduleResult;
import vn.edu.vnua.dse.calendar.common.AppConstant;
import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.crawling.ExamEventDetails;
import vn.edu.vnua.dse.calendar.crawling.SubjectEventDetails;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleCalendar;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
import vn.edu.vnua.dse.calendar.model.Calendar;
import vn.edu.vnua.dse.calendar.model.CalendarDetail;
import vn.edu.vnua.dse.calendar.model.Event;
import vn.edu.vnua.dse.calendar.model.Semester;
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
	public String Create(@ModelAttribute("scheduleCreate") ScheduleCreate scheduleCreate, Model model) throws NoSuchAlgorithmException, IOException, ParseException {
		String studentId = scheduleCreate.getStudentId();
		// 1. Kiểm tra xem lịch thi với mã sinh viên đã được thêm chưa?
		Optional<Calendar> calenOptional = calendarRepository.findByStudentIdAndType(studentId, true);
		if (calenOptional.isPresent()) {
			Calendar calendar = calenOptional.get();
			// kiểm tra xem calendar còn trên calendar list cua nguoi dung khong
			String calendarId = calendar.getCalendarId();
			aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
			GoogleCalendar googleCalendar = aPIWrapper.findCalendarInCalendarList(calendarId);
			if (googleCalendar == null) {
				calendarRepository.delete(calendar.getId());
				String message = addCalendar(studentId);
				model.addAttribute(message, message);
			} else {
				// kiem tra xem lich thi da duoc them chua
				Optional<CalendarDetail> optional = calendarDetailRepository.findByCalendar(calendar);

				if (optional.isPresent()) {
					CalendarDetail calendarDetail = optional.get();
					ScheduleResult<List<GoogleEvent>> newEvents = ExamEventDetails.getEventsFromSchedule(studentId);

					if (newEvents.isStatus()) {
						String newHash = newEvents.getScheduleHash();
						String oldHash = calendarDetail.getScheduleHash();

						if (!newHash.equals(oldHash)) {// neu thoi khoa bieu thay doi
							Set<Event> oldEvents = calendarDetail.getEvents();

							for (Event oldEvent : oldEvents) {
								aPIWrapper.deleteEvent(calendar.getCalendarId(), oldEvent.getEventId());// xoa tren
								eventRepository.delete(oldEvent);
							}

							updateCalendar(calendar, newEvents.getResult(), newHash);
							calendarDetailRepository.delete(calendarDetail.getId());
							// thong bao cap nhat thoi khoa bieu thanh cong
							// model.addAttribute("message", newEvents.getMassage());//
							model.addAttribute(newEvents.getMassage().toString(), AppUtils.getScheduleMessage(newEvents.getMassage()));
						} else {
							model.addAttribute("exist", "Lịch thi đã tồn tại!");
						}
					}
				} else {
					String mesage = updateCalendar(calendar, studentId);
					model.addAttribute(mesage, AppUtils.getScheduleMessage(mesage));
				}
			}
		} else {// Neu chua duoc them
			String message = addCalendar(studentId);
			model.addAttribute(message, AppUtils.getScheduleMessage(message));
		}
		//
		return "testschedule/create";
	}

	private String addCalendar(String studentId) throws IOException, NoSuchAlgorithmException, ParseException {
		//lay thoi khoa bieu
		ScheduleResult<List<GoogleEvent>> result = ExamEventDetails.getEventsFromSchedule(studentId);
		// tao calendar
		String summary = AppConstant.TEST_SCHEDULE_SUMMARY + studentId;
		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		GoogleCalendar ggcalen = aPIWrapper.insertCalendar(summary);
		
		User user = UserDetailsServiceImpl.getUser();
		List<GoogleEvent> ggEvents = result.getResult();
		if(ggEvents.size() > 0) {
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
	
		// tra ve message
		return result.getMassage();
	}

	// Khi da co calendar
	private String updateCalendar(Calendar calendar, String studentId) throws NoSuchAlgorithmException, IOException, ParseException {
		ScheduleResult<Set<String>> result = testScheduleService.insert(calendar.getCalendarId(), studentId);

		HashSet<String> eventIds;
		if (result.isStatus()) {
			eventIds = (HashSet<String>) result.getResult();
			if (eventIds.size() > 0) {
				HashSet<Event> events = new HashSet<>();
				for (String eventId : eventIds) {
					Event e = new Event(eventId);
					events.add(e);
				}

				CalendarDetail calendarDetail = new CalendarDetail(SubjectEventDetails.scheduleHash, events);
				// cập nhật calendar
				calendar.addCalendarDetails(calendarDetail);

				calendarRepository.save(calendar);
			}

		}

		return result.getMassage();
	}

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
