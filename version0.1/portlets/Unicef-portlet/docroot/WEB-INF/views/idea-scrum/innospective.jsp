<%@include file="../init.jsp"%>
<%
String innoViewJsp = (String)request.getAttribute("innoViewJsp");
String ratingIncludeURL = "/WEB-INF/views/idea-scrum/innospective/" + innoViewJsp.trim() + ".jsp";
%>
<c:import url="<%= ratingIncludeURL %>"/>