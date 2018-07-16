<%@include file="../init.jsp"%>

<portlet:resourceURL var="ideaChartURL" id="ideaChartURL">
</portlet:resourceURL>
<script type="text/javascript">
$(document).ready(function () {
	ideaChartByType('innovation');
	});
ideaChartByType = function(type){
	
	var ideaType = type;
	var titleOfChart = '';
	if(ideaType == 'innovation')
		titleOfChart = 'Ideas by Innvoation Type';
	else if(ideaType == 'edgeService')
		titleOfChart = 'Ideas by Edge Services';
	else	
		titleOfChart = 'Ideas by Vertical';
	
	$('#ideaByType').empty();
	$('#ideaByType1').empty();
	$.ajax({
		   url :"<%=ideaChartURL.toString()%>",
				data : {
					ideaType : ideaType,
				},
				dataType:"JSON",
				 type:'POST',
				success : function(data) {
					$(function () {
						$('#ideaByType').highcharts({
					        chart: {
					            plotBackgroundColor: null,
					            plotBorderWidth: null,
					            plotShadow: false,
					            type: 'pie'
					        },
					     	 //for remove default site name option 
							credits:{
								enabled : false
							},
							
							//for chart download option disable
							exporting:{
								enabled:false
							},
					        title: {							
								text: titleOfChart
					        },
					        tooltip: {
					            pointFormat: '{series.name}: <b>{point.percentage:}</b>'
					        },
					        plotOptions: {
					            pie: {
					                allowPointSelect: true,
					                cursor: 'pointer',
					                dataLabels: {
					                    enabled: true,
					                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
					                    style: {
					                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					                    }
					                }
					            }
					        },
					        series: [{
							name:"Ideas",
							 colorByPoint: true,
							 data:data
							}]
					    });
					});
								$(function () {
						$('#ideaByType1').highcharts({
					        chart: {
					            plotBackgroundColor: null,
					            plotBorderWidth: null,
					            plotShadow: false,
					            type: 'pie'
					        },
					     	 //for remove default site name option 
							credits:{
								enabled : false
							},
							
							//for chart download option disable
							exporting:{
								enabled:false
							},
					        title: {
					            text: 'Ideas By Innovation type '
					        },
					        tooltip: {
					            pointFormat: '{series.name}: <b>{point.percentage:}</b>'
					        },
					        plotOptions: {
					            pie: {
					                allowPointSelect: true,
					                cursor: 'pointer',
					                dataLabels: {
					                    enabled: true,
					                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
					                    style: {
					                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					                    }
					                }
					            }
					        },
					        series: [{
							name:"Ideas",
							 colorByPoint: true,
							 data:data
							}]
					    });
					});
				},
				 async: false
			});
	console.log(ideaType);
}
$(function () {
    // Create the chart
    $('#ideaByMonth').highcharts({
        chart: {
            type: 'column'
        },
      //for remove default site name option 
		credits:{
			enabled : false
		},
		//for chart download option disable
		exporting:{
			enabled:false
		},
        title: {
            text: ${chartDate}
        },
        subtitle: {
            text: 'Click the columns to view Ideas of Month.'
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            title: {
                text: 'Total Number of Ideas'
            }

        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y}'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> '
        },

        series: [${listOfMonthWiseIdea}],
        drilldown: {
            series: ${listOfDayWiseIdea}
        }
    });
});

$(function () {
    // Create the chart
    $('#ideaByMonth1').highcharts({
        chart: {
            type: 'column'
        },
      //for remove default site name option 
		credits:{
			enabled : false
		},
		//for chart download option disable
		exporting:{
			enabled:false
		},
        title: {
            text: ${chartDate}
        },
        subtitle: {
            text: 'Click the columns to view Ideas of Month.'
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            title: {
                text: 'Total Number of Ideas'
            }

        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y}'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> '
        },

        series: [${listOfMonthWiseIdea}],
        drilldown: {
            series: ${listOfDayWiseIdea}
        }
    });
});


/* 
$(function () {
    $('#ideaByStage').highcharts({
        chart: {
            type: 'funnel',
            marginRight: 100
        },
		//for remove default site name option 
		credits:{
			enabled : false
		},
		
		//for chart download option disable
		exporting:{
			enabled:false
		},
        title: {
            text: 'Ideas By Stage',
            x: -50
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b> ({point.y:,.0f})',
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black',
                    softConnector: true
                },
                neckWidth: '30%',
                neckHeight: '25%'
            }
        },
        legend: {
            enabled: false
        },
        series: [${listOfStageWiseIdea}]
    });
}); */
$(function () {
    $('#socialInnovation').highcharts({

        chart: {
            type: 'column'
        },
        //for remove default site name option 
		credits:{
			enabled : false
		},
		
		//for chart download option disable
		exporting:{
			enabled:false
		},
        title: {
            text: 'My Social Points'
        },

        xAxis: {
            categories: ${monthYearList}
        },

        yAxis: {
            allowDecimals: false,
            min: 0,
            title: {
                text: 'Number of thank yous and upvotes'
            }
        },

        tooltip: {
            formatter: function () {
                return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + this.y + '<br/>' +
                    'Total: ' + this.point.stackTotal;
            }
        },

        plotOptions: {
            column: {
                stacking: 'normal'
            }
        },

        series: [{
            name: 'Thank You',
            data: ${thanksCountList},
            stack: 'Thank You'
        }, {
            name: 'Upvotes',
            data: ${likeCountList},
            stack: 'Upvotes'
        }]
    });
});

$(function () {
    $('#socialInnovation1').highcharts({

        chart: {
            type: 'column'
        },
        //for remove default site name option 
		credits:{
			enabled : false
		},
		
		//for chart download option disable
		exporting:{
			enabled:false
		},
        title: {
            text: 'My Social Points'
        },

        xAxis: {
            categories: ${monthYearList}
        },

        yAxis: {
            allowDecimals: false,
            min: 0,
            title: {
                text: 'Number of thank yous and upvotes'
            }
        },

        tooltip: {
            formatter: function () {
                return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + this.y + '<br/>' +
                    'Total: ' + this.point.stackTotal;
            }
        },

        plotOptions: {
            column: {
                stacking: 'normal'
            }
        },

        series: [{
            name: 'Thank You',
            data: ${thanksCountList},
            stack: 'Thank You'
        }, {
            name: 'Upvotes',
            data: ${likeCountList},
            stack: 'Upvotes'
        }]
    });
});
</script>