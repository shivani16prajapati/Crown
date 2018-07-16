<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@include file="../init.jsp"%>
<liferay-portlet:renderURL var="portletURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>">
</liferay-portlet:renderURL>
<%
String tabValue = ParamUtil.getString(request, "tab", "the-solution");
String viewJsp = (String)request.getAttribute("viewJsp");
boolean isInventorPrefsAdmin = (Boolean)request.getAttribute("isInventorPrefsAdmin");
String tabNames = (String)request.getAttribute("tabNames");
String tabValues = (String)request.getAttribute("tabValues");
String includeURL = "/WEB-INF/views/solution-scrum/" + viewJsp.trim() + ".jsp";
%>
 
<liferay-ui:tabs names="<%=tabNames %>" 
tabsValues="<%=tabValues %>" 
param="tab" url="<%= portletURL %>" />
<c:import url="<%= includeURL %>"/>