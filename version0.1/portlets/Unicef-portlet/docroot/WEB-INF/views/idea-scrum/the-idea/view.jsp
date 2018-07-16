<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.unicef.domain.IdeaAttachement"%>
<%@include file="../../init.jsp"%>
<%@page import="com.unicef.util.IdeaConstant"%>
<portlet:renderURL var="backToIdeaURL"/>
<portlet:renderURL var="updateFromViewURL">
  <portlet:param name="view" value="updateFromView" />
  <portlet:param name="ideaId" value="${idea.ideaId}" />
  <portlet:param name="scopeGroupId" value="${idea.groupId}"/>
  <portlet:param name="companyId" value="${idea.companyId}"/>
</portlet:renderURL>
<portlet:actionURL var="promoteToScrumURL" name="promoteToScrum">
	<portlet:param name="action" value="promoteToScrum"/>
	<portlet:param name="ideaId" value="${idea.ideaId}"/>
</portlet:actionURL>
 
<div class="clearfix np-rounded-box">
  <div class="np-container clearfix">
    <div class="clearfix np-idea-mainbox01">
      <div class="np-idea-imgbox01"> <img src="${userAvtar}" class="image">
       <c:set value="${idea.coInventorId}" var="userId"/>
      <fmt:formatDate pattern="dd MMM, yyyy" value="${idea.submissionDate}" var="submissionDate"/>
      <%
      	User userName = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId"));
		String coInventerName = userName.getFullName();
		long loginUserId = themeDisplay.getUserId();
		pageContext.setAttribute("loginUserId", loginUserId);
      %>
      
      </div>
      <div class="np-idea-title01"><%=coInventerName %></div>
      <div class="col-sm-11 col-xs-12 col-sm-offset-1 ">
		<div style="margin-bottom: 15px ; position: relative;" class="clearfix">
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
				<div class="np-title03">
					<span>${ideaHistory.ideaTitle}</span>
					<span class="clearfix"><span>Posted :${histroyDate}</span><span>Version :${ideaHistory.version}</span> </span>
				</div>
				<div class="np-text01 clearfix"><p>${ideaHistory.description}</p></div>
					<c:set var="ideaId" value="${ideaHistory.idea.ideaId}" scope="page" />
					<c:set var="ideaVersion" value="${ideaHistory.version }" scope="page" />
					<div class="clearfix" style="padding:5px">
					<%
					long ideaId = (Long)pageContext.getAttribute("ideaId");
					double version =(Double) pageContext.getAttribute("ideaVersion");
					List<IdeaAttachement>ideaAttachementList = IdeaUtil.findFileEntryIdByIdeaAndVersion(ideaId, version);
					for (IdeaAttachement ideaAttachement : ideaAttachementList) {
						FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(ideaAttachement.getFileEntryId());
						String url = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, "&imagePreview=1");
						String fileName = fileEntry.getTitle();
					%>
					<a href="<%= url %>" target="_blank" class="cn-linkfix1" ><span class="glyphicon glyphicon-paperclip"></span>&nbsp;<%= fileName %></a>					
					<%}%>
					
				</div>
			</div>
			<c:set var="divId" value="${divId - 1}" scope="page"></c:set>
  	  	</c:forEach> 
		</div>
		<div class="np-buttonbox clearfix">
		 <div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
			<div id='slider'></div>
		</div>
		 
		<div class="col-sm-12">
        <div class="clearfix">
          <div class="pull-left">
           <div class="sp-qust-link01 clearfix buffer-top">
					<c:if test="${loginUserId == userId}">
						<a href="<%=updateFromViewURL.toString()%>">
						<span>Edit</span>
							<span>
								<img src="<%=request.getContextPath()%>/images/edit.png" class="cg-icon-fix01">
							</span> 
							 
						</a>
					</c:if>
		</div>
          	  <c:if test="<%=themeDisplay.isSignedIn() %>">
              
        <div class="sp-qust-link01 clearfix buffer-top">
	           <c:choose>
		          	<c:when test="${ideaLike}">
		          		 <a class="" href="javascript:void(0);" id="<portlet:namespace/>ideLike" 
		           			 onclick="javascript:<portlet:namespace />ideaLike('${idea.ideaId}');">
		           			 <span id="<portlet:namespace/>likeUnlikeText" style="margin-right: 4px !important;">DownVote</span>&nbsp;<span id='<portlet:namespace/>ideaLike' class="badge" style="margin-right: 4px !important;">${ideaLikeCount}</span></a> 
		          	</c:when>
		          	<c:otherwise>
		          		 <a href="javascript:void(0);" id="<portlet:namespace/>ideLike" 
		             		 onclick="javascript:<portlet:namespace />ideaLike('${idea.ideaId}');">
		              		<span id="<portlet:namespace/>likeUnlikeText" style="margin-right: 4px !important;">UpVote</span>&nbsp;<span id='<portlet:namespace/>ideaLike' class="badge">${ideaLikeCount}</span></a>
		          	</c:otherwise>
	          </c:choose></div>
              
        <div class="sp-qust-link01 clearfix buffer-top">
	           <a id='<portlet:namespace/>ideaCommentBtn'  href="javascript:void(0);" onclick="javascript:<portlet:namespace />ideaComment();">
	            <liferay-ui:message key="comments"/>&nbsp;<span class="badge" id='<portlet:namespace/>commentCountDisplay'></span>
	           </a></div>
	          </c:if>
          </div>
        </div></div></div>
      </div>
    </div>
  </div>
  <div class="clearfix buffer-top">
    <div class="col-sm-11 col-sm-offset-1 col-xs-12" id='<portlet:namespace/>ideaCommentView'></div>
  </div>
</div>
<style>
	.div-off{ visibility:hidden; opacity:0; height:0px; overflow : hidden; position:relative; left:0; top:0px;  -webkit-transition: all 0.5s ease-in-out; -moz-transition: all 0.5s ease-in-out; -o-transition: all 0.5s ease-in-out; -ms-transition: all 0.5s ease-in-out; transition: all 0.5s ease-in-out;}
	.div-on{ visibility: visible; opacity:1; z-index:1; height:auto; overflow:auto; position:relative; left:0px; top:0px;  -webkit-transition: all 0.5s ease-in-out; -moz-transition: all 0.5s ease-in-out; -o-transition: all 0.5s ease-in-out; -ms-transition: all 0.5s ease-in-out; transition: all 0.5s ease-in-out;}
	.cn-linkfix1{ padding:5px; border:1px solid #e8e8e8; margin-right:7px; float:left}
	
	.ui-widget-content{ background:#bdbdbd;}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default{ background-color:#207cc7; border:none;}
.ui-slider .ui-slider-range{ background-color:#4da9fc;}

</style>
 <%@ include file="view_js.jsp"%>