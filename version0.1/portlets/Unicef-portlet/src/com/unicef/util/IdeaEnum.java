package com.unicef.util;

public enum IdeaEnum {

	DRAFT(0),IDEA_BACKLOG(1),IDEA_SCRUM(2);
	
	private int value;
	
	private IdeaEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
