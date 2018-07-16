package com.unicef.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.ScrumDiscussionThanksDAO;
import com.unicef.domain.ScrumDiscussionThanks;
import com.unicef.service.ScrumDiscussionThanksService;

@Service
public class ScrumDiscussionThanksServiceImpl extends GenericServiceImpl<ScrumDiscussionThanks, Long> implements ScrumDiscussionThanksService {
	
	private ScrumDiscussionThanksDAO thanksDAO;
	
	@Inject
	public void initScrumDiscussionThanksDAOFactory(ScrumDiscussionThanksDAO thanksDAO) {
		this.thanksDAO = thanksDAO;
		setGenericDAOFactory(thanksDAO);
	}

	@Override
	public boolean checkUserThanks(long scrumDiscussionId, long userId) {
		return thanksDAO.checkUserThanks(scrumDiscussionId,userId);
	}

}
