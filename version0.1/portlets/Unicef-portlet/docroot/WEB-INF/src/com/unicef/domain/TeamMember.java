package com.unicef.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "team_member" )
public class TeamMember {
	
	@Id
	@Column(name="team_member_id")
	@GeneratedValue
	private Long teamMemberId;
	
	@Column(name="idea_id")
	private Long ideaId;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="role_name")
	private String roleName;

	public Long getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(Long teamMemberId) {
		this.teamMemberId = teamMemberId;
	}

	public Long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
