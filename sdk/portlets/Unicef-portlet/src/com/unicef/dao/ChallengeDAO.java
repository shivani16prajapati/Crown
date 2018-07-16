package com.unicef.dao;

import com.unicef.domain.LaunchAChallenge;

import java.util.List;

public interface ChallengeDAO extends GenericDAO<LaunchAChallenge, Long> {

	List<LaunchAChallenge> getListOfChalleneges(long start, int recordsSize);

}
