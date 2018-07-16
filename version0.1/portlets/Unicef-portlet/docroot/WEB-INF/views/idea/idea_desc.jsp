<%@include file="../init.jsp"%>


<portlet:renderURL var="renderPrevIdeaURL"/>

<portlet:actionURL var="saveIdeaInfoURL">
	<portlet:param name="action" value="saveIdeaInfo"/>
</portlet:actionURL>

<div class="ust-if-mainbox" id="<portlet:namespace/>createIdeafm">
	
	<h1>${ideaId}</h1>
		
	<aui:form action="<%=saveIdeaInfoURL %>" method="post" name="ideaForm" enctype="multipart/form-data">
		
		<aui:input name="ideaId" type="hidden" value="${ideaId}"/>
	
		<div class="ust-form-box01">
			<liferay-ui:input-editor initMethod="initDescriptionckEditor"
				onChangeMethod="keywordExtractorfocusout"
				onBlurMethod="keywordExtractBlurMethod" name="ideaDescriptionChanged" />
		</div>
		<div class="ust-form-box01">
			<aui:input type="file" required="true" name="fileUpload1" id="fileUpload1" cssClass="upload" showRequiredLabel="<%=false %>">
								<aui:validator name="acceptFiles"
									errorMessage="Please select proper type of file <br>('${fileExtension}')">
									'${fileExtension}'
								</aui:validator>
			</aui:input>
		</div>
		
		<div class="ust-form-box01 clearfix" style="margin-top: 15px;">	
			<aui:button type="button" cssClass="ust-form-submitbut" value="Submit" onClick='<%=renderResponse.getNamespace() +  "submitForm();"%>' />
			<aui:button value="cancel" cssClass="ust-form-cancelbut" type="cancel" onClick="<%=renderPrevIdeaURL%>"/>
		</div>
	
	</aui:form>
</div>