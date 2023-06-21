package dictionary.dto;

import java.sql.Timestamp;

public class VoteRequestDTO {
	
	private int user_id;
	private String vote_type;
	private int definitionId;
	private String createdBy;
	private String updatedBy;
	private int count;
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getVote_type() {
		return vote_type;
	}
	public void setVote_type(String vote_type) {
		this.vote_type = vote_type;
	}
	public int getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(int definitionId) {
		this.definitionId = definitionId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
