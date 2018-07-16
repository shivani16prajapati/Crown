package com.unicef.portlet.comingsoon;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class ComingSoonController {
	
	private static final String VIEW = "view";
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,
			RenderResponse renderResponse){
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String urlName = themeDisplay.getLayout().getFriendlyURL();
		if(urlName.equalsIgnoreCase("/launch-a-challenge")){
			model.addAttribute("pageName", "launchCahllenge");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/knowledge-repository")){
			model.addAttribute("pageName", "knowledgeRepository");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/usthink-blog")){
			model.addAttribute("pageName", "usThinkBlog");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/patentability-test")){
			model.addAttribute("pageName", "patentablityTest");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/training")){
			model.addAttribute("pageName", "training");
			return VIEW;
		} else if(urlName.equalsIgnoreCase("/innomail")){
			model.addAttribute("pageName", "innoMail");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/connections")){
			model.addAttribute("pageName", "connections");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/command-center")){
			model.addAttribute("pageName", "commandCenter");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/pivot-manager")){
			model.addAttribute("pageName", "pivotManager");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/about-agile-innovation")){
			model.addAttribute("pageName", "agileInnovation");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/self-assesment")){
			model.addAttribute("pageName", "selfAssesment");
			return VIEW;
		}else if(urlName.equalsIgnoreCase("/pipeline-manager")){
			model.addAttribute("pageName", "pipelineManager");
			return VIEW;
		}
		return VIEW;
	}
}
