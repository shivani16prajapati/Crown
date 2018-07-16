package com.unicef.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.SolutionScrumThanksDAO;
import com.unicef.domain.SolutionScrumThanks;
import com.unicef.service.SolutionScrumThanksService;

@Service
public class SolutionScrumThanksServiceImpl extends GenericServiceImpl<SolutionScrumThanks, Long> implements SolutionScrumThanksService {

	private SolutionScrumThanksDAO solutionScrumThanksDAO;
	
	
	@Inject
	public void initSolutionScrumDiscussionThanksDAOFactory(SolutionScrumThanksDAO solutionScrumThanksDAO) {
		this.solutionScrumThanksDAO = solutionScrumThanksDAO;
		setGenericDAOFactory(solutionScrumThanksDAO);
	}


	@Override
	public boolean checkUserThanks(long scrumDiscussionId, long userId) {
		return solutionScrumThanksDAO.checkUserThanks(scrumDiscussionId,userId);
	}
	
}
