package com.unicef.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.unicef.dao.UserDataDAO;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.Like;
import com.unicef.domain.UserData;

@Transactional
@Repository
public class UserDataDAOImpl extends GenericDAOImpl<UserData,Long> implements UserDataDAO{
	
	private static final Log _log = LogFactoryUtil.getLog(UserDataDAOImpl.class);

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

/*	@Override
	public UserData getuserData(long userId) {
		Session session = sessionFactory.getCurrentSession();
		return (UserData) session
				.createSQLQuery(
						"Select * from user_data where user_id="
								+ userId).setMaxResults(1).uniqueResult();
	 
	}
*/	
	@Override
	public UserData getuserData(long userId){
		Session session = sessionFactory.getCurrentSession();
		String query = "Select * from user_data where user_id="+userId;
		return (UserData)session.createSQLQuery(query).addEntity(UserData.class).uniqueResult();
	}

	
	@Override
	public List<UserData> getUserDataByIdeaUser() {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT * FROM user_data where user_id IN (SELECT coinventor_id FROM idea group by coinventor_id);";
		return (List<UserData>) session
				.createSQLQuery(query).addEntity(UserData.class)
				.list();
	}
	
}
