<%@include file="../../init.jsp"%>

<input type="hidden" name='<portlet:namespace/>solutionAnswerCmtCount_${solutionAnsId}' id='<portlet:namespace/>solutionAnswerCmtCount_${solutionAnsId}' value="${answerCommentCount}">
<c:forEach items="${commentView}" var="comment">
	<fmt:formatDate pattern="dd MMM, yyyy" value="${comment.commentDate}" var="commentDate"/>
	<div class="col-xs-12 np-bor-01">
	      <div class="row">
	        <div class="col-sm-1 col-xs-3 "> <img src="${comment.avtarUrl}" class="img-responsive np-img-border img-circle"> </div>
	        <div class="col-sm-11 col-xs-9">
	          <div class="np-title05 clearfix"><span>${comment.userName}</span><span>Posted: ${commentDate}</span></div>
	          <div class="np-text01 clearfix">
	            <p>${comment.comment}</p>
	          </div>
	        </div>
	      </div>
	    </div>
</c:forEach>