package com.unicef.portlet.SolutionHotness;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.unicef.domain.IdeaScrum;
import com.unicef.service.SolutionService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class SolutionHotnessController {
	
	@Autowired
	private SolutionService solutionService;
	
	@RenderMapping
	public String view(RenderRequest renderRequest,RenderResponse renderResponse, Model model) throws PortalException{
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		IdeaScrum ideaScrum = solutionService.getIdeaScrum(themeDisplay.getScopeGroupId());
		long solutionId = 0;
		double solutionHotness = 0.0;
		try{
			if(ideaScrum != null){
				solutionId =  ideaScrum.getSolutionId();
				solutionHotness = solutionService.find(solutionId).getHotWeight();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("hotness",solutionHotness * 100);
		return "view";
	}
	
}
