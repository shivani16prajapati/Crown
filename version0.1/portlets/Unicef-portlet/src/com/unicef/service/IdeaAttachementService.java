package com.unicef.service;

import com.unicef.domain.IdeaAttachement;

import java.util.List;

public interface IdeaAttachementService extends GenericService<IdeaAttachement, Long>{
	
	List<IdeaAttachement> findByIdeaIdAndIdeaVersion(long ideaId, double ideaVersionId);
}
