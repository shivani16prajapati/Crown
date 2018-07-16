<%@page import="java.text.DecimalFormat"%>
<%@include file="../init.jsp"%>
<c:set value="${hotness}" var="hotnessValue" />

<%
String newHotCount = StringPool.BLANK;
	
DecimalFormat df = new DecimalFormat("###.#");
newHotCount = df.format((Double)pageContext.getAttribute("hotnessValue"));
pageContext.setAttribute("newHotCount", newHotCount);
%>

<div id='<portlet:namespace/>ideaHotness'>
<div style="font-size: 18px;color:#fff;font-weight: bold;padding-bottom: 11px;background-color: #c4c5c7;border-radius:10px 10px 0 0;line-height:18px;padding-top:11px;padding-left:12px;">Solution Velocity</div>
    <div class="ku-m-box clearfix" >
    	<c:if test="${newHotCount eq 0}">
    		<div class="valocity-main-box"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 0 && newHotCount <= 5}">
    		<div class="valocity-main-box l-01"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 5 && newHotCount <= 10}">
    		<div class="valocity-main-box l-02"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 10 && newHotCount <= 15}">
    		<div class="valocity-main-box l-03"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 15 && newHotCount <= 20}">
    		<div class="valocity-main-box l-04"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 20 && newHotCount <= 25}">
    		<div class="valocity-main-box l-05"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 25 && newHotCount <= 30}">
    		<div class="valocity-main-box l-06"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 30 && newHotCount <= 35}">
    		<div class="valocity-main-box l-07"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 35 && newHotCount <= 40}">
    		<div class="valocity-main-box l-08"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 40 && newHotCount <= 45}">
    		<div class="valocity-main-box l-09"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 45 && newHotCount <= 50}">
    		<div class="valocity-main-box l-10"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 50 && newHotCount <= 55}">
    		<div class="valocity-main-box l-11"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 55 && newHotCount <= 60}">
    		<div class="valocity-main-box l-12"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 60 && newHotCount <= 65}">
    		<div class="valocity-main-box l-13"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 65 && newHotCount <= 70}">
    		<div class="valocity-main-box l-14"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 70 && newHotCount <= 75}">
    		<div class="valocity-main-box l-15"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 75 && newHotCount <= 80}">
    		<div class="valocity-main-box l-16"></div>
    	</c:if>
    	<c:if test="${newHotCount gt 80 && newHotCount <= 85}">
    		<div class="valocity-main-box l-17"></div>
    	</c:if>
	    <c:if test="${newHotCount gt 85 && newHotCount <= 90}">
	    		<div class="valocity-main-box l-18"></div>
	    </c:if>	
	    <c:if test="${newHotCount gt 90 && newHotCount <= 95}">
	    		<div class="valocity-main-box l-19"></div>
	    </c:if>	
	     <c:if test="${newHotCount gt 95 && newHotCount <= 100}">
	    		<div class="valocity-main-box l-20"></div>
	    </c:if>	
       
          <div class="valocity-value-text"> <span>${newHotCount}</span> <span>HOTNESS</span> </div>
          <div class="valocity-value-text-01">0</div>
          <div class="valocity-value-text-02">100</div>
        </div>
      </div>

<!-- <script type="text/javascript">
$(function () {
	var ideaHotness = parseInt("${hotness}");
    var gaugeOptions = {

        chart: {
            type: 'solidgauge'
        },

        title: null,

        pane: {
            center: ['50%', '85%'],
            size: '140%',
            startAngle: -90,
            endAngle: 90,
            background: {
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
                innerRadius: '60%',
                outerRadius: '100%',
                shape: 'arc'
            }
        },

        tooltip: {
            enabled: false
        },

        // the value axis
        yAxis: {
            stops: [
                [0.1, '#55BF3B'], // green
                [0.5, '#DDDF0D'], // yellow
                [0.9, '#DF5353'] // red
            ],
            lineWidth: 0,
            minorTickInterval: null,
            tickPixelInterval: 400,
            tickWidth: 0,
            title: {
                y: -70
            },
            labels: {
                y: 16
            }
        },

        plotOptions: {
            solidgauge: {
                dataLabels: {
                    y: 5,
                    borderWidth: 0,
                    useHTML: true
                }
            }
        }
    };

    // The speed gauge
    $('#velocityContainer').highcharts(Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: 0,
            max: 100,
            title: {
                text: ''
            }
        },

        credits: {
            enabled: false
        },

        series: [{
            name: '',
            data: [ideaHotness],
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:25px;color:' +
                    ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
                       '<span style="font-size:12px;color:silver">Hotness</span></div>'
            },
            tooltip: {
                valueSuffix: 'Hotness'
            }
        }]

    }));

    

    // Bring life to the dials
   


});
/* $(function () {
	var ideaHotness = parseInt("${hotness}");
	var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'velocityContainer',
            type: 'gauge',
            plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: 0,
            plotShadow: false
        },
        exporting: {
        	enabled: false
        },
        title: '',
        pane: {
            startAngle: -90,
            endAngle: 90,
          //  center: ['50%', '100%'],
            background: [{
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#FFF'],
                        [1, '#333']
                    ]
                },
                borderWidth: 0,
               outerRadius: '109%'
            }, {
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#333'],
                        [1, '#FFF']
                    ]
                },
                borderWidth: 1,
                outerRadius: '107%'
            }, {
                // default background
            }, {
                backgroundColor: '#DDD',
                borderWidth: 0,
                outerRadius: '105%',
                innerRadius: '103%'
            }]
        },
		   
        // the value axis
        yAxis: {
            min: 0,
            max: 100,
            
            minorTickInterval: 'auto',
            minorTickWidth: 1,
            minorTickLength: 10,
            minorTickPosition: 'inside',
            minorTickColor: '#666',
    
            tickPixelInterval: 30,
            tickWidth: 2,
            tickPosition: 'inside',
            tickLength: 10,
            tickColor: '#666',
            labels: {
                step: 2,
                rotation: 'auto'
            },
            title: {
              //  text: 'Idea Hotness'
            },
            plotBands: [{
                from: 0,
                to: 11,
				innerRadius: '45%',
                outerRadius: '75%',
                color: '#55BF3B' // green
            }, {
                from: 11,
                to: 50,
				innerRadius: '45%',
                outerRadius: '75%',
                color: '#DDDF0D' // yellow
            }, {
                from: 51,
                to: 100,
				innerRadius: '45%',
                outerRadius: '75%',
                color: '#DF5353' // red
            }]        
        },
		credits: {
			enabled: false
		},
        series: [{
            name: 'Hotness',
            data: [ideaHotness],
            tooltip: {
                valueSuffix: ' '
            }
        }]
    }, 
    // Add some life
    function (chart) {
    });
}); */

</script>
<div style="width: 100%; height:233px; overflow:hidden;">
	<div id="velocityContainer" style="width: 100%; height:350px; " ></div>
 </div>
 <div style="width: 280px; height: 225px; margin: 0 auto;overflow:hidden;">
	<div id="velocityContainer" style="width: 270px; height: 200px; float: left"></div>
</div>

<style>
.highcharts-button{
	display: none !important;
}
</style> -->