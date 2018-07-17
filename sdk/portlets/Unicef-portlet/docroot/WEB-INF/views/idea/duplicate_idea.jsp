<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.unicef.service.IdeaCommentVoteService"%>
<%@include file="../init.jsp" %>

     <%
    	long structureId = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "Idea_WAR_Unicefportlet");
    %>
    
     <c:if test="${idea_duplicate eq 1}">
            <liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=structureId %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewIdea" />
	    	   <portlet:param name="ideaId" value="${idea.ideaId}" /> 	   
	    	</liferay-portlet:renderURL>
			
      <span style="font: normal 21px/30px 'roboto', Arial, Helvetica, sans-serif;color: red;">We've detected a similar idea, please check: <a href="${ideaPortletURL}" target="_blank">Click Here</a></span>
      
</c:if>