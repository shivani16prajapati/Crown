package com.unicef.portlet.idea;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.functions.Value;
import org.springframework.context.ApplicationContext;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.sun.org.apache.bcel.internal.generic.FALOAD;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.service.IdeaCommentService;
import com.unicef.service.IdeaCommentVoteService;
import com.unicef.service.IdeaEndorsementService;
import com.unicef.service.IdeaService;
import com.unicef.service.LikeService;


//Author Mukesh

public class IdeaHotnessCron implements MessageListener{
	private static final Log log = LogFactoryUtil.getLog(IdeaHotnessCron.class);


	@Override
	public void receive(Message message) throws MessageListenerException {
		
		SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
		Date today = new Date();
		
		
		log.info(today +" Idea Hotness Scheduler called");
		
//		ApplicationContext use because spring security
		
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();		
		IdeaService ideaservice = (IdeaService) applicationContext.getBean("ideaServiceImpl");
		LikeService likeService = (LikeService) applicationContext.getBean("likeServiceImpl");
		IdeaCommentService ideaCommentService = (IdeaCommentService) applicationContext.getBean("ideaCommentServiceImpl");
		IdeaEndorsementService ideaEndorsementService = (IdeaEndorsementService) applicationContext.getBean("ideaEndorsementServiceImpl");
		IdeaCommentVoteService ideaCommentVoteService = (IdeaCommentVoteService) applicationContext.getBean("ideaCommentVoteServiceImpl");
		
		List<Idea> idea_list = ideaservice.ideaList();	
		
		List<Long> ideaupvotes = new ArrayList<Long>();
		List<Long> ideaendorsment = new ArrayList<Long>();
		List<Long> ideacomments = new ArrayList<Long>();
		List<Float> ideaHotness = new ArrayList<Float>();
		
		
		for(Idea idea : idea_list){	
			long upvote = likeService.getCountOfIdeaLike(idea.getIdeaId());
			long comment = ideaCommentService.getIdeaCommentCount(idea.getIdeaId());
			long endorsment = ideaEndorsementService.getIdeaEndorsementCount(idea.getIdeaId());
			ideaupvotes.add(upvote);
		    ideaendorsment.add(endorsment);
		    ideacomments.add(comment);
		}
		
		long ideaendorsmentBig = Collections.max(ideaendorsment);
		long ideaupvotesBig = Collections.max(ideaupvotes);
		long ideacommentsBig = Collections.max(ideacomments);
	
		for(Idea idea : idea_list){		
			long upvote = likeService.getCountOfIdeaLike(idea.getIdeaId());
			long comment = ideaCommentService.getIdeaCommentCount(idea.getIdeaId());
			long endorsment = ideaEndorsementService.getIdeaEndorsementCount(idea.getIdeaId());			
			Date ideadate = idea.getSubmissionDate();
			
			log.info("idea date "+ideadate);
			
			int diff_days = (int) ((today.getTime() - ideadate.getTime()) / (1000 * 60 * 60 * 24));			
			float ideaNewnessPoint = 0;	   		
			    
		    if(diff_days<10 && diff_days != 0){
		    	ideaNewnessPoint = (float) (diff_days * 0.1);	
		    }else if(diff_days == 0){
		    	ideaNewnessPoint = 1;
		    }else if(diff_days >10){
		    	ideaNewnessPoint = (float) 00.03;
		    }
		    
		    if (ideaendorsmentBig != 0){
		    	endorsment = endorsment/ideaendorsmentBig;
		    }
		    
		    if (ideaupvotesBig != 0){
		    	upvote = upvote/ideaupvotesBig;
		    }
		    
		    if(ideacommentsBig != 0){
		    	comment = comment/ideacommentsBig;
		    }
		    
			float hotness = (float)((0.4*endorsment) +(0.3*upvote) +(0.2*comment) + (0.1 * ideaNewnessPoint));			
			log.info(idea.getIdeaId() + " Hotness " + hotness);			
			ideaHotness.add(hotness);
			idea.setHotWeight(Double.parseDouble(String.valueOf(hotness)));
			ideaservice.update(idea);
			log.info(idea.getIdeaTitle() +" Is Hot :" + idea.isIdeaHot() + " Idea weight :"+idea.getHotWeight() );
			
		}
		
		float ideaHotnessBig = Collections.max(ideaHotness);
		
		
		for(Idea idea : idea_list){	
			float publicHotness = (float)(idea.getHotWeight()/ideaHotnessBig);
			double hotness = Double.parseDouble(new DecimalFormat("##.##").format(publicHotness));
			
			log.info(idea.getIdeaTitle() +" Is publicHotness :" + publicHotness + " Idea hotness :"+hotness );
			
			if(hotness > .97){
				hotness = 1;
			}if(hotness == 00.00){
				hotness = 0.01;
			}
			idea.setHotWeight(hotness);
			idea.setIdeaHot(true);
			ideaservice.update(idea);
			
			
			
		}	
		
		log.info("Idea Hotness Scheduler Complated");
		
	
		
	}

	
	

}
;