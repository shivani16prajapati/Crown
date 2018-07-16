package com.unicef.dao;

import com.unicef.domain.SolutionAnswerVote;
import com.unicef.domain.SolutionLike;

import java.util.List;

public interface SolutionAnswerVoteDAO extends GenericDAO<SolutionAnswerVote, Long> {

	public SolutionAnswerVote getSolutionAnswerVote(long solutionAnswerId, long userId);

	public long getCountOfSolutionAnswerVote(long solutionAnswerId);
	
	public boolean checkSolutionAnswerVote(long solutionAnswerId, long userId);
	
	public long getWantAnswerCount(long solutionId);
	
	List<SolutionAnswerVote>getAnsVoteListBySolutionIdAndAnswerId(long solutionId, long answerId);
	
	public List<SolutionAnswerVote> getlikebySolutionId(long solutionId);
}
