package com.unicef.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.IdeaRateDAO;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaRate;
import com.unicef.domain.IdeaRateCriteria;
import com.unicef.service.IdeaRateService;

@Service
public class IdeaRateServiceImpl extends GenericServiceImpl<IdeaRate, Long> implements IdeaRateService {

	private IdeaRateDAO ideaRateDAO; 
	
	@Inject
	public void initIdeaRateDAOFactory(IdeaRateDAO ideaRateDAO){
		this.ideaRateDAO = ideaRateDAO;
		setGenericDAOFactory(ideaRateDAO);
	}
	
	@Override
	public List<IdeaRateCriteria> getAllIdeaRateCriterias() {
		return ideaRateDAO.getAllIdeaRateCriterias();
	}

	@Override
	public List<IdeaRate> getRateCriteriasbyUser(long userId, long ideaId) {
		return ideaRateDAO.getRateCriteriasbyUser(userId,ideaId);
	}

	@Override
	public IdeaRate findByUserIdIdeaIdAndCriteriaId(long userId, long ideaId,long ideaRateCriteriaId) {
		return ideaRateDAO.findByUserIdIdeaIdAndCriteriaId(userId, ideaId, ideaRateCriteriaId);
	}

	@Override
	public IdeaRateCriteria getIdeaRateCriteriaByCriteriaId(long ideaRateCriteriaId) {
		return ideaRateDAO.getIdeaRateCriteriaByCriteriaId(ideaRateCriteriaId);
	}

	@Override
	public Map<IdeaRateCriteria, List<Double>> findStandardDeviationANDAvergeRatingOfIdea(Idea idea) {
		return ideaRateDAO.findStandardDeviationANDAvergeRatingOfIdea(idea);
	}
	
	@Override
	public Map<String,Double> getIdeaRateByIdeaCriteriaName(String criteria_Name){
		return ideaRateDAO.getIdeaRateByIdeaCriteriaName(criteria_Name);
	}
	
	@Override
	public Map<String,Map<String, Double>> getVotingAverageforIdeaInIdeaScrum(){
		return ideaRateDAO.getVotingAverageforIdeaInIdeaScrum();
	}
}
