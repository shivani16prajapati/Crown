 <%@page import="javax.swing.text.StyledEditorKit.BoldAction"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.unicef.domain.SolutionLike"%>
<%@page import="com.unicef.service.SolutionLikeService"%>
<%@page import="com.unicef.domain.SolutionAnswerVote"%>
<%@page import="com.unicef.domain.SolutionAnswer"%>
<%@page import="com.unicef.service.SolutionAnswerVoteService"%>
<%@page import="com.unicef.domain.Like"%>
<%@page import="com.unicef.service.LikeService"%>
<%@page import="com.unicef.domain.IdeaEndorsement"%>
<%@page import="com.unicef.service.IdeaEndorsementService"%>
<%@page import="com.unicef.domain.IdeaComment"%>
<%@page import="com.unicef.service.IdeaCommentService"%>
<%@page import="com.unicef.domain.Solution"%>
<%@page import="com.unicef.service.SolutionService"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.unicef.service.IdeaService"%>
<%@page import="com.unicef.domain.Idea"%>
<%@include file="../init.jsp"%>

<%long ideaPortlet = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "Idea_WAR_Unicefportlet"); %>

<%long solutionPortlet = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "SolutionGenerator_WAR_Unicefportlet"); %>



<c:if test="${empty feedlist}">
<tr value="1">
   <td>No Feed Available </td>
   <td></td>
</tr>
</c:if>

<c:forEach items="${feedlist}" var="feed">
<fmt:formatDate pattern="dd/MM/yyyy" value="${feed.createdDate}" var="today" />
<fmt:formatDate pattern="dd MMM yyyy HH:mm:ss" value="${feed.createdDate}" var="notiday" />

      <c:set var="referId" value="${feed.referId}"></c:set>
      
      <c:set var="byuserId" value="${feed.byuserId}"></c:set>
      <c:set var="userId" value="${feed.userId}"></c:set>
      <%
      User byuser = null;
      try{
    	  byuser = UserLocalServiceUtil.getUser((Long)pageContext.getAttribute("byuserId"));
        }catch(Exception e){}
      %>
      <c:choose>
        <c:when test="${feed.feedtypeId eq 1}">
           <% 
           IdeaService ideaService = (IdeaService)request.getAttribute("ideaService");
           Idea idea = ideaService.find((Long)pageContext.getAttribute("referId"));
           boolean ismanger = false;
           List<Role> userroles = RoleLocalServiceUtil.getUserRoles(byuser.getUserId());
           for (Role _role :userroles){
   			System.out.println(_role.getName());
   			if(_role.getName().equalsIgnoreCase("sales/marketing senior manager") || _role.getName().equalsIgnoreCase("engineering senior manager")){
   				ismanger = true;
   			}
   		   }
           if(ismanger){
           %>
           <tr value="0.9">
           <%}else{%>
           <tr value="0.5">
           <% }%>
            
           	<liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=ideaPortlet %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewIdea" />
	    	   <portlet:param name="ideaId" value="${referId}" /> 	   
	    	</liferay-portlet:renderURL>
           <td>New Idea Submitted <a href="<%=ideaPortletURL.toString()%>"> <%=idea.getIdeaTitle()%> </a> By 
               <%if(themeDisplay.getUserId() == byuser.getUserId()){%>
            		You
               <%}else{ %>	
            		<%=byuser.getFullName()%> 
            	<%}%>
           </td>
           <td><time datetime="${notiday}" class="age">${today}</time></td>
           </tr>
        </c:when>
        
        <c:when test="${feed.feedtypeId eq 2}">
          
           <liferay-portlet:renderURL portletName="SolutionGenerator_WAR_Unicefportlet"  var="solutionPortletURL" plid="<%=solutionPortlet %>" varImpl="solutionPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewSolution" />
	    	   <portlet:param name="solutionId" value="${referId}" /> 	   
	    	</liferay-portlet:renderURL>
           
           <% 
           SolutionService solutionService = (SolutionService)request.getAttribute("solutionService");
           Solution solution = solutionService.find((Long)pageContext.getAttribute("referId"));
           List<Role> userroles = RoleLocalServiceUtil.getUserRoles(byuser.getUserId());
           boolean ismanger = false;
           for (Role _role :userroles){
   			System.out.println(_role.getName());
   			if(_role.getName().equalsIgnoreCase("sales/marketing senior manager") || _role.getName().equalsIgnoreCase("engineering senior manager")){
   				ismanger = true;
   			}
   		   }
           if(ismanger){
           %>
           <tr value="0.9">
           <%}else{%>
           <tr value="0.5">
           <% }%>
            
	    	
        	<td>New solution request Submitted <a href="<%=solutionPortletURL.toString()%>"> <%=solution.getSolutionTitle()%> </a> By 
        	     <%if(themeDisplay.getUserId() == byuser.getUserId()){%>
            		You
                 <%}else{ %>	
            		<%=byuser.getFullName()%> 
            	 <%}%>
            	</td>
            	<td><time datetime="${notiday}" class="age">${today}</time></td>
            	</tr>
            	
        </c:when>
        <c:when test="${feed.feedtypeId eq 3}">
        <tr value="0.4">
           <% 
           IdeaService ideaService = (IdeaService)request.getAttribute("ideaService");
           IdeaCommentService ideaCommentService = (IdeaCommentService)request.getAttribute("ideaCommentService");
           IdeaComment comment = ideaCommentService.find((Long)pageContext.getAttribute("referId"));
           Idea idea = ideaService.find(comment.getIdea().getIdeaId());
           pageContext.setAttribute("idea", idea);
           %>
           <liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=ideaPortlet %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewIdea" />
	    	   <portlet:param name="ideaId" value="${idea.ideaId}" /> 	   
	    	</liferay-portlet:renderURL>
            <td>Your Idea  <a href="<%=ideaPortletURL.toString()%>"> <%=idea.getIdeaTitle()%> </a> Got Comment By 
               <%if(themeDisplay.getUserId() == byuser.getUserId()){%>
            		You
               <%}else{ %>	
            		<%=byuser.getFullName()%> 
            	<%}%>
           </td>
           <td><time datetime="${notiday}" class="age">${today}</time></td>
           </tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 4}">
        <tr value="0.4">
           <% 
          		 SolutionService solutionService = (SolutionService)request.getAttribute("solutionService");
           		Solution solution = solutionService.find((Long)pageContext.getAttribute("referId"));
           %>
             <liferay-portlet:renderURL portletName="Solution_Generator_WAR_Unicefportlet"  var="solutionPortletURL" plid="<%=solutionPortlet %>" varImpl="solutionPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewSolution" />
	    	   <portlet:param name="solutionId" value="${referId}" /> 	   
	    	</liferay-portlet:renderURL>
	    	
        	    <td>Your Problem <a href="<%=solutionPortletURL.toString()%>"> <%=solution.getSolutionTitle()%> </a>Got Comment By 
        	     <%if(themeDisplay.getUserId() == byuser.getUserId()){%>
            		You
               <%}else{ %>	
            		<%=byuser.getFullName()%> 
            	<%}%>
            	</td>
            	<td><time datetime="${notiday}" class="age">${today}</time></td>
        </tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 5}">
        
        <tr value="0.5">
            <% 
          		SolutionService solutionService = (SolutionService)request.getAttribute("solutionService");
           		Solution solution = solutionService.find((Long)pageContext.getAttribute("referId"));
            %>
             <liferay-portlet:renderURL portletName="Solution_Generator_WAR_Unicefportlet"  var="solutionPortletURL" plid="<%=solutionPortlet %>" varImpl="solutionPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewSolution" />
	    	   <portlet:param name="solutionId" value="${referId}" /> 	   
	    	</liferay-portlet:renderURL>
	    	
        	    <td>Your solution request <a href="<%=solutionPortletURL.toString()%>"> <%=solution.getSolutionTitle()%> </a>Got Answer By 
        	     <%if(themeDisplay.getUserId() == byuser.getUserId()){%>
            		You
               <%}else{ %>	
            		<%=byuser.getFullName()%> 
            	<%}%>
            	</td>
            	<td><time datetime="${notiday}" class="age">${today}</time></td>
            	</tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 6}">
        <tr value="0.4">
          <% 
            IdeaService ideaService = (IdeaService)request.getAttribute("ideaService");
            IdeaCommentService ideaCommentService = (IdeaCommentService)request.getAttribute("ideaCommentService");
            IdeaComment comment = ideaCommentService.find((Long)pageContext.getAttribute("referId"));
            Idea idea = ideaService.find(comment.getIdea().getIdeaId());
            pageContext.setAttribute("idea", idea);
           %>
           <liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=ideaPortlet %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewIdea" />
	    	   <portlet:param name="ideaId" value="${idea.ideaId}" /> 	   
	    	</liferay-portlet:renderURL>
            <td>You just got a thank you for your suggestion to an idea <a href="<%=ideaPortletURL.toString()%>"> <%=idea.getIdeaTitle()%> </a>
            <td><time datetime="${notiday}" class="age">${today}</time></td>
        </tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 7}">
          <tr value="1">
          <% 
            IdeaService ideaService = (IdeaService)request.getAttribute("ideaService");
          	IdeaEndorsementService ideaEndorsementService = (IdeaEndorsementService)request.getAttribute("ideaEndorsementService");
          	IdeaEndorsement ideaEndorsement = ideaEndorsementService.find((Long)pageContext.getAttribute("referId"));
            Idea idea = ideaService.find(ideaEndorsement.getIdea().getIdeaId());
            pageContext.setAttribute("idea", idea);
           %>
           <liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=ideaPortlet %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewIdea" />
	    	   <portlet:param name="ideaId" value="${idea.ideaId}" /> 	   
	    	</liferay-portlet:renderURL>
            <td>You just got endorsement for an idea <a href="<%=ideaPortletURL.toString()%>"> <%=idea.getIdeaTitle()%> </a>
            <td><time datetime="${notiday}" class="age">${today}</time></td>
        	</tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 8}">
        <tr value="0.4">
          <% 
            IdeaService ideaService = (IdeaService)request.getAttribute("ideaService");
            LikeService likeService = (LikeService)request.getAttribute("likeService");
          	Like like = likeService.find((Long)pageContext.getAttribute("referId"));
            Idea idea = ideaService.find(like.getIdea().getIdeaId());
            pageContext.setAttribute("idea", idea);
           %>
           <liferay-portlet:renderURL portletName="Idea_WAR_Unicefportlet"  var="ideaPortletURL" plid="<%=ideaPortlet %>" varImpl="ideaPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewIdea" />
	    	   <portlet:param name="ideaId" value="${idea.ideaId}" /> 	   
	    	</liferay-portlet:renderURL>
            <td>You just got upvote for an idea <a href="<%=ideaPortletURL.toString()%>"> <%=idea.getIdeaTitle()%> </a>
            <td><time datetime="${notiday}" class="age">${today}</time></td>
     	</tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 9}">
        
        <tr value="0.4">
            <% 
          		SolutionService solutionService = (SolutionService)request.getAttribute("solutionService");
           		Solution solution = solutionService.find((Long)pageContext.getAttribute("referId"));
            %>
             <liferay-portlet:renderURL portletName="Solution_Generator_WAR_Unicefportlet"  var="solutionPortletURL" plid="<%=solutionPortlet %>" varImpl="solutionPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewSolution" />
	    	   <portlet:param name="solutionId" value="${referId}" /> 	   
	    	</liferay-portlet:renderURL>
	    	
        	    <td>Your answer to solution request <a href="<%=solutionPortletURL.toString()%>"> <%=solution.getSolutionTitle()%> </a>Got Comment By 
        	     <%if(themeDisplay.getUserId() == byuser.getUserId()){%>
            		You
                 <%}else{ %>	
            		<%=byuser.getFullName()%> 
            	 <%}%>
            	</td>
            	<td><time datetime="${notiday}" class="age">${today}</time></td>
            	</tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 10}">
          <tr value="0.4">
            <% 
          		SolutionService solutionService = (SolutionService)request.getAttribute("solutionService");
            	SolutionAnswerVoteService solutionAnswerVoteService = (SolutionAnswerVoteService)request.getAttribute("solutionAnswerVoteService");
           		SolutionAnswerVote solutionAnswervote = solutionAnswerVoteService.find((Long)pageContext.getAttribute("referId"));
           		Solution solution = solutionService.find(solutionAnswervote.getSolutionId());
           		pageContext.setAttribute("solution", solution);
            %>
             <liferay-portlet:renderURL portletName="Solution_Generator_WAR_Unicefportlet"  var="solutionPortletURL" plid="<%=solutionPortlet %>" varImpl="solutionPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewSolution" />
	    	   <portlet:param name="solutionId" value="${solution.getSolutionId()}" /> 	   
	    	</liferay-portlet:renderURL>
	    	
        	    <td>Your answer to solution request <a href="<%=solutionPortletURL.toString()%>"> <%=solution.getSolutionTitle()%> </a>Got Upvote By 
        	     <%if(themeDisplay.getUserId() == byuser.getUserId()){%>
            		You
                 <%}else{ %>	
            		<%=byuser.getFullName()%> 
            	 <%}%>
            	</td>
            	<td><time datetime="${notiday}" class="age">${today}</time></td>
            	</tr>
        </c:when>
        
         <c:when test="${feed.feedtypeId eq 11}">
         <tr value="0.4">
            <% 
          		SolutionService solutionService = (SolutionService)request.getAttribute("solutionService");
            	SolutionLikeService solutionLikeService = (SolutionLikeService)request.getAttribute("solutionLikeService");
           		SolutionLike solutionLike = solutionLikeService.find((Long)pageContext.getAttribute("referId"));
           		Solution solution = solutionService.find(solutionLike.getSolution().getSolutionId());
           		pageContext.setAttribute("solution", solution);
           		
      		
            %>
             <liferay-portlet:renderURL portletName="Solution_Generator_WAR_Unicefportlet"  var="solutionPortletURL" plid="<%=solutionPortlet %>" varImpl="solutionPortletURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	    	   <portlet:param name="view" value="viewSolution" />
	    	   <portlet:param name="solutionId" value="${solution.getSolutionId()}" /> 	   
	    	</liferay-portlet:renderURL>
	    	
        	    <td>Your answer to solution request <a href="<%=solutionPortletURL.toString()%>"> <%=solution.getSolutionTitle()%> </a>Got Upvote By 
        	     <%if(themeDisplay.getUserId() == byuser.getUserId()){%>
            		You
                 <%}else{ %>	
            		<%=byuser.getFullName()%> 
            	 <%}%>
            	</td>
            	<td><time datetime="${notiday}" class="age">${today}</time></td>
            	</tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 12}">
         <tr value="0.8">
             <td>Your idea just made it into the top ten for upvotes or hotness  <a href="/group/guest/my-innovation#most-hotidea"> click Here</a>
            	</td>
            	<td><time datetime="${notiday}" class="age">${today}</time></td>
         
         </tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 13}">
         <tr value="0.9">
             <td>You are now in the top five thank you getters  <a href="/group/guest/my-innovation#most-thank"> click Here</a>
            	</td>
              <td><time datetime="${notiday}" class="age">${today}</time></td>
         </tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 14}">
         <tr value="0.6">
             <td> ${referId} New idea listed on hot idea list <a href="/group/guest/my-innovation#most-hotidea"> click Here</a>
            	</td>
              <td><time datetime="${notiday}" class="age">${today}</time></td>
         </tr>
        </c:when>
        <c:when test="${feed.feedtypeId eq 15}">
         <tr value="0.7">
             <td>${referId} New user get listed in most thank you getter list <a href="/group/guest/my-innovation#most-thank"> click Here</a>
            	</td>
              <td><time datetime="${notiday}" class="age">${today}</time></td>
         </tr>
        </c:when>
	</c:choose>
</c:forEach>
<tr value="0">
<td></td>
   <td>
   		<c:if test="${nextnotification eq 1}">
   			<span style="float:right"><a href="javascript:void(0)" onclick="getnewsfeed('${nextstart}','${nextend}')">NEXT >></a></span>
   		</c:if>
   		<c:if test="${prevnotification eq 1}">
   			<span style="float:right"><a href="javascript:void(0)" onclick="getnewsfeed('${start}','${end}')"> << PREV </a></span>
   		</c:if>
   </td>
</tr>



 <c:if test="${isPriority eq 1}">
	 <script>
	 $(document).ready(function(){
		 rearrange()
	 })
	 </script>
 </c:if>