package dictionary.dto;

import java.time.LocalDate;

public class UserRequestDTO {
	private String username;
	private String email;
	private String password;
	private String confirm_password;
	private int id;
	private LocalDate updateAt;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(LocalDate updateAt) {
		this.updateAt = updateAt;
	}
	
	
	

}
