package com.unicef.schedular;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.unicef.util.IdeaUtil;
import com.unicef.util.SolutionUtil;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;

public class UnicefCronJobSchedular {
	private static final Log log = LogFactoryUtil.getLog(UnicefCronJobSchedular.class);
	
	//12 hours schedular
	//final String CRON_JOB_FORMAT = "0 0 0/12 1/1 * ?";
	
	//minute schedular
	final String CRON_JOB_FORMAT = "0 0/5 * * * ?";
	
	 @Scheduled(cron = CRON_JOB_FORMAT)
     public void doReceive(){
		log.info("Method executed at every 12 hours. Current time is :: "+ new Date());
        /*IdeaUtil.updateHotIdeaList();
        SolutionUtil.updateHotSolutionList();*/
     }
	 
}
