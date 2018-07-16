package com.unicef.domain;

import java.util.Date;

public class AnswerCommentView {
	private String avtarUrl;
	
	public String getAvtarUrl() {
		return avtarUrl;
	}
	public void setAvtarUrl(String avtarUrl) {
		this.avtarUrl = avtarUrl;
	}
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private Date commentDate;
	

	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
	private String comment;

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
