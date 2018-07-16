<%@ include file="../init.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<form action="<portlet:actionURL />"
	method="post" name="<portlet:namespace />fm">

	<div class="unicef-conf">
	 	 <aui:input name="newTHRESHOLD" type="text" value="${newTHRESHOLD}" label="newTHRESHOLD:"></aui:input>
	 	 <aui:input name="hotTHRESHOLD" type="text" value="${hotTHRESHOLD}" label="hotTHRESHOLD:"></aui:input>
	</div>

	<br /> <input type="hidden" name="<portlet:namespace />action" value="save"> <input
		type="button" value="Save"
		onClick="submitForm(document.<portlet:namespace />fm);" />
</form>