package com.unicef.dao;

import java.util.List;

import com.unicef.domain.Like;
import com.unicef.domain.SolutionLike;

public interface SolutionLikeDAO extends GenericDAO<SolutionLike, Long> {

	boolean checkSolutionLike(long solutionId, long userId);

	long getCountOfSolutionLike(long solutionId);

	SolutionLike getIdeaLike(long solutionId, long userId);

	public long getWeeklyCountOfSolutionLike(long solutionId);

	long getTotalLikesOfLastWeek();

	List<SolutionLike> getlikebySolutionId(long solutionId);
}
