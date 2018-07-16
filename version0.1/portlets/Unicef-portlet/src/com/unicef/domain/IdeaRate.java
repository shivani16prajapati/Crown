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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "idea_rating")
public class IdeaRate {

	@Id
	@Column(name = "idea_rate_id")
	@GeneratedValue
	private long ideaRateId;
	
	@Column(name = "idea_rate_value")
	private int rateValue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "idea_id", nullable = false)
	private Idea idea;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "idea_rate_criteria_id", nullable = false)
	private IdeaRateCriteria ideaRateCriteria;
	
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "rating_date")
	private Date ratingDate;
	
	@Column(name = "group_id")
	private long groupId;
	
	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getIdeaRateId() {
		return ideaRateId;
	}

	public void setIdeaRateId(long ideaRateId) {
		this.ideaRateId = ideaRateId;
	}

	public int getRateValue() {
		return rateValue;
	}

	public void setRateValue(int rateValue) {
		this.rateValue = rateValue;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public IdeaRateCriteria getIdeaRateCriteria() {
		return ideaRateCriteria;
	}

	public void setIdeaRateCriteria(IdeaRateCriteria ideaRateCriteria) {
		this.ideaRateCriteria = ideaRateCriteria;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}	
}
