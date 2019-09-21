package vn.edu.vnua.dse.calendar.crawling;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import vn.edu.vnua.dse.calendar.co.BaseResult;
import vn.edu.vnua.dse.calendar.co.ScheduleResult;
import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.ExGoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleDateTime;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.CalendarConstant;
import vn.edu.vnua.dse.calendar.model.Semester;

@Service
public final class SubjectEventDetails {
	
	private static final String DESCRIPTION = "Mã học phần: %s" + "\nMã lớp: %s" + "\nNhóm: %s" + "\nTuần học: %s"
			+ "\nTiết học: %s";
	private static final String DESCRIPTION_HAVE_PRACTICE = "Mã học phần: %s" + "\nMã lớp: %s" + "\nNhóm: %s"
			+ "\nNhóm thực hành: %s" + "\nTuần học: %s" + "\nTiết học: %s";
	
	public static String scheduleHash;

	public static Date semesterStart;

	public static final BaseResult<List<GoogleEvent>> getEventsFromSchedule(String studentId, Semester semester)
			throws IOException, ParseException, NoSuchAlgorithmException {
		ScheduleResult<ArrayList<ArrayList<String>>> scheduleResult = getSchedule(studentId, semester.getId());

		ArrayList<ArrayList<String>> scheduleJson = scheduleResult.getResult();
		ArrayList<String> weekEventJson = getWeekOfSemesEvent(semester);
		if(scheduleResult.isStatus()) {
			if (scheduleJson.size() > 0) {
				// return toGoogleEvent(scheduleJson);
				return new BaseResult<List<GoogleEvent>>(true, toGoogleEvent(scheduleJson), scheduleResult.getMessage(), weekEventJson);	
			}else {
				return new BaseResult<List<GoogleEvent>>(true, toGoogleEvent(scheduleJson), "Học kỳ không có môn học nào, Thêm lịch không thành công!", weekEventJson);	
			}
		}

		return new BaseResult<List<GoogleEvent>>(false, new ArrayList<GoogleEvent>(), scheduleResult.getMessage(), new ArrayList<String>());
	}

	private static final ScheduleResult<ArrayList<ArrayList<String>>> getSchedule(String studentId, String semesId)
			throws IOException, NoSuchAlgorithmException, ParseException {
		// Mo trinh duyet
		WebDriver driver = ScheduleUtils.openChrome();

		// driver.manage().window().setPosition(new Point(-1000, -1000));
		driver.get(String.format(ScheduleConstant.SCHEDULE_URL, studentId));

		WebDriverWait wait = new WebDriverWait(driver, 5);

		// check update
		if (AppUtils.isAlertPresent(driver)) {
			Alert alert = driver.switchTo().alert();
			// Capturing alert message.
			String alertMessage = driver.switchTo().alert().getText();
			System.out.println(alertMessage);
			// Accepting alert
			alert.accept();
			String message = "error";
			if (alertMessage.equals("Server đang tải lại dữ liệu. Vui lòng trở lại sau 15 phút!")) {
				message = "Server đang tải lại dữ liệu. Vui lòng trở lại sau 15 phút!";
			}
			driver.close();
			driver.quit();
			return new ScheduleResult<ArrayList<ArrayList<String>>>(false, new ArrayList(), message, null);
		} else {
			ScheduleUtils.injectResourceJQuery(driver, "js/MyJQuery.js");
			JavascriptExecutor jse = ((JavascriptExecutor) driver);
			String passCap = ScheduleUtils.readResourceFile("js/capcha.js");
			jse.executeScript(passCap);
			driver.navigate().to(String.format(ScheduleConstant.SCHEDULE_URL, studentId));
			//check thong tin sinh vien 
			//wait.until(ExpectedConditions.presenceOfElementLocated(By.id(ScheduleConstant.CONTENT_MSV)));
			
			if(driver.findElements(By.id(ScheduleConstant.HEADER_DAOTAO_ID)).size() == 0){
				driver.close();
				driver.quit();
				return new ScheduleResult<ArrayList<ArrayList<String>>>(false, new ArrayList(), "Trang đạo tạo VNUA hiện không truy cập được \nVui lòng thử lại sau!", scheduleHash);
			}
			if(driver.findElements(By.id(ScheduleConstant.CONTENT_MSV)).size() == 0){
				driver.close();
				driver.quit();
				return new ScheduleResult<ArrayList<ArrayList<String>>>(false, new ArrayList(), "Không tìm thấy thông tin thời khóa biểu sinh viên/giảng viên", scheduleHash);
			}
			// chon hoc ky
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(ScheduleConstant.SCHEDULE_ELEMENT)));
			Select dropdown = new Select(driver.findElement(By.id(ScheduleConstant.SCHEDULE_ELEMENT)));
			try {
				dropdown.selectByValue(semesId);
			} catch (Exception e) {
				// TODO: handle exception
				driver.close();
				driver.quit();

				scheduleHash = null;
				return new ScheduleResult<ArrayList<ArrayList<String>>>(false, new ArrayList(), "error", scheduleHash);
			}

			// chon che do xem theo thu tiet
			WebElement radio = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.id(ScheduleConstant.THUTIET_RADIO)));

			if (ExpectedConditions.elementToBeClickable(radio) != null) {
				radio.click();
				
				///
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
				
				String code = ScheduleUtils.readResourceFile("js/getSchedule.js");
				@SuppressWarnings("unchecked")
				ArrayList<ArrayList<String>> scheduleJson = (ArrayList<ArrayList<String>>) jse.executeScript(code);
				
				for(ArrayList<String> json : scheduleJson) {//json = 1 subject
					driver.navigate().to(json.get(14));
					@SuppressWarnings("unused")
					Long siso = (Long) jse.executeScript("return $('#ctl00_ContentPlaceHolder1_ctl00_gvDSSinhVien >tbody>tr').length ?  $('#ctl00_ContentPlaceHolder1_ctl00_gvDSSinhVien >tbody>tr').length - 1: 0;");
					json.add(String.valueOf(siso));
				}

				driver.close();
				driver.quit();

				scheduleHash = AppUtils.getMD5(scheduleJson.toString());
				return new ScheduleResult<ArrayList<ArrayList<String>>>(true, scheduleJson, "Lấy lịch thành công", scheduleHash);
			}

			driver.close();
			driver.quit();

			scheduleHash = null;
			return new ScheduleResult<ArrayList<ArrayList<String>>>(false, new ArrayList(), "Có lỗi xảy ra", scheduleHash);
		}

	}

	private static final List<GoogleEvent> toGoogleEvent(ArrayList<ArrayList<String>> scheduleJson) throws ParseException {
		
		List<GoogleEvent> events = new ArrayList<>();
		Gson gson = new Gson();
		for (ArrayList<String> item : scheduleJson) {
//			// convert json array to java array
//			@SuppressWarnings("rawtypes")
//			ArrayList item = gson.fromJson(json, ArrayList.class);
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
				String subjectName = item.get(1).toString().trim();
				String location = item.get(11).toString();

				String description = getDescription(subjectCode, classCode, group, practiceGroup, weekStudy, startSlot,
						endSlot, item.get(14), item.get(15));

				Date start = getStartTime(weekStudy.get(0), day, startSlot);
				Date end = getEnd_Time(weekStudy.get(0), day, endSlot);
//				String RDATE = getRDATE(weekStudy, day, startSlot);
				int count = ScheduleUtils.getFullWeek(weekStr).size();
				String WEEKLY = getWEEKLY_COUNT(count);
				
				ArrayList<Integer> exceptWeek = ScheduleUtils.getExceptWeek(weekStr);
				ArrayList<String> recurrence = new ArrayList<>();
				recurrence.add(WEEKLY);
				if(exceptWeek.size() > 0) {
					String EXDATE = getEXDATE(exceptWeek, day, startSlot);
					recurrence.add(EXDATE);
				}
				

				String summary = getSummary(subjectName, subjectCode, group, practiceGroup);
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

	private static String getWEEKLY_COUNT(int count) {
		// TODO Auto-generated method stub
		String WEEKLY_COUNT = String.format(CalendarConstant.RRULE_WEEKLY_COUNT, count);
		return WEEKLY_COUNT;
	}

	private static String getDescription(String subjectCode, String classCode, String group, String practiceGroup,
			ArrayList<Integer> weekStudy, int startSlot, int endSlot, String dssv, String siso) {
		String weekDes = ScheduleUtils.joinIntArray(", ", weekStudy);
		String slotDes = startSlot + "-" + endSlot;

		String descpription = "";
		if (practiceGroup.equals("")) {
			descpription = String.format(DESCRIPTION, subjectCode, classCode, group, weekDes, slotDes)+"\n" +"<a href=\"" + dssv +"\">Danh sách sinh viên</a>"+ "\nSĩ số: " + siso;
		} else {
			descpription = String.format(DESCRIPTION_HAVE_PRACTICE, subjectCode, classCode, group, practiceGroup,
					weekDes, slotDes)+"\n" +"<a href=\"" + dssv +"\">Danh sách sinh viên</a>"+ "\nSĩ số: " + siso;
		}
		return descpription;
	}
	
	private static String getSummary(String subjectName, String subjectCode, String group, String practiceGroup) {
		String summary = "";
		if (practiceGroup.equals("")) {
			summary = subjectName + ", " + subjectCode + "_" + group;
		} else {
			summary = "TH " + subjectName + ", " + subjectCode + "_" + group + "_" + practiceGroup;
		}
		
		return summary;
	}

	private static Date getStartTime(int week, int day, int slot) throws ParseException {
		Date semesterDate = semesterStart; // lay ngay băt dau cua ky hoc

		String timeStr = DateTimeConstant.STARTTIME.get(slot);
		Date date = ScheduleUtils.findDay(semesterDate, week, day);
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateStr = dateFormat.format(date);
		// create datetime
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateTimeFormat.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));
		date = dateTimeFormat.parse(dateStr + " " + timeStr);
		//set time zone
		
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
		dateTimeFormat.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));
		date = dateTimeFormat.parse(dateStr + " " + timeStr);
		return date;
	}

	private static ArrayList<String> getWeekOfSemesEvent(Semester semester) {
		String semeseterName = semester.getName();
		
		ArrayList<String> events = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(semester.getStartDate());
		int monthStart = calendar.get(Calendar.MONTH);
		if(monthStart >= 4 && monthStart < 6) {
			for (int i = 0; i < 12; i++) {
				// set date
				Calendar calen = Calendar.getInstance();
				calen.setTime(semester.getStartDate());
				// compute
				calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + i * 7 + 0);//thêm vào mỗi thứ 2
				Date start = calen.getTime();
				calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + 1);
				Date end = calen.getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String startStr = formatter.format(start);
				String endStr = formatter.format(end);
				//start, end, description, visibility
				String event = String.format(ExGoogleEvent.weekStudyEvent, "Tuần " + (i + 1), startStr, endStr, semeseterName, "default");
				events.add(event);
			}
		}else {
			for (int i = 0; i < 19; i++) {
				
				// set date
				Calendar calen = Calendar.getInstance();
				calen.setTime(semester.getStartDate());
				// compute
				calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + i * 7 + 0);//thêm vào mỗi thứ 2
				Date start = calen.getTime();
				calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + 1);
				Date end = calen.getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String startStr = formatter.format(start);
				String endStr = formatter.format(end);
				
				String event = String.format(ExGoogleEvent.weekStudyEvent, "Tuần " + (i + 1), startStr, endStr, semeseterName, "default");
				events.add(event);
			}
		}
		
		
		return events;
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

	
	private static String getEXDATE(ArrayList<Integer> excepWeek, int day, int slot) throws ParseException {
		ArrayList<String> EXDATE_Arr = new ArrayList<>();
		String timeStr = DateTimeConstant.STARTTIME.get(slot);
		for (int i = 0; i < excepWeek.size(); i++) {
			// RDATE.add(MyUtils.formatyyMMddTHHmmss(findDay(date, weekStudy.get(i), day)));
			Date result = ScheduleUtils.findDay(semesterStart, excepWeek.get(i), day);
			String dateSTr = new SimpleDateFormat("yyyy/MM/dd").format(result);

			// create datetime
			result = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(dateSTr + " " + timeStr);
			// format datetime
			EXDATE_Arr.add(ScheduleUtils.formatyyMMddTHHmmss(result));
		}
		String EXDATE = String.join(",", EXDATE_Arr);
		EXDATE = String.format(CalendarConstant.EXDATE, CalendarConstant.TIME_ZONE, EXDATE);

		return EXDATE;
	}
}
