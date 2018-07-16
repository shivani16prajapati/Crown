<%@include file="../../init.jsp"%>

<portlet:resourceURL var="solutionAnswerVoteURL" id="solutionAnswerVoteURL">
	<portlet:param name="solutionAnswer" value="vote"/>
</portlet:resourceURL>

<portlet:resourceURL var="postSolutionCommentUrl" id="postSolutionComment" />
<portlet:resourceURL var="solutionCommentViewURL" id="solutionComment" />
<portlet:resourceURL var="solutionAnswerViewURL" id="solutionAnswer" />
<portlet:resourceURL var="postSolutionAnswerUrl" id="postAnswers" />
<portlet:resourceURL var="solutionAnswerCommentURL" id="solutionAnswerCmtDiv"/>
<portlet:resourceURL var="postAnswerCommentURL" id="postAnswerComment"/>
<script type="text/javascript">
<portlet:namespace />solutionAnswerVote = function(solutionAnswerId){
	var answerVote = $('#<portlet:namespace />answerVoteTextSpan_'+solutionAnswerId).text();
	var solutionId = "${solution.solutionId}";
	$.ajax({
		   url :"<%=solutionAnswerVoteURL.toString()%>",
				data : {
					answerVote : answerVote,
					solutionAnswerId : solutionAnswerId,
					solutionId : solutionId
				},
				dataType:"JSON",
				type:'POST',
				success : function(result) {
				$('#<portlet:namespace />answerUpVoteSpan_'+solutionAnswerId).text(result.answerVoteCount);
				$('#<portlet:namespace />answerVoteSpan_'+solutionAnswerId).text(result.answerVoteCount);
				$('#<portlet:namespace/>wantAnswerCountDisplay').text(result.wantAnswerCount);
				
				if(result.answerVoteCount > 0){
					$('#<portlet:namespace />voteDisplaySpan_'+solutionAnswerId).show();
					$('#<portlet:namespace />noOfUserLike_'+solutionAnswerId).text(result.answerVoteCount + ' Upvotes By');
				    $('#<portlet:namespace />upvotesUsersList_'+solutionAnswerId).text(result.upvoteDisplayUser);
				    document.getElementById('<portlet:namespace />upvoteUserMore_'+solutionAnswerId).title = result.upvoteMoreUser;	
					
				    /* display more link of upvote userlist if 5 user like this  */
				    if(result.answerVoteCount > 5){
				    	$('#<portlet:namespace />upvoteUserMore_'+solutionAnswerId).show();
				    }else{
				    	$('#<portlet:namespace />upvoteUserMore_'+solutionAnswerId).hide();
				    }
				}else{
					$('#<portlet:namespace />voteDisplaySpan_'+solutionAnswerId).hide();
				}
				
			    //$(".cg-ans-more-tooltip").tooltip();
				if(answerVote == "UpVote"){
					$('#<portlet:namespace />answerVoteTextSpan_'+solutionAnswerId).text('DownVote');	
				}else if(answerVote == "DownVote"){
					$('#<portlet:namespace />answerVoteTextSpan_'+solutionAnswerId).text('UpVote');
				}
				}
			});
	}
	
$(document).ready(function () {
	 displaySolutionCommentView("${solution.solutionId}");
	 displaySolutionAnswerView("${solution.solutionId}", 'getMore');
	 /* set the tool tip of user upvote name */
	 $(".cg-ans-more-tooltip").tooltip();
});

function  displaySolutionCommentView(solutionId){
	 $.ajax({
	    url :"<%=solutionCommentViewURL%>",
	    data : {
	    'solutionId' : solutionId,
	   },
	    success : function(result) {
	    	 $('#<portlet:namespace/>solutionCommentView').html(''); 
	    	 $('#<portlet:namespace/>solutionCommentView').html(result);
	    	 var commentCount = $('#<portlet:namespace/>solutionCommentCount').val();
	    	 if(commentCount > 1){
	    		 $('#<portlet:namespace/>commentText').text('<liferay-ui:message key="comments"/>');
	    	 }else{
	    		 $('#<portlet:namespace/>commentText').text('<liferay-ui:message key="comment"/>');
	    	 }
	    	 
	    	 $('#<portlet:namespace/>commentCountDisplay').text(commentCount);
	   }
	 });
}

var ansCount = 0;
function  displaySolutionAnswerView(solutionId, type){
	if(type == 'getMore'){
		ansCount = ansCount + 1;	
	}
	$.ajax({
	    url :"<%=solutionAnswerViewURL%>",
	    data : {
	    'solutionId' : solutionId,
	    'ansCount' : ansCount,
	    'ajexType' :type
	   },
	    success : function(result) {
	    	if(type == 'postAns'){
	    		var oldAnsHtml = $('#<portlet:namespace/>solutionAnswerView').html();
	    		$('#<portlet:namespace/>solutionAnswerView').html(result + oldAnsHtml);
	    	}else{
	    		$('#<portlet:namespace/>solutionAnswerView').append(result);	
	    	}
	    	 var answerCount = $('#<portlet:namespace/>solutionAnswerCount').val();
	    	 $('#<portlet:namespace/>answerCount').text(answerCount);
	    	 $('#<portlet:namespace/>answerCountDisplay').text(answerCount);
	    	 
	    	 var displayAns = ansCount * 3;
	    	 var totalAns = $('#<portlet:namespace/>solutionAnswerSize').val();
	    	 if(displayAns >= totalAns){
				$('#<portlet:namespace/>loadMoreImage').hide();
				$('#<portlet:namespace/>noMoreImage').show();
	    	 }
	   }
	 });
}
<portlet:namespace />solutionComment = function(){
	$("#<portlet:namespace/>solutionCommentTxt" ).val('');
		if ($("#<portlet:namespace/>commentDiv" ).css('display') == 'none') {
		$("#<portlet:namespace/>commentDiv").toggle(function(){
			   $("#<portlet:namespace/>commentDiv").slideDown();
			});
		}else{
		  $("#<portlet:namespace/>commentDiv").toggle(function(){
				$("#<portlet:namespace/>commentDiv").slideUp();
			});
		}
}
<portlet:namespace />soltionAnswerCommentDiv=function(solutionAnswerId){
	var solutionId = "${solution.solutionId}";
	$.ajax({
		   url :"<%=solutionAnswerCommentURL.toString()%>",
				data : {
					solutionAnswerId : solutionAnswerId,
					solutionId : solutionId
				},
				type:'POST',
				success : function(result) {
					$('#<portlet:namespace/>solutionAnswerComments_'+solutionAnswerId).html(result);
					
					if ($('#<portlet:namespace/>answerCommentDiv_'+solutionAnswerId).css('display') == 'none') {
						$('#<portlet:namespace/>answerCommentDiv_'+solutionAnswerId).toggle(function(){
						   $('#<portlet:namespace/>answerCommentDiv_'+solutionAnswerId).slideDown();
						});
					}else{
					  $('#<portlet:namespace/>answerCommentDiv_'+solutionAnswerId).toggle(function(){
							$('#<portlet:namespace/>answerCommentDiv_'+solutionAnswerId).slideUp();
						});
					}
				}
	});
}
<portlet:namespace />postAnswerComment = function(solutionAnswerId){
	var solutionId = "${solution.solutionId}";
	 var answerCommentText = $('#<portlet:namespace/>solutionAnswerCommentTxt_'+solutionAnswerId).val();
	 if($.trim(answerCommentText) == ''){
		  $('#<portlet:namespace/>cmtDiv_'+solutionAnswerId).addClass("has-error");
		  return;
	}
	 $('#<portlet:namespace/>answerCommtCount_'+solutionAnswerId).val('');
	 $.ajax({
		    url :"<%=postAnswerCommentURL%>",
		    data : {
		    'solutionAnswerId' : solutionAnswerId, 
		    'solutionId' : solutionId,
		    'commentText' : answerCommentText,
		    },
		    success : function(result) {
		    	$('#<portlet:namespace/>solutionAnswerComments_'+solutionAnswerId).html(result);
		    	$('#<portlet:namespace/>solutionAnswerCommentTxt_'+solutionAnswerId).val('');
		    	$('#<portlet:namespace/>cmtDiv_'+solutionAnswerId).removeClass("has-error");
		    	 
		    	var answerCommentCount = $('#<portlet:namespace/>solutionAnswerCmtCount_'+solutionAnswerId).val();
		    	$('#<portlet:namespace/>answerCommtCount_'+solutionAnswerId).text(answerCommentCount);
		    	 
		   }
		 });
} 
</script>