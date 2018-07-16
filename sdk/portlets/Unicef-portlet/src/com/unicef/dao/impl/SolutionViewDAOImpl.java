package com.unicef.dao.impl;

import com.unicef.dao.SolutionViewDAO;
import com.unicef.domain.SolutionView;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class SolutionViewDAOImpl extends GenericDAOImpl<SolutionView, Long> implements SolutionViewDAO{

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public long getSolutionViewCountByUserId(long solutionId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) from solution_view where solution_id = "+solutionId+" and user_id =	"+userId+" ";
		return ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
	}

	@Override
	public long getSolutionViewCountBySolutionId(long solutionId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) from solution_view where solution_id = "+solutionId+" ";
		return ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
	}
	
}
