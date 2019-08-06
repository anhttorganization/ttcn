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

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.edu.vnua.dse.calendar.co.BaseResult;
import vn.edu.vnua.dse.calendar.common.AppConstant;
import vn.edu.vnua.dse.calendar.crawling.ScheduleUtils;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.ExGoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleAllDayEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleDate;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleDateTime;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.CalendarConstant;

@Service("importService")
public class ImportServiceImpl {
	@Autowired
	static APIWrapper aPIWrapper;

	public static String insert(String calendarId, File file) throws IOException {
		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		try {
			BaseResult<List<String>> result = getEventsFromExcelFile0(file);
			List<String> eventJsons = result.getResult();

			if (eventJsons.size() > 0) {
				for (String eventJson : eventJsons) {
					aPIWrapper.insertEvent(calendarId, eventJson);
				}
			}
			return result.getMessage();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Có lỗi xảy ra!";
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
	private static BaseResult<List<String>> getEventsFromExcelFile0(File file)
			throws FileNotFoundException, IOException, ParseException {
		// Đọc một file XSLS.
		List<String> errors = new ArrayList<>();
		List<String> events = new ArrayList<>();
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Có lỗi khi đọc file");
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Có lỗi khi đọc file excel!");
		}

		// Đối tượng workbook cho file XSLS.
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//					e.printStackTrace();
			System.out.println("Có lỗi khi tạo workbook");
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Có lỗi khi đọc file excel!");
		}

		// Lấy ra sheet đầu tiên từ workbook
		XSSFSheet sheet = workbook.getSheetAt(0);
		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();
		Row first = rowIterator.next();
		
		//Kiem tra header
		if(first.getCell(0).getStringCellValue().equals("Subject") && first.getCell(1).getStringCellValue().equals("Start Date")
				&&first.getCell(2).getStringCellValue().equals("Start Time")&&first.getCell(3).getStringCellValue().equals("End Date")
				&&first.getCell(4).getStringCellValue().equals("End Time")&&first.getCell(5).getStringCellValue().equals("All day event")
				&&first.getCell(6).getStringCellValue().equals("Description")&&first.getCell(7).getStringCellValue().equals("Location")) 
		{
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if(checkIfRowIsEmpty(row)) {
					continue;
				}
				
				try {
					boolean isAllDay;
					if(row.getCell(5).getCellTypeEnum() == CellType.BOOLEAN) {
						isAllDay = row.getCell(5).getBooleanCellValue();
					}else {
						String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 1, "Sai định dạng, cột \"All day event\" phải để kiểu BOOLEAN");
						return new BaseResult<List<String>>(false, new ArrayList<>(), error);
					}
					if (!isAllDay && row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(4) != null) 
					{
						String summary = row.getCell(0).getStringCellValue();

						Date startDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(1))){
							startDate = row.getCell(1).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 1, "Sai định dạng, cột \"Start Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						Date startTime = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(2))){
							startTime = row.getCell(2).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 2, "Sai định dạng, cột \"Start Time\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						Date endDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(3))){
							endDate = row.getCell(3).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 3, "Sai định dạng, cột \"End Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						Date endTime = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(4))){
							endTime = row.getCell(4).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 4, "Sai định dạng, cột \"End Time\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						String description = row.getCell(6).getStringCellValue();
						String location = row.getCell(7).getStringCellValue();
						
						String startDateStr = new SimpleDateFormat("dd/MM/yyyy").format(startDate);
						String startTimeStr = new SimpleDateFormat("HH:mm:ss").format(startTime);
						String endDateStr = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
						String endTimeStr = new SimpleDateFormat("HH:mm:ss").format(endTime);
						
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
						
						Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.000+07:00").disableHtmlEscaping()
								.excludeFieldsWithoutExposeAnnotation().create();
						
						String eventJson = gson.toJson(event);
						events.add(eventJson);
						
					}
					if (isAllDay && row.getCell(1) != null && row.getCell(3) != null) {
						String summary = row.getCell(0).getStringCellValue();

						Date startDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(1))){
							startDate = row.getCell(1).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 1, "Sai định dạng, cột \"Start Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, null, error);
						}
						
						Date endDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(3))){
							endDate = row.getCell(3).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 3, "Sai định dạng, cột \"End Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, null, error);
						}
						
						String description = row.getCell(6).getStringCellValue();
						String location = row.getCell(7).getStringCellValue();

						GoogleAllDayEvent event = new GoogleAllDayEvent();
						event.setSummary(summary);
						event.setStart(new GoogleDate(startDate));
						event.setEnd(new GoogleDate(endDate));
						event.setDescription(description);
						event.setLocation(location);
						
						
						Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();						
						String eventJson = gson.toJson(event);

						events.add(eventJson);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("Lỗi khi đọc file excel");
					return new BaseResult<List<String>>(false, new ArrayList<>(), "Có lỗi khi đọc file excel!");
				}
			}
		}else {		
			if(!first.getCell(0).getStringCellValue().equals("Subject")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ nhất!");
			}else if(!first.getCell(1).getStringCellValue().equals("Start Date")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 2!");
			}else if(!first.getCell(2).getStringCellValue().equals("Start Time")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 3!");
			}else if(!first.getCell(3).getStringCellValue().equals("End Date")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 4!");
			}else if(!first.getCell(4).getStringCellValue().equals("End Time")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 5!");
			}else if(!first.getCell(5).getStringCellValue().equals("All day event")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 6!");
			}else if(!first.getCell(6).getStringCellValue().equals("Description")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 7!");
			}else if(!first.getCell(7).getStringCellValue().equals("Location")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 8!");
			}
		}
		return new BaseResult<List<String>>(true, events, "Đọc sự kiện từ file excel thành công!");

		
	}
	

	
	@SuppressWarnings({ "resource", "unused" })
	private static BaseResult<List<String>> getEventsFromExcelFile2(File file)
			throws FileNotFoundException, IOException, ParseException {
		// Đọc một file XSLS.
		List<String> errors = new ArrayList<>();
		List<String> events = new ArrayList<>();
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Có lỗi khi đọc file");
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Có lỗi khi đọc file excel!");
		}

		// Đối tượng workbook cho file XSLS.
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//					e.printStackTrace();
			System.out.println("Có lỗi khi tạo workbook");
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Có lỗi khi đọc file excel!");
		}

		// Lấy ra sheet đầu tiên từ workbook
		XSSFSheet sheet = workbook.getSheetAt(0);
		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();
		Row first = rowIterator.next();
		
		//Kiem tra header
		if(first.getCell(0).getStringCellValue().equals("Subject") && first.getCell(1).getStringCellValue().equals("Start Date")
				&&first.getCell(2).getStringCellValue().equals("Start Time")&&first.getCell(3).getStringCellValue().equals("End Date")
				&&first.getCell(4).getStringCellValue().equals("End Time")&&first.getCell(5).getStringCellValue().equals("All day event")
				&&first.getCell(6).getStringCellValue().equals("Description")&&first.getCell(7).getStringCellValue().equals("Location")) 
		{
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if(checkIfRowIsEmpty(row)) {
					continue;
				}
				
				try {
					boolean allday = row.getCell(5).getBooleanCellValue();
					if (!allday && row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(4) != null) 
					{
						String summary = row.getCell(0).getStringCellValue();

						Date startDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(1))){
							startDate = row.getCell(1).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 1, "Sai định dạng, cột \"Start Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						Date startTime = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(2))){
							startTime = row.getCell(2).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 2, "Sai định dạng, cột \"Start Time\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						Date endDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(3))){
							endDate = row.getCell(3).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 3, "Sai định dạng, cột \"End Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						Date endTime = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(4))){
							endTime = row.getCell(4).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 4, "Sai định dạng, cột \"End Time\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						boolean isAllDay;
						if(row.getCell(5).getCellTypeEnum() == CellType.BOOLEAN) {
							isAllDay = row.getCell(5).getBooleanCellValue();
						}
						
						String description = row.getCell(6).getStringCellValue();
						String location = row.getCell(7).getStringCellValue();

						String startDateStr = new SimpleDateFormat("dd/MM/yyyy").format(startDate);
						String startTimeStr = new SimpleDateFormat("HH:mm:ss").format(startTime);
						String endDateStr = new SimpleDateFormat("dd/MM/yyyy").format(startDate);
						String endTimeStr = new SimpleDateFormat("HH:mm:ss").format(startTime);
						
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
						formatter.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));
						Date start;
						start = formatter.parse(startDateStr + startTimeStr);
						Date end = formatter.parse(endDateStr + endTimeStr);
						// start, end, description, location, visibility "yyyy-MM-dd'T'HH:mm:ssZ"
						formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
						String startStr = formatter.format(start);
						String endStr = formatter.format(end);
						String event = String.format(ExGoogleEvent.event, summary, startStr, endStr, description, location);

						events.add(event);
					}
					if (allday && row.getCell(1) != null && row.getCell(3) != null) {
						String summary = row.getCell(0).getStringCellValue();

						Date startDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(1))){
							startDate = row.getCell(1).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 1, "Sai định dạng, cột \"Start Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, null, error);
						}
						
						Date endDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(3))){
							endDate = row.getCell(3).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum(), 3, "Sai định dạng, cột \"End Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, null, error);
						}
						
						boolean isAllDay;
						if(row.getCell(5).getCellTypeEnum() == CellType.BOOLEAN) {
							isAllDay = row.getCell(5).getBooleanCellValue();
						}
						
						String description = row.getCell(6).getStringCellValue();
						String location = row.getCell(7).getStringCellValue();

						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String startStr = formatter.format(startDate);
						String endStr = formatter.format(endDate);
						String event = String.format(ExGoogleEvent.allDayEvent, summary, startStr, endStr, description,	location);

						events.add(event);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("Lỗi khi đọc file excel");
					return new BaseResult<List<String>>(false, new ArrayList<>(), "Có lỗi khi đọc file excel!");
				}
			}
		}else {		
			if(!first.getCell(0).getStringCellValue().equals("Subject")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ nhất!");
			}else if(!first.getCell(1).getStringCellValue().equals("Start Date")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 2!");
			}else if(!first.getCell(2).getStringCellValue().equals("Start Time")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 3!");
			}else if(!first.getCell(3).getStringCellValue().equals("End Date")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 4!");
			}else if(!first.getCell(4).getStringCellValue().equals("End Time")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 5!");
			}else if(!first.getCell(5).getStringCellValue().equals("All day event")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 6!");
			}else if(!first.getCell(6).getStringCellValue().equals("Description")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 7!");
			}else if(!first.getCell(7).getStringCellValue().equals("Location")) {
				return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên thứ 8!");
			}
		}
		return new BaseResult<List<String>>(true, events, "Import lịch thành công!");

		
	}
	
	  private static boolean checkIfRowIsEmpty(Row row) {
		    if (row == null) {
		        return true;
		    }
		    if (row.getLastCellNum() <= 0) {
		        return true;
		    }
		    for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
		        Cell cell = row.getCell(cellNum);
		        if (cell != null && cell.getCellTypeEnum() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
		            return false;
		        }
		    }
		    return true;
		}
}
