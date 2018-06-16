package org.jejadle.retreat.core.repository;

import org.jejadle.retreat.core.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	

}
