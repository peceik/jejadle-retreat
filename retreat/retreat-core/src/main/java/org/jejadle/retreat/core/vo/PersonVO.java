package org.jejadle.retreat.core.vo;

public class PersonVO {
	
	int deptId=0;
	int personId=0;
	int exceptTypeId=0;
	
	String name="";
	String phone="";
	
	String stay1="";
	String stay2="";
	
	String option1="";
	
	public PersonVO(){
		
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getExceptTypeId() {
		return exceptTypeId;
	}

	public void setExceptTypeId(int exceptTypeId) {
		this.exceptTypeId = exceptTypeId;
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

	public String getStay1() {
		return stay1;
	}

	public void setStay1(String stay1) {
		this.stay1 = stay1;
	}

	public String getStay2() {
		return stay2;
	}

	public void setStay2(String stay2) {
		this.stay2 = stay2;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	@Override
	public String toString() {
		return "PersonVO [deptId=" + deptId + ", personId=" + personId + ", exceptTypeId=" + exceptTypeId + ", name="
				+ name + ", phone=" + phone + ", stay1=" + stay1 + ", stay2=" + stay2 + ", option1=" + option1 + "]";
	}

	
	

}
