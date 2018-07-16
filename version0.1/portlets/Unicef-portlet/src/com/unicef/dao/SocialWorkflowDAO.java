package com.unicef.dao;

import com.unicef.domain.SocialWorkflow;

import java.util.List;

public interface SocialWorkflowDAO  extends GenericDAO<SocialWorkflow , Long>{

	long getNextSocialWorkflowTaskId();

	List<SocialWorkflow> getSortedIdeaList(long nextSocialWorkflowTaskId);

	List<SocialWorkflow> getSortedWorkflowList(int start, int recordsize);

	List<SocialWorkflow> getWorkflowList();

	SocialWorkflow findWorkflowFromIdeaId(long ideaId);

	SocialWorkflow findWorkflowFromSolutionId(Long solutionId);

}
