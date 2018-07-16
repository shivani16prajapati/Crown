
<%@page import="com.unicef.util.SearchQueryUtil"%>
<%@page import="com.liferay.portal.kernel.util.DateUtil"%>
<%@page import="com.unicef.util.CommonUtil"%>
<%@page import="com.unicef.util.SolutionUtil"%>
<%@page import="com.unicef.domain.Solution"%>
<%@page import="com.unicef.util.IdeaUtil"%>
<%@page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@page import="com.unicef.constants.Constants"%>
<%@page import="com.unicef.domain.Idea"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoValue"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoColumn"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoTable"%>
<%@page import="com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil"%>
<%@page import="com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil"%>
<%@page import="com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@ include file="../init.jsp"%>
<%@page import="com.unicef.util.SearchUtil"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetVocabulary"%>
<%@page import="com.liferay.portlet.asset.NoSuchCategoryException"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.portlet.PortletURLUtil"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.portal.kernel.search.Summary"%>
<%@page import="com.liferay.portal.kernel.search.IndexerRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.search.Indexer"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.util.PortletKeys"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Document document = (Document)row.getObject();

String className = document.get(Field.ENTRY_CLASS_NAME);

String entryTitle = null;
String entrySummary = null;
String downloadURL = null;
String returnToFullPageURL = (String)request.getAttribute("search.jsp-returnToFullPageURL");
PortletURL viewFullContentURL = null;
String viewURL = null;
AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

AssetRenderer assetRenderer = null;

boolean inheritRedirect = false;

boolean viewInContext = true;
String currentURL = PortalUtil.getCurrentURL(renderRequest);

long entryId = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));
ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.getTable(themeDisplay.getCompanyId(), PortalUtil.getClassNameId(DLFileEntry.class), "CUSTOM_FIELDS");
ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(expandoTable.getTableId(), "urlTitle");
ExpandoValue urlTitleValue = null;

if (assetRendererFactory != null) {
	long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

	long resourcePrimKey = GetterUtil.getLong(document.get(Field.ROOT_ENTRY_CLASS_PK));
	
	AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(className,classPK);

	assetRenderer = assetRendererFactory.getAssetRenderer(classPK);

	downloadURL = assetRenderer.getURLDownload(themeDisplay);

	viewFullContentURL = SearchUtil._getViewFullContentURL(request, themeDisplay, PortletKeys.ASSET_PUBLISHER, document);

	viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");

	if (Validator.isNotNull(returnToFullPageURL)) {
		viewFullContentURL.setParameter("returnToFullPageURL", returnToFullPageURL);
	}

	viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
	viewFullContentURL.setParameter("type", assetRendererFactory.getType());

	if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
		if ((assetRenderer.getGroupId() > 0) && (assetRenderer.getGroupId() != scopeGroupId)) {
			viewFullContentURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
		}

		viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
	}
	if (viewInContext) {
		inheritRedirect = true;

		String viewFullContentURLString = viewFullContentURL.toString();

		viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

		viewURL = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, viewFullContentURLString);
	}
	else {
		viewURL = viewFullContentURL.toString();
	}
}
else {
	String portletId = document.get(Field.PORTLET_ID);

	viewFullContentURL = SearchUtil._getViewFullContentURL(request, themeDisplay, portletId, document);

	if (Validator.isNotNull(returnToFullPageURL)) {
		viewFullContentURL.setParameter("returnToFullPageURL", returnToFullPageURL);
	}

	viewURL = viewFullContentURL.toString();
}

Indexer indexer = IndexerRegistryUtil.getIndexer(className);

if (indexer != null) {
	String snippet = document.get(Field.SNIPPET);

	Summary summary = indexer.getSummary(document, locale, snippet, viewFullContentURL);

	entryTitle = summary.getTitle();
	entrySummary = summary.getContent();
}
else if (assetRenderer != null) {
	entryTitle = assetRenderer.getTitle(themeDisplay.getLocale());
	entrySummary = assetRenderer.getSearchSummary(themeDisplay.getLocale());
}

if ((assetRendererFactory == null) && viewInContext) {
	viewURL = viewFullContentURL.toString();
}

viewURL = SearchUtil._checkViewURL(themeDisplay, viewURL, currentURL, inheritRedirect);

String[] queryTerms = (String[])request.getAttribute("search.jsp-queryTerms");

PortletURL portletURL = (PortletURL)request.getAttribute("search.jsp-portletURL");
PortletURL customPortletURL = null;
String friendlyURL = StringPool.BLANK;
boolean isNew = Boolean.FALSE;
boolean isHot = Boolean.FALSE;
int newTHRESHOLD = 2;
int hotTHRESHOLD = 7;
if(className.equals(DLFileEntry.class.getName())){
	
    //portletURL.setParameter("fileEntryId", String.valueOf(classPK));
    
    try {
    	urlTitleValue = ExpandoValueLocalServiceUtil.
    					getValue(themeDisplay.getCompanyId(),PortalUtil.getClassNameId(DLFileEntry.class),expandoTable.getName(), 
    					expandoColumn.getName(),entryId);
    	if(Validator.isNotNull(urlTitleValue)){
    		friendlyURL = "/group/guest/" + entryId + StringPool.DASH +"toolkit-documents" + StringPool.DASH + urlTitleValue.getData();
    	}
    	else{
    		friendlyURL = viewURL;
    	}
    }
    catch(Exception e){
        System.out.println("Error in getting expando value in top downloads portlet view.jsp:"+e.getMessage());
        friendlyURL = viewURL;
    }
}else if(className.equals(Idea.class.getName()) || className.equals(Solution.class.getName())){
    try {
		entryTitle = document.get(Field.TITLE);
		entrySummary = document.get(Field.DESCRIPTION);
		if(className.equals(Idea.class.getName())){
			customPortletURL = CommonUtil.createIdeaPortletURL(request, themeDisplay.getPlid(), entryId, themeDisplay.getScopeGroupId());
			boolean isIdeaHot = GetterUtil.getBoolean(document.get(Constants.HOT));
			double hotWeight = Double.parseDouble(document.get(Constants.HOT_WEIGHT));
			Date createdDate = SearchQueryUtil.indexerDateFormat.parse(document.get(Field.CREATE_DATE));
			isNew = IdeaUtil.isIdeaNew(newTHRESHOLD, createdDate);
			isHot = IdeaUtil.isIdeaHOT(hotTHRESHOLD, createdDate, isIdeaHot, hotWeight);
		}else if(className.equals(Solution.class.getName())){
			customPortletURL = CommonUtil.createSolutionPortletURL(request, themeDisplay.getPlid(), entryId, themeDisplay.getScopeGroupId());
			boolean isSolutionHot = GetterUtil.getBoolean(document.get(Constants.HOT));
			double hotWeight = Double.parseDouble(document.get(Constants.HOT_WEIGHT));
			Date createdDate = SearchQueryUtil.indexerDateFormat.parse(document.get(Field.CREATE_DATE));
			isNew = SolutionUtil.isSolutionNew(newTHRESHOLD, createdDate);
			isHot = SolutionUtil.isSolutionHOT(hotTHRESHOLD, createdDate, isSolutionHot, hotWeight);
		}
		
		 
    }catch(Exception e){
        System.out.println("Error in getting entryTitle or entrySummary for Idea:"+e.getMessage());
    }
} else{
	friendlyURL = viewURL;
}
%>

<span class="asset-entry">

	<c:choose>
		<c:when test="<%=className.equals(Idea.class.getName()) %>">
			<span class="asset-entry-type" style="float: right !important;font-size: .8em !important;color: #777 !important;">Idea</span>
		</c:when>
		
		<c:when test="<%=className.equals(Solution.class.getName()) %>">
			<span class="asset-entry-type" style="float: right !important;font-size: .8em !important;color: #777 !important;">Solution</span>
		</c:when>
		
		<c:otherwise>
			<span class="asset-entry-type" style="float: right !important;font-size: .8em !important;color: #777 !important;">
				<%= ResourceActionsUtil.getModelResource(themeDisplay.getLocale(), className) %>
			</span>	
		</c:otherwise>
		
	</c:choose>

	<c:choose>
		<c:when test="<%=className.equals(Idea.class.getName()) %>">
			<span class="asset-entry-title" style="color: #5b677d !important;display: block !important;font-size: 1.5em !important;font-weight: bold !important;">
				<a href="<%=customPortletURL.toString()%>">
				 	 <c:choose>
				         <c:when test="<%=isHot %>">
				           <img alt="Hot Idea" src="<%=request.getContextPath()%>/images/hot.png" class="imgWidth">
				         </c:when>
				         <c:when test="<%=isNew %>">
				           	<img alt="New Idea" src="<%=request.getContextPath()%>/images/new.png" class="imgWidth">
				         </c:when>  
				         <c:otherwise>
				         	<img alt="Idea" src="<%=request.getContextPath()%>/images/idea.png" class="imgWidth">
				         </c:otherwise> 
       				 </c:choose>
					<%= StringUtil.highlight(HtmlUtil.escape(entryTitle), queryTerms) %>
				</a>
			</span>
		</c:when>
		<c:when test="<%=className.equals(Solution.class.getName()) %>">
			<span class="asset-entry-title" style="color: #5b677d !important;display: block !important;font-size: 1.5em !important;font-weight: bold !important;">
				<a href="<%=customPortletURL.toString()%>">
						 <c:choose>
				         <c:when test="<%=isHot %>">
				           <img alt="Hot Solution" src="<%=request.getContextPath()%>/images/hot.png" class="imgWidth">
				         </c:when>
				         <c:when test="<%=isNew %>">
				           	<img alt="New Solution" src="<%=request.getContextPath()%>/images/new.png" class="imgWidth">
				         </c:when>  
				         <c:otherwise>
				         	<img alt="Solution" src="<%=request.getContextPath()%>/images/solution.png" class="imgWidth">
				         </c:otherwise> 
       				 </c:choose>
						<%= StringUtil.highlight(HtmlUtil.escape(entryTitle), queryTerms) %>
				</a>
			</span>
		</c:when>
		
		<c:otherwise>
			<span class="asset-entry-title" style="color: #5b677d !important;display: block !important;font-size: 1.5em !important;font-weight: bold !important;">
				<a href="<%= friendlyURL %>">
					<c:if test="<%= assetRenderer != null %>">
						<img alt="" src="<%= assetRenderer.getIconPath(renderRequest) %>" />
					</c:if>
		
					<%= StringUtil.highlight(HtmlUtil.escape(entryTitle), queryTerms) %>
				</a>
		
				 <c:if test="<%= Validator.isNotNull(downloadURL) %>">
					<liferay-ui:icon image="../arrows/01_down" label="<%= false %>" message='<%= LanguageUtil.format(pageContext, "download-x", HtmlUtil.escape(entryTitle)) %>' url="<%= downloadURL %>" />
				</c:if>
			</span>
		</c:otherwise>
	</c:choose>
	
	<%
	String[] assetCategoryIds = document.getValues(Field.ASSET_CATEGORY_IDS);
	String[] assetTagNames = document.getValues(Field.ASSET_TAG_NAMES);
	%>
	<c:choose>
		<c:when test="">
			<div class="asset-entry-content">
				<c:if test="<%= Validator.isNotNull(entrySummary) %>">
					<span class="asset-entry-summary" style=" display: block !important;margin-bottom: 1em !important;">
						<%= StringUtil.highlight(HtmlUtil.escape(entrySummary), queryTerms) %>
					</span>
				</c:if>
			</div>
		</c:when>
		<c:otherwise>
			<c:if test="<%= Validator.isNotNull(entrySummary) || Validator.isNotNull(assetCategoryIds[0]) || Validator.isNotNull(assetTagNames[0]) %>">
		<div class="asset-entry-content">
			<c:if test="<%= Validator.isNotNull(entrySummary) %>">
				<span class="asset-entry-summary" style=" display: block !important;margin-bottom: 1em !important;">
					<%= StringUtil.highlight(HtmlUtil.escape(entrySummary), queryTerms) %>
				</span>
			</c:if>

			<c:if test="<%= Validator.isNotNull(assetTagNames[0]) %>">
				<div class="asset-entry-tags">

					<%
					for (int i = 0; i < assetTagNames.length; i++) {
						String assetTagName = assetTagNames[i].trim();

						PortletURL tagURL = PortletURLUtil.clone(portletURL, renderResponse);

						tagURL.setParameter(Field.ASSET_TAG_NAMES, assetTagName);
					%>

						<c:if test="<%= i == 0 %>">
							<div class="taglib-asset-tags-summary">
						</c:if>

						<a class="tag" href="<%= tagURL.toString() %>"><%= assetTagName %></a>

						<c:if test="<%= (i + 1) == assetTagNames.length %>">
							</div>
						</c:if>

					<%
					}
					%>

				</div>
			</c:if>

			<c:if test="<%= Validator.isNotNull(assetCategoryIds[0]) %>">
				<div class="asset-entry-categories">

					<%
					for (int i = 0; i < assetCategoryIds.length; i++) {
						long assetCategoryId = GetterUtil.getLong(assetCategoryIds[i]);

						AssetCategory assetCategory = null;

						try {
							assetCategory = AssetCategoryLocalServiceUtil.getCategory(assetCategoryId);
						}
						catch (NoSuchCategoryException nsce) {
						}

						if (assetCategory == null) {
							continue;
						}

						AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(assetCategory.getVocabularyId());

						PortletURL categoryURL = PortletURLUtil.clone(portletURL, renderResponse);

						categoryURL.setParameter(Field.ASSET_CATEGORY_TITLES, assetCategory.getTitle(LocaleUtil.getDefault()));
					%>

						<c:if test="<%= i == 0 %>">
							<div class="taglib-asset-categories-summary">
								<span class="asset-vocabulary">
									<%= HtmlUtil.escape(assetVocabulary.getTitle(themeDisplay.getLocale())) %>:
								</span>
						</c:if>

						<a class="asset-category" href="<%= categoryURL.toString() %>">
							<%= SearchUtil._buildAssetCategoryPath(assetCategory, themeDisplay.getLocale()) %>
						</a>

						<c:if test="<%= (i + 1) == assetCategoryIds.length %>">
							</div>
						</c:if>

					<%
					}
					%>

				</div>
			</c:if>
		</div>
	</c:if>		
		</c:otherwise>
	</c:choose>
</span>