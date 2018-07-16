package com.unicef.portlet.thanks;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.domain.IdeaScrum;
import com.unicef.service.IdeaCommentService;
import com.unicef.service.IdeaCommentVoteService;
import com.unicef.service.IdeaService;
import com.unicef.util.IdeaUtil;

import java.util.ArrayList;
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
public class ThankYouMeterController {
	
	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private IdeaCommentVoteService commentVoteService;
	
	@Autowired
	private IdeaCommentService commentService;
	private static final String VIEW = "view";
	
	private static final Log log = LogFactoryUtil.getLog(ThankYouMeterController.class);
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,
			RenderResponse renderResponse){
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long ideaThanks = 0L;
		long weekComments = 0L;
		long remainingThanks = 0L;
		long ideaCreator = 0L;
		List<IdeaCommentVote> totlaVotes = new ArrayList<IdeaCommentVote>();
		try{
			totlaVotes = commentVoteService.findAll();
			IdeaScrum ideaScrum = ideaService.getIdeaScrum(themeDisplay.getScopeGroupId());
			if (Validator.isNotNull(ideaScrum)) {
				long ideaId = ideaScrum.getIdeaId();
				ideaThanks = IdeaUtil.getThanksCount(ideaId);
				weekComments = commentService.getIdeaCommentCount(ideaId);
				weekComments = weekComments * 2;
				remainingThanks = weekComments - ideaThanks;
				Idea idea = ideaService.find(ideaId);
				ideaCreator = idea.getCoInventorId();
				totlaVotes = commentVoteService.getThanksListByIdeaId(ideaId);
			}
		}catch(Exception e){
			log.error("Error in Thank You Meter Controller::"+e.getMessage());
		}
		model.addAttribute("totalThanks", totlaVotes.size());
		model.addAttribute("ideaCreator", ideaCreator);
		model.addAttribute("remainigThanks", remainingThanks);
		model.addAttribute("ideaThanks", ideaThanks);
		return VIEW;
	}
}
