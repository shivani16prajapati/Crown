package com.unicef.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.IdeaViewDAO;
import com.unicef.domain.IdeaView;
import com.unicef.service.IdeaViewService;

@Service
public class IdeaViewServiceImpl extends GenericServiceImpl<IdeaView, Long> implements IdeaViewService { 

	private IdeaViewDAO ideaViewDAO;
	
	@Inject
	public void initIdeaWikiDAOFactory(IdeaViewDAO ideaViewDAO) {
		this.ideaViewDAO = ideaViewDAO;
		setGenericDAOFactory(ideaViewDAO);
	}

	@Override
	public long getIdeaViewCountByUserId(long ideaId, long userId) {
		return ideaViewDAO.getIdeaViewCountByUserId(ideaId, userId);
	}
	@Override
	public long getIdeaViewCountByIdeaId(long ideaId) {
		return ideaViewDAO.getIdeaViewCountByIdeaId(ideaId);
	}
}
