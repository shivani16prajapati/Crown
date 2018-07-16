package com.unicef.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.liferay.portal.kernel.util.StringPool;
import com.unicef.dao.IdeaCommentVoteDAO;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.domain.Like;

@Transactional
@Repository
public class IdeaCommentVoteDAOImpl extends GenericDAOImpl<IdeaCommentVote, Long> implements IdeaCommentVoteDAO {

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public Boolean checkIdeaCommentVote(long commentId,long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Select idea_vote_id from idea_comment_vote where comment_id="+commentId+" and user_id="+userId);
		if(query.list().isEmpty()){
			return false;
		}else{
			return true;	
		}
	}

	@Override
	public IdeaCommentVote getIdeaCommentVote(long commentId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select * from idea_comment_vote where comment_id="+commentId+" and user_id="+userId;
		return (IdeaCommentVote)session.createSQLQuery(query).addEntity(IdeaCommentVote.class).uniqueResult();
	}

	@Override
	public long countIdeaThankYou(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from idea_comment_vote where idea_id="+ideaId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public List<IdeaCommentVote> getListOfComment(long commentId, long userId) {
		List<IdeaCommentVote> thanksList = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaCommentVote.class);
		criteria.add(Restrictions.eq("ideaComment.commentId", commentId));
		criteria.add(Restrictions.eq("userId", userId));
		criteria.addOrder(Order.desc("voteDate"));
		thanksList = criteria.list();
		return thanksList;
	}

	@Override
	public long getCurrentWeekCount(long commentId,String type) {
		Session session = sessionFactory.getCurrentSession();
		String query = StringPool.BLANK;
		if(type.equalsIgnoreCase("commentCount")){
			 query = "SELECT count(*) FROM idea_comment_vote WHERE voted_date BETWEEN DATE_SUB(CURDATE() ,INTERVAL(WEEKDAY(CURDATE())) DAY ) AND CURDATE()+1 And comment_id="+commentId+"";
		}else if(type.equalsIgnoreCase("thankYou")){
			query = "SELECT count(*) FROM idea_comment_vote WHERE voted_date BETWEEN DATE_SUB(CURDATE() ,INTERVAL(WEEKDAY(CURDATE())) DAY ) AND CURDATE()+1 And idea_id="+commentId+"";
		}
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public List<IdeaCommentVote> getThanksListByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaCommentVote.class);
		criteria.add(Restrictions.eq("ideaId", ideaId));
		return criteria.list();
	}
	
	@Override
	 public long countLastWeekIdeaThankYou(long ideaId) {
	  Calendar currentDate = Calendar.getInstance();
	  Date endDate = currentDate.getTime();
	  currentDate.add(Calendar.DATE, -7);
	  Date startDate = currentDate.getTime();
	  Session session = sessionFactory.getCurrentSession();
	  Criteria criteria = session.createCriteria(IdeaCommentVote.class);
	  criteria.add(Restrictions.eq("ideaId", ideaId));
	  criteria.add(Restrictions.between("voteDate",startDate,endDate));
	  criteria.setProjection(Projections.rowCount());
	  return ((Number)(criteria.uniqueResult())).longValue();
	  
	  
	}

	@Override
	public long getmonthwiseIdeavote(String ideadate, String ideaid) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM idea_comment_vote where idea_id IN "+ideaid+" and voted_date like '"+ideadate+"%'";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public long getCountTotalThankyou() {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM idea_comment_vote";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	
	@Override
	public long getCountLastWeekThankyou() {
		 Calendar currentDate = Calendar.getInstance();
		 Date endDate = currentDate.getTime();
		 currentDate.add(Calendar.DATE, -7);
		 Date startDate = currentDate.getTime();
		 Session session = sessionFactory.getCurrentSession();
		 Criteria criteria = session.createCriteria(IdeaCommentVote.class);
		 criteria.add(Restrictions.between("voteDate",startDate,endDate));
		 criteria.setProjection(Projections.rowCount());
		 return ((Number)(criteria.uniqueResult())).longValue();
	}

	@Override
	public long get_thankyoucounntBydate(String year) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM idea_comment_vote where voted_date like '"+year+"%'";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public List<IdeaCommentVote> getmostthankedUser(int start, int end) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT * FROM idea_comment_vote GROUP BY user_id order by count(*) desc limit "+start+","+end;
		return session.createSQLQuery(query).addEntity(IdeaCommentVote.class).list();
	}

	@Override
	public long countThnakyoubyuser(long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from idea_comment_vote where user_id="+userId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}
}
