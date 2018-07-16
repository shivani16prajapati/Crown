package com.unicef.portlet.InnometricsDashboard;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Comment;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.domain.UserData;
import com.unicef.portlet.NewDashboard.HotIdeas;
import com.unicef.portlet.NewDashboard.ideaTypeChartModel;
import com.unicef.portlet.thanks.ThankYouMeterController;
import com.unicef.service.IdeaCommentService;
import com.unicef.service.IdeaCommentVoteService;
import com.unicef.service.IdeaEndorsementService;
import com.unicef.service.IdeaService;
import com.unicef.service.LikeService;
import com.unicef.service.SolutionService;
import com.unicef.service.UserDataService;
import com.unicef.util.IdeaUtil;






@Controller(value = "InnometricsDashboard")
@RequestMapping("VIEW")
public class InnometricsDashboard {
	
	@Autowired
	private IdeaService ideaService;
	
	@Autowired		
	private LikeService likeService;		
					
	@Autowired		
	private IdeaCommentService ideaCommentService;
	
	@Autowired
	private IdeaEndorsementService ideaEndorsementService;
	
	
	@Autowired
	private IdeaCommentVoteService ideaCommentVoteService;
	
	@Autowired
	private UserDataService userDataService;
	
	@Autowired
	private SolutionService solutionService;
	
	
	
	private static final Log log = LogFactoryUtil.getLog(InnometricsDashboard.class);
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request,RenderResponse response,Model model) throws PortalException, SystemException{
		String[] monthname = new String[]{"","Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
		
		
	
		
		ThemeDisplay themedisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		long total_ideas = ideaService.noofIdeas();
		long lastWeek_ideas = ideaService.countLastWeekSubmitedIdeas();
		
		long total_thankYou = ideaCommentVoteService.getCountTotalThankyou();
		long lastWeek_thankYou  = ideaCommentVoteService.getCountLastWeekThankyou();
		
		long total_upVotes = likeService.getTotalLike();
		long lastWeek_upVotes = likeService.getTotalLikesOfLastWeek();
		
		long total_endorsed = ideaEndorsementService.gettotalIdeaendorsed();
		long lastWeek_endorsed = ideaEndorsementService.getTotalIdeaEndorsementOfLastWeek();
		
		long total_solution =  solutionService.noOfSolution();
		long lastWeek_solution = (long) solutionService.countLastWeekSubmitedSolution();
		  
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");  
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.DATE,1);
		Date extDat = cal.getTime();
		String todaydate = dateformatter.format(extDat);  
		cal.add(Calendar.YEAR,-1);
		Date beforeyear = cal.getTime();
		String enddate = dateformatter.format(beforeyear);
		
		    List<AssetCategory> ideaTypeList = null;
			List<AssetCategory> lineofBussiness = null;
			List<ideaTypeChartModel> ideachart = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> ideachartlof = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> ideaByMonth = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> idealikeschart = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> ideathankschart = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> headOfficeIdea = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> otherofficeidea = new ArrayList<ideaTypeChartModel>();
			
			List<Long> ideastageList = new ArrayList<Long>();
			List<Float> avarageChart = new ArrayList<Float>();
			List<HotIdeas> leaderboardIdeaList = new ArrayList<HotIdeas>();
			List<HotIdeas> thankyouList = new ArrayList<HotIdeas>();
			
			List<IdeaCommentVote> userthankyoulist = ideaCommentVoteService.getmostthankedUser(0,5);
			for(IdeaCommentVote _IdeaCommentVote:userthankyoulist){
				HotIdeas thnakyou = new HotIdeas();
				try{
					thnakyou.setInventorName(UserLocalServiceUtil.getUser(_IdeaCommentVote.getUserId()).getFullName());
					long votes = ideaCommentVoteService.countThnakyoubyuser(_IdeaCommentVote.getUserId());
					if(Validator.isNotNull(votes)){thnakyou.setUpVotes(votes);}else{thnakyou.setUpVotes(0);}
					thankyouList.add(thnakyou);
					}catch(Exception e){
						log.error(e.toString());
					}
				
			}
			
		
			
			
			List<UserData> userData= userDataService.getUserDataByIdeaUser();
			
			long USA = 0;
			long GBR=0;
			long ARG=0;
			long UKR=0;
			long CHN=0;
			long DEU =0;
			
			for(UserData _user:userData){
				/*log.info(ideaService.getideabyuser(_user.getUserId()).size());*/
				if(_user.getUserCountry().equalsIgnoreCase("USA")){				
					USA = ideaService.getideabyuser(_user.getUserId()).size();				
				}else if(_user.getUserCountry().equalsIgnoreCase("GBR")){
					GBR = ideaService.getideabyuser(_user.getUserId()).size();
				}
				else if(_user.getUserCountry().equalsIgnoreCase("ARG")){
					ARG = ideaService.getideabyuser(_user.getUserId()).size();
				}
				else if(_user.getUserCountry().equalsIgnoreCase("UKR")){
					UKR = ideaService.getideabyuser(_user.getUserId()).size();
				}
				else if(_user.getUserCountry().equalsIgnoreCase("CHN")){
					CHN = ideaService.getideabyuser(_user.getUserId()).size();
				}
				else if(_user.getUserCountry().equalsIgnoreCase("DEU")){
					DEU = ideaService.getideabyuser(_user.getUserId()).size();
				}
			}
			
			
			for(long i=1;i<7;i++){
				ideastageList.add(ideaService.getAllIdeaByNewStage(i));
			}
		
			try {
				ideaTypeList = IdeaUtil.getIdeaTypeList(
						themedisplay.getScopeGroupId(), themedisplay.getUserId());
				lineofBussiness = IdeaUtil.getBusinessLine(
						themedisplay.getScopeGroupId(), themedisplay.getUserId());
			} catch (PortalException | SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			
			for(AssetCategory asstcat:ideaTypeList){
				if(asstcat.getName() != "Massive disruption potential"){
					ideaTypeChartModel ideachartmodel = new ideaTypeChartModel();
					ideachartmodel.setTypename(asstcat.getName());
					ideachartmodel.setValue(ideaService.countByIdeaTypeByYear(enddate,todaydate,asstcat.getCategoryId()));
					ideachart.add(ideachartmodel);
				}			
			
			}
			
			for(AssetCategory asstcat:lineofBussiness){
				ideaTypeChartModel ideachartmodel = new ideaTypeChartModel();
				ideachartmodel.setTypename(asstcat.getName());
				ideachartmodel.setValue(ideaService.countByIdeaLineofbusinessByYear(enddate,todaydate,asstcat.getCategoryId()));
				ideachartlof.add(ideachartmodel);
				
			}
			
			DecimalFormat formatter = new DecimalFormat("00");
			for(int i=0; i<monthname.length;i++){
				ideaTypeChartModel ideaMonth = new ideaTypeChartModel();
				ideaTypeChartModel likchart = new ideaTypeChartModel();
				ideaTypeChartModel thankyouchart = new ideaTypeChartModel();
				ideaTypeChartModel headoffice = new ideaTypeChartModel();
				ideaTypeChartModel otheroffice = new ideaTypeChartModel();
				
				cal.add(Calendar.MONTH,1);
				Date graphdate = cal.getTime();
				int tmp_month = graphdate.getMonth();
				if(tmp_month == 0){
					tmp_month = 12;
				}
				String tmpmonth = formatter.format(tmp_month);
				int tmp_year = graphdate.getYear()+1900;
				String year = tmp_year+"-"+tmpmonth;
				ideaMonth.setTypename(monthname[tmp_month]);
				ideaMonth.setValue(ideaService.getIdeaCountByMonthWiseAndYear(year));
				ideaByMonth.add(ideaMonth);
				likchart.setTypename(monthname[tmp_month]);
				likchart.setValue(likeService.getAll_LikesByDatewise(year));
				thankyouchart.setTypename(monthname[tmp_month]);
				thankyouchart.setValue(ideaCommentVoteService.get_thankyoucounntBydate(year));
				ideathankschart.add(thankyouchart);	
				idealikeschart.add(likchart);
				float divider = 2;
				float likevalue = likchart.getValue();
				float thankvalue = thankyouchart.getValue();
				float avgValue = ((likevalue+thankvalue)/divider) ;
				avarageChart.add(avgValue);
				headoffice.setTypename(monthname[tmp_month]);
				otheroffice.setTypename(monthname[tmp_month]);
				headoffice.setValue(ideaService.getHeadquaterIdeaBymonthwise("USA",year));
				otheroffice.setValue(ideaService.getRegionalIdeaBymonthwise("USA",year));
				headOfficeIdea.add(headoffice);
				otherofficeidea.add(otheroffice);
				
				
			}
		
		model.addAttribute("today_date",today);
		model.addAttribute("thankyouList",thankyouList);
		model.addAttribute("ideastageList",ideastageList);
		model.addAttribute("headOfficeIdea",headOfficeIdea);
		model.addAttribute("otherofficeidea",otherofficeidea);
		model.addAttribute("before_year",beforeyear);
		model.addAttribute("ideaByMonth",ideaByMonth);
		model.addAttribute("total_ideas",total_ideas);
		model.addAttribute("lastWeek_ideas",lastWeek_ideas);
		model.addAttribute("total_thankYou",total_thankYou);
		model.addAttribute("lastWeek_thankYou",lastWeek_thankYou);
		model.addAttribute("total_upVotes",total_upVotes);
		model.addAttribute("lastWeek_upVotes",lastWeek_upVotes);
		model.addAttribute("total_endorsed",total_endorsed);
		model.addAttribute("lastWeek_endorsed",lastWeek_endorsed);
		model.addAttribute("ideachart",ideachart);
		model.addAttribute("ideachartlof",ideachartlof);
		model.addAttribute("idealikeschart",idealikeschart);
		model.addAttribute("ideathankschart",ideathankschart);
		model.addAttribute("avarageChart",avarageChart);
		model.addAttribute("leaderboardIdeaList",leaderboardIdeaList);
		model.addAttribute("total_solution",total_solution);
		model.addAttribute("lastWeek_solution",lastWeek_solution);
		model.addAttribute("USA",USA);
		model.addAttribute("GBR",GBR);
		model.addAttribute("ARG",ARG);
		model.addAttribute("UKR",UKR);
		model.addAttribute("CHN",CHN);
		model.addAttribute("DEU",DEU);
		
		return "Innometrics";
	}
	
	@ResourceMapping(value = "gethotidea")
	public String gethotidea(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model) throws ParseException, PortalException, SystemException{
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		
		 
		int start = ParamUtil.getInteger(request, "start");
		int end = ParamUtil.getInteger(request, "end");
		
		List<Idea> hotIdeasList = ideaService.getideaHotnessByPagiantion(start, end);
		
		List<HotIdeas> finalHotIdeasList = new ArrayList<HotIdeas>();
		
		for(Idea hotidea:hotIdeasList){
			    HotIdeas hot = new HotIdeas();
				hot.setIdeaName(hotidea.getIdeaTitle());
				hot.setInventorName(UserLocalServiceUtil.getUser(hotidea.getCoInventorId()).getFullName());
				long votes = likeService.getCountOfIdeaLike(hotidea.getIdeaId());
				if(Validator.isNotNull(votes)){hot.setUpVotes(votes);}else{hot.setUpVotes(0);}
				long comments  = ideaCommentService.getIdeaCommentCount(hotidea.getIdeaId());
				if(Validator.isNotNull(comments)){hot.setComments(comments);}else{hot.setComments(0);}
				hot.setSubmissionDate(hotidea.getSubmissionDate());
				finalHotIdeasList.add(hot);
		}
		if(end < 10){
			model.addAttribute("see10",1);
		}
		
		
		model.addAttribute("hotIdeasList",finalHotIdeasList);
		return "hotidea";
		
	}
	
}
