package vn.edu.vnua.dse.calendar.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import com.google.gson.Gson;

import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;

public class test {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ParseException {
		ImportServiceImpl impl = new ImportServiceImpl();
		List<GoogleEvent> events = impl.getEventsFromExcelFile("C:\\Users\\Administrator\\Desktop\\event.xlsx");
		Gson gson = new Gson();
		for (GoogleEvent googleEvent : events) {
			System.out.println(gson.toJson(googleEvent, GoogleEvent.class));
		}
		
//		Gson gson = new Gson();
//		System.out.println(gson.toJson(new Date(110, 10, 12), Date.class));
	}
}
