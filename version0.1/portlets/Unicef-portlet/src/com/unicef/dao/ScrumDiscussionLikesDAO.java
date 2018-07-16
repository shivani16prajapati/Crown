/**
 * 
 */
package com.unicef.dao;

import com.unicef.domain.Scrum_Discussion_Like;

/**
 * @author minddeft007
 *
 */
public interface ScrumDiscussionLikesDAO extends
		GenericDAO<Scrum_Discussion_Like, Long> {
	
	public Scrum_Discussion_Like checkUserIsLiked(long scrumDissionId, long userId);
	
}
