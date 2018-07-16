package com.unicef.portlet.ideaScrums;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaScrum;
import com.unicef.service.IdeaService;
import com.unicef.service.SprintService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@RequestMapping("VIEW")
public class IdeaScrumsController {

	@Autowired
	IdeaService ideaService;
	
	@Autowired
	SprintService sprintService;
	
	//@Autowired
	//Idea
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) {
		return "view";
	}
	static final Log log = LogFactoryUtil.getLog(IdeaScrumsController.class);
		
	
	@ResourceMapping(value = "getIdeaScrumList")
	public String getIdeaDataURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse, Model model){
		
		
		List<Idea> ideaScrumLists = ideaService.getListOfPromotedIdeas();
		
		for(Idea idea : ideaScrumLists){
			log.error(idea.getIdeaTitle());
		}
	
		List<Idea> ideaScrumList = new ArrayList<Idea>();
		
		model.addAttribute("sprintService", sprintService);
		model.addAttribute("ideaScrums", ideaScrumLists);
		
		return "idea_scrum_template";
	}

}
