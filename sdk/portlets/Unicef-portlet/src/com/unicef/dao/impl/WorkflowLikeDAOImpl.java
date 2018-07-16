package com.unicef.dao.impl;

import com.unicef.dao.WorkflowLikeDAO;
import com.unicef.domain.WorkflowLike;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class WorkflowLikeDAOImpl extends GenericDAOImpl<WorkflowLike, Long> implements WorkflowLikeDAO{
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public boolean checkWorkflowLike(long taskId, long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Select like_id from social_workflow_like where task_id="+taskId+" and user_id="+userId);
		if(query.list().isEmpty()){
			return false;
		}else{
			return true;	
		}
	}

	@Override
	public WorkflowLike getWorkflowLike(long userId, long taskId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select * from social_workflow_like where task_id="+taskId+" and user_id="+userId;
		return (WorkflowLike)session.createSQLQuery(query).addEntity(WorkflowLike.class).uniqueResult();
	}

	@Override
	public long getLikeCountByTaskId(long taskId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from social_workflow_like where task_id="+taskId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

}
