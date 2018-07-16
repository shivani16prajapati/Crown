package com.unicef.dao;

import com.unicef.domain.Idea;
import com.unicef.domain.IdeaRate;
import com.unicef.domain.IdeaRateCriteria;

import java.util.List;
import java.util.Map;

public interface IdeaRateDAO extends GenericDAO<IdeaRate, Long> {

	List<IdeaRateCriteria> getAllIdeaRateCriterias();

	List<IdeaRate> getRateCriteriasbyUser(long userId, long ideaId);

	IdeaRate findByUserIdIdeaIdAndCriteriaId(long userId, long ideaId,long ideaRateCriteriaId);

	IdeaRateCriteria getIdeaRateCriteriaByCriteriaId(long ideaRateCriteriaId);

	Map<IdeaRateCriteria, List<Double>> findStandardDeviationANDAvergeRatingOfIdea(Idea idea);
	
	Map<String,Double> getIdeaRateByIdeaCriteriaName(String criteria_Name);
	
	Map<String,Map<String, Double>> getVotingAverageforIdeaInIdeaScrum();
}
