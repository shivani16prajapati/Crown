<%@include file="../../init.jsp"%>
<portlet:resourceURL var="getDiscussionsURL" id="getDiscussionsURL">
</portlet:resourceURL>
<portlet:resourceURL var="sendMessageURL" id="sendMessageURL">
</portlet:resourceURL>
<portlet:resourceURL var="discussionThanksURL" id="discussionThanksURL"/>
<div class="chat-container clearfix">
	<div class="chat-question-box clearfix">
		<div class="chat-user-img-box clearfix">
		    <div class="chat-user-img"><img src="${inventorAvatarURL}"></div>
		    <div class="chat-user-img-text01">${inventorName}</div>
	    </div>
	    <div class="chat-user-comment">${sprint.text}
		    <div class="chat-user-reply-section clearfix">
		    	<div class="fr">
		    		<c:if test="${loggedInUserId == inventorId}">
			    		<a href="#" class="chat-user-tnx_btn">Thanks</a>
		    		</c:if>
		    			<a href="javascript:void(0);" class="chat-user-reply_btn" onclick="javascript:<portlet:namespace />postReplay(0,0,0);">Reply</a>
			    		<!-- <a href="#" class="chat-user-tnked_btn">THANKED</a> -->
		    	</div>
			</div>
		</div>
		<div class="chat-video-help "><a class="various-1"  href="#video-button">video link</a></div>
  	</div>
  
     <div id="discussionWrapper" class="chat-container2">
		
    </div>
    <input type="hidden" name="<portlet:namespace/>firstLevelIdsHidden" id="<portlet:namespace/>firstLevelIdsHidden" />
    <input type="hidden" name="<portlet:namespace/>secondLevelIdsHidden" id="<portlet:namespace/>secondLevelIdsHidden" />
    <input type="hidden" name="<portlet:namespace/>thirdLevelIdsHidden" id="<portlet:namespace/>thirdLevelIdsHidden" />
   	<input type="hidden" name="<portlet:namespace/>parentDiscussionId" id="<portlet:namespace/>parentDiscussionId"/>
   	<input type="hidden" name="<portlet:namespace/>level" id="<portlet:namespace/>level"/>
   	<input type="hidden" name="<portlet:namespace/>ideaScrumDiscussionId" id="<portlet:namespace/>ideaScrumDiscussionId"/>
    <div id="<portlet:namespace/>replayDiv" style="display: none;" class="clearfix chat-ans-text-box">
			 <div id='<portlet:namespace/>replay' class="chat-user-img-box clearfix">
			 	<div class="chat-user-img"><img src="${loggedInUserAvatarURL}"></div>
    			<div class="chat-user-img-text01">${loggedInUserName}</div>
    		</div>
			<div id="<portlet:namespace />replayDiv">
    		<div class="chat-user-comment" id="<portlet:namespace />messageButtonWrapper">
  				<textarea class="cs-chat-form-control-01" id="<portlet:namespace/>responseDiscussionInput" name="<portlet:namespace/>responseDiscussionInput"></textarea>
 				 <a href="javascript:void(0);" id='<portlet:namespace/>level-1Comment' class="btn btn-info chat-btn" onclick="javascript:<portlet:namespace/>sendResponseMessage();">Reply</a>
 			</div>
			</div>
	</div>
</div>

<div id="video-button" style="display:none;">
	<div class="responsive-container">
		<iframe allowfullscreen="" frameborder="0" id="player1" mozallowfullscreen="" src="http://player.vimeo.com/video/127329463?autoplay=false&amp;loop=false&amp;byline=false&amp;portrait=false&amp;title=false?autoplay=1" webkitallowfullscreen=""></iframe>
	</div>
</div>
<script>
$(document).ready(function() {
	$(".various-1").fancybox({
		maxWidth	: 820,
		maxHeight	: 600,
		fitToView	: false,
		width		: '70%',
		height		: 'auto',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
	});
});

var loggedInUserId = "${loggedInUserId}";
var firstLevelIdsArray = [];
var secondLevelIdsArray = [];
var thirdLevelIdsArray = [];
var isPopUpEdit = "true";
isPopUpEdit = true;
$(document).ready(function() {
	autosize(document.querySelectorAll('textarea'));
	<portlet:namespace/>getAllDiscussions();
	
	
	 $("#<portlet:namespace/>responseDiscussionInput").keypress(function (e) {
	        var code = (e.keyCode ? e.keyCode : e.which);
	        if (code == 13 && !e.shiftKey) {
	           $("a#<portlet:namespace/>level-1Comment").click();
	        }else{
				return;
			}
	    });
		
		
	
	 
});
<portlet:namespace />autoEnterKey = function(ideaScrumDiscussionId){
$("#<portlet:namespace/>responseDiscussionInput_"+ideaScrumDiscussionId).keypress(function (e) {
	        var code = (e.keyCode ? e.keyCode : e.which);
	        if (code == 13 && !e.shiftKey) {
	           $("a#<portlet:namespace/>secondLevel"+ideaScrumDiscussionId).click();
	        }else{
				return;
			}
	    });
}

setInterval(function(){
	if(isPopUpEdit){
		<portlet:namespace/>getAllDiscussions();
	}
}, 3000);
<portlet:namespace/>getAllDiscussions = function(){
	var solutionScrumId = "${solutionScrumId}";
	var solutionId = "${solutionId}";
	var sprintId = "${sprint.sprintId}";
	$.ajax({
	    url: "<%=getDiscussionsURL.toString()%>",
	    type: "POST",
	    dataType: "json",
	    data:{
			<portlet:namespace />solutionScrumId:solutionScrumId,
			<portlet:namespace />solutionId:solutionId,
			<portlet:namespace />sprintId:sprintId,
		},
	    success: function( data ) {
			<portlet:namespace/>createDiscussionMessage(data);
	    },
	    error: function (error) {
	       console.log('error in sending message: ' + error);
	    }
    });
}
<portlet:namespace/>createDiscussionMessage = function(data){
	$("#discussionWrapper").html(" ");
	var messageHTML = '';
	
	$.each(data, function(index, element) {
			messageHTML += '<div id="discussion_'+element.ideaScrumDiscussionId+'" class="Level_'+element.levelCount+'">';	
			messageHTML += <portlet:namespace />generateLevelHTML(element.ideaScrumDiscussionId, element.avatarURL, element.userName,
					element.message, element.parentDiscussionId, element.level, element.levelCount, element.isChild.toString(),element.userId,element.isThanks.toString(),element.isInventorPrefsAdmin.toString());
				
			if(element.isChild.toString() == 'true'){
			
				$.each(element.secondLevelMessages, function(index, secondElement) {
						messageHTML += <portlet:namespace />generateLevelHTML(secondElement.ideaScrumDiscussionId, secondElement.avatarURL, 
								secondElement.userName, secondElement.message, secondElement.parentDiscussionId, secondElement.level, 
								secondElement.levelCount, secondElement.isChild.toString(),secondElement.userId,secondElement.isThanks.toString(),secondElement.isInventorPrefsAdmin.toString());
					
						if(secondElement.isChild.toString() == 'true'){
							
							$.each(secondElement.thirdLevelMessages, function(index, thirdElement) {
									messageHTML += <portlet:namespace />generateLevelHTML(thirdElement.ideaScrumDiscussionId, thirdElement.avatarURL, 
											thirdElement.userName, thirdElement.message, thirdElement.parentDiscussionId, thirdElement.level, 
											thirdElement.levelCount, thirdElement.isChild.toString(),thirdElement.userId,thirdElement.isThanks.toString(),thirdElement.isInventorPrefsAdmin.toString());
							});	
							messageHTML +='</div>';
						}
				});	
			messageHTML +='</div>';
			messageHTML += '</div>';
			}else{
				messageHTML += '</div>';
			}
			$("#discussionWrapper").append(messageHTML);
			messageHTML = '';
	});
}
<portlet:namespace />generateLevelHTML = function(ideaScrumDiscussionId, avatarURL, userName, message, parentDiscussionId, level, levelCount, isChild,userId,isThanks,isInventorPrefsAdmin){
	var levelHTML = '';
	if(isChild == 'true'){
		levelHTML += '<div class="cab-leval-'+levelCount+'" id="cab-level-'+ideaScrumDiscussionId+'">';
						if(userId == loggedInUserId){
							levelHTML +=	'<div class="chat-ansr-box right-user clearfix">';
						}else{
							levelHTML += '<div class="chat-ansr-box clearfix">' ;
						}
						levelHTML +='<div class="chat-user-img-box clearfix">' +
								'<div class="chat-user-img">' +
									'<img src="'+avatarURL+'"></img>' +
								'</div>' +
								'<div class="chat-user-img-text01">'+userName+'</div>' +
							'</div>' + 
							'<div class="chat-user-ansr-comment">'+message+''+
								'<div class="chat-user-reply-section clearfix">' +
					            	'<div class="fr">' ;
					            		if(isThanks == "false" && isInventorPrefsAdmin == "true"){
					            			levelHTML += '<a href="javascript:void(0);" class="chat-user-tnx_btn" onclick="javascript:<portlet:namespace />discussionThanks('+ideaScrumDiscussionId+');" id="thanks-button_'+ideaScrumDiscussionId+'">Thanks</a>';
					            			levelHTML += '<a href="#" class="chat-user-tnked_btn np-display-off" id="thanked-buttion_'+ideaScrumDiscussionId+'">THANKED</a>';
					            		}else if(isThanks == "true" && isInventorPrefsAdmin == "true"){
					            			levelHTML += '<a href="#" class="chat-user-tnked_btn">THANKED</a>';
					            		}
										if(levelCount != 3){
											   levelHTML += '<a href="javascript:void(0);" class="chat-user-reply_btn"' +
											                'onclick="javascript:<portlet:namespace />postReplay('+parentDiscussionId+','+levelCount+','+ideaScrumDiscussionId+');">Reply</a>';
										 }
										 
										 levelHTML +='</div>'+
				            	'</div>'+
							'</div>' +
							'<div class="chat-user-ansr-comment-reply" id="ansr-comment-reply'+ideaScrumDiscussionId+'" style="display:none;">' +
							'<textarea class="cs-chat-form-control-01" id="<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId+'" name="<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId+'"></textarea>'+
							    /* '<input name="<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId+'" id="<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId+'" type="text" class="cg-form-control"/>' + */
							    '<a href="javascript:void(0);" id="<portlet:namespace/>secondLevel'+ideaScrumDiscussionId+'" class="chat-user-reply_btn"'+
								'onclick="javascript:<portlet:namespace/>sendResponseMessage();">Reply</a>'+
						'</div>'+
					   '</div>';
	}else{
		levelHTML += '<div class="cab-leval-'+levelCount+'" id="cab-level-'+ideaScrumDiscussionId+'">' ;
							if(userId == loggedInUserId){
								levelHTML +=	'<div class="chat-ansr-box right-user clearfix">';
							}else{
								levelHTML += '<div class="chat-ansr-box clearfix">' ;
							}
							levelHTML +='<div class="chat-user-img-box clearfix">' +
								'<div class="chat-user-img">' +
									'<img src="'+avatarURL+'"></img>' +
								'</div>' +
								'<div class="chat-user-img-text01">'+userName+'</div>' +
							'</div>' + 
							'<div class="chat-user-ansr-comment">'+message+''+
								'<div class="chat-user-reply-section clearfix">' +
					            	'<div class="fr">' ;
										if(isThanks == "false" && isInventorPrefsAdmin == "true"){
					            			levelHTML += '<a href="javascript:void(0);" class="chat-user-tnx_btn" onclick="javascript:<portlet:namespace />discussionThanks('+ideaScrumDiscussionId+');" id="thanks-button_'+ideaScrumDiscussionId+'">Thanks</a>';
					            			levelHTML += '<a href="#" class="chat-user-tnked_btn np-display-off" id="thanked-buttion_'+ideaScrumDiscussionId+'">THANKED</a>';
					            		}else if(isThanks == "true" && isInventorPrefsAdmin == "true"){
					            			levelHTML += '<a href="#" class="chat-user-tnked_btn">THANKED</a>';
					            		}
					            		if(levelCount != 3){
												   levelHTML += '<a href="javascript:void(0);" class="chat-user-reply_btn"' +
												                'onclick="javascript:<portlet:namespace />postReplay('+parentDiscussionId+','+levelCount+','+ideaScrumDiscussionId+');">Reply</a>';
										 }
										  
					            		 levelHTML +='</div>'+
				            	'</div>'+
							'</div>' +
							'<div class="chat-user-ansr-comment-reply" id="ansr-comment-reply'+ideaScrumDiscussionId+'" style=\"display:none;\">' +
							'<textarea class="cs-chat-form-control-01" id="<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId+'" name="<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId+'"></textarea>'+
							  /*   '<input name="<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId+'" id="<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId+'" type="text" class="cg-form-control"/>' + */
							    '<a href="javascript:void(0);" id="<portlet:namespace/>secondLevel'+ideaScrumDiscussionId+'" class="chat-user-reply_btn"'+
								'onclick="javascript:<portlet:namespace/>sendResponseMessage();">Reply</a>'+
							'</div>'+
						'</div>'+
					'</div>';
	}

	return levelHTML;
}

<portlet:namespace/>discussionThanks = function(ideaScrumDiscussionId){
	var solutionScrumId = "${solutionScrumId}";
	var solutionId = "${solutionId}";
	var sprintId = "${sprint.sprintId}";
	$.ajax({
	    url: "<%=discussionThanksURL.toString()%>",
	    data:{
			<portlet:namespace />solutionScrumId:solutionScrumId,
			<portlet:namespace />solutionId:solutionId,
			<portlet:namespace />sprintId:sprintId,
			<portlet:namespace />ideaScrumDiscussionId:ideaScrumDiscussionId,
		},
	    success: function() {
			$('#thanks-button_'+ideaScrumDiscussionId).addClass("np-display-off");
			$('#thanked-buttion_'+ideaScrumDiscussionId).removeClass("np-display-off");
	    }, 
	    async: false,
	    error: function (error) {
	       console.log('error in sending message: ' + error);
	    }
    });
}
<portlet:namespace/>sendResponseMessage = function(){
	var message = "";
	var level = $("#<portlet:namespace/>level").val();
	var parentDiscussionId = $("#<portlet:namespace/>parentDiscussionId").val();
	var ideaScrumDiscussionId = $("#<portlet:namespace/>ideaScrumDiscussionId").val();
	if(level == 0){
		 message=$("#<portlet:namespace/>responseDiscussionInput").val();
		  if($.trim(message) == ''){
			$('#<portlet:namespace />messageButtonWrapper').addClass("has-error");
		  return;
		}
	}else{
		message =$('#<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId).val();
		  if($.trim(message) == ''){
			$('#ansr-comment-reply'+ideaScrumDiscussionId).addClass("has-error");
		  return;
		}
	}
	var solutionScrumId = "${solutionScrumId}";
	var solutionId = "${solutionId}";
	var sprintId = "${sprint.sprintId}";
	var messageHTML = '';
	$.ajax({
	    url: "<%=sendMessageURL.toString()%>",
	    type: "POST",
	    dataType: "json",
	    data:{
			<portlet:namespace />message:message,
			<portlet:namespace />solutionScrumId:solutionScrumId,
			<portlet:namespace />solutionId:solutionId,
			<portlet:namespace />sprintId:sprintId,
			<portlet:namespace />parentDiscussionId:parentDiscussionId,
			<portlet:namespace />level:level,
			<portlet:namespace />ideaScrumDiscussionId:ideaScrumDiscussionId
		},
	    success: function( data ) {
	    	$.each(data, function(index, element) {
	    			if(level == 0){
	    				messageHTML += '<div id="discussion_'+element.ideaScrumDiscussionId+'" class="Level_'+element.level+'">';			
		    			messageHTML += <portlet:namespace />generateLevelHTML(element.ideaScrumDiscussionId, element.avatarURL, element.userName,
		    					element.message, element.parentDiscussionId, element.level, element.levelCount, element.isChild.toString(),element.userId,element.isThanks.toString(),element.isInventorPrefsAdmin.toString());
			    		messageHTML += '</div>';
						$("#discussionWrapper").append(messageHTML);
						messageHTML = '';
	    			}else{
		    			messageHTML += <portlet:namespace />generateLevelHTML(element.ideaScrumDiscussionId, element.avatarURL, element.userName,
		    					element.message, element.parentDiscussionId, element.level, element.levelCount, element.isChild.toString(),element.userId,element.isThanks.toString(),element.isInventorPrefsAdmin.toString());
						$('#cab-level-'+ideaScrumDiscussionId).append(messageHTML);
						messageHTML = '';
	    			}
	    	});
			if(level == 0){
				 $('#<portlet:namespace/>responseDiscussionInput').val('');
				 $('#<portlet:namespace/>responseDiscussionInput').css('height','54px');
					<portlet:namespace/>goToLastMessage();
					$("#<portlet:namespace/>replayDiv").toggle(function(){
						$("#<portlet:namespace/>replayDiv").slideUp();
						isPopUpEdit = true;
					});
				$('#<portlet:namespace />messageButtonWrapper').removeClass('has-error');
			}else if(level == 1){
				$('#<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId).val('');
				 $('#<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId).css('height','54px');
				$('#ansr-comment-reply'+ideaScrumDiscussionId).toggle(function(){
					$('#ansr-comment-reply'+ideaScrumDiscussionId).slideUp();
					$("#<portlet:namespace/>replayDiv").slideUp();
					isPopUpEdit = true;
				});
				$('#ansr-comment-reply'+ideaScrumDiscussionId).removeClass('has-error');
			}else if(level == 2){
				$('#<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId).val('');
				 $('#<portlet:namespace/>responseDiscussionInput_'+ideaScrumDiscussionId).css('height','54px');
				$('#ansr-comment-reply'+ideaScrumDiscussionId).toggle(function(){
					$('#ansr-comment-reply'+ideaScrumDiscussionId).slideUp();
					$("#<portlet:namespace/>replayDiv").slideUp();
					isPopUpEdit = true;
				});
				$('#ansr-comment-reply'+ideaScrumDiscussionId).removeClass('has-error');
			}
	    }, 
	    async: false,
	    error: function (error) {
	       console.log('error in sending message: ' + error);
	    }
    });
	$("#<portlet:namespace/>parentDiscussionId").val('');
	$("#<portlet:namespace/>level").val('');
 }
 
<portlet:namespace/>goToLastMessage = function(){
	 $("#discussionWrapper").animate({ scrollTop: $("#discussionWrapper")[0].scrollHeight}, 0);
 }
 
<portlet:namespace />postReplay = function(parentDiscussionId, level, ideaScrumDiscussionId){
	 autosize(document.querySelectorAll('textarea'));
	 <portlet:namespace />autoEnterKey(ideaScrumDiscussionId);
	if(level == 0){
		$("#<portlet:namespace/>parentDiscussionId").val(parentDiscussionId);
		$("#<portlet:namespace/>level").val(level);
		$("#<portlet:namespace/>ideaScrumDiscussionId").val(ideaScrumDiscussionId);
		$("#<portlet:namespace/>responseDiscussionInput" ).val('');
		if ($("#<portlet:namespace/>replayDiv" ).css('display') == 'none') {
			$("#<portlet:namespace/>replayDiv").toggle(function(){
				   $("#<portlet:namespace/>replayDiv").slideDown();
				 });
				isPopUpEdit = false;
		}else{
		    $("#<portlet:namespace/>replayDiv").toggle(function(){
				$("#<portlet:namespace/>replayDiv").slideUp();
				isPopUpEdit = true;
			});
			$('#<portlet:namespace />messageButtonWrapper').removeClass('has-error');
		}
	}else{
		$("#<portlet:namespace/>level").val(level);
		$("#<portlet:namespace/>parentDiscussionId").val(parentDiscussionId);
		$('#<portlet:namespace/>ideaScrumDiscussionId').val(ideaScrumDiscussionId);
		$("#<portlet:namespace/>responseDiscussionInput_"+ideaScrumDiscussionId).val('');
		if ($('#ansr-comment-reply'+ideaScrumDiscussionId).css('display') == 'none') {
			$('#ansr-comment-reply'+ideaScrumDiscussionId).toggle(function(){
				$('#ansr-comment-reply'+ideaScrumDiscussionId).slideDown();
				isPopUpEdit = false;
			});
		}else{
			$('#ansr-comment-reply'+ideaScrumDiscussionId).toggle(function(){
				$('#ansr-comment-reply'+ideaScrumDiscussionId).slideUp();
				isPopUpEdit = true;
			});
			$('#ansr-comment-reply'+ideaScrumDiscussionId).removeClass('has-error');
		}
	}
} 

</script>