<%@page pageEncoding="UTF-8"%>
<%@include file="../init.jsp"%>


<script src="<%=request.getContextPath()%>/js/jquery.min-1.11.3.js" type="text/javascript"></script>    
<script src="<%=request.getContextPath()%>/js/jquery.fancybox.js" type="text/javascript"></script>   
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.fancybox.css">  


<script src="<%=request.getContextPath()%>/js/Fileupload.js" type="text/javascript"></script>  
<script src="<%=request.getContextPath()%>/js/jquery.knob.js" type="text/javascript"></script>  

<script src="//cdnjs.cloudflare.com/ajax/libs/d3/3.5.3/d3.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/topojson/1.6.9/topojson.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/datamaps/0.5.8/datamaps.all.js"></script>
 
<%-- <script src="<%=request.getContextPath()%>/js/datamaps.world.min.js" type="text/javascript"></script>   --%>



<portlet:defineObjects />

<portlet:resourceURL id="editProfile" var="editProfile" ></portlet:resourceURL>
<portlet:resourceURL id="changeProfile" var="changeProfile" ></portlet:resourceURL>
<portlet:resourceURL id="gethotidea" var="gethotidea" ></portlet:resourceURL>

<liferay-theme:defineObjects />

<fmt:formatDate pattern="dd/MM/yyyy" value="${before_year}" var="beforeyear" />
<fmt:formatDate pattern="dd/MM/yyyy" value="${today_date}" var="today" />

<script src="<%=request.getContextPath()%>/js/chart_util.js"></script>

<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.bundle.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/css/CustomeMIstyle.css">

<style>
.Matrix_Header,#button_container{
width: 100%;
margin:0 auto;
}
.slider_container{
width: 100%;
}

#wrapper_graph{
width: 100%;
margin: 15px 0;
}
.dial{
  border: none !important;
  box-shadow: none !important;
}
</style>
	 


<div style="center" class="ng-scope nav-static  chat-sidebar-container pace-done" ng-controller="App" sn-demo="">

<main id="content" class="mi-container view-animate fade-up ng-scope" role="main" data-ui-view>  <!-- content  -->
<h1 class="mi-title01 ng-scope">InnoMetrics Dashboard</h1>
<div class="row ng-scope">
  <div class="col-sm-6 col-md-3">
    <section class="widget text-white ng-isolate-scope mi-fbox01" ng-class="" icon="flash"   data="vm.ideas" style="">
  <div class="widget-body clearfix">
		<div class="mi-fboxtitle01"> <span><img alt="" src="<%=request.getContextPath()%>/images/mi-f-icon01.png"></span> <span>Ideas</span>     </div>
		<div class="col-xs-6"><div class="mi-fboxtitle02"><span>${lastWeek_ideas}</span><span>Last Week </span></div>  </div>
		<div class="col-xs-6 clearfix" ><div class="mi-fboxtitle03"> <span>${total_ideas}</span><span>Total</span> </div> </div>
  </div>
</section>
  </div>
  <div class="col-sm-6 col-md-3">
    <section class="widget text-white ng-isolate-scope mi-fbox02" ng-class=" " icon="heart"  data="vm.thanks" style="">
<div class="widget-body clearfix">
		<div class="mi-fboxtitle01-02"> <span><img alt="" src="<%=request.getContextPath()%>/images/mi-f-icon02.png"></span> <span>Endorsements</span>     </div>
		<div class="col-xs-6"><div class="mi-fboxtitle02"><span>${lastWeek_endorsed}</span><span>Last Week </span></div>  </div>
		<div class="col-xs-6 clearfix" ><div class="mi-fboxtitle03"> <span>${total_endorsed}</span><span>Total</span> </div> </div>
</div>
</section>
  </div>
  <div class="col-sm-6 col-md-3">
    <section class="widget text-white ng-isolate-scope mi-fbox03" ng-class="" icon="comment"   data="vm.comments" style="">
<div class="widget-body clearfix">
		<div class="mi-fboxtitle01-03"> <span><img alt="" src="<%=request.getContextPath()%>/images/mi-f-icon03.png"></span> <span>Thank You’s</span>     </div>
		<div class="col-xs-6"><div class="mi-fboxtitle02"><span>${lastWeek_thankYou}</span><span>Last Week </span></div>  </div>
		<div class="col-xs-6 clearfix" ><div class="mi-fboxtitle03"> <span>${total_thankYou}</span><span>Total</span> </div> </div>
 </div>
</section>
  </div>
  <div class="col-sm-6 col-md-3">
    <section class="widget text-white ng-isolate-scope mi-fbox04" ng-class="" icon="thumbs-up"  data="vm.kudos" style="">
<div class="widget-body clearfix">
		<div class="mi-fboxtitle01-04"> <span><img alt="" src="<%=request.getContextPath()%>/images/mi-f-icon04.png"></span><span>Solutions</span>     </div>
		<div class="col-xs-6"><div class="mi-fboxtitle02"><span>${lastWeek_solution}</span><span>Last Week </span></div>  </div>
  		<div class="col-xs-6 clearfix" ><div class="mi-fboxtitle03"> <span>${total_solution}</span><span>Total</span> </div> </div>
 </div>
</section>
  </div>
</div>


<div class="row ng-scope">
  <div class="col-md-6">
    <section class="widget widget-chart-stats-simple ">
      <header class="widget-header">
        <h3 class="fw-thin mi-fboxtitle04">
          Idea Volume
        </h3>
        <div class="end-date text-brown text-right pull-right mt-n-lg">
          <p class="value3">${beforeyear} - ${today}</p>
        </div>
      </header>
      <div class="widget-body">
        <canvas id="idea_volume"></canvas>
      </div>
    </section>
  </div>  

	<div class="col-md-6">
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header">
        <h3 class="fw-thin mi-fboxtitle04">
          Idea By Type
        </h3>
        <div class="end-date text-brown text-right pull-right mt-n-lg">
          <p class="value3">${beforeyear} - ${today}</p>
        </div>
      </header>
      <div class="widget-body">
        <div class="dChart" id="idea_type_inv_div" >
          <canvas id="idea_type_inv" height="385" width="770"> </canvas>
        </div>
        <div class="dChart" id="idea_type_lof_div" style="display:none">
           <canvas id="idea_type_lof" height="385" width="770"> </canvas>
        </div>
         <div style="text-align: center;">
        <div class="md-radio md-radio-inline"> 
       		<input type="radio" name="radio2" id="radio2" value="radio2" checked="checked" onclick="idea_by_idea_type()">
	  		<label for="radio2" onclick="idea_by_idea_type()">Innovation Type </label>
		</div>
		<div class="md-radio md-radio-inline"> 
       		<input type="radio" name="radio2" id="radio1" onclick="idea_by_lof()" value="radio2">
	  		<label for="radio1" onclick="idea_by_lof()"> Line of Busiiness </label>
		</div>
		</div>
      </div>
    </section>
  </div>
	
</div>

<div class="row ng-scope">
  <div class="col-md-6">
    <section class="widget widget-chart-stats-simple ">
      <header class="widget-header">
        <h3 class="fw-thin mi-fboxtitle04">
          Idea By Stage
        </h3>
        <div class="end-date text-brown text-right pull-right mt-n-lg">
          <p class="value3">${beforeyear} - ${today}</p>
        </div>
      </header>
      <div class="widget-body">
      <div class="id-graphbox">
	
	
<table width="100%" border="0" cellspacing="0" cellpadding="6">
  <tbody>
    <tr>
      <td width="50%" class="id-graphtxt01">${ideastageList[0]}</td>
      <td width="50%" class="id-graphtxt02">Submitted </td>
    </tr>
    <tr>
      <td class="id-graphtxt01">${ideastageList[1]}</td>
      <td class="id-graphtxt02">Finalist</td>
    </tr>
    <tr>
      <td class="id-graphtxt01">${ideastageList[2]}</td>
      <td class="id-graphtxt02">Incubation</td>
    </tr>
    <tr>
      <td class="id-graphtxt01">${ideastageList[3]}</td>
      <td class="id-graphtxt02">Pilot</td>
    </tr>
    <tr>
      <td class="id-graphtxt01">${ideastageList[4]}</td>
      <td class="id-graphtxt02">Deployed</td>
    </tr>
    <tr>
      <td class="id-graphtxt01">${ideastageList[5]}</td>
      <td class="id-graphtxt02">Repository</td>
    </tr>
  </tbody>
</table>

	
</div>
	
       
      </div>
    </section>
  </div>  

	<div class="col-md-6">
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header">
        <h3 class="fw-thin mi-fboxtitle04">
          Thank You's & Upvotes
        </h3>
        <div class="end-date text-brown text-right pull-right mt-n-lg">
          <p class="value3">${beforeyear} - ${today}</p>
        </div>
      </header>
      <div class="widget-body">
        <canvas id="idea_like"></canvas>
      </div>
    </section>
  </div>
	
</div>


<div class="row ng-scope">

    <div class="col-md-6">
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header">
        <div class="mi-fboxtitle05">
          <span><img alt="" src="<%=request.getContextPath()%>/images/mi-box-Ticon-03.png"></span>  <span>Hot Ideas</span>
        </div>
        <div class="widget-controls">
          <a href="#"><i class="glyphicon glyphicon-cog"></i></a>
        </div>
      </header>
      <div class="widget-body Np-table-responsive">
        <table class="table mi-tablefix01 table-hover">
  		
  		   <tbody id="hotidealist">
  			
  			  
	  			
	  		</tbody>

		</table>
      </div>
    </section>
  </div>
  
<div class="col-md-6">
<section class="widget widget-chart-stats-simple ">
      <header class="widget-header">
        <h3 class="fw-thin mi-fboxtitle04">
          Idea By Location
        </h3>
        <div class="end-date text-brown text-right pull-right mt-n-lg">
          <p class="value3">${beforeyear} - ${today}</p>
        </div>
      </header>
      <div class="widget-body">
        <div id="vmap" style="width:100%; height:auto;position:relative;text-align:center;"></div>
      </div>
    </section>
    
  </div>
</div>


<div class="row ng-scope">
<div class="col-md-6">
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header">
        <div class="mi-fboxtitle05">
          <span><img alt="" src="<%=request.getContextPath()%>/images/mi-f-icon03.png"></span>  <span>Top Thank You Getter</span>
        </div>
        <div class="widget-controls">
          <a href="#"><i class="glyphicon glyphicon-cog"></i></a>
        </div>
      </header>
      <div class="widget-body Np-table-responsive">
        <table class="table mi-tablefix01 table-hover">
  		
  		<tbody>
  			
  			    <tr>
			      <th scope="col">Name</th>
			      <th scope="col">Thank You's</th>
			    </tr>
			    <c:if test="${empty thankyouList}">
			        <tr>
				      <td colspan="2">No Data Available</td>
				    </tr>
			    </c:if>
	  			<c:forEach items="${thankyouList}" var="recentHot">
		  			<tr>
				      <td>${recentHot.getInventorName()}</td>
				      <td>${recentHot.getUpVotes()}</td>
				    </tr>
	  			</c:forEach>
	  			
	  		</tbody>

		</table>
      </div>
    </section>
  </div>
  <div class="col-md-6">
  <section class="widget widget-chart-stats-simple">
    
      <header class="widget-header">
        <h3 class="fw-thin mi-fboxtitle04">
         Innovation Training
        </h3>
        <div class="end-date text-brown text-right pull-right mt-n-lg">
          <p class="value3">${beforeyear} - ${today}</p>
        </div>
      </header>
      <div class="widget-body clearfix">
       <div class="training_chart"> 
			<input type="text" value="90" class="dial" readonly="readonly" data-width="100"  data-height="100" data-fgColor="#8d193f"data-bgColor="#a57686">
			<div class="training_chart_text">Engineering</div>
	   </div>
       <div class="training_chart"> 
			<input type="text" value="75" class="dial" readonly="readonly" data-width="100"  data-height="100" data-fgColor="#21743d" data-bgColor="#7baa87">
			<div class="training_chart_text">IT</div>
	   </div>
       <div class="training_chart"> 
	       <input type="text" value="55" class="dial" readonly="readonly" data-width="100" data-height="100" data-fgColor="#2c418b" data-bgColor="#7987aa">
		   <div class="training_chart_text">Facilities</div>
		</div>
	   <div class="training_chart">   
			<input type="text" value="60" class="dial" readonly="readonly" data-width="100" data-height="100" data-fgColor="#ae8638" data-bgColor="#cab38b">
			<div class="training_chart_text">Bio-Engineering</div>
	   </div>
	   <div class="training_chart"> 
		   <input type="text" value="70" class="dial" readonly="readonly" data-width="100" data-height="100" data-fgColor="#198b81" data-bgColor="#7fc3bb">   
		   <div class="training_chart_text">Business</div>
		</div>
      </div>
    </section>
    
  </div>  

  
	
</div>


<div class="row ng-scope">
<div class="col-md-6">
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header">
        <h3 class="fw-thin mi-fboxtitle04">
          Decision Matrix
        </h3>
        <div class="end-date text-brown text-right pull-right mt-n-lg">
          <p class="value3">${beforeyear} - ${today}</p>
        </div>
      </header>
      <div class="widget-body">
          <liferay-portlet:runtime portletName="decisionmatrix_WAR_Unicefportlet"></liferay-portlet:runtime>
      </div>
    </section>
  </div> 

  
	
</div>


  




















<script>

function showhotidea(start,end){
	data = {start:start,end:end};
	fr_CallAjax('${gethotidea}', data, function(html){
		$('#hotidealist').html(html);
	})	
	
}
showhotidea(0,3)



function fr_CallAjax(url, data, handler) {
	$.ajax({
		type : 'GET',
		url  : url,
		data : data,
		cache: false,
		success : function(response) {
			if (handler) {
				handler(response);
			}
		}
	});
}


var chart = new Chart(document.getElementById('idea_type_inv'), {
    type: 'doughnut',
    data: {
        labels: [<c:forEach items="${ideachart}" var="ideaType"><c:if test="${ideaType.typename ne 'Massive disruption potential'}" >"${ideaType.typename}",</c:if></c:forEach>],
        datasets: [{
            backgroundColor: ['#3370B7','#B55C23','#716BB9','#805A45','#BA84BB','#7be5de','#8b57e0','#23d32f'],
            data: [<c:forEach items="${ideachart}" var="ideaType"><c:if test="${ideaType.typename ne 'Massive disruption potential'}" >${ideaType.value},</c:if></c:forEach>],
        }]
    },
    options: {}
});  

var chart = new Chart(document.getElementById('idea_type_lof'), {
    type: 'doughnut',
	    data: {
        labels: [<c:forEach items="${ideachartlof}" var="ideaType">"${ideaType.typename}",</c:forEach>],
        datasets: [{
            backgroundColor: ['#3370B7','#B55C23','#716BB9','#805A45','#BA84BB','#7be5de','#8b57e0','#23d32f'],
            data: [<c:forEach items="${ideachartlof}" var="ideaType">${ideaType.value},</c:forEach>],
        }]
    },

    options: {
    	responsive:true,
    	maintainAspectRatio:true
    }
}); 

function idea_by_idea_type(){
   $(".dChart").hide();  
   $("#idea_type_inv_div").show();             
}  

function idea_by_lof(){
$(".dChart").hide();
$("#idea_type_lof_div").show();

}  


new Chart(document.getElementById("idea_volume"), {
	  type: 'line',
	  data: {
	    labels: [<c:forEach items="${ideaByMonth}" var="ideaType">"${ideaType.typename}",</c:forEach>],
	    datasets: [{ 
	        data: [<c:forEach items="${ideaByMonth}" var="ideaType">${ideaType.value},</c:forEach>],
	        label: "Total Idea",
	        type: "line",
	        borderColor: "#716BB9",
	        fill: false
	      },{
  	          label: "Idea By Head Quarter",
  	          type: "line",
  	          borderColor: "#3e95cd",
  	          data: [<c:forEach items="${headOfficeIdea}" var="ideaType">${ideaType.value},</c:forEach>],
  	          fill: false
  	        },{
  	          label: "Idea By Other Offices",
  	          type: "line",
  	          borderColor: "#3370B7",
  	          data: [<c:forEach items="${otherofficeidea}" var="ideaType">${ideaType.value},</c:forEach>],
  	          fill: false
  	        }
	    ]
	  },
	  options: {
	    title: {
	      display: false,
	      text: ''
	    }
	  }
});

new Chart(document.getElementById("idea_like"), {
	  type: 'line',
	  data: {
	    labels: [<c:forEach items="${idealikeschart}" var="ideaType">"${ideaType.typename}",</c:forEach>],
	    datasets: [{ 
	        data: [<c:forEach items="${ideathankschart}" var="ideaType">${ideaType.value},</c:forEach>],
	        label: "Thank You",
	        borderColor: "#3e95cd",
	        fill: false
	      }, { 
	        data: [<c:forEach items="${idealikeschart}" var="ideaType">${ideaType.value},</c:forEach>],
	        label: "Kudos",
	        borderColor: "#8e5ea2",
	        fill: false
	      }, { 
	        data: [<c:forEach items="${avarageChart}" var="ideaType">${ideaType},</c:forEach>],
	        label: "Average",
	        borderColor: "#3cba9f",
	        fill: false
	      }
	    ]
	  },
	  options: {
	    title: {
	      display: false,
	      text: ''
	    }
	  }
});

/* $('#').vectorMap({
    map: 'world_en',
    backgroundColor:'#fff',
    color: '#ffffff',
    hoverOpacity: 0.7,
    selectedColor: '#666666',
    enableZoom: false,
    showTooltip: true,
    scaleColors: ['#C8EEFF', '#006491'],
    normalizeFunction: 'polynomial'
}); */

$(function() {
    $(".dial").knob();
});
</script>
<script>$(document).ready(function(){$(".np-portlate-fix01").removeClass("np-portlate-fix01").addClass("np-portlate-fix0111")});</script>

<script>
    // example data from server
   
      var series = [
        ["USA",${USA}],["GBR",${GBR}],["UKR",${UKR}],["ARG",${ARG}],["CHN",${CHN}],
        ["DEU",${DEU}]
        ];


    // Datamaps expect data in format:
    // { "USA": { "fillColor": "#42a844", numberOfWhatever: 75},
    //   "FRA": { "fillColor": "#8dc386", numberOfWhatever: 43 } }
    var dataset = {};

    // We need to colorize every country based on "numberOfWhatever"
    // colors should be uniq for every value.
    // For this purpose we create palette(using min/max series-value)
  
    var onlyValues = series.map(function(obj){ return obj[1]; });
  
    var minValue = Math.min.apply(null, onlyValues),
            maxValue = Math.max.apply(null, onlyValues);
    var paletteScale = d3.scale.linear()
            .domain([minValue,maxValue])
            .range(["#f1f1f3","#2b261f"]); 

  
    series.forEach(function(item){ //
        var iso = item[0],
                value = item[1];
        dataset[iso] = { numberOfThings: value, fillColor: paletteScale(value) };
    });

    // render map
    new Datamap({
        element: document.getElementById('vmap'),
        projection: 'mercator', // big world map
        height:250,
        fills: { defaultFill: '#f8f8f8' },
        data: dataset,
        geographyConfig: {
            borderColor: '#DEDEDE',
            highlightBorderWidth: 2,
          
            highlightFillColor: function(geo) {
                return geo['fillColor'] || '#f8f8f8';
            },
            // only change border
            highlightBorderColor: '#B7B7B7',
            // show desired information in tooltip
            popupTemplate: function(geo, data) {
                return ['<div class="hoverinfo">',
                    '<strong>', geo.properties.name, '</strong>',
                    '<br>Idea: <strong>', data.numberOfThings, '</strong>',
                    '</div>'].join('');
            }
        }
    });
</script>
