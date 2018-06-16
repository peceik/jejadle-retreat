package org.jejadle.retreat.core;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jejadle.retreat.core.model.Dept;
import org.jejadle.retreat.core.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FindDeptTest implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(RetreatProcess.class);

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	DeptService deptService;
	
	@Override	
	@Transactional
	public void run(String...  strings) throws Exception {
		
		
		//deptTest();
			
	}
	
	public void deptTest()throws Exception{
		
		logger.info("Dept Test Start");
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<Dept> list = deptService.findDept("m", "1979");
		
		//logger.info("{}",list);
		for(Dept dept:list){
			
			logger.info("dept:{}", dept.getName());
		}
		
		
		list = deptService.findDept("f", "1980");
		
		for(Dept dept:list){
			
			logger.info("dept:{}", dept.getName());
		}
		
		list = deptService.findDept("f", "2012");
		
		for(Dept dept:list){
			
			logger.info("dept:{}", dept.getName());
		}
		
		list = deptService.findDept("m", "2014");
		
		for(Dept dept:list){
			
			logger.info("dept:{}", dept.getName());
		}
		
		
		list = deptService.findDept("", "1984");
		
		for(Dept dept:list){
			
			logger.info("dept:{}", dept.getName());
		}
		
		list = deptService.findDept("", "1998");
		//사역자, 디모데
		for(Dept dept:list){
			
			logger.info("dept:{}", dept.getName());
		}
		
		list = deptService.findDept("", "aa");
		//전체
		for(Dept dept:list){
			
			logger.info("dept:{}", dept.getName());
		}
		
		list = deptService.findDept("", "1898");
		//전체
		for(Dept dept:list){
			
			logger.info("dept:{}", dept.getName());
		}
		String jsonString = mapper.writeValueAsString(list);
		logger.info("list:{}", jsonString);
		
		logger.info("Dept Test End");
		
	}

}
