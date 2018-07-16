package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.IdeaHistoryDAO;
import com.unicef.domain.IdeaHistory;
import com.unicef.service.IdeaHistoryService;

@Service
public class IdeaHistoryServiceImpl  extends GenericServiceImpl<IdeaHistory, Long> implements IdeaHistoryService{
	
	private IdeaHistoryDAO ideaHistoryDAO;
	@Inject
	public void initIdeaHistoryDAOFactory(IdeaHistoryDAO ideaHistoryDAO) {
		this.ideaHistoryDAO = ideaHistoryDAO;
		setGenericDAOFactory(ideaHistoryDAO);
	}
	@Override
	public List<IdeaHistory> getIdeaHistoryList(long ideaId) {
		return ideaHistoryDAO.getIdeaHistoryList(ideaId);
	}

}
