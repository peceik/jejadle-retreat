package org.jejadle.retreat.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Meal {
	@Id @GeneratedValue
	int mealId;
	
	short count;
	
	Date mealDate;
	
	@Enumerated(EnumType.STRING)
	@Column(length=10, nullable=false)
	MealType mealType;
	
	@ManyToOne
	Retreat retreat;

	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
	}

	public short getCount() {
		return count;
	}

	public void setCount(short count) {
		this.count = count;
	}

	public Date getMealDate() {
		return mealDate;
	}

	public void setMealDate(Date mealDate) {
		this.mealDate = mealDate;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public Retreat getRetreat() {
		return retreat;
	}

	public void setRetreat(Retreat retreat) {
		this.retreat = retreat;
	}

	@Override
	public String toString() {
		return "Meal [mealId=" + mealId + ", count=" + count + ", mealDate=" + mealDate + ", mealType=" + mealType
				+ "]";
	}
	
}
