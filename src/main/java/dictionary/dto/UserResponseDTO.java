package dictionary.dto;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserResponseDTO {
	
	private String username;
	private String email;
	private String password;
	private String confirm_password;
	
	private String term;
	private LocalDate createdDate;
	private LocalDate UpdatedDate; 
	private int userId;
	
	private String isVerified;
	private String isLocked;
	
	private String hasLiked;
	private String hasDisliked;
	
	private ArrayList<Integer> likedDefIds;
	private ArrayList<Integer> dislikedDefIds;

	public ArrayList<Integer> getLikedDefIds() {
		return likedDefIds;
	}
	public void setLikedDefIds(ArrayList<Integer> likedDefIds) {
		this.likedDefIds = likedDefIds;
	}
	public ArrayList<Integer> getDislikedDefIds() {
		return dislikedDefIds;
	}
	public void setDislikedDefIds(ArrayList<Integer> dislikedDefIds) {
		this.dislikedDefIds = dislikedDefIds;
	}
	public String getHasLiked() {
		return hasLiked;
	}
	public void setHasLiked(String hasLiked) {
		this.hasLiked = hasLiked;
	}
	public String getHasDisliked() {
		return hasDisliked;
	}
	public void setHasDisliked(String hasDisliked) {
		this.hasDisliked = hasDisliked;
	}
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}
	public String getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}
	
	
}
