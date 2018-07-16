package com.unicef.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.unicef.dao.IdeaDAO;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.IdeaScrumDiscussion;
import com.unicef.service.IdeaService;

/**
 * <p>
 * This class is a implementation of IdeaService and has methods to access
 * {@link IdeaDAO} methods.
 * </p>
 * 
 * @author Divyang Patel
 */
@Service
public class IdeaServiceImpl extends GenericServiceImpl<Idea, Long> implements IdeaService {
	
	private IdeaDAO ideaDAO;

	@Inject
	public void initIdeaDAOFactory(IdeaDAO ideaDAO) {
		this.ideaDAO = ideaDAO;
		setGenericDAOFactory(ideaDAO);
	}
	
	@Override
	public List<Idea> getListOfIdea(long start, int recordsSize,
			String fieldname, String orderBy) {
		return ideaDAO.getListOfIdea(start, recordsSize, fieldname, orderBy);
	}
	
	@Override
	public long getIdeaAttachementCountByIdeaId(Long ideaId, double ideaVersion) {
		return ideaDAO.getIdeaAttachementCountByIdeaId(ideaId, ideaVersion);
	}
	@Override
	public void deleteIdeaAttachementByIdeaId(long ideaId) {
		 ideaDAO.deleteIdeaAttachementByIdeaId(ideaId);
	}
	@Override
	public List<IdeaAttachement> getIdeaAttachementByIdeaId(long ideaId) {
		return ideaDAO.getIdeaAttachementByIdeaId(ideaId);
	}
	@Override
	public Long noofIdeas() {
		return ideaDAO.noofIdeas();
	}
	@Override
	public void deleteIdeaLikesByIdeaId(long ideaId) {
		 ideaDAO.deleteIdeaLikesByIdeaId(ideaId);
	}
	@Override
	public void deleteIdeaCommentsByIdeaId(long ideaId) {
		ideaDAO.deleteIdeaCommentsByIdeaId(ideaId);
	}
	@Override
	public void deleteIdeaViewByIdeaId(long ideaId) {
		ideaDAO.deleteIdeaViewByIdeaId(ideaId);
	}
	@Override
	public void deleteIdeaHistory(long ideaId) {
		ideaDAO.deleteIdeaHistory(ideaId);
	}
	@Override
	public List<Idea> ideaList() {
		return ideaDAO.ideaList();
	}
	@Override
	public void createIdeaScrum(IdeaScrum scrum) {
		ideaDAO.createIdeaScrum(scrum);
	}
	
	@Override
	public Idea update(Idea idea) {
		
		idea = ideaDAO.update(idea);
		
		Indexer indexer = IndexerRegistryUtil.getIndexer(idea.getClass());
		if(indexer != null){
			try {
				indexer.reindex(idea);
			} catch (SearchException e) {
				_log.error(e.getMessage(), e);
			}
		}
		return idea;
	}
	
	@Override
	public Long create(Idea idea) {
		
		long ideaId = 0;
		ideaId = ideaDAO.create(idea);
		
		idea = ideaDAO.find(ideaId);
		
		Indexer indexer = IndexerRegistryUtil.getIndexer(idea.getClass());
		if(indexer != null){
			try {
				
				indexer.reindex(idea);
			} catch (SearchException e) {
				_log.error(e.getMessage(), e);
			}
		}
		return ideaId;
	}
	
	@Override
	public void delete(Long ideaId) {
		
		Idea idea = ideaDAO.find(ideaId);
		
		Indexer indexer = IndexerRegistryUtil.getIndexer(idea.getClass());
		if(indexer != null){
			try {
				indexer.delete(idea);
			} catch (SearchException e) {
				_log.error(e.getMessage(), e);
			}
		}
		
		ideaDAO.delete(idea);
	}
	
	/*@Override
	public List<Idea> listOfIdea(long start, long recordsize) {
		return ideaDAO.listOfIdea(start,recordsize);
	}*/
	
	@Override
	public IdeaScrum getIdeaScrum(long scopeGroupId) {
		return ideaDAO.getIdeaScrum(scopeGroupId);
	}
	@Override
	public List<Idea> getAllIdeas(Date startDate, Date endDate) {
		return ideaDAO.getAllIdeas(startDate, endDate);
	}
	@Override
	public void deleteIdeaFromWorkflow(long ideaId) {
		ideaDAO.deleteIdeaFromWorkflow(ideaId);
	}
	@Override
	public List<Idea> getAllHotIdeas() {
		return ideaDAO.getAllHotIdeas();
	}
	@Override
	public void deleteWorkflowLikeFromTaskId(long taskId) {
		ideaDAO.deleteWorkflowLikeFromTaskId(taskId);
	}
	@Override
	public void deleteWorkflowCommentFromTaskId(long taskId) {
		ideaDAO.deleteWorkflowCommentFromTaskId(taskId);
	}
	@Override
	public List<Idea> getLatestHotIdeas() {
		return ideaDAO.getLatestHotIdeas();
	}

	@Override
	public void deleteIdeaCommentVoteByIdeaId(long ideaId) {
		ideaDAO.deleteIdeaCommentVoteByIdeaId(ideaId);
	}
	
	@Override
	public List<IdeaScrumDiscussion> getIdeaScrumDiscussion(long ideaScrumId, long ideaId, long sprintId) {
		return ideaDAO.getIdeaScrumDiscussion(ideaScrumId, ideaId, sprintId);
	}
	
	@Override
	public Long createIdeaScrumDiscussion(IdeaScrumDiscussion ideaScrumDiscussion){
		return ideaDAO.createIdeaScrumDiscussion(ideaScrumDiscussion);
	}
	
	@Override
	public List<Idea> getListOfIdeaByCurrentUser(long userId) {
		return ideaDAO.getListOfIdeaByCurrentUser(userId);
	}
	@Override
	public long countTotalIdeas() {
		return ideaDAO.countTotalIdeas();
	}
	
	@Override
	public long countLastWeekSubmitedIdeas() {
		return ideaDAO.countLastWeekSubmitedIdeas();
	}
	private static Log _log = LogFactoryUtil.getLog(IdeaServiceImpl.class);

	@Override
	public IdeaScrum getIdeaScrumByIdeaId(long ideaId) {
		return ideaDAO.getIdeaScrumByIdeaId(ideaId);
	}
	
	@Override
	public List<Idea> getListOfPromotedIdeas() {
		return ideaDAO.getListOfPromotedIdeas();
	}
	
	@Override
	public List<IdeaScrumDiscussion> getChildIdeaScrumDiscussion(long ideaScrumId, long ideaId, long sprintId, long parentDiscussionId, int level) {
		return ideaDAO.getChildIdeaScrumDiscussion(ideaScrumId, ideaId, sprintId, parentDiscussionId, level);
	}
	
	@Override
	public IdeaScrumDiscussion getIdeaScrumDiscussion(long scrumDiscussionId){
		return ideaDAO.getIdeaScrumDiscussion(scrumDiscussionId);
	}
	
	@Override
	public void updateIdeaScrumDiscussion(IdeaScrumDiscussion ideaScrumDiscussion){
		ideaDAO.updateIdeaScrumDiscussion(ideaScrumDiscussion);
	}
	@Override
	public IdeaScrum getIdeaScrumBySolutionId(long solutionId) {
		return ideaDAO.getIdeaScrumBySolutionId(solutionId);
	}
	@Override
	public List<Idea> getSortedIdeaHomeList(int start, int recordsize) {
		return ideaDAO.getSortedIdeaHomeList(start,recordsize);
	}
	@Override
	public List<IdeaAttachement> getLatestIdeaAttachementByIdeaId(long ideaId) {
		return ideaDAO.getLatestIdeaAttachementByIdeaId(ideaId);
	}
	@Override
	public List<Idea> getIdeasOfLastYear() {
		return ideaDAO.getIdeasOfLastYear();
	}
	@Override
	public List<Idea> getIdeasByMonth(Date ideaCreatedDate) {
		return ideaDAO.getIdeasByMonth(ideaCreatedDate);
	}
	@Override
	public List<Idea> getIdeasByDay(Date submissionDate) {
		return ideaDAO.getIdeasByDay(submissionDate);
	}
	@Override
	public long getIdeaCountByStage(long categoryId) {
		return ideaDAO.getIdeaCountByStage(categoryId);
	}
	@Override
	public List<Idea> getLeaderBoardHotIdeas() {
		return ideaDAO.getLeaderBoardHotIdeas();
	}
	@Override
	public long countIdeasByType(long categoryId,String type) {
		return ideaDAO.countIdeasByType(categoryId,type);
	}
	
	@Override
	public LinkedHashMap<String, Integer> getAllIdeasSubmissionDate(){
		return ideaDAO.getAllIdeasSubmissionDate();
	}

	@Override
	public long countByIdeaTypeandUserId(long ideaTypeId, long userId) {
		return ideaDAO.countByIdeaTypeandUserId(ideaTypeId, userId);
	}

	@Override
	public long countByIdeaLineofbusinessandUserId(long lofBussiness,
			long userId) {
		
		return ideaDAO.countByIdeaLineofbusinessandUserId(lofBussiness, userId);
	}

	@Override
	public List<Idea> getideabyuser(long userId) {
		
		return ideaDAO.getideabyuser(userId);
	}

	@Override
	public float hotideaBymonthByuser(long userId, String date) {
		
		return ideaDAO.hotideaBymonthByuser(userId,date);
	}

	@Override
	public List<Idea> getLeaderBoardIdea() {
		
		return ideaDAO.getLeaderBoardIdea();
	}

	@Override
	public List<Idea> getSortedByHotnessHomeList(int start, int recordsize) {
		return ideaDAO.getSortedByHotnessHomeList(start, recordsize);
	}

	@Override
	public long getIdeaCountByMonthWiseAndYear(String year) {
		return ideaDAO.getIdeaCountByMonthWiseAndYear(year);
	}


	@Override
	public long countByIdeaTypeByYear(String startdate, String enddate,
			long ideaTypeId) {
		
		return ideaDAO.countByIdeaTypeByYear(startdate, enddate, ideaTypeId);
	}

	@Override
	public long countByIdeaLineofbusinessByYear(String startdate,
			String enddate, long lofBussiness) {	
		return ideaDAO.countByIdeaLineofbusinessByYear(startdate, enddate, lofBussiness);
	}

	@Override
	public long getAllIdeaByNewStage(long ideaStageId) {
		return ideaDAO.getAllIdeaByNewStage(ideaStageId);
	}

	@Override
	public long getHeadquaterIdeaBymonthwise(String headquater, String date) {
		
		return ideaDAO.getHeadquaterIdeaBymonthwise(headquater,date);
	}

	@Override
	public long getRegionalIdeaBymonthwise(String headquater, String date) {
		return ideaDAO.getRegionalIdeaBymonthwise(headquater,date);
	}

	@Override
	public long countLastWeekSubmitedIdeasByuser(long userId) {
		
		return ideaDAO.countLastWeekSubmitedIdeasByuser(userId);
	}

	@Override
	public Idea getIdeaByIdeaNam(String ideaName) {
		return ideaDAO.getIdeaByIdeaNam(ideaName);
	}

	@Override
	public List<Idea> gettoptenidea() {
		return ideaDAO.gettoptenidea();
	}

	@Override
	public List<Idea> getideaHotnessByPagiantion(int start, int end) {
		return ideaDAO.getideaHotnessByPagiantion(start, end);
	}
	

	
}
