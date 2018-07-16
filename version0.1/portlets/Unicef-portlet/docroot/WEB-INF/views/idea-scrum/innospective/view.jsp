<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@include file="../../init.jsp"%>
<portlet:renderURL var="ideaRateChartURL" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>">
	<portlet:param name="view" value="innospective-view"/>
	<portlet:param name="tab" value="innospective"/>
</portlet:renderURL>

<div class="clearfix np-rounded-box"><div class="chat-container clearfix">
	<div class="chat-question-box clearfix">
		<div class="chat-user-img-box clearfix">
		    <div class="chat-user-img"><img src=""></div>
		    <div class="chat-user-img-text01">Moses Ma</div>
	    </div>
	    <div class="chat-user-comment">Innospective Tab
		    <div class="chat-user-reply-section clearfix">
		    	<div class="fr">
		    		
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
			 	<div class="chat-user-img"><img src=""></div>
    			<div class="chat-user-img-text01">Moses Ma</div>
    		</div>
			<div id="<portlet:namespace />replayDiv">
    		<div class="chat-user-comment" id="<portlet:namespace />messageButtonWrapper">
  				<textarea class="cs-chat-form-control-01" id="<portlet:namespace/>responseDiscussionInput" name="<portlet:namespace/>responseDiscussionInput"></textarea>
 				 <a href="javascript:void(0);" id='<portlet:namespace/>level-1Comment' class="btn btn-info chat-btn" onclick="javascript:<portlet:namespace/>sendResponseMessage();">Reply</a>
 			</div>
			</div>
	</div>
</div></div>