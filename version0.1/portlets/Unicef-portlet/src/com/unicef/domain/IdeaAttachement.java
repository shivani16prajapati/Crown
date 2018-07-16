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
@Table(name="idea_attachements")
public class IdeaAttachement {
	@Id
	@Column(name = "idea_attachement_id")
	@GeneratedValue
	private long attachementId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idea_id", nullable=false)
	private Idea idea;
	

	@Column(name="file_entry_id")
	private long fileEntryId;
	
	@Column(name="company_id")
	private long companyId;
	
	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	@Column(name="group_id")
	private long groupId;
	
	@Column(name="idea_version")
	private double version;

	public long getAttachementId() {
		return attachementId;
	}

	public void setAttachementId(long attachementId) {
		this.attachementId = attachementId;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public long getFileEntryId() {
		return fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		this.fileEntryId = fileEntryId;
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
