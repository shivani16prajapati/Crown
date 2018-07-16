package com.tes.ter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserService;
import com.unicef.util.IdeaScrumDiscussionTitleDescription;

import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	
		String body = StringUtil.replace(getBodyTemplate(), new String[] {
			"[$CONFIRM$]", "[$CONFIRM_URL$]", "[$IGNORE$]",
			"[$IGNORE_URL$]", "[$TITLE$]", "[$BODY_TEXT$]" }, new String[] {
			"APROVE ACTION", "WWW.CONFIRMURL.COM",
			"REJECT ACTION", "WWW.REJECTURL.COM",
			"TITLE", "BODY TEXT BODY" });
	
		System.out.println(body);
		
	}
	
	public static String getBodyTemplate() throws Exception {
		StringBundler sb = new StringBundler(5);
		sb.append("<div class=\"title\"");
		sb.append(">[$TITLE$]</div><div ");
		sb.append("class=\"body\">[$BODY_TEXT$]");
		sb.append("<input type=\"button\" class=\"btn btn-action btn-success\" value=\"[$CONFIRM$]\" onclick=\"fireNotificationAction('[$CONFIRM_URL$]');\" />");
		sb.append("<input type=\"button\" class=\"btn btn-action btn-warning\" value=\"[$IGNORE$]\" onclick=\"fireNotificationAction('[$IGNORE_URL$]');\" />");
		sb.append("</div>");
		return sb.toString();
	}
	
}	