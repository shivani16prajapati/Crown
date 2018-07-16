<%@include file="../../init.jsp"%>

<liferay-ui:panel-container extended="<%=true%>" accordion="<%=true%>" >
	<liferay-ui:panel id="defineProblem" title="define-problem" collapsible="<%=true %>" extended="<%=true%>" >
		<jsp:include page="define-problem.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="painStoriming" title="pain-storming">
		<jsp:include page="pain-storming.jsp" />		
	</liferay-ui:panel>
	<liferay-ui:panel id="personas" title="personas">
		<jsp:include page="personas.jsp" />		
	</liferay-ui:panel>
	<liferay-ui:panel id="ethnography" title="ethnography">
		<jsp:include page="ethnography.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="stakeholderInterviewNotes" title="stakeholder-interview-notes">
		<jsp:include page="stackholder-interview-notes.jsp" />		
	</liferay-ui:panel>
	<liferay-ui:panel id="competitiveLandscape" title="competitive-landscape">
		<jsp:include page="competitive-landscape.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="insightsBlindsVisionQuest" title="insights-blindspots-vision-quest">
		<jsp:include page="insights-blindspots-vision-quest.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="successMetrics" title="success-metrics">
		<jsp:include page="success-metrics.jsp" />
	</liferay-ui:panel>
</liferay-ui:panel-container>
