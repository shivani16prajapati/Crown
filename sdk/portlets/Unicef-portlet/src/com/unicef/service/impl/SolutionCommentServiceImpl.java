package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.SolutionCommentDAO;
import com.unicef.domain.SolutionComment;
import com.unicef.service.SolutionCommentService;

@Service
public class SolutionCommentServiceImpl extends GenericServiceImpl<SolutionComment, Long>  implements SolutionCommentService{
	
	private SolutionCommentDAO solutionCommentDAO;
	@Inject
	public void initSolutionCommentDAOFactory(SolutionCommentDAO solutionCommentDAO) {
		this.solutionCommentDAO = solutionCommentDAO;
		setGenericDAOFactory(solutionCommentDAO);
	}
	@Override
	public List<SolutionComment> getListOfSolutionComment(long solutionId) {
		return solutionCommentDAO.getListOfSolutionComment(solutionId);
	}
	@Override
	public long getSolutionCommentCount(long solutionId) {
		return solutionCommentDAO.getSolutionCommentCount(solutionId);
	}

}
