<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@include file="../init.jsp"%>

<liferay-portlet:renderURL var="portletURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>">
</liferay-portlet:renderURL>
<%
String tabValue = ParamUtil.getString(request, "tab", "the-lession");
String viewJsp = (String)request.getAttribute("viewJsp");
String tabNames = (String)request.getAttribute("tabNames");
String tabValues = (String)request.getAttribute("tabValues");
String includeURL = "/WEB-INF/views/structure-learning/" + viewJsp.trim() + ".jsp";
%>

<div id='<portlet:namespace/>structureLearning'>
<div style="font-size: 18px;color:#fff;font-weight: bold;padding-bottom: 11px;background-color: #c4c5c7;border-radius:10px 10px 0 0;line-height:18px;padding-top:11px;padding-left:12px;">Structure Learning</div>

<liferay-ui:tabs names="<%=tabNames %>" 
tabsValues="<%=tabValues %>" 
param="tab" url="<%= portletURL %>" />

<c:import url="<%= includeURL %>"/>
</div>
