<%@include file="../init.jsp"%>
<div id='<portlet:namespace/>kudoMeterMainDiv'>

<div style="font-size: 18px;color:#fff;font-weight: bold;padding-bottom: 11px;background-color: #4da9fc;border-radius:10px 10px 0 0;line-height:18px;padding-top:11px;padding-left:12px;">Kudo Meter</div>
	<div class="ku-m-box clearfix" >
	      <div class="ku-m-whitebar">
	        	<div class="ku-m-whitebar-progresor" style="width:${kutoDisplayCount}%;"></div>
	      </div>
	      <div class="ku-m-text01 clearfix"> <span>Weekly Upvotes:</span><span>${weeklyIdeaLikeCount}</span> </div>
	      <div class="ku-m-text01 clearfix"> <span>Total Upvotes:</span><span>${totalKudos}</span> </div>
	 </div>
</div>