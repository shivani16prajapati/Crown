package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.IdeaAttachementDAO;
import com.unicef.domain.IdeaAttachement;
import com.unicef.service.IdeaAttachementService;

@Service
public class IdeaAttachementServiceImpl  extends GenericServiceImpl<IdeaAttachement, Long> implements IdeaAttachementService{

	private IdeaAttachementDAO ideaAttachementDAO;
	
	@Inject
	public void initIdeaAttachementDAOFactory(IdeaAttachementDAO ideaAttachementDAO){
		this.ideaAttachementDAO = ideaAttachementDAO;
		setGenericDAOFactory(ideaAttachementDAO);
	}

	@Override
	public List<IdeaAttachement> findByIdeaIdAndIdeaVersion(long ideaId,
			double ideaVersionId) {
		return ideaAttachementDAO.findByIdeaIdAndIdeaVersion(ideaId, ideaVersionId);
	}

}
