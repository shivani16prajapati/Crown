<%@page import="com.unicef.util.IdeaScrumUtil"%>
<%@page import="com.unicef.service.SprintService"%>
<%@include file="../../init.jsp"%>
<c:choose>
	<c:when test="${not empty sprintList}">
		<c:forEach items="${sprintList}" var="sprint" varStatus="row">
			<c:set value="${sprint.type}" var="sprintType" />
			<c:set value="${sprint.inventorId}" var="inventorId" />
			<c:set value="${sprint.sprintId}" var="sprintId" />

			<fmt:formatDate pattern="dd MMM, yyyy" value="${sprint.startDate}"
				var="startDate" />
				
				<portlet:actionURL var="deleteSprintURL">
					<portlet:param name="action" value="deleteSprint"/>
					<portlet:param name="sprintId" value="${sprintId}"/>
					<portlet:param name="tab" value="inventor-prefs" />
				</portlet:actionURL>
				
				
				<portlet:renderURL var="sprintUpdateURL">
					<portlet:param name="view" value="editSprint" />
					<portlet:param name="sprintId" value="${sprintId}" />
					<portlet:param name="tab" value="inventor-prefs" />
				</portlet:renderURL>
			<%
	  			User inventorUser = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("inventorId"));
	  			String inventerName = inventorUser.getFullName();
	  			SprintService sprintService = (SprintService)request.getAttribute("sprintService");
	  			long userId = themeDisplay.getUserId();
	  			pageContext.setAttribute("userId", userId);
	  			boolean isInventorPrefsPermission = IdeaScrumUtil.isInventorPrefsPermission(themeDisplay.getCompanyId(), userId, inventorUser.getUserId());
			%>
			
				
			
			<tr class="${sprint.status == 1 ? 'np-current-red':''}" id="${sprint.sprintId}">
			    <td class="center">${sprint.orderIndex}</td>
				<td class="center">${sprint.focusTitle}</td>
				<c:choose>
					<c:when test="${fn:contains(sprint.type, 'Required')}"><td class="center">Required</td></c:when>
					<c:otherwise><td class="center">${sprintType}</td></c:otherwise>
				</c:choose>
				<td class="center">${startDate}</td>
				<td class="center">
				     <c:choose>
				     	<c:when test="${sprint.status == 0}">
				     		<img class="np-imglink001" src="<%=request.getContextPath() %>/images/right-sign-icon.png" width="24" height="24">
				     	</c:when>
				     	<c:when test="${sprint.status == 1}">
				     		<img class="np-imglink001" src="<%=request.getContextPath() %>/images/progress-icon.gif" width="24" height="24">
				     	</c:when>
				     </c:choose>
				</td>
				<c:choose>
					<c:when test="<%=isInventorPrefsPermission %>">
						<td class="center">
						   <c:choose>
							 <c:when test="${fn:contains(sprint.type, 'Required')}">
								<a href="<%=sprintUpdateURL.toString()%>" class="np-imglink001 np-d-disabled">
									<img src="<%=request.getContextPath()%>/images/edit-sign-icon.png">
								</a>
							</c:when>
							<c:otherwise>
								<a href="<%=sprintUpdateURL.toString()%>" class="np-imglink001">
									<img src="<%=request.getContextPath()%>/images/edit-sign-icon.png">
								</a>
							</c:otherwise>
							</c:choose>
						</td>
					</c:when>
					<c:otherwise>
						<td class="center"></td>
					</c:otherwise>
				</c:choose>
				<td class="center">
					<a href="#prebakedPerspectives" class="fancybox np-imglink001"><img src="<%=request.getContextPath()%>/images/idea-sign-icon.png" width="24" height="24"></a>
				</td>
				<c:choose>
					<c:when test="<%=isInventorPrefsPermission %>">
						<td class="center">
							<div style="width:147px; display:block; margin:0 auto;">
							    <c:if test="${not fn:contains(sprint.type, 'Required')}">
							    	 <portlet:renderURL var="sprintInsertURL">
										<portlet:param name="view" value="createSprint" />
										<portlet:param name="sprintId" value="${sprintId}" />
										<portlet:param name="tab" value="inventor-prefs" />
									 </portlet:renderURL>
									 <c:if test="<%=themeDisplay.isSignedIn() %>">
									     <a href="<%=sprintInsertURL %>" class="np-insert-sprint-icon" onmouseover="Liferay.Portal.ToolTip.show(this, 'Insert below');">insert-sprint</a>
									 </c:if>
									 <c:if test="${sprint.orderIndex != 1}">
									 	<a href="javascript:void(0);" class="np-upaero-icon cg-up">top</a>
								     	<a href="javascript:void(0);" class="np-downaero-icon cg-down">down</a>
										<a href="<%=deleteSprintURL.toString() %>" class="np-delete-icon">delete</a>
									 </c:if>
								</c:if>
							</div>
						</td>
					</c:when>
					<c:otherwise>
						<td class="center"></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>

	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="11" style="text-align: center;"><liferay-ui:message key="no-sprint-available" /></td>
		</tr>
	</c:otherwise>
</c:choose>
