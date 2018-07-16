package com.unicef.dao;

import java.util.List;

import com.unicef.domain.UserFeed;


public interface UserFeedDAO extends GenericDAO<UserFeed,Long>{

	public List<UserFeed> getUserFeed(long userId,int start,int end);
	
	public long getUserFeedCount(long userId);
	
	public void deletefeed(long typeId,long referId);
	
	public List<UserFeed> getuserfeedbyType(long typeId,long referId,long userId);
	
}
