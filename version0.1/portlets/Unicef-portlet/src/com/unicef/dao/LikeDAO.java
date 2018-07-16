package com.unicef.dao;

import java.util.List;

import com.unicef.domain.Like;

public interface LikeDAO extends GenericDAO<Like, Long> {

	boolean checkIdeaLike(long ideaId, long userId);

	Like getIdeaLike(long ideaId, long userId);

	long getCountOfIdeaLike(long ideaId);

	long getWeeklyCountOfIdeaLike(long ideaId);

	long getTotalLikesOfLastWeek();
	
	long getTotalLike();
	
	long getmonthwiseIdea(String ideadate,String ideaid);
	
	long getAll_LikesByDatewise(String year);
	
	List<Like> getlikebyideaId(long ideaId);
}
