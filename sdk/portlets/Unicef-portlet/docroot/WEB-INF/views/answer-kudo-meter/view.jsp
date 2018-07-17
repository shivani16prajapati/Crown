<%@include file="../init.jsp"%>
<%-- <div id='<portlet:namespace/>kudoMeterMainDiv'>
	<div style="width: 100%; margin: 0 auto;">
		<div class="km-graybox">
			<div class="km-dal-yellow">
				<div class="km-light-yellow" style="width:${kutoDisplayCount}%;"></div>
			</div>
		</div>
	</div>
	<div>
		<span style="display:block;"><b>UpVote This Week: ${weeklyIdeaLikeCount}</b></span>
		<span style="display:block;"><b>Total UpVote : ${totalKudos}</b></span>
		<!-- <span style="display:block;">Kudos To Give: 2</span> -->
	</div>		
</div> --%>

<div id='<portlet:namespace/>kudoMeterMainDiv'>

<div style="font-size: 18px;color:#fff;font-weight: bold;padding-bottom: 11px;background-color: #c4c5c7;border-radius:10px 10px 0 0;line-height:18px;padding-top:11px;padding-left:12px;">Kudo Meter</div>
	<div class="ku-m-box clearfix" >
	      <div class="ku-m-whitebar">
	        	<div class="ku-m-whitebar-progresor" style="width:${kutoDisplayCount}%;"></div>
	      </div>
	      <div class="ku-m-text01 clearfix"> <span>Weekly Upvotes:</span><span>${weeklySolutionLikeCount}</span> </div>
	      <div class="ku-m-text01 clearfix"> <span>Total Upvotes:</span><span>${totalKudos}</span> </div>
	 </div>
</div>