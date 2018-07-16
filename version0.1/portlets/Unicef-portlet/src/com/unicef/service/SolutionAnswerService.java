package com.unicef.service;

import com.unicef.domain.SolutionAnswer;

import java.util.List;


public interface SolutionAnswerService extends GenericService<SolutionAnswer, Long> {
   
	List<SolutionAnswer> getSolutionAnswers(long solutionId);
	
	long getSoutionAnswerCount(long solutionId);

	List<SolutionAnswer> getSolutionAnswers(long start, long recordsize,long solutionId);
}
