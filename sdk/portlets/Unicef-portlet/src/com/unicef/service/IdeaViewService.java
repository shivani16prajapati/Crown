package com.unicef.service;

import com.unicef.domain.IdeaView;

public interface IdeaViewService extends GenericService<IdeaView, Long> {
	
	long getIdeaViewCountByUserId(long ideaId, long userId);

	long getIdeaViewCountByIdeaId(long ideaId);
}
