package com.unicef.portlet.launch.challenge;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.unicef.domain.LaunchAChallenge;
import com.unicef.domain.Sponsor;
import com.unicef.service.ChallengeService;
import com.unicef.service.SponsorService;
import com.unicef.util.ChallengeConstant;
import com.unicef.util.IdeaUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@RequestMapping("VIEW")
public class ChallengeController {
	private static final String CHALLENGE_RESPONSE_JSP = "challenge_response";
	private static final Log log = LogFactoryUtil.getLog(ChallengeController.class);
	@Autowired 
	private ChallengeService challengeService;
	
	@Autowired
	private SponsorService sponsorServices;
	
	@RenderMapping
	public String indexPage(RenderRequest renderRequest,RenderResponse renderResponse,Model model){	
		log.info("launch a challenge render");
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long challengeId = ParamUtil.getLong(renderRequest, "challengeId");
		List<AssetCategory>	categoryList = null;
		List<AssetCategory> edgeCategory = null;
		List<AssetCategory> ideaTypeList=null;
		List<AssetCategory> lineOfbussiness = null;
		List<User> users = null;
		List<KeyValuePair> availableUserList = new ArrayList<KeyValuePair>();
		List<KeyValuePair> selectedUserList = new ArrayList<KeyValuePair>();
		LaunchAChallenge challenge = null;
		boolean checkPrize = false;
		try{
			log.info("launch a challenge render try");
			if(challengeId>0){
				log.info("launch a challenge render try if");
				challenge = challengeService.find(challengeId);
				for (Sponsor sponsor : challenge.getSponsors()) {
					 KeyValuePair key = new KeyValuePair();
					 key.setKey(String.valueOf(sponsor.getSponsorId()));
					 key.setValue(sponsor.getSponsorName());
					 selectedUserList.add(key);
				}
				if(challenge.isPrize()){
					checkPrize = true;
				}
			}
			log.info("launch a challenge render try else");
			categoryList = IdeaUtil.getCategoryList(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
			 edgeCategory = IdeaUtil.getEdgeServiceCategoryList(
					 themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
			 ideaTypeList = IdeaUtil.getIdeaTypeList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
			 lineOfbussiness = IdeaUtil.getBusinessLine(
					 themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
			 users = UserLocalServiceUtil.getUsers(-1, -1);
			 
			for (User user : users) {
				 KeyValuePair key = new KeyValuePair();
				 key.setKey(String.valueOf(user.getUserId()));
				 key.setValue(user.getFullName());
				 
				 availableUserList.add(key);
			}
			log.info("launch a challenge render try afterFor");
			availableUserList.removeAll(selectedUserList);
			
			 model.addAttribute("checkPrize", checkPrize);
			 model.addAttribute("challenge", challenge);
			 model.addAttribute("selectedUserList", selectedUserList);
			 model.addAttribute("availableUserIdsList", availableUserList);
			 model.addAttribute("userList", users);
			 model.addAttribute("categoryList",categoryList);
			 model.addAttribute("edgeCategory",edgeCategory);
			 model.addAttribute("ideaTypeList",ideaTypeList);
			 model.addAttribute("lineOfbussiness",lineOfbussiness);
			 log.info("launch a challenge render try afterFor beforecatch");
		}catch(Exception e){
			log.info("Error in getting category list"+e.getMessage());
		}
		log.info("before launch");
		return "launch-a-challenge";
	}
	
	@RenderMapping(params="action=challangelist")
	public String view(Locale locale, Model model, RenderRequest renderRequest,RenderResponse renderResponse) throws PortalException{
		try{
			model.addAttribute("noOfChallenge", challengeService.findAll());	
		}catch(Exception e){
			log.info("Error in Render Method of Challenge Controller"+e.getMessage());
		}
		log.info("lauch a challenge page");
		return "view";
	}
	
	@ResourceMapping(value = "getChallengeDataURL")
	public String getIdeaDataURL(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model){

		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		int recordsSize = Integer.parseInt(request.getParameter("recordsPerPage"));
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		long start = (long) (pageNo * recordsSize) - recordsSize;
		
		try{
			List<LaunchAChallenge> challenges = challengeService.getListOfChalleneges(start,recordsSize);
			if (challenges.size() > 0) {
				model.addAttribute("challengeService", challengeService);
				model.addAttribute("challengeList", challenges);
			}
		}catch(Exception e){
			log.error("Error in getting Idea Data Using Ajax Method:::"+e.getMessage());
		}
		return CHALLENGE_RESPONSE_JSP;
	}
	
	@RenderMapping(params = "action = backToLaunch")
	public String testing(RenderRequest renderRequest,RenderResponse renderResponse,Model model){
		log.info("return url");
		
		return "view";
	}
	
	@RenderMapping(params = "action=challenge")
	public String launchChallengeRender(RenderRequest renderRequest,RenderResponse renderResponse,Model model){
		
		log.info("launch a challenge render");
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long challengeId = ParamUtil.getLong(renderRequest, "challengeId");
		List<AssetCategory>	categoryList = null;
		List<AssetCategory> edgeCategory = null;
		List<AssetCategory> ideaTypeList=null;
		List<AssetCategory> lineOfbussiness = null;
		List<User> users = null;
		List<KeyValuePair> availableUserList = new ArrayList<KeyValuePair>();
		List<KeyValuePair> selectedUserList = new ArrayList<KeyValuePair>();
		LaunchAChallenge challenge = null;
		boolean checkPrize = false;
		try{
			log.info("launch a challenge render try");
			if(challengeId>0){
				log.info("launch a challenge render try if");
				challenge = challengeService.find(challengeId);
				for (Sponsor sponsor : challenge.getSponsors()) {
					 KeyValuePair key = new KeyValuePair();
					 key.setKey(String.valueOf(sponsor.getSponsorId()));
					 key.setValue(sponsor.getSponsorName());
					 selectedUserList.add(key);
				}
				if(challenge.isPrize()){
					checkPrize = true;
				}
			}
			log.info("launch a challenge render try else");
			categoryList = IdeaUtil.getCategoryList(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
			 edgeCategory = IdeaUtil.getEdgeServiceCategoryList(
					 themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
			 ideaTypeList = IdeaUtil.getIdeaTypeList(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
			 lineOfbussiness = IdeaUtil.getBusinessLine(
					 themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
			 users = UserLocalServiceUtil.getUsers(-1, -1);
			 
			for (User user : users) {
				 KeyValuePair key = new KeyValuePair();
				 key.setKey(String.valueOf(user.getUserId()));
				 key.setValue(user.getFullName());
				 
				 availableUserList.add(key);
			}
			log.info("launch a challenge render try afterFor");
			availableUserList.removeAll(selectedUserList);
			
			 model.addAttribute("checkPrize", checkPrize);
			 model.addAttribute("challenge", challenge);
			 model.addAttribute("selectedUserList", selectedUserList);
			 model.addAttribute("availableUserIdsList", availableUserList);
			 model.addAttribute("userList", users);
			 model.addAttribute("categoryList",categoryList);
			 model.addAttribute("edgeCategory",edgeCategory);
			 model.addAttribute("ideaTypeList",ideaTypeList);
			 model.addAttribute("lineOfbussiness",lineOfbussiness);
			 log.info("launch a challenge render try afterFor beforecatch");
		}catch(Exception e){
			log.info("Error in getting category list"+e.getMessage());
		}
		log.info("before launch");
		return "launch-a-challenge";
	}
	
	@ActionMapping(params = "action=launchChallenge")
	public void launchChallengeAction(ActionRequest actionRequest,ActionResponse actionResponse,Model model) throws ParseException{
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		long challengeId = ParamUtil.getLong(uploadRequest, "challengeId");
		String title = ParamUtil.getString(uploadRequest, "challengeTitle");
		String challengeTagline = ParamUtil.getString(uploadRequest, "challengeTagline");
		String description = ParamUtil.getString(uploadRequest, "challengeContent");
		String startDate = ParamUtil.getString(uploadRequest, "startDate");
		String endDate = ParamUtil.getString(uploadRequest, "endDate");
		long vertical = ParamUtil.getLong(uploadRequest, "vertical",0);
		long edgeService = ParamUtil.getLong(uploadRequest, "edgeService",0);
		long goalId = ParamUtil.getLong(uploadRequest, "challengeGoal",0);
		long LinOfBusiness = ParamUtil.getLong(uploadRequest, "LinOfBusiness",0);	
		boolean prize =ParamUtil.getBoolean(uploadRequest, "prize",false);
		String prizeDescription = ParamUtil.getString(uploadRequest, "prizeDescription",StringPool.BLANK);
		long[] selectedUserIds = ParamUtil.getLongValues(uploadRequest, "userCategoryIds");
		
		startDate+=" 00:00:01";
		endDate+=" 23:23:59";
		
		/*Date start = dateFormat.parse(startDate);
		Date end = dateFormat.parse(endDate);*/
		
		LaunchAChallenge challenge = null;
		
		if(challengeId > 0){
			challenge = challengeService.find(challengeId);
			challenge.setChallengeModifiedDate(new Date());
		}else{
			challenge = new LaunchAChallenge();
			challenge.setChallengeCreatedDate(new Date());
		}
	
		challenge.setChallengeTitle(title);
		challenge.setDescription(description);
		challenge.setChallengeStartDate(new Date());
		challenge.setChallengeEndDate(new Date());
		challenge.setVerticalId(vertical);
		challenge.setEdgeId(edgeService);
		challenge.setPrize(prize);
		challenge.setPrizeDescription(prizeDescription);
		challenge.setGoalId(goalId);
		challenge.setChallengorId(themeDisplay.getUserId());
		challenge.setCompanyId(themeDisplay.getCompanyId());
		challenge.setGroupId(themeDisplay.getScopeGroupId());
		challenge.setChallengeTagline(challengeTagline);
		challenge.setLineOfbussinessId(LinOfBusiness);
		Sponsor sponsor = null;
		Set<Sponsor> sponsorList = null;
		List<Long> selectedUsersList =new ArrayList<Long>(Arrays.asList(ArrayUtils.toObject(selectedUserIds)));
		
		if(challengeId > 0 && Validator.isNotNull(challenge.getSponsors())){
			sponsorList = challenge.getSponsors();
			for (Sponsor oldSponsor : sponsorList) {
				sponsorServices.deleteFromMappingTable(oldSponsor.getId());
				sponsorServices.delete(oldSponsor.getId());
			}
			
			sponsorList = new HashSet<Sponsor>();
			for(long id : selectedUsersList){
				try {
					User user = UserLocalServiceUtil.getUser(id);
						sponsor = new Sponsor();
						sponsor.setSponsorId(id);
						sponsor.setSponsorName(user.getFullName());
						sponsor.setChallenge(challenge);
						sponsorList.add(sponsor);
				} catch (PortalException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}else{
			sponsorList = new HashSet<Sponsor>();
			for(long id : selectedUsersList){
				try {
					User user = UserLocalServiceUtil.getUser(id);
					sponsor = new Sponsor();
					sponsor.setSponsorId(id);
					sponsor.setSponsorName(user.getFullName());
					sponsor.setChallenge(challenge);
					sponsorList.add(sponsor);
					
				} catch (PortalException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
		challenge.setSponsors(sponsorList);
		
		if(challengeId > 0){
			challengeService.update(challenge);
			SessionMessages.add(actionRequest,ChallengeConstant.SUCCESS_CHALLENGE_MESSAGE_UPDATE);
		}else{
			challengeService.create(challenge);
			SessionMessages.add(actionRequest,ChallengeConstant.SUCCESS_CHALLENGE_MESSAGE_CREATE);
		}
		actionResponse.setRenderParameter("action","challangelist");
	}
	@ResourceMapping(value = "deleteChallengeURL")
	public void deleteChallengeURL(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Model model) {
		HttpServletRequest request = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(resourceRequest));
		long challengeId = ParamUtil.getLong(request, "challengeId");
		try{
			challengeService.delete(challengeId);
			model.addAttribute("noOfChallenge", challengeService.findAll());
		}catch(Exception e){
			log.info("Error in Delete Challenge"+e.getMessage());
		}
	}
}
