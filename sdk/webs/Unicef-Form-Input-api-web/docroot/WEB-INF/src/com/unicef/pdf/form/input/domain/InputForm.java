package com.unicef.pdf.form.input.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name="input_form")
public class InputForm {
	
	@Id
	@Column(name = "input_id")
	@GeneratedValue
	private long inputId;

	@Column(name = "prim_participant")
	@Type(type = "text")
	private String prim_participant;
	
	@Column(name = "sec_participant")
	@Type(type = "text")
	private String sec_participant;
	
	@Column(name = "ter_participant")
	@Type(type = "text")
	private String ter_participant;
	
	@Column(name = "prim_CurrentBehavior")
	@Type(type = "text")
	private String prim_CurrentBehavior;
	
	@Column(name = "sec_CurrentBehavior")
	@Type(type = "text")
	private String sec_CurrentBehavior;
	
	@Column(name = "ter_CurrentBehavior")
	@Type(type = "text")
	private String ter_CurrentBehavior;
	
	@Column(name = "prim_RecomBehavior")
	@Type(type = "text")
	private String prim_RecomBehavior;
	
	@Column(name = "sec_RecomBehavior")
	@Type(type = "text")
	private String sec_RecomBehavior;
	
	@Column(name = "ter_RecomBehavior")
	@Type(type = "text")
	private String ter_RecomBehavior;
	
	@Column(name = "prim_KeyBarrier")
	@Type(type = "text")
	private String prim_KeyBarrier;
	
	@Column(name = "sec_KeyBarrier")
	@Type(type = "text")
	private String sec_KeyBarrier;
	
	@Column(name = "ter_KeyBarrier")
	@Type(type = "text")
	private String ter_KeyBarrier;
	
	@Column(name = "prim_OtherBarrier")
	@Type(type = "text")
	private String prim_OtherBarrier;
	
	@Column(name = "sec_OtherBarrier")
	@Type(type = "text")
	private String sec_OtherBarrier;
	
	@Column(name = "ter_OtherBarrier")
	@Type(type = "text")
	private String ter_OtherBarrier;
	
	@Column(name = "prim_benefit")
	@Type(type = "text")
	private String prim_benefit;
	
	@Column(name = "sec_benefit")
	@Type(type = "text")
	private String sec_benefit;
	
	@Column(name = "ter_benefit")
	@Type(type = "text")
	private String ter_benefit;
	
	@Column(name = "prim_practice")
	@Type(type = "text")
	private String prim_practice;
	
	@Column(name = "sec_practice")
	@Type(type = "text")
	private String sec_practice;
	
	@Column(name = "ter_practice")
	@Type(type = "text")
	private String ter_practice;
	
	@Column(name = "prim_support")
	@Type(type = "text")
	private String prim_support;
	
	@Column(name = "sec_support")
	@Type(type = "text")
	private String sec_support;
	
	@Column(name = "ter_support")
	@Type(type = "text")
	private String ter_support;

	public long getInputId() {
		return inputId;
	}

	public void setInputId(long inputId) {
		this.inputId = inputId;
	}

	public String getPrim_participant() {
		return prim_participant;
	}

	public void setPrim_participant(String prim_participant) {
		this.prim_participant = prim_participant;
	}

	public String getSec_participant() {
		return sec_participant;
	}

	public void setSec_participant(String sec_participant) {
		this.sec_participant = sec_participant;
	}

	public String getTer_participant() {
		return ter_participant;
	}

	public void setTer_participant(String ter_participant) {
		this.ter_participant = ter_participant;
	}

	public String getPrim_CurrentBehavior() {
		return prim_CurrentBehavior;
	}

	public void setPrim_CurrentBehavior(String prim_CurrentBehavior) {
		this.prim_CurrentBehavior = prim_CurrentBehavior;
	}

	public String getSec_CurrentBehavior() {
		return sec_CurrentBehavior;
	}

	public void setSec_CurrentBehavior(String sec_CurrentBehavior) {
		this.sec_CurrentBehavior = sec_CurrentBehavior;
	}

	public String getTer_CurrentBehavior() {
		return ter_CurrentBehavior;
	}

	public void setTer_CurrentBehavior(String ter_CurrentBehavior) {
		this.ter_CurrentBehavior = ter_CurrentBehavior;
	}

	public String getPrim_RecomBehavior() {
		return prim_RecomBehavior;
	}

	public void setPrim_RecomBehavior(String prim_RecomBehavior) {
		this.prim_RecomBehavior = prim_RecomBehavior;
	}

	public String getSec_RecomBehavior() {
		return sec_RecomBehavior;
	}

	public void setSec_RecomBehavior(String sec_RecomBehavior) {
		this.sec_RecomBehavior = sec_RecomBehavior;
	}

	public String getTer_RecomBehavior() {
		return ter_RecomBehavior;
	}

	public void setTer_RecomBehavior(String ter_RecomBehavior) {
		this.ter_RecomBehavior = ter_RecomBehavior;
	}

	public String getPrim_KeyBarrier() {
		return prim_KeyBarrier;
	}

	public void setPrim_KeyBarrier(String prim_KeyBarrier) {
		this.prim_KeyBarrier = prim_KeyBarrier;
	}

	public String getSec_KeyBarrier() {
		return sec_KeyBarrier;
	}

	public void setSec_KeyBarrier(String sec_KeyBarrier) {
		this.sec_KeyBarrier = sec_KeyBarrier;
	}

	public String getTer_KeyBarrier() {
		return ter_KeyBarrier;
	}

	public void setTer_KeyBarrier(String ter_KeyBarrier) {
		this.ter_KeyBarrier = ter_KeyBarrier;
	}

	public String getPrim_OtherBarrier() {
		return prim_OtherBarrier;
	}

	public void setPrim_OtherBarrier(String prim_OtherBarrier) {
		this.prim_OtherBarrier = prim_OtherBarrier;
	}

	public String getSec_OtherBarrier() {
		return sec_OtherBarrier;
	}

	public void setSec_OtherBarrier(String sec_OtherBarrier) {
		this.sec_OtherBarrier = sec_OtherBarrier;
	}

	public String getTer_OtherBarrier() {
		return ter_OtherBarrier;
	}

	public void setTer_OtherBarrier(String ter_OtherBarrier) {
		this.ter_OtherBarrier = ter_OtherBarrier;
	}

	public String getPrim_benefit() {
		return prim_benefit;
	}

	public void setPrim_benefit(String prim_benefit) {
		this.prim_benefit = prim_benefit;
	}

	public String getSec_benefit() {
		return sec_benefit;
	}

	public void setSec_benefit(String sec_benefit) {
		this.sec_benefit = sec_benefit;
	}

	public String getTer_benefit() {
		return ter_benefit;
	}

	public void setTer_benefit(String ter_benefit) {
		this.ter_benefit = ter_benefit;
	}

	public String getPrim_practice() {
		return prim_practice;
	}

	public void setPrim_practice(String prim_practice) {
		this.prim_practice = prim_practice;
	}

	public String getSec_practice() {
		return sec_practice;
	}

	public void setSec_practice(String sec_practice) {
		this.sec_practice = sec_practice;
	}

	public String getTer_practice() {
		return ter_practice;
	}

	public void setTer_practice(String ter_practice) {
		this.ter_practice = ter_practice;
	}

	public String getPrim_support() {
		return prim_support;
	}

	public void setPrim_support(String prim_support) {
		this.prim_support = prim_support;
	}

	public String getSec_support() {
		return sec_support;
	}

	public void setSec_support(String sec_support) {
		this.sec_support = sec_support;
	}

	public String getTer_support() {
		return ter_support;
	}

	public void setTer_support(String ter_support) {
		this.ter_support = ter_support;
	}
	
	
	
	
}
