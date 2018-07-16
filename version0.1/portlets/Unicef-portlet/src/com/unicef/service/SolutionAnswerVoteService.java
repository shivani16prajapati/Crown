package com.unicef.service;

import com.unicef.domain.SolutionAnswerVote;

import java.util.List;

public interface SolutionAnswerVoteService extends GenericService<SolutionAnswerVote, Long> {
	
	public SolutionAnswerVote getSolutionAnswerVote(long solutionAnswerId, long userId);

	public long getCountOfSolutionAnswerVote(long solutionAnswerId);
	
	public boolean checkSolutionAnswerVote(long solutionAnswerId, long userId);
	
	public long getWantAnswerCount(long solutionId);
	
	List<SolutionAnswerVote>getAnsVoteListBySolutionIdAndAnswerId(long solutionId, long answerId);
	
	public List<SolutionAnswerVote> getlikebySolutionId(long solutionId);
	
}
