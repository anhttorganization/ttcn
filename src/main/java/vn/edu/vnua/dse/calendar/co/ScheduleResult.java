package vn.edu.vnua.dse.calendar.co;

public class ScheduleResult<T> extends BaseResult<T> {
	String scheduleHash;

	public ScheduleResult(boolean status, T result, String message, String scheduleHash) {
		super(status, result, message);
		this.scheduleHash = scheduleHash;
	}

	public String getScheduleHash() {
		return scheduleHash;
	}

	public void setScheduleHash(String scheduleHash) {
		this.scheduleHash = scheduleHash;
	}

	
	
}
