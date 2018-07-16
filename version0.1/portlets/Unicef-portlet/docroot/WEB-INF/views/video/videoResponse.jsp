<%@page import="com.unicef.util.SolutionEnum"%>
<%@page import="com.unicef.util.SolutionUtil"%>
<%@page import="com.unicef.service.SolutionAnswerVoteService"%>
<%@page import="com.unicef.service.SolutionAnswerService"%>
<%@page import="com.unicef.service.SolutionCommentService"%>
<%@page import="com.unicef.service.SolutionViewService"%>
<%@page import="com.unicef.dao.SolutionAnswerDAO"%>
<%@page import="com.unicef.dao.SolutionViewDAO"%>
<%@page import="com.unicef.dao.SolutionCommentDAO"%>
<%@include file="../init.jsp"%>

<c:choose>
	<c:when test="${not empty videoList}">
		<c:forEach items="${videoList}" var="video">
			
			<portlet:renderURL var="viewVideoUrl">
				<portlet:param name="view" value="viewVideo" />
				<portlet:param name="videoId" value="${video.getVideoId()}" />
			</portlet:renderURL>
			
			<portlet:renderURL var="videoUpdateURL">
				<portlet:param name="view" value="updateVideo" />
				<portlet:param name="videoId" value="${video.getVideoId()}" />
			</portlet:renderURL>
			
			<%-- <portlet:renderURL var="viewVideoURL">
				<portlet:param name="view" value="viewVideo" />
				<portlet:param name="videoId" value="${video.getVideoId()}" />
			</portlet:renderURL> --%>


			<fmt:formatDate pattern="dd MMM, yyyy" value="${video.getExpireDate()}"
				var="expireDate" />
			<tr>
				<td class="il-font-18 il-font-red"><a href="<%=viewVideoUrl.toString() %>">${video.getVideoName()}</a></td>
				<td class="il-font-18 il-font-gray">${expireDate}</td>
				<td class="il-font-15 il-font-red">${video.getVideoUrl()}</td>
				<td class="il-font-15 il-font-gray">${video.getFeeOrderPlacement()}</td>
				<td>
				  	<a href="<%=videoUpdateURL.toString()%>"><img
						src="<%=request.getContextPath()%>/images/edit.png" class="cg-icon-fix01"></a>
				</td>
				<td>
					<a href="javascript:void(0);"
						onclick="javascript:<portlet:namespace />deleteVideo(${video.getVideoId()});"><img
							src="<%=request.getContextPath()%>/images/delete.png"
							class="cg-icon-fix01"/></a>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="4" style="text-align: center;"><liferay-ui:message
					key="no-video-avaialable" /></td>
		</tr>
	</c:otherwise>
</c:choose>