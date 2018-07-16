package com.unicef.dao;

import com.unicef.domain.Sprint;

import java.util.List;

public interface SprintDAO extends GenericDAO<Sprint, Long> {

	public List<Sprint> getListofSprint(long start, int recordsSize, String fieldname, String orderBy, long ideaId, long ideaScrumId);
	
	public List<Sprint> sprintList();
	
	public Long noofSprints(long ideaId);

	public List<Sprint> getSprintListByIdeaIdAndIdeaScrumId(long ideaId, long ideaScrumId);

	public Sprint getSprintByOrderIndex(int orderIndex, long ideaId, long ideaScrumId,String type);

	public List<Sprint> getSprintsByOrderIndex(int orderIndex,long ideaId, long ideaScrumId,String type);
	
	public List<Sprint> getSprintsByAfterOrderIndex(int orderIndex, long ideaId, long ideaScrumId);

	public List<Sprint> getSprintsByIdeaId(long ideaId);

	public long getInventorAvtarFromSprintId(long sprintId);

	public Sprint findNextSprintFromOrderIndex(int nextSprintOrder,long ideaId, long ideaScrumId);

	public Sprint findPreviousPrintFromOrderIndex(int previousSprintOrder,long ideaId, long ideaScrumId);

	public List<Sprint> getSprintListBySolutionIdAndSolutionScrumId(
			long solutionId, long groupId);

	public List<Sprint> getListofSolutionSprint(long start, int recordsSize,
			String fieldname, String orderBy, long solutionId,
			long solutionScrumId);

	public List<Sprint> getSolutionSprintsByAfterOrderIndex(int orderIndex,
			long solutionId, long solutionScrumId);

	public Sprint findPreviousSolutionSPrintFromOrderIndex(
			int sPreviousSprintOrder, long solutionId, long solutionScrumId);

	public Sprint findSolutionNextSprintFromOrderIndex(int nextSprintOrder,
			long solutionId, long solutionScrumId);
}
