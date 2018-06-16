package org.jejadle.retreat.core;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.velocity.VelocityContext;
import org.jejadle.retreat.core.repository.NativeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class RetreatStateTest implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(RetreatStateTest.class);

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	NativeRepository nativeRepo;
	
	@Override	
	@Transactional
	public void run(String...  strings) throws Exception {
		
		logger.info("retreatStat test");
		
		//Retreat retreat =em.find(Retreat.class, 10);
		
		//Person person = personRepo.findByRetreatAndPersonId(retreat, 22);
		/*
		VelocityContext context = new VelocityContext();
    	context.put("dept_id", "1");
    	
    	List<Map<String, String>> list= nativeRepo.getNativeQueryResultMap("retreat.getDept", context);
    	logger.info("list:{}", list);
    	
    	context.put("dept_id", "");
    	
    	list= nativeRepo.getNativeQueryResultMap("retreat.getDept", context);
    	logger.info("list:{}", list);
    	*/
		
		//logger.info("person:{}", person);
		//modifyTest();
		
		//checkStayDate();
	}
	
}
