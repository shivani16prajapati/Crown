package com.unicef.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.unicef.portlet.ideascrum.IdeaScrumController;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;

public class MemberNotificationHandler extends BaseUserNotificationHandler {

	public static final String PORTLET_ID = "dockbarnotificationaction_WAR_DockBarCustomNotificationportlet";

	public MemberNotificationHandler() {
		setPortletId(com.unicef.util.MemberNotificationHandler.PORTLET_ID);
	}

	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext) throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());

		int notificationType = jsonObject.getInt("notificationType");

		String notificationBody = StringPool.BLANK;
		_log.error("-------------------");
		_log.error(notificationType);
		_log.error("-------------------");

			switch (notificationType) {

		// Notification has been process for following criteria
		// Subsription Approved
				case 0:
					_log.info("notification has been approved.");
					notificationBody = approvedNotificationRequest(
							userNotificationEvent, jsonObject, serviceContext);
					break;
				// Subscription Rejected
				case 1:
					_log.info("notification has been rejected.");
					notificationBody = rejectNotificationRequest(userNotificationEvent,
							jsonObject, serviceContext);
					break;
				// Subscription Requested
				case 2:
					_log.info("notification has been requested.");
					notificationBody = sendNotificationRequest(userNotificationEvent,
							jsonObject, serviceContext);
					break;
				// already sent request
				case 3:
					_log.info("notification has been requested.");
					notificationBody = alreadyRequested(userNotificationEvent,
							jsonObject, serviceContext);
					break;
				// change body after approved or rejected request
				case 4:
					_log.info("notification body changeds");
					break;
		
				}

				return notificationBody;
		}

	private String changeBodyforApproval(UserNotificationEvent userNotificationEvent,JSONObject jsonObject, ServiceContext serviceContext)throws Exception {

	
		return null;
	}

	private String alreadyRequested(
			UserNotificationEvent userNotificationEvent, JSONObject jsonObject,
			ServiceContext serviceContext) throws Exception {

		String ideaTitle = jsonObject.getString("ideaName");
		String userName = jsonObject.getString("user");
		String title = "<strong>Subscription Request for Idea : " + ideaTitle
				+ " already sent.</strong>";
		String bodyText = "<p>Dear <b>" + userName
				+ "</b>, your request for idea <b>" + ideaTitle
				+ "</b> is in process.</p>";
		StringBundler sb = new StringBundler(5);

		/*
		 * JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
		 * payloadJSON.put("notificationType", 0);
		 */
		sb.append("<div class=\"title\"");
		sb.append(" id=\"notEventId_\"");
		sb.append(">" + title + "</div><div ");
		sb.append("class=\"body\">" + bodyText);
		sb.append("</div>");

		return sb.toString();
	}

	private String approvedNotificationRequest(
			UserNotificationEvent userNotificationEvent, JSONObject jsonObject,
			ServiceContext serviceContext) throws Exception {

		String ideaTitle = jsonObject.getString("ideaName");
		String userName = jsonObject.getString("user");
		String title = "<strong>Subscription Request for Idea : " + ideaTitle
				+ " has been Approved.</strong>";
		String bodyText = "<p>Dear <b>" + userName
				+ "</b>, your request for idea <b>" + ideaTitle
				+ "</b> has been Approved.</p>";
		StringBundler sb = new StringBundler(5);

		/*
		 * JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
		 * payloadJSON.put("notificationType", 0);
		 */
		sb.append("<div class=\"title\"");
		sb.append(" id=\"notEventId_\"");
		sb.append(">" + title + "</div><div ");
		sb.append("class=\"body\">" + bodyText);
		sb.append("</div>");

		// UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(userNotificationEventId);
		// log.info("Notification removed :----------<> " +
		// userNotificationEventId );
		return sb.toString();
	}

	private String rejectNotificationRequest(
			UserNotificationEvent userNotificationEvent, JSONObject jsonObject,
			ServiceContext serviceContext) throws Exception {

		String ideaTitle = jsonObject.getString("ideaName");
		String userName = jsonObject.getString("user");
		String userId = jsonObject.getString("userId");
		String title = "<strong>Subscription Request for Idea : " + ideaTitle
				+ " has been Rejected.</strong>";
		String bodyText = "<p>Dear <b>" + userName
				+ "</b>, your request for idea <b>" + ideaTitle
				+ "</b> has been Rejected. Please request again..</p>";

		/*
		 * JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
		 * payloadJSON.put("notificationType", 1);
		 */
		StringBundler sb = new StringBundler(5);
		sb.append("<div class=\"title\"");
		sb.append(" id=\"notEventId_\"");
		sb.append(">" + title + "</div><div ");
		sb.append("class=\"body\">" + bodyText);
		sb.append("</div>");

		return sb.toString();
	}

	private String sendNotificationRequest(
			UserNotificationEvent userNotificationEvent, JSONObject jsonObject,
			ServiceContext serviceContext) throws Exception {

		long yourCustomEntityId = jsonObject.getLong("yourCustomEntityId");

		String ideaTitle = jsonObject.getString("ideaName");
		String userName = jsonObject.getString("user");
		String ideaId = jsonObject.getString("ideaId");
		String userId = jsonObject.getString("userId");

		String title = "<strong>Subscription Request for Idea:" + ideaTitle
				+ "</strong>";

		String bodyText = "<p><b>" + userName
				+ "</b>, have request to subscribe for idea <b>" + ideaTitle
				+ "</b></p>";

		LiferayPortletResponse liferayPortletResponse = serviceContext
				.getLiferayPortletResponse();

		PortletURL confirmURL = liferayPortletResponse
				.createActionURL("Idea_WAR_Unicefportlet");
		confirmURL.setParameter("action", "approveSubscription");
		confirmURL.setParameter(ActionRequest.ACTION_NAME,"approveSubscription");
		confirmURL.setParameter("redirect", serviceContext.getLayoutFullURL());
		confirmURL.setParameter("ideaId", ideaId);
		confirmURL.setParameter("userId", userId);
		confirmURL.setParameter("yourCustomEntityId",
				String.valueOf(yourCustomEntityId));
		confirmURL.setParameter("userNotificationEventId", String
				.valueOf(userNotificationEvent.getUserNotificationEventId()));
		// confirmURL.setWindowState(WindowState.NORMAL);

		// liferayPortletResponse.createR
		PortletURL ignoreURL = liferayPortletResponse
				.createActionURL("Idea_WAR_Unicefportlet");
		ignoreURL.setParameter("action", "rejectSubscription");
		ignoreURL.setParameter(ActionRequest.ACTION_NAME, "rejectSubscription");
		ignoreURL.setParameter("ideaId", ideaId);
		ignoreURL.setParameter("userId", userId);
		ignoreURL.setParameter("redirect", serviceContext.getLayoutFullURL());
		ignoreURL.setParameter("yourCustomEntityId",
				String.valueOf(yourCustomEntityId));
		ignoreURL.setParameter("userNotificationEventId", String
				.valueOf(userNotificationEvent.getUserNotificationEventId()));
		// ignoreURL.setWindowState(WindowState.NORMAL);

		String body = StringUtil.replace(
				getBodyTemplate(String.valueOf(userNotificationEvent
						.getUserNotificationEventId())),
				new String[] { "[$CONFIRM$]", "[$CONFIRM_URL$]", "[$IGNORE$]",
						"[$IGNORE_URL$]", "[$TITLE$]", "[$BODY_TEXT$]" },
				new String[] { serviceContext.translate("approve"),
						confirmURL.toString(),
						serviceContext.translate("reject"),
						ignoreURL.toString(), title, bodyText });

		return body;
	}

	@Override
	protected String getLink(UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext) throws Exception {

		JSONObject jsonObject = JSONFactoryUtil
				.createJSONObject(userNotificationEvent.getPayload());

		long yourCustomEntityId = jsonObject.getLong("yourCustomEntityId");

		LiferayPortletResponse liferayPortletResponse = serviceContext
				.getLiferayPortletResponse();

		PortletURL viewURL = liferayPortletResponse
				.createActionURL("dockbarnotificationaction_WAR_DockBarCustomNotificationportlet");
		// viewURL.setParameter(ActionRequest.ACTION_NAME, "showDetails");
		viewURL.setParameter("redirect", serviceContext.getLayoutFullURL());
		// viewURL.setParameter("yourCustomEntityId",
		// String.valueOf(yourCustomEntityId));
		// viewURL.setParameter("userNotificationEventId",
		// String.valueOf(userNotificationEvent.getUserNotificationEventId()));
		// viewURL.setWindowState(WindowState.NORMAL);

		return serviceContext.getLayoutFullURL(); // viewURL.toString();
	}

	/*
	 * protected String getBodyTemplate(String eventId) throws Exception {
	 * StringBundler sb = new StringBundler(5);
	 * sb.append("<div class=\"title\"");
	 * sb.append("id=\"notEventId_"+eventId+"\"");
	 * sb.append(">[$TITLE$]</div><div ");
	 * sb.append("class=\"body\">[$BODY_TEXT$]");
	 * sb.append("<a class=\"btn btn-action ");
	 * sb.append("btn-success\" href=\"[$CONFIRM_URL$]\">[$CONFIRM$]</a>");
	 * sb.append("<a class=\"btn btn-action btn-warning\" href=\"");
	 * sb.append("[$IGNORE_URL$]\">[$IGNORE$]</a></div>"); return sb.toString();
	 * }
	 */

	public static String getBodyTemplate(String eventId) throws Exception {

		StringBundler sb = new StringBundler(5);

		_log.error("body template");

		sb.append("<div class=\"title\"");
		sb.append(" id=\"notEventId_" + eventId + "\"");
		sb.append(">[$TITLE$]</div><div ");
		sb.append("class=\"body\">[$BODY_TEXT$]");
		sb.append("<a class=\"btn btn-action btn-success\" href=\"[$CONFIRM_URL$]\" onClick=\"window.location.reload()\">[$CONFIRM$]</a>");
		// sb.append("btn-success\" href=\"[$CONFIRM_URL$]\">[$CONFIRM$]</a>");
		sb.append("<a class=\"btn btn-action btn-warning\" href=\"[$IGNORE_URL$]\" onClick=\"window.location.reload()\">[$IGNORE$]</a></div>");
		// sb.append("[$IGNORE_URL$]\">[$IGNORE$]</a></div>");
		// sb.append("<input type=\"button\" class=\"btn btn-action btn-success\" value=\"[$CONFIRM$]\" onclick=\"approveNotification('[$CONFIRM_URL$]','"+eventId+"');\" />");
		// sb.append("<input type=\"button\" class=\"btn btn-action btn-warning\" value=\"[$IGNORE$]\" onclick=\"rejectNotification('[$IGNORE_URL$]','"+eventId+"');\" />");
		sb.append("</div>");

		return sb.toString();

	}

	private static Log _log = LogFactoryUtil
			.getLog(MemberNotificationHandler.class);
}
