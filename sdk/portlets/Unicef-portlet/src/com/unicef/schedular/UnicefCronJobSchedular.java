package com.unicef.schedular;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.unicef.util.IdeaUtil;
import com.unicef.util.SolutionUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
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
