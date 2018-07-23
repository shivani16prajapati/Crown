package com.unicef.util;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.Solution;
import com.unicef.service.IdeaService;
import com.unicef.service.SolutionAnswerVoteService;
import com.unicef.service.SolutionCommentService;
import com.unicef.service.SolutionLikeService;
import com.unicef.service.SolutionService;
import com.unicef.service.SolutionViewService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolutionUtil {
	private static final Log log = LogFactoryUtil.getLog(SolutionUtil.class);
	
	private static SolutionService solutionService;
	private static SolutionViewService solutionViewService;
	private static SolutionCommentService solutionCommentService;
	private static SolutionAnswerVoteService solutionAnswerVoteService;
	private static IdeaService ideaService;
	private static SolutionLikeService solutionLikeService;
	
	static ServiceContext serviceContext = new ServiceContext();
	
	private static final String SOLUTION_FRIENDLY_URL_LAYOUT = PropsUtil.get("solution-generator-friendly-layout");
	private static final String VIEW_WEIGHT = PropsUtil.get("solution-view-weight");
	private static final String WANT_WEIGHT = PropsUtil.get("solution-want-weight");
	private static final String COMMENT_WEIGHT = PropsUtil.get("solution-comment-weight");
	
	@Autowired
	private SolutionUtil(SolutionService solutionService, SolutionViewService solutionViewService, 
			SolutionCommentService solutionCommentService, SolutionAnswerVoteService solutionAnswerVoteService,IdeaService ideaService,SolutionLikeService solutionLikeService){
		SolutionUtil.solutionService = solutionService;
		SolutionUtil.solutionViewService = solutionViewService;
		SolutionUtil.solutionCommentService = solutionCommentService;
		SolutionUtil.solutionAnswerVoteService = solutionAnswerVoteService;
		SolutionUtil.ideaService = ideaService;
		SolutionUtil.solutionLikeService = solutionLikeService;
	}
	
	public static List<AssetCategory> getSolutionCategory(long scopeGroupId,
			long userId) throws PortalException, SystemException {
		List<AssetCategory> categoryList = new ArrayList<AssetCategory>();
		AssetVocabulary categoryAssetVocabulary=null;
			
			try {
				categoryAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "Category");
			} catch (PortalException e) {
				serviceContext.setScopeGroupId(scopeGroupId);
				categoryAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId, PropsUtil.get("unicef.idea.category"), serviceContext);
			} catch (SystemException e) {
				e.printStackTrace();
			}
			long categoryVocabularyId = categoryAssetVocabulary.getVocabularyId();

			DynamicQuery query = DynamicQueryFactoryUtil.forClass(AssetCategory.class);
			query.add(PropertyFactoryUtil.forName("vocabularyId").eq(categoryVocabularyId));
			query.add(PropertyFactoryUtil.forName("parentCategoryId").eq(Long.parseLong("0")));
			try {
				categoryList = AssetCategoryLocalServiceUtil.dynamicQuery(query, -1, -1);
			} catch (SystemException e) {
				log.error("No Category List Found"+e.getMessage());
			}
		
		return categoryList;
	}

	public static List<AssetCategory> getSolutionProgramme(long scopeGroupId,
			long userId) throws PortalException, SystemException {
		List<AssetCategory> ideaContestList =new ArrayList<AssetCategory>();
		AssetVocabulary ideaContestAssetVocabulary = null;
		try {
			ideaContestAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "Solution Program");
		} catch (PortalException e) {
			serviceContext.setScopeGroupId(scopeGroupId);
			ideaContestAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId, PropsUtil.get("unicef.idea.solution.program"), serviceContext);
			log.error("Portal Exception Called"+e.getMessage());
		} catch (SystemException e) {
			log.error("System exception called"+e.getMessage());
		}
		long ideaContestVocabularyId = ideaContestAssetVocabulary.getVocabularyId();
		try {
			ideaContestList = AssetCategoryLocalServiceUtil.getVocabularyCategories(ideaContestVocabularyId, -1, -1, null);
		} catch (SystemException e) {
			log.error("No List Found Of Idea Contest"+e.getMessage());
		}
		return ideaContestList;
	}

	public static List<AssetCategory> getsolutionStageList(long scopeGroupId,
			long userId) throws PortalException, SystemException {
		List<AssetCategory> stageList = new ArrayList<AssetCategory>();
		AssetVocabulary stageAssetVocabulary=null;
		
			try {
				stageAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "Solution Stage");
			} catch (PortalException e) {
				serviceContext.setScopeGroupId(scopeGroupId);
				stageAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId, PropsUtil.get("unicef.solution.stage"), serviceContext);
			} catch (SystemException e) {
				e.printStackTrace();
			}
			
			long stageVocabularyId = stageAssetVocabulary.getVocabularyId();
			try {
				stageList = AssetCategoryLocalServiceUtil.getVocabularyCategories(stageVocabularyId, -1, -1, null);
			} catch (SystemException e) {
				log.error("No Stage Category found"+e.getMessage());
			}
		return stageList;
	}

	/*public static List<AssetCategory> getSolutionSpaceList(long scopeGroupId,
			long userId) throws PortalException, SystemException {
		List<AssetCategory> ideaTypeList = new ArrayList<AssetCategory>();
		AssetVocabulary ideaAssetVocabulary=null;
		
			try {
				ideaAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "Solution Space");
			} catch (PortalException e) {
				serviceContext.setScopeGroupId(scopeGroupId);
				ideaAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId, PropsUtil.get("unicef.solution.space"), serviceContext);
			} catch (SystemException e) {
				e.printStackTrace();
			}
			long ideaVocabularyId = ideaAssetVocabulary.getVocabularyId();
			try {
				ideaTypeList = AssetCategoryLocalServiceUtil.getVocabularyCategories(ideaVocabularyId, -1, -1, null);
			} catch (SystemException e) {
				log.error("Solution Type List Not Found"+e.getMessage());
			}
		
		return ideaTypeList;
	}*/
	
	
	public static List<AssetCategory> getSolutionSpaceList(long scopeGroupId,
			long userId) throws PortalException, SystemException {
		List<AssetCategory> ideaTypeList = new ArrayList<AssetCategory>();
		AssetVocabulary ideaAssetVocabulary=null;
		
			try {
				ideaAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "Solution_Space");
			} catch (PortalException e) {
				serviceContext.setScopeGroupId(scopeGroupId);
				ideaAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId, "Solution_Space", serviceContext);
			} catch (SystemException e) {
				e.printStackTrace();
			}
			long ideaVocabularyId = ideaAssetVocabulary.getVocabularyId();
			try {
				ideaTypeList = AssetCategoryLocalServiceUtil.getVocabularyCategories(ideaVocabularyId, -1, -1, null);
				if(ideaTypeList.size() == 0){
					AssetCategoryLocalServiceUtil.addCategory(userId, "Possible solution to an existing problem", ideaVocabularyId, serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId, "Problem Goal", ideaVocabularyId, serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId, "A new product", ideaVocabularyId, serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId, "A new service", ideaVocabularyId, serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId, "Improving how we work", ideaVocabularyId, serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId, "Social innovation", ideaVocabularyId, serviceContext);
					ideaTypeList = AssetCategoryLocalServiceUtil.getVocabularyCategories(ideaVocabularyId, -1, -1, null);
				}
			} catch (SystemException e) {
				log.error("Solution Type List Not Found"+e.getMessage());
				
			}
		
		return ideaTypeList;
	}
	
	
	public static List<Solution> getSolutionList(){
		return solutionService.solutionList();
	}
	
	public static Solution getSolution(long solutionId){
		return solutionService.find(solutionId);
	}
	
	public static String getSolutionFriendlyLayoutURL(){
		if(Validator.isNotNull(SOLUTION_FRIENDLY_URL_LAYOUT)){
			return SOLUTION_FRIENDLY_URL_LAYOUT;
		}
		
		return "/solutions-generator";
	}
	
	public static Layout getSolutionGeneratorLayout(long groupId){
		Layout solutionLayout = null;
		try {
			solutionLayout = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, Boolean.TRUE, getSolutionFriendlyLayoutURL());
		} catch (PortalException e) {
			log.error("Error in getting solution layout:"+e.getMessage());
		} catch(SystemException e) {
			log.error("Error in getting solution layout:"+e.getMessage());
		}
		return solutionLayout;
		
	}
	
	public static boolean isSolutionNew(int newTHRESHOLD, Date createdDate){
		 
	    Calendar previousCal = Calendar.getInstance();
	    previousCal.add(Calendar.DATE, - newTHRESHOLD);
        Date fromDate = previousCal.getTime();
        
        Calendar fromCal = Calendar.getInstance();
        Date toDate = fromCal.getTime();
        
		return CommonUtil.getDateInRange(fromDate, toDate, createdDate);
    }
 
	 public static boolean isSolutionHOT(int hotTHRESHOLD, Date createdDate, boolean isSolutionHot, double hotWeight){
		 
		    Calendar previousCal = Calendar.getInstance();
		    previousCal.add(Calendar.DATE, - hotTHRESHOLD);
	        Date fromDate = previousCal.getTime();
	        
	        Calendar fromCal = Calendar.getInstance();
	        Date toDate = fromCal.getTime();
	        
			if(CommonUtil.getDateInRange(fromDate, toDate, createdDate) && isSolutionHot && (hotWeight >= 0.9)){
				return true;
			}
			return false;
	 }
	 
	 public static void updateHotSolutionList(){
		   Calendar currentDate = Calendar.getInstance();
		   Date endDate = currentDate.getTime();
		   
		   currentDate.add(Calendar.DATE, -7);
		   Date startDate = currentDate.getTime();
		   
		   DecimalFormat decimalFormat = new DecimalFormat("#.##");
		   
		   double viewWEIGHT = getViewWEIGHT(); 
		   double wantWEIGHT = getWantWEIGHT();
		   double commentWEIGHT = getCommentWEIGHT();
		   List<Solution> solutions = solutionService.getAllSolutions(startDate, endDate);
		   if(solutions.size() >= 5){
			   LinkedHashMap<Long, Double> solutionHotList = new LinkedHashMap<Long, Double>();
			   for (Solution solution : solutions) {
				    double solutionView = solutionViewService.getSolutionViewCountBySolutionId(solution.getSolutionId());
				    double solutionComment = solutionCommentService.getSolutionCommentCount(solution.getSolutionId());
				    double solutionWant = solutionAnswerVoteService.getWantAnswerCount(solution.getSolutionId());
				    double solutionHotness = 0;
				    solutionHotness = ((solutionView * viewWEIGHT)/100) + ((solutionComment * commentWEIGHT)/100) + ((solutionWant * wantWEIGHT)/100) ;
				    solutionHotList.put(solution.getSolutionId(), solutionHotness);
			   }
			   
			   LinkedList<Double> solutionList = new LinkedList<Double>(solutionHotList.values());
			   /*boolean isContinue = true;
			   while(isContinue){
				   if(solutionList.getLast() == 0.0){
					   solutionList.removeLast();
				   }else{
					   isContinue = false;
				   }
			   }*/
			   double maxHotness = Collections.max(solutionList);
			   for (Entry<Long, Double> entry : solutionHotList.entrySet()) {
				   	Solution solution = solutionService.find(entry.getKey());
				   	double solutionWeight = 0;
				   	try{
				   		if(maxHotness != 0){
				   			solutionWeight = entry.getValue() / maxHotness;
				   		}
				   	}catch(Exception e){
				   		log.error("Error in generating idea weight:"+e.getMessage());
				   	}
				   	solution.setHotWeight(Double.valueOf(decimalFormat.format(solutionWeight)));
				   	solution.setSolutionHot(Boolean.TRUE);
				   	solutionService.update(solution);
			   } 
		   	}
		  }
	 public static int getViewWEIGHT(){
			if(Validator.isNotNull(VIEW_WEIGHT)){
				return Integer.parseInt(VIEW_WEIGHT);
			}
			
			return 20;
		}
		
		public static int getWantWEIGHT(){
			if(Validator.isNotNull(WANT_WEIGHT)){
				return Integer.parseInt(WANT_WEIGHT);
			}
			
			return 50;
		}
		
		public static int getCommentWEIGHT(){
			if(Validator.isNotNull(COMMENT_WEIGHT)){
				return Integer.parseInt(COMMENT_WEIGHT);
			}
			
			return 30;
		}
		
		public static String getFriendlyURLOfCommunityBySolution(long solutionId){
			String url = StringPool.BLANK;
			if(Validator.isNotNull(solutionId) && solutionId != 0){
				IdeaScrum ideaScrum = ideaService.getIdeaScrumBySolutionId(solutionId);
				if(Validator.isNotNull(ideaScrum)){
					try {
						url = "/group"+GroupLocalServiceUtil.getGroup(ideaScrum.getGroupId()).getFriendlyURL();
					} catch (PortalException e) {
						log.info(e.getMessage());
					} catch (SystemException e) {
						log.info(e.getMessage());
					}
				}
			}
			return url;
		}

		public static double getKudoCount(long solutionId) {
			   double avg_like = 0;
			   double kudoCount = 0;
			   try{
			    long weeklySolutionLikeCount = solutionLikeService.getWeeklyCountOfSolutionLike(solutionId);
			    double lastWeekLikes = solutionLikeService.getTotalLikesOfLastWeek();
			    double lastWeekSubmitedSolutions = solutionService.countLastWeekSubmitedSolution();
			    if(lastWeekSubmitedSolutions != 0){
			     avg_like = lastWeekLikes / lastWeekSubmitedSolutions;
			     double displayCount = 0; 
			     displayCount = (weeklySolutionLikeCount / avg_like) * 0.5;
			     if(weeklySolutionLikeCount <=  (2* avg_like)){
			      kudoCount = 1;
			     }else{
			      kudoCount = displayCount;
			     }
			    }
			   }catch(Exception e){
			    log.error("Error in Getting Kudo Count in Solution Util"+e.getMessage());
			   }
			   return kudoCount;
		}

}
