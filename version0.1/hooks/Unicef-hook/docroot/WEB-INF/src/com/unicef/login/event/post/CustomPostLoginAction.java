package com.unicef.login.event.post;
import static com.unicef.constants.UnicefConstants.*;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;








import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;


public class CustomPostLoginAction extends Action{

	
	
	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ActionException {
		
		System.out.println("Invoking this line after Login");
		HttpSession session = request.getSession();
		
		try {
			User user= PortalUtil.getUser(request);
			String status = UserLocalServiceUtil.getUser(user.getUserId()).getOpenId();
			if(status.equalsIgnoreCase("1")){
				LastPath lastPath  = new LastPath(StringPool.BLANK, USER_DEFAULT_UNICEF_PATH, new HashMap<String, String[]>());
				session.setAttribute(WebKeys.LAST_PATH, lastPath);
			}
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		}
		
	}

}
