package org.jejadle.retreat.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.jejadle.retreat.core.model.Person;
import org.jejadle.retreat.core.model.Retreat;
import org.jejadle.retreat.core.repository.PersonRepository;
import org.jejadle.retreat.core.repository.RetreatRepository;
import org.jejadle.retreat.core.service.RetreatService;
import org.jejadle.retreat.core.vo.PersonVO;
import org.jejadle.retreat.core.vo.RetreatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class RetreatSaveTest implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(RetreatSaveTest.class);

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	PersonRepository personRepo;
	
	@Autowired
	RetreatRepository retreatRepo;
	
	@Autowired
	RetreatService retreatService;
	
	@Override	
	@Transactional
	public void run(String...  strings) throws Exception {
		
		logger.info("test");
		
		//Retreat retreat =em.find(Retreat.class, 10);
		
		//Person person = personRepo.findByRetreatAndPersonId(retreat, 22);
		
		//logger.info("person:{}", person);
		//modifyTest();
		
		//checkStayDate();
		
		//List<Retreat> list = retreatRepo.findAllOrderByRetreatIdDesc();
		
		//logger.info("list:{}", list);
	}
	
	public void checkStayDate(){
		
		Person person = personRepo.findOne(29);
		logger.info("person:{}", person);
		
		logger.info("stay 20170731:{}", person.hasStay("20170731"));
		logger.info("stay 20170730:{}", person.hasStay("20170730"));
		
		
		
		
	}
	
	
	public void modifyTest(){
		
		
		RetreatVO retreatVo = makeTestData();
		
		retreatService.save(retreatVo);
		
	}
	
	
	public RetreatVO makeTestData(){
		RetreatVO retreat=new RetreatVO();
		
		retreat.setRetreatId(2);
		
		retreat.setDiscount1("N");
		retreat.setDiscount2("N");
		
		retreat.setProgram1("1");
		retreat.setOfferingAmount(0);
		retreat.setRemarks("test");
		retreat.setTransferType("auto");
		
		PersonVO person1= new PersonVO();
		person1.setPersonId(8);
		person1.setDeptId(1);
		person1.setExceptTypeId(1);
		person1.setName("박승진");
		person1.setOption1("약한냉방");
		person1.setPhone("010111");
		person1.setStay1("Y");
		person1.setStay2("");
		
		List<PersonVO> persons = new ArrayList<PersonVO>();
		
		persons.add(person1);
		
		PersonVO person2= new PersonVO();
		person2.setPersonId(9);
		person2.setDeptId(1);
		person2.setExceptTypeId(1);
		person2.setName("김유은");
		person2.setOption1("약한냉방");
		person2.setPhone("01012");
		person2.setStay1("Y");
		person2.setStay2("Y");
		
		persons.add(person2);
		
		PersonVO person3= new PersonVO();
		//person2.setPersonId(9);
		person3.setDeptId(1);
		person3.setExceptTypeId(1);
		person3.setName("김유은1");
		person3.setOption1("약한냉방");
		person3.setPhone("01012");
		person3.setStay1("Y");
		person3.setStay2("");
		
		persons.add(person3);
		
		
		retreat.setPersons(persons);
		
		List<String> meals = new ArrayList<String>();
		
		meals.add("2");
		meals.add("2");
		meals.add("2");
		meals.add("2");
		meals.add("2");
		meals.add("2");
		
		retreat.setMeals(meals);
		
		
		
		
		
		return retreat;
		
	}
	
	
	
}
