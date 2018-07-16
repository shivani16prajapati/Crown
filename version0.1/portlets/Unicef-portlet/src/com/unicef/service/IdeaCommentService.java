package com.unicef.service;

import com.unicef.domain.IdeaComment;

import java.util.List;

public interface IdeaCommentService extends GenericService<IdeaComment, Long> {

	List<IdeaComment> getIdeaComments(long ideaId);
	
	long getIdeaCommentCount(long ideaId);

	public void deleteIdeaThanksVoteByComment(long commentId);
}
