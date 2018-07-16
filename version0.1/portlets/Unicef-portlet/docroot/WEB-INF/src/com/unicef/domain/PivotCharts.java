package com.unicef.domain;

public class PivotCharts {

	private String submissionDate;
	private int size;
	
	public String getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
	@Override
	public String toString() {
		return "[\"" + submissionDate + "\", "
				+ size + "]";
	}
	
	
}
