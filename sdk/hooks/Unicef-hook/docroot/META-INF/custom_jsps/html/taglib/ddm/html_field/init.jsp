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

@generated
--%>

<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@ include file="/html/taglib/taglib-init.jsp" %>

<%
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("ddm:html-field:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("ddm:html-field:scopedAttributes");

Map<String, Object> _options = new HashMap<String, Object>();

if ((scopedAttributes != null) && !scopedAttributes.isEmpty()) {
	_options.putAll(scopedAttributes);
}

if ((dynamicAttributes != null) && !dynamicAttributes.isEmpty()) {
	_options.putAll(dynamicAttributes);
}

long classNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("ddm:html:classNameId")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("ddm:html:classPK")));
com.liferay.portlet.dynamicdatamapping.storage.Fields fields = (com.liferay.portlet.dynamicdatamapping.storage.Fields)request.getAttribute("ddm:html:fields");
java.lang.String combinedFieldsNamespace = GetterUtil.getString((java.lang.String)request.getAttribute("ddm:html:fieldsNamespace"));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ddm:html:readOnly")));
boolean repeatable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ddm:html:repeatable")), true);
java.util.Locale requestedLocale = (java.util.Locale)request.getAttribute("ddm:html:requestedLocale");
java.lang.String fieldsNamespace = combinedFieldsNamespace.split(StringPool.COMMA)[0];
int readCount =  GetterUtil.getInteger(String.valueOf(combinedFieldsNamespace.split(StringPool.COMMA)[1]));

_updateOptions(_options, "classNameId", classNameId);
_updateOptions(_options, "classPK", classPK);
_updateOptions(_options, "field", field);
_updateOptions(_options, "fieldsNamespace", fieldsNamespace);
_updateOptions(_options, "readOnly", readOnly);
_updateOptions(_options, "repeatable", repeatable);
_updateOptions(_options, "requestedLocale", requestedLocale);
%>

<%@ include file="/html/taglib/ddm/html_field/init-ext.jspf" %>

<%!
private static final String _NAMESPACE = "ddm:html-field:";
%>