<%@page import="com.unicef.util.SolutionConstant"%>
<%@page import="com.unicef.util.SolutionUtil"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@include file="../init.jsp"%>
<portlet:renderURL var="backToSolutionURL"/>

<portlet:actionURL var="promoteToScrumURL" name="promoteToScrum">
	<portlet:param name="action" value="promoteToScrum"/>
	<portlet:param name="solutionId" value="${solution.solutionId}"/>
</portlet:actionURL>
 <liferay-ui:success key="<%=SolutionConstant.SOLUTION_PROMOTE_TO_SCRUM%>" message="solution-promote-to-scrum-successfully"/>
 <liferay-ui:error key="<%=SolutionConstant.ERROR_IN_PRMOTE_SOLUTION_SCRUM%>" message="error-in-create-solution-scrum"/>
<script type="text/javascript">
		var addthis_config = {
    		 pubid: "ra-550d8fe213da5069"
		}
	</script>
<script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js"></script>
<div class="container">
  <div class="row">
    <div class="col-xs-12"> <a href="<%=backToSolutionURL.toString() %>" class="btn btn-default"><span aria-hidden="true" class="glyphicon glyphicon-menu-left"></span>Back</a> </div>
  	<!-- Go to www.addthis.com/dashboard to customize your tools -->
	<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-550d8fe213da5069" async="async"></script>
  </div>
  <div class="np-container clearfix">
    <div class="clearfix">
      <div class="col-sm-2 col-xs-12 "> <a href="${userDisplayURL}"><img src="${userAvtar}" class="img-responsive np-img-border img-circle"></a>
       <c:set value="${solution.coInventorId}" var="userId"/>
        <c:set var="solutionId" value="${solution.solutionId}"/>
        <fmt:formatDate pattern="dd MMM, yyyy" value="${solution.submissionDate}" var="submissionDate"/>
       
       	 <%
    	 	String SolutionScrumURL = StringPool.BLANK;
       		SolutionScrumURL = SolutionUtil.getFriendlyURLOfCommunityBySolution((Long)pageContext.getAttribute("solutionId"));
      		User userName = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId"));
			String coInventerName = userName.getFullName();
     	 %>
        <div class="np-title006"><a href="${userDisplayURL}"><%=coInventerName %></a></div>
      </div>
      <div class="col-sm-10 col-xs-12 ">
        <div class="np-title005"><span>${solution.solutionTitle}</span><span>Posted:${submissionDate}</span></div>
        <div class="np-text01 clearfix"><p>${solution.description}</p></div>
         <div class="col-xs-12 clearfix buffer-bottom">
			 <c:if test="${isPrmoted eq true}">
			 	<div>
			 		<b><liferay-ui:message key="this-solution-was-promoted-to-be-devloed-further"/></b>
			 			<a href="<%=SolutionScrumURL.toString()%>">
							Click Here
						</a>&nbsp;<b>to go its SOLUTIONSCRUM page.</b>
			 	</div>
			 </c:if>
		 </div>
      </div>
    </div>
  </div>
  <div class="np-buttonbox02 clearfix">
          <div class="clearfix"> 
          <div class="np-menu-gap2"><div class="btn btn-default" style="pointer-events:none !important; ">Want an Answer&nbsp;<span id='<portlet:namespace/>wantAnswerCountDisplay' class="badge">${wantAnswerCount}</span></div></div> 
          	<div class="np-menu-gap2">
	          	<a id='<portlet:namespace/>solutionCommentBtn' class="btn-link" href="javascript:void(0);"
	          	onclick="javascript:<portlet:namespace />solutionComment();"><span id="<portlet:namespace />commentText"></span> 
	          		<span id='<portlet:namespace/>commentCountDisplay' class="badge"></span>
	          	</a>
	          </div> 
	         <%
	         String shareURL = PortalUtil.getCurrentURL(request);
	         %>
	           	<div class="np-menu-gap2">
	           		<a href="<%=shareURL %>" class="addthis_button cs_imgfix01" addthis:url="<%=shareURL%>" addthis:title="${solution.solutionTitle}">
	           			<img src="<%=request.getContextPath() %>/images/share.png" border="0" alt="Share" />
	           		</a>
	           	</div>
	           	<c:if test="${isPromoteToScrum}">
		           	<div class="np-menu-gap2">
		           		<a href="javascript:void(0);" onclick="javasciprt:<portlet:namespace />promoteToScrum('<%=promoteToScrumURL.toString() %>')" class="btn-link">
		           			<liferay-ui:message key="prmote-to-scrum"/>
		           		</a>
		           	</div>
	           </c:if>
          <c:if test="<%=themeDisplay.isSignedIn() %>">
	          	  <c:choose>
			          <c:when test="${solutionLike}">
	          	 		<div class="np-menu-gap2">
							<a class="btn btn-default" href="javascript:void(0);" 
	          	  			id="<portlet:namespace/>solutionLike" onclick="javascript:<portlet:namespace />solutionLike('${solution.solutionId}');">
								<span id="<portlet:namespace/>likeUnlikeTextSpan" >DownVote</span>
								<span id='<portlet:namespace/>solutionLikeSpan' class="badge">${solutionLikeCount}</span>
							</a>
						</div>  
			          </c:when>
			          <c:otherwise>
			          	<div class="np-menu-gap2">
								<a class="btn btn-default" href="javascript:void(0);" 
	          	  			id="<portlet:namespace/>solutionLike" onclick="javascript:<portlet:namespace />solutionLike('${solution.solutionId}');">
	     					<span id="<portlet:namespace/>likeUnlikeTextSpan" >UpVote</span>
							<span id='<portlet:namespace/>solutionLikeSpan' class="badge">${solutionLikeCount}</span>
							</a>
						</div>  
			          </c:otherwise>
		          </c:choose>
          		</c:if> 
          </div>
        </div>
        
  <div id="<portlet:namespace/>commentDiv" style="display: none;">
  <div id='<portlet:namespace/>comment'>
   <c:if test="<%=themeDisplay.isSignedIn() %>">
  	  <div class="well buffer-top clearfix" >
	   		<div class="np-title02">Post Your Comment</div>
	    	<textarea id='<portlet:namespace/>solutionCommentTxt' placeholder="Place your comment here..." rows="3" class="form-control" style="width: 100%;"></textarea>
	   		 <div class="pull-right buffer-top">
	   		 	<a class="btn btn-primary" href="javascript:void(0);" onclick="javascript:<portlet:namespace />postSolutionComment(${solution.solutionId});">
	       			<liferay-ui:message key="comment" />
	      		</a>
	   		 </div>
  		</div>
  			</c:if>
  	</div>
  		<div class="row clearfix buffer-top">
   			 <div class="col-xs-12" id='<portlet:namespace/>solutionCommentView'></div>
 		</div> 
  </div>   
  
  <div class="row clearfix buffer-top">
  	<div class="col-xs-12 np-title01 buffer-top buffer-bottom">
  		<span id='<portlet:namespace/>answerCount'></span>
  	</div>
  		<c:if test="<%=themeDisplay.isSignedIn() %>">
  			<div class="well buffer-top clearfix" id='<portlet:namespace/>answerDiv'>
   				 <div class="np-title02">Post Your Answer</div>
    				<textarea id='<portlet:namespace/>solutionAnswerTxt' placeholder="Place your answer here.." class="form-control" style="width: 100%;"></textarea>
   				 	<div class="pull-right buffer-top">
   		 				<a class="btn btn-primary" href="javascript:void(0);" id="<portlet:namespace/>answerClick" onclick="javascript:<portlet:namespace />postAnswer(${solution.solutionId});">
       						<liferay-ui:message key="answer" />
      					</a>
   		 			</div>
  			</div>
  		</c:if>
		<div class="col-xs-12 buffer-top" id="<portlet:namespace/>solutionAnswerView"></div>
	    <div style="text-align:center;">
	    	<a id='<portlet:namespace/>loadMoreImage' href="javascript:void(0);" onclick="javascript:displaySolutionAnswerView(${solution.solutionId}, 'getMore');">
	    		<img src="<%=request.getContextPath()%>/images/load-more.png">
	    	</a>
	    	<a id='<portlet:namespace/>noMoreImage' style="display:none;" href="javascript:void(0);"><img src="<%=request.getContextPath()%>/images/no-more.png"></a>
	    </div>
  </div>
</div>
 <%@ include file="viewSolution_js.jsp"%>
