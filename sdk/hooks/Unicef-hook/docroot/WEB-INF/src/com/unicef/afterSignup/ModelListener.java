package com.unicef.afterSignup;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupModel;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.SiteConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.ui.MySitesTag;

import java.util.List;

import javax.portlet.RenderRequest;

import org.omg.PortableInterceptor.USER_EXCEPTION;

public class ModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {
		final Log log = LogFactoryUtil.getLog(ModelListener.class);
		log.error("on aftercreate method call");

		try {
			String webId = "liferay.com"; // PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID)
			Company company;

			company = CompanyLocalServiceUtil.getCompanyByWebId(webId);
			long CompanyId = (long) company.getCompanyId();

			List<Group> groups = GroupLocalServiceUtil.search(CompanyId, null,
					null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			long userid = user.getUserId();
			long groupid[] = new long[groups.size() + 1];
			int i = 0;
			for (Group group : groups) {
				groupid[i] = group.getGroupId();
				log.error(i + " " + userid + " " + CompanyId + " " + groupid[i]
						+ " " + group.getGroupId() + " ");
				try {
					if ((group.getType() == 1)
							&& (!(group.getName()).equalsIgnoreCase("guest"))) {
						UserLocalServiceUtil.addGroupUser(group.getGroupId(),
								userid);
						log.error(userid + "user asign to group: " + groupid[i]);
						i++;
					}
				} catch (Exception e) {
					log.error("error is " + e);
				}
			}
			} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onAfterUpdate(User user) throws ModelListenerException {
		System.out.println(user.getFullName());
	}

}
