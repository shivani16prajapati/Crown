package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.IdeaCommentVoteDAO;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.service.IdeaCommentVoteService;

@Service
public class IdeaCommentVoteServiceImpl extends GenericServiceImpl<IdeaCommentVote, Long> implements IdeaCommentVoteService{
 private IdeaCommentVoteDAO IdeaCommentVoteDAO;
 
	@Inject
	public void initIdeaCommentVoteDAOFactory(IdeaCommentVoteDAO commentVoteDAO) {
		this.IdeaCommentVoteDAO = commentVoteDAO;
		setGenericDAOFactory(commentVoteDAO);
	}
 
	@Override
	public Boolean checkIdeaCommentVote(long commentId,long userId) {
		return IdeaCommentVoteDAO.checkIdeaCommentVote(commentId,userId);
	}

	@Override
	public IdeaCommentVote getIdeaCommentVote(long commentId, long userId) {
		return IdeaCommentVoteDAO.getIdeaCommentVote(commentId,userId);
	}

	@Override
	public long countIdeaThankYou(long ideaId) {
		return IdeaCommentVoteDAO.countIdeaThankYou(ideaId);
	}

	@Override
	public List<IdeaCommentVote> getListOfComment(long commentId, long userId) {
		return IdeaCommentVoteDAO.getListOfComment(commentId,userId);
	}

	@Override
	public long getCurrentWeekCount(long commentId,String type) {
		return IdeaCommentVoteDAO.getCurrentWeekCount(commentId,type);
	}

	@Override
	public List<IdeaCommentVote> getThanksListByIdeaId(long ideaId) {
		return IdeaCommentVoteDAO.getThanksListByIdeaId(ideaId);
	}

	@Override
	public long countLastWeekIdeaThankYou(long ideaId) {
		return IdeaCommentVoteDAO.countLastWeekIdeaThankYou(ideaId);
	}

	@Override
	public long getmonthwiseIdeavote(String ideadate, String ideaid) {
		return IdeaCommentVoteDAO.getmonthwiseIdeavote(ideadate, ideaid);
	}

	@Override
	public long getCountTotalThankyou() {
		return IdeaCommentVoteDAO.getCountTotalThankyou();
	}

	@Override
	public long getCountLastWeekThankyou() {
		return IdeaCommentVoteDAO.getCountLastWeekThankyou();
	}

	@Override
	public long get_thankyoucounntBydate(String year) {
		return IdeaCommentVoteDAO.get_thankyoucounntBydate(year);
	}

	@Override
	public List<IdeaCommentVote> getmostthankedUser(int start, int end) {
		return IdeaCommentVoteDAO.getmostthankedUser(start, end);
	}

	@Override
	public long countThnakyoubyuser(long userId) {
		// TODO Auto-generated method stub
		return IdeaCommentVoteDAO.countThnakyoubyuser(userId);
	}

}
