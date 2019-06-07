package vn.edu.vnua.dse.calendar.crawling;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;

import vn.edu.vnua.dse.calendar.co.BaseResult;
import vn.edu.vnua.dse.calendar.co.ScheduleResult;
import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleDateTime;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.CalendarConstant;

public class ExamEventDetails {
	private static final String DESCRIPTION = "Mã học phần: %s" + "\nNhóm: %s" + "\nTổ: %s";

	public static String examScheduleHash;

	// lấy danh dách các event từ lịch thi
	public static final ScheduleResult<List<GoogleEvent>> getEventsFromSchedule(String studentId)
			throws IOException, ParseException, NoSuchAlgorithmException {
		ScheduleResult<ArrayList<String>> examScheduleResult = getExamSchedule(studentId);
		ArrayList<String> examScheduleJson = examScheduleResult.getResult();
		if (examScheduleJson.size() > 0) {
			return new ScheduleResult<List<GoogleEvent>>(true, toGoogleEvent(examScheduleJson),
					examScheduleResult.getMassage(), examScheduleResult.getScheduleHash());
		}

		return new ScheduleResult<List<GoogleEvent>>(false, null, examScheduleResult.getMassage(), null);
	}

	// chuyển từ list json sang list GoogleEvent
	private static List<GoogleEvent> toGoogleEvent(ArrayList<String> examScheduleJson) throws ParseException {
		List<GoogleEvent> events = new ArrayList<>();
		Gson gson = new Gson();

		for (String json : examScheduleJson) {
			// convert json to array
			ArrayList item = gson.fromJson(json, ArrayList.class);

			String subjectCode = item.get(1).toString();
			String subjectName = item.get(2).toString();
			String group = item.get(3).toString();
			String team = item.get(4).toString();
			String dateStr = item.get(6).toString();
			int startSlot = Integer.parseInt(item.get(7).toString());
			int endSlot = startSlot + Integer.parseInt(item.get(8).toString()) - 1;
			String location = item.get(9).toString();

			String summary = subjectName;
			String startTimeStr = DateTimeConstant.STARTTIME.get(startSlot);
			Date start = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateStr + " " + startTimeStr);
			String endTimeStr = DateTimeConstant.ENDTIME.get(endSlot);
			Date end = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateStr + " " + endTimeStr);
			String description = String.format(DESCRIPTION, subjectCode, group, team);

			GoogleEvent event = new GoogleEvent();
			event.setSummary(summary);
			event.setLocation(location);
			event.setStart(new GoogleDateTime(start, CalendarConstant.TIME_ZONE));
			event.setEnd(new GoogleDateTime(end, CalendarConstant.TIME_ZONE));
			event.setDescription(description);
			// set recurrence
			ArrayList<String> RDATE_Arr = new ArrayList<>();
			RDATE_Arr.add(ScheduleUtils.formatyyMMddTHHmmss(start));
			String RDATE = String.join(",", RDATE_Arr);
			RDATE = String.format(CalendarConstant.RDATE, RDATE);
			ArrayList<String> recurrence = new ArrayList<>();
			recurrence.add(RDATE);

			event.setRecurrence(recurrence);
			events.add(event);
			System.out.println("-----------------------------");
			System.out.println(gson.toJson(event));
		}

		return events;
	}

	// Lấy danh sách thời khóa biểu dạng json
	private static ScheduleResult<ArrayList<String>> getExamSchedule(String studentId) throws IOException {
		// Open browser
		WebDriver driver = ScheduleUtils.openChrome();
		driver.manage().window().setPosition(new Point(-1000, -1000));
		String url = String.format(ScheduleConstant.EXAM_SCHEDULE_URL, studentId);
		driver.get(url);

		// Get schedule
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// ScheduleUtils.injectJQuery(driver, ScheduleConstant.JQUERY_FILE);
		ScheduleUtils.injectResourceJQuery(driver, "js/MyJQuery.js");
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		// check update
		if (AppUtils.isAlertPresent(driver)) {
			Alert alert = driver.switchTo().alert();
			// Capturing alert message.
			String alertMessage = driver.switchTo().alert().getText();
			// Accepting alert
			alert.accept();
			String message = "error";
			if (alertMessage.equals("Server đang tải lại dữ liệu. Vui lòng trở lại sau 15 phút!")) {
				message = "update";
			}
			driver.close();
			driver.quit();
			return new ScheduleResult<ArrayList<String>>(false, new ArrayList<String>(), message, null);
		} else {
			String passCap = ScheduleUtils.readResourceFile("js/capcha.js");
			jse.executeScript(passCap);

			// Lay lich thi
			String code = ScheduleUtils.readResourceFile("js/getExamSchedule.js");
			@SuppressWarnings("unchecked")
			ArrayList<String> examScheduleJson = (ArrayList<String>) jse.executeScript(code);

			driver.close();
			driver.quit();

			examScheduleHash = AppUtils.getMD5(examScheduleJson.toString());
			return new ScheduleResult<ArrayList<String>>(true, examScheduleJson, "success", examScheduleHash);
		}
	}
}
