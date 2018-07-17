<%@page import="com.unicef.util.CommonUtil"%>
<%@include file="../init.jsp"%>
<fmt:formatNumber value="${kudosCount - 0.0000}" maxFractionDigits="3" var="kudosCount"/>
<div id='<portlet:namespace/>myInnovation'>
<div style="font-size: 18px;color:#fff;font-weight: bold;padding-bottom: 11px;background-color: #4da9fc;border-radius:10px 10px 0 0;line-height:18px;padding-top:11px;padding-left:12px;">My Innovation</div>
<div class="ku-m-box clearfix" >
	<div style="text-align:left;font-size:18px;font-weight: bold; padding:10px 0;">My Points</div>
 	<div style="width: 100%; margin: 0 auto;">
		<div class="ku-m-text01 clearfix"><span>Inno Points:</span><span>0</span></div>
		<div class="ku-m-text01 clearfix"><span>Thank You's:</span><span>${thanksPoint}</span></div>
		<div class="ku-m-text01 clearfix"><span>Kudos:</span><span>${kudosCount}</span></div>
 	</div>
 	<div>
 	<c:if test="${fn:length(ideas) gt 0}">
 		 <div style="text-align:left;font-size:18px;font-weight: bold;padding:10px 0;">My Ideas</div>
		<c:forEach items="${ideas}" var="idea">
			<c:set value="${idea.groupId}" var="groupId"/>
			<c:set value="${idea.ideaId}" var="ideaId"/>
			<%
			PortletURL customPortletURL =null;
			customPortletURL = CommonUtil.createIdeaPortletURL(request, themeDisplay.getPlid(),
					((Long)pageContext.getAttribute("ideaId")) , ((Long)pageContext.getAttribute("groupId")));
			%>
				<span style="display:block;"><a href="<%=customPortletURL%>">${idea.ideaTitle}</a></span>
		</c:forEach>
 	</c:if>

 	</div>  
</div>
</div>
