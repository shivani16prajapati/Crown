package com.unicef.dao.impl;

import com.unicef.dao.IdeaHistoryDAO;
import com.unicef.domain.IdeaHistory;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class IdeaHistoryDAOImpl extends GenericDAOImpl<IdeaHistory, Long> implements IdeaHistoryDAO {

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<IdeaHistory> getIdeaHistoryList(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		String query ="select * from idea_history where idea_id="+ideaId+" ORDER BY idea_version DESC";
		return (List<IdeaHistory>)session.createSQLQuery(query).addEntity(IdeaHistory.class).list();
	}
	
}
