<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.unicef.service.IdeaCommentVoteService"%>
<%@include file="../../init.jsp"%>

<input type="hidden" name='<portlet:namespace/>ideaCommentCount' id='<portlet:namespace/>ideaCommentCount' value="${commentCount}">
<c:forEach items="${ideaComments}" var="comment">
	<c:set value="${comment.commenId}" var="commentId"/>
	<c:set value="${ideaId}" var="ideaId"/>
	<c:set value="${ideaCreator}" var="creator"/>
	<%
	
	IdeaCommentVoteService voteService = (IdeaCommentVoteService)request.getAttribute("commentVoteService");
	
	Boolean commentVote = voteService.checkIdeaCommentVote((Long)pageContext.getAttribute("commentId"),themeDisplay.getUserId());
	Boolean thankYou = IdeaUtil.checkCommentThanks((Long)pageContext.getAttribute("commentId"), themeDisplay.getUserId());
	long currentWeekCount = voteService.getCurrentWeekCount((Long)pageContext.getAttribute("commentId"),"commentCount");
	
	long currentUser = themeDisplay.getUserId();
	pageContext.setAttribute("commentVoteCount", currentWeekCount );
	pageContext.setAttribute("currentUser", currentUser);
	%>
	<fmt:formatDate pattern="dd MMM, yyyy" value="${comment.commentDate}" var="commentDate"/>
	
	<div class="clearfix np-comment-fix001">
    	<div class="col-md-1 col-sm-2 col-xs-12 ">
        	<div class="np-idea-imgbox02"><img src="${comment.avtarUrl}" class="img-responsive np-img-border img-circle"> </div>
        </div>
        <div class="col-md-11 col-sm-10 col-xs-12 ">
        	<div class="np-title05 clearfix">
        		<span>${comment.userName}</span>
        		<span>Posted : ${commentDate}</span>
        	</div>
          	<div class="np-text01 clearfix">
            	<p>${comment.comment}
            		<c:if test="${creator eq currentUser}">
		          		<c:choose>
		          			<c:when test="<%=thankYou %>">
		          				   <a class="np-chat-tnx-btn clearfix" href="javascript:void(0);" id="<portlet:namespace/>commentVote_${commentId}" onclick="javascript:<portlet:namespace />ideaCommentVote('${commentId}','${ideaId}');">
		          				   <span>&nbsp;</span>
		          				   	<span id='<portlet:namespace/>ideaVoteSpan_${commentId}'>${commentVoteCount}</span>
		          				   </a>
		          			</c:when>
		          			<c:otherwise>
			          			 <a class="np-chat-tnx-btn clearfix np-d-disabled" id="<portlet:namespace/>commentVote_${commentId}">
		          				   	 <span>&nbsp;</span>
		          				   	<span id='<portlet:namespace/>ideaVoteSpan_${commentId}' class="np-d-disabled">${commentVoteCount}</span>
		          				   </a>
		          			</c:otherwise>
		          		</c:choose>
       				</c:if>
            	</p>
          	</div>
       </div>
   </div>
</c:forEach>



