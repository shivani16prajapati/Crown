package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.SolutionAnswerDAO;
import com.unicef.domain.SolutionAnswer;
import com.unicef.service.SolutionAnswerService;

@Service
public class SolutionAnswerServiceImpl extends GenericServiceImpl<SolutionAnswer, Long> implements SolutionAnswerService {
	
	private SolutionAnswerDAO solutionAnswerDAO;

	@Inject
	public void initSolutionDAOFactory(SolutionAnswerDAO solutionAnswerDAO) {
	  this.solutionAnswerDAO = solutionAnswerDAO;
	  setGenericDAOFactory(solutionAnswerDAO);
	 }

	@Override
	public List<SolutionAnswer> getSolutionAnswers(long solutionId) {
		return solutionAnswerDAO.getSolutionAnswers(solutionId);
	}
	@Override
	public long getSoutionAnswerCount(long solutionId) {
		return solutionAnswerDAO.getSoutionAnswerCount(solutionId);
	}

	@Override
	public List<SolutionAnswer> getSolutionAnswers(long start, long recordsize,long solutionId) {
		return solutionAnswerDAO.getSolutionAnswers(start,recordsize,solutionId);
	}

}
