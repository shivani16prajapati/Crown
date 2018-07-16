package com.unicef.dao;

import com.unicef.domain.IdeaComment;

import java.util.List;


public interface IdeaCommentDAO extends GenericDAO<IdeaComment, Long> {

	List<IdeaComment> getIdeaComments(long ideaId);
	
	
	long getIdeaCommentCount(long ideaId);


	public void deleteIdeaThanksVoteByComment(long commentId);
}
