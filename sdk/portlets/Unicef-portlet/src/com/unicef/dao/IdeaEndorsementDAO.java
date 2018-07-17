package com.unicef.dao;

import java.util.List;

import com.unicef.domain.IdeaEndorsement;
import com.unicef.domain.Like;

public interface IdeaEndorsementDAO extends GenericDAO<IdeaEndorsement,Long>{

	boolean checkIdeaEndorsement(long ideaId, long userId);
	
	List<IdeaEndorsement> getIdeaEndorsement(long ideaId);
	
	long getIdeaEndorsementCount(long ideaId);
	
	IdeaEndorsement getcheckIdeaEndorsement(long ideaId, long userId);
	
	long getWeeklyCountOfIdeaEndorsement(long ideaId);

	long getTotalIdeaEndorsementOfLastWeek();
	
	long gettotalIdeaendorsed();
	
}
