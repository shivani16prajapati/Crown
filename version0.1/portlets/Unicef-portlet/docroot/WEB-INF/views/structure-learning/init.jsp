<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="javax.portlet.WindowState"%>
<%@page import="javax.portlet.PortletMode"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="com.liferay.portal.service.LayoutServiceUtil"%>
<%@page import="com.liferay.portal.model.Layout"%>
<%@page import="com.liferay.portal.model.LayoutConstants"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.kernel.util.StringBundler"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.util.ContentUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.model.PortalPreferences"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.theme.PortletDisplay"%>
<%@page import="com.liferay.portal.security.permission.ActionKeys"%>
<%@page import="com.liferay.portal.service.permission.PortletPermissionUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionChecker"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.dao.search.SearchContainer"%>
<%@ page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.FacetedSearcher" %><%@
page import="com.liferay.portal.kernel.search.FolderSearcher" %><%@
page import="com.liferay.portal.kernel.search.HitsOpenSearchImpl" %><%@
page import="com.liferay.portal.kernel.search.KeywordsSuggestionHolder" %><%@
page import="com.liferay.portal.kernel.search.OpenSearch" %><%@
page import="com.liferay.portal.kernel.search.OpenSearchUtil" %><%@
page import="com.liferay.portal.kernel.search.facet.AssetEntriesFacet" %><%@
page import="com.liferay.portal.kernel.search.facet.Facet" %><%@
page import="com.liferay.portal.kernel.search.facet.ScopeFacet" %><%@
page import="com.liferay.portal.kernel.search.facet.collector.FacetCollector" %><%@
page import="com.liferay.portal.kernel.search.facet.collector.TermCollector" %><%@
page import="com.liferay.portal.kernel.search.facet.config.FacetConfiguration" %><%@
page import="com.liferay.portal.kernel.search.facet.config.FacetConfigurationUtil" %><%@
page import="com.liferay.portal.kernel.search.facet.util.FacetFactoryUtil" %><%@
page import="com.liferay.portal.kernel.search.facet.util.RangeParserUtil" %><%@
page import="com.liferay.portal.kernel.util.DateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.xml.Element" %><%@
page import="com.liferay.portal.kernel.xml.SAXReaderUtil" %><%@
page import="com.liferay.portal.security.permission.comparator.ModelResourceComparator" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.NoSuchCategoryException" %><%@
page import="com.liferay.taglib.aui.ScriptTag" %><%@
page import="com.liferay.util.PropertyComparator" %>
<%@page import="com.liferay.portal.service.PortalPreferencesLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="javax.portlet.WindowState"%>
<%@page import="com.liferay.portal.kernel.dao.search.SearchContainer"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.util.PortletKeys"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.kernel.util.SortedArrayList"%>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryServiceUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetVocabularyServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetVocabulary"%>
<%@page import="com.liferay.portal.kernel.search.SearchContextFactory"%>
<%@page import="com.liferay.portal.kernel.search.SearchContext"%>
<%@page import="com.liferay.portal.kernel.search.Indexer"%>
<%@page import="com.liferay.portal.kernel.search.Hits"%>
<%@page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.CalendarFactoryUtil"%>
<%@page import="com.liferay.portal.service.GroupLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Group"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@page import="com.unicef.dao.IdeaDAO"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<portlet:defineObjects />
<liferay-theme:defineObjects />
