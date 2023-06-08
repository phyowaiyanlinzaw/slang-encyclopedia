package dictionary.dto;

import java.sql.Timestamp;

public class OtpRequestDTO {
	
	private String otpNumber;
	private String requestedBy;
	private Timestamp expTime;
	
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
	
	
	
	
	
}
