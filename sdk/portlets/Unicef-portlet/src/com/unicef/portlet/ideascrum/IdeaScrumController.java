package com.unicef.portlet.ideascrum;

import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.unicef.*;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.IdeaComment;
import com.unicef.domain.IdeaCommentView;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.domain.IdeaHistory;
import com.unicef.domain.IdeaRate;
import com.unicef.domain.IdeaRateCriteria;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.IdeaScrumDiscussion;
import com.unicef.domain.Like;
import com.unicef.domain.ScrumDiscussionThanks;
import com.unicef.domain.Scrum_Discussion_Like;
import com.unicef.domain.SocialWorkflow;
import com.unicef.domain.Sprint;
import com.unicef.domain.TeamMember;
import com.unicef.service.IdeaAttachementService;
import com.unicef.service.IdeaCommentService;
import com.unicef.service.IdeaCommentVoteService;
import com.unicef.service.IdeaHistoryService;
import com.unicef.service.IdeaRateService;
import com.unicef.service.IdeaService;
import com.unicef.service.IdeaViewService;
import com.unicef.service.LikeService;
import com.unicef.service.ScrumDiscussionLikeService;
import com.unicef.service.ScrumDiscussionThanksService;
import com.unicef.service.SocialWorkflowService;
import com.unicef.service.SprintService;
import com.unicef.service.TeamMemberService;
import com.unicef.util.IdeaConstant;
import com.unicef.util.IdeaScrumDiscussionTitleDescription;
import com.unicef.util.IdeaScrumUtil;
import com.unicef.util.IdeaToScrumRoleConstant;
import com.unicef.util.IdeaUtil;
import com.unicef.util.MemberNotificationHandler;
import com.unicef.util.SocialWorkflowEnum;
import com.unicef.util.SprintConstant;

import java.io.File;
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
import java.util.Map;

import javax.inject.Inject;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

@Controller
@RequestMapping("VIEW")
public class IdeaScrumController {
	
	@Autowired
	private IdeaRateService ideaRateService;
	
	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private IdeaHistoryService ideaHistoryService;
	
	@Autowired
	private IdeaAttachementService ideaAttachementService;
	
	@Autowired
	private IdeaViewService ideaViewService;
	
	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private IdeaCommentService ideaCommentService;
	
	@Autowired
	private IdeaCommentVoteService commentVoteService;
	
	@Autowired ScrumDiscussionThanksService thanksService;
	
	@Autowired
	private SocialWorkflowService socialWorkflowService;
	
	@Autowired
	private ScrumDiscussionLikeService scrumDiscussionLikeService;
	
	@Autowired
	private TeamMemberService teamMemberService;
	
	/*@Autowired
	protected SessionFactory sessionFactory;
	*/
	
	private static final Log log = LogFactoryUtil.getLog(IdeaScrumController.class);
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = 0;
		try{
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			if(ideaScrum != null){
				ideaId =  ideaScrum.getIdeaId();
			}
			System.out.println(ideaId);
			Idea currentIdea = ideaService.find(ideaId);
			
			long coInventorId = currentIdea.getCoInventorId();
			
			boolean isInventorPrefsAdmin =  IdeaScrumUtil.isInventorPrefsPermission(themeDisplay.getCompanyId(), themeDisplay.getUserId(), coInventorId);
			
			List<Sprint> sprints = new ArrayList<Sprint>();
			sprints = sprintService.getSprintListByIdeaIdAndIdeaScrumId(ideaId, ideaScrum.getGroupId());
			
			
			// Ideascrum tab structure
				String tabNames = "The Idea";
				String tabValues = "the-idea";
				
				tabNames = tabNames + StringPool.COMMA + "Understand";
			 	tabValues = tabValues + StringPool.COMMA + "Understand";
				
			 	tabNames = tabNames + StringPool.COMMA + "Diverge";
			 	tabValues = tabValues + StringPool.COMMA + "Diverge";  
			 	
			 	tabNames = tabNames + StringPool.COMMA + "Converge";
			 	tabValues = tabValues + StringPool.COMMA + "Converge";  
			 	
			 	tabNames = tabNames + StringPool.COMMA + "Prototype";
			 	tabValues = tabValues + StringPool.COMMA + "Prototype";  
			 	
			 	tabNames = tabNames + StringPool.COMMA + "Validate";
			 	tabValues = tabValues + StringPool.COMMA + "Validate";  
	
			 	tabNames = tabNames + StringPool.COMMA + "Innospective";
				tabValues = tabValues + StringPool.COMMA + "innospective";
				
				if(Validator.isNotNull(sprints)){
					for(Sprint sprint : sprints){
						if(sprint.getType().equals("Rating(Required)")){
							 tabNames = tabNames + StringPool.COMMA + "Rating this Idea";
							 tabValues = tabValues + StringPool.COMMA + "rating-this-idea";
						 }
					}
				}

				// scrum prefs tab for idea owner
				if(isInventorPrefsAdmin) {
					tabNames = tabNames + StringPool.COMMA + "Scrum Prefs"; 
					tabValues = tabValues + StringPool.COMMA + "inventor-prefs"; 
				}
				
			String tab = ParamUtil.getString(renderRequest, "tab", "the-idea");
		
			if(tab.equals("the-idea")){
				theIdeaView(locale, model, renderRequest, renderResponse);
			}else if(tab.equals("rating-this-idea")){
				rateThisIdeaView(locale, model, renderRequest, renderResponse);
			}else if(tab.equals("inventor-prefs")){
				inventorPrefsView(locale, model, renderRequest, renderResponse);
			}else{
				sprintView(locale, model, renderRequest, renderResponse, tab);
			}
			
			String viewJsp = StringPool.BLANK;
			
			if(tab.contains("Understand") || tab.contains("Diverge") || tab.contains("Converge")
					|| tab.contains("Prototype") || tab.contains("Validate")){
				viewJsp = "sprint";
			} else if(tab.equalsIgnoreCase("Innospective")) {
				viewJsp = "sprint";
			} else{
				viewJsp = tab;
			}
			
			model.addAttribute("idea", currentIdea);
			model.addAttribute("viewJsp", viewJsp);
			model.addAttribute("isInventorPrefsAdmin", isInventorPrefsAdmin);
			model.addAttribute("tabNames", tabNames);
			model.addAttribute("tabValues", tabValues);
			
			List<TeamMember> teamMembers = teamMemberService.findMembersByIdeaId(ideaId);
			
			for(int ii=0;ii<teamMembers.size();ii++){			
				String abc =String.valueOf(teamMembers.get(ii).getRoleName());				
				if(abc.contains("IdeaScrum")){	
				  abc=abc.replace("IdeaScrum", "Dsprint");				  
				  teamMembers.get(ii).setRoleName(abc);
				}
				
				
			}
			
			if(Validator.isNotNull(teamMembers)) {
				model.addAttribute("teamMembers",teamMembers);
			}
			
			
			List<TeamMember> isMembershipRequired = teamMemberService.findByUserId(themeDisplay.getUserId(), ideaId);
			
			model.addAttribute("isMember",isIdeaInventorORFacilitator(isMembershipRequired));
			

		}catch(Exception e){
			_log.error("Error in generating idea scrum view");
		}
		model.addAttribute("noOfSprints", sprintService.noofSprints(ideaId));
		
		return "view";
	}
	
	private static boolean isIdeaInventorORFacilitator(List<TeamMember> c) {
	    
		for(TeamMember o : c) {
			
			if (o.getRoleName().equals(IdeaToScrumRoleConstant.SCRUM_TEAM_MEMBER) || o.getRoleName().equals(IdeaToScrumRoleConstant.INVENTOR)  ) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private void innospectiveView(Locale locale, Model model,
			RenderRequest renderRequest, RenderResponse renderResponse) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String view = ParamUtil.getString(renderRequest, "view", StringPool.BLANK);
		
		String innoViewJsp = "view";
		
		IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
		long ideaId = 0;
		if(ideaScrum != null){
			ideaId =  ideaScrum.getIdeaId();
		}
		model.addAttribute("ideaId", ideaId);
		model.addAttribute("innoViewJsp", innoViewJsp );
	}

	public void inventorPrefsView(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException{
		
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
			saveAllSprints(locale, renderRequest, renderResponse, model);
		}
		else{
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			long ideaId = 0;
			if(ideaScrum != null){
				ideaId =  ideaScrum.getIdeaId();
			}
			
			model.addAttribute("sprintListSize", sprintService.sprintList().size());
			model.addAttribute("ideaId", ideaId);
			model.addAttribute("ideaScrumId", ideaScrum.getGroupId());
		}
		model.addAttribute("inventorPrefsJSP", inventorPrefsJSP);
	}

	public void saveAllSprints(Locale locale, RenderRequest renderRequest, RenderResponse renderResponse, Model model){
		try {
			//get ideascrum
			long ideaId = ParamUtil.getLong(renderRequest, "ideaId");
			long ideaScrumId = ParamUtil.getLong(renderRequest, "ideaScrumId");
			List<Sprint> sprints = sprintService.getSprintListByIdeaIdAndIdeaScrumId(ideaId, ideaScrumId);
			for(Sprint sprint : sprints){
				sprint.setModifiedDate(new Date());
				sprintService.update(sprint);
			}
			model.addAttribute("sprints-saved-success", Boolean.TRUE);
		} catch (Exception e){
			_log.error("Error in saving sprint "+e.getMessage());
		}
	}
	
	@ActionMapping(params="action=addToTeam")
	public void addToTeam(ActionRequest actionRequest, ActionResponse actionResponse) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long ideaId = ParamUtil.getLong(actionRequest, "ideaId");
		Idea idea = ideaService.find(ideaId);
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
			
			List<User> users = UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount());
			
		//	UserNotificationEventLocalServiceUtil eu;
		//	eu.fetchUserNotificationEvent(userNotificationEventId)
			
			boolean sendNotification = false;
			
			long InventorId = idea.getCoInventorId();
			
			for (User user : users) {
				List<Role> userRoles = user.getRoles();
				
				for(Role role : userRoles) {
					if("Administrator".equals(role.getName()) ||  InventorId==user.getUserId()) {
						JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
						payloadJSON.put("notificationType", 2);
						payloadJSON.put("actionRequired", Boolean.TRUE); 
						payloadJSON.put("userId", themeDisplay.getUserId());
						payloadJSON.put("user",themeDisplay.getUser().getFullName());
						payloadJSON.put("ideaId", ideaId);
						payloadJSON.put("ideaName", idea.getIdeaTitle());
						
						log.error("payload"+ payloadJSON);
						
						List<UserNotificationEvent> userNotifications = UserNotificationEventLocalServiceUtil.getUserNotificationEvents(user.getUserId());
						
						//check if usernotificationevent table is empty then
						if(userNotifications.size()== 0){
							
							sendNotification = true;
							
						}else{
						
							for(UserNotificationEvent userNotificationEvent : userNotifications) {
							
							log.error("already "+userNotificationEvent.getPayload()+"userId"+userNotificationEvent.getUserId());
							
							//convert payloadjson object in string 
							String check = payloadJSON.toString();
							
							if(check.equals(userNotificationEvent.getPayload())) {
								log.error("if part"); 
								sendNotification = false;
								break;
							} else {
								sendNotification=true;
								log.error("else part");
								break;
							}
						}
						}
						//int i = session.createSQLQuery("select * from usernotificationevent where payload="+payloadJSON).executeUpdate();
						
						if(sendNotification){
							
						UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
									user.getUserId(),MemberNotificationHandler.PORTLET_ID,
									(new Date()).getTime(), themeDisplay.getUserId()/*user.getUserId()*/,payloadJSON.toString(),false, serviceContext);
						}
						else{
							payloadJSON.put("notificationType", 3);
							payloadJSON.put("actionRequired", Boolean.FALSE); 
							payloadJSON.put("userId", themeDisplay.getUserId());
							payloadJSON.put("user",themeDisplay.getUser().getFullName());
							payloadJSON.put("ideaId", ideaId);
							payloadJSON.put("ideaName", idea.getIdeaTitle());
							
							UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
									themeDisplay.getUserId(),MemberNotificationHandler.PORTLET_ID,
									(new Date()).getTime(), themeDisplay.getUserId()/*user.getUserId()*/,payloadJSON.toString(),false, serviceContext);
						}
						break;
					} 
				}
			}
		
		} catch (Exception e) {
			log.info("error cause while sending notification " + e.getMessage());
		}

	}
	/*
	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	*/
	public String createSprint(Locale locale, RenderRequest renderRequest, RenderResponse renderResponse, Model model){
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			//get ideascrum
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			long ideaId = 0;
			if(ideaScrum != null){
				ideaId =  ideaScrum.getIdeaId();
			}
			long sprintId = ParamUtil.getLong(renderRequest, "sprintId", 0);
			Sprint sprint = sprintService.find(sprintId);
			model.addAttribute("sprintTypes", IdeaScrumUtil.getSprintTypes());
			model.addAttribute("ideaScrumId", ideaScrum.getGroupId());
			model.addAttribute("ideaId", ideaId);
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
			_log.error("Error in getting create sprint view");
		}
		return "inventor_prefs/createSprint";
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
			long ideaId = 0;
			if(ideaScrum != null){
				ideaId =  ideaScrum.getIdeaId();
			}
			model.addAttribute("sprintTypes", IdeaScrumUtil.getSprintTypes());
			model.addAttribute("ideaScrumId", ideaScrum.getGroupId());
			model.addAttribute("ideaId", ideaId);
			model.addAttribute("isAction", "Edit");
		}catch(Exception e){
			_log.error("Error in edit sprint Method" + e.getMessage());
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
		long ideaScrumId = ParamUtil.getLong(actionRequest, "ideaScrumId", 0);
		long ideaId = ParamUtil.getLong(actionRequest, "ideaId", 0);
		int orderIndex = ParamUtil.getInteger(actionRequest, "orderIndex", 0);
		
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		Date sprintStartDate = null;
		try {
			sprintStartDate = formatter.parse(startDate);
		} catch (ParseException e) {
			_log.error("Error in parsing date:"+e.getMessage());
		}
		
		Date sprintEndDate = IdeaScrumUtil.getNextDate(sprintStartDate, (sprintLength-1));
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
		sprint.setIdeaScrumId(ideaScrumId);
		sprint.setIdeaId(ideaId);
		
		if (sprintId > 0 && isAction.equals("Edit")) {
			sprint = sprintService.update(sprint);
		} else {
			sprintId = sprintService.create(sprint);
		}
		
		if (sprintId > 0 && isAction.equals("Edit")) {
			IdeaScrumUtil.updateAfterWardsSprint(sprint, sprint.getSprintId(), ideaId, ideaScrumId);
		} else {
			IdeaScrumUtil.updateAfterWardsSprint(sprint, sprintId, ideaId, ideaScrumId);
		}
		actionResponse.setRenderParameter("tab", tab);
	}
	
	
	@ResourceMapping(value = "moveSptintDataURL")
	public void moveSptintDataURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		
			long sprintId = ParamUtil.getLong(request, "rowId");
			long ideaId = ParamUtil.getLong(request, "ideaId");
			long ideaScrumId = ParamUtil.getLong(request, "ideaScrumId");
			String arrowType = ParamUtil.getString(request, "arrowType");
			
			Sprint sprint = sprintService.find(sprintId);
			
			long currentSprintOrder = sprint.getOrderIndex();
			if(arrowType.equalsIgnoreCase("UP")){
				int sPreviousSprintOrder = (int)currentSprintOrder - 2;
				
				Sprint sPreviousSprint = sprintService.findPreviousPrintFromOrderIndex(sPreviousSprintOrder, ideaId, ideaScrumId);
				
				Date sprintStartDate = IdeaScrumUtil.getNextDate(sPreviousSprint.getEndDate(), 1);
				Date sprintEndDate = IdeaScrumUtil.getNextDate(sprintStartDate, (sprint.getDaysLength() - 1));
				sprint.setStartDate(sprintStartDate);
				sprint.setEndDate(sprintEndDate);
				sprint.setOrderIndex(sprint.getOrderIndex() - 1);
				sprint.setModifiedDate(new Date());
				sprintService.update(sprint);
				
				IdeaScrumUtil.updateAfterWardsSprint(sprint, sprint.getSprintId(), ideaId, ideaScrumId);
			}else if(arrowType.equalsIgnoreCase("DOWN")){
				int nextSprintOrder = (int) (currentSprintOrder + 1);
				int previousSprintOrder = (int)currentSprintOrder - 1;
				
				Sprint nextSprint  = sprintService.findNextSprintFromOrderIndex(nextSprintOrder,ideaId,ideaScrumId);
				Sprint previousSprint  = sprintService.findPreviousPrintFromOrderIndex(previousSprintOrder,ideaId,ideaScrumId);
				
				Date sprintStartDate = IdeaScrumUtil.getNextDate(previousSprint.getEndDate(), 1);
				Date sprintEndDate = IdeaScrumUtil.getNextDate(sprintStartDate, (nextSprint.getDaysLength()-1));
				nextSprint.setStartDate(sprintStartDate);
				nextSprint.setEndDate(sprintEndDate);
				nextSprint.setOrderIndex(nextSprint.getOrderIndex()-1);
				nextSprint.setModifiedDate(new Date());
				sprintService.update(nextSprint);
				
				IdeaScrumUtil.updateAfterWardsSprint(nextSprint, nextSprint.getSprintId(), ideaId, ideaScrumId);
			}
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
		long ideaId = Long.parseLong(request.getParameter("ideaId"));
		long ideaScrumId = Long.parseLong(request.getParameter("ideaScrumId"));
		long start = (long) (pageNo * recordsSize) - recordsSize;
		
		try {
			List<Sprint> sprintList = sprintService.getListofSprint(start, recordsSize, fieldname, orderBy, ideaId, ideaScrumId);
			if (sprintList.size() > 0) {
				model.addAttribute("sprintService", sprintService);
				model.addAttribute("sprintList", sprintList);
			}
		}catch(Exception e){
			_log.error("Error in getting Sprint Data Using Ajax Method:::"+e.getMessage());
		}
		return "inventor-prefs/sprint_response";
	}
	
	public void sprintView(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse, String tab) throws PortalException{
		
		final String[] HARD_CODED_STRING_STRUCTURE = {"Understand","Diverge","Converge","Prototype","Validate","innospective"};
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
		long ideaId = 0;
		if(ideaScrum != null){
			ideaId =  ideaScrum.getIdeaId();
		}
		int orderIndex = Arrays.asList(HARD_CODED_STRING_STRUCTURE).indexOf(tab) + 1;
		
		long sprintId = 0;

		if(Validator.isNotNull(orderIndex)){
			
			List<Sprint> sprints = sprintService.getSprintsByOrderIndex(orderIndex, ideaId, ideaScrum.getGroupId(),"idea");
			
			if(tab.equalsIgnoreCase("Understand")) {
				configureUnderstand(sprints, model);
				model.addAttribute("activeTab", "Understand");
			} else if(tab.equalsIgnoreCase("Converge")) {
				configurationConverge(sprints, model);
				model.addAttribute("activeTab", "Converge");
			} else if(tab.equalsIgnoreCase("Prototype")) {
				configurationPrototype(sprints, model);
				model.addAttribute("activeTab", "Prototype");
			} else if(tab.equalsIgnoreCase("Validate")) {
				configurationValidate(sprints, model);
				model.addAttribute("activeTab", "Validate");
			} else if(tab.equalsIgnoreCase("Diverge")) {
				configurationDiverge(sprints, model);
				model.addAttribute("activeTab", "Diverge");
			} else if(tab.equalsIgnoreCase("Innospective")) {
				configurationInnospective(sprints, model);
				model.addAttribute("activeTab", "Innospective");
			}
			
			Sprint sprint = sprintService.getSprintByOrderIndex(orderIndex, ideaId, ideaScrum.getGroupId(),"idea");
			sprintId = sprint.getSprintId();
			
		} else {
			List<Sprint> sprints = sprintService.getSprintsByOrderIndex(1, ideaId, ideaScrum.getGroupId(),"idea");
			model.addAttribute("sprints", sprints);
			Sprint sprint = sprintService.getSprintByOrderIndex(1, ideaId, ideaScrum.getGroupId(),"idea");
			sprintId = sprint.getSprintId();
		}
		String tabInventerAvtar = IdeaUtil.getTabInventorAvtarURL(sprintId);
	
		String sprintViewJsp = "view";
		model.addAttribute("sprintViewJsp", sprintViewJsp);
		model.addAttribute("loggedInUserId", themeDisplay.getUserId());
		model.addAttribute("loggedInUserAvatarURL", IdeaUtil.getAvatarURL(themeDisplay.getUserId()));
		model.addAttribute("loggedInUserName", IdeaUtil.getUserName(themeDisplay.getUserId()));
		model.addAttribute("ideaId", ideaId);
		model.addAttribute("ideaScrumId", ideaScrum.getGroupId());
		model.addAttribute("inventorAvatarURL", tabInventerAvtar);
		model.addAttribute("inventorName", ideaScrum.getUserName());
	}
	
	private void configureUnderstand(List<Sprint> sprints, Model model) {
		
		log.info("configure understnad -------- ");
		
		String titleDescrition = StringPool.BLANK;
		
		if(sprints.size() == 8 ) {
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(0).getText());
			model.addAttribute("sprint1", sprints.get(0));
			model.addAttribute("sprint1TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(1).getText());
			model.addAttribute("sprint2", sprints.get(1));
			model.addAttribute("sprint2TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(2).getText());
			model.addAttribute("sprint3", sprints.get(2));
			model.addAttribute("sprint3TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(3).getText());
			model.addAttribute("sprint4", sprints.get(3));
			model.addAttribute("sprint4TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(4).getText());
			model.addAttribute("sprint5", sprints.get(4));
			model.addAttribute("sprint5TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(5).getText());
			model.addAttribute("sprint6", sprints.get(5));
			model.addAttribute("sprint6TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(6).getText());
			model.addAttribute("sprint7", sprints.get(6));
			model.addAttribute("sprint7TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(7).getText());
			model.addAttribute("sprint8", sprints.get(7));
			model.addAttribute("sprint8TitleDescription", titleDescrition);
			
		}	
	}
	
	private void configurationDiverge(List<Sprint> sprints, Model model) {
		String titleDescrition = StringPool.BLANK;
		
		if(sprints.size() == 12 ) {	
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(0).getText());
			model.addAttribute("sprint1", sprints.get(0));
			model.addAttribute("sprint1TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(1).getText());
			model.addAttribute("sprint2", sprints.get(1));
			model.addAttribute("sprint2TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(2).getText());
			model.addAttribute("sprint3", sprints.get(2));
			model.addAttribute("sprint3TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(3).getText());
			model.addAttribute("sprint4", sprints.get(3));
			model.addAttribute("sprint4TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(4).getText());
			model.addAttribute("sprint5", sprints.get(4));
			model.addAttribute("sprint5TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(5).getText());
			model.addAttribute("sprint6", sprints.get(5));
			model.addAttribute("sprint6TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(6).getText());
			model.addAttribute("sprint7", sprints.get(6));
			model.addAttribute("sprint7TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(7).getText());
			model.addAttribute("sprint8", sprints.get(7));
			model.addAttribute("sprint8TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(8).getText());
			model.addAttribute("sprint9", sprints.get(8));
			model.addAttribute("sprint9TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(9).getText());
			model.addAttribute("sprint10", sprints.get(9));
			model.addAttribute("sprint10TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(10).getText());
			model.addAttribute("sprint11", sprints.get(10));
			model.addAttribute("sprint11TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(11).getText());
			model.addAttribute("sprint12", sprints.get(11));
			model.addAttribute("sprint12TitleDescription", titleDescrition);

		}	
	}
	
	private void configurationConverge(List<Sprint> sprints, Model model) {
		String titleDescrition = StringPool.BLANK;
		
		if(sprints.size() == 10 ) { 
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(0).getText());
			model.addAttribute("sprint1", sprints.get(0));
			model.addAttribute("sprint1TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(1).getText());
			model.addAttribute("sprint2", sprints.get(1));
			model.addAttribute("sprint2TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(2).getText());
			model.addAttribute("sprint3", sprints.get(2));
			model.addAttribute("sprint3TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(3).getText());
			model.addAttribute("sprint4", sprints.get(3));
			model.addAttribute("sprint4TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(4).getText());
			model.addAttribute("sprint5", sprints.get(4));
			model.addAttribute("sprint5TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle("CPAINstorming");
			model.addAttribute("sprint6", sprints.get(5));
			model.addAttribute("sprint6TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(6).getText());
			model.addAttribute("sprint7", sprints.get(6));
			model.addAttribute("sprint7TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(7).getText());
			model.addAttribute("sprint8", sprints.get(7));
			model.addAttribute("sprint8TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(8).getText());
			model.addAttribute("sprint9", sprints.get(8));
			model.addAttribute("sprint9TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(9).getText());
			model.addAttribute("sprint10", sprints.get(9));
			model.addAttribute("sprint10TitleDescription", titleDescrition);
		}
	}
	
	private void configurationPrototype(List<Sprint> sprints, Model model) {
		String titleDescrition = StringPool.BLANK;
		
		if(sprints.size() == 5 ) {
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(0).getText());
			model.addAttribute("sprint1", sprints.get(0));
			model.addAttribute("sprint1TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(1).getText());
			model.addAttribute("sprint2", sprints.get(1));
			model.addAttribute("sprint2TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(2).getText());
			model.addAttribute("sprint3", sprints.get(2));
			model.addAttribute("sprint3TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(3).getText());
			model.addAttribute("sprint4", sprints.get(3));
			model.addAttribute("sprint4TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(4).getText());
			model.addAttribute("sprint5", sprints.get(4));
			model.addAttribute("sprint5TitleDescription", titleDescrition);
		}	
	}
	
	private void configurationValidate(List<Sprint> sprints, Model model) {
		String titleDescrition = StringPool.BLANK;
		
		if(sprints.size() == 10 ) {
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(0).getText());
			model.addAttribute("sprint1", sprints.get(0));
			model.addAttribute("sprint1TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(1).getText());
			model.addAttribute("sprint2", sprints.get(1));
			model.addAttribute("sprint2TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(2).getText());
			model.addAttribute("sprint3", sprints.get(2));
			model.addAttribute("sprint3TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(3).getText());
			model.addAttribute("sprint4", sprints.get(3));
			model.addAttribute("sprint4TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(4).getText());
			model.addAttribute("sprint5", sprints.get(4));
			model.addAttribute("sprint5TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(5).getText());
			model.addAttribute("sprint6", sprints.get(5));
			model.addAttribute("sprint6TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(6).getText());
			model.addAttribute("sprint7", sprints.get(6));
			model.addAttribute("sprint7TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(7).getText());
			model.addAttribute("sprint8", sprints.get(7));
			model.addAttribute("sprint8TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(8).getText());
			model.addAttribute("sprint9", sprints.get(8));
			model.addAttribute("sprint9TitleDescription", titleDescrition);
			
			titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(9).getText());
			model.addAttribute("sprint10", sprints.get(9));
			model.addAttribute("sprint10TitleDescription", titleDescrition);
		}
	}
	
	private void configurationInnospective(List<Sprint> sprints, Model model) {
		String titleDescrition = StringPool.BLANK;
		
		if(Validator.isNotNull(sprints)) {
			
			if(sprints.size() == 5 ) {
				
				titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(0).getText());
				model.addAttribute("sprint1", sprints.get(0));
				model.addAttribute("sprint1TitleDescription", titleDescrition);
				
				titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(1).getText());
				model.addAttribute("sprint2", sprints.get(1));
				model.addAttribute("sprint2TitleDescription", titleDescrition);
				
				titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(2).getText());
				model.addAttribute("sprint3", sprints.get(2));
				model.addAttribute("sprint3TitleDescription", titleDescrition);
				
				titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(3).getText());
				model.addAttribute("sprint4", sprints.get(3));
				model.addAttribute("sprint4TitleDescription", titleDescrition);
				
				titleDescrition = IdeaScrumDiscussionTitleDescription.getDescriptionTitle(sprints.get(4).getText());
				model.addAttribute("sprint5", sprints.get(4));
				model.addAttribute("sprint5TitleDescription", titleDescrition);
				
			}	
		}
		
	}
	
	public void theIdeaView(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException{
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
		long ideaId = 0;
		if(ideaScrum != null){
			ideaId =  ideaScrum.getIdeaId();
		}
		Idea idea = ideaService.find(ideaId);
		
		boolean ideaLike = false;
		long ideaLikeCount = 0;
		
		String userAvatarURL = StringPool.BLANK;
		List<IdeaHistory> ideaHistories = null;
		userAvatarURL = IdeaUtil.getAvatarURL(idea.getCoInventorId());
		ideaLike = likeService.checkIdeaLike(ideaId,themeDisplay.getUserId());
		ideaLikeCount = likeService.getCountOfIdeaLike(ideaId);
		ideaHistories = ideaHistoryService.getIdeaHistoryList(ideaId);
		
		model.addAttribute("ideaHistories", ideaHistories);
		model.addAttribute("ideaLikeCount", ideaLikeCount);
		model.addAttribute("ideaLike", ideaLike);
		model.addAttribute("userAvtar", userAvatarURL);
		model.addAttribute("idea", idea);
		model.addAttribute("historyListSize", ideaHistories.size());

		String theIdeaViewJsp = "view";
		model.addAttribute("theIdeaViewJsp", theIdeaViewJsp);
		
	}
	
	@ResourceMapping(value = "ideaCommentView")
	public String viewIdeaComments(ResourceRequest resourceRequest,ResourceResponse resourceResponse, Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = ParamUtil.getLong(request, "ideaId");
		try{
			Idea idea = ideaService.find(ideaId);
			List<IdeaComment> ideaComments = ideaCommentService.getIdeaComments(ideaId);
			List<IdeaCommentView> ideaCommentViews = new ArrayList<IdeaCommentView>();
			IdeaCommentView commentView;
			User user = null;
			for (IdeaComment ideaComment : ideaComments) {
				commentView = new IdeaCommentView();
				try {
					user = UserLocalServiceUtil.getUser(ideaComment.getUserId());
				} catch (PortalException e) {
					_log.error("Error in Getting User in IdeaCommentView Method"+e.getMessage());
				} catch (SystemException e) {
					_log.error("Error in Getting User in IdeaCommentView Method"+e.getMessage());
				}
				commentView.setUserName(user.getFullName());
				commentView.setAvtarUrl(IdeaUtil.getAvatarURL(user.getUserId()));
				commentView.setComment(ideaComment.getDescription());
				commentView.setCommentDate(ideaComment.getCreatedDate());
				commentView.setCommenId(ideaComment.getCommentId());
				ideaCommentViews.add(commentView);
			}
			model.addAttribute("ideaCreator", idea.getCoInventorId());
			model.addAttribute("commentVoteService", commentVoteService);
			model.addAttribute("ideaId", ideaId);
			model.addAttribute("commentCount", ideaComments.size());
			model.addAttribute("ideaComments", ideaCommentViews);
		}catch(Exception e){
			_log.error("Error in IdeaCommentView Method"+e.getMessage());
		}
		return "the-idea/ideaCommentsTemplet";
	}
	
	@ResourceMapping(value = "ideaLikeURL")
	public void ideaLikeURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse, Model model) {
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long ideaId = ParamUtil.getLong(request, "ideaId", 0);
		String likeValue = ParamUtil.getString(request, "like");
		PrintWriter pw = null;
		try {
			Idea idea = ideaService.find(ideaId);
			if (likeValue.equalsIgnoreCase("UpVote")) {
				Like like = new Like();
				like.setTaskId(0);
				like.setLikeDate(new Date());
				like.setIdea(idea);
				like.setUserId(themeDisplay.getUserId());
				likeService.create(like);
			} else if (likeValue.equalsIgnoreCase("DownVote")) {
				Like like = likeService.getIdeaLike(ideaId,themeDisplay.getUserId());
				likeService.delete(like.getLikeId());
			}
			
			long ideaLikeCount = likeService.getCountOfIdeaLike(ideaId);

			StringBuilder countIdeaLike = new StringBuilder();
			countIdeaLike.append(ideaLikeCount);
			pw = resourceResponse.getWriter();
			pw.print(countIdeaLike);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	@ResourceMapping(value="ideaCommentVote")
	public void ideaCommentVote(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = ParamUtil.getLong(request, "ideaId");
		long commentId = ParamUtil.getLong(request, "commentId");
		
		PrintWriter pw = null;
		try{
			IdeaComment comment = ideaCommentService.find(commentId);
				IdeaCommentVote commentVote = new IdeaCommentVote();
				commentVote.setCompanyId(themeDisplay.getCompanyId());
				commentVote.setGroupId(themeDisplay.getScopeGroupId());
				commentVote.setIdeaComment(comment);
				commentVote.setUserId(themeDisplay.getUserId());
				commentVote.setVoteDate(new Date());
				commentVote.setIdeaId(ideaId);
				commentVoteService.create(commentVote);
			long commentsCount = ideaCommentService.getIdeaCommentCount(ideaId);
			commentsCount = commentsCount * 2;
			
			long currentWeekCount = commentVoteService.getCurrentWeekCount(commentId,"commentCount");
			long weekThanksCount = IdeaUtil.getThanksCount(ideaId);
			Boolean thankYou = IdeaUtil.checkCommentThanks(commentId, themeDisplay.getUserId());
			long remainingCount = commentsCount - weekThanksCount;
			List<IdeaCommentVote> totalThanksSize =commentVoteService.getThanksListByIdeaId(ideaId); 
			 JSONObject resultObj = JSONFactoryUtil.createJSONObject();
			 
			  resultObj.put("thanksSize", totalThanksSize.size());
			  resultObj.put("remainingCount", remainingCount);
			  resultObj.put("weekThanksCount", weekThanksCount);
			  resultObj.put("commentVoteCount", currentWeekCount);
			  resultObj.put("thankYou", thankYou);
			  
			pw = null;
			pw = resourceResponse.getWriter();
			pw.print(resultObj.toString());
			pw.flush();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
	
	@ResourceMapping(value = "postComment")
	public void postComment(ResourceRequest resourceRequest,ResourceResponse resourceResponse, Model model) {
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = ParamUtil.getLong(request, "ideaId");
		String comment = ParamUtil.getString(request, "commentText");
		try{
			IdeaComment ideaComment = new IdeaComment();
			ideaComment.setCompanyId(themeDisplay.getCompanyId());
			ideaComment.setCreatedDate(new Date());
			ideaComment.setDescription(comment);
			ideaComment.setGroupId(themeDisplay.getScopeGroupId());
			ideaComment.setParentCommentId(0);
			ideaComment.setTaskId(0);
			ideaComment.setUserId(themeDisplay.getUserId());

			Idea idea = ideaService.find(ideaId);
			ideaComment.setIdea(idea);
			ideaCommentService.create(ideaComment);
		}catch(Exception e){
			_log.error("Error in Post Comment Method:::"+e.getMessage());
		}
	}
	
	
	public void rateThisIdeaView(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String view = ParamUtil.getString(renderRequest, "view", StringPool.BLANK);
		
		String ratingViewJsp = "view";
		if(view.equals("ideaRateChart")){
			rateChart(renderRequest, renderResponse, model);
			ratingViewJsp = "ratinggraph";
		}else{
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			long ideaId = 0;
			if(ideaScrum != null){
				ideaId =  ideaScrum.getIdeaId();
			}
			
			List<IdeaRateCriteria> ideaRateCriterias = ideaRateService.getAllIdeaRateCriterias();
			model.addAttribute("ideaRateService", ideaRateService);
			model.addAttribute("ideaId", ideaId);
			model.addAttribute("ideaRateCriterias", ideaRateCriterias);
		}
		model.addAttribute("ratingViewJsp", ratingViewJsp);
	}
	
	private void rateChart(RenderRequest renderRequest, RenderResponse renderResponse, Model model){
		long ideaId = ParamUtil.getLong(renderRequest, "ideaId", 0);
		Idea idea = ideaService.find(ideaId);
		
		/*find average rating of idea*/
		Map<IdeaRateCriteria, List<Double>> rateCriterias = ideaRateService.findStandardDeviationANDAvergeRatingOfIdea(idea);
		JSONObject responseJSON =JSONFactoryUtil.createJSONObject();
		responseJSON.put("avergerating", IdeaScrumUtil.convertchartRatingToJSONArray(rateCriterias));
		model.addAttribute("data", responseJSON.toString());
	}

	@ResourceMapping(value="updateIdeaRate")
	public void updateIdeaRate(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model) throws IOException{
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponse);
		JSONObject resObj = JSONFactoryUtil.createJSONObject();
		long ideaId = ParamUtil.getLong(request, "ideaId",0);
		long ideaRateCriteriaId = ParamUtil.getLong(request, "ideaRateCriteriaId", 0);
		long rate = ParamUtil.getLong(request, "rate", 0);
		try{
			IdeaRate ideaRate = null;
			ideaRate =  ideaRateService.findByUserIdIdeaIdAndCriteriaId(themeDisplay.getUserId(), ideaId, ideaRateCriteriaId);
			if(ideaRate != null){ 	//update entry
				ideaRate.setGroupId(themeDisplay.getScopeGroupId());
				ideaRate.setRateValue((int) rate);
				ideaRate.setRatingDate(new Date());
				ideaRate.setUserId(themeDisplay.getUserId());
				ideaRate.setIdea(ideaService.find(ideaId));
				ideaRate.setIdeaRateCriteria(ideaRateService.getIdeaRateCriteriaByCriteriaId(ideaRateCriteriaId));
				ideaRateService.update(ideaRate);
			} else { 	
				ideaRate = new IdeaRate();
				ideaRate.setGroupId(themeDisplay.getScopeGroupId());
				ideaRate.setRateValue((int)rate);
				ideaRate.setRatingDate(new Date());
				ideaRate.setUserId(themeDisplay.getUserId());
				ideaRate.setIdea(ideaService.find(ideaId));
				ideaRate.setIdeaRateCriteria(ideaRateService.getIdeaRateCriteriaByCriteriaId(ideaRateCriteriaId));
				ideaRateService.create(ideaRate);
			}
			resObj.put("status", "success");
		}catch(Exception e){
			resObj.put("status", "error");
		}
		PrintWriter writer = response.getWriter();
		writer.print(resObj);
	}
	
	@ResourceMapping(value="discussionThanksURL")
	public void discussionThanksURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,@RequestParam("ideaScrumId") long ideaScrumId,
			@RequestParam("ideaId") long ideaId,@RequestParam("sprintId") long sprintId,@RequestParam("ideaScrumDiscussionId") long ideaScrumDiscussionId){
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		ScrumDiscussionThanks thanks =null;
		try{
			 thanks = new ScrumDiscussionThanks();	
			 thanks.setCompanyId(themeDisplay.getCompanyId());
			 thanks.setCreatedDate(new Date());
			 thanks.setIdeaId(ideaId);
			 thanks.setIdeaScrumId(ideaScrumId);
			 thanks.setScrumDiscussionId(ideaScrumDiscussionId);
			 thanks.setSprintId(sprintId);
			 thanks.setThanksCreator(themeDisplay.getUserId());
			 thanksService.create(thanks);
		}catch(Exception e){
			log.info("Error in Updating New Discussion Thanks "+e.getMessage());
		} 
	}
	
	@ResourceMapping(value="likeDiscussionURL")
	public void likeDiscussionURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse, @RequestParam("ideaScrumDiscussionId") long ideaScrumDiscussionId){
		try{
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			Scrum_Discussion_Like scrumLikes = new Scrum_Discussion_Like();
			scrumLikes.setCompanyId(themeDisplay.getCompanyId());
			scrumLikes.setScrumDiscussionId(ideaScrumDiscussionId);
			scrumLikes.setCreatedDate(new Date());
			scrumLikes.setUserId(themeDisplay.getUserId());
			scrumDiscussionLikeService.create(scrumLikes);
			
		}catch(Exception e){
			log.info("Error in Updating New Discussion Message "+e.getMessage());
		} 
	}
	
	@ResourceMapping(value="disLikeDiscussionURL")
	public void disLikeDiscussionURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse, @RequestParam("ideaScrumDiscussionId") long ideaScrumDiscussionId){
		try{
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			Scrum_Discussion_Like scrumLikes = scrumDiscussionLikeService.checkUserIsLiked(ideaScrumDiscussionId, themeDisplay.getUserId() );
			scrumDiscussionLikeService.delete(scrumLikes.getLikeId());
		}catch(Exception e){
			log.info("Error in Updating New Discussion Message "+e.getMessage());
		} 
	}
	
	@ResourceMapping(value="updateDiscussionURL")
	public void updateDiscussionURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,@RequestParam("ideaScrumId") long ideaScrumId,
			@RequestParam("ideaId") long ideaId,@RequestParam("sprintId") long sprintId,@RequestParam("ideaScrumDiscussionId") long ideaScrumDiscussionId,
			@RequestParam("message") String message){
		try{
			IdeaScrumDiscussion ideaScrumDiscusssion = ideaService.getIdeaScrumDiscussion(ideaScrumDiscussionId);
			ideaScrumDiscusssion.setMessage(message);
			ideaService.updateIdeaScrumDiscussion(ideaScrumDiscusssion);
		}catch(Exception e){
			log.info("Error in Updating New Discussion Message "+e.getMessage());
		} 
	}
	
	@ResourceMapping(value = "getDiscussionsURL")
	public void getDiscussionsURL(ResourceRequest request,
			ResourceResponse response,@RequestParam("ideaScrumId") long ideaScrumId, 
			@RequestParam("ideaId") long ideaId, @RequestParam("sprintId") long sprintId) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		JSONArray firstLevelJSONArray = JSONFactoryUtil.createJSONArray();
		JSONArray secondLevelJSONArray = null;
		JSONArray thirdLevelJSONArray = null;
		JSONObject firstLevelJSONObj = null;
		JSONObject secondLevelJSONObj = null;
		JSONObject thirdLevelJSONObj = null;
	
		long coInventorId = ideaService.find(ideaId).getCoInventorId();
		
		boolean isInventorPrefsAdmin =  IdeaScrumUtil.isInventorPrefsPermission(themeDisplay.getCompanyId(), themeDisplay.getUserId(), coInventorId);
		
		boolean userThanks = true;
		boolean isThanks = false;
		List<IdeaScrumDiscussion> firstLevelDiscussions = new ArrayList<IdeaScrumDiscussion>();
		try{
			 firstLevelDiscussions = ideaService.getChildIdeaScrumDiscussion(ideaScrumId, ideaId, 
					sprintId, 0, 0);
			 
			 if(Validator.isNotNull(firstLevelDiscussions)) {
			 
			 for(IdeaScrumDiscussion firstLevelDiscussion : firstLevelDiscussions){
				 		if(isInventorPrefsAdmin){
				 			userThanks = thanksService.checkUserThanks(firstLevelDiscussion.getScrumDiscussionId(),themeDisplay.getUserId());
				 		}else{
				 			userThanks = false;
				 		}
				 	
				// 	isThanks = (userThanks && isInventorPrefsAdmin ? true : false);
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
					
					boolean isLiked = scrumDiscussionLikeService.isUserLiked(firstLevelDiscussion.getScrumDiscussionId(), themeDisplay.getUserId());
					
					firstLevelJSONObj.put("isLiked", isLiked);
					
					secondLevelJSONArray = JSONFactoryUtil.createJSONArray();
					if(firstLevelDiscussion.isChild()){
						List<IdeaScrumDiscussion> secondLevelDiscussions = ideaService.getChildIdeaScrumDiscussion(ideaScrumId, ideaId, 
								sprintId, firstLevelDiscussion.getScrumDiscussionId(), 1);
						for(IdeaScrumDiscussion secondLevelDiscussion : secondLevelDiscussions){
							if(isInventorPrefsAdmin){
								userThanks = thanksService.checkUserThanks(secondLevelDiscussion.getScrumDiscussionId(), themeDisplay.getUserId());
							}else{
								userThanks = false;
							}
							
						//	isThanks = (userThanks && isInventorPrefsAdmin ? true : false);
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
							
							boolean isSecondLevelLiked = scrumDiscussionLikeService
									.isUserLiked(secondLevelDiscussion
											.getScrumDiscussionId(),
											themeDisplay.getUserId());
							
							secondLevelJSONObj.put("isLiked", isSecondLevelLiked);
							
							thirdLevelJSONArray = JSONFactoryUtil.createJSONArray();
							if(secondLevelDiscussion.isChild()){
								List<IdeaScrumDiscussion> thirdLevelDiscussions = ideaService.getChildIdeaScrumDiscussion(ideaScrumId, ideaId, 
										sprintId, secondLevelDiscussion.getScrumDiscussionId(), 2);
								if(Validator.isNotNull(thirdLevelDiscussions)){
									for(IdeaScrumDiscussion thirdLevelDiscussion : thirdLevelDiscussions){
										if(isInventorPrefsAdmin){
											userThanks = thanksService.checkUserThanks(thirdLevelDiscussion.getScrumDiscussionId(), themeDisplay.getUserId());
										}else{
											userThanks = false;
										}
										//userThanks = thanksService.checkUserThanks(thirdLevelDiscussion.getScrumDiscussionId(), themeDisplay.getUserId());
									//	isThanks = (userThanks && isInventorPrefsAdmin ? true : false);
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
										
										boolean isThirdLevelLiked = scrumDiscussionLikeService
												.isUserLiked(thirdLevelDiscussion
														.getScrumDiscussionId(),
														themeDisplay.getUserId());
										
										thirdLevelJSONObj.put("isLiked", isThirdLevelLiked);
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
		   }	 
		}catch(Exception e){
			_log.error("Error while getting discussion objects:"+e.getMessage());
		}
		try {
			response.getWriter().write(firstLevelJSONArray.toString());
		} catch (IOException e) {
			_log.error(e.getMessage(), e);
		}
	}
	
	@ResourceMapping(value = "sendMessageURL")
	public void sendMessageURL(ResourceRequest request,
			ResourceResponse response,
			@RequestParam("message") String message,
			@RequestParam("ideaScrumId") long ideaScrumId,
			@RequestParam("ideaId") long ideaId,
			@RequestParam("sprintId") long sprintId,
			@RequestParam("parentDiscussionId") long parentDiscussionId,
			@RequestParam("level") int level,@RequestParam("ideaScrumDiscussionId") long ideaScrumDiscussionId) {
		JSONArray messageJSONArray = JSONFactoryUtil.createJSONArray();
		JSONObject messsageJSONObj = null;
		
		long coInventorId = ideaService.find(ideaId).getCoInventorId();
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isInventorPrefsAdmin =  IdeaScrumUtil.isInventorPrefsPermission(themeDisplay.getCompanyId(), themeDisplay.getUserId(), coInventorId);
		boolean userThanks = true;
		boolean isThanks = false;
		long userId = themeDisplay.getUserId();
		long companyId = themeDisplay.getCompanyId();
		long ideaDiscussionId = 0;
		try{
			IdeaScrumDiscussion ideaScrumDiscussion = new IdeaScrumDiscussion();
			ideaScrumDiscussion.setMessage(message);
			ideaScrumDiscussion.setIdeaScrumId(ideaScrumId);
			ideaScrumDiscussion.setIdeaId(ideaId);
			ideaScrumDiscussion.setSprintId(sprintId);
			ideaScrumDiscussion.setUserId(userId);
			ideaScrumDiscussion.setUserName(themeDisplay.getUser().getFullName());
			ideaScrumDiscussion.setCreatedDate(new Date());
			ideaScrumDiscussion.setModifiedDate(new Date());
			ideaScrumDiscussion.setParentScrumDiscussionId(ideaScrumDiscussionId);
			if(ideaScrumDiscussionId > 0){
				IdeaScrumDiscussion parenDiscussion = ideaService.getIdeaScrumDiscussion(ideaScrumDiscussionId);
				parenDiscussion.setChild(true);
				ideaService.updateIdeaScrumDiscussion(parenDiscussion);
			}
			ideaScrumDiscussion.setLevel(level);
			ideaScrumDiscussion.setChild(false);
			ideaScrumDiscussion.setCompanyId(companyId);
			ideaDiscussionId = ideaService.createIdeaScrumDiscussion(ideaScrumDiscussion);
			
			messsageJSONObj = JSONFactoryUtil.createJSONObject();
			messsageJSONObj.put("ideaScrumDiscussionId", ideaScrumDiscussion.getScrumDiscussionId());
			messsageJSONObj.put("message", message);
			messsageJSONObj.put("avatarURL", ideaScrumDiscussion.getAvatarURL());
			messsageJSONObj.put("userId", ideaScrumDiscussion.getUserId());
			messsageJSONObj.put("userName", ideaScrumDiscussion.getUserName());
			messsageJSONObj.put("parentDiscussionId", ideaScrumDiscussion.getParentScrumDiscussionId());
			messsageJSONObj.put("isChild", ideaScrumDiscussion.isChild());
			
			boolean isThirdLevelLiked = scrumDiscussionLikeService
					.isUserLiked(ideaScrumDiscussion
							.getScrumDiscussionId(),
							themeDisplay.getUserId());
			
			messsageJSONObj.put("isLiked", isThirdLevelLiked);
			if(isInventorPrefsAdmin){
				userThanks = thanksService.checkUserThanks(ideaScrumDiscussion.getScrumDiscussionId(), themeDisplay.getUserId());
			}else{
				userThanks = false;
			}
			if(ideaScrumDiscussion.getLevel() == 0){
				messsageJSONObj.put("levelCount", 1);
				messsageJSONObj.put("level", "first");
			}else if(ideaScrumDiscussion.getLevel() == 1){
				messsageJSONObj.put("levelCount", 2);
				messsageJSONObj.put("level", "second");
			}else if(ideaScrumDiscussion.getLevel() == 2){
				messsageJSONObj.put("levelCount", 3);
				messsageJSONObj.put("level", "third");
			}
			messsageJSONObj.put("isInventorPrefsAdmin",isInventorPrefsAdmin);
			messsageJSONObj.put("isThanks", userThanks);
			messageJSONArray.put(messsageJSONObj);
		}catch(Exception e){
			_log.error("Error while saving ideascrumdiscussion message:"+e.getMessage());
		}
		
		try {
			response.getWriter().write(messageJSONArray.toString());
		} catch (IOException e) {
			_log.error(e.getMessage(), e);
		}
	}
	
  /*@ResourceMapping(value = "deleteSprintURL")
	public void deleteSprintURL(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model) {
		
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));

		long sprintId = ParamUtil.getLong(request, "sprintId");
		try {
			Sprint sprint = sprintService.find(sprintId);
			
			IdeaScrumUtil.updateComingSprintAfterDelete(sprint, sprintId);
			
			sprintService.delete(sprintId);
			model.addAttribute("noOfSprints", sprintService.noofSprints());
		} catch (Exception e) {
			_log.error("Sprint Not Deleted" + e.getMessage());
		}
	}*/
	
	@ActionMapping(params="action=deleteSprint")
	public void deleteSprint(ActionRequest actionRequest,ActionResponse actionResponse,Model model){
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long sprintId = ParamUtil.getLong(actionRequest, "sprintId");
		String tab = ParamUtil.getString(actionRequest, "tab", StringPool.BLANK);
		long ideaId = 0;
		try {
			Sprint sprint = sprintService.find(sprintId);
			
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			if(ideaScrum != null){
				ideaId =  ideaScrum.getIdeaId();
			}
			//update afterwards sprints
			IdeaScrumUtil.updateComingSprintAfterDelete(sprint, sprintId, ideaId, ideaScrum.getGroupId());
			sprintService.delete(sprintId);
			
			model.addAttribute("noOfSprints", sprintService.noofSprints(ideaId));
			SessionMessages.add(actionRequest, SprintConstant.SPRINT_DELETED_SUCCESSFULLY);
			actionResponse.setRenderParameter("tab", tab);
		} catch (Exception e) {
			_log.error("Sprint Not Deleted" + e.getMessage());
		}
	}
	@RenderMapping(params = "view=updateFromView")
	public View updateFromView(RenderRequest renderRequest,
			RenderResponse renderResponse, Model model) throws PortalException,
			SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = ParamUtil.getLong(renderRequest, "ideaId");
		long scopeGroupId = ParamUtil.getLong(renderRequest, "scopeGroupId");
		boolean sliderEdit = false;
		
		try{
			if (ideaId > 0) {
				Idea idea = ideaService.find(ideaId);
				sliderEdit = true;
				List<AssetCategory> categoryList = IdeaUtil.getCategoryList(
						scopeGroupId, themeDisplay.getUserId());
				List<AssetCategory> ideaContestList = IdeaUtil.getIdeaContestList(
						scopeGroupId, themeDisplay.getUserId());
				List<AssetCategory> stageList = IdeaUtil.getIdeaStageList(
						scopeGroupId, themeDisplay.getUserId());
				List<AssetCategory> ideaTypeList = IdeaUtil.getIdeaTypeList(
						scopeGroupId, themeDisplay.getUserId());
				Date todayDate = new Date();
				
				model.addAttribute("sliderEdit", sliderEdit);
				model.addAttribute("idea", idea);
				model.addAttribute("ideaContestList", ideaContestList);
				model.addAttribute("ideaTypeList", ideaTypeList);
				model.addAttribute("stageList", stageList);
				model.addAttribute("categoryList", categoryList);
				model.addAttribute("submissionDate", todayDate);
			}
		}catch(Exception e){
			log.error("Error in Update From View Method" + e.getMessage());
		}
	
		return new InternalResourceView("/WEB-INF/views/idea-scrum/the-idea/edit_idea.jsp");
	}
	@ActionMapping(params = "action=editIdea")
	public void editIdea(ActionRequest actionRequest,
			ActionResponse actionResponse){

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			SessionErrors.add(actionRequest, IdeaConstant.ERROR_IDEA_LOG_IN);
			return;
		}
		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);

		String tab = ParamUtil.getString(actionRequest, "tab", StringPool.BLANK);
		long ideaId = ParamUtil.getLong(uploadRequest, "ideaId", 0);
		String ideaTitle = ParamUtil.getString(uploadRequest, "ideaTitle",
				StringPool.BLANK);
		String hiddenTitle = ParamUtil.getString(uploadRequest, "hiddenTitle",
				StringPool.BLANK);
		long ideaProgramme = ParamUtil.getLong(uploadRequest, "ideaContest", 0);
		long hiddenContest = ParamUtil.getLong(uploadRequest, "hiddenContest",
				0);
		long category = ParamUtil.getLong(uploadRequest, "category", 0);
		long hiddenCategory = ParamUtil.getLong(uploadRequest,
				"hiddenCategory", 0);
		long stage = ParamUtil.getLong(uploadRequest, "stage", 0);
		long hiddenStage = ParamUtil.getLong(uploadRequest, "hiddenStage", 0);
		long innovationType = ParamUtil.getLong(uploadRequest, "ideaType", 0);
		long hiddenType = ParamUtil.getLong(uploadRequest, "hiddenType", 0);
		String ideaContent = ParamUtil.getString(uploadRequest, "ideaContent",
				StringPool.BLANK);
		String hiddenDescription = ParamUtil.getString(uploadRequest,
				"hiddenDescription", StringPool.BLANK);
		boolean sliderEdit = ParamUtil.getBoolean(uploadRequest, "sliderEdit");
		long companyId = ParamUtil.getLong(uploadRequest, "companyId");
		long scopeGroupId = ParamUtil.getLong(uploadRequest, "scopeGroupId");
		String hiddenIdeaTagLine  = ParamUtil.getString(uploadRequest, "hiddenTagTitle");
		String ideaTagLine = ParamUtil.getString(uploadRequest, "ideaDescribeTitle");
		double version = 1;

		Idea idea = null;
		SocialWorkflow workflow = null;
		IdeaHistory ideaHistory = null;

		if (ideaId > 0) {
			idea = ideaService.find(ideaId);
		} else {
			idea = new Idea();
			idea.setSubmissionDate(new Date());
		}
		idea.setIdeaTitle(ideaTitle);
		idea.setIdeaProgramme(ideaProgramme);
		idea.setIdeaCategory(category);
		idea.setIdeaStage(stage);
		idea.setInnovationType(innovationType);
		idea.setModifiedDate(new Date());
		idea.setDescription(ideaContent);
		idea.setIdeaHot(Boolean.FALSE);
		idea.setIdeaTagTitle(ideaTagLine);
		idea.setHotWeight(0.0);
		if (ideaId <= 0) {
			idea.setVersion(IdeaUtil.getFormattedVersion(version));
		}

		// idea.setStatus(IdeaEnum.IDEA_BACKLOG.getValue());
		idea.setCompanyId(companyId);
		idea.setCoInventorId(themeDisplay.getUserId());
		idea.setGroupId(scopeGroupId);
		
		boolean isUpdated = true;
		if (ideaId > 0) {
			boolean isEdit = true;
			String filePrefix = "fileUpload";
			String fileName = null;
			for (int i = 1; i <= 5; i++) {
				String currentFileName = filePrefix + i;
				fileName = uploadRequest.getFileName(currentFileName);
				if (Validator.isNotNull(fileName) && fileName.length() > 0
						&& isEdit) {
					idea.setVersion(IdeaUtil.getFormattedVersion(idea
							.getVersion() + 0.1));
					ideaService.update(idea);
					ideaHistory = createIdeaHistory(idea, ideaHistory, ideaId,
							idea.getVersion());
					ideaHistoryService.create(ideaHistory);
					try {
						workflow = createIdeaWorkflow(idea,workflow,themeDisplay,isUpdated);
					} catch (PortalException e) {
						log.error("Error in Creating Workflow Entry"+e.getMessage());
					} catch (SystemException e) {
						log.error("Error in Creating Workflow Entry"+e.getMessage());
					}
					isEdit = false;
				} else if ((!ideaTitle.equalsIgnoreCase(hiddenTitle)
						|| !(Long.valueOf(ideaProgramme).equals((Long
								.valueOf(hiddenContest))))
						|| !(Long.valueOf(category).equals((Long
								.valueOf(hiddenCategory))))
						|| !(Long.valueOf(stage).equals((Long
								.valueOf(hiddenStage))))
						|| !(Long.valueOf(innovationType).equals((Long
								.valueOf(hiddenType)))) || !ideaContent
							.equalsIgnoreCase(hiddenDescription)
						|| 	(!ideaTagLine.equalsIgnoreCase(hiddenIdeaTagLine)))
						&& isEdit) {
					idea.setVersion(IdeaUtil.getFormattedVersion(idea
							.getVersion() + 0.1));
					ideaService.update(idea);
					ideaHistory = createIdeaHistory(idea, ideaHistory, ideaId,
							idea.getVersion());
					ideaHistoryService.create(ideaHistory);
					try {
						workflow = createIdeaWorkflow(idea,workflow,themeDisplay,isUpdated);
					} catch (PortalException e) {
						log.error("Error in Creating Workflow Entry"+e.getMessage());
					} catch (SystemException e) {
						log.error("Error in Creating Workflow Entry"+e.getMessage());
					}
					isEdit = false;
				}
			}
			if (isEdit) {
				try {
					ServiceContext serviceContext = ServiceContextFactory.getInstance(uploadRequest);
					AssetEntryLocalServiceUtil.updateEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), 
							Idea.class.getName(), ideaId,  serviceContext.getAssetCategoryIds(), serviceContext.getAssetTagNames());
					idea.setVersion(IdeaUtil.getFormattedVersion(idea.getVersion()));
					ideaService.update(idea);
					workflow = createIdeaWorkflow(idea,workflow,themeDisplay,isUpdated);
				} catch (PortalException e) {
					log.error("Error in Creating Workflow Entry"+e.getMessage());
				} catch (SystemException e) {
					log.error("Error in Creating Workflow Entry"+e.getMessage());
				}
				
			}
			SessionMessages.add(actionRequest,
					IdeaConstant.SUCCESS_IDEA_MESSAGE_UPDATE);
			
		} else {
			isUpdated = false;
			try {
				ServiceContext	serviceContext = ServiceContextFactory.getInstance(uploadRequest);
				AssetEntryLocalServiceUtil.updateEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), 
						Idea.class.getName(), ideaId,  serviceContext.getAssetCategoryIds(), serviceContext.getAssetTagNames());
				ideaService.create(idea);
				ideaHistory = createIdeaHistory(idea, ideaHistory, ideaId,idea.getVersion());
				ideaHistoryService.create(ideaHistory);
				workflow = createIdeaWorkflow(idea,workflow,themeDisplay,isUpdated);
				SessionMessages.add(actionRequest,IdeaConstant.SUCCESS_IDEA_MESSAGE_CREATE);
			} catch (PortalException e) {
				log.error("Error in Creating Workflow Entry"+e.getMessage());
				SessionErrors.add(actionRequest, IdeaConstant.ERROR_IN_IDEA_MESSAGE_CREATE);
			} catch (SystemException e) {
				log.error("Error in Creating Workflow Entry"+e.getMessage());
				SessionErrors.add(actionRequest, IdeaConstant.ERROR_IN_IDEA_MESSAGE_CREATE);
			}
			
		}
		/* get the 5 file names */
		FileEntry fileEntry = null;
		String filePrefix = "fileUpload";

		for (int i = 1; i <= 5; i++) {
			String currentFileName = filePrefix + i;
			String fileName = uploadRequest.getFileName(currentFileName);
			if (Validator.isNotNull(fileName) && fileName.length() > 0) {
				int sourceFileExtension = fileName.lastIndexOf(".");
				if (sourceFileExtension != -1) {
					try {
						fileEntry = saveImage(uploadRequest, themeDisplay,
								currentFileName);
						IdeaAttachement ideaAttachement = new IdeaAttachement();
						ideaAttachement.setFileEntryId(fileEntry.getFileEntryId());
						if (ideaId > 0) {
							ideaAttachement.setVersion(idea.getVersion());
						} else {
							ideaAttachement.setVersion(version);
						}

						ideaAttachement.setIdea(idea);
						ideaAttachement.setCompanyId(themeDisplay.getCompanyId());
						ideaAttachement.setGroupId(themeDisplay.getScopeGroupId());
						ideaAttachementService.create(ideaAttachement);
					} catch (PortalException e) {
						log.error("Error in Uploading files in Idea"+e.getMessage());
						SessionErrors.add(actionRequest, IdeaConstant.ERROR_IN_UPLOADING_FILE);
					} catch (SystemException e) {
						log.error("Error in Uploading files in Idea"+e.getMessage());
						SessionErrors.add(actionRequest, IdeaConstant.ERROR_IN_UPLOADING_FILE);
					}
				}
			}
		}
		if (sliderEdit) {
			actionResponse.setRenderParameter("tab", tab);
			actionResponse.setRenderParameter("ideaId", String.valueOf(ideaId));
		}
	}
	
	public static IdeaHistory createIdeaHistory(Idea idea, IdeaHistory ideaHistory,
			long ideaId, double version) {

		if (ideaId <= 0) {
			ideaHistory = new IdeaHistory();
			ideaHistory.setIdeaTitle(idea.getIdeaTitle());
			ideaHistory.setIdeaProgramme(idea.getIdeaProgramme());
			ideaHistory.setIdeaCategory(idea.getIdeaCategory());
			ideaHistory.setIdeaStage(idea.getIdeaStage());
			ideaHistory.setCoInventorId(idea.getCoInventorId());
			ideaHistory.setStatus(idea.getStatus());
			ideaHistory.setDescription(idea.getDescription());
			ideaHistory.setSubmissionDate(idea.getSubmissionDate());
			ideaHistory.setInnovationType(idea.getInnovationType());
			ideaHistory.setCompanyId(idea.getCompanyId());
			ideaHistory.setGroupId(idea.getGroupId());
			ideaHistory.setVersion(idea.getVersion());
			ideaHistory.setIdeaTagTitle(idea.getIdeaTagTitle());
			ideaHistory.setIdea(idea);
		} else {
			ideaHistory = new IdeaHistory();
			ideaHistory.setIdeaTitle(idea.getIdeaTitle());
			ideaHistory.setIdeaProgramme(idea.getIdeaProgramme());
			ideaHistory.setIdeaCategory(idea.getIdeaCategory());
			ideaHistory.setDescription(idea.getDescription());
			ideaHistory.setIdeaStage(idea.getIdeaStage());
			ideaHistory.setCoInventorId(idea.getCoInventorId());
			ideaHistory.setStatus(idea.getStatus());
			ideaHistory.setSubmissionDate(new Date());
			ideaHistory.setInnovationType(idea.getInnovationType());
			ideaHistory.setCompanyId(idea.getCompanyId());
			ideaHistory.setGroupId(idea.getGroupId());
			ideaHistory.setModifiedDate(idea.getModifiedDate());
			ideaHistory.setVersion(idea.getVersion());
			ideaHistory.setIdeaTagTitle(idea.getIdeaTagTitle());
			ideaHistory.setIdea(idea);

		}
		return ideaHistory;
	}
	
	private SocialWorkflow createIdeaWorkflow(Idea idea, SocialWorkflow workflow, ThemeDisplay themeDisplay, boolean isUpdated) throws PortalException, SystemException {
		workflow = new SocialWorkflow();
		if(isUpdated){
			 workflow = socialWorkflowService.findWorkflowFromIdeaId(idea.getIdeaId());
			 workflow.setModifiedDate(new Date());
			 workflow.setDescription(idea.getDescription());
			 workflow.setIdea(idea);
			 workflow.setAction(IdeaConstant.SUBMIT_AN_IDEA);
			 workflow.setActionRequirements(IdeaConstant.PLEASE_REVIEW);
			 workflow.setActor(themeDisplay.getUserId());
			 workflow.setApp(idea.getStatus());
			
			if(workflow.getStatus() == SocialWorkflowEnum.WORKFLOW_DRAFT.getValue()){
				workflow.setTimeNeeded(0L);
				workflow.setStatus(SocialWorkflowEnum.WORKFLOW_DRAFT.getValue());
			}
			socialWorkflowService.update(workflow);
		}else{
			workflow.setCreatedDate(new Date());
			workflow.setModifiedDate(new Date());
			workflow.setDescription(idea.getDescription());
			workflow.setIdea(idea);
			workflow.setAction(IdeaConstant.SUBMIT_AN_IDEA);
			workflow.setActionRequirements(IdeaConstant.PLEASE_REVIEW);
			workflow.setActor(themeDisplay.getUserId());
			workflow.setApp(idea.getStatus());
			workflow.setTimeNeeded(0L);
			workflow.setStatus(SocialWorkflowEnum.WORKFLOW_DRAFT.getValue());
			socialWorkflowService.create(workflow);
		}
		return workflow;
	}
	
	private FileEntry saveImage(UploadPortletRequest uploadRequest,
			ThemeDisplay themeDisplay, String currentFileName)
			throws PortalException, SystemException {
		File file = uploadRequest.getFile(currentFileName);
		ServiceContext serviceContext = ServiceContextFactory
				.getInstance(uploadRequest);
		serviceContext.setAddGuestPermissions(Boolean.TRUE);
		Folder defaultFolder = null;
		try {
			defaultFolder = DLAppServiceUtil.getFolder(
					themeDisplay.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					PropsUtil.get("unicef.document.folder"));
		} catch (PrincipalException e3) {
			log.error(e3.getMessage(), e3);
		} catch (Exception e) {
			try {
				defaultFolder = DLAppServiceUtil.addFolder(
						themeDisplay.getScopeGroupId(),
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
						PropsUtil.get("unicef.document.folder"),
						StringPool.BLANK, serviceContext);
			} catch (Exception e1) {
				log.error(e1.getMessage(), e1);
			}
		}

		String contentType = MimeTypesUtil.getContentType(file);
		FileEntry fileEntry = null;
		try {
			fileEntry = DLAppLocalServiceUtil.addFileEntry(
					themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
					defaultFolder.getFolderId(),
					uploadRequest.getFullFileName(currentFileName),
					contentType,
					uploadRequest.getFullFileName(currentFileName),
					uploadRequest.getFullFileName(currentFileName),
					"changeLog", file, serviceContext);
		} catch (PortalException e2) {
			fileEntry = DLAppLocalServiceUtil.addFileEntry(
					themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
					defaultFolder.getFolderId(),
					uploadRequest.getFullFileName(currentFileName),
					contentType, uploadRequest.getFullFileName(currentFileName)
							+ new Date().getTime(), "bgImage", "changeLog",
					file, serviceContext);
		} catch (SystemException e3) {
			log.error(e3.getMessage(), e3);
		}

		return fileEntry;
	}
	
	private Log _log = LogFactoryUtil.getLog(IdeaScrumController.class);
}
