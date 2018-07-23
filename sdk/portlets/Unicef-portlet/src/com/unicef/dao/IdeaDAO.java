package com.unicef.dao;

import com.unicef.domain.Idea;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.IdeaScrumDiscussion;
import com.unicef.portlet.NewDashboard.ideaTypeChartModel;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Divyang Patel
 */
public interface IdeaDAO extends GenericDAO<Idea, Long> {
	
	List<Idea> getListOfIdea(long start, int recordsSize, String fieldname, String orderBy);

	long getIdeaAttachementCountByIdeaId(Long ideaId,double ideaVersion);

	public void deleteIdeaAttachementByIdeaId(long ideaId);

	List<IdeaAttachement> getIdeaAttachementByIdeaId(long ideaId);

	public Long noofIdeas();

	void deleteIdeaLikesByIdeaId(long ideaId);

	void deleteIdeaCommentsByIdeaId(long ideaId);

	void deleteIdeaViewByIdeaId(long ideaId);

	void deleteIdeaHistory(long ideaId);

	public List<Idea> ideaList();
	
	public List<Idea> getUnSubmittedIdeasInPython();

	void createIdeaScrum(IdeaScrum scrum);

	IdeaScrum getIdeaScrum(long scopeGroupId);

	List<Idea> getAllIdeas(Date startDate, Date endDate);
	
	public void deleteIdeaFromWorkflow(long ideaId);

	List<Idea> getAllHotIdeas();

	public void deleteWorkflowLikeFromTaskId(long taskId);

	public void deleteWorkflowCommentFromTaskId(long taskId);

	List<Idea> getLatestHotIdeas();

	public void deleteIdeaCommentVoteByIdeaId(long ideaId);

	List<Idea> getListOfIdeaByCurrentUser(long userId);

	public List<IdeaScrumDiscussion> getIdeaScrumDiscussion(long scopeGroupId, long ideaId, long sprintId);
	
	public Long createIdeaScrumDiscussion(IdeaScrumDiscussion ideaScrumDiscussion);
	
	public long countTotalIdeas();

	public long countLastWeekSubmitedIdeas();
	
	public long countLastWeekSubmitedIdeasByuser(long userId);

	public IdeaScrum getIdeaScrumByIdeaId(long ideaId);

	public List<Idea> getListOfPromotedIdeas();

	List<IdeaScrumDiscussion> getChildIdeaScrumDiscussion(long ideaScrumId, long ideaId, long sprintId, long parentDiscussionId,int level);

	IdeaScrumDiscussion getIdeaScrumDiscussion(long scrumDiscussionId);
	
	public void updateIdeaScrumDiscussion(IdeaScrumDiscussion ideaScrumDiscussion);

	public IdeaScrum getIdeaScrumBySolutionId(long solutionId);

	List<Idea> getSortedIdeaHomeList(int start, int recordsize);
	
	public List<Idea> getSortedByHotnessHomeList(int start, int recordsize);
	
	List<IdeaAttachement> getLatestIdeaAttachementByIdeaId(long ideaId);

	List<Idea> getIdeasOfLastYear();

	List<Idea> getIdeasByMonth(Date ideaCreatedDate);

	List<Idea> getIdeasByDay(Date submissionDate);

	public long getIdeaCountByStage(long categoryId);

	List<Idea> getLeaderBoardHotIdeas();

	 public long countIdeasByType(long categoryId,String type);
	 
	 public LinkedHashMap<String, Integer> getAllIdeasSubmissionDate();
	 
	 public long countByIdeaTypeandUserId(long ideaTypeId,long userId);
	 
	 public long countByIdeaLineofbusinessandUserId(long lofBussiness,long userId);
	 
	 List<Idea> getideabyuser(long userId);
	 
	 public float hotideaBymonthByuser(long userId,String date);
	 
	 public List<Idea> getLeaderBoardIdea();
	 
	 long getIdeaCountByMonthWiseAndYear(String year);
	 
	 public long countByIdeaTypeByYear(String startdate,String enddate,long ideaTypeId);
	 
	 public long countByIdeaLineofbusinessByYear(String startdate,String enddate,long lofBussiness);
	 
	 
	 long getAllIdeaByNewStage(long ideaStageId);
	 
	 long getHeadquaterIdeaBymonthwise(String headquater,String date);
	 
	 long getRegionalIdeaBymonthwise(String headquater,String date);
	 
	 Idea getIdeaByIdeaNam(String ideaName);
	 
	 List<Idea> gettoptenidea();
	 
	 List<Idea> getideaHotnessByPagiantion(int start,int end);
	 
	}