package org.jejadle.retreat.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.jejadle.retreat.core.model.BooleanType;
import org.jejadle.retreat.core.model.Dept;
import org.jejadle.retreat.core.model.ExceptType;
import org.jejadle.retreat.core.model.Meal;
import org.jejadle.retreat.core.model.MealType;
import org.jejadle.retreat.core.model.Person;
import org.jejadle.retreat.core.model.Retreat;
import org.jejadle.retreat.core.model.Stay;
import org.jejadle.retreat.core.repository.DeptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;






@Component
public class RetreatProcess implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(RetreatProcess.class);

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	DeptRepository deptRepo;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	@Override	
	@Transactional
	public void run(String...  strings) throws Exception {
		
		//insertRetreat();
			
	}
	
	
	public void insertRetreat(){
		logger.info("insert-retreat");
		
		//person
		try{
			Retreat retreat = new Retreat();
			
			
			Person person = makePerson1();
			
			person.setRetreat(retreat);
			
			List<Person> persons = new ArrayList<Person>();
			
			persons.add(person);
			retreat.setPersons(persons);
			List<Meal> meals = makeMeals();
			
			for(Meal meal:meals){
				meal.setRetreat(retreat);
			}
			
			retreat.setDiscount1(BooleanType.N);
			retreat.setDiscount2(BooleanType.N);
			retreat.setDiscount3(BooleanType.N);
			
			retreat.setRemarks("test1111");
			
			retreat.setTransferType("car");
			
			retreat.setProgram1("11");
			
			retreat.setRegName(person.getName());
			retreat.setPhone(person.getPhone());
			
			retreat.setMeals(meals);
			
			em.persist(retreat);
		
		}catch(Exception e){
			
			logger.error(e.toString());
		}
		
		
		//
		
	}
	
	public Person makePerson1()throws Exception{
		
		Person person = new Person();
		
		
		
		//1.부서
		
		Dept dept = em.find(Dept.class, 7);
		
		person.setDept(dept);
		
		//2.면제 대상 
		ExceptType exceptType= em.find(ExceptType.class, 1);
		
		person.setExceptType(exceptType);
		
		//3.stay
		
		List<Stay> stays = new ArrayList<Stay>();
		
		Stay stay1=new Stay();
		stay1.setPerson(person);
		stay1.setStayDate(sdf.parse("20170730"));
		Stay stay2=new Stay();
		stay2.setStayDate(sdf.parse("20170731"));
		stay2.setPerson(person);
		
		stays.add(stay1);
		stays.add(stay2);
		
		person.setStays(stays);
		
		//4.이름 
		person.setName("test1");
		//5.전화번호
		person.setPhone("01011112222");
		
		
		
		return person;
		
	}
	/**
	 * 면제 대상
	 * @return
	 * @throws Exception
	 */
	
	public Person makePerson2()throws Exception{
		
		Person person = new Person();
		
		
		
		//1.부서
		
		Dept dept = em.find(Dept.class, 7);
		
		person.setDept(dept);
		
		//2.면제 대상 
		ExceptType exceptType= em.find(ExceptType.class, 2);
		
		person.setExceptType(exceptType);
		
		//3.stay
		
		List<Stay> stays = new ArrayList<Stay>();
		
		Stay stay1=new Stay();
		stay1.setPerson(person);
		stay1.setStayDate(sdf.parse("20170730"));
		Stay stay2=new Stay();
		stay2.setStayDate(sdf.parse("20170731"));
		stay2.setPerson(person);
		
		stays.add(stay1);
		stays.add(stay2);
		
		person.setStays(stays);
		
		//4.이름 
		person.setName("test1");
		//5.전화번호
		person.setPhone("01011112222");
		
		
		
		return person;
		
	}
	
	public List<Meal> makeMeals()throws Exception{
		
		List<Meal> meals = new ArrayList<Meal>();
		
		Meal meal1 = new Meal();
		
		meal1.setMealDate(sdf.parse("20170730"));
		meal1.setCount((short) 1);
		meal1.setMealType(MealType.dinner);
		meals.add(meal1);
		
		Meal meal2 = new Meal();
		
		meal2.setMealDate(sdf.parse("20170731"));
		meal2.setCount((short) 1);
		meal2.setMealType(MealType.breakfast);
		meals.add(meal2);
		
		Meal meal3 = new Meal();
		
		meal3.setMealDate(sdf.parse("20170731"));
		meal3.setCount((short) 1);
		meal3.setMealType(MealType.lunch);
		
		meals.add(meal3);
		
		//meal.setCount(1);
		
		
		return meals;
		
	}
	
	
	
}
