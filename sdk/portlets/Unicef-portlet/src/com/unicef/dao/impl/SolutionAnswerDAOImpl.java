package com.unicef.dao.impl;

import com.unicef.dao.SolutionAnswerDAO;
import com.unicef.domain.SolutionAnswer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class SolutionAnswerDAOImpl extends GenericDAOImpl<SolutionAnswer, Long> implements SolutionAnswerDAO{
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<SolutionAnswer> getSolutionAnswers(long solutionId) {
		/*Session session = sessionFactory.getCurrentSession();
		String query = "from SolutionAnswer solutionAnswer where solutionAnswer.solution.solutionId:="+solutionId;
		//String query = "select * from idea_comment where idea_id = '"+ideaId+"' and parent_comment_id = 0 ORDER BY created_date Desc";
		return session.createQuery(query).addEntity(SolutionAnswer.class).list();*/
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SolutionAnswer.class,"solutionAnswer");
		criteria.add(Restrictions.eq("solutionAnswer.solution.solutionId", solutionId));
		criteria.addOrder(Order.desc("solutionAnswer.createdDate"));
		return criteria.list();
	}

	@Override
	public long getSoutionAnswerCount(long solutionId) {
		/*Session session = sessionFactory.getCurrentSession();
		String query = "select count(*) from idea_comment where idea_id ="+ideaId+" and parent_comment_id = 0";
		return ((Number)session.createSQLQuery(query).uniqueResult()).longValue();*/
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SolutionAnswer.class,"solutionAnswer");
		criteria.add(Restrictions.eq("solutionAnswer.solution.solutionId", solutionId));
		Integer totalCount = criteria.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		return totalCount;
	}

	@Override
	public List<SolutionAnswer> getSolutionAnswers(long start, long recordsize,long solutionId) {
		List<SolutionAnswer> answers = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SolutionAnswer.class,"solutionAnswer");
		criteria.add(Restrictions.eq("solutionAnswer.solution.solutionId", solutionId));
		long a = 0;	
		if(recordsize != 1){
			answers = criteria.list();
			Collections.sort(answers , answerDESCComparator);
			if(answers.size() < (start +recordsize)){
				a = answers.size();
			}else{
				a = start + recordsize;
			}
			answers = answers.subList((int)start,(int)a);
		}else{
			criteria.addOrder(Order.desc("solutionAnswer.createdDate"));
			criteria.setFirstResult((int)start);
			criteria.setMaxResults((int)recordsize);
			answers = criteria.list();
		}
		return answers;
	}
 private static Comparator<SolutionAnswer> answerDESCComparator = null;
	 
	 static {
		 answerDESCComparator = new Comparator<SolutionAnswer>() {
			   public int compare(SolutionAnswer a, SolutionAnswer b) {
				   
				   return (a.getSolutionAnswerVotes().size() > b.getSolutionAnswerVotes().size()) ? -1: (a.getSolutionAnswerVotes().size() < b.getSolutionAnswerVotes().size()) ? 1:0 ;
			   }
			 };
	 } 

	

}
