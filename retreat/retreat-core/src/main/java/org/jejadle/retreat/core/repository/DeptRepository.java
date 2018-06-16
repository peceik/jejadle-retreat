package org.jejadle.retreat.core.repository;

import java.util.List;

import org.jejadle.retreat.core.model.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository("deptRepository")
public interface DeptRepository extends JpaRepository<Dept, Integer> {

	List<Dept> findAll();
	
	List<Dept> findByName(String name);
	
	@Query("select m from Dept m where m.isApply = true order by m.sort")
	List<Dept> findOrderBySort();
    
	List<Dept> findByIsApplyTrue();
}
