package com.unicef.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
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
public class SolutionScrumUtil {

	
	private static String SPRINT_TYPES = PropsUtil.get("unicef.sprint.type");
	
	private static SprintService sprintService;
	
	
	@Autowired
	private SolutionScrumUtil(SprintService sprintService) {
		SolutionScrumUtil.sprintService = sprintService;
	}
	public static void createDefaultSprint(int numPrefs, int length,
			long coInventorId, long groupId, long companyId, long solutionScrumId,
			long solutionId, long ideaId) {
		
		try{
			Calendar kickOffSprintStartCal = Calendar.getInstance();
			kickOffSprintStartCal.add(Calendar.DATE, numPrefs);
			
			Date kickOffSprintStartdate = getNextDate(kickOffSprintStartCal.getTime(), 0);
			Date kickOffSprintEnddate = getNextDate(kickOffSprintStartCal.getTime(), 6);
			
			
			Sprint kickOffSprint = getSprintObj("KickOff Sprint", SprintConstant.STATUS_PENDING, "Standard", "Text TBD", 1, StringPool.BLANK,
					kickOffSprintStartdate, length, kickOffSprintEnddate, coInventorId, groupId, companyId, solutionScrumId,solutionId, ideaId);
			
			sprintService.create(kickOffSprint);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static Sprint getSprintObj(String sprintTitle, int status,
			String type, String text, int orderIndex, String videoURL,
			Date startDate, int length, Date endDate,
			long coInventorId, long groupId, long companyId,
			long solutionScrumId, long solutionId, long ideaId) {
		
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
		sprint.setInventorId(coInventorId);
		sprint.setGroupId(groupId);
		sprint.setCompanyId(companyId);
		sprint.setIdeaScrumId(solutionScrumId);
		sprint.setSolutionId(solutionId);
		sprint.setIdeaId(ideaId);
		return sprint;
	}
	public static Date getNextDate(Date date, int days){
		  Calendar cal = Calendar.getInstance();
	      cal.setTime(date);
	      cal.add(Calendar.DATE, days);
	      return cal.getTime();
	}
	
	private static ServiceContext serviceContext = new ServiceContext();
	private static Log _log = LogFactoryUtil.getLog(SolutionScrumUtil.class);


	public static JSONArray convertchartRatingToJSONArray(Map<IdeaRateCriteria, List<Double>> rateCriterias){
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
	public static void updateAfterWardsSprint(Sprint oldSprint, Long sprintId,
			long solutionId, long solutionScrumId) {
		List<Sprint> sprints = sprintService.getSolutionSprintsByAfterOrderIndex(oldSprint.getOrderIndex(), solutionId, solutionScrumId);
		
		int nextOrder = oldSprint.getOrderIndex() + 1;
		
		Date sprintStartdate = getNextDate(oldSprint.getEndDate(), 1);
		
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
	public static void updateComingSprintAfterDelete(Sprint oldSprint,
			long sprintId, long solutionId, long groupId) {
		
		List<Sprint> sprints = sprintService.getSolutionSprintsByAfterOrderIndex(oldSprint.getOrderIndex(), solutionId, groupId);
		
		int nextOrder = oldSprint.getOrderIndex();
		
		Date sprintStartdate = getNextDate(oldSprint.getStartDate(), 0);
		
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

}
