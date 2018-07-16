<%@page import="com.unicef.constants.Constants"%>
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

<%-- <c:if test="${sprints-saved-success}">
   <div></div>
</c:if> --%>
<div class="clearfix np-rounded-box">
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
  <div id="prebakedPerspectives" style="display:none; width:100%; max-width:800px;">
  	<div class="col-xs-12 as-title01 buffer-bottom">Prebaked Perspectives</div>
	<ul>
		<li>[More Passion] How can we become more passionate about this idea?</li>
		<li>[Enlarge this Idea] How can we enlarge this idea, maybe turn it into a global idea?</li>
		<li>[Open it Up] How can we open it up, maybe do open systems thinking here?</li>
		<li>[Time Machine] How would you deal with this were 10 years ago? How about 10 years in the future?</li>
		<li>[WWSJD?] How would Steve Jobs solve this problem? Warren Buffet? Albert Einstein? Thomas Edison?</li>
		<li>[Be the Customer] Let’s become the customer and think about it from their perspectives.</li>
		<li>[Mind the Gap] What is the gap that exists between A and B?</li>
		<li>[Let's think visually] Let’s create a visual picture, a mind map of the situation.</li>
		<li>[Challenge me!] Let’s let someone play the challenger – and challenge all the assumptions in our situation?</li>
		<li>[Force Analysis] Let’s do a force analysis and draw what the forces are?</li>
		<li>[No Limitations] What if money, time, people, supplies are not issues at all?</li>
		<li>[Root cause analysis] What is the root causes of what we are investigating?</li>
		<li>[Other applications?] What are the other applications of this solution?</li>
		<li>[What's changed?] How has something changed in the world since we started? Would those changes be important to consider?</li>
		<li>[Known and Unknown] What is known and unknown about something?</li>
		<li>[Category thinking] What category of ideas or objects does what we are working on belong to? What happens if we shift categories.</li>
		<li>[What is High Score] What is high score in the game we’re playing?</li>
		<li>[Invert this Idea] How do we flip it around, invert it, reverse this idea?</li>
		<li>[SWOT analysis] Let’s do a SWOT analysis to open up our thinking.</li>
	</ul>
</div>   
 <%@ include file="sprintList_js.jsp"%>
 
 <style>
#sprintTable tr:nth-child(2) .np-upaero-icon{ opacity:0.6; cursor:default !important; pointer-events: none !important; }
/* #sprintTable tr:last-child .np-downaero-icon{ opacity:0.2 !important; pointer-event:none;  } */
#sprintTable tr:nth-last-child(-n+3) .np-downaero-icon{ opacity:0.2 !important; pointer-events:none !important;  }
 </style>
