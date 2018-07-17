<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.unicef.service.IdeaCommentVoteService"%>
<%@include file="../init.jsp" %>

<input type="hidden" name='<portlet:namespace/>ideaCommentCount' id='<portlet:namespace/>ideaCommentCount' value="${commentCount}">
<c:forEach items="${ideaComments}" var="comment">
	<c:set value="${comment.commenId}" var="commentId"/>
	<c:set value="${ideaId}" var="ideaId"/>
	<c:set value="${ideaCreator}" var="creator"/>
	<c:set value="${comment.userId}" var="commentCreator"/>
	<%
	
	IdeaCommentVoteService voteService = (IdeaCommentVoteService)request.getAttribute("commentVoteService");
	
	Boolean commentVote = voteService.checkIdeaCommentVote((Long)pageContext.getAttribute("commentId"),themeDisplay.getUserId());
	Boolean thankYou = IdeaUtil.checkCommentThanks((Long)pageContext.getAttribute("commentId"), themeDisplay.getUserId());
	long currentWeekCount = voteService.getCurrentWeekCount((Long)pageContext.getAttribute("commentId"),"commentCount");
	
	long currentUser = themeDisplay.getUserId();
	pageContext.setAttribute("commentVoteCount", currentWeekCount );
	pageContext.setAttribute("currentUser", currentUser);
	%>
	<fmt:formatDate pattern="dd MMM, yyyy" value="${comment.commentDate}" var="commentDate"/>
	
	  <div class="col-xs-12 clearfix buffer-top">
	       	 <div class="col-sm-2 col-xs-3 "> 
	       	 	<img class="img-responsive np-img-border" src="${comment.avtarUrl}"> 
	       	 </div>
	        	<div class="col-sm-10 col-xs-12 ">
	          		<div class="np-title05 clearfix">
	          			<span>${comment.userName}</span>
	          			<span>Posted: ${commentDate}</span>
	          			
	          			 <div class="np-title05-addedit-block clearfix" id="<portlet:namespace />commentDeleteDiv_${commentId}">
							<c:if test="${commentCreator == currentUser}">
								<a href="javascript:void(0);" onclick="<portlet:namespace />editIdeaComment('${commentId}','${ideaId}');" class="np-edit-block">edit</a>
							 </c:if>
		          			 <c:if test="${isInventorPrefsAdmin or commentCreator == currentUser}">
		          			 	<a href="javascript:void(0);" onclick="<portlet:namespace />deleteIdeaComment('${commentId}','${ideaId}');" class="np-delete-block">delete</a>
		          			 </c:if>
                   		 </div>
	          		</div>
				       <div class="np-text01 clearfix">
				       		<div id="<portlet:namespace />ideaCommentDiv_${commentId}">
				          		<p id="<portlet:namespace />comment_${commentId}">${comment.comment}</p>
				          	</div>
				          	<a href="javascript:void(0);" style="display:none;"  id="<portlet:namespace />commentMore_${commentId}" onclick="javascript:<portlet:namespace />moreCommentText('${commentId}');">
				          		Read More
				          	</a>
				          	<script type="text/javascript">
				          		var commentDivHieght = $('#<portlet:namespace/>ideaCommentDiv_'+'${commentId}').height();
									if(commentDivHieght > 35){
										$("#<portlet:namespace />ideaCommentDiv_"+'${commentId}').css({height:"25px",overflow:"hidden"});
										$("#<portlet:namespace />commentMore_"+'${commentId}').show();
									}
				          		<portlet:namespace />moreCommentText=function(commentId){
									$("#<portlet:namespace />commentMore_"+commentId).hide();
									$("#<portlet:namespace />ideaCommentDiv_"+commentId).css({height:"",overflow:""});
								}
				          	
				          	</script>
								<div class="clearfix">
									
									<textarea rows="1"  id="<portlet:namespace />hiddenComment_${commentId}" style="display: none;width:100%;"></textarea>
									
									<div id="<portlet:namespace />commentEditDiv_${commentId}" class="clearfix" style="margin-bottom:6px;display: none;">
										<a href="javascript:void(0);" onclick="<portlet:namespace />modifiedIdeaComment('${commentId}','${ideaId}');" style="margin-right:6px;float: left;" >
											<img src="<%=request.getContextPath()%>/images/save-button.png">
										</a>
										<a href="javascript:void(0);" onclick="<portlet:namespace />cancelEditComment('${commentId}');" style="float: left;">
											<img src="<%=request.getContextPath()%>/images/cancel-button.png">
										</a>
									</div>
								</div>
								
								<div class="sp-qust-link01 clearfix">
			          			 <c:if test="${creator eq currentUser and comment.userId ne currentUser}">
						          		<c:choose>
						          			<c:when test="<%=thankYou %>">
						          				   <a class="clearfix" href="javascript:void(0);" id="<portlet:namespace/>commentVote_${commentId}" onclick="javascript:<portlet:namespace />ideaCommentVote('${commentId}','${ideaId}');">
						          				   		<span>Thank You</span>
						          				   		<%-- <span id='<portlet:namespace/>ideaVoteSpan_${commentId}'>${commentVoteCount}</span> --%>
						          				   </a>
						          			</c:when>
						          			<c:otherwise>
							          			  <a class="clearfix np-d-disabled" id="<portlet:namespace/>commentVote_${commentId}">
						          				   	 <span>Thanked</span>
						          				   	 <%-- <span id='<portlet:namespace/>ideaVoteSpan_${commentId}' class="np-d-disabled">${commentVoteCount}</span> --%>
						          				   </a>
						          			</c:otherwise>
						          		</c:choose>
		       					</c:if>
		       					</div>
				         </div>
	         	</div>
	     </div>
</c:forEach>
