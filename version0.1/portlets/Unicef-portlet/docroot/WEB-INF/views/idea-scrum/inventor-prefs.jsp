<%@include file="../init.jsp"%>
<%
String inventorPrefsJSP = (String)request.getAttribute("inventorPrefsJSP");
String inventorPrefsIncludeURL = "/WEB-INF/views/idea-scrum/inventor-prefs/" + inventorPrefsJSP.trim() + ".jsp";
%>
<c:import url="<%= inventorPrefsIncludeURL %>"/>