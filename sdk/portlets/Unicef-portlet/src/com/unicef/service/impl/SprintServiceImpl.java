package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.SprintDAO;
import com.unicef.domain.Sprint;
import com.unicef.service.SprintService;

@Service
public class SprintServiceImpl extends GenericServiceImpl<Sprint, Long> implements SprintService {

	private SprintDAO sprintDAO;
	
	@Inject
	public void initLikeDAOFactory(SprintDAO sprintDAO) {
		this.sprintDAO = sprintDAO;
		setGenericDAOFactory(sprintDAO);
	}

	@Override
	public List<Sprint> getListofSprint(long start, int recordsSize,
			String fieldname, String orderBy, long ideaId, long ideaScrumId) {
		return sprintDAO.getListofSprint(start, recordsSize, fieldname, orderBy, ideaId, ideaScrumId) ;
	}

	@Override
	public List<Sprint> sprintList() {
		return sprintDAO.sprintList();
	}

	@Override
	public Long noofSprints(long ideaId) {
		return sprintDAO.noofSprints(ideaId);
	}

	@Override
	public List<Sprint> getSprintListByIdeaIdAndIdeaScrumId(long ideaId, long ideaScrumId) {
		return sprintDAO.getSprintListByIdeaIdAndIdeaScrumId(ideaId, ideaScrumId);
	}
	
	@Override
	public Sprint getSprintByOrderIndex(int orderIndex, long ideaId, long ideaScrumId,String type){
		return sprintDAO.getSprintByOrderIndex(orderIndex, ideaId, ideaScrumId,type);
	}

	@Override
	public List<Sprint> getSprintsByAfterOrderIndex(int orderIndex, long ideaId, long ideaScrumId) {
		return sprintDAO.getSprintsByAfterOrderIndex(orderIndex, ideaId, ideaScrumId);
	}

	@Override
	public List<Sprint> getSprintsByIdeaId(long ideaId) {
		return sprintDAO.getSprintsByIdeaId(ideaId);
	}

	@Override
	public long getInventorAvtarFromSprintId(long sprintId) {
		return sprintDAO.getInventorAvtarFromSprintId(sprintId);
	}

	@Override
	public Sprint findNextSprintFromOrderIndex(int nextSprintOrder,
			long ideaId, long ideaScrumId) {
		return sprintDAO.findNextSprintFromOrderIndex(nextSprintOrder,ideaId,ideaScrumId);
	}

	@Override
	public Sprint findPreviousPrintFromOrderIndex(int previousSprintOrder,
			long ideaId, long ideaScrumId) {
		return sprintDAO.findPreviousPrintFromOrderIndex(previousSprintOrder,ideaId,ideaScrumId);
	}

	@Override
	public List<Sprint> getSprintListBySolutionIdAndSolutionScrumId(
			long solutionId, long groupId) {
		return sprintDAO.getSprintListBySolutionIdAndSolutionScrumId(solutionId,groupId);
	}

	@Override
	public List<Sprint> getListofSolutionSprint(long start, int recordsSize,
			String fieldname, String orderBy, long solutionId,
			long solutionScrumId) {
		return sprintDAO.getListofSolutionSprint(start,recordsSize,fieldname,orderBy,solutionId,solutionScrumId);
	}

	@Override
	public List<Sprint> getSolutionSprintsByAfterOrderIndex(int orderIndex,
			long solutionId, long solutionScrumId) {
		return sprintDAO.getSolutionSprintsByAfterOrderIndex(orderIndex,solutionId,solutionScrumId);
	}

	@Override
	public Sprint findPreviousSolutionSPrintFromOrderIndex(
			int sPreviousSprintOrder, long solutionId, long solutionScrumId) {
		return sprintDAO.findPreviousSolutionSPrintFromOrderIndex(sPreviousSprintOrder,solutionId,solutionScrumId);
	}

	@Override
	public Sprint findSolutionNextSprintFromOrderIndex(int nextSprintOrder,
			long solutionId, long solutionScrumId) {
		return sprintDAO.findSolutionNextSprintFromOrderIndex(nextSprintOrder,solutionId,solutionScrumId);
	}

	@Override
	public List<Sprint> getSprintsByOrderIndex(int orderIndex, long ideaId,
			long ideaScrumId, String type) {
		return sprintDAO.getSprintsByOrderIndex(orderIndex, ideaId, ideaScrumId, type);
	}

	
}
