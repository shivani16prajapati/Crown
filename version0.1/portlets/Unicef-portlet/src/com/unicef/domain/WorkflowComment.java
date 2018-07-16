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
@Table(name = "social_workflow_comment")
public class WorkflowComment {

	@Id
	@Column(name = "workflow_comment_id", unique = true, nullable = false)
	@GeneratedValue
	private long commentId;
	
	@Column(name = "description")
	@Type(type = "text")
	private String description;
	
	@Column(name="user_id")
	private long userId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name="group_id")
	private long groupId;
	
	@Column(name="company_id")
	private long companyId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id", nullable=false)
	private SocialWorkflow socialWorkflow;
	
	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public SocialWorkflow getSocialWorkflow() {
		return socialWorkflow;
	}

	public void setSocialWorkflow(SocialWorkflow socialWorkflow) {
		this.socialWorkflow = socialWorkflow;
	}


}
