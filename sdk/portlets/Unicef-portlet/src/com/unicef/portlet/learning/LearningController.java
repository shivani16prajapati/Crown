package com.unicef.portlet.learning;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaHistory;
import com.unicef.service.IdeaHistoryService;
import com.unicef.service.IdeaService;
import com.unicef.util.IdeaUtil;

import java.util.List;
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
public class LearningController {

	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private IdeaHistoryService ideaHistoryService;
	
	
	private static final Log log = LogFactoryUtil.getLog(LearningController.class);
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,
			RenderResponse renderResponse){
		
		long ideaId = 0;
		PortletSession session1 = renderRequest.getPortletSession();
		String sessionValue = (String) session1.getAttribute("ideaSessionId", PortletSession.APPLICATION_SCOPE);
		
		ideaId = ParamUtil.getLong(renderRequest, "ideaId");
		if(ideaId == 0){
			ideaId = Long.valueOf(sessionValue);
		}
		try{
			
			Idea idea = ideaService.find(ideaId);
			String tabNames = "The Lession";
			String tabValues = "the-lession";
			
			for(int i=1;i<=7;i++){
				tabNames = tabNames + StringPool.COMMA + "Step "+i;
				tabValues = tabValues + StringPool.COMMA + "step-"+i;
			}
			tabNames = tabNames +  StringPool.COMMA + "Review";
			tabValues = tabValues + StringPool.COMMA + "review";
			
			tabNames = tabNames + StringPool.COMMA + "Feedback";
			tabValues = tabValues + StringPool.COMMA +"feedback";
			
			String tab = ParamUtil.getString(renderRequest, "tab","the-lession");
			if(tab.equals("the-lession")){
				theIdeaLession(locale,model,renderRequest,renderResponse,idea);
			}else if(tab.equals("feedback")){
				feedBackView(locale,model,renderRequest,renderResponse,idea);
			}else if(tab.equals("review")){
				reviewJsp(locale,model,renderRequest,renderResponse,idea);
			}else{
				theStepView(locale,model,renderRequest,renderResponse,idea,tab);
			}
			
			String viewJsp = StringPool.BLANK;
			if(tab.contains("step")){
				viewJsp = "step";
			}else{
				viewJsp = tab;
			}
			PortletSession session = renderRequest.getPortletSession();
			session.setAttribute("ideaSessionId",String.valueOf(idea.getIdeaId()), PortletSession.APPLICATION_SCOPE);
			model.addAttribute("viewJsp", viewJsp);
			model.addAttribute("tabNames", tabNames);
			model.addAttribute("tabValues", tabValues);
		}catch(Exception e){
			log.info("Error in Getting Structure Learning"+e.getMessage());
		}
	
		return "view";
	}
	private void reviewJsp(Locale locale, Model model,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Idea idea) {
		String reviewJsp = "view";
		model.addAttribute("ideaId", idea.getIdeaId());
		model.addAttribute("idea", idea);
		model.addAttribute("reviewJsp", reviewJsp);
	}
	private void feedBackView(Locale locale, Model model,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Idea idea) {
		String feedBackViewJsp = "view";
		model.addAttribute("ideaId", idea.getIdeaId());
		model.addAttribute("idea", idea);
		model.addAttribute("feedBackViewJsp", feedBackViewJsp);

	}
	private void theStepView(Locale locale, Model model,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Idea idea, String tab) {
		
		/*String order = tab.split("-")[1];*/
		
		String stepViewJsp = "view";
		model.addAttribute("idea", idea);
		model.addAttribute("ideaId",idea.getIdeaId());
		model.addAttribute("stepViewJsp", stepViewJsp);
		
	}
	private void theIdeaLession(Locale locale, Model model,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Idea idea) {
		
		String userAvatarURL = StringPool.BLANK;
		List<IdeaHistory> ideaHistories = null;
		String theLessionViewJsp = "view";	
		try{
			userAvatarURL = IdeaUtil.getAvatarURL(idea.getCoInventorId());
			ideaHistories = ideaHistoryService.getIdeaHistoryList(idea.getIdeaId());
		}catch(Exception e){
			log.info("Error in Getting User Image in Learing Controller"+e.getMessage());
		}
		
		model.addAttribute("historyListSize", ideaHistories.size());
		model.addAttribute("ideaHistories", ideaHistories);
		model.addAttribute("userAvtar", userAvatarURL);
		model.addAttribute("idea", idea);
		model.addAttribute("theLessionViewJsp ", theLessionViewJsp );
	}
	
	
}
