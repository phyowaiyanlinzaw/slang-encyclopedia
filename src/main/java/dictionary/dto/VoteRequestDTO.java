package dictionary.dto;

public class VoteRequestDTO {
	private String user_id;
	private String vote_type;
	private String definition_id;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getVote_type() {
		return vote_type;
	}
	public void setVote_type(String vote_type) {
		this.vote_type = vote_type;
	}
	public String getDefinition_id() {
		return definition_id;
	}
	public void setDefinition_id(String definition_id) {
		this.definition_id = definition_id;
	}
	

}
