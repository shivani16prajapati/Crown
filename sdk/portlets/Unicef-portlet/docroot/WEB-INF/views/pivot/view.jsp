<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.unicef.util.CommonUtil"%>
<%@include file="../init.jsp"%>
   <div class="ust-gr-title01 clearfix">
    <div class="ust-gr-t01-box1">MyInnovation Dashboard</div>
    <div class="ust-gr-t01-box2"><a href="#">Options</a></div>
  </div>
  
<div class="ust-g-container clearfix">
<div class="clearfix">
	  <div class="ust-g-box01">
		  	  <div class="ust-grap-clickbox">
		  		 <a href="#ideaByMonth1" class="fancybox"><img src="<%=request.getContextPath()%>/images/dummy-button.png"></a>
		  	  </div>
			  <div class="ust-graph" id="ideaByMonth"></div>
			  <div style="display: none;" id="ideaByMonth1"></div>
	  </div>
	  <div class="ust-g-box01" id="box2">
	  		<div class="ust-grap-clickbox-02 clearfix">
		  			<div class="fl" style="margin:10px 10px 0 0;">
		  				Ideas By :	
		  			</div>
					<div class="fl" style="margin-right:10px">
				 	 	<aui:input inlineLabel="right" checked='true'  name="ideaCategory"  type="radio"  value="innovationType" label='Innovation Type' onClick="ideaChartByType('innovation')" />
				 	</div>
				  	<div class="fl" style="margin-right:10px">
				  		<aui:input inlineLabel="right"  name="ideaCategory"  type="radio"  value="vertical" label='Vertical' onClick="ideaChartByType('vertical')"  />
				  	</div>
				  	<div class="fl" style="margin-right:10px">
				  		<aui:input inlineLabel="right"  name="ideaCategory"  type="radio"  value="edgeService" label='Edge Service' onClick="ideaChartByType('edgeService')" />
			  		</div>
	  		</div>
	 		 <div class="ust-grap-clickbox">
		  		<a href="#ideaByType1" class="fancybox"><img src="<%=request.getContextPath()%>/images/dummy-button.png"></a>
		  	 </div>
	  		 <div class="ust-graph" id="ideaByType" style="margin-bottom:15px;"></div>
	  	     <div style="display: none;" id="ideaByType1"></div> 
	  </div>
	</div>
	  <div class="ust-g-box02" id="box3">
	  		<div class="ust-graph">
	  			<a class="fancybox" href="<%=request.getContextPath()%>/images/ust-g-3.png">
	  				<img src="<%=request.getContextPath()%>/images/ust-g-3.png">
	  			</a>
	  		</div>
	  </div>
	  <div class="ust-g-box02" id="box4">
	 	 <div class="ust-grap-clickbox">
	  		<a href="#socialInnovation1" class="fancybox">
	  			<img src="<%=request.getContextPath()%>/images/dummy-button.png">
	  		</a>
	  	 </div>
	  	 <div class="ust-graph" id="socialInnovation"></div>
	  	 <div style="display: none;" id="socialInnovation1"></div>
	  </div>
	  <div class="ust-g-box02" id="box5">
	  	<div class="ust-graph">
	  		<a class="fancybox" href="<%=request.getContextPath()%>/images/ust-g-5.png">
	  			<img src="<%=request.getContextPath()%>/images/ust-g-5.png">
	  		</a>
	  	</div>
	  </div>
	  <div class="ust-g-box02" id="box6">
	  		<div class="ust-graph">	
	  			<div><img src="<%=request.getContextPath()%>/images/lb-title.png"></div>
	  			<div class="ust-lb-container">
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
							  			long structureId = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "Idea_WAR_Unicefportlet");
									%>
										<liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=structureId %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
											<portlet:param name="view" value="viewIdea" />
											<portlet:param name="ideaId" value="${ideaId}" />
										</liferay-portlet:renderURL>
									<div class="ust-lb-box01">
				 					 	<a href="<%=ideaPortletURL.toString()%>">
				 					 		<span>${idea.ideaTagTitle}</span>
				 					 		<span>${idea.hotWeight}</span>
				 					 	</a>
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
</div>
  
  
<script type="text/javascript">
  $(document).ready(function() {
    $('.fancybox').fancybox();
   });
</script>

<%@ include file="view_js.jsp"%>