var imgPath;
var canvas;
var Technical_Merit = 2.5;
var Time_To_Market = 2.5;
var Strategic_Alignment = 2.5;
var Market_Opportunity = 2.5;

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
var Technical_Merit_vote;
var Time_To_Market_vote;
var Strategic_Alignment_vote;
var Market_Opportunity_vote;
var utility;
var graphData=[];
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
        	calculateWeight();
        	calculateUtility();
        	drawGraph();
        	changeSliderValue();
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
        	"Market_Opportunity":Market_Opportunity
         },
         success: function(data){
        	 alert("Successfully saved");
         }
     });
});

$(function() {
	
	$("#TECHNICAL_MERIT").slider(
			{				 
				range : "min",
				min : 0,
				max : 2,
				step : 1,
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
				step : 1,
				value : Time_To_Market,
				slide: function(event, ui) {
					Time_To_Market = ui.value;
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
					Time_To_Market = ui.value;
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
				step : 1,
				value : Strategic_Alignment,
				slide: function(event, ui) {
					Strategic_Alignment = ui.value;
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
					Strategic_Alignment = ui.value;
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
					Market_Opportunity = ui.value;
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
					Market_Opportunity = ui.value;
					//changeSliderValue();
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
	var barWidth;
    $("#wrapper_graph").html("");    
	var ideaUtility=[];
	var ideaName=[];
	var IdeaUrl=[]
	
	graphData.sort(function(a,b){
		return (b.IdeaUtility - a.IdeaUtility);
	});
	graphData.forEach(function(d){
		if(ideaUtility.length <5){
			ideaUtility.push(d.IdeaUtility);
			ideaName.push(d.IdeaName);
			IdeaUrl.push(d.IdeaUrl);
			
		}	
	});
	if(ideaUtility.length<=5)
    	barWidth = 30;
    else
    	barWidth = 19;	
var j = -1;
ideaName.unshift('');

var colors = [ '#EFC53D', '#A1D164', '#33BCF0', '#A7A8AC', '#468EB4', '#33B674' ];

var xscale = d3.scale.linear().domain([ 0, 2 ]).range([ 0, 722 ]);

var yscale = d3.scale.linear().domain([ 0, ideaName.length ]).range([ 0, 500 ]);

canvas = d3.select('#wrapper_graph').append('svg').attr({
	'width' : 700,
	'height' : 550
});

var xAxis = d3.svg.axis();
xAxis.orient('bottom').scale(xscale).tickValues(d3.range(11));

var xAxis1 = d3.svg.axis();
xAxis1.orient('top').scale(xscale).tickSize(0).tickFormat(function(d, i) {
	return '';
});

var yAxis1 = d3.svg.axis();
yAxis1.orient('right').scale(yscale).tickSize(0).tickFormat(function(d, i) {
	return '';
});

var yAxis = d3.svg.axis();
yAxis.orient('left').scale(yscale).tickSize(0).tickFormat(function(d, i) {
	if (i != 0)
		return i;
}).tickValues(d3.range(ideaName.length));

var y_xis = canvas.append('g').attr("transform", "translate(100,0)").attr('id',
		'yaxis').call(yAxis);

var x_xis = canvas.append('g').attr("transform", "translate(100,500)").attr(
		'id', 'xaxis').call(xAxis);

var y_xis_upper = canvas.append('g').attr("transform", "translate(822,0)")
		.attr('id', 'yaxis').call(yAxis1);

var x_xis = canvas.append('g').attr("transform", "translate(100,0)").attr('id',
		'xaxis').call(xAxis1);

var chart = canvas.append('g').attr("transform", "translate(100,0)").attr('id',
		'bars').selectAll('rect').data(ideaUtility).enter().append('rect').attr(
		'height',barWidth).attr({
	'x' : 0,
	'y' : function(d, i) {
		return yscale(i+1);
	}
}).style('fill',function(d){
    if(Market_Opportunity_weight<=2.5)
        return '#68A3D8';
    else if(Market_Opportunity_weight>2.5 && Market_Opportunity_weight<=5)
        return '#4C93C9';
    else if(Market_Opportunity_weight>5 && Market_Opportunity_weight<=7.5)
        return '#377EB4';
    else
        return '#286594';
        
}).on("click",function(d,i){
//            drawSpider(ideaName[i+1]);
			  location.href = IdeaUrl[i+1]
        });

var transit = d3.select("#wrapper_graph>svg").selectAll("rect").data(ideaUtility).transition()
		 .duration(1000).attr("width", function(d) {
			return xscale(d);
		});

var transitext = d3.select('#bars').selectAll('text').data(ideaUtility).enter()
		.append('text').attr({
			'x' : function(d) {
				return xscale(d)+10;
			},
			'y' : function(d, i) {
				if(barWidth == 30)
					return yscale(i+1) + 20;
				else
					return yscale(i+1) + 14;
			}
		}).text(function(d,i) {
            
			return ideaName[i+1]+"("+d+")";
		}).style({
			'fill' : '#000',
			'font-size' : '16px',
			'font-family' : 'serif'
		});
var transitext1 = d3.select('#bars').selectAll('p').data(ideaUtility).enter().append('text').attr({
			'x' : function(d) {
				return xscale(d)-50;
			},
			'y' : function(d, i) {
				if(barWidth == 30)
					return yscale(i+1) + 20;
				else
					return yscale(i+1) + 14;
			}
		}).text(function(d,i) {            
			return "";
		}).style({
			'fill' : '#fff',
			'font-size' : '16px',
			'font-family' : 'serif'
		});
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
        itm['IdeaName'] = votingAverage_by_criteria[i].Name;
        itm['IdeaUtility'] = utility.toFixed(2);
        itm['IdeaUrl'] = votingAverage_by_criteria[i].url;
        
        graphData.push(itm);
    }
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