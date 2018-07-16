package com.unicef.dao;

import com.unicef.domain.SolutionScrumThanks;

public interface SolutionScrumThanksDAO extends GenericDAO<SolutionScrumThanks, Long> {

	public boolean checkUserThanks(long scrumDiscussionId, long userId);

}
