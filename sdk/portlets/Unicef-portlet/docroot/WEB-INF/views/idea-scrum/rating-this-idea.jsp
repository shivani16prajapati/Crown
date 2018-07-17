<%@include file="../init.jsp"%>
<%
String ratingViewJsp = (String)request.getAttribute("ratingViewJsp");
String ratingIncludeURL = "/WEB-INF/views/idea-scrum/rating-this-idea/" + ratingViewJsp.trim() + ".jsp";
%>
<c:import url="<%= ratingIncludeURL %>"/>