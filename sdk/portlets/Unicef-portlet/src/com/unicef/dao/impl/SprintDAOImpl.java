package com.unicef.dao.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.unicef.dao.SprintDAO;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.Sprint;
import com.unicef.util.SprintConstant;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * This class is a implementation of SprintDAO and has methods to access sprint
 * table.
 * </p>
 * 
 * @author Divyang Patel
 */
@Transactional
@Repository
public class SprintDAOImpl extends GenericDAOImpl<Sprint, Long> implements SprintDAO {

	private static final Log _log = LogFactoryUtil.getLog(SprintDAOImpl.class);

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<Sprint> sprintList(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		List<Sprint> sprintlist = criteria.list();
		return sprintlist;
	}

	@Override
	public List<Sprint> getListofSprint(long start, int recordsSize, String fieldname, String orderBy, long ideaId, long ideaScrumId) {
		List<Sprint> sprintlist = null;
		String flName = StringPool.BLANK;
		if (fieldname.equals("orderIndex")) {
			flName = "orderIndex";
		}else if(fieldname.equals("startDate")){
			flName = "startDate";
		}
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		Order order = (orderBy.equals(SprintConstant.ASCENDING) ? Order.asc(flName) : Order.desc(flName));
		criteria.add(Restrictions.eq("ideaId", ideaId));
		criteria.add(Restrictions.eq("ideaScrumId", ideaScrumId));
		criteria.addOrder(order);
		criteria.setFirstResult((int) start);
		criteria.setMaxResults(recordsSize);
		sprintlist = criteria.list();
		return sprintlist;
	}

	@Override
	public Long noofSprints(long ideaId) {
		Long noofSprints = 0l;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("ideaId", ideaId));
		criteria.setProjection(Projections.rowCount());
		noofSprints = ((Long) criteria.list().get(0)).longValue();
		return noofSprints;
	}
	
	@Override
	public List<Sprint> getSprintListByIdeaIdAndIdeaScrumId(long ideaId, long ideaScrumId){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("ideaId", ideaId));
		criteria.add(Restrictions.eq("ideaScrumId", ideaScrumId));
		Order order = Order.asc("orderIndex");
		criteria.addOrder(order);
		return criteria.list().size() > 0 ? (List<Sprint>)criteria.list() : null;
	}

	@Override
	public Sprint getSprintByOrderIndex(int orderIndex, long ideaId, long ideaScrumId,String type) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("orderIndex", orderIndex));
		if(type.equalsIgnoreCase("idea")){
			criteria.add(Restrictions.eq("ideaId", ideaId));
		}else if(type.equalsIgnoreCase("solution")){
			criteria.add(Restrictions.eq("solutionId", ideaId));
		}
		
		criteria.add(Restrictions.eq("ideaScrumId", ideaScrumId));
		return criteria.list().size() > 0 ? (Sprint)criteria.list().get(0) : null;
	}
	
	@Override
	public List<Sprint> getSprintsByOrderIndex(int orderIndex, long ideaId,
			long ideaScrumId, String type) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("orderIndex", orderIndex));
		if(type.equalsIgnoreCase("idea")){
			criteria.add(Restrictions.eq("ideaId", ideaId));
		}else if(type.equalsIgnoreCase("solution")){
			criteria.add(Restrictions.eq("solutionId", ideaId));
		}
		
		criteria.add(Restrictions.eq("ideaScrumId", ideaScrumId));
		
		return criteria.list().size() > 0 ? (List<Sprint>)criteria.list() : null;
	}
	
	@Override
	public List<Sprint> getSprintsByAfterOrderIndex(int orderIndex, long ideaId, long ideaScrumId){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.ge("orderIndex", orderIndex));
		criteria.add(Restrictions.eq("ideaId", ideaId));
		criteria.add(Restrictions.eq("ideaScrumId", ideaScrumId));
		Order order = Order.asc("orderIndex");
		criteria.addOrder(order);
		return criteria.list().size() > 0 ? (List<Sprint>)criteria.list() : null;
	}

	@Override
	public List<Sprint> getSprintsByIdeaId(long ideaId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("ideaId", ideaId));
		criteria.addOrder(Order.asc("startDate"));
		return criteria.list();
	}

	@Override
	public long getInventorAvtarFromSprintId(long sprintId) {
		Session session =sessionFactory.getCurrentSession();
		String query = "select inventor_id from sprint where sprint_id="+sprintId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public Sprint findNextSprintFromOrderIndex(int nextSprintOrder,
			long ideaId, long ideaScrumId) {
		Session session =sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("orderIndex", nextSprintOrder));
		criteria.add(Restrictions.eq("ideaId", ideaId));
		criteria.add(Restrictions.eq("ideaScrumId", ideaScrumId));
		return criteria.list().size() > 0 ? (Sprint)criteria.list().get(0) : null;
	}

	@Override
	public Sprint findPreviousPrintFromOrderIndex(int previousSprintOrder,
			long ideaId, long ideaScrumId) {
		Session session =sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("orderIndex", previousSprintOrder));
		criteria.add(Restrictions.eq("ideaId", ideaId));
		criteria.add(Restrictions.eq("ideaScrumId", ideaScrumId));
		return criteria.list().size() > 0 ? (Sprint)criteria.list().get(0) : null;
	}

	@Override
	public List<Sprint> getSprintListBySolutionIdAndSolutionScrumId(
			long solutionId, long groupId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("solutionId", solutionId));
		criteria.add(Restrictions.eq("ideaScrumId", groupId));
		Order order = Order.asc("orderIndex");
		criteria.addOrder(order);
		return criteria.list().size() > 0 ? (List<Sprint>)criteria.list() : null;
	}

	@Override
	public List<Sprint> getListofSolutionSprint(long start, int recordsSize,
			String fieldname, String orderBy, long solutionId,
			long solutionScrumId) {
	
		List<Sprint> sprintlist = null;
		String flName = StringPool.BLANK;
		if (fieldname.equals("orderIndex")) {
			flName = "orderIndex";
		}else if(fieldname.equals("startDate")){
			flName = "startDate";
		}
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		Order order = (orderBy.equals(SprintConstant.ASCENDING) ? Order.asc(flName) : Order.desc(flName));
		criteria.add(Restrictions.eq("solutionId", solutionId));
		criteria.add(Restrictions.eq("ideaScrumId", solutionScrumId));
		criteria.addOrder(order);
		criteria.setFirstResult((int) start);
		criteria.setMaxResults(recordsSize);
		sprintlist = criteria.list();
		return sprintlist;
	}

	@Override
	public List<Sprint> getSolutionSprintsByAfterOrderIndex(int orderIndex,
			long solutionId, long solutionScrumId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.ge("orderIndex", orderIndex));
		criteria.add(Restrictions.eq("solutionId", solutionId));
		criteria.add(Restrictions.eq("ideaScrumId", solutionScrumId));
		Order order = Order.asc("orderIndex");
		criteria.addOrder(order);
		return criteria.list().size() > 0 ? (List<Sprint>)criteria.list() : null;
	}

	@Override
	public Sprint findPreviousSolutionSPrintFromOrderIndex(
			int sPreviousSprintOrder, long solutionId, long solutionScrumId) {
		Session session =sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("orderIndex", sPreviousSprintOrder));
		criteria.add(Restrictions.eq("solutionId", solutionId));
		criteria.add(Restrictions.eq("ideaScrumId", solutionScrumId));
		return criteria.list().size() > 0 ? (Sprint)criteria.list().get(0) : null;
	}

	@Override
	public Sprint findSolutionNextSprintFromOrderIndex(int nextSprintOrder,
			long solutionId, long solutionScrumId) {
		Session session =sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sprint.class);
		criteria.add(Restrictions.eq("orderIndex", nextSprintOrder));
		criteria.add(Restrictions.eq("solutionId", solutionId));
		criteria.add(Restrictions.eq("ideaScrumId", solutionScrumId));
		return criteria.list().size() > 0 ? (Sprint)criteria.list().get(0) : null;
	}
	
}
