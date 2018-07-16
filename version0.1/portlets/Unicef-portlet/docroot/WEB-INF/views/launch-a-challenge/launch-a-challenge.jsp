<%@include file="../init.jsp"%>
<portlet:renderURL var="backToLaunchChallenge" >
    <portlet:param name="action" value="backToLaunch"/>
</portlet:renderURL>
<portlet:actionURL var="launchAChallengeURL">
    <portlet:param name="action" value="launchChallenge"/>
</portlet:actionURL>

<portlet:actionURL var="listIdeaChallenge">
    <portlet:param name="action" value="challangelist"/>
</portlet:actionURL>

<fmt:formatDate var="startDate" pattern="MM/dd/yyyy" value="${challenge.challengeStartDate}" />
<fmt:formatDate var="endDate" pattern="MM/dd/yyyy" value="${challenge.challengeEndDate}" />
<body style="background-color:#e6eaed">
<div class="ust-if-mainbox">

<aui:form name="launchChallenge" action="<%=launchAChallengeURL %>"  enctype="multipart/form-data" method="post">
    <aui:input name="challengeContent" type="hidden" value="third blog"/>
        <aui:input name="challengeId" type="hidden" value="${challenge.challengeId}"/>
        
     <div class="ust-form-box004 ust-form-compelsory">
        <aui:input name="challengeTitle" type="text" required="true" showRequiredLabel="<%=false%>" value="${challenge.challengeTitle}" cssClass="ust-form-box01-form004" label="" placeholder="Challenge Title" >
            <aui:validator name="required" />
            <aui:validator name="maxLength">120</aui:validator>
        </aui:input>
    </div>
    
                  <div class="ust-form-box01 ust-form-compelsory" id="<portlet:namespace/>ust-form-div-002">
                         <aui:input type="hidden" name="hiddenTagTitle" value="${challenge.challengeTagline}"/>
                         <aui:input name="challengeTagline" required="true"  showRequiredLabel="<%=false %>" label="" placeholder="TagLine" value="${challenge.challengeTagline}" cssClass="ust-form-box01-form01">
                                <aui:validator name="required" />
                                <aui:validator name="maxLength">250</aui:validator>
                         </aui:input>
                         <span style="position:absolute;right: 31px;top: 2px;">
                            <img onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key='challenge-tagline' />');" src="/html/themes/control_panel/images/portlet/help.png">
                         </span>
                  </div>
                  
    <div class="ust-form-box004">
        <liferay-ui:message key="description-of-goal"/>
    </div>
    <div id="descriptionError" class="portlet-msg-error" style="display:none;margin-left: 10%; width: 80%;" >
                    <liferay-ui:message key="please-describe-your-idea"/>
    </div>
    <div class="ust-form-box004">
             <aui:input name="hiddenDescription" type="hidden" value="${challenge.description}"/>
            <liferay-ui:input-editor initMethod="initDescriptionckEditor"  name="descriptionckEditor"  />
    </div>
<%--     <div class="ust-form-box004">
            <aui:input type="checkbox" name="prize" showRequiredLabel="<%=false %>" label="Include a Prize:" checked="${challenge.prize eq 'true' ? 'true' : ''}"  onChange="checkPrizeValue(this);"/>
    </div>
    <div class="ust-form-box004" id="<portlet:namespace />prizeDiv" style="display: none;">
        <aui:input name="prizeDescription" type="text" showRequiredLabel="<%=false%>" value="${challenge.prizeDescription}" cssClass="ust-form-box01-form004" label="" placeholder="Prize Description"/>
    </div>
    <div id='challengeError'  class="portlet-msg-error" style="display: none;margin-left: 10%;width: 80%;margin-top: 10px;">
        <liferay-ui:message key="please-select-challenge-start-date"/>
    </div> --%>
    <%-- <div class="ust-form-box004 clearfix">
        
            <div class="col-sm-6 col-xs-12" style="padding-left:0px;">
                <label>Challenge Begins:</label>
                <input type="text" class="datepicker cs-input" name='<portlet:namespace />startDate' id='<portlet:namespace/>startDate' placeholder="(mm/dd/yyyy)" value="${startDate}">
            </div>
            <div class="col-sm-6 col-xs-12">
                <label>Challenge Ends:</label>
                <input type="text" class="datepicker cs-input" name='<portlet:namespace/>endDate' id='<portlet:namespace/>endDate' placeholder="(mm/dd/yyyy)" value="${endDate}">
            </div>
        
    </div> --%>
    <%--  <div class="ust-form-box01 ust-form-compelsory">
                        <aui:select name="vertical" showRequiredLabel="<%=false %>" label="" required="true" cssClass="basic">
                            <aui:option value="" selected="selected">
                                <liferay-ui:message key="vertical"/>
                            </aui:option>
                            <c:forEach items="${categoryList}" var="vertical">
                                <aui:option value="${vertical.categoryId}" selected="${challenge.verticalId == vertical.categoryId}">${vertical.name}</aui:option>
                            </c:forEach>
                        </aui:select>
    </div> --%>
          <div class="ust-form-box01 ust-form-compelsory"">
                    <aui:select name="LinOfBusiness" showRequiredLabel="<%=false %>" required="true" label="" cssClass="basic">
                        <aui:option value="" selected="selected">
                            <liferay-ui:message key="Line Of Business"/>
                        </aui:option>
                           <c:forEach items="${lineOfbussiness}" var="lofbuss">                   
                                    <aui:option value="${lofbuss.categoryId}" selected="">${lofbuss.name}</aui:option>
                            </c:forEach>
                    </aui:select>
           </div>
      
      <div class="ust-form-box01 ust-form-compelsory">
                        <aui:select name="challengeGoal" showRequiredLabel="<%=false %>" required="true" label="" cssClass="basic">
                            <aui:option value="" selected="selected">
                                <liferay-ui:message key="challenge-goal"/>
                            </aui:option>
                            <c:forEach items="${ideaTypeList}" var="goalType">
                                <c:if test="${ideaType.name ne 'Massive disruption potential'}" >
                                   <aui:option value="${goalType.categoryId}" selected="${challenge.goalId == goalType.categoryId}">${goalType.name}</aui:option>
                                </c:if>
                            </c:forEach>
                        </aui:select>
        </div>
        
        <%-- <div class="ust-form-box01">
        <div>Sponsors:</div>
        <aui:input name="userCategoryIds" type="hidden" />
             <liferay-util:buffer var="selectUserIds">
                 <liferay-ui:input-move-boxes
                 leftBoxName="availableUserIds"
                 leftList="${availableUserIdsList}"
                 leftTitle="available"
                 rightBoxName="selectedUserIds"
                 rightList="${selectedUserList}"
                 rightTitle="selected" 
                 rightReorder="true"
              />
             </liferay-util:buffer>
        </div> --%>
        
<div class="ust-form-box004 clearfix">
         <aui:button type="button" cssClass="ust-form-submitbut" style="float:left !important; margin-right:10px;" value="Submit" onClick='<%=renderResponse.getNamespace() +  "submitForm();"%>' />
         <aui:button type="button" value="cancel" cssClass="ust-form-cancelbut" onClick="location.href='${listIdeaChallenge}'"/>
</div>
</aui:form>
</div>
</body>
<div id="descriptionHtmlData" style="display: none;">${challenge.description}</div> 
<style>
.ust-form-box004 .control-group{
margin-bottom:0px;
}
.cs-input{ width:220px !important; margin:0 !important; padding:4px  !important; height:32px  !important;}.end{margin-top:10px;}
.challenge-fix-01 label{margin-top:13px;}
</style>
<%@ include file="launch-a-challenge_js.jsp"%>