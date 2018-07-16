<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import = "java.util.*" %>  

<%@include file="../init.jsp"%>

<portlet:defineObjects />
<portlet:resourceURL id = "saveRequest" var = "saveRequest"></portlet:resourceURL>
<portlet:resourceURL id = "resetRequest" var = "resetRequest"></portlet:resourceURL>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.bundle.min.js"></script>
<style>.btn{float:none}.slider{width:33% !important;}</style>

<%long structureId = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "Idea_WAR_Unicefportlet"); %>
			
		    
<script>
	var path = "<%=renderRequest.getContextPath()%>";
	var resourceURL = '${resetRequest}';
	var actionURL = '${saveRequest}';
	console.log(resourceURL);
	
	<%
		
		Map<String, Map<String, Double>> votingAverage = (Map<String, Map<String, Double>>) renderRequest
					.getAttribute("votingAverage");%>
			var votingAverage_by_criteria = [];
			<%
			for (String key : votingAverage.keySet()) { 
			  String[] ideaDetails = key.split("_@_");
			%>
			<liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=structureId %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewIdea" />
	    	   <portlet:param name="ideaId" value="<%=ideaDetails[1]%>" /> 	   
	    	</liferay-portlet:renderURL>
			
		    
				var ideaName = "<%=ideaDetails[0]%>";
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
					idea['url'] = "<%=ideaPortletURL.toString()%>";
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
			<div class="slider_name">ENDORSEMENT</div>
			<div id="TECHNICAL_MERIT"></div>
		</div>
		<div class="slider">
			<div class="slider_name">UPVOTES</div>
			<div id="TIME_TO_MARKET"></div>
		</div>
		<div class="slider">
			<div class="slider_name">COMMENTS</div>
			<div id="STRATEGIC_ALLIGNMENT"></div>
		</div>
		<div class="slider" style="display:none;">
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