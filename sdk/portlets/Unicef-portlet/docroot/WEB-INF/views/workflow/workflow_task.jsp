<%@page import="com.unicef.service.WorkflowCommentService"%>
<%@page import="com.unicef.util.SocialWorkflowEnum"%>
<%@page import="com.unicef.util.CommonUtil"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@include file="../init.jsp"%>
<c:set value="${workflowTask.taskId}" var="taskId"/>
<c:set value="${workflowTask.actor}" var="userId" />
<c:set value="${workflowTask.acceptorUserId}" var="acceptedUser" />
<c:set value="${workflowTask.status}" var="status"/>
<c:set value="${workflowTask.acceptedDate}" var="acceptedDate" />
<c:set value="${workflowTask.createdDate}" var="createdDate"/>
<c:set value="${workflowTask.modifiedDate}" var="modifiedDate" />
<c:set value="${workflowTask.idea.ideaId}" var="ideaId"/>
<c:set value="${workflowTask.solution.solutionId}" var="solutionId"/>

<fmt:formatDate pattern="M/dd/yyyy" value="${acceptedDate}" var="newDate" />
<%
WorkflowCommentService commentService = (WorkflowCommentService)request.getAttribute("workflowCommentService");
User acceptedUser = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("acceptedUser"));
String acceptedUserAvtar = acceptedUser.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(acceptedUser.getPortraitId()) :
					IdeaUtil.getUserImagePathScreenName(acceptedUser.getScreenName(), acceptedUser.getCompanyId());

User activityUser = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId"));
String avtarURL = activityUser.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(activityUser.getPortraitId()) :
						IdeaUtil.getUserImagePathScreenName(activityUser.getScreenName(), activityUser.getCompanyId());


PortletURL customPortletURL =null;
if(((Long)pageContext.getAttribute("ideaId")) != null){
	customPortletURL = CommonUtil.createIdeaPortletURL(request, themeDisplay.getPlid(),
			((Long)pageContext.getAttribute("ideaId")) , themeDisplay.getScopeGroupId());	
}else{
	customPortletURL = CommonUtil.createSolutionPortletURL(request, themeDisplay.getPlid(),
			((Long)pageContext.getAttribute("solutionId")), themeDisplay.getScopeGroupId());
}

String modifiedDate = commentService.calculateEpocTime((Date)pageContext.getAttribute("modifiedDate"));

long currentUserId = themeDisplay.getUserId();
User currentUser = UserLocalServiceUtil.getUser(currentUserId);
String currentUserAvtar = currentUser.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(currentUser.getPortraitId()) : 
								IdeaUtil.getUserImagePathScreenName(currentUser.getScreenName(), currentUser.getCompanyId());

pageContext.setAttribute("currentUserAvtar", currentUserAvtar);

pageContext.setAttribute("Draft", SocialWorkflowEnum.WORKFLOW_DRAFT.getValue());
pageContext.setAttribute("InProgress", SocialWorkflowEnum.WORKFLOW_IN_PROGRESS.getValue());
pageContext.setAttribute("Blocked", SocialWorkflowEnum.WORKFLOW_BLOCKED.getValue());
pageContext.setAttribute("Completed", SocialWorkflowEnum.WORKFLOW_COMPLETED.getValue());
%>

	    <div class="sop-user-box">
      	  	<div class="sop-user-imgbox"><img src="<%=acceptedUserAvtar%>" width="120" height="120"></div>
       	 	<div class="sop-user-imgbox2"><img src="<%=request.getContextPath() %>/images/user-round-icon.png" width="26" height="26"></div>
     	 </div>
	  <div class="sop-head"> 
          <span class="sop-head-aero"></span>
          <div class="sop-head-text01">IdeaBacklog</div>
          <div class="sop-head-text02"><%=modifiedDate %></div>
        </div>

        <div class="sop-box02 clearfix">
          <div class="sop-box02-part1 clearfix"><img src="<%=acceptedUserAvtar%>" width="120" height="120"></div>
          <div class="sop-box02-part2 clearfix">
          	<c:choose>
			 			<c:when test="${workflowTask.solution eq null}">
			 			  <div class="clearfix">
			 			  	<a href="#"><%=activityUser .getFirstName() %></a> <liferay-ui:message key="submitted-an-idea"/>
			 			  	 <a href="<%=customPortletURL%>">${workflowTask.idea.ideaTitle}</a> 
			 			  </div>
			 			</c:when>
			 			<c:otherwise>
			 			 <div class="clearfix">
			 			  	<a href="#"><%=activityUser .getFirstName() %></a> <liferay-ui:message key="submitted-a-solution"/>
			 			  	 <a href="<%=customPortletURL%>">${workflowTask.solution.solutionTitle}</a> 
			 			  </div>
			 			</c:otherwise>
			 		</c:choose>
            <div class="clearfix">
            	<liferay-ui:message key="action-requested"/>${workflowTask.actionRequirements}
            </div>
            <div class="clearfix">
            	<liferay-ui:message key="task-accepted-by"/><%=acceptedUser.getFirstName() %>
            </div>
            <div class="clearfix">
            	<liferay-ui:message key="due-date"/>${newDate}
            </div>
            <div class="clearfix" style="margin-top:8px;">
              <div class="pull-left">Status:</div>
              <div class="pull-left clearfix"> 
              <c:if test="${workflowTask.status eq Draft}">
              	<span class="sop-grayB" title="<liferay-ui:message key="defined"/>">D</span> 
              	<span class="sop-whiteB" title="<liferay-ui:message key="in-progress"/>">P</span>
              	<span class="sop-whiteB" title="<liferay-ui:message key="blocked"/>">B</span> 
              	<span class="sop-whiteB" title="<liferay-ui:message key="completed"/>">C</span>
              </c:if>
              <c:if test="${workflowTask.status eq InProgress}">
              	<span class="sop-grayB" title="<liferay-ui:message key="defined"/>">D</span> 
              	<span class="sop-greenB" title="<liferay-ui:message key="in-progress"/>">P</span>
              	<span class="sop-whiteB" title="<liferay-ui:message key="blocked"/>">B</span> 
              	<span class="sop-whiteB" title="<liferay-ui:message key="completed"/>">C</span>
              </c:if>
              <c:if test="${workflowTask.status eq Blocked}">
              	<span class="sop-grayB" title="<liferay-ui:message key="defined"/>">D</span> 
              	<span class="sop-grayB" title="<liferay-ui:message key="in-progress"/>">P</span>
              	<span class="sop-greenB" title="<liferay-ui:message key="blocked"/>">B</span> 
              	<span class="sop-whiteB" title="<liferay-ui:message key="completed"/>">C</span>
              </c:if>
              <c:if test="${workflowTask.status eq Completed}">
              	<span class="sop-grayB" title="<liferay-ui:message key="defined"/>">D</span> 
              	<span class="sop-grayB" title="<liferay-ui:message key="in-progress"/>">P</span>
              	<span class="sop-grayB" title="<liferay-ui:message key="blocked"/>">B</span> 
              	<span class="sop-greenB" title="<liferay-ui:message key="completed"/>">C</span>
              </c:if>
              </div>
            </div>
          </div>
          <div style="clear:both;"></div>
        </div>