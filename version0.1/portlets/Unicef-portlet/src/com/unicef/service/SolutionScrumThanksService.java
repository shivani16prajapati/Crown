package com.unicef.service;

import com.unicef.domain.SolutionScrumThanks;

public interface SolutionScrumThanksService extends GenericService<SolutionScrumThanks, Long> {

	public boolean checkUserThanks(long scrumDiscussionId, long userId);

}
