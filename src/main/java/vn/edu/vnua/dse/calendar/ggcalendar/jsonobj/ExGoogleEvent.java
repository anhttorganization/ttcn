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
			+ "\"end\":{\"date\":\"%s\"},"
			+ "\"sequence\":0,"
			+ "\"description\":\"%s\","
			+ "\"location\":\"%s\","
			+ " \"visibility\": \"%s\"" + 
			"}";
	
	public static final String weekStudyEvent =  //start, end, description, visibility
			"{\"summary\":\"%s\","
			+ "\"start\":{"
			+ "\"date\":\"%s\"},"
			+ "\"end\":{\"date\":\"%s\"},"
			+ "\"sequence\":0,"
			+ "\"description\":\"%s\","
			+ " \"visibility\": \"%s\"" + 
			"}";
}
