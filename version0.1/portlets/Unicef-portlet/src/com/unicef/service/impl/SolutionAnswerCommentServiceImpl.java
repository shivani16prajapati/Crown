package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.SolutionAnswerCommentDAO;
import com.unicef.domain.SolutionAnswerComment;
import com.unicef.service.SolutionAnswerCommentService;

@Service
public class SolutionAnswerCommentServiceImpl extends GenericServiceImpl<SolutionAnswerComment, Long> implements SolutionAnswerCommentService{

	private SolutionAnswerCommentDAO solutionAnswerCommentDAO;
	
	@Inject
	public void initSolutionAnswerCommentDAOFactory(SolutionAnswerCommentDAO solutionAnswerCommentDAO) {
		this.solutionAnswerCommentDAO = solutionAnswerCommentDAO;
		setGenericDAOFactory(solutionAnswerCommentDAO);
	}

	@Override
	public List<SolutionAnswerComment> getListOfComment(
			long solutionAnswerId) {
		
		return solutionAnswerCommentDAO.getListOfComment(solutionAnswerId);
	}

	@Override
	public long getCountOfAnswerComment(long solutionAnswerId) {
		// TODO Auto-generated method stub
		return solutionAnswerCommentDAO.getCountOfAnswerComment(solutionAnswerId);
	}
}
