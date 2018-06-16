package org.jejadle.retreat.core.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Dept {

	@Id @GeneratedValue
	int deptId;
	
	
	@Column(length=100, nullable=false)
	String name;
	
	
	@Column(nullable=false, length=1)
	boolean isCalc=true;
	
	@JsonIgnore
	@OneToMany(mappedBy="dept")
	List<Person> persons;
	
	@Column(nullable=false)
	short sort=0;
	
	@Column(length=100, nullable=true)
	String ages;
	
	@Column(length=10, nullable=true)
	String sex;
	
	@Column(length=5, nullable=true)
	String stayDiscountType="";
	
	@Column(nullable=false, length=1)
	boolean isApply=true;
	
	public Dept(){
		
	}
	
	public Dept(String name){
		this.name=name;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public short getSort() {
		return sort;
	}

	public void setSort(short sort) {
		this.sort = sort;
	}


	public boolean isCalc() {
		return isCalc;
	}

	public void setCalc(boolean isCalc) {
		this.isCalc = isCalc;
	}

	public String getAges() {
		return ages;
	}

	public void setAges(String ages) {
		this.ages = ages;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	

	public String getStayDiscountType() {
		return stayDiscountType;
	}

	public void setStayDiscountType(String stayDiscountType) {
		this.stayDiscountType = stayDiscountType;
	}
	
	
	

	public boolean isApply() {
		return isApply;
	}

	public void setApply(boolean isApply) {
		this.isApply = isApply;
	}

	@Override
	public String toString() {
		return "Dept [deptId=" + deptId + ", name=" + name + ", isCalc=" + isCalc + ", persons=" + persons + ", sort="
				+ sort + ", ages=" + ages + ", sex=" + sex + "]";
	}

	
	
	
	
	
	
}
