package com.unicef.service;

import com.unicef.domain.SolutionComment;

import java.util.List;

public interface SolutionCommentService extends GenericService<SolutionComment, Long> {
	
	List<SolutionComment> getListOfSolutionComment(long solutionId);

	long getSolutionCommentCount(long solutionId);
}
