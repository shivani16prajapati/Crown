package com.unicef.service.impl;

import com.unicef.dao.PivotRiskUtilityDAO;
import com.unicef.domain.PivotRiskUtility;
import com.unicef.service.PivotRiskUtilityService;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class PivotRiskUtilityServiceImpl extends GenericServiceImpl<PivotRiskUtility, Long> implements PivotRiskUtilityService{

	private PivotRiskUtilityDAO pivotRiskUtilityDAO;
	@Inject
	public void initPivotRiskUtilityDAOFactory(PivotRiskUtilityDAO pivotRiskUtilityDAO) {
		this.pivotRiskUtilityDAO = pivotRiskUtilityDAO;
		setGenericDAOFactory(pivotRiskUtilityDAO);
	}
}
