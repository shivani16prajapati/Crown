package com.unicef.dao.impl;

import com.unicef.dao.SolutionAnswerCommentDAO;
import com.unicef.domain.SolutionAnswerComment;
import com.unicef.service.SolutionAnswerCommentService;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class SolutionAnswerCommentDAOImpl extends GenericDAOImpl<SolutionAnswerComment, Long> implements
	SolutionAnswerCommentDAO {
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<SolutionAnswerComment> getListOfComment(
			long solutionAnswerId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select * from solution_answer_comment where answer_id="+solutionAnswerId;
		return (List<SolutionAnswerComment>)session.createSQLQuery(query).addEntity(SolutionAnswerComment.class).list();
	}

	@Override
	public long getCountOfAnswerComment(long solutionAnswerId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from solution_answer_comment where answer_id="+solutionAnswerId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}
}
