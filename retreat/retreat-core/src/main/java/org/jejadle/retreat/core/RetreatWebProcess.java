package org.jejadle.retreat.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.jejadle.retreat.core.model.BooleanType;
import org.jejadle.retreat.core.model.Dept;
import org.jejadle.retreat.core.model.ExceptType;
import org.jejadle.retreat.core.model.Meal;
import org.jejadle.retreat.core.model.MealType;
import org.jejadle.retreat.core.model.Person;
import org.jejadle.retreat.core.model.Retreat;
import org.jejadle.retreat.core.model.Stay;
import org.jejadle.retreat.core.repository.DeptRepository;
import org.jejadle.retreat.core.repository.ExceptTypeRepository;
import org.jejadle.retreat.core.service.RetreatService;
import org.jejadle.retreat.core.vo.PersonVO;
import org.jejadle.retreat.core.vo.RetreatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;






@Component
@PropertySource("retreat.properties")
public class RetreatWebProcess implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(RetreatWebProcess.class);

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private Environment env;
	
	@Autowired
	DeptRepository deptRepo;
	
	@Autowired
	ExceptTypeRepository exceptTypeRepo;
	
	@Autowired
	RetreatService retreatService;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	@Override	
	@Transactional
	public void run(String...  strings) throws Exception {
		/*
		RetreatVO retreatVo = makeTestData();
		
		Retreat retreat = convert(retreatVo);
		
		logger.info("retreat:{}", retreat);
		
		for(Person person:retreat.getPersons()){
			
			logger.info("person:{}", person);
		}
		
		for(Meal meal:retreat.getMeals()){
			logger.info("meal:{}", meal);
		}
		
		retreatService.calculate(retreat);
		
		logger.info("retreat:{}", retreat);*/
		
		//insertRetreat();
			
	}
	
	public Retreat convert(RetreatVO retreatVO){
		
		Retreat retreat = new Retreat();
		
		retreat.setDiscount1(BooleanType.valueOf(retreatVO.getDiscount1()));
		retreat.setDiscount2(BooleanType.valueOf(retreatVO.getDiscount2()));
		
		retreat.setProgram1(retreatVO.getProgram1());
		
		retreat.setRemarks(retreatVO.getRemarks());
		retreat.setTransferContent1(retreatVO.getTransferContent1());
		retreat.setTransferContent2(retreatVO.getTransferContent2());
		retreat.setTransferType(retreatVO.getTransferType());
		
		retreat.setOfferingAmount(retreatVO.getOfferingAmount());
		
		List<Person> persons = new ArrayList<Person>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		//person
		for(PersonVO personVo:retreatVO.getPersons()){
			Person person = new Person();
			
			Dept dept = deptRepo.findOne(personVo.getDeptId());
			ExceptType exceptType = exceptTypeRepo.findOne(personVo.getExceptTypeId());
			person.setDept(dept);
			person.setExceptType(exceptType);
			
			List<Stay> stays = new ArrayList<Stay>();
			try{
				if(StringUtils.isNotEmpty(personVo.getStay1())){
					Stay stay1 = new Stay();
					stay1.setStayDate(sdf.parse(env.getProperty("stay.1.date", "2017-07-30")));
					stay1.setPerson(person);
					stays.add(stay1);
				}
				
				if(StringUtils.isNotEmpty(personVo.getStay2())){
					Stay stay2 = new Stay();
					stay2.setStayDate(sdf.parse(env.getProperty("stay.2.date", "2017-07-31")));
					stay2.setPerson(person);
					stays.add(stay2);
				}
				person.setName(personVo.getName());
				person.setPhone(personVo.getPhone());
				person.setRetreat(retreat);
				person.setOption1(personVo.getOption1());
				person.setStays(stays);
				
				persons.add(person);
				
			}catch(Exception e){
				logger.error("error:{}", e);
			}
		
			
		}
		
		retreat.setPersons(persons);
		//meals
		List<Meal> meals = new ArrayList<Meal>();
		int idx=0;
		for(String cont:retreatVO.getMeals()){
			idx++;
			if(cont==null){
				continue;
			}
			
			try{
				short cnt = Short.parseShort(cont);
				Meal meal = new Meal();
				meal.setCount(cnt);
				meal.setMealDate(sdf.parse(env.getProperty("meal."+idx+".date", "2017-07-30")));
				meal.setMealType(MealType.valueOf(env.getProperty("meal."+idx+".type")));
				meal.setRetreat(retreat);
				
				meals.add(meal);
			}catch(Exception e){
				logger.error("error:{}",e);
			}
			
		}
		
		retreat.setMeals(meals);
		
		
		return retreat;
		
	}
	
	
	public RetreatVO makeTestData(){
		RetreatVO retreat=new RetreatVO();
		
		retreat.setDiscount1("N");
		retreat.setDiscount2("N");
		
		retreat.setProgram1("1");
		retreat.setOfferingAmount(0);
		retreat.setRemarks("test");
		retreat.setTransferType("auto");
		
		PersonVO person1= new PersonVO();
		
		person1.setDeptId(1);
		person1.setExceptTypeId(2);
		person1.setName("박승진");
		person1.setOption1("약한냉방");
		person1.setPhone("010111");
		person1.setStay1("Y");
		person1.setStay2("Y");
		
		List<PersonVO> persons = new ArrayList<PersonVO>();
		
		persons.add(person1);
		
		PersonVO person2= new PersonVO();
		
		person2.setDeptId(1);
		person2.setExceptTypeId(1);
		person2.setName("김유은");
		person2.setOption1("약한냉방");
		person2.setPhone("01012");
		person2.setStay1("Y");
		person2.setStay2("Y");
		
		persons.add(person2);
		
		retreat.setPersons(persons);
		
		List<String> meals = new ArrayList<String>();
		
		meals.add("1");
		meals.add("2");
		meals.add("3");
		meals.add("4");
		meals.add("5");
		meals.add("6");
		
		retreat.setMeals(meals);
		
		
		
		
		
		return retreat;
		
	}
	
	
	
	
}
