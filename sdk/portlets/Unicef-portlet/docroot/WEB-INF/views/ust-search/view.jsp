<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@include file="../init.jsp"%>

<%
long structureId = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "Unicefsearch_WAR_Unicefportlet");
%>
<liferay-portlet:renderURL portletName="Unicefsearch_WAR_Unicefportlet"  var="searchPortletURL" plid="<%=structureId %>" varImpl="searchPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
</liferay-portlet:renderURL>

  <div class="ust-searchbox-input01"> 
    <input type="text" placeholder="SEARCH" id="<portlet:namespace/>ustThemeSearch" class="">
 </div>
 
<a href="javascript:void(0)" id="<portlet:namespace />searchTargetLink" onclick="javascript:<portlet:namespace />searchTarget('<%=searchPortletURL.toString()%>');"></a>

<script type="text/javascript">

$(document).ready(function () {
	  $("#<portlet:namespace/>ustThemeSearch").keypress(function (e) {
	         var code = (e.keyCode ? e.keyCode : e.which);
	         if (code == 13 && !e.shiftKey) {
	            $("a#<portlet:namespace />searchTargetLink").click();
	         }else{
	    return;
	   }
	     });
	});
	
<portlet:namespace />searchTarget = function(url){
	  window.location.href = url;
	}

</script>
