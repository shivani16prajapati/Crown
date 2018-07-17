package com.unicef.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "social_workflow")
public class SocialWorkflow {
	
	@Id
	@Column(name = "task_id", unique = true, nullable = false)
	@GeneratedValue
	private long taskId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "idea_id")
	private Idea idea;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "solution_id")
	private Solution solution;

	@Column(name="actor")
	private long actor;
	
	@Column(name="description")
	@Type(type = "text")
	private String description;
	
	@Column(name="app")
	private int app;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@Column(name="action_requirements")
	private String actionRequirements;
	
	@Column(name="action")
	private String action;
	
	@Column(name="acceptor_user_id")
	private long acceptorUserId;
	
	@Column(name="accepted_date")
	private Date acceptedDate;
	
	@Column(name="time_needed")
	private long timeNeeded;
	
	@Column(name="status")
	private int status;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="socialWorkflow",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<WorkflowLike> workflowLikes;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="socialWorkflow",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<WorkflowComment> workflowComments;
	
	public Set<WorkflowComment> getWorkflowComments() {
		return workflowComments;
	}
	public void setWorkflowComments(Set<WorkflowComment> workflowComments) {
		this.workflowComments = workflowComments;
	}
	public Set<WorkflowLike> getWorkflowLikes() {
		return workflowLikes;
	}
	public void setWorkflowLikes(Set<WorkflowLike> workflowLikes) {
		this.workflowLikes = workflowLikes;
	}
	
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public Idea getIdea() {
		return idea;
	}
	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public Solution getSolution() {
		return solution;
	}
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	
	public long getActor() {
		return actor;
	}
	public void setActor(long actor) {
		this.actor = actor;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getApp() {
		return app;
	}
	public void setApp(int app) {
		this.app = app;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getActionRequirements() {
		return actionRequirements;
	}
	public void setActionRequirements(String actionRequirements) {
		this.actionRequirements = actionRequirements;
	}
	
	
	public long getAcceptorUserId() {
		return acceptorUserId;
	}
	public void setAcceptorUserId(long acceptorUserId) {
		this.acceptorUserId = acceptorUserId;
	}
	public Date getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	
	public long getTimeNeeded() {
		return timeNeeded;
	}
	public void setTimeNeeded(long timeNeeded) {
		this.timeNeeded = timeNeeded;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
