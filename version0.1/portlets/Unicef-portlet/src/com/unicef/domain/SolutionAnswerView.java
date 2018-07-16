package com.unicef.domain;

import java.util.Date;

public class SolutionAnswerView {
	private String avtarUrl;
	private long answerId;
	private String userName;
	private String comment;
	private Date commentDate;
	private long solutionId;
	private String upvoteDisplayUsers;
	private String upvoteMoreUsers;
	
	
	public long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(long solutionId) {
		this.solutionId = solutionId;
	}

	public String getUpvoteDisplayUsers() {
		return upvoteDisplayUsers;
	}

	public void setUpvoteDisplayUsers(String upvoteDisplayUsers) {
		this.upvoteDisplayUsers = upvoteDisplayUsers;
	}

	public String getUpvoteMoreUsers() {
		return upvoteMoreUsers;
	}

	public void setUpvoteMoreUsers(String upvoteMoreUsers) {
		this.upvoteMoreUsers = upvoteMoreUsers;
	}

	public long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(long answerId) {
		this.answerId = answerId;
	}

	public String getAvtarUrl() {
		return avtarUrl;
	}

	public void setAvtarUrl(String avtarUrl) {
		this.avtarUrl = avtarUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
