package vn.edu.vnua.dse.calendar.ggcalendar.jsonobj;

public class ExGoogleEvent extends GoogleEvent{
	public static final String event = 	//start, end, description, location, visibility
			"{\"summary\":\"%s\","
					+ "\"start\":{"
					+ "\"dateTime\":\"%s\","
					+ "\"timeZone\":\"Asia/Ho_Chi_Minh\"},"
					+ "\"end\":{"
					+ "\"dateTime\":\"%s\","
					+ "\"timeZone\":\"Asia/Ho_Chi_Minh\"},"
					+ "\"sequence\":0,"
//					+ "\"recurrence\":[%s],"
					+ "\"description\":\"%s\","
					+ "\"location\":\"%s\","
					+ " \"visibility\": \"%s\"" + 
					"}";
	
	public static final String allDayEvent = //start, end, description, location, visibility
			"{\"summary\":\"%s\","
			+ "\"start\":{"
			+ "\"date\":\"%s\"},"
//			+ "\"timeZone\":\"Asia/Ho_Chi_Minh\"},"
			+ "\"end\":{\"date\":\"%s\"},"
//			+ "\"timeZone\":\"Asia/Ho_Chi_Minh\"},"
			+ "\"sequence\":0,"
//			+ "\"recurrence\":[%s],"
			+ "\"description\":\"%s\","
			+ "\"location\":\"%s\","
			+ " \"visibility\": \"%s\"" + 
			"}";
}
