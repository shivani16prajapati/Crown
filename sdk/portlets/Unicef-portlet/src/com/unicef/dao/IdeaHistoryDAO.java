package com.unicef.dao;

import com.unicef.domain.IdeaHistory;

import java.util.List;

public interface IdeaHistoryDAO extends GenericDAO<IdeaHistory, Long> {

	List<IdeaHistory> getIdeaHistoryList(long ideaId);

}
