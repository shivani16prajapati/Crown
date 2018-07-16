package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.unicef.dao.TeamMemberDao;
import com.unicef.dao.impl.TeamMemberDaoImpl;
import com.unicef.domain.TeamMember;
import com.unicef.service.TeamMemberService;

@Service
public class TeamMemberServiceImpl extends GenericServiceImpl<TeamMember, Long> implements TeamMemberService {
	
	private TeamMemberDao teamMemberDao;
	private static final Log _log = LogFactoryUtil.getLog(TeamMemberDaoImpl.class);
	
	@Inject
	public void initIdeaDAOFactory(TeamMemberDao teamMemberDao) {
		this.teamMemberDao=teamMemberDao;
		setGenericDAOFactory(teamMemberDao);
	}
	
	@Override
	public List<TeamMember> findByUserId(long userId, long ideaId) {
		return teamMemberDao.findByUserId(userId, ideaId);
	}

	@Override
	public List<TeamMember> findMembersByIdeaId(long ideaId) {
		return teamMemberDao.findMembersByIdeaId(ideaId);
	}

	/*	@Override
	public void deleteTeammember(long ideaId) {
		
		teamMemberDao.deleteTeamMember(ideaId);
		
		List<TeamMember> findMembersByIdeaId = findMembersByIdeaId(ideaId);
	//	TeamMember t = teamMemberDao.find(ideaId);
		for(TeamMember t : findMembersByIdeaId){
			_log.error(t.toString());
		teamMemberDao.delete(t);  
		}*/
	}
	
	
