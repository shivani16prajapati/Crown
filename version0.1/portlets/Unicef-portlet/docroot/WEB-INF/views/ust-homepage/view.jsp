<%@include file="../init.jsp"%>
<link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
<script src="<%=request.getContextPath()%>/js/iziModal.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/iziModal.min.css">


<div id="<portlet:namespace/>listOfHomePageIdea"></div>
 <div style="text-align:center; background-color: #484848;">
	    	<a id='<portlet:namespace/>loadMoreImage' href="javascript:void(0);" onclick="javascript:loadMoreIdeas();">
	    		<img src="<%=request.getContextPath()%>/images/load-more.png" /> 
	    	</a>
	    	<a id='<portlet:namespace/>noMoreImage' style="display:none;" href="javascript:void(0);"><img src="<%=request.getContextPath()%>/images/no-more.png"></a>
	    </div>	
	    
<div id="videomodal"></div>
<%@include file="view_js.jsp"%>