package com.unicef.domain;

import java.util.Date;

public class IdeaCommentView {
	private String avtarUrl;
	private String userName;
	private Date commentDate;
	private String comment;
	private long commenId;
	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCommenId() {
		return commenId;
	}

	public void setCommenId(long commenId) {
		this.commenId = commenId;
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
