package com.unicef.portlet.kudo.meter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.unicef.domain.IdeaScrum;
import com.unicef.service.IdeaService;
import com.unicef.service.LikeService;
import com.unicef.util.IdeaUtil;

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
public class KudoMeterController {

	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private LikeService likeService;
	
	Log log = LogFactoryUtil.getLog(KudoMeterController.class);
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException{
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
		long ideaId = 0;
		long totalIdeaLikeCount = 0;
		long weeklyIdeaLikeCount = 0;
		try{
			if(ideaScrum != null){
				ideaId =  ideaScrum.getIdeaId();
				totalIdeaLikeCount = likeService.getCountOfIdeaLike(ideaId);
				weeklyIdeaLikeCount = likeService.getWeeklyCountOfIdeaLike(ideaId);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		double kudoCount = 0;
		try{
			kudoCount = IdeaUtil.getKudoCount(ideaId);
			kudoCount = kudoCount *100;
			if(kudoCount >= 100){
				kudoCount = 100;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		model.addAttribute("kutoDisplayCount",kudoCount);
		model.addAttribute("weeklyIdeaLikeCount", weeklyIdeaLikeCount);
		model.addAttribute("totalKudos", totalIdeaLikeCount);
		return "view";
	}
}
