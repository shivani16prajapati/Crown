package com.unicef.pdf.form.input.controller;

import com.unicef.pdf.form.input.service.InputFormService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PDFFormInputController {

	@Autowired
	private InputFormService inputFormService;
	
	@RequestMapping("submitData")
	public String redirectIndexPage(ModelMap modelMap, final HttpServletRequest httpServletRequest) throws Exception {
		modelMap.addAttribute("inputFormService", inputFormService);
		return "index";
	}
	
}
