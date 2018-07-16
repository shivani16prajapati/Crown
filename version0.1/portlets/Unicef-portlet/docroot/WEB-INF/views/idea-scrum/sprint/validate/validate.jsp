<%@include file="../../init.jsp"%>

<liferay-ui:panel-container extended="<%=true%>" accordion="<%=true%>" >
	<liferay-ui:panel id="interviewFindings" title="interview-findings" collapsible="<%=true %>" extended="<%=true%>" >
		<jsp:include page="everything-else.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="painPoints" title="pain-points">
		<jsp:include page="happily-ever-after.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="solutionPoints" title="solution-points">
		<jsp:include page="how-you-get-there.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="theBigVision" title="the-big-vision">
		<jsp:include page="interview-findings.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="howYouGetThere" title="how-you-get-there">
		<jsp:include page="meet-cute.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="happilyEverAfter" title="happily-ever-after">
		<jsp:include page="pain-points.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="meetCute" title="meet-cute">
		<jsp:include page="remember.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="everythingElse" title="everything-else">
		<jsp:include page="solution-points.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="theThroughLine" title="the-throughline">
		<jsp:include page="the-big-vision.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="remember" title="remember">
		<jsp:include page="the-throughline.jsp" />
	</liferay-ui:panel>
</liferay-ui:panel-container>
