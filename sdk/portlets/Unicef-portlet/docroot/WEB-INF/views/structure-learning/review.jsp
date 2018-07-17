<%@include file="../init.jsp"%>
<%
String reviewJsp  = (String)request.getAttribute("reviewJsp");
String theSolutionIncludeURL = "/WEB-INF/views/structure-learning/review/" + reviewJsp .trim() + ".jsp";
%>
<c:import url="<%= theSolutionIncludeURL %>"/>