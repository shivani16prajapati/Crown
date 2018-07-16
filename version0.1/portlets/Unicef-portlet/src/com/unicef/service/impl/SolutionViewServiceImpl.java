package com.unicef.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.SolutionViewDAO;
import com.unicef.domain.SolutionView;
import com.unicef.service.SolutionViewService;

@Service
public class SolutionViewServiceImpl extends GenericServiceImpl<SolutionView, Long> implements SolutionViewService { 

	private SolutionViewDAO solutionViewDAO;
	
	@Inject
	public void initSolutionWikiDAOFactory(SolutionViewDAO solutionViewDAO) {
		this.solutionViewDAO = solutionViewDAO;
		setGenericDAOFactory(solutionViewDAO);
	}

	@Override
	public long getSolutionViewCountByUserId(long solutionId, long userId) {
		return solutionViewDAO.getSolutionViewCountByUserId(solutionId, userId);
	}
	@Override
	public long getSolutionViewCountBySolutionId(long solutionId) {
		return solutionViewDAO.getSolutionViewCountBySolutionId(solutionId);
	}
}
