<%@page import="com.unicef.domain.Idea"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@include file="../init.jsp"%>

 <portlet:actionURL var="editIdeaURL">
	<portlet:param name="action" value="editIdea"/>
	<portlet:param name="tab" value="the-idea"/>
	<portlet:param name="companyId" value="${idea.companyId}"/>
	<portlet:param name="scopeGroupId" value="${idea.groupId}"/>
</portlet:actionURL>
<c:set var="id" value="${idea.ideaId}"/>
<portlet:renderURL var="backToIdeaScrumURL"/>
<fmt:formatDate pattern="dd MMM, yyyy" value="${submissionDate}" var="formattedSubmissionDate" />
<div class="container" id="<portlet:namespace/>createIdeafm">
<liferay-ui:asset-tags-error />
<aui:form action="<%=editIdeaURL %>" method="post" name="ideaForm" enctype="multipart/form-data">
  <aui:input name="sliderEdit" type="hidden" value="${sliderEdit}"/>
  <div class="np-title01 buffer-bottom">Idea Submission - Full Form</div>
  <aui:input name="ideaContent" type="hidden" value="third blog"/>
  <aui:input name="ideaId" type="hidden" value="${idea.ideaId}"/>
  <div class="row clearfix">
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
      		<aui:input type="hidden" name="hiddenTitle" value="${idea.ideaTitle}"/>
        	<aui:input name="ideaTitle" style="height:30px" required="true" label="<span>Idea Name:</span><span class='mandatory'>*</span>" showRequiredLabel="false" value="${idea.ideaTitle}" cssClass="form-control">
				<aui:validator name="required" />
				<aui:validator name="maxLength">120</aui:validator>
			</aui:input>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
		<div class="form-group np-formboxfix01 clearfix">
		<aui:input type="hidden" name="hiddenTagTitle" value="${idea.ideaTagTitle}"/>
		<aui:input name="ideaDescribeTitle" style="height:30px;padding-right:30px;" required="true" label="<span>TagLine</span><span class='mandatory'>*</span>" showRequiredLabel="false" value="${idea.ideaTagTitle}" cssClass="form-control">
					<aui:validator name="required" />
					<aui:validator name="maxLength">50</aui:validator>
		</aui:input>
		<span style="position:absolute;right: 31px;top: 2px;">
			<img onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key='idea-tagline' />');" src="/html/themes/control_panel/images/portlet/help.png">
		</span>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
		<div class="form-group np-formboxfix01 clearfix">
		<aui:input type="hidden" name="hiddenTagTitle" value="${idea.ideaTagTitle}"/>
		<aui:input name="ideaDescribeTitle" style="height:30px;padding-right:30px;" required="true" label="<span>TagLine</span><span class='mandatory'>*</span>" showRequiredLabel="false" value="${idea.ideaTagTitle}" cssClass="form-control">
					<aui:validator name="required" />
					<aui:validator name="maxLength">20</aui:validator>
		</aui:input>
		<span style="position:absolute;right: 31px;top: 2px;">
			<img onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key='idea-tagline' />');" src="/html/themes/control_panel/images/portlet/help.png">
		</span>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
        <label for="exampleInputEmail1"><liferay-ui:message key="submission-date"/> </label>
        <span class="np-formboxfix02 form-control">${formattedSubmissionDate}</span> </div>
    </div>
    
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
      	<aui:input type="hidden" name="hiddenContest" value="${idea.ideaProgramme}"/>
        <aui:select name="ideaContest" required="true" label="<span>Idea Program:</span><span class='mandatory'>*</span>" showRequiredLabel="false" cssClass="form-control">
			<c:forEach items="${ideaContestList}" var="ideaContest">
				<aui:option value="${ideaContest.categoryId}" selected="${idea.ideaProgramme == ideaContest.categoryId}">${ideaContest.name}</aui:option>
			</c:forEach>
		</aui:select>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
      	<aui:input type="hidden" name="hiddenCategory" value="${idea.ideaCategory}"/>
        <aui:select name="category" showRequiredLabel="false" label="<span>Category:</span>" cssClass="form-control">
			<aui:option value="" selected="selected">
				<liferay-ui:message key="select"/>
			</aui:option>
			<c:forEach items="${categoryList}" var="categoryList">
				<aui:option value="${categoryList.categoryId}" selected="${idea.ideaCategory == categoryList.categoryId}">${categoryList.name}</aui:option>
			</c:forEach>
		</aui:select>
      </div>
    </div> 
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
      <aui:input type="hidden" name="hiddenStage" value="${idea.ideaStage}"/>
        <aui:select name="stage" label="<span>Stage:</span>" showRequiredLabel="false"  cssClass="form-control">
			<aui:option value="" selected="selected">
				<liferay-ui:message key="select"/>
			</aui:option>
			<c:forEach items="${stageList}" var="stageList">
				<aui:option value="${stageList.categoryId}" selected="${idea.ideaStage == stageList.categoryId}">${stageList.name}</aui:option>
			</c:forEach>
		</aui:select>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
      <aui:input type="hidden" name="hiddenType" value="${idea.innovationType}"/>
        <aui:select name="ideaType" showRequiredLabel="false" label="<span>Innovation Type:</span><span class='mandatory'>*</span>" required="true" cssClass="form-control">
			<aui:option value="" selected="selected">
				<liferay-ui:message key="select"/>
			</aui:option>
			<c:forEach items="${ideaTypeList}" var="ideaType">
				<aui:option value="${ideaType.categoryId}" selected="${idea.innovationType ==  ideaType.categoryId}">${ideaType.name}</aui:option>
			</c:forEach>
		</aui:select>
      </div>
    </div>
     <div class="col-md-7 col-sm-8 col-xs-12">
	   <%--  <liferay-ui:panel extended="<%= false %>" id="ideaTaggingPanel" persistState="<%= true %>" title="Tagging">
			<aui:fieldset> --%>
			     <c:choose>
			     	<c:when test="${idea.ideaId > 0}">
			     		<aui:input classPK="${idea.ideaId}" model="<%= Idea.class %>" name="tags" type="assetTags" />
			     	</c:when>
			     	<c:otherwise>
			     		<aui:input classPK="0" model="<%= Idea.class %>" name="tags" type="assetTags" />
			     	</c:otherwise>
			     </c:choose>
			<%-- </aui:fieldset>
		</liferay-ui:panel> --%>
	</div>
  </div>
  <div class="np-title02 buffer-bottom buffer-top"><liferay-ui:message key="describe-your-idea"/><span>*</span> </div>
  <div id="<portlet:namespace/>descriptionError" class="portlet-msg-error" style="display:none;">
	<liferay-ui:message key="please-describe-your-idea"/>
  </div>
  <div class="row">
    <div class="col-md-8 col-sm-10 col-xs-12">
    <aui:input name="hiddenDescription" type="hidden" value="${idea.description}"/>
     <liferay-ui:input-editor initMethod="initDescriptionckEditor" name="descriptionckEditor"  />
     </div>
    <div class="col-xs-12">
	<div id="fileUploader1" class="row fileUploader">
      <div id="unicefUpload1" class="col-md-5 col-sm-7 col-xs-9 buffer-top unicefUpload">
        <div id="ideaFileUploader1" class="form-group np-formboxfix01 clearfix">
           <aui:input type="file" required="true" name="fileUpload1" showRequiredLabel="false" label="<span>Attachements:</span>">
						<aui:validator name="acceptFiles"
							errorMessage="Please select proper type of file <br>('${fileExtension}')">
							'${fileExtension}'
						</aui:validator>
			</aui:input> 
        </div>
      </div>
       <div id="addbtnDiv" class="col-md-2 col-sm-4 col-xs-3 np-formboxfix03">
	    <a href="#" name='<portlet:namespace/>removeFileUploaderButton1' id='<portlet:namespace/>removeFileUploaderButton1' style="display: none;">
				<img src="<%=request.getContextPath()%>/images/delete-icon.png" width="16" height="16" onclick="<portlet:namespace/>removeFileDetails('1');">
		</a>
    	<a id="<portlet:namespace/>fileUploadLink"  href="javascript:void(0);" onclick="javascript:<portlet:namespace/>addFileUpload();"><img id='<portlet:namespace/>btnAdd' src="<%=request.getContextPath()%>/images/plus-icon.png" width="16" height="16"></a>
    </div>
    </div>
	
	<div id="dynamicDiv">
	</div>
</div>	
	
</div>
  <div class="clearfix buffer-top">
   <aui:button type="button" cssClass="btn btn-primary" value="Submit" onClick='<%=renderResponse.getNamespace() +  "submitForm();"%>' />
	  <aui:button value="cancel" cssClass="btn btn-default" type="cancel" onClick="<%=backToIdeaScrumURL.toString() %>"/> 
	  <aui:button type="Reset" value="Reset" cssClass="btn btn-default"/>
  </div>
  </aui:form>
</div>
<div id="descriptionHtmlData" style="display: none;">${idea.description}</div> 
<%@ include file="edit_idea_js.jsp"%>