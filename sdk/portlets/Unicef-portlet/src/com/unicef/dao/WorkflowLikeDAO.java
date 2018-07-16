package com.unicef.dao;

import com.unicef.domain.WorkflowLike;

public interface WorkflowLikeDAO extends GenericDAO<WorkflowLike, Long>{

	public boolean checkWorkflowLike(long taskId, long userId);

	public WorkflowLike getWorkflowLike(long userId, long taskId);

	public long getLikeCountByTaskId(long taskId);

}
