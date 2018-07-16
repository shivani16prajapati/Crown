<%@include file="../init.jsp"%>
<%
String sprintViewJsp = (String)request.getAttribute("sprintViewJsp");
String sprintIncludeURL = "/WEB-INF/views/idea-scrum/sprint/" + sprintViewJsp.trim() + ".jsp";
%>
<c:import url="<%= sprintIncludeURL %>"/>