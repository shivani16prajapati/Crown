package com.unicef.portlet.dashboard;

import java.util.Date;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller(value = "DashboardController")
@RequestMapping("VIEW")
public class DashboardController {
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request,RenderResponse response,Model model){
		model.addAttribute("currentDate", new Date());
		return "home";
	}
}
