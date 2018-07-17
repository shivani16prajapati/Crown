package com.unicef.dao.impl;

import com.unicef.dao.ChallengeDAO;
import com.unicef.domain.LaunchAChallenge;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ChallengeDAOImpl extends GenericDAOImpl<LaunchAChallenge, Long> implements ChallengeDAO {

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<LaunchAChallenge> getListOfChalleneges(long start,
			int recordsSize) {
		List<LaunchAChallenge> challenges = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LaunchAChallenge.class);
		criteria.addOrder(Order.desc("challengeStartDate"));
		criteria.setFirstResult((int) start);
		criteria.setMaxResults(recordsSize);
		challenges = criteria.list();
		return challenges;
	}
}
