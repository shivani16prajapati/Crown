package com.unicef.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "challenge_sponsor")
public class Sponsor {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;
	
	@Column(name = "sponsor_id")
	private long sponsorId;
	
	@Column(name = "sponsor_name", length = 150)
	private String sponsorName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "challenge_id",nullable= false)
	private LaunchAChallenge challenge;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public LaunchAChallenge getChallenge() {
		return challenge;
	}

	public void setChallenge(LaunchAChallenge challenge) {
		this.challenge = challenge;
	}
	
}
