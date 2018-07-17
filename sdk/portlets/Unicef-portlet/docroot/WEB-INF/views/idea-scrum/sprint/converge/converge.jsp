<%@include file="../../init.jsp"%>

<liferay-ui:panel-container extended="<%=true%>" accordion="<%=true%>" >
	<liferay-ui:panel id="painStormingCoverge" title="pain-storming" collapsible="<%=true %>" extended="<%=true%>" >
		<jsp:include page="costs-and-hurdles.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="solution" title="solution">
		<jsp:include page="customers-and-channels.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="foundational" title="foundational">
		<jsp:include page="find-the-spine.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="uniqueValueProposition" title="unique-value-proposition">
		<jsp:include page="first-five-steps.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="unfairAdvantage" title="unfair-advantage">
		<jsp:include page="foundational.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="customerAndChannel" title="customers-and-channels">
		<jsp:include page="PAINstorming.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="revenueStreams" title="revenue-streams">
		<jsp:include page="revenue-stream.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="costsAndHurdles" title="costs-and-hurdles">
		<jsp:include page="solution.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="firstFiveSteps" title="first-five-steps">
		<jsp:include page="unfair-advantage.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="findTheSpine" title="find-the-spine">
		<jsp:include page="unique-value-proposition.jsp" />
	</liferay-ui:panel>
</liferay-ui:panel-container>	

