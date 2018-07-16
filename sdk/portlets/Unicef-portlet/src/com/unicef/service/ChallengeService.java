package com.unicef.service;

import com.unicef.domain.LaunchAChallenge;

import java.util.List;

public interface ChallengeService extends GenericService<LaunchAChallenge, Long> {

	List<LaunchAChallenge> getListOfChalleneges(long start, int recordsSize);

}
