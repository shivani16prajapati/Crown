package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.LikeDAO;
import com.unicef.domain.Like;
import com.unicef.service.LikeService;

@Service
public class LikeServiceImpl extends GenericServiceImpl<Like, Long> implements LikeService {

	private LikeDAO likeDAO;
	
	@Inject
	public void initLikeDAOFactory(LikeDAO likeDAO) {
		this.likeDAO = likeDAO;
		setGenericDAOFactory(likeDAO);
	}
	@Override
	public boolean checkIdeaLike(long ideaId, long userId) {
		return likeDAO.checkIdeaLike(ideaId, userId);
	}
	@Override
	public Like getIdeaLike(long ideaId, long userId) {
		return likeDAO.getIdeaLike(ideaId, userId);
	}
	@Override
	public long getCountOfIdeaLike(long ideaId) {
		return likeDAO.getCountOfIdeaLike(ideaId);
	}
	@Override
	public long getWeeklyCountOfIdeaLike(long ideaId) {
		return likeDAO.getWeeklyCountOfIdeaLike(ideaId);
	}
	@Override
	public long getTotalLikesOfLastWeek() {
		return likeDAO.getTotalLikesOfLastWeek();
	}
	@Override
	public long getmonthwiseIdea(String ideadate, String ideaid) {
		return likeDAO.getmonthwiseIdea(ideadate, ideaid);
	}
	@Override
	public long getTotalLike() {
		return likeDAO.getTotalLike();
	}
	@Override
	public long getAll_LikesByDatewise(String year) {
		return likeDAO.getAll_LikesByDatewise(year);
	}
	@Override
	public List<Like> getlikebyideaId(long ideaId) {
		return likeDAO.getlikebyideaId(ideaId);
	}
}
