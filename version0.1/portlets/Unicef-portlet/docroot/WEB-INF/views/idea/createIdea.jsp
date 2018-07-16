<%@page import="com.unicef.util.IdeaConstant"%>
<%@page import="com.unicef.domain.Idea"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../init.jsp"%>

<portlet:actionURL var="saveIdeaURL">
	<portlet:param name="action" value="saveIdea"/>
</portlet:actionURL>
<c:set var="id" value="${idea.ideaId}"/>

<portlet:renderURL var="backToIdeaViewURL"/>
<fmt:formatDate pattern="dd MMM, yyyy" value="${submissionDate}" var="formattedSubmissionDate" />
<liferay-ui:error key="<%=IdeaConstant.ERROR_IDEA_LOG_IN %>" message="please-sign-in-to-continue"/>
<liferay-ui:success key="<%=IdeaConstant.SUCCESS_IDEA_MESSAGE_CREATE %>" message="new-idea-created-succesfully."/>

<body style="background-color:#e6eaed">
	<div class="ust-if-mainbox" id="<portlet:namespace/>createIdeafm">
	<liferay-ui:asset-tags-error />
		<aui:form action="<%=saveIdeaURL %>" method="post" name="ideaForm" enctype="multipart/form-data">
		 	<aui:input name="sliderEdit" type="hidden" value="${sliderEdit}"/>
		  	<aui:input name="ideaContent" type="hidden" value="third blog"/>
  			<aui:input name="ideaId" type="hidden" value="${idea.ideaId}"/>
			<aui:input name="keywords" type="hidden"/>
				  <div class="ust-if-title01 clearfix">
				  		
				  		<c:if test="${editIdeaOnly}">
				  			<div class="ust-if-t01-box1">Edit Idea Information - Full Form</div>
				  		</c:if>
				  		<c:if test="${!editIdeaOnly}">
				  			<div class="ust-if-t01-box1">Idea Submission - Full Form</div>
				  		</c:if>
				    	
				    <div class="ust-if-t01-box2"><span>Required Fields</span></div>
				  </div>
				  
			     <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-008">
				   		<aui:input type="hidden" name="hiddenInvName" value="${idea.invName}"/>
				   		<c:choose>
				   		<c:when test="${empty idea.invName}">
				   		     <aui:input name="invName" required="true"  disabled="true" showRequiredLabel="<%=false %>" label="" value="<%=themeDisplay.getUser().getFullName()%>" cssClass="ust-form-box01-form01" placeholder="Inventors  Name">
								<aui:validator name="required" />
								<aui:validator name="maxLength">250</aui:validator>
							</aui:input>
				   		
				   		</c:when>
				   		<c:otherwise>
				   			<aui:input name="invName" required="true" disabled="true" showRequiredLabel="<%=false %>" label="" value="${idea.invName}" cssClass="ust-form-box01-form01" placeholder="Inventors  Name">
								<aui:validator name="required" />
								<aui:validator name="maxLength">250</aui:validator>
							</aui:input>
				   		</c:otherwise>
				   		</c:choose>
				  </div>
				  
				  <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-001">
				   		<aui:input type="hidden" name="hiddenTitle" value="${idea.ideaTitle}"/>
			        	<aui:input name="ideaTitle" id="ideaTitle" required="true" showRequiredLabel="<%=false %>" label="" value="${idea.ideaTitle}" cssClass="ust-form-box01-form01" placeholder="Idea Name">
							<aui:validator name="required" />
							<aui:validator name="maxLength">120</aui:validator>
						</aui:input>
				  </div>
				  
				  <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-002">
				 		 <aui:input type="hidden" name="hiddenTagTitle" value="${idea.ideaTagTitle}"/>
				 		 <aui:input name="ideaDescribeTitle" required="true"  showRequiredLabel="<%=false %>" label="" placeholder="TagLine" value="${idea.ideaTagTitle}" cssClass="ust-form-box01-form01">
								<aui:validator name="required" />
								<aui:validator name="maxLength">30</aui:validator>
						 </aui:input>
						 <span style="position:absolute;right: 31px;top: 2px;">
							<img onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key='idea-tagline' />');" src="/html/themes/control_panel/images/portlet/help.png">
						 </span>
				  </div>
				  
				  
				  <c:choose>
				  <c:when test="${update_idea eq 1}">
				  
				  <fmt:formatDate pattern="dd MMM, yyyy" value="${idea.submissionDate}" var="ideacreateddate" />
				  <div class="ust-form-box01 clearfix" id="<portlet:namespace/>ust-form-div-010">
				    <div class="ust-form-box02"><liferay-ui:message key="submission-date"/></div>
				    <div class="ust-form-box03">${ideacreateddate}</div>
				  </div>
				  </c:when>
				  <c:otherwise>
				   <div class="ust-form-box01 clearfix" id="<portlet:namespace/>ust-form-div-003">
				    <div class="ust-form-box02"><liferay-ui:message key="submission-date"/></div>
				    <div class="ust-form-box03">${formattedSubmissionDate}</div>
				  </div>
				  </c:otherwise>
				  </c:choose>
				  
				  <%-- <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-004">
				  	<aui:input type="hidden" name="hiddenContest" value="${idea.ideaProgramme}"/>
				  	 <aui:select name="ideaContest" required="true"  showRequiredLabel="<%=false %>" label="" cssClass="basic">
				  	 	<aui:option value="" selected="selected">
								<liferay-ui:message key="idea-program"/>
							</aui:option>
						<c:forEach items="${ideaContestList}" var="ideaContest">
							<aui:option value="${ideaContest.categoryId}" selected="${idea.ideaProgramme == ideaContest.categoryId}">${ideaContest.name}</aui:option>
						</c:forEach>
					</aui:select>
				  </div> --%>
				  
				   
				    <%-- <div class="ust-form-box01" id="<portlet:namespace/>ust-form-div-005">
				  		<aui:input type="hidden" name="hiddenCategory" value="${idea.ideaCategory}"/>
					  	<aui:select name="category" showRequiredLabel="<%=false %>" label="" cssClass="basic">
							<aui:option value="" selected="selected">
								<liferay-ui:message key="innovation-focus"/>
							</aui:option>
							<c:forEach items="${categoryList}" var="categoryList">
								<aui:option value="${categoryList.categoryId}" selected="${idea.ideaCategory == categoryList.categoryId}">${categoryList.name}</aui:option>
							</c:forEach>
						</aui:select>
				  </div> --%>
					  <%-- <div class="ust-form-box01">
							 	<aui:select name="edgeService" showRequiredLabel="<%=false %>" label="" cssClass="basic">
									<aui:option value="" selected="selected">
										<liferay-ui:message key="edge-service"/>
									</aui:option>
									<c:forEach items="${edgeCategory}" var="edgeCategory">
										<aui:option value="${edgeCategory.categoryId}" selected="${idea.edgeServiceId == edgeCategory.categoryId}">${edgeCategory.name}</aui:option>
									</c:forEach>
								</aui:select>
		  					</div> 
		  			  --%>
		  			  
		<%-- 		  <div class="ust-form-box01" id="<portlet:namespace/>ust-form-div-006">
				    <aui:input type="hidden" name="hiddenStage" value="${idea.ideaStage}"/>
				      <aui:select name="stage"  showRequiredLabel="<%=false %>" label=""  cssClass="basic">
				      	<c:choose>
				      		<c:when test="${isUpdateForm}">
				      		   <aui:option value="" selected="selected">
											<liferay-ui:message key="stage"/>
								</aui:option>
				      			<c:forEach items="${stageList}" var="stageList">
									<aui:option value="${stageList.categoryId}" selected="${idea.ideaStage == stageList.categoryId}">${stageList.name}</aui:option>
								</c:forEach> 
				      		</c:when>
				      		<c:otherwise>
				      			<c:choose>
				      				<c:when test="${isEditStage}">
				      				    <aui:option value="" selected="selected">
											<liferay-ui:message key="stage"/>
										</aui:option>
				      					<c:forEach items="${stageList}" var="stageList">
				      							<aui:option value="${stageList.categoryId}">${stageList.name}</aui:option>
				      					</c:forEach>
				      				</c:when>
				      				<c:otherwise>
				      				     <aui:option value="" selected="selected">
											<liferay-ui:message key="stage"/>
										</aui:option>
				      					<c:forEach items="${stageList}" var="stageList">
				      						<aui:option value="${stageList.categoryId}" selected="${stageList.name == 'Idea Backlog'}">${stageList.name}</aui:option>
				      					</c:forEach>
				      				</c:otherwise>
				      			</c:choose>
				      		</c:otherwise>
				      	</c:choose>
					</aui:select>
				  </div> --%>
				  
		          <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-007">
				  	 	<aui:input type="hidden" name="hiddenType" value="${idea.innovationType}"/>
				  	  	<aui:select name="ideaType" showRequiredLabel="<%=false %>"  required="true" label="" cssClass="basic">
							<aui:option value="" selected="selected">
								<liferay-ui:message key="innovation-type"/>
							</aui:option>
							<c:forEach items="${ideaTypeList}" var="ideaType">
								<c:if test="${ideaType.name ne 'Massive disruption potential'}" >
									<aui:option value="${ideaType.categoryId}" selected="${idea.innovationType ==  ideaType.categoryId}">${ideaType.name}</aui:option>
								</c:if>
							</c:forEach>
						</aui:select>
				  </div>
				  
				 <%--  <div class="ust-form-box01" id="<portlet:namespace/>ust-form-div-010" style="display: none;">
				    <aui:input type="hidden" name="hiddenStage" value="${idea.bussInvType}"/>
				     <aui:select name="bussInvType" showRequiredLabel="<%=false%>"  label="" cssClass="basic">
							<aui:option value="">Type OF Business</aui:option>
							<aui:option value="1">Incremental</aui:option>
							<aui:option value="2">Radical</aui:option>
						</aui:select>
				   </div> --%>
				  
				 
				  
				  <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-009">
				  	 	<aui:input type="hidden" name="hiddenlineOfbussinessId" value="${idea.lineOfbussinessId}"/>
				  	  	<aui:select name="lineofbusiness"  showRequiredLabel="<%=false %>" required="true" label="" cssClass="basic">
							<aui:option value="" selected="selected">
								<liferay-ui:message key="Line Of Business "/>
							</aui:option>
							<c:forEach items="${lineOfbussiness}" var="lofbuss">					
									<aui:option value="${lofbuss.categoryId}" selected="">${lofbuss.name}</aui:option>
							</c:forEach>
						</aui:select>
				  </div> 
				  
				  
				   <div class="ust-form-box01">
				   	<liferay-ui:message key="describe-your-idea"/><span>*</span>
				  </div>
				  <div id="<portlet:namespace/>descriptionError" class="portlet-msg-error" style="display:none;">
					<liferay-ui:message key="please-describe-your-idea"/>
				  </div>
				   <div class="ust-form-box01" id="editor_inline">
    				 <aui:input name="hiddenDescription" type="hidden" value="${idea.description}"/>
    				 <liferay-ui:input-editor initMethod="initDescriptionckEditor"  onChangeMethod="keywordExtractorfocusout" onBlurMethod="keywordExtractBlurMethod" name="descriptionckEditor" />
    			   </div>
    			   <div id='<portlet:namespace/>keywordError' class="portlet-msg-error" style="display: none;margin-left: 10%;width: 80%;margin-top: 10px;">
    			   		<liferay-ui:message key="please-select-only-5-keywords"/>
    			   </div>
    			   	<div class="ust-form-box01" id="<portlet:namespace />keywordDiv" style="display: none;">
						<div class="ust-keyw-title01">Tags:</div>
	    			  		<div class="ab-keyword clearfix" id="<portlet:namespace/>importKeywords">
	    			  	</div>
				  	</div>
				  	<div class="ust-form-box01" id="duplicate_Detactor"></div>
	  <div class="ust-form-box01">
	    <div class="clearfix ust-form-imgupload-box">
	     <div class="ust-form-img-box">
	        <aui:input type="file" required="false" name="fileUpload1" id="fileUpload1" cssClass="upload" onchange="checkimageRatio(this)" showRequiredLabel="<%=false %>">
					<%-- <aui:validator name="acceptFiles"
							errorMessage="Please select proper type of file <br>('${fileExtension}')">
							'${fileExtension}'
						</aui:validator> --%>
			</aui:input> 
	     </div> 
	      <div class="ust-form-img-title-text clearfix">
	       
	       <span><liferay-ui:message key="please-upload-cool-image"/></span> 
	       <span id="<portlet:namespace />textName"></span> </div>
	    </div>
	  </div>

	  <div class="ust-form-box01 clearfix" style="margin-top: 15px;">
		  <aui:button type="button" cssClass="ust-form-submitbut" value="Submit" onClick='<%=renderResponse.getNamespace() +  "submitForm();"%>' />
		  <aui:button value="cancel" cssClass="ust-form-cancelbut" type="cancel" onClick="location.href='/group/guest/home'"/> 
		  <%-- <aui:button type="Reset" value="Reset" cssClass="ust-form-cancelbut"/> --%>
	  </div>
	    </aui:form>
	</div>

</body>
<div id="descriptionHtmlData" style="display: none;">${idea.description}</div>

<style>

.ab-keyword{ padding:8px; border:1px solid #dedede;}
.ab-keyword .ab-keywordbox2{ float:left; font-size:15px; padding:5px 8px; border:1px solid #ececec; border-radius:4px; margin:0 7px 7px 0; position: relative;}

.cancelTag{
position: absolute;
top: -5;
right: -5;
font: bold 12px/18px arial !important;
background-color: #333;
border-radius:20px;
height: 16px;
width: 16px;
text-align: center;
font-size: 13px;
opacity:0.6; color:#ffffff !important;
}
 
.cancelTag:HOVER {
	opacity:1;
}
</style> 
<script>$(document).ready(function(){$(".np-portlate-fix01").removeClass("np-portlate-fix01").addClass("np-portlate-fix0111")});</script>
<c:if test="${editIdeaOnly}">
	<script type="text/javascript">
		$("#<portlet:namespace/>ust-form-div-001").addClass("np-display-off");
		$("#<portlet:namespace/>ust-form-div-002").addClass("np-display-off");
		$("#<portlet:namespace/>ust-form-div-003").addClass("np-display-off");
		$("#<portlet:namespace/>ust-form-div-004").addClass("np-display-off");
		$("#<portlet:namespace/>ust-form-div-005").addClass("np-display-off");
		$("#<portlet:namespace/>ust-form-div-006").addClass("np-display-off");
		$("#<portlet:namespace/>ust-form-div-007").addClass("np-display-off");
		
		
		
		
	</script>
</c:if>


<%@ include file="createidea_js.jsp"%>