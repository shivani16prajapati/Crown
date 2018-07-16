<%@page import="com.unicef.util.CommonUtil"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@include file="../init.jsp"%>

<!-- <div style="width:100%; max-width:500px; margin:10px auto 0 auto;"> -->
<div class="clearfix">
<div id='<portlet:namespace/>hotIdeas'>
<div style="font-size: 18px;color:#fff;font-weight: bold;padding-bottom: 11px;background-color: #4da9fc;border-radius:10px 10px 0 0;line-height:18px;padding-top:11px;padding-left:12px;">Hot Ideas</div>
		<div class="ku-m-box clearfix" >
		<c:choose>
			<c:when test="${not empty hotIdeas}">
				<c:forEach items="${hotIdeas}" var="idea">
					<c:set value="${idea.coInventorId}" var="userId" />
					<c:set value="${idea.ideaId}" var="ideaId" />
					<c:set value="${idea.groupId}" var="groupId" />
					<c:set value="${idea.submissionDate}" var="createdDate" />
						<%
							User ideaCreater = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("userId"));
							String userIcon = (ideaCreater.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(ideaCreater.getPortraitId()) :  IdeaUtil.getUserImagePathScreenName(ideaCreater.getScreenName(), themeDisplay.getCompanyId()));
							pageContext.setAttribute("userName", ideaCreater.getFirstName());
							pageContext.setAttribute("userAvtar", userIcon);
							PortletURL viewIdeaURL = CommonUtil.createIdeaPortletURL(request, themeDisplay.getPlid(),((Long)pageContext.getAttribute("ideaId")) , (Long)pageContext.getAttribute("groupId"));	
						
							Date createdDate = (Date)pageContext.getAttribute("createdDate");
				  			int newTHRESHOLD =  2; /* Integer.parseInt(String.valueOf(pageContext.getAttribute("newTHRESHOLD"))); */
				  			boolean isNew = IdeaUtil.isIdeaNew(newTHRESHOLD, createdDate);
						%>
						<div class="cg-chat-mainbox clearfix">
							<div class="cg-chat-imgbox">
		  						 <span><img src="${userAvtar}" width="119" height="119" /></span> 
		  	 					<span>${userName} </span> 
		  					</div>
		  					<div class="cg-chat-textbox01"> 
		  						<span><a href="#" >${idea.ideaTitle}</a></span>
		  						<span>
			           				<c:choose>
			           					<c:when test="${ (idea.hotWeight >= 0.9)}">
			           						<img alt="HOT Idea" style=" width: 9%;"  src="<%=request.getContextPath()%>/images/hot.png">
			           					</c:when>
			           				</c:choose>
		  						</span> 
						  </div>
					  </div>
				</c:forEach>
			</c:when>	
			<c:otherwise>
					<div><b><liferay-ui:message key="no-hot-ideas"/> </b></div>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
</div>

<style>
.cg-chat-mainbox *{ -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box;}

.cg-chat-mainbox{ display:block; position:relative; border:1px solid #CCC; padding:8px; margin-bottom:14px;}
.cg-chat-imgbox{  width:16%; float:left;  display:block;  font:normal 13px/18px Arial, Helvetica, sans-serif; color:#000;}
.cg-chat-imgbox span{ display:block;}
.cg-chat-imgbox img{ width:100%; height:auto;}
.cg-chat-textbox01{ padding:6px; margin-left:10px; width:80%; float:left;}
.cg-chat-textbox01 span{ display:block;}

.cg-chat-titlebox{ font:bold 16px/18px Arial, Helvetica, sans-serif; color:#1d86cf; margin-bottom:10px;}

.clearfix:before, .clearfix:after { content: ""; display: table; }
.clearfix:after{ clear: both; }
.clearfix{ zoom: 1;}

</style>