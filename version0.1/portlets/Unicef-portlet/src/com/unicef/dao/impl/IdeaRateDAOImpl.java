package com.unicef.dao.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.unicef.dao.IdeaRateDAO;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaRate;
import com.unicef.domain.IdeaRateCriteria;
import com.unicef.domain.IdeaScrum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class IdeaRateDAOImpl extends GenericDAOImpl<IdeaRate, Long> implements IdeaRateDAO {

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<IdeaRateCriteria> getAllIdeaRateCriterias() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaRateCriteria.class);
		return criteria.list();
	}

	@Override
	public List<IdeaRate> getRateCriteriasbyUser(long userId, long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaRate.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("idea.ideaId", ideaId));
		return criteria.list();
	}

	@Override
	public IdeaRate findByUserIdIdeaIdAndCriteriaId(long userId, long ideaId, long ideaRateCriteriaId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaRate.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("idea.ideaId", ideaId));
		criteria.add(Restrictions.eq("ideaRateCriteria.ideaRateCriteriaId", ideaRateCriteriaId));
		return criteria.list().size() > 0 ? (IdeaRate)criteria.list().get(0) : null;
	}

	@Override
	public IdeaRateCriteria getIdeaRateCriteriaByCriteriaId(long ideaRateCriteriaId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaRateCriteria.class);
		criteria.add(Restrictions.eq("ideaRateCriteriaId", ideaRateCriteriaId));
		return (IdeaRateCriteria)criteria.list().get(0);
	}

	@Override
	public Map<IdeaRateCriteria, List<Double>> findStandardDeviationANDAvergeRatingOfIdea(Idea idea) {
		Map<IdeaRateCriteria, List<Double>> resultMap = new HashMap<IdeaRateCriteria, List<Double>>();
		List<IdeaRateCriteria> ideaRateCriterias = sessionFactory.getCurrentSession().createCriteria(IdeaRateCriteria.class).list();
		for (IdeaRateCriteria ideaRateCriteria : ideaRateCriterias) {
		    	List<IdeaRate> ideaRates = getIdeaRateByIdeaCriteriaId(ideaRateCriteria);
		    	int count = 0;
				double val[] = new double[ideaRates.size()];
		    	for (IdeaRate ideaRate : ideaRates) {
		    		val[count] = ideaRate.getRateValue();
					count++;
				}
		    	List<Double> valSet = new ArrayList<Double>();
		    	valSet.add(findAverge(val));
			    valSet.add(findStandardDeviation(val));
			    resultMap.put(ideaRateCriteria, valSet);
			    ideaRates.clear();
		}
		return resultMap;
	}

	//find standard deviation of numbers
	private static double findAverge(double[] values){
		double sum=0;
		double finalsum = 0;
		double average = 0;
		for( double i : values) {
			finalsum =   (sum += i);
		 }
		 average = finalsum/(values.length);
		 average = Math.round(average * 100);
		 average = average / 100;
		return average;
	 }
    
	//find standard deviation of numbers
	private static double findStandardDeviation(double[] values){
		double sum=0;
	    double finalsum = 0;
	    double average = 0;
	    for( double i : values) {
	        finalsum =   (sum += i);
	     }
	    average = finalsum/(values.length);
	    double sumX=0;
	    double finalsumX=0;
	    double[] x1_average = new double[2000];
	    for (int i = 0; i<values.length; i++){
	    	double fvalue = (Math.pow((values[i] - average), 2));
	        x1_average[i]= fvalue;
	     }
	     for( double i : x1_average) {
	    	 finalsumX =   (sumX += i);
	       }
	     Double AverageX = finalsumX/(values.length - 1);
	     double SquareRoot = Math.sqrt(AverageX);
	     SquareRoot = Math.round(SquareRoot * 100);
	     SquareRoot = SquareRoot / 100;
	     return SquareRoot;
	}

	@Override
	public Map<String,Double> getIdeaRateByIdeaCriteriaName(String criteria_Name) {
		Session session = sessionFactory.getCurrentSession();
		
		//fetch criteria id from criteria name
		Criteria criteria1 = session.createCriteria(IdeaRateCriteria.class);
		criteria1.add(Restrictions.eq("ideaRateCriteriaName",criteria_Name));
		criteria1.setProjection(Projections.property("ideaRateCriteriaId"));
		long criteria_id = (Long)criteria1.uniqueResult();
		_log.info("Criteria_id------------"+criteria_id);
		long idea_id;
		String idea_name;
		
		//calculate voting average on criteria basis for each idea
		Map<String,Double> votingAverage = new HashMap<String,Double >();
		Criteria criteria2 = session.createCriteria(IdeaScrum.class);
		criteria2.setProjection(Projections.distinct(Projections.property("ideaId")));
		//Criteria criteria2 = session.createCriteria(Idea.class);s
		Iterator itr = (criteria2.list()).iterator();
		while(itr.hasNext()){
			idea_id = (Long)itr.next();
			//_log.info("idea_id---------"+idea_id);
			Criteria criteria3 = session.createCriteria(Idea.class);
			criteria3.add(Restrictions.eq("ideaId", idea_id));
			criteria3.setProjection(Projections.property("ideaTitle"));
			idea_name = (String)criteria3.uniqueResult();
			//_log.info("----idea_name-------"+idea_name);
			Criteria criteria4 = session.createCriteria(IdeaRate.class);
			criteria4.add(Restrictions.eq("idea.ideaId",idea_id));
			criteria4.add(Restrictions.eq("ideaRateCriteria.ideaRateCriteriaId", criteria_id));
			criteria4.setProjection(Projections.avg("rateValue"));
			if(criteria4.uniqueResult()!=null){
				//_log.info("------average--------"+(Double)criteria4.uniqueResult());
				votingAverage.put(idea_name, ((Double)criteria4.uniqueResult()));
			}
			else{
				//_log.info("------average--------2.5-----null");
				votingAverage.put(idea_name, 2.5);
			}
		}
		return votingAverage;
	}
	

	public List<IdeaRate> getIdeaRateByIdeaCriteriaId(IdeaRateCriteria ideaRateCriteria) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaRate.class);
		criteria.add(Restrictions.eq("ideaRateCriteria.ideaRateCriteriaId", ideaRateCriteria.getIdeaRateCriteriaId()));
		return criteria.list();
	}
	
	@Override
	public Map<String,Map<String, Double>> getVotingAverageforIdeaInIdeaScrum() {
		
		Map<String,Map<String, Double>> votingAverage = new HashMap<String, Map<String,Double>>();
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria2 = session.createCriteria(Idea.class);
		criteria2.setProjection(Projections.distinct(Projections.property("ideaId")));
		Iterator itr1 = (criteria2.list()).iterator();
		
		while(itr1.hasNext()){
			Map<String,Double> criteria_vote_average = new HashMap<String, Double>();
			
	        Criteria criteria1 = session.createCriteria(IdeaRateCriteria.class);
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.property("ideaRateCriteriaId"));
			projectionList.add(Projections.property("ideaRateCriteriaName"));
			criteria1.setProjection(Projections.distinct(projectionList));
			
			Iterator itr = (criteria1.list()).iterator();
			long idea_id = (Long)itr1.next();
			//_log.info("Idea id ------------:"+idea_id);
			
			Criteria criteria3 = session.createCriteria(Idea.class);
			criteria3.add(Restrictions.eq("ideaId",idea_id));
			criteria3.setProjection(Projections.property("ideaTitle"));
			String idea_name = (String)criteria3.uniqueResult();
			
			//_log.info("Idea Name ------------:"+idea_name);
			while(itr.hasNext()){
				
				Object obj[] = (Object[])itr.next();
				
				String criteria_Name = (String)obj[1];
				long criteria_id = (Long)obj[0];
				
				//_log.info("Id of ideaRateCriteriaId:"+criteria_id);
				//_log.info("Name of ideaRateCriteriaName"+criteria_Name);
				
				Criteria criteria4 = session.createCriteria(IdeaRate.class);
				criteria4.add(Restrictions.eq("idea.ideaId",idea_id));
				criteria4.add(Restrictions.eq("ideaRateCriteria.ideaRateCriteriaId", criteria_id));
				criteria4.setProjection(Projections.avg("rateValue"));
			
//				String query = "Select count(*) from idea_like where idea_id="+idea_id;
//				long like = ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
//				
//				query = "select count(*) from idea_comment where idea_id ="+ idea_id+" and parent_comment_id = 0";
//				long comment =  ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
//				
//			    query = "select count(*) from idea_endorsment where idea_id ="+idea_id;
//				long endorsement = ((Number)session.createSQLQuery(query).uniqueResult()).longValue();
				
				
				String query = " SELECT hot_weight FROM idea where idea_id="+idea_id;
				double hotneess = ((Double)session.createSQLQuery(query.toString()).uniqueResult()).doubleValue();
				
				Double total = (double) ( hotneess ) ;
				
/*//				_log.info(like +"+"+comment+"+"+ endorsement +"="+total);
*/				
				if(total > 0){
					//_log.info("------average--------"+(Double)criteria4.uniqueResult());
					criteria_vote_average.put(criteria_Name, (Double)total);
				}
				else{
					//_log.info("------average--------2.5-----null");
					criteria_vote_average.put(criteria_Name, 2.5);
				}
				
			}
			idea_name = idea_name+"_@_"+idea_id;
			votingAverage.put(idea_name, criteria_vote_average);
		}
		
		return votingAverage;
	}
	
	private static final Log _log = LogFactoryUtil.getLog(IdeaRateDAOImpl.class.getName());

}
