<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.unicef.domain.IdeaAttachement"%>
<%@include file="../../init.jsp"%>

<div class="clearfix">
  <div class="np-container clearfix">
    <div class="clearfix">
      <div class="col-sm-2 col-xs-12 "> <img src="${userAvtar}" class="img-responsive np-img-border img-circle">
       <c:set value="${idea.coInventorId}" var="userId"/>
      <fmt:formatDate pattern="dd MMM, yyyy" value="${idea.submissionDate}" var="submissionDate"/>
      <%
      	User userName = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId"));
		String coInventerName = userName.getFullName();
		long loginUserId = themeDisplay.getUserId();
		pageContext.setAttribute("loginUserId", loginUserId);
      %>
      <div class="np-title04"><%=coInventerName %></div>
      </div>
      <div class="col-sm-10 col-xs-12 ">
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
		<%--  <div class="sp-qust-link01 clearfix buffer-top"> 
					<c:if test="${loginUserId == userId}">
						<a href="<%=updateFromViewURL.toString()%>">
						<span>Edit</span>
							<span>
								<img src="<%=request.getContextPath()%>/images/edit.png" class="cg-icon-fix01">
							</span> 
							 
						</a>
					</c:if>
		</div> --%>
		<div class="col-sm-12">
<%--         	<div class="clearfix">
         		 <div class="pull-right">
		          	  <c:if test="<%=themeDisplay.isSignedIn() %>">
			           <c:choose>
				          	<c:when test="${ideaLike}">
				          		 <a class="btn btn-default" href="javascript:void(0);" id="<portlet:namespace/>ideLike" 
				           			 onclick="javascript:<portlet:namespace />ideaLike('${idea.ideaId}');">
				           			 <span id="<portlet:namespace/>likeUnlikeText" style="margin-right: 4px !important;">DownVote</span><span id='<portlet:namespace/>ideaLike' class="badge" style="margin-right: 4px !important;">${ideaLikeCount}</span></a> 
				          	</c:when>
				          	<c:otherwise>
				          		 <a class="btn btn-default" href="javascript:void(0);" id="<portlet:namespace/>ideLike" 
				             		 onclick="javascript:<portlet:namespace />ideaLike('${idea.ideaId}');">
				              		<span id="<portlet:namespace/>likeUnlikeText" style="margin-right: 4px !important;">UpVote</span><span id='<portlet:namespace/>ideaLike' class="badge">${ideaLikeCount}</span></a>
				          	</c:otherwise>
			          </c:choose>
			           <a id='<portlet:namespace/>ideaCommentBtn' class="btn btn-default disabled" href="javascript:void(0);" onclick="javascript:<portlet:namespace />ideaComment();">
			            <liferay-ui:message key="comments"/><span class="badge" id='<portlet:namespace/>commentCountDisplay'></span>
			           </a>
			          </c:if>
          		</div>
        	</div> --%>
        </div></div>
      </div>
    </div>
  </div>
   <%@ include file="view_js.jsp"%>