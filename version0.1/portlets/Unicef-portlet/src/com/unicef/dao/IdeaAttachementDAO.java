package com.unicef.dao;

import com.unicef.domain.IdeaAttachement;

import java.util.List;

public interface IdeaAttachementDAO extends GenericDAO<IdeaAttachement, Long> {
	
	List<IdeaAttachement> findByIdeaIdAndIdeaVersion(long ideaId, double ideaVersionId);
}
