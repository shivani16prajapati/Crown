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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "solution_answer_comment")
public class SolutionAnswerComment {
	@Id
	@Column(name = "solution_answer_comment_id")
	@GeneratedValue
	private long solutionAnswerCommentId;
	
	public long getSolutionAnswerCommentId() {
		return solutionAnswerCommentId;
	}

	public void setSolutionAnswerCommentId(long solutionAnswerCommentId) {
		this.solutionAnswerCommentId = solutionAnswerCommentId;
	}
	
	@Column(name="user_id")
	private long userId;
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "comment_date")
	private Date commentDate;
	
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_id", nullable=false)
	private SolutionAnswer solutionAnswer;

	public SolutionAnswer getSolutionAnswer() {
		return solutionAnswer;
	}

	public void setSolutionAnswer(SolutionAnswer solutionAnswer) {
		this.solutionAnswer = solutionAnswer;
	}

	@Column(name="solution_id")
	private long solutionId;
	
	public long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(long solutionId) {
		this.solutionId = solutionId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;
	
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "description")
	@Type(type = "text")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
