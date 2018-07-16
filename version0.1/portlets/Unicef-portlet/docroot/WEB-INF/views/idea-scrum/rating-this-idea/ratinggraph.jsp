<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@include file="../../init.jsp"%>
<portlet:renderURL var="BackToViewURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>">
	<portlet:param name="tab" value="rating-this-idea"/>
</portlet:renderURL>

<script type="text/javascript">
$(function () {
	var data= '<%=request.getAttribute("data")%>';
	data = JSON.parse(data);
	var labels = new Array();
	var series = new Array();;
	for(key in data.avergerating){
		if (data.avergerating.hasOwnProperty(key)) {
			if(data.avergerating[key].rateCriteriaName != null){
				labels.name = data.avergerating[key].rateCriteriaName;
				labels.push(labels.name);
				var valueArray= new Array();
				valueArray.push(Math.round((data.avergerating[key].averageValue - data.avergerating[key].standardDeviation) * 100) / 100);
				valueArray.push(Math.round((+data.avergerating[key].averageValue + +data.avergerating[key].standardDeviation) * 100) / 100); 
				series.push(valueArray);
				}
			}
	}
	$('#container').highcharts({
	    chart: {
	        type: 'columnrange',
	        inverted: true
	    },
	    title: {
	        text: 'Idea Rating'
	    },
	    credits: {
			enabled: false
		},
	    xAxis: {
	        categories: labels
	    },
	    yAxis: {
	        title: {
	            text: 'Average'
	        }
	    },
	    tooltip: {
	        valueSuffix: ''
	    },
	    plotOptions: {
	        columnrange: {
	        	dataLabels: {
	        		enabled: true,
	        		formatter: function () {
	        			return this.y + '';
	        		}
	        	}
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    series: [{
	        name: 'Rates',
	        data: series
	    }]
	});
});
</script>

<a class="btn btn-primary" href="${BackToViewURL}">Back</a>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>