package com.unicef.portlet.my.innovation;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaScrum;
import com.unicef.service.IdeaService;
import com.unicef.util.IdeaUtil;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;


@Controller
@RequestMapping("VIEW")
public class InnovationController {
	
	@Autowired
	private IdeaService ideaService;
	
	private static final String INNOVATION = "innovation";
	
	private static final Log log = LogFactoryUtil.getLog(InnovationController.class);
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,
			RenderResponse renderResponse){
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<Idea> userIdeas =null;
		long thanksPoint = 0L;
		double kudosCount = 0;
		
		try{
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			long ideaId = ideaScrum.getIdeaId();
			thanksPoint = IdeaUtil.getThanksCount(ideaId);
			DecimalFormat decimalFormat = new DecimalFormat("#.#");
			kudosCount = IdeaUtil.getKudoCount(ideaId);
			userIdeas = ideaService.getListOfIdeaByCurrentUser(themeDisplay.getUserId());
		}catch(Exception e){
			log.error("Error in Innovation Controller"+e.getMessage());
		}
		model.addAttribute("kudosCount", kudosCount);
		model.addAttribute("thanksPoint", thanksPoint);
		model.addAttribute("ideas", userIdeas);
		return INNOVATION;
	}

}
