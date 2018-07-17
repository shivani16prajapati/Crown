package com.unicef.dao;

import com.unicef.domain.IdeaCommentVote;

import java.util.List;

public interface IdeaCommentVoteDAO extends GenericDAO<IdeaCommentVote, Long> {

	Boolean checkIdeaCommentVote(long commentId,long userId);

	IdeaCommentVote getIdeaCommentVote(long commentId, long userId);

	public long countIdeaThankYou(long ideaId);

	List<IdeaCommentVote> getListOfComment(long commentId, long userId);

	public long getCurrentWeekCount(long commentId,String type);

	public List<IdeaCommentVote> getThanksListByIdeaId(long ideaId);
	
	public long countLastWeekIdeaThankYou(long ideaId);
	
	long getmonthwiseIdeavote(String ideadate,String ideaid);
	
    long getCountTotalThankyou();
	
	long getCountLastWeekThankyou();
	
	long get_thankyoucounntBydate(String year);

	List<IdeaCommentVote> getmostthankedUser(int start,int end);
	
	public long countThnakyoubyuser(long userId);

}
