package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.UserFeedDAO;
import com.unicef.domain.UserFeed;
import com.unicef.service.UserFeedService;

@Service
public class UserFeedServiceImpl extends GenericServiceImpl<UserFeed,Long> implements UserFeedService{
	
	private UserFeedDAO userfeed;
	
	@Inject
	public void initUserFeedDAOFactory(UserFeedDAO userfeed) {
		this.userfeed = userfeed;
		setGenericDAOFactory(userfeed);
	}

	@Override
	public List<UserFeed> getUserFeed(long userId, int start, int end) {
		return userfeed.getUserFeed(userId, start, end);
	}

	@Override
	public long getUserFeedCount(long userId) {
		return userfeed.getUserFeedCount(userId);
	}

	@Override
	public void deletefeed(long typeId, long referId) {
	  userfeed.deletefeed(typeId,referId);
		
	}

	@Override
	public List<UserFeed> getuserfeedbyType(long typeId, long referId,
			long userId) {
		return userfeed.getuserfeedbyType(typeId, referId, userId);
	}

}
