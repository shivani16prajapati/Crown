package com.unicef.portlet.idea;

import java.io.IOException;

import javax.portlet.ActionResponse;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

/**
 * <p>
 * This class is a Configuration-Controller for Idea portlet. All requests
 * from preference page will be handle by this class.
 * </p>
 * 
 * @author Divyan Patel
 */
@Controller
@RequestMapping("edit")
public class ConfigurationController {
	private static final Logger log = LoggerFactory
			.getLogger(ConfigurationController.class);

	/**
	 * <p>
	 * This method will be handle action request of Idea Portlet Preference
	 * page.
	 * </p>
	 * 
	 * @param newTHRESHOLD
	 * @param prefs
	 * @param response
	 * @throws ReadOnlyException
	 * @throws ValidatorException
	 * @throws IOException
	 * @throws PortletModeException
	 */
	@ActionMapping(params = "action=save")
	public void action(@RequestParam("newTHRESHOLD")  String newTHRESHOLD,
			@RequestParam("hotTHRESHOLD")  String hotTHRESHOLD,
			@RequestParam("sprintNdays")  String sprintNdays,
			PortletPreferences prefs, ActionResponse response)
			throws ReadOnlyException, ValidatorException, IOException,
			PortletModeException {

		log.info("action");

			if (newTHRESHOLD != null && !newTHRESHOLD.equals("")) {
				prefs.reset("newTHRESHOLD");
				prefs.setValue("newTHRESHOLD", newTHRESHOLD);
			}
			prefs.setValue("newTHRESHOLD", newTHRESHOLD);
			
			if (hotTHRESHOLD != null && !hotTHRESHOLD.equals("")) {
				prefs.reset("hotTHRESHOLD");
				prefs.setValue("hotTHRESHOLD", hotTHRESHOLD);
			}
			prefs.setValue("hotTHRESHOLD", hotTHRESHOLD);
			
			if (sprintNdays != null && !sprintNdays.equals("")) {
				prefs.reset("sprintNdays");
				prefs.setValue("sprintNdays", sprintNdays);
			}
			prefs.setValue("sprintNdays", sprintNdays);
		prefs.store();
	}

	/**
	 * <p>
	 * This method will be handle action render of Idea Portlet Preference
	 * page.
	 * </p>
	 * 
	 * 
	 * @param model
	 * @param prefs
	 * @return
	 */
	@RequestMapping
	public String render(Model model, PortletPreferences prefs) {
		log.info("render");

		model.addAttribute("newTHRESHOLD", prefs.getValue("newTHRESHOLD", ""));
		model.addAttribute("hotTHRESHOLD", prefs.getValue("hotTHRESHOLD", ""));
		model.addAttribute("sprintNdays", prefs.getValue("sprintNdays", ""));
		return "configuration";
	}
}
