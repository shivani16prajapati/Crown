package com.unicef.dao;

import com.unicef.domain.Idea;
import com.unicef.domain.IdeaScrum;
import com.unicef.domain.IdeaScrumDiscussion;
import com.unicef.domain.Solution;
import com.unicef.domain.SolutionScrumDiscussion;

import java.util.Date;
import java.util.List;

public interface SolutionDAO extends GenericDAO<Solution, Long>{

	List<Solution> getListofSoultion(long start, int recordsSize,
			String fieldname, String orderBy);


	public void deleteSolutionAnswerBySolutionId(long solutionId);
	
	public void deleteSolutionLikesBySolutionId(long solutionId);

	public void deleteSolutionCommentsBySolutionId(long solutionId);

	public void deleteSolutionViewBySolutionId(long solutionId);
	 
	public Long noofSolutions();

	public Long noOfSolution();

	public void deleteSolutionAnswerVoteBySolutionId(long solutionId);

	public void deleteSolutionAnswerComment(long solutionId);
	
	public List<Solution> solutionList();

	public void deleteSolutionByLikeFromWorkflow(long taskId);

	public void deleteSolutionByCommentFromWorkflow(long taskId);

	public void deleteSolutionFromWorkflow(long solutionId);
	
	List<Solution> getAllSolutions(Date startDate, Date endDate);

	public long createSolutionScrumDiscussion(SolutionScrumDiscussion scrumDiscussion);

	List<SolutionScrumDiscussion> getChildSolutionScrumDiscussion(long solutionScrumId, long solutionId, long sprintId,long scrumDiscussionId, int level);


	SolutionScrumDiscussion getSolutionScrumDiscussion(
			long ideaScrumDiscussionId);


	public void updateSolutionScrumDiscussion(SolutionScrumDiscussion parentDiscussion);


	IdeaScrum getIdeaScrum(long scopeGroupId);


	double countLastWeekSubmitedSolution();
}
