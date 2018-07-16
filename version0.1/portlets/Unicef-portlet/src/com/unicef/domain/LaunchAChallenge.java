package com.unicef.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "launch_a_challenge")
public class LaunchAChallenge {

	@Id
	@Column(name = "challenge_id")
	@GeneratedValue
	private Long challengeId;
	
	@Column(name = "challenge_title", nullable=false)
	private String challengeTitle;
	
	@Column(name = "challenge_tagline", nullable=false)
	private String challengeTagline;
	
	@Column(name = "line_of_bussiness_id",nullable = true)
	private long lineOfbussinessId;
	
	
	@Column(name="description")
	@Type(type = "text")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "challenge_start_date")
	private Date challengeStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "challenge_end_date")
	private Date challengeEndDate;
	
	@Column(name="challengor_id",nullable=false)
	private long challengorId;
	
	@Column(name="vertical_id")
	private long verticalId;
	
	@Column(name="edge_id")
	private long edgeId;
	
	@Column(name="goal_id")
	private long goalId;
	
	@Column(name="company_id")
	private long companyId;

	@Column(name="group_id")
	private long groupId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "challenge_created_date")
	private Date challengeCreatedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "challenge_modified_date")
	private Date challengeModifiedDate;
	
	@Column(name = "prize")
	private boolean prize;
	
	@Column(name = "prize_description")
	private String prizeDescription;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<Sponsor> sponsors;
	

	public Set<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(Set<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public Date getChallengeCreatedDate() {
		return challengeCreatedDate;
	}

	public void setChallengeCreatedDate(Date challengeCreatedDate) {
		this.challengeCreatedDate = challengeCreatedDate;
	}

	public boolean isPrize() {
		return prize;
	}
	
	

	public String getChallengeTagline() {
		return challengeTagline;
	}

	public void setChallengeTagline(String challengeTagline) {
		this.challengeTagline = challengeTagline;
	}

	public long getLineOfbussinessId() {
		return lineOfbussinessId;
	}

	public void setLineOfbussinessId(long lineOfbussinessId) {
		this.lineOfbussinessId = lineOfbussinessId;
	}

	public void setPrize(boolean prize) {
		this.prize = prize;
	}

	public String getPrizeDescription() {
		return prizeDescription;
	}

	public void setPrizeDescription(String prizeDescription) {
		this.prizeDescription = prizeDescription;
	}

	public Date getChallengeModifiedDate() {
		return challengeModifiedDate;
	}

	public void setChallengeModifiedDate(Date challengeModifiedDate) {
		this.challengeModifiedDate = challengeModifiedDate;
	}

	public Long getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(Long challengeId) {
		this.challengeId = challengeId;
	}

	public String getChallengeTitle() {
		return challengeTitle;
	}

	public void setChallengeTitle(String challengeTitle) {
		this.challengeTitle = challengeTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getChallengeStartDate() {
		return challengeStartDate;
	}

	public void setChallengeStartDate(Date challengeStartDate) {
		this.challengeStartDate = challengeStartDate;
	}

	public Date getChallengeEndDate() {
		return challengeEndDate;
	}

	public void setChallengeEndDate(Date challengeEndDate) {
		this.challengeEndDate = challengeEndDate;
	}

	public long getChallengorId() {
		return challengorId;
	}

	public void setChallengorId(long challengorId) {
		this.challengorId = challengorId;
	}

	public long getVerticalId() {
		return verticalId;
	}

	public void setVerticalId(long verticalId) {
		this.verticalId = verticalId;
	}

	public long getEdgeId() {
		return edgeId;
	}

	public void setEdgeId(long edgeId) {
		this.edgeId = edgeId;
	}

	public long getGoalId() {
		return goalId;
	}

	public void setGoalId(long goalId) {
		this.goalId = goalId;
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
