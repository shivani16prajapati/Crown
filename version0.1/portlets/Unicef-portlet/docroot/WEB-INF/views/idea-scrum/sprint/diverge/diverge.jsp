<%@include file="../../init.jsp"%>

<liferay-ui:panel-container extended="<%=true%>" accordion="<%=true%>" >
	<liferay-ui:panel id="painStormingCoverge" title="start-by-emptying-your-idea" collapsible="<%=true %>" extended="<%=true%>" >
		<jsp:include page="start-by-emptying-your-idea-teacup.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="openThinking" title="open-thinking" >
		<jsp:include page="open-thinking.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="deepenOurCustomerInsight" title="deepen-our-customer-insight" >
		<jsp:include page="deepen-our-customer-insight.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="letsMapIt" title="lets-map-it" >	
		<jsp:include page="expand-the-idea.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="letsGetVisual" title="lets-get-visual" >
		<jsp:include page="lets-deconstrain-our-thinking.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="expandTheIdea" title="expand-the-idea" >
		<jsp:include page="lets-get-visual.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="letsDeConstrainOurThinking" title="lets-de-constrain-our-thinking" >
		<jsp:include page="lets-hunt-for-blindspots.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="letsHuntForBlindspots" title="lets-hunt-for-blindspots" >
		<jsp:include page="lets-map-it.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="letsReframeTheIdea" title="lets-reframe-the-idea" >
		<jsp:include page="lets-reframe-the-idea.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="whatIsTheEcosystem" title="what-is-the-ecosystem" >
		<jsp:include page="steal-and-make.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="stealAndMakeBetter" title="steal-and-make-better" >
		<jsp:include page="unleashing-our-core-creativity.jsp" />
	</liferay-ui:panel>
	<liferay-ui:panel id="unleashingOurCoreCreativity" title="unleashing-our-core-creativity" >
		<jsp:include page="what-is-the-ecosystem-here.jsp" />
	</liferay-ui:panel>
</liferay-ui:panel-container>