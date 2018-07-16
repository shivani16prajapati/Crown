<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.unicef.util.VideoConstant"%>
<%@include file="../init.jsp"%>


<div id="deleteVideo" class="portlet-msg-success" style="display: none;">
</div>

<liferay-ui:error key="<%=VideoConstant.ERROR_VIDEO_LOG_IN %>" message="please-sign-in-to-continue-video"/>
<liferay-ui:success key="<%=VideoConstant.SUCCESS_VIDEO_MESSAGE_CREATE %>" message="new-video-created-succesfully."/>
<liferay-ui:success key="<%=VideoConstant.SUCCESS_VIDEO_MESSAGE_UPDATE%>" message="video-updated-succesfully"/>


<div class="clearfix">
	
	  <div id="loadingDiv" style="display: none;width: 100%;text-align: center;margin-top: 120px;min-height:250px;">
  		<img  src="<%=request.getContextPath()%>/images/loading.GIF" width="50px" height="50px"/>
	  </div>
	  
	  <div class="table-responsive np-tablefix01" id="container">
	  	 <table border="1" class="table il-tableS-01" bordercolor="#e3e3e3">
         <thead>
          <tr>
            
            <th onclick='<portlet:namespace/>videoSortOnHeader("Video");' class="cursor">
	            <div class="cs-mainblock">
	             	<span class="cs-block1"><liferay-ui:message key="video"/></span><span id='<portlet:namespace/>sortSpanVideo'></span>
	            </div>
             </th>
             
             <th onclick='<portlet:namespace/>videoSortOnHeader("ExpireDate");' class="cursor">
	             <div class="cs-mainblock">
	             	<span class="cs-block1"><liferay-ui:message key="expire-date"/></span><span id='<portlet:namespace/>sortSpanExpireDate'></span>
	             </div>
             </th>
             
             <th><liferay-ui:message key="upload-video-url"/></th>
             
             <th onclick='<portlet:namespace/>videoSortOnHeader("FeedOrder");' id="feedorder" class="cursor">
	            <div class="cs-mainblock">
	             <span class="cs-block1"><liferay-ui:message key="feed-order"/></span><span id='<portlet:namespace/>sortSpanFeedOrder'></span>
	            </div>
	         </th>
	         
	         <th colspan="2"><liferay-ui:message key="actions"/> </th>
            
          </tr>
        </thead>
        
        <tbody id="VideoTable">
    	</tbody>
    	
	 	</table>
	
		<div id="pagination" class="pull-right np-pagination-fix"></div>
		
	</div>

</div>
    
<%@ include file="videoList_js.jsp"%>
