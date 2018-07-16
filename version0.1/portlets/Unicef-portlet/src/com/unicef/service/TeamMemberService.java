package com.unicef.service;

import com.unicef.domain.TeamMember;

import java.util.List;

public interface TeamMemberService extends GenericService<TeamMember, Long>{
	
	public List<TeamMember> findByUserId(long userId, long ideaId);
	
	public List<TeamMember> findMembersByIdeaId(long ideaId);
	
	//public void deleteTeammember(long ideaId);
}
