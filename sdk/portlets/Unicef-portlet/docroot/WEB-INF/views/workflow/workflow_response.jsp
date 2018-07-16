<%@page import="com.unicef.util.SolutionEnum"%>
<%@page import="com.unicef.service.WorkflowCommentService"%>
<%@page import="com.unicef.domain.WorkflowComment"%>
<%@page import="com.unicef.service.WorkflowLikeService"%>
<%@page import="com.unicef.util.SocialWorkflowEnum"%>
<%@page import="com.unicef.util.CommonUtil"%>
<%@page import="com.unicef.util.IdeaEnum"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@include file="../init.jsp"%>

<input type="hidden" name='<portlet:namespace/>workflowSize'
	id='<portlet:namespace/>workflowSize' value="${workflowSize}">
<c:choose>
	<c:when test="${not empty workflowList}">
		<c:forEach items="${workflowList}" var="workflow">
			<%
				pageContext.setAttribute("Draft",
									SocialWorkflowEnum.WORKFLOW_DRAFT.getValue());
							pageContext.setAttribute("InProgress",
									SocialWorkflowEnum.WORKFLOW_IN_PROGRESS
											.getValue());
							pageContext.setAttribute("Blocked",
									SocialWorkflowEnum.WORKFLOW_BLOCKED.getValue());
							pageContext.setAttribute("Completed",
									SocialWorkflowEnum.WORKFLOW_COMPLETED
											.getValue());
							pageContext.setAttribute("Idea BackLog",
									IdeaEnum.IDEA_BACKLOG.getValue());
							pageContext.setAttribute("Idea Scrum",
									IdeaEnum.IDEA_SCRUM.getValue());
							pageContext.setAttribute("Solution BackLog",
									SolutionEnum.SOLUTION_BACKLOG.getValue());
							pageContext.setAttribute("Solution Scrum",
									SolutionEnum.SOLUTION_SCRUM.getValue());
			%>

			<c:set value="${workflow.taskId}" var="taskId" />
			<c:set value="${workflow.actor}" var="userId" />
			<c:set value="${workflow.status}" var="status" />
			<c:set value="${workflow.app}" var="app" />
			<c:if test="${status eq InProgress}">
				<c:set value="${workflow.acceptorUserId}" var="acceptedUserId" />
			</c:if>
			<c:set value="${workflow.acceptedDate}" var="acceptedDate" />
			<c:set value="${workflow.createdDate}" var="createdDate" />
			<c:set value="${workflow.modifiedDate}" var="modifiedDate" />
			<c:set value="${workflow.idea.ideaId}" var="ideaId" />
			<c:set value="${workflow.solution.solutionId}" var="solutionId" />

			<fmt:formatDate pattern="M/dd/yyyy" value="${acceptedDate}"
				var="acceptedDate" />
			<%
				WorkflowLikeService likeService = (WorkflowLikeService) request
									.getAttribute("workflowLikeService");
							WorkflowCommentService commentService = (WorkflowCommentService) request
									.getAttribute("workflowCommentService");
							User activityUser = UserLocalServiceUtil
									.getUser((Long) pageContext
											.getAttribute("userId"));

							String activityDate = StringPool.BLANK;
							User userAvtar = null;
							String userAvtarUrl = StringPool.BLANK;
							User acceptedUser = null;
							String newDate = StringPool.BLANK;
							if ((Integer) pageContext.getAttribute("status") == 0) {
								userAvtar = UserLocalServiceUtil
										.getUser((Long) pageContext
												.getAttribute("userId"));
								userAvtarUrl = userAvtar.getPortraitId() > 0 ? IdeaUtil
										.getUserImagePath(userAvtar.getPortraitId())
										: IdeaUtil.getUserImagePathScreenName(
												userAvtar.getScreenName(),
												userAvtar.getCompanyId());
								activityDate = commentService
										.calculateEpocTime((Date) pageContext
												.getAttribute("modifiedDate"));
							} else {
								userAvtar = UserLocalServiceUtil
										.getUser((Long) pageContext
												.getAttribute("acceptedUserId"));
								userAvtarUrl = userAvtar.getPortraitId() > 0 ? IdeaUtil
										.getUserImagePath(userAvtar.getPortraitId())
										: IdeaUtil.getUserImagePathScreenName(
												userAvtar.getScreenName(),
												userAvtar.getCompanyId());
								acceptedUser = UserLocalServiceUtil
										.getUser((Long) pageContext
												.getAttribute("acceptedUserId"));
								activityDate = commentService
										.calculateEpocTime((Date) pageContext
												.getAttribute("modifiedDate"));
							}

							Boolean workflowLike = likeService.checkWorkflowLike(
									(Long) pageContext.getAttribute("taskId"),
									themeDisplay.getUserId());

							String status = StringPool.BLANK;
							PortletURL customPortletURL = null;
							if (((Long) pageContext.getAttribute("ideaId")) != null) {
								customPortletURL = CommonUtil
										.createIdeaPortletURL(request, themeDisplay
												.getPlid(), ((Long) pageContext
												.getAttribute("ideaId")),
												themeDisplay.getScopeGroupId());
								if ((Integer) pageContext.getAttribute("app") == 1) {
									status = "Idea BackLog";
								} else {
									status = "Idea Scrum";
								}

							} else {
								customPortletURL = CommonUtil
										.createSolutionPortletURL(
												request,
												themeDisplay.getPlid(),
												((Long) pageContext
														.getAttribute("solutionId")),
												themeDisplay.getScopeGroupId());

								if ((Integer) pageContext.getAttribute("app") == 1) {
									status = "Solution Backlog";
								} else {
									status = "Solution Scrum";
								}
							}

							long likeCount = likeService
									.getLikeCountByTaskId((Long) pageContext
											.getAttribute("taskId"));
							long commentCount = commentService
									.getCommentCountByTaskId((Long) pageContext
											.getAttribute("taskId"));
							long currentUserId = themeDisplay.getUserId();

							User currentUser = UserLocalServiceUtil
									.getUser(currentUserId);
							String currentUserAvtar = currentUser.getPortraitId() > 0 ? IdeaUtil
									.getUserImagePath(currentUser.getPortraitId())
									: IdeaUtil.getUserImagePathScreenName(
											currentUser.getScreenName(),
											currentUser.getCompanyId());

							List<WorkflowComment> comments = commentService
									.getCommentFromTaskId((Long) pageContext
											.getAttribute("taskId"));

							pageContext.setAttribute("currentUserAvtar",
									currentUserAvtar);
							pageContext.setAttribute("workflowLike", workflowLike);
							pageContext.setAttribute("likeCount", likeCount);
							pageContext.setAttribute("commentCount", commentCount);
							pageContext.setAttribute("commentList", comments);
			%>
			<div class="container">
				<div class="sop-main-box clearfix">
					<div class="sop-container clearfix">
						<div class="sop-box01 clearfix">
							<div id='<portlet:namespace/>workflowStatus_${taskId}'>
								<div class="sop-user-box">
									<div class="sop-user-imgbox">
										<img src="<%=userAvtarUrl%>" width="120" height="120">
									</div>
									<div class="sop-user-imgbox2">
										<img src="<%=request.getContextPath()%>/images/user-round-icon.png" width="26" height="26">
									</div>
								</div>
								<div class="sop-head">
									<span class="sop-head-aero"></span>
									<div class="sop-head-text01"><%=status%></div>
									<div class="sop-head-text02"><%=activityDate%></div>
								</div>
								<div class="sop-box02 clearfix">
									<div class="sop-box02-part1 clearfix">
										<img src="<%=userAvtarUrl%>" width="120" height="120">
									</div>
									<div class="sop-box02-part2 clearfix">
										<c:choose>
											<c:when test="${workflow.solution eq null}">
												<div class="clearfix">
													<a href="#"><%=activityUser.getFirstName()%></a>
													<liferay-ui:message key="submitted-an-idea" />
													<a href="<%=customPortletURL%>">${workflow.idea.ideaTitle}</a>
												</div>
											</c:when>
											<c:otherwise>
												<div class="clearfix">
													<a href="#"><%=activityUser.getFirstName()%></a>
													<liferay-ui:message key="submitted-a-solution" />
													<a href="<%=customPortletURL%>">${workflow.solution.solutionTitle}</a>
												</div>
											</c:otherwise>
										</c:choose>
										<div class="clearfix">
											<liferay-ui:message key="action-requested" />${workflow.actionRequirements}
										</div>
										<c:if test="${workflow.status eq Draft}">
											<div class="clearfix">
												<c:if test="${isCoachUser}">
													<a class="btn btn-primary" href="javascript:void(0);" id="<portlet:namespace/>acceptBtn"
														style="text-decoration: none;" onclick="javascript:<portlet:namespace/>acceptTask('${workflow.taskId}');">
														<liferay-ui:message key="accept" />
													</a>
												</c:if>
											</div>
										</c:if>
										<c:if test="${workflow.status eq InProgress}">
											<div class="clearfix">
												<liferay-ui:message key="task-accepted-by" /><%=acceptedUser.getFirstName()%>
											</div>
											<div class="clearfix">
												<liferay-ui:message key="due-date" />${acceptedDate}</div>
										</c:if>
										<div class="clearfix" style="margin-top: 8px;">
											<div class="pull-left">Status:</div>
											<div class="pull-left clearfix">

												<%-- 	
												<span class="sop-grayB" title="<liferay-ui:message key="defined"/>">D</span> 
								              	<span class="sop-greenB" title="<liferay-ui:message key="in-progress"/>">P</span>
								              	<span class="sop-whiteB" title="<liferay-ui:message key="blocked"/>">B</span> 									              	<span class="sop-whiteB" title="<liferay-ui:message key="completed"/>">C</span>--%>

												<c:if test="${workflow.status eq Draft}">
													<div class="div-flow">
														<span class="sop-grayB"
															title="<liferay-ui:message key="Requested"/>">R</span> <span
															class="sop-greenB"
															title="<liferay-ui:message key="Accepted"/>">A</span> <span
															class="sop-whiteB"
															title="<liferay-ui:message key="Complted"/>">C</span> <span
															class="sop-whiteB"
															title="<liferay-ui:message key="Endorsed"/>">E</span>
													</div>
													<div class="hexagon flow">
														<span title="<liferay-ui:message key="Blocked"/>">B</span>
													</div>
												</c:if>

												<c:if test="${workflow.status eq Accepted}">
													<div class="div-flow">
														<span class="sop-grayB" title="<liferay-ui:message key="Requested"/>">R</span> 
														<span class="sop-greenB" title="<liferay-ui:message key="Accepted"/>">A</span> 
														<span class="sop-whiteB" title="<liferay-ui:message key="Complted"/>">C</span> 
														<span class="sop-whiteB" title="<liferay-ui:message key="Endorsed"/>">E</span>
													</div>
													<div class="hexagon flow">
														<span title="<liferay-ui:message key="Blocked"/>">B</span>
													</div>
												</c:if>

												<c:if test="${workflow.status eq Completed}">
													<div class="div-flow">
														<span class="sop-grayB" title="<liferay-ui:message key="Requested"/>">R</span> 
														<span class="sop-grayB" title="<liferay-ui:message key="Accepted"/>">A</span> 
														<span class="sop-greenB" title="<liferay-ui:message key="Complted"/>">C</span> 
														<span class="sop-whiteB" title="<liferay-ui:message key="Endorsed"/>">E</span>
													</div>
													<div class="hexagon flow">
														<span title="<liferay-ui:message key="Blocked"/>">B</span>
													</div>
												</c:if>

												<c:if test="${workflow.status eq Endorsed}">
													<div class="div-flow">
														<span class="sop-grayB" title="<liferay-ui:message key="Requested"/>">R</span> 
														<span class="sop-grayB" title="<liferay-ui:message key="Accepted"/>">A</span> 
														<span class="sop-grayB"	title="<liferay-ui:message key="Complted"/>">C</span> 
														<span class="sop-greenB" title="<liferay-ui:message key="Endorsed"/>">E</span>
													</div>
													<div class="hexagon flow">
														<span title="<liferay-ui:message key="Blocked"/>">B</span>
													</div>
												</c:if>

												<c:if test="${workflow.status eq Blocked}">
													<div class="div-flow">
														<span class="sop-grayB" title="<liferay-ui:message key="Requested"/>">R</span> 
														<span class="sop-grayB" title="<liferay-ui:message key="Accepted"/>">A</span> 
														<span class="sop-grayB" title="<liferay-ui:message key="Complted"/>">C</span> 
														<span class="sop-grayB" title="<liferay-ui:message key="Endorsed"/>">E</span>
													</div>
													<div>
														<span class="hexagon flow-hexa"
															title="<liferay-ui:message key="Blocked"/>">B</span>
													</div>
												</c:if>

											</div>
										</div>
									</div>
									<div style="clear: both;"></div>
								</div>
							</div>
							<c:if test="<%=themeDisplay.isSignedIn()%>">
								<div class="sop-gray-block clearfix">
									<a id="<portlet:namespace />workflowLike_${taskId}"
										href="javascript:void(0);" class="sop-gray-link"
										onclick="javascript:<portlet:namespace />postLike('${taskId}');">
										<c:choose>
											<c:when test="${workflowLike}">
												<i id="<portlet:namespace />likeIcon_${taskId}"
													class="cs-like-icon active"></i>
											</c:when>
											<c:otherwise>
												<i id="<portlet:namespace />likeIcon_${taskId}"
													class="cs-like-icon"></i>
											</c:otherwise>
										</c:choose> <span id="<portlet:namespace />noOfLikes_${taskId}">${likeCount}</span>
									</a> <a id='<portlet:namespace/>workflowComment'
										href="javascript:void(0);"
										onclick="javascript:<portlet:namespace />openCommentDiv(${taskId});"
										class="sop-gray-link"> <i class="cs-comment-icon"></i> <span
										id='<portlet:namespace/>noOfComments_${taskId}'>${commentCount}</span>
									</a>

								</div>
							</c:if>
							<div class="sop-comment-mainbox clearfix">
								<div class="clearfix buffer-bottom"
									id="<portlet:namespace/>commentDiv_${taskId}"
									style="display: none;">
									<div class="sop-comment-part1 clearfix">
										<img src="${currentUserAvtar}">
									</div>
									<div class="sop-comment-part2 clearfix"
										id="<portlet:namespace/>cmtDiv_${taskId}">
										<div class="row">
											<div class="col-sm-10 col-xs-12 buffer-bottom">
												<textarea
													id="<portlet:namespace/>workflowCommentTxt_${taskId}"
													placeholder="Place your comment here..." rows="2"
													class="form-control" style="width: 100%;"></textarea>
											</div>
											<div class="col-sm-2 col-xs-12">
												<a href="javascript:void(0);"
													class="btn btn-primary collapsed"
													onclick="javascript:<portlet:namespace />postComment(${taskId});">
													<liferay-ui:message key="comment" />
												</a>
											</div>
										</div>
									</div>
								</div>
								<div id='<portlet:namespace/>commentActivity_${taskId}'>

									<c:forEach items="${commentList}" var="comment">
										<div class="clearfix buffer-bottom">
											<c:set value="${comment.userId}" var="commentUser" />
											<c:set value="${comment.createdDate}" var="createdDate" />
											<c:set value="${comment.modifiedDate}" var="modifiedDate" />
											<%
												User commentUser = UserLocalServiceUtil
																		.getUser((Long) pageContext
																				.getAttribute("commentUser"));
																String commentUserAvtar = commentUser
																		.getPortraitId() > 0 ? IdeaUtil
																		.getUserImagePath(commentUser
																				.getPortraitId()) : IdeaUtil
																		.getUserImagePathScreenName(
																				commentUser.getScreenName(),
																				commentUser.getCompanyId());

																String commentTime = commentService
																		.calculateEpocTime((Date) pageContext
																				.getAttribute("createdDate"));

																String commentUserName = commentUser.getFullName();

																pageContext
																		.setAttribute("commentTime", commentTime);
																pageContext.setAttribute("commentUserName",
																		commentUserName);
																pageContext.setAttribute("commentUserAvtar",
																		commentUserAvtar);
											%>
											<div class="sop-comment-part1 clearfix">
												<img src="${commentUserAvtar}">
											</div>
											<div class="sop-comment-part2 clearfix">
												<div class="sop-comment-title1">
													<span>${commentUserName}</span><span>${commentTime}</span>
												</div>
												<div class="sop-comment-text01">${comment.description}</div>
											</div>
										</div>
									</c:forEach>

								</div>
							</div>
						</div>
					</div>

					<div class="sop-main-line"></div>
				</div>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div style="text-align: center;">
			<liferay-ui:message key="no-activity-available" />
		</div>
	</c:otherwise>
</c:choose>

