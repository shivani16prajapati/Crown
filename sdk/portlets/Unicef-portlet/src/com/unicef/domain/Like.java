package com.unicef.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="idea_like")
public class Like {
	
	@Id
	@Column(name = "idea_like_id")
	@GeneratedValue
	private long likeId;
	
	public long getLikeId() {
		return likeId;
	}

	public void setLikeId(long likeId) {
		this.likeId = likeId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idea_id", nullable=false)
	private Idea idea;
	
	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "liked_date")
	private Date likeDate;
	
	public Date getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}
	
	@Column(name="task_id")
	private long taskId;
	
	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	@Column(name="user_id")
	private long userId;

	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
