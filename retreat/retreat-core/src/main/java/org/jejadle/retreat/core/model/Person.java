package org.jejadle.retreat.core.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.time.DateUtils;

@Entity
public class Person extends BaseEntity {
	
	@Id @GeneratedValue
	int personId;
	
	@Column(length=30, nullable=false)
	String name="";
	@Column(length=30, nullable=true)
	String phone="";
	
	@ManyToOne
	Dept dept;
	
	@ManyToOne
	ExceptType exceptType;
	
	@ManyToOne
	Retreat retreat;
	
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	List<Stay> stays;
	
	@Column(length=30, nullable=true)
	String option1;
	
	@Column(length=30, nullable=true)
	String option2;
	
	@Column(length=30, nullable=true)
	String option3;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public ExceptType getExceptType() {
		return exceptType;
	}

	public void setExceptType(ExceptType exceptType) {
		this.exceptType = exceptType;
	}

	public Retreat getRetreat() {
		return retreat;
	}

	public void setRetreat(Retreat retreat) {
		this.retreat = retreat;
	}

	public List<Stay> getStays() {
		return stays;
	}

	public void setStays(List<Stay> stays) {
		this.stays = stays;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}
	
	public boolean hasStay(String date){
		
		boolean result = false;
		try{
			Date checkDate = DateUtils.parseDate(date, "yyyyMMdd");
		
			for(Stay stay:stays){
				
				if(DateUtils.isSameDay(stay.stayDate, checkDate)){
					result=true;
					break;
				}
			}
			
		}catch(Exception e){
			System.out.println(e);
			
		}
		
		return result;
	}
	

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", name=" + name + ", phone=" + phone + ", option1=" + option1
				+ ", option2=" + option2 + ", option3=" + option3 + "]";
	}
	
	
}
