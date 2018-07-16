package com.unicef.answer.kudometer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.unicef.domain.IdeaScrum;
import com.unicef.service.IdeaService;
import com.unicef.service.LikeService;
import com.unicef.service.SolutionLikeService;
import com.unicef.util.SolutionUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class AnswerKudoMeter {

	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private IdeaService solutionService;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private SolutionLikeService solutionLikeService;
	
	Log log = LogFactoryUtil.getLog(AnswerKudoMeter.class);
	
	
	@RenderMapping
	public String view(RenderRequest renderRequest,RenderResponse renderResponse, Model model) throws PortalException{
	
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		IdeaScrum ideaScrum = solutionService.getIdeaScrum(themeDisplay.getScopeGroupId());
		long solutionId = 0;
		long totalSolutionLikeCount = 0;
		long weeklySolutionLikeCount = 0;
		try{
			if(ideaScrum != null){
				solutionId =  ideaScrum.getSolutionId();
				totalSolutionLikeCount = solutionLikeService.getCountOfSolutionLike(solutionId);
				weeklySolutionLikeCount = solutionLikeService.getWeeklyCountOfSolutionLike(solutionId);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		double kudoCount = 0;
		try{
			kudoCount = SolutionUtil.getKudoCount(solutionId);
			kudoCount = kudoCount *100;
			if(kudoCount >= 100){
				kudoCount = 100;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		model.addAttribute("kutoDisplayCount",kudoCount);
		model.addAttribute("weeklySolutionLikeCount", weeklySolutionLikeCount);
		model.addAttribute("totalKudos", totalSolutionLikeCount);
		return "view";
	}
}
