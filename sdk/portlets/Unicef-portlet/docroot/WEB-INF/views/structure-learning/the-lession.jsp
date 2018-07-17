<%@include file="../init.jsp"%>
<%
String theLessionViewJsp  = (String)request.getAttribute("theLessionViewJsp ");
String theSolutionIncludeURL = "/WEB-INF/views/structure-learning/the-lession/" + theLessionViewJsp .trim() + ".jsp";
%>
<c:import url="<%= theSolutionIncludeURL %>"/>