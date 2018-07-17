<%@page import="com.unicef.util.SprintConstant"%>
<%@page import="com.unicef.domain.Sprint"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@include file="../../init.jsp"%>


<portlet:actionURL var="saveSprintURL">
	<portlet:param name="action" value="saveSprint"/>
	<portlet:param name="tab" value="inventor-prefs"/>
</portlet:actionURL>
<c:set var="id" value="${sprint.sprintId}"/>
<portlet:renderURL var="backToSprintViewURL">
	<portlet:param name="tab" value="inventor-prefs"/>
</portlet:renderURL>
<fmt:formatDate pattern="MM/dd/yy" value='${sprint.startDate}' var="formattedStartDate" />
<div class="clearfix" id="<portlet:namespace/>createSprintfm">
<aui:form action="<%=saveSprintURL %>" method="post" name="sprintForm">
  <div class="np-title01 buffer-bottom">${sprintTitle}</div>
  <aui:input name="isAction" type="hidden" value="${isAction}"/>
  <aui:input name="orderIndex" type="hidden" value="${oldSprint.orderIndex}"/>
  <aui:input name="sprintId" type="hidden" value="${sprint.sprintId}"/>
  <aui:input name="solutionId" type="hidden" value="${solutionId}"/>
  <aui:input name="solutionScrumId" type="hidden" value="${solutionScrumId}"/>
  <div class="row clearfix">
  		<aui:input name="lastSprintOrder" value="${lastSprintOrder}" type="hidden"/>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
        	<aui:input name="sprintTitle" style="height:30px" required="true" label="<span>Focus:</span><span class='mandatory'>*</span>" showRequiredLabel="false" value="${sprint.focusTitle}" cssClass="form-control">
				<aui:validator name="required" />
				<aui:validator name="maxLength">120</aui:validator>
			</aui:input>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
        <aui:select name="sprintType" required="true" label="<span>Type:</span><span class='mandatory'>*</span>" showRequiredLabel="false" cssClass="form-control">
			    <aui:option>Select Type</aui:option>
			<c:forEach items="${sprintTypes}" var="sprintType">
				<aui:option value="${sprintType}" selected="${sprint.type == sprintType}">${sprintType}</aui:option>
			</c:forEach>
		</aui:select>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
        <aui:input name="sprintText" style="height:30px" required="true" label="<span>Text:</span><span class='mandatory'>*</span>" showRequiredLabel="false" value="${sprint.text}" cssClass="form-control">
				<aui:validator name="required" />
		</aui:input>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
        <aui:input name="sprintVideo" style="height:30px" label="<span>Video:</span>" showRequiredLabel="false" value="${sprint.videoURL}" cssClass="form-control">
		</aui:input>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
        <aui:input name="sprintStartDate" style="height:30px" required="true" label="<span>Start Date:</span><span class='mandatory'>*</span>" showRequiredLabel="false" cssClass="form-control datepicker" value="${formattedStartDate}">
				<aui:validator name="required" />
		</aui:input>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
        <aui:input name="sprintLength" style="height:30px" required="true" label="<span>Length(In Days):</span><span class='mandatory'>*</span>" showRequiredLabel="false" value="${sprint.daysLength}" cssClass="form-control">
				<aui:validator name="required" />
		</aui:input>
      </div>
    </div>
    <div class="col-md-7 col-sm-8 col-xs-12">
      <div class="form-group np-formboxfix01 clearfix">
        <aui:select name="sprintStatus" required="true" label="<span>Status:</span><span class='mandatory'>*</span>" showRequiredLabel="false" cssClass="form-control">
        		 <aui:option>Select Status</aui:option>
				<aui:option value="<%=SprintConstant.STATUS_PENDING %>" selected="${sprint.status == 0}">Pending</aui:option>
				<aui:option value="<%=SprintConstant.STATUS_CURRENT %>" selected="${sprint.status == 1}">Current</aui:option>
				<aui:option value="<%=SprintConstant.STATUS_COMPLETED %>" selected="${sprint.status == 2}">Completed</aui:option>
		</aui:select>
      </div>
    </div>
  </div>
  <div class="clearfix buffer-top">
   <aui:button type="button" cssClass="btn btn-primary" value="Submit" onClick='<%=renderResponse.getNamespace() +  "submitForm();"%>' />
	  <aui:button value="cancel" cssClass="btn btn-default" type="cancel" onClick="<%=backToSprintViewURL.toString() %>"/> 
  </div>
  </aui:form>
</div>
<script>
<portlet:namespace/>submitForm = function(){
	submitForm(document.<portlet:namespace />sprintForm);
}
<portlet:namespace/>initDatePicker = function(){
	<portlet:namespace/>sprintStartDatePicker();
};
<portlet:namespace/>sprintStartDatePicker = function(){
    var sprintListSize = "${sprintListSize}";
   var isAction = "${isAction}";
    var min;
    if(sprintListSize > 0 && isAction == "Insert"){
        var oldSprintEndDate = "${oldSprint.endDate}";
        min =  new Date(oldSprintEndDate);
		min.setDate(min.getDate() + 1);
    }else if(isAction == "Edit"){
        var sprintStartDate = "${sprint.startDate}";
        min =  new Date(sprintStartDate);
		min.setDate(min.getDate());
    }else{
    	min = new Date();
    }
	$('#<portlet:namespace/>sprintStartDate').datepicker({
	   showAnim: 'slide',
	   dateFormat: 'mm/dd/yy',
	   minDate : min
	});
};
<portlet:namespace/>initDatePicker();
</script>