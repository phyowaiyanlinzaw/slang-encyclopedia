package dictionary.dto;

public class DefandTermResponseDTO {
	private String term;
	private String definition_text;
	private String userId;
	private String termId;
	private String createdBy;
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	


}
