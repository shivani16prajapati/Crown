package com.unicef.dao.impl;

import com.unicef.dao.SolutionAnswerVoteDAO;
import com.unicef.domain.SolutionAnswerVote;
import com.unicef.domain.SolutionLike;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class SolutionAnswerVoteDAOImpl extends GenericDAOImpl<SolutionAnswerVote, Long> implements SolutionAnswerVoteDAO {
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

	@Override
	public SolutionAnswerVote getSolutionAnswerVote(long solutionAnswerId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select * from solution_answer_vote where answer_id="+solutionAnswerId+" and user_id="+userId;
		return (SolutionAnswerVote)session.createSQLQuery(query).addEntity(SolutionAnswerVote.class).uniqueResult();
	}

	@Override
	public long getCountOfSolutionAnswerVote(long solutionAnswerId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from solution_answer_vote where answer_id="+solutionAnswerId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}
	
	@Override
	public boolean checkSolutionAnswerVote(long solutionAnswerId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Select solution_answer_vote_id from solution_answer_vote where answer_id="+solutionAnswerId+" and user_id="+userId);
		if(query.list().isEmpty()){
			return false;
		}else{
			return true;	
		}
	}
	
	@Override
	public long getWantAnswerCount(long solutionId) {
		try{
			Session session = sessionFactory.getCurrentSession();
			String query = "select count(sav.answer_id) as result from solution_answer_vote as sav where sav.solution_id = "+solutionId+" GROUP BY sav.answer_id order by result desc limit 1";
			return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
		}catch(Exception e){
			return 0;
		}
		
	}

	@Override
	public List<SolutionAnswerVote> getAnsVoteListBySolutionIdAndAnswerId(long solutionId, long answerId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select * from solution_answer_vote where solution_id = "+solutionId+" and answer_id = "+answerId+" ORDER BY voted_date DESC ";
		return session.createSQLQuery(query).addEntity(SolutionAnswerVote.class).list();
	}

	@Override
	public List<SolutionAnswerVote> getlikebySolutionId(long solutionId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select * from solution_answer_vote where solution_id = '"+solutionId+"'";
		return session.createSQLQuery(query).addEntity(SolutionAnswerVote.class).list();
	}
}
