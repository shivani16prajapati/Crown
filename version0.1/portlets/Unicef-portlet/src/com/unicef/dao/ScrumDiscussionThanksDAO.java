package com.unicef.dao;

import com.unicef.domain.ScrumDiscussionThanks;

public interface ScrumDiscussionThanksDAO extends GenericDAO<ScrumDiscussionThanks, Long> {

	public boolean checkUserThanks(long scrumDiscussionId, long userId);

}
