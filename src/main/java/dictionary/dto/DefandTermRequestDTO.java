package dictionary.dto;

public class DefandTermRequestDTO {
	private String term;
	private String definition_text;
	private String createdBy;
	private String createdAt;
	private String updatedBy;
	private String updatedAt;
	private String status;
	private String userId;
	private String definitionId;
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getDefinition_text() {
		return definition_text;
	}
	public void setDefinition_text(String definition_text) {
		this.definition_text = definition_text;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	

}
