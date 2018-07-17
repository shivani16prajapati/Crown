<%@page import="com.unicef.util.IdeaConstant"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.unicef.domain.IdeaAttachement"%>
<%@page import="com.unicef.domain.Idea"%>
<%@page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@include file="../init.jsp"%>

<portlet:renderURL var="backToIdeaURL"/>
<portlet:resourceURL var="updateIdeaInfoURL" id="updateIdeaInfoURL" />

<portlet:renderURL var="updateIdeaOnlyURL">
  <portlet:param name="view" value="updateFromView" />
  <portlet:param name="ideaId" value="${idea.ideaId}" />
  <portlet:param name="editIdeaInfoOnly" value="true" />
</portlet:renderURL>
<portlet:renderURL var="updateFromViewURL">
  <portlet:param name="view" value="updateFromView" />
  <portlet:param name="ideaId" value="${idea.ideaId}" />
  <portlet:param name="editIdeaInfoOnly" value="false" />
</portlet:renderURL>
<portlet:actionURL var="promoteToScrumURL" name="promoteToScrum">
	<portlet:param name="action" value="promoteToScrum"/>
	<portlet:param name="ideaId" value="${idea.ideaId}"/>
</portlet:actionURL>

 <liferay-ui:success key="<%=IdeaConstant.IDEA_PROMOTE_TO_SCRUM_SUCCESSFULLY%>" message="idea-promote-to-scrum-successfully"/>
 
 <liferay-ui:error key="<%=IdeaConstant.ERROR_IN_PRMOTE_IDEA_SCRUM %>" message="error-in-create-idea-scrum"/>
 
 <liferay-ui:error key="<%=IdeaConstant.DUPLICATE_PROMOTE_IDEA_SCRUM %>" message="Idea promoted to scrum successfully, Already"/>
 
 <liferay-ui:error key="<%=IdeaConstant.DUPLICATE_IDEA_FOUND %>" message="Duplicate-idea-found"/>
 
 <script type="text/javascript">
	var addthis_config = {
   		 pubid: "ra-550d8fe213da5069"
	}
 </script>
 
 <script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js"></script>

<div class="container">
	<div class="row clearfix">
		<div class="col-xs-12">
		<c:if test="${duplicateidea eq 1}">
			<div class="alert alert-danger"><liferay-ui:message key="Duplicate-idea-found"/></div>
		</c:if>
		<div class="unv2-title01  ">Idea Discussion</div>
		
			<a href="<%=backToIdeaURL.toString() %>" class="btn btn-default">
				<span aria-hidden="true" class="glyphicon glyphicon-menu-left"></span>Back
			</a>
		</div>
		 <c:set value="${idea.coInventorId}" var="userId"/>
		 <c:set var="currentIdeaId" value="${idea.ideaId}"/>
		 <fmt:formatDate pattern="dd MMM, yyyy" value="${idea.submissionDate}" var="submissionDate"/>
		  <%
		 	String IdeaScrumURL = StringPool.BLANK;
			IdeaScrumURL = IdeaUtil.getFriendlyURLOfCommunityByIdea((Long)pageContext.getAttribute("currentIdeaId"));
	      	User userName = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId"));
			String coInventerName = userName.getFullName();
			long loginUserId = themeDisplay.getUserId();
			pageContext.setAttribute("loginUserId", loginUserId);
      	%>
		<div class="col-md-7 col-md-offset-3 col-xs-12 buffer-bottom" style="background-color:#fff; padding:0px; margin-top:46px;">
			 
			 <c:choose>
			 	<c:when test="${empty ideaHistory.invName}">
			 		<div class="sp-qust-usertext"><%=coInventerName %></div>
			 	</c:when>
			 	<c:otherwise>
			 		<div class="sp-qust-usertext">${ideaHistory.invName}</div>
			 	</c:otherwise>		 
			 </c:choose>
			 
			  <div class="sp-qust-box01 clearfix">
			  
		        <div class="sp-qust-userimg"> 
		        	<img src="${userAvtar}" width="120" height="120"> 
		        	 
		        </div>
		        

				<c:set var="divId" value="${historyListSize}" scope="page" /> 
				<c:forEach items="${ideaHistories}" var="ideaHistory">
		       	 	<c:choose>
						<c:when test="${divId eq historyListSize}">
							<div id='slider_${divId}' class='div-on unicef-slide'>
						</c:when>
						<c:otherwise>
							<div id='slider_${divId}' class='div-off unicef-slide'>
						</c:otherwise>
					</c:choose>
				
		        
		        <fmt:formatDate pattern="dd MMM, yyyy" value="${ideaHistory.submissionDate}" var="histroyDate"/>
				<div class="sp-qust-title01">
					<span>${ideaHistory.ideaTitle}</span>
					<span class="clearfix"><span>Version Posted :${histroyDate}</span><span>Version :${ideaHistory.version}</span> </span>
				</div>
				
				
		        
		       
		         <div class="sp-qust-box02">
		        
				<div class="sp-qust-title02" id="<portlet:namespace />ideaDescription_${divId}">
					<input type="hidden" id="<portlet:namespace />originalIdeaDescription" name="<portlet:namespace />originalIdeaDescription" value="${ideaHistory.description}" />
						<p>${ideaHistory.description}</p>
						<%-- <textarea class="cs-chat-form-control-01"
							style="border: none !important;" readonly="readonly" id="<portlet:namespace/>orgIdeaDesc">${ideaHistory.description}</textarea> --%>
						</div>
					<a href="javascript:void(0);" style="display:none;" id="<portlet:namespace />readMore_${divId}" onclick="javascript:<portlet:namespace />moreText('${divId}');">
						Read More
					</a>
					<c:set var="ideaId" value="${ideaHistory.idea.ideaId}" scope="page" />
					<c:set var="ideaVersion" value="${ideaHistory.version }" scope="page" />
					
					<div class="clearfix" style="padding:5px">
						
						<!-- <input type="file" id="changeIdeaImageWith" name="changeIdeaImageWith" class="np-display-off" /> -->
						
							<%
								long ideaId = (Long)pageContext.getAttribute("ideaId");
								double version =(Double) pageContext.getAttribute("ideaVersion");
								List<IdeaAttachement>ideaAttachementList = IdeaUtil.findFileEntryIdByIdeaAndVersion(ideaId, version);
								for (IdeaAttachement ideaAttachement : ideaAttachementList) {
									if(ideaAttachement.getFileEntryId()!=0){
										FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(ideaAttachement.getFileEntryId());
										String url = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, "&imagePreview=1");
										String fileName = fileEntry.getTitle();
										long attachmentId = ideaAttachement.getAttachementId();
									
							%>
							
							<%-- 
								<a href="<%= url %>" target="_blank" class="cn-linkfix1" >
									<span class="glyphicon glyphicon-paperclip"></span>&nbsp;<%= fileName %>
								</a> 
							--%>
							<aui:input type="hidden" name="ideaUpImageAttachmentId" value="<%=attachmentId%>" />
							
							<input type="hidden" id="<portlet:namespace/>originalIdeaImage" name="<portlet:namespace/>originalIdeaImage" value="<%=url%>" /> 
							<img src="<%=url%>" id="<portlet:namespace/>updatedIdeaImageBy"
								name="<portlet:namespace/>updatedIdeaImageBy" width="250px"
								heigh="250px" class="cn-linkfix1" />
							<%}
							}%>	
				</div>
					<c:set var="divId" value="${divId - 1}" scope="page"></c:set>
				</div>
				 </div>
  	  			</c:forEach> 
  	  		
		
		 <%--  <div class="col-xs-12 clearfix buffer-bottom">
			 <c:if test="${isPrmoted eq true}">
			 	<div>
			 		<b><liferay-ui:message key="this-idea-was-promoted-to-be-devloed-further"/></b>
			 			<a href="<%=IdeaScrumURL.toString()%>">
							Click Here
						</a>&nbsp;<b>to go its IDEASCRUM page.</b>
			 	</div>
			 </c:if>
		 </div> --%>
		 <div class="col-xs-12 clearfix buffer-bottom">
			<div id='slider'></div>
		 </div>
		 
		 <%
		 double ideaVersionT =(Double) pageContext.getAttribute("ideaVersion");
		 %>
		 
	        <div class="sp-qust-link01 clearfix buffer-top col-xs-12"> 
				<%-- <c:if test="${loginUserId == userId}">
					<a href="<%=updateIdeaOnlyURL%>"  id="<portlet:namespace/>editOrgIdea">
						<span>Edit Idea</span>
						<span>
							<img src="<%=request.getContextPath()%>/images/edit.png" class="cg-icon-fix01">
						</span> 
					</a>
					<a href="javascript:void(0);" class="np-display-off" id="<portlet:namespace/>updateIdea" onclick="javascript:<portlet:namespace/>updateIdeaInfo(${idea.ideaId}, <%=ideaVersionT%> );">
						<span>Update Idea</span>
						<span>
							<img src="<%=request.getContextPath()%>/images/save-button.png" class="cg-icon-fix01">
						</span> 
					</a>
					<a href="javascript:void(0);" class="np-display-off" id="<portlet:namespace/>cancelIdeaUpdate" onclick="javascript:<portlet:namespace/>cancelEditIdea();">
						<span>Cancel</span>
						<span>
							<img src="<%=request.getContextPath()%>/images/cancel-button.png" class="cg-icon-fix01">
						</span> 
					</a>
				</c:if> --%>
				<c:if test="${loginUserId == userId}">
					<a href="<%=updateFromViewURL.toString()%>">
						<span>Edit Submission</span>
						<span>
							<img src="<%=request.getContextPath()%>/images/edit2.png" class="cg-icon-fix01">
						</span> 
					</a>
				</c:if>
	        	<c:if test="<%=themeDisplay.isSignedIn() %>">
	        	 			<a id='<portlet:namespace/>ideaCommentBtn' href="javascript:void(0);">
	        				 	<span id='<portlet:namespace/>commentText'></span>
	        				 	<span id='<portlet:namespace/>commentCountDisplay'></span>
	        				 </a> 
	        				<c:choose>
	        					<c:when test="${ideaLike}">	
	        						<a href="javascript:void(0);" id="<portlet:namespace/>ideLike" onclick="javascript:<portlet:namespace />ideaLike('${idea.ideaId}');">
		           						<span id="<portlet:namespace/>likeUnlikeText">DownVote</span>
		           						<span id='<portlet:namespace/>ideaLike'>${ideaLikeCount}</span>
		           					</a> 
	        					</c:when>
	        					<c:otherwise>
	        						<a href="javascript:void(0);" id="<portlet:namespace/>ideLike" onclick="javascript:<portlet:namespace />ideaLike('${idea.ideaId}');">
		              					<span id="<portlet:namespace/>likeUnlikeText">UpVote</span>
		              					<span id='<portlet:namespace/>ideaLike'>${ideaLikeCount}</span>
		              				</a>
	        					</c:otherwise>
	        				</c:choose>
	        				<c:if test="${isRoleforendorsed and IsideaEndorsed eq 0}">
	        				<c:choose>
	        				   <c:when test="${ideaEndorsed}">
	        				        <a href="javascript:void(0);" id="<portlet:namespace/>ideEndorsed" onclick="javascript:<portlet:namespace />ideaEndorsed('${idea.ideaId}');">
		           						<span id="<portlet:namespace/>endrorsedText">DeEndorsed</span>
		           						<span id='<portlet:namespace/>ideaEndorsed'>${ideaEndorsedCount}</span>
		           					</a> 
	        				   </c:when>
	        				   <c:otherwise>
	        				       <a href="javascript:void(0);" id="<portlet:namespace/>ideEndorsed" onclick="javascript:<portlet:namespace />ideaEndorsed('${idea.ideaId}');">
		           						<span id="<portlet:namespace/>endrorsedText">Endorsed</span>
		           						<span id='<portlet:namespace/>ideaEndorsed'>${ideaEndorsedCount}</span>
		           					</a> 
	        				   </c:otherwise>
	        				</c:choose>
	        				</c:if>
	        				<c:if test="${IsideaEndorsed eq 1}">
	        				       <a href="javascript:void(0);" id="<portlet:namespace/>ideEndorsed">
		           						<span id="<portlet:namespace/>endrorsedText">Idea Promoted To Finalist</span>
		           					</a> 
		           	        </c:if>
			       <%--  <%
		         		String shareURL = PortalUtil.getCurrentURL(request);
		         	%>
		        	<a href="<%=shareURL %>" class="addthis_button" addthis:url="<%=shareURL%>" addthis:title="${ideaHistory.ideaTitle}">
		        		<span>SHARE</span>
		        		<span><span style="color:#fff;font: bold 22px/15px arial;margin: 0px;padding: 0;">+</span></span>
		        	</a> --%>
	        	</c:if>
	        	<%-- <c:if test="${isPromoteToScrum}">
					<a href="javascript:void(0);" onclick="javascript:<portlet:namespace/>promoteToScrum('<%=promoteToScrumURL.toString() %>');">
						<liferay-ui:message key="prmote-to-scrum"/>
					</a>
				</c:if> --%>
				
	        </div>
		   </div>
	    </div>

	</div>
 <div class="row clearfix">
    <div class="col-md-7 col-md-push-3 col-xs-12 buffer-bottom" style="background-color:#fff; ">
      <div class="sp-qust-box03">
      	<div class="sp-qust-title03 clearfix">
     		 <span id='<portlet:namespace/>commentCount'></span><span>&nbsp;</span>
     	 </div>
      	<div class="row" id='<portlet:namespace/>ideaCommentView'></div>
      </div>
    </div>
    <%
    	User currentUser = UserLocalServiceUtil.getUser(themeDisplay.getUserId());
    	String currentUserAvtar = currentUser.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(currentUser.getPortraitId()) :
        IdeaUtil.getUserImagePathScreenName(currentUser.getScreenName(), currentUser.getCompanyId());
    	String currentUserName = currentUser.getFullName();
    %>
    <div class="col-md-3 col-md-pull-7 col-xs-12">
      <div class="sp-ans-mainbox">
        <!-- <div class="sp-title01">Post Your Comment</div> -->
        <div class="clearfix" id='<portlet:namespace/>postComment' style="background-color:#fff;">
        	<div class="clearfix" style="background-color:#bcbcbc; "><div class="sp-ans-part01"><img src="<%=currentUserAvtar%>"></div>
            	<div class="sp-ans-part02 sp-title02"><span><%=currentUserName %></span><span><liferay-ui:message key="site-user"/></span></div>
          	</div>
          <div class="clearfix">
            <textarea id='<portlet:namespace/>ideaCommentTxt' placeholder="Write & post your comment here..." rows="1" class="new-taControl"></textarea>
          </div>
           <a class="new-cBtn" href="javascript:void(0);" id="<portlet:namespace />ideaComment" onclick="javascript:<portlet:namespace />postComment(${idea.ideaId});">
       			<liferay-ui:message key="comment" />
      		</a>
        </div>
      </div>
    </div>
    <div class="col-md-2 col-xs-12"> <span style="min-height:300px; display:block;"></span></div>
  </div>
</div> 

<style>
	.div-off{ visibility:hidden; opacity:0; height:0px; overflow : hidden; position:relative; left:0; top:0px;  -webkit-transition: all 0.5s ease-in-out; -moz-transition: all 0.5s ease-in-out; -o-transition: all 0.5s ease-in-out; -ms-transition: all 0.5s ease-in-out; transition: all 0.5s ease-in-out;}
	.div-on{ visibility: visible; opacity:1; z-index:1; height:auto; overflow:auto; position:relative; left:0px; top:0px;  -webkit-transition: all 0.5s ease-in-out; -moz-transition: all 0.5s ease-in-out; -o-transition: all 0.5s ease-in-out; -ms-transition: all 0.5s ease-in-out; transition: all 0.5s ease-in-out;}
	.cn-linkfix1{ padding:5px; border:1px solid #e8e8e8; margin-right:7px; float:left}
</style>

<script>

$( document ).ready(function() { 
	$( ".np-portlate-fix01" ).removeClass( "np-portlate-fix01" ).addClass( "np-portlate-fix0111" );
 });


</script>



<%@ include file="viewIdea_js.jsp"%>