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
@Table(name = "idea_endorsment")
public class IdeaEndorsement {

	@Id
	@Column(name = "endorsment_id")
	@GeneratedValue
	private long endorsmentId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idea_id", nullable = false)
	private Idea idea;
	
	@Column(name = "user_id")
	private long userId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "group_id")
	private long groupId;

	@Column(name = "company_id")
	private long companyId;

	public long getEndorsmentId() {
		return endorsmentId;
	}

	public void setEndorsmentId(long endorsmentId) {
		this.endorsmentId = endorsmentId;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
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
