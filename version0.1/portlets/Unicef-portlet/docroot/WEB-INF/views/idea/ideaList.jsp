<%@page import="com.unicef.util.IdeaConstant"%>
<%@include file="../init.jsp"%>
<portlet:renderURL var="createIdeaURL">
	<portlet:param name="view" value="updateIdea" />
</portlet:renderURL>
<portlet:renderURL var="WorkflowEntryURL">
	<portlet:param name="view" value="workflow"/>
</portlet:renderURL>

<% 
String createIdeaForm = "javascript:" + renderResponse.getNamespace() + "ideaForm('"+createIdeaURL+"');";
/* String workflowEntry = "javascript:" + renderResponse.getNamespace() + "workflowEntry('"+WorkflowEntryURL+"');"; */
%>
<div id="deleteIdea" class="portlet-msg-success" style="display: none;">
</div>
<liferay-ui:error key="<%=IdeaConstant.ERROR_IDEA_LOG_IN %>" message="please-sign-in-to-continue"/>
<liferay-ui:success key="<%=IdeaConstant.SUCCESS_IDEA_MESSAGE_CREATE %>" message="new-idea-created-succesfully."/>
<liferay-ui:success key="<%=IdeaConstant.SUCCESS_IDEA_MESSAGE_UPDATE %>" message="idea-updated-succesfully."/>
<div class="clearfix">
 <%--  <div class="clearfix" style="padding:8px 0;">
      <div class="form-inline">
      <c:if test="<%=themeDisplay.isSignedIn() %>">
   		<button class="btn  btn-primary pull-right il-btn-01" onclick="<%=createIdeaForm.toString()%>"><span style="font-size:16px" class="glyphicon glyphicon-plus-sign"></span><liferay-ui:message key="submit-idea"/> </button>
   		<button class="btn  btn-primary pull-right" onclick="<%=workflowEntry.toString()%>"><span style="font-size:16px" class="glyphicon glyphicon-plus-sign"></span> Workflow Entry</button>
   		</c:if>
   </div>
  </div> --%>
  <div id="loadingDiv" style="display: none;width: 100%;text-align: center;margin-top: 120px;min-height:250px;">
  			<img  src="<%=request.getContextPath()%>/images/loading.GIF" width="50px" height="50px"/>
	</div>
 <div class="table-responsive np-tablefix01" id="container">
      <table border="1" class="table il-tableS-01" bordercolor="#e3e3e3">
       <thead>
          <tr>
          	<th align="center"></th>
            <th onclick='<portlet:namespace/>ideaSortingOnHeader("Idea");' class="cursor">
              <div class="cs-mainblock">
             <span class="cs-block1"><liferay-ui:message key="idea"/></span><span id="<portlet:namespace/>sortSpanIdea"></span>
            </div>
            </th>
            <th><liferay-ui:message key="inventer"/></th>
            <th onclick='<portlet:namespace/>ideaSortingOnHeader("Created");' class="cursor">
               <div class="cs-mainblock">
             <span class="cs-block1"><liferay-ui:message key="created"/></span><span id="<portlet:namespace/>sortSpanCreated"></span>
            </div>
            </th>
            <th onclick='<portlet:namespace/>ideaSortingOnHeader("Last Updated");' class="cursor">
            <div class="cs-mainblock">
             <span class="cs-block1"><liferay-ui:message key="last-updated"/></span><span id="<portlet:namespace/>sortSpanUpdated"></span>
            </div>
            </th>
            <th onclick='<portlet:namespace/>ideaSortingOnHeader("Idea Type");' class="cursor">
             <div class="cs-mainblock">
             <span class="cs-block1"><liferay-ui:message key="idea-type"/></span><span id="<portlet:namespace/>sortSpanType"></span>
           	</div>
            </th>
            <th align="center"><liferay-ui:message key="idea-stage"/></th>
            <th onclick='<portlet:namespace/>ideaSortingOnHeader("Hotness")' class="cursor">
             <div class="cs-mainblock">
            	<span class="cs-block1"><liferay-ui:message key="hotness"/></span><span id="<portlet:namespace/>sortSpanHotness"></span>
         	</div>
            </th>
            <th onclick='<portlet:namespace/>ideaSortingOnHeader("UpVote")' class="cursor">
             <div class="cs-mainblock">
            	<span class="cs-block1"><liferay-ui:message key="upvotes"/></span><span id="<portlet:namespace/>sortSpanVote"></span>
         	</div>
            </th>
           <%--  <th align="center"><span class="np-attach-icon"></span></th>
            <th align="center"><span class="np-comment-icon"></span></th>
            <th align="center"><span class="np-view-icon"></span></th> --%>
            <th colspan="2"><liferay-ui:message key="actions"/></th>
          </tr>
        </thead>
         <tbody id="ideaTable">
    	 </tbody>
		 </table>
   </div>
  <div id="pagination" class="pull-right np-pagination-fix"></div>
  </div>    
  
<script>
	$( document ).ready(function() { 
		$( ".np-portlate-fix01" ).removeClass( "np-portlate-fix01" ).addClass( "np-portlate-fix0111" );
	});
</script>
  

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
 <%@ include file="ideaList_js.jsp"%>
