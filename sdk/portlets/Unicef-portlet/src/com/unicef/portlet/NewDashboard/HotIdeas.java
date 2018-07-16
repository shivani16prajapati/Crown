package com.unicef.portlet.NewDashboard;

import java.util.Date;

public class HotIdeas {
	
	
	private String ideaName;
	
	private String inventorName;
	
	private Date submissionDate;
	
	private long upVotes;
	
	private long comments;

	
	
	public String getIdeaName() {
		return ideaName;
	}

	public void setIdeaName(String ideaName) {
		this.ideaName = ideaName;
	}

	public String getInventorName() {
		return inventorName;
	}

	public void setInventorName(String inventorName) {
		this.inventorName = inventorName;
	}


	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public long getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(long upVotes) {
		this.upVotes = upVotes;
	}

	public long getComments() {
		return comments;
	}

	public void setComments(long comments) {
		this.comments = comments;
	}
	

}
