package com.unicef.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.ScrumDiscussionLikesDAO;
import com.unicef.domain.Scrum_Discussion_Like;
import com.unicef.service.ScrumDiscussionLikeService;

@Service
public class ScrumDiscussionLikeServiceImpl extends GenericServiceImpl<Scrum_Discussion_Like, Long> implements ScrumDiscussionLikeService {

	private ScrumDiscussionLikesDAO ScrumDiscussionLikesDAO;
	
	@Inject
	public void initScrumDiscussionThanksDAOFactory(ScrumDiscussionLikesDAO ScrumDiscussionLikesDAO) {
		this.ScrumDiscussionLikesDAO = ScrumDiscussionLikesDAO;
		setGenericDAOFactory(ScrumDiscussionLikesDAO);
	}
	
	@Override
	public Scrum_Discussion_Like checkUserIsLiked(long scrumDiscussionId, long userId) {
		return ScrumDiscussionLikesDAO.checkUserIsLiked(scrumDiscussionId, userId);
	}

	@Override
	public boolean isUserLiked(long scrumDiscussionId, long userId) {
		
		Scrum_Discussion_Like discussionLike = ScrumDiscussionLikesDAO.checkUserIsLiked(scrumDiscussionId, userId);
		
		if(discussionLike != null ) {
			return true;
		}
		
		return false;
	}
	
}
