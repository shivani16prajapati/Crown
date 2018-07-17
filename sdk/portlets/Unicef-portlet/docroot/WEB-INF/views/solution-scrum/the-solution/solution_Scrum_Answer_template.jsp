<%@include file="../../init.jsp"%>
<%@page import="com.unicef.service.SolutionAnswerCommentService"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.unicef.domain.SolutionAnswerVote"%>
<%@page import="com.unicef.service.SolutionAnswerVoteService"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.unicef.dao.SolutionAnswerVoteDAO"%>
<%@page import="com.unicef.service.SolutionAnswerService"%>
<%@page import="com.unicef.domain.SolutionAnswer"%>


 <input type="hidden" name='<portlet:namespace/>solutionAnswerCount' id='<portlet:namespace/>solutionAnswerCount' value="${answerCount}">
 <input type="hidden" name='<portlet:namespace/>solutionAnswerSize' id='<portlet:namespace/>solutionAnswerSize' value="${answerSize}">
<script type="text/javascript">
		var addthis_config = {
    		 pubid: "ra-550d8fe213da5069"
		}
	</script>
<script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js"></script>

 <c:forEach items="${answerList}" var="amswer">
 	<c:set var="ansId" value="${amswer.answerId}"/>
	<c:set var="upvoteDisplayUserList" value="${amswer.upvoteDisplayUsers}"/>
	<c:set var="upvoteMoreUserList" value="${amswer.upvoteMoreUsers}"/>

<%
	SolutionAnswerVoteService solutionAnswerVoteService = (SolutionAnswerVoteService)request.getAttribute("solutionAnswerVoteService");
	SolutionAnswerCommentService answerCommentService = (SolutionAnswerCommentService)request.getAttribute("solutionAnswerCommentService");
	Boolean solutionAnswerVote = solutionAnswerVoteService.checkSolutionAnswerVote((Long)pageContext.getAttribute("ansId"),themeDisplay.getUserId());
	long solutionAnswerVoteCount = solutionAnswerVoteService.getCountOfSolutionAnswerVote((Long)pageContext.getAttribute("ansId"));
	long answerCommentCount = answerCommentService.getCountOfAnswerComment((Long)pageContext.getAttribute("ansId"));
	pageContext.setAttribute("solutionAnswerVoteCount", solutionAnswerVoteCount);
	pageContext.setAttribute("answerCommentCount", answerCommentCount);
%>
 
 <fmt:formatDate pattern="dd MMM, yyyy" value="${amswer.commentDate}" var="commentDate"/>
 <div class="clearfix np-bor-03" id="solutionAns_${amswer.answerId}">
        <div class="col-sm-1 col-xs-12 "> <img src="${amswer.avtarUrl}" class="img-responsive np-img-border img-circle"> </div>
        <div class="col-sm-11 col-xs-12 ">
          <div class="np-title03 clearfix">
          	<span>${amswer.userName}</span>
          	<span>Posted: ${commentDate}</span>
          	<c:choose>
          		<c:when test="${solutionAnswerVoteCount gt 0}">
	          		<span id='<portlet:namespace/>voteDisplaySpan_${amswer.answerId}' class="clearfix cs-fix01">
	             		<span id='<portlet:namespace/>noOfUserLike_${amswer.answerId}'>${solutionAnswerVoteCount} Upvotes By</span> 
	             		<span id='<portlet:namespace/>upvotesUsersList_${amswer.answerId}'> ${upvoteDisplayUserList}</span>
	             		<c:choose>
	             			<c:when test="${solutionAnswerVoteCount gt 5}">
	             				<span id='<portlet:namespace/>upvoteUserMore_${amswer.answerId}' class="cg-ans-more-tooltip" title='${upvoteMoreUserList}'>(more)</span>
	             			</c:when>
	             			<c:otherwise>
	             				<span id='<portlet:namespace/>upvoteUserMore_${amswer.answerId}' class="cg-ans-more-tooltip" style="display:none;" title=''>(more)</span>
	             			</c:otherwise>
	             		</c:choose>
	            	</span>
          		</c:when>
          		<c:otherwise>
          			<span id='<portlet:namespace/>voteDisplaySpan_${amswer.answerId}' class="clearfix cs-fix01" style="display:none">
	             		<span id='<portlet:namespace/>noOfUserLike_${amswer.answerId}'></span> 
	             		<span id='<portlet:namespace/>upvotesUsersList_${amswer.answerId}'></span>
	             		<span id='<portlet:namespace/>upvoteUserMore_${amswer.answerId}' class="cg-ans-more-tooltip" title=''>(more)</span>
	            	</span>
          		</c:otherwise>
          	</c:choose>
          </div>
          <div class="np-text01 clearfix"><p>${amswer.comment}</p></div>
          <div class="clearfix"> 
	            <c:if test="<%=themeDisplay.isSignedIn() %>">
					<c:choose>
				          <c:when test="<%=solutionAnswerVote%>">
		          	 		<div class="np-menu-gap2">
								<a class="btn btn-default" href="javascript:void(0);" id="<portlet:namespace/>solutionLike" onclick="javascript:<portlet:namespace />solutionAnswerVote('${amswer.answerId}');">
			     					<span id="<portlet:namespace/>answerVoteTextSpan_${amswer.answerId}">DownVote</span>
									<span id='<portlet:namespace/>answerVoteSpan_${amswer.answerId}' class="badge">${solutionAnswerVoteCount}</span>
								</a>
							</div>  
				          </c:when>
				          <c:otherwise>
				          	<div class="np-menu-gap2">
								<a class="btn btn-default" href="javascript:void(0);" id="<portlet:namespace/>solutionLike" onclick="javascript:<portlet:namespace />solutionAnswerVote('${amswer.answerId}');">
			     					<span id="<portlet:namespace/>answerVoteTextSpan_${amswer.answerId}" >UpVote</span>
									<span id='<portlet:namespace/>answerVoteSpan_${amswer.answerId}' class="badge">${solutionAnswerVoteCount}</span>
								</a>
							</div> 
				          </c:otherwise>
	          		</c:choose>
          		</c:if>
	  			<% String shareURL = PortalUtil.getCurrentURL(request);%>
	  			<div class="np-menu-gap2">
		          	<a href="<%=shareURL %>" class="addthis_button cs_imgfix01" addthis:url="<%=shareURL%>" addthis:title="${amswer.comment}">
	           			<img src="<%=request.getContextPath() %>/images/share.png" border="0" alt="Share" />
	           		</a>
		       </div>
		       <div class="np-menu-gap2">
		       		<a class="btn btn-default" href="javascript:void(0);" id='<portlet:namespace/>solutionAnswerComment_${amswer.answerId}' onclick="javascript:<portlet:namespace/>soltionAnswerCommentDiv('${amswer.answerId}');">
		       			<span>Comment</span>
		       			<span class="badge" id='<portlet:namespace />answerCommtCount_${amswer.answerId}'>${answerCommentCount}</span>
		       		</a>
		       </div>
          </div>
          <div style="display: none;" id='<portlet:namespace/>answerCommentDiv_${amswer.answerId}' class="col-sm-11 col-sm-offset-1 np-bor-02 buffer-top col-xs-12"> 
  			 <div id='<portlet:namespace/>solutionAnswerComments_${amswer.answerId}'></div>
  		 </div>
       </div>
  </div>
 </c:forEach>