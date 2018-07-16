package com.unicef.portlet.solution;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.unicef.domain.IdeaEndorsement;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.SocialWorkflow;
import com.unicef.domain.Solution;
import com.unicef.domain.SolutionAnswer;
import com.unicef.domain.SolutionAnswerComment;
import com.unicef.domain.SolutionAnswerView;
import com.unicef.domain.SolutionAnswerVote;
import com.unicef.domain.SolutionComment;
import com.unicef.domain.SolutionCommentView;
import com.unicef.domain.SolutionLike;
import com.unicef.domain.SolutionView;
import com.unicef.domain.UserFeed;
import com.unicef.portlet.idea.IdeaController;
import com.unicef.service.IdeaService;
import com.unicef.service.SocialWorkflowService;
import com.unicef.service.SolutionAnswerCommentService;
import com.unicef.service.SolutionAnswerService;
import com.unicef.service.SolutionAnswerVoteService;
import com.unicef.service.SolutionCommentService;
import com.unicef.service.SolutionLikeService;
import com.unicef.service.SolutionService;
import com.unicef.service.SolutionViewService;
import com.unicef.service.UserFeedService;
import com.unicef.userfeed.UserFeedConstant;
import com.unicef.userfeed.UserFeedMehtod;
import com.unicef.util.IdeaConstant;
import com.unicef.util.IdeaEnum;
import com.unicef.util.IdeaUtil;
import com.unicef.util.SocialWorkflowEnum;
import com.unicef.util.SolutionConstant;
import com.unicef.util.SolutionEnum;
import com.unicef.util.SolutionScrumUtil;
import com.unicef.util.SolutionUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@RequestMapping("VIEW")
public class SolutionController {
	private static final String VIEW_SOLUTION_JSP = "viewSolution";
	private static final String SOLUTION_LIST_JSP= "solutionList";
	private static final String CREATE_SOLUTION_JSP= "createSolution";
	private static final String SOLUTION_RESPONSE_JSP= "solutionResponse";
	private static final String SOLUTION_COMMENT_JSP= "solutionComment_template";
	private static final String SOLUTION_ANSWER_JSP= "solutionAnswer_template";
	private static final String SOLUTION_ANSWER_COMMENT = "solution_answer_commet_template";

	@Autowired
	private SolutionViewService solutionViewService;
	
	@Autowired
	private SolutionAnswerService solutionAnswerService;
	
	@Autowired
	private SolutionService solutionService;
	
	@Autowired
	private SolutionCommentService solutionCommentService;
	
	@Autowired
	private SolutionLikeService solutionLikeService;
	
	@Autowired
	private SolutionAnswerVoteService solutionAnswerVoteService;
	
	@Autowired
	private SolutionAnswerCommentService solutionAnswerCommentService;
	
	@Autowired
	private SocialWorkflowService socialWorkflowService;
	
	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private UserFeedService userFeedService;
	
	
	 
	private static final Log log = LogFactoryUtil.getLog(SolutionController.class);
	 
	 /**
		 * @param locale
		 * @param model
		 * @param renderRequest
		 * @return
		 * @throws SystemException 
		 * @throws PortalException 
		 */
@RenderMapping
public String view(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException{
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
	        String urlName = themeDisplay.getLayout().getFriendlyURL();
	        if(urlName.equalsIgnoreCase("/submit-a-problem")){
	        	List<AssetCategory> solutionCategoryList=null;
				List<AssetCategory> solutionProgrammeList =null;
				List<AssetCategory> solutionStageList=null;
				List<AssetCategory> solutionTypeList=null;
				List<AssetCategory> lineOfbussiness = null;
				try{
					 solutionCategoryList = SolutionUtil.getSolutionCategory(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
					 solutionProgrammeList = SolutionUtil.getSolutionProgramme(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
					 solutionStageList = SolutionUtil.getsolutionStageList(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
					 solutionTypeList = SolutionUtil.getSolutionSpaceList(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
					 lineOfbussiness = IdeaUtil.getBusinessLine(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
					Date todayDate = new Date();
					
					model.addAttribute("lineOfbussiness", lineOfbussiness);
					model.addAttribute("submissionDate", todayDate);
					model.addAttribute("solutionCategoryList", solutionCategoryList);
					model.addAttribute("solutionProgrammeList", solutionProgrammeList);
					model.addAttribute("solutionTypeList", solutionTypeList);
					model.addAttribute("stageList", solutionStageList);
				}catch(PortalException e){
					log.error("Error in Getting Create Solution Method"+e.getMessage());
				}catch(SystemException e){
					log.error("Error in Getting Create Solution Method"+e.getMessage());
				}
				return CREATE_SOLUTION_JSP;
	        	
	        }else{
	         model.addAttribute("noOfSolution", solutionService.noOfSolution());
			  return SOLUTION_LIST_JSP;
			}
}

@RenderMapping(params="view=workflow")
public String workflow(RenderRequest renderRequest,RenderResponse renderResponse,Model model){
	List<Solution> solutions = solutionService.findAll();
	for (Solution solution : solutions) {
		SocialWorkflow socialWorkflow = new SocialWorkflow();
		
		socialWorkflow.setCreatedDate(solution.getSubmissionDate());
		socialWorkflow.setModifiedDate(solution.getSubmissionDate());
		socialWorkflow.setDescription(solution.getDescription());
		socialWorkflow.setAction(SolutionConstant.SUBMITTED_A_SOLUTION);
		socialWorkflow.setActionRequirements(IdeaConstant.PLEASE_REVIEW);
		socialWorkflow.setActor(solution.getCoInventorId());
		socialWorkflow.setSolution(solution);
		socialWorkflow.setApp(SolutionEnum.SOLUTION_BACKLOG.getValue());
		socialWorkflow.setTimeNeeded(0L);
		socialWorkflow.setStatus(SocialWorkflowEnum.WORKFLOW_DRAFT.getValue());
		socialWorkflowService.create(socialWorkflow);
		
	}
	model.addAttribute("noOfSolution", solutionService.noOfSolution());
	return SOLUTION_LIST_JSP;
}
		
@RenderMapping(params="view=createSolution")
public String createSolution(RenderRequest renderRequest,RenderResponse renderResponse,Model model){
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			List<AssetCategory> solutionCategoryList=null;
			List<AssetCategory> solutionProgrammeList =null;
			List<AssetCategory> solutionStageList=null;
			List<AssetCategory> solutionTypeList=null;
			List<AssetCategory> lineOfbussiness = null;
			try{
				 solutionCategoryList = SolutionUtil.getSolutionCategory(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 solutionProgrammeList = SolutionUtil.getSolutionProgramme(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 solutionStageList = SolutionUtil.getsolutionStageList(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 solutionTypeList = SolutionUtil.getSolutionSpaceList(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 lineOfbussiness = IdeaUtil.getBusinessLine(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				Date todayDate = new Date();
				
				model.addAttribute("lineOfbussiness", lineOfbussiness);
				model.addAttribute("submissionDate", todayDate);
				model.addAttribute("solutionCategoryList", solutionCategoryList);
				model.addAttribute("solutionProgrammeList", solutionProgrammeList);
				model.addAttribute("solutionTypeList", solutionTypeList);
				model.addAttribute("stageList", solutionStageList);
			}catch(PortalException e){
				log.error("Error in Getting Create Solution Method"+e.getMessage());
			}catch(SystemException e){
				log.error("Error in Getting Create Solution Method"+e.getMessage());
			}
			return CREATE_SOLUTION_JSP;
}

@RenderMapping(params="view=viewSolution")
public String viewSolution(RenderRequest renderRequest,RenderResponse renderResponse,Model model){
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			long solutionId = ParamUtil.getLong(renderRequest, "solutionId",0);
			
			Solution solution = solutionService.find(solutionId);
			User user=null;
			String userIcon=StringPool.BLANK;
			String userDisplayURL = StringPool.BLANK;
			boolean solutionLike = false;
			boolean isCoachUser = false;
			boolean isPromoteToScrum = false;
			long solutionLikeCount=0;
			Role role = null;
			try {
				user = UserLocalServiceUtil.getUser(solution.getCoInventorId());
				userIcon =  (user.getPortraitId() > 0 ?  IdeaUtil.getUserImagePath(user.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(user.getScreenName(), user.getCompanyId()));
				solutionLike = solutionLikeService.checkSolutionLike(solutionId,themeDisplay.getUserId());
				solutionLikeCount = solutionLikeService.getCountOfSolutionLike(solutionId);
				userDisplayURL = user.getDisplayURL(themeDisplay);
				/*add entry in solutionView*/
			    long viewCount = solutionViewService.getSolutionViewCountByUserId(solutionId, themeDisplay.getUserId());
			     if(viewCount <= 0){
			      SolutionView solutionView = new SolutionView();
			      solutionView.setSolution(solution);
			      solutionView.setUserId(themeDisplay.getUserId());
			      solutionView.setViewDate(new Date());
			      solutionViewService.create(solutionView);
			     }
			    
			     role = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), "coach");
				isCoachUser = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), role.getRoleId());
			    
				if((solution.getStatus() == SolutionEnum.SOLUTION_BACKLOG.getValue()) && isCoachUser){
						isPromoteToScrum = true;
				}
				model.addAttribute("isPrmoted", (solution.getStatus() == SolutionEnum.SOLUTION_SCRUM.getValue() ? true : false));
				model.addAttribute("isPromoteToScrum", isPromoteToScrum);
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
			return VIEW_SOLUTION_JSP;
}

@RenderMapping(params="view=updateSolution")
public String updateSolution(RenderRequest renderRequest,RenderResponse renderResponse,Model model){
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			long solutionId = ParamUtil.getLong(renderRequest, "solutionId");
			
			List<AssetCategory> solutionCategoryList=null;
			List<AssetCategory> solutionProgrammeList=null;
			List<AssetCategory> solutionStageList=null;
			List<AssetCategory> solutionTypeList=null;
			List<AssetCategory> lineOfbussiness = null;
			
			Solution solution =null;
		
			if(solutionId > 0){
			 try {
				 solution = solutionService.find(solutionId);
				 solutionCategoryList = SolutionUtil.getSolutionCategory(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 solutionProgrammeList = SolutionUtil.getSolutionProgramme(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 solutionStageList = SolutionUtil.getsolutionStageList(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 solutionTypeList = SolutionUtil.getSolutionSpaceList(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
				 
				 Date todayDate = new Date();
					model.addAttribute("solution", solution);
					model.addAttribute("lineOfbussiness", lineOfbussiness);
					model.addAttribute("submissionDate", todayDate);
					model.addAttribute("solutionCategoryList", solutionCategoryList);
					model.addAttribute("solutionProgrammeList", solutionProgrammeList);
					model.addAttribute("solutionTypeList", solutionTypeList);
					model.addAttribute("stageList", solutionStageList);
			 	} catch (PortalException e) {
			 		log.error("Error in Update Solution Method"+e.getMessage());
				} catch (SystemException e) {
					log.error("Error in Update Solution Method"+e.getMessage());
				}		
			}
			return CREATE_SOLUTION_JSP;
}

	@ResourceMapping(value="solutionLikeURL")
	public void solutionLikeURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
				HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
				ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
				long solutionId = ParamUtil.getLong(request, "solutionId",0);
				String likeValue = ParamUtil.getString(request, "like");
				try{
					Solution solution = solutionService.find(solutionId);
					if(likeValue.equalsIgnoreCase("UpVote")){
						SolutionLike solutionLike = new SolutionLike();
						solutionLike.setTaskId(0);
						solutionLike.setLikeDate(new Date());
						solutionLike.setUserId(themeDisplay.getUserId());
						solutionLike.setSolution(solution);
						solutionLikeService.create(solutionLike);
						this.adduserfeed(UserFeedConstant.SOLUTION_UPVOTE,solution.getCoInventorId(),solutionLike.getLikeId(),themeDisplay.getUserId());						
					}else if(likeValue.equalsIgnoreCase("DownVote")){
						SolutionLike soluLike = solutionLikeService.getIdeaLike(solutionId,themeDisplay.getUserId());
						solutionLikeService.delete(soluLike.getLikeId());
						userFeedService.deletefeed(UserFeedConstant.SOLUTION_UPVOTE,soluLike.getLikeId());
					}
					long solutionLikeCount = solutionLikeService.getCountOfSolutionLike(solutionId);
					StringBuilder countIdeaLike = new StringBuilder();
					countIdeaLike.append(solutionLikeCount);
					PrintWriter pw = null;
					try {
						pw = resourceResponse.getWriter();
						pw.print(countIdeaLike);
						pw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						pw.close();
					}
				}catch(Exception e){
					log.error("Error in SolutionLike Method"+e.getMessage());
				}
	}
			
	@ActionMapping(params="action=saveSolution")
	public void saveSolution(ActionRequest actionRequest,ActionResponse actionResponse){
				ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
				
				if(!themeDisplay.isSignedIn()){
					SessionErrors.add(actionRequest, SolutionConstant.ERROR_SOLUTION_LOG_IN);
					return;
				}
				long solutionId = ParamUtil.getLong(actionRequest, "solutionId",0);
				String solutionTitle = ParamUtil.getString(actionRequest, "solutionTitle",StringPool.BLANK);
				long solutionProgramme = ParamUtil.getLong(actionRequest, "solutionProgramme",0);
				long solutionStage = ParamUtil.getLong(actionRequest, "stage",0);
				long solutionSpace = ParamUtil.getLong(actionRequest, "solutionSpace",0);
				long solutionCategory = ParamUtil.getLong(actionRequest, "category",0);
				String solutionContent = ParamUtil.getString(actionRequest, "solutionContent",StringPool.BLANK);
				String tagLine =  ParamUtil.getString(actionRequest,"challengeTagline",StringPool.BLANK);
				long lof = ParamUtil.getLong(actionRequest,"LinOfBusiness",0);
				
				Solution solution = null;
				SocialWorkflow socialWorkflow = null;
				if(solutionId > 0){
					solution = solutionService.find(solutionId);
				}else{
					solution = new Solution();
					solution.setSubmissionDate(new Date());
				}
				solution.setSolutionTitle(solutionTitle);
				solution.setSolutionProgramme(solutionProgramme);
				solution.setSolutionStage(solutionStage);
				solution.setSpaceId(solutionSpace);
				solution.setSolutionCategory(solutionCategory);
				solution.setStatus(SolutionEnum.SOLUTION_BACKLOG.getValue());
				solution.setDescription(solutionContent);
				solution.setCompanyId(themeDisplay.getCompanyId());
				solution.setModifiedDate(new Date());
				solution.setCoInventorId(themeDisplay.getUserId());
				solution.setGroupId(themeDisplay.getScopeGroupId());
				solution.setSolutionHot(Boolean.FALSE);
				solution.setHotWeight(0.0);
				solution.setSolutiontagline(tagLine);
				solution.setLineofbussiness(lof);
				
				boolean isUpdated = true;
				if(solutionId > 0){
						solutionService.update(solution);
						socialWorkflow = createSolutionWorkflow(solution,themeDisplay,socialWorkflow,isUpdated);
						SessionMessages.add(actionRequest, SolutionConstant.SUCCESS_SOLUTION_MESSAGE_UPDATE);
				}else{
						isUpdated = false;
						solutionService.create(solution);
						socialWorkflow = createSolutionWorkflow(solution,themeDisplay,socialWorkflow,isUpdated);
						SessionMessages.add(actionRequest,SolutionConstant.SUCCESS_SOLUTION_MESSAGE_CREATE);
						this.adduserfeed(UserFeedConstant.NEW_PROBLEM,UserFeedConstant.All_USER,solution.getSolutionId(),themeDisplay.getUserId());
				}
	}
	@ResourceMapping(value="getSolutionDataURL")
	public String getSolutionDataURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
				HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
				
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
				try{
					long start = (long) (pageNo * recordsSize) - recordsSize;
					List<Solution> solutions = solutionService.getListofSoultion(start,recordsSize,fieldname,orderBy);
					if(solutions.size() > 0){
						model.addAttribute("solutionAnswerVoteService", solutionAnswerVoteService);
						model.addAttribute("solutionViewService", solutionViewService);
					    model.addAttribute("solutionCommentService", solutionCommentService);
					    model.addAttribute("solutionAnswerService", solutionAnswerService);
						model.addAttribute("solutionList", solutions);
					}
				}catch(Exception e){
					log.error("Error in Get Solution Data URl MEthod"+e.getMessage());
				}
				return SOLUTION_RESPONSE_JSP;
	}

	@ResourceMapping(value="deleteSolutionURL")
	public void deleteSolutionURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
			   HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
			  
			   long solutionId = ParamUtil.getLong(request, "solutionId");
			   try{
			    /*find the list of attachements and delete*/
				   
				   List<SolutionLike> solutionlike = solutionLikeService.getlikebySolutionId(solutionId);
				   
				  
					for(SolutionLike _solutionlike:solutionlike){
						userFeedService.deletefeed(UserFeedConstant.IDEA_ENDORSED,_solutionlike.getSolution().getSolutionId());
					}
					
					List<SolutionAnswerVote> solutionAnswerVote = solutionAnswerVoteService.getlikebySolutionId(solutionId);
					
					for(SolutionAnswerVote _SolutionAnswerVote:solutionAnswerVote){
						userFeedService.deletefeed(UserFeedConstant.IDEA_ENDORSED,_SolutionAnswerVote.getSolutionId());
					}
					
			     
			    /*delete all solution likes*/
				   solutionService.deleteSolutionLikesBySolutionId(solutionId);
			    
			    /*delete all solution comments*/
				   solutionService.deleteSolutionCommentsBySolutionId(solutionId);
				   
				   solutionService.deleteSolutionAnswerVoteBySolutionId(solutionId);
				   
				   /*delete all solution answer comment*/
					  solutionService.deleteSolutionAnswerComment(solutionId);
			    
			    /*delete all solution answers*/
				   solutionService.deleteSolutionAnswerBySolutionId(solutionId);
			    
			    /*delete all solution views*/
				  solutionService.deleteSolutionViewBySolutionId(solutionId);
				  
				  userFeedService.deletefeed(UserFeedConstant.NEW_PROBLEM,solutionId);
				  
			 
				  
				SocialWorkflow workflow = socialWorkflowService.findWorkflowFromSolutionId(solutionId);
			    if(Validator.isNotNull(workflow)){
			    	solutionService.deleteSolutionByLikeFromWorkflow(workflow.getTaskId());
			    	solutionService.deleteSolutionByCommentFromWorkflow(workflow.getTaskId());
			    	solutionService.deleteSolutionFromWorkflow(solutionId);
			    }
				/*delete solution*/
			     solutionService.delete(solutionId);
			    
			    model.addAttribute("noOfIdeas", solutionService.noofSolutions());
			   }catch(Exception e){
			    log.error("Solution Not Deleted"+ e.getMessage());
			   }
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
		
		return SOLUTION_ANSWER_JSP;
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
			 return SOLUTION_COMMENT_JSP;
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
			    this.adduserfeed(UserFeedConstant.SOLUTION_ANSWER_UPVOTE,solutionAnswer.getUserId(),solutionAnswerVote.getSolutionAnswerVoteId(),themeDisplay.getUserId());
			   }else if(answerVote.equalsIgnoreCase("DownVote")){
			    SolutionAnswerVote solutionAnswerVote = solutionAnswerVoteService.getSolutionAnswerVote(solutionAnswerId,themeDisplay.getUserId());
			    solutionAnswerVoteService.delete(solutionAnswerVote.getSolutionAnswerVoteId());
			    userFeedService.deletefeed(UserFeedConstant.SOLUTION_ANSWER_UPVOTE,solutionAnswerVote.getSolutionAnswerVoteId());
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
		
@ResourceMapping(value = "postSolutionComment")
public void postSolutionComment(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
			 HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
			 ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			  long solutionId = ParamUtil.getLong(request, "solutionId");
			  String comment = ParamUtil.getString(request, "commentText");
			  try{
				  Solution solution = solutionService.find(solutionId);
				  
				  SolutionComment solutionComment = new SolutionComment();
				  solutionComment.setCompanyId(themeDisplay.getCompanyId());
				  solutionComment.setCreatedDate(new Date());
				  solutionComment.setParentCommentId(0);
				  solutionComment.setDescription(comment);
				  solutionComment.setGroupId(themeDisplay.getScopeGroupId());
				  solutionComment.setUserId(themeDisplay.getUserId());
				  solutionComment.setTaskId(0);
				  solutionComment.setSolution(solution);
				  solutionCommentService.create(solutionComment); 
				  
				  this.adduserfeed(UserFeedConstant.COMMENT_ON_PROBLEM,solution.getCoInventorId(),solutionComment.getCommentId(),themeDisplay.getUserId());
			 
			  }catch(Exception e){
				  log.error("Error in Getting Post Solution Comment Method"+e.getMessage());
			  }
}
		 
@ResourceMapping(value = "postAnswers")
public void postAnswers(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
			 HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
			 ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			  long solutionId = ParamUtil.getLong(request, "solutionId");
			  String comment = ParamUtil.getString(request, "commentText");
			  try{
				  Solution solution = solutionService.find(solutionId);
				  
				  SolutionAnswer solutionAnswer = new SolutionAnswer();
				  solutionAnswer.setCompanyId(themeDisplay.getCompanyId());
				  solutionAnswer.setGroupId(themeDisplay.getScopeGroupId());
				  solutionAnswer.setCreatedDate(new Date());
				  solutionAnswer.setDescription(comment);
				  solutionAnswer.setUserId(themeDisplay.getUserId());
				  solutionAnswer.setSolution(solution);
				  solutionAnswerService.create(solutionAnswer);
				  this.adduserfeed(UserFeedConstant.ANSWER_ON_PROBLEM,solution.getCoInventorId(),solutionAnswer.getAnswerId(),themeDisplay.getUserId());
					 
			  }catch(Exception e){
				  log.error("Error in Post Answers Method"+e.getMessage());
			  }
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
		 return SOLUTION_ANSWER_COMMENT;
	}
	@ResourceMapping(value = "postAnswerComment")
	public String postAnswerComment(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		 HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		 ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		 
		 long solutionId = ParamUtil.getLong(request, "solutionId");
		 long solutionAnswerId = ParamUtil.getLong(request, "solutionAnswerId");
		 String comment = ParamUtil.getString(request, "commentText");
		 try{
			 SolutionAnswer solutionAnswer = solutionAnswerService.find(solutionAnswerId);
			 
			 SolutionAnswerComment answerComment = new SolutionAnswerComment();
			 answerComment.setSolutionId(solutionId);
			 answerComment.setUserId(themeDisplay.getUserId());
			 answerComment.setDescription(comment);
			 answerComment.setCommentDate(new Date());
			 answerComment.setSolutionAnswer(solutionAnswer);
			 solutionAnswerCommentService.create(answerComment);
			 
			 this.adduserfeed(UserFeedConstant.SOLUTION_ANSWER_COMMENT,solutionAnswer.getUserId(),solutionId,themeDisplay.getUserId());
			 
			 
			 List<SolutionAnswerComment> comments = solutionAnswerCommentService.getListOfComment(solutionAnswerId);
			 List<SolutionCommentView> commentViews = new ArrayList<SolutionCommentView>();
			 long answerCommentCount = solutionAnswerCommentService.getCountOfAnswerComment(solutionAnswerId);
			 SolutionCommentView commentView;
			 for (SolutionAnswerComment solutionAnswerComment : comments) {
				 commentView = new SolutionCommentView();
				 User user=null;
				try {
					user = UserLocalServiceUtil.getUser(solutionAnswerComment.getUserId());
				} catch (PortalException e) {
					log.error("Error in Getting Users in PostAnswer Comment method"+e.getMessage());
				}catch(SystemException e){
					log.error("Error in Getting Users in PostAnswer Comment method"+e.getMessage());
				}
				 commentView.setUserName(user.getFirstName());
				 commentView.setAvtarUrl(user.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(user.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(user.getScreenName(), user.getCompanyId()));
				 commentView.setCommentDate(solutionAnswerComment.getCommentDate());
				 commentView.setComment(solutionAnswerComment.getDescription());
				 commentView.setSolutionAnswerId(solutionAnswerId);
				 commentViews.add(commentView);
			 }
			 model.addAttribute("solutionAnsId", solutionAnswerId);
			 model.addAttribute("commentView", commentViews);
			 model.addAttribute("answerCommentCount", answerCommentCount);
		 }catch(Exception e){
			 log.error("Error in Getting post Answe Comment Method"+e.getMessage());
		 }
		 return SOLUTION_ANSWER_COMMENT;
		
	}
	private SocialWorkflow createSolutionWorkflow(Solution solution,
			ThemeDisplay themeDisplay, SocialWorkflow socialWorkflow, boolean isUpdated){
		
		if(isUpdated){
			socialWorkflow = socialWorkflowService.findWorkflowFromSolutionId(solution.getSolutionId());
			socialWorkflow.setModifiedDate(new Date());
			socialWorkflow.setDescription(solution.getDescription());
			socialWorkflow.setAction(SolutionConstant.SUBMITTED_A_SOLUTION);
			socialWorkflow.setActionRequirements(IdeaConstant.PLEASE_REVIEW);
			socialWorkflow.setActor(themeDisplay.getUserId());
			socialWorkflow.setSolution(solution);
			if(socialWorkflow.getStatus()== SocialWorkflowEnum.WORKFLOW_DRAFT.getValue()){
				socialWorkflow.setTimeNeeded(0L);
				socialWorkflow.setStatus(SocialWorkflowEnum.WORKFLOW_DRAFT.getValue());
			}
			socialWorkflowService.update(socialWorkflow);
		}else{
			socialWorkflow = new SocialWorkflow();
			socialWorkflow.setCreatedDate(new Date());
			socialWorkflow.setModifiedDate(new Date());
			socialWorkflow.setDescription(solution.getDescription());
			socialWorkflow.setAction(SolutionConstant.SUBMITTED_A_SOLUTION);
			socialWorkflow.setActionRequirements(IdeaConstant.PLEASE_REVIEW);
			socialWorkflow.setActor(themeDisplay.getUserId());
			socialWorkflow.setSolution(solution);
			socialWorkflow.setApp(SolutionEnum.SOLUTION_BACKLOG.getValue());
			socialWorkflow.setTimeNeeded(0L);
			socialWorkflow.setStatus(SocialWorkflowEnum.WORKFLOW_DRAFT.getValue());
			socialWorkflowService.create(socialWorkflow);
		}
		return socialWorkflow;
	}
	
	@ActionMapping(params="action=promoteToScrum")
	public void promoteToScrum(ActionRequest actionRequest,ActionResponse actionResponse,Model model){
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long solutionId = ParamUtil.getLong(actionRequest, "solutionId",0);
		Solution solution = solutionService.find(solutionId);
		
		long defaultUserId = 0;
		//get the userId for the default user
		try {
			 defaultUserId = UserLocalServiceUtil.getDefaultUserId(themeDisplay.getCompanyId());
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		Group group = null ;
		
		 try {
			group = GroupLocalServiceUtil.addGroup(defaultUserId, GroupConstants.DEFAULT_PARENT_GROUP_ID, Group.class.getName(), 0, GroupConstants.DEFAULT_LIVE_GROUP_ID, solution.getSolutionTitle(), "description", GroupConstants.TYPE_SITE_OPEN, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, "/" + solution.getSolutionTitle(), true, true, new ServiceContext());
		
			UserLocalServiceUtil.addGroupUser(group.getGroupId(), themeDisplay.getUserId());
			

			/*find the promoteToIdea site template*/
			LayoutSetPrototype ourLayoutSetPrototype = null;
			
			for(LayoutSetPrototype layoutSetPrototype : LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(QueryUtil.ALL_POS, QueryUtil.ALL_POS)){
				if("promoteToSolution".equalsIgnoreCase(layoutSetPrototype.getName(themeDisplay.getUser().getLanguageId()))){
					ourLayoutSetPrototype = layoutSetPrototype;
					break;
				}
			}
			if(ourLayoutSetPrototype != null){
				/*promoteToIdea layout set to currect site*/
				LayoutSetLocalServiceUtil.updateLayoutSetPrototypeLinkEnabled(group.getGroupId(), true, true, ourLayoutSetPrototype.getUuid());
				
				/*copy all site template pages to site */
				LayoutLocalServiceUtil.getLayouts(group.getGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
			}
			
			
			IdeaScrum scrum = new IdeaScrum();
			scrum.setIdeaId(0); 
			scrum.setSolutionId(solution.getSolutionId());
			scrum.setGroupId(group.getGroupId());
			scrum.setCreatedDate(new Date());
			scrum.setIdeaScrumCreater(themeDisplay.getUserId());
			ideaService.createIdeaScrum(scrum);
			
			solution.setStatus(SolutionEnum.SOLUTION_SCRUM.getValue());
			solutionService.update(solution);
			
			SocialWorkflow workflow = socialWorkflowService.findWorkflowFromSolutionId(solutionId);
			workflow.setApp(SolutionEnum.SOLUTION_SCRUM.getValue());
			socialWorkflowService.update(workflow);
			
			String spNdays = actionRequest.getPreferences().getValue("sprintNdays", "3");
			int sprintNDays = 0;
			if(Validator.isNotNull(spNdays)){
				sprintNDays = Integer.parseInt(spNdays);
			}
			
			SolutionScrumUtil.createDefaultSprint(sprintNDays,7,solution.getCoInventorId(),group.getGroupId(),
					themeDisplay.getCompanyId(),group.getGroupId(),solutionId,0);
			SessionMessages.add(actionRequest, SolutionConstant.SOLUTION_PROMOTE_TO_SCRUM);
		 } catch (PortalException e) {
				SessionErrors.add(actionRequest, SolutionConstant.ERROR_IN_PRMOTE_SOLUTION_SCRUM);
		} catch (SystemException e) {
			SessionErrors.add(actionRequest, SolutionConstant.ERROR_IN_PRMOTE_SOLUTION_SCRUM);
		}finally{
			actionResponse.setRenderParameter("view", "viewSolution");
			actionResponse.setRenderParameter("solutionId", String.valueOf(solutionId));
		}
	}
	
	public void adduserfeed(long feedtype,long userId,long referId,long byuserId){
		UserFeed userfeed = new UserFeed();
		userfeed.setCreatedDate(new Date());
		userfeed.setUserId(userId);
		userfeed.setReferId(referId);
		userfeed.setByuserId(byuserId);
		userfeed.setFeedtypeId(feedtype);
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
