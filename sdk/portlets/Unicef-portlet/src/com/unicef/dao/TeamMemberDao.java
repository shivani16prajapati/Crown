package com.unicef.dao;

import com.unicef.domain.TeamMember;

import java.util.List;

public interface TeamMemberDao extends GenericDAO<TeamMember, Long>{
	
	public List<TeamMember> findByUserId(long userId, long ideaId);
	
	public List<TeamMember> findMembersByIdeaId(long ideaId);
	
	public void deleteTeamMember(long ideaId);
	
}
