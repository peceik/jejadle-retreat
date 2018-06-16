package org.jejadle.retreat.core.repository;

import java.util.List;

import org.jejadle.retreat.core.model.Person;
import org.jejadle.retreat.core.model.Retreat;
import org.jejadle.retreat.core.model.Retreat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

	List<Person> findAll();
	
	//List<Retreat> findByName(String name);
	Person findByRetreatAndPersonId(Retreat retreat, int personId);
	List<Person> findByRetreat(Retreat retreat);
    
}
