package com.unicef.domain;

/**
 * @author Divyang Patel
 */
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
@Table(name = "sprint")
public class Sprint  {
	
	@Id
	@Column(name = "sprint_id")
	@GeneratedValue
	private Long sprintId;

	@Column(name = "focus_title", nullable=false)
	@Type(type = "text")
	private String focusTitle;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "text", nullable=false)
	@Type(type = "text")
	private String text;
	
	@Column(name = "videoURL", length = 500)
	@Type(type = "text")
	private String videoURL;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name="days_length")
	private int daysLength;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name="status")
	private int status;
	
	@Column(name="order_index")
	private int orderIndex;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name="inventor_id",nullable=false)
	private long inventorId;
	
	@Column(name="idea_scrum_id")
	private long ideaScrumId;
	
	@Column(name="idea_id")
	private long ideaId;
	
	@Column(name="solution_id")
	private long solutionId;

	@Column(name="group_id")
	private long groupId;
	
	@Column(name="company_id")
	private long companyId;

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	
	public Long getSprintId() {
		return sprintId;
	}

	public void setSprintId(Long sprintId) {
		this.sprintId = sprintId;
	}

	public String getFocusTitle() {
		return focusTitle;
	}

	public void setFocusTitle(String focusTitle) {
		this.focusTitle = focusTitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}


	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getDaysLength() {
		return daysLength;
	}

	public void setDaysLength(int daysLength) {
		this.daysLength = daysLength;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public long getInventorId() {
		return inventorId;
	}

	public void setInventorId(long inventorId) {
		this.inventorId = inventorId;
	}

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

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	public long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(long solutionId) {
		this.solutionId = solutionId;
	}
	
}
