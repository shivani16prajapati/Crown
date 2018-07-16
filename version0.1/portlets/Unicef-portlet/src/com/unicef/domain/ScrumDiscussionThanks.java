package com.unicef.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="scrum_discussion_thanks")
public class ScrumDiscussionThanks {

	@Id
	@Column(name = "scrum_thanks_id")
	@GeneratedValue
	private long thanksId;
	
	@Column(name="company_id")
	private long companyId;
	
	@Column(name="idea_id")
	private long ideaId;
	
	@Column(name="idea_scrum_id")
	private long ideaScrumId;
	
	@Column(name="thanks_creator")
	private long thanksCreator;
	
	@Column(name="sprint_id")
	private long sprintId;
	
	@Column(name="scrum_discussion_id")
	private long scrumDiscussionId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	public long getThanksId() {
		return thanksId;
	}

	public void setThanksId(long thanksId) {
		this.thanksId = thanksId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}

	public long getIdeaScrumId() {
		return ideaScrumId;
	}

	public void setIdeaScrumId(long ideaScrumId) {
		this.ideaScrumId = ideaScrumId;
	}

	public long getThanksCreator() {
		return thanksCreator;
	}

	public void setThanksCreator(long thanksCreator) {
		this.thanksCreator = thanksCreator;
	}

	public long getSprintId() {
		return sprintId;
	}

	public void setSprintId(long sprintId) {
		this.sprintId = sprintId;
	}

	public long getScrumDiscussionId() {
		return scrumDiscussionId;
	}

	public void setScrumDiscussionId(long scrumDiscussionId) {
		this.scrumDiscussionId = scrumDiscussionId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
