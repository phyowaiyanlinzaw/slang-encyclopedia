package dictionary.dto;

import java.time.LocalDate;

public class UserResponseDTO {
	private String username;
	private String email;
	private String password;
	private String confirm_password;
	
	private String term;
	private LocalDate createdDate;
	private LocalDate UpdatedDate; 

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirm_password() {
		return confirm_password;
	}
	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDate getUpdatedDate() {
		return UpdatedDate;
	}
	public void setUpdatedDate(LocalDate updatedDate) {
		UpdatedDate = updatedDate;
	}
	
	
}
