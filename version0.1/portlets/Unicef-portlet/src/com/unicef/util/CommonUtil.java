package com.unicef.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.unicef.constants.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil{
	
	private static Log log = LogFactoryUtil.getLog(CommonUtil.class);
	
	public static PortletURL createIdeaPortletURL(HttpServletRequest request, long currentPagePlid, long entryId, long groupId){
		PortletURL customPortletURL = null;
		try {
			Layout ideaLayout =  IdeaUtil.getIdeaMachineLayout(groupId);
			if(Validator.isNotNull(ideaLayout)){
				customPortletURL =  PortletURLFactoryUtil.create(request, Constants.IDEA_PORTLET_ID, ideaLayout.getPlid() , "RENDER_PHASE");
			}else{
				customPortletURL =  PortletURLFactoryUtil.create(request, Constants.IDEA_PORTLET_ID, currentPagePlid , "RENDER_PHASE");
			}
			customPortletURL.setWindowState(WindowState.NORMAL);
			customPortletURL.setPortletMode(PortletMode.VIEW);
			customPortletURL.setParameter("ideaId",String.valueOf(entryId));
			customPortletURL.setParameter("view","viewIdea");
		} catch (Exception e) {
			log.error("Error in creating idea portlet URL:"+e.getMessage());
		}
		return customPortletURL;
	}
	
	public static PortletURL createSolutionPortletURL(HttpServletRequest request, long currentPagePlid, long entryId, long groupId){
		PortletURL customPortletURL = null;
		try {
			Layout ideaLayout =  SolutionUtil.getSolutionGeneratorLayout(groupId);
			if(Validator.isNotNull(ideaLayout)){
				customPortletURL =  PortletURLFactoryUtil.create(request, Constants.SOLUTION_PORTLET_ID, ideaLayout.getPlid() , "RENDER_PHASE");
			}else{
				customPortletURL =  PortletURLFactoryUtil.create(request, Constants.SOLUTION_PORTLET_ID, currentPagePlid , "RENDER_PHASE");
			}
			customPortletURL.setWindowState(WindowState.NORMAL);
			customPortletURL.setPortletMode(PortletMode.VIEW);
			customPortletURL.setParameter("solutionId",String.valueOf(entryId));
			customPortletURL.setParameter("view","viewSolution");
		} catch (Exception e) {
			log.error("Error in creating solution portlet URL:"+e.getMessage());
		}
		return customPortletURL;
	}
	
	public static boolean getDateInRange(Date fromDate, Date toDate, Date d){
		return d.compareTo(fromDate) >= 0 && d.compareTo(toDate) <= 0;
	}
	
	 public static boolean getArchivedStatusOfChallenge(Date todayDate,Date startDate,Date endDate){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String chStartDate = sdf.format(startDate);
			String chEndDate = sdf.format(endDate);
			String tDate = sdf.format(todayDate);
		  if(tDate.compareTo(chEndDate) > 0){
			  return true;
		  }
		return false;
	 }
	 public static boolean getActiveStatusOfChallenge(Date todayDate,Date startDate,Date endDate){
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		    String chStartDate = sdf.format(startDate);
			String chEndDate = sdf.format(endDate);
			String tDate = sdf.format(todayDate);
			  if((tDate.compareTo(chStartDate) ==  0) || (tDate.compareTo(chStartDate) > 0 && tDate.compareTo(chEndDate ) < 0) ){
				  return true;
			  }
		return false;
	 }
}
