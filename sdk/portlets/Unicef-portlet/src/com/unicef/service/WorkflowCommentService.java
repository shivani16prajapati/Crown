package com.unicef.service;

import com.unicef.domain.WorkflowComment;

import java.util.Date;
import java.util.List;

public interface WorkflowCommentService extends GenericService<WorkflowComment, Long> {

	List<WorkflowComment> getCommentFromTaskId(long taskId);

	public long getCommentCountByTaskId(long taskId);

	String calculateEpocTime(Date createdDate);

}
