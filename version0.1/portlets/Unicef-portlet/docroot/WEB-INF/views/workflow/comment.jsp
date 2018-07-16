<%@page import="com.unicef.service.WorkflowCommentService"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@include file="../init.jsp"%>
<input type="hidden" id='<portlet:namespace/>commentCount_${taskId}' name='<portlet:namespace/>commentCount${taskId}' value="${commentCount}">

<c:forEach items="${comments}" var="comment">
<div class="clearfix buffer-bottom">
				<c:set value="${comment.userId}" var="commentUser"/>
				<c:set value="${comment.createdDate}" var="createdDate"/>
				<c:set value="${comment.modifiedDate}" var="modifiedDate"/>
			<%
			WorkflowCommentService commentService = (WorkflowCommentService)request.getAttribute("workflowCommentService");
				User commentUser = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("commentUser")); 
				String commentUserAvtar =  commentUser.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(commentUser.getPortraitId()) :
					IdeaUtil.getUserImagePathScreenName(commentUser.getScreenName(), commentUser.getCompanyId());
				
				String commentTime = commentService.calculateEpocTime((Date)pageContext.getAttribute("createdDate"));
				
				String commentUserName = commentUser.getFullName();
				
				pageContext.setAttribute("commentTime", commentTime);
				pageContext.setAttribute("commentUserName", commentUserName);
				pageContext.setAttribute("commentUserAvtar", commentUserAvtar);
			%>
	      	<div class="sop-comment-part1 clearfix">
			  	<img src="${commentUserAvtar}" >
			 </div>
            <div class="sop-comment-part2 clearfix">
              <div class="sop-comment-title1"> <span>${commentUserName}</span><span>${commentTime}</span> </div>
              <div class="sop-comment-text01">${comment.description}</div>
            </div>
    </div>
</c:forEach>

	