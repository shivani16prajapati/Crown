<%@include file="../../init.jsp"%>

<liferay-ui:panel-container extended="<%=true%>" accordion="<%=true%>" >
	<liferay-ui:panel id="defineTheMVP" title="define-the-mvp" collapsible="<%=true %>" extended="<%=true%>" >
		<jsp:include page="define-the-mvp.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="userTestingScript" title="user-testing-script">
		<jsp:include page="find-a-tool.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="findTool" title="find-a-tool">
		<jsp:include page="goals.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="timeboxing" title="timeboxing">
		<jsp:include page="timeboxing.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="goals" title="goals">
		<jsp:include page="user-testing-script.jsp" />
	</liferay-ui:panel>
</liferay-ui:panel-container>
