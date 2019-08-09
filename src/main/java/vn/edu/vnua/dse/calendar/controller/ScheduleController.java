package vn.edu.vnua.dse.calendar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vnua.dse.calendar.co.BaseResult;
import vn.edu.vnua.dse.calendar.co.ScheduleCreate;
import vn.edu.vnua.dse.calendar.common.AppConstant;
import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.crawling.SubjectEventDetails;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleCalendar;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.CalendarConstant;
import vn.edu.vnua.dse.calendar.model.Calendar;
import vn.edu.vnua.dse.calendar.model.CalendarDetail;
import vn.edu.vnua.dse.calendar.model.Event;
import vn.edu.vnua.dse.calendar.model.Semester;
import vn.edu.vnua.dse.calendar.model.User;
import vn.edu.vnua.dse.calendar.repository.CalendarDetailRepository;
import vn.edu.vnua.dse.calendar.repository.CalendarRepository;
import vn.edu.vnua.dse.calendar.repository.EventRepository;
import vn.edu.vnua.dse.calendar.repository.SemesterRepository;
import vn.edu.vnua.dse.calendar.repository.UserRepository;
import vn.edu.vnua.dse.calendar.service.AuthorizationService;
import vn.edu.vnua.dse.calendar.service.ScheduleService;
import vn.edu.vnua.dse.calendar.service.UserDetailsServiceImpl;

@Controller
@RequestMapping
public class ScheduleController {

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
	ScheduleService scheduleService;

	@Autowired
	SemesterRepository semesterRepository;

	@RequestMapping(value = "/schedule/create", method = RequestMethod.GET)
	public String Create(Model model) throws FileNotFoundException, IOException {
		Map<String, String> semesters = new HashMap<String, String>();
		List<Semester> lstSemester = semesterRepository.fin3CurrentSemester();
		for (Semester semester : lstSemester) {
			semesters.put(semester.getId(), semester.getName());
		}

		if (AuthorizationService.isauthorized() != null) {
			return "redirect:" + AuthorizationService.isauthorized();
		}
		model.addAttribute("scheduleCreate", new ScheduleCreate());
		model.addAttribute("semesters", semesters);
		return "schedule/create";
	}

	@RequestMapping(value = "/schedule/create", method = RequestMethod.POST)
	public String Create(@ModelAttribute("scheduleCreate") ScheduleCreate scheduleCreate, Model model, RedirectAttributes ra)
			throws IOException, ParseException, NoSuchAlgorithmException {
		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());

		String semesterId = scheduleCreate.getSemester();
		String studentId = scheduleCreate.getStudentId();

		// 1. Kiểm tra xem thời với mã sinh viên nhập vào đã được thêm chưa find
		// calendar by student id
		Optional<Calendar> calenOptional = calendarRepository.findByStudentIdAndType(studentId, false);
		if (calenOptional.isPresent()) { // hoc ky da duoc them
			Calendar calendar = calenOptional.get();
			// kiểm tra xem calendar còn trên calendar list cua nguoi dung khong
			String calendarId = calendar.getCalendarId();
			GoogleCalendar googleCalendar = aPIWrapper.findCalendarInCalendarList(calendarId);
			if (googleCalendar == null) {
				calendarRepository.delete(calendar.getId());
				// trả về thông báo
				BaseResult<List<GoogleEvent>> result = addCalendar(scheduleCreate);
				if (result.isStatus()) {
					if(result.getResult().size() > 0) {
						ra.addFlashAttribute("success", "Thêm lịch thành công!");
					}else {
						ra.addFlashAttribute("info", result.getMessage());
					}
				} else {
					ra.addFlashAttribute("error", result.getMessage());
				}
			} else {
				Semester semester = semesterRepository.findById(semesterId);
				// kiem tra xem calendar voi hoc ky nhap vao da co chua
				Optional<CalendarDetail> calenDetailOptional = calendarDetailRepository
						.findByCalendarAndSemester(calendar, semester);

				if (calenDetailOptional.isPresent()) {
					CalendarDetail calendarDetail = calenDetailOptional.get();
					BaseResult<List<GoogleEvent>> newEvents = SubjectEventDetails.getEventsFromSchedule(studentId, semester);
					if (newEvents.isStatus()) {
						String newHash = SubjectEventDetails.scheduleHash;
						String oldHash = calendarDetail.getScheduleHash();

						if (!newHash.equals(oldHash)) {// neu thoi khoa bieu thay doi
							Set<Event> oldEvents = calendarDetail.getEvents();
							
							if(oldEvents != null && oldEvents.size() > 0) {
								for (Event oldEvent : oldEvents) {
									aPIWrapper.deleteEvent(calendar.getCalendarId(), oldEvent.getEventId());// xoa tren
									eventRepository.delete(oldEvent);
								}
							}

							updateCalendar(calendar, newEvents.getResult(), semesterId, newHash);
							calendarDetailRepository.delete(calendarDetail.getId());
							// thong bao cap nhat thoi khoa bieu thanh cong
							ra.addFlashAttribute("success", "Cập nhật thay đổi thời khóa biểu thành công!");
						} else {
							ra.addFlashAttribute("info", "Thời khóa biểu đã tồn tại!");
						}
					}else {
						ra.addFlashAttribute("error", newEvents.getMessage());
					}
				} else {// ma sinh viên da duoc them, hoc ky chua duoc them
					BaseResult<Set<String>> result = updateCalendar(calendar, scheduleCreate);
					
					if (result.isStatus()) {
						if(result.getResult().size() > 0) {
							ra.addFlashAttribute("success", "Thêm lịch thành công!");
						}else {
							ra.addFlashAttribute("info", result.getMessage());
						}
					} else {
						ra.addFlashAttribute("error", result.getMessage());
					}
				}
			}
		} else { // ma sinh vien chua duoc tao lich
			// Nếu chưa thêm thì insert vào ggcalen và thêm calendar vào db
			BaseResult<List<GoogleEvent>> result = addCalendar(scheduleCreate);
			if (result.isStatus()) {
				if(result.getResult().size() > 0) {
					ra.addFlashAttribute("success", "Thêm lịch thành công!");
				}else {
					ra.addFlashAttribute("info", result.getMessage());
				}
			} else {
				ra.addFlashAttribute("error", result.getMessage());
			}
		}

		return "redirect:/schedule/create";
	}

	private BaseResult<List<GoogleEvent>> addCalendar(ScheduleCreate scheduleCreate)
			throws IOException, NoSuchAlgorithmException, ParseException {
		String studentId = scheduleCreate.getStudentId();
		String semesterId = scheduleCreate.getSemester();

		Semester semester = semesterRepository.findById(semesterId);
		BaseResult<List<GoogleEvent>> result = SubjectEventDetails.getEventsFromSchedule(studentId, semester);
		if (result.isStatus()) {

			String summary = AppConstant.SCHEDULE_SUMMARY + studentId;

			aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());

			// lấy tkb
			List<GoogleEvent> ggEvents = result.getResult();
			if (ggEvents.size() > 0) {
				GoogleCalendar ggcalen = aPIWrapper.insertCalendar(summary, CalendarConstant.TIME_ZONE);
				User user = UserDetailsServiceImpl.getUser();
				Set<String> eventIds = scheduleService.insert(ggcalen.getId(), ggEvents);
				if (eventIds.size() > 0) {
					HashSet<Event> events = new HashSet<>();
					for (String eventId : eventIds) {
						Event e = new Event(eventId);
						events.add(e);
					}
					// Thêm tuần của học kỳ
					scheduleService.insert1(ggcalen.getId(), result.getWeekEvents());

					String scheduleHash = SubjectEventDetails.scheduleHash;//

					CalendarDetail calendarDetail = new CalendarDetail(semester, scheduleHash, events);

					Calendar calendar = new Calendar(user, studentId, ggcalen.getId(), false, calendarDetail);

					calendarRepository.save(calendar);
				}
			}
		}
		// thong bao loi
		return result;
	}

	private BaseResult<Set<String>> updateCalendar(Calendar calendar, ScheduleCreate scheduleCreate)// lich cua hoc ky chu duoc them
			throws NoSuchAlgorithmException, IOException, ParseException {
		HashSet<String> eventIds;
		BaseResult<Set<String>> result = scheduleService.insert(calendar.getCalendarId(), scheduleCreate);
		// insert list event, calendardetail, calendar
		if (result.isStatus()) {
			eventIds = (HashSet<String>) result.getResult();
			if (eventIds.size() > 0) {
				HashSet<Event> events = new HashSet<>();
				for (String eventId : eventIds) {
					Event e = new Event(eventId);
					events.add(e);
				}
				// Thêm tuần của học kỳ
				scheduleService.insert1(calendar.getCalendarId(), result.getWeekEvents());

				CalendarDetail calendarDetail = new CalendarDetail(
						semesterRepository.findById(scheduleCreate.getSemester()), SubjectEventDetails.scheduleHash,
						events);
				// cập nhật calendar
				calendar.addCalendarDetails(calendarDetail);

				calendarRepository.save(calendar);
			}

		}

		return result;
	}

	private void updateCalendar(Calendar calendar, List<GoogleEvent> newEvents, String semester, String newHash) {
		HashSet<String> eventIds;

		try {
			eventIds = (HashSet<String>) scheduleService.insert(calendar.getCalendarId(), newEvents);

			// insert list event, calendardetail, calendar
			if (eventIds.size() > 0) {
				HashSet<Event> events = new HashSet<>();
				for (String eventId : eventIds) {
					Event e = new Event(eventId);
					events.add(e);
				}
				CalendarDetail calendarDetail = new CalendarDetail(semesterRepository.findById(semester), newHash,
						events);
				// cập nhật calendar
				calendar.addCalendarDetails(calendarDetail);

				calendarRepository.save(calendar);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
