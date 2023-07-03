package dictionary.model;

import javax.validation.constraints.NotEmpty;

public class DefinitionAndTermBean {
	@NotEmpty
	private String term;
	@NotEmpty
	private String definition_text;
	@NotEmpty
	private String example;
	@NotEmpty
	private String userId;
	@NotEmpty
	private String termId;
	@NotEmpty
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
	public String getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	

}
