package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.SocialWorkflowDAO;
import com.unicef.domain.SocialWorkflow;
import com.unicef.service.SocialWorkflowService;

@Service
public class SocialWorkflowServiceImpl extends GenericServiceImpl<SocialWorkflow, Long> implements SocialWorkflowService{
	
	private SocialWorkflowDAO socialWorkflowDAO;
	
	@Inject
	public void initWorkflowDAOFactory(SocialWorkflowDAO socialWorkflowDAO) {
		this.socialWorkflowDAO = socialWorkflowDAO;
		setGenericDAOFactory(socialWorkflowDAO);
	}

	@Override
	public long getNextSocialWorkflowTaskId() {
		return socialWorkflowDAO.getNextSocialWorkflowTaskId();
	}

	@Override
	public List<SocialWorkflow> getSortedIdeaList(long nextSocialWorkflowTaskId) {
		return socialWorkflowDAO.getSortedIdeaList(nextSocialWorkflowTaskId);
	}

	@Override
	public List<SocialWorkflow> getSortedWorkflowList(int start,
			int recordsize) {
		return socialWorkflowDAO.getSortedWorkflowList(start,recordsize);
	}

	@Override
	public List<SocialWorkflow> getWorkflowList() {
		return socialWorkflowDAO.getWorkflowList();
	}

	@Override
	public SocialWorkflow findWorkflowFromIdeaId(long ideaId) {
		return socialWorkflowDAO.findWorkflowFromIdeaId(ideaId);
	}

	@Override
	public SocialWorkflow findWorkflowFromSolutionId(Long solutionId) {
		return socialWorkflowDAO.findWorkflowFromSolutionId(solutionId);
	}

}
