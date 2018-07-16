package com.unicef.portlet.workflow;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.unicef.domain.SocialWorkflow;
import com.unicef.domain.WorkflowComment;
import com.unicef.domain.WorkflowLike;
import com.unicef.service.IdeaService;
import com.unicef.service.SocialWorkflowService;
import com.unicef.service.WorkflowCommentService;
import com.unicef.service.WorkflowLikeService;
import com.unicef.util.SocialWorkflowEnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
public class WorkflowController {
	
	private static final String VIEW = "view";
	private static final String WORKFLOW_RESPONSE = "workflow_response";
	private static final String COMMENT = "comment";
	private static final String WORKFLOW_TASK = "workflow_task";
	
	@Autowired
	private IdeaService ideaservice;
	
	@Autowired
	private SocialWorkflowService workflowService;
	
	@Autowired
	private WorkflowLikeService workflowLikeService;
	
	@Autowired
	private WorkflowCommentService workflowCommentService;
	
	private static final Log log = LogFactoryUtil.getLog(WorkflowController.class);
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException{
		
		
			return VIEW;
	}
	
	@ResourceMapping(value="ideaView")
	public String ideaView(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		int ideaCount = ParamUtil.getInteger(request,"ideaCount");
		int recordsize = 2;
		int start =  (ideaCount * recordsize) - recordsize;
		try{
			List<SocialWorkflow> totalWorkflow = workflowService.getWorkflowList();
			List<SocialWorkflow> workflowList = workflowService.getSortedWorkflowList(start,recordsize);
			
			Role role;
			try {
				role = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), "coach");
				boolean isCoachUser = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), role.getRoleId());
				
				model.addAttribute("workflowCommentService", workflowCommentService);
				model.addAttribute("workflowLikeService", workflowLikeService);
				model.addAttribute("workflowSize", totalWorkflow.size());
				model.addAttribute("isCoachUser", isCoachUser);
				model.addAttribute("workflowList", workflowList);
			} catch (PortalException e) {
				log.error("Error in Idea View Method"+e.getMessage());
			} catch (SystemException e) {
				log.error("Error in Idea View Method"+e.getMessage());
			}
		}catch(Exception e){
			log.error("Error in Idea View Method"+e.getMessage());
		}
		return WORKFLOW_RESPONSE;
	}
	@ResourceMapping(value="workflowLike")
	public void workflowLike(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long taskId = ParamUtil.getLong(request, "taskId");
		boolean isUserLike = ParamUtil.getBoolean(request, "isUserLike");
		try{
			if(isUserLike){
				WorkflowLike like = workflowLikeService.getWorkflowLike(themeDisplay.getUserId(),taskId);
				workflowLikeService.delete(like.getLikeId());
			}else{
				SocialWorkflow socialWorkflow = workflowService.find(taskId);
				WorkflowLike like = new WorkflowLike();
				like.setCompanyId(themeDisplay.getCompanyId());
				like.setGroupId(themeDisplay.getScopeGroupId());
				like.setLikeDate(new Date());
				like.setSocialWorkflow(socialWorkflow);
				like.setUserId(themeDisplay.getUserId());
				workflowLikeService.create(like);
			}
			
			long likeCount = workflowLikeService.getLikeCountByTaskId(taskId);
			StringBuilder builder = new StringBuilder();
			builder.append(likeCount);
			
			PrintWriter pw = null;
			try {
				pw = resourceResponse.getWriter();
				pw.print(builder);
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				pw.close();
			}
		}catch(Exception e){
			log.error("Error in getting Data Of Workflow like Method"+e.getMessage());
		}
	}
	@ResourceMapping(value="workflowComment")
	public String workflowComment(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long taskId = ParamUtil.getLong(request, "taskId");
		String comment = ParamUtil.getString(request, "commentText");
		try{
			SocialWorkflow socialWorkflow = workflowService.find(taskId);
			
			WorkflowComment workflowComment = new WorkflowComment();
			workflowComment.setDescription(comment);
			workflowComment.setCompanyId(themeDisplay.getCompanyId());
			workflowComment.setCreatedDate(new Date());
			workflowComment.setGroupId(themeDisplay.getScopeGroupId());
			workflowComment.setUserId(themeDisplay.getUserId());
			workflowComment.setSocialWorkflow(socialWorkflow);
			workflowCommentService.create(workflowComment);
			
			List<WorkflowComment> comments = workflowCommentService.getCommentFromTaskId(taskId);
			
			model.addAttribute("comments", comments);
			model.addAttribute("workflowCommentService", workflowCommentService);
			model.addAttribute("taskId", taskId);
			model.addAttribute("commentCount", workflowCommentService.getCommentCountByTaskId(taskId));
		}catch(Exception e){
			log.error("Error in Workflow Comment Method"+e.getMessage());
		}
		return COMMENT;
	}
	@ResourceMapping(value="taskStatus")
	public String taskStatus(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long taskId = ParamUtil.getLong(request, "taskId");
		long noOfDay = ParamUtil.getLong(request, "noOfDay");
		try{
			SocialWorkflow workflow = workflowService.find(taskId);
			workflow.setTimeNeeded(noOfDay);
			workflow.setAcceptorUserId(themeDisplay.getUserId());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, (int)noOfDay);
			workflow.setAcceptedDate(calendar.getTime());
			workflow.setModifiedDate(new Date());
			workflow.setStatus(SocialWorkflowEnum.WORKFLOW_IN_PROGRESS.getValue());
			workflowService.update(workflow);
			
			model.addAttribute("workflowCommentService", workflowCommentService);
			model.addAttribute("workflowTask", workflow);
		}catch(Exception e){
			log.error("Error in Task Status Method"+e.getMessage());
		}
		
		return WORKFLOW_TASK;
	}
}
