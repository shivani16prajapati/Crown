package com.unicef.service;

import com.unicef.domain.SolutionView;

public interface SolutionViewService extends GenericService<SolutionView, Long> {
	
	long getSolutionViewCountByUserId(long solutionId, long userId);

	long getSolutionViewCountBySolutionId(long solutionId);
}
