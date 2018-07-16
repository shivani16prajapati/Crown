<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.unicef.util.SolutionConstant"%>
<%@include file="../init.jsp"%>
<portlet:renderURL var="createSolutionURL">
	<portlet:param name="view" value="createSolution" />
</portlet:renderURL>
<portlet:renderURL var="WorkflowEntryURL">
	<portlet:param name="view" value="workflow"/>
</portlet:renderURL>
<% 
String createSolutionForm = "javascript:" + renderResponse.getNamespace() + "solutionForm('"+createSolutionURL+"');";
/* String workflowEntry= "javascript:" + renderResponse.getNamespace() + "workflow('"+WorkflowEntryURL+"');"; */
%>
<div id="deleteIdea" class="portlet-msg-success" style="display: none;">
</div>

<liferay-ui:error key="<%=SolutionConstant.ERROR_SOLUTION_LOG_IN %>" message="please-sign-in-to-continue"/>
<liferay-ui:success key="<%=SolutionConstant.SUCCESS_SOLUTION_MESSAGE_CREATE %>" message="new-solution-created-succesfully"/>
<liferay-ui:success key="<%=SolutionConstant.SUCCESS_SOLUTION_MESSAGE_UPDATE%>" message="solution-updated-succesfully"/>

<div class="clearfix">
  <%-- <div class=" clearfix" style="padding:10px 0;">
      <div class="form-inline">
      <c:if test="<%=themeDisplay.isSignedIn() %>">
   		<button class="btn  btn-primary pull-right il-btn-01" onclick="<%=createSolutionForm.toString()%>"><span style="font-size:16px" class="glyphicon glyphicon-plus-sign"></span><liferay-ui:message key="new-request"/> </button>
   		<button class="btn  btn-primary pull-right" onclick="<%=workflowEntry.toString()%>"><span style="font-size:16px" class="glyphicon glyphicon-plus-sign"></span>Workflow Entry</button>
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
            <th onclick='<portlet:namespace/>solutionSortOnHeader("Request");' class="cursor">
            <div class="cs-mainblock">
             <span class="cs-block1"><liferay-ui:message key="problem"/></span><span id='<portlet:namespace/>sortSpanRequest'></span>
            </div>
             </th>
            <th><liferay-ui:message key="who"/></th>
            <th onclick='<portlet:namespace/>solutionSortOnHeader("Created");' class="cursor">
              <div class="cs-mainblock">
             	<span class="cs-block1"><liferay-ui:message key="created"/></span><span id='<portlet:namespace/>sortSpanCreated'></span>
             </div>
            </th>
            <th onclick='<portlet:namespace/>solutionSortOnHeader("Last Updated");' class="cursor">
             <div class="cs-mainblock">
             <span class="cs-block1"><liferay-ui:message key="last-updated"/></span><span id='<portlet:namespace/>sortSpanUpdated'></span>
            </div>
            </th>
            <th onclick='<portlet:namespace/>solutionSortOnHeader("Problem Space");' class="cursor">
             <div class="cs-mainblock">
             <span class="cs-block1"><liferay-ui:message key="problem-goal"/></span><span id='<portlet:namespace/>sortSpanSpace'></span>
            </div>
            </th>
            <th align="center">
            	<liferay-ui:message key="problem-stage"/>
            </th>
             <th onclick='<portlet:namespace/>solutionSortOnHeader("UpVote");' class="cursor">
            <div class="cs-mainblock">
             <span class="cs-block1"><liferay-ui:message key="wants"/></span><span id='<portlet:namespace/>sortSpanUpVote'></span>
            </div>
            </th>
            
            <th align="center">
            	<div class="cs-mainblock">
	             <span class="cs-block1"><liferay-ui:message key="answers"/></span><%-- <span id='<portlet:namespace/>sortSpanUpVote'></span> --%>
	            </div>
            </th>
            
            
        	
        <%--<th align="center"><span class="np-ans-icon"></span></th>
                 <th align="center"><span class="np-comment-icon"></span></th>
            <th align="center"><span class="np-view-icon"></span></th>--%>
            
            <th colspan="2"><liferay-ui:message key="actions"/> </th> 
          </tr>
        </thead>
        <tbody id="SolutionTable">
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
  
  
    
    <%@ include file="solutionList_js.jsp"%>
