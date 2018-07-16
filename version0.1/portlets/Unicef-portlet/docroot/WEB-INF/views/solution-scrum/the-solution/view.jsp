<%@page import="com.unicef.util.SolutionConstant"%>
<%@page import="com.unicef.util.SolutionUtil"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@include file="../../init.jsp"%>

<script type="text/javascript">
		var addthis_config = {
    		 pubid: "ra-550d8fe213da5069"
		}
</script>
<script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js"></script>

<div class="clearfix">
  <div class="row">
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
    	 	/* String SolutionScrumURL = StringPool.BLANK;
       		SolutionScrumURL = SolutionUtil.getFriendlyURLOfCommunityBySolution((Long)pageContext.getAttribute("solutionId")); */
      		User userName = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId"));
			String coInventerName = userName.getFullName();
     	 %>
        <div class="np-title006"><a href="${userDisplayURL}"><%=coInventerName %></a></div>
      </div>
      <div class="col-sm-10 col-xs-12 ">
        <div class="np-title005"><span>${solution.solutionTitle}</span><span>Posted:${submissionDate}</span></div>
        <div class="np-text01 clearfix"><p>${solution.description}</p></div>
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
	        
   
          </div>
        </div>
        
  <div id="<portlet:namespace/>commentDiv" style="display: none;">
  		<div class="row clearfix buffer-top">
   			 <div class="col-xs-12" id='<portlet:namespace/>solutionCommentView'></div>
 		</div> 
  </div>   
  
  <div class="row clearfix buffer-top">
  	<div class="col-xs-12 np-title01 buffer-top buffer-bottom">
  		<span id='<portlet:namespace/>answerCount'></span>
  	</div>
		<div class="col-xs-12 buffer-top" id="<portlet:namespace/>solutionAnswerView"></div>
	    <div style="text-align:center;">
	    	<a id='<portlet:namespace/>loadMoreImage' href="javascript:void(0);" onclick="javascript:displaySolutionAnswerView(${solution.solutionId}, 'getMore');">
	    		<img src="<%=request.getContextPath()%>/images/load-more.png">
	    	</a>
	    	<a id='<portlet:namespace/>noMoreImage' style="display:none;" href="javascript:void(0);"><img src="<%=request.getContextPath()%>/images/no-more.png"></a>
	    </div>
  </div>
</div>
 <%@ include file="view_js.jsp"%>
