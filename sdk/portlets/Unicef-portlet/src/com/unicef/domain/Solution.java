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

@Entity
@Table(name = "solution")
public class Solution {
	@Id
	@Column(name = "solution_id")
	@GeneratedValue
	private Long solutionId;
	
	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}
	
	@Column(name = "solution_title", length = 150, nullable=false)
	private String solutionTitle;
	
	@Column(name = "solution_tag_line")
	private String solutiontagline;
	
	
	@Column(name = "line_of_bussiness")
	private long lineofbussiness;
	
	public String getSolutionTitle() {
		return solutionTitle;
	}

	public void setSolutionTitle(String solutionTitle) {
		this.solutionTitle = solutionTitle;
	}
	@Column(name="solution_programme")
	private long solutionProgramme;
	
	public long getSolutionProgramme() {
		return solutionProgramme;
	}

	public void setSolutionProgramme(long solutionProgramme) {
		this.solutionProgramme = solutionProgramme;
	}
	
	@Column(name="solution_category_id")
	private long solutionCategory;
	
	public long getSolutionCategory() {
		return solutionCategory;
	}

	public void setSolutionCategory(long solutionCategory) {
		this.solutionCategory = solutionCategory;
	}
	
	@Column(name="solution_stage_id")
	private long solutionStage;
	
	public long getSolutionStage() {
		return solutionStage;
	}

	public void setSolutionStage(long solutionStage) {
		this.solutionStage = solutionStage;
	}
	
	@Column(name="problem_space_id")
	private long spaceId;
	
	public long getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(long spaceId) {
		this.spaceId = spaceId;
	}

	
	@Column(name="description")
	@Type(type = "text")
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submission_date")
	private Date submissionDate;
	
	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;
	

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name="coinventor_id",nullable=false)
	private long coInventorId;
	
	public long getCoInventorId() {
		return coInventorId;
	}

	public void setCoInventorId(long coInventorId) {
		this.coInventorId = coInventorId;
	}
	
	@Column(name="status")
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name="group_id")
	private long groupId;
	
	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	@Column(name="company_id")
	private long companyId;
	
	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	private int wantCount;

	public int getWantCount() {
		return wantCount;
	}

	public void setWantCount(int wantCount) {
		this.wantCount = wantCount;
	}
	
	@Column(name ="solution_hot" ,columnDefinition = "boolean default false", nullable = false)
	 private boolean isSolutionHot = false;
	
	public boolean isSolutionHot() {
		return isSolutionHot;
	}

	public void setSolutionHot(boolean isSolutionHot) {
		this.isSolutionHot = isSolutionHot;
	}
	 
	 @Column(name = "hot_weight", columnDefinition = "double default 0.0", nullable = false)
	 private double hotWeight;
	 
	 public double getHotWeight() {
			return hotWeight;
		}
	public void setHotWeight(double hotWeight) {
		this.hotWeight = hotWeight;
	}

	public String getSolutiontagline() {
		return solutiontagline;
	}

	public void setSolutiontagline(String solutiontagline) {
		this.solutiontagline = solutiontagline;
	}

	public long getLineofbussiness() {
		return lineofbussiness;
	}

	public void setLineofbussiness(long lineofbussiness) {
		this.lineofbussiness = lineofbussiness;
	}
	
	
	
}
