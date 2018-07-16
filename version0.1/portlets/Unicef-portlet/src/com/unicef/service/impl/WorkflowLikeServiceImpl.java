package com.unicef.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.WorkflowLikeDAO;
import com.unicef.domain.WorkflowLike;
import com.unicef.service.WorkflowLikeService;

@Service
public class WorkflowLikeServiceImpl extends GenericServiceImpl<WorkflowLike, Long> implements WorkflowLikeService{

	private WorkflowLikeDAO workflowLikeDAO;
	
	@Inject
	public void initWorkflowLikeDAOFactory(WorkflowLikeDAO workflowLikeDAO) {
		this.workflowLikeDAO = workflowLikeDAO;
		setGenericDAOFactory(workflowLikeDAO);
	}

	@Override
	public boolean checkWorkflowLike(long taskId, long userId) {
		return workflowLikeDAO.checkWorkflowLike(taskId,userId);
	}

	@Override
	public WorkflowLike getWorkflowLike(long userId, long taskId) {
		return workflowLikeDAO.getWorkflowLike(userId,taskId);
	}

	@Override
	public long getLikeCountByTaskId(long taskId) {
		return workflowLikeDAO.getLikeCountByTaskId(taskId);
	}
	
}
