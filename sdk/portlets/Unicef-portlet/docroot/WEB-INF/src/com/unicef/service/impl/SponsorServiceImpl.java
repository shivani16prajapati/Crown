package com.unicef.service.impl;

import com.unicef.dao.SponsorDAO;
import com.unicef.domain.Sponsor;
import com.unicef.service.SponsorService;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class SponsorServiceImpl extends GenericServiceImpl<Sponsor, Long> implements SponsorService {

	private SponsorDAO sponsorDAO;
	
	@Inject
	public void initSponsorDAOFactory(SponsorDAO sponsorDAO) {
		this.sponsorDAO = sponsorDAO;
		setGenericDAOFactory(sponsorDAO);
	}

	@Override
	public Sponsor findSponsorByUserId(long id) {
		return sponsorDAO.findSponsorByUserId(id);
	}

	@Override
	public void deleteFromMappingTable(long sponsorId) {
		 sponsorDAO.deleteFromMappingTable(sponsorId);
	}
	
	
}
