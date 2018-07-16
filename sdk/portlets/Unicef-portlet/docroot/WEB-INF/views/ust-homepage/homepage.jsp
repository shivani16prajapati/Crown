<%@include file="../init.jsp"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/css/CustomeMIstyle.css">
<liferay-theme:defineObjects />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/css/CustomeMIstyle.css">
<style>
.videoWn {
    position: absolute;
    bottom: 0;
    background: #c00000;
    height: 30px;
    width: 30px;
    text-align: center;
}
</style>
<div class="np-portlate-fix01" id="homePageDiv">
 <div class="clearfix">
 <input value="${ideaListSize}" name="ideaListSizeClass" class="ideaListSizeClass" type="hidden"/>
 <c:if test="${fn:length(ustHomePageIdeaList) gt 1}">
  <c:if test="${ideaCount eq 1 }">
   <c:forEach items="${ustHomePageIdeaList}" var="idea"  varStatus="ideaCounter">
    
    <%
    long structureId = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "Idea_WAR_Unicefportlet");
    %>
    
    <liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=structureId %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
    <portlet:param name="view" value="viewIdea" />
    <portlet:param name="ideaId" value="${idea.ideaId}" />
    </liferay-portlet:renderURL>
   
    <c:if test="${ideaCounter.count == 1}">  
         <div class="ust-leftbox01 clearfix">
           <a href="javascript:void(0);" onclick="javascript:<portlet:namespace />viewIdeaLink('<%=ideaPortletURL.toString()%>')">
            <div class="ust-idea-box1 np-imgR-fix">
             
             <c:if test="${ideahottest eq 1}"> 
             	<div class="hotidea-cover" style=""></div>
             </c:if>
             
             <c:if test="${idealatest eq 1}"> 
             	<div class="newidea-cover" style=""></div>
             </c:if>
            
            <img src="${idea.ideaImage}" class="image">
            </div>
            <div class="ust-idea-box2">
              <div class="clearfix">
                <div class="ust-idea-imgbox01 np-imgR-fix">
                 <img src="${idea.userImage}" class="image">
                </div>
                <!-- <div class="ust-idea-imgbox-text01">1</div> -->
              </div>
              <div class="ust-text-part clearfix">
                <div class="ust-title01">${idea.ideaTitle}</div>
                <div class="ust-text01">${idea.tagLine}</div>
              </div>
              <div class="clearfix">
<%--                 <div class="ust-iconpart01"> <img src="<%= request.getContextPath()%>/images/ust-icon-1.png"></div>
                <div class="ust-iconpart02"><img src="<%= request.getContextPath()%>/images/ust-icon-2.png"> </div> --%>
                
                <div class="wall-ico-01">
                	<span>${idea.ideaComment}</span>
                	<span> <img src="<%= request.getContextPath()%>/images/newwall-icon02.png"></span>
                </div>
                
                <div class="wall-ico-01">
                	<span>${idea.ideaUpvote}</span>
                	<span> <img src="<%= request.getContextPath()%>/images/newwall-icon01.png"></span>
                </div>
                
                
                
              </div>
              
            </div>
        </a>
         </div>  
    </c:if>
    
   </c:forEach> 
   <div class="ust-rightbox01 clearfix">
    <c:forEach items="${ustHomePageIdeaList}" var="idea"  varStatus="ideaCounter">
     <%
     long structureId = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "Idea_WAR_Unicefportlet");
     %> 
      <c:choose>
       <c:when test="${ideaCounter.count gt 1 && ideaCounter.count lt 10}">
	      <c:if test="${!idea.isvideo}">
	      <liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=structureId %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	       <portlet:param name="view" value="viewIdea" />
	       <portlet:param name="ideaId" value="${idea.ideaId}" />
	     </liferay-portlet:renderURL>
     
        	<div class="ust-small-imgbox01 np-imgR-fix"> 
         		<a href="javascript:void(0);" onclick="javascript:<portlet:namespace />viewIdeaLink('<%=ideaPortletURL.toString()%>')"> 
           			<img src="${idea.ideaImage}" class="image">
         			<div class="ust-imgbox-cover">${idea.tagLine}</div>
        		</a> 
       		</div>
       		</c:if>
       		<c:if test="${idea.isvideo}">
       		<div class="ust-small-imgbox01 np-imgR-fix"> 
         		<a href="javascript:void(0);" onclick="javascript:openvideo('${idea.tagLine}')"> 
           			<img src="${idea.ideaImage}" class="image">
         			<div class="ust-imgbox-cover">${idea.ideaTitle}</div>
        		</a> 
        		<div class="videoWn" >
						<img src="<%= request.getContextPath()%>/images/video.png" />	
				</div>
       		</div>
       		</c:if>
      </c:when>
     </c:choose>
    </c:forEach>
 
    
   <c:if test="${placeHolderCount > 0}">
    <c:forEach begin="1" end="${placeHolderCount}">
    <!-- place holder image -->
     <div class="ust-small-imgbox01 np-imgR-fix">
      <img src="<%= request.getContextPath()%>/images/coming-soon-wall.png">
     </div>
    </c:forEach>
   </c:if>
    
   </div> 
  </c:if>
 </c:if>
   
   <%-- <c:if test="${ideaCounter.count == 1}">   
   <c:if test="${fn:length(ustHomePageIdeaList) eq 0}">
     <div class="ust-leftbox01 clearfix">
      <div class="ust-idea-box1 np-imgR-fix">
         <img src="<%= request.getContextPath()%>/images/coming-soon-wall.png">
       </div>
      <div class="ust-idea-box2">
      <img src="<%= request.getContextPath()%>/images/coming-soon-wall.png">
     </div>
    </div>
    
   </c:if>
   </c:if>
    
  <c:if test="${ideaCounter.count == 1}">  
   <c:if test="${fn:length(ustHomePageIdeaList) eq 0}">
    <div class="ust-rightbox01 clearfix">
     <c:forEach begin="2" end="${placeHolderCount}">
       <div class="ust-small-imgbox01 np-imgR-fix">
        <img src="<%= request.getContextPath()%>/images/coming-soon-wall.png">
       </div>
     </c:forEach>
    </div>
   </c:if>
   </c:if> --%>
  
  
 </div>
 
 <div class="ust-second-row clearfix">
 <c:if test="${ideaCount eq 2 }">
  
  <div class="clearfix">
   <c:forEach items="${ustHomePageIdeaList}" var="idea"  varStatus="ideaCounter">
   <%
    long structureId = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "Idea_WAR_Unicefportlet");
   %>
   <liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=structureId %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
    <portlet:param name="view" value="viewIdea" />
    <portlet:param name="ideaId" value="${idea.ideaId}" />
   </liferay-portlet:renderURL>
   
    <c:choose>
     <c:when test="${ideaCounter.count >= 1 && ideaCounter.count <= 8}">
      <div class="ust-small-imgbox01-v2 np-imgR-fix"> <a href="javascript:void(0);" onclick="javascript:<portlet:namespace />viewIdeaLink('<%=ideaPortletURL.toString()%>')"> <img src="${idea.ideaImage}" class="image">
       <div class="ust-imgbox-cover">${idea.tagLine}</div>
       </a> </div>
     </c:when>
    </c:choose>
   </c:forEach>
   
 <c:if test="${placeHolderCount > 0}">  
   <c:if test="${placeHolderCount < 8}">
   	    <c:forEach begin="1" end="${placeHolderCount}">
	     	<div class="ust-small-imgbox01-v2 np-imgR-fix">
	      		<img src="<%= request.getContextPath()%>/images/coming-soon-wall.png">
	     	</div>
	    </c:forEach>
	 </c:if>
 </c:if>
   
  </div> 
  </c:if>
 </div>
 
 <div class="np-small-imgbox-fix01 clearfix">
 <c:if test="${ideaCount gt 2 }">
  <c:forEach items="${ustHomePageIdeaList}" var="idea"  varStatus="ideaCounter">
 
   <%
    long structureId = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "Idea_WAR_Unicefportlet");
   %>
 
   <liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=structureId %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
    <portlet:param name="view" value="viewIdea" />
    <portlet:param name="ideaId" value="${idea.ideaId}" />
   </liferay-portlet:renderURL>
   
 
     <c:if test="${ideaCounter.count >= 1 && ideaCounter.count <= 8}">
      <div class="ust-small-imgbox01 np-imgR-fix"> <a href="javascript:void(0);" onclick="javascript:<portlet:namespace />viewIdeaLink('<%=ideaPortletURL.toString()%>')"> <img src="${idea.ideaImage}" class="image">
       <div class="ust-imgbox-cover">${idea.tagLine}</div>
       </a> </div>
     </c:if>
   </c:forEach>
  
 <c:if test="${placeHolderCount > 0}">  
   <c:if test="${placeHolderCount < 8}">
        <c:forEach begin="1" end="${placeHolderCount}">
	    <!-- place holder image -->
	     <div class="ust-small-imgbox01-v2 np-imgR-fix">
	      <img src="<%= request.getContextPath()%>/images/coming-soon-wall.png">
	     </div>
	    </c:forEach>	
	</c:if>
</c:if>
  </c:if> 
 </div>
 
</div>

<script type="text/javascript">
<portlet:namespace />viewIdeaLink = function(url){
   window.location.href = url;
}

</script>