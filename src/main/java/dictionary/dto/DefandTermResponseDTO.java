package dictionary.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DefandTermResponseDTO {
	
	private int defId;
	private String term;
	private String definition_text;
	private String userId;
	private String termId;
	private String createdBy;
    private LocalDate createdDate;
    private LocalDate updatedAt;
    private int voteCount;
    private int likeCount;
    private int dislikeCount;
    private String example;
	
	public int getDefId() {
		return defId;
	}
	public void setDefId(int defId) {
		this.defId = defId;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getDislikeCount() {
		return dislikeCount;
	}
	public void setDislikeCount(int dislikeCount) {
		this.dislikeCount = dislikeCount;
	}
	public int getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
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
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDate getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	

	


}
