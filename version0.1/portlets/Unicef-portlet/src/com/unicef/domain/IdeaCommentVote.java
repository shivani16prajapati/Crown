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
@Table(name = "idea_comment_vote")
public class IdeaCommentVote {
	
	@Id
	@Column(name = "idea_vote_id")
	@GeneratedValue
	private long ideaVoteId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id", nullable=false)
	private IdeaComment ideaComment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "voted_date")
	private Date voteDate;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="idea_id")
	private long ideaId;
	
	@Column(name="company_Id")
	private long companyId;
	
	@Column(name="group_id")
	private long groupId;
	
	public long getIdeaVoteId() {
		return ideaVoteId;
	}

	public void setIdeaVoteId(long ideaVoteId) {
		this.ideaVoteId = ideaVoteId;
	}

	public IdeaComment getIdeaComment() {
		return ideaComment;
	}

	public void setIdeaComment(IdeaComment ideaComment) {
		this.ideaComment = ideaComment;
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

	public long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
}
