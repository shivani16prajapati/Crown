package com.unicef.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.liferay.portal.kernel.transaction.Transactional;
import com.unicef.dao.UserFeedDAO;
import com.unicef.domain.UserFeed;

@Transactional
@Repository
public class UserFeedDAOImpl extends GenericDAOImpl<UserFeed,Long> implements UserFeedDAO {

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<UserFeed> getUserFeed(long userId,int start,int end) {
		Session session = sessionFactory.getCurrentSession();
		return (List<UserFeed>) session
				.createSQLQuery("Select * from user_feed where user_id=0 or user_id="+userId+" order by userfeed_id desc LIMIT 10 offset "+start).addEntity(UserFeed.class).list();
	}

	@Override
	public long getUserFeedCount(long userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select count(*) from user_feed where user_id=0 or user_id="+userId;
		return ((Number)session.createSQLQuery(query.toString()).uniqueResult()).longValue();
	}

	@Override
	public void deletefeed(long typeId, long referId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from user_feed where feedtype_id = "+typeId+" and refer_id= "+referId+"").executeUpdate();
	}

	@Override
	public List<UserFeed> getuserfeedbyType(long typeId, long referId,long userId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<UserFeed>) session
				.createSQLQuery("Select * from user_feed where feedtype_id = "+typeId+" and refer_id= "+referId+" and user_id ="+userId+" order by userfeed_id desc ").addEntity(UserFeed.class).list();
	}

}
