package com.unicef.portlet.idea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.net.ssl.HttpsURLConnection;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.IdeaComment;
import com.unicef.domain.IdeaCommentView;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.domain.IdeaEndorsement;
import com.unicef.domain.IdeaHistory;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.IdeaView;
import com.unicef.domain.Like;
import com.unicef.domain.SocialWorkflow;
import com.unicef.domain.TeamMember;
import com.unicef.domain.UserFeed;
import com.unicef.service.IdeaAttachementService;
import com.unicef.service.IdeaCommentService;
import com.unicef.service.IdeaCommentVoteService;
import com.unicef.service.IdeaEndorsementService;
import com.unicef.service.IdeaHistoryService;
import com.unicef.service.IdeaService;
import com.unicef.service.IdeaViewService;
import com.unicef.service.LikeService;
import com.unicef.service.SocialWorkflowService;
import com.unicef.service.TeamMemberService;
import com.unicef.service.UserFeedService;
import com.unicef.userfeed.UserFeedConstant;
import com.unicef.util.IdeaConstant;
import com.unicef.util.IdeaEnum;
import com.unicef.util.IdeaScrumUtil;
import com.unicef.util.IdeaToScrumRoleConstant;
import com.unicef.util.IdeaUtil;
import com.unicef.util.MemberNotificationHandler;
import com.unicef.util.SocialWorkflowEnum;

@Controller
@RequestMapping("VIEW")
public class IdeaController {

	private static final String IDEA_LIST_JSP = "ideaList";
	private static final String CREATE_IDEA_JSP = "createIdea";
	private static final String VIEW_IDEA_JSP = "viewIdea";
	private static final String IDEA_RESPONSE_JSP = "idea_response";
	private static final String IDEA_COMMENT_TEMPLATE = "ideaCommentsTemplet";
	private static final String IDEA_DESC = "idea_desc";

	@Autowired
	private IdeaService ideaService;

	@Autowired
	private IdeaAttachementService ideaAttachementService;

	@Autowired
	private LikeService likeService;

	@Autowired
	private IdeaCommentService ideaCommentService;

	@Autowired
	private IdeaHistoryService ideaHistoryService;

	@Autowired
	private IdeaViewService ideaViewService;
	
	@Autowired
	private SocialWorkflowService socialWorkflowService;
	
	@Autowired
	private IdeaCommentVoteService commentVoteService;
	
	@Autowired
	private TeamMemberService teamMemberService;
	
	@Autowired
	private IdeaEndorsementService ideaEndorsementService;
	
	@Autowired
	private UserFeedService userFeedService;
	
	
	
	private static final Log log = LogFactoryUtil.getLog(IdeaController.class);
	
	
	
	public static String getapicall(){
		Configuration configuration = ConfigurationFactoryUtil.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
		String value = configuration.get("APICALL");
		return value;
	}

	/**
	 * @param locale
	 * @param model
	 * @param renderRequest
	 * @return
	 * @throws SystemException
	 * @throws PortalException
	 */
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,
			RenderResponse renderResponse){
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String urlName = themeDisplay.getLayout().getFriendlyURL();

		String view = ParamUtil.getString(renderRequest, "view");
		log.info(getapicall());
		if(urlName.equalsIgnoreCase("/submit-an-idea")){
			List<AssetCategory>	categoryList = null;
			List<AssetCategory> ideaContestList = null;
			List<AssetCategory> stageList = null;
			List<AssetCategory> ideaTypeList=null;
			List<AssetCategory> edgeCategory = null;
			List<AssetCategory> lineOfbussiness = null;
			 String fileExtension= StringPool.BLANK;
			 boolean isEditStage = false;
			 boolean isCoachUser = false;
			 boolean isAdminUser = false;
			 Role coachRole = null;
			 Role adminRole = null;
			try {
				categoryList = IdeaUtil.getCategoryList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				 ideaContestList = IdeaUtil.getIdeaContestList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				 stageList = IdeaUtil.getIdeaNewStageList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				 ideaTypeList = IdeaUtil.getIdeaTypeList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				 edgeCategory = IdeaUtil.getEdgeServiceCategoryList(
						 themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 lineOfbussiness = IdeaUtil.getBusinessLine(
						 themeDisplay.getScopeGroupId(),themeDisplay.getUserId()); 
				 fileExtension = PropsUtil.get("idea.attachement.file.extension");
				 Date todayDate = new Date();
				 coachRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), "coach");
				 adminRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(),"Administrator");
				 isCoachUser = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), coachRole.getRoleId());
				 isAdminUser = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), adminRole.getRoleId());
				 if(isCoachUser || isAdminUser){
					 isEditStage = true;
				 }
				 
				model.addAttribute("isEditStage", isEditStage);
				model.addAttribute("edgeCategory", edgeCategory); 
				model.addAttribute("isCoachUser", isCoachUser); 
				model.addAttribute("fileExtension", fileExtension);
				model.addAttribute("ideaContestList", ideaContestList);
				model.addAttribute("ideaTypeList", ideaTypeList);
				model.addAttribute("stageList", stageList);
				model.addAttribute("categoryList", categoryList);
				model.addAttribute("submissionDate", todayDate);
				model.addAttribute("lineOfbussiness",lineOfbussiness);
				log.error("just check  rendering....");
			} catch (PortalException e) {
				log.error("Error in getting Idea Category From Vocabulary"+e.getMessage());
			} catch (SystemException e) {
				log.error("Error in getting Idea Category From Vocabulary"+e.getMessage());
			}
			return CREATE_IDEA_JSP;
		}else{
			model.addAttribute("noOfIdeas", ideaService.noofIdeas());		
			return IDEA_LIST_JSP;
		}
	}
	
	@RenderMapping(params = "view=editIdeaOnly")
	public String editIdeaOnly(RenderRequest renderRequest, RenderResponse renderResponse, Model model) {
		
		long ideaId = ParamUtil.getLong(renderRequest, "ideaId");
		String fileExtension = PropsUtil
				.get("idea.attachement.file.extension");
		if (ideaId > 0) {
			model.addAttribute("ideaId", ideaId);
			model.addAttribute("fileExtension", fileExtension);
		}
		
		return IDEA_DESC;
	}
	
	@RenderMapping(params = "view=updateIdea")
	public String updateIdea(RenderRequest renderRequest,
			RenderResponse renderResponse, Model model) {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = ParamUtil.getLong(renderRequest, "ideaId");
			
			List<AssetCategory> categoryList = null;
			List<AssetCategory> ideaContestList = null;
			List<AssetCategory> stageList = null;
			List<AssetCategory> ideaTypeList  = null;
			List<AssetCategory> edgeCategory = null;
			List<AssetCategory> lineOfbussiness = null;
			
			String fileExtension = StringPool.BLANK;
			boolean isUpdateForm = false;
			Idea idea = null;
			
			 boolean isEditStage = false;
			 boolean isCoachUser = false;
			 boolean isAdminUser = false;
			 Role coachRole = null;
			 Role adminRole = null;
			
			List<String> keywordsList=  new ArrayList<String>();
			try {
				if(ideaId > 0){
					idea = ideaService.find(ideaId);
						
					if(Validator.isNotNull(idea.getIdeaKeywords())){
						keywordsList  = Arrays.asList(idea.getIdeaKeywords().split("\\s*,\\s*"));
					}
					isUpdateForm = true;
					model.addAttribute("keywordsList", keywordsList);
					model.addAttribute("isUpdateForm",isUpdateForm);
					
				}
				categoryList = IdeaUtil.getCategoryList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				 ideaContestList = IdeaUtil.getIdeaContestList(
							themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				 stageList = IdeaUtil.getIdeaStageList(
							themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				 ideaTypeList = IdeaUtil.getIdeaTypeList(
							themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				 
				 edgeCategory = IdeaUtil.getEdgeServiceCategoryList(
						 themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 lineOfbussiness = IdeaUtil.getBusinessLine(
						 themeDisplay.getScopeGroupId(),themeDisplay.getUserId()); 
				 fileExtension = PropsUtil
							.get("idea.attachement.file.extension");
				Date todayDate = new Date();
				coachRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), "coach");
				adminRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(),"Administrator");
				
				 isCoachUser = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), coachRole.getRoleId());
				 isAdminUser = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), adminRole.getRoleId());
				 
				 if(isCoachUser || isAdminUser){
					 isEditStage = true;
				 }
				 
				model.addAttribute("isEditStage", isEditStage);
				model.addAttribute("edgeCategory", edgeCategory);  
			    model.addAttribute("isCoachUser", isCoachUser); 
			    model.addAttribute("idea", idea);
			    model.addAttribute("fileExtension", fileExtension);
				model.addAttribute("ideaContestList", ideaContestList);
				model.addAttribute("ideaTypeList", ideaTypeList);
				model.addAttribute("stageList", stageList);
				model.addAttribute("categoryList", categoryList);
				model.addAttribute("submissionDate", todayDate);
				model.addAttribute("lineOfbussiness",lineOfbussiness);
				model.addAttribute("update_idea",1);
			} catch (PortalException e) {
				log.error("Error in getting Idea Category From Vocabulary in update Idea Method"+e.getMessage());
			} catch (SystemException e) {
				log.error("Error in getting Idea Category From Vocabulary in update Idea Method"+e.getMessage());
			}
		return CREATE_IDEA_JSP;
	}

	@RenderMapping(params = "view=workflow")
	public String workflow(RenderRequest renderRequest,RenderResponse renderResponse,Model model){
	
		List<Idea> ideaList = ideaService.findAll();
		for (Idea idea : ideaList) {
			SocialWorkflow socialWorkflow = new SocialWorkflow();
			socialWorkflow.setCreatedDate(idea.getSubmissionDate());
			socialWorkflow.setModifiedDate(idea.getSubmissionDate());
			socialWorkflow.setDescription(idea.getDescription());
			socialWorkflow.setIdea(idea);
			socialWorkflow.setAction(IdeaConstant.SUBMIT_AN_IDEA);
			socialWorkflow.setActionRequirements(IdeaConstant.PLEASE_REVIEW);
			socialWorkflow.setActor(idea.getCoInventorId());
			socialWorkflow.setApp(idea.getStatus());
			socialWorkflow.setTimeNeeded(0L);
			socialWorkflow.setStatus(SocialWorkflowEnum.WORKFLOW_DRAFT.getValue());
			socialWorkflowService.create(socialWorkflow);
		}
		model.addAttribute("noOfIdeas", ideaService.noofIdeas());
		return IDEA_LIST_JSP;
	}

	@RenderMapping(params = "view=viewIdea")
	public String viewIdea(RenderRequest renderRequest,
			RenderResponse renderResponse, Model model) throws SystemException, PortalException{
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = ParamUtil.getLong(renderRequest, "ideaId", 0);
		long duplicateId = 0;
		try{
			duplicateId = ParamUtil.getLong(renderRequest, "duplicate_idea", 0);
		}catch(Exception e){}
		Idea idea = ideaService.find(ideaId);
	
		// ------------------- //
		/*TeamMember teamMember = new TeamMember();
		teamMember.setIdeaId(idea.getIdeaId());
		teamMember.setUserId(themeDisplay.getUserId());
		teamMember.setFullName(themeDisplay.getUser().getFullName());
		
		teamMember.setRoleName(IdeaToScrumRoleConstant.IDEASCRUM_FACILITATOR);
		log.error(idea.getIdeaId()+" "+themeDisplay.getUserId()+" "+themeDisplay.getUser().getFullName()+" "+IdeaToScrumRoleConstant.IDEASCRUM_FACILITATOR);
		teamMemberService.create(teamMember);*/
		
		/*User user;
		try {
			user = UserLocalServiceUtil.getUser(idea.getCoInventorId());
			long ideaid= idea.getCoInventorId();
			TeamMember teammemberI = new TeamMember();
			teammemberI.setIdeaId(idea.getIdeaId());
			teammemberI.setUserId(idea.getCoInventorId());
			teammemberI.setFullName(user.getFullName());
			teammemberI.setRoleName(IdeaToScrumRoleConstant.INVENTOR);
			log.error(idea.getIdeaId()+" "+idea.getCoInventorId()+" "+user.getFullName()+" "+IdeaToScrumRoleConstant.INVENTOR);
			teamMemberService.create(teammemberI);
		} catch (PortalException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}catch (SystemException e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
		
		// ------------------------//
		
		Role role = null;
		boolean isCoachUser = false;
		boolean isPromoteToScrum = false;
		boolean ideaLike = false;
		boolean ideaEndorsed = false;
		long ideaLikeCount = 0;
		long ideaEndorsedCount = 0;
		boolean isRoleforendorsed =false;
		
		String userAvatarURL = StringPool.BLANK;
		List<IdeaHistory> ideaHistories = null;
		try {
			
			userAvatarURL = IdeaUtil.getAvatarURL(idea.getCoInventorId());
			
			ideaLike = likeService.checkIdeaLike(ideaId,themeDisplay.getUserId());
			ideaLikeCount = likeService.getCountOfIdeaLike(ideaId);
			ideaHistories = ideaHistoryService.getIdeaHistoryList(ideaId);
			ideaEndorsed = ideaEndorsementService.checkIdeaEndorsement(ideaId,themeDisplay.getUserId());
			ideaEndorsedCount = ideaEndorsementService.getIdeaEndorsementCount(ideaId);
			 role = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), "coach");
				isCoachUser = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), role.getRoleId());
		} catch (PortalException e1) {
			log.error("Error in getting view Idea Method"+e1.getMessage());
		} catch (SystemException e1) {
			log.error("Error in getting view Idea Method"+e1.getMessage());
		}
		
		/*check coach role and stage of idea for promote idea*/
		PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();
		
		if((idea.getStatus() == IdeaEnum.IDEA_BACKLOG.getValue()) && isCoachUser || permissionChecker.isOmniadmin() ){
			isPromoteToScrum = true;
		}
		
		List<Role> userroles = RoleLocalServiceUtil.getUserRoles(themeDisplay.getUserId());
		
		for (Role _role :userroles){
			System.out.println(_role.getName());
			if(_role.getName().equalsIgnoreCase("sales/marketing senior manager") || _role.getName().equalsIgnoreCase("engineering senior manager")){
				isRoleforendorsed = true;
			}
		}
		System.out.println(isRoleforendorsed);
		
		if(ideaEndorsedCount >= 2){
			idea.setNewStage(NewIdeaStage.FINALIST);
			ideaService.update(idea);
			model.addAttribute("IsideaEndorsed",1);
		}else{
			model.addAttribute("IsideaEndorsed",0);
		}
		
		model.addAttribute("isPrmoted", (idea.getStatus() == IdeaEnum.IDEA_SCRUM.getValue() ? true : false));
		model.addAttribute("isPromoteToScrum", isPromoteToScrum);
		model.addAttribute("ideaHistories", ideaHistories);
		model.addAttribute("ideaLikeCount", ideaLikeCount);
		model.addAttribute("ideaLike", ideaLike);
		model.addAttribute("userAvtar", userAvatarURL);
		model.addAttribute("ideaEndorsed",ideaEndorsed);
		model.addAttribute("ideaEndorsedCount", ideaEndorsedCount);
		model.addAttribute("isRoleforendorsed",isRoleforendorsed);
		model.addAttribute("idea", idea);
		model.addAttribute("historyListSize", ideaHistories.size());
		if(duplicateId == 1){
			model.addAttribute("duplicateidea", 1);
		}
		/* Add entry in ideaView */
		long viewCount =0;
		try{
			viewCount = ideaViewService.getIdeaViewCountByUserId(ideaId,
					themeDisplay.getUserId());
			if (viewCount <= 0) {
				IdeaView ideaView = new IdeaView();
				ideaView.setIdea(idea);
				ideaView.setUserId(themeDisplay.getUserId());
				ideaView.setViewDate(new Date());
				ideaViewService.create(ideaView);
			}
		}catch(Exception e){
			log.error("Error in getting Idea Count Method"+e.getMessage());
		}
		
		return VIEW_IDEA_JSP;
	}
	
	@ActionMapping(params = "action=saveIdea")
	public void saveIdea(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, com.liferay.portal.kernel.json.JSONException{

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			SessionErrors.add(actionRequest, IdeaConstant.ERROR_IDEA_LOG_IN);
			return;
		}
		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);

		long ideaId = ParamUtil.getLong(uploadRequest, "ideaId", 0);
		
		String ideaTitle = ParamUtil.getString(uploadRequest, "ideaTitle",
				StringPool.BLANK);
		
		String invName = ParamUtil.getString(uploadRequest, "invName",
				StringPool.BLANK);
		
		String hiddenTitle = ParamUtil.getString(uploadRequest, "hiddenTitle",
				StringPool.BLANK);
		long ideaProgramme = ParamUtil.getLong(uploadRequest, "ideaContest", 0);
		long hiddenContest = ParamUtil.getLong(uploadRequest, "hiddenContest",
				0);
		long category = ParamUtil.getLong(uploadRequest, "category", 0);
		long hiddenCategory = ParamUtil.getLong(uploadRequest,
				"hiddenCategory", 0);
		long stage = ParamUtil.getLong(uploadRequest, "stage");
		long hiddenStage = ParamUtil.getLong(uploadRequest, "hiddenStage");
		long innovationType = ParamUtil.getLong(uploadRequest, "ideaType", 0);
		long hiddenType = ParamUtil.getLong(uploadRequest, "hiddenType", 0);
		String ideaContent = ParamUtil.getString(uploadRequest, "ideaContent",
				StringPool.BLANK);
		String hiddenDescription = ParamUtil.getString(uploadRequest,
				"hiddenDescription", StringPool.BLANK);
		boolean sliderEdit = ParamUtil.getBoolean(uploadRequest, "sliderEdit");
		String hiddenIdeaTagLine  = ParamUtil.getString(uploadRequest, "hiddenTagTitle");
		String ideaTagLine = ParamUtil.getString(uploadRequest, "ideaDescribeTitle");
		long edgeServiceId = ParamUtil.getLong(uploadRequest, "edgeService",0);
		String keywords = ParamUtil.getString(uploadRequest, "keywords");
		
		long lineofbusiness = ParamUtil.getLong(uploadRequest, "lineofbusiness", 0);
		
		long bussInvType = ParamUtil.getLong(uploadRequest, "bussInvType", 0);
		double version = 1;

		Idea idea = null;
		SocialWorkflow workflow = null;
		IdeaHistory ideaHistory = null;
		
		try{
			Map<String,Object> params = new LinkedHashMap<>();	
			params.put("ideaname",ideaTitle);
			String ideaDescrption = ideaContent.replaceAll("\\<[^>]*>","");
			params.put("ideadetails",ideaDescrption);
			StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	    	String requestPage = getapicall()+"/checkduplicate";
	    	log.info(requestPage);
			URL url = new URL(requestPage);
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		    conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);
	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        String output = "";
	        for (int c; (c = in.read()) >= 0;){
	        	output += ((char)c);
	    	}
	        JSONObject jsonObject = JSONFactoryUtil.createJSONObject(output);
	        if(jsonObject.getInt("response")!=-1){
	        	log.info(jsonObject.getString("ideaname"));
	        	Idea _idea = ideaService.getIdeaByIdeaNam(jsonObject.getString("ideaname"));
	        	if(Validator.isNotNull(_idea.getIdeaId())){
	        		actionResponse.setRenderParameter("view", "viewIdea");
	    			actionResponse.setRenderParameter("ideaId", String.valueOf(_idea.getIdeaId()));
	    			actionResponse.setRenderParameter("duplicate_idea","1");
	    			return;
	        	}
	        	
	        }
        }catch(Exception e){
        	
        }
		if (ideaId > 0) {
			idea = ideaService.find(ideaId);
		} else {
			idea = new Idea();
			idea.setSubmissionDate(new Date());
			
		}
		idea.setIdeaTitle(ideaTitle);
		idea.setInvName(invName);
		idea.setIdeaProgramme(ideaProgramme);
		idea.setIdeaCategory(category);
		idea.setIdeaStage(stage);
		idea.setInnovationType(innovationType);
		idea.setModifiedDate(new Date());
		idea.setDescription(ideaContent);
		idea.setIdeaTagTitle(ideaTagLine);
		idea.setEdgeServiceId(edgeServiceId);
		idea.setIdeaHot(Boolean.FALSE);
		idea.setHotWeight(0.0);
		idea.setIdeaKeywords(keywords);
		idea.setLineOfbussinessId(lineofbusiness);
		idea.setBussInvType(bussInvType);
		idea.setNewStage(1);
		
		if (ideaId <= 0) {
			idea.setVersion(IdeaUtil.getFormattedVersion(version));
		}
		
		
		idea.setStatus(IdeaEnum.IDEA_BACKLOG.getValue());
		idea.setCompanyId(themeDisplay.getCompanyId());
		idea.setCoInventorId(themeDisplay.getUserId());
		idea.setGroupId(themeDisplay.getScopeGroupId());
		
		
		Map<String,Object> _params = new LinkedHashMap<>();
		_params.put("ideaname",idea.getIdeaTitle());
		_params.put("ideaid",idea.getIdeaTitle());	
	    String _ideaDescrption = idea.getDescription().replaceAll("\\<[^>]*>","");
	    _params.put("ideadetails",_ideaDescrption);
		
		StringBuilder _postData = new StringBuilder();
        for (Map.Entry<String,Object> param : _params.entrySet()) {
            if (_postData.length() != 0) _postData.append('&');
            _postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            _postData.append('=');
            _postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] _postDataBytes = _postData.toString().getBytes("UTF-8");
        
    	String request = getapicall()+"/create_file";
		URL _url = new URL(request);
		HttpURLConnection _conn= (HttpURLConnection) _url.openConnection();
		_conn.setRequestMethod( "POST" );
		_conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		_conn.setRequestProperty("Content-Length", String.valueOf(_postDataBytes.length));
		_conn.setDoOutput(true);
		_conn.getOutputStream().write(_postDataBytes);
        Reader _in = new BufferedReader(new InputStreamReader(_conn.getInputStream(), "UTF-8"));
	        
        
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
						|| (!ideaTagLine.equalsIgnoreCase(hiddenIdeaTagLine)))
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
				this.adduserfeed(UserFeedConstant.NEW_IDEA_FEED,UserFeedConstant.All_USER,idea.getIdeaId(),themeDisplay.getUserId());
				ideaHistory = createIdeaHistory(idea, ideaHistory, ideaId,idea.getVersion());
				ideaHistoryService.create(ideaHistory);
				workflow = createIdeaWorkflow(idea,workflow,themeDisplay,isUpdated);
				
				// Add default team members
				// Idea owner
				TeamMember teamMember = new TeamMember();
				teamMember.setIdeaId(idea.getIdeaId());
				teamMember.setUserId(themeDisplay.getUserId());
				teamMember.setFullName(themeDisplay.getUser().getFullName());
				teamMember.setRoleName(IdeaToScrumRoleConstant.INVENTOR);
				teamMemberService.create(teamMember);
				
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
		boolean isImage = false;
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
						
						isImage = true;
						
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
		
		if(!isImage){
			try {
				fileEntry = saveDefaultImage(uploadRequest, themeDisplay);
			} catch (PortalException | SystemException e) {
				log.error("Error in Uploading files in Idea"+e.getMessage());
			}
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
		}
		
		if (sliderEdit) {
			actionResponse.setRenderParameter("view", "viewIdea");
			actionResponse.setRenderParameter("ideaId", String.valueOf(ideaId));
		}
	}

	private FileEntry saveDefaultImage(UploadPortletRequest uploadRequest,ThemeDisplay themeDisplay) throws PortalException, SystemException{
		ServiceContext serviceContext = ServiceContextFactory.getInstance(uploadRequest);
		serviceContext.setAddGuestPermissions(Boolean.TRUE);
		Folder defaultFolder = null;
		try {
			defaultFolder = DLAppServiceUtil.getFolder(
					themeDisplay.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					"Idea Images");
		} catch (PrincipalException e3) {
			log.error(e3.getMessage(), e3);
		} catch (Exception e) {
			try {
				defaultFolder = DLAppServiceUtil.addFolder(
						themeDisplay.getScopeGroupId(),
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
						"Idea Images",
						StringPool.BLANK, serviceContext);
			} catch (Exception e1) {
				log.error(e1.getMessage(), e1);
			}
		}
		FileEntry fileEntry = null;
		try {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(themeDisplay.getScopeGroupId(), defaultFolder.getFolderId(), "IdeaDefault");
		} catch (PortalException | SystemException e) {
			log.error(e.getMessage(), e);
		}
		
		return fileEntry;
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
			ideaHistory.setIdeaKeywords(idea.getIdeaKeywords());
			ideaHistory.setVersion(idea.getVersion());
			ideaHistory.setIdeaTagTitle(idea.getIdeaTagTitle());
			ideaHistory.setEdgeServiceId(idea.getEdgeServiceId());
			ideaHistory.setInvName(idea.getInvName());
			ideaHistory.setLineOfbussinessId(idea.getLineOfbussinessId());
			ideaHistory.setBussInvType(idea.getBussInvType());
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
			ideaHistory.setIdeaKeywords(idea.getIdeaKeywords());
			ideaHistory.setSubmissionDate(new Date());
			ideaHistory.setInnovationType(idea.getInnovationType());
			ideaHistory.setCompanyId(idea.getCompanyId());
			ideaHistory.setGroupId(idea.getGroupId());
			ideaHistory.setModifiedDate(idea.getModifiedDate());
			ideaHistory.setVersion(idea.getVersion());
			ideaHistory.setIdeaTagTitle(idea.getIdeaTagTitle());
			ideaHistory.setEdgeServiceId(idea.getEdgeServiceId());
			ideaHistory.setInvName(idea.getInvName());
			ideaHistory.setLineOfbussinessId(idea.getLineOfbussinessId());
			ideaHistory.setBussInvType(idea.getBussInvType());
			ideaHistory.setIdea(idea);
		}
		return ideaHistory;
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
					"Idea Images");
		} catch (PrincipalException e3) {
			log.error(e3.getMessage(), e3);
		} catch (Exception e) {
			try {
				defaultFolder = DLAppServiceUtil.addFolder(
						themeDisplay.getScopeGroupId(),
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
						"Idea Images",
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
	
	@ResourceMapping(value="updateIdeaInfoURL")
	public void updateIdeaInfoURL(ResourceRequest resourceRequest, ResourceResponse resourceResponse, Model model) {
		
		log.info("edit idea has been completed....... ------------> > > > ---------------- > > > > ");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		
		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(resourceRequest);
		
		long ideaId = ParamUtil.getLong(uploadRequest, "ideaId");
		long ideaVersionId = ParamUtil.getLong(uploadRequest, "ideaVersionId");
		
		String ideaDescription = ParamUtil.getString(uploadRequest, "orgIdeaDesc",
				StringPool.BLANK);
		
		Idea idea = ideaService.find(ideaId);
		if(Validator.isNotNull(idea)) {
			idea.setDescription(ideaDescription);
			ideaService.update(idea);
		}
		
		// long ideaAttachmentId = ParamUtil.getLong(uploadRequest, "ideaUpImageAttachmentId");
		
		FileEntry fileEntry = null;
		String fileName = uploadRequest.getFileName("changeIdeaImageWith");
		if (Validator.isNotNull(fileName) && fileName.length() > 0) {
			int sourceFileExtension = fileName.lastIndexOf(".");
			if (sourceFileExtension != -1) {
				try {
					fileEntry = saveImage(uploadRequest, themeDisplay,
							fileName);
					List<IdeaAttachement> ideaAttachements = ideaAttachementService.findByIdeaIdAndIdeaVersion(ideaId, ideaVersionId);
					if(Validator.isNotNull(ideaAttachements)) {
						IdeaAttachement ideaAttachement = ideaAttachements.get(0);
						ideaAttachement.setFileEntryId(fileEntry.getFileEntryId());
						ideaAttachementService.update(ideaAttachement);
					}
				} catch (PortalException e) {
					log.error("Error in Uploading files in Idea"+e.getMessage());
					SessionErrors.add(resourceRequest, IdeaConstant.ERROR_IN_UPLOADING_FILE);
				} catch (SystemException e) {
					log.error("Error in Uploading files in Idea"+e.getMessage());
					SessionErrors.add(resourceRequest, IdeaConstant.ERROR_IN_UPLOADING_FILE);
				}
			}
		}
		
		// changeIdeaImageWith
	}
	
	@ResourceMapping(value = "getIdeaDataURL")
	public String getIdeaDataURL(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model){
		
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));

		PortletPreferences preferences = resourceRequest.getPreferences();
		
		if(Validator.isNotNull(preferences.getValue("newTHRESHOLD", StringPool.BLANK))){
			model.addAttribute("newTHRESHOLD", preferences.getValue("newTHRESHOLD", StringPool.BLANK));
		}else{
			model.addAttribute("newTHRESHOLD", 0);
		}
		
		if(Validator.isNotNull(preferences.getValue("hotTHRESHOLD", StringPool.BLANK))){
			model.addAttribute("hotTHRESHOLD", preferences.getValue("hotTHRESHOLD", StringPool.BLANK));
		}else{
			model.addAttribute("hotTHRESHOLD", 0);
		}
		
		int recordsSize = Integer.parseInt(request.getParameter("recordsPerPage"));
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		String fieldname = request.getParameter("fieldName");
		String orderBy = request.getParameter("orderBy");

		long start = (long) (pageNo * recordsSize) - recordsSize;
		try{
			List<Idea> ideasList = ideaService.getListOfIdea(start, recordsSize,
					fieldname, orderBy);
			if (ideasList.size() > 0) {
				model.addAttribute("ideaViewService", ideaViewService);
				model.addAttribute("ideaCommentService", ideaCommentService);
				model.addAttribute("ideaService", ideaService);
				model.addAttribute("ideaList", ideasList);
			}
		}catch(Exception e){
			log.error("Error in getting Idea Data Using Ajax Method:::"+e.getMessage());
		}
		return IDEA_RESPONSE_JSP;
	}
	
	@ResourceMapping(value = "checkDuplicateidea")
	public String checkDuplicateidea(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model) throws IOException, com.liferay.portal.kernel.json.JSONException{
		
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));

		String ideaName = ParamUtil.getString(request,"ideaTittle");
		String ideaDetails = ParamUtil.getString(request,"editorHTML");
	    
		Map<String,Object> params = new LinkedHashMap<>();	
		params.put("ideaname",ideaName);
		String ideaDescrption = ideaDetails.replaceAll("\\<[^>]*>","");
		params.put("ideadetails",ideaDescrption);
		
		StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
    	String requestPage = getapicall()+"/checkduplicate";
		URL url = new URL(requestPage);
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();
		conn.setRequestMethod( "POST" );
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	    conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String output = "";
        for (int c; (c = in.read()) >= 0;){
        	output += ((char)c);
    	}
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject(output);
        if(jsonObject.getInt("response")!=-1){
        	log.info(jsonObject.getString("ideaname"));
        	Idea idea = ideaService.getIdeaByIdeaNam(jsonObject.getString("ideaname"));
        	if(Validator.isNotNull(idea.getIdeaId())){
        		log.info(idea.getIdeaId());
        		model.addAttribute("idea_duplicate",1);
        		model.addAttribute("idea",idea);
        	}
        	return "duplicate_idea";
        }else{
        	return "duplicate_idea";	
        }
		
	}

	@ResourceMapping(value = "ideaLikeURL")
	public void ideaLikeURL(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model) {
		
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		
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
				if(idea.getCoInventorId() != themeDisplay.getUserId()){
				  this.adduserfeed(UserFeedConstant.IDEA_UPVOTE,idea.getCoInventorId(),like.getLikeId(),themeDisplay.getUserId());
				}
			} else if (likeValue.equalsIgnoreCase("DownVote")) {
				Like like = likeService.getIdeaLike(ideaId,
						themeDisplay.getUserId());
				userFeedService.deletefeed(UserFeedConstant.IDEA_UPVOTE,like.getLikeId());
				likeService.delete(like.getLikeId());
				
				
			}
			
			long ideaLikeCount = likeService.getCountOfIdeaLike(ideaId);

			StringBuilder countIdeaLike = new StringBuilder();
			countIdeaLike.append(ideaLikeCount);
			
			pw = null;
			
			pw = resourceResponse.getWriter();
			pw.print(countIdeaLike);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
	
	@ResourceMapping(value = "ideaEndrosed")
	public void ideaEndrosed(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model) {
		
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		
		long ideaId = ParamUtil.getLong(request,"ideaId", 0);
		String endrosed = ParamUtil.getString(request,"endorsed");
		PrintWriter pw = null;
		try {
			Idea idea = ideaService.find(ideaId);
			if (endrosed.equalsIgnoreCase("Endorsed")) {
				IdeaEndorsement endorsement = new IdeaEndorsement();
				endorsement.setIdea(idea);
				endorsement.setUserId(themeDisplay.getUserId());
				endorsement.setCompanyId(0);
				endorsement.setCreatedDate(new Date());
				ideaEndorsementService.create(endorsement);
				this.adduserfeed(UserFeedConstant.IDEA_ENDORSED,idea.getCoInventorId(),endorsement.getEndorsmentId(),themeDisplay.getUserId());
			} else if (endrosed.equalsIgnoreCase("DeEndorsed")) {
				IdeaEndorsement IdeaEndorsement = ideaEndorsementService.getcheckIdeaEndorsement(ideaId,
						themeDisplay.getUserId());
				userFeedService.deletefeed(UserFeedConstant.IDEA_ENDORSED,IdeaEndorsement.getEndorsmentId());
				ideaEndorsementService.delete(IdeaEndorsement.getEndorsmentId());
				
			}
			
			long endorsementCount = ideaEndorsementService.getIdeaEndorsementCount(ideaId);
			

			StringBuilder countIdeaLike = new StringBuilder();
			countIdeaLike.append(endorsementCount);
			
			pw = null;
			
			pw = resourceResponse.getWriter();
			pw.print(countIdeaLike);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	@ResourceMapping(value = "ideaCommentView")
	public String viewIdeaComments(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model){
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
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
					log.error("Error in Getting User in IdeaCommentView Method"+e.getMessage());
				} catch (SystemException e) {
					log.error("Error in Getting User in IdeaCommentView Method"+e.getMessage());
				}
				commentView.setUserName(user.getFullName());
				commentView.setAvtarUrl(user.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(user
								.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(
								user.getScreenName(), user.getCompanyId()));
				commentView.setComment(ideaComment.getDescription());
				commentView.setCommentDate(ideaComment.getCreatedDate());
				commentView.setCommenId(ideaComment.getCommentId());
				commentView.setUserId(ideaComment.getUserId());
				ideaCommentViews.add(commentView);
			}
			boolean isInventorPrefsAdmin =  IdeaScrumUtil.isInventorPrefsPermission(themeDisplay.getCompanyId(), themeDisplay.getUserId(), idea.getCoInventorId());
			
			model.addAttribute("isInventorPrefsAdmin", isInventorPrefsAdmin);
			model.addAttribute("ideaCreator", idea.getCoInventorId());
			model.addAttribute("commentVoteService", commentVoteService);
			model.addAttribute("ideaId", ideaId);
			model.addAttribute("commentCount", ideaComments.size());
			model.addAttribute("ideaComments", ideaCommentViews);
		}catch(Exception e){
			log.error("Error in IdeaCommentView Method"+e.getMessage());
		}
		
		return IDEA_COMMENT_TEMPLATE;
	}

	@ResourceMapping(value = "postComment")
	public void postComment(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model) {
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = ParamUtil.getLong(request, "ideaId");
		long commentId = ParamUtil.getLong(request, "commentId");
		String comment = ParamUtil.getString(request, "commentText");
		
		try{
			IdeaComment ideaComment = null;
			
			if(commentId > 0){
				ideaComment = ideaCommentService.find(commentId);
				ideaComment.setModifiedDate(new Date());
			}else{
				ideaComment = new IdeaComment();
				ideaComment.setCreatedDate(new Date());
			}
			ideaComment.setCompanyId(themeDisplay.getCompanyId());
			ideaComment.setDescription(comment);
			ideaComment.setGroupId(themeDisplay.getScopeGroupId());
			ideaComment.setParentCommentId(0);
			ideaComment.setTaskId(0);
			ideaComment.setUserId(themeDisplay.getUserId());
			Idea idea = ideaService.find(ideaId);
			ideaComment.setIdea(idea);
			
			if(commentId > 0){
				ideaCommentService.update(ideaComment);
			}else{
				ideaCommentService.create(ideaComment);
			}
			if (commentId <= 0) {
				if(idea.getCoInventorId() != themeDisplay.getUserId()){
					this.adduserfeed(UserFeedConstant.COMMENT_ON_IDEA,idea.getCoInventorId(),ideaComment.getCommentId(),themeDisplay.getUserId());
				}
			}
		}catch(Exception e){
			log.error("Error in Post Comment Method:::"+e.getMessage());
		}
		
	}

	@ResourceMapping(value = "deleteIdeaURL")
	public void deleteIdeaURL(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model) {
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));

		long ideaId = ParamUtil.getLong(request, "ideaId");
		try {
			/* find the list of attachements and delete */
			/*List<IdeaAttachement> ideaAttachements = ideaService
					.getIdeaAttachementByIdeaId(ideaId);
			for (IdeaAttachement ideaAttachement : ideaAttachements) {
				if (ideaAttachement.getFileEntryId() > 0) {
					DLAppServiceUtil.deleteFileEntry(ideaAttachement
							.getFileEntryId());
				}
			}*/

			/* delete idea attachement entry */
			userFeedService.deletefeed(UserFeedConstant.NEW_IDEA_FEED,ideaId);
			
			List<IdeaComment> ideacomment = ideaCommentService.getIdeaComments(ideaId);
			for(IdeaComment ideacomm:ideacomment){
				userFeedService.deletefeed(UserFeedConstant.COMMENT_ON_IDEA,ideacomm.getCommentId());
				userFeedService.deletefeed(UserFeedConstant.COMMENT_GET_THANK_IDEA,ideacomm.getCommentId());
				
			}
			
			List<IdeaEndorsement> ideaEndorsements = ideaEndorsementService.getIdeaEndorsement(ideaId);
			for(IdeaEndorsement ideacomm:ideaEndorsements){
				userFeedService.deletefeed(UserFeedConstant.IDEA_ENDORSED,ideacomm.getEndorsmentId());
			}
			
			List<Like> like = likeService.getlikebyideaId(ideaId);
			for(Like _like:like){
				userFeedService.deletefeed(UserFeedConstant.IDEA_UPVOTE,_like.getLikeId());
			}
			
			
			ideaService.deleteIdeaAttachementByIdeaId(ideaId);

			/* delete all the link of idea */
			ideaService.deleteIdeaLikesByIdeaId(ideaId);

			/* delete from idea comment vote */
			ideaService.deleteIdeaCommentVoteByIdeaId(ideaId);
			
			/* delete all idea comments */
			ideaService.deleteIdeaCommentsByIdeaId(ideaId);

			/* delete all ideaView */
			ideaService.deleteIdeaViewByIdeaId(ideaId);

			/* delete all ideaHistory */
			ideaService.deleteIdeaHistory(ideaId);
			
			
			/*delete workflow comment and like from taskId*/
			
			List<TeamMember> findMembersByIdeaId = teamMemberService.findMembersByIdeaId(ideaId);
			//	TeamMember t = teamMemberDao.find(ideaId);
				for(TeamMember t : findMembersByIdeaId){
					
					teamMemberService.delete(t.getTeamMemberId());
				}
			
			SocialWorkflow workflow = socialWorkflowService.findWorkflowFromIdeaId(ideaId);
			if(Validator.isNotNull(workflow)){
				ideaService.deleteWorkflowLikeFromTaskId(workflow.getTaskId());
				ideaService.deleteWorkflowCommentFromTaskId(workflow.getTaskId());
				ideaService.deleteIdeaFromWorkflow(ideaId);
			}
			/* delete idea */
			ideaService.delete(ideaId);
			model.addAttribute("noOfIdeas", ideaService.noofIdeas());
		} catch (Exception e) {
			log.error("Idea Not Deleted " + e.getMessage());
			e.printStackTrace();
		}
	}

	@RenderMapping(params = "view=updateFromView")
	public String updateFromView(RenderRequest renderRequest,
			RenderResponse renderResponse, Model model) throws PortalException,
			SystemException {
		
		boolean ideaEditOnly = ParamUtil.getBoolean(renderRequest, "editIdeaInfoOnly");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = ParamUtil.getLong(renderRequest, "ideaId");
		boolean sliderEdit = false;
		try{
			if (ideaId > 0) {
				Idea idea = ideaService.find(ideaId);
				sliderEdit = true;
				List<AssetCategory> categoryList = IdeaUtil.getCategoryList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				List<AssetCategory> ideaContestList = IdeaUtil.getIdeaContestList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				List<AssetCategory> stageList = IdeaUtil.getIdeaNewStageList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				List<AssetCategory> ideaTypeList = IdeaUtil.getIdeaTypeList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
				
				List<AssetCategory> lineOfbussiness = IdeaUtil.getBusinessLine(
						 themeDisplay.getScopeGroupId(),themeDisplay.getUserId()); 
				Date todayDate = new Date();

				model.addAttribute("sliderEdit", sliderEdit);
				model.addAttribute("idea", idea);
				model.addAttribute("ideaContestList", ideaContestList);
				model.addAttribute("ideaTypeList", ideaTypeList);
				model.addAttribute("stageList", stageList);
				model.addAttribute("categoryList", categoryList);
				model.addAttribute("submissionDate", todayDate);
				model.addAttribute("editIdeaOnly", ideaEditOnly);
				model.addAttribute("lineOfbussiness",lineOfbussiness);
				model.addAttribute("update_idea",1);
			}
		}catch(Exception e){
			log.error("Error in Update From View Method" + e.getMessage());
		}
	
		return CREATE_IDEA_JSP;
	}
	
@ActionMapping(params="action=promoteToScrum")
public void promoteToScrum(ActionRequest actionRequest,ActionResponse actionResponse) throws PortalException, SystemException{
	
	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	
	long ideaId = ParamUtil.getLong(actionRequest, "ideaId",0);
	
	Idea idea = ideaService.find(ideaId);
	
	//get the userId for the default user
	long defaultUserId = UserLocalServiceUtil.getDefaultUserId(themeDisplay.getCompanyId());
	Group group = null ;
	
	try{
		/**
		 * @Mahendra Panchal
		 * 
		 * Remarks : When Idea promoted to scrum, Internally site has been created where private page assigned site-template : promoteToIdea 
		 * Before : This site has been accessible to only user who is promote idea to scrum
		 * Issue : Other user does not access this site even idea is promoted as scrum.
		 * Temporary Solution : We are assign every user to this site ( while idea promoted to scrum ). *New user does not added to this site
		 * 
		 * Need to implement : possibly any user can access site/idea scrum ( guest or power user ), assigned or not. 
		 * 
		 */
		// Role userRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), "POWER USER");
		
		//	ServiceContext serviceContext = new ServiceContext();
		//	serviceContext.setAddGroupPermissions(addGroupPermissions);
		// 	serviceContext.setAddGuestPermissions(true);
		
		/*create the site with the name of idea name*/
		 group = GroupLocalServiceUtil.addGroup(defaultUserId, GroupConstants.DEFAULT_PARENT_GROUP_ID, Group.class.getName(), 0, GroupConstants.DEFAULT_LIVE_GROUP_ID, idea.getIdeaTitle(), "description", GroupConstants.TYPE_SITE_OPEN, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, "/" + idea.getIdeaTitle(), true, true, new ServiceContext());
		 
		 //group = GroupLocalServiceUtil.deleteGroup(groupId);
		 /* adding current user in the new site */
		 List<User> user_list = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		 UserLocalServiceUtil.addGroupUsers(group.getGroupId(), user_list);
		 
		 /*find the promoteToIdea site template*/
		 LayoutSetPrototype ourLayoutSetPrototype = null;
		
		for(LayoutSetPrototype layoutSetPrototype : LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(QueryUtil.ALL_POS, QueryUtil.ALL_POS)){
			if("promoteToIdea".equalsIgnoreCase(layoutSetPrototype.getName(themeDisplay.getUser().getLanguageId()))){
				ourLayoutSetPrototype = layoutSetPrototype;
				break;
			}
		}
		
		if(ourLayoutSetPrototype != null){
			/* promoteToIdea layout set to currect site*/
			LayoutSetLocalServiceUtil.updateLayoutSetPrototypeLinkEnabled(group.getGroupId(), true, true,ourLayoutSetPrototype.getUuid());
			
			/* copy all site template pages to site */
			LayoutLocalServiceUtil.getLayouts(group.getGroupId(),true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
			
			TeamMember teamMember = new TeamMember();
			teamMember.setIdeaId(idea.getIdeaId());
			teamMember.setUserId(themeDisplay.getUserId());
			teamMember.setFullName(themeDisplay.getUser().getFullName());
			teamMember.setRoleName(IdeaToScrumRoleConstant.IDEASCRUM_FACILITATOR);
			teamMemberService.create(teamMember);
			
		}
		
		IdeaScrum scrum = new IdeaScrum();
		scrum.setIdeaId(idea.getIdeaId()); 
		scrum.setGroupId(group.getGroupId());
		scrum.setCreatedDate(new Date());
		scrum.setIdeaScrumCreater(themeDisplay.getUserId());
		ideaService.createIdeaScrum(scrum);
		
		/*change idea stage backlog to scrum*/
		idea.setStatus(IdeaEnum.IDEA_SCRUM.getValue());
		ideaService.update(idea);
		
		SocialWorkflow workflow = socialWorkflowService.findWorkflowFromIdeaId(ideaId);
		workflow.setApp(IdeaEnum.IDEA_SCRUM.getValue());
		socialWorkflowService.update(workflow);
		
		String spNdays = actionRequest.getPreferences().getValue("sprintNdays", "3");
		int sprintNDays = 0;
		if(Validator.isNotNull(spNdays)){
			sprintNDays = Integer.parseInt(spNdays);
		}
		
		IdeaScrumUtil.createDefaultSprint(sprintNDays, 7, idea.getCoInventorId(), group.getGroupId(), 
				themeDisplay.getCompanyId(), group.getGroupId(), ideaId,0);
		
		SessionMessages.add(actionRequest, IdeaConstant.IDEA_PROMOTE_TO_SCRUM_SUCCESSFULLY);
	}catch (DuplicateGroupException e){
		SessionErrors.add(actionRequest, IdeaConstant.DUPLICATE_PROMOTE_IDEA_SCRUM);
		SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}catch(Exception e){
		
		log.error(group+" "+ defaultUserId+" "+GroupConstants.DEFAULT_PARENT_GROUP_ID+" "+Group.class.getName()+" "+GroupConstants.DEFAULT_LIVE_GROUP_ID+" "+idea.getIdeaTitle()+" description"+" "+GroupConstants.TYPE_SITE_OPEN+" "+ GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION+ "/" + idea.getIdeaTitle());
		SessionErrors.add(actionRequest, IdeaConstant.ERROR_IN_PRMOTE_IDEA_SCRUM);
		SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}finally{
		actionResponse.setRenderParameter("view", "viewIdea");
		actionResponse.setRenderParameter("ideaId", String.valueOf(ideaId));
	}
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
	
	@ResourceMapping(value="ideaCommentVote")
	public void ideaCommentVote(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
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
			long currentWeekCount = commentVoteService.getCurrentWeekCount(commentId,"commentCount");
			Boolean thankYou = IdeaUtil.checkCommentThanks(commentId, themeDisplay.getUserId());
			
			 JSONObject resultObj = JSONFactoryUtil.createJSONObject();
			  resultObj.put("commentVoteCount", currentWeekCount);
			  resultObj.put("thankYou",thankYou);
			  
			  this.adduserfeed(UserFeedConstant.COMMENT_GET_THANK_IDEA,comment.getUserId(),commentId,themeDisplay.getUserId());		  
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
	
	@ResourceMapping(value="deleteIdeaCommentURL")
	public void deleteIdeaCommentURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		try{
			long commentId = ParamUtil.getLong(request, "commentId");
			
			ideaCommentService.deleteIdeaThanksVoteByComment(commentId);
			ideaCommentService.delete(commentId);
			userFeedService.deletefeed(UserFeedConstant.COMMENT_ON_IDEA,commentId);
			userFeedService.deletefeed(UserFeedConstant.COMMENT_GET_THANK_IDEA,commentId);
		}catch(Exception e){
			log.info("Error in Delete in Idea Comment"+e.getMessage());
		} 
	}
	
	@ResourceMapping(value="getKeywordExtractor")
	public void getKeywordExtractor(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model) throws JSONException{
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		String content = ParamUtil.getString(request, "editorHTML");
		HttpResponse<JsonNode> response = null;
		org.json.JSONObject apiResponse = null;
		double scoreThreshold = 1.2;
		content =  org.json.JSONObject.quote(content); // some libraries use .escape() instead
		String mashapeKey = "hbaakx0lztmshve9hY9BxfN7VOMFp1OdrObjsnQ7bZPXA3pXxt";
		try {
			response = Unirest
					.post("https://maui-v1.p.mashape.com/api/keywords")
					.header("X-Mashape-Key", mashapeKey)
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.body("{\"return_translations\":false,\"return_uris\":false,\"content\": "
							+ content + " ,\"thesaurus\":\"English Wikipedia\"}")
					.asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		try {
			apiResponse = response.getBody().getObject();
		
			JSONArray keywords = apiResponse.getJSONArray("results");
	
			org.json.JSONObject resultItem;
			int topicId;
			String topicTitle;
			double topicScore;
			PrintWriter pw = null;
			List<String> impKeywords = new ArrayList<String>();
			for (int i = 0; i < keywords.length(); i++) {
				resultItem = keywords.getJSONObject(i);
				topicId = resultItem.getInt("id");
				topicTitle = resultItem.getString("title");
				topicScore = resultItem.getDouble("probability");
				if (topicScore > scoreThreshold){
					impKeywords.add(topicTitle);
				}
			}
			pw = null;
			pw = resourceResponse.getWriter();
			pw.print(impKeywords);
			pw.flush();
		} catch (Exception e) {
			if (apiResponse != null) {
				System.out.println(apiResponse.getString("message"));
			} else {
				e.printStackTrace();
			}
		}
	}
	
	@ActionMapping(params="action=approveSubscription")
	public void approveSubscription(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.info("Suscribe user in approve mode -------> ");

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaId = ParamUtil.getLong(actionRequest, "ideaId");
		long userId = ParamUtil.getLong(actionRequest, "userId");
		String redirect = ParamUtil.getString(actionRequest, "redirect");
		
		//long userNotificationEventId = ParamUtil.getLong(actionRequest, "userNotificationEventId");
		
		Idea idea = ideaService.find(ideaId);
		
		try {
			List<TeamMember> list = teamMemberService.findByUserId(userId, ideaId);
			
			if(list.size()==0){
			TeamMember teamMember = new TeamMember();
			teamMember.setIdeaId(ideaId);
			teamMember.setUserId(userId);
			teamMember.setFullName(UserLocalServiceUtil.getUser(userId).getFullName());
			teamMember.setRoleName(IdeaToScrumRoleConstant.SCRUM_TEAM_MEMBER);
			teamMemberService.create(teamMember);
			
			// long groupId = themeDisplay.getScopeGroupId();
			
			long userNotificationEventId = ParamUtil.getLong(actionRequest, "userNotificationEventId");
			
			// Do the good thing you wanted to do...
			// Perhaps delete the notification now that the user has reacted?
		
			UserNotificationEvent usernotification =  UserNotificationEventLocalServiceUtil.getUserNotificationEvent(userNotificationEventId);
			
			/*for(UserNotificationEvent usernotificationevent : usernotification){*/
				
				usernotification.setArchived(true);
			/*}*/
			
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(userNotificationEventId);
			log.info("Notification removed :----------<> " + userNotificationEventId );
			
			actionResponse.setRenderParameter("jspPage", "view");
			
			ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
			
			JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
			payloadJSON.put("notificationType", 0);
			payloadJSON.put("userId", userId);
			payloadJSON.put("user", UserLocalServiceUtil.getUser(userId).getFullName());
			payloadJSON.put("ideaId", ideaId);
			payloadJSON.put("ideaName", idea.getIdeaTitle());
			
			UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
					userId,MemberNotificationHandler.PORTLET_ID,
					(new Date()).getTime(), userId,payloadJSON.toString(),false, serviceContext);
			
			actionResponse.sendRedirect(redirect);
			}
			else{
				
				long userNotificationEventId = ParamUtil.getLong(actionRequest, "userNotificationEventId");
				UserNotificationEvent usernotification =  UserNotificationEventLocalServiceUtil.getUserNotificationEvent(userNotificationEventId);
				UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(userNotificationEventId);
			}
		} catch (PortalException e) {
			log.debug("Failed to remove notification:"+e.getMessage(), e);
		} catch (SystemException e) {
			log.debug("Failed to remove notification:"+e.getMessage(), e);
		} catch (IOException e) {
			log.debug("Failed to remove notification:"+e.getMessage(), e);
		}
		
	}
	
	/*@ActionMapping(params="action=alreadyRequested")
	public void alreadyRequested(ActionRequest actionRequest, ActionResponse actionResponse) {
	
	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long ideaId = ParamUtil.getLong(actionRequest, "ideaId");
		long userId = ParamUtil.getLong(actionRequest, "userId");
		long userNotificationEventId = ParamUtil.getLong(actionRequest, "userNotificationEventId");
		
		Idea idea = ideaService.find(ideaId);
		
	try {
			
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(userNotificationEventId);
			
			actionResponse.setRenderParameter("view", "reject");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
			
			JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
			payloadJSON.put("notificationType", 3);
			payloadJSON.put("userId", userId);
			payloadJSON.put("user", UserLocalServiceUtil.getUser(userId).getFullName());
			payloadJSON.put("ideaId", ideaId);
			payloadJSON.put("ideaName", idea.getIdeaTitle());
			
			UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
					userId,MemberNotificationHandler.PORTLET_ID,
					(new Date()).getTime(), userId,payloadJSON.toString(),false, serviceContext);
			
			String portletName = (String)actionRequest.getAttribute(WebKeys.PORTLET_ID);
			     
			PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(actionRequest),portletName,themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
			
			actionResponse.setRenderParameter("view", "reject");
			
			log.error(redirectURL);
			actionResponse.sendRedirect(redirectURL.toString());
				
			} catch (PortalException e) {
				log.debug("Failed to remove notification:"+e.getMessage(), e);
			} catch (SystemException e) {
				log.debug("Failed to remove notification:"+e.getMessage(), e);
			} catch (IOException e) {
				log.debug("Failed to remove notification:"+e.getMessage(), e);
			}

		
	}*/
	

	@ActionMapping(params="action=rejectSubscription")
	public void rejectSubscription(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.info("Suscribe user in rejected mode");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long ideaId = ParamUtil.getLong(actionRequest, "ideaId");
		long userId = ParamUtil.getLong(actionRequest, "userId");
		long userNotificationEventId = ParamUtil.getLong(actionRequest, "userNotificationEventId");
		
		Idea idea = ideaService.find(ideaId);
		
		// Do the good thing you wanted to do...
		// Perhaps delete the notification now that the user has reacted?
		try {
			
			
			UserNotificationEvent usernotification =  UserNotificationEventLocalServiceUtil.getUserNotificationEvent(userNotificationEventId);
			
			/*for(UserNotificationEvent usernotificationevent : usernotification){*/
				
			//usernotification.setArchived(false);
			usernotification.setArchived(true);
		/*}*/
			
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(userNotificationEventId);
			
			actionResponse.setRenderParameter("view", "reject");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
			
			JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
			payloadJSON.put("notificationType", 1);
			payloadJSON.put("userId", userId);
			payloadJSON.put("user", UserLocalServiceUtil.getUser(userId).getFullName());
			payloadJSON.put("ideaId", ideaId);
			payloadJSON.put("ideaName", idea.getIdeaTitle());
			
		//	payloadJSON.put("requestaction", "clear");
			
			LiferayPortletResponse liferayPortletResponse = serviceContext.getLiferayPortletResponse();
			PortletURL viewURL = liferayPortletResponse.createActionURL("dockbarnotificationaction_WAR_DockBarCustomNotificationportlet");
			viewURL.setParameter("redirect", serviceContext.getLayoutFullURL());
			
			UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
					userId,MemberNotificationHandler.PORTLET_ID,
					(new Date()).getTime(), userId,payloadJSON.toString(),false, serviceContext);
			
			String portletName = (String)actionRequest.getAttribute(WebKeys.PORTLET_ID);
			     
			PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(actionRequest),portletName,themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
			
			actionResponse.setRenderParameter("view", "reject");
			
			log.error(redirectURL);
			actionResponse.sendRedirect(redirectURL.toString());
				
			} catch (PortalException e) {
				log.debug("Failed to remove notification:"+e.getMessage(), e);
			} catch (SystemException e) {
				log.debug("Failed to remove notification:"+e.getMessage(), e);
			} catch (IOException e) {
				log.debug("Failed to remove notification:"+e.getMessage(), e);
			}
			
	}
	
	public static long compare(String string, String compareString){
	    int length = string.length();
	    int comLength = compareString.length();
	    int max = length;
	    int min = comLength;
	    int result = 0;
	    if (length < comLength){
	        max = comLength;
	        min = length;
	    }

	    for(int index = 0; index < min; index++){
	        if(string.charAt(index) == compareString.charAt(index)){
	            result++;
	        }
	    }
	    return (long)(result)/ (long)(max);
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
		 long length = userFeedService.getUserFeedCount(userId);
		 if(end<length){
			 end = (int) length;
		 }
		 List<UserFeed> userfeed = userFeedService.getUserFeed(userId, start, end);
		 return userfeed;
	}
}
