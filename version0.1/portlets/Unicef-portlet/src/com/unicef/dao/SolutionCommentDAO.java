package com.unicef.dao;

import com.unicef.domain.SolutionComment;

import java.util.List;

public interface SolutionCommentDAO extends GenericDAO<SolutionComment, Long> {

	List<SolutionComment> getListOfSolutionComment(long solutionId);

	long getSolutionCommentCount(long solutionId);
}
