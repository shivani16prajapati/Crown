<%@page import="com.unicef.util.SolutionConstant"%>
<%@include file="../init.jsp"%>


<portlet:actionURL var="saveSolutionURL">
	<portlet:param name="action" value="saveSolution"/>
</portlet:actionURL>

<portlet:renderURL var="backToSolutionViewURL"/>
<fmt:formatDate pattern="dd MMM, yyyy" value="${submissionDate}" var="formattedSubmissionDate" />


<liferay-ui:error key="<%=SolutionConstant.ERROR_SOLUTION_LOG_IN %>" message="please-sign-in-to-continue"/>
<liferay-ui:success key="<%=SolutionConstant.SUCCESS_SOLUTION_MESSAGE_CREATE %>" message="new-solution-created-succesfully"/>
<liferay-ui:success key="<%=SolutionConstant.SUCCESS_SOLUTION_MESSAGE_UPDATE%>" message="solution-updated-succesfully"/>


<body style="background-color:#e6eaed">
<div class="ust-if-mainbox">
	<aui:form action="<%=saveSolutionURL %>" method="post" name="solutionForm">
	  <div class="ust-if-title01 clearfix">
	    <div class="ust-if-t01-box1">Solution Request Submission - Full Form</div>
	    <div class="ust-if-t01-box2"><span>Required Fields</span></div>
	  </div>
	  
	   <aui:input name="solutionContent" type="hidden" value="third blog"/>
 		 
 		 <aui:input name="solutionId" type="hidden" value="${solution.solutionId}"/>
		  <div class="ust-form-box01 ust-form-compelsory">
		  		<aui:input name="solutionTitle"  required="true" label="" showRequiredLabel="<%=false %>" value="${solution.solutionTitle}" placeholder="Problem Title" cssClass="ust-form-box01-form01">
						<aui:validator name="required" />
						<aui:validator name="maxLength">120</aui:validator>
					</aui:input>
		  </div>
		  
	  <div class="ust-form-box01 clearfix">
	    <div class="ust-form-box02">Submission Date</div>
	    <div class="ust-form-box03">${formattedSubmissionDate}</div>
	  </div>
	  
	  <%-- <div class="ust-form-box01 ust-form-compelsory">
	  	 <aui:select name="solutionProgramme" required="true" label="" showRequiredLabel="<%=false %>" cssClass="basic">
			<aui:option value="" selected="selected">
				<liferay-ui:message key="solution-programme"/>
			</aui:option>
			<c:forEach items="${solutionProgrammeList}" var="solutionProgramme">
				<aui:option value="${solutionProgramme.categoryId}" selected="${solution.solutionProgramme == solutionProgramme.categoryId}">${solutionProgramme.name}</aui:option>
			</c:forEach>
		</aui:select>
	  </div> --%>
	  
	       <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-002">
                         <aui:input type="hidden" name="hiddenTagTitle" value="${challenge.challengeTagline}"/>
                         <aui:input name="challengeTagline" required="true"  showRequiredLabel="<%=false %>" label="" placeholder="TagLine" value="${solution.solutiontagline}" cssClass="ust-form-box01-form01">
                                <aui:validator name="required" />
                                <aui:validator name="maxLength">250</aui:validator>
                         </aui:input>
                         <span style="position:absolute;right: 31px;top: 2px;">
                            <img onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key='challenge-tagline' />');" src="/html/themes/control_panel/images/portlet/help.png">
                         </span>
           </div>
         
         
         
          <div class="ust-form-box01"><liferay-ui:message key="describe-your-problem"/><span>*</span> </div>
		  <div id="<portlet:namespace/>descriptionError" class="portlet-msg-error" style="display:none;">
			<liferay-ui:message key="please-describe-your-problem"/>
	 	 </div>
   
         <div class="ust-form-box01">
		   	 <aui:input name="hiddenDescription" type="hidden" value="${solution.description}"/>
		     <liferay-ui:input-editor initMethod="initDescriptionckEditor" name="descriptionckEditor"  />
	     </div>
	   
	      <div class="ust-form-box01 ust-form-compelsory">
                    <aui:select name="LinOfBusiness" showRequiredLabel="<%=false %>"  required="true" label="" cssClass="basic">
                        <aui:option value="" selected="selected">
                            <liferay-ui:message key="line-of-bussiness"/>
                        </aui:option>
                           <c:forEach items="${lineOfbussiness}" var="lofbuss">                   
                                    <aui:option value="${lofbuss.categoryId}" selected="${solution.lineofbussiness == lofbuss.categoryId}">${lofbuss.name}</aui:option>
                            </c:forEach>
                    </aui:select>
           </div>
	  
<%-- 	  <div class="ust-form-box01 ust-form-compelsory">
       <aui:select name="solutionProgramme" required="true" label="" showRequiredLabel="<%=false %>" cssClass="basic">
			<aui:option value="" selected="selected">
				<liferay-ui:message key="problem-goal"/>
			</aui:option>
			<c:forEach items="${solutionProgrammeList}" var="solutionProgramme">
				<aui:option value="${solutionProgramme.categoryId}" selected="${solution.solutionProgramme == solutionProgramme.categoryId}">${solutionProgramme.name}</aui:option>
			</c:forEach>
		</aui:select>
     </div>
      --%>
     
  
  
   <div class="ust-form-box01 ust-form-compelsory">
  	  <aui:select name="solutionSpace" showRequiredLabel="<%=false %>" label="" required="true" cssClass="basic">
			<aui:option value="" selected="selected">
				<liferay-ui:message key="problem-goal"/>
			</aui:option>
			<c:forEach items="${solutionTypeList}" var="solutionType">
				<%-- <c:choose>
					<c:when test="${solutionType.getName() == 'Good Practices'}"></c:when>
					<c:when test="${solutionType.getName() == 'Lessons Learned'}"></c:when>
					<c:otherwise>
						<aui:option value="${solutionType.categoryId}" selected="${solution.spaceId == solutionType.categoryId}">${solutionType.name}</aui:option>
					</c:otherwise>
				</c:choose> --%>
				
				<aui:option value="${solutionType.categoryId}" selected="${solution.spaceId == solutionType.categoryId}">${solutionType.name}</aui:option>
				
			</c:forEach>
		</aui:select>
  </div> 
  
<%--   <div class="ust-form-box01">
  <aui:select name="category" showRequiredLabel="<%=false %>" label="" cssClass="basic">
			<aui:option value="" selected="selected">
				<liferay-ui:message key="category"/>
			</aui:option>
			<c:forEach items="${solutionCategoryList}" var="categoryList">
				<aui:option value="${categoryList.categoryId}" selected="${solution.solutionCategory == categoryList.categoryId}">${categoryList.name}</aui:option>
			</c:forEach>
		</aui:select>
  </div> --%>
      
  <div class="ust-form-box01 clearfix">
  	 <aui:button type="button" cssClass="ust-form-submitbut" value="Submit" onClick='<%=renderResponse.getNamespace() +  "submitSolutionForm();"%>' />
	 <aui:button value="cancel" cssClass="ust-form-cancelbut" type="cancel" onClick="/group/guest/home"/> 
     <%-- <aui:button type="Reset" value="Reset" cssClass="ust-form-cancelbut"/> --%>
  </div>
  </aui:form>
</div>

</body>
<div id="descriptionHtmlData" style="display: none;">${solution.description}</div> 
<script>$(document).ready(function(){$(".np-portlate-fix01").removeClass("np-portlate-fix01").addClass("np-portlate-fix0111")});</script>

<%@ include file="createSolution_js.jsp"%>