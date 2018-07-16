package com.unicef.util;

public enum SolutionEnum {
	
		SOLUTION_BACKLOG(1),SOLUTION_SCRUM(2);
		
		private int value;
		
		private SolutionEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
}
