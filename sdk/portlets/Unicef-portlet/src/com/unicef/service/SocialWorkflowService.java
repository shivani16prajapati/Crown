package com.unicef.service;

import com.unicef.domain.SocialWorkflow;

import java.util.List;

public interface SocialWorkflowService extends GenericService<SocialWorkflow, Long> {
	public long getNextSocialWorkflowTaskId();

	List<SocialWorkflow> getSortedIdeaList(long nextSocialWorkflowTaskId);

	public List<SocialWorkflow> getSortedWorkflowList(int start,
			int recordsize);

	public List<SocialWorkflow> getWorkflowList();

	public SocialWorkflow findWorkflowFromIdeaId(long ideaId);

	public SocialWorkflow findWorkflowFromSolutionId(Long solutionId);

}
