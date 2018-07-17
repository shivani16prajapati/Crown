package com.unicef.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "idea_rate_criteria")
public class IdeaRateCriteria {

	@Id
	@Column(name = "idea_rate_criteria_id")
	@GeneratedValue
	private long ideaRateCriteriaId;

	@Column(name = "idea_rate_description")
	private String description;

	@Column(name = "idea_rate_criteria_name")
	private String ideaRateCriteriaName;

	public long getIdeaRateCriteriaId() {
		return ideaRateCriteriaId;
	}

	public void setIdeaRateCriteriaId(long ideaRateCriteriaId) {
		this.ideaRateCriteriaId = ideaRateCriteriaId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIdeaRateCriteriaName() {
		return ideaRateCriteriaName;
	}

	public void setIdeaRateCriteriaName(String ideaRateCriteriaName) {
		this.ideaRateCriteriaName = ideaRateCriteriaName;
	}

}
