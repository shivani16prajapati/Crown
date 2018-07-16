package com.unicef.dao.impl;

import com.unicef.dao.SolutionLikeDAO;
import com.unicef.domain.Like;
import com.unicef.domain.SolutionLike;

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
public class SolutionLikeDAOImpl extends GenericDAOImpl<SolutionLike, Long> implements SolutionLikeDAO {
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

	@Override
	public boolean checkSolutionLike(long solutionId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Select solution_like_id from solution_like where solution_id="+solutionId+" and user_id="+userId);
		if(query.list().isEmpty()){
			return false;
		}else{
			return true;	
		}
	}

	@Override
	public long getCountOfSolutionLike(long solutionId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from solution_like where solution_id="+solutionId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public SolutionLike getIdeaLike(long solutionId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select * from solution_like where solution_id="+solutionId+" and user_id="+userId;
		return (SolutionLike)session.createSQLQuery(query).addEntity(SolutionLike.class).uniqueResult();
	}

	@Override
	public long getWeeklyCountOfSolutionLike(long solutionId) {
		  Calendar currentDate = Calendar.getInstance();
		  Date endDate = currentDate.getTime();
		  
		  currentDate.add(Calendar.DATE, -7);
		  Date startDate = currentDate.getTime();
		  
		  Session session = sessionFactory.getCurrentSession();
		  Criteria criteria = session.createCriteria(SolutionLike.class);
		  criteria.add(Restrictions.eq("solution.solutionId", solutionId));
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
		  Criteria criteria = session.createCriteria(SolutionLike.class);
		  criteria.add(Restrictions.between("likeDate", startDate, endDate));
		  criteria.setProjection(Projections.rowCount());
		  return ((Number)(criteria.uniqueResult())).longValue();
	}

	@Override
	public List<SolutionLike> getlikebySolutionId(long solutionId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select * from solution_like where solution_id = '"+solutionId+"'";
		return session.createSQLQuery(query).addEntity(SolutionLike.class).list();
	}
	
	


}
