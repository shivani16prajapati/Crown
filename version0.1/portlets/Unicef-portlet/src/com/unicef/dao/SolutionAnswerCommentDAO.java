package com.unicef.dao;

import com.unicef.domain.SolutionAnswerComment;
import com.unicef.service.SolutionAnswerCommentService;

import java.util.List;

public interface SolutionAnswerCommentDAO extends GenericDAO<SolutionAnswerComment, Long> {

	List<SolutionAnswerComment> getListOfComment(long solutionAnswerId);

	long getCountOfAnswerComment(long solutionAnswerId);

}
