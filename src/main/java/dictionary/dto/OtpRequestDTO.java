package dictionary.dto;

public class OtpRequestDTO {
	
	private String otpNumber;
	private String createdAt;
	private String requestedBy;
	private String expTime;
	
	public String getOtpNumber() {
		return otpNumber;
	}
	public void setOtpNumber(String otpNumber) {
		this.otpNumber = otpNumber;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getExpTime() {
		return expTime;
	}
	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}
	
	
	
	
	
}
