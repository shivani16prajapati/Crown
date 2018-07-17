package com.unicef.dao.impl;

import com.unicef.dao.ScrumDiscussionLikesDAO;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.domain.Scrum_Discussion_Like;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ScrumDiscussionLikeDAOImpl extends GenericDAOImpl<Scrum_Discussion_Like, Long> implements ScrumDiscussionLikesDAO {

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}
	
	@Override
	public Scrum_Discussion_Like checkUserIsLiked(long scrumDissionId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		
		// Query query = session.createSQLQuery("Select * from scrum_discussion_like where scrum_discussion_id="+scrumDissionId+" and user_id="+userId);
		
		String query = "Select * from scrum_discussion_like where scrum_discussion_id="+scrumDissionId+" and user_id="+userId ;
		
		return (Scrum_Discussion_Like)session.createSQLQuery(query).addEntity(Scrum_Discussion_Like.class).uniqueResult();
	}

}
