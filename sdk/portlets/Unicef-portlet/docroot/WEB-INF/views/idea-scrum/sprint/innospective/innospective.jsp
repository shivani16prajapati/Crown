<%@include file="../../init.jsp"%>

<liferay-ui:panel-container extended="<%=true%>" accordion="<%=true%>" >
	<liferay-ui:panel id="threeWords" title="three-words" collapsible="<%=true %>" extended="<%=true%>" >
		<jsp:include page="happy-about.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="happyAbout" title="happy-about">
		<jsp:include page="ideas-for-improving-agile-innovation.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="needToImprove" title="need-to-improve">
		<jsp:include page="ideas-for-improving-this-process.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="ideaForImprovingProcess" title="idea-for-improving-process">
		<jsp:include page="need-to-improve.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="ideaForImprovingAgile" title="idea-for-improving-agile">
		<jsp:include page="three-words.jsp" />
	</liferay-ui:panel>
</liferay-ui:panel-container>		