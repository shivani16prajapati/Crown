package com.unicef.dao.impl;

import com.unicef.dao.IdeaAttachementDAO;
import com.unicef.domain.IdeaAttachement;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class IdeaAttachementDAOImpl extends GenericDAOImpl<IdeaAttachement, Long> implements IdeaAttachementDAO{

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@Override
	 public List<IdeaAttachement> findByIdeaIdAndIdeaVersion(long ideaId, double ideaVersionId) {
	  Session session = sessionFactory.getCurrentSession();
	  return (List<IdeaAttachement>)session.createSQLQuery("select * from idea_attachements where idea_id = "+ideaId+" and idea_version = "+ideaVersionId+" ").addEntity(IdeaAttachement.class).list();
	 }
	
}
