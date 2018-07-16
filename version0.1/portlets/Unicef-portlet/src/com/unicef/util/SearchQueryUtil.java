package com.unicef.util;

import static com.unicef.portlet.search.SearchConstants.*;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.unicef.constants.Constants;
import com.unicef.domain.Idea;
import com.unicef.domain.Solution;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SearchQueryUtil {

	private static Log _log = LogFactoryUtil.getLog(SearchQueryUtil.class);
	public static final String SOURCE_DATE_FORMAT_PATTERN  = "MM/dd/yyyy";
	public static final SimpleDateFormat sdfSource = new SimpleDateFormat(SOURCE_DATE_FORMAT_PATTERN, Locale.getDefault());
	public static final SimpleDateFormat indexerDateFormat = new SimpleDateFormat(PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));
	
	public static BooleanQuery createSourceBooleanQuery(String[] sourceFields, SearchContext searchContext){
		BooleanQuery sourceQuery = BooleanQueryFactoryUtil.create(searchContext);
		
		try {
			if(sourceFields.length > 0){
				for(int i=0;i<sourceFields.length;i++){
					if(sourceFields[i].trim().equals(IDEAS)){
						
						BooleanQuery ideasQuery = BooleanQueryFactoryUtil.create(searchContext);
						ideasQuery.addTerm(Field.ENTRY_CLASS_NAME, Idea.class.getName());
						sourceQuery.add(ideasQuery, StringPool.BLANK);
						
					}else if(sourceFields[i].trim().equals(SOLUTIONS)){
						
						BooleanQuery solutionsQuery = BooleanQueryFactoryUtil.create(searchContext);
						solutionsQuery.addTerm(Field.ENTRY_CLASS_NAME, Solution.class.getName());
						sourceQuery.add(solutionsQuery, StringPool.BLANK);
					
					}else if(sourceFields[i].trim().equals(REPOSITORY)){
						
						BooleanQuery repositoryQuery = BooleanQueryFactoryUtil.create(searchContext);
						
						BooleanQuery documentQuery = BooleanQueryFactoryUtil.create(searchContext);
						documentQuery.addTerm(Field.ENTRY_CLASS_NAME, DLFileEntry.class.getName());
						
						BooleanQuery statusQuery = BooleanQueryFactoryUtil.create(searchContext);
						statusQuery.addTerm(Field.STATUS, 0);
						
						BooleanQuery hiddenQuery = BooleanQueryFactoryUtil.create(searchContext);
						hiddenQuery.addTerm(Field.HIDDEN, Boolean.FALSE.toString());
						
						repositoryQuery.add(documentQuery, BooleanClauseOccur.MUST);
						repositoryQuery.add(statusQuery, BooleanClauseOccur.MUST);
						repositoryQuery.add(hiddenQuery, BooleanClauseOccur.MUST);
						
						sourceQuery.add(repositoryQuery, StringPool.BLANK);
						
					}else if(sourceFields[i].trim().equals(WEBSITE)){
						
						BooleanQuery websiteQuery = BooleanQueryFactoryUtil.create(searchContext);
						websiteQuery.addTerm(Field.ENTRY_CLASS_NAME, BlogsEntry.class.getName());
						
						sourceQuery.add(websiteQuery, StringPool.BLANK);
						
					}
				}
			}
		}catch(Exception e){
			_log.error("Error in creating source query:"+e.getMessage());
		}
		return sourceQuery;
	}
	
	public static BooleanQuery createTypeBooleanQuery(String[] typeFields, SearchContext searchContext){
		BooleanQuery typeQuery = BooleanQueryFactoryUtil.create(searchContext);
		
		try {
			if(typeFields.length > 0){
				for(int i=0;i<typeFields.length;i++){
					if(typeFields[i].trim().equals(A_NEW_PRODUCT)){
						
						long ideaTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(INNOVATION_TYPE,typeFields[i].trim());
						long solutionTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_SPACE,typeFields[i].trim());
						
						BooleanQuery newProductQuery = BooleanQueryFactoryUtil.create(searchContext);
						newProductQuery.addTerm(Constants.INNOVATION_TYPE, ideaTypeId);
						newProductQuery.addTerm(Constants.SOLUTION_SPACE_ID, solutionTypeId);
						
						typeQuery.add(newProductQuery, StringPool.BLANK);
					
					}else if(typeFields[i].trim().equals(A_NEW_SERVICE)){
						
						long ideaTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(INNOVATION_TYPE,typeFields[i].trim());
						long solutionTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_SPACE,typeFields[i].trim());
						
						BooleanQuery newServiceQuery = BooleanQueryFactoryUtil.create(searchContext);
						newServiceQuery.addTerm(Constants.INNOVATION_TYPE, ideaTypeId);
						newServiceQuery.addTerm(Constants.SOLUTION_SPACE_ID, solutionTypeId);
						
						typeQuery.add(newServiceQuery, StringPool.BLANK);
					
					}else if(typeFields[i].trim().equals(GOOD_PRACTICES)){
						
						long ideaTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(INNOVATION_TYPE,typeFields[i].trim());
						long solutionTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_SPACE,typeFields[i].trim());
						
						BooleanQuery goodPracticesQuery = BooleanQueryFactoryUtil.create(searchContext);
						goodPracticesQuery.addTerm(Constants.INNOVATION_TYPE, ideaTypeId);
						goodPracticesQuery.addTerm(Constants.SOLUTION_SPACE_ID, solutionTypeId);
						
						typeQuery.add(goodPracticesQuery, StringPool.BLANK);
					
					}else if(typeFields[i].trim().equals(IMPROVING_HOW_WE_WORK)){
						
						long ideaTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(INNOVATION_TYPE,typeFields[i].trim());
						long solutionTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_SPACE,typeFields[i].trim());
						
						BooleanQuery weWorkQuery = BooleanQueryFactoryUtil.create(searchContext);
						weWorkQuery.addTerm(Constants.INNOVATION_TYPE, ideaTypeId);
						weWorkQuery.addTerm(Constants.SOLUTION_SPACE_ID, solutionTypeId);
						
						typeQuery.add(weWorkQuery, StringPool.BLANK);
					
					}else if(typeFields[i].trim().equals(LESSONS_LEARNED)){
						
						long ideaTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(INNOVATION_TYPE,typeFields[i].trim());
						long solutionTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_SPACE,typeFields[i].trim());
						
						BooleanQuery lessionsQuery = BooleanQueryFactoryUtil.create(searchContext);
						lessionsQuery.addTerm(Constants.INNOVATION_TYPE, ideaTypeId);
						lessionsQuery.addTerm(Constants.SOLUTION_SPACE_ID, solutionTypeId);
						
						typeQuery.add(lessionsQuery, StringPool.BLANK);
					
					}else if(typeFields[i].trim().equals(SOCIAL_INNOVATION)){
						
						long ideaTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(INNOVATION_TYPE,typeFields[i].trim());
						long solutionTypeId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_SPACE,typeFields[i].trim());
						
						BooleanQuery innovationQuery = BooleanQueryFactoryUtil.create(searchContext);
						innovationQuery.addTerm(Constants.INNOVATION_TYPE, ideaTypeId);
						innovationQuery.addTerm(Constants.SOLUTION_SPACE_ID, solutionTypeId);
						
						typeQuery.add(innovationQuery, StringPool.BLANK);
					}
				}
			}
		}catch(Exception e){
			_log.error("Error in creating type query:"+e.getMessage());
		}
		return typeQuery;
	}
	
	public static BooleanQuery createStageBooleanQuery(String[] stageFields, SearchContext searchContext){
		BooleanQuery stageQuery = BooleanQueryFactoryUtil.create(searchContext);
		
		try {
			if(stageFields.length > 0){
				for(int i=0;i<stageFields.length;i++){
					if(stageFields[i].trim().equals(BACKLOG)){
						
						long ideaStageId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(STAGE,"Idea "+ stageFields[i].trim());
						long solutionStageId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_STAGE,stageFields[i].trim());
						
						BooleanQuery backlogQuery = BooleanQueryFactoryUtil.create(searchContext);
						backlogQuery.addTerm(Constants.IDEA_STAGE, ideaStageId);
						backlogQuery.addTerm(Constants.SOLUTION_STAGE, solutionStageId);
						
						stageQuery.add(backlogQuery, StringPool.BLANK);
					
					}else if(stageFields[i].trim().equals(ARCHIVED)){
						
						long ideaStageId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(STAGE,stageFields[i].trim());
						long solutionStageId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_STAGE,stageFields[i].trim());
						
						BooleanQuery archivedQuery = BooleanQueryFactoryUtil.create(searchContext);
						archivedQuery.addTerm(Constants.IDEA_STAGE, ideaStageId);
						archivedQuery.addTerm(Constants.SOLUTION_STAGE, solutionStageId);
						
						stageQuery.add(archivedQuery, StringPool.BLANK);
					
					}else if(stageFields[i].trim().equals(DEVELOPING)){
						
						long ideaStageScrumId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(STAGE,IDEASCRUM);
						long ideaStageIncubationId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(STAGE, INCUBATION);
						long ideaStagePilotId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(STAGE, PILOT);
						long solutionStageId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_STAGE, PROMOTED_TO_SOLUTIONSCRUM);
						
						BooleanQuery developingQuery = BooleanQueryFactoryUtil.create(searchContext);
						developingQuery.addTerm(Constants.IDEA_STAGE, ideaStageScrumId);
						developingQuery.addTerm(Constants.IDEA_STAGE, ideaStageIncubationId);
						developingQuery.addTerm(Constants.IDEA_STAGE, ideaStagePilotId);
						developingQuery.addTerm(Constants.SOLUTION_STAGE, solutionStageId);
						
						stageQuery.add(developingQuery, StringPool.BLANK);
					
					}else if(stageFields[i].trim().equals(DEPLOYED)){
						
						long ideaStageId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(STAGE,stageFields[i].trim());
						long solutionStageId = IdeaUtil.getAssetCategoryIdByCatNameAndVocName(SOLUTION_STAGE, ANSWER_WIKI);
						
						BooleanQuery developedQuery = BooleanQueryFactoryUtil.create(searchContext);
						developedQuery.addTerm(Constants.IDEA_STAGE, ideaStageId);
						developedQuery.addTerm(Constants.SOLUTION_STAGE, solutionStageId);
						
						stageQuery.add(developedQuery, StringPool.BLANK);
						
						
					}
				}
			}
		}catch(Exception e){
			_log.error("Error in creating stage query:"+e.getMessage());
		}
		return stageQuery;
	}
	public static BooleanQuery createDateRangeBooleanQuery(String strFromDate, String strToDate, SearchContext searchContext){
		BooleanQuery dateRangeQuery = BooleanQueryFactoryUtil.create(searchContext);
		
		try {
			 if(Validator.isNotNull(strFromDate) && Validator.isNotNull(strToDate)) {
				
	             Date fromDate =  sdfSource.parse(strFromDate);
	             Calendar fromDateCalendar = CalendarFactoryUtil.getCalendar();
	             fromDateCalendar.setTime(fromDate);
	             fromDate = fromDateCalendar.getTime();
	             
	             Date toDate = sdfSource.parse(strToDate);
	             
	             Calendar toDateCalendar = CalendarFactoryUtil.getCalendar();
	             toDateCalendar.setTime(toDate);
	             toDate = toDateCalendar.getTime();
	             
	             String fromDateStr = indexerDateFormat.format(fromDate);        
	             
	             String toDateStr = indexerDateFormat.format(toDate);
	             
	             BooleanQuery statusQuery = BooleanQueryFactoryUtil.create(searchContext);
				 statusQuery.addTerm(Field.STATUS, 0);
					
				 BooleanQuery hiddenQuery = BooleanQueryFactoryUtil.create(searchContext);
				 hiddenQuery.addTerm(Field.HIDDEN, Boolean.FALSE.toString());
					
	             dateRangeQuery.addRangeTerm(Field.CREATE_DATE, Long.valueOf(fromDateStr).longValue(), Long.valueOf(toDateStr).longValue());
	             dateRangeQuery.add(statusQuery, BooleanClauseOccur.MUST);
	             hiddenQuery.add(statusQuery, BooleanClauseOccur.MUST);
			}
			 System.out.println("dateRangeQuery:"+dateRangeQuery.toString());
		}catch(Exception e){
			_log.error("Error in creating date range query:"+e.getMessage());
		}
		return dateRangeQuery;
	}

	public static BooleanQuery createIncludeTagsBooleanQuery(String[] includeTags, SearchContext searchContext){
		BooleanQuery includeTagsQuery = BooleanQueryFactoryUtil.create(searchContext);
		
		try {
			if(includeTags.length > 0){
					BooleanQuery query = BooleanQueryFactoryUtil
							.create(searchContext);
					for (String tagId : includeTags) {
						query.addTerm(Constants.TAG_IDS, Long.parseLong(tagId.trim()));
					}
					includeTagsQuery.add(query, StringPool.BLANK);
			}
		}catch(Exception e){
			_log.error("Error in creating include tags query:"+e.getMessage());
		}
		return includeTagsQuery;
	}
	
	public static BooleanQuery createExcludeTagsBooleanQuery(String[] excludeTags, SearchContext searchContext){
		BooleanQuery excludeTagsQuery = BooleanQueryFactoryUtil.create(searchContext);
		
		try {
			if(excludeTags.length > 0){
					BooleanQuery query = BooleanQueryFactoryUtil
							.create(searchContext);
					for (String tagId : excludeTags) {
						query.addTerm(Constants.TAG_IDS, Long.parseLong(tagId.trim()));
					}
					excludeTagsQuery.add(query, StringPool.BLANK);
			}
		}catch(Exception e){
			_log.error("Error in creating exclude tags query:"+e.getMessage());
		}
		return excludeTagsQuery;
	}
	public static BooleanQuery createHotIdeasBooleanQuery(String[] hotIdeasFields, SearchContext searchContext){
		BooleanQuery hotIdeasQuery = BooleanQueryFactoryUtil.create(searchContext);
	        
		try {
			if(hotIdeasFields.length > 0){
		             
				for(int i=0;i<hotIdeasFields.length;i++){
					 if(hotIdeasFields[i].trim().equals(HOT_IDEAS_ONLY)){
						 BooleanQuery query = BooleanQueryFactoryUtil.create(searchContext);
						 query.addTerm(Constants.HOT, 1);
						
						 hotIdeasQuery.add(query, BooleanClauseOccur.MUST);
						 
						 Calendar previousCal = Calendar.getInstance();
					     previousCal.add(Calendar.DATE, - 7);
				         Date fromDate = previousCal.getTime();
				        
				         Calendar fromCal = Calendar.getInstance();
				         Date toDate = fromCal.getTime();
				         String fromDateStr = indexerDateFormat.format(fromDate);        
			             
			             String toDateStr = indexerDateFormat.format(toDate);
						
						 BooleanQuery dateRangeQuery = BooleanQueryFactoryUtil.create(searchContext);
			             dateRangeQuery.addRangeTerm(Field.CREATE_DATE, Long.valueOf(fromDateStr).longValue(), Long.valueOf(toDateStr).longValue());
			             
			             hotIdeasQuery.add(dateRangeQuery, BooleanClauseOccur.MUST);
			             
			             BooleanQuery hotWeightQuery = BooleanQueryFactoryUtil.create(searchContext);
			             hotWeightQuery.addRangeTerm(Constants.HOT_WEIGHT, "0.9", "1.0");
			            
			             hotIdeasQuery.add(hotWeightQuery, BooleanClauseOccur.MUST);
					 }
				}
			}
		}catch(Exception e){
			_log.error("Error in creating hot query:"+e.getMessage());
		}
		return hotIdeasQuery;
	}
}