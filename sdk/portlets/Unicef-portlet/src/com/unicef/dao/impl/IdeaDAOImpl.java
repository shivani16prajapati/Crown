package com.unicef.dao.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.unicef.dao.IdeaDAO;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.IdeaScrumDiscussion;
import com.unicef.util.IdeaConstant;
import com.unicef.util.IdeaEnum;

/**
 * <p>
 * This class is a implementation of IdeaDAO and has methods to access idea
 * table.
 * </p>
 * 
 * @author Divyang Patel
 */
@Transactional
@Repository
public class IdeaDAOImpl extends GenericDAOImpl<Idea, Long> implements IdeaDAO {

	private static final Log _log = LogFactoryUtil.getLog(IdeaDAOImpl.class);

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public long getIdeaAttachementCountByIdeaId(Long ideaId, double ideaVersions) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(idea_attachement_id) from idea_attachements where idea_id="
				+ ideaId + " and idea_version=" + ideaVersions;
		return ((Number) session.createSQLQuery(query).uniqueResult())
				.longValue();
	}

	@Override
	public void deleteIdeaAttachementByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(
				"delete from idea_attachements where idea_id=" + ideaId + "")
				.executeUpdate();
	}

	@Override
	public List<IdeaAttachement> getIdeaAttachementByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<IdeaAttachement>) session
				.createSQLQuery(
						"Select * from idea_attachements where idea_id="
								+ ideaId).addEntity(IdeaAttachement.class)
				.list();
	}

	@Override
	public Long noofIdeas() {
		Long noofIdeas = 0l;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.setProjection(Projections.rowCount());
		noofIdeas = ((Long) criteria.list().get(0)).longValue();
		return noofIdeas;
	}

	@Override
	public List<Idea> getListOfIdea(long start, int recordsSize, String fieldname, String orderBy) {
		List<Idea> idealist = null;
		String flName = StringPool.BLANK;
		if (fieldname.equals("Idea")) {
			flName = "ideaTitle";
		} else if (fieldname.equals("Created")) {
			flName = "submissionDate";
		} else if (fieldname.equals("Last Updated")) {
			flName = "modifiedDate";
		} else if (fieldname.equals("Idea Type")) {
			flName = "innovationType";
		} else if (fieldname.equals("Hotness")) {
			flName = "hotWeight";
		} else if(fieldname.equals("UpVote")){
			flName = "modifiedDate";
		}
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		Order order = (orderBy.equals(IdeaConstant.ASCENDING) ? Order.asc(flName) : Order.desc(flName));
		criteria.addOrder(order);
		criteria.setFirstResult((int) start);
		criteria.setMaxResults(recordsSize);
		idealist = criteria.list();
		if(fieldname.equals("UpVote")){
			if(orderBy.equals(IdeaConstant.ASCENDING)){
				Collections.sort(idealist , ideaASCComparator);
			}else{
				Collections.sort(idealist , ideaDESCComparator);
			}
		}
		return idealist;
	}
	@Override
	public void deleteIdeaLikesByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from idea_like where idea_id=" + ideaId + "")
				.executeUpdate();
	}
	@Override
	public void deleteIdeaCommentsByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(
				"delete from idea_comment where idea_id=" + ideaId + "")
				.executeUpdate();
	}
	@Override
	public void deleteIdeaViewByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(
				"delete from idea_view where idea_id=" + ideaId + "")
				.executeUpdate();
	}
	@Override
	public void deleteIdeaHistory(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(
				"delete from idea_history where idea_id=" + ideaId + "")
				.executeUpdate();
	}
	@Override
	public List<Idea> ideaList(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		List<Idea> idealist = criteria.list();
		return idealist;
	}
	@Override
	public void createIdeaScrum(IdeaScrum scrum) {
		sessionFactory.getCurrentSession().save(scrum);
	}
	@Override
	public IdeaScrum getIdeaScrum(long scopeGroupId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaScrum.class);
		criteria.add(Restrictions.eq("groupId", scopeGroupId));
		return criteria.list().size() > 0 ? (IdeaScrum)criteria.list().get(0) : null;
	}
	@Override
	public List<Idea> getAllIdeas(Date startDate, Date endDate) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.between("submissionDate", startDate, endDate));
		return criteria.list();
	}
	@Override
	public void deleteIdeaFromWorkflow(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(
				"delete from social_workflow where idea_id=" + ideaId + "")
				.executeUpdate();
	}
	@Override
	public void deleteWorkflowLikeFromTaskId(long taskId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from social_workflow_like where task_id="+taskId+"").executeUpdate();
	}

	@Override
	public void deleteWorkflowCommentFromTaskId(long taskId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from social_workflow_comment where task_id="+taskId+"").executeUpdate();
	}

	@Override
	public List<Idea> getAllHotIdeas() {
		Session session = sessionFactory.getCurrentSession();
		return (List<Idea>) session
				.createSQLQuery(
						"SELECT * FROM idea where idea_hot = 1 order by hot_weight desc;").
						addEntity(Idea.class)
				.list();
	}

	@Override
	public List<Idea> getLatestHotIdeas() {
		Calendar currentDate = Calendar.getInstance();
		Date endDate = currentDate.getTime();
		
		currentDate.add(Calendar.DATE, -7);
		Date startDate = currentDate.getTime();
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.eq("isIdeaHot", true));
		criteria.addOrder(Order.desc("hotWeight"));
		criteria.add(Restrictions.between("submissionDate", startDate, endDate));
		criteria.setFirstResult(0);
		criteria.setMaxResults(3);
		return criteria.list();
	}

	@Override
	public void deleteIdeaCommentVoteByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from idea_comment_vote where idea_id="+ideaId+"").executeUpdate();
		
	}
	 
	@Override
	public List<IdeaScrumDiscussion> getIdeaScrumDiscussion(long ideaScrumId, long ideaId, long sprintId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaScrumDiscussion.class);
		criteria.add(Restrictions.eq("ideaScrumId", ideaScrumId));
		criteria.add(Restrictions.eq("ideaId", ideaId));
		criteria.add(Restrictions.eq("sprintId", sprintId));
		criteria.addOrder(Order.asc("createdDate"));
		return criteria.list().size() > 0 ? (List<IdeaScrumDiscussion>)criteria.list() : null;
	}
	
	@Override
	public Long createIdeaScrumDiscussion(IdeaScrumDiscussion ideaScrumDiscussion) {
		Long scrumDiscussionId =  (Long) sessionFactory.getCurrentSession().save(ideaScrumDiscussion);
		return scrumDiscussionId;
	}
	
	@Override
	public void updateIdeaScrumDiscussion(IdeaScrumDiscussion ideaScrumDiscussion) {
		sessionFactory.getCurrentSession().saveOrUpdate(ideaScrumDiscussion);
	}
	
	@Override
	public long countTotalIdeas() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.setProjection(Projections.rowCount());
		return ((Number)(criteria.uniqueResult())).longValue();
	}
	
	@Override
	public long countLastWeekSubmitedIdeas() {
		Calendar currentDate = Calendar.getInstance();
		Date endDate = currentDate.getTime();
		currentDate.add(Calendar.DATE, -7);
		Date startDate = currentDate.getTime();
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.between("submissionDate", startDate, endDate));
		criteria.setProjection(Projections.rowCount());
		return ((Number)(criteria.uniqueResult())).longValue();
	}
	
	@Override
	public long countLastWeekSubmitedIdeasByuser(long userId){
		Calendar currentDate = Calendar.getInstance();
		Date endDate = currentDate.getTime();
		currentDate.add(Calendar.DATE, -7);
		Date startDate = currentDate.getTime();
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.between("submissionDate", startDate, endDate));
		criteria.add(Restrictions.eq("coInventorId",userId));
		criteria.setProjection(Projections.rowCount());
		return ((Number)(criteria.uniqueResult())).longValue();
	}
	
	
	@Override
	public List<Idea> getListOfIdeaByCurrentUser(long userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.eq("coInventorId", userId));
		criteria.addOrder(Order.desc("modifiedDate"));
		return criteria.list();
	}
	
	@Override
	public IdeaScrum getIdeaScrumByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaScrum.class);
		criteria.add(Restrictions.eq("ideaId", ideaId));
		return criteria.list().size() > 0 ? (IdeaScrum)criteria.list().get(0) : null;
	}
	
	@Override
	public List<Idea> getListOfPromotedIdeas() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.eq("status", IdeaEnum.IDEA_SCRUM.getValue()));
		return criteria.list();
	}
	
	@Override
	public List<IdeaScrumDiscussion> getChildIdeaScrumDiscussion(long ideaScrumId, long ideaId, long sprintId, long parentDiscussionId, int level) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaScrumDiscussion.class);
		criteria.add(Restrictions.eq("ideaScrumId", ideaScrumId));
		criteria.add(Restrictions.eq("ideaId", ideaId));
		criteria.add(Restrictions.eq("sprintId", sprintId));
		criteria.add(Restrictions.eq("parentScrumDiscussionId", parentDiscussionId));
		criteria.add(Restrictions.eq("level", level));
		criteria.addOrder(Order.asc("createdDate"));
		return criteria.list().size() > 0 ? (List<IdeaScrumDiscussion>)criteria.list() : null;
	}
	
	@Override
	public IdeaScrumDiscussion getIdeaScrumDiscussion(long scrumDiscussionId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaScrumDiscussion.class);
		criteria.add(Restrictions.eq("scrumDiscussionId", scrumDiscussionId));
		return criteria.list().size() > 0 ? (IdeaScrumDiscussion)criteria.list().get(0) : null;
	}
	
	 private static Comparator<Idea> ideaASCComparator = null;
	 private static Comparator<Idea> ideaDESCComparator = null;
	 static {
		 ideaASCComparator = new Comparator<Idea>() {
		   public int compare(Idea a, Idea b) {
		    return (a.getLikes().size() < b.getLikes().size()) ? -1: (a.getLikes().size() > b.getLikes().size()) ? 1:0 ;
		   }
		 };
		 ideaDESCComparator = new Comparator<Idea>() {
			   public int compare(Idea a, Idea b) {
			    return (a.getLikes().size() > b.getLikes().size()) ? -1: (a.getLikes().size() < b.getLikes().size()) ? 1:0 ;
			   }
			 };
	 }
	@Override
	public IdeaScrum getIdeaScrumBySolutionId(long solutionId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IdeaScrum.class);
		criteria.add(Restrictions.eq("solutionId", solutionId));
		return criteria.list().size() > 0 ? (IdeaScrum)criteria.list().get(0) : null;
	}

	@Override
	public List<Idea> getSortedIdeaHomeList(int start, int recordsize) {
		List<Idea> ideaHomePageList = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		Order order = (Order.desc("submissionDate"));
		criteria.addOrder(order);
		criteria.setFirstResult( start);
		criteria.setMaxResults(recordsize);
		ideaHomePageList = criteria.list();		
		return ideaHomePageList;
	}
	
	
	@Override
	public List<Idea> getSortedByHotnessHomeList(int start, int recordsize) {
		List<Idea> ideaHomePageList = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		Order order = (Order.desc("hotWeight"));
		criteria.add(Restrictions.eq("isIdeaHot",true));
		criteria.addOrder(order);
		criteria.setFirstResult(start);
		criteria.setMaxResults(recordsize);
		ideaHomePageList = criteria.list();		
		return ideaHomePageList;
	}

	@Override
	public List<IdeaAttachement> getLatestIdeaAttachementByIdeaId(long ideaId) {

		Session session = sessionFactory.getCurrentSession();
		String query = "select * from idea_attachements where idea_id="+ideaId+" and idea_version=(select idea_version from idea where idea_id="+ideaId+") limit 1;";
		
		return (List<IdeaAttachement>) session
				.createSQLQuery(query).addEntity(IdeaAttachement.class)
				.list();
	}

	@Override
	public List<Idea> getIdeasOfLastYear() {
		Calendar lastYear= Calendar.getInstance();
		lastYear.add(Calendar.DAY_OF_YEAR, -365);
		Date lastYearDate = lastYear.getTime();
		
		Calendar currentYear = Calendar.getInstance();
		Date todayDate = currentYear.getTime();
		
		List<Idea> ideas = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.between("submissionDate", lastYearDate,todayDate));
		criteria.addOrder(Order.asc("submissionDate"));
		ideas =criteria.list();
		return ideas;
	}

	@Override
	public List<Idea> getIdeasByMonth(Date ideaCreatedDate) {
		Calendar monthFirstDate = Calendar.getInstance();
		monthFirstDate.setTime(ideaCreatedDate);
		monthFirstDate.set(monthFirstDate.DAY_OF_MONTH, 1);
		Date firstDateOfMonth = monthFirstDate.getTime();
		
		Calendar monthLastDate = Calendar.getInstance();
		monthLastDate.setTime(ideaCreatedDate);
		monthLastDate.set(monthLastDate.DAY_OF_MONTH, monthLastDate.getActualMaximum(monthLastDate.DAY_OF_MONTH));
		Date lastDateOfMonth = monthLastDate.getTime();
				
		
		List<Idea> currentMonthIdeas = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.between("submissionDate", firstDateOfMonth, lastDateOfMonth));
		criteria.addOrder(Order.asc("submissionDate"));
		currentMonthIdeas = criteria.list();
		return currentMonthIdeas;
		
	}

	@Override
	public List<Idea> getIdeasByDay(Date submissionDate) {
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(submissionDate);
		startTime.set(Calendar.HOUR_OF_DAY, 0);
		startTime.set(Calendar.MINUTE, 0);
		startTime.set(Calendar.SECOND, 0);
		startTime.set(Calendar.MILLISECOND, 0);
		Date startingTime = startTime.getTime();
		
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(startingTime);
		endTime.set(Calendar.HOUR_OF_DAY,23);
		endTime.set(Calendar.MINUTE, 59);
		endTime.set(Calendar.SECOND, 59);
		Date endingTime = endTime.getTime();
		
		List<Idea> currentDayIdeas = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.between("submissionDate", startingTime, endingTime));
		currentDayIdeas = criteria.list();
		return currentDayIdeas;
	}

	@Override
	public long getIdeaCountByStage(long categoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("ideaStage", categoryId));
		return (Long)criteria.uniqueResult();
	}

	@Override
	public List<Idea> getLeaderBoardHotIdeas() {
		Calendar lastYear= Calendar.getInstance();
		lastYear.add(Calendar.DAY_OF_YEAR, -365);
		Date lastYearDate = lastYear.getTime();
		
		Calendar currentYear = Calendar.getInstance();
		Date todayDate = currentYear.getTime();
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		criteria.add(Restrictions.eq("isIdeaHot", true));
		criteria.addOrder(Order.desc("hotWeight"));
		criteria.add(Restrictions.between("submissionDate", lastYearDate, todayDate));
		return criteria.list();
	}

	@Override
	public long countIdeasByType(long categoryId,String type) {
		 Session session = sessionFactory.getCurrentSession();
		  Criteria crit = session.createCriteria(Idea.class);
		  crit.setProjection(Projections.rowCount());
		  if(type.equalsIgnoreCase("innovation")){
			  crit.add( Restrictions.eq("innovationType", categoryId));
		  }else if(type.equalsIgnoreCase("edgeService")){
			  crit.add( Restrictions.eq("edgeServiceId", categoryId));
		  }else{
			  crit.add( Restrictions.eq("ideaCategory", categoryId));
		  }
		  return (Long) crit.uniqueResult(); 
	}
	
	@Override
	public LinkedHashMap<String, Integer> getAllIdeasSubmissionDate(){
		int freq=0;
		Idea idea;
		String monthName;
		List<String> monthList = new ArrayList<String>();
		LinkedHashMap<String, Integer> graphData = new LinkedHashMap<String, Integer>();
		graphData.put("January", 0);
		graphData.put("February", 0);
		graphData.put("March", 0);
		graphData.put("April", 0);
		graphData.put("May", 0);
		graphData.put("June", 0);
		graphData.put("July", 0);
		graphData.put("August", 0);
		graphData.put("September", 0);
		graphData.put("October", 0);
		graphData.put("November", 0);
		graphData.put("December", 0);
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Idea.class);
		List<Idea> ideaList = criteria.list();
		//List<Date> dateList = criteria.setProjection(Projections.property("submissionDate")).list();
		java.util.ListIterator<Idea> itr = ideaList.listIterator();
		while(itr.hasNext()){
			 idea = itr.next();
			Format formatter = new SimpleDateFormat("MMMM");
		    monthName = formatter.format(idea.getSubmissionDate());
		    graphData.put(monthName, graphData.get(monthName)+1);
		    /*if(!(monthList.contains(monthName))){
		    	monthList.add(monthName);
		    	 freq = Collections.frequency(monthList, monthName);
		    	 graphData.put(monthName,freq);
		    }
		    else{
		    	 graphData.put(monthName, graphData.get(monthName)+1);
		    }*/
		    //System.out.println("Month====="+monthName+"Frequency====="+freq);
		}
		return graphData;
	}

	@Override
	public long countByIdeaTypeandUserId(long ideaTypeId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from idea where innovation_type_id =" +ideaTypeId + " and coinventor_id ="+userId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public long countByIdeaLineofbusinessandUserId(long lofBussiness,
			long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from idea where line_of_bussiness_id =" +lofBussiness + " and coinventor_id ="+userId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public List<Idea> getideabyuser(long userId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Idea>) session
				.createSQLQuery(
						"Select * from idea where coinventor_id="
								+ userId).addEntity(Idea.class)
				.list();
	}

	@Override
	public float hotideaBymonthByuser(long userId, String date) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT IFNULL(SUM(hot_weight),0) FROM idea where coinventor_id = "+userId+" and submission_date like '"+date+"%'";
		return (float) ((Double)session.createSQLQuery(query.toString()).uniqueResult()).doubleValue();
	}

	@Override
	public List<Idea> getLeaderBoardIdea() {
		Session session = sessionFactory.getCurrentSession();
		return (List<Idea>) session
				.createSQLQuery("select * from idea where idea_id  In ( select a.idea_id From idea_comment a  , idea_like b Where a.idea_id = b.idea_id group by idea_id order by count(a.comment_id)+count(b.idea_like_id) ) limit 3").addEntity(Idea.class)
				.list();
	}

	@Override
	public List<Idea> getUnSubmittedIdeasInPython() {
		boolean ideas = true;
		Session session = sessionFactory.getCurrentSession();
		return (List<Idea>) session
				.createSQLQuery(
						"Select * from idea where is_submitted_api ="
								+ ideas).addEntity(Idea.class)
				.list();
	}
	
	@Override
	public long getIdeaCountByMonthWiseAndYear(String year) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from idea where submission_date like '"+year+"%'";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public long countByIdeaTypeByYear(String startdate,String enddate,long ideaTypeId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM idea where submission_date between '"+startdate+"' AND '"+enddate+"' and innovation_type_id="+ideaTypeId+";";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public long countByIdeaLineofbusinessByYear(String startdate,String enddate,long lofBussiness) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM idea where submission_date between '"+startdate+"' AND '"+enddate+"' and line_of_bussiness_id="+lofBussiness+";";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
		
	}

	@Override
	public long getAllIdeaByNewStage(long ideaStageId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from idea where new_stage = "+ideaStageId+"";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public long getHeadquaterIdeaBymonthwise(String headquater, String date) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM idea where coinventor_id In (SELECT user_id FROM user_data WHERE user_country = '"+headquater+"') and submission_date like '"+date+"%'";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public long getRegionalIdeaBymonthwise(String headquater, String date) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT count(*)-(SELECT count(*) FROM idea where coinventor_id In (SELECT user_id FROM user_data WHERE user_country = '"+headquater+"') and submission_date like '"+date+"%') AS count FROM idea where submission_date like '"+date+"%'";
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public Idea getIdeaByIdeaNam(String ideaName) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select * from idea where idea_title = '"+ideaName+"'";
		List<Idea> idea =  (List<Idea>)session.createSQLQuery(query.toString()).addEntity(Idea.class).list();
		System.out.println(idea.size());
		if(idea.size() >0){
			return idea.get(0);
		}else{
			return new Idea();
		}
	}

	@Override
	public List<Idea> gettoptenidea() {
		Session session = sessionFactory.getCurrentSession();
		return (List<Idea>) session
				.createSQLQuery("SELECT * FROM idea order by hot_weight desc limit 0,9").addEntity(Idea.class).list();
	}

	@Override
	public List<Idea> getideaHotnessByPagiantion(int start, int end) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Idea>) session
				.createSQLQuery("SELECT * FROM idea order by hot_weight desc limit "+start+","+end).addEntity(Idea.class).list();
	}

	
	
	
	
	 

}
