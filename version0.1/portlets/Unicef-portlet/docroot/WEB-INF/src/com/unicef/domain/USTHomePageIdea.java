package com.unicef.domain;

public class USTHomePageIdea {

	private long ideaId;
	private String ideaTitle;
	private String ideaImage;
	private String userImage;
	private String tagLine;
	private long ideaComment;
	private long ideaUpvote;
	
	
	public long getIdeaComment() {
		return ideaComment;
	}
	public void setIdeaComment(long ideaComment) {
		this.ideaComment = ideaComment;
	}
	public long getIdeaUpvote() {
		return ideaUpvote;
	}
	public void setIdeaUpvote(long ideaUpvote) {
		this.ideaUpvote = ideaUpvote;
	}
	public long getIdeaId() {
		return ideaId;
	}
	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}
	public String getIdeaTitle() {
		return ideaTitle;
	}
	public void setIdeaTitle(String ideaTitle) {
		this.ideaTitle = ideaTitle;
	}
	public String getIdeaImage() {
		return ideaImage;
	}
	public void setIdeaImage(String ideaImage) {
		this.ideaImage = ideaImage;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getTagLine() {
		return tagLine;
	}
	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}
	
}
