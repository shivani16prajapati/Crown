package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.UserDataDAO;
import com.unicef.domain.UserData;
import com.unicef.service.UserDataService;

@Service
public class UserDataServiceImpl extends GenericServiceImpl<UserData,Long> implements UserDataService{

	
	private UserDataDAO userDataDAO;
	
	@Inject
	public void initIdeaDAOFactory(UserDataDAO userDataDAO) {
		this.userDataDAO = userDataDAO;
		setGenericDAOFactory(userDataDAO);
	}
	
	@Override
	public UserData getuserData(long userId) {
		return userDataDAO.getuserData(userId);
	}

	@Override
	public List<UserData> getUserDataByIdeaUser() {
		return userDataDAO.getUserDataByIdeaUser();
	}

}
