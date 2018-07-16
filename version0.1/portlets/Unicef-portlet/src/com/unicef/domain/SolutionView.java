package com.unicef.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "solution_view")
public class SolutionView {

	@Id
	@Column(name = "view_id")
	@GeneratedValue
	private Long viewId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "solution_id", nullable = false)
	private Solution solution;

	@Column(name = "user_id")
	private long userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "view_date")
	private Date viewDate;

	public Long getViewId() {
		return viewId;
	}

	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	
}
