package com.unicef.portlet.solutionscrum;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.Solution;
import com.unicef.domain.SolutionAnswer;
import com.unicef.domain.SolutionAnswerComment;
import com.unicef.domain.SolutionAnswerView;
import com.unicef.domain.SolutionAnswerVote;
import com.unicef.domain.SolutionComment;
import com.unicef.domain.SolutionCommentView;
import com.unicef.domain.SolutionScrumDiscussion;
import com.unicef.domain.SolutionScrumThanks;
import com.unicef.domain.Sprint;
import com.unicef.service.IdeaRateService;
import com.unicef.service.IdeaService;
import com.unicef.service.SolutionAnswerCommentService;
import com.unicef.service.SolutionAnswerService;
import com.unicef.service.SolutionAnswerVoteService;
import com.unicef.service.SolutionCommentService;
import com.unicef.service.SolutionLikeService;
import com.unicef.service.SolutionScrumThanksService;
import com.unicef.service.SolutionService;
import com.unicef.service.SprintService;
import com.unicef.util.IdeaScrumUtil;
import com.unicef.util.IdeaUtil;
import com.unicef.util.SolutionScrumUtil;
import com.unicef.util.SprintConstant;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@RequestMapping("VIEW")
public class SolutionScrumController {
	
	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private SolutionService solutionService;
	
	@Autowired
	private SolutionLikeService solutionLikeService;
	
	@Autowired
	private SolutionAnswerVoteService solutionAnswerVoteService;

	@Autowired
	private SolutionCommentService solutionCommentService;
	
	@Autowired
	private SolutionAnswerService solutionAnswerService;
	
	@Autowired
	private SolutionAnswerCommentService solutionAnswerCommentService;
	
	@Autowired
	private IdeaRateService ideaRateService;

	@Autowired
	private SolutionScrumThanksService solutionScrumThanksService;
	private static final Log log = LogFactoryUtil.getLog(SolutionScrumController.class);
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long solutionId =0;
		
		try{
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			if(ideaScrum != null){
				solutionId =  ideaScrum.getSolutionId();
			}
			long coInventorId = solutionService.find(solutionId).getCoInventorId();
			
			boolean isInventorPrefsAdmin =  IdeaScrumUtil.isInventorPrefsPermission(themeDisplay.getCompanyId(), themeDisplay.getUserId(), coInventorId);
			
			List<Sprint> sprints = new ArrayList<Sprint>();
			sprints = sprintService.getSprintListBySolutionIdAndSolutionScrumId(solutionId, ideaScrum.getGroupId());
			
			String tabNames = "The Solution";
			String tabValues = "the-solution";
			
			if(Validator.isNotNull(sprints)){
				for(Sprint sprint : sprints){
						 tabNames = tabNames + StringPool.COMMA + "Sprint "+sprint.getOrderIndex();
						 tabValues = tabValues + StringPool.COMMA + "sprint-"+ sprint.getOrderIndex();
				}
			}
			if(isInventorPrefsAdmin){
				tabNames = tabNames + StringPool.COMMA + "Scrum Prefs"; 
				tabValues = tabValues + StringPool.COMMA + "inventor-prefs"; 
			}
			String tab = ParamUtil.getString(renderRequest, "tab", "the-solution");
			if(tab.equals("the-solution")){
				theSolutionView(locale, model, renderRequest, renderResponse);
			}else if(tab.equals("inventor-prefs")){
				inventorPrefsView(locale, model, renderRequest, renderResponse);
			}else{
				sprintView(locale, model, renderRequest, renderResponse, tab);
			}
			String viewJsp = StringPool.BLANK;
			if(tab.contains("sprint")){
				viewJsp = "sprint";
			}else{
				viewJsp = tab;
			}
			model.addAttribute("viewJsp", viewJsp);
			model.addAttribute("isInventorPrefsAdmin", isInventorPrefsAdmin);
			model.addAttribute("tabNames", tabNames);
			model.addAttribute("tabValues", tabValues);
		}catch(Exception e){
			log.error("Error in generating idea scrum view");
		}
		model.addAttribute("noOfSprints", sprintService.noofSprints(solutionId));
		return "view";
	}

	private void inventorPrefsView(Locale locale, Model model,
			RenderRequest renderRequest, RenderResponse renderResponse) {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String view = ParamUtil.getString(renderRequest, "view", StringPool.BLANK);
		
		String inventorPrefsJSP = "sprintList";
		
		if(view.equals("createSprint")){
			createSprint(locale, renderRequest, renderResponse, model);
			inventorPrefsJSP = "createSprint";
		}else if(view.equals("editSprint")){
			editSprint(locale, renderRequest, renderResponse, model);
			inventorPrefsJSP = "createSprint";
		}else if(view.equals("saveSprint")){
			//saveAllSprints(locale, renderRequest, renderResponse, model);
		}
		else{
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			long solutionId = 0;
			if(ideaScrum != null){
				solutionId =  ideaScrum.getSolutionId();
			}
			
			model.addAttribute("sprintListSize", sprintService.sprintList().size());
			model.addAttribute("solutionId", solutionId);
			model.addAttribute("solutionScrumId", ideaScrum.getGroupId());
		}
		model.addAttribute("inventorPrefsJSP", inventorPrefsJSP);
		
	}
	@ActionMapping(params="action=deleteSprint")
	public void deleteSprint(ActionRequest actionRequest,ActionResponse actionResponse,Model model){
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long sprintId = ParamUtil.getLong(actionRequest, "sprintId");
		String tab = ParamUtil.getString(actionRequest, "tab", StringPool.BLANK);
		long solutionId = 0;
		try {
			Sprint sprint = sprintService.find(sprintId);
			
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			if(ideaScrum != null){
				solutionId =  ideaScrum.getSolutionId();
			}
			//update afterwards sprints
			SolutionScrumUtil.updateComingSprintAfterDelete(sprint, sprintId, solutionId, ideaScrum.getGroupId());
			sprintService.delete(sprintId);
			
			model.addAttribute("noOfSprints", sprintService.noofSprints(solutionId));
			SessionMessages.add(actionRequest, SprintConstant.SPRINT_DELETED_SUCCESSFULLY);
			actionResponse.setRenderParameter("tab", tab);
		} catch (Exception e) {
			log.error("Sprint Not Deleted" + e.getMessage());
		}
	}
	public String editSprint(Locale locale, RenderRequest renderRequest,
			RenderResponse renderResponse, Model model){
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		long sprintId = ParamUtil.getLong(renderRequest, "sprintId");
		try{
			if (sprintId > 0) {
				Sprint sprint = sprintService.find(sprintId);
				model.addAttribute("sprint", sprint);
				model.addAttribute("sprintTitle", "Sprint "+sprint.getOrderIndex());
			}
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			long solutionId = 0;
			if(ideaScrum != null){
				solutionId =  ideaScrum.getSolutionId();
			}
			model.addAttribute("sprintTypes", IdeaScrumUtil.getSprintTypes());
			model.addAttribute("solutionScrumId", ideaScrum.getGroupId());
			model.addAttribute("solutionId", solutionId);
			model.addAttribute("isAction", "Edit");
		}catch(Exception e){
			log.error("Error in edit sprint Method" + e.getMessage());
		}
	
		return "inventor_prefs/createSprint";
	}
	public String createSprint(Locale locale, RenderRequest renderRequest, RenderResponse renderResponse, Model model){
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			//get ideascrum
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			long solutionId = 0;
			if(ideaScrum != null){
				solutionId =  ideaScrum.getSolutionId();
			}
			long sprintId = ParamUtil.getLong(renderRequest, "sprintId", 0);
			Sprint sprint = sprintService.find(sprintId);
			model.addAttribute("sprintTypes", IdeaScrumUtil.getSprintTypes());
			model.addAttribute("solutionScrumId", ideaScrum.getGroupId());
			model.addAttribute("solutionId", solutionId);
			model.addAttribute("sprintListSize", sprintService.sprintList().size());
			model.addAttribute("sprintTitle", "Sprint "+(sprint.getOrderIndex()+1));
			model.addAttribute("oldSprint", sprint);
			model.addAttribute("isAction", "Insert");
			if(sprintService.sprintList().size() > 0){
				model.addAttribute("lastSprintOrder", 0);
			}else{
				model.addAttribute("lastSprintOrder", 0);
			}
			
			
		} catch (Exception e){
			log.error("Error in getting create sprint view");
		}
		return "inventor_prefs/createSprint";
	}
	
	@ActionMapping(params = "action=saveSprint")
	public void saveSprint(Locale locale, ActionRequest actionRequest, ActionResponse actionResponse, Model model){
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			SessionErrors.add(actionRequest, SprintConstant.ERROR_SPRINT_LOG_IN);
			return;
		}
		String tab = ParamUtil.getString(actionRequest, "tab", StringPool.BLANK);
		String isAction = ParamUtil.getString(actionRequest, "isAction", StringPool.BLANK);
		long sprintId = ParamUtil.getLong(actionRequest, "sprintId", 0);
		String sprintTitle = ParamUtil.getString(actionRequest, "sprintTitle", StringPool.BLANK);
		String sprintType = ParamUtil.getString(actionRequest, "sprintType", StringPool.BLANK);
		String sprintText = ParamUtil.getString(actionRequest, "sprintText", StringPool.BLANK);
		String sprintVideo = ParamUtil.getString(actionRequest, "sprintVideo", StringPool.BLANK);
		String startDate = ParamUtil.getString(actionRequest, "sprintStartDate", StringPool.BLANK);
		int sprintLength = ParamUtil.getInteger(actionRequest, "sprintLength", 0);
		int sprintStatus = ParamUtil.getInteger(actionRequest, "sprintStatus", SprintConstant.STATUS_PENDING);
		long solutionScrumId = ParamUtil.getLong(actionRequest, "solutionScrumId", 0);
		long solutionId = ParamUtil.getLong(actionRequest, "solutionId", 0);
		int orderIndex = ParamUtil.getInteger(actionRequest, "orderIndex", 0);
		
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		Date sprintStartDate = null;
		try {
			sprintStartDate = formatter.parse(startDate);
		} catch (ParseException e) {
			log.error("Error in parsing date:"+e.getMessage());
		}
		
		Date sprintEndDate = SolutionScrumUtil.getNextDate(sprintStartDate, (sprintLength-1));
		Sprint sprint = null;

		if (sprintId > 0 && isAction.equals("Edit")) {
			sprint = sprintService.find(sprintId);
		} else {
			sprint = new Sprint();
			sprint.setCreatedDate(new Date());
			sprint.setOrderIndex(orderIndex+1);
		}
		sprint.setFocusTitle(sprintTitle);
		sprint.setStatus(sprintStatus);
		sprint.setType(sprintType);
		sprint.setText(sprintText);
		sprint.setVideoURL(sprintVideo);
		sprint.setStartDate(sprintStartDate);
		sprint.setDaysLength(sprintLength);
		sprint.setEndDate(sprintEndDate);
		sprint.setStatus(sprintStatus);
		sprint.setModifiedDate(new Date());
		sprint.setInventorId(themeDisplay.getUserId());
		sprint.setGroupId(themeDisplay.getScopeGroupId());
		sprint.setCompanyId(themeDisplay.getCompanyId());
		sprint.setIdeaScrumId(solutionScrumId);
		sprint.setIdeaId(0);
		sprint.setSolutionId(solutionId);
		
		if (sprintId > 0 && isAction.equals("Edit")) {
			sprint = sprintService.update(sprint);
		} else {
			sprintId = sprintService.create(sprint);
		}
		
		if (sprintId > 0 && isAction.equals("Edit")) {
			SolutionScrumUtil.updateAfterWardsSprint(sprint, sprint.getSprintId(), solutionId, solutionScrumId);
		} else {
			SolutionScrumUtil.updateAfterWardsSprint(sprint, sprintId, solutionId, solutionScrumId);
		}
		actionResponse.setRenderParameter("tab", tab);
	}
	private void sprintView(Locale locale, Model model,
			RenderRequest renderRequest, RenderResponse renderResponse,
			String tab) {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String view = ParamUtil.getString(renderRequest, "view", StringPool.BLANK);
		
		IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
		long solutionId = 0;
		if(ideaScrum != null){
			solutionId =  ideaScrum.getSolutionId();
		}
		String order = tab.split("-")[1];
		long sprintId = 0;
		if(Validator.isNotNull(order)){
			int orderIndex = Integer.parseInt(order);
			Sprint sprint = sprintService.getSprintByOrderIndex(orderIndex, solutionId, ideaScrum.getGroupId(),"solution");
			sprintId = sprint.getSprintId();
			model.addAttribute("sprint", sprint);
		}else{
			Sprint sprint = sprintService.getSprintByOrderIndex(1, solutionId, ideaScrum.getGroupId(),"solution");
			sprintId = sprint.getSprintId();
			model.addAttribute("sprint", sprint);
		}
		String tabInventerAvtar = IdeaUtil.getTabInventorAvtarURL(sprintId);
		
		String sprintViewJsp = "view";
		model.addAttribute("sprintViewJsp", sprintViewJsp);
		model.addAttribute("loggedInUserId", themeDisplay.getUserId());
		model.addAttribute("loggedInUserAvatarURL", IdeaUtil.getAvatarURL(themeDisplay.getUserId()));
		model.addAttribute("loggedInUserName", IdeaUtil.getUserName(themeDisplay.getUserId()));
		model.addAttribute("solutionId", solutionId);
		model.addAttribute("solutionScrumId", ideaScrum.getGroupId());
		model.addAttribute("inventorAvatarURL", tabInventerAvtar);
		model.addAttribute("inventorName", ideaScrum.getUserName());		
	}
	private void theSolutionView(Locale locale, Model model,
			RenderRequest renderRequest, RenderResponse renderResponse) {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String view = ParamUtil.getString(renderRequest, "view", StringPool.BLANK);
		
		IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
		long solutionId = 0;
		if(ideaScrum != null){
			solutionId =  ideaScrum.getSolutionId();
		}
		Solution solution = solutionService.find(solutionId);
		User user=null;
		String userIcon=StringPool.BLANK;
		String userDisplayURL = StringPool.BLANK;
		boolean solutionLike = false;
		long solutionLikeCount=0;
		Role role = null;
		
		try {
			user = UserLocalServiceUtil.getUser(solution.getCoInventorId());
			userIcon =  (user.getPortraitId() > 0 ?  IdeaUtil.getUserImagePath(user.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(user.getScreenName(), user.getCompanyId()));
			solutionLike = solutionLikeService.checkSolutionLike(solutionId,themeDisplay.getUserId());
			solutionLikeCount = solutionLikeService.getCountOfSolutionLike(solutionId);
			userDisplayURL = user.getDisplayURL(themeDisplay);
		
			model.addAttribute("solutionLikeCount", solutionLikeCount);
			model.addAttribute("wantAnswerCount", solutionAnswerVoteService.getWantAnswerCount(solutionId));
			model.addAttribute("solutionLike", solutionLike);
			model.addAttribute("solution", solution);
			model.addAttribute("userAvtar", userIcon);
			model.addAttribute("userDisplayURL", userDisplayURL);
		} catch (PortalException e) {
			log.error("Error in getting view Solution Method"+e.getMessage());
		} catch (SystemException e) {
			log.error("Error in getting view Solution Method"+e.getMessage());
		}
		model.addAttribute("solution", solution);

		String theSolutionViewJsp = "view";
		model.addAttribute("theSolutionViewJsp", theSolutionViewJsp);
	}
	
	
	@ResourceMapping(value = "getSprintDataURL")
	public String getSprintDataURL(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model){
		
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		
		int recordsSize = Integer.parseInt(request.getParameter("recordsPerPage"));
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		String fieldname = request.getParameter("fieldName");
		String orderBy = request.getParameter("orderBy");
		long solutionId = Long.parseLong(request.getParameter("solutionId"));
		long solutionScrumId = Long.parseLong(request.getParameter("solutionScrumId"));

		long start = (long) (pageNo * recordsSize) - recordsSize;
		try{
			List<Sprint> sprintList = sprintService.getListofSolutionSprint(start, recordsSize, fieldname, orderBy, solutionId, solutionScrumId);
			if (sprintList.size() > 0) {
				model.addAttribute("sprintService", sprintService);
				model.addAttribute("sprintList", sprintList);
			}
		}catch(Exception e){
			log.error("Error in getting Sprint Data Using Ajax Method:::"+e.getMessage());
		}
		return "inventor-prefs/sprint_response";
	}
	@ResourceMapping(value="solutionComment")
	public String solutionComment(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
				 HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
				 long solutionId = ParamUtil.getLong(request, "solutionId");
				 try{
					 List<SolutionComment> solutionComments = solutionCommentService.getListOfSolutionComment(solutionId);
						
					 List<SolutionCommentView> commentViews = new ArrayList<SolutionCommentView>();
					 SolutionCommentView commentView;
					 for (SolutionComment solution: solutionComments) {
						 commentView = new SolutionCommentView();
						 User user=null;;
						try {
							user = UserLocalServiceUtil.getUser(solution.getUserId());
						} catch (PortalException e) {
							log.error("Error in Getting User in Solution Comment Method"+e.getMessage());
						}catch(SystemException e){
							log.error("Error in Getting User in Solution Comment Method"+e.getMessage());
						}
						 commentView.setUserName(user.getFullName());
						 commentView.setAvtarUrl( user.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(user.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(user.getScreenName(), user.getCompanyId()));
						 commentView.setComment(solution.getDescription());
						 commentView.setCommentDate(solution.getCreatedDate());
						 commentViews.add(commentView);
					 }
					 model.addAttribute("commentCount", solutionComments.size());
					 model.addAttribute("commentViews", commentViews);
				 }catch(Exception e){
					 log.error("Error in Getting Solution Comment Method"+e.getMessage());
				 }
				 return "the-solution/solution_Scrum_Comment_template";
	} 
	
	@ResourceMapping(value="solutionAnswer")
	public String solutionAnswer(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
			HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
			long solutionId = ParamUtil.getLong(request, "solutionId");
			try{
				List<SolutionAnswer> totalSolutionAnswers = solutionAnswerService.getSolutionAnswers(solutionId);
				long ansCount = ParamUtil.getLong(request,"ansCount");
				String ajexType = ParamUtil.getString(request, "ajexType");
				long recordsize = 3;
				long start =  (ansCount * recordsize) - recordsize;
				
				List<SolutionAnswer> solutionAnswers = null;
				if("getMore".equalsIgnoreCase(ajexType)){
					solutionAnswers = solutionAnswerService.getSolutionAnswers(start, recordsize, solutionId);
				}else{
					solutionAnswers = solutionAnswerService.getSolutionAnswers(0, 1, solutionId);
				}
				
				List<SolutionAnswerView> answerViews = new ArrayList<SolutionAnswerView>();
				SolutionAnswerView solutionAnswerView;
				for (SolutionAnswer solutionAnswer : solutionAnswers) {
					 solutionAnswerView = new SolutionAnswerView();
					 solutionAnswerView.setAnswerId(solutionAnswer.getAnswerId());;
					 User user = UserLocalServiceUtil.getUser(solutionAnswer.getUserId());
					 solutionAnswerView.setUserName(user.getFullName());
					 solutionAnswerView.setAvtarUrl(user.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(user.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(user.getScreenName(), user.getCompanyId()));
					 solutionAnswerView.setComment(solutionAnswer.getDescription());
					 solutionAnswerView.setCommentDate(solutionAnswer.getCreatedDate());
					 solutionAnswerView.setUpvoteDisplayUsers(upvoteDisplayUserList(solutionAnswer.getSolution().getSolutionId(), solutionAnswer.getAnswerId()));
					 solutionAnswerView.setUpvoteMoreUsers(upvoteMoreUserList(solutionAnswer.getSolution().getSolutionId(), solutionAnswer.getAnswerId()));
					 solutionAnswerView.setSolutionId(solutionAnswer.getSolution().getSolutionId());
					 answerViews.add(solutionAnswerView);
				 }
				 if(solutionAnswers.size() > 1){
					 model.addAttribute("answerCount", totalSolutionAnswers.size()+" Answers");
				 }else{
					 model.addAttribute("answerCount", totalSolutionAnswers.size()+" Answer");
				 }
				 model.addAttribute("answerSize", totalSolutionAnswers.size());
				 model.addAttribute("solutionAnswerVoteService", solutionAnswerVoteService);
				 model.addAttribute("solutionAnswerVoteService", solutionAnswerVoteService);
				 model.addAttribute("solutionAnswerCommentService", solutionAnswerCommentService);
				 model.addAttribute("solutionCommentDAO", solutionCommentService);
				 model.addAttribute("answerList", answerViews);
			}catch(Exception e){
				log.error("Error in Getting Solution Answer Data Method"+e.getMessage());
			}
			return "the-solution/solution_Scrum_Answer_template";
	}
	private String upvoteDisplayUserList(long solutionId, long answerId){
		String userList = StringPool.BLANK;
		StringBuilder upvoteUserList = new StringBuilder();
		try{
			List<SolutionAnswerVote>ansVoteList = solutionAnswerVoteService.getAnsVoteListBySolutionIdAndAnswerId(solutionId, answerId);
			for(SolutionAnswerVote ansVote : ansVoteList){
				try {
					upvoteUserList.append(UserLocalServiceUtil.getUser(ansVote.getUserId()).getFullName() + ",");
				} catch (PortalException e) {
					log.error("Error in Getting User in upVoteDisplayUserList Method"+e.getMessage());
				}catch(SystemException e){
					log.error("Error in Getting User in upVoteDisplayUserList Method"+e.getMessage());
				}
			}
			List<String>displayUserList = Arrays.asList(upvoteUserList.toString().split(","));
			if(displayUserList.size() > 5 ){
				List<String>userSubList = displayUserList.subList(0, 5);
				for (String usr: userSubList) {
					userList = userList + usr + ",";
				}
			}else{
				for (String usr : displayUserList) {
					userList = userList + usr + ",";
				}
			}
			if(!StringPool.BLANK.equalsIgnoreCase(userList)){
				userList = userList.substring(0, userList.length() - 1);
				userList = userList + ".";
			}
		}catch(Exception e){
			log.error("Error in Upvote Display UserList Method");
		}
		return userList;
	}
	private String upvoteMoreUserList(long solutionId, long answerId){
		String userList = StringPool.BLANK;
		StringBuilder upvoteUsers = new StringBuilder();
		try{
			List<SolutionAnswerVote>ansVoteList = solutionAnswerVoteService.getAnsVoteListBySolutionIdAndAnswerId(solutionId, answerId);
			for(SolutionAnswerVote ansVote : ansVoteList){
				try {
					upvoteUsers.append(UserLocalServiceUtil.getUser(ansVote.getUserId()).getFullName() + ",");
				} catch (PortalException e) {
					log.error("Error In Getting UpVote More User List Method"+e.getMessage());
				}catch(SystemException e){
					log.error("Error In Getting UpVote More User List Method"+e.getMessage());
				}
			}
			List<String>upvoteUserList = Arrays.asList(upvoteUsers.toString().split(","));
			if(upvoteUserList.size() > 5){
				List<String>subList = upvoteUserList.subList(5, upvoteUserList.size());
				for (String usr : subList) {
					userList = userList + usr + ",";
				}
			}
			if(!StringPool.BLANK.equalsIgnoreCase(userList)){
				userList = userList.substring(0, userList.length() - 1);
				userList = userList + ".";
			}
		}catch(Exception e){
			log.error("Error In Getting UpVote More User List Method"+e.getMessage());
		}
		return userList;
	}
	@ResourceMapping(value="solutionAnswerCmtDiv")
	public String solutionAnswerCmtDiv(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		 HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		 
		 long solutionAnswerId = ParamUtil.getLong(request, "solutionAnswerId");
		 try{
			 List<SolutionAnswerComment> comments = solutionAnswerCommentService.getListOfComment(solutionAnswerId);
			 List<SolutionCommentView> commentViews = new ArrayList<SolutionCommentView>();
			 SolutionCommentView commentView;
			
			 for (SolutionAnswerComment solutionAnswerComment : comments) {
				 commentView = new SolutionCommentView();
				 User user=null;
				try {
					user = UserLocalServiceUtil.getUser(solutionAnswerComment.getUserId());
				} catch (PortalException e) {
					log.error("Error in getting User in solutionAnswerCmtDiv Method");
				}catch(SystemException e){
					log.error("Error in getting User in solutionAnswerCmtDiv Method");
				}
				 commentView.setUserName(user.getFirstName());
				 commentView.setAvtarUrl(user.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(user.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(user.getScreenName(), user.getCompanyId()));
				 commentView.setCommentDate(solutionAnswerComment.getCommentDate());
				 commentView.setComment(solutionAnswerComment.getDescription());
				 commentViews.add(commentView);
			 }
			 model.addAttribute("commentView", commentViews);
		 }catch(Exception e){
			 log.error("Error in Getting solutionAnswerCmtDiv Method");
		 }
		 return "the-solution/solution_answer_commet_template";
	}
	
	@ResourceMapping(value = "getDiscussionsURL")
	public void getDiscussionsURL(ResourceRequest request,
			ResourceResponse response,@RequestParam("solutionScrumId") long solutionScrumId, 
			@RequestParam("solutionId") long solutionId, @RequestParam("sprintId") long sprintId) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		JSONArray firstLevelJSONArray = JSONFactoryUtil.createJSONArray();
		JSONArray secondLevelJSONArray = null;
		JSONArray thirdLevelJSONArray = null;
		JSONObject firstLevelJSONObj = null;
		JSONObject secondLevelJSONObj = null;
		JSONObject thirdLevelJSONObj = null;
	
		long coInventorId =solutionService.find(solutionId).getCoInventorId();
		
		boolean isInventorPrefsAdmin =  IdeaScrumUtil.isInventorPrefsPermission(themeDisplay.getCompanyId(), themeDisplay.getUserId(), coInventorId);
		
		boolean userThanks = true;
		boolean isThanks = false;
		List<SolutionScrumDiscussion> firstLevelDiscussions = new ArrayList<SolutionScrumDiscussion>();
		try{
			firstLevelDiscussions = solutionService.getChildSolutionScrumDiscussion(solutionScrumId,solutionId,sprintId,0,0);
			for (SolutionScrumDiscussion firstLevelDiscussion : firstLevelDiscussions) {
				if(isInventorPrefsAdmin){
		 			userThanks = solutionScrumThanksService.checkUserThanks(firstLevelDiscussion.getScrumDiscussionId(),themeDisplay.getUserId());
		 		}else{
		 			userThanks = false;
		 		}
				firstLevelJSONObj = JSONFactoryUtil.createJSONObject();
				firstLevelJSONObj.put("ideaScrumDiscussionId", firstLevelDiscussion.getScrumDiscussionId());
				firstLevelJSONObj.put("message", firstLevelDiscussion.getMessage());
				firstLevelJSONObj.put("avatarURL", firstLevelDiscussion.getAvatarURL());
				firstLevelJSONObj.put("userId", firstLevelDiscussion.getUserId());
				firstLevelJSONObj.put("userName", firstLevelDiscussion.getUserName());
				firstLevelJSONObj.put("isChild", firstLevelDiscussion.isChild());
				firstLevelJSONObj.put("parentDiscussionId", firstLevelDiscussion.getParentScrumDiscussionId());
				firstLevelJSONObj.put("levelCount", 1);
				firstLevelJSONObj.put("level", "first");
				firstLevelJSONObj.put("isInventorPrefsAdmin", isInventorPrefsAdmin);
				firstLevelJSONObj.put("isThanks", userThanks);
				secondLevelJSONArray = JSONFactoryUtil.createJSONArray();
				if(firstLevelDiscussion.isChild()){
					List<SolutionScrumDiscussion> secondLevelDiscussions = solutionService.getChildSolutionScrumDiscussion(solutionScrumId, solutionId, sprintId, firstLevelDiscussion.getScrumDiscussionId(), 1);
						for (SolutionScrumDiscussion secondLevelDiscussion : secondLevelDiscussions) {
							if(isInventorPrefsAdmin){
								userThanks = solutionScrumThanksService.checkUserThanks(secondLevelDiscussion.getScrumDiscussionId(),themeDisplay.getUserId());
							}else{
								userThanks = false;
							}
							secondLevelJSONObj = JSONFactoryUtil.createJSONObject();
							secondLevelJSONObj.put("ideaScrumDiscussionId", secondLevelDiscussion.getScrumDiscussionId());
							secondLevelJSONObj.put("message", secondLevelDiscussion.getMessage());
							secondLevelJSONObj.put("avatarURL", secondLevelDiscussion.getAvatarURL());
							secondLevelJSONObj.put("userId", secondLevelDiscussion.getUserId());
							secondLevelJSONObj.put("userName", secondLevelDiscussion.getUserName());
							secondLevelJSONObj.put("isChild", secondLevelDiscussion.isChild());
							secondLevelJSONObj.put("parentDiscussionId", secondLevelDiscussion.getParentScrumDiscussionId());
							secondLevelJSONObj.put("levelCount", 2);
							secondLevelJSONObj.put("level", "second");
							secondLevelJSONObj.put("isInventorPrefsAdmin", isInventorPrefsAdmin);
							secondLevelJSONObj.put("isThanks", userThanks);
							thirdLevelJSONArray = JSONFactoryUtil.createJSONArray();
							if(secondLevelDiscussion.isChild()){
								List<SolutionScrumDiscussion> thirdLevelDiscussions = solutionService.getChildSolutionScrumDiscussion(solutionScrumId, solutionId, sprintId, secondLevelDiscussion.getScrumDiscussionId(), 2);
								if(Validator.isNotNull(thirdLevelDiscussions)){
									for (SolutionScrumDiscussion thirdLevelDiscussion : thirdLevelDiscussions) {
										if(isInventorPrefsAdmin){
											userThanks = solutionScrumThanksService.checkUserThanks(thirdLevelDiscussion.getScrumDiscussionId(),themeDisplay.getUserId());
										}else{
											userThanks = false;
										}
										thirdLevelJSONObj = JSONFactoryUtil.createJSONObject();
										thirdLevelJSONObj.put("ideaScrumDiscussionId", thirdLevelDiscussion.getScrumDiscussionId());
										thirdLevelJSONObj.put("message", thirdLevelDiscussion.getMessage());
										thirdLevelJSONObj.put("avatarURL", thirdLevelDiscussion.getAvatarURL());
										thirdLevelJSONObj.put("userId", thirdLevelDiscussion.getUserId());
										thirdLevelJSONObj.put("userName", thirdLevelDiscussion.getUserName());
										thirdLevelJSONObj.put("isChild", thirdLevelDiscussion.isChild());
										thirdLevelJSONObj.put("parentDiscussionId", thirdLevelDiscussion.getParentScrumDiscussionId());
										thirdLevelJSONObj.put("levelCount", 3);
										thirdLevelJSONObj.put("level", "third");
										thirdLevelJSONObj.put("isInventorPrefsAdmin", isInventorPrefsAdmin);
										thirdLevelJSONObj.put("isThanks", userThanks);
										thirdLevelJSONArray.put(thirdLevelJSONObj);
									}
								}
							}
							secondLevelJSONObj.put("thirdLevelMessages", thirdLevelJSONArray);
							secondLevelJSONArray.put(secondLevelJSONObj);
						}
				}
				firstLevelJSONObj.put("secondLevelMessages", secondLevelJSONArray);
				firstLevelJSONArray.put(firstLevelJSONObj);
			}
		}catch(Exception e){
			log.error("Error while getting discussion objects:"+e.getMessage());
		}
		try {
			response.getWriter().write(firstLevelJSONArray.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@ResourceMapping(value = "sendMessageURL")
	public void sendMessageURL(ResourceRequest request,
			ResourceResponse response,
			@RequestParam("message") String message,
			@RequestParam("solutionScrumId") long solutionScrumId,
			@RequestParam("solutionId") long solutionId,
			@RequestParam("sprintId") long sprintId,
			@RequestParam("parentDiscussionId") long parentDiscussionId,
			@RequestParam("level") int level,@RequestParam("ideaScrumDiscussionId") long ideaScrumDiscussionId) {
		JSONArray messageJSONArray = JSONFactoryUtil.createJSONArray();
		JSONObject messsageJSONObj = null;
		
		long coInventorId = solutionService.find(solutionId).getCoInventorId();
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isInventorPrefsAdmin =  IdeaScrumUtil.isInventorPrefsPermission(themeDisplay.getCompanyId(), themeDisplay.getUserId(), coInventorId);
		boolean userThanks = true;
		boolean isThanks = false;
		long userId = themeDisplay.getUserId();
		long companyId = themeDisplay.getCompanyId();
		long solutionDiscussionId = 0;
		try{
			SolutionScrumDiscussion scrumDiscussion = new SolutionScrumDiscussion();
			
			scrumDiscussion.setMessage(message);
			scrumDiscussion.setSolutionScrumId(solutionScrumId);;
			scrumDiscussion.setSolutionId(solutionId);;
			scrumDiscussion.setSprintId(sprintId);
			scrumDiscussion.setUserId(userId);
			scrumDiscussion.setUserName(themeDisplay.getUser().getFullName());
			scrumDiscussion.setCreatedDate(new Date());
			scrumDiscussion.setModifiedDate(new Date());
			scrumDiscussion.setParentScrumDiscussionId(ideaScrumDiscussionId);
			if(ideaScrumDiscussionId > 0){
				SolutionScrumDiscussion parentDiscussion = solutionService.getSolutionScrumDiscussion(ideaScrumDiscussionId);
				parentDiscussion.setChild(true);
				solutionService.updateSolutionScrumDiscussion(parentDiscussion);
			}
			scrumDiscussion.setLevel(level);
			scrumDiscussion.setChild(false);
			scrumDiscussion.setCompanyId(companyId);
			solutionDiscussionId = solutionService.createSolutionScrumDiscussion(scrumDiscussion);
			
			messsageJSONObj = JSONFactoryUtil.createJSONObject();
			messsageJSONObj.put("ideaScrumDiscussionId", scrumDiscussion.getScrumDiscussionId());
			messsageJSONObj.put("message", message);
			messsageJSONObj.put("avatarURL", scrumDiscussion.getAvatarURL());
			messsageJSONObj.put("userId", scrumDiscussion.getUserId());
			messsageJSONObj.put("userName", scrumDiscussion.getUserName());
			messsageJSONObj.put("parentDiscussionId", scrumDiscussion.getParentScrumDiscussionId());
			messsageJSONObj.put("isChild", scrumDiscussion.isChild());
			if(isInventorPrefsAdmin){
				userThanks = solutionScrumThanksService.checkUserThanks(scrumDiscussion.getScrumDiscussionId(),themeDisplay.getUserId());
			}else{
				userThanks = false;
			}
			if(scrumDiscussion.getLevel() == 0){
				messsageJSONObj.put("levelCount", 1);
				messsageJSONObj.put("level", "first");
			}else if(scrumDiscussion.getLevel() == 1){
				messsageJSONObj.put("levelCount", 2);
				messsageJSONObj.put("level", "second");
			}else if(scrumDiscussion.getLevel() == 2){
				messsageJSONObj.put("levelCount", 3);
				messsageJSONObj.put("level", "third");
			}
			messsageJSONObj.put("isInventorPrefsAdmin",isInventorPrefsAdmin);
			messsageJSONObj.put("isThanks", userThanks);
			messageJSONArray.put(messsageJSONObj);
		}catch(Exception e){
			log.error("Error while saving ideascrumdiscussion message:"+e.getMessage());
		}
		try {
			response.getWriter().write(messageJSONArray.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@ResourceMapping(value="discussionThanksURL")
	public void discussionThanksURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,@RequestParam("solutionScrumId") long solutionScrumId,
			@RequestParam("solutionId") long solutionId,@RequestParam("sprintId") long sprintId,@RequestParam("ideaScrumDiscussionId") long ideaScrumDiscussionId){
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		//ScrumDiscussionThanks thanks =null;
		SolutionScrumThanks scrumThanks = null;
		try{
			scrumThanks = new SolutionScrumThanks();	
			scrumThanks.setCompanyId(themeDisplay.getCompanyId());
			scrumThanks.setCreatedDate(new Date());
			scrumThanks.setSolutionScrumId(solutionScrumId);;
			scrumThanks.setScrumDiscussionId(ideaScrumDiscussionId);
			scrumThanks.setSprintId(sprintId);
			scrumThanks.setThanksCreator(themeDisplay.getUserId());
			solutionScrumThanksService.create(scrumThanks);
		}catch(Exception e){
			log.info("Error in Updating New Discussion Thanks "+e.getMessage());
		} 
	}
	
	@ResourceMapping(value = "moveSptintDataURL")
	public void moveSptintDataURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		
			long sprintId = ParamUtil.getLong(request, "rowId");
			long solutionId = ParamUtil.getLong(request, "solutionId");
			long solutionScrumId = ParamUtil.getLong(request, "solutionScrumId");
			String arrowType = ParamUtil.getString(request, "arrowType");
			
			Sprint sprint = sprintService.find(sprintId);
			
			long currentSprintOrder = sprint.getOrderIndex();
			if(arrowType.equalsIgnoreCase("UP")){
				int sPreviousSprintOrder = (int)currentSprintOrder - 2;
				
				Sprint sPreviousSprint = sprintService.findPreviousSolutionSPrintFromOrderIndex(sPreviousSprintOrder, solutionId, solutionScrumId);
				
				Date sprintStartDate = SolutionScrumUtil.getNextDate(sPreviousSprint.getEndDate(), 1);
				Date sprintEndDate = SolutionScrumUtil.getNextDate(sprintStartDate, (sprint.getDaysLength() - 1));
				sprint.setStartDate(sprintStartDate);
				sprint.setEndDate(sprintEndDate);
				sprint.setOrderIndex(sprint.getOrderIndex() - 1);
				sprint.setModifiedDate(new Date());
				sprintService.update(sprint);
				
				SolutionScrumUtil.updateAfterWardsSprint(sprint, sprint.getSprintId(), solutionId, solutionScrumId);
			}else if(arrowType.equalsIgnoreCase("DOWN")){
				int nextSprintOrder = (int) (currentSprintOrder + 1);
				int previousSprintOrder = (int)currentSprintOrder - 1;
				
				Sprint nextSprint  = sprintService.findSolutionNextSprintFromOrderIndex(nextSprintOrder,solutionId,solutionScrumId);
				Sprint previousSprint  = sprintService.findPreviousSolutionSPrintFromOrderIndex(previousSprintOrder,solutionId,solutionScrumId);
				
				Date sprintStartDate = IdeaScrumUtil.getNextDate(previousSprint.getEndDate(), 1);
				Date sprintEndDate = IdeaScrumUtil.getNextDate(sprintStartDate, (nextSprint.getDaysLength()-1));
				nextSprint.setStartDate(sprintStartDate);
				nextSprint.setEndDate(sprintEndDate);
				nextSprint.setOrderIndex(nextSprint.getOrderIndex()-1);
				nextSprint.setModifiedDate(new Date());
				sprintService.update(nextSprint);
				
				SolutionScrumUtil.updateAfterWardsSprint(nextSprint, nextSprint.getSprintId(), solutionId, solutionScrumId);
			}
	}
	@ResourceMapping(value="solutionAnswerVoteURL")
	public void solutionAnswerVoteURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
			   HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
			   ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			   long solutionAnswerId = ParamUtil.getLong(request, "solutionAnswerId",0);
			   long solutionId = ParamUtil.getLong(request, "solutionId", 0);
			   String answerVote = ParamUtil.getString(request, "answerVote");
			   try{
				   SolutionAnswer solutionAnswer = solutionAnswerService.find(solutionAnswerId);
				   if(answerVote.equalsIgnoreCase("UpVote")){
				    SolutionAnswerVote solutionAnswerVote = new SolutionAnswerVote();
				    solutionAnswerVote.setVoteDate(new Date());
				    solutionAnswerVote.setSolutionAnswer(solutionAnswer);
				    solutionAnswerVote.setUserId(themeDisplay.getUserId());
				    solutionAnswerVote.setSolutionId(solutionId);
				    solutionAnswerVoteService.create(solutionAnswerVote);
				   }else if(answerVote.equalsIgnoreCase("DownVote")){
				    SolutionAnswerVote solutionAnswerVote = solutionAnswerVoteService.getSolutionAnswerVote(solutionAnswerId,themeDisplay.getUserId());
				    solutionAnswerVoteService.delete(solutionAnswerVote.getSolutionAnswerVoteId());
				   }
				  
				   /*display total upvote and upvote user list for perticular ans of solution*/
				   long answerVoteCount = solutionAnswerVoteService.getCountOfSolutionAnswerVote(solutionAnswerId);
				   
				   String upvoteDisplayUser = upvoteDisplayUserList(solutionId, solutionAnswerId);
				   String upvoteMoreUser = upvoteMoreUserList(solutionId, solutionAnswerId);
				   
				   JSONObject resultObj = JSONFactoryUtil.createJSONObject();
				   resultObj.put("upvoteDisplayUser", upvoteDisplayUser);
				   resultObj.put("upvoteMoreUser", upvoteMoreUser);
				   resultObj.put("answerVoteCount", answerVoteCount);
				   resultObj.put("wantAnswerCount", solutionAnswerVoteService.getWantAnswerCount(solutionId));
				  
				   StringBuilder countAnswerVote = new StringBuilder();
				   countAnswerVote.append(answerVoteCount);
				   
				   PrintWriter pw = null;
				   try {
					   pw = resourceResponse.getWriter();
					   pw.print(resultObj.toString());
					   pw.flush();
				   } catch (IOException e) {
					   e.printStackTrace();
				   } finally {
					   pw.close();
				   }
			   }catch(Exception e){
				   log.error("Error in Getting Solution Answer Vote Url Method"+e.getMessage());
			   }
	}
	
}
