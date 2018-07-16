<%@include file="../init.jsp"%>

<portlet:resourceURL var="updateIdeaInfoURL" id="updateIdeaInfoURL" />

<portlet:resourceURL var="ideaLikeURL" id="ideaLikeURL">
	<portlet:param name="idea" value="like"/>
</portlet:resourceURL>
<portlet:actionURL var="addToTeamURL" name="addToTeam">
	<portlet:param name="action" value="addToTeam"/>
	<portlet:param name="ideaId" value="${idea.ideaId}"/>
</portlet:actionURL>
<portlet:resourceURL var="postCommentUrl" id="postComment" />
<portlet:resourceURL var="ideaCommentViewURL" id="ideaCommentView" />
<portlet:resourceURL var="ideaCommentVoteURL" id="ideaCommentVote"/>
<portlet:resourceURL var="deleteIdeaCommentURL" id="deleteIdeaCommentURL"/>
<portlet:resourceURL var="modifiedCommentURL" id="modifiedCommentURL"/>
<portlet:resourceURL var="ideaEndrosed" id="ideaEndrosed"/>


<script type="text/javascript">

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        
        reader.onload = function (e) {
            $('#<portlet:namespace/>updatedIdeaImageBy').attr('src', e.target.result);
        }
        
        reader.readAsDataURL(input.files[0]);
    }
}

$("#changeIdeaImageWith").change(function(){
   readURL(this);
});

<portlet:namespace/>updateIdeaInfo = function(ideaId, ideaVersionId) {
	
	var orgIdeaDesc = $("#<portlet:namespace/>orgIdeaDesc").val();
	alert(ideaId +' ==== ' + ideaVersionId);
	
	$.ajax({
	    url: "<%=updateIdeaInfoURL.toString()%>",
	    fileElementId : 'changeIdeaImageWith',
	    data:{
			<portlet:namespace />ideaId:ideaId,
			<portlet:namespace />ideaVersionId:ideaVersionId,
			<portlet:namespace />orgIdeaDesc:orgIdeaDesc
		},
	    success: function() {
			alert('success uploaded...');
	    }, 
	    async: false,
	    error: function (error) {
	       alert("File upload faile");
	       console.log('error in sending message: ' + error);
	    }
    });
}

<portlet:namespace/>editIdea = function() { 
	
	$("#<portlet:namespace/>orgIdeaDesc").removeAttr("readonly");
	$("#<portlet:namespace/>orgIdeaDesc").focus();
	
	$("#<portlet:namespace/>updateIdea").removeClass("np-display-off");
	$("#<portlet:namespace/>cancelIdeaUpdate").removeClass("np-display-off");
	$("#changeIdeaImageWith").removeClass("np-display-off");

	$("#<portlet:namespace/>editOrgIdea").addClass("np-display-off");
}

<portlet:namespace/>cancelEditIdea = function() {

	$("#<portlet:namespace/>orgIdeaDesc").val($("#<portlet:namespace />originalIdeaDescription").val());
	$('#<portlet:namespace/>updatedIdeaImageBy').attr('src', $("#<portlet:namespace/>originalIdeaImage").val());
	
	$("#<portlet:namespace/>updateIdea").addClass("np-display-off");
	$("#<portlet:namespace/>cancelIdeaUpdate").addClass("np-display-off");
	$("#changeIdeaImageWith").addClass("np-display-off");
	
	$("#<portlet:namespace/>editOrgIdea").removeClass("np-display-off");
}

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
		<portlet:namespace />IdeaMachineDescriptionHeight(ui.value);
	});
	
	$("#slider").on( "slidechange", function( event, ui ) {
        $('.unicef-slide').removeClass("div-on");
		$('.unicef-slide').addClass("div-off");
		$('#slider_'+ui.value).addClass("div-on");
		<portlet:namespace />IdeaMachineDescriptionHeight(ui.value);
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
<portlet:namespace />ideaEndorsed = function(ideaId){
	var endorsed = document.getElementById('<portlet:namespace/>endrorsedText').innerHTML;
	$.ajax({
		   url :"<%=ideaEndrosed.toString()%>",
				data : {
					endorsed : endorsed,
					ideaId : ideaId,
				},
				success : function(data) {
					result =data;
					document.getElementById('<portlet:namespace/>ideaEndorsed').innerHTML = result;
					if(endorsed == "DeEndorsed"){
					document.getElementById('<portlet:namespace/>endrorsedText').innerHTML = "Endorsed";
					}else if(endorsed == "Endorsed"){
					document.getElementById('<portlet:namespace/>endrorsedText').innerHTML = "DeEndorsed";
					}
				},
				 async: false
			});
};
var descriptionHeight = 0;
$(document).ready(function () {
	displayIdeaCommentView("${idea.ideaId}");
	autosize(document.querySelectorAll('textarea'));
	
	 $("#<portlet:namespace/>ideaCommentTxt").keypress(function (e) {
	        var code = (e.keyCode ? e.keyCode : e.which);
	        if (code == 13 && !e.shiftKey) {
	           $("a#<portlet:namespace />ideaComment").click();
	        }else{
				return;
			}
	    });
		var historySize = "${historyListSize}";
		<portlet:namespace />IdeaMachineDescriptionHeight(historySize);
});
<portlet:namespace />postComment = function(ideaId){
	 var ideaCommentText = $('#<portlet:namespace/>ideaCommentTxt').val();
	 if($.trim(ideaCommentText) == ''){
	  $('#<portlet:namespace/>postComment').addClass("has-error");
	  return;
	 }
	 var commentId = 0;
	 $.ajax({
	    url :"<%=postCommentUrl%>",
	    data : {
	    'ideaId' : ideaId,
	    'commentText' : ideaCommentText,
	    'commentId':commentId,
	    },
	    success : function(result) {
	     $('#<portlet:namespace/>ideaCommentTxt').val('');
	     $('#<portlet:namespace/>postComment').removeClass("has-error");
	     $('#<portlet:namespace/>ideaCommentTxt').css('height','30px');
	     displayIdeaCommentView(ideaId);
	   },
	   async: false
	 });
	}
function  displayIdeaCommentView(ideaId){
	 $.ajax({
	    url :"<%=ideaCommentViewURL%>",
	    data : {
	    'ideaId' : ideaId,
	   },
	    success : function(result) {
	     $('#<portlet:namespace/>ideaCommentView').html('');
	     $('#<portlet:namespace/>ideaCommentView').html(result);
	     var commentCount = $('#<portlet:namespace/>ideaCommentCount').val();
	     $('#<portlet:namespace/>commentCountDisplay').text($('#<portlet:namespace/>ideaCommentCount').val());
	     if(commentCount <= 1){
		     $('#<portlet:namespace/>commentCount').text($('#<portlet:namespace/>ideaCommentCount').val()+ " "+"Comment");
		     $('#<portlet:namespace/>commentText').text("Comment");
	     }else{
		     $('#<portlet:namespace/>commentCount').text($('#<portlet:namespace/>ideaCommentCount').val()+ " "+"Comments");
		     $('#<portlet:namespace/>commentText').text("Comments");
	     }
	   },
	   async: false
	 });
}

<portlet:namespace />promoteToScrum = function(url){
	 window.location.href = url;
}

<portlet:namespace />ideaCommentVote = function(commentId,ideaId){
	 $.ajax({
		    url :"<%=ideaCommentVoteURL%>",
		    data : {
		    'ideaId' : ideaId,
		    'commentId':commentId,
		   },
		   dataType:"JSON",
		   type:'POST',
		    success : function(result) {
		    	
		    		$('#<portlet:namespace/>thankYouImage_'+commentId).addClass('np-d-disabled');
		    		$('#<portlet:namespace/>commentVote_'+commentId).addClass('np-d-disabled');
		    		$('#<portlet:namespace/>commentVote_'+commentId+'+ span').text('Thanked')
					$('#<portlet:namespace />commentVote_'+commentId).removeAttr("onclick");
		    	
		   },
		   async: false
		 });
}
<portlet:namespace />deleteIdeaComment = function(commentId,ideaId){
	 var result=confirm("<liferay-ui:message key='idea-comment-delete-confirmation' />");
	 if(result){
	   $.ajax({
	         url:"<%=deleteIdeaCommentURL%>&commentId="+commentId,
	         type: 'POST',
	         success: function(data){
	        	  displayIdeaCommentView(ideaId);
	         },
			   async: false
	     });
	   }
}
 var commentMessage = ""; 
 var globalCommentId = "";
 
<portlet:namespace />editIdeaComment = function(commentId,ideaId){
	if(globalCommentId > 0){
		<portlet:namespace />cancelEditComment(globalCommentId);
	}
	 enableEditMode(commentId,ideaId);
	 globalCommentId = commentId;
	 
}
enableEditMode = function(commentId,ideaId){
	$('#<portlet:namespace/>commentDeleteDiv_'+commentId).hide();
	 
	 commentMessage = $('#<portlet:namespace/>comment_'+commentId).text();
	 $('#<portlet:namespace/>comment_'+commentId).hide();
	
	 $('#<portlet:namespace/>hiddenComment_'+commentId).val(commentMessage);
	 $('#<portlet:namespace/>hiddenComment_'+commentId).show();
	 $('#<portlet:namespace/>commentEditDiv_'+commentId).show();
}
<portlet:namespace />cancelEditComment = function(commentId){
	
	$('#<portlet:namespace/>hiddenComment_'+commentId).val(commentMessage);

	$('#<portlet:namespace/>hiddenComment_'+commentId).hide();
	  $('#<portlet:namespace/>hiddenComment_'+commentId).removeClass("comment-has-error");
	$('#<portlet:namespace/>comment_'+commentId).show();
	$('#<portlet:namespace/>commentEditDiv_'+commentId).hide();
	 
	$('#<portlet:namespace/>commentDeleteDiv_'+commentId).show();
}
<portlet:namespace />modifiedIdeaComment = function(commentId,ideaId){
	var editedText = $('#<portlet:namespace/>hiddenComment_'+commentId).val();
	 if($.trim(editedText) == ''){
		  $('#<portlet:namespace/>hiddenComment_'+commentId).addClass("comment-has-error");
		  return;
		 }
	
	 $.ajax({
		    url :"<%=postCommentUrl%>",
		    data : {
		    'ideaId' : ideaId,
		    'commentId':commentId,
		    'commentText':editedText,
		   },
		    success : function(result) {
		    	 $('#<portlet:namespace/>ideaCommentTxt').val('');
			     $('#<portlet:namespace/>hiddenComment_'+commentId).removeClass("comment-has-error");
			 	$('#<portlet:namespace/>hiddenComment_'+commentId).text('');
				$('#<portlet:namespace/>hiddenComment_'+commentId).hide();
				$('#<portlet:namespace/>comment_'+commentId).show();
				 $('#<portlet:namespace/>commentEditDiv_'+commentId).hide();
			     displayIdeaCommentView(ideaId);
		   },
		   async: false
		 });
}
<portlet:namespace />IdeaMachineDescriptionHeight = function(divId){
	descriptionHeight = $('#<portlet:namespace/>ideaDescription_'+divId).height();
		if(descriptionHeight >= 80){
				$("#<portlet:namespace />ideaDescription_"+divId).css({height:"80px",overflow:"hidden"});
				$("#<portlet:namespace />readMore_"+divId).show();
			}
}

<portlet:namespace/>moreText = function(divId){
		$("#<portlet:namespace />readMore_"+divId).hide();
		$("#<portlet:namespace />ideaDescription_"+divId).css({height:"",overflow:""});
}
</script>