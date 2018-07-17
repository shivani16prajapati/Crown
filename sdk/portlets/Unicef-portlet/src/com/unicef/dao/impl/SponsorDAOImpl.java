package com.unicef.dao.impl;

import com.unicef.dao.SponsorDAO;
import com.unicef.domain.Sponsor;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class SponsorDAOImpl extends GenericDAOImpl<Sponsor, Long> implements SponsorDAO {
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public Sponsor findSponsorByUserId(long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Sponsor.class);
		criteria.add(Restrictions.eq("sponsorId", id));
		return (Sponsor)criteria.uniqueResult();
	}

	@Override
	public void deleteFromMappingTable(long sponsorId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(
				"delete from launch_a_challenge_challenge_sponsor where sponsors_id=" + sponsorId + "")
				.executeUpdate();
	}

	
}
