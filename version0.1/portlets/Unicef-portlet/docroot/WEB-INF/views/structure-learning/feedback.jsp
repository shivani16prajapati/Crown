<%@include file="../init.jsp"%>
<%
String feedBackViewJsp  = (String)request.getAttribute("feedBackViewJsp");
String theFeedBackIncludeURL = "/WEB-INF/views/structure-learning/feedback/" + feedBackViewJsp .trim() + ".jsp";
%>
<c:import url="<%= theFeedBackIncludeURL %>"/>

