<%@include file="../init.jsp"%>
<%@include file="../init.jsp"%>
<fmt:formatDate pattern="dd MMM, yyyy" value="${currentDate}" var="formattedSubmissionDate" />
<div style="padding:3px; border:2px solid #f4f4f4; border-radius:4px; -webkit-border-radius:4px;" class="container">

<div class="np-pivot-box clearfix">

	Innovation Pivot Analysis - ${formattedSubmissionDate}
 	<div class="np-link-rightlink">
		<div class="np-nc-link-rightlink-part2 clearfix">
			<a href="#">calender</a>
		</div>
		<div class="np-nc-link-rightlink-part1 clearfix">
			<a href="#" class="active">2014</a>
			<a href="#">today</a>
			<a href="#">7 days</a>
			<a class="fancybox" href="#decision-pad">30 days</a>
			<a href="#">60 days</a>
			<a href="#">90 day</a>
		</div>
	</div>
</div>
 
  <div class="row clearfix">
    <div class="col-xs-12 cs-graph-img-fix">
    	<img alt="" src="<%=request.getContextPath()%>/images/grap-001.jpg" class="img-responsive">
    </div>
    <div class="col-sm-4 col-xs-12">
    	<img alt="" src="<%=request.getContextPath()%>/images/grap-002.jpg">
    </div>
    <div class="col-sm-4 col-xs-12">
    	<img alt="" src="<%=request.getContextPath()%>/images/grap-003.jpg">
    </div>
    <div class="col-sm-4 col-xs-12">
    	<img alt="" src="<%=request.getContextPath()%>/images/grap-004.jpg">
    </div>
  </div>
 
</div>
<div id="decision-pad" style="display: none;">
	<img alt="" src="<%=request.getContextPath()%>/images/project_detail.jpg">
</div>
<script type="text/javascript">
  $(document).ready(function() {
    $('.fancybox').fancybox();
   });
 </script>

<%@ include file="view_js.jsp"%>