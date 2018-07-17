package com.unicef.portlet.pivot;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.unicef.domain.Idea;
import com.unicef.domain.PivotCharts;
import com.unicef.domain.PivotStageChart;
import com.unicef.service.IdeaCommentVoteService;
import com.unicef.service.IdeaService;
import com.unicef.service.LikeService;
import com.unicef.util.IdeaUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@RequestMapping("VIEW")
public class PivotController {
	
	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private IdeaCommentVoteService ideaCommentVoteService;
	
	@Autowired
	private LikeService likeService;
	
	private static final String VIEW = "view";
	private static final Log log = LogFactoryUtil.getLog(PivotController.class);
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,
			RenderResponse renderResponse) throws PortalException, SystemException, ParseException{
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try{
			//Idea By Month In Last year chart
			List<HashMap<String, String>> monthwiseIdeaList = new ArrayList<HashMap<String, String>>();
			List<HashMap<String, Object>> drillDownSeries = new ArrayList<HashMap<String, Object>>();
			
			Map<String, Object> listOfMonthWiseIdea = new HashMap<String, Object>();
			Map<String, Map<String, Object>> series = new HashMap<String, Map<String, Object>>();
			
			HashMap<String, Object> listOfMonthWiseDetailIdea = new HashMap<String, Object>();
			HashMap<String, String> monthwiseIdeaMap = null;
			HashMap<Long, List<Idea>> dayWiseIdeaMap = new HashMap<Long, List<Idea>>();
			List<Idea> ideasList = ideaService.getIdeasOfLastYear();
			
			String monthAndYear = null;
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			PivotCharts charts = null;
			List<PivotCharts> pivotChartsList = new ArrayList<PivotCharts>();
			
			for (Idea idea : ideasList) {
					Date ideaCreatedDate = idea.getSubmissionDate();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(ideaCreatedDate);
					int year = calendar.get(Calendar.YEAR);
					
					monthAndYear = new SimpleDateFormat("MMM").format(calendar.getTime())+" "+String.valueOf(year);
					boolean blnExists = monthwiseIdeaList.toString().contains(monthAndYear);
					
					monthwiseIdeaMap = new HashMap<String, String>();
					List<Idea> ideasByMonth =null;
						if(!blnExists){
							ideasByMonth = ideaService.getIdeasByMonth(ideaCreatedDate);
							
							monthwiseIdeaMap.put("name","\""+monthAndYear+"\"");
							monthwiseIdeaMap.put("y", String.valueOf(ideasByMonth.size()));
							monthwiseIdeaMap.put("drilldown", "\""+monthAndYear+"\"");
							monthwiseIdeaList.add(monthwiseIdeaMap);
							pivotChartsList = new ArrayList<PivotCharts>();
							
							
							List<Idea> ideasByDay = null;
							
								for(Idea ideaObj : ideasByMonth){
									
									Date dayOfIdea = ideaObj.getSubmissionDate();
									Calendar calendar2 = Calendar.getInstance();
									calendar2.setTime(dayOfIdea);
									int day = calendar2.get(Calendar.DAY_OF_MONTH);
									Long newDay = (long) day;
									boolean ideaDayExist = pivotChartsList.toString().contains(formatter.format(ideaObj.getSubmissionDate()));
									if(!ideaDayExist){
										  ideasByDay = ideaService.getIdeasByDay(ideaObj.getSubmissionDate());
										  dayWiseIdeaMap.put(newDay, ideasByDay);
										  charts = new PivotCharts();
										  charts.setSubmissionDate(formatter.format(ideaObj.getSubmissionDate()));
										  charts.setSize(ideasByDay.size());
										  pivotChartsList.add(charts);
									}
								  
								}
							listOfMonthWiseDetailIdea = new HashMap<String, Object>();
							listOfMonthWiseDetailIdea.put("name", "\""+monthAndYear+"\"");
							listOfMonthWiseDetailIdea.put("id", "\""+monthAndYear+"\"");
							listOfMonthWiseDetailIdea.put("data", pivotChartsList);
							
							drillDownSeries.add(listOfMonthWiseDetailIdea);
						}
					
			}
			
			listOfMonthWiseIdea.put("name", "\""+"Ideas"+"\"");
			listOfMonthWiseIdea.put("colorByPoint", "true");
			listOfMonthWiseIdea.put("data", monthwiseIdeaList);
			series.put("series", listOfMonthWiseIdea);
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");	

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -365);
			String lastYearDate = sdf.format(calendar.getTime());
			
			
			Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.setTime(new Date());
			String currentYearDate = sdf.format(currentCalendar.getTime());
			
			
			String chartDate = "Ideas of "+ lastYearDate +" to "+currentYearDate;
			
			model.addAttribute("chartDate", "\'"+chartDate+"\'");
			model.addAttribute("listOfDayWiseIdea", drillDownSeries.toString().replaceAll("=", ":"));
			model.addAttribute("listOfMonthWiseIdea", listOfMonthWiseIdea.toString().replaceAll("=", ":"));
			
			//Idea By Stage 
			
			List<AssetCategory> stageList = null;
			stageList = IdeaUtil.getIdeaStageList(themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
			PivotStageChart stageChart = null;
			List<PivotStageChart> pivotStageChartsList = new ArrayList<PivotStageChart>();
			Map<String, Object> listOfStageWiseIdea = new HashMap<String, Object>();
			for (AssetCategory assetCategory : stageList) {
				long ideaCount = ideaService.getIdeaCountByStage(assetCategory.getCategoryId());
				
				stageChart = new PivotStageChart();
			
				stageChart.setStageName(assetCategory.getName());
				stageChart.setIdeaStageCount(ideaCount);
				pivotStageChartsList.add(stageChart);
			}
			
			Collections.sort(pivotStageChartsList , ideaDESCComparator);
			
			listOfStageWiseIdea.put("name", "\'"+"No Of Ideas"+"\'");
			listOfStageWiseIdea.put("data", pivotStageChartsList);
			model.addAttribute("listOfStageWiseIdea", listOfStageWiseIdea.toString().replaceAll("=", ":"));
			
			//Leader Board For Hot Ideas
			
			List<Idea> hotIdeasList = ideaService.getLeaderBoardHotIdeas();
			model.addAttribute("hotIdeas", hotIdeasList);
			
			//Ideas By Type Pie Chart
			
		/*	List<AssetCategory> ideaTypeList  = null;
			
			HashMap<String, String> typeWiseIdeaMap = null;
			
			ideaTypeList = IdeaUtil.getIdeaTypeList(themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
			List<HashMap<String, String>> typewiseIdeaList = new ArrayList<HashMap<String, String>>();
			Map<String, Object> listOfTypeWiseIdea = new HashMap<String, Object>();
			
			for (AssetCategory assetCategory : ideaTypeList) {
				long ideaCount = ideaService.countIdeasByType(assetCategory.getCategoryId());
				typeWiseIdeaMap = new HashMap<String, String>();
				typeWiseIdeaMap.put("name","\""+assetCategory.getName()+"\"");
				typeWiseIdeaMap.put("y", String.valueOf(ideaCount));
				typewiseIdeaList.add(typeWiseIdeaMap);
			}
			listOfTypeWiseIdea.put("name", "\""+"Ideas"+"\"");
			listOfTypeWiseIdea.put("colorByPoint", "true");
			listOfTypeWiseIdea.put("data", typewiseIdeaList);
			model.addAttribute("listOfTypeWiseIdea", listOfTypeWiseIdea.toString().replaceAll("=", ":"));*/
			
			//Ideas Month Wise My Social Points Chart
			   String monthAndYearSocial = null;
			 
			   List<String> monthYearList = new ArrayList<String>();
			   List<Long> likeCountList = new ArrayList<Long>();
			   List<Long> thanksCountList = new ArrayList<Long>();
			   for (Idea idea : ideasList) {
			    Date ideaCreatedDate = idea.getSubmissionDate();
			    Calendar socialCalendar = Calendar.getInstance();
			    socialCalendar.setTime(ideaCreatedDate);
			    int year = socialCalendar.get(Calendar.YEAR);

			    monthAndYearSocial = new SimpleDateFormat("MMM").format(socialCalendar.getTime())+" "+String.valueOf(year);
			    
			    List<Idea> ideasByMonth =null;
			    boolean blnExists = monthYearList.toString().contains(monthAndYearSocial);
			    
			    if(!blnExists){
			     ideasByMonth = ideaService.getIdeasByMonth(ideaCreatedDate);
			     long monthLikeCount = 0;
			     long monthThanksCount = 0;
			     for (Idea idea2 : ideasByMonth) {
			      
			      long likeCount = likeService.getCountOfIdeaLike(idea2.getIdeaId());
			      monthLikeCount += likeCount;
			      
			      long thankyouCount = ideaCommentVoteService.countIdeaThankYou(idea2.getIdeaId());
			      monthThanksCount += thankyouCount;
			     }
			     monthYearList.add("\""+monthAndYearSocial+"\"");
			     likeCountList.add(monthLikeCount);
			     thanksCountList.add(monthThanksCount);
			    }
			   }
			   model.addAttribute("monthYearList", monthYearList);
			   model.addAttribute("likeCountList", likeCountList);
			   model.addAttribute("thanksCountList", thanksCountList);
		}catch(Exception e){
			log.info("Error in Pivot Controller"+e.getMessage());
		}
		return VIEW;
	}
	private static Comparator<PivotStageChart> ideaDESCComparator = null;
	static {
		ideaDESCComparator = new Comparator<PivotStageChart>() {
		   public int compare(PivotStageChart a, PivotStageChart b) {
		    return (a.getIdeaStageCount() > b.getIdeaStageCount()) ? -1: (a.getIdeaStageCount() < b.getIdeaStageCount()) ? 1:0 ;
		   }
		 };
	 }
	@ResourceMapping(value ="ideaChartURL")
	public void ideaChartURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String type = ParamUtil.getString(request, "ideaType");
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		JSONObject jsonObject = null;
		
		List<AssetCategory> ideaTypeList  = null;
		try {
			if(type.equalsIgnoreCase("innovation")){
				ideaTypeList = IdeaUtil.getIdeaTypeList(10184, themeDisplay.getUserId());
			}else if(type.equalsIgnoreCase("edgeService")){
				ideaTypeList = IdeaUtil.getEdgeServiceCategoryList(10184, themeDisplay.getUserId());
			}else{
				ideaTypeList = IdeaUtil.getCategoryList(10184, themeDisplay.getUserId());
			}
			for (AssetCategory assetCategory : ideaTypeList) {
				long ideaCount = ideaService.countIdeasByType(assetCategory.getCategoryId(),type);
				
				//String category = "name";
				jsonObject = JSONFactoryUtil.createJSONObject();
				jsonObject.put("name", assetCategory.getName());
				jsonObject.put("y", ideaCount);
				jsonArray.put(jsonObject);
			}
			PrintWriter pw = null;
			pw = null;
			try {
				pw = resourceResponse.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pw.print(jsonArray.toString());
			pw.flush();
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
}
