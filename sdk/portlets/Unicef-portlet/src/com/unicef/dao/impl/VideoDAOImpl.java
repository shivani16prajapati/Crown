package com.unicef.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.unicef.dao.VideoDAO;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.Solution;
import com.unicef.domain.Video;
import com.unicef.util.SolutionConstant;

@Transactional
@Repository
public class VideoDAOImpl extends GenericDAOImpl<Video, Long> implements VideoDAO{

	private static final Log _log = LogFactoryUtil.getLog(VideoDAOImpl.class);
	
	
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}


	@Override
	public void deleteVideoByVideoId(long videoId) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from video where video_id="+videoId+"").executeUpdate();
	}


	@Override
	public Video getVideoByVideoId(long videoId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Video.class);
		criteria.add(Restrictions.eq("videoId", videoId));
		return criteria.list().size() > 0 ? (Video)criteria.list().get(0) : null;
		
	}


	@Override
	public List<Video> getListofVideos(long start, int recordsSize,
			String fieldname, String orderBy) {
		
		 List<Video> videos = null;
		  String flName = StringPool.BLANK;
		  if(fieldname.equalsIgnoreCase("Video")){
			  flName = "videoName";
		  }else if(fieldname.equalsIgnoreCase("ExpireDate")){
			  flName = "expireDate";
		  }else if(fieldname.equalsIgnoreCase("FeedOrder")){
			  flName = "feeOrderPlacement";
		  }
		  Session session = sessionFactory.getCurrentSession();
		  Criteria criteria = session.createCriteria(Video.class);
		  Order order =(orderBy.equals(SolutionConstant.ASCENDING) ? Order.asc(flName) : Order.desc(flName));
		  criteria.addOrder(order);
		  criteria.setFirstResult((int)start);
		  criteria.setMaxResults(recordsSize);
		  videos = criteria.list();
		  
		  return videos;
	}
	
}
