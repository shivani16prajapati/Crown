package com.unicef.domain;
import java.util.Date;
import java.util.Set;

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
@Table(name = "idea")
public class Idea  {
	
	@Id
	@Column(name = "idea_id")
	@GeneratedValue
	private Long ideaId;

	@Column(name = "idea_title", length = 150, nullable=false)
	private String ideaTitle;
	
	@Column(name="idea_programme")
	private long ideaProgramme;
	
	@Column(name="category_id")
	private long ideaCategory;
	
	@Column(name="stage_id")
	private long ideaStage;
	
	@Column(name="innovation_type_id")
	private long innovationType;
	
	@Column(name="description")
	@Type(type = "text")
	private String description;
	
	@Column(name="is_deleted")
	private boolean isDeleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submission_date")
	private Date submissionDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name="coinventor_id",nullable=false)
	private long coInventorId;
	
	@Column(name="status")
	private int status;
	
	@Column(name="group_id")
	private long groupId;
	
	@Column(name="company_id")
	private long companyId;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="idea",fetch = FetchType.LAZY)
	private Set<IdeaHistory> ideaHistory;
	
	@Column(name="idea_version")
	private double version;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="idea",fetch = FetchType.LAZY)
	private Set<IdeaAttachement> attachments;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="idea",fetch = FetchType.LAZY)
	private Set<Like> likes;
	
	@Column(name ="idea_hot" ,columnDefinition = "boolean default false", nullable = false)
	private boolean isIdeaHot = false;
	
	@Column(name = "hot_weight", columnDefinition = "double default 0.0", nullable = false)
	private double hotWeight;
	 
	@Column(name = "idea_tag_title", length = 30, nullable=false)
	private String ideaTagTitle;
	
	@Column(name = "edge_service_id")
	private long edgeServiceId;
	
	@Column(name = "idea_keywords")
	private String ideaKeywords;
	
	@Column(name = "line_of_bussiness_id",nullable = true)
	private long lineOfbussinessId;
	
	@Column(name = "buss_invType_id",nullable = true)
	private long bussInvType;
	
	@Column(name = "invetor_name",nullable = true)
	private String invName;
	
	@Column(name = "new_stage",nullable = true)
	private long newStage;
	
	public long getLineOfbussinessId() {
		return lineOfbussinessId;
	}
	public void setLineOfbussinessId(long lineOfbussinessId) {
		this.lineOfbussinessId = lineOfbussinessId;
	}
	public long getBussInvType() {
		return bussInvType;
	}
	public void setBussInvType(long bussInvType) {
		this.bussInvType = bussInvType;
	}
	public String getInvName() {
		return invName;
	}
	public void setInvName(String invName) {
		this.invName = invName;
	}
   
	
	
	public long getNewStage() {
		return newStage;
	}
	public void setNewStage(long newStage) {
		this.newStage = newStage;
	}
	public String getIdeaKeywords() {
		return ideaKeywords;
	}
	public void setIdeaKeywords(String ideaKeywords) {
		this.ideaKeywords = ideaKeywords;
	}

	public Long getIdeaId() {
		return ideaId;
	}
	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
	}

	public String getIdeaTitle() {
		return ideaTitle;
	}
	public void setIdeaTitle(String ideaTitle) {
		this.ideaTitle = ideaTitle;
	}
	
	public long getIdeaProgramme() {
		return ideaProgramme;
	}
	public void setIdeaProgramme(long ideaProgramme) {
		this.ideaProgramme = ideaProgramme;
	}
	
	public long getIdeaCategory() {
		return ideaCategory;
	}
	public void setIdeaCategory(long ideaCategory) {
		this.ideaCategory = ideaCategory;
	}
	
	public long getIdeaStage() {
		return ideaStage;
	}
	public void setIdeaStage(long ideaStage) {
		this.ideaStage = ideaStage;
	}
	
	public long getInnovationType() {
		return innovationType;
	}
	public void setInnovationType(long innovationType) {
		this.innovationType = innovationType;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public long getCoInventorId() {
		return coInventorId;
	}
	public void setCoInventorId(long coInventorId) {
		this.coInventorId = coInventorId;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	
	public Set<IdeaHistory> getIdeaHistory() {
		return ideaHistory;
	}
	public void setIdeaHistory(Set<IdeaHistory> ideaHistory) {
		this.ideaHistory = ideaHistory;
	}
	
	public double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version;
	}
	
	public Set<IdeaAttachement> getAttachments() {
		return attachments;
	}
	public void setAttachments(Set<IdeaAttachement> attachments) {
		this.attachments = attachments;
	}
	
	public Set<Like> getLikes() {
		return likes;
	}
	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}
	
	public boolean isIdeaHot() {
		return isIdeaHot;
	}
	public void setIdeaHot(boolean isIdeaHot) {
		this.isIdeaHot = isIdeaHot;
	}
	 
	public double getHotWeight() {
		return hotWeight;
	}
	public void setHotWeight(double hotWeight) {
		this.hotWeight = hotWeight;
	}
	
	public String getIdeaTagTitle() {
		return ideaTagTitle;
	}
	public void setIdeaTagTitle(String ideaTagTitle) {
		this.ideaTagTitle = ideaTagTitle;
	}

	public long getEdgeServiceId() {
		return edgeServiceId;
	}
	public void setEdgeServiceId(long edgeServiceId) {
		this.edgeServiceId = edgeServiceId;
	} 
}
