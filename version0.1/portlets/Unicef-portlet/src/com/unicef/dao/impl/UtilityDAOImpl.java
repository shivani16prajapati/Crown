package com.unicef.dao.impl;

import com.liferay.portal.kernel.transaction.Transactional;
import com.unicef.dao.UtilityDAO;
import com.unicef.domain.Utility;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class UtilityDAOImpl extends GenericDAOImpl<Utility, Long> implements UtilityDAO{

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	
}
