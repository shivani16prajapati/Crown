<%@page import="com.unicef.util.ChallengeConstant"%>
<%@include file="../init.jsp"%>

<portlet:renderURL var="launchChallengeURL">
	<portlet:param name="action" value="challenge"/>
</portlet:renderURL>

<div id="deleteChallenge" class="portlet-msg-success" style="display: none;">
</div>

<liferay-ui:error key="<%=ChallengeConstant.ERROR_CHALLENGE_LOG_IN %>" message="please-sign-in-to-continue"/>
<liferay-ui:success key="<%=ChallengeConstant.SUCCESS_CHALLENGE_MESSAGE_CREATE%>" message="new-challenge-created-succesfully"/>
<liferay-ui:success key="<%=ChallengeConstant.SUCCESS_CHALLENGE_MESSAGE_UPDATE %>" message="challenge-updated-succesfully"/>
<div class="clearfix">
  <div class="gray-bar clearfix">
  <%-- <div style="color:red;"><%=launchChallengeURL.toString() %></div> --%>
      <div class="form-inline">
     <c:if test="<%=themeDisplay.isSignedIn() %>">	
     <a style="display:inline-block; background-color:#0276C0; text-align:center; color:#FFFFFF; padding:5px 12px; border-radius:4px;" href="<%=launchChallengeURL.toString()%>">launch a challenge</a>
   	<%-- 	<aui:button type="submit" value="launch-a-challenge" href="<%=launchChallengeURL.toString()%>"/> --%> 		
   	</c:if> 		
   </div>
  </div>
  
  <div id="loadingDiv" style="display: none;width: 100%;text-align: center;margin-top: 120px;min-height:250px;">
  			<img  src="<%=request.getContextPath()%>/images/loading.GIF" width="50px" height="50px"/>
	</div>
 <div class="table-responsive np-tablefix01" id="container">
      <table border="1" class="table il-tableS-01" bordercolor="#e3e3e3">
       <thead>
          <tr>
          	<th>
          	</th>
            <th>
              <div class="cs-mainblock">
             	<span class="cs-block1"><liferay-ui:message key="challenge-title"/></span>
              </div>
            </th>
            <th>
              <div class="cs-mainblock">
             	<span class="cs-block1"><liferay-ui:message key="challenge-tagline"/></span>
              </div>
            </th>
            <th><liferay-ui:message key="challenger"/></th>
        <%--     <th>
               <div class="cs-mainblock">
              	 <span class="cs-block1"><liferay-ui:message key="challenge-start-date"/></span>
               </div>
            </th>
             <th>
               <div class="cs-mainblock">
              	 <span class="cs-block1"><liferay-ui:message key="challenge-end-date"/></span>
               </div>
            </th> --%>
            <th>
             	<div class="cs-mainblock">
             		<span class="cs-block1"><liferay-ui:message key="challenge-goal"/></span>
           		</div>
            </th>
            <th>
             <div class="cs-mainblock">
            	<span class="cs-block1"><liferay-ui:message key="line-ofbussiness"/></span>
         	</div>
            </th>
            <th colspan="2"><liferay-ui:message key="actions"/></th>
          </tr>
        </thead>
        <tbody id="challengeTable">
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
  
 <%@ include file="view_js.jsp"%>
