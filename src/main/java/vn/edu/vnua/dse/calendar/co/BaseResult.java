package vn.edu.vnua.dse.calendar.co;

public class BaseResult<T> {
	boolean status;

	T result;
	
	String massage;

	public BaseResult(boolean status, T result, String massage) {
		super();
		this.status = status;
		this.result = result;
		this.massage = massage;
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
