package com.unicef.service;

import com.unicef.domain.Scrum_Discussion_Like;

public interface ScrumDiscussionLikeService extends GenericService<Scrum_Discussion_Like, Long> {

	public Scrum_Discussion_Like checkUserIsLiked(long scrumDiscussionId, long userId);
	
	public boolean isUserLiked(long scrumDiscussionId, long userId);
	
}
