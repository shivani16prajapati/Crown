package com.unicef.dao.impl;

import com.unicef.dao.SocialWorkflowDAO;
import com.unicef.domain.SocialWorkflow;
import java.util.List;
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
public class SocialWorkflowDAOImpl extends GenericDAOImpl<SocialWorkflow, Long> implements SocialWorkflowDAO {
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@Override
	public long getNextSocialWorkflowTaskId() {
		Session session = sessionFactory.openSession();
		Long socialWorkflowTaskId = (Long) session.createQuery("select taskId from SocialWorkflow order by taskId DESC LIMIT 1").list().get(0);
		Long nextSocialWorkflowTaskId = socialWorkflowTaskId + 1;
		session.close();
		sessionFactory.close();
		return nextSocialWorkflowTaskId;
	}
	
	@Override
	public List<SocialWorkflow> getSortedIdeaList(long nextSocialWorkflowTaskId) {
		Session session = sessionFactory.openSession();
		List<SocialWorkflow> socialWorkflowTasks = session.createQuery("from SocialWorkflow socialWorkflow LEFT JOIN FETCH socialWorkflow.idea where socialWorkflow.taskId < "+nextSocialWorkflowTaskId+"order by socialWorkflow.taskId desc").setFirstResult(0).setMaxResults(2).list();
		session.close();
		sessionFactory.close();
		return socialWorkflowTasks;
	}

	@Override
	public List<SocialWorkflow> getSortedWorkflowList(int start,
			int recordsize) {
		List<SocialWorkflow> workflow = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SocialWorkflow.class);
		Order order = (Order.desc("createdDate"));
		criteria.addOrder(order);
		criteria.setFirstResult( start);
		criteria.setMaxResults(recordsize);
		workflow = criteria.list();		
		return workflow;
	}

	@Override
	public List<SocialWorkflow> getWorkflowList() {
		List<SocialWorkflow> workflow = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SocialWorkflow.class);
		workflow = criteria.list();
		return workflow;
	}

	@Override
	public SocialWorkflow findWorkflowFromIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SocialWorkflow.class);
		criteria.add(Restrictions.eq("idea.ideaId", ideaId));
		return criteria.list().size() > 0 ? (SocialWorkflow)criteria.list().get(0) :  null;
	}

	@Override
	public SocialWorkflow findWorkflowFromSolutionId(Long solutionId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SocialWorkflow.class);
		criteria.add(Restrictions.eq("solution.solutionId", solutionId));
		return criteria.list().size() > 0 ? (SocialWorkflow)criteria.list().get(0) : null;
	}
}
