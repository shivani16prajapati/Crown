package com.unicef.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.unicef.util.IdeaUtil;

@Entity
@Table(name = "solution_scrum_discussion")
public class SolutionScrumDiscussion {

	@Id
	@Column(name = "scrum_discussion_id")
	@GeneratedValue
	private long scrumDiscussionId;
	
	@Type(type = "text")
	private String message;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name = "solution_id")
	private long solutionId;
	
	@Column(name = "sprint_id")
	private long sprintId;
	
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "solution_scrum_id")
	private long solutionScrumId;
	
	public long getScrumDiscussionId() {
		return scrumDiscussionId;
	}

	public void setScrumDiscussionId(long scrumDiscussionId) {
		this.scrumDiscussionId = scrumDiscussionId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(long solutionId) {
		this.solutionId = solutionId;
	}

	public long getSprintId() {
		return sprintId;
	}

	public void setSprintId(long sprintId) {
		this.sprintId = sprintId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getSolutionScrumId() {
		return solutionScrumId;
	}

	public void setSolutionScrumId(long solutionScrumId) {
		this.solutionScrumId = solutionScrumId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getParentScrumDiscussionId() {
		return parentScrumDiscussionId;
	}

	public void setParentScrumDiscussionId(long parentScrumDiscussionId) {
		this.parentScrumDiscussionId = parentScrumDiscussionId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isChild() {
		return isChild;
	}

	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}

	@Column(name = "company_id")
	private long companyId;
	
	@Column(name = "parent_scrum_discussion_id")
	private long parentScrumDiscussionId;
	
	@Column(name = "level")
	private int level;
	
	@Column(name="is_child")
	private boolean isChild;
	
	public String getAvatarURL() {
		return IdeaUtil.getAvatarURL(userId);
	}
	
}
