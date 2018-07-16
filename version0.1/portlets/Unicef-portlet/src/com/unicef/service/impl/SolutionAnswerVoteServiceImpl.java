package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.SolutionAnswerVoteDAO;
import com.unicef.domain.SolutionAnswerVote;
import com.unicef.service.SolutionAnswerVoteService;

@Service
public class SolutionAnswerVoteServiceImpl extends GenericServiceImpl<SolutionAnswerVote, Long> implements
 SolutionAnswerVoteService{

	private SolutionAnswerVoteDAO solutionAnswerVoteDAO;
	
	@Inject
	public void initSolutionVoteAnswerDAOFactory(SolutionAnswerVoteDAO solutionAnswerVoteDAO) {
		this.solutionAnswerVoteDAO = solutionAnswerVoteDAO;
		setGenericDAOFactory(solutionAnswerVoteDAO);
	}
	@Override
	public SolutionAnswerVote getSolutionAnswerVote(long solutionAnswerId,
			long userId) {
		return solutionAnswerVoteDAO.getSolutionAnswerVote(solutionAnswerId, userId);
	}
	@Override
	public long getCountOfSolutionAnswerVote(long solutionAnswerId) {
		return solutionAnswerVoteDAO.getCountOfSolutionAnswerVote(solutionAnswerId);
	}
	@Override
	public boolean checkSolutionAnswerVote(long solutionAnswerId, long userId) {
		return solutionAnswerVoteDAO.checkSolutionAnswerVote(solutionAnswerId, userId);
	}
	@Override
	public long getWantAnswerCount(long solutionId) {
		return solutionAnswerVoteDAO.getWantAnswerCount(solutionId);
	}
	@Override
	public List<SolutionAnswerVote> getAnsVoteListBySolutionIdAndAnswerId(long solutionId, long answerId) {
		return solutionAnswerVoteDAO.getAnsVoteListBySolutionIdAndAnswerId(solutionId, answerId);
	}
	@Override
	public List<SolutionAnswerVote> getlikebySolutionId(long solutionId) {
		return solutionAnswerVoteDAO.getlikebySolutionId(solutionId);
	}
}
