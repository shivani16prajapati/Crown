package com.unicef.util;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.unicef.dao.IdeaAttachementDAO;
import com.unicef.dao.IdeaDAO;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.IdeaCommentVote;
import com.unicef.domain.IdeaHistory;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.Solution;
import com.unicef.portlet.idea.IdeaController;
import com.unicef.service.IdeaCommentService;
import com.unicef.service.IdeaCommentVoteService;
import com.unicef.service.IdeaHistoryService;
import com.unicef.service.IdeaService;
import com.unicef.service.IdeaViewService;
import com.unicef.service.LikeService;
import com.unicef.service.SolutionService;
import com.unicef.service.SprintService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdeaUtil {
	
	private static IdeaService ideaService;
	private static IdeaHistoryService ideaHistoryService;
	private static IdeaDAO ideaDAO;
	private static SolutionService solutionService;
	private static IdeaAttachementDAO ideaAttachmentDAO;
	private static IdeaViewService ideaViewService;
	private static IdeaCommentService ideaCommentService;
	private static LikeService likeService;
	private static IdeaCommentVoteService commentVoteService;
	private static SprintService sprintService;
	
	
	private static final String IDEA_FRIENDLY_URL_LAYOUT = PropsUtil.get("idea-machine-friendly-layout");
	private static final String VIEW_WEIGHT = PropsUtil.get("idea-view-weight");
	private static final String LIKE_WEIGHT = PropsUtil.get("idea-like-weight");
	private static final String COMMENT_WEIGHT = PropsUtil.get("idea-comment-weight");
	
	@Autowired
	private IdeaUtil(IdeaService ideaService, IdeaHistoryService ideaHistoryService, IdeaDAO ideaDAO, 
			SolutionService solutionService, IdeaAttachementDAO ideaAttachementDAO, IdeaViewService ideaViewService, IdeaCommentService ideaCommentService, LikeService likeService,IdeaCommentVoteService commentVoteService,SprintService sprintService ){
		IdeaUtil.ideaService = ideaService;
	    IdeaUtil.ideaHistoryService = ideaHistoryService;
	    IdeaUtil.ideaDAO = ideaDAO;
	    IdeaUtil.solutionService = solutionService;
	    IdeaUtil.ideaAttachmentDAO = ideaAttachementDAO;
	    IdeaUtil.ideaViewService = ideaViewService;
	    IdeaUtil.ideaCommentService = ideaCommentService;
	    IdeaUtil.likeService = likeService;
	    IdeaUtil.commentVoteService = commentVoteService;
	    IdeaUtil.sprintService = sprintService;
	}
	
	private static final Log log = LogFactoryUtil.getLog(IdeaUtil.class);
	
	static ServiceContext serviceContext = new ServiceContext();
	
	public static List<AssetCategory> getCategoryList(long scopeGroupId,long userId) throws PortalException, SystemException  {
	
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

	public static List<AssetCategory> getIdeaContestList(long scopeGroupId, long userId) throws PortalException, SystemException {
		List<AssetCategory> ideaContestList =new ArrayList<AssetCategory>();
		AssetVocabulary ideaContestAssetVocabulary = null;
		try {
			ideaContestAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "Idea Program");
		} catch (PortalException e) {
			serviceContext.setScopeGroupId(scopeGroupId);
			ideaContestAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId, PropsUtil.get("unicef.idea.contest"), serviceContext);
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

	public static List<AssetCategory> getIdeaStageList(long scopeGroupId,long userId) throws PortalException, SystemException {
		List<AssetCategory> stageList = new ArrayList<AssetCategory>();
		AssetVocabulary stageAssetVocabulary=null;
		
			try {
				stageAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "Stage");
			} catch (PortalException e) {
				serviceContext.setScopeGroupId(scopeGroupId);
				stageAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId, PropsUtil.get("unicef.idea.stage"), serviceContext);
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
	
	public static List<AssetCategory> getIdeaNewStageList(long scopeGroupId,long userId) throws PortalException, SystemException {
		List<AssetCategory> stageList = new ArrayList<AssetCategory>();
		AssetVocabulary stageAssetVocabulary=null;
		
			try {
				stageAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "NewStage");
			} catch (Exception e) {
				serviceContext.setScopeGroupId(scopeGroupId);
				stageAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId,"NewStage",serviceContext);
			}
			
			long stageVocabularyId = stageAssetVocabulary.getVocabularyId();
			try {
				stageList = AssetCategoryLocalServiceUtil.getVocabularyCategories(stageVocabularyId, -1, -1, null);
				if (stageList.size() == 0){
					AssetCategoryLocalServiceUtil.addCategory(userId,"Submitted",stageVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Finalist",stageVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Incubation",stageVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Pilot",stageVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Deployed",stageVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Repository",stageVocabularyId,serviceContext);
					stageList = AssetCategoryLocalServiceUtil.getVocabularyCategories(stageVocabularyId, -1, -1, null);
				}
			} catch (SystemException e) {
				log.error("No Stage Category found"+e.getMessage());
			}
		return stageList;
	}

	public static List<AssetCategory> getBusinessLine(long scopeGroupId,long userId) throws PortalException, SystemException {
		List<AssetCategory> BusinessLinelist = new ArrayList<AssetCategory>();
		AssetVocabulary bussLineAssetVocabulary=null;		
			try {
				bussLineAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "NewLineOfBusiness");
			} catch (Exception e) {
				serviceContext.setScopeGroupId(scopeGroupId);
				bussLineAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId,"NewLineOfBusiness",serviceContext);
			}
			
			long bussLineVocabularyId = bussLineAssetVocabulary.getVocabularyId();
			try {
				BusinessLinelist = AssetCategoryLocalServiceUtil.getVocabularyCategories(bussLineVocabularyId, -1, -1, null);
				if (BusinessLinelist.size() == 0){
					AssetCategoryLocalServiceUtil.addCategory(userId,"Extraction",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Refining ",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Oleo-Chemicals",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Presses",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Protein",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Specialty",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Aftermarket",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"New",bussLineVocabularyId,serviceContext);
					BusinessLinelist = AssetCategoryLocalServiceUtil.getVocabularyCategories(bussLineVocabularyId, -1, -1, null);
				}
				
			} catch (SystemException e) {
				log.error("No BusinessLinelist Category found"+e.getMessage());
			}
		return BusinessLinelist;
	}
	
	public static List<AssetCategory> getEdgeServiceCategoryList(
			long scopeGroupId, long userId) throws PortalException, SystemException {
		List<AssetCategory> edgeCategoryService = new ArrayList<AssetCategory>();
		AssetVocabulary edgeServiceVocabulary = null;
		try{
			edgeServiceVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "Edge services");
		}catch(PortalException e){
			serviceContext.setScopeGroupId(scopeGroupId);
			edgeServiceVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId, PropsUtil.get("unicef.idea.edge.service"), serviceContext);
		}catch(SystemException e){
			e.printStackTrace();
		}
		long edgeVocabularyId = edgeServiceVocabulary.getVocabularyId();
		try{
			edgeCategoryService = AssetCategoryLocalServiceUtil.getVocabularyCategories(edgeVocabularyId, -1, -1, null);
		}catch(SystemException e){
			log.error("Edge Service List Not Found"+e.getMessage());
		}
		return edgeCategoryService;
	}
	
	/*public static List<AssetCategory> getIdeaTypeList(long scopeGroupId,long userId) throws PortalException, SystemException {
		List<AssetCategory> ideaTypeList = new ArrayList<AssetCategory>();
		AssetVocabulary ideaAssetVocabulary=null;
		
			try {
				ideaAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "Innovation Type");
			} catch (PortalException e) {
				serviceContext.setScopeGroupId(scopeGroupId);
				ideaAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId, PropsUtil.get("unicef.idea.type"), serviceContext);
			} catch (SystemException e) {
				e.printStackTrace();
			}
			long ideaVocabularyId = ideaAssetVocabulary.getVocabularyId();
			try {
				ideaTypeList = AssetCategoryLocalServiceUtil.getVocabularyCategories(ideaVocabularyId, -1, -1, null);
			} catch (SystemException e) {
				log.error("Idea Type List Not Found"+e.getMessage());
			}
		
		return ideaTypeList;
	}
	*/
	
	public static List<AssetCategory> getIdeaTypeList(long scopeGroupId,long userId) throws PortalException, SystemException {
		List<AssetCategory> ideaTypeList = new ArrayList<AssetCategory>();
		AssetVocabulary ideaAssetVocabulary=null;		
			try {
				ideaAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(scopeGroupId, "NewInnovationType");
			} catch (Exception e) {
				serviceContext.setScopeGroupId(scopeGroupId);
				ideaAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(userId,"NewInnovationType",serviceContext);
			}
			
			long bussLineVocabularyId = ideaAssetVocabulary.getVocabularyId();
			try {
				ideaTypeList = AssetCategoryLocalServiceUtil.getVocabularyCategories(bussLineVocabularyId, -1, -1, null);
				if (ideaTypeList.size() == 0){
					AssetCategoryLocalServiceUtil.addCategory(userId,"New Product",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"New Service",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"New Business Model",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Incremental Improvement",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Improving How We Work ",bussLineVocabularyId,serviceContext);
					AssetCategoryLocalServiceUtil.addCategory(userId,"Social Innovation ",bussLineVocabularyId,serviceContext);
					ideaTypeList = AssetCategoryLocalServiceUtil.getVocabularyCategories(bussLineVocabularyId, -1, -1, null);
				}
				
			} catch (SystemException e) {
				log.error("No BusinessLinelist Category found"+e.getMessage());
			}
		return ideaTypeList;
	}
	
	

	public static List<AssetCategory> getIdeaSubCategoryList(long categoryId) {
		List<AssetCategory> subCategoryList = new ArrayList<AssetCategory>();
		try {
				subCategoryList = AssetCategoryLocalServiceUtil.getChildCategories(categoryId);
			} catch (SystemException e) {
				log.error("Error in Getting Child Categories List"+e.getMessage());
			}
		
		return subCategoryList;
	}
	 
	 public static JSONObject submitQuickIdea(String type, String title, String description, HttpServletRequest httpServletRequest) {
	
		 JSONObject responseJSON = JSONFactoryUtil.createJSONObject();
	 /*add new entry in idea table*/
	 try{ 
		 /*get the default user for web service*/
		 User user = UserLocalServiceUtil.getUserByScreenName(PortalUtil.getCompanyId(httpServletRequest), PropsUtil.get("unicef-web-service-user-name"));

		 /*find the asset category id of C4D Idea Machine*/
		 long ideaProgram = getAssetCategoryIdByCategoryName("C4D Idea Machine");
		
		 /*find the value of idea stage*/
		 long ideaStage = getAssetCategoryIdByCategoryName("Idea Backlog");
		 
		 /*find the value of innovation type category*/
		 long innovationType = getAssetCategoryIdByCategoryName(decodeText(type));
		 
		 Idea idea = new Idea();
		 
		 idea.setModifiedDate(new Date());
		 idea.setSubmissionDate(new Date());
		 idea.setIdeaTitle(decodeText(title));
		 idea.setIdeaProgramme(ideaProgram);
		 idea.setIdeaCategory(0);
		 idea.setIdeaStage(ideaStage);
		 idea.setInnovationType(innovationType);
		 idea.setDescription(decodeText(description));
		 idea.setVersion(IdeaUtil.getFormattedVersion(1.0));
		 idea.setStatus(IdeaEnum.IDEA_BACKLOG.getValue());
		 idea.setCompanyId(PortalUtil.getCompanyId(httpServletRequest));
		 idea.setCoInventorId(user.getUserId());
		 idea.setGroupId(user.getGroupId());
		 ideaService.create(idea);
		 IdeaHistory history = IdeaController.createIdeaHistory(idea, new IdeaHistory(), idea.getIdeaId(), idea.getVersion());
		 ideaHistoryService.create(history);
		 responseJSON.put("status", "success");
		 responseJSON.put("message", "Idea created successfully");
		 
	 	}catch(Exception e){
	 		
	 		e.printStackTrace();
	 		responseJSON.put("status", "error");
			responseJSON.put("message", "Fail to create an idea");
	 	}
	 	return responseJSON;
	 }
	 
	 public static JSONObject submitSolutionSolution(String type, String title, String description, HttpServletRequest httpServletRequest) {
		
		 JSONObject responseJSON = JSONFactoryUtil.createJSONObject();
		 /*add new entry in idea table*/
		 try{ 
			 /*get the default user for web service*/
			 User user = UserLocalServiceUtil.getUserByScreenName(PortalUtil.getCompanyId(httpServletRequest), PropsUtil.get("unicef-web-service-user-name"));

			 /*find the asset category id of C4D Idea Machine*/
			 long solutionProgramme = getAssetCategoryIdByCategoryName("C4D Solutions Generator");
			
			 /*find the value of idea stage*/
			 long solutionStage = getAssetCategoryIdByCategoryName("Backlog");
			 
			 /*find the value of innovation type category*/
			 long solutionSpaceId = getAssetCategoryIdByCategoryName(decodeText(type));
			 
			 Solution solution = new Solution();
			 solution.setCoInventorId(user.getUserId());
			 solution.setCompanyId(user.getCompanyId());
			 solution.setDescription(decodeText(description));
			 solution.setGroupId(user.getGroupId());
			 solution.setModifiedDate(new Date());
			 solution.setSolutionCategory(0);
			 solution.setSolutionProgramme(solutionProgramme);
			 solution.setSolutionStage(solutionStage);
			 solution.setSolutionTitle(decodeText(title));
			 solution.setStatus(IdeaEnum.IDEA_BACKLOG.getValue());
			 solution.setSubmissionDate(new Date());
			 solution.setSpaceId(solutionSpaceId);
			 solutionService.create(solution);
			 
			 responseJSON.put("status", "success");
			 responseJSON.put("message", "Solution created successfully");
		 	}catch(Exception e){
		 		e.printStackTrace();
		 		responseJSON.put("status", "error");
				responseJSON.put("message", "Fail to create a solution");
		 	}
		 	return responseJSON;
	 }
	 
	 public static long getAssetCategoryIdByCategoryName(String categoryName) {
		 long categoryId = 0;
		 List<AssetCategory> assetCategory;
		 DynamicQuery query = DynamicQueryFactoryUtil.forClass(AssetCategory.class);
		 /*query.add(RestrictionsFactoryUtil.like("name", new StringBuilder("%").append(categoryName).append("%").toString()));*/
		 query.add(PropertyFactoryUtil.forName("name").eq(categoryName));
		 try {
			 assetCategory =(List<AssetCategory>) AssetCategoryLocalServiceUtil.dynamicQuery(query);
			 categoryId = assetCategory.get(0).getCategoryId();
		 } catch (SystemException e) {
			log.error("No Category found ");
		 }
		return categoryId;
	}
	private static String decodeText(String hexValue){
		StringBuilder output = new StringBuilder("");
		for (int i = 0; i < hexValue.length(); i += 2){
			String str = hexValue.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
	    }
	   return output.toString();
	}
	
	public static double getFormattedVersion(double version) {
		return Double.valueOf(new DecimalFormat("#.#").format(version));
	}
	
	public static List<IdeaAttachement> findFileEntryIdByIdeaAndVersion(long ideaId, double ideaVersionId) {
		  return ideaAttachmentDAO.findByIdeaIdAndIdeaVersion(ideaId, ideaVersionId);
    }
	
	public static List<Idea> getIdeaList(){
		return ideaService.ideaList();
	}
	
	public static Idea getIdea(long ideaId){
		return ideaService.find(ideaId);
	}
	
	public static String getIdeaFriendlyLayoutURL(){
		if(Validator.isNotNull(IDEA_FRIENDLY_URL_LAYOUT)){
			return IDEA_FRIENDLY_URL_LAYOUT;
		}
		
		return "/idea-machine";
	}
	
	public static Layout getIdeaMachineLayout(long groupId){
		Layout ideaLayout = null;
		try {
			ideaLayout = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, Boolean.TRUE, getIdeaFriendlyLayoutURL());
		} catch (PortalException e) {
			log.error("Error in getting idea layout:"+e.getMessage());
		} catch(SystemException e) {
			log.error("Error in getting idea layout:"+e.getMessage());
		}
		return ideaLayout;
		
	}

	 public static long getAssetCategoryIdByCatNameAndVocName(String vocabularyName, String categoryName) {
		 long vocabularyId = getAssetVocabularyIdByName(vocabularyName);
		 long categoryId = 0;
		 if(vocabularyId > 0){
			 List<AssetCategory> assetCategory;
			 DynamicQuery query = DynamicQueryFactoryUtil.forClass(AssetCategory.class);
			 query.add(PropertyFactoryUtil.forName("name").eq(categoryName));
			 query.add(PropertyFactoryUtil.forName("vocabularyId").eq(vocabularyId));
			 try {
				 assetCategory =(List<AssetCategory>) AssetCategoryLocalServiceUtil.dynamicQuery(query);
				 categoryId = assetCategory.get(0).getCategoryId();
			 } catch (SystemException e) {
				log.error("No Category found ");
			 }
		 }
		 return categoryId;
	}
	 
	 public static long getAssetVocabularyIdByName(String name) {
		 long vocabularyId = 0;
		 List<AssetVocabulary> assetVocabularies;
		 DynamicQuery query = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class);
		 query.add(PropertyFactoryUtil.forName("name").eq(name));
		 try {
			 assetVocabularies =(List<AssetVocabulary>) AssetVocabularyLocalServiceUtil.dynamicQuery(query);
			 vocabularyId = assetVocabularies.get(0).getVocabularyId();
		 } catch (SystemException e) {
			log.error("No Vocabulary found ");
		 }
		return vocabularyId;
	}

	 public static boolean isIdeaNew(int newTHRESHOLD, Date createdDate){
		 
		    Calendar previousCal = Calendar.getInstance();
		    previousCal.add(Calendar.DATE, - newTHRESHOLD);
	        Date fromDate = previousCal.getTime();
	        
	        Calendar fromCal = Calendar.getInstance();
	        Date toDate = fromCal.getTime();
	        
			return CommonUtil.getDateInRange(fromDate, toDate, createdDate);
	 }
	 
	 public static boolean isIdeaHOT(int hotTHRESHOLD, Date createdDate, boolean isIdeaHot, double hotWeight){
		 
		    Calendar previousCal = Calendar.getInstance();
		    previousCal.add(Calendar.DATE, - hotTHRESHOLD);
	        Date fromDate = previousCal.getTime();
	        Calendar fromCal = Calendar.getInstance();
	        Date toDate = fromCal.getTime();
	        
			if(CommonUtil.getDateInRange(fromDate, toDate, createdDate) && isIdeaHot && (hotWeight >= 0.9)){
				return true;
			}
			return false;
	 }
	 
	 public static void updateHotIdeaList(){
		   Calendar currentDate = Calendar.getInstance();
		   Date endDate = currentDate.getTime();
		   
		   currentDate.add(Calendar.DATE, -7);
		   Date startDate = currentDate.getTime();
		   
		   DecimalFormat decimalFormat = new DecimalFormat("#.##");
		   
		   double viewWEIGHT = getViewWEIGHT();
		   double commentWEIGHT = getCommentWEIGHT();
		   double likeWEIGHT = getLikeWEIGHT();
		   List<Idea>ideas = ideaService.getAllIdeas(startDate, endDate);
		  // List<Idea> ideas = ideaService.findAll();
		   if(ideas.size() >= 5){
			   LinkedHashMap<Long, Double>ideaHotList = new LinkedHashMap<Long, Double>();
			   for (Idea idea : ideas) {
				    double ideaView = ideaViewService.getIdeaViewCountByIdeaId(idea.getIdeaId());
				    double ideaComment = ideaCommentService.getIdeaCommentCount(idea.getIdeaId());
				    double ideaLike = likeService.getCountOfIdeaLike(idea.getIdeaId());
				    double ideaHotness = 0;
				    ideaHotness = ((ideaView * viewWEIGHT)/100) + ((ideaComment * commentWEIGHT)/100) + ((ideaLike * likeWEIGHT)/100) ;
				    ideaHotList.put(idea.getIdeaId(), ideaHotness);
			   }
			   
			   LinkedList<Double> ideaList = new LinkedList<Double>(ideaHotList.values());
			  /* boolean isContinue = true;
			   while(isContinue){
				   if(ideaList.getLast() == 0.0){
					   ideaList.removeLast();
				   }else{
					   isContinue = false;
				   }
			   }*/
			   double maxHotness = Collections.max(ideaList);
			   for (Entry<Long, Double> entry : ideaHotList.entrySet()) {
				   	Idea idea = ideaService.find(entry.getKey());
				   	double ideaWeight = 0;
				   	try{
				   		if(maxHotness != 0){
				   			ideaWeight = entry.getValue() / maxHotness;
				   		}
				   	}catch(Exception e){
				   		log.error("Error in generating idea weight:"+e.getMessage());
				   	}
				   	idea.setHotWeight(Double.valueOf(decimalFormat.format(ideaWeight)));
				   	idea.setIdeaHot(Boolean.TRUE);
				   	ideaService.update(idea);
			   } 
		   	}
		  }
		
		public static String getUserImagePathScreenName(String screenName, long companyId) {
			   return "/image/user_portrait?screenName="+screenName+"&companyId="+ companyId;
		}

		public static String getUserImagePath(long userId) {
			  return "/image/user_portrait?img_id="+userId;
		}
		
		public static int getViewWEIGHT(){
			if(Validator.isNotNull(VIEW_WEIGHT)){
				return Integer.parseInt(VIEW_WEIGHT);
			}
			
			return 20;
		}
		
		public static int getLikeWEIGHT(){
			if(Validator.isNotNull(LIKE_WEIGHT)){
				return Integer.parseInt(LIKE_WEIGHT);
			}
			
			return 50;
		}
		
		public static int getCommentWEIGHT(){
			if(Validator.isNotNull(COMMENT_WEIGHT)){
				return Integer.parseInt(COMMENT_WEIGHT);
			}
			
			return 30;
		}
		public static Boolean checkCommentThanks(long commentId, long userId) {
			List<IdeaCommentVote> thankYou = commentVoteService.getListOfComment(commentId, userId);
			
			boolean thanks = true;
			
			if(thankYou.size() == 0){
				thanks= true;
			}else if(thankYou.size() == 1){
				thanks = false;
			}else if(thankYou.size() > 1){
				Date date1 = thankYou.get(0).getVoteDate();
				
				Calendar currentDate = Calendar.getInstance();
				currentDate.setTime(new Date());
				int currentWeek = currentDate.get(Calendar.WEEK_OF_YEAR);
				
				Calendar targetDate = Calendar.getInstance();
				targetDate.setTime(date1);
				int targetWeek = targetDate.get(Calendar.WEEK_OF_YEAR);
				long currentWeekCount = commentVoteService.getCurrentWeekCount(commentId,"commentCount");
				if(currentWeek == targetWeek && currentWeekCount < 2){
					thanks = true;
				}else if(currentWeek != targetWeek && currentWeekCount < 2){
					thanks = true;
				}else{
					thanks=false;
				}
			}
			return thanks;
		}
		
		public static String getAvatarURL(long userId){
			User user = null;
			String avatarURL = StringPool.BLANK;
			try {
				user = UserLocalServiceUtil.getUser(userId);
			} catch (PortalException e) {
				log.error("Error in Getting User in IdeaScrumDiscussion"+e.getMessage());
			} catch (SystemException e) {
				log.error("Error in Getting User in IdeaScrumDiscussion"+e.getMessage());
			}
			if(Validator.isNotNull(user)){
				avatarURL = user.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(user
						.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(
						user.getScreenName(), user.getCompanyId());
			}
			return avatarURL;
		}
		
		public static String getInventorAvatarURL(long ideaId){
			Idea idea = ideaService.find(ideaId);
			
			User user = null;
			String avatarURL = StringPool.BLANK;
			try {
				user = UserLocalServiceUtil.getUser(idea.getCoInventorId());
			} catch (PortalException e) {
				log.error("Error in Getting User in IdeaScrumDiscussion"+e.getMessage());
			} catch (SystemException e) {
				log.error("Error in Getting User in IdeaScrumDiscussion"+e.getMessage());
			}
			if(Validator.isNotNull(user)){
				avatarURL = user.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(user
						.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(
						user.getScreenName(), user.getCompanyId());
			}
			return avatarURL;
		}
		
		public static String getFriendlyURLOfCommunityByIdea(long ideaId){
			String url = StringPool.BLANK;
			if(Validator.isNotNull(ideaId) && ideaId != 0){
				IdeaScrum ideaScrum = ideaService.getIdeaScrumByIdeaId(ideaId);
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
		
		public static long getThanksCount(long ideaId) {
			long thanksCount = 0L;
			try{
				thanksCount = commentVoteService.getCurrentWeekCount(ideaId, "thankYou");
			}catch(Exception e){
				log.error("Error in Getting Thanks Count Of week"+e.getMessage());
			}
			return thanksCount;
		}

		public static double getKudoCount(long ideaId) {
			double avg_like = 0;
			double kudoCount = 0;
			try{
				long weeklyIdeaLikeCount = likeService.getWeeklyCountOfIdeaLike(ideaId);
				double lastWeekLikes = likeService.getTotalLikesOfLastWeek();
				double lastWeekSubmitedIdeas = ideaService.countLastWeekSubmitedIdeas();
				if(lastWeekSubmitedIdeas != 0){
					avg_like = lastWeekLikes / lastWeekSubmitedIdeas;
					double displayCount = 0; 
					displayCount = (weeklyIdeaLikeCount / avg_like) * 0.5;
					if(weeklyIdeaLikeCount <=  (2* avg_like)){
						kudoCount = 1;
					}else{
						kudoCount = displayCount;
					}
				}
			}catch(Exception e){
				log.error("Error in Getting Kudo Count in Idea Util"+e.getMessage());
			}
			return kudoCount;
		}

		public static String getUserName(long userId) {
		   try {
			   //get user name
			User user = UserLocalServiceUtil.getUser(userId);
			return user.getFullName();
		   } catch (PortalException e) {
			log.error("Error in getting user:"+e.getMessage());
		   } catch (SystemException e) {
			log.error("Error in getting user:"+e.getMessage());
		   }
		   
		   return "Guest User";
		}
		
		public static long countPerOfCompletedSprint(Date startDate, Date endDate){
			long diff = 0;
			if(Validator.isNotNull(startDate) && Validator.isNotNull(endDate)){
			if(startDate.getTime() <= new Date().getTime()){
					if(endDate.getTime() <= new  Date().getTime()){			
						return 90;
					}
					
					long totalDayOfSpint = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
					System.out.println("start date time :::"+startDate.getTime()+ "end date time ::::"+endDate.getTime());
					System.out.println("Start Date Time"+ totalDayOfSpint);
					long currentDayOfSprint = (new Date().getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
					System.out.println("Cuurent date Date Time"+ currentDayOfSprint);
					try{
						diff = (currentDayOfSprint * 100) / totalDayOfSpint;
					}catch(Exception e){
						e.getMessage();
					}
				}
			}
			return diff;
		}

		public static String getTabInventorAvtarURL(long sprintId) {
			User user = null;
			String avatarURL = StringPool.BLANK;
			long userId = sprintService.getInventorAvtarFromSprintId(sprintId);
			
			try {
				user = UserLocalServiceUtil.getUser(userId);
			} catch (PortalException e) {
				log.error("Error in Getting User in IdeaScrumDiscussion"+e.getMessage());
			} catch (SystemException e) {
				log.error("Error in Getting User in IdeaScrumDiscussion"+e.getMessage());
			}
			if(Validator.isNotNull(user)){
				avatarURL = user.getPortraitId() > 0 ? IdeaUtil.getUserImagePath(user
						.getPortraitId()) : IdeaUtil.getUserImagePathScreenName(
						user.getScreenName(), user.getCompanyId());
			}
			
			return avatarURL;
		}
}
