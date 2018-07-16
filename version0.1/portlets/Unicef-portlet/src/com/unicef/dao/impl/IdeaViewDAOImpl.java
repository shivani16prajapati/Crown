package com.unicef.dao.impl;

import com.unicef.dao.IdeaViewDAO;
import com.unicef.domain.IdeaView;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class IdeaViewDAOImpl extends GenericDAOImpl<IdeaView, Long> implements IdeaViewDAO{

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public long getIdeaViewCountByUserId(long ideaId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) from idea_view where idea_id = "+ideaId+" and user_id =	"+userId+" ";
		return ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
	}

	@Override
	public long getIdeaViewCountByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) from idea_view where idea_id = "+ideaId+" ";
		return ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
	}
	
}
