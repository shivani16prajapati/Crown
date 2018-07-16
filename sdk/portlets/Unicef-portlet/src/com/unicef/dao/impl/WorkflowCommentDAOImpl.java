package com.unicef.dao.impl;

import com.unicef.dao.WorkflowCommentDAO;
import com.unicef.domain.WorkflowComment;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class WorkflowCommentDAOImpl extends GenericDAOImpl<WorkflowComment, Long> implements WorkflowCommentDAO{
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<WorkflowComment> getCommentFromTaskId(long taskId) {
		List<WorkflowComment> comments = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WorkflowComment.class);
		criteria.add(Restrictions.eq("socialWorkflow.taskId",taskId));
		criteria.addOrder(Order.desc("createdDate"));
		comments = criteria.list();
		return comments;
	}

	@Override
	public long getCommentCountByTaskId(long taskId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from social_workflow_comment where task_id="+taskId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public String calculateEpocTime(Date createdDate) {
		Long time = 0l;
		StringBuilder timesAgo = new StringBuilder();
	
			time = (Long)TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - createdDate.getTime());
			if(time >= 0 && time < 1){
				timesAgo.append("less than a minute ago");
			}else if(time >= 1 && time < 60){
				timesAgo.append(time+" minutes ago"); 
			}else if(time >= 60 && time < 1440){
				time = (Long)TimeUnit.MILLISECONDS.toHours(new Date().getTime() - createdDate.getTime());
				timesAgo.append(time+" hours ago"); 
			}else{
				time = (Long)TimeUnit.MILLISECONDS.toDays(new Date().getTime() - createdDate.getTime());
				timesAgo.append(time+" days ago"); 
			}
		
		return timesAgo.toString();
	}

}
