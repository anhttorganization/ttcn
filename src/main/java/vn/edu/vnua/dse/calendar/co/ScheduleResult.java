package vn.edu.vnua.dse.calendar.co;

public class ScheduleResult<T> extends BaseResult<T> {
	String scheduleHash;

	public ScheduleResult(boolean status, T result, String massage, String scheduleHash) {
		super(status, result, massage);
		this.scheduleHash = scheduleHash;
	}

	public String getScheduleHash() {
		return scheduleHash;
	}

	public void setScheduleHash(String scheduleHash) {
		this.scheduleHash = scheduleHash;
	}

	
	
}
