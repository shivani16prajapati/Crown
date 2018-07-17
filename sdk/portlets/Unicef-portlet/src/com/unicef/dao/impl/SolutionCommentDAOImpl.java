package com.unicef.dao.impl;

import com.unicef.dao.SolutionCommentDAO;
import com.unicef.domain.IdeaComment;
import com.unicef.domain.SolutionComment;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class SolutionCommentDAOImpl extends GenericDAOImpl<SolutionComment, Long> implements SolutionCommentDAO {
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<SolutionComment> getListOfSolutionComment(long solutionId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select * from solution_comment where solution_id = '"+solutionId+"' and parent_comment_id = 0 ORDER BY created_date Desc";
		return session.createSQLQuery(query).addEntity(SolutionComment.class).list();
	}

	@Override
	public long getSolutionCommentCount(long solutionId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select count(*) from solution_comment where solution_id ="+solutionId+" and parent_comment_id = 0";
		return ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
	}
}
