package dictionary.model;

import javax.validation.constraints.NotEmpty;

public class OtpBean {
	
	@NotEmpty
	private String otpNumber;
	@NotEmpty
	private String createdAt;
	@NotEmpty
	private String expTime;
	
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getExpTime() {
		return expTime;
	}
	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}
	public String getOtpNumber() {
		return otpNumber;
	}
	public void setOtpNumber(String otpNumber) {
		this.otpNumber = otpNumber;
	}

}
