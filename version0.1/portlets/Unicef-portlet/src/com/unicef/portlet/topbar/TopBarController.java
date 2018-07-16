package com.unicef.portlet.topbar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * <p>
 * This class is a {@link Controller} of Top bar portlet. All portlet requests
 * of Top bar portlet will be handle by this class.
 * </p>
 * 
 * @author Divyang Patel
 */
@Controller
@RequestMapping("VIEW")
public class TopBarController {


	private static final String VIEW_JSP = "top-bar";

	private static final Logger logger = LoggerFactory
			.getLogger(TopBarController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 * @throws SystemException 
	 * @throws PortalException 
	 * @see Locale
	 * @see Model
	 */
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest, RenderResponse renderResponse) throws PortalException, SystemException {
		logger.info("Topbar portlet view mode.");
		String view = ParamUtil.getString(renderRequest, "view", StringPool.BLANK);
		String keyword = ParamUtil.getString(renderRequest, "keyword", StringPool.BLANK);
		if(Validator.isNotNull(view) && view.equalsIgnoreCase("search-page")){
			return view;
		}
		return VIEW_JSP;
	}
	
	@ActionMapping(params = "action=search")
	public void searchALL(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException{
		String keywords = ParamUtil.getString(actionRequest, "searchAll",StringPool.BLANK);
		String redirectURL = "/web/guest/search?from=topbar&keywords="+keywords;
		actionResponse.sendRedirect(redirectURL);
	}
}