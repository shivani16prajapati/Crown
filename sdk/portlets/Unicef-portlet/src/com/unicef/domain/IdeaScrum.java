package com.unicef.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.unicef.util.IdeaUtil;

@Entity
@Table(name = "idea_scrum_mapping")
public class IdeaScrum {

	@Id
	@Column(name = "scrum_id")
	@GeneratedValue
	private long ideaScrumId;

	@Column(name = "idea_id")
	private long ideaId;
	
	@Column(name = "solution_id")
	private long solutionId;

	@Column(name = "group_id")
	private long groupId;

	@Column(name = "idea_scrum_creater")
	private long ideaScrumCreater;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	public long getIdeaScrumId() {
		return ideaScrumId;
	}

	public void setIdeaScrumId(long ideaScrumId) {
		this.ideaScrumId = ideaScrumId;
	}

	public long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getIdeaScrumCreater() {
		return ideaScrumCreater;
	}

	public void setIdeaScrumCreater(long ideaScrumCreater) {
		this.ideaScrumCreater = ideaScrumCreater;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getInventorAvatarURL() {
		return IdeaUtil.getInventorAvatarURL(ideaId);
	}

	public String getUserName() {
		return IdeaUtil.getUserName(ideaScrumCreater);
	}

	public long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(long solutionId) {
		this.solutionId = solutionId;
	}
}
