package vn.edu.vnua.dse.calendar.crawling;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;

import vn.edu.vnua.dse.calendar.co.BaseResult;
import vn.edu.vnua.dse.calendar.co.ScheduleResult;
import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleDateTime;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.CalendarConstant;

public final class SubjectEventDetails {
	private static final String DESCRIPTION = "Mã học phần: %s" + "\nMã lớp: %s" + "\nNhóm: %s" + "\nTuần học: %s"
			+ "\nTiết học: %s";
	private static final String DESCRIPTION_HAVE_PRACTICE = "Mã học phần: %s" + "\nMã lớp: %s" + "\nNhóm: %s"
			+ "\nNhóm thực hành: %s" + "\nTuần học: %s" + "\nTiết học: %s";

	public static String scheduleHash;

	public static Date semesterStart;

	public static final BaseResult<List<GoogleEvent>> getEventsFromSchedule(String studentId, String semesId)
			throws IOException, ParseException, NoSuchAlgorithmException {
		ScheduleResult<ArrayList<String>> scheduleResult = getSchedule(studentId, semesId);

		ArrayList<String> scheduleJson = scheduleResult.getResult();
		if (scheduleJson.size() > 0 && scheduleJson != null) {
			// return toGoogleEvent(scheduleJson);
			return new BaseResult<List<GoogleEvent>>(true, toGoogleEvent(scheduleJson), scheduleResult.getMassage());
		}

		return new BaseResult<List<GoogleEvent>>(false, null, scheduleResult.getMassage());
	}

	private static final ScheduleResult<ArrayList<String>> getSchedule(String studentId, String semesId)
			throws IOException, NoSuchAlgorithmException, ParseException {
		// Mo trinh duyet
		WebDriver driver = ScheduleUtils.openChrome();
		driver.manage().window().setPosition(new Point(-1000, -1000));
		driver.get(String.format(ScheduleConstant.SCHEDULE_URL, studentId));

		WebDriverWait wait = new WebDriverWait(driver, 10);

		ScheduleUtils.injectResourceJQuery(driver, "js/MyJQuery.js");
		JavascriptExecutor jse = ((JavascriptExecutor) driver);

		// check update
		if (AppUtils.isAlertPresent(driver)) {
			Alert alert = driver.switchTo().alert();
			// Capturing alert message.
			String alertMessage = driver.switchTo().alert().getText();
			System.out.println(alertMessage);
			// Accepting alert
			alert.accept();
			String message = "error";
			System.out.println("Server đang tải lại dữ liệu. Vui lòng trở lại sau 15 phút!");
			if (alertMessage.equals("Server đang tải lại dữ liệu. Vui lòng trở lại sau 15 phút!")) {
				message = "update";
			}
			driver.close();
			driver.quit();
			return new ScheduleResult<ArrayList<String>>(false, new ArrayList<String>(), message, null);
		} else {
			String passCap = ScheduleUtils.readResourceFile("js/capcha.js");
			jse.executeScript(passCap);

			// chon hoc ky
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(ScheduleConstant.SCHEDULE_ELEMENT)));
			Select dropdown = new Select(driver.findElement(By.id(ScheduleConstant.SCHEDULE_ELEMENT)));
			dropdown.selectByValue(semesId);

			// chon che do xem theo thu tiet
			WebElement radio = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.id(ScheduleConstant.THUTIET_RADIO)));

			if (ExpectedConditions.elementToBeClickable(radio) != null) {
				radio.click();
				String code = ScheduleUtils.readResourceFile("js/getSchedule.js");
				@SuppressWarnings("unchecked")
				ArrayList<String> scheduleJson = (ArrayList<String>) jse.executeScript(code);
				String semesStartDate = (String) jse
						.executeScript("return semesStart = $('#ctl00_ContentPlaceHolder1_ctl00_lblNote').text()");
				// get day string
				Pattern pattern = Pattern.compile("\\d{1,2}\\/\\d{1,2}\\/\\d{4}");
				Matcher matcher = pattern.matcher(semesStartDate);
				String semesStartDateStr = "";
				if (matcher.find()) {
					semesStartDateStr = matcher.group().trim();
				}

				semesterStart = new SimpleDateFormat("dd/MM/yyyy").parse(semesStartDateStr);

				driver.close();
				driver.quit();

				scheduleHash = AppUtils.getMD5(scheduleJson.toString());
				return new ScheduleResult<ArrayList<String>>(true, scheduleJson, "success", scheduleHash);
			}

			driver.close();
			driver.quit();

			scheduleHash = null;
			return new ScheduleResult<ArrayList<String>>(false, null, "error", scheduleHash);
		}

	}

	private static final List<GoogleEvent> toGoogleEvent(ArrayList<String> scheduleJson) throws ParseException {
		List<GoogleEvent> events = new ArrayList<>();
		Gson gson = new Gson();
		for (String json : scheduleJson) {
			// convert json array to java array
			@SuppressWarnings("rawtypes")
			ArrayList item = gson.fromJson(json, ArrayList.class);
			// convert java array to SubjectEvent object -> function getSubjectEvent
			String classCode = item.get(4).toString().trim();
			if (!classCode.equals("")) {
				String subjectCode = item.get(0).toString().trim();
				String group = item.get(2).toString().trim();
				String practiceGroup = item.get(7).toString().trim().replace("\n", "");
				int day = ScheduleUtils.getDay(item.get(8).toString().trim());
				int startSlot = Integer.parseInt(item.get(9).toString().trim());
				int endSlot = startSlot + Integer.parseInt(item.get(10).toString().trim()) - 1;
				String weekStr = item.get(13).toString().trim();
				ArrayList<Integer> weekStudy = ScheduleUtils.getWeek(weekStr);
				String summary = item.get(1).toString().trim();
				String location = item.get(11).toString();

				String description = getDescription(subjectCode, classCode, group, practiceGroup, weekStudy, startSlot,
						endSlot);

				Date start = getStartTime(weekStudy.get(0), day, startSlot);
				Date end = getEnd_Time(weekStudy.get(0), day, endSlot);
				String RDATE = getRDATE(weekStudy, day, startSlot);
				ArrayList<String> recurrence = new ArrayList<>();
				recurrence.add(RDATE);

				GoogleEvent event = new GoogleEvent();
				event.setSummary(summary);
				event.setLocation(location);
				event.setDescription(description);
				event.setStart(new GoogleDateTime(start, CalendarConstant.TIME_ZONE));
				event.setEnd(new GoogleDateTime(end, CalendarConstant.TIME_ZONE));
				event.setRecurrence(recurrence);

				events.add(event);
				System.out.println("-----------------------------");
				System.out.println(gson.toJson(event));
			}
		}

		return events;
	}

	private static String getDescription(String subjectCode, String classCode, String group, String practiceGroup,
			ArrayList<Integer> weekStudy, int startSlot, int endSlot) {
		String weekDes = ScheduleUtils.joinIntArray(", ", weekStudy);
		String slotDes = startSlot + " - " + endSlot;

		String descpription = "";
		if (practiceGroup.equals("")) {
			descpription = String.format(DESCRIPTION, subjectCode, classCode, group, weekDes, slotDes);
		} else {
			descpription = String.format(DESCRIPTION_HAVE_PRACTICE, subjectCode, classCode, group, practiceGroup,
					weekDes, slotDes);
		}
		return descpription;
	}

	private static Date getStartTime(int week, int day, int slot) throws ParseException {
		Date semesterDate = semesterStart; // lay ngay băt dau cua ky hoc

		String timeStr = DateTimeConstant.STARTTIME.get(slot);
		Date date = ScheduleUtils.findDay(semesterDate, week, day);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateStr = dateFormat.format(date);
		// create datetime
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = dateTimeFormat.parse(dateStr + " " + timeStr);
		return date;
	}

	private static Date getEnd_Time(int week, int day, int slot) throws ParseException {
		Date semesterDate = semesterStart; // lay ngay băt dau cua ky hoc

		String timeStr = DateTimeConstant.ENDTIME.get(slot);
		Date date = ScheduleUtils.findDay(semesterDate, week, day);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateStr = dateFormat.format(date);
		// create datetime
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = dateTimeFormat.parse(dateStr + " " + timeStr);
		return date;
	}

	private static String getRDATE(ArrayList<Integer> weekStudy, int day, int slot) throws ParseException {
		ArrayList<String> RDATE_Arr = new ArrayList<>();
		String timeStr = DateTimeConstant.STARTTIME.get(slot);
		for (int i = 0; i < weekStudy.size(); i++) {
			// RDATE.add(MyUtils.formatyyMMddTHHmmss(findDay(date, weekStudy.get(i), day)));
			Date result = ScheduleUtils.findDay(semesterStart, weekStudy.get(i), day);
			String dateSTr = new SimpleDateFormat("yyyy/MM/dd").format(result);

			// create datetime
			result = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(dateSTr + " " + timeStr);
			// format datetime
			RDATE_Arr.add(ScheduleUtils.formatyyMMddTHHmmss(result));
		}
		String RDATE = String.join(",", RDATE_Arr);
		RDATE = String.format(CalendarConstant.RDATE, RDATE);

		return RDATE;
	}

}
