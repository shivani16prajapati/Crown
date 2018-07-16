package com.unicef.portlet.decisionmatrix;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.unicef.domain.Idea;
import com.unicef.domain.Utility;
import com.unicef.service.IdeaRateService;
import com.unicef.service.IdeaService;
import com.unicef.service.UtilityService;
import com.unicef.util.UtilityConstant;


/**
 * Portlet implementation class Decisionmatrix
 */
@Controller(value = "Decisionmatrix")
@RequestMapping("VIEW")

public class Decisionmatrix {
	
	@Autowired
	private IdeaRateService ideaRateService;

	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private IdeaService ideaService;
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request,
			RenderResponse response) {		
		Map<String,Map<String, Double>> votingAverage = ideaRateService.getVotingAverageforIdeaInIdeaScrum();
	
		
		request.setAttribute("votingAverage", votingAverage);
		
		return "view";
	}
	
	@ResourceMapping(value = "resetRequest")
	public void resetRequest(ResourceRequest resourceRequest,ResourceResponse resourceResponse){
	try{
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Utility utility = new Utility();
		utility = utilityService.find(themeDisplay.getUserId());
		_log.info("at ajax call");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Technical_Merit", utility.getTechnical_Merit());
		jsonObject.put("Market_Opportunity", utility.getMarket_Opportunity());
		jsonObject.put("Strategic_Alignment", utility.getStrategic_Alignment());
		jsonObject.put("Time_To_Market", utility.getTime_To_Market());
		resourceResponse.getWriter().write(jsonObject.toJSONString());
	}catch(Exception e){
		e.printStackTrace();
		_log.info("Error in response data to ajax call");
	}
	}
	
	@ResourceMapping(value = "saveRequest")
	public void saveRequest(ResourceRequest resourceRequest,ResourceResponse resourceResponse) throws IOException{
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			SessionErrors.add(resourceRequest, UtilityConstant.ERROR_UTILITY_LOG_IN);
			return;
		}
		_log.info("at the method:");
		Utility utility = new Utility();
		long user_id = themeDisplay.getUserId();
		
		double Technical_Merit = ParamUtil.getDouble(resourceRequest,"Technical_Merit");
		double Market_Opportunity = ParamUtil.getDouble(resourceRequest,"Market_Opportunity");
		double Strategic_Alignment = ParamUtil.getDouble(resourceRequest,"Strategic_Alignment");
		double Time_To_Market = ParamUtil.getDouble(resourceRequest,"Time_To_Market");
		
		utility.setTechnical_Merit(Technical_Merit);
		utility.setMarket_Opportunity(Market_Opportunity);
		utility.setStrategic_Alignment(Strategic_Alignment);
		utility.setTime_To_Market(Time_To_Market);
		utility.setUser_id(user_id);
		utility.setSaveDate(new Date());
		_log.info(utility.toString());
		Utility utility1 = utilityService.find(user_id);
		if(utility1==null){
			_log.info("No data found in Utility");
			utilityService.create(utility);
		}
		else{
			_log.info("data found");
			utilityService.update(utility);
		}
		_log.info("data saved successfully");
		resourceResponse.getWriter().write("done");
	}
	
	private static final Log _log = LogFactoryUtil.getLog(Decisionmatrix.class.getName());
}
