package dictionary.dto;

public class DefandTermResponseDTO {
	private String term;
	private String definition_text;
	private String userId;
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
	


}
