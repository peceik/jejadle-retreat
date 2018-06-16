package org.jejadle.retreat.core.repository;

import java.util.List;

import org.jejadle.retreat.core.model.Retreat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RetreatRepository extends JpaRepository<Retreat, Integer> {

	List<Retreat> findAll();
	
		
	@Query("select m from Retreat m order by m.retreatId desc")
	List<Retreat> findAllOrderByRetreatIdDesc();
	
	
    
}
