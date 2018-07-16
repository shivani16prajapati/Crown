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
@Table(name = "social_workflow_like")
public class WorkflowLike {

	@Id
	@Column(name = "like_id", unique = true, nullable = false)
	@GeneratedValue
	private long likeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "liked_date")
	private Date likeDate;
	
	@Column(name="user_id")
	private long userId;

	@Column(name="group_id")
	private long groupId;
	
	@Column(name="company_id")
	private long companyId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_id", nullable=false)
	private SocialWorkflow socialWorkflow;
	
	public long getLikeId() {
		return likeId;
	}

	public void setLikeId(long likeId) {
		this.likeId = likeId;
	}

	public Date getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public SocialWorkflow getSocialWorkflow() {
		return socialWorkflow;
	}

	public void setSocialWorkflow(SocialWorkflow socialWorkflow) {
		this.socialWorkflow = socialWorkflow;
	}
	
	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}
