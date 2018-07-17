package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.SolutionLikeDAO;
import com.unicef.domain.SolutionLike;
import com.unicef.service.SolutionLikeService;

@Service
public class SolutionLikeServiceImpl extends GenericServiceImpl<SolutionLike, Long> implements SolutionLikeService {

	private SolutionLikeDAO solutionLikeDAO;
	@Inject
	public void initSolutionLikeDAOFactory(SolutionLikeDAO solutionLikeDAO) {
		this.solutionLikeDAO = solutionLikeDAO;
		setGenericDAOFactory(solutionLikeDAO);
	}
	@Override
	public boolean checkSolutionLike(long solutionId, long userId) {
		return solutionLikeDAO.checkSolutionLike(solutionId, userId);
	}
	@Override
	public long getCountOfSolutionLike(long solutionId) {
		return solutionLikeDAO.getCountOfSolutionLike(solutionId);
	}
	@Override
	public SolutionLike getIdeaLike(long solutionId, long userId) {
		return solutionLikeDAO.getIdeaLike(solutionId, userId);
	}
	@Override
	public long getWeeklyCountOfSolutionLike(long solutionId) {
		return solutionLikeDAO.getWeeklyCountOfSolutionLike(solutionId);
	}
	@Override
	public long getTotalLikesOfLastWeek() {
		return solutionLikeDAO.getTotalLikesOfLastWeek();
	}
	@Override
	public List<SolutionLike> getlikebySolutionId(long solutionId) {
		return solutionLikeDAO.getlikebySolutionId(solutionId);
	}
}
