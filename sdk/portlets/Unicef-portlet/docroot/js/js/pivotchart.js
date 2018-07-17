var imgPath;
var canvas;
var Technical_Merit = 2.5;
var Time_To_Market = 2.5;
var Strategic_Alignment = 2.5;
var Market_Opportunity = 2.5;


var risk_a = 5;
var utility_b = 4;
/*var Uniqueness = 0.25;
var Low_Technical_Risk = 0.25;
var Development_Cost = 0.25;
var Patentability = 0.25;
var Competitive_Durability = 0.25;
var Innovation_Accelerator = 0.25;*/
var Technical_Merit_weight;
var Time_To_Market_weight;
var Strategic_Alignment_weight;
var Market_Opportunity_weight;
var Market_Opportunity_riskweight;
var Time_To_Market_riskweight;
var Technical_Merit_vote;
var Time_To_Market_vote;
var Strategic_Alignment_vote;
var Market_Opportunity_vote;
var utility;
var risk;
var graphData=[];
var initGraphData = [];
var isInitCalculated=0;
calculateWeight();
calculateUtility();
drawGraph();
var tooltip_bottom1 = $('<div class="tooltip_bottom1" />');
var tooltip_bottom2 = $('<div class="tooltip_bottom2" />');
var tooltip_bottom3 = $('<div class="tooltip_bottom3" />');
var tooltip_bottom4 = $('<div class="tooltip_bottom4" />');	

if (typeof path !== undefined) {
	imgPath = path + "/docroot/images";
}
/*var toggle = 0;
$('#dropdown1').click(function() {
	$('.slider_container1').toggle();
	if (toggle == 0) {
		toggle = 1;
		$('#dropdown1').attr("src", imgPath + "/hide.png");
	} else {
		toggle = 0;
		$('#dropdown1').attr("src", imgPath + "/show.png");
	}
});*/
$('#default-btn').click(function() {

	Technical_Merit = 2.5;
	Strategic_Alignment = 2.5;
	Time_To_Market = 2.5;
	Market_Opportunity = 2.5;
	risk_a = 5;
	utility_b = 4;

	/*Uniqueness = 0.25;
	Low_Technical_Risk = 0.25;
	Development_Cost = 0.25;
	Patentability = 0.25;
	Competitive_Durability = 0.25;
	Innovation_Accelerator = 0.25;*/
	changeSliderValue();
	
});
$('#reset-btn').click(function() {
	$.ajax({
        url: resourceURL ,
        type: 'GET',
        success: function(data){
        	data = JSON.parse(data);
        	Technical_Merit = data['Technical_Merit'];
        	Time_To_Market = data['Time_To_Market'];
        	Strategic_Alignment = data['Strategic_Alignment'];
        	Market_Opportunity = data['Market_Opportunity'];
        	risk_a = data['Risk_Slider'];
        	utility_b = data['Utility_Slider'];
        	calculateWeight();
        	calculateUtility();
        	drawGraph();
        	changeSliderValue();
        	alert("Reset completed");
        },
        error: function(data){
        	alert('Reset not completed');
        }
    });
});
$('#save-btn').click(function() {
	 
	 $.ajax({
         url: actionURL ,
         type: 'GET',
         data:{
        	"Technical_Merit":Technical_Merit,
        	"Time_To_Market":Time_To_Market,
        	"Strategic_Alignment":Strategic_Alignment,
        	"Market_Opportunity":Market_Opportunity,
        	"Risk_Slider":risk_a,
	 		"Utility_Slider":utility_b
         },
         success: function(data){
        	 alert("Successfully saved");
         },
         error: function(data){
        	 alert("Not saved successfully");
         }
     });
});
$(function() {
	$("#TECHNICAL_MERIT").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				value : Technical_Merit,
				slide: function(event, ui) {
					Technical_Merit = ui.value;
					calculateWeight();
                    calculateUtility();
                    drawGraph();
		    	    $(ui.handle).find('.tooltip_bottom1').text(ui.value);
					$(event.target).find('.ui-slider-handle').append(tooltip_bottom1);
					$(tooltip_bottom1).show();
		    	},
				stop: function(event, ui){
					$(tooltip_bottom1).hide();
				},
		    	
				change : function(event, ui) {
					$(ui.handle).find('.tooltip_bottom1').text(ui.value);
					Technical_Merit = ui.value;
					//changeSliderValue();
                    calculateWeight();
                    calculateUtility();
                    drawGraph();
				}

			}).on("mouseenter",function(){
				$(tooltip_bottom1).show();
			}).on("mouseleave",function(){
				$(tooltip_bottom1).hide();
			});
	$("#TIME_TO_MARKET").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				value : Time_To_Market,
				slide: function(event, ui) {
					Strategic_Alignment = ui.value;
					calculateWeight();
                    calculateUtility();
                    drawGraph();
		    	    $(ui.handle).find('.tooltip_bottom2').text(ui.value);
					$(event.target).find('.ui-slider-handle').append(tooltip_bottom2);
					$(tooltip_bottom2).show();
		    	},
				stop: function(event, ui){
					$(tooltip_bottom2).hide();
				
				},
		    	
				change : function(event, ui) {
					$(ui.handle).find('.tooltip_bottom2').text(ui.value);
					Strategic_Alignment = ui.value;
					//changeSliderValue();
                    calculateWeight();
                    calculateUtility();
                    
                    drawGraph();
					}
			}).on("mouseenter",function(){
				$(tooltip_bottom2).show();
			}).on("mouseleave",function(){
				$(tooltip_bottom2).hide();
			});
	$("#STRATEGIC_ALLIGNMENT").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				value : Strategic_Alignment,
				slide: function(event, ui) {
					Time_To_Market = ui.value;
					calculateWeight();
                    calculateUtility();
                    drawGraph();
		    	    $(ui.handle).find('.tooltip_bottom3').text(ui.value);
					$(event.target).find('.ui-slider-handle').append(tooltip_bottom3);
					$(tooltip_bottom3).show();
		    	},
				stop: function(event, ui){
					$(tooltip_bottom3).hide();
				
				},
		    	
				change : function(event, ui) {
					$(ui.handle).find('.tooltip_bottom3').text(ui.value);
					Time_To_Market = ui.value;
					//changeSliderValue();
					calculateWeight();
                    calculateUtility();
                    
                    drawGraph();
                    }
			}).on("mouseenter",function(){
				$(tooltip_bottom3).show();
			}).on("mouseleave",function(){
				$(tooltip_bottom3).hide();
			});
	$("#MARKET_OPPORTUNITY").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				slide: function(event, ui) {
					Strategic_Alignment = ui.value;
					calculateWeight();
                    calculateUtility();
                    drawGraph();
		    	    $(ui.handle).find('.tooltip_bottom4').text(ui.value);
					$(event.target).find('.ui-slider-handle').append(tooltip_bottom4);
					$(tooltip_bottom4).show();
		    	},
		    	
				stop: function(event, ui){
					$(tooltip_bottom4).hide();
				
				},
				value : Strategic_Alignment,
				change : function(event, ui) {
					$(ui.handle).find('.tooltip_bottom4').text(ui.value);
					Strategic_Alignment = ui.value;
				    calculateWeight();
                    calculateUtility();
                    
                    drawGraph();
                    }
			}).on("mouseenter",function(){
				$(tooltip_bottom4).show();
			}).on("mouseleave",function(){
				$(tooltip_bottom4).hide();
			});
	/*$("#UNIQUENESS").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				value : Strategic_Alignment,
				change : function(event, ui4) {
					Time_To_Market = ui4.value;
					calculateWeight(Technical_Merit, Time_To_Market,
							Strategic_Alignment, Market_Opportunity);
				}
			});
	$("#LOW_TECHNICAL_RISK").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				value : Strategic_Alignment,
				change : function(event, ui4) {
					Time_To_Market = ui4.value;
					calculateWeight(Technical_Merit, Time_To_Market,
							Strategic_Alignment, Market_Opportunity);
				}
			});
	$("#DEVELOPMENT_COST").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				value : Strategic_Alignment,
				change : function(event, ui4) {
					Time_To_Market = ui4.value;
					calculateWeight(Technical_Merit, Time_To_Market,
							Strategic_Alignment, Market_Opportunity);
				}
			});
	$("#PATENTABILITY").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				value : Strategic_Alignment,
				change : function(event, ui4) {
					Time_To_Market = ui4.value;
					calculateWeight(Technical_Merit, Time_To_Market,
							Strategic_Alignment, Market_Opportunity);
				}
			});
	$("#COMPETITTIVE_DURABILITY").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				value : Strategic_Alignment,
				change : function(event, ui4) {
					Time_To_Market = ui4.value;
					calculateWeight(Technical_Merit, Time_To_Market,
							Strategic_Alignment, Market_Opportunity);
				}
			});
	$("#INNOVATION_ACCELERATOR").slider(
			{
				range : "min",
				min : 0,
				max : 10,
				step : 0.01,
				value : Strategic_Alignment,
				change : function(event, ui4) {
					Time_To_Market = ui4.value;
					calculateWeight(Technical_Merit, Time_To_Market,
							Strategic_Alignment, Market_Opportunity);
				}
			});*/
    $( "#spiderChart" ).dialog({
         autoOpen: false,
      resizable: false,
      height: 400,
      width: 800,
      modal: true
    });
});

function changeSliderValue(){
	$("#TECHNICAL_MERIT").slider("option","value",Technical_Merit);
	$("#TIME_TO_MARKET").slider("option","value",Time_To_Market);
	$("#STRATEGIC_ALLIGNMENT").slider("option","value",Strategic_Alignment);
	$("#MARKET_OPPORTUNITY").slider("option","value",Market_Opportunity);
}

function drawGraph(){
	var w = 900;
	var h = 500;
	var isInsideCurve = 0;
    $("#wrapper_graph").html("");
    
	var ideaUtility=[];
	var ideaName=[];
	
	graphData.sort(function(a,b){
		return (b.IdeaUtility - a.IdeaUtility);
	});
	initGraphData.sort(function(a,b){
		return (b.Init_Idea_Utility - a.Init_Idea_Utility);
	});
	graphData.forEach(function(d){
		ideaUtility.push(d.IdeaUtility);
		ideaName.push(d.IdeaName);
	});
	var max = d3.max(d3.values(graphData),function(d){
		return d.IdeaRisk;
	});
	var maxDomain = (parseFloat(max)+parseFloat(2)).toFixed(2);
	var xScale = d3.scale.linear().domain([0,10]).range([0,w]);
	var yScale = d3.scale.linear().domain([0,10]).range([h,0]);
	var svg = d3.select("#wrapper_graph").append("svg").attr("width",w).attr("height",h);
	var xAxis_bottom = d3.svg.axis().scale(xScale).orient("bottom");
	var yAxis_left = d3.svg.axis().scale(yScale).orient("left");
	var xAxis_top = d3.svg.axis().scale(xScale).orient("top");
	var yAxis_right = d3.svg.axis().scale(yScale).orient("right");
	svg.append("g").attr("id","xaxis")
					.attr("transform","translate(0,"+h+")").call(xAxis_bottom);
	svg.append("g").attr("id","yaxis").call(yAxis_left);
	svg.append("g").attr("id","xaxis").call(xAxis_top);
	svg.append("g").attr("id","yaxis").attr("transform","translate("+w+",0)").call(yAxis_right);
	
    drawCurve(svg,xScale,yScale);
    drawLever(svg,xScale,yScale);
	
    d3.selectAll("line").remove();
	
    var Label_Derisked = svg.append("text")
        .attr("x",xScale(9))
        .attr("y",yScale(0.25))
        .attr("font-family","serif")
        .attr("font-size","20px")
        .text("De-Risk");
    
    var Label_Utility = svg.append("text")
        .attr("x",xScale(0.25))
        .attr("y",yScale(9.5))
        .attr("font-family","serif")
        .attr("font-size","20px")
        .text("Utility");
    
	var lines = svg.selectAll("line")
	.data(graphData)
	.enter()
	.append("line")
	.attr("x1",function(d,i){
		return xScale(initGraphData[i].Init_Idea_Risk);
		})
	.attr("y1",function(d,i){
		return yScale(initGraphData[i].Init_Idea_Utility);
	})
	.attr("x2",function(d){
		return xScale(d.IdeaRisk);
		})
	.attr("y2",function(d){
		return yScale(d.IdeaUtility);
	})
	.attr("stroke-width", 10)
    .attr("stroke",function(d,j){
						
                        for(var i=risk_a+0.01;i<=10;i = i+0.01){
                            if((initGraphData[j].Init_Idea_Risk >= i && initGraphData[j].Init_Idea_Utility
                                        >= calculateY(i)))    
                                isInsideCurve = 1;
		                          
	                    }
                        if(isInsideCurve == 1){
                            isInsideCurve = 0;
                            return "#73D1E6";
                        }
                        else{
							     return "#EA4F1E";    
                        }
                        
					});
	
	var circles = svg.selectAll("circle")
					.data(graphData)
					.enter()
					.append("circle")
					.attr("cx",function(d){
						return xScale(d.IdeaRisk);
                        
					})
					.attr("cy",function(d){
						return yScale(d.IdeaUtility);
                        
					})
					.attr("r",8)
					.style("fill",function(d,j){
						
                        for(var i=risk_a+0.01;i<=10;i = i+0.01){
                            if((initGraphData[j].Init_Idea_Risk >= i && initGraphData[j].Init_Idea_Utility
                                        >= calculateY(i)))    
                                isInsideCurve = 1;
		                          
	                    }
                        if(isInsideCurve == 1){
                            isInsideCurve = 0;
                            return "#02ACED";
                        }
                        else{
							     return "#F5A693";    
                        }
                        
					}).on("click",function(d,i){
                        drawSpider(graphData[i].IdeaName);
                    });
		
	
}
function drawLever(svg,xScale,yScale){
	var drag_risk = d3.behavior.drag().on("drag",function(){
		var temp_x = d3.event.x;
        risk_a = xScale.invert(temp_x);
        drawGraph();
	});
	var drag_utility = d3.behavior.drag().on("drag",function(){
        var temp_y = d3.event.y;
        utility_b = yScale.invert(temp_y);
        drawGraph();
	});
	var widgetRisk = svg.append("rect")
					.attr("x",xScale(risk_a))
					.attr("y",yScale(10))
					.attr("width",10)
					.attr("height",10)
					.attr("fill","#8BC43F")
					.call(drag_risk);
    
	var widgetUtility = svg.append("rect")
					.attr("x",(xScale(10))-10)
					.attr("y",yScale(utility_b))
					.attr("width",10)
					.attr("height",10)
					.attr("fill","#8BC43F")
					.call(drag_utility);
	}
    function calculateY(temp_risk){
        var b2 = Math.pow(10-utility_b,2);
			var a2 = Math.pow(10-risk_a,2);
			var x2 = Math.pow(10-temp_risk,2);
			var cal = (b2*(a2-x2))/a2;
			var ans = 10-((Math.sqrt((b2*(a2-x2))/a2)).toFixed(2));
			return ans;
    }
function drawCurve(svg,xScale,yScale){
	var startPath = "M "+xScale(risk_a)+" "+yScale(10);" L "+xScale(10) +" "+yScale(utility_b)
	var endPath = " L "+ xScale(10) +" "+yScale(10)+" L "+ xScale(risk_a) +" "+yScale(10);
	var path = startPath;
	
	for(var i=risk_a+0.01;i<=10;i = i+0.01){
		path = path + " L "+xScale(i) +" "+yScale(calculateY(i));
	}
	path = path + endPath;
	
	svg.append("path").attr("d",path).attr("fill","#E9F3DA");
    				
}

function calculateWeight() {
	Technical_Merit_weight = Technical_Merit
			/ (Technical_Merit + Time_To_Market + Strategic_Alignment + Market_Opportunity);
	Time_To_Market_weight = Time_To_Market
			/ (Technical_Merit + Time_To_Market + Strategic_Alignment + Market_Opportunity);
	Strategic_Alignment_weight = Strategic_Alignment
			/ (Technical_Merit + Time_To_Market + Strategic_Alignment + Market_Opportunity);
	Market_Opportunity_weight = Market_Opportunity
			/ (Technical_Merit + Time_To_Market + Strategic_Alignment + Market_Opportunity);
	Market_Opportunity_riskweight = Market_Opportunity_weight/(Market_Opportunity_weight+Time_To_Market_weight);
	Time_To_Market_riskweight = Time_To_Market_weight/(Market_Opportunity_weight+Time_To_Market_weight);
}

function calculateUtility(){
    var itm = {};
	graphData = [];
    for(var i=0;i<votingAverage_by_criteria.length;i++){
        itm = {};
        for(var j=0;j<votingAverage_by_criteria[i].data.length;j++){
            var temp_criteria_name = votingAverage_by_criteria[i].data[j].CriteriaName;
            var temp_votingAverage = votingAverage_by_criteria[i].data[j].VotingAverage;
            if(temp_criteria_name == "Technical Merit"){
                Technical_Merit_vote = temp_votingAverage;
            }
            if(temp_criteria_name == "Market Potential"){
                Market_Opportunity_vote = temp_votingAverage;
            }
            if(temp_criteria_name == "Strategic Alignment"){
                Strategic_Alignment_vote = temp_votingAverage;
            }
            if(temp_criteria_name == "Development Time"){
                Time_To_Market_vote = temp_votingAverage;
            }
            
        }
        utility = (Technical_Merit_weight*Technical_Merit_vote)+(Market_Opportunity_weight*Market_Opportunity_weight)+(Strategic_Alignment_weight*Strategic_Alignment_vote)+(Time_To_Market_weight*Time_To_Market_vote);
        risk = (Market_Opportunity_riskweight*Market_Opportunity_vote)+(Time_To_Market_riskweight*Time_To_Market_vote);
        itm['IdeaName'] = votingAverage_by_criteria[i].Name;
        itm['IdeaUtility'] = utility.toFixed(2);
        itm['IdeaRisk'] = risk.toFixed(2);
        graphData.push(itm);
        if(!isInitCalculated){
        	itm = {};
        	itm['IdeaName'] = votingAverage_by_criteria[i].Name;
        	itm['Init_Idea_Utility'] = utility.toFixed(2);
        	itm['Init_Idea_Risk'] = risk.toFixed(2);
        	initGraphData.push(itm);
        }
    }
    isInitCalculated = 1;
}

function drawSpider(barName){
 $('#spiderChart').dialog('open');
  // $('#spiderChart').show();
var spiderLabel = [];
var spiderData = [];
var ctx = document.getElementById("spiderChart");
    for(var i=0;i<votingAverage_by_criteria.length;i++){
        if(votingAverage_by_criteria[i].Name==barName){
            for(var j=0;j<votingAverage_by_criteria[i].data.length;j++){
                var temp_criteria_name = votingAverage_by_criteria[i].data[j].CriteriaName;
                if(temp_criteria_name == "Technical Merit"){
                    spiderLabel.push(votingAverage_by_criteria[i].data[j].CriteriaName); 
                    spiderData.push(votingAverage_by_criteria[i].data[j].VotingAverage);
                }
                if(temp_criteria_name == "Market Potential"){
                    spiderLabel.push(votingAverage_by_criteria[i].data[j].CriteriaName); 
                    spiderData.push(votingAverage_by_criteria[i].data[j].VotingAverage);
                }
                if(temp_criteria_name == "Strategic Alignment"){
                    spiderLabel.push(votingAverage_by_criteria[i].data[j].CriteriaName); 
                    spiderData.push(votingAverage_by_criteria[i].data[j].VotingAverage);
                }
                if(temp_criteria_name == "Development Time"){
                    spiderLabel.push(votingAverage_by_criteria[i].data[j].CriteriaName); 
                    spiderData.push(votingAverage_by_criteria[i].data[j].VotingAverage);
                }
                
                
            }
        }
    }
            var data = {
    labels: spiderLabel,
    datasets: [
        {
            label: barName,
            backgroundColor: "rgba(38,151,217,0.2)",
            borderWidth:1,
            pointRadius:1,
            borderColor: "rgba(205,226,242,9)",
            pointBackgroundColor: "rgba(205,226,242,1)",
            pointBorderWidth:1,
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(205,226,242,1)",
            data: spiderData
        }
    ]
};
var myRadarChart = new Chart(ctx, {
    type: 'radar',
    data: data,
   options: {
            scale: {
                reverse: false,
                ticks: {
                    display:false,
                    beginAtZero: true,
                    min:0,
                    max:10,
                    stepSize:2
                }
            },
    legend:{
       display:false
   }
   }
});
}