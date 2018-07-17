package com.unicef.dao.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.unicef.dao.SolutionDAO;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.Solution;
import com.unicef.domain.SolutionScrumDiscussion;
import com.unicef.service.SolutionAnswerVoteService;
import com.unicef.util.SolutionConstant;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class SolutionDAOImpl extends GenericDAOImpl<Solution, Long> implements SolutionDAO {
	@Autowired
	private SolutionAnswerVoteService voteService;
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	 public List<Solution> getListofSoultion(long start, int recordsSize, String fieldname, String orderBy) {
	  List<Solution> solutions = null;
	  String flName = StringPool.BLANK;
	  if(fieldname.equalsIgnoreCase("Request")){
		  flName = "solutionTitle";
	  }else if(fieldname.equalsIgnoreCase("Created")){
		  flName = "submissionDate";
	  }else if(fieldname.equalsIgnoreCase("Last Updated")){
		  flName = "modifiedDate";
	  }else if(fieldname.equalsIgnoreCase("Problem Space")){
		  flName = "spaceId";
	  }else if(fieldname.equalsIgnoreCase("UpVote")){
		  flName = "modifiedDate";
	  }
	  Session session = sessionFactory.getCurrentSession();
	  Criteria criteria = session.createCriteria(Solution.class);
	  Order order =(orderBy.equals(SolutionConstant.ASCENDING) ? Order.asc(flName) : Order.desc(flName));
	  criteria.addOrder(order);
	  criteria.setFirstResult((int)start);
	  criteria.setMaxResults(recordsSize);
	  solutions = criteria.list();
	  
		if(fieldname.equals("UpVote")){
			for (Solution solution : solutions) {
				solution.setWantCount(Integer.parseInt(String.valueOf(voteService.getWantAnswerCount(solution.getSolutionId()))));
			}
			if(orderBy.equals(SolutionConstant.ASCENDING)){
				Collections.sort(solutions , solutionASCComparator);
			}else{
				Collections.sort(solutions , solutionDESCComparator);
			}
		}
	  return solutions;
	 }
	
	@Override
	public void deleteSolutionAnswerBySolutionId(long solutionId) {
		  Session session = sessionFactory.getCurrentSession();
		  session.createSQLQuery("delete from solution_answer where solution_id="+solutionId+"").executeUpdate();
		
	}
	
	@Override
	 public void deleteSolutionLikesBySolutionId(long solutionId) {
	  Session session = sessionFactory.getCurrentSession();
	  session.createSQLQuery("delete from solution_like where solution_id="+solutionId+"").executeUpdate();
	 }

	 @Override
	 public void deleteSolutionCommentsBySolutionId(long solutionId) {
	  Session session = sessionFactory.getCurrentSession();
	  session.createSQLQuery("delete from solution_comment where solution_id="+solutionId+"").executeUpdate();
	 }

	 @Override
	 public void deleteSolutionViewBySolutionId(long solutionId) {
	  Session session = sessionFactory.getCurrentSession();
	  session.createSQLQuery("delete from solution_view where solution_id="+solutionId+"").executeUpdate();
	 }
	 
	 @Override
	 public Long noofSolutions() {
	  Long noofSolutions = 0l;
	  Session session = sessionFactory.getCurrentSession();
	  Criteria criteria = session.createCriteria(Solution.class);
	  criteria.setProjection(Projections.rowCount());
	  noofSolutions = ((Long)criteria.list().get(0)).longValue();
	  return noofSolutions;
	 }

	@Override
	public Long noOfSolution() {
		Long noofSolution = 0l;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Solution.class);
		criteria.setProjection(Projections.rowCount());
		noofSolution = ((Long)criteria.list().get(0)).longValue();
		return noofSolution;
	}

	@Override
	public void deleteSolutionAnswerVoteBySolutionId(long solutionId) {
		 Session session = sessionFactory.getCurrentSession();
		 session.createSQLQuery("delete from solution_answer_vote where solution_id="+solutionId).executeUpdate();
	}

	@Override
	public void deleteSolutionAnswerComment(long solutionId) {
		Session session = sessionFactory.getCurrentSession();
		 session.createSQLQuery("delete from solution_answer_comment where solution_id="+solutionId).executeUpdate();
	}
	
	@Override
	public List<Solution> solutionList(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Solution.class);
		List<Solution> solutionlist = criteria.list();
		return solutionlist;
	}
	
	 private static Comparator<Solution> solutionASCComparator = null;
	 private static Comparator<Solution> solutionDESCComparator = null;
	 
	 static {
		 solutionASCComparator = new Comparator<Solution>() {
		   public int compare(Solution a, Solution b) {
		    return (a.getWantCount() < b.getWantCount()) ? -1: (a.getWantCount() > b.getWantCount()) ? 1:0 ;
		   }
		 };
		 solutionDESCComparator = new Comparator<Solution>() {
			   public int compare(Solution a, Solution b) {
			    return (a.getWantCount() > b.getWantCount()) ? -1: (a.getWantCount() < b.getWantCount()) ? 1:0 ;
			   }
			 };
	 }

	@Override
	public void deleteSolutionByLikeFromWorkflow(long taskId) {
		 Session session = sessionFactory.getCurrentSession();
		 session.createSQLQuery("delete from social_workflow_like where task_id="+taskId).executeUpdate();
	}

	@Override
	public void deleteSolutionByCommentFromWorkflow(long taskId) {
		 Session session = sessionFactory.getCurrentSession();
		 session.createSQLQuery("delete from social_workflow_comment where task_id="+taskId).executeUpdate();		
	}

	@Override
	public void deleteSolutionFromWorkflow(long solutionId) {
		 Session session = sessionFactory.getCurrentSession();
		 session.createSQLQuery("delete from social_workflow where solution_id="+solutionId).executeUpdate();
	} 
	
	@Override
	public List<Solution> getAllSolutions(Date startDate, Date endDate) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Solution.class);
		criteria.add(Restrictions.between("submissionDate", startDate, endDate));
		return criteria.list();
	}

	@Override
	public long createSolutionScrumDiscussion(
			SolutionScrumDiscussion scrumDiscussion) {
		Long scrumDiscussionId =  (Long) sessionFactory.getCurrentSession().save(scrumDiscussion);
		return scrumDiscussionId;
	}

	@Override
	public List<SolutionScrumDiscussion> getChildSolutionScrumDiscussion(
			long solutionScrumId, long solutionId, long sprintId,
			long scrumDiscussionId, int level) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SolutionScrumDiscussion.class);
		criteria.add(Restrictions.eq("solutionScrumId", solutionScrumId));
		criteria.add(Restrictions.eq("solutionId", solutionId));
		criteria.add(Restrictions.eq("sprintId", sprintId));
		criteria.add(Restrictions.eq("parentScrumDiscussionId", scrumDiscussionId));
		criteria.add(Restrictions.eq("level", level));
		criteria.addOrder(Order.asc("createdDate"));
		return criteria.list().size() > 0 ? (List<SolutionScrumDiscussion>)criteria.list() : null;
	}

	@Override
	public SolutionScrumDiscussion getSolutionScrumDiscussion(
			long ideaScrumDiscussionId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SolutionScrumDiscussion.class);
		criteria.add(Restrictions.eq("scrumDiscussionId", ideaScrumDiscussionId));
		return criteria.list().size() > 0 ? (SolutionScrumDiscussion)criteria.list().get(0) : null;
	}

	@Override
	public void updateSolutionScrumDiscussion(
			SolutionScrumDiscussion parentDiscussion) {
		sessionFactory.getCurrentSession().saveOrUpdate(parentDiscussion);
	}

	@Override
	public IdeaScrum getIdeaScrum(long scopeGroupId) {
		Session session = sessionFactory.getCurrentSession();
		  Criteria criteria = session.createCriteria(IdeaScrum.class);
		  criteria.add(Restrictions.eq("groupId", scopeGroupId));
		  return criteria.list().size() > 0 ? (IdeaScrum)criteria.list().get(0) : null;
	}

	@Override
	public double countLastWeekSubmitedSolution() {
		  Calendar currentDate = Calendar.getInstance();
		  Date endDate = currentDate.getTime();
		  currentDate.add(Calendar.DATE, -7);
		  Date startDate = currentDate.getTime();
		  
		  Session session = sessionFactory.getCurrentSession();
		  Criteria criteria = session.createCriteria(Solution.class);
		  criteria.add(Restrictions.between("submissionDate", startDate, endDate));
		  criteria.setProjection(Projections.rowCount());
		  return ((Number)(criteria.uniqueResult())).longValue();
	}
	
	
}
