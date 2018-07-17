<%@ include file="../init.jsp"%>
<!-- Search Bar Start-->
<portlet:resourceURL var="suggestWords" id="suggestWords">
</portlet:resourceURL>
<portlet:actionURL var="searchActionURL">
   <portlet:param name="action" value="search"/>
</portlet:actionURL>

<div class="searchAllWrapper">
<aui:form action="<%=searchActionURL.toString() %>" autocomplete="off" name="searchForm">
	<aui:fieldset id="searchContainer">
		<aui:input name="searchAll" label="" placeholder="What can we help you find?" />
		<aui:input inlineField="<%= true %>" label="" name="search" src='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' 
		   title="search" type="image" onclick="searchAllPage();" />
	</aui:fieldset>
</aui:form>
</div>

<aui:script>
function searchFormSumit(form){
	searchAllPage();
	return false;
}
function searchAllPage(){
	submitForm(document.<portlet:namespace />searchForm);
}
</aui:script>