package com.unicef.service;

import com.unicef.domain.IdeaHistory;

import java.util.List;

public interface IdeaHistoryService  extends GenericService<IdeaHistory, Long>{
	
	List<IdeaHistory> getIdeaHistoryList(long ideaId);
}
