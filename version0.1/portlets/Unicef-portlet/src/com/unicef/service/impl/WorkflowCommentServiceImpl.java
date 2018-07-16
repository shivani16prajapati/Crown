package com.unicef.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.WorkflowCommentDAO;
import com.unicef.domain.WorkflowComment;
import com.unicef.service.WorkflowCommentService;

@Service
public class WorkflowCommentServiceImpl extends GenericServiceImpl<WorkflowComment, Long> implements
WorkflowCommentService{
	
	private WorkflowCommentDAO workflowCommentDAO;
	
	@Inject
	public void initWorkflowCommentDAOFactory(WorkflowCommentDAO workflowCommentDAO) {
		this.workflowCommentDAO = workflowCommentDAO;
		setGenericDAOFactory(workflowCommentDAO);
	}

	@Override
	public List<WorkflowComment> getCommentFromTaskId(long taskId) {
		return workflowCommentDAO.getCommentFromTaskId(taskId);
	}

	@Override
	public long getCommentCountByTaskId(long taskId) {
		return workflowCommentDAO.getCommentCountByTaskId(taskId);
	}

	@Override
	public String calculateEpocTime(Date createdDate) {
		return workflowCommentDAO.calculateEpocTime(createdDate);
	}

}
