package com.unicef.dao;

import java.util.List;

import com.unicef.domain.UserData;

public interface UserDataDAO extends GenericDAO<UserData,Long> {
	
	UserData getuserData(long userId);
	
	List<UserData> getUserDataByIdeaUser();

}
