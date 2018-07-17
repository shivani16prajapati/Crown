<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.blogs.model.BlogsEntry"%>
<%@page import="com.unicef.domain.Solution"%>
<%@page import="com.unicef.domain.Idea"%>
<%@ include file="../facets/init.jsp" %>
<div class="search-facet search-asset_entries" data-facetFieldName="entryClassName" id="assetEntryfacet">
	<aui:input name="entryClassName" type="hidden" value="" />
	<%
		String keyword = ParamUtil.getString(request, "keywords", StringPool.BLANK);
	    String entryClassName = ParamUtil.getString(request, "entryClassName", StringPool.BLANK);
		PortletURL everyThingActionURL = renderResponse.createActionURL();
		everyThingActionURL.setParameter("action", "filter");
		everyThingActionURL.setParameter("entryClassName", StringPool.BLANK);
		everyThingActionURL.setParameter("keywords", keyword); 
		
		boolean isAdvanceFiterSearch = (Boolean)renderRequest.getAttribute("isAdvanceSearch");
		String sourceFilterField = (String)renderRequest.getAttribute("sourceField");
		String typeFilterField = (String)renderRequest.getAttribute("typeField");
		String stageFilterField = (String)renderRequest.getAttribute("stageField");
		String hotIdeasFilterField = (String)renderRequest.getAttribute("hotIdeasField");
		String fieldFrom = (String)renderRequest.getAttribute("fieldFrom");
		String fieldTo = (String)renderRequest.getAttribute("fieldTo");
		String includeTag = (String)renderRequest.getAttribute("includeTag");
		String excludeTag = (String)renderRequest.getAttribute("excludeTag");
		
		if(isAdvanceFiterSearch){
			everyThingActionURL.setParameter("isAdvanceSearch", String.valueOf(isAdvanceFiterSearch));
			if(isAdvanceFiterSearch){
				everyThingActionURL.setParameter("sourceField", sourceFilterField);
				everyThingActionURL.setParameter("typeField", typeFilterField);
				everyThingActionURL.setParameter("stageField", stageFilterField);
				everyThingActionURL.setParameter("hotIdeasField", hotIdeasFilterField);
				everyThingActionURL.setParameter("fieldFrom", fieldFrom);
				everyThingActionURL.setParameter("fieldTo", fieldTo);
				everyThingActionURL.setParameter("includeTags", includeTag);
				everyThingActionURL.setParameter("excludeTags", excludeTag);
			}
		}
		
		%>
	<ul class="asset-type nav nav-pills nav-stacked">
		<li class="facet-value default active">
			<a data-value="" href="<%=everyThingActionURL.toString()%>"><aui:icon image="search" /> <liferay-ui:message key="everything" /></a>
		</li>
		<c:if test="${not empty docsMap}">
	        <c:forEach items="${docsMap}" var="doc">
	           <c:set var="astType" value="${doc.key}"/>
	            <%
	            String assetType = String.valueOf(pageContext.getAttribute("astType"));
	            PortletURL actionURL = renderResponse.createActionURL();
	            actionURL.setParameter("action", "filter");
	            actionURL.setParameter("entryClassName", assetType);
	            actionURL.setParameter("keywords", keyword); 
	            if(isAdvanceFiterSearch){
	            	actionURL.setParameter("isAdvanceSearch", String.valueOf(isAdvanceFiterSearch));
	    			if(isAdvanceFiterSearch){
	    				actionURL.setParameter("sourceField", sourceFilterField);
	    				actionURL.setParameter("typeField", typeFilterField);
	    				actionURL.setParameter("stageField", stageFilterField);
	    				actionURL.setParameter("hotIdeasField", hotIdeasFilterField);
	    				actionURL.setParameter("fieldFrom", fieldFrom);
	    				actionURL.setParameter("fieldTo", fieldTo);
	    				actionURL.setParameter("includeTags", includeTag);
	    				actionURL.setParameter("excludeTags", excludeTag);
	    			}
	    		}
	            AssetRendererFactory assetRendererFactory = null;
	            if(!assetType.equals(Idea.class.getName()) || !assetType.equals(Solution.class.getName())){
	            	 assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetType);	
	            }
	            %>
	            <c:if test="${doc.value > 0}">
		           <li class="facet-value">
		             <c:choose>
		             <c:when test="<%=assetType.equals(entryClassName) %>">
		                <c:choose>
		                	<c:when test="<%=assetType.equals(Idea.class.getName()) %>">
		                		<a data-value="${doc.key}" href="<%=actionURL.toString()%>" class="seleted-filter">
										 <img alt="" src="<%=request.getContextPath() %>/images/idea.png" class="imgWidth" />  
									   Idea
									<c:if test="<%= true %>">
										<span class="badge badge-info frequency">${doc.value}</span>
									</c:if>
								</a>
		                	</c:when>
		                	<c:when test="<%=assetType.equals(Solution.class.getName()) %>">
		                		<a data-value="${doc.key}" href="<%=actionURL.toString()%>" class="seleted-filter">
										<img alt="" src="<%=request.getContextPath() %>/images/solution.png" class="imgWidth" /> 
									   Solution
									<c:if test="<%= true %>">
										<span class="badge badge-info frequency">${doc.value}</span>
									</c:if>
								</a>
		                	</c:when>
		                	<c:otherwise>
		                		<a data-value="${doc.key}" href="<%=actionURL.toString()%>" class="seleted-filter">
									<c:if test="<%= assetRendererFactory != null %>">
										<img alt="" src="<%= assetRendererFactory.getIconPath(renderRequest) %>" />
									</c:if>
									<%= assetRendererFactory.getTypeName(locale, false) %>
									<c:if test="<%= true %>">
										<span class="badge badge-info frequency">${doc.value}</span>
									</c:if>
								</a>
		                	</c:otherwise>
		                </c:choose>
		             	
		             </c:when>
		             <c:otherwise>
		             	<c:choose>
		                	<c:when test="<%=assetType.equals(Idea.class.getName()) %>">
		                		<a data-value="${doc.key}" href="<%=actionURL.toString()%>">
										<img alt="" src="<%=request.getContextPath() %>/images/idea.png" class="imgWidth"/>
									   Idea
									<c:if test="<%= true %>">
										<span class="badge badge-info frequency">${doc.value}</span>
									</c:if>
								</a>
		                	</c:when>
		                	<c:when test="<%=assetType.equals(Solution.class.getName()) %>">
		                		<a data-value="${doc.key}" href="<%=actionURL.toString()%>">
										<img alt="" src="<%=request.getContextPath() %>/images/solution.png" class="imgWidth"/> 
									   Solution
									<c:if test="<%= true %>">
										<span class="badge badge-info frequency">${doc.value}</span>
									</c:if>
								</a>
		                	</c:when>
		                	<c:otherwise>
		                		<a data-value="${doc.key}" href="<%=actionURL.toString()%>">
									<c:if test="<%= assetRendererFactory != null %>">
										<img alt="" src="<%= assetRendererFactory.getIconPath(renderRequest) %>" />
									</c:if>
									<%= assetRendererFactory.getTypeName(locale, false) %>
									<c:if test="<%= true %>">
										<span class="badge badge-info frequency">${doc.value}</span>
									</c:if>
								</a>
		                	</c:otherwise>
		                </c:choose>
		             </c:otherwise>
		             </c:choose>
					
				  </li>
			 </c:if>
	        </c:forEach>
        </c:if>
     </ul>
</div>
<style>
.seleted-filter{
 background-color: #eeeeee;
 color: #009ae5 !important;
}
</style>