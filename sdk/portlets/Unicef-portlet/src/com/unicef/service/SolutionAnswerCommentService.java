package com.unicef.service;

import com.unicef.domain.SolutionAnswerComment;

import java.util.List;

public interface SolutionAnswerCommentService extends GenericService<SolutionAnswerComment, Long> {

	List<SolutionAnswerComment> getListOfComment(long solutionAnswerId);

	long getCountOfAnswerComment(long solutionAnswerId);

}
