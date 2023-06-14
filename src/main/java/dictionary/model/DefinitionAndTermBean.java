package dictionary.model;

import javax.validation.constraints.NotEmpty;

public class DefinitionAndTermBean {
	@NotEmpty
	private String term;
	@NotEmpty
	private String definition_text;
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
	

}
