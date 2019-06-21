package vn.edu.vnua.dse.calendar.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.vnua.dse.calendar.crawling.ScheduleUtils;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.ExGoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleDateTime;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.CalendarConstant;

@Service("importService")
public class ImportServiceImpl {
	@Autowired
	static APIWrapper aPIWrapper;

	public static boolean insert(String calendarId, File file) throws IOException {
		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		try {
			List<String> eventJsons = getEventsFromExcelFile2(file);

			if (eventJsons.size() > 0) {
				for (String eventJson : eventJsons) {
					aPIWrapper.insertEvent(calendarId, eventJson);
				}
			}
			return true;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("resource")
	public List<GoogleEvent> getEventsFromExcelFile(String path) {
		// Đọc một file XSLS.
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Có lỗi khi đọc file");
			return null;
		}

		// Đối tượng workbook cho file XSLS.
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Có lỗi khi tạo workbook");
			return null;
		}

		// Lấy ra sheet đầu tiên từ workbook
		XSSFSheet sheet = workbook.getSheetAt(0);
		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();

		List<GoogleEvent> events = new ArrayList<>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(4) != null) {
				try {
					String summary = row.getCell(0).getStringCellValue();
					String description = row.getCell(6).getStringCellValue();
					String location = row.getCell(7).getStringCellValue();
					boolean allday = row.getCell(5).getBooleanCellValue();
					String startDateStr = row.getCell(1).getStringCellValue();
					String startTimeStr = row.getCell(2).getStringCellValue();
					String endDateStr = row.getCell(3).getStringCellValue();
					String endTimeStr = row.getCell(4).getStringCellValue();
					boolean _private = row.getCell(8).getBooleanCellValue();

					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
					formatter.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));

					Date start;
					start = formatter.parse(startDateStr + startTimeStr);
					Date end = formatter.parse(endDateStr + endTimeStr);

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
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("Lỗi khi đọc file excel");
				}
			}
		}
		return events;
	}

	@SuppressWarnings({ "resource", "unused" })
	private static List<String> getEventsFromExcelFile2(File file)
			throws FileNotFoundException, IOException, ParseException {
		// Đọc một file XSLS.
		List<String> events = new ArrayList<>();
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Có lỗi khi đọc file");
			return null;
		}

		// Đối tượng workbook cho file XSLS.
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//					e.printStackTrace();
			System.out.println("Có lỗi khi tạo workbook");
			return null;
		}

		// Lấy ra sheet đầu tiên từ workbook
		XSSFSheet sheet = workbook.getSheetAt(0);
		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();
		Row fisrt = rowIterator.next();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			try {
				boolean allday = row.getCell(5).getBooleanCellValue();
				if (!allday && row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null
						&& row.getCell(4) != null) {

					String summary = row.getCell(0).getStringCellValue();
					String description = row.getCell(6).getStringCellValue();
					String location = row.getCell(7).getStringCellValue();
					// boolean allday = row.getCell(5).getBooleanCellValue();
					String startDateStr = row.getCell(1).getStringCellValue();
					String startTimeStr = row.getCell(2).getStringCellValue();
					String endDateStr = row.getCell(3).getStringCellValue();
					String endTimeStr = row.getCell(4).getStringCellValue();
					boolean _private = row.getCell(8).getBooleanCellValue();
					String visibility = "default";
					if (_private) {
						visibility = "private";
					}

					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
					formatter.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));
					Date start;
					start = formatter.parse(startDateStr + startTimeStr);
					Date end = formatter.parse(endDateStr + endTimeStr);
					// start, end, description, location, visibility "yyyy-MM-dd'T'HH:mm:ssZ"
					formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
					String startStr = formatter.format(start);
					String endStr = formatter.format(end);
					String event = String.format(ExGoogleEvent.event, summary, startStr, endStr, description,
							location, visibility);

					events.add(event);
				}
				if (allday && row.getCell(1) != null && row.getCell(3) != null) {
					String summary = row.getCell(0).getStringCellValue();
					String description = row.getCell(6).getStringCellValue();
					String location = row.getCell(7).getStringCellValue();
					// boolean allday = row.getCell(5).getBooleanCellValue();
					String startDateStr = row.getCell(1).getStringCellValue();
					String startTimeStr = row.getCell(2).getStringCellValue();
					String endDateStr = row.getCell(3).getStringCellValue();
					String endTimeStr = row.getCell(4).getStringCellValue();
					boolean _private = row.getCell(8).getBooleanCellValue();
					String visibility = "default";
					if (_private) {
						visibility = "private";
					}

					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date start;
					start = formatter.parse(startDateStr);
					Date end = formatter.parse(endDateStr);

					formatter = new SimpleDateFormat("yyyy-MM-dd");
					String startStr = formatter.format(start);
					String endStr = formatter.format(end);
					String event = String.format(ExGoogleEvent.allDayEvent, summary, startStr, endStr, description,	location, visibility);

					events.add(event);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("Lỗi khi đọc file excel");
			}
		}
		return events;
	}
}
