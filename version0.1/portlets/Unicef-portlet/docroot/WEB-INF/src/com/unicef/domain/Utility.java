package com.unicef.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Utility")
public class Utility {
	@Id
	@Column(name = "user_id")
	private long user_id;
	
	@Column(name = "Technical_Merit") 
	private double Technical_Merit;
	
	@Column(name = "Market_Opportunity")
	private double Market_Opportunity;
	
	@Column(name = "Strategic_Alignment")
	private double Strategic_Alignment;
	
	@Column(name = "Time_To_Market")
	private double Time_To_Market;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "save_date")
	private Date saveDate;
	
	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
	
	public double getTechnical_Merit() {
		return Technical_Merit;
	}

	public void setTechnical_Merit(double technical_Merit) {
		Technical_Merit = technical_Merit;
	}

	public double getStrategic_Alignment() {
		return Strategic_Alignment;
	}

	public void setStrategic_Alignment(double strategic_Alignment) {
		Strategic_Alignment = strategic_Alignment;
	}

	public long getUser_id() {
		return user_id;
	}

	public double getMarket_Opportunity() {
		return Market_Opportunity;
	}

	public void setMarket_Opportunity(double market_Opportunity) {
		Market_Opportunity = market_Opportunity;
	}

	public double getTime_To_Market() {
		return Time_To_Market;
	}

	public void setTime_To_Market(double time_To_Market) {
		Time_To_Market = time_To_Market;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
}
