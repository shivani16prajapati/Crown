<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portal.service.ClassNameLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.ClassName"%>
<%@ include file="/html/taglib/ddm/html/init.jsp" %>

<div class="lfr-ddm-container" id="<%= randomNamespace %>">
	<c:if test="<%= Validator.isNotNull(xsd) %>">
	    
	    <div class="lfr-forms-field-wrapper field-wrapper-content">
	     	 	<label> No of Downloads</label>
	     	 	<%=readCount %>
	     </div> 
	   
		<%= DDMXSDUtil.getHTML(pageContext, xsd, fields, portletResponse.getNamespace(), fieldsNamespace, mode, readOnly, requestedLocale) %>

		<aui:input name="<%= fieldsDisplayInputName %>" type="hidden" />

		<aui:script use="liferay-ddm-repeatable-fields">
			new Liferay.DDM.RepeatableFields(
				{
					classNameId: <%= classNameId %>,
					classPK: <%= classPK %>,
					container: '#<%= randomNamespace %>',
					doAsGroupId: <%= scopeGroupId %>,
					fieldsDisplayInput: '#<portlet:namespace /><%= fieldsDisplayInputName %>',
					namespace: '<%= fieldsNamespace %>',
					p_l_id: <%= themeDisplay.getPlid() %>,
					portletNamespace: '<portlet:namespace />',
					repeatable: <%= repeatable %>
				}
			);
		</aui:script>
	</c:if>