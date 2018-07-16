package com.unicef.portlet.idea;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.domain.UserFeed;
import com.unicef.service.IdeaCommentService;
import com.unicef.service.IdeaCommentVoteService;
import com.unicef.service.IdeaEndorsementService;
import com.unicef.service.IdeaService;
import com.unicef.service.LikeService;
import com.unicef.service.UserFeedService;
import com.unicef.userfeed.UserFeedConstant;

public class Notification implements MessageListener{

	private boolean createNewFile;
	
	private static final Log log = LogFactoryUtil.getLog(Notification.class);

	@Override
	public void receive(Message arg0) throws MessageListenerException {
		log.info("Idea Notification Scheduler called");
		
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();		
		IdeaService ideaservice = (IdeaService) applicationContext.getBean("ideaServiceImpl");
		LikeService likeService = (LikeService) applicationContext.getBean("likeServiceImpl");
		IdeaCommentService ideaCommentService = (IdeaCommentService) applicationContext.getBean("ideaCommentServiceImpl");
		IdeaEndorsementService ideaEndorsementService = (IdeaEndorsementService) applicationContext.getBean("ideaEndorsementServiceImpl");
		IdeaCommentVoteService ideaCommentVoteService = (IdeaCommentVoteService) applicationContext.getBean("ideaCommentVoteServiceImpl");
		UserFeedService userFeedService = (UserFeedService) applicationContext.getBean("userFeedServiceImpl");
		
		List<Idea> idea_list = ideaservice.gettoptenidea();	

	
	    int count_idea  = 0;
	    int count_thank = 0;
		for(Idea idea : idea_list){
			
			List<UserFeed> _userfeed =  userFeedService.getuserfeedbyType(UserFeedConstant.TOP_5_HOTTEST_IDEA, idea.getIdeaId(),idea.getCoInventorId());
			
			boolean isNotified = true;
			
			if(_userfeed.size() != 0){	
				
				Date d1 = _userfeed.get(0).getCreatedDate();
				Date d2 = new Date();
				long diff = d2.getTime() - d1.getTime();
				long diffInDays = diff / (1000 * 60 * 60 * 24);
				
				if(diffInDays < 3){
					isNotified = false;
				}else{
					count_idea++;
				}	
		    }
			
			if(_userfeed.size() == 0){	
				isNotified = true;
				count_idea++;
			}
		    
			if(isNotified){
				UserFeed userfeed = new UserFeed();
				userfeed.setCreatedDate(new Date());
				userfeed.setUserId(idea.getCoInventorId());
				userfeed.setReferId(idea.getIdeaId());
				userfeed.setFeedtypeId(UserFeedConstant.TOP_5_HOTTEST_IDEA);
				userfeed.setByuserId(idea.getCoInventorId());
				userFeedService.create(userfeed);
			}		
		}
		List<IdeaCommentVote> userthankyoulist = ideaCommentVoteService.getmostthankedUser(0,4);
		
		for(IdeaCommentVote _IdeaCommentVote:userthankyoulist){
			
			List<UserFeed> _userfeed =  userFeedService.getuserfeedbyType(UserFeedConstant.Top_5_THANK_YOU_GETTER, _IdeaCommentVote.getUserId(),_IdeaCommentVote.getUserId());
			boolean isNotified = true;
			if(_userfeed.size() != 0){		
				Date d1 = _userfeed.get(0).getCreatedDate();
				Date d2 = new Date();
				long diff = d2.getTime() - d1.getTime();
				long diffInDays = diff / (1000 * 60 * 60 * 24);
				
				if(diffInDays < 3){
					isNotified = false;
				}else{
					count_thank++;
				}	
		    }
			
			if(_userfeed.size() == 0){	
				isNotified = true;
				count_thank++;
			}
			if(isNotified){
				try{
						UserFeed userfeed = new UserFeed();
						userfeed.setCreatedDate(new Date());
						userfeed.setUserId(_IdeaCommentVote.getUserId());
						userfeed.setReferId(_IdeaCommentVote.getUserId());
						userfeed.setFeedtypeId(UserFeedConstant.Top_5_THANK_YOU_GETTER);
						userfeed.setByuserId(_IdeaCommentVote.getUserId());
						userFeedService.create(userfeed);		
					}catch(Exception e){
				
				}
			}
			
		}
		
		if(count_idea != 0 ){
			UserFeed userfeed = new UserFeed();
			userfeed.setCreatedDate(new Date());
			userfeed.setUserId(0);
			userfeed.setReferId(count_idea);
			userfeed.setFeedtypeId(UserFeedConstant.NEW_IDEA_IN_HOTNESS);
			userfeed.setByuserId(0);
			userFeedService.create(userfeed);
			
		}
		
		if(count_thank != 0 ){
			UserFeed userfeed = new UserFeed();
			userfeed.setCreatedDate(new Date());
			userfeed.setUserId(0);
			userfeed.setReferId(count_thank);
			userfeed.setFeedtypeId(UserFeedConstant.NEW_THANK_YOU_GETTER);
			userfeed.setByuserId(0);
			userFeedService.create(userfeed);
			
		}
		
		log.info("Idea Notification Scheduler End");
	}

}
