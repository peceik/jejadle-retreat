package org.jejadle.retreat.core.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Retreat extends BaseEntity {

	@Id @GeneratedValue
	int retreatId;
	
	@OneToMany(mappedBy="retreat",cascade=CascadeType.ALL)
	List<Person> persons;
	
	@OneToMany(mappedBy="retreat",cascade=CascadeType.ALL)
	List<Meal> meals;
	
	@Column(length=30, nullable=false)
	String regName;
	
	@Column(length=30, nullable=false)
	String phone;	
	
	@Column(length=20, nullable=true)
	String transferType;
	@Column(length=20, nullable=true)
	String transferContent1;
	@Column(length=20, nullable=true)
	String transferContent2;
	
	@Column(length=300, nullable=true)
	String remarks;
	
	
	int stayAmount=0;
	int foodAmount=0;
	int discountAmount=0;
	int offeringAmount=0;
	int totalAmount=0;
	int exceptAmount=0;
	
	int stayCount=0;
	int foodCount=0;
	int exceptPartCount=0;
	int exceptAllCount=0;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=true, length=1)
	BooleanType discount1=BooleanType.N;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=true, length=1)
	BooleanType discount2=BooleanType.N;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=true, length=1)
	BooleanType discount3=BooleanType.N;
	
	@Column(length=30, nullable=true)
	String program1="";
	
	public Retreat(){
		
	}

	public int getRetreatId() {
		return retreatId;
	}

	public void setRetreatId(int retreatId) {
		this.retreatId = retreatId;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public int getStayAmount() {
		return stayAmount;
	}

	public void setStayAmount(int stayAmount) {
		this.stayAmount = stayAmount;
	}

	public int getFoodAmount() {
		return foodAmount;
	}

	public void setFoodAmount(int foodAmount) {
		this.foodAmount = foodAmount;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}

	public int getOfferingAmount() {
		return offeringAmount;
	}

	public void setOfferingAmount(int offeringAmount) {
		this.offeringAmount = offeringAmount;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getExceptAmount() {
		return exceptAmount;
	}

	public void setExceptAmount(int exceptAmount) {
		this.exceptAmount = exceptAmount;
	}

	public BooleanType getDiscount1() {
		return discount1;
	}

	public void setDiscount1(BooleanType discount1) {
		this.discount1 = discount1;
	}

	public BooleanType getDiscount2() {
		return discount2;
	}

	public void setDiscount2(BooleanType discount2) {
		this.discount2 = discount2;
	}

	public BooleanType getDiscount3() {
		return discount3;
	}

	public void setDiscount3(BooleanType discount3) {
		this.discount3 = discount3;
	}

	public String getProgram1() {
		return program1;
	}

	public void setProgram1(String program1) {
		this.program1 = program1;
	}
	
	

	public int getStayCount() {
		return stayCount;
	}

	public void setStayCount(int stayCount) {
		this.stayCount = stayCount;
	}

	public int getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}

	public int getExceptPartCount() {
		return exceptPartCount;
	}

	public void setExceptPartCount(int exceptPartCount) {
		this.exceptPartCount = exceptPartCount;
	}

	public int getExceptAllCount() {
		return exceptAllCount;
	}

	public void setExceptAllCount(int exceptAllCount) {
		this.exceptAllCount = exceptAllCount;
	}

	@Override
	public String toString() {
		return "Retreat [retreatId=" + retreatId + ", regName=" + regName + ", phone=" + phone + ", transferType="
				+ transferType + ", transferContent1=" + transferContent1 + ", transferContent2=" + transferContent2
				+ ", remarks=" + remarks + ", stayAmount=" + stayAmount + ", foodAmount=" + foodAmount
				+ ", discountAmount=" + discountAmount + ", offeringAmount=" + offeringAmount + ", totalAmount="
				+ totalAmount + ", exceptAmount=" + exceptAmount + ", discount1=" + discount1 + ", discount2="
				+ discount2 + ", discount3=" + discount3 + ", program1=" + program1 + "]";
	}
	
	
}
