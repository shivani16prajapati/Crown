package com.unicef.service;

import com.unicef.domain.Sponsor;

public interface SponsorService extends GenericService<Sponsor, Long> {

	Sponsor findSponsorByUserId(long id);

	public void deleteFromMappingTable(long sponsorId);

}
