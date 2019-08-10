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

	public static BaseResult<List<String>> insert(String calendarId, File file) throws IOException {
		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		try {
			BaseResult<List<String>> result = getEventsFromExcelFile(file);
			List<String> eventJsons = result.getResult();
            if(result.isStatus()) {
            	if (eventJsons.size() > 0) {
            		for (String eventJson : eventJsons) {
            			aPIWrapper.insertEvent(calendarId, eventJson);
            		}
            		return result;
            	}else {
        			return new  BaseResult<List<String>>(true, new ArrayList<String>(), "File excel không có sự kiện!") ;
            	}
            }else {
    			return new  BaseResult<List<String>>(false, new ArrayList<String>(), result.getMessage());
            }
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new  BaseResult<List<String>>(false, new ArrayList<String>(), "Có lỗi xảy ra. Import không thành công!") ;
		}
	}

	@SuppressWarnings({ "resource", "unused" })
	private static BaseResult<List<String>> getEventsFromExcelFile(File file)
			throws FileNotFoundException, IOException, ParseException {
		// Đọc một file XSLS.
		List<String> errors = new ArrayList<>();
		List<String> events = new ArrayList<>();
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Có lỗi khi đọc file excel!");
		}

		// Đối tượng workbook cho file XSLS.
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Có lỗi khi đọc file excel!");
		}

		// Lấy ra sheet đầu tiên từ workbook
		XSSFSheet sheet = workbook.getSheetAt(0);
		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();
		Row first = rowIterator.next();
		if(checkIfRowIsEmpty(first)) {//bỏ qua dòng trống
			
		}
		//Kiem tra header
		if(first.getCell(0) == null || !first.getCell(0).getStringCellValue().equals("Subject")) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên cột thứ nhất!");
		}else if(first.getCell(1) == null || !first.getCell(1).getStringCellValue().equals("Start Date")) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên cột thứ 2!");
		}else if(first.getCell(2) == null || !first.getCell(2).getStringCellValue().equals("Start Time")) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên cột thứ 3!");
		}else if(first.getCell(3) == null || !first.getCell(3).getStringCellValue().equals("End Date")) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên cột thứ 4!");
		}else if(first.getCell(4) == null || !first.getCell(4).getStringCellValue().equals("End Time")) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên cột thứ 5!");
		}else if(first.getCell(5) == null || !first.getCell(5).getStringCellValue().equals("All day event")) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên cột thứ 6!");
		}else if(first.getCell(6) == null || !first.getCell(6).getStringCellValue().equals("Description")) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên cột thứ 7!");
		}else if(first.getCell(7) == null || !first.getCell(7).getStringCellValue().equals("Location")) {
			return new BaseResult<List<String>>(false, new ArrayList<>(), "Sai tên cột thứ 8!");
		}else{
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if(checkIfRowIsEmpty(row)) {//bỏ qua dòng trống
					continue;
				}
				
				try {
					boolean isAllDay;
					if(row.getCell(5) != null && row.getCell(5).getCellTypeEnum() == CellType.BOOLEAN) {
						isAllDay = row.getCell(5).getBooleanCellValue();
					}else {
						String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 6, "Sai định dạng, cột \"All day event\" phải để kiểu BOOLEAN");
						return new BaseResult<List<String>>(false, new ArrayList<>(), error);
					}
					if (!isAllDay && row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(4) != null) 
					{
						if(row.getCell(1) == null) {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 2, "Cột \"Start Date\" không được để trống!");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						if(row.getCell(2) == null) {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 3, "Cột \"Start Time\" không được để trống!");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						if(row.getCell(3) == null) {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 4, "Cột \"End Date\" không được để trống!");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						if(row.getCell(4) == null) {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 5, "Cột \"End Time\" không được để trống!");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						String summary = "";
						if(row.getCell(0) != null) {
							summary = row.getCell(0).getStringCellValue();
						}

						Date startDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(1))){
							startDate = row.getCell(1).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 1, "Sai định dạng, cột \"Start Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						Date startTime = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(2))){
							startTime = row.getCell(2).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 2, "Sai định dạng, cột \"Start Time\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						Date endDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(3))){
							endDate = row.getCell(3).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 3, "Sai định dạng, cột \"End Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						Date endTime = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(4))){
							endTime = row.getCell(4).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 4, "Sai định dạng, cột \"End Time\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						String description = "";
						if(row.getCell(0) != null) {
							description = row.getCell(6).getStringCellValue();
						}
						String location = "";
						if(row.getCell(0) != null) {
							location = row.getCell(7).getStringCellValue();
						}
						
						String startDateStr = new SimpleDateFormat("dd/MM/yyyy").format(startDate);
						String startTimeStr = new SimpleDateFormat("HH:mm:ss").format(startTime);
						String endDateStr = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
						String endTimeStr = new SimpleDateFormat("HH:mm:ss").format(endTime);
						
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
						formatter.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));
						Date start;
						start = formatter.parse(startDateStr + startTimeStr);
						Date end = formatter.parse(endDateStr + endTimeStr);
						
						if(end.before(start)) {
							String error = "Dòng " + (row.getRowNum() + 1)+ ": Thời gian kết thúc phải sau thời gian bắt đầu!";
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
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
						if(row.getCell(1) == null) {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 2, "Cột \"Start Date\" không được để trống!");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						if(row.getCell(2) == null) {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 3, "Cột \"Start Time\" không được để trống!");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						if(row.getCell(3) == null) {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 4, "Cột \"End Date\" không được để trống!");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						if(row.getCell(4) == null) {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 5, "Cột \"End Time\" không được để trống!");
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						String summary = "";
						if(row.getCell(0) != null) {
							summary = row.getCell(0).getStringCellValue();
						}

						Date startDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(1))){
							startDate = row.getCell(1).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 1, "Sai định dạng, cột \"Start Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, null, error);
						}
						
						Date endDate = new Date();
						if(HSSFDateUtil.isCellDateFormatted(row.getCell(3))){
							endDate = row.getCell(3).getDateCellValue();
						}
						else {
							String error = String.format(AppConstant.IMPORT_ERROR, row.getRowNum() + 1, 3, "Sai định dạng, cột \"End Date\" phải để kiểu DATETIME");
							return new BaseResult<List<String>>(false, null, error);
						}
						
						if(endDate.before(startDate)) {
							String error = "Dòng " + (row.getRowNum() + 1) + ": Ngày"
									+ " kết thúc phải sau ngày bắt đầu!";
							return new BaseResult<List<String>>(false, new ArrayList<>(), error);
						}
						
						String description = "";
						if(row.getCell(6) != null) {
							description = row.getCell(6).getStringCellValue();
						}
						String location = "";
						if(row.getCell(7) != null) {
							location = row.getCell(7).getStringCellValue();
						}

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
					return new BaseResult<List<String>>(false, new ArrayList<>(), "Có lỗi khi đọc file excel!");
				}
			}
		}
		return new BaseResult<List<String>>(true, events, "Đọc sự kiện từ file excel thành công!");

		
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
