package org.jejadle.retreat.core.repository;

import java.util.List;

import org.jejadle.retreat.core.model.Dept;
import org.jejadle.retreat.core.model.ExceptType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExceptTypeRepository extends JpaRepository<ExceptType, Integer> {

	List<ExceptType> findAll();
	
	List<ExceptType> findByName(String name);
    
}
