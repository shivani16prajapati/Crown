<%@include file="../init.jsp" %>
<input type="hidden" name='<portlet:namespace/>solutionCommentCount' id='<portlet:namespace/>solutionCommentCount' value="${commentCount}">
<c:forEach items="${commentViews}" var="solutionComment">
<fmt:formatDate pattern="dd MMM, yyyy" value="${solutionComment.commentDate}" var="commentDate"/>
<div class="np-comment-fix001 clearfix">
        <div class="col-sm-1 col-xs-12 ">
        	 <img src="${solutionComment.avtarUrl}" class="img-responsive np-img-border img-circle">
        </div>
        <div class="col-sm-11 col-xs-12 ">
          <div class="np-title05 clearfix">
          	<span>${solutionComment.userName}</span>
          	<span>Posted: ${commentDate}</span>
          </div>
          <div class="np-text01 clearfix">
           	 <p>${solutionComment.comment}</p>
          </div>
        </div>
</div>
</c:forEach>
