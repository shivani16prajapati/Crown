package com.unicef.portlet.hotIdeas;

import com.liferay.portal.kernel.exception.PortalException;
import com.unicef.domain.Idea;
import com.unicef.service.IdeaService;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class HotIdeasController {

	@Autowired
	private IdeaService ideaService;
	
	@RenderMapping
	public String view(RenderRequest renderRequest,RenderResponse renderResponse, Model model) throws PortalException{
		List<Idea>hotIdeaList = ideaService.getLatestHotIdeas();
		model.addAttribute("hotIdeas", hotIdeaList);
		return "view";
	}
}
