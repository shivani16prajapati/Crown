package com.unicef.service;

import java.util.List;

import com.unicef.domain.UserData;

public interface UserDataService extends GenericService<UserData,Long>{
	
	UserData getuserData(long userId);
	
	public List<UserData> getUserDataByIdeaUser() ;

}
