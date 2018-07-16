package com.unicef.dao;

import com.unicef.domain.Sponsor;

public interface SponsorDAO extends GenericDAO<Sponsor, Long>  {

	Sponsor findSponsorByUserId(long id);

	void deleteFromMappingTable(long sponsorId);


}
