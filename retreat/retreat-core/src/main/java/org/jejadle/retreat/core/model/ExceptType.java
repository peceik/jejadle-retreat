package org.jejadle.retreat.core.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ExceptType {
	
	@Id @GeneratedValue
	int exceptTypeId;
	
	@Column(length=100, nullable=false)
	String name;
	
	
	@Column(nullable=false, length=1)
	boolean isAllExcept=true;
	
	
	@Column(nullable=false, length=1)
	boolean isExcept=true;
	
	
	@OneToMany(mappedBy="exceptType")
	List<Person> persons;
	
	short sort=0;
	
	public ExceptType(){
		
	}

	public ExceptType(String name) {
		super();
		this.name = name;
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
	



	public boolean isAllExcept() {
		return isAllExcept;
	}

	public void setAllExcept(boolean isAllExcept) {
		this.isAllExcept = isAllExcept;
	}

	public boolean isExcept() {
		return isExcept;
	}

	public void setExcept(boolean isExcept) {
		this.isExcept = isExcept;
	}

	@Override
	public String toString() {
		return "ExceptType [exceptTypeId=" + exceptTypeId + ", name=" + name + ", sort=" + sort + "]";
	}
	
	

}
