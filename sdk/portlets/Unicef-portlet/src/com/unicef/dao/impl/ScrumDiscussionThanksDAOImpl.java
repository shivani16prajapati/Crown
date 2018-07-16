package com.unicef.dao.impl;

import com.unicef.dao.ScrumDiscussionThanksDAO;
import com.unicef.domain.ScrumDiscussionThanks;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ScrumDiscussionThanksDAOImpl extends GenericDAOImpl<ScrumDiscussionThanks, Long> implements ScrumDiscussionThanksDAO {
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

	@Override
	public boolean checkUserThanks(long scrumDiscussionId, long userId) {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Select scrum_thanks_id from scrum_discussion_thanks where scrum_discussion_id="+scrumDiscussionId+" and thanks_creator="+userId);
		if(query.list().isEmpty()){
			return false;
		}else{
			return true;	
		}
	}

}
