<%@include file="../init.jsp"%>
<%
String theSolutionViewJsp = (String)request.getAttribute("theSolutionViewJsp");
String theSolutionIncludeURL = "/WEB-INF/views/solution-scrum/the-solution/" + theSolutionViewJsp.trim() + ".jsp";
%>
<c:import url="<%= theSolutionIncludeURL %>"/>