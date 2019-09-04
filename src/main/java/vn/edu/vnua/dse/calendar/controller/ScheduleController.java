package vn.edu.vnua.dse.calendar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
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
	public String Create(@ModelAttribute("scheduleCreate") ScheduleCreate scheduleCreate, Model model,
			RedirectAttributes ra) throws IOException, ParseException, NoSuchAlgorithmException {
		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());

		String semesterId = scheduleCreate.getSemester();
		String studentId = scheduleCreate.getStudentId();
		User user = UserDetailsServiceImpl.getUser();
		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		// 1. Kiểm tra xem thời với mã sinh viên nhập vào đã được thêm chưa find
		Optional<List<Calendar>> calenOptional = calendarRepository.findByStudentIdAndTypeAndUserId(studentId, false,
				user.getId());

		// Nếu không có lịch với mã sinh viên được thêm
		if (!calenOptional.isPresent()) {
			BaseResult<List<GoogleEvent>> result = addCalendar(scheduleCreate);
			addFlashAtributes(ra, result);
			return "redirect:/schedule/create";
		}

		// Nếu đã có lịch với mã sinh viên truyền vào
		List<Calendar> calendars = calenOptional.get();
		Calendar calendar = filterCalendar(calendars);
		if (calendar == null) {
			BaseResult<List<GoogleEvent>> result = addCalendar(scheduleCreate);
			addFlashAtributes(ra, result);
			return "redirect:/schedule/create";
		}

		// kiem tra xem calendar voi hoc ky nhap vao da co chua
		Optional<CalendarDetail> detailOptional = calendarDetailRepository.findByCalendarAndSemester(calendar,
			semesterRepository.findOne(semesterId));
		if (!detailOptional.isPresent()) {
		    BaseResult<Set<String>> result = updateCalendar(calendar, scheduleCreate);
		    addFlashAtributesString(ra, result);
			return "redirect:/schedule/create";
		}

		CalendarDetail calendarDetail = detailOptional.get();
		handlingChanges(ra, calendarDetail, scheduleCreate, calendar);

		return "redirect:/schedule/create";
	}

	/**
	 * @param ra
	 * @param result
	 */
	private void addFlashAtributesString(RedirectAttributes ra, BaseResult<Set<String>> result) {
		// TODO Auto-generated method stub
		if (!result.isStatus()) {
			ra.addFlashAttribute("error", result.getMessage());
			return;
		}
		if (result.getResult().size() > 0) {
			ra.addFlashAttribute("success", "Thêm lịch thành công!");
		} else {
			ra.addFlashAttribute("info", result.getMessage());
		}
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
			if(calendar != null) {
				String calendarId = calendar.getCalendarId(); // kiểm tra xem calendar còn trên calendar list cua nguoi dung
				// khong
				GoogleCalendar googleCalendar = aPIWrapper.findCalendarInCalendarList(calendarId);
				// Nếu lịch nào chỉ có trong db mà không có trên GGCalendar thì xóa trong db
				if (googleCalendar == null) {
//					results.remove(calendar);
					calendarRepository.delete(calendar);
				}else {
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
	 * @param scheduleCreate
	 * @param calendar
	 * @throws ParseException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	private void handlingChanges(RedirectAttributes ra, CalendarDetail calendarDetail, ScheduleCreate scheduleCreate,
			Calendar calendar) throws NoSuchAlgorithmException, IOException, ParseException {
		// lay list new Events
		String studentId = scheduleCreate.getStudentId();
		String semesterId = scheduleCreate.getSemester();
		Semester semester = semesterRepository.findById(semesterId);
		BaseResult<List<GoogleEvent>> newEvents = SubjectEventDetails.getEventsFromSchedule(studentId, semester);
		if (!newEvents.isStatus()) {
			ra.addFlashAttribute("error", newEvents.getMessage());
			return;
		}

		String newHash = SubjectEventDetails.scheduleHash;
		String oldHash = calendarDetail.getScheduleHash();
		// Nếu lịch không thay đổi
		if (newHash.equals(oldHash)) {
			ra.addFlashAttribute("info", "Thời khóa biểu đã tồn tại!");
			return;
		}
		// Nếu lịch thay đổi
		Set<Event> oldEvents = calendarDetail.getEvents();
		for (Event oldEvent : oldEvents) {
			aPIWrapper.deleteEvent(calendar.getCalendarId(), oldEvent.getEventId());// xoa tren
			eventRepository.delete(oldEvent);
		}

		updateCalendar(calendar, newEvents.getResult(), semester.getId(), newHash);
		calendarDetailRepository.delete(calendarDetail.getId());
		// thong bao cap nhat thoi khoa bieu thanh cong
		ra.addFlashAttribute("success", "Cập nhật thay đổi thời khóa biểu thành công!");
		return;

	}

	/**
	 * @param ra
	 * @param result
	 */
	private void addFlashAtributes(RedirectAttributes ra, BaseResult<List<GoogleEvent>> result) {
		// TODO Auto-generated method stub
		if (!result.isStatus()) {
			ra.addFlashAttribute("error", result.getMessage());
			return;
		}
		if (result.getResult().size() > 0) {
			ra.addFlashAttribute("success", "Thêm lịch thành công!");
		} else {
			ra.addFlashAttribute("info", result.getMessage());
		}
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

	private BaseResult<Set<String>> updateCalendar(Calendar calendar, ScheduleCreate scheduleCreate)// lich cua hoc ky
																									// chu duoc them
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
