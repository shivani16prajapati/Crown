package com.unicef.portal.events;
import static com.unicef.constants.UnicefConstants.*;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnicefLandingPageAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("request.getParameter('redirect'):"+request.getParameter("redirect"));
		try {
		if (Validator.isNotNull(DEFAULT_UNICEF_PATH) && request.getRequestURI() == DEFAULT_UNICEF_PATH) { 
			LastPath lastPath = new LastPath(StringPool.BLANK, DEFAULT_UNICEF_PATH, new HashMap<String, String[]>());
			HttpSession session = request.getSession();
			session.setAttribute(WebKeys.LAST_PATH, lastPath);
		} 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
