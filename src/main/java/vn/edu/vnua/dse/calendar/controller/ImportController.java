package vn.edu.vnua.dse.calendar.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import vn.edu.vnua.dse.calendar.co.ImportCreate;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleCalendar;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
import vn.edu.vnua.dse.calendar.service.AuthorizationService;
import vn.edu.vnua.dse.calendar.service.ImportServiceImpl;
import vn.edu.vnua.dse.calendar.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/import/")
public class ImportController {
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String Create(Model model) throws IOException {

		if (AuthorizationService.isauthorized() != null) {
			return "redirect:" + AuthorizationService.isauthorized();
		}
		// add list calendar
		Map<String, String> calendars = new HashMap<String, String>();
		// get list calendar
		APIWrapper apiWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		ArrayList<GoogleCalendar> calendarList = (ArrayList<GoogleCalendar>) apiWrapper.getCalendarList();
		// put into list
		for (GoogleCalendar googleCalendar : calendarList) {
			calendars.put(googleCalendar.getId(), googleCalendar.getSummary());
		}
		// add to attribute
		model.addAttribute("calendars", calendars);
		model.addAttribute("importCreate", new ImportCreate());
		return "import/create";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String Create(Model model, ImportCreate importCreate, HttpServletRequest request) throws IOException {
		String calendarId = importCreate.getCalendarId();

		String uploadRootPath = request.getServletContext().getRealPath("upload");
		System.out.println("uploadRootPath=" + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);
		//
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		try {
			MultipartFile multipartFile = importCreate.getMultipartFile();
//      String fileName = multipartFile.getOriginalFilename();
			File file = new File(uploadRootDir.getAbsolutePath() + File.separator + "event.xlsx");

			multipartFile.transferTo(file);
			// code
			ImportServiceImpl.insert(calendarId, file);

			file.delete();

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "upload failed");
		}

		// add list calendar
		Map<String, String> calendars = new HashMap<String, String>();
		// get list calendar
		APIWrapper apiWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		ArrayList<GoogleCalendar> calendarList = (ArrayList<GoogleCalendar>) apiWrapper.getCalendarList();
		// put into list
		for (GoogleCalendar googleCalendar : calendarList) {
			calendars.put(googleCalendar.getId(), googleCalendar.getSummary());
		}
		// add to attribute
		model.addAttribute("calendars", calendars);
		model.addAttribute("importCreate", new ImportCreate());
		
		return "import/create";
	}
}
