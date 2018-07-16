package com.unicef.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_data")
public class UserData {
	
	@Id
	@Column(name = "userdata_id")
	@GeneratedValue
	private Long userDataId;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="full_name")
	private String full_name;
	
	
	@Column(name="currentRole")
	private String current_role;
	
	@Column(name="lob")
	private String lob;
	
	@Column(name="department")
	private String department;
	
	@Column(name="worksfor")
	private String works_for;
	
	@Column(name="expertise")
	private String expertise;
	
	@Column(name="experience")
	private int experience;
	
	@Column(name="skillSet")
	private String skill_set;
	
	@Column(name="patents")
	private String patents;
	
	@Column(name="forFun")
	private String fun;
	
	@Column(name="user_country")
	private String userCountry;

	
	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public Long getUserDataId() {
		return userDataId;
	}

	public void setUserDataId(Long userDataId) {
		this.userDataId = userDataId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCurrent_role() {
		return current_role;
	}

	public void setCurrent_role(String current_role) {
		this.current_role = current_role;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getWorks_for() {
		return works_for;
	}

	public void setWorks_for(String works_for) {
		this.works_for = works_for;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getSkill_set() {
		return skill_set;
	}

	public void setSkill_set(String skill_set) {
		this.skill_set = skill_set;
	}

	public String getPatents() {
		return patents;
	}

	public void setPatents(String patents) {
		this.patents = patents;
	}

	public String getFun() {
		return fun;
	}

	public void setFun(String fun) {
		this.fun = fun;
	}
	
	

}
