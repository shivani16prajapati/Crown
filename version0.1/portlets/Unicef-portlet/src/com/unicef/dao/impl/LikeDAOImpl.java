package com.unicef.dao.impl;

import com.unicef.dao.LikeDAO;
import com.unicef.domain.IdeaEndorsement;
import com.unicef.domain.Like;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class LikeDAOImpl extends GenericDAOImpl<Like, Long> implements LikeDAO{
	
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

	@Override
	public boolean checkIdeaLike(long ideaId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Select idea_like_id from idea_like where idea_id="+ideaId+" and user_id="+userId);
		if(query.list().isEmpty()){
			return false;
		}else{
			return true;	
		}
		
	}

	@Override
	public Like getIdeaLike(long ideaId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select * from idea_like where idea_id="+ideaId+" and user_id="+userId;
		return (Like)session.createSQLQuery(query).addEntity(Like.class).uniqueResult();
	}

	@Override
	public long getCountOfIdeaLike(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from idea_like where idea_id="+ideaId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public long getWeeklyCountOfIdeaLike(long ideaId) {
		Calendar currentDate = Calendar.getInstance();
		Date endDate = currentDate.getTime();
		
		currentDate.add(Calendar.DATE, -7);
		Date startDate = currentDate.getTime();
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Like.class);
		criteria.add(Restrictions.eq("idea.ideaId", ideaId));
		criteria.add(Restrictions.between("likeDate", startDate, endDate));
		criteria.setProjection(Projections.rowCount());
		return ((Number)(criteria.uniqueResult())).longValue();
	}

	@Override
	public long getTotalLikesOfLastWeek() {
		Calendar currentDate = Calendar.getInstance();
		Date endDate = currentDate.getTime();		
		currentDate.add(Calendar.DATE, -7);
		Date startDate = currentDate.getTime();		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Like.class);
		criteria.add(Restrictions.between("likeDate", startDate, endDate));
		criteria.setProjection(Projections.rowCount());
		return ((Number)(criteria.uniqueResult())).longValue();
	}

	@Override
	public long getmonthwiseIdea(String ideadate, String ideaid) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM idea_like where idea_id IN "+ideaid+" and liked_date like '"+ideadate+"%'";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public long getTotalLike() {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM idea_like ";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public long getAll_LikesByDatewise(String year) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM idea_like where liked_date like '"+year+"%'";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public List<Like> getlikebyideaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select * from idea_like where idea_id = '"+ideaId+"'";
		return session.createSQLQuery(query).addEntity(Like.class).list();
	}

}
