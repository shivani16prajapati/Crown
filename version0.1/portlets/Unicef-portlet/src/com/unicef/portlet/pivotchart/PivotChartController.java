package com.unicef.portlet.pivotchart;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.unicef.domain.PivotRiskUtility;
import com.unicef.service.IdeaRateService;
import com.unicef.service.PivotRiskUtilityService;
import com.unicef.service.UtilityService;
import com.unicef.util.UtilityConstant;

import java.util.Date;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@RequestMapping("VIEW")
public class PivotChartController{

	@Autowired
	private IdeaRateService ideaRateService;
	
	@Autowired
	private PivotRiskUtilityService pivotRiskUtilityService;
	
	@RenderMapping
	public String view(RenderRequest renderRequest,RenderResponse renderResponse, Model model) throws PortalException{
		Map<String,Map<String, Double>> votingAverage = ideaRateService.getVotingAverageforIdeaInIdeaScrum();
		renderRequest.setAttribute("votingAverage", votingAverage);
		return "view";
	}
	@ActionMapping
	public void saveRequest(ActionRequest actionRequest,ActionResponse actionResponse){
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			SessionErrors.add(actionRequest, UtilityConstant.ERROR_UTILITY_LOG_IN);
			return;
		}
		PivotRiskUtility pivotRiskUtility = new PivotRiskUtility();
		long user_id = themeDisplay.getUserId();
		double Technical_Merit = ParamUtil.getDouble(actionRequest, "Technical_Merit");
		double Market_Opportunity = ParamUtil.getDouble(actionRequest, "Market_Opportunity");
		double Strategic_Alignment = ParamUtil.getDouble(actionRequest, "Strategic_Alignment");
		double Time_To_Market = ParamUtil.getDouble(actionRequest, "Time_To_Market");
		double Risk_Slider = ParamUtil.getDouble(actionRequest, "Risk_Slider");
		double Utility_Slider = ParamUtil.getDouble(actionRequest, "Utility_Slider");
		_log.info("Technical_Merit"+Technical_Merit);
		_log.info("Market_Opportunity"+Market_Opportunity);
		_log.info("Strategic_Alignment"+Strategic_Alignment);
		_log.info("Time_To_Market"+Time_To_Market);
		_log.info("Risk_Slider"+Risk_Slider);
		_log.info("Utility_Slider"+Utility_Slider);
		pivotRiskUtility.setTechnical_Merit(Technical_Merit);
		pivotRiskUtility.setMarket_Opportunity(Market_Opportunity);
		pivotRiskUtility.setStrategic_Alignment(Strategic_Alignment);
		pivotRiskUtility.setTime_To_Market(Time_To_Market);
		pivotRiskUtility.setUser_id(user_id);
		pivotRiskUtility.setSaveDate(new Date());
		pivotRiskUtility.setRisk_Slider(Risk_Slider);
		pivotRiskUtility.setUtility_Slider(Utility_Slider);
		
		/*_log.info("Market_opportunity"+pivotRiskUtility.getMarket_Opportunity());
		_log.info("strategic_allignment"+pivotRiskUtility.getStrategic_Alignment());
		_log.info("technical_merit"+pivotRiskUtility.getTechnical_Merit());
		_log.info("time to market"+pivotRiskUtility.getTime_To_Market());
		_log.info("risk_slider"+pivotRiskUtility.getRisk_Slider());
		_log.info("utility slider"+pivotRiskUtility.getUtility_Slider());*/
		PivotRiskUtility risk_util = pivotRiskUtilityService.find(user_id);
		if(risk_util==null){
			_log.info("No data found in Utility");
			pivotRiskUtilityService.create(pivotRiskUtility);
		}
		else{
			_log.info("data found");
			pivotRiskUtilityService.update(pivotRiskUtility);
		}
		_log.info("data saved successfully");
	}
	
	@ResourceMapping(value = "resetRequest")
	public void resetRequest(ResourceRequest resourceRequest,ResourceResponse resourceResponse){
	try{
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PivotRiskUtility pivotRiskUtility = new PivotRiskUtility();
		_log.info("Market_opportunity"+pivotRiskUtility.getMarket_Opportunity());
		_log.info("strategic_allignment"+pivotRiskUtility.getStrategic_Alignment());
		_log.info("technical_merit"+pivotRiskUtility.getTechnical_Merit());
		_log.info("time to market"+pivotRiskUtility.getTime_To_Market());
		_log.info("risk_slider"+pivotRiskUtility.getRisk_Slider());
		_log.info("utility slider"+pivotRiskUtility.getUtility_Slider());
		pivotRiskUtility = pivotRiskUtilityService.find(themeDisplay.getUserId());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Technical_Merit", pivotRiskUtility.getTechnical_Merit());
		jsonObject.put("Market_Opportunity", pivotRiskUtility.getMarket_Opportunity());
		jsonObject.put("Strategic_Alignment", pivotRiskUtility.getStrategic_Alignment());
		jsonObject.put("Time_To_Market", pivotRiskUtility.getTime_To_Market());
		jsonObject.put("Risk_Slider", pivotRiskUtility.getRisk_Slider());
		jsonObject.put("Utility_Slider", pivotRiskUtility.getUtility_Slider());
		resourceResponse.getWriter().write(jsonObject.toJSONString());
	}catch(Exception e){
		e.printStackTrace();
		_log.info("Error in response data to ajax call");
	}
	}
	private static final Log _log = LogFactoryUtil.getLog(PivotChartController.class.getName());
}




