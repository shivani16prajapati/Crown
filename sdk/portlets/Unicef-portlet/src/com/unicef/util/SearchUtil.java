package com.unicef.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.FacetedSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ModifiedFacet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.FacetFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.wiki.model.WikiPage;
import com.unicef.domain.Idea;
import com.unicef.domain.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class SearchUtil {

	/**
	 * @param renderRequest
	 * @param keyword
	 * @param sort
	 * @param start
	 * @param end
	 * @return
	 */
	public static Hits searchAll(PortletRequest portletRequest, String keyword, String sort, int start, int end, String entryClassName){
	    
		HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(portletRequest));
		
		SearchContext searchContext = SearchContextFactory.getInstance(httpReq);
		searchContext.setCompanyId(PortalUtil.getCompanyId(portletRequest));
		searchContext.setAttribute("paginationType", "more");
		
		searchContext.setKeywords(keyword);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		
		searchContext.addFacet(getScopeFacet(searchContext));
		searchContext.addFacet(getAssetEntryFacet(searchContext));
		searchContext.addFacet(getAssetTagNameFacet(searchContext));
		searchContext.addFacet(getAssetCategoriesFacet(searchContext));
		searchContext.addFacet(getFoldersFacet(searchContext));
		searchContext.addFacet(getUsersFacet(searchContext));
		searchContext.addFacet(getModifiedFacet(searchContext));
		
			if(Validator.isNotNull(entryClassName)){
				String entryClassNames[] = {entryClassName};
				searchContext.setEntryClassNames(entryClassNames);
			}else{
				String entryClassNames[] = {User.class.getName(),
						BookmarksEntry.class.getName(),BookmarksFolder.class.getName(), 
						BlogsEntry.class.getName(),DLFileEntry.class.getName(),
						DLFolder.class.getName(),JournalArticle.class.getName(),
						JournalFolder.class.getName(),MBMessage.class.getName(),
						WikiPage.class.getName(),Idea.class.getName(),
						Solution.class.getName()
				};
				searchContext.setEntryClassNames(entryClassNames);
			}
			
		Indexer indexer = FacetedSearcher.getInstance();
		Hits hits = null;
		try {
			hits = indexer.search(searchContext);
		} catch (SearchException e) {
			_log.error("Error in getting search result:"+e.getMessage());;
		}
		
		/*Hits hits2 = null;
		try {
			System.out.println("BooleanQuery:::"+getBooleanQuery(searchContext));
			 hits2  = SearchEngineUtil.search(searchContext, getBooleanQuery(searchContext));
		} catch (SearchException e) {
			_log.error("Error in getting idea search results:"+e.getMessage());
		}
		for(Document document: hits2.toList()){
		    System.out.println("document.get(Field.TITLE):"+document.get(Field.TITLE));
		}*/
		return hits;
	}
	
	public static Hits advanceSearch(String[] sourceFields,
			String[] typeFields, String[] stageFields, String fieldFrom,
			String fieldTo, String[] hotIdeasFields, String[] includeTags, String[] excludeTags,
			PortletRequest portletRequest, int start, int end) {
		
		HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(portletRequest));
		
		SearchContext searchContext = SearchContextFactory.getInstance(httpReq);
		searchContext.setCompanyId(PortalUtil.getCompanyId(portletRequest));
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.addFacet(getScopeFacet(searchContext));
		searchContext.addFacet(getAssetEntryFacet(searchContext));
		searchContext.addFacet(getAssetTagNameFacet(searchContext));
		searchContext.addFacet(getAssetCategoriesFacet(searchContext));
		searchContext.addFacet(getFoldersFacet(searchContext));
		searchContext.addFacet(getUsersFacet(searchContext));
		searchContext.addFacet(getModifiedFacet(searchContext));
		
		BooleanQuery searchFullQuery = BooleanQueryFactoryUtil
				.create(searchContext);
		
		try{
			if(sourceFields.length > 0 && Validator.isNotNull(sourceFields[0])){
				searchFullQuery.add(SearchQueryUtil.createSourceBooleanQuery(sourceFields, searchContext), BooleanClauseOccur.MUST);
			}
			if(typeFields.length > 0 && Validator.isNotNull(typeFields[0])){
				searchFullQuery.add(SearchQueryUtil.createTypeBooleanQuery(typeFields, searchContext), BooleanClauseOccur.MUST);
			}
			if(stageFields.length > 0 && Validator.isNotNull(stageFields[0])){
				searchFullQuery.add(SearchQueryUtil.createStageBooleanQuery(stageFields, searchContext), BooleanClauseOccur.MUST);
			}
			if(Validator.isNotNull(fieldFrom) && Validator.isNotNull(fieldTo)){
				searchFullQuery.add(SearchQueryUtil.createDateRangeBooleanQuery(fieldFrom, fieldTo, searchContext), BooleanClauseOccur.MUST);
			}
			if(includeTags.length > 0  && Validator.isNotNull(includeTags[0])){
				searchFullQuery.add(SearchQueryUtil.createIncludeTagsBooleanQuery(includeTags, searchContext), BooleanClauseOccur.MUST);
			}
			if(excludeTags.length > 0  && Validator.isNotNull(excludeTags[0])){
				searchFullQuery.add(SearchQueryUtil.createExcludeTagsBooleanQuery(excludeTags, searchContext), BooleanClauseOccur.MUST_NOT);
			}
			if(hotIdeasFields.length > 0  && Validator.isNotNull(hotIdeasFields[0])){
				searchFullQuery.add(SearchQueryUtil.createHotIdeasBooleanQuery(hotIdeasFields, searchContext), BooleanClauseOccur.MUST);
			}
		} catch (ParseException e) {
			_log.error("Error in creating advance search query:"+e.getMessage());
		}
		_log.info("searchQuery:"+searchFullQuery.toString());
		Hits hits = null;
		try {
			 hits = SearchEngineUtil.search(searchContext, searchFullQuery);
		} catch (SearchException e) {
			_log.error("Error in getting advance search results:"+e.getMessage());
		}
		_log.info("LENGTH:"+hits.getLength());
		return hits;
	}
	
	
	/*private static Hits filterHits(Hits hits) {
		//Hits filteredHits = null;
		List<Document> documentsList = new ArrayList<Document>();
		if(Validator.isNotNull(hits)){
			for(Document document: hits.getDocs()){
				if(Validator.isNotNull(document.get(Field.TITLE)) && !document.get(Field.TITLE).startsWith(StringPool.FORWARD_SLASH)){
					documentsList.add(document);
				}
			}
			Document[] documents = documentsList.toArray(new Document[documentsList.size()]);
			hits.setDocs(documents);;
	     }
		System.out.println("Filter Hits: hits.getLength()"+hits.getLength());
		return hits;
		
	}*/

	/**
	 * @param renderRequest
	 * @param keywords
	 * @param start
	 * @param end
	 * @return
	 */
	public static HashMap<String, Integer> getDocsMap(RenderRequest renderRequest, String keywords, int start, int end) {
		HashMap<String, Integer> docsMap = new HashMap<String, Integer>();
		
		int userCount=0;
		int bookMarkEntryCount = 0;
		int bookmarksFolderCount=0;
		int blogCount=0;
		int documentCount = 0;
		int folderCount = 0;
		int journalArticleCount = 0;
		int journalFolderCount = 0;
		int mbMessageCount = 0;
		int wikiCount = 0;
		int ideaCount = 0;
		int solutionCount = 0;
		
		try {
		Hits hits = searchAll(renderRequest, keywords, StringPool.BLANK, -1, -1, StringPool.BLANK);
		if(Validator.isNotNull(hits)){
			for(Document document : hits.getDocs()){
				if(document.get(Field.ENTRY_CLASS_NAME).equals(User.class.getName())){
				   userCount++;
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(BookmarksEntry.class.getName())){
				   bookMarkEntryCount++;
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(BookmarksFolder.class.getName())){
				   bookmarksFolderCount++;			
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(BlogsEntry.class.getName())){
				   blogCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(DLFileEntry.class.getName())){
				   documentCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(DLFolder.class.getName())){
				   folderCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(JournalArticle.class.getName())){
				   journalArticleCount++;
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(JournalFolder.class.getName())){
				   journalFolderCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(MBMessage.class.getName())){
				   mbMessageCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(WikiPage.class.getName())){
				   wikiCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(Idea.class.getName())){
				   ideaCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(Solution.class.getName())){
				   solutionCount++;	
				}
			}
		}
			docsMap.put(User.class.getName(), userCount);
			docsMap.put(BookmarksEntry.class.getName(), bookMarkEntryCount);
			docsMap.put(BookmarksFolder.class.getName(), bookmarksFolderCount);
			docsMap.put(BlogsEntry.class.getName(), blogCount);
			docsMap.put(DLFileEntry.class.getName(), documentCount);
			docsMap.put(DLFolder.class.getName(), folderCount);
			docsMap.put(JournalArticle.class.getName(), journalArticleCount);
			docsMap.put(JournalFolder.class.getName(), journalFolderCount);
			docsMap.put(MBMessage.class.getName(), mbMessageCount);
			docsMap.put(WikiPage.class.getName(), wikiCount);
			docsMap.put(Idea.class.getName(), ideaCount);
			docsMap.put(Solution.class.getName(), solutionCount);
			
		}catch(Exception e){
			_log.error("Error in getting docsMap:"+e.getMessage());
		}
		
		return docsMap;
	}
	
	public static HashMap<String, Integer> getDocsMap(String[] sourceFields,
			String[] typeFields, String[] stageFields, String fieldFrom,
			String fieldTo, String[] hotIdeasFields, String[] includeTags, 
			String[] excludeTags, PortletRequest portletRequest) {
		HashMap<String, Integer> docsMap = new HashMap<String, Integer>();
		
		int userCount=0;
		int bookMarkEntryCount = 0;
		int bookmarksFolderCount=0;
		int blogCount=0;
		int documentCount = 0;
		int folderCount = 0;
		int journalArticleCount = 0;
		int journalFolderCount = 0;
		int mbMessageCount = 0;
		int wikiCount = 0;
		int ideaCount = 0;
		int solutionCount = 0;
		
		try {
		Hits hits = advanceSearch(sourceFields, typeFields, stageFields, fieldFrom, fieldTo, hotIdeasFields, includeTags, excludeTags, portletRequest, -1, -1);
		if(Validator.isNotNull(hits)){
			for(Document document : hits.getDocs()){
				if(document.get(Field.ENTRY_CLASS_NAME).equals(User.class.getName())){
				   userCount++;
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(BookmarksEntry.class.getName())){
				   bookMarkEntryCount++;
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(BookmarksFolder.class.getName())){
				   bookmarksFolderCount++;			
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(BlogsEntry.class.getName())){
				   blogCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(DLFileEntry.class.getName())){
				   documentCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(DLFolder.class.getName())){
				   folderCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(JournalArticle.class.getName())){
				   journalArticleCount++;
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(JournalFolder.class.getName())){
				   journalFolderCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(MBMessage.class.getName())){
				   mbMessageCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(WikiPage.class.getName())){
				   wikiCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(Idea.class.getName())){
				   ideaCount++;	
				}
				else if(document.get(Field.ENTRY_CLASS_NAME).equals(Solution.class.getName())){
				   solutionCount++;	
				}
			}
		}
			docsMap.put(User.class.getName(), userCount);
			docsMap.put(BookmarksEntry.class.getName(), bookMarkEntryCount);
			docsMap.put(BookmarksFolder.class.getName(), bookmarksFolderCount);
			docsMap.put(BlogsEntry.class.getName(), blogCount);
			docsMap.put(DLFileEntry.class.getName(), documentCount);
			docsMap.put(DLFolder.class.getName(), folderCount);
			docsMap.put(JournalArticle.class.getName(), journalArticleCount);
			docsMap.put(JournalFolder.class.getName(), journalFolderCount);
			docsMap.put(MBMessage.class.getName(), mbMessageCount);
			docsMap.put(WikiPage.class.getName(), wikiCount);
			docsMap.put(Idea.class.getName(), ideaCount);
			docsMap.put(Solution.class.getName(), solutionCount);
			
		}catch(Exception e){
			_log.error("Error in getting docsMap:"+e.getMessage());
		}
		
		return docsMap;
	}
	
	/**
	 * @param searchContext
	 * @return
	 */
	public static Facet getScopeFacet(SearchContext searchContext){
		Facet facet = null;
		FacetConfiguration facetConfiguration = getFacetConfigObj("scopes","groupId", "site", "OrderHitsDesc", Boolean.FALSE,
				1.6, ScopeFacet.class.getName(), getScopeDataJSONobject(1, 10, Boolean.TRUE));
		try {
			facet = FacetFactoryUtil.create(searchContext, facetConfiguration);
		} catch (Exception e1) {
			_log.error("Error in creating facet:"+e1.getMessage());
		}
		return facet;
	}
	
	/**
	 * @param searchContext
	 * @return
	 */
	public static Facet getAssetEntryFacet(SearchContext searchContext){
		Facet facet = null;
		FacetConfiguration facetConfiguration = getFacetConfigObj("asset_entries","entryClassName", "asset-type", "OrderHitsDesc", Boolean.FALSE,
				1.5, AssetEntriesFacet.class.getName(), getAssetEntriesDataJSONobject(1));
		try {
			facet = FacetFactoryUtil.create(searchContext, facetConfiguration);
		} catch (Exception e1) {
			_log.error("Error in creating facet:"+e1.getMessage());
		}
		return facet;
	}
	
	/**
	 * @param searchContext
	 * @return
	 */
	public static Facet getAssetTagNameFacet(SearchContext searchContext){
		Facet facet = null;
		FacetConfiguration facetConfiguration = getFacetConfigObj("asset_tags","assetTagNames", "tag", "OrderHitsDesc", Boolean.FALSE,
				1.4, MultiValueFacet.class.getName(), getDataJSONobject("list", 1, 10, Boolean.TRUE));
		try {
			facet = FacetFactoryUtil.create(searchContext, facetConfiguration);
		} catch (Exception e1) {
			_log.error("Error in creating facet:"+e1.getMessage());
		}
		return facet;
	}
	
	/**
	 * @param searchContext
	 * @return
	 */
	public static Facet getAssetCategoriesFacet(SearchContext searchContext){
		Facet facet = null;
		FacetConfiguration facetConfiguration = getFacetConfigObj("asset_categories","assetCategoryIds", "category", "OrderHitsDesc", Boolean.FALSE,
				1.3, MultiValueFacet.class.getName(), getDataJSONobject("list", 1, 10, Boolean.TRUE));
		try {
			facet = FacetFactoryUtil.create(searchContext, facetConfiguration);
		} catch (Exception e1) {
			_log.error("Error in creating facet:"+e1.getMessage());
		}
		return facet;
	}
	
	/**
	 * @param searchContext
	 * @return
	 */
	public static Facet getFoldersFacet(SearchContext searchContext){
		Facet facet = null;
		FacetConfiguration facetConfiguration = getFacetConfigObj("folders","folderId", "folder", "OrderHitsDesc", Boolean.FALSE,
				1.2, MultiValueFacet.class.getName(), getScopeDataJSONobject(1, 10, Boolean.TRUE));
		try {
			facet = FacetFactoryUtil.create(searchContext, facetConfiguration);
		} catch (Exception e1) {
			_log.error("Error in creating facet:"+e1.getMessage());
		}
		return facet;
	}
	
	/**
	 * @param searchContext
	 * @return
	 */
	public static Facet getUsersFacet(SearchContext searchContext){
		Facet facet = null;
		FacetConfiguration facetConfiguration = getFacetConfigObj("users","userId", "user", "OrderHitsDesc", Boolean.FALSE,
				1.1, MultiValueFacet.class.getName(), getScopeDataJSONobject(1, 10, Boolean.TRUE));
		try {
			facet = FacetFactoryUtil.create(searchContext, facetConfiguration);
		} catch (Exception e1) {
			_log.error("Error in creating facet:"+e1.getMessage());
		}
		return facet;
	}
	
	/**
	 * @param searchContext
	 * @return
	 */
	public static Facet getModifiedFacet(SearchContext searchContext){
		Facet facet = null;
		FacetConfiguration facetConfiguration = getFacetConfigObj("modified","modified", "modified", "OrderHitsDesc", Boolean.FALSE,
				1.0, ModifiedFacet.class.getName(), getModifiedDataJSONobject(0));
		try {
			facet = FacetFactoryUtil.create(searchContext, facetConfiguration);
		} catch (Exception e1) {
			_log.error("Error in creating facet:"+e1.getMessage());
		}
		return facet;
	}
	
	/**
	 * @param displayStyle
	 * @param fieldName
	 * @param label
	 * @param order
	 * @param stc
	 * @param weight
	 * @param className
	 * @param dataObj
	 * @return
	 */
	private static FacetConfiguration getFacetConfigObj(String displayStyle, String fieldName, String label, String order, boolean stc,
			double weight, String className, JSONObject dataObj){
		
		FacetConfiguration facetConfiguration = new FacetConfiguration();
		facetConfiguration.setDataJSONObject(dataObj);
		facetConfiguration.setDisplayStyle(displayStyle);
		facetConfiguration.setFieldName(fieldName);
		facetConfiguration.setLabel(label);
		facetConfiguration.setOrder(order);
		facetConfiguration.setStatic(stc);
		facetConfiguration.setWeight(weight);
		facetConfiguration.setClassName(className);
		
		return facetConfiguration;
	}
	
	/**
	 * @param displayStyle
	 * @param frequencyThreshold
	 * @param maxTerms
	 * @param showAssetCount
	 * @return
	 */
	private static JSONObject getDataJSONobject(String displayStyle, int frequencyThreshold, int maxTerms, boolean showAssetCount){
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("displayStyle", displayStyle);
		jsonObject.put("frequencyThreshold", frequencyThreshold);
		jsonObject.put("maxTerms", maxTerms);
		jsonObject.put("showAssetCount", showAssetCount);
		return jsonObject;
	}
	
	/**
	 * @param frequencyThreshold
	 * @param maxTerms
	 * @param showAssetCount
	 * @return
	 */
	private static JSONObject getScopeDataJSONobject(int frequencyThreshold, int maxTerms, boolean showAssetCount){
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("frequencyThreshold", frequencyThreshold);
		jsonObject.put("maxTerms", maxTerms);
		jsonObject.put("showAssetCount", showAssetCount);
		return jsonObject;
	}
	
	/**
	 * @param frequencyThreshold
	 * @return
	 */
	private static JSONObject getAssetEntriesDataJSONobject(int frequencyThreshold){
	
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		JSONArray valuesArray = JSONFactoryUtil.createJSONArray();
		
		valuesArray.put(User.class.getName());
		valuesArray.put(BookmarksEntry.class.getName());
		valuesArray.put(BookmarksFolder.class.getName());
		valuesArray.put(BlogsEntry.class.getName());
		valuesArray.put(DLFileEntry.class.getName());
		valuesArray.put(DLFolder.class.getName());
		valuesArray.put(JournalArticle.class.getName());
		valuesArray.put(JournalFolder.class.getName());
		valuesArray.put(MBMessage.class.getName());
		valuesArray.put(WikiPage.class.getName());
		valuesArray.put(Idea.class.getName());
		valuesArray.put(Solution.class.getName());
		
		jsonObject.put("frequencyThreshold", frequencyThreshold);
		jsonObject.put("values", valuesArray);
		
		return jsonObject;
	}

	/**
	 * @param frequencyThreshold
	 * @return
	 */
	private static JSONObject getModifiedDataJSONobject(int frequencyThreshold){
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		JSONArray rangeJSONArray = JSONFactoryUtil.createJSONArray();
		
		rangeJSONArray.put(getRangeJSONObject("past-hour","[past-hour TO *]"));
		rangeJSONArray.put(getRangeJSONObject("past-24-hours","[past-24-hours TO *]"));
		rangeJSONArray.put(getRangeJSONObject("past-week","[past-week TO *]"));
		rangeJSONArray.put(getRangeJSONObject("past-month","[past-month TO *]"));
		rangeJSONArray.put(getRangeJSONObject("past-year","[past-year TO *]"));
		
		jsonObject.put("frequencyThreshold", frequencyThreshold);
		jsonObject.put("ranges", rangeJSONArray);
		
		return jsonObject;
	}

	/**
	 * @param label
	 * @param range
	 * @return
	 */
	private static JSONObject getRangeJSONObject(String label, String range){
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("label", label);
		jsonObject.put("range", range);
		
		return jsonObject;
	}
	
	/**
	 * @param assetCategory
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public static  String _buildAssetCategoryPath(AssetCategory assetCategory, Locale locale) throws Exception {
		List<AssetCategory> assetCategories = assetCategory.getAncestors();

		if (assetCategories.isEmpty()) {
			return HtmlUtil.escape(assetCategory.getTitle(locale));
		}

		Collections.reverse(assetCategories);

		StringBundler sb = new StringBundler(assetCategories.size() * 2 + 1);

		for (AssetCategory curAssetCategory : assetCategories) {
			sb.append(HtmlUtil.escape(curAssetCategory.getTitle(locale)));
			sb.append(" &raquo; ");
		}

		sb.append(HtmlUtil.escape(assetCategory.getTitle(locale)));

		return sb.toString();
	}

	/**
	 * @param themeDisplay
	 * @param viewURL
	 * @param currentURL
	 * @param inheritRedirect
	 * @return
	 */
	public static  String _checkViewURL(ThemeDisplay themeDisplay, String viewURL, String currentURL, boolean inheritRedirect) {
		if (Validator.isNotNull(viewURL) && viewURL.startsWith(themeDisplay.getURLPortal())) {
			viewURL = HttpUtil.setParameter(viewURL, "inheritRedirect", inheritRedirect);

			if (!inheritRedirect) {
				viewURL = HttpUtil.setParameter(viewURL, "redirect", currentURL);
			}
		}

		return viewURL;
	}

	/**
	 * @param request
	 * @param themeDisplay
	 * @param portletId
	 * @param document
	 * @return
	 * @throws Exception
	 */
	public static PortletURL _getViewFullContentURL(HttpServletRequest request, ThemeDisplay themeDisplay, String portletId, Document document) throws Exception {
		long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

		if (groupId == 0) {
			Layout layout = themeDisplay.getLayout();

			groupId = layout.getGroupId();
		}

		long scopeGroupId = GetterUtil.getLong(document.get(Field.SCOPE_GROUP_ID));

		if (scopeGroupId == 0) {
			scopeGroupId = themeDisplay.getScopeGroupId();
		}

		long plid = LayoutConstants.DEFAULT_PLID;

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		if (layout != null) {
			plid = layout.getPlid();
		}

		if (plid == 0) {
			plid = LayoutServiceUtil.getDefaultPlid(groupId, scopeGroupId, portletId);
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(request, portletId, plid, PortletRequest.RENDER_PHASE);

		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(WindowState.MAXIMIZED);

		return portletURL;
	}
	
	/**
	 * @param request
	 * @param themeDisplay
	 * @param portletId
	 * @param groupId
	 * @param scopeGroupId
	 * @return
	 * @throws Exception
	 */
	public static PortletURL _getDocumentViewFullContentURL(HttpServletRequest request, ThemeDisplay themeDisplay, String portletId, long groupId, 
			long scopeGroupId) throws Exception {
		//long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

		if (groupId == 0) {
			Layout layout = themeDisplay.getLayout();

			groupId = layout.getGroupId();
		}

		//long scopeGroupId = GetterUtil.getLong(document.get(Field.SCOPE_GROUP_ID));

		if (scopeGroupId == 0) {
			scopeGroupId = themeDisplay.getScopeGroupId();
		}

		long plid = LayoutConstants.DEFAULT_PLID;

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		if (layout != null) {
			plid = layout.getPlid();
		}

		if (plid == 0) {
			plid = LayoutServiceUtil.getDefaultPlid(groupId, scopeGroupId, portletId);
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(request, portletId, plid, PortletRequest.RENDER_PHASE);

		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(WindowState.MAXIMIZED);

		return portletURL;
	}
	
	
	/**
	 * @param queryTerms
	 * @return
	 */
	public static String[] generateNewCombinationTerms(String[] queryTerms){
		
		List<String> queryTermsList  = new ArrayList<String>();
		
		for(int i = 0; i < queryTerms.length; i++){
			queryTermsList.add(queryTerms[i].toLowerCase());
			queryTermsList.add(queryTerms[i].toUpperCase());
			queryTermsList.add(generateFirstLetterUpperCaseTerm(queryTerms[i]));
			queryTermsList.add(generateFirstLetterLowerCaseTerm(queryTerms[i]));
		}
		return queryTermsList.toArray(new String[queryTermsList.size()]);
		
	      
	}
	
	/**
	 * @param queryTerm
	 * @return
	 */
	public static String generateFirstLetterUpperCaseTerm(String queryTerm) {
		
		StringBuffer wordbuffer = new StringBuffer();
	    Matcher m = Pattern.compile(CASE_REGEX,
	    Pattern.CASE_INSENSITIVE).matcher(queryTerm);
	    
	    while (m.find()) {
	         m.appendReplacement(wordbuffer, 
	         m.group(1).toUpperCase() + m.group(2).toLowerCase());
	      }
		
	    return m.appendTail(wordbuffer).toString();
	}
	
	/**
	 * @param queryTerm
	 * @return
	 */
	public static String generateFirstLetterLowerCaseTerm(String queryTerm) {
		
		StringBuffer wordbuffer = new StringBuffer();
	    Matcher m = Pattern.compile(CASE_REGEX,
	    Pattern.CASE_INSENSITIVE).matcher(queryTerm);
	    
	    while (m.find()) {
	         m.appendReplacement(wordbuffer, 
	         m.group(1).toLowerCase() + m.group(2).toUpperCase());
	      }
		
	    return m.appendTail(wordbuffer).toString();
	}
	
	public static BooleanQuery getBooleanQuery(SearchContext searchContext) {
		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create(searchContext);
		try {
			
			BooleanQuery eQuery = BooleanQueryFactoryUtil
					.create(searchContext);
			eQuery.addTerm(Field.ENTRY_CLASS_NAME, Idea.class.getName());
			
			BooleanQuery titleQuery = BooleanQueryFactoryUtil
					.create(searchContext);
			titleQuery.addTerm(Field.TITLE, searchContext.getKeywords());
			
			BooleanQuery contentQuery = BooleanQueryFactoryUtil
					.create(searchContext);
			contentQuery.addTerm(Field.CONTENT, searchContext.getKeywords());
			
			BooleanQuery descriptionQuery = BooleanQueryFactoryUtil
					.create(searchContext);
			descriptionQuery.addTerm(Field.DESCRIPTION, searchContext.getKeywords());
			
			fullQuery.add(eQuery, BooleanClauseOccur.MUST);
			fullQuery.add(titleQuery, BooleanClauseOccur.MUST);
			fullQuery.add(contentQuery, BooleanClauseOccur.MUST);
			fullQuery.add(descriptionQuery, BooleanClauseOccur.MUST);
			_log.debug(fullQuery.toString());
		}catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return fullQuery;
	}
	
	public static String getSOLRServerURL(){
		if(Validator.isNotNull(SOLR_SERVER_URL)){
			return SOLR_SERVER_URL;
		}
		
		return "http://localhost:7080/solr";
	}
	
	private static final Log _log = LogFactoryUtil.getLog(SearchUtil.class);

	private static final String CASE_REGEX = "([a-z])([a-z]*)";
	
	private static final String SOLR_SERVER_URL = PropsUtil.get("apache.solr.home.url");
	
}
