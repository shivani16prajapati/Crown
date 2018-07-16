package com.unicef.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.unicef.dao.SolutionDAO;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.Solution;
import com.unicef.domain.SolutionScrumDiscussion;
import com.unicef.service.SolutionService;

@Service
public class SolutionServiceImpl extends GenericServiceImpl<Solution, Long> implements SolutionService{
	
	private SolutionDAO solutionDAO;
	
	@Inject
	public void initSolutionDAOFactory(SolutionDAO solutionDAO) {
		this.solutionDAO = solutionDAO;
		setGenericDAOFactory(solutionDAO);
	}

	@Override
	public List<Solution> getListofSoultion(long start, int recordsSize,
			String fieldname, String orderBy) {
		return solutionDAO.getListofSoultion(start, recordsSize, fieldname, orderBy);
	}

	@Override
	public void deleteSolutionAnswerBySolutionId(long solutionId) {
		solutionDAO.deleteSolutionAnswerBySolutionId(solutionId);
	}
	@Override
	public void deleteSolutionLikesBySolutionId(long solutionId) {
		solutionDAO.deleteSolutionLikesBySolutionId(solutionId);
	}
	@Override
	public void deleteSolutionCommentsBySolutionId(long solutionId) {
		solutionDAO.deleteSolutionCommentsBySolutionId(solutionId);		
	}
	@Override
	public void deleteSolutionViewBySolutionId(long solutionId) {
		solutionDAO.deleteSolutionViewBySolutionId(solutionId);
	}
	@Override
	public Long noofSolutions() {
		return solutionDAO.noofSolutions();
	}
	@Override
	public Long noOfSolution() {
		return solutionDAO.noOfSolution();
	}
	@Override
	public void deleteSolutionAnswerVoteBySolutionId(long solutionId) {	
		 solutionDAO.deleteSolutionAnswerVoteBySolutionId(solutionId);
	}

	@Override
	public void deleteSolutionAnswerComment(long solutionId) {
		 solutionDAO.deleteSolutionAnswerComment(solutionId);
	}

	@Override
	public List<Solution> solutionList() {
		return solutionDAO.solutionList();
	}

	@Override
	public void deleteSolutionByLikeFromWorkflow(long taskId) {
		solutionDAO.deleteSolutionByLikeFromWorkflow(taskId);
	}

	@Override
	public void deleteSolutionByCommentFromWorkflow(long taskId) {
		solutionDAO.deleteSolutionByCommentFromWorkflow(taskId);
	}

	@Override
	public void deleteSolutionFromWorkflow(long solutionId) {
		solutionDAO.deleteSolutionFromWorkflow(solutionId);
	}
	@Override
	public List<Solution> getAllSolutions(Date startDate, Date endDate) {
		return solutionDAO.getAllSolutions(startDate, endDate);
	}
	
	@Override
	public Solution update(Solution solution) {
		
		solution = solutionDAO.update(solution);
		
		Indexer indexer = IndexerRegistryUtil.getIndexer(solution.getClass());
		if(indexer != null){
			try {
				indexer.reindex(solution);
			} catch (SearchException e) {
				_log.error(e.getMessage(), e);
			}
		}
		return solution;
	}
	
	@Override
	public Long create(Solution solution) {
		
		long solutionId = 0;
		solutionId = solutionDAO.create(solution);
		
		solution = solutionDAO.find(solutionId);
		
		Indexer indexer = IndexerRegistryUtil.getIndexer(solution.getClass());
		if(indexer != null){
			try {
				
				indexer.reindex(solution);
			} catch (SearchException e) {
				_log.error(e.getMessage(), e);
			}
		}
		return solutionId;
	}
	
	@Override
	public void delete(Long ideaId) {
		
		Solution solution = solutionDAO.find(ideaId);
		
		Indexer indexer = IndexerRegistryUtil.getIndexer(solution.getClass());
		if(indexer != null){
			try {
				indexer.delete(solution);
			} catch (SearchException e) {
				_log.error(e.getMessage(), e);
			}
		}
		
		solutionDAO.delete(solution);
	}
	
	private static Log _log = LogFactoryUtil.getLog(SolutionServiceImpl.class);

	@Override
	public long createSolutionScrumDiscussion(
			SolutionScrumDiscussion scrumDiscussion) {
		return solutionDAO.createSolutionScrumDiscussion(scrumDiscussion);
	}

	@Override
	public List<SolutionScrumDiscussion> getChildSolutionScrumDiscussion(
			long solutionScrumId, long solutionId, long sprintId,
			long scrumDiscussionId, int level) {
		return solutionDAO.getChildSolutionScrumDiscussion(solutionScrumId,solutionId,sprintId,scrumDiscussionId,level);
	}

	@Override
	public SolutionScrumDiscussion getSolutionScrumDiscussion(
			long ideaScrumDiscussionId) {
		return solutionDAO.getSolutionScrumDiscussion(ideaScrumDiscussionId);
	}

	@Override
	public void updateSolutionScrumDiscussion(
			SolutionScrumDiscussion parentDiscussion) {
		solutionDAO.updateSolutionScrumDiscussion(parentDiscussion);
	}

	@Override
	public double countLastWeekSubmitedSolution() {
		return solutionDAO.countLastWeekSubmitedSolution();
	}

	@Override
	public IdeaScrum getIdeaScrum(long scopeGroupId) {
		return solutionDAO.getIdeaScrum(scopeGroupId);
	}

}
