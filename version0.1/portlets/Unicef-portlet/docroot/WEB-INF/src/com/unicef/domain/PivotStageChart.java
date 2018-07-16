package com.unicef.domain;

public class PivotStageChart {

	private String stageName;
	private long ideaStageCount;
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public long getIdeaStageCount() {
		return ideaStageCount;
	}
	public void setIdeaStageCount(long ideaStageCount) {
		this.ideaStageCount = ideaStageCount;
	}
	@Override
	public String toString() {
		return "[\"" + stageName + "\","
				+ ideaStageCount + "]";
	}
}
