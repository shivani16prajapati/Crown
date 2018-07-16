package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.IdeaEndorsementDAO;
import com.unicef.domain.IdeaEndorsement;
import com.unicef.service.IdeaEndorsementService;

@Service
public class IdeaEndorsementServiceImpl extends GenericServiceImpl<IdeaEndorsement,Long> implements IdeaEndorsementService{

	private IdeaEndorsementDAO ideaEndorsementDAO;
	
	@Inject
	public void initIdeaEndorsementDAOFactory(IdeaEndorsementDAO ideaEndorsementDAO) {
		this.ideaEndorsementDAO = ideaEndorsementDAO;
		setGenericDAOFactory(ideaEndorsementDAO);
	}
	
	@Override
	public List<IdeaEndorsement> getIdeaEndorsement(long ideaId) {
		return ideaEndorsementDAO.getIdeaEndorsement(ideaId);
	}

	@Override
	public long getIdeaEndorsementCount(long ideaId) {
		// TODO Auto-generated method stub
		return ideaEndorsementDAO.getIdeaEndorsementCount(ideaId);
	}

	@Override
	public boolean checkIdeaEndorsement(long ideaId, long userId) {
		return ideaEndorsementDAO.checkIdeaEndorsement(ideaId, userId);
	}

	@Override
	public IdeaEndorsement getcheckIdeaEndorsement(long ideaId, long userId) {
		return ideaEndorsementDAO.getcheckIdeaEndorsement(ideaId, userId);
	}

	@Override
	public long getWeeklyCountOfIdeaEndorsement(long ideaId) {
		return ideaEndorsementDAO.getWeeklyCountOfIdeaEndorsement(ideaId);
	}

	@Override
	public long getTotalIdeaEndorsementOfLastWeek() {
		return ideaEndorsementDAO.getTotalIdeaEndorsementOfLastWeek();
	}

	@Override
	public long gettotalIdeaendorsed() {
		return ideaEndorsementDAO.gettotalIdeaendorsed();
	}

	

}
