<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
#barGraph{
	margin:0px;
	height:600px;
	width:100%
}
.bar{
    fill: #8EB408;
  }

	.axis {
	  font: 10px sans-serif;
	}

	.axis path,
	.axis line {
	  fill: none;
	  stroke: steelblue;
	 
	}
	path {
		stroke: steelblue;
		stroke-width: 2;
		fill: none;
	}
</style>
</head>
<body>
	<div id = "barGraph"></div>
	<script type="text/javascript">
	var item={};
	var data = [];
	<%
		LinkedHashMap<String, Integer> graphData =(LinkedHashMap<String, Integer>)request.getAttribute("graphData");
		Iterator itr = graphData.entrySet().iterator();
		while(itr.hasNext()){
			Map.Entry data = (Map.Entry) itr.next(); %>
			item={};
			item["month"] = "<%=data.getKey()%>";
			item["count"] = <%=data.getValue()%>;
			data.push(item);
		<%		
			itr.remove();
		}
		%>
		var margin = {top: 20,right:20,bottom:70,left:40},
    width = 800 - margin.left - margin.right,
    height = 400 - margin.top - margin.bottom;


// set the ranges
var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

var y = d3.scale.linear().range([height, 0]);

// define the axis
var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom")


var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .ticks(10);


// add the SVG element
var svg = d3.select("#barGraph").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", 
          "translate(" + margin.left + "," + margin.top + ")");

var line = d3.svg.line()
.x(function(d) { return (x(d.month) + x.rangeBand()/2); })
.y(function(d) { return y(d.count); });

// load the data
    data.forEach(function(d) {
        d.month = d.month;
        d.count = +d.count;
  // scale the range of the data
  x.domain(data.map(function(d) { return d.month; }));
  y.domain([0, d3.max(data, function(d) { return d.count; })]);

  // add axis
  svg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis)
    .selectAll("text")
      .style("text-anchor", "end")
      .attr("dx", "-.8em")
      .attr("dy", "-.55em")
      .attr("transform", "rotate(-90)" );

  svg.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 5)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Count");

  // Add bar chart
  svg.selectAll("bar")
      .data(data)
    .enter().append("rect")
      .attr("class", "bar")
      .attr("x", function(d) { return x(d.month); })
      .attr("width", x.rangeBand())
      .attr("y", function(d) { return y(d.count); })
      .attr("height", function(d) { return height - y(d.count); });
  
  svg.append("g").append("path")
	  	.datum(data)
  		.attr("class", "line")
  		.attr("d", line);
  
});
	</script>
</body>
</html>