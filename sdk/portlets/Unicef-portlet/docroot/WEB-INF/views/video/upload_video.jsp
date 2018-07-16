<%@page import="com.unicef.util.VideoConstant"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page pageEncoding="UTF-8"%>
<%@include file="../init.jsp"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.min.css">
	
<liferay-ui:error key="<%=VideoConstant.ERROR_VIDEO_LOG_IN %>" message="please-sign-in-to-continue-video"/>
<liferay-ui:success key="<%=VideoConstant.SUCCESS_VIDEO_MESSAGE_CREATE %>" message="new-video-created-succesfully."/>

<portlet:actionURL var="uploadVideoURL">
	<portlet:param name="action" value="uploadVideo"/>
</portlet:actionURL>



<input type="hidden"  id="uploadVideoURL" value="${uploadVideoURL}"/>


<body style="background-color:#e6eaed">
	
	<div class="ust-if-mainbox" id="<portlet:namespace/>createIdeafm">
			<aui:form action="<%=uploadVideoURL %>" method="post" name="videoForm" enctype="multipart/form-data">
				 
				 <aui:input type="hidden" name="hiddenVideoAction" value="${action}"/>
				 
				  <div class="ust-if-title01 clearfix">
				  	<div class="ust-if-t01-box1">Video Submission - Form</div>
				    <div class="ust-if-t01-box2"><span>Required Fields</span></div>
				  </div>
				 
				  <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-001">
				   		<aui:input type="hidden" name="hiddenVideoId" value="${videoId}"/>
			        	<aui:input name="videoName" id="videoName" required="true" showRequiredLabel="<%=false %>" label="" value="${videoName}" cssClass="ust-form-box01-form01" placeholder="Video Name">
							<aui:validator name="required" />
						</aui:input>
				  </div>
				 
				 <fmt:formatDate pattern="MM/dd/yyyy" value="${videoExpireDate}"
					var="editExpireDate" />
				 
				   <div class="ust-form-box01 clearfix" id="<portlet:namespace/>ust-form-div-020">
				    <div class="ust-form-box02"><liferay-ui:message key="exipre-date"/></div>
				    <div class="ust-form-box03"> 
					    <input type="text" class="np-form-control" id="expireDate" readonly="readonly"  name="expireDate" value="${editExpireDate}">
						<i class="fa fa-calendar" aria-hidden="true"></i> 
					</div>
				   </div>
					
				  <div class="ust-form-box01 ust-form-compelsory">
				    <div class="clearfix ust-form-imgupload-box">
				     <div class="ust-form-img-box">
				        <aui:input type="file" required="true" name="videfileUpload" id="videfileUpload" cssClass="upload" onchange="checkimageRatio(this)" showRequiredLabel="<%=false %>">
						</aui:input> 
				     </div> 
				      <div class="ust-form-img-title-text clearfix">
				       
				       <span><liferay-ui:message key="please-upload-cool-image-video"/></span>
				       <span id="<portlet:namespace />textName"></span> </div>
				    </div>
				  </div>
				
				  <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-001">
				   		<aui:input type="hidden" name="hiddenTitle" value=""/>
			        	<aui:input name="videoUrl" id="videoUrl" required="true" showRequiredLabel="<%=false %>" label="" value="${videoUrl}" cssClass="ust-form-box01-form01" placeholder="Video Url">
							<aui:validator name="required" />
						</aui:input>
				  </div>
				
				   <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-001">
				   		<aui:input type="hidden" name="hiddenTitle" value=""/>
			        	<aui:input name="videofeed" id="videofeed" required="true" showRequiredLabel="<%=false %>" label="" value="${videoFeedOrder}" cssClass="ust-form-box01-form01" placeholder="Feed order placement">
							<aui:validator name="required" />
						</aui:input>
				  </div>
				
				   
				  
				  <div class="ust-form-box01 clearfix" style="margin-top: 15px;">
					  <aui:button type="button" cssClass="ust-form-submitbut" value="Submit" onClick='<%=renderResponse.getNamespace() +  "submitForm();"%>'/>
					  <aui:button value="cancel" cssClass="ust-form-cancelbut" type="cancel" onClick="location.href='/group/guest/home'"/> 
				  </div>
				
			</aui:form>
 	</div>

</body>

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


<script>
$(document).ready(function(){
	$(".np-portlate-fix01").removeClass("np-portlate-fix01").addClass("np-portlate-fix0111")
});

$('#expireDate').datepicker({
	format:"mm/dd/yyyy",				
	startDate: "today",
	todayBtn: true,
	forceParse: false,
	autoclose: true,
	todayHighlight: true
});

function validateVideoFormFeed(){
	if (!($('#<portlet:namespace />videofeed').val()).match('^[0-3]{1}$')) {
		  $("#<portlet:namespace />videofeed").focus();
		  alert("Feed Order Is Number Only or less than Four");
		  return false;
	}else{
		return true;
	}
}

</script>


<%@ include file="upload_video_js.jsp"%>
