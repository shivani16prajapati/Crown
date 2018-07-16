package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.IdeaCommentDAO;
import com.unicef.domain.IdeaComment;
import com.unicef.service.IdeaCommentService;

@Service
public class IdeaCommentServiceImpl extends GenericServiceImpl<IdeaComment, Long> implements IdeaCommentService{
	
	private IdeaCommentDAO ideaCommentDAO;
	
	@Inject
	public void initCommentDAOFactory(IdeaCommentDAO ideaCommentDAO) {
	  this.ideaCommentDAO = ideaCommentDAO;
	  setGenericDAOFactory(ideaCommentDAO);
	 }

	@Override
	public List<IdeaComment> getIdeaComments(long ideaId) {
		return ideaCommentDAO.getIdeaComments(ideaId);
	}
	@Override
	public long getIdeaCommentCount(long ideaId) {
		return ideaCommentDAO.getIdeaCommentCount(ideaId);
	}

	@Override
	public void deleteIdeaThanksVoteByComment(long commentId) {
		ideaCommentDAO.deleteIdeaThanksVoteByComment(commentId);
	}
}
