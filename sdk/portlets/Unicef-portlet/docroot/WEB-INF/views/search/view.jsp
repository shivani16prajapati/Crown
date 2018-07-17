<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@ include file="../init.jsp"%>

<%
 String redirect = ParamUtil.getString(request, "redirect");

WindowState windowState = renderRequest.getWindowState();

if (Validator.isNotNull(redirect)) {
	portletDisplay.setURLBack(redirect);
} 

long groupId = ParamUtil.getLong(request, "groupId",0);

String keywords = ParamUtil.getString(request, "keywords", StringPool.BLANK);
String format = ParamUtil.getString(request, "format", StringPool.BLANK);

List<String> portletTitles = new ArrayList<String>();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("keywords", keywords);
portletURL.setParameter("format", format); 

request.setAttribute("search.jsp-portletURL", portletURL);
request.setAttribute("search.jsp-returnToFullPageURL", portletDisplay.getURLBack()); 
%>
<portlet:actionURL var="searchURL">
  <portlet:param name="action" value="search"/>
</portlet:actionURL>
<portlet:actionURL var="advanceSearchURL">
  <portlet:param name="action" value="advanceSearch"/>
</portlet:actionURL>
<portlet:resourceURL var="suggestWords" id="suggestWords">
</portlet:resourceURL>
<portlet:resourceURL var="suggestTags" id="suggestTags">
</portlet:resourceURL>

<aui:form action="<%= searchURL %>" method="post" name="searchForm" onSubmit='<%="event.preventDefault();"+renderResponse.getNamespace()+"searchResult();"%>' autocomplete="off">
	<aui:input name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" type="hidden" value="<%= ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR) %>" />
	<aui:input name="format" type="hidden" value="" />

	<aui:fieldset id="searchContainer">
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" inlineField="<%= true %>" label="" name="keywords" size="30" value="${keywords}" cssClass="keywordsInput"/>
        <aui:input type="hidden" name="searchAllTxt" label="" value="" />
		<%-- <aui:input inlineField="<%= true %>" label="" name="search" src='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' title="search" type="image" />

		<aui:input inlineField="<%= true %>" label="" name="clearSearch" src='<%= themeDisplay.getPathThemeImages() + "/common/close.png" %>' 
		           title="clear-search" type="image" onClick='<%=renderResponse.getNamespace()+"clearSearchInput();"%>' />
		           
		           
		<input class="btn" type="button" name="<portlet:namespace/>advanceSearchbtn" id="<portlet:namespace/>advanceSearchbtn" 
				value="Advance Search" onclick="<portlet:namespace />generateAdvanceSearchModalBox();">
		<a class="fancybox btn btn-primary" href="#advancesearchbox-result" >Advanced Search</a> --%>
	
	</aui:fieldset>

	<div class="lfr-token-list" id="<portlet:namespace />searchTokens">
		<div class="lfr-token-list-content" id="<portlet:namespace />searchTokensContent"></div>
	</div>

	<aui:script use="liferay-token-list">
		Liferay.namespace('Search').tokenList = new Liferay.TokenList(
			{
				after: {
					close: function(event) {
						var item = event.item;

						var fieldValues = item.attr('data-fieldValues').split();

						A.Array.each(
							fieldValues,
							function(item, index, collection) {
								var values = item.split('|');

								var field = A.one('#' + values[0]);

								if (field) {
									field.val(values[1]);
								}
							}
						);

						var clearFields = A.all('#' + event.item.attr('data-clearFields').split().join(',#'));

						clearFields.remove();

						if (fieldValues.length || clearFields.size()) {
							submitForm(document.<portlet:namespace />fm);
						}
					}
				},
				boundingBox: '#<portlet:namespace />searchTokens',
				contentBox: '#<portlet:namespace />searchTokensContent'
			}
		).render();
	</aui:script>
	     <c:choose>
	         <c:when test="${not empty hits}">
				<%@ include file="search-result.jspf"%>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info"><liferay-ui:message key="no-result-found" /></div>
			</c:otherwise>
		</c:choose>
</aui:form>
<div id="advancesearchbox"></div>

<div id="advancesearchbox-result" style="display:none; width:100%; max-width:800px;">
	<%@ include file="advance_search.jspf"%>
</div>

<aui:script>
function <portlet:namespace />searchResult(){
	submitForm(document.<portlet:namespace />searchForm);
}

function <portlet:namespace />clearSearchInput(){
   AUI().one('#<portlet:namespace />keywords').val('');
}
</aui:script>
<script>
$(function() {
$("#<portlet:namespace />keywords").autocomplete({
    source: function(request, response) {
    var inputValue=$("#<portlet:namespace />keywords").val();
    inputValue = inputValue.replace(/[^a-zA-Z0-9 ]/g, "");		
    $.ajax({
    url: "<%=suggestWords.toString()%>",
    type: "POST",
    dataType: "json",
    data:{
		<portlet:namespace />searchChars:inputValue,
	},
    success: function( data ) {
             
        response( $.map( data, function( item ) {
        return {
            label: item.name,
            value: item.name,
        }
        }));
    },
    error: function (error) {
       consoloe.log('error in getting suggested words: ' + error);
    }
    });
    },
    minLength: 3
    });
});
<portlet:namespace />generateAdvanceSearchModalBox=function(){ 
	var searchmodal;
	AUI().use('aui-base','aui-modal', function(A) {
		A.one('#advancesearchbox-result').setStyle('display','block');
		searchmodal = new A.Modal(
		{
			srcNode : '#advancesearchbox-result',
			centered: true,
			draggable: false,
			resizable: false,
			headerContent:"Advance Search",
			modal: true,
			render: '#advancesearchbox',
			width: 900
		}
		);
	});
	return searchmodal;
}
</script>
<script type="text/javascript">
	$(document).ready(function() {
 		$('.fancybox').fancybox();
 	});
	</script> 
<style>
.keywordsInput{
	height: 28px !important;
	margin-top: 8px !important;
}
</style>
