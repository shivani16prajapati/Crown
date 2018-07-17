<%@include file="../init.jsp"%>

<div id="<portlet:namespace/>listOfIdea"></div>
 <div style="text-align:center;">
	    	<a id='<portlet:namespace/>loadMoreImage' href="javascript:void(0);" onclick="javascript:displayIdea();">
	    		<img src="<%=request.getContextPath()%>/images/load-more.png">
	    	</a>
	    	<a id='<portlet:namespace/>noMoreImage' style="display:none;" href="javascript:void(0);"><img src="<%=request.getContextPath()%>/images/no-more.png"></a>
	    </div>
	    
<div id="<portlet:namespace />acceptTaskConfirmDialog" style="display: none;">
	<%@include file="task_accept.jsp" %>
</div>
	    
<%@include file="view_js.jsp"%>