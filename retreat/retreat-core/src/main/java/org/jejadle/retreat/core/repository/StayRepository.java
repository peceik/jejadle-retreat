package org.jejadle.retreat.core.repository;

import java.util.List;

import org.jejadle.retreat.core.model.Person;
import org.jejadle.retreat.core.model.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StayRepository extends JpaRepository<Stay, Integer> {

	List<Stay> findAll();
	
	//List<Retreat> findByName(String name);
	List<Stay> findByPerson(Person person);
    
}
