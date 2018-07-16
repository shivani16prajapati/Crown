package com.unicef.url.filter;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This filte apply on all render request of page. If reuest url is not of any
 * page, it check in product, manufacurer, category, promotion, eveluation and
 * if url is found in these table, request is forworded on that detail page.
 * 
 * @author Divyang Patel
 */
public class UnicefURLMappingFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(UnicefURLMappingFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			try {
				if (!getRedirect((HttpServletRequest) request,
						(HttpServletResponse) response)) {

					chain.doFilter(request, response);
				}
			} catch (Exception e) {
				logger.info("Unicef URL filter");
			}
		}
	}

	@Override
	public void destroy() {

	}

	protected boolean getRedirect(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Check render request
		if(ParamUtil.getInteger(request, "p_p_lifecycle", 0) != 0) {
			return false;
		}
		String path = request.getPathInfo();
		Map params = request.getParameterMap();
		if (Validator.isNull(path) || path.length() == 1
				|| (path.charAt(0) != CharPool.SLASH)
				|| path.contains("/portal/")
				|| path.contains("/control_panel/")) {
			return false;
		}

		// Group friendly URL

		String friendlyURL = null;
		String siteName = StringPool.BLANK;

		int pos = path.indexOf(CharPool.SLASH, 1);

		if (pos != -1) {
			friendlyURL = path.substring(0, pos);
			siteName = friendlyURL;
		} else if (path.length() > 1) {
			friendlyURL = path;
		}

		if (Validator.isNull(friendlyURL)) {
			return false;
		}
		long companyId = (Long) request.getAttribute(COMPANY_ID);

		Group group = GroupLocalServiceUtil.fetchFriendlyURLGroup(companyId,
				friendlyURL);

		if (group == null) {
			String screenName = friendlyURL.substring(1);

			if (!Validator.isNumber(screenName)) {
				User user = UserLocalServiceUtil.fetchUserByScreenName(
						companyId, screenName);

				if (user != null) {
					group = user.getGroup();
				}/* else if (_log.isWarnEnabled()) {
					_log.warn("No user exists with friendly URL " + screenName);
				}*/
			} else {
				long groupId = GetterUtil.getLong(screenName);

				group = GroupLocalServiceUtil.fetchGroup(groupId);

				if (group == null) {
					if (_log.isDebugEnabled()) {
						_log.debug("No group exists with friendly URL "
								+ groupId
								+ ". Try fetching by screen name instead.");
					}

					User user = UserLocalServiceUtil.fetchUserByScreenName(
							companyId, screenName);

					if (user != null) {
						group = user.getGroup();
					} else if (_log.isWarnEnabled()) {
						_log.warn("No user or group exists with friendly URL "
								+ groupId);
					}
				}
			}
		}

		if (group == null) {
			return false;
		}

		// Layout friendly URL

		friendlyURL = null;

		if ((pos != -1) && ((pos + 1) != path.length())) {
			friendlyURL = path.substring(pos);
		}

		if (Validator.isNull(friendlyURL)) {
			return false;
		}

		Map<String, Object> requestContext = new HashMap<String, Object>();

		requestContext.put("request", request);

		ServiceContext serviceContext = ServiceContextThreadLocal
				.getServiceContext();

		if (serviceContext == null) {
			serviceContext = ServiceContextFactory.getInstance(request);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}
		String urlTitle = friendlyURL;
		if(urlTitle.startsWith("/")){
			urlTitle = urlTitle.substring(1);
		}
		if (friendlyURL.split("/").length > 2) {
			return false;
		}
		if (Validator.isNotNull(friendlyURL)) {
			
			try {
				PortalUtil.getLayoutFriendlyURLComposite(group.getGroupId(),
						_private, friendlyURL, params, requestContext);
				return false;
			} catch (NoSuchLayoutException e) {
				HttpSession session = PortalSessionThreadLocal.getHttpSession();

				if (session == null) {
					session = request.getSession();
				}

				ServletContext servletContext = session.getServletContext();
				
				//get redirect URL if layout not found
				String redirect = getReditrctURL(urlTitle, companyId);
				
				if (Validator.isNull(redirect)) {
					return false;
				}
				
				//forward to redirect URL
				RequestDispatcher requestDispatcher = servletContext
						.getRequestDispatcher(URL_PREFIX + group.getFriendlyURL() + redirect);

				if (requestDispatcher != null) {
					requestDispatcher.forward(request, response);
				}
				
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		return false;
	}

  private static String getReditrctURL(String urlTitle, long companyId){
	  
	  String redirectURL = StringPool.BLANK;
	  if(urlTitle.contains(StringPool.DASH)){
		  
		  String enntryId = urlTitle.split(StringPool.DASH)[0];
		  
		  long fileEntryId = 0;
		  
		  if(Validator.isNotNull(enntryId)){
			 fileEntryId = Long.parseLong(enntryId);
		  }
		  
		  redirectURL = createDocumentRedirectURL(fileEntryId);
	  }else{
		  redirectURL = DEFAULT_URL;
	  }
	   
      return redirectURL;
  }
	

	private static String createDocumentRedirectURL(long fileEntryId) {
		
		String redirectURL = DOCUMENT_CONTEXT_PATH + StringPool.QUESTION + PORTLET_ID + StringPool.AMPERSAND
							+ PORTLET_LIFECYCLE + StringPool.AMPERSAND + PORTLET_STATE + StringPool.AMPERSAND 
							+ PORTLET_MODE + StringPool.AMPERSAND + PORTLET_ACTION
							+ StringPool.AMPERSAND + PORTLET_FILE_ENTRY_ID + fileEntryId;
	
		return redirectURL;
	}

	private static final String COMPANY_ID = "COMPANY_ID";
	private boolean _private = true;
	private static final String DEFAULT_URL = "/home";
	private static final String URL_PREFIX = "/group";
	private static final String DOCUMENT_CONTEXT_PATH = "/polio-knowledge-repository";
	private static final String PORTLET_ID = "p_p_id=20";
	private static final String PORTLET_LIFECYCLE = "p_p_lifecycle=0";
	private static final String PORTLET_STATE = "p_p_state=maximized";
	private static final String PORTLET_MODE = "p_p_mode=view";
	private static final String PORTLET_ACTION = "_20_struts_action=/document_library/view_file_entry";
	private static final String PORTLET_FILE_ENTRY_ID = "_20_fileEntryId=";
	private static Log _log = LogFactoryUtil.getLog(UnicefURLMappingFilter.class);
}
