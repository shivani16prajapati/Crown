package com.ust.portlet.themesearch;

import java.util.Locale;

import javax.portlet.RenderRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class USTThemeSearchController {

	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest) {
		return "view";
	}
}
