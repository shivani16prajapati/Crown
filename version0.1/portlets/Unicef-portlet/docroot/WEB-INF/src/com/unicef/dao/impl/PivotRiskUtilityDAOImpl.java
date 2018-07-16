package com.unicef.dao.impl;

import com.liferay.portal.kernel.transaction.Transactional;
import com.unicef.dao.PivotRiskUtilityDAO;
import com.unicef.domain.PivotRiskUtility;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class PivotRiskUtilityDAOImpl extends GenericDAOImpl<PivotRiskUtility, Long> implements PivotRiskUtilityDAO{

		@Inject
		public void initSessionFactory(SessionFactory sessionFactory) {
			setSessionFactory(sessionFactory);
		}
}
