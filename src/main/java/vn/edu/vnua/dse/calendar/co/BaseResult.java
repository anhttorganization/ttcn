package vn.edu.vnua.dse.calendar.co;

import java.util.ArrayList;

public class BaseResult<T> {
	public ArrayList<String> getWeekEvents() {
		return weekEvents;
	}

	public void setWeekEvents(ArrayList<String> weekEvents) {
		this.weekEvents = weekEvents;
	}

	boolean status;

	T result;
	
	String massage;

	ArrayList<String> weekEvents;
	public BaseResult(boolean status, T result, String massage) {
		super();
		this.status = status;
		this.result = result;
		this.massage = massage;
	}
	
	public BaseResult(boolean status, T result, String massage, ArrayList<String> weekEvents) {
		super();
		this.status = status;
		this.result = result;
		this.massage = massage;
		this.weekEvents = weekEvents;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}
	
	

	
}
