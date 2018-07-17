package com.unicef.dao.impl;

import com.unicef.dao.IdeaCommentDAO;
import com.unicef.domain.IdeaComment;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class IdeaCommentDAOImpl extends GenericDAOImpl<IdeaComment, Long> implements IdeaCommentDAO{
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<IdeaComment> getIdeaComments(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select * from idea_comment where idea_id = '"+ideaId+"' and parent_comment_id = 0 ORDER BY created_date Desc";
		return session.createSQLQuery(query).addEntity(IdeaComment.class).list();
	}

	@Override
	public long getIdeaCommentCount(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select count(*) from idea_comment where idea_id ="+ideaId+" and parent_comment_id = 0";
		return ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
	}

	@Override
	public void deleteIdeaThanksVoteByComment(long commentId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(
				"delete from idea_comment_vote where comment_id=" + commentId + "")
				.executeUpdate();
	}
}
