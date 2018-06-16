package org.jejadle.retreat.core.repository;

import java.util.List;

import org.jejadle.retreat.core.model.Meal;
import org.jejadle.retreat.core.model.Retreat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

	List<Meal> findAll();
	
	//List<Retreat> findByName(String name);
	List<Meal> findByRetreat(Retreat retreat);
    
}
