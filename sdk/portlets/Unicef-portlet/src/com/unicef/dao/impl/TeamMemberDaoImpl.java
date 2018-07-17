package com.unicef.dao.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.unicef.dao.TeamMemberDao;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.TeamMember;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class TeamMemberDaoImpl extends GenericDAOImpl<TeamMember, Long> implements TeamMemberDao {

	private static final Log _log = LogFactoryUtil.getLog(TeamMemberDaoImpl.class);

	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<TeamMember> findByUserId(long userId, long ideaId) {
		_log.error("Inside find Team Member by user id and Idea id ");
		Session session = sessionFactory.getCurrentSession();
		return (List<TeamMember>) session
				.createSQLQuery(
						"Select * from team_member where user_id="
								+ userId + " and idea_id="+ideaId).addEntity(TeamMember.class)
				.list();
	}

	@Override
	public List<TeamMember> findMembersByIdeaId(long ideaId) {
		_log.error("Inside find Team Member by idea id ");
		Session session = sessionFactory.getCurrentSession();
		return (List<TeamMember>) session
				.createSQLQuery(
						"Select * from team_member where idea_id="
								+ ideaId).addEntity(TeamMember.class)
				.list();
	}

	@Override
	public void deleteTeamMember(long ideaId) {
		_log.error("Inside delete Team Member by idea id ");
		Session session = sessionFactory.getCurrentSession();
		session.createQuery("delete from team_member where idea_id="+ideaId).executeUpdate();
		
	}

}
