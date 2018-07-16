package com.unicef.service;

import com.unicef.domain.IdeaCommentVote;

import java.util.List;

public interface IdeaCommentVoteService extends GenericService<IdeaCommentVote, Long> {

	Boolean checkIdeaCommentVote(long commentId, long userId);

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
	
	public List<IdeaCommentVote> getmostthankedUser(int start, int end);
	
	public long countThnakyoubyuser(long userId);

}
