package com.unicef.rest.service;

import com.liferay.portal.kernel.json.JSONObject;
import com.unicef.util.IdeaUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;



@Path("/solution")
@Produces(MediaType.APPLICATION_JSON)
public class SolutionRS {
	   
	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/submitQuickSolution")
	public String submitQuickIdea(@QueryParam("type") String type,
			@QueryParam("title") String title,
			@QueryParam("description") String description) {

		JSONObject responseJSON = IdeaUtil.submitSolutionSolution(type, title, description, request);
		return responseJSON.toString();
	}
	
}
