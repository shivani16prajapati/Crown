<%@include file="../init.jsp"%>
<%
String theIdeaViewJsp = (String)request.getAttribute("theIdeaViewJsp");
String theIdeaIncludeURL = "/WEB-INF/views/idea-scrum/the-idea/" + theIdeaViewJsp.trim() + ".jsp";
%>
<c:import url="<%= theIdeaIncludeURL %>"/>
