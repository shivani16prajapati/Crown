package com.unicef.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.UtilityDAO;
import com.unicef.domain.Utility;
import com.unicef.service.UtilityService;

@Service
public class UtilityServiceImpl extends GenericServiceImpl<Utility, Long> implements UtilityService{
	
	private UtilityDAO utilityDAO;

	@Inject
	public void initUtilDAOFactory(UtilityDAO utilityDAO) {
		this.utilityDAO = utilityDAO;
		setGenericDAOFactory(utilityDAO);
	}
}
