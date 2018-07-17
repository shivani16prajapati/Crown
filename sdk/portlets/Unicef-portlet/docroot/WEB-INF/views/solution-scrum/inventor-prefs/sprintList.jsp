<%@page import="com.unicef.util.SprintConstant"%>
<%@include file="../../init.jsp"%>
<portlet:renderURL var="createSprintURL">
	<portlet:param name="view" value="createSprint" />
	<portlet:param name="tab" value="inventor-prefs" />
</portlet:renderURL>

<% 
String createSprintForm = "javascript:" + renderResponse.getNamespace() + "sprintForm('"+createSprintURL+"');";
%>
<liferay-ui:success key="<%=SprintConstant.SPRINT_DELETED_SUCCESSFULLY %>" message="sprint-deleted-successfully"/>
<liferay-ui:error key="<%=SprintConstant.ERROR_SPRINT_LOG_IN %>" message="please-sign-in-to-continue"/>
<liferay-ui:success key="<%=SprintConstant.SUCCESS_SPRINT_MESSAGE_CREATE %>" message="new-sprint-created-succesfully."/>
<liferay-ui:success key="<%=SprintConstant.SUCCESS_SPRINT_MESSAGE_UPDATE %>" message="sprint-updated-succesfully."/>
<div class="clearfix">
   <div class="gray-bar clearfix">
      <div class="col-xs-12 clearfix">
          <button type="submit" class="btn btn-primary pull-right" onclick="javascript:window.history.back();">Cancel</button>
	      <button type="submit" class="btn btn-primary pull-right buffer-right" onclick="javascript:saveAllSprints(${ideaId},${ideaScrumId});">Save</button>
	      <c:if test="<%=themeDisplay.isSignedIn() %>">
			  <c:if test="${sprintListSize == 0}">
			      <button type="submit" class="btn  btn-primary pull-right buffer-right" onclick="<%=createSprintForm.toString()%>">
			      	<span style="font-size:16px" class="glyphicon glyphicon-plus-sign"></span> Insert Sprint</button>
		      </c:if>
	      </c:if>
      </div>
  </div>
  <div id="loadingDiv" style="display: none;width: 100%;text-align: center;margin-top: 120px;min-height:250px;">
  			<img  src="<%=request.getContextPath()%>/images/loading.GIF" width="50px" height="50px"/>
	</div>
 <div class="table-responsive np-tablefix01" id="container">
      <table border="1" class="table" bordercolor="#e3e3e3" width="100%" id="sprintTableContainer">
       <thead>
          	<tr>
	            <th width="5%" class="center"><span>Sprint</span></th>
	            <th width="30%" class="center"><span>Focus</span></th>
	            <th width="11%" class="center"><span>Type</span></th>
	            <th width="17%" class="center" onclick='<portlet:namespace/>sprintSortingOnHeader("startDate");' class="cursor">
	            	<div class="cs-mainblock"><span class="cs-block1">Start Date</span><span id="<portlet:namespace/>sortSpanStarted"></span></div>
	            </th>
	            <th width="6%" class="center" align="center"><a href="#" class="np-okstatus-icon"></a></th>
	            <th width="7%" class="center" align="center"><a href="#" class="np-edit-icon"></a></th>
	            <th width="6%" class="center" align="center"><a href="#" class="np-idea-icon"></a></th>
	            <th width="18%" class="center" align="center"><span>Actions</span></th>
           </tr>
        </thead>
        <tbody id="sprintTable">
    	 </tbody>
		 </table>
   </div>
  <div id="pagination" class="pull-right np-pagination-fix"></div>
  </div>

 <%@ include file="sprintList_js.jsp"%>
 
  
 <style>
#sprintTable tr:nth-child(2) .np-upaero-icon{ opacity:0.6; cursor:default !important; pointer-events: none !important; }
/* #sprintTable tr:last-child .np-downaero-icon{ opacity:0.2 !important; pointer-event:none;  } */
#sprintTable tr:last-child .np-downaero-icon{ opacity:0.2 !important; pointer-events:none !important;  }
 </style>