<%@include file="../init.jsp"%>

<%
String sprintViewJsp = (String)request.getAttribute("sprintViewJsp");
String sprintIncludeURL = "/WEB-INF/views/solution-scrum/sprint/" + sprintViewJsp.trim() + ".jsp";
%>
<c:import url="<%= sprintIncludeURL %>"/>