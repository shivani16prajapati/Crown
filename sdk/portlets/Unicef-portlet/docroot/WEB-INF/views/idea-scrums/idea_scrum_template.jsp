<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.unicef.domain.Sprint"%>
<%@page import="com.unicef.service.SprintService"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="java.text.DecimalFormat"%>
<%@include file="../init.jsp"%>
<c:choose>
	<c:when test="${not empty ideaScrums}">
		<c:forEach items="${ideaScrums}" var="idea">
			<c:set value="${idea.coInventorId}" var="userId" />
			<c:set value="${idea.hotWeight}" var="hotWeight" />
			<c:set value="${idea.ideaId}" var="ideaId" />
			<c:set value="${idea.innovationType}" var="ideaType" />
			<c:set value="${idea.groupId}" var="groupId" />
			
			<%
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
			String startDate = StringPool.BLANK, endDate = StringPool.BLANK;
			SprintService sprintService = (SprintService)request.getAttribute("sprintService");
			List<Sprint>sprintList = sprintService.getSprintsByIdeaId((Long)pageContext.getAttribute("ideaId"));
			long perOfComplete = 0;
			if(sprintList.size() > 0){
				Date sDate = sprintList.get(0).getStartDate();
				Date eDate = sprintList.get(sprintList.size() -1).getEndDate();
				
				startDate = sdf.format(sDate);
				endDate = sdf.format(eDate);	
				System.out.println("start Date" + startDate);
				System.out.println("End Date" + endDate);
				/* calculate the percentage of completed */
				perOfComplete = IdeaUtil.countPerOfCompletedSprint(sDate, eDate);
			}
			
			pageContext.setAttribute("perOfComplete", perOfComplete);
			pageContext.setAttribute("startDate", startDate);
			pageContext.setAttribute("endDate", endDate);
			
			AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory((Long)pageContext.getAttribute("ideaType"));
			String ideaTypeName = StringPool.BLANK;
			ideaTypeName = assetCategory.getName();
			pageContext.setAttribute("ideaType", ideaTypeName);
			
			User userName = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId"));
  			String coInventerName = StringPool.BLANK; 
  			coInventerName = userName.getFullName();
			pageContext.setAttribute("coInventerName", coInventerName);
			
			String newHotCount = StringPool.BLANK;
			DecimalFormat df = new DecimalFormat("###.#");
			newHotCount = df.format((Double)pageContext.getAttribute("hotWeight"));
  		    pageContext.setAttribute("newHotCount", newHotCount);
			
  		 	String ideaScrumURL = StringPool.BLANK;
			ideaScrumURL = IdeaUtil.getFriendlyURLOfCommunityByIdea((Long)pageContext.getAttribute("ideaId"));
			pageContext.setAttribute("ideaScrumURL", ideaScrumURL);
			%>
			
			<tr>
				<td><a href="${ideaScrumURL}">${idea.ideaTitle}</a></td>
				<td>${coInventerName}</td>
				<td>${startDate}</td>
				<td>${endDate}</td>
				<td>${perOfComplete} %</td>
				<td>${ideaType}</td>
				<td>${newHotCount}</td>
				<!-- <td></td> -->			
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="8" style="text-align: center;">
				<liferay-ui:message key="no-idea-scrum-avaialable" />
			</td>
		</tr>
	</c:otherwise>
</c:choose>