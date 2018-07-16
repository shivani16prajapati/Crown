package com.unicef.portlet.breadcrumb;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.unicef.domain.Idea;
import com.unicef.domain.Solution;
import com.unicef.service.IdeaService;
import com.unicef.service.SolutionService;

import java.util.Locale;

import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class UnicefBreadcrumbController {

	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private SolutionService solutionService;
	
	Log log = LogFactoryUtil.getLog(UnicefBreadcrumbController.class);
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException, SystemException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletSession session = renderRequest.getPortletSession();
		
		try{
			/** get idea/solution detail from session **/
			
			String sessionValue = (String) session.getAttribute("solutionViewId", PortletSession.APPLICATION_SCOPE);
			
			if(Validator.isNotNull(sessionValue)){
				long ideaSolutionId = Long.valueOf(sessionValue);
				String moduleTitle = (String) session.getAttribute("moduleTitle", PortletSession.APPLICATION_SCOPE);
				String funtionTitle = (String) session.getAttribute("funtionTitle", PortletSession.APPLICATION_SCOPE);

				/** end **/

				String scrum = (String) session.getAttribute("scrum", PortletSession.APPLICATION_SCOPE);
				String pageURL = StringPool.BLANK;
				if(Validator.isNotNull(scrum)){
					if(scrum.equalsIgnoreCase("solutionScrum")){
						pageURL = "/group/guest/solutions-generator";
					}else if(scrum.equalsIgnoreCase("ideaScrum")){
						pageURL = "/group/guest/idea-machine";
					}
				}else{
					pageURL = "/group/guest" + themeDisplay.getLayout().getFriendlyURL();
				}
				
				Idea idea = null;Solution solution = null;
				if(moduleTitle.contains("Idea")){
					idea = ideaService.find(ideaSolutionId);
				}else{
					solution = solutionService.find(ideaSolutionId);
				}
				
				if(idea != null){
					String status = idea.getStatus() == 1 ? "Backlog" : "Active IdeaScrum";
					model.addAttribute("status", status);
				}
				if(solution != null){
					String status = solution.getStatus() == 1 ? "Backlog" : "Active SolutionScrum";
					model.addAttribute("status", status);
				}

				model.addAttribute("currentModuleURL", themeDisplay.getURLCurrent());
				model.addAttribute("moduleName", moduleTitle);
				model.addAttribute("pageURL", pageURL);

				
				model.addAttribute("title", funtionTitle);
				model.addAttribute("currentModuleURL", themeDisplay.getURLCurrent());
				model.addAttribute("sessionValue", sessionValue);
			}else{
				model.addAttribute("sessionValue", "");
			}
		}finally{
			session.setAttribute("solutionViewId","", PortletSession.APPLICATION_SCOPE);
			session.setAttribute("moduleTitle","", PortletSession.APPLICATION_SCOPE);
			session.setAttribute("funtionTitle","", PortletSession.APPLICATION_SCOPE);
			session.setAttribute("scrum","", PortletSession.APPLICATION_SCOPE);
		}
		return "view";
	}
}
