package dictionary.dto;

import java.sql.Timestamp;

public class OtpRequestDTO {
	
	private String otpNumber;
	private String requestedBy;
	private Timestamp expTime;
	private int otpCount;
	private int userId;
	private Timestamp restrictTime;
	
	public String getOtpNumber() {
		return otpNumber;
	}
	public void setOtpNumber(String otpNumber) {
		this.otpNumber = otpNumber;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public Timestamp getExpTime() {
		return expTime;
	}
	public void setExpTime(Timestamp expTime) {
		this.expTime = expTime;
	}
	public int getOtpCount() {
		return otpCount;
	}
	public void setOtpCount(int otpCount) {
		this.otpCount = otpCount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Timestamp getRestrictTime() {
		return restrictTime;
	}
	public void setRestrictTime(Timestamp restrictTime) {
		this.restrictTime = restrictTime;
	}
	
	
	
	
	
}
