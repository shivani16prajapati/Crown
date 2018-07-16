package com.unicef.service;

import com.unicef.domain.ScrumDiscussionThanks;

public interface ScrumDiscussionThanksService extends GenericService<ScrumDiscussionThanks, Long>{

	public boolean checkUserThanks(long scrumDiscussionId, long userId);

}
