package com.unicef.service;

import java.util.List;

import com.unicef.domain.SolutionLike;

public interface SolutionLikeService extends GenericService<SolutionLike, Long>{
	
	boolean checkSolutionLike(long solutionId, long userId);

	long getCountOfSolutionLike(long solutionId);

	SolutionLike getIdeaLike(long solutionId, long userId);

	public long getWeeklyCountOfSolutionLike(long solutionId);
	
	long getTotalLikesOfLastWeek();
	
	public List<SolutionLike> getlikebySolutionId(long solutionId);
}
