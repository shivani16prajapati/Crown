package com.unicef.portlet.ideaHotness;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.unicef.domain.IdeaScrum;
import com.unicef.service.IdeaService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class IdeaHotnessController {

	@Autowired
	private IdeaService ideaService;
	
	@RenderMapping
	public String view(RenderRequest renderRequest,RenderResponse renderResponse, Model model) throws PortalException{
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
		long ideaId = 0;
		double ideaHotness = 0.0;
		try{
			if(ideaScrum != null){
				ideaId =  ideaScrum.getIdeaId();
				ideaHotness = ideaService.find(ideaId).getHotWeight();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("hotness",ideaHotness * 100);
		return "view";
	}
}
