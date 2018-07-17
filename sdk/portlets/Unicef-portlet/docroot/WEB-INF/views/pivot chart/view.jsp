<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import = "java.util.*" %>  
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects />
<portlet:actionURL name = "saveRequest" var = "saveRequest"></portlet:actionURL>
<portlet:resourceURL id = "resetRequest" var = "resetRequest"></portlet:resourceURL>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.bundle.min.js"></script>
<script>
	var path = "<%=renderRequest.getContextPath()%>";
	var resourceURL = '${resetRequest}';
	var actionURL = '${saveRequest}';
	<%
		Map<String, Map<String, Double>> votingAverage = (Map<String, Map<String, Double>>) renderRequest
					.getAttribute("votingAverage");%>
			var votingAverage_by_criteria = [];
			<%
			for (String key : votingAverage.keySet()) {%>
				var ideaName = "<%=key%>";
				var data = [];
				var idea = {};
				idea['Name'] = ideaName;
				<%
					Map<String, Double> temp = votingAverage.get(key);
					for (String key1 : temp.keySet()) {
						%>
							var criteria_Name = "<%=key1%>";
							var voting_average = <%=temp.get(key1)%>;
							var criteria_votingAverage = {};
							criteria_votingAverage['CriteriaName']=criteria_Name;
							criteria_votingAverage['VotingAverage']=voting_average;
							data.push(criteria_votingAverage);
						<%
					}%>
					idea['data'] = data;
					votingAverage_by_criteria.push(idea);
			<%}
	%>
</script>

<body>

	<div class="Matrix_Header">Top Ideas</div>

	<div id="wrapper_graph"></div>
	<div id="button_container">
		<button type="button" class="btn" id="default-btn">OFFICIAL STRATEGY</button>
		<button type="button" class="btn" id="reset-btn">RESET</button>
		<button type="button" class="btn" id="save-btn">SAVE</button>
	</div>
	<div class="slider_container">
		<div class="slider">
			<div class="slider_name">TECHNICAL MERIT</div>
			<div id="TECHNICAL_MERIT"></div>
		</div>
		<div class="slider">
			<div class="slider_name">TIME TO MARKET</div>
			<div id="TIME_TO_MARKET"></div>
		</div>
		<div class="slider">
			<div class="slider_name">STRATEGIC ALIGNMENT</div>
			<div id="STRATEGIC_ALLIGNMENT"></div>
		</div>
		<div class="slider">
			<div class="slider_name">MARKET OPPORTUNITY</div>
			<div id="MARKET_OPPORTUNITY"></div>
		</div>
		<div style="clear: both"></div>
	</div>
	<canvas id="spiderChart" style="background:#FFF"></canvas>
	<!--  <div class="dropdown1">
		<img id="dropdown1" src="images/show.png">
	</div>
	<div class="slider_container1" style="display: none">
		<div class="slider">
			<div class="slider_name">UNIQUENESS</div>
			<div id="UNIQUENESS"></div>
		</div>
		<div class="slider">
			<div class="slider_name">LOW TECHNICAL RISK</div>
			<div id="LOW_TECHNICAL_RISK"></div>
		</div>
		<div class="slider">
			<div class="slider_name">DEVELOPMENT COST</div>
			<div id="DEVELOPMENT_COST"></div>
		</div>
		<div class="slider">
			<div class="slider_name">PATENTABILITY</div>
			<div id="PATENTABILITY"></div>
		</div>
		<div class="slider">
			<div class="slider_name">COMPETITTIVE DURABILITY</div>
			<div id="COMPETITTIVE_DURABILITY"></div>
		</div>
		<div class="slider">
			<div class="slider_name">INNOVATION ACCELERATOR</div>
			<div id="INNOVATION_ACCELERATOR"></div>
		</div>
	</div>-->
</body>
</html>