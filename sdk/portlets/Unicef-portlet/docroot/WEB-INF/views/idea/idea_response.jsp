<%@page import="java.text.DecimalFormat"%>
<%@page import="com.unicef.util.IdeaEnum"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.unicef.service.IdeaService"%>
<%@page import="com.unicef.service.IdeaCommentService"%>
<%@page import="com.unicef.service.IdeaViewService"%>
<%@page import="com.unicef.dao.IdeaViewDAO"%>
<%@page import="com.unicef.dao.IdeaCommentDAO"%>
<%@include file="../init.jsp"%>
<c:set value="${newTHRESHOLD}" var="newTHRESHOLD" />
<c:set value="${hotTHRESHOLD}" var="hotTHRESHOLD" />
<% pageContext.setAttribute("isadmin",permissionChecker.isOmniadmin()); %>
<c:choose>
	<c:when test="${not empty ideaList}">
		<c:forEach items="${ideaList}" var="idea">
		  <c:if test="${idea.ideaTitle ne ''}">
			<c:set value="${idea.innovationType}" var="ideaType" />
			<c:set value="${idea.coInventorId}" var="userId" />
			<c:set value="${idea.ideaId}" var="ideaId" />
			<c:set value="${idea.version}" var="ideaVersion"/>
			<c:set value="${idea.status}" var="status"/>
			<c:set value="${idea.submissionDate}" var="createdDate" />
			<c:set value="${idea.isIdeaHot()}" var="isHotness" />
			<c:set value="${idea.hotWeight}" var="hotWeight" />
			
			<portlet:renderURL var="ideaUpdateURL">
				<portlet:param name="view" value="updateIdea" />
				<portlet:param name="ideaId" value="${ideaId}" />
			</portlet:renderURL>
			<portlet:renderURL var="viewIdeaURL">
				<portlet:param name="view" value="viewIdea" />
				<portlet:param name="ideaId" value="${ideaId}" />
			</portlet:renderURL>


			<fmt:formatDate pattern="dd MMM, yyyy" value="${idea.submissionDate}"
				var="submissionDate" />
			<c:if test="${not empty idea.modifiedDate}">
				<fmt:formatDate pattern="dd MMM, yyyy" value="${idea.modifiedDate}"
					var="modifiedDate" />
			</c:if>
			<%
			
	  			AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory((Long)pageContext.getAttribute("ideaType"));
			
	  			User userName = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId"));
	  			
	  			String coInventerName = userName.getFullName();
	  			
	  			String ideaTypeName = assetCategory.getName();
	  			
	  			IdeaService ideaService = (IdeaService)request.getAttribute("ideaService");
	  			IdeaCommentService ideaCommentService = (IdeaCommentService)request.getAttribute("ideaCommentService");
	  			IdeaViewService ideaViewService  = (IdeaViewService)request.getAttribute("ideaViewService");
	  			long ideaAttachement = ideaService.getIdeaAttachementCountByIdeaId((Long)pageContext.getAttribute("ideaId"),(Double)pageContext.getAttribute("ideaVersion"));
	  			long ideaCommentCount = ideaCommentService.getIdeaCommentCount((Long)pageContext.getAttribute("ideaId"));
	  			long ideaViewCount = ideaViewService.getIdeaViewCountByIdeaId((Long)pageContext.getAttribute("ideaId"));
	  			long userId = themeDisplay.getUserId();
	  			pageContext.setAttribute("userId", userId);
	  			Date createdDate = (Date)pageContext.getAttribute("createdDate");
	  			int newTHRESHOLD = Integer.parseInt(String.valueOf(pageContext.getAttribute("newTHRESHOLD")));
	  			int hotTHRESHOLD = Integer.parseInt(String.valueOf(pageContext.getAttribute("hotTHRESHOLD")));
	  			double hotWeight = Double.parseDouble(String.valueOf(pageContext.getAttribute("hotWeight")));
	  			boolean isHotness = Boolean.parseBoolean(String.valueOf(pageContext.getAttribute("isHotness")));
	  			boolean isNew = IdeaUtil.isIdeaNew(newTHRESHOLD, createdDate);
	  			boolean isHot = IdeaUtil.isIdeaHOT(hotTHRESHOLD, createdDate, isHotness, hotWeight);
	  			String IdeaScrumURL = StringPool.BLANK;
	  			if((Integer)pageContext.getAttribute("status") == 2){
	  				IdeaScrumURL = IdeaUtil.getFriendlyURLOfCommunityByIdea((Long)pageContext.getAttribute("ideaId"));
	  			}
	  			String newHotCount = StringPool.BLANK;
	  			
	  		    DecimalFormat df = new DecimalFormat("##.##");
	  		    newHotCount = df.format((Double)pageContext.getAttribute("hotWeight"));
	  		    
	  		    pageContext.setAttribute("newHotCount", newHotCount);
	  			pageContext.setAttribute("IdeaBacklog", IdeaEnum.IDEA_BACKLOG.getValue());
	  			pageContext.setAttribute("IdeaScrum", IdeaEnum.IDEA_SCRUM.getValue());
			%>
			<tr>
			    <td>  <!-- idea img -->
				     <c:choose>
				         <c:when test="<%=isHot %>">
				           <img alt="Hot Idea" src="<%=request.getContextPath()%>/images/hot.png">
				         </c:when>
				         <c:when test="<%=isNew %>">
				           	<img alt="New Idea" src="<%=request.getContextPath()%>/images/new.png">
				         </c:when>  
				         <c:otherwise>
				         	<img alt="Idea" src="<%=request.getContextPath()%>/images/idea.png">
				         </c:otherwise> 
       				 </c:choose>
			    </td>
				<td class="il-font-18 il-font-red"><a href="<%=viewIdeaURL.toString() %>">${idea.ideaTitle}</a></td>  <!-- idea -->
							
				<c:set value="${userId}" var="user" />  
				<td class="il-font-18 il-font-gray"><%=coInventerName %></td>  <!-- inventor -->
				<td class="il-font-15 il-font-gray">${submissionDate}</td>  <!-- created date -->
				<td class="il-font-15 il-font-gray"><c:out
						value="${empty idea.modifiedDate ? 'Not Updated' : modifiedDate}" /></td>
						  <!-- modifyed date -->
				<td class="il-font-18 il-font-gray"><%=ideaTypeName%></td>  <!-- idea type -->
				
				<td class="il-font-14 il-font-red">  <!-- idea stage -->
					<c:choose>
						<c:when test="${idea.status eq IdeaBacklog}">
							IdeaBacklog
						</c:when>
						<c:otherwise>
						<a href="<%=IdeaScrumURL.toString()%>">
							Dsprint
						</a>
							
						</c:otherwise>
					</c:choose>
				</td>
				
				 <td class="il-font-14 il-font-gray" style="text-align: center;">${newHotCount}</td> <!-- hottness -->
				 <td class="il-font-14 il-font-gray" style="text-align: center;">${idea.likes.size()}</td>   <!-- Upvotes -->
				 
				 <%-- <td style="text-align:center;"><%=ideaAttachement %></td>
   				 <td style="text-align:center;"><%=ideaCommentCount %></td>
   				 <td style="text-align:center;"><%=ideaViewCount %></td>--%>
   				 
				<c:choose>
					<c:when test="${idea.coInventorId == user || isadmin}">
						<td> <c:if test="${idea.coInventorId == user}"><a href="<%=ideaUpdateURL.toString()%>"><img
								src="<%=request.getContextPath()%>/images/edit.png" class="cg-icon-fix01"></a></c:if></td>
						<td><a href="javascript:void(0);"
							onclick="javascript:<portlet:namespace />deleteIdea(${ideaId});"><img
								src="<%=request.getContextPath()%>/images/delete.png" class="cg-icon-fix01"/></a></td>
					</c:when>
					<c:otherwise>
						<td></td>
						<td></td>
					</c:otherwise>
				</c:choose> 
				
			</tr>
			</c:if>
		</c:forEach>

	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="13" style="text-align: center;"><liferay-ui:message
					key="no-idea-avaialable" /></td>
		</tr>
	</c:otherwise>
</c:choose>

