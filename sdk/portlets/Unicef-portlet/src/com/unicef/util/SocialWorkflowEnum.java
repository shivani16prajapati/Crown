package com.unicef.util;

public enum SocialWorkflowEnum {
	
	WORKFLOW_DRAFT(0),WORKFLOW_IN_PROGRESS(1),WORKFLOW_BLOCKED(2),WORKFLOW_COMPLETED(3);
	
	private int value;
	
	private SocialWorkflowEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
