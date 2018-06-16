package org.jejadle.retreat.core.vo;

import java.util.List;

public class RetreatVO {
	
	int retreatId=0;
	
	String program1;
	
	String discount1;
	String discount2;
	
	int offeringAmount=0;
	
	String transferType;
	String transferContent1;
	String transferContent2;
	
	String remarks;

	List<PersonVO> persons;
	
	List<String> meals;
	

	public String getProgram1() {
		return program1;
	}

	public void setProgram1(String program1) {
		this.program1 = program1;
	}

	public String getDiscount1() {
		return discount1;
	}

	public void setDiscount1(String discount1) {
		this.discount1 = discount1;
	}

	public String getDiscount2() {
		return discount2;
	}

	public void setDiscount2(String discount2) {
		this.discount2 = discount2;
	}

	public int getOfferingAmount() {
		return offeringAmount;
	}

	public void setOfferingAmount(int offeringAmount) {
		this.offeringAmount = offeringAmount;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getTransferContent1() {
		return transferContent1;
	}

	public void setTransferContent1(String transferContent1) {
		this.transferContent1 = transferContent1;
	}

	public String getTransferContent2() {
		return transferContent2;
	}

	public void setTransferContent2(String transferContent2) {
		this.transferContent2 = transferContent2;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	public List<PersonVO> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonVO> persons) {
		this.persons = persons;
	}
	

	public List<String> getMeals() {
		return meals;
	}

	public void setMeals(List<String> meals) {
		this.meals = meals;
	}

	public int getRetreatId() {
		return retreatId;
	}

	public void setRetreatId(int retreatId) {
		this.retreatId = retreatId;
	}

	@Override
	public String toString() {
		return "RetreatVO [retreatId=" + retreatId + ", program1=" + program1 + ", discount1=" + discount1
				+ ", discount2=" + discount2 + ", offeringAmount=" + offeringAmount + ", transferType=" + transferType
				+ ", transferContent1=" + transferContent1 + ", transferContent2=" + transferContent2 + ", remarks="
				+ remarks + ", persons=" + persons + ", meals=" + meals + "]";
	}

	
	
	
	
	
	
}
