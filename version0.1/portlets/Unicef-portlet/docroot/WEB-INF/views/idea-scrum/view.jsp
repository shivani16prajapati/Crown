<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@include file="../init.jsp"%>
<liferay-portlet:renderURL var="portletURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>">
</liferay-portlet:renderURL>

<portlet:actionURL var="addToTeamURL" name="addToTeam">
	<portlet:param name="action" value="addToTeam"/>
	<portlet:param name="ideaId" value="${idea.ideaId}"/>
</portlet:actionURL>

<%
	String tabValue = ParamUtil.getString(request, "tab", "the-idea");
	String viewJsp = (String)request.getAttribute("viewJsp");
	boolean isInventorPrefsAdmin = (Boolean)request.getAttribute("isInventorPrefsAdmin");
	String tabNames = (String)request.getAttribute("tabNames");
	String tabValues = (String)request.getAttribute("tabValues");
	String includeURL = "/WEB-INF/views/idea-scrum/" + viewJsp.trim() + ".jsp";
%>

<div id='<portlet:namespace/>ideaScrum'>
	 <div style="font-size: 18px;color:#fff;font-weight: bold;padding-bottom: 11px;background-color: #4DA9FC;border-radius:10px 10px 0 0;line-height:18px;padding-top:11px;padding-left:12px;margin-bottom: 8px;">
	 	Dsprint
	 </div>
	<div class="np-spacer-01 clearfix" >
		<liferay-ui:tabs names="<%=tabNames %>" 
			tabsValues="<%=tabValues %>" 
			param="tab" url="<%= portletURL %>" />
			<c:import url="<%= includeURL %>" />
	</div>
</div>

<% 
	String currentURL = PortalUtil.getCurrentURL(request);
%>

<liferay-ui:search-container emptyResultsMessage="there-are-no-team-members">
	<liferay-ui:search-container-results
		results="${teamMembers}"
		total="${fn:length(teamMembers)}"
	/>
	<liferay-ui:search-container-row className="com.unicef.domain.TeamMember" modelVar="teamMember" >
		<liferay-ui:search-container-column-text property="fullName" title="Full Name" />
		<liferay-ui:search-container-column-text property="roleName" title="Role Name" />
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<c:if test="${!isMember}">
	<a href="<%=addToTeamURL.toString()%>" onclick="return confirm('Submit your request for approval.?');">
		<liferay-ui:message key="join-the-team"/>
	</a>
</c:if>
