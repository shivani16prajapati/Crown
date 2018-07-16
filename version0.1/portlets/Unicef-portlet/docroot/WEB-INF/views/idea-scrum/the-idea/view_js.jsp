<%@include file="../../init.jsp"%>
<portlet:resourceURL var="ideaLikeURL" id="ideaLikeURL">
	<portlet:param name="idea" value="like"/>
</portlet:resourceURL>

<portlet:resourceURL var="postCommentUrl" id="postComment" />
<portlet:resourceURL var="ideaCommentViewURL" id="ideaCommentView" />
<portlet:resourceURL var="ideaCommentVoteURL" id="ideaCommentVote"/>

<script type="text/javascript">
if("${historyListSize}" > 1 ){
	  var slider = $("#slider").slider({
	   min: 1,
	      max: "${historyListSize}",
	      range: "min",
	   value : "${historyListSize}"
	  });
	 }
    
	$("#slider").on( "slidechange", function( event, ui ) {
		$('#'+ui.value).removeClass("div-off");
		$('#'+ui.value).addClass("div-on");
	} );
	
	$("#slider").on( "slidechange", function( event, ui ) {
        $('.unicef-slide').removeClass("div-on");
		$('.unicef-slide').addClass("div-off");
		$('#slider_'+ui.value).addClass("div-on");
	} );
  
  
<portlet:namespace />ideaLike = function(ideaId){
var like= document.getElementById('<portlet:namespace />likeUnlikeText').innerHTML;
$.ajax({
	   url :"<%=ideaLikeURL.toString()%>",
			data : {
				like : like,
				ideaId : ideaId,
			},
			success : function(data) {
					result =data;
					document.getElementById('<portlet:namespace/>ideaLike').innerHTML = result;
				if(like == "UpVote"){
					document.getElementById('<portlet:namespace />likeUnlikeText').innerHTML = "DownVote";
				}else if(like == "DownVote"){
					document.getElementById('<portlet:namespace />likeUnlikeText').innerHTML = "UpVote";
				}
			},
			 async: false
		});
}


$(document).ready(function () {
	 displayIdeaCommentView("${idea.ideaId}");
});
function  displayIdeaCommentView(ideaId){
	 $.ajax({
	    url :"<%=ideaCommentViewURL%>",
	    data : {
	    'ideaId' : ideaId,
	   },
	    success : function(result) {
	     $('#<portlet:namespace/>ideaCommentView').html('');
	     $('#<portlet:namespace/>ideaCommentView').html(result);
	     $('#<portlet:namespace/>commentCountDisplay').text($('#<portlet:namespace/>ideaCommentCount').val());  
	   },
	   async: false
	 });
}

<portlet:namespace />ideaCommentVote = function(commentId,ideaId){
	 $.ajax({
		    url :"<%=ideaCommentVoteURL%>",
		    data : {
		    'ideaId' : ideaId,
		    'commentId':commentId,
		   },
		   dataType:"JSON",
		   type:'get',
		    success : function(result) {
				Liferay.fire('ideaCommentVoteURL', {
					userData:result
		    		});
				document.getElementById('<portlet:namespace/>ideaVoteSpan_'+commentId).innerHTML = result.commentVoteCount;
		    	if(result.thankYou == false){
		    		$('#<portlet:namespace/>thankYouImage_'+commentId).addClass('np-d-disabled');
		    		 $('#<portlet:namespace/>commentVote_'+commentId).addClass('np-d-disabled');
					$('#<portlet:namespace />commentVote_'+commentId).removeAttr("onclick");
		    	}
		   },
		   async: false
		 });
}
</script>