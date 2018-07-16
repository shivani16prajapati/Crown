package com.unicef.portlet.NewDashboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.domain.UserData;
import com.unicef.domain.UserFeed;
import com.unicef.service.IdeaCommentService;
import com.unicef.service.IdeaCommentVoteService;
import com.unicef.service.IdeaEndorsementService;
import com.unicef.service.IdeaService;
import com.unicef.service.LikeService;
import com.unicef.service.SolutionAnswerVoteService;
import com.unicef.service.SolutionLikeService;
import com.unicef.service.SolutionService;
import com.unicef.service.UserDataService;
import com.unicef.service.UserFeedService;
import com.unicef.util.IdeaUtil;

@Controller
@RequestMapping("VIEW")
public class NewDashboard {
	
	@Autowired
	private IdeaService ideaService;
	
	@Autowired		
	private LikeService likeService;		
					
	@Autowired		
	private IdeaCommentService ideaCommentService;
	
	@Autowired
	private IdeaEndorsementService ideaEndorsementService;
	
	@Autowired
	private UserDataService userDataService;
	
	@Autowired
	private IdeaCommentVoteService ideaCommentVoteService;
	
	@Autowired
	private UserFeedService userFeedService;
	
	@Autowired
	private SolutionService solutionService;
	
	@Autowired
	private SolutionAnswerVoteService solutionAnswerVoteService;
	
	@Autowired
	private SolutionLikeService solutionLikeService;


	
	
	/*@SuppressWarnings("null")*/
	@RenderMapping
	public String handleRenderRequest(RenderRequest request,RenderResponse response,Model model) throws PortalException, SystemException{
		
		ThemeDisplay themedisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		long  userId =  themedisplay.getUserId();
		
		//ObjectMapper mapperObj = new ObjectMapper();
		LinkedHashMap<String, Integer> graphData = new LinkedHashMap<String, Integer>();
		graphData = ideaService.getAllIdeasSubmissionDate(); 
		/*try{
			String jsonResp = mapperObj.writeValueAsString(graphData);
			request.setAttribute("jsonResp",jsonResp);
		}
		catch(IOException e){
			e.printStackTrace();
		} */
		
		User user = null ;
		List<Idea> ideaList = new ArrayList<Idea>();
		List<Idea> recentIdeasList = new ArrayList<Idea>();
		
		List<HotIdeas> finalRecentIdeasList = new ArrayList<HotIdeas>();
		List<HotIdeas> leaderboardIdeaList = new ArrayList<HotIdeas>();
		
		
		long total_ideas = 0 ;
		long lastWeek_ideas = 0;
		long total_thankYou = 0;
		long lastWeek_thankYou  = 0;
		long total_upVotes = 0;
		long lastWeek_upVotes =0;
		long total_endorsed =0;
		long lastWeek_endorsed =0;
		String userImagUrl = StringPool.BLANK;
		
		try{
			total_ideas = ideaService.getListOfIdeaByCurrentUser(userId).size();
			lastWeek_ideas = ideaService.countLastWeekSubmitedIdeasByuser(userId);
			ideaList = ideaService.getListOfIdeaByCurrentUser(userId);
			if(!ideaList.isEmpty()){
				for(Idea ideas : ideaList){
					total_upVotes  = total_upVotes  + likeService.getCountOfIdeaLike(ideas.getIdeaId());
					lastWeek_upVotes = lastWeek_upVotes + likeService.getWeeklyCountOfIdeaLike(ideas.getIdeaId());
					total_thankYou = total_thankYou + ideaCommentVoteService.countIdeaThankYou(ideas.getIdeaId());
					lastWeek_thankYou = lastWeek_thankYou + ideaCommentVoteService.countLastWeekIdeaThankYou(ideas.getIdeaId());
					total_endorsed = total_endorsed + ideaEndorsementService.getIdeaEndorsementCount(ideas.getIdeaId());
					lastWeek_endorsed = lastWeek_endorsed + ideaEndorsementService.getWeeklyCountOfIdeaEndorsement(ideas.getIdeaId());
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<Idea> leaderIdea = ideaService.getLeaderBoardIdea();
		
		for(Idea idea:leaderIdea){
			HotIdeas hotidea = new HotIdeas();
			hotidea.setIdeaName(idea.getIdeaTitle());
			hotidea.setInventorName(UserLocalServiceUtil.getUser(idea.getCoInventorId()).getFullName());
			hotidea.setSubmissionDate(idea.getSubmissionDate());
			hotidea.setUpVotes(likeService.getCountOfIdeaLike(idea.getIdeaId()));
			hotidea.setComments(ideaCommentVoteService.countIdeaThankYou(idea.getIdeaId()));
			leaderboardIdeaList.add(hotidea);
		
		}
		
		/*for profile */
		try {
			user  = UserLocalServiceUtil.getUser(userId);
			userImagUrl = userImagUrl + user.getPortraitURL(themedisplay);
			
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		Date d1 = user.getCreateDate();
	    Date d2 = new Date();
	   
	    long diff = d2.getTime() - d1.getTime();
	    long diffInDays = diff / (1000 * 60 * 60 * 24);
	    long years = 0;
	    
	    if(diffInDays >= 365){
	    	years  = diffInDays / 365;
    	}
	    
	    UserData usershow = null;
		
		try{
			System.out.println("userId "+userId);
			usershow  = userDataService.getuserData(userId);
			if(Validator.isNull(usershow)){
				System.out.println("not found userData");
				usershow = new UserData();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		recentIdeasList  = ideaService.getSortedIdeaHomeList(0,3);;
		
		
	
		
		
		if(Validator.isNotNull(recentIdeasList)){
			if(recentIdeasList.size() > 3){
				for(int i = 0 ;i < 3;i++){
					HotIdeas hot = new HotIdeas();
					hot.setIdeaName(recentIdeasList.get(i).getIdeaTitle());
					hot.setInventorName(UserLocalServiceUtil.getUser(recentIdeasList.get(i).getCoInventorId()).getFullName());
					long votes = likeService.getCountOfIdeaLike(recentIdeasList.get(i).getIdeaId());
					if(Validator.isNotNull(votes)){hot.setUpVotes(votes);}else{hot.setUpVotes(0);}
					long comments  = ideaCommentService.getIdeaCommentCount(recentIdeasList.get(i).getIdeaId());
					if(Validator.isNotNull(comments)){hot.setComments(comments);}else{hot.setComments(0);}
					hot.setSubmissionDate(recentIdeasList.get(i).getSubmissionDate());
					finalRecentIdeasList.add(hot);
				}
			}else{
				for(Idea ide : recentIdeasList){
					HotIdeas hot = new HotIdeas();
					hot.setIdeaName(ide.getIdeaTitle());
					hot.setInventorName(UserLocalServiceUtil.getUser(ide.getCoInventorId()).getFullName());
					long votes = likeService.getCountOfIdeaLike(ide.getIdeaId());
					if(Validator.isNotNull(votes)){hot.setUpVotes(votes);}else{hot.setUpVotes(0);}
					long comments  = ideaCommentService.getIdeaCommentCount(ide.getIdeaId());
					if(Validator.isNotNull(comments)){hot.setComments(comments);}else{hot.setComments(0);}
					hot.setSubmissionDate(ide.getSubmissionDate());
					finalRecentIdeasList.add(hot);
				}
			}
		}	
		int recentPlaceHolder = 3 - finalRecentIdeasList.size();
		/*System.out.println("recent place holder : "+recentPlaceHolder);*/
		
		
		    String[] monthname = new String[]{"","Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
		
		    List<AssetCategory> ideaTypeList = null;
			List<AssetCategory> lineofBussiness = null;
			List<ideaTypeChartModel> ideachart = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> ideachartlof = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> idealikeschart = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> ideathankschart = new ArrayList<ideaTypeChartModel>();
			List<ideaTypeChartModel> ideaHotnesschart = new ArrayList<ideaTypeChartModel>();
			
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
					ideachartmodel.setValue(ideaService.countByIdeaTypeandUserId(asstcat.getCategoryId(),themedisplay.getUserId()));
					ideachart.add(ideachartmodel);
				}			
			
			}
			for(AssetCategory asstcat:lineofBussiness){
				ideaTypeChartModel ideachartmodel = new ideaTypeChartModel();
				ideachartmodel.setTypename(asstcat.getName());
				ideachartmodel.setValue(ideaService.countByIdeaLineofbusinessandUserId(asstcat.getCategoryId(),themedisplay.getUserId()));
				ideachartlof.add(ideachartmodel);
				
			}
			
//			for diff between data
			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();
			cal.add(Calendar.YEAR,-1);
			
			
			Date beforeyear = cal.getTime();
				List<Idea> user_idea_list = ideaService.getideabyuser(themedisplay.getUserId());	
			String ideaid = "(";	
			for (int i=0 ; i<user_idea_list.size();i++){
			  if (i+1 != user_idea_list.size()){
				     ideaid = ideaid + user_idea_list.get(i).getIdeaId()+",";
				 }else{
					 ideaid = ideaid + user_idea_list.get(i).getIdeaId()+")";
				 }				
			}
			if(user_idea_list.size()==0){
				ideaid = ideaid +"0)";
			}
			List<Float> avarageChart = new ArrayList<Float>();
			
			DecimalFormat formatter = new DecimalFormat("00");
			for(int i=0; i<monthname.length;i++){
				ideaTypeChartModel likchart = new ideaTypeChartModel();
				ideaTypeChartModel thankyouchart = new ideaTypeChartModel();
				ideaTypeChartModel ideaHotchart = new ideaTypeChartModel();
				cal.add(Calendar.MONTH,1);
				Date graphdate = cal.getTime();
				int tmp_month = graphdate.getMonth();
				if(tmp_month == 0){
					tmp_month = 12;
				}		
				String tmpmonth = formatter.format(tmp_month);			
				int tmp_year = graphdate.getYear()+1900;
				String year = tmp_year+"-"+tmpmonth;
				likchart.setTypename(monthname[tmp_month]);
				likchart.setValue(likeService.getmonthwiseIdea(year,ideaid));
				thankyouchart.setTypename(monthname[tmp_month]);
				thankyouchart.setValue(ideaCommentVoteService.getmonthwiseIdeavote(year,ideaid));
				ideaHotchart.setTypename(monthname[tmp_month]);
				ideaHotchart.setFloatValue(ideaService.hotideaBymonthByuser(themedisplay.getUserId(),year));
				idealikeschart.add(likchart);
				ideathankschart.add(thankyouchart);	
				ideaHotnesschart.add(ideaHotchart);
				avarageChart.add(Float.parseFloat(String.valueOf((likchart.getValue()+thankyouchart.getValue())/2)));
		}
			
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
					
					}
				
			}
			
	    usershow.setFull_name(themedisplay.getUser().getFullName());
		model.addAttribute("leaderboardIdeaList",leaderboardIdeaList);
		model.addAttribute("thankyouList",thankyouList);
	    model.addAttribute("recentIdeasList",finalRecentIdeasList);
		model.addAttribute("ideathankschart",ideathankschart);
		model.addAttribute("ideaHotnesschart",ideaHotnesschart);
		model.addAttribute("avarageChart",avarageChart);
		model.addAttribute("recentHolder",recentPlaceHolder);
		
		model.addAttribute("userData",usershow);
		model.addAttribute("user",user);
		model.addAttribute("userImagUrl",userImagUrl);
		model.addAttribute("years",years);
		request.setAttribute("graphData",graphData);
		model.addAttribute("idealikeschart",idealikeschart);
		model.addAttribute("currentDate", new Date());
		model.addAttribute("today_date",today);
		model.addAttribute("before_year",beforeyear);
		model.addAttribute("total_ideas",total_ideas);
		model.addAttribute("ideachart",ideachart);
		model.addAttribute("ideachartlof",ideachartlof);
		model.addAttribute("lastWeek_ideas",lastWeek_ideas);
		model.addAttribute("total_thankYou",total_thankYou);
		model.addAttribute("lastWeek_thankYou",lastWeek_thankYou);
		model.addAttribute("total_upVotes",total_upVotes);
		model.addAttribute("lastWeek_upVotes",lastWeek_upVotes);
		model.addAttribute("total_endorsed",total_endorsed);
		model.addAttribute("lastWeek_endorsed",lastWeek_endorsed);
		
		return "view";
	}
	
	@SuppressWarnings("null")
	@ResourceMapping(value="editProfile")
	public String editProfile(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model) throws IOException{
	
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themedisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		long  userId =  themedisplay.getUserId();
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		java.io.File photoImage = uploadRequest.getFile("file-upload");
		String photoImageName = uploadRequest.getFileName("file-upload");
		
		String method  = ParamUtil.getString(request, "method");
		System.out.println("method : "+method);
		
		User user = null ;
		String userImagUrl = StringPool.BLANK;
		boolean landPage = false;
		
		if(method.equalsIgnoreCase("showDetails")){
			UserData usershow = null;
			
			try{
				usershow  = userDataService.getuserData(userId);
				/*if(Validator.isNull(usershow)){
					usershow = new UserData();
				}*/
				if(Validator.isNull(usershow)){
					System.out.println("not found userData");
					usershow = new UserData();
				}else{
					usershow.setFull_name(themedisplay.getUser().getFullName());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try {
				user  = UserLocalServiceUtil.getUser(userId);
				    if((user.getOpenId()).equalsIgnoreCase("1")){
						landPage = true;
					}
				userImagUrl = userImagUrl + user.getPortraitURL(themedisplay);
				
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
			Date d1 = user.getCreateDate();
		    Date d2 = new Date();
		    long diff = d2.getTime() - d1.getTime();
		    long diffInDays = diff / (1000 * 60 * 60 * 24);
		    long years = 0;
		    if(diffInDays >= 365){
		    	years  = diffInDays / 365;
	    	}
		    model.addAttribute("user",user);
		    model.addAttribute("landPage",landPage);
		    model.addAttribute("userData",usershow);
			model.addAttribute("userImagUrl",userImagUrl);
			model.addAttribute("years",years);
		    
		}else if(method.equalsIgnoreCase("saveDetails")){
			
			System.out.println("photoImage : "+photoImage+" photoImageName : "+photoImageName);
			
			String name = ParamUtil.getString(request, "name");
			String current_role = ParamUtil.getString(request, "current_role");
			String department = ParamUtil.getString(request, "department");
			/*String works = ParamUtil.getString(request, "works");*/
			/*String lob = ParamUtil.getString(request, "lob");*/
			String contact = ParamUtil.getString(request, "contact");
			String expertise = ParamUtil.getString(request, "expertise");
			int experience   = Integer.parseInt(request.getParameter("experience"));
			String skillset = ParamUtil.getString(request, "skillset");
			String patents = ParamUtil.getString(request, "patents");
			String fun = ParamUtil.getString(request, "fun");
			String country = ParamUtil.getString(request,"userCountry");
			
			String landingPage = ParamUtil.getString(request, "checkBox");
			
			System.out.println("Department : "+department);
			System.out.println("Location/Country : "+country);
			System.out.println("Patent : "+patents);
			
			
			User userSave = null;
			UserData userData  = null;
			
			try {
				userSave  = UserLocalServiceUtil.getUser(userId);
				userSave.setEmailAddress(contact);
				if(landingPage.equalsIgnoreCase("true")){
					userSave.setOpenId("1");
				}else{
					userSave.setOpenId("");
				}
				
				//update Image code
				System.out.println("uploadrequest "+uploadRequest+"photoImages "+photoImage+"and photoimageName"+photoImageName);

				if (Validator.isNotNull(photoImageName)) {
					byte[] photoInBytes = new byte[(int) photoImage.length()];
					FileInputStream fileInputStream = null;
					try {
						// convert file into array of bytes
						try {
							fileInputStream = new FileInputStream(photoImage);
							fileInputStream.read(photoInBytes);
							fileInputStream.close();
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
						user = UserLocalServiceUtil.updatePortrait(user.getUserId(),photoInBytes);
						jsonObject.put("userProfilePicPath",user.getPortraitURL(themedisplay));
						System.out.println("path "+user.getPortraitURL(themedisplay));
					} catch (PortalException e) {
						System.out.println(e.getMessage());
					} catch (SystemException e) {
						System.out.println(e.getMessage());
					}
					
					System.out.println(photoImage+" file found :"+photoImageName);
				
				}
				
				userSave = UserLocalServiceUtil.updateUser(userSave);
				if(Validator.isNotNull(userSave)){
					System.out.println("User table fields updated");
				}
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
			
			
			try{
				userData = userDataService.getuserData(userId);
				if(Validator.isNotNull(userData)){
					userData.setFull_name(name);
					userData.setCurrent_role(current_role);
					/*userData.setLob(lob);*/
					userData.setDepartment(department);
					/*userData.setWorks_for(works);*/
					userData.setExpertise(expertise);
					userData.setExperience(experience);
					userData.setSkill_set(skillset);
					userData.setPatents(patents);
					userData.setFun(fun);
					userData.setUserCountry(country);
					userDataService.update(userData);
				}else{
					userData = new UserData();
					userData.setUserId(userId);
					userData.setCurrent_role(current_role);
					/*userData.setLob(lob);*/
					userData.setDepartment(department);
					/*userData.setWorks_for(works);*/
					userData.setExpertise(expertise);
					userData.setExperience(experience);
					userData.setSkill_set(skillset);
					userData.setPatents(patents);
					userData.setFun(fun);
					userData.setUserCountry(country);
					userDataService.create(userData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
		
		resourceResponse.getWriter().println(jsonObject);
		return "editProfile";
	}
	
	
	
	@ResourceMapping(value = "getnewsfeed")
	public String getnewsfeed(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model) throws ParseException{
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		
		 
		int start = ParamUtil.getInteger(request, "start");
		int end = ParamUtil.getInteger(request, "end");
		String localdate = ParamUtil.getString(request,"current_time");
		
		System.out.println(localdate);
		
		List<UserFeed> userfeed = this.getuserfeed(themeDisplay.getUserId(),start, end);
		long userfeedlength = userFeedService.getUserFeedCount(themeDisplay.getUserId());
		
		Date _localdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(localdate);  
		
		Date serverDate = new Date();
		
		long diff = _localdate.getTime() - serverDate.getTime();
		
		int diffMinutes = (int) (diff / (60 * 1000) % 60);
		int diffHours = (int) (diff / (60 * 60 * 1000) % 24);
		
		if(userfeed.size() !=0){
			long diffdays = ((serverDate.getTime()  - userfeed.get(0).getCreatedDate().getTime())/(24 * 60 * 60 * 1000));
			System.out.println(diffdays);
			if(diffdays >=2 || start>300){
				model.addAttribute("isPriority",1);
			}	
		}
		
		for(UserFeed _userfeed:userfeed){
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTime(_userfeed.getCreatedDate());
	    	cal.add(Calendar.HOUR,diffHours);
	    	cal.add(Calendar.MINUTE,diffMinutes);
	    	_userfeed.setCreatedDate(cal.getTime());
	    }
		
		
	
		
		
		if(userfeedlength>=end){
			model.addAttribute("nextnotification",1);
			int nextstart=0;
			if(start == 0){
				nextstart = start+11;
			}else{
				nextstart = start+10;
			}
			model.addAttribute("nextstart",nextstart);
			
			model.addAttribute("nextend",end+10);
		}
		if(start == 1){
			start = 0;
		}
		
		if(start !=0 ){
			start = start - 10;
			end = end - 10;
			if(start == 1){
				start = 0;
			}
			model.addAttribute("prevnotification",1);
			model.addAttribute("start",start);
			model.addAttribute("end",end);
		}
		
		model.addAttribute("ideaService",ideaService);
		model.addAttribute("ideaService",ideaService);
		model.addAttribute("solutionService",solutionService);
		model.addAttribute("ideaCommentService",ideaCommentService);
		model.addAttribute("ideaEndorsementService",ideaEndorsementService);
		model.addAttribute("likeService",likeService);
		model.addAttribute("feedlist",userfeed);
		model.addAttribute("solutionAnswerVoteService",solutionAnswerVoteService);
		model.addAttribute("solutionLikeService",solutionLikeService);	
		return "myfeed_response";
		
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
	
	@SuppressWarnings("null")
	@ResourceMapping(value="changeProfile")
	public void changeProfile(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model) throws IOException{
		
		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(resourceRequest);
		
		 ThemeDisplay themedisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		 HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(resourceRequest);
		 HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(resourceResponse);
		    
		File photoImage = uploadRequest.getFile("file-upload");
		String photoImageName = uploadRequest.getFileName("file-upload");
		
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
	    serviceContext.setScopeGroupId(themedisplay.getScopeGroupId());
	    serviceContext.setCompanyId(themedisplay.getCompanyId());
		
        User user = null;
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
	    if (Validator.isNotNull(photoImageName)) {
			System.out.println("photoImage is not null"+photoImageName);
			byte[] photoInBytes = new byte[(int) photoImage.length()];
			FileInputStream fileInputStream = null;
			try {
				// convert file into array of bytes
				try {
					fileInputStream = new FileInputStream(photoImage);
					fileInputStream.read(photoInBytes);
					fileInputStream.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				user = UserLocalServiceUtil.updatePortrait(themedisplay.getUserId(),photoInBytes);
				jsonObject.put("userProfilePicPath",user.getPortraitURL(themedisplay));
				
			} catch (PortalException e) {
				System.out.println(e.getMessage());
			} catch (SystemException e) {
				System.out.println(e.getMessage());
			}		
	}
	    final PrintWriter out = httpResponse.getWriter();
	    out.write(jsonObject.toString());
	    out.close();
}
	
	public void adduserfeed(long feedtype,long userId,long referId,long byuserId){
		UserFeed userfeed = new UserFeed();
		userfeed.setCreatedDate(new Date());
		userfeed.setUserId(userId);
		userfeed.setReferId(referId);
		userfeed.setFeedtypeId(feedtype);
		userfeed.setByuserId(byuserId);
		userFeedService.create(userfeed);
	}
	

	public List<UserFeed> getuserfeed(long userId,int start,int end){
		 List<UserFeed> userfeed = userFeedService.getUserFeed(userId, start, end);
		 return userfeed;
   }
	
}
