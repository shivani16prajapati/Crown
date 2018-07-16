<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="org.apache.http.entity.FileEntity"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil"%>
<%@page import="com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoValue"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoColumn"%>
<%@page import="com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoTable"%>
<%@page import="org.springframework.beans.propertyeditors.FileEditor"%>
<%@page import="com.liferay.portlet.PortletURLFactory"%>
<%@page import="com.unicef.util.SearchUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@ include file="../init.jsp"%>
<%
PortletURL portletURL =  PortletURLFactoryUtil.create(request, "20", themeDisplay.getLayout().getPlid(), "RENDER_PHASE");
portletURL.setWindowState(WindowState.MAXIMIZED);
portletURL.setPortletMode(PortletMode.VIEW);
portletURL.setParameter("struts_action","/document_library/view_file_entry");
 
long companyId = PortalUtil.getCompanyId(request);
ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.getTable(companyId, PortalUtil.getClassNameId(DLFileEntry.class), "CUSTOM_FIELDS");
ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(expandoTable.getTableId(), "urlTitle");
ExpandoValue urlTitleValue = null;


%>
<div id="topDownloadsWrapper"  class="span4" style="float:right;">
   <div class="mpd-box1 clearfix">
		<c:choose>
		<c:when test="${not empty fileEntryList}">
		    <div class="mpd-box-label"><liferay-ui:message key="most-popular-downloads" /></div>
			<c:forEach items="${fileEntryList}" var="fileEntry" varStatus="row">
			    <%
			     DLFileEntry dlFileEntry = (DLFileEntry)pageContext.getAttribute("fileEntry");
			     portletURL.setParameter("fileEntryId", String.valueOf(dlFileEntry.getFileEntryId()));
			     
			     String friendlyURL = StringPool.BLANK;
			     try {
			     	urlTitleValue = ExpandoValueLocalServiceUtil.
			     					getValue(companyId,PortalUtil.getClassNameId(DLFileEntry.class),expandoTable.getName(), 
			     					expandoColumn.getName(),dlFileEntry.getFileEntryId());
			     	if(Validator.isNotNull(urlTitleValue)){
			     		friendlyURL = "/group/guest/" + dlFileEntry.getFileEntryId()+ StringPool.DASH +"toolkit-documents" + StringPool.DASH + urlTitleValue.getData();
			     	}
			     	else{
			     		friendlyURL = portletURL.toString();
			     	}
			     }
			     catch(Exception e){
			         System.out.println("Error in getting expando value in top downloads portlet view.jsp:"+e.getMessage());
			         friendlyURL = portletURL.toString();
			     }
			    %>
			   <div class="mpd-titlebox1 clearfix">
			      <%-- <div class="mpd-title2"><a href="<%=friendlyURL%>">${fileEntry.title}</a></div> --%>
			      <div>
			      		<span class="document-thumbnail">
							<%
							FileEntry tempFileEntry = DLAppLocalServiceUtil.getFileEntry(dlFileEntry.getFileEntryId());
							String thumbnailSrc = DLUtil.getThumbnailSrc(tempFileEntry, tempFileEntry.getFileVersion(), null, themeDisplay);
							%>
							<a href="<%=friendlyURL%>">
								<img alt="" border="no" class="thumbnail" src="<%= thumbnailSrc %>" style="max-height: 128px; max-width: 128px;" />
							</a>
						</span>
			      </div>
			      <div><a href="<%=friendlyURL%>">${fileEntry.title}</a></div>
    			</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			 <div class="mpd-box-label"><liferay-ui:message key="no-top-most-popular-downloads" /></div>
		</c:otherwise>
		</c:choose>
	</div>
</div>

<style>
.mpd-box1 {
	background-color: #f4f2f3;
	padding: 8px;
	border-radius: 5px;
	-webkit-border-radius: 5px;
}
.mpd-box-label{ font: bold 20px/22px Arial, Helvetica, sans-serif; margin:6px 0 12px 0; color:#000;}
.mpd-titlebox1 {
	font: normal 14px/22px Arial, Helvetica, sans-serif;
	background-color: #dedede;
	border-radius: 5px;
	-webkit-border-radius: 5px;
	padding: 4px 0 8px 0;
	margin-bottom:8px; position:relative;
}
.mpd-title1 {
	border-radius: 50px;
	background-color: #0CF;
	display: block;
	width: 24px;
	height: 24px;
	text-align: center;
	font-weight: bold;
	color: #fff; 
	position:absolute;
	left:5px;
	top:5px;
}
.mpd-title2 {
	float: left;
	font-size: 14px
	display:block;
	padding-left:35px;
	
}
.mpd-title2 a {
	text-decoration: none;
	color: #111;
}
/* Contain floats: h5bp.com/q */ 
.clearfix:before, .clearfix:after {
	content: "";
	display: table;
}
.clearfix:after {
	clear: both;
}
.clearfix {
	zoom: 1;
}
</style>
				