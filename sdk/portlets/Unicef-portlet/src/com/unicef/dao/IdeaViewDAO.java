package com.unicef.dao;

import com.unicef.domain.IdeaView;

public interface IdeaViewDAO extends GenericDAO<IdeaView, Long> {

	long getIdeaViewCountByUserId(long ideaId, long userId);

	long getIdeaViewCountByIdeaId(long ideaId);

}
