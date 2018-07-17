package com.unicef.service;

import com.unicef.domain.WorkflowLike;

public interface WorkflowLikeService extends GenericService<WorkflowLike, Long>{

	public boolean checkWorkflowLike(long taskId, long userId);

	public WorkflowLike getWorkflowLike(long userId, long taskId);

	public long getLikeCountByTaskId(long taskId);

}
