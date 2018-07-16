package com.unicef.service.impl;

import com.unicef.dao.ChallengeDAO;
import com.unicef.domain.LaunchAChallenge;
import com.unicef.service.ChallengeService;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class ChallengeServiceImpl extends GenericServiceImpl<LaunchAChallenge, Long> implements ChallengeService {

	private ChallengeDAO challengeDAO;
	
	@Inject
	public void initIdeaDAOFactory(ChallengeDAO challengeDAO) {
		this.challengeDAO = challengeDAO;
		setGenericDAOFactory(challengeDAO);
	}

	@Override
	public List<LaunchAChallenge> getListOfChalleneges(long start,
			int recordsSize) {
		return challengeDAO.getListOfChalleneges(start,recordsSize);
	}
}
