<%@include file="../init.jsp"%>
<portlet:resourceURL var="ideaViewURL" id="ideaView" />
<portlet:resourceURL var="postWorkflowLikeURL" id="workflowLike"/>
<portlet:resourceURL var="taskStatusURL" id="taskStatus"/>
<portlet:resourceURL var="postWorklowCommentUrl" id="workflowComment"/>
<script type="text/javascript">
$(document).ready(function () {
	 displayIdea();
});
var ideaCount = 0;
function  displayIdea(){
	$('#<portlet:namespace/>loadMoreImage').hide();
	ideaCount = ideaCount + 1;	
	$.ajax({
	    url :"<%=ideaViewURL%>",
	    data : {
	    'ideaCount' : ideaCount
	   },
	    success : function(result) {
	    	if(ideaCount > 1){
	    		var oldHtml = $('#<portlet:namespace/>listOfIdea').html();
	    		$('#<portlet:namespace/>listOfIdea').html(oldHtml + result);
	    	}else{
	    		document.getElementById('<portlet:namespace/>listOfIdea').innerHTML = result;	
	    	}
	    	$('#<portlet:namespace/>loadMoreImage').show();
	    	 var displayAns = ideaCount * 2;
	    	 var totalAns = $('#<portlet:namespace/>workflowSize').val();
	    	 if(displayAns >= totalAns){
				$('#<portlet:namespace/>loadMoreImage').hide();
				$('#<portlet:namespace/>noMoreImage').show();
	    	 }
	   },
	   async: false
	 });
}
var currentTaskId = 0;
<portlet:namespace />acceptTask = function(taskId){
	 currentTaskId = taskId;
	$( "#<portlet:namespace />acceptTaskConfirmDialog").dialog({
		title:'<liferay-ui:message key="confirmation"/>',
	    width: 'auto',
	    height: 'auto',
	    top: 136,
	    left: 183,
	    modal: true,
	    resizable: false,
	    close: function(event, ui) {
			$(this).dialog("destroy");
			$("#<portlet:namespace />noOfDays").val('');
		} 
	 });
}
<portlet:namespace />closePopUp=function(){
	$("#<portlet:namespace />acceptTaskConfirmDialog").dialog( "close" );
	$("#<portlet:namespace />noOfDays").val('');
}
<portlet:namespace />changeTaskStatus = function(){
	var e = document.getElementById("<portlet:namespace />noOfDays");
	var noOfDay = e.options[e.selectedIndex].value;
	$.ajax({
	    url : "<%=taskStatusURL%>",
	    data : {
	    'noOfDay' : noOfDay,
	    'taskId' : currentTaskId
	   },
	    success : function(result) {
	    	 $('#<portlet:namespace />workflowStatus_'+currentTaskId).html('');
	    	 $('#<portlet:namespace />workflowStatus_'+currentTaskId).html(result);
	    	 $("#<portlet:namespace />acceptTaskConfirmDialog").dialog( "close" );
	    	 
	   },
	   async: false
	 });
}
<portlet:namespace />postLike = function(taskId){
	var isUserLike;
	 if($('#<portlet:namespace />likeIcon_'+taskId).hasClass('active')){
			isUserLike = true;
		  }else{
			  isUserLike = false;
		  }
	  $.ajax({
		   url :"<%=postWorkflowLikeURL.toString()%>",
		   data : {
			   taskId : taskId,
			   isUserLike : isUserLike
			},
			
		   success : function(response) {
			   if(isUserLike){
				   $('#<portlet:namespace />likeIcon_'+taskId).removeClass('active');
				 }else{
				   $('#<portlet:namespace />likeIcon_'+taskId).addClass('active');
			    }
			   
			   $('#<portlet:namespace />noOfLikes_'+taskId).html('');
			   $('#<portlet:namespace />noOfLikes_'+taskId).html(response);
			   
			},
			async: false
		});
}
<portlet:namespace />openCommentDiv = function(taskId){
	$("#<portlet:namespace/>workflowCommentTxt_"+taskId ).val('');
	 $('#<portlet:namespace/>cmtDiv_'+taskId).removeClass("has-error");
		if ($("#<portlet:namespace/>commentDiv_"+taskId).css('display') == 'none') {
		$("#<portlet:namespace/>commentDiv_"+taskId).toggle(function(){
			   $("#<portlet:namespace/>commentDiv_"+taskId).slideDown();
			});
		}else{
		  $("#<portlet:namespace/>commentDiv_"+taskId).toggle(function(){
				$("#<portlet:namespace/>commentDiv_"+taskId).slideUp();
			});
		}
}
<portlet:namespace />postComment = function(taskId){
	 var commentText = $('#<portlet:namespace/>workflowCommentTxt_'+taskId).val();
	 if($.trim(commentText) == ''){
		  $('#<portlet:namespace/>cmtDiv_'+taskId).addClass("has-error");
		  return;
		}
	 $.ajax({
		    url :"<%=postWorklowCommentUrl%>",
		    data : {
		    'taskId' : taskId,
		    'commentText' : commentText,
		    },
		    success : function(result) {
		     $('#<portlet:namespace/>workflowCommentTxt_'+taskId).val('');
		     $('#<portlet:namespace/>cmtDiv_'+taskId).removeClass("has-error");
		     $('#<portlet:namespace/>commentActivity_'+taskId).val('');
		     
		     
		     $('#<portlet:namespace />commentActivity_'+taskId).html(result);
		     
		     var commentCount = $('#<portlet:namespace/>commentCount_'+taskId).val();
		    $('#<portlet:namespace/>noOfComments_'+taskId).text(commentCount);
		   },
		  async: false
		 });
}
</script>
