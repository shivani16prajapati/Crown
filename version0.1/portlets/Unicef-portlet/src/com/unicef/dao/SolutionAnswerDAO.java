package com.unicef.dao;

import com.unicef.domain.SolutionAnswer;

import java.util.List;

public interface SolutionAnswerDAO extends GenericDAO<SolutionAnswer, Long> {

	List<SolutionAnswer> getSolutionAnswers(long solutionId);
	
	long getSoutionAnswerCount(long solutionId);

	List<SolutionAnswer> getSolutionAnswers(long start, long recordsize,long solutionId);
}