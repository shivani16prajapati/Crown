<%@include file="../init.jsp"%>
<%
long currentUser = themeDisplay.getUserId();
pageContext.setAttribute("currentUser", currentUser);
%>
<%-- <div id='<portlet:namespace/>thankUMeter'>
	<div style="text-align: center;font-size: 20px;font-weight: bold;padding-bottom: 20px;">Thank You Meter</div>
 	<div style="width: 100%; margin: 0 auto;">
		<div class="km-graybox">
	  		<div class="km-dal-red">
	   			<div class="km-light-red" style="width:${ideaThanks}%;"></div>
	   		</div>
	  	</div>
 	</div>
 	<div>
		<span style="display:block;"><b>You have earned ${ideaThanks} Thank You's this week.</b></span>
		<span style="display:block;"><b>And ${ideaThanks} Thank You's in total.</b></span> 
		<c:if test="${ideaCreator eq currentUser}">
			<span style="display:block;"><b>You have ${remainigThanks} Thank You's remaining</b></span>
			<span style="display:block;"><b>to give on this idea.</b></span>
		</c:if>
 	</div>  
</div> --%>
<div id='<portlet:namespace/>thankUMeter'>
<div style="font-size: 18px;color:#fff;font-weight: bold;padding-bottom: 11px;background-color: #4DA9FC;border-radius:10px 10px 0 0;line-height:18px;padding-top:11px;padding-left:12px;">Thank You Meter</div>

<div class="ku-m-box clearfix" >
      <div class="ku-m-orangebar">
        <div id="<portlet:namespace />thankaGraph" class="ku-m-orangebar-progresor" style="width:${ideaThanks}%;"></div>
      </div>
      <div class="ku-m-text02 clearfix"> <span>Weekly Thanks:</span><span id="<portlet:namespace />weeklyThanks">${ideaThanks}</span> </div>
	    <div class="ku-m-text02 clearfix"> <span>Remaining Thanks:</span><span id="<portlet:namespace />remainingThanks"> ${remainigThanks} </span> </div>
      <div class="ku-m-text02 clearfix"> <span>Total Thanks:</span><span id="<portlet:namespace />totalThanks">${totalThanks}</span> </div>
    </div>
</div>
<script>
Liferay.on(
'ideaCommentVoteURL',function(event) {
	$( "#<portlet:namespace />remainingThanks" ).text('');
	$( "#<portlet:namespace />weeklyThanks" ).text('');
	$( "#<portlet:namespace />totalThanks" ).text('');
	
	$( "#<portlet:namespace />remainingThanks" ).text(event.userData.remainingCount);
	$( "#<portlet:namespace />weeklyThanks" ).text(event.userData.weekThanksCount);
	$( "#<portlet:namespace />totalThanks" ).text(event.userData.thanksSize);
	
	$('#<portlet:namespace />thankaGraph').css('width',event.userData.weekThanksCount+'%');
});
</script>