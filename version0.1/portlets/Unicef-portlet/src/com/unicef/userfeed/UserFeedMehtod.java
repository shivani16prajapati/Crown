package com.unicef.userfeed;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unicef.domain.UserFeed;
import com.unicef.service.UserFeedService;

@Component
public class UserFeedMehtod {
	
	@Autowired
	private UserFeedService userFeedService;
	
	
	public void adduserfeed(long feedtype,long userId,long referId,long byuserId){
		UserFeed userfeed = new UserFeed();
		userfeed.setCreatedDate(new Date());
		userfeed.setUserId(userId);
		userfeed.setReferId(referId);
		userfeed.setByuserId(byuserId);
		userfeed.setFeedtypeId(feedtype);
		userFeedService.create(userfeed);
	}
	

	public List<UserFeed> getuserfeed(long userId,int start,int end){
		 long length = userFeedService.getUserFeedCount(userId);
		 if(end<length){
			 end = (int) length;
		 }
		 List<UserFeed> userfeed = userFeedService.getUserFeed(userId, start, end);
		return userfeed;
   }
	
	
	
	


}

