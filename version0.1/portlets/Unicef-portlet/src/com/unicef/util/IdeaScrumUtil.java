package com.unicef.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.unicef.domain.IdeaRateCriteria;
import com.unicef.domain.Sprint;
import com.unicef.service.SprintService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdeaScrumUtil {

	private static String SPRINT_TYPES = PropsUtil.get("unicef.sprint.type");
	
	private static SprintService sprintService;
	
	@Autowired
	private IdeaScrumUtil(SprintService sprintService) {
		IdeaScrumUtil.sprintService = sprintService;
	}
	
	public static JSONArray convertchartRatingToJSONArray(Map<IdeaRateCriteria, List<Double>> rateCriterias) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			
			if (rateCriterias == null || rateCriterias.isEmpty()) {
				jsonObject.put("message", "no-avergerating-found");
				jsonObject.put("status", "success");
				return jsonArray.put(jsonObject);
			}
			  
			for (Entry<IdeaRateCriteria, List<Double>> entry : rateCriterias.entrySet()) {
			    JSONObject ratingObject = JSONFactoryUtil.createJSONObject();
			    
			    ratingObject.put("rateCriteriaId",String.valueOf(entry.getKey().getIdeaRateCriteriaId()));
			    ratingObject.put("rateCriteriaName",entry.getKey().getIdeaRateCriteriaName());

			    ratingObject.put("averageValue",String.valueOf(entry.getValue().get(0)));
			    ratingObject.put("standardDeviation",String.valueOf(entry.getValue().get(1)));
			    jsonArray.put(ratingObject);
			}
		    return jsonArray;
		}
	
	public static boolean isInventorPrefsPermission(long companyId, long userId, long coInventorId){
		boolean isInventorPrefsAdmin = false;
		if(userId == coInventorId){
			isInventorPrefsAdmin = true;
		}else{
			Role coachRole = null;
			Role adminRole = null;
			try {
				 coachRole = RoleLocalServiceUtil.getRole(companyId, "coach");
				 isInventorPrefsAdmin = RoleLocalServiceUtil.hasUserRole(userId, coachRole.getRoleId());
				 
				 if(!isInventorPrefsAdmin){
					 adminRole = RoleLocalServiceUtil.getRole(companyId, "Administrator");
					 isInventorPrefsAdmin = RoleLocalServiceUtil.hasUserRole(userId, adminRole.getRoleId());
				 }
			} catch (PortalException e1) {
				_log.error("Error in checking inventor prefs permission"+e1.getMessage());
			} catch (SystemException e1) {
				_log.error("Error in checking inventor prefs permission"+e1.getMessage());
			}
		}
		return isInventorPrefsAdmin;
	}
	
	public static String getSprintTypes(){
		if(Validator.isNotNull(SPRINT_TYPES)){
			return SPRINT_TYPES;
		}
		return "Standard,Starter,Custom,Reframing";
	}
	
	public static Date getNextDate(Date date, int days){
		  Calendar cal = Calendar.getInstance();
	      cal.setTime(date);
	      cal.add(Calendar.DATE, days);
	      return cal.getTime();
	}
	
	public static void createDefaultSprint(int numPrefs, int length, long inventorId, long groupId, long companyId, long ideaScrumId, long ideaId, long solutionId){
		
		try{
			Calendar kickOffSprintStartCal = Calendar.getInstance();
			kickOffSprintStartCal.add(Calendar.DATE, numPrefs);
			
			//Date kickOffSprintStartdate = getNextDate(kickOffSprintStartCal.getTime(), 0);
			//Date kickOffSprintEnddate = getNextDate(kickOffSprintStartCal.getTime(), 6);
			//Date ratingSprintStartdate = getNextDate(kickOffSprintEnddate, 1);
			//Date ratingSprintEnddate = getNextDate(ratingSprintStartdate, 6);
			//Date innospectiveStartdate = getNextDate(ratingSprintEnddate, 1);
			//Date innospectiveEnddate = getNextDate(innospectiveStartdate, 6);
			
			Date understandStartdate = getNextDate(kickOffSprintStartCal.getTime(), 0);
			Date understandEnddate = getNextDate(kickOffSprintStartCal.getTime(), 6);
			
			Date divergeStartdate = getNextDate(understandEnddate, 3);
			Date divergeEnddate = getNextDate(divergeStartdate, 6);
			
			Date convergeStartdate = getNextDate(divergeEnddate, 2);
			Date convergeEnddate = getNextDate(convergeStartdate, 6);
			
			Date prototypeStartdate = getNextDate(convergeEnddate, 3);
			Date prototypeEnddate = getNextDate(prototypeStartdate, 6);
			
			Date validateStartdate = getNextDate(prototypeEnddate, 2);
			Date validateEnddate = getNextDate(validateStartdate, 6);
			
			Date innospectiveStartdate = getNextDate(validateEnddate, 1);
			Date innospectiveEnddate = getNextDate(innospectiveStartdate, 6);
			
			Date ratingSprintStartdate = getNextDate(innospectiveEnddate, 1);
			Date ratingSprintEnddate = getNextDate(ratingSprintStartdate, 6);
			
			
			/**
			Sprint kickOffSprint = getSprintObj("KickOff Sprint", SprintConstant.STATUS_PENDING, "Standard", "Text TBD", 1, StringPool.BLANK,
					kickOffSprintStartdate, length, kickOffSprintEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			
			Sprint ratingSprint = getSprintObj("Idea Rating", SprintConstant.STATUS_COMPLETED, "Rating(Required)", "Text TBD", 2, StringPool.BLANK,
					ratingSprintStartdate, length, ratingSprintEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			
			Sprint innospectiveSprint = getSprintObj("Innospective", SprintConstant.STATUS_COMPLETED, "Innospective(Required)", "Text TBD", 3, 
					StringPool.BLANK, innospectiveStartdate, length, innospectiveEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			*/
			
			// Understand
			Sprint understandDefineTheProblem = getSprintObj("Understand", SprintConstant.STATUS_PENDING, "Standard", "Define the Problem", 1, StringPool.BLANK,
					understandStartdate, length, understandEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(understandDefineTheProblem);
			Sprint understandUserPersonas = getSprintObj("Understand", SprintConstant.STATUS_PENDING, "Standard", "PAINstorming", 1, StringPool.BLANK,
					understandStartdate, length, understandEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(understandUserPersonas);
			Sprint understandEthnography = getSprintObj("Understand", SprintConstant.STATUS_PENDING, "Standard", "Personas", 1, StringPool.BLANK,
					understandStartdate, length, understandEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(understandEthnography);
			Sprint understandCompetitiveLandscape = getSprintObj("Understand", SprintConstant.STATUS_PENDING, "Standard", "Ethnography", 1, StringPool.BLANK,
					understandStartdate, length, understandEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(understandCompetitiveLandscape);
			Sprint understandInterviewNotes = getSprintObj("Understand", SprintConstant.STATUS_PENDING, "Standard", "Stakeholder Interview Notes", 1, StringPool.BLANK,
					understandStartdate, length, understandEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(understandInterviewNotes);
			Sprint understandPAINstorming = getSprintObj("Understand", SprintConstant.STATUS_PENDING, "Standard", "Competitive Landscape", 1, StringPool.BLANK,
					understandStartdate, length, understandEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(understandPAINstorming);
			Sprint understandInsights = getSprintObj("Understand", SprintConstant.STATUS_PENDING, "Standard", "Insights, Blindspots, Vision Quest", 1, StringPool.BLANK,
					understandStartdate, length, understandEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(understandInsights);
			Sprint understandSuccessMetrics = getSprintObj("Understand", SprintConstant.STATUS_PENDING, "Standard", "Success Metrics", 1, StringPool.BLANK,
					understandStartdate, length, understandEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(understandSuccessMetrics);
			
			// Diverge
			Sprint divergeMindMaps = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Start by emptying your idea teacup", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeMindMaps);
			Sprint divergeEmotionMaps = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Open thinking", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeEmotionMaps);
			Sprint divergeCrazy8s = 
					getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Deepen our customer insight", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeCrazy8s);
			Sprint divergeMV1Howdoweenlarge = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Let’s map it!", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeMV1Howdoweenlarge);
			Sprint divergeThisIdea = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Let’s get visual", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeThisIdea);
			Sprint divergeMV2WhocanWe = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Expand the idea", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeMV2WhocanWe);
			Sprint divergePartnerwith = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Let’s de-constrain our thinking", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergePartnerwith);
			Sprint divergeMV3TurnThisIdea = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Let’s hunt for blindspots", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeMV3TurnThisIdea);
			Sprint divergeInsideOut = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Let’s reframe the idea", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeInsideOut);
			Sprint divergeInsideOut1 = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "What is the eco-system here?", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeInsideOut1);
			Sprint divergeInsideOut11 = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Steal and make better", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeInsideOut11);
			Sprint divergeInsideOut111 = getSprintObj("Diverge", SprintConstant.STATUS_PENDING, "Standard", "Unleashing our core creativity", 2, StringPool.BLANK,
					divergeStartdate, length, divergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(divergeInsideOut111);
			
			//Converge
			Sprint convergeBlindspotting = getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "PAINstorming", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergeBlindspotting);
			Sprint convergeMVPdefinition  = getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "Solution", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergeMVPdefinition);
			Sprint convergeHeatMapping = getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "Foundational", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergeHeatMapping);
			Sprint convergeStoryboarding = getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "Unique value proposition", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergeStoryboarding);
			Sprint convergeMultiVotingResults = getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "Unfair advantage", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergeMultiVotingResults);
			
			Sprint convergePrioritize = getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "Customers & channels", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergePrioritize);
			
			Sprint convergeBusinessModelCanvas = getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "Revenue streams", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergeBusinessModelCanvas);
			
			Sprint convergeCostsAndHurdles = getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "Costs and hurdles", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergeCostsAndHurdles);
			
			Sprint convergeFirstFiveSteps = getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "First 5 steps", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergeFirstFiveSteps);
			
			Sprint convergeFineTheSpine= getSprintObj("Converge", SprintConstant.STATUS_PENDING, "Standard", "Find the spine ", 3, StringPool.BLANK,
					convergeStartdate, length, convergeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(convergeFineTheSpine);
			
			//Prototype
			Sprint prototypeUseCase = getSprintObj("Prototype", SprintConstant.STATUS_PENDING, "Standard", "Define the MVP", 4, StringPool.BLANK,
					prototypeStartdate, length, prototypeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(prototypeUseCase);
			Sprint prototypeFidelityTuning = getSprintObj("Prototype", SprintConstant.STATUS_PENDING, "Standard", "User testing script", 4, StringPool.BLANK,
					prototypeStartdate, length, prototypeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(prototypeFidelityTuning);
			Sprint prototypeDivideAndConquer  = getSprintObj("Prototype", SprintConstant.STATUS_PENDING, "Standard", "Find a tool", 4, StringPool.BLANK,
					prototypeStartdate, length, prototypeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(prototypeDivideAndConquer);
			Sprint prototypeStitchItTogether  = getSprintObj("Prototype", SprintConstant.STATUS_PENDING, "Standard", "Timeboxing", 4, StringPool.BLANK,
					prototypeStartdate, length, prototypeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(prototypeStitchItTogether);
			Sprint prototypePickTheTools = getSprintObj("Prototype", SprintConstant.STATUS_PENDING, "Standard", "Goals", 4, StringPool.BLANK,
					prototypeStartdate, length, prototypeEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(prototypePickTheTools);
			
			//Validate
			Sprint validateInterviewFindings = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "Interview findings", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validateInterviewFindings);
			Sprint validatePainPoints = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "Pain points", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validatePainPoints);
			Sprint validateSolutionPoints = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "Solution points", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validateSolutionPoints);
			Sprint validateTheBIGVision = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "The BIG Vision", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validateTheBIGVision);
			Sprint validateHowyougetthere = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "How you get there", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validateHowyougetthere);
			Sprint validateWhatDidWeLearn = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "Happily ever after", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validateWhatDidWeLearn);
			Sprint validateObserveUsers = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "Meet cute", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validateObserveUsers);
			Sprint validateInterviewUsers = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "Everything else", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validateInterviewUsers);
			Sprint validateBusinessPanorama = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "The throughline", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validateBusinessPanorama);
			Sprint validateRemember = getSprintObj("Validate", SprintConstant.STATUS_PENDING, "Standard", "Remember", 5, StringPool.BLANK,
					validateStartdate, length, validateEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(validateRemember);
			
			// Innospective
			Sprint innospectiveThreeWords = getSprintObj("Innospective", SprintConstant.STATUS_COMPLETED, "Innospective(Required)", "Three words", 6, 
					StringPool.BLANK, innospectiveStartdate, length, innospectiveEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(innospectiveThreeWords);
			Sprint innospectiveHappyAbout = getSprintObj("Innospective", SprintConstant.STATUS_COMPLETED, "Innospective(Required)", "Happy about", 6, 
					StringPool.BLANK, innospectiveStartdate, length, innospectiveEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(innospectiveHappyAbout);
			Sprint innospectiveNeedToImprove = getSprintObj("Innospective", SprintConstant.STATUS_COMPLETED, "Innospective(Required)", "Need to improve", 6, 
					StringPool.BLANK, innospectiveStartdate, length, innospectiveEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(innospectiveNeedToImprove);
			Sprint innospectiveIdeasForImproving = getSprintObj("Innospective", SprintConstant.STATUS_COMPLETED, "Innospective(Required)", "Ideas for improving this process", 6, 
					StringPool.BLANK, innospectiveStartdate, length, innospectiveEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(innospectiveIdeasForImproving);
			Sprint innospectiveAgileInnovation = getSprintObj("Innospective", SprintConstant.STATUS_COMPLETED, "Innospective(Required)", "Ideas for improving Agile Innovation", 6, 
					StringPool.BLANK, innospectiveStartdate, length, innospectiveEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(innospectiveAgileInnovation);
			
			Sprint ratingSprint = getSprintObj("Idea Rating", SprintConstant.STATUS_COMPLETED, "Rating(Required)", "Text TBD", 7, StringPool.BLANK,
					ratingSprintStartdate, length, ratingSprintEnddate, inventorId, groupId, companyId, ideaScrumId, ideaId,solutionId);
			sprintService.create(ratingSprint);
			
			_log.info("Default Sprints(Kick off , Rating, Innospective) created sucessfully");
			
		}catch(Exception e) {
			_log.error("Error in creating default sprints: " + e.getMessage() );
		}
		
	}
	public static Sprint getSprintObj(String sprintTitle, int status, String type, String text, int orderIndex, String videoURL, Date startDate, int length,
			Date endDate, long inventorId, long groupId, long companyId, long ideaScrumId, long ideaId, long solutionId){
		Sprint sprint = new Sprint();
		sprint.setFocusTitle(sprintTitle);
		sprint.setStatus(status);
		sprint.setType(type);
		sprint.setText(text);
		sprint.setOrderIndex(orderIndex);
		sprint.setVideoURL(videoURL);
		sprint.setStartDate(startDate);
		sprint.setDaysLength(length);
		sprint.setEndDate(endDate);
		sprint.setCreatedDate(new Date());
		sprint.setModifiedDate(new Date());
		sprint.setInventorId(inventorId);
		sprint.setGroupId(groupId);
		sprint.setCompanyId(companyId);
		sprint.setIdeaScrumId(ideaScrumId);
		sprint.setIdeaId(ideaId);
		sprint.setSolutionId(solutionId);
		return sprint;
	}
	
	public static void updateAfterWardsSprint(Sprint actionSprint, Long sprintId, long ideaId, long ideaScrumId) {
		List<Sprint> sprints = sprintService.getSprintsByAfterOrderIndex(actionSprint.getOrderIndex(), ideaId, ideaScrumId);
	
		int nextOrder = actionSprint.getOrderIndex() + 1;
		
		Date sprintStartdate = getNextDate(actionSprint.getEndDate(), 1);
		
		for(Sprint sprint: sprints){
			 if(!sprint.getSprintId().equals(sprintId)){
				 sprint.setOrderIndex(nextOrder);
				 Date sprintEnddate = getNextDate(sprintStartdate, (sprint.getDaysLength()-1));
				 sprint.setStartDate(sprintStartdate);
				 sprint.setEndDate(sprintEnddate);
				 sprint.setModifiedDate(new Date());
				 sprintService.update(sprint);
				 nextOrder = nextOrder + 1;
				 sprintStartdate = getNextDate(sprintEnddate, 1);
			 }
		}
	}
	
	public static void updateComingSprintAfterDelete(Sprint actionSprint, Long sprintId, long ideaId, long ideaScrumId) {
		List<Sprint> sprints = sprintService.getSprintsByAfterOrderIndex(actionSprint.getOrderIndex(), ideaId, ideaScrumId);
		
		int nextOrder = actionSprint.getOrderIndex();
		
		Date sprintStartdate = getNextDate(actionSprint.getStartDate(), 0);
		
		for(Sprint sprint: sprints){
			 if(sprint.getSprintId() != sprintId){
				 sprint.setOrderIndex(nextOrder);
				 Date sprintEnddate = getNextDate(sprintStartdate, (sprint.getDaysLength()-1));
				 sprint.setStartDate(sprintStartdate);
				 sprint.setEndDate(sprintEnddate);
				 sprint.setModifiedDate(new Date());
				 sprintService.update(sprint);
				 nextOrder = nextOrder + 1;
				 sprintStartdate = getNextDate(sprintEnddate, 1);
			 }
		}
	}
	
	private static ServiceContext serviceContext = new ServiceContext();
	private static Log _log = LogFactoryUtil.getLog(IdeaScrumUtil.class);
}
