package com.unicef.portlet.patentability;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class PatentabilityTestController {

	private static final String VIEW = "view";
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,
			RenderResponse renderResponse){
		
		return VIEW;
		
	}
}
