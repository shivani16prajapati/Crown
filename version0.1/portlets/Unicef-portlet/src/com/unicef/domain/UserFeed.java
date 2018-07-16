package com.unicef.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "user_feed")
public class UserFeed {
	
	@Id
	@Column(name = "userfeed_id")
	@GeneratedValue
	private Long userFeedId;
		
	@Column(name="user_id")
	private long userId;
	
	@Column(name="feedtype_id")
	private long feedtypeId;
	
	@Column(name="refer_id")
	private long referId;
	
	@Column(name="byuser_id")
	private long byuserId;
	
	public long getByuserId() {
		return byuserId;
	}

	public void setByuserId(long byuserId) {
		this.byuserId = byuserId;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	public Long getUserFeedId() {
		return userFeedId;
	}

	public void setUserFeedId(Long userFeedId) {
		this.userFeedId = userFeedId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getFeedtypeId() {
		return feedtypeId;
	}

	public void setFeedtypeId(long feedtypeId) {
		this.feedtypeId = feedtypeId;
	}



	public long getReferId() {
		return referId;
	}

	public void setReferId(long referId) {
		this.referId = referId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void SetUserFeed(long userId, long feedtypeId, long referId, long byuserId,
			Date createdDate) {
		this.userId = userId;
		this.feedtypeId = feedtypeId;
		this.referId = referId;
		this.byuserId = byuserId;
		this.createdDate = createdDate;
	}


	
	
	
	

}
