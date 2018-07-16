<%@include file="../init.jsp"%>
<%
String stepViewJsp = (String)request.getAttribute("stepViewJsp");
String stepIncludeURL = "/WEB-INF/views/structure-learning/step/" + stepViewJsp.trim() + ".jsp";
%>
<c:import url="<%= stepIncludeURL %>"/>