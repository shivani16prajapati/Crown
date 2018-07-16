<%@page import="com.unicef.domain.Sprint"%>
<%@page import="com.unicef.util.IdeaScrumDiscussionTitleDescription"%>
<%@include file="../../init.jsp"%>

<portlet:resourceURL var="sendMessageURL" id="sendMessageURL">
</portlet:resourceURL>
<portlet:resourceURL var="getDiscussionsURL" id="getDiscussionsURL">
</portlet:resourceURL>
<portlet:resourceURL var="discussionThanksURL" id="discussionThanksURL"/>
<portlet:resourceURL var="updateDiscussionURL" id="updateDiscussionURL"/>

<portlet:resourceURL var="likeDiscussionURL" id="likeDiscussionURL"/>
<portlet:resourceURL var="disLikeDiscussionURL" id="disLikeDiscussionURL"/>

<div class="np-rounded-box clearfix"><div class="chat-container clearfix np-rounded-box">
	<div class="chat-question-box clearfix">
		<div class="chat-user-img-box clearfix">
		    <div class="chat-user-img"><img src="${inventorAvatarURL}"></div>
		    <div class="chat-user-img-text02">${inventorName}</div>
	    </div>
	    <div class="chat-user-comment">${sprint2.text}
	    	<p class="sprint-discussion-title-description">${sprint2TitleDescription}</p>
		    <div class="chat-user-reply-section clearfix">
		    	<div class="fr">
		    		<c:if test="${loggedInUserId == inventorId}">
			    		<a href="#" class="chat-user-tnx_btn">Thanks</a>
		    		</c:if>
		    			<a href="javascript:void(0);" class="chat-user-reply_btn" onclick="javascript:<portlet:namespace />postReplay2(0,0,0);">Reply</a>
		    	</div>
			</div>
		</div>
		<div class="chat-video-help "><a class="various-2"  href="#video-button">video link</a></div>
  	</div>
  
     <div id="discussionWrapper2" class="chat-container2">
		
    </div>
    <input type="hidden" name="<portlet:namespace/>firstLevelIdsHidden" id="<portlet:namespace/>firstLevelIdsHidden" />
    <input type="hidden" name="<portlet:namespace/>secondLevelIdsHidden" id="<portlet:namespace/>secondLevelIdsHidden" />
    <input type="hidden" name="<portlet:namespace/>thirdLevelIdsHidden" id="<portlet:namespace/>thirdLevelIdsHidden" />
   	<input type="hidden" name="<portlet:namespace/>parentDiscussionId" id="<portlet:namespace/>parentDiscussionId"/>
   	<input type="hidden" name="<portlet:namespace/>level" id="<portlet:namespace/>level"/>
   	<input type="hidden" name="<portlet:namespace/>ideaScrumDiscussionId" id="<portlet:namespace/>ideaScrumDiscussionId"/>
   	<input type="hidden" name="<portlet:namespace/>isDiscussionInEditMode" id="<portlet:namespace/>isDiscussionInEditMode"/>
   	
    <div id="<portlet:namespace/>replayDiv2" style="display: none;" class="clearfix chat-ans-text-box">
			 <div id='<portlet:namespace/>replay' class="chat-user-img-box clearfix">
			 	<div class="chat-user-img"><img src="${loggedInUserAvatarURL}"></div>
    			<div class="chat-user-img-text02">${loggedInUserName}</div>
    		</div>
			<div id="<portlet:namespace />replayDiv2">
    		<div class="chat-user-comment" id="<portlet:namespace />messageButtonWrapper2">
  				<textarea class="cs-chat-form-control-02" id="<portlet:namespace/>responseDiscussionInput2" name="<portlet:namespace/>responseDiscussionInput2"></textarea>
 				<a href="javascript:void(0);" id='<portlet:namespace/>level-2Comment' class="btn btn-info chat-btn">Reply</a>
 				<a href="javascript:void(0);" id='<portlet:namespace/>level-2Comment-Submit' class="btn btn-info chat-btn np-display-off" onclick="javascript:<portlet:namespace/>sendResponseMessage2();">Submit</a>
 			</div>
			</div>
	</div>
</div></div>

<div id="video-button" style="display:none;">
	<div class="responsive-container">
		<iframe allowfullscreen="" frameborder="0" id="player2" mozallowfullscreen="" src="http://player.vimeo.com/video/227329463?autoplay=false&amp;loop=false&amp;byline=false&amp;portrait=false&amp;title=false?autoplay=2" webkitallowfullscreen=""></iframe>
	</div>
</div>

<script>
$(document).ready(function() {
	$(".various-2").fancybox({
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
	debugger;
	autosize(document.querySelectorAll('textarea'));
	<portlet:namespace/>getAllDiscussions2();

	$("#<portlet:namespace/>responseDiscussionInput2").keypress(function (e) {
	        
			if($.trim($("#<portlet:namespace/>responseDiscussionInput2").val()) !== '') {
				$("#<portlet:namespace/>level-2Comment-Submit").removeClass("np-display-off");
			} else {
				$("#<portlet:namespace/>level-2Comment").addClass("np-display-off");
			}
			
			if($.trim($("#<portlet:namespace/>responseDiscussionInput2").val()) === '') { 
				$("#<portlet:namespace/>level-2Comment-Submit").addClass("np-display-off");
				$("#<portlet:namespace/>level-2Comment").removeClass("np-display-off");
			}
		
			var code = (e.keyCode ? e.keyCode : e.which);
	        if (code == 23 && !e.shiftKey) {
	           $("a#<portlet:namespace/>level-2Comment").click();
	        }else{
				return;
			}
	    });
});

<portlet:namespace />autoEnterKey2 = function(ideaScrumDiscussionId){
	$("#<portlet:namespace/>responseDiscussionInput2_"+ideaScrumDiscussionId).keypress(function (e) {
		
		if($.trim($("#<portlet:namespace/>responseDiscussionInput2_"+ideaScrumDiscussionId).val()) !== '') {
			$("#<portlet:namespace/>secondLevelSubmit"+ideaScrumDiscussionId).removeClass("np-display-off");
		} else {
			$("#<portlet:namespace/>secondLevel"+ideaScrumDiscussionId).addClass("np-display-off");
		}
		
		if($.trim($("#<portlet:namespace/>responseDiscussionInput2_"+ideaScrumDiscussionId).val()) === '') {
			$("#<portlet:namespace/>secondLevelSubmit"+ideaScrumDiscussionId).addClass("np-display-off");
			$("#<portlet:namespace/>secondLevel"+ideaScrumDiscussionId).removeClass("np-display-off");
		}
		
        var code = (e.keyCode ? e.keyCode : e.which);
        if (code == 23 && !e.shiftKey) {
           $("a#<portlet:namespace/>secondLevel"+ideaScrumDiscussionId).click();
        }else{
			return;
		}
	});
}

setInterval(function(){
	if(isPopUpEdit){
		<portlet:namespace/>getAllDiscussions2();
	}
}, 3000);

<portlet:namespace/>getAllDiscussions2 = function(){

	var ideaScrumId = "${ideaScrumId}";
	var ideaId = "${ideaId}";
	var sprintId = "${sprint2.sprintId}";
	$.ajax({
	    url: "<%=getDiscussionsURL.toString()%>",
	    type: "POST",
	    dataType: "json",
	    data:{
			<portlet:namespace />ideaScrumId:ideaScrumId,
			<portlet:namespace />ideaId:ideaId,
			<portlet:namespace />sprintId:sprintId,
		},
	    success: function( data ) {
			<portlet:namespace/>createDiscussionMessage2(data);
	    },
	    error: function (error) {
	       console.log('error in sending message: ' + error);
	    }
    });
}
<portlet:namespace/>createDiscussionMessage2 = function(data){
	$("#discussionWrapper2").html(" ");
	var messageHTML = '';
	
	$.each(data, function(index, element) {
		
			messageHTML += '<div id="discussion_'+element.ideaScrumDiscussionId+'" class="Level_'+element.levelCount+'">';	
			messageHTML += <portlet:namespace />generateLevelHTML2(element.ideaScrumDiscussionId, element.avatarURL, element.userName,
					element.message, element.parentDiscussionId, element.level, element.levelCount, element.isChild.toString(),element.userId,element.isThanks.toString(),element.isInventorPrefsAdmin.toString(), element.isLiked);
				
			if(element.isChild.toString() == 'true'){
			
				$.each(element.secondLevelMessages, function(index, secondElement) {
						messageHTML += <portlet:namespace />generateLevelHTML2(secondElement.ideaScrumDiscussionId, secondElement.avatarURL, 
								secondElement.userName, secondElement.message, secondElement.parentDiscussionId, secondElement.level, 
								secondElement.levelCount, secondElement.isChild.toString(),secondElement.userId,secondElement.isThanks.toString(),secondElement.isInventorPrefsAdmin.toString(), secondElement.isLiked);
					
						if(secondElement.isChild.toString() == 'true'){
							
							$.each(secondElement.thirdLevelMessages, function(index, thirdElement) {
									messageHTML += <portlet:namespace />generateLevelHTML2(thirdElement.ideaScrumDiscussionId, thirdElement.avatarURL, 
											thirdElement.userName, thirdElement.message, thirdElement.parentDiscussionId, thirdElement.level, 
											thirdElement.levelCount, thirdElement.isChild.toString(),thirdElement.userId, thirdElement.isThanks.toString(),thirdElement.isInventorPrefsAdmin.toString(), thirdElement.isLiked);
							});	
							messageHTML +='</div>';
						}
				});	
			messageHTML +='</div>';
			messageHTML += '</div>';
			}else{
				messageHTML += '</div>';
			}
			$("#discussionWrapper2").append(messageHTML);
			messageHTML = '';
	});
}
<portlet:namespace />generateLevelHTML2 = function(ideaScrumDiscussionId, avatarURL, userName, message, parentDiscussionId, level, levelCount, isChild,userId,isThanks,isInventorPrefsAdmin,isLiked){
	var levelHTML = '';
	if(isChild == 'true'){
		levelHTML += '<div class="cab-leval-'+levelCount+'" id="cab-level-'+ideaScrumDiscussionId+'">';
						
						if(userId == loggedInUserId){
							levelHTML +='<div class="chat-ansr-box right-user clearfix">';
						}else{
							levelHTML += '<div class="chat-ansr-box clearfix">';
						}
						
						levelHTML +='<div class="chat-user-img-box clearfix">' +
								'<div class="chat-user-img">' +
									'<img src="'+avatarURL+'"></img>' +
								'</div>' +
								'<div class="chat-user-img-text02">'+userName+'</div>' +
							'</div>' + 
							'<div class="chat-user-ansr-comment"><textarea id="discussion-message_'+ideaScrumDiscussionId+'" class="discussion-message-text cs-chat-form-control-01" readonly="readonly">'+message+'</textarea>'+
								'<div class="chat-user-reply-section clearfix">' +
					            	'<div class="fr">';
										
					            		if(isLiked === false ){
						            		levelHTML += '<a href="javascript:void(0);" class="like-discussion-board np-display-off chat-user-tnx_btn" onclick="javascript:<portlet:namespace />deLikeDiscussion2('+ideaScrumDiscussionId+');" id="like-button_'+ideaScrumDiscussionId+'"></a>';
				            				levelHTML += '<a href="javascript:void(0);" class="liked-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />likeDiscussion2('+ideaScrumDiscussionId+');" id="liked-button_'+ideaScrumDiscussionId+'"></a>';
										} else {
											levelHTML += '<a href="javascript:void(0);" class="like-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />deLikeDiscussion2('+ideaScrumDiscussionId+');" id="like-button_'+ideaScrumDiscussionId+'"></a>';
				            				levelHTML += '<a href="javascript:void(0);" class="liked-discussion-board np-display-off chat-user-tnx_btn" onclick="javascript:<portlet:namespace />likeDiscussion2('+ideaScrumDiscussionId+');" id="liked-button_'+ideaScrumDiscussionId+'"></a>';													
										}
					            	
					            		if(isInventorPrefsAdmin == "true") {
					            			if($("#<portlet:namespace/>isDiscussionInEditMode").hasClass("np-display-off")) {
					            				levelHTML += '<a href="javascript:void(0);" class="edit-discussion-board np-display-off chat-user-tnx_btn" onclick="javascript:<portlet:namespace />editDiscussion2('+ideaScrumDiscussionId+');" id="edit-button_'+ideaScrumDiscussionId+'"></a>';
					            				levelHTML += '<a href="javascript:void(0);" class="update-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />updateDiscussion2('+ideaScrumDiscussionId+');" id="update-button_'+ideaScrumDiscussionId+'"></a>';
					            				levelHTML += '<a href="javascript:void(0);" class="cancel-update-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />cancelUpdateDiscussion2('+ideaScrumDiscussionId+');" id="cancel-update-button_'+ideaScrumDiscussionId+'"></a>';
					            			} else {
					            				levelHTML += '<a href="javascript:void(0);" class="edit-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />editDiscussion2('+ideaScrumDiscussionId+');" id="edit-button_'+ideaScrumDiscussionId+'"></a>';	
					            				levelHTML += '<a href="javascript:void(0);" class="update-discussion-board chat-user-tnx_btn np-display-off" onclick="javascript:<portlet:namespace />updateDiscussion2('+ideaScrumDiscussionId+');" id="update-button_'+ideaScrumDiscussionId+'"></a>';
					            				levelHTML += '<a href="javascript:void(0);" class="cancel-update-discussion-board chat-user-tnx_btn np-display-off" onclick="javascript:<portlet:namespace />cancelUpdateDiscussion2('+ideaScrumDiscussionId+');" id="cancel-update-button_'+ideaScrumDiscussionId+'"></a>';
					            			}							            			
					            		}
					            		
					            		if(isThanks == "false" && isInventorPrefsAdmin == "true"){
					            			levelHTML += '<a href="javascript:void(0);" class="chat-user-tnx_btn" onclick="javascript:<portlet:namespace />discussionThanks2('+ideaScrumDiscussionId+');" id="thanks-button_'+ideaScrumDiscussionId+'">Thanks</a>';
					            			levelHTML += '<a href="#" class="chat-user-tnked_btn np-display-off" id="thanked-buttion_'+ideaScrumDiscussionId+'">THANKED</a>';
					            		}else if(isThanks == "true" && isInventorPrefsAdmin == "true"){
					            			levelHTML += '<a href="#" class="chat-user-tnked_btn">THANKED</a>';
					            		}
					            		
										if(levelCount != 3){
											if(userId == loggedInUserId && isInventorPrefsAdmin !== "true"){ 
										    	if($("#<portlet:namespace/>isDiscussionInEditMode").hasClass("np-display-off")) {
						            				levelHTML += '<a href="javascript:void(0);" class="edit-discussion-board np-display-off chat-user-tnx_btn" onclick="javascript:<portlet:namespace />editDiscussion2('+ideaScrumDiscussionId+');" id="edit-button_'+ideaScrumDiscussionId+'"></a>';
						            				levelHTML += '<a href="javascript:void(0);" class="update-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />updateDiscussion2('+ideaScrumDiscussionId+');" id="update-button_'+ideaScrumDiscussionId+'"></a>';
						            				levelHTML += '<a href="javascript:void(0);" class="cancel-update-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />cancelUpdateDiscussion2('+ideaScrumDiscussionId+');" id="cancel-update-button_'+ideaScrumDiscussionId+'"></a>';
						            			} else {
						            				levelHTML += '<a href="javascript:void(0);" class="edit-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />editDiscussion2('+ideaScrumDiscussionId+');" id="edit-button_'+ideaScrumDiscussionId+'"></a>';	
						            				levelHTML += '<a href="javascript:void(0);" class="update-discussion-board chat-user-tnx_btn np-display-off" onclick="javascript:<portlet:namespace />updateDiscussion2('+ideaScrumDiscussionId+');" id="update-button_'+ideaScrumDiscussionId+'"></a>';
						            				levelHTML += '<a href="javascript:void(0);" class="cancel-update-discussion-board chat-user-tnx_btn np-display-off" onclick="javascript:<portlet:namespace />cancelUpdateDiscussion2('+ideaScrumDiscussionId+');" id="cancel-update-button_'+ideaScrumDiscussionId+'"></a>';
						            			}	
										    }
											levelHTML += '<a href="javascript:void(0);" class="chat-user-reply_btn"' +
											                'onclick="javascript:<portlet:namespace />postReplay2('+parentDiscussionId+','+levelCount+','+ideaScrumDiscussionId+');">Reply</a>';
										}
										 
										 levelHTML +='</div>'+
				            	'</div>'+
							'</div>' +
							'<div class="chat-user-ansr-comment-reply" id="ansr-comment-reply'+ideaScrumDiscussionId+'" style="display:none;">' +
							'<textarea class="cs-chat-form-control-02" id="<portlet:namespace/>responseDiscussionInput2_'+ideaScrumDiscussionId+'" name="<portlet:namespace/>responseDiscussionInput2_'+ideaScrumDiscussionId+'"></textarea>'+
							    '<a href="javascript:void(0);" id="<portlet:namespace/>secondLevel'+ideaScrumDiscussionId+'" class="chat-user-reply_btn"'+
								'>Reply</a>'+
								'<a href="javascript:void(0);" id="<portlet:namespace/>secondLevelSubmit'+ideaScrumDiscussionId+'" class="chat-user-reply_btn np-display-off"'+
								'onclick="javascript:<portlet:namespace/>sendResponseMessage2();">Submit</a>'+
						'</div>'+
					   '</div>';
		}else{
			levelHTML += '<div class="cab-leval-'+levelCount+'" id="cab-level-'+ideaScrumDiscussionId+'">' ;
							if(userId == loggedInUserId){
								levelHTML += '<div class="chat-ansr-box right-user clearfix">';
							}else{
								levelHTML += '<div class="chat-ansr-box clearfix">';
							}
							levelHTML +='<div class="chat-user-img-box clearfix">' +
								'<div class="chat-user-img">' +
									'<img src="'+avatarURL+'"></img>' +
								'</div>' +
								'<div class="chat-user-img-text02">'+userName+'</div>' +
							'</div>' + 
							'<div class="chat-user-ansr-comment"><textarea id="discussion-message_'+ideaScrumDiscussionId+'" class="discussion-message-text cs-chat-form-control-01" readonly="readonly">'+message+'</textarea>'+
								'<div class="chat-user-reply-section clearfix">' +
					            	'<div class="fr">' ;
										
						            	if(isLiked === false ){
						            		levelHTML += '<a href="javascript:void(0);" class="like-discussion-board np-display-off chat-user-tnx_btn" onclick="javascript:<portlet:namespace />deLikeDiscussion2('+ideaScrumDiscussionId+');" id="like-button_'+ideaScrumDiscussionId+'"></a>';
				            				levelHTML += '<a href="javascript:void(0);" class="liked-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />likeDiscussion2('+ideaScrumDiscussionId+');" id="liked-button_'+ideaScrumDiscussionId+'"></a>';
						            	} else {
											levelHTML += '<a href="javascript:void(0);" class="like-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />deLikeDiscussion2('+ideaScrumDiscussionId+');" id="like-button_'+ideaScrumDiscussionId+'"></a>';
				            				levelHTML += '<a href="javascript:void(0);" class="liked-discussion-board np-display-off chat-user-tnx_btn" onclick="javascript:<portlet:namespace />likeDiscussion2('+ideaScrumDiscussionId+');" id="liked-button_'+ideaScrumDiscussionId+'"></a>';													
										}
					            	
						            	if(isInventorPrefsAdmin == "true") {
							            	if($("#<portlet:namespace/>isDiscussionInEditMode").hasClass("np-display-off")) {
					            				levelHTML += '<a href="javascript:void(0);" class="edit-discussion-board np-display-off chat-user-tnx_btn" onclick="javascript:<portlet:namespace />editDiscussion2('+ideaScrumDiscussionId+');" id="edit-button_'+ideaScrumDiscussionId+'"></a>';
					            				levelHTML += '<a href="javascript:void(0);" class="update-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />updateDiscussion2('+ideaScrumDiscussionId+');" id="update-button_'+ideaScrumDiscussionId+'"></a>';
					            				levelHTML += '<a href="javascript:void(0);" class="cancel-update-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />cancelUpdateDiscussion2('+ideaScrumDiscussionId+');" id="cancel-update-button_'+ideaScrumDiscussionId+'"></a>';
							            	} else {
					            				levelHTML += '<a href="javascript:void(0);" class="edit-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />editDiscussion2('+ideaScrumDiscussionId+');" id="edit-button_'+ideaScrumDiscussionId+'"></a>';	
					            				levelHTML += '<a href="javascript:void(0);" class="update-discussion-board chat-user-tnx_btn np-display-off" onclick="javascript:<portlet:namespace />updateDiscussion2('+ideaScrumDiscussionId+');" id="update-button_'+ideaScrumDiscussionId+'"></a>';
					            				levelHTML += '<a href="javascript:void(0);" class="cancel-update-discussion-board chat-user-tnx_btn np-display-off" onclick="javascript:<portlet:namespace />cancelUpdateDiscussion2('+ideaScrumDiscussionId+');" id="cancel-update-button_'+ideaScrumDiscussionId+'"></a>';
							            	}
						            	}
					            	
					            	if(isThanks == "false" && isInventorPrefsAdmin == "true"){
										levelHTML += '<a href="javascript:void(0);" class="chat-user-tnx_btn" onclick="javascript:<portlet:namespace />discussionThanks2('+ideaScrumDiscussionId+');" id="thanks-button_'+ideaScrumDiscussionId+'">Thanks</a>';
				            			levelHTML += '<a href="#" class="chat-user-tnked_btn np-display-off" id="thanked-buttion_'+ideaScrumDiscussionId+'">THANKED</a>';
				            		}else if(isThanks == "true" && isInventorPrefsAdmin == "true"){
				            			levelHTML += '<a href="#" class="chat-user-tnked_btn">THANKED</a>';
				            		}
										
				            			if(levelCount != 3){
				            				if(userId == loggedInUserId && isInventorPrefsAdmin !== "true"){ 
									    		if($("#<portlet:namespace/>isDiscussionInEditMode").hasClass("np-display-off")) {
					            					levelHTML += '<a href="javascript:void(0);" class="edit-discussion-board np-display-off chat-user-tnx_btn" onclick="javascript:<portlet:namespace />editDiscussion2('+ideaScrumDiscussionId+');" id="edit-button_'+ideaScrumDiscussionId+'"></a>';
					            					levelHTML += '<a href="javascript:void(0);" class="update-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />updateDiscussion2('+ideaScrumDiscussionId+');" id="update-button_'+ideaScrumDiscussionId+'"></a>';
					            					levelHTML += '<a href="javascript:void(0);" class="cancel-update-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />cancelUpdateDiscussion2('+ideaScrumDiscussionId+');" id="cancel-update-button_'+ideaScrumDiscussionId+'"></a>';
									    		} else {
					            					levelHTML += '<a href="javascript:void(0);" class="edit-discussion-board chat-user-tnx_btn" onclick="javascript:<portlet:namespace />editDiscussion2('+ideaScrumDiscussionId+');" id="edit-button_'+ideaScrumDiscussionId+'"></a>';	
					            					levelHTML += '<a href="javascript:void(0);" class="update-discussion-board chat-user-tnx_btn np-display-off" onclick="javascript:<portlet:namespace />updateDiscussion2('+ideaScrumDiscussionId+');" id="update-button_'+ideaScrumDiscussionId+'"></a>';
					            					levelHTML += '<a href="javascript:void(0);" class="cancel-update-discussion-board chat-user-tnx_btn np-display-off" onclick="javascript:<portlet:namespace />cancelUpdateDiscussion2('+ideaScrumDiscussionId+');" id="cancel-update-button_'+ideaScrumDiscussionId+'"></a>';	
									    		}
									    	}
				            				levelHTML += '<a href="javascript:void(0);" class="chat-user-reply_btn"' +
									                	'onclick="javascript:<portlet:namespace />postReplay2('+parentDiscussionId+','+levelCount+','+ideaScrumDiscussionId+');">Reply</a>';
					            		 }
					            		 levelHTML +='</div>'+
				            	'</div>'+
							'</div>' +
							'<div class="chat-user-ansr-comment-reply" id="ansr-comment-reply'+ideaScrumDiscussionId+'" style=\"display:none;\">' +
							'<textarea class="cs-chat-form-control-02" id="<portlet:namespace/>responseDiscussionInput2_'+ideaScrumDiscussionId+'" name="<portlet:namespace/>responseDiscussionInput2_'+ideaScrumDiscussionId+'"></textarea>'+
							    '<a href="javascript:void(0);" id="<portlet:namespace/>secondLevel'+ideaScrumDiscussionId+'" class="chat-user-reply_btn"'+
								'>Reply</a>'+
								'<a href="javascript:void(0);" id="<portlet:namespace/>secondLevelSubmit'+ideaScrumDiscussionId+'" class="chat-user-reply_btn np-display-off"'+
								'onclick="javascript:<portlet:namespace/>sendResponseMessage2();">Submit</a>'+
							'</div>'+
						'</div>'+
					'</div>';
		}

	return levelHTML;
}

<portlet:namespace/>deLikeDiscussion2 = function(ideaScrumDiscussionId){
	
	isPopUpEdit=false;
	
	$.ajax({
	    url: "<%=disLikeDiscussionURL.toString()%>",
	    data:{
			<portlet:namespace />ideaScrumDiscussionId:ideaScrumDiscussionId,
		},
	    success: function() {
	    	$('#like-button_'+ideaScrumDiscussionId).addClass("np-display-off");
	    	$('#liked-button_'+ideaScrumDiscussionId).removeClass("np-display-off");
	    },
	    async: false,
	    error: function (error) {
	       console.log('error in sending message: ' + error);
	    }
    });
	
}

<portlet:namespace/>likeDiscussion2 = function(ideaScrumDiscussionId){
	isPopUpEdit=false;
	
	$.ajax({
	    url: "<%=likeDiscussionURL.toString()%>",
	    data:{
			<portlet:namespace />ideaScrumDiscussionId:ideaScrumDiscussionId,
		},
	    success: function() {
	    	$('#like-button_'+ideaScrumDiscussionId).removeClass("np-display-off");
	    	$('#liked-button_'+ideaScrumDiscussionId).addClass("np-display-off");
	    }, 
	    async: false,
	    error: function (error) {
	       console.log('error in sending message: ' + error);
	    }
    });

}

<portlet:namespace/>editDiscussion2 = function(ideaScrumDiscussionId){
	$('#edit-button_'+ideaScrumDiscussionId).removeClass("edit-discussion-board");
	$('#edit-button_'+ideaScrumDiscussionId).addClass("disable-edit-discussion-board");
	$("#edit-button_"+ideaScrumDiscussionId).attr("disabled","disabled");
	
	$('#update-button_'+ideaScrumDiscussionId).removeClass("np-display-off");
	$('#cancel-update-button_'+ideaScrumDiscussionId).removeClass("np-display-off");
	
	$("#discussion-message_"+ideaScrumDiscussionId).removeAttr("readonly");
	$("#discussion-message_"+ideaScrumDiscussionId).focus();
	
	isPopUpEdit=false;
}

<portlet:namespace/>cancelUpdateDiscussion2 = function(ideaScrumDiscussionId){ 
	$('#edit-button_'+ideaScrumDiscussionId).addClass("edit-discussion-board");
	$('#edit-button_'+ideaScrumDiscussionId).removeClass("disable-edit-discussion-board");
	
	$('#update-button_'+ideaScrumDiscussionId).addClass("np-display-off");
	$('#cancel-update-button_'+ideaScrumDiscussionId).addClass("np-display-off");
	
	$("#discussion-message_"+ideaScrumDiscussionId).attr("readonly","readonly");
}

<portlet:namespace/>updateDiscussion2 = function(ideaScrumDiscussionId){ 
	var ideaScrumId = "${ideaScrumId}";
	var ideaId = "${ideaId}";
	var sprintId = "${sprint2.sprintId}";
	var updatedMessage = $("#discussion-message_"+ideaScrumDiscussionId).val();
	
	$.ajax({
	    url: "<%=updateDiscussionURL.toString()%>",
	    data:{
			<portlet:namespace />ideaScrumId:ideaScrumId,
			<portlet:namespace />ideaId:ideaId,
			<portlet:namespace />sprintId:sprintId,
			<portlet:namespace />ideaScrumDiscussionId:ideaScrumDiscussionId,
			<portlet:namespace />message:updatedMessage
		},
	    success: function() {
			$('#edit-button_'+ideaScrumDiscussionId).addClass("edit-discussion-board");
			$('#edit-button_'+ideaScrumDiscussionId).removeClass("disable-edit-discussion-board");
			
			$('#update-button_'+ideaScrumDiscussionId).addClass("np-display-off");
			$('#cancel-update-button_'+ideaScrumDiscussionId).addClass("np-display-off");

			$("#discussion-message_"+ideaScrumDiscussionId).attr("readonly","readonly");
	    }, 
	    async: false,
	    error: function (error) {
	       console.log('error in sending message: ' + error);
	    }
    });
}

<portlet:namespace/>discussionThanks2 = function(ideaScrumDiscussionId){
	var ideaScrumId = "${ideaScrumId}";
	var ideaId = "${ideaId}";
	var sprintId = "${sprint2.sprintId}";
	$.ajax({
	    url: "<%=discussionThanksURL.toString()%>",
	    data:{
			<portlet:namespace />ideaScrumId:ideaScrumId,
			<portlet:namespace />ideaId:ideaId,
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

<portlet:namespace/>sendResponseMessage2 = function(){
	var message = "";
	var level = $("#<portlet:namespace/>level").val();
	var parentDiscussionId = $("#<portlet:namespace/>parentDiscussionId").val();
	var ideaScrumDiscussionId = $("#<portlet:namespace/>ideaScrumDiscussionId").val();
	if(level == 0){
		 message=$("#<portlet:namespace/>responseDiscussionInput2").val();
		  if($.trim(message) == ''){
			$('#<portlet:namespace />messageButtonWrapper2').addClass("has-error");
		  return;
		}
	}else{
		message =$('#<portlet:namespace/>responseDiscussionInput2_'+ideaScrumDiscussionId).val();
		  if($.trim(message) == ''){
			$('#ansr-comment-reply'+ideaScrumDiscussionId).addClass("has-error");
		  return;
		}
	}
	
	var ideaScrumId = "${ideaScrumId}";
	var ideaId = "${ideaId}";
	var sprintId = "${sprint2.sprintId}";

	var messageHTML = '';
	$.ajax({
	    url: "<%=sendMessageURL.toString()%>",
	    type: "POST",
	    dataType: "json",
	    data:{
			<portlet:namespace />message:message,
			<portlet:namespace />ideaScrumId:ideaScrumId,
			<portlet:namespace />ideaId:ideaId,
			<portlet:namespace />sprintId:sprintId,
			<portlet:namespace />parentDiscussionId:parentDiscussionId,
			<portlet:namespace />level:level,
			<portlet:namespace />ideaScrumDiscussionId:ideaScrumDiscussionId
		},
	    success: function( data ) {
	    	
	    	$.each(data, function(index, element) {
	    			if(level == 0){
	    				messageHTML += '<div id="discussion_'+element.ideaScrumDiscussionId+'" class="Level_'+element.level+'">';			
		    			messageHTML += <portlet:namespace />generateLevelHTML2(element.ideaScrumDiscussionId, element.avatarURL, element.userName,
		    					element.message, element.parentDiscussionId, element.level, element.levelCount, element.isChild.toString(),element.userId,element.isThanks.toString(),element.isInventorPrefsAdmin.toString(), element.isLiked );
			    		messageHTML += '</div>';
						$("#discussionWrapper2").append(messageHTML);
						messageHTML = '';
	    			}else{
		    			messageHTML += <portlet:namespace />generateLevelHTML2(element.ideaScrumDiscussionId, element.avatarURL, element.userName,
		    					element.message, element.parentDiscussionId, element.level, element.levelCount, element.isChild.toString(),element.userId,element.isThanks.toString(),element.isInventorPrefsAdmin.toString(), element.isLiked);
						$('#cab-level-'+ideaScrumDiscussionId).append(messageHTML);
						messageHTML = '';
	    			}
	    	});
			if(level == 0){
				 $('#<portlet:namespace/>responseDiscussionInput2').val('');
				 $('#<portlet:namespace/>responseDiscussionInput2').css('height','54px');
					<portlet:namespace/>goToLastMessage();
					$("#<portlet:namespace/>replayDiv2").toggle(function(){
						$("#<portlet:namespace/>replayDiv2").slideUp();
						isPopUpEdit = true;
					});
				$('#<portlet:namespace />messageButtonWrapper2').removeClass('has-error');
			}else if(level == 2){
				$('#<portlet:namespace/>responseDiscussionInput2_'+ideaScrumDiscussionId).val('');
				 $('#<portlet:namespace/>responseDiscussionInput2_'+ideaScrumDiscussionId).css('height','54px');
				$('#ansr-comment-reply'+ideaScrumDiscussionId).toggle(function(){
					$('#ansr-comment-reply'+ideaScrumDiscussionId).slideUp();
					$("#<portlet:namespace/>replayDiv2").slideUp();
					isPopUpEdit = true;
				});
				$('#ansr-comment-reply'+ideaScrumDiscussionId).removeClass('has-error');
			}else if(level == 2){
				$('#<portlet:namespace/>responseDiscussionInput2_'+ideaScrumDiscussionId).val('');
				 $('#<portlet:namespace/>responseDiscussionInput2_'+ideaScrumDiscussionId).css('height','54px');
				$('#ansr-comment-reply'+ideaScrumDiscussionId).toggle(function(){
					$('#ansr-comment-reply'+ideaScrumDiscussionId).slideUp();
					$("#<portlet:namespace/>replayDiv2").slideUp();
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
	 $("#discussionWrapper2").animate({ scrollTop: $("#discussionWrapper2")[0].scrollHeight}, 0);
 }
 
<portlet:namespace />postReplay2 = function(parentDiscussionId, level, ideaScrumDiscussionId){
	 autosize(document.querySelectorAll('textarea'));
	 <portlet:namespace />autoEnterKey2(ideaScrumDiscussionId);
	if(level == 0){
		$("#<portlet:namespace/>parentDiscussionId").val(parentDiscussionId);
		$("#<portlet:namespace/>level").val(level);
		$("#<portlet:namespace/>ideaScrumDiscussionId").val(ideaScrumDiscussionId);
		$("#<portlet:namespace/>responseDiscussionInput2" ).val('');
		if ($("#<portlet:namespace/>replayDiv2" ).css('display') == 'none') {
			$("#<portlet:namespace/>replayDiv2").toggle(function(){
				   $("#<portlet:namespace/>replayDiv2").slideDown();
				 });
				isPopUpEdit = false;
		}else{
		    $("#<portlet:namespace/>replayDiv2").toggle(function(){
				$("#<portlet:namespace/>replayDiv2").slideUp();
				isPopUpEdit = true;
			});
			$('#<portlet:namespace />messageButtonWrapper2').removeClass('has-error');
		}
	}else{
		$("#<portlet:namespace/>level").val(level);
		$("#<portlet:namespace/>parentDiscussionId").val(parentDiscussionId);
		$('#<portlet:namespace/>ideaScrumDiscussionId').val(ideaScrumDiscussionId);
		$("#<portlet:namespace/>responseDiscussionInput2_"+ideaScrumDiscussionId).val('');
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
// level-2Comment-Submit

/* $("#<portlet:namespace/>responseDiscussionInput2").keyup(function(){
	if($.trim($("#<portlet:namespace/>responseDiscussionInput2").val()) !== '') {
		$("#<portlet:namespace/>level-2Comment-Submit").removeClass("np-display-off");
	} else {
		$("#<portlet:namespace/>level-2Comment").addClass("np-display-off");
	}
	
	if($.trim($("#<portlet:namespace/>responseDiscussionInput2").val()) === '') { 
		$("#<portlet:namespace/>level-2Comment-Submit").addClass("np-display-off");
		$("#<portlet:namespace/>level-2Comment").removeClass("np-display-off");
	}
}); */


</script>
