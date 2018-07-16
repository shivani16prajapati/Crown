package com.unicef.dao.impl;

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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.unicef.dao.IdeaEndorsementDAO;
import com.unicef.domain.IdeaEndorsement;
import com.unicef.domain.Like;


@Transactional
@Repository
public class IdeaEndorsementDAOImpl extends GenericDAOImpl<IdeaEndorsement,Long> implements IdeaEndorsementDAO{

	
	private static final Log _log = LogFactoryUtil.getLog(IdeaEndorsementDAOImpl.class);

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<IdeaEndorsement> getIdeaEndorsement(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select * from idea_endorsment where idea_id = '"+ideaId+"' ORDER BY created_date Desc";
		return session.createSQLQuery(query).addEntity(IdeaEndorsement.class).list();
	}

	@Override
	public long getIdeaEndorsementCount(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select count(*) from idea_endorsment where idea_id ="+ideaId;
		return ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
	}

	@Override
	public boolean checkIdeaEndorsement(long ideaId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Select endorsment_id from idea_endorsment where idea_id="+ideaId+" and user_id="+userId);
		if(query.list().isEmpty()){
			return false;
		}else{
			return true;	
		}
	}

	@Override
	public IdeaEndorsement getcheckIdeaEndorsement(long ideaId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select * from idea_endorsment where idea_id="+ideaId+" and user_id="+userId;
		return (IdeaEndorsement)session.createSQLQuery(query).addEntity(IdeaEndorsement.class).uniqueResult();
	}

	@Override
	public long getWeeklyCountOfIdeaEndorsement(long ideaId) {
		Calendar currentDate = Calendar.getInstance();
		Date endDate = currentDate.getTime();
		
		currentDate.add(Calendar.DATE, -7);
		Date startDate = currentDate.getTime();
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaEndorsement.class);
		criteria.add(Restrictions.eq("idea.ideaId", ideaId));
		criteria.add(Restrictions.between("createdDate", startDate, endDate));
		criteria.setProjection(Projections.rowCount());
		return ((Number)(criteria.uniqueResult())).longValue();
	}

	@Override
	public long getTotalIdeaEndorsementOfLastWeek() {
		Calendar currentDate = Calendar.getInstance();
		Date endDate = currentDate.getTime();
		
		currentDate.add(Calendar.DATE, -7);
		Date startDate = currentDate.getTime();
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaEndorsement.class);
		criteria.add(Restrictions.between("createdDate", startDate, endDate));
		criteria.setProjection(Projections.rowCount());
		return ((Number)(criteria.uniqueResult())).longValue();
	}

	@Override
	public long gettotalIdeaendorsed() {
		Session session = sessionFactory.getCurrentSession();
		String query = "select count(*) from idea_endorsment ";
		return ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
	}

}
