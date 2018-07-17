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
@Table(name="solution_answer_vote")
public class SolutionAnswerVote {
	@Id
	@Column(name = "solution_answer_vote_id")
	@GeneratedValue
	private long solutionAnswerVoteId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_id", nullable=false)
	private SolutionAnswer solutionAnswer;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "voted_date")
	private Date voteDate;
	
	
	@Column(name="user_id")
	private long userId;
	
	public long getSolutionId() {
		return solutionId;
	}


	public void setSolutionId(long solutionId) {
		this.solutionId = solutionId;
	}

	@Column(name="solution_id")
	private long solutionId;


	public long getSolutionAnswerVoteId() {
		return solutionAnswerVoteId;
	}


	public void setSolutionAnswerVoteId(long solutionAnswerVoteId) {
		this.solutionAnswerVoteId = solutionAnswerVoteId;
	}


	public SolutionAnswer getSolutionAnswer() {
		return solutionAnswer;
	}


	public void setSolutionAnswer(SolutionAnswer solutionAnswer) {
		this.solutionAnswer = solutionAnswer;
	}


	public Date getVoteDate() {
		return voteDate;
	}


	public void setVoteDate(Date voteDate) {
		this.voteDate = voteDate;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
