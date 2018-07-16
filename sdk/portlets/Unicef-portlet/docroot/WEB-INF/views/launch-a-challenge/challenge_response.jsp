<%@page import="com.unicef.util.CommonUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="../init.jsp"%>
<c:choose>
	<c:when test="${not empty challengeList}">
		<c:forEach items="${challengeList}" var="challenge">
			<c:set value="${challenge.goalId}" var="goalType" />
			
			<c:set value="${challenge.challengorId}" var="challengor" />
			<c:set value="${challenge.lineOfbussinessId}" var="lofbuss"/> 
			<c:set value="${challenge.challengeId}" var="challengeId" />
			<c:set value="${challenge.challengeEndDate}" var="chEndDate"/>
			
			<portlet:renderURL var="challengeUpdateURL">
				<portlet:param name="action" value="challenge" />
				<portlet:param name="challengeId" value="${challengeId}" />
			</portlet:renderURL>

			   <fmt:formatDate pattern="dd MMM, yyyy" value="${challenge.challengeStartDate}"
				    var="challengeStartDate" />
			
				<fmt:formatDate pattern="dd MMM, yyyy" value="${challenge.challengeEndDate}"
					var="challengeEndDate" />
			<%
				/* Date challengeStartDate = (Date)pageContext.getAttribute("chCreatedDate");
				Date challengeEndDate = (Date)pageContext.getAttribute("chEndDate");
				Date today =  new Date(); */
			
				boolean isArchived = false;/* CommonUtil.getArchivedStatusOfChallenge(today,challengeStartDate,challengeEndDate); */
				boolean isActive = true;/*  CommonUtil.getActiveStatusOfChallenge(today,challengeStartDate,challengeEndDate); */
				
	  			AssetCategory goalAssetCategory = AssetCategoryLocalServiceUtil.getAssetCategory((Long)pageContext.getAttribute("goalType"));
	  			String goalType = goalAssetCategory.getName();
	  			
	  			User userName = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("challengor"));
	  			String challengerName = userName.getFullName();
	  			
	  			AssetCategory verticalAssetCategory = AssetCategoryLocalServiceUtil.getAssetCategory((Long)pageContext.getAttribute("lofbuss"));
	  			String verticalType= verticalAssetCategory.getName();
	  			
	  			long userId = themeDisplay.getUserId();
	  			pageContext.setAttribute("userId", userId);
	  			pageContext.setAttribute("userId", userId);
			%>
			
			<tr>
				<c:choose>
					<c:when test="<%=isArchived %>">
						<td><img src="<%=request.getContextPath()%>/images/archive.png" title="Archived"></td>
					</c:when>
					<c:when test="<%=isActive %>">
						<td><img src="<%=request.getContextPath()%>/images/active.png" title="Active"></td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose> 
				<td class="il-font-18 il-font-red ">${challenge.challengeTitle}</td>
				<td class="il-font-15 il-font-gray ">${challenge.challengeTagline}</td>
				<c:set value="${userId}" var="user" />
				<td class="il-font-15 il-font-gray "><%=challengerName %></td>
				<td class="il-font-15 il-font-gray "><%=goalType%></td>
				<td class="il-font-15 il-font-red "><%=verticalType%></td>
				<c:choose>
					<c:when test="${challenge.challengorId == user}">
						<td><a href="<%=challengeUpdateURL.toString()%>"><img
								src="<%=request.getContextPath()%>/images/edit.png" class="cg-icon-fix01"></a></td>
						<td><a href="javascript:void(0);"
							onclick="javascript:<portlet:namespace />deleteChallenge(${challengeId});"><img
								src="<%=request.getContextPath()%>/images/delete.png" class="cg-icon-fix01"/></a></td>
					</c:when>
					<c:otherwise>
						<td></td>
						<td></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>

	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="13" style="text-align: center;"><liferay-ui:message
					key="no-challenge-avaialable" /></td>
		</tr>
	</c:otherwise>
</c:choose>