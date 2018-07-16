<%@include file="init.jsp"%>
<script type="text/javascript">
bQuery(function () {
	bQuery('#chartContainer').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Polio communication staff in place at country level(%)',
            align: 'left',
			style: { "color": "#1176BE", "fontSize": "12px"}
        },
        xAxis: {
            categories: [
                'Mar 11',
                'June 11',
                'Sept 11',
                'Dec 11',
                'Mar 12',
                'Aug 12'
            ]
        },
		credits: {
		  enabled: false
		},
        yAxis: {
            min: 0,
			max: 100,
            title: {
                text: ''
            }
        },
       tooltip: {
		 backgroundColor: '#FFA500',
		 borderColor: '#FFA500',
		 style: {
			fontSize: '11px',
			color: '#FFFFFF',
			fontWeight: 'bold'
		  },
		  useHTML : true,
		  formatter: function () {
                return this.series.name + '<br/>' + this.y + '<br/>';
            }
		},
        plotOptions: {
            column: {
				pointWidth: 33,
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    color: 'white'
                }
            }
        },
		yAxis: {
            max: 100,
			min: 0,
			title:'',
			gridLineColor: '#D9D9D9',
			gridLineWidth: 0,
			lineWidth: 1,
			lineColor: '#D9D9D9',
            stackLabels: {
                enabled: false,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
        },
        series: [{
            name: 'In place - Total',
            data: [90, 90, 89.3, 86, 100, 93.3],
			color: '#FFA500'

        }],
		legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0,
			margin: 15,
			padding:0,
			useHTML: true,
			symbolHeight: 15,
			symbolWidth: 10
        },
    });
});
</script>
<style type="text/css">
.np-test{
 cursor: pointer;
}
.np-link-rightlink-part1 a:hover{
 color: #FFFFFF !important;
}
.np-link-rightlink-part1 a{
  color: gray !important;
}
.np-link-rightlink-part1 .active{
  color: #FFFFFF !important;
}
</style>
<fmt:formatDate pattern="dd MMM, yyyy" value="${currentDate}" var="formattedSubmissionDate" />
<div style="padding:3px; border:2px solid #f4f4f4; border-radius:4px; -webkit-border-radius:4px;">
<div class="np-link-box clearfix">
		Innovation Dashboard - ${formattedSubmissionDate}
 	<div class="np-link-rightlink">
		<div class="np-link-rightlink-part2 clearfix">
			<a href="#">calender</a>
		</div>
		<div class="np-link-rightlink-part1 clearfix">
			<a href="#" class="active">2012</a>
			<a href="#">today</a>
			<a href="#">7 days</a>
			<a href="#">30 days</a>
			<a href="#">60 days</a>
			<a href="#">90 day</a>
		</div>
	</div>
</div>
<div class="np-sliderfix">
  <div class="flexslider carousel" >
    <ul class="slides">
     <li class="np-box01 np-active-blue np-test" onclick="javascript:showTimelineChart(1,'np1','positive');" id="np1">
     	<img src="<%=request.getContextPath()%>/images/np-gr-img-1.png">
      </li>
      <li class="np-box01 np-test" onclick="javascript:showTimelineChart(4,'np4','negative');" id="np4">
      	<img src="<%=request.getContextPath()%>/images/np-gr-img-2.png">
      </li>
      <li class="np-box01 np-test" onclick="javascript:showTimelineChart(2,'np2','positive');" id="np2">
       <img src="<%=request.getContextPath()%>/images/np-gr-img-3.png">
      </li>
       <li class="np-box01 np-test" onclick="javascript:showTimelineChart(5,'np5','negative');" id="np5">
         <img src="<%=request.getContextPath()%>/images/np-gr-img-4.png">
      </li>
      <li class="np-box01 np-test" onclick="javascript:showTimelineChart(3,'np3','positive');" id="np3">
       <img src="<%=request.getContextPath()%>/images/np-gr-img-5.png">
      </li>
      <li class="np-box01 np-test" onclick="javascript:showTimelineChart(6,'np6','negative');" id="np6">
        <img src="<%=request.getContextPath()%>/images/np-gr-img-6.png">
      </li>
      <li class="np-box01 np-test" onclick="javascript:showTimelineChart(7,'np7','positive');" id="np7">
     	 <img src="<%=request.getContextPath()%>/images/np-gr-img-1.png">
      </li>
      <li class="np-box01 np-test" onclick="javascript:showTimelineChart(8,'np8','negative');" id="np8">
       <img src="<%=request.getContextPath()%>/images/np-gr-img-2.png">
      </li>
      <li class="np-box01 np-test" onclick="javascript:showTimelineChart(9,'np9','positive');" id="np9">
       <img src="<%=request.getContextPath()%>/images/np-gr-img-3.png">
      </li>
    </ul>
  </div>
</div>

<div id="chartWrapper">
   <div id="chartContainer" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
</div>

</div>


<div style="margin-top: 12px;">
<div class="np-sliderfix">
  <div class="flexslider2 carousel" >
    <ul class="slides np-slide-fix002">
    
      <li class="np-dashboard-bottom-slide-fix01">
       <img src="<%=request.getContextPath()%>/images/np-gr-img-7.png">
      </li>
      
      <li class="np-dashboard-bottom-slide-fix01">
       <img src="<%=request.getContextPath()%>/images/np-gr-img-8.png">
      </li>
      
      <li class="np-dashboard-bottom-slide-fix01">
        <img src="<%=request.getContextPath()%>/images/np-gr-img-9.png">
      </li>
      
      <li class="np-dashboard-bottom-slide-fix01">
       	<img src="<%=request.getContextPath()%>/images/np-gr-img-10.png">
      </li>
      
      <li class="np-dashboard-bottom-slide-fix01">
      	<img src="<%=request.getContextPath()%>/images/np-gr-img-11.png">
      </li>
      
      <li class="np-dashboard-bottom-slide-fix01">
       <img src="<%=request.getContextPath()%>/images/np-gr-img-12.png">
      </li>
      
      <li class="np-dashboard-bottom-slide-fix01">
       <img src="<%=request.getContextPath()%>/images/np-gr-img-9.png">
      </li>
    </ul>
  </div>
</div></div> 
<script type="text/javascript">
  function showSelectedChartOption(id, type){
	  if(bQuery('.np-test').hasClass('np-active-blue')){
		  bQuery('.np-test').removeClass('np-active-blue');
	  }
	  if(bQuery('.np-test').hasClass('np-active-red')){
		  bQuery('.np-test').removeClass('np-active-red');
	  }
	  if(type == 'positive'){
		  bQuery('#'+id).addClass('np-active-blue');
	  }else{
		  bQuery('#'+id).addClass('np-active-red');
	  }
	  
  }
  function showTimelineChart(index, id, type){
	 showSelectedChartOption(id, type);
     if(index == 1){
    	 bQuery('#chartContainer').highcharts({
    	        chart: {
    	            type: 'column'
    	        },
    	        title: {
    	            text: 'Polio communication staff in place at country level(%)',
    	            align: 'left',
    				style: { "color": "#1176BE", "fontSize": "12px"}
    	        },
    	        xAxis: {
    	            categories: [
    	                'Mar 11',
    	                'June 11',
    	                'Sept 11',
    	                'Dec 11',
    	                'Mar 12',
    	                'Aug 12'
    	            ]
    	        },
    			credits: {
    			  enabled: false
    			},
    	        yAxis: {
    	            min: 0,
    				max: 100,
    	            title: {
    	                text: ''
    	            }
    	        },
    	       tooltip: {
    			 backgroundColor: '#FFA500',
    			 borderColor: '#FFA500',
    			 style: {
    				fontSize: '11px',
    				color: '#FFFFFF',
    				fontWeight: 'bold'
    			  },
    			  useHTML : true,
    			  formatter: function () {
    	                return this.series.name + '<br/>' + this.y + '<br/>';
    	            }
    			},
    	        plotOptions: {
    	            column: {
    					pointWidth: 33,
    	                stacking: 'normal',
    	                dataLabels: {
    	                    enabled: true,
    	                    color: 'white'
    	                }
    	            }
    	        },
    			yAxis: {
    	            max: 100,
    				min: 0,
    				title:'',
    				gridLineColor: '#D9D9D9',
    				gridLineWidth: 0,
    				lineWidth: 1,
    				lineColor: '#D9D9D9',
    	            stackLabels: {
    	                enabled: false,
    	                style: {
    	                    fontWeight: 'bold',
    	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
    	                }
    	            }
    	        },
    	        series: [{
    	            name: 'In place - Total',
    	            data: [90, 90, 89.3, 86, 100, 93.3],
    				color: '#FFA500'

    	        }],
    			legend: {
    	            layout: 'horizontal',
    	            align: 'center',
    	            verticalAlign: 'bottom',
    	            borderWidth: 0,
    				margin: 15,
    				padding:0,
    				useHTML: true,
    				symbolHeight: 15,
    				symbolWidth: 10
    	        },
    	    });
	 }else if(index == 2){
		 bQuery('#chartContainer').highcharts({
		        title: {
		            text: 'Social mobilization funds are available in HRAs before SIA\'s(%)',
		            align: 'left',
					style: { "color": "#1176BE", "fontSize": "12px"}
		        },
		        xAxis: {
		            categories: ['May 11', 'June 11', 'July 11', 'Aug 12', 'Nov 11', 'Feb 12', 'Mar 12'],
					lineColor: '#D9D9D9'
		        },
		        yAxis: {
					max: 100,
					min: 0,
					title:'',
					gridLineColor: '#D9D9D9',
					gridLineWidth: 0,
					lineWidth: 1,
					lineColor: '#D9D9D9',
		        },
		        legend: {
		            layout: 'horizontal',
		            align: 'center',
		            verticalAlign: 'bottom',
		            borderWidth: 0,
					margin: 15,
					padding:0,
					useHTML: true,
					symbolHeight: 50,
		        },
		        series: [{
		            name: 'Percentage of HRAs that received timely communtication/social mobilization funding before SIA\'s',
		            data: [100, 100, 100, 100, 100, 96, 100],
					color: '#FFA500',
					lineWidth: 1
		        }],
				chart: {
				  plotBorderColor: 'red',
				  borderColor: 'black',
				  style: {
					fontSize: '12px',
					color: 'red'
				  }
				},
				tooltip: {
				 backgroundColor: '#FFA500',
				 borderColor: '#FFA500',
				 style: {
					fontSize: '11px',
					color: '#FFFFFF',
					fontWeight: 'bold'
				  },
				  useHTML : true
				},
				credits: {
				  enabled: false
				},
				plotOptions:{
				  area:{
				     fillColor: { "color": "#1176BE", "fontSize": "12px"}
				   }
				}
				
		    });
	 }else if(index == 3){
		 bQuery('#chartContainer').highcharts({
		        title: {
		            text: 'Missed Children(%)',
		            align: 'left',
					style: { "color": "#1176BE", "fontSize": "12px"}
		        },
		        xAxis: {
		            categories: ['June 11', 'July 11', 'Sept 11', 'Nov 11', 'Mar 12', 'May 12', 'Jul 12'],
					lineColor: '#D9D9D9'
		        },
		        yAxis: {
					max: 8,
					min: 0,
					title:'',
					gridLineColor: '#D9D9D9',
					gridLineWidth: 0,
					lineWidth: 1,
					lineColor: '#D9D9D9',
		        },
		        legend: {
		            layout: 'horizontal',
		            align: 'center',
		            verticalAlign: 'bottom',
		            borderWidth: 0,
					margin: 15,
					padding:0,
					useHTML: true,
					symbolHeight: 50,
		        },
		        series: [{
		            name: 'Percentage of Missed Children',
		            data: [6, 7, 6.84, 7, 7, 7.3, 6.8],
					color: '#FFA500',
					lineWidth: 1
		        }],
				chart: {
				  plotBorderColor: 'red',
				  borderColor: 'black',
				  style: {
					fontSize: '12px',
					color: 'red'
				  }
				},
				tooltip: {
				 backgroundColor: '#FFA500',
				 borderColor: '#FFA500',
				 style: {
					fontSize: '11px',
					color: '#FFFFFF',
					fontWeight: 'bold'
				  },
				  useHTML : true,
				  formatter: function () {
		                return this.series.name + '<br/>' + this.y + '<br/>';
		            }
				},
				credits: {
				  enabled: false
				},
				plotOptions:{
				  area:{
				     fillColor: { "color": "#1176BE", "fontSize": "12px"}
				   }
				}
				
		    });
	 }else if(index == 4){
		 bQuery('#chartContainer').highcharts({
		        title: {
		            text: 'Missed children due to refusal(%)',
		            align: 'left',
					style: { "color": "#1176BE", "fontSize": "12px"}
		        },
		        xAxis: {
		            categories: ['June 11', 'July 11', 'Sept 11', 'Nov 11', 'Mar 12', 'May 12', 'Jul 12'],
					lineColor: '#D9D9D9'
		        },
		        yAxis: {
					max: 28,
					min: 0,
					title:'',
					gridLineColor: '#D9D9D9',
					gridLineWidth: 0,
					lineWidth: 1,
					lineColor: '#D9D9D9',
		        },
		        legend: {
		            layout: 'horizontal',
		            align: 'center',
		            verticalAlign: 'bottom',
		            borderWidth: 0,
					margin: 15,
					padding:0,
					useHTML: true,
					symbolHeight: 50,
		        },
		        series: [{
		            name: 'Percentage of Missed Children',
		            data: [28, 25, 25, 24, 26, 24, 24],
					color: '#FFA500',
					lineWidth: 1
		        }],
				chart: {
				  plotBorderColor: 'red',
				  borderColor: 'black',
				  style: {
					fontSize: '12px',
					color: 'red'
				  }
				},
				tooltip: {
				 backgroundColor: '#FFA500',
				 borderColor: '#FFA500',
				 style: {
					fontSize: '11px',
					color: '#FFFFFF',
					fontWeight: 'bold'
				  },
				  useHTML : true
				},
				credits: {
				  enabled: false
				},
				plotOptions:{
				  area:{
				     fillColor: { "color": "#1176BE", "fontSize": "12px"}
				   }
				}
				
		    });
	 }else if(index == 5){
		 bQuery('#chartContainer').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: 'Social data is systematically used for communication planning(%)',
		            align: 'left',
					style: { "color": "#1176BE", "fontSize": "12px"}
		        },
		        xAxis: {
		            categories: [
		                'Dec 10',
		                'Jan 11',
		                'Mar 11',
		                'June 11',
		                'Sept 11',
		                'Mar 12',
		                'Aug 12'
		            ]
		        },
				credits: {
				  enabled: false
				},
		        yAxis: {
		            min: 0,
					max: 100,
		            title: {
		                text: ''
		            }
		        },
		       tooltip: {
				 backgroundColor: '#69AF64',
				 borderColor: '#69AF64',
				 style: {
					fontSize: '11px',
					color: '#FFFFFF',
					fontWeight: 'bold'
				  },
				  useHTML : true,
				  formatter: function () {
		                return this.series.name + '<br/>' + this.y + '<br/>';
		            }
				},
		        plotOptions: {
		            column: {
						pointWidth: 33,
		                stacking: 'normal',
		                dataLabels: {
		                    enabled: true,
		                    color: 'white'
		                }
		            }
		        },
				yAxis: {
		            max: 100,
					min: 0,
					title:'',
					gridLineColor: '#D9D9D9',
					gridLineWidth: 0,
					lineWidth: 1,
					lineColor: '#D9D9D9',
		            stackLabels: {
		                enabled: false,
		                style: {
		                    fontWeight: 'bold',
		                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
		                }
		            }
		        },
		        series: [{
		            name: 'Percentage of plans that incorporated recent social data',
		            data: [90, 95, 98, 97, 98, 0, 0],
					color: '#69AF64'

		        }],
				legend: {
		            layout: 'horizontal',
		            align: 'center',
		            verticalAlign: 'bottom',
		            borderWidth: 0,
					margin: 15,
					padding:0,
					useHTML: true,
					symbolHeight: 15,
					symbolWidth: 10
		        },
		    });
     }else if(index == 6){
    	 bQuery('#chartContainer').highcharts({
    	        chart: {
    	            type: 'column'
    	        },
    	        title: {
    	            text: 'Percentage of non-polio AFP cases(Children 6-35 months) with 0 and >=4 does of OPV*',
    	            align: 'left',
    				style: { color: '#3DA0D4', fontName: 'Arial', fontSize: 12, fontWeight: 'bold'}
    	        },
    	        xAxis: {
    	            categories: ['Nigeria', 'Bombo', 'Jigava', 'Kano', 'Kastina', 'Kebbi', 'Sokoto', 'Yobe', 'Zamfara']
    	        },
    	        yAxis: {
    	            max: 100,
    				min: 0,
    				title:'',
    				gridLineColor: '#D9D9D9',
    				gridLineWidth: 0,
    				lineWidth: 1,
    				lineColor: '#D9D9D9',
    	            stackLabels: {
    	                enabled: false,
    	                style: {
    	                    fontWeight: 'bold',
    	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
    	                }
    	            }
    	        },
    	         legend: {
    	            layout: 'horizontal',
    	            align: 'center',
    	            verticalAlign: 'bottom',
    	            borderWidth: 0,
    				margin: 15,
    				padding:0,
    				useHTML: true,
    				symbolHeight: 15,
    				symbolWidth: 10
    	        },
    	        tooltip: {
    			 style: {
    				fontSize: '11px',
    				color: '#000000',
    				fontWeight: 'bold'
    			  },
    			  useHTML : true,
    			  headerFormat: '<table>',
    	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name} </td></tr/>' +
    	                '<tr><td style="padding:0"><b>{point.y}</b></td></tr>',
    	            footerFormat: '</table>'
    	        },
    			credits: {
    			  enabled: false
    			},
    	        plotOptions: {
    	            column: {
    					pointWidth: 33,
    	                stacking: 'normal',
    	                dataLabels: {
    	                    enabled: false,
    	                    color: 'white'
    	                }
    	            }
    	        },
    	        series: [{
    	            name: 'more than 4 doses',
    	            data: [76.1, 20.1, 68.42, 61.9, 71.66, 93.18, 65.03, 71.43, 76.1],
    				color: '#459FD2'
    	        }, {
    	            name: '1-3 doses',
    	            data: [21.9, 27.83, 28.94, 29.63, 6.82, 30.89, 28.57, 18.36],
    				color: '#69AF64'
    	        }, {
    	            name: '0 doses',
    	            data: [2, 1.03, 1.32, 8.47, 4.17, 0, 4.07, 0, 4.4],
    				color: '#F4771E'
    	        }, {
    	            name: 'unknown doses',
    	            data: [],
    				color: '#7375B0'
    	        }]
    	    });
     }else if(index == 7){
    	 bQuery('#chartContainer').highcharts({
    	        chart: {
    	            plotBackgroundColor: null,
    	            plotBorderWidth: null,
    	            plotShadow: false,
    				events:{
    					options3d:{
    						frame:{
    							back:{
    								color:'transparent',
    							}
    						}
    					}
    				}
    	        },
    			colors : ['#6C6DA4','#E56F1C','#184907'],
    			credits:{
    				enabled:false
    			},
    	         title: {
    	            text: 'Reason for Missed Children(%)',
    	            align: 'left',
    				style: { "color": "#1176BE", "fontSize": "12px"}
    	        },
    	        tooltip: {
    	            style: {
    				fontSize: '11px',
    				color: '#000000',
    				fontWeight: 'bold'
    			  },
    			  useHTML : true,
    			  headerFormat: '<table>',
    	          pointFormat: '<tr><td style="color:{point.color};padding:0">{point.name} </td></tr/>' +
    	                '<tr><td style="padding:0"><b>{point.percentage:.1f}</b></td></tr>',
    	          footerFormat: '</table>'
    	        },
    	        plotOptions: {
    	            pie: {
    	                allowPointSelect: true,
    	                cursor: 'pointer',
    					animation:false,
    					enableMouseTracking:true,
    					dataLabels: {
    	                    enabled: false,
    						useHTML: true,
    						format: '{point.percentage:.1f}',
    						inside: true,
    						verticalAlign: 'middle',
    						overflow: 'none',
    						crop: false
    	                },
    					showInLegend: true
    	            }
    	        },
    			 legend: {
    	            layout: 'horizontal',
    	            align: 'center',
    	            verticalAlign: 'bottom',
    	            borderWidth: 0,
    				margin: 15,
    				padding:0,
    				useHTML: true,
    				symbolHeight: 8,
    				symbolWidth: 8,
    				title: {
    				 style: {
    				   fontweight : 'normal'
    				 }
    				}
    	        },
    	        series: [{
    	            type: 'pie',
    	           data: [
    	                ['Child not available',   69],
    	                ['Refusal to accept OPV', 24],
    					['No team/team did not visit', 7]  
    	            ]
    	        }],
    	    });
    	// Highcharts.setOptions(Highcharts.theme);
     }else if(index == 8){
    	 bQuery('#chartContainer').highcharts({
    	        title: {
    	            text: 'Caregivers aware of polio campaigns(%)',
    	            align: 'left',
    				style: { "color": "#1176BE", "fontSize": "12px"}
    	        },
    	        xAxis: {
    	            categories: ['Sept 11', 'Oct 11', 'Dec 11', 'Mar 12', 'Apr 12', 'Jul 12'],
    				lineColor: '#D9D9D9'
    	        },
    	        yAxis: {
    				max: 100,
    				min: 0,
    				title:'',
    				gridLineColor: '#D9D9D9',
    				gridLineWidth: 0,
    				lineWidth: 1,
    				lineColor: '#D9D9D9',
    	        },
    	        legend: {
    	            layout: 'horizontal',
    	            align: 'center',
    	            verticalAlign: 'bottom',
    	            borderWidth: 0,
    				margin: 15,
    				padding:0,
    				useHTML: true,
    				symbolHeight: 50,
    	        },
    	        series: [{
    	            name: 'Percentage of caregivers aware of polio campaigns',
    	            data: [66.06, 57.38, 56.04, 60.01, 57.06, 48.56],
    				color: '#FFA500',
    				lineWidth: 1
    	        }],
    			chart: {
    			  plotBorderColor: 'red',
    			  borderColor: 'black',
    			  style: {
    				fontSize: '12px',
    				color: 'red'
    			  }
    			},
    			tooltip: {
    			 backgroundColor: '#FFA500',
    			 borderColor: '#FFA500',
    			 style: {
    				fontSize: '11px',
    				color: '#FFFFFF',
    				fontWeight: 'bold'
    			  },
    			  useHTML : true
    			},
    			credits: {
    			  enabled: false
    			},
    			plotOptions:{
    			  area:{
    			     fillColor: { "color": "#1176BE", "fontSize": "12px"}
    			   }
    			}
    			
    	    });
     }else if(index == 9){
    	 bQuery('#chartContainer').highcharts({
    	        chart: {
    	            type: 'column',
    				plotShadow:true,
    				
    	        },
    			colors : ['#F4771E','#69AF64','#459FD2'],
    			credits:{
    				enabled:false
    			},
    	        title: {
    	            text: 'Source of information for caregivers aware of polio campaigns(%)',
    				align :'left',
    				style:{
    					"color": '#1176AF',"fontSize": "12px"
    				}
    	        },
    	         xAxis: {
    	            categories: [
    	                'May 11',
    	                'June 11',
    	                'July 11',
    	                'Sept 11',
    	                'Mar 12',
    	                'July 12'
    	            ]
    	        },
    	        yAxis: {
    	            min: 0,
    				max:100,
    				lineWidth:1,
    				gridLineColor:'#fff',
    				title:''
    	         },
    	        tooltip: {
    	            headerFormat: '<table>',
    	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name} </td></tr/>' +
    	                '<tr><td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
    	            footerFormat: '</table>',
    				shadow:false,
    				style:{
    					color:'#000000',
    				},
    				useHTML: true
    	        },
    	        plotOptions: {
    	            column: {
    	                pointPadding: 0.3,
    	                borderWidth: 1
    	            }
    	        },
    	        series: [{
    	            name: 'Mass media',
    	            data: [17, 19, 15, 18, 21, 20],
    				}, 
    			{
    	            name: 'Interpersonal source',
    	            data: [51, 46, 55, 43, 19, 19],
    				

    	        },
    			{
    	            name: 'Health service worker',
    	            data: [3, 3, 6, 3, 11, 3],
    				
    	}]
    	    });
     }
  }
 
  bQuery(window).load(function(){
      bQuery('.flexslider').flexslider({
        animation: "slide",
        animationLoop: false,
        itemWidth: 160,
        itemMargin: 0,
  slideshow: false,
        pausePlay: false,
  controlNav: false
       });
    
      bQuery('.flexslider2').flexslider({
        animation: "slide",
        animationLoop: false,
        itemWidth: 180,
        itemMargin:8,
  slideshow: false,
        pausePlay: false,
  controlNav: false
       });


    });
  </script>

 <style>
.np-box01{  border:3px solid transparent;}
.np-box01 img{ opacity:0.6; -webkit-touch-callout: none; -webkit-user-select: none; -khtml-user-select: none; -moz-user-select: none;  -ms-user-select: none;  user-select: none; }
.np-box01.np-active-blue img, .np-box01.np-active-red img{ opacity:1; background-color:#FFF;}
.np-box01.np-active-blue, .np-box01.np-active-red{ border:3px solid #666;}
.np-dashboard-bottom-slide-fix01{ border:2px solid #d9d9d9; border-radius:4px;padding: 6px;}


</style>