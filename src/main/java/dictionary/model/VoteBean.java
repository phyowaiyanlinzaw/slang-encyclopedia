package dictionary.model;

import javax.validation.constraints.NotEmpty;

public class VoteBean {
	@NotEmpty
	private String id;
	@NotEmpty
	private String vote_type;
	private String definition_id;
	  
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getDefinition_id() {
		return definition_id;
	}
	public void setDefinition_id(String definition_id) {
		this.definition_id = definition_id;
	}
	public String getVote_type() {
		return vote_type;
	}
	public void setVote_type(String vote_type) {
		this.vote_type = vote_type;
	}

}
