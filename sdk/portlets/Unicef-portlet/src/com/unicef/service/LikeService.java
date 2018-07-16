package com.unicef.service;

import java.util.List;

import com.unicef.domain.Like;

public interface LikeService extends GenericService<Like, Long> {
	
	boolean checkIdeaLike(long ideaId, long userId);

	public Like getIdeaLike(long ideaId, long userId);

	long getCountOfIdeaLike(long ideaId);

	long getWeeklyCountOfIdeaLike(long ideaId);

	long getTotalLikesOfLastWeek();
	
	long getTotalLike();
	
	long getmonthwiseIdea(String ideadate, String ideaid);
	
	long getAll_LikesByDatewise(String year);
	
	public List<Like> getlikebyideaId(long ideaId);

}
