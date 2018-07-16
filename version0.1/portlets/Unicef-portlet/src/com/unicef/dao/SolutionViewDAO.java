package com.unicef.dao;

import com.unicef.domain.SolutionView;

public interface SolutionViewDAO extends GenericDAO<SolutionView, Long> {

	long getSolutionViewCountByUserId(long solutionId, long userId);

	long getSolutionViewCountBySolutionId(long solutionId);

}
