<%@page pageEncoding="UTF-8"%>
<%@include file="../init.jsp"%>



<portlet:defineObjects />

<portlet:resourceURL id="editProfile" var="editProfile" ></portlet:resourceURL>
<portlet:resourceURL id="changeProfile" var="changeProfile" ></portlet:resourceURL>
<portlet:resourceURL id="getnewsfeed" var="getnewsfeed" ></portlet:resourceURL>
<portlet:resourceURL id="gethotidea" var="gethotidea" ></portlet:resourceURL>



<liferay-theme:defineObjects />

<fmt:formatDate pattern="dd/MM/yyyy" value="${before_year}" var="beforeyear" />
<fmt:formatDate pattern="dd/MM/yyyy" value="${today_date}" var="today" />

<script src="<%=request.getContextPath()%>/js/jquery.min-1.11.3.js" type="text/javascript"></script>    
<script src="<%=request.getContextPath()%>/js/jquery.fancybox.js" type="text/javascript"></script>   
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.fancybox.css">  
<script src="<%=request.getContextPath()%>/js/chart_util.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.bundle.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/css/CustomeMIstyle.css">
<script src="<%=request.getContextPath()%>/js/Fileupload.js" type="text/javascript"></script>  
<script src='https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js'></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.6/jstz.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment-timezone/0.5.16/moment-timezone.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment-timezone/0.5.16/moment-timezone-with-data.min.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery.age/1.2.4/jquery.age.min.js'></script>

<body style="center" class="ng-scope nav-static  chat-sidebar-container pace-done" ng-controller="App" sn-demo="">

<main id="content" class="mi-container view-animate fade-up ng-scope" role="main" data-ui-view>  <!-- content  -->
<h1 class="mi-title01 ng-scope">My Innovation</h1>
<div class="row ng-scope">
  <div class="col-sm-6 col-md-3">
    <section class="widget text-white ng-isolate-scope mi-fbox01" ng-class="" icon="flash"   data="vm.ideas" style="">
  <div class="widget-body clearfix">
		<div class="mi-fboxtitle01"> <span><img alt="" src="<%=request.getContextPath()%>/images/mi-f-icon01.png"></span> <span>My Ideas</span>     </div>
		<div class="col-xs-6"><div class="mi-fboxtitle02"><span>${lastWeek_ideas}</span><span>Last Week </span></div>  </div>
		<div class="col-xs-6 clearfix" ><div class="mi-fboxtitle03"> <span>${total_ideas}</span><span>Total</span> </div> </div>
  </div>
</section>
  </div>
  <div class="col-sm-6 col-md-3">
    <section class="widget text-white ng-isolate-scope mi-fbox02" ng-class=" " icon="heart"  data="vm.thanks" style="">
<div class="widget-body clearfix">
		<div class="mi-fboxtitle01-02"> <span><img alt="" src="<%=request.getContextPath()%>/images/mi-f-icon02.png"></span> <span>Endorsed</span>     </div>
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
		<div class="mi-fboxtitle01-04"> <span><img alt="" src="<%=request.getContextPath()%>/images/mi-f-icon04.png"></span> <span>Upvotes</span>     </div>
		<div class="col-xs-6"><div class="mi-fboxtitle02"><span>${lastWeek_upVotes}</span><span>Last Week </span></div>  </div>
		<div class="col-xs-6 clearfix" ><div class="mi-fboxtitle03"> <span>${total_upVotes}</span><span>Total</span> </div> </div>
 </div>
</section>
  </div>
</div>

<div class="row ng-scope">
<div class="col-md-6">
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header"> 
	      <h3 class="fw-thin mi-fboxtitle04"> My Inno News Feed </h3> 
	      <div class="end-date text-brown text-right pull-right mt-n-lg"> <p class="value3">24/11/15 - 24/12/15</p> </div> 
	  </header>
      <div class="widget-body">
		<div >
		
		<table class="table mi-tablefix01 ">
  		
			<tbody id="news_feed">
			
			</tbody>
			
		</table>
		
		</div>
      </div>
    </section>
  </div>
  <div class="col-md-6">
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header">
        <div class="mi-fboxtitle05">
          <span><img alt="" src="<%=request.getContextPath()%>/images/mi-box-Ticon-05.png"></span>  
          <span>My Profile</span>
        </div>
        <div class="widget-controls">
          <div class="mi-userimgbox"> 
          	<img alt="" id="userprofilepic" src="${userImagUrl}">
          	<div class="user-prf-picedit ani-1">
	          	<label for="file-upload" class="custom-file-upload"> <i class="glyphicon glyphicon-pencil"></i></label>
				<input id="file-upload" name ="file-upload" type="file"  onchange='updateProfileImage("file-upload")'/>
          	</div>
          </div>
        </div>
      </header>
      <div class="widget-body Np-table-responsive">
	      
	     <a href="javascript:showProfileDetail();">Edit Profile </a>
	      
        <table class="table mi-tablefix02">
  		
  		<tbody> 
        <tr>
      <td class="mi-table-text01">Name:</td>
      <td class="mi-table-text02">${user.getFullName()}</td>
    </tr>
    <tr>
      <td class="mi-table-text01">Current Role:</td>
      <td class="mi-table-text02">${userData.getCurrent_role()}</td>
    </tr>
    <%-- <tr>
      <td class="mi-table-text01">LOB:</td>
      <td class="mi-table-text02">${userData.getLob()}</td>
    </tr> --%>
    <tr>
      <td class="mi-table-text01">Location:</td>
      <td class="mi-table-text02">
                       <c:if test="${userData.getUserCountry() eq 'USA' }">North America</c:if>
			           <c:if test="${userData.getUserCountry() eq 'UKR' }">Europe</c:if>
			           <c:if test="${userData.getUserCountry() eq 'GBR' }">United Kingdom</c:if>
			           <c:if test="${userData.getUserCountry() eq 'ARG' }">Argentina</c:if>
			           <c:if test="${userData.getUserCountry() eq 'CHN' }">China</c:if>
			           <c:if test="${userData.getUserCountry() eq 'DEU' }">Germany</c:if>    
    
      </td>
    </tr>
    <%-- <tr>
      <td class="mi-table-text01">Department:</td>
      <td class="mi-table-text02">${userData.getDepartment()}</td>
    </tr> --%>
    <tr>
       <td class="mi-table-text01">Department:</td>
       <td class="mi-table-text02">
    					<c:if test="${userData.getDepartment() eq 'Engineering'}">Engineering</c:if>
			           <c:if test="${userData.getDepartment() eq 'Sales'}">Sales</c:if>
			           <c:if test="${userData.getDepartment() eq 'Administration'}">Administration</c:if>
			           <c:if test="${userData.getDepartment() eq 'Supply Chain'}">Supply Chain</c:if>
			           <c:if test="${userData.getDepartment() eq 'Aftermarket'}">Aftermarket</c:if>
    	</td>
    </tr>
    <tr>
      <td class="mi-table-text01">Years with Crown:</td>
      <td class="mi-table-text02">${years}</td>
    </tr>
    <%-- <tr>
      <td class="mi-table-text01">Works for:</td>
      <td class="mi-table-text02">${userData.getWorks_for()}</td>
    </tr> --%>
    <tr>
      <td class="mi-table-text01">Email:</td>
      <td class="mi-table-text02">${user.getEmailAddress()}</td>
    </tr>
    <tr>
      <td class="mi-table-text01">Expertise:</td>
      <td class="mi-table-text02">${userData.getExpertise()} </td>
    </tr>
    <tr>
      <td class="mi-table-text01">Experience:</td>
      <td class="mi-table-text02">${userData.getExperience()} years</td>
    </tr>
    <tr>
      <td class="mi-table-text01">Skillset:</td>
      <td class="mi-table-text02">${userData.getSkill_set()}</td>
    </tr>
    <tr>
      <td class="mi-table-text01">Patents:</td>
      <td class="mi-table-text02">${userData.getPatents()}</td>
    </tr>
    <tr>
      <td class="mi-table-text01">What I do for fun:</td>
      <td class="mi-table-text02">${userData.getFun()}</td>
    </tr>
    <%-- <tr>
      <td class="mi-table-text01">Country:</td>
      <td class="mi-table-text02">
                       <c:if test="${userData.getUserCountry() eq 'USA'}">USA</c:if>
			           <c:if test="${userData.getUserCountry() eq 'GBR'}">United Kingdom</c:if>
			           <c:if test="${userData.getUserCountry() eq 'ARG'}">Argentina</c:if>
			           <c:if test="${userData.getUserCountry() eq 'BRA'}">BRAZIL</c:if>
			           <c:if test="${userData.getUserCountry() eq 'CHN'}">CHINA</c:if>
			           <c:if test="${userData.getUserCountry() eq 'DEU'}">GERMANY</c:if>    
    
      </td>
    </tr> --%>

  </tbody>

		</table>
      </div>
    </section>
  </div>
  
  
</div>


<div class="row ng-scope">
  <div class="col-md-6">
    <section class="widget widget-chart-stats-simple ">
      <header class="widget-header">
        <h3 class="fw-thin mi-fboxtitle04">
          My Ideas Hotness
        </h3>
        <div class="end-date text-brown text-right pull-right mt-n-lg">
          <p class="value3">${beforeyear} - ${today}</p>
        </div>
      </header>
      <div class="widget-body">
        <canvas id="idea_hotness"></canvas>
      </div>
    </section>
  </div>  

    <div class="col-md-6">
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header"> 
	      <h3 class="fw-thin mi-fboxtitle04"> Ideas By Type </h3> 
	      <div class="end-date text-brown text-right pull-right mt-n-lg"> <p class="value3">24/11/15 - 24/12/15</p> </div> 
	  </header>
      <div class="widget-body">
       <!--  <canvas id="idea_type" height="465" width="770"> </canvas> -->
		<div id="idea_type" style="text-align: center;"> </div>
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
  <div class="col-md-6" id="most-hotidea">
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
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header">
        <h3 class="fw-thin mi-fboxtitle04">
          Thank You’s & Kudos
        </h3>
        <div class="end-date text-brown text-right pull-right mt-n-lg">
          <p class="value3">${beforeyear} - ${today}</p>
        </div>
      </header>
      <div class="widget-body">
        <canvas id="mylike_chart"></canvas>
      </div>
    </section>
  </div>


</div>



<div class="row ng-scope">
<div class="col-md-6">
    <section class="widget widget-chart-stats-simple">
      <header class="widget-header">
        <div class="mi-fboxtitle05">
          <span><img alt="" src="<%=request.getContextPath()%>/images/mi-box-Ticon-02.png"></span>  <span>Recent Ideas</span>
        </div>
        <div class="widget-controls">
          <a href="#"><i class="glyphicon glyphicon-cog"></i></a>
        </div>
      </header>
      <div class="widget-body Np-table-responsive">
        <table class="table mi-tablefix01 table-hover">
  			<tbody>
  			
  			    <tr>
			      <th scope="col">IDEA NAME</th>
			      <th scope="col">INVENTOR</th>
			      <th scope="col">DATE SUBMITTED</th>
			      <th scope="col">UPVOTES</th>
			      <th scope="col">COMMENTS</th>
			    </tr>
			   
	  			<c:forEach items="${recentIdeasList}" var="recent">
		  			<tr>
				      <td>${recent.getIdeaName()}</td>
				      <td>${recent.getInventorName()}</td>
				      
				      <fmt:formatDate pattern="dd-MM-yyyy" value="${recent.getSubmissionDate()}" var="SubmissionDate" />
				      <td>${SubmissionDate}</td>
				      <td>${recent.getUpVotes()}</td>
				      <td>${recent.getComments()}</td>
				    </tr>
	  			</c:forEach>
	  			<c:forEach begin="1" end="${recentHolder}">
	  				<tr>
						<td>Coming Soon</td>
					    <td>Coming Soon</td>
					    <td>Coming Soon </td>
					    <td>Coming Soon</td>
					    <td>Coming Soon</td>
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
        <div class="mi-fboxtitle05">
          <span><img alt="" src="<%=request.getContextPath()%>/images/mi-box-Ticon-04.png"></span>  <span>My Innovation Training</span>
        </div>
        <div class="widget-controls">
          <a href="#"><i class="glyphicon glyphicon-cog"></i></a>
        </div>
      </header>
      <div class="widget-body Np-table-responsive">
        <table class="table mi-tablefix01 table-hover">
  		
  		<tbody>
    <tr>
      <th scope="col">LESSON</th>
      <th scope="col">TOPIC</th>
      <th scope="col">TYPE</th>
      <th scope="col">COMPLETE</th>
    </tr>
    <tr>
      <td>Field Sorting</td>
      <td>Mark Carlton </td>
      <td>Sorting</td>
      <td> 50</td>
     
    </tr>
    <tr>
      <td>Field</td>
      <td>Mark Carlton </td>
      <td>Sorting</td>
      <td>87 </td>
      
    </tr>
    <tr>
      <td>LESSON Name</td>
      <td>TOPIC </td>
      <td>Sorting</td>
      <td>66 </td>
     
    </tr>
  </tbody>

		</table>
      </div>
    </section>
  </div>

</div>
<div class="row ng-scope">
<div class="col-md-6" id="most-thank">
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
  </div>









<!-- for fancybox displaying user details -->
<div id="profileUserDetails" style="display:none;">

</div>


</main>
</body>
<script>



var tz = jstz.determine();
var timezone = tz.name();
var current_time =  moment().tz(timezone).format('DD/MM/YYYY HH:mm:ss');


function getnewsfeed(start,end){
	data = {start:start,end:end,current_time:current_time}
	fr_CallAjax('${getnewsfeed}', data, function(html){
		$('#news_feed').html(html);
		$('.age').age();
	})	
	
}

function showhotidea(start,end){
	data = {start:start,end:end};
	fr_CallAjax('${gethotidea}', data, function(html){
		$('#hotidealist').html(html);
	})	
	
}

showhotidea(0,3)
getnewsfeed(0,10);

	function updateProfileImage(id){
		$.ajaxFileUpload({
			url : '${changeProfile}',
		  	secureuri: false,
		  	fileElementId: id,
		  	dataType: 'json',
		  	success :function(data, status, e){	
				$("#userprofilepic").attr("src",data.userProfilePicPath);
			},error: function(data, status, e) {
				
			}
		});	
	}
	   
	function showProfileDetail(){
		var data = {method:"showDetails"};
		fr_CallAjax('${editProfile}', data, function(html) {
			html = html.replace('{}','');
			$('#profileUserDetails').html(html);
			$.fancybox.open("#profileUserDetails");
		})	
	}

	function rearrange(){
		 var notification = $("#news_feed tr");
		 notification.sort(function(a,b){
			   console.log(a.value)
			     a = a.getAttribute("value");
			     b = b.getAttribute("value")	 
			    return b-a;
			});
		 $("#news_feed").html(notification);
	}
	
	 function updateUserDetail(){
			var data = true;
			data = userDataFormValidate();
			if(data){ 
			 	var name = $('#name').val();
				var current_role = $('#current_role').val();
				/* var lob = $('#lob').val(); */
				var department = $('#department').val();
				/* var works = $('#works').val(); */
				var contact = $('#contact').val();
				var expertise = $('#expertise').val();
				var experience = $('#experience').val();
				var skillset = $('#skillset').val();
				var patents = $('#lob').val();
				var fun = $('#fun').val();
				var patents = $('#patents').val();
				var userCountry = $('#userCountry').val();
				
				var checkBox = $('#landingCheckbox').is(':checked');
				console.log(checkBox);
				
				/* var data = {
						method:"saveDetails",  name : name, current_role : current_role, lob : lob, department : department, works : works, contact :contact ,expertise : expertise, experience: experience ,skillset : skillset, patents : patents, fun : fun ,userCountry:userCountry
				}; */
				
				var data = {
						method:"saveDetails", checkBox:checkBox, name : name, current_role : current_role, department : department, contact :contact ,expertise : expertise, experience: experience ,skillset : skillset, patents : patents, fun : fun ,userCountry:userCountry
				};
				fr_CallAjax('${editProfile}', data, function(html) {
					location.reload();
				}) 
				
			}	
		 }


function userDataFormValidate(){
	var filter = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
	var regex = '^[a-zA-Z][a-zA-Z\\s\\,]+$';
	/* if($('#name').val().trim()=="" || $('#current_role').val().trim()=="" || $('#lob').val().trim()=="" || $('#department').val().trim()=="" || $('#works').val().trim()=="" || $('#contact').val().trim()=="" || $('#expertise').val().trim()=="" || $('#experience').val().trim()=="" || $('#skillset').val().trim()=="" || $('#patents').val().trim()=="" || $('#fun').val().trim()==""){
		alert("Please fill all the fields ");
		return false;
    }  */
	
	if($('#name').val().trim()=="" || $('#current_role').val().trim()=="" || $('#department').val().trim()=="" ||  $('#contact').val().trim()=="" || $('#expertise').val().trim()=="" || $('#experience').val().trim()=="" || $('#skillset').val().trim()=="" || $('#patents').val().trim()=="" || $('#fun').val().trim()==""){
		alert("Please fill all the fields ");
		return false;
    } 
	
   if (!($('#current_role').val()).match(regex)) {
		  $("#current_role").focus();
		  alert("Current Role Is Not Proper");
		  return false;
	/* }else if (!($('#lob').val()).match(regex)) {
		  $("#lob").focus();
		  alert("LOB Is Not Proper");
		  return false; */
	/* }else if (!($('#department').val()).match(regex)) {
		  $("#department").focus();
		  alert("Department Is Not Proper");
		  return false; */
	/* }else if (!($('#works').val()).match(regex)) {
		  $("#works").focus();
		  alert("Works Is Not Proper");
		  return false; */
	}else if (!filter.test($('#contact').val())) {
		alert("Email sn not Proper");
		return false;
	}else if (!($('#expertise').val()).match(regex)) {
		  $("#expertise").focus();
		  alert("Expertise Is Not Proper");
		  return false;
	}else if (!($('#experience').val()).match('^[0-9]$')) {
		  $("#experience").focus();
		  alert("Experience Is Not Proper");
		  return false;
	}else if (!($('#patents').val()).match('^[0-9]{1,3}$')) {
		  $("#patent").focus();
		  alert("Patent Is Not Proper");
		  return false;
	}else if (!($('#skillset').val()).match(regex)) {
		  $("#skillset").focus();
		  alert("Skill Set Not Proper");
		  return false;
	}else if (!($('#fun').val()).match(regex)) {
		  $("#fun").focus();
		  alert("For Fun Is Not Proper");
		  return false;
	}else{
		return true;
	}
	
}


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

 
    
</script>
<script>

 
 function idea_by_idea_type(){
     $("#idea_type").html("");

     var dataset = [<c:forEach items="${ideachart}" var="ideaType"><c:if test="${ideaType.typename ne 'Massive disruption potential'}" >
    					 { name: '${ideaType.typename}', percent:${ideaType.value} },
    				</c:if></c:forEach>];
     
     create_chart(dataset);
          
 }
 
idea_by_idea_type();
function idea_by_lof(){
    $("#idea_type").html("");
    var dataset = [<c:forEach items="${ideachartlof}" var="ideaType">
	 { name: '${ideaType.typename}', percent:${ideaType.value} },
	 </c:forEach>];
	
    create_chart(dataset);
}

new Chart(document.getElementById("mylike_chart"), {
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
	


new Chart(document.getElementById("idea_hotness"),{"type":"bar","data":{"labels":[<c:forEach items="${ideaHotnesschart}" var="ideaType">"${ideaType.typename}",</c:forEach>],"datasets":[{"label":"Idea Hotness","data":[<c:forEach items="${ideaHotnesschart}" var="ideaType">${ideaType.floatValue},</c:forEach>],"fill":false,"backgroundColor":"rgba(255, 99, 132, 0.2)","borderColor":"rgb(255, 99, 132)","borderWidth":1}]},"options":{"scales":{"yAxes":[{"ticks":{"beginAtZero":true}}]}}});


function create_chart(dataset){
	
	var pie=d3.layout.pie()
    .value(function(d){return d.percent})
    .sort(null)
    .padAngle(.0);

			var w=465,h=420;
			var outerRadius= w/4;
			var innerRadius= 175;
			var color = d3.scale.category10();
			var arc=d3.svg.arc()
			    .outerRadius(outerRadius)
			    .innerRadius(innerRadius);
			var svg=d3.select("#idea_type")
			    .append("svg")
			    .attr({
			        width:w,
			        height:h,
			        class:'shadow'
			    }).append('g')
			    .attr({
			        transform:'translate('+w/2+','+h/2+')'
			    });

			var path=svg.selectAll('path')
			    .data(pie(dataset))
			    .enter()
			    .append('path')
			    .attr({
			        d:arc,
			        fill:function(d,i){
			            return color(d.data.name);
			        } 
			    });
			
			path.transition()
			    .duration(1000)
			    .attrTween('d', function(d) {
			        var interpolate = d3.interpolate({startAngle:0, endAngle: 0}, d);
			        return function(t) {
			            return arc(interpolate(t));
			        };
			    });
			
			
			var restOfTheData=function(){
			 	var text=svg.selectAll('text')
			        .data(pie(dataset))
			        .enter()
			        .append("text")
			        .transition()
			        .duration(200)
			        .attr("transform", function (d) {
			            return "translate(120)";
			        })
			        .attr("dy", "4em")
			        .attr("text-anchor","")
			        .text()
			        .style({
			            fill:'#fff',
			            'font-size':'7px'
					
			       });
			
			var legendRectSize=12;
			var legendSpacing=6;
			var legendHeight=legendRectSize+legendSpacing;
			
			
			var legend=svg.selectAll('.legend')
			        .data(color.domain())
			        .enter()
			        .append('g')
			        .attr({
			            class:'legend',
			            transform:function(d,i){
			                return 'translate(-85,' + ((i*legendHeight)-65) + ')';
			            }
			        });
			legend.append('rect')
			        .attr({
			            width:legendRectSize,
			            height:legendRectSize,
			            rx:20,
			            ry:20
			        })
			        .style({
			            fill:color,
			            stroke:color
			        });
			
			legend.append('text')
			        .attr({
			            x:20,
			            y:15
			        })
			        .text(function(d){
			            return d;
			        }).style({
			            fill:'#929DAF',
			            'font-size':'15px'
			        });
			};
			
			setTimeout(restOfTheData,1000);
}


</script>
<script>$(document).ready(function(){$(".np-portlate-fix01").removeClass("np-portlate-fix01").addClass("np-portlate-fix0111")});</script>
</div>
