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
<c:set value="${newTHRESHOLD}" var="newTHRESHOLD" />
<c:set value="${hotTHRESHOLD}" var="hotTHRESHOLD" />
<% pageContext.setAttribute("isadmin",permissionChecker.isOmniadmin()); %>
<c:choose>
	<c:when test="${not empty solutionList}">
		<c:forEach items="${solutionList}" var="solution">
			<c:set value="${solution.spaceId}" var="solutionType" />
			<c:set value="${solution.coInventorId}" var="userId" />
			<c:set value="${solution.solutionId}" var="solutionId" />
			<c:set value="${solution.status}" var="status"/>
			<c:set value="${solution.submissionDate}" var="createdDate" />
			<c:set value="${solution.isSolutionHot()}" var="isHotness" />
			<c:set value="${solution.hotWeight}" var="hotWeight" />
			<portlet:renderURL var="solutionUpdateURL">
				<portlet:param name="view" value="updateSolution" />
				<portlet:param name="solutionId" value="${solutionId}" />
			</portlet:renderURL>
			<portlet:renderURL var="viewSolutionURL">
				<portlet:param name="view" value="viewSolution" />
				<portlet:param name="solutionId" value="${solutionId}" />
			</portlet:renderURL>


			<fmt:formatDate pattern="dd MMM, yyyy" value="${solution.submissionDate}"
				var="submissionDate" />
			<c:if test="${not empty solution.modifiedDate}">
				<fmt:formatDate pattern="dd MMM, yyyy" value="${solution.modifiedDate}"
					var="modifiedDate" />
			</c:if>
			<tr>
				<%
				  		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory((Long)pageContext.getAttribute("solutionType"));
				  		String coInventerName = StringPool.BLANK;
				  	     try{
				  	          User userName = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId")); 
				  	          coInventerName = userName.getFullName();
				  	        }catch(Exception e){
				  	          e.printStackTrace();
				  	         }
				  			String solutionTypeName = assetCategory.getName();
				  			long userId = themeDisplay.getUserId();
				  			pageContext.setAttribute("userId", userId);
				  			SolutionCommentService solutionCommentService  = (SolutionCommentService)request.getAttribute("solutionCommentService");
				  	        SolutionViewService solutionViewService = (SolutionViewService)request.getAttribute("solutionViewService");
				  	        long solutionCommentCount = solutionCommentService.getSolutionCommentCount((Long)pageContext.getAttribute("solutionId"));
				  	        long solutionViewCount = solutionViewService.getSolutionViewCountBySolutionId((Long)pageContext.getAttribute("solutionId"));
				  	        SolutionAnswerService solutionAnswerService = (SolutionAnswerService)request.getAttribute("solutionAnswerService");
				            long solutionAnswerCount = solutionAnswerService.getSoutionAnswerCount((Long)pageContext.getAttribute("solutionId"));
				            
				            SolutionAnswerVoteService answerVoteService = (SolutionAnswerVoteService)request.getAttribute("solutionAnswerVoteService");
				            long maxAnswerCount = answerVoteService.getWantAnswerCount((Long)pageContext.getAttribute("solutionId"));
				            int newTHRESHOLD = Integer.parseInt(String.valueOf(pageContext.getAttribute("newTHRESHOLD")));
				  			int hotTHRESHOLD = Integer.parseInt(String.valueOf(pageContext.getAttribute("hotTHRESHOLD")));
				  			double hotWeight = Double.parseDouble(String.valueOf(pageContext.getAttribute("hotWeight")));
				  			boolean isHotness = Boolean.parseBoolean(String.valueOf(pageContext.getAttribute("isHotness")));
				  			Date createdDate = (Date)pageContext.getAttribute("createdDate");
				  			boolean isNew = SolutionUtil.isSolutionNew(newTHRESHOLD, createdDate);
				  			boolean isHot = SolutionUtil.isSolutionHOT(hotTHRESHOLD, createdDate, isHotness, hotWeight);
				  			String SolutionScrumURL = StringPool.BLANK;
				  			if((Integer)pageContext.getAttribute("status") == 2){
				  				SolutionScrumURL = SolutionUtil.getFriendlyURLOfCommunityBySolution((Long)pageContext.getAttribute("solutionId"));
				  			}
				  			pageContext.setAttribute("SolutionBacklog", SolutionEnum.SOLUTION_BACKLOG.getValue());
				  			pageContext.setAttribute("SolutionScrum", SolutionEnum.SOLUTION_SCRUM.getValue());
				  	         
				  %>
				<c:set value="${userId}" var="user" />
				<td>
				     <c:choose>
				         <c:when test="<%=isHot %>">
				           <img alt="Hot Solution" src="<%=request.getContextPath()%>/images/hot.png">
				         </c:when>
				         <c:when test="<%=isNew %>">
				           	<img alt="New Solution" src="<%=request.getContextPath()%>/images/new.png">
				         </c:when>  
				         <c:otherwise>
				         	<img alt="Solution" src="<%=request.getContextPath()%>/images/solution.png">
				         </c:otherwise> 
       				 </c:choose>
			    </td>
				<td class="il-font-18 il-font-red"><a href="<%=viewSolutionURL.toString() %>">${solution.solutionTitle}</a></td>
				<td class="il-font-18 il-font-gray"><%=coInventerName %></td>
				<td class="il-font-15 il-font-gray">${submissionDate}</td>
				<td class="il-font-14 il-font-gray"><c:out
						value="${empty solution.modifiedDate ? 'Not Updated' : modifiedDate}" /></td>
				<td class="il-font-15 il-font-gray"><%=solutionTypeName%></td>
				<td class="il-font-15 il-font-red">
						<c:choose>
						<c:when test="${solution.status eq SolutionBacklog}">
							Solution Backlog
						</c:when>
						<c:otherwise>
						<a href="<%=SolutionScrumURL.toString()%>">
							Solution Scrum
						</a>
							
						</c:otherwise>
					</c:choose>
				</td>
				<td class="il-font-14 il-font-gray" style="text-align:center;"><%=maxAnswerCount %></td>
				<td class="il-font-14 il-font-gray"  style="text-align:center;"><%=solutionAnswerCount %></td>
				
			<%-- 		<td class="il-font-14 il-font-gray" style="text-align:center;"><%=solutionCommentCount %></td>
    			<td class="il-font-14 il-font-gray" style="text-align:center;"><%=solutionViewCount %></td>--%>
    			
				<c:choose>
					<c:when test="${solution.coInventorId == user || isadmin}">
					
						<td>
						   <c:if test="${solution.coInventorId == user}">
						  	<a href="<%=solutionUpdateURL.toString()%>"><img
									src="<%=request.getContextPath()%>/images/edit.png" class="cg-icon-fix01"></a>
						  </c:if>
						</td>
						<td>
						
						<a href="javascript:void(0);"
							onclick="javascript:<portlet:namespace />deleteSolution(${solutionId});"><img
								src="<%=request.getContextPath()%>/images/delete.png"
								class="cg-icon-fix01"/></a>
						
						</td>
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
					key="no-solution-avaialable" /></td>
		</tr>
	</c:otherwise>
</c:choose>