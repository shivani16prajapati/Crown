package com.unicef.dao;

import com.unicef.domain.WorkflowComment;

import java.util.Date;
import java.util.List;

public interface WorkflowCommentDAO extends GenericDAO<WorkflowComment, Long> {

	List<WorkflowComment> getCommentFromTaskId(long taskId);

	public long getCommentCountByTaskId(long taskId);

	String calculateEpocTime(Date createdDate);

}
