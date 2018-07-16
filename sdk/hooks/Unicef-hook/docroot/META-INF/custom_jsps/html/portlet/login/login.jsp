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

<%@ include file="/html/portlet/login/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		String signedInAs = HtmlUtil.escape(user.getFullName());

		if (themeDisplay.isShowMyAccountIcon() && (themeDisplay.getURLMyAccount() != null)) {
			String myAccountURL = String.valueOf(themeDisplay.getURLMyAccount());

			if (PropsValues.DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP) {
				signedInAs = "<a href=\"javascript:Liferay.Util.openWindow({dialog: {destroyOnHide: true}, title: '" + LanguageUtil.get(pageContext, "my-account") + "', uri: '" + HtmlUtil.escape(myAccountURL) + "'});\">" + signedInAs + "</a>";
			}
			else {
				myAccountURL = HttpUtil.setParameter(myAccountURL, "controlPanelCategory", PortletCategoryKeys.MY);

				signedInAs = "<a href=\"" + HtmlUtil.escape(myAccountURL) + "\">" + signedInAs + "</a>";
			}
		}
		%>

		<%= LanguageUtil.format(pageContext, "you-are-signed-in-as-x", signedInAs, false) %>
	</c:when>
	<c:otherwise>

		<%
		String redirect = ParamUtil.getString(request, "redirect");

		String login = LoginUtil.getLogin(request, "login", company);
		String password = StringPool.BLANK;
		boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");

		if (Validator.isNull(authType)) {
			authType = company.getAuthType();
		}
		%>

		<portlet:actionURL secure="<%= PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS || request.isSecure() %>" var="loginURL">
			<portlet:param name="struts_action" value="/login/login" />
		</portlet:actionURL>

		<aui:form action="<%= loginURL %>" autocomplete='<%= PropsValues.COMPANY_SECURITY_LOGIN_FORM_AUTOCOMPLETE ? "on" : "off" %>' cssClass="sign-in-form" method="post" name="fm">
			<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="doActionAfterLogin" type="hidden" value="<%= portletName.equals(PortletKeys.FAST_LOGIN) ? true : false %>" />

			<c:choose>
				<c:when test='<%= SessionMessages.contains(request, "userAdded") %>'>

					<%
					String userEmailAddress = (String)SessionMessages.get(request, "userAdded");
					String userPassword = (String)SessionMessages.get(request, "userAddedPassword");
					%>

					<div class="alert alert-success">
						<c:choose>
							<c:when test="<%= company.isStrangersVerify() || Validator.isNull(userPassword) %>">
								<%= LanguageUtil.get(pageContext, "thank-you-for-creating-an-account") %>

								<c:if test="<%= company.isStrangersVerify() %>">
									<%= LanguageUtil.format(pageContext, "your-email-verification-code-has-been-sent-to-x", userEmailAddress) %>
								</c:if>
							</c:when>
							<c:otherwise>
								<%= LanguageUtil.format(pageContext, "thank-you-for-creating-an-account.-your-password-is-x", userPassword, false) %>
							</c:otherwise>
						</c:choose>

						<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED) %>">
							<%= LanguageUtil.format(pageContext, "your-password-has-been-sent-to-x", userEmailAddress) %>
						</c:if>
					</div>
				</c:when>
				<c:when test='<%= SessionMessages.contains(request, "userPending") %>'>

					<%
					String userEmailAddress = (String)SessionMessages.get(request, "userPending");
					%>

					<div class="alert alert-success">
						<%= LanguageUtil.format(pageContext, "thank-you-for-creating-an-account.-you-will-be-notified-via-email-at-x-when-your-account-has-been-approved", userEmailAddress) %>
					</div>
				</c:when>
			</c:choose>

			<liferay-ui:error exception="<%= AuthException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-login-because-the-maximum-number-of-users-has-been-reached" />
			<liferay-ui:error exception="<%= CookieNotSupportedException.class %>" message="authentication-failed-please-enable-browser-cookies" />
			<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= PasswordExpiredException.class %>" message="your-password-has-expired" />
			<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= UserLockoutException.class %>" message="this-account-has-been-locked" />
			<liferay-ui:error exception="<%= UserPasswordException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="authentication-failed" />

			<aui:fieldset>

				<%
				String loginLabel = null;

				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					loginLabel = "email-address";
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					loginLabel = "screen-name";
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
					loginLabel = "id";
				}
				%>
					<div class="np-input"> 
						<aui:input autoFocus="<%= windowState.equals(LiferayWindowState.EXCLUSIVE) || windowState.equals(WindowState.MAXIMIZED) %>" cssClass="clearable np-form-control-home1" placeholder="Enter username" name="login" showRequiredLabel="<%= false %>" type="text" label="">
							<aui:validator name="required" />
						</aui:input>
					</div>
	                <div class="np-input">
						<aui:input label="" placeholder="Enter password" name="password" cssClass="np-form-control-home2" showRequiredLabel="<%= false %>" type="password" value="<%= password %>">
							<aui:validator name="required" />
						</aui:input>
	                </div> 
					<span id="<portlet:namespace />passwordCapsLockSpan" style="display: none;"><liferay-ui:message key="caps-lock-is-on" /></span>
					<div class="np-input-button">
						<aui:button type="submit" value="Sign In" cssClass="np-submit" />
					</div>
					<div class="clearfix">
						<div>
							<div class="has-js">
								<label class="label_check" for="sample-check">
								<c:if test="<%= company.isAutoLogin() && !PropsValues.SESSION_DISABLED %>">
									<aui:input checked="<%= rememberMe %>" name="rememberMe" id="sample-check" showRequiredLabel="<%=false %>"  type="checkbox" />
								</c:if>
								</label>
							</div>
							<!-- <div class="has-js">
								<label class="label_check" for="sample-check">
    							<input name="Remember Me" id="sample-check" value="1" type="checkbox" />
    								Remember Me
								</label>
							</div> -->
							<liferay-util:include page="/html/portlet/login/navigation.jsp" />
						</div> 
					</div> 
			</aui:fieldset>

			
		</aui:form>

		

<aui:script use="aui-base">
			var password = A.one('#<portlet:namespace />password');

			if (password) {
				password.on(
					'keypress',
					function(event) {
						Liferay.Util.showCapsLock(event, '<portlet:namespace />passwordCapsLockSpan');
					}
				);
			}
			
			
			
var d = document;
var safari = (navigator.userAgent.toLowerCase().indexOf('safari') != -1) ? true : false;
var gebtn = function(parEl,child) { return parEl.getElementsByTagName(child); };
onload = function() {
    
    var body = gebtn(d,'body')[0];
    body.className = body.className && body.className != '' ? body.className + ' has-js' : 'has-js';
    
    if (!d.getElementById || !d.createTextNode) return;
    var ls = gebtn(d,'label');
    for (var i = 0; i < ls.length; i++) {
        var l = ls[i];
        if (l.className.indexOf('label_') == -1) continue;
        var inp = gebtn(l,'input')[0];
        if (l.className == 'label_check') {
            l.className = (safari && inp.checked == true || inp.checked) ? 'label_check c_on' : 'label_check c_off';
            l.onclick = check_it;
        };
        if (l.className == 'label_radio') {
            l.className = (safari && inp.checked == true || inp.checked) ? 'label_radio r_on' : 'label_radio r_off';
            l.onclick = turn_radio;
        };
    };
};
var check_it = function() {
    var inp = gebtn(this,'input')[0];
    if (this.className == 'label_check c_off' || (!safari && inp.checked)) {
        this.className = 'label_check c_on';
        if (safari) inp.click();
    } else {
        this.className = 'label_check c_off';
        if (safari) inp.click();
    };
};
var turn_radio = function() {
    var inp = gebtn(this,'input')[0];
    if (this.className == 'label_radio r_off' || inp.checked) {
        var ls = gebtn(this.parentNode,'label');
        for (var i = 0; i < ls.length; i++) {
            var l = ls[i];
            if (l.className.indexOf('label_radio') == -1)  continue;
            l.className = 'label_radio r_off';
        };
        this.className = 'label_radio r_on';
        if (safari) inp.click();
    } else {
        this.className = 'label_radio r_off';
        if (safari) inp.click();
    };
};
			
</aui:script>
	</c:otherwise>
</c:choose>

<%if(themeDisplay.isSignedIn()){%>
	<style>
	#p_p_id_58_{
		display: none !important;
	}
	</style>
<%}else{%>
	<style>
		.portlet-borderless-container .portlet-borderless-bar{ display:none !important;}
	</style>
<%}%>

<style>
/*.portlet-borderless-container{
	background: none !important;
}
.control-label, .checkbox{
	color:white !important;
}
.login-container .control-group{
	margin-bottom: 12px !important;
}
#ctvk_column1_0{
	display: none !important;
}
a.taglib-icon:focus .taglib-text, a.taglib-icon:hover .taglib-text{
	text-decoration: none !important;
}

.np-label-remember label{ font-size:13px; font-weight:bold;}*/
</style>