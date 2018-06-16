package org.jejadle.retreat.core.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import org.jejadle.retreat.core.repository.MealRepository;
import org.jejadle.retreat.core.repository.PersonRepository;
import org.jejadle.retreat.core.repository.RetreatRepository;
import org.jejadle.retreat.core.repository.StayRepository;
import org.jejadle.retreat.core.vo.PersonVO;
import org.jejadle.retreat.core.vo.RetreatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("retreat.properties")
public class RetreatService {
	
	@Autowired
	DeptRepository deptRepo;
	
	@Autowired
	ExceptTypeRepository exceptTypeRepo;
	
	@Autowired
	RetreatRepository retreatRepo;
	
	@Autowired
	StayRepository stayRepo;
	
	@Autowired
	MealRepository mealRepo;
	
	@Autowired
	PersonRepository personRepo;

	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(RetreatService.class);
	
	@Transactional
	public Retreat save(RetreatVO retreatVO){
		
		Retreat retreat = convert(retreatVO);
		
		/*calculate(retreat);
		
		retreatRepo.save(retreat);*/
		
		return save(retreat);
		
	}
	
	
	
	@Transactional
	public Retreat save(Retreat retreat){
		
		logger.debug("save retreat.persons:{}", retreat.getPersons().size());
		
		calculate(retreat);
		
		Retreat savedRetreat = retreatRepo.save(retreat);
		
		return savedRetreat;
		
	}
	
	
	@Transactional
	public void delete(int retreatId){
		
		//Retreat retreat = getRetreat(retreatId);
		retreatRepo.delete(retreatId);
	}
	
	//미리 보기
	@Transactional
	public Retreat preview(RetreatVO retreatVo){
		
		Retreat retreat = convert(retreatVo);
		
		calculate(retreat);
		return retreat;
		
	}
	
	public String getRetreatMessage(){
		
		return env.getProperty("stay.1.date");
	}

	public Retreat convert(RetreatVO retreatVO) {
		
		Retreat retreat = new Retreat();
		
		if(retreatVO.getRetreatId()!=0){
			retreat = retreatRepo.findOne(retreatVO.getRetreatId());
		}
		
		retreat.setDiscount1(BooleanType.N);
		retreat.setDiscount2(BooleanType.N);

		if(retreatVO.getDiscount1()!=null){
			retreat.setDiscount1(BooleanType.valueOf(retreatVO.getDiscount1()));
		}
		if(retreatVO.getDiscount2()!=null){
			retreat.setDiscount2(BooleanType.valueOf(retreatVO.getDiscount2()));
		}

		retreat.setProgram1(retreatVO.getProgram1());

		retreat.setRemarks(retreatVO.getRemarks());
		retreat.setTransferContent1(retreatVO.getTransferContent1());
		retreat.setTransferContent2(retreatVO.getTransferContent2());
		retreat.setTransferType(retreatVO.getTransferType());

		retreat.setOfferingAmount(retreatVO.getOfferingAmount());

		List<Person> persons = new ArrayList<Person>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// person
		int personIdx=0;
		
		//logger.debug("convert person cnt:{}", retreatVO.getPersons().size());
		
		
		
		
		
		for (PersonVO personVo : retreatVO.getPersons()) {
			
			
			//person id=0  이고 이름이 들어오지 않는 경우에는 입력이 되지 않은 것으로 간주함
			if(personVo.getPersonId()==0 && StringUtils.isEmpty(personVo.getName())){
				continue;
			}
			
			Person person = new Person();
			if(personVo.getPersonId()!=0){
				person = personRepo.findByRetreatAndPersonId(retreat, personVo.getPersonId());
				
				//person id !=0 이고 이름이 들어오지 않는 경우에는 기존 것을 삭제
				logger.debug("save:person:{}",person);
				
				if(StringUtils.isEmpty(personVo.getName())){
					//미리보기에서 한번 삭제하므로
					if(person!=null){
						personRepo.delete(person);
					}
					continue;
				}
				
				
			}

			Dept dept = deptRepo.findOne(personVo.getDeptId());
			ExceptType exceptType = exceptTypeRepo.findOne(personVo.getExceptTypeId());
			person.setDept(dept);
			person.setExceptType(exceptType);
			
			//stay 초기화
			if(personVo.getPersonId()!=0){
				List<Stay> initStays = stayRepo.findByPerson(person);
				stayRepo.delete(initStays);
			}
			

			List<Stay> stays = new ArrayList<Stay>();
			try {
				
				if (StringUtils.isNotEmpty(personVo.getStay1())) {
					Stay stay1 = new Stay();
					stay1.setStayDate(sdf.parse(env.getProperty("stay.1.date", "2017-07-30")));
					stay1.setPerson(person);
					stays.add(stay1);
				}

				if (StringUtils.isNotEmpty(personVo.getStay2())) {
					Stay stay2 = new Stay();
					stay2.setStayDate(sdf.parse(env.getProperty("stay.2.date", "2017-07-31")));
					stay2.setPerson(person);
					stays.add(stay2);
				}

			} catch (Exception e) {
				logger.error("error:{}", e);
			}
			
			if(personIdx==0){
				retreat.setRegName(personVo.getName());
				retreat.setPhone(personVo.getPhone());
				
			}
			
			person.setName(personVo.getName());
			person.setPhone(personVo.getPhone());
			person.setRetreat(retreat);
			person.setOption1(personVo.getOption1());
			person.setStays(stays);
			
			//personRepo.save(person);

			persons.add(person);
			
			personIdx++;

		}
		
		//person 초기화
		//하지 않으면 여러건이 등록이 듯
		if(retreatVO.getRetreatId()!=0){
			List<Person> initPersons = personRepo.findByRetreat(retreat);
			personRepo.delete(initPersons);
		}
		
		
		logger.debug("setting persons cnt:{}", persons.size());
		
		
		

		retreat.setPersons(persons);
		
		//meal init 초기화
		//stay 초기화
		if(retreatVO.getRetreatId()!=0){
			List<Meal> initMeals = mealRepo.findByRetreat(retreat);
			mealRepo.delete(initMeals);
		}
		
		// meals
		List<Meal> meals = new ArrayList<Meal>();
		int idx = 0;
		for (String cont : retreatVO.getMeals()) {
			idx++;
			if (cont == null || StringUtils.isEmpty(cont) || !StringUtils.isNumeric(cont)) {
				continue;
			}

			try {
				short cnt = Short.parseShort(cont);
				Meal meal = new Meal();
				meal.setCount(cnt);
				meal.setMealDate(sdf.parse(env.getProperty("meal." + idx + ".date", "2017-07-30")));
				meal.setMealType(MealType.valueOf(env.getProperty("meal." + idx + ".type")));
				meal.setRetreat(retreat);
				//mealRepo.save(meal);
				meals.add(meal);
			} catch (Exception e) {
				logger.error("error:{}", e);
			}

		}

		retreat.setMeals(meals);

		return retreat;

	}
	
	/**
	 * 숙박계산
	 */
	
	public void calculate(Retreat retreat){
		
		//소속: 영아부-초등부, 나오미 무료 숙박 무료
		
		//면제사유: 사랑나눔, 취침도우미 는 숙박만 무료, 나머지는 전체 무료
		
		int stayAmount=0;
		int exceptAmount=0;
		int foodAmount=0;
		
		int discountAmount=0;
		//할인금액
		int offeringAmount=retreat.getOfferingAmount();
		//나눔헌금
		
		
		//식대 면제 대상자
		int exceptAllPeopleCnt=0;
		
		//가족 총 식판 개수 
		int foodCount=0;
		//가족 총 숙박 개수 
		int stayCount=0;
		
		//부분 면제자수
		int exceptPartCount=0;
		
		//전체 면제자수 
		int exceptAllCount=0;
		
		//전체면제자수 
		
		//숙박금액
		//int STAY_PRICE=30000; 
		//int STAY_DISCOUNT_PRICE=20000;
		int STAY_PRICE=NumberUtils.toInt(env.getProperty("stay.price"));
		int STAY_DISCOUNT_PRICE=NumberUtils.toInt(env.getProperty("stay.discount.price"));
		
		for(Person person:retreat.getPersons()){
			//int stayPersonAmount=0;
			Dept dept = person.getDept();
			ExceptType exceptType = person.getExceptType();
			
			//바울, 디모데, 청소년부 숙박비 할인
			if(dept.getStayDiscountType().equals("Y")){
				STAY_PRICE=STAY_DISCOUNT_PRICE;
			}
			
			
			for(Stay stay:person.getStays()){
				
				logger.debug("sty");
				if(dept.isCalc()){
					//연령대로는 계산 대상이면서 면제 대상이 아닌 것만
					if(!exceptType.isExcept()){
						stayAmount=stayAmount+STAY_PRICE;
					}else{
						stayAmount=stayAmount+STAY_PRICE;
						exceptAmount=exceptAmount+STAY_PRICE;
					}
					//숙박  count
					stayCount++;
				}
				
				/*
				if(dept.isCalc() && !exceptType.isExcept()){
					//연령대로는 계산 대상이면서 면제 대상이 아닌 것만
					stayAmount=stayAmount+30000;
				}else{
					stayAmount=stayAmount+30000;
					exceptAmount=exceptAmount+30000;
				}
				*/
				
			}
			
			if(exceptType.isExcept()){
				exceptPartCount++;
			}
			
			if(exceptType.isAllExcept()){
				//모든 경비의 예외가 되는 사람의 식비 계산을 위해서 
				exceptAllPeopleCnt++;
				
				exceptAllCount++;
			}
			
		}
		
		//식사금액
		int MEAL_PRICE=NumberUtils.toInt(env.getProperty("meal.price")); 
		
		for(Meal meal:retreat.getMeals()){
			logger.debug("meal1");
			if(meal.getCount() >0){
				logger.debug("meal2");
				//foodAmount+= (meal.getCount()- exceptAllPeopleCnt)*5000;
				foodAmount+= meal.getCount()*MEAL_PRICE;
				if(meal.getCount() -exceptAllPeopleCnt > -1){
					//식사개수가 면제인 식사보다 많은 경우 
					logger.debug("meal.except.meal.case1");
					exceptAmount=exceptAmount + (exceptAllPeopleCnt*MEAL_PRICE);
				}else{
					//식사개수가 면제인 식사보다 적은 경우
					logger.debug("meal.except.meal.case2");
					exceptAmount=exceptAmount + (meal.getCount()*MEAL_PRICE);
				}
			}
			foodCount+=meal.getCount();
		}
		
		//할인 금액 반영 
		if(retreat.getDiscount1()==BooleanType.Y){
			discountAmount+=10000;
		}
		
		if(retreat.getDiscount2()==BooleanType.Y){
			discountAmount+=10000;
		}
		
		
		retreat.setFoodAmount(foodAmount);
		retreat.setStayAmount(stayAmount);
		retreat.setExceptAmount(exceptAmount);
		retreat.setDiscountAmount(discountAmount);
		
		retreat.setStayCount(stayCount);
		retreat.setFoodCount(foodCount);
		retreat.setExceptPartCount(exceptPartCount);
		retreat.setExceptAllCount(exceptAllCount);
		
		
		
		int foodPlusOffering= foodAmount+offeringAmount;
		
		/*
		totalAmount = 
				(offeringAmount+foodAmount+logic(stayAmount-discountAmount))
					-exceptAmount
		*/
		
		
		//할인 정책 적용
		//숙박비에서 할인을 적용함, 숙박비가 없는 경우에는 할인을 적용할 수 없음
		int applyDiscountForStay = stayAmount;
		
		//면제금액이 없는 경우에만 할인 가능
		if(exceptAmount==0){
			applyDiscountForStay=stayAmount-discountAmount;
		}
		if(applyDiscountForStay<0){
			applyDiscountForStay=0;
		}
		
		int totalAmount = foodPlusOffering+applyDiscountForStay -exceptAmount;
		
		
		
		//전체 금액 대비 할인 금액이 많은 경우에는 0으로 고정한다.
		if(totalAmount<0){
			totalAmount=0;
		}
		
		retreat.setTotalAmount(totalAmount);
		
	}
	
	public Retreat getRetreat(int retreatId){
		
		Retreat retreat = retreatRepo.findOne(retreatId);
		
		//식판이 없는 경우에는 0으로 채운다.
		if(retreat.getMeals()==null||retreat.getMeals().size()==0){
			List<Meal> meals = new ArrayList<Meal>();
			
			for(int i=0;i<6;i++){
				Meal meal = new Meal();
				meal.setCount((short)0);
				meals.add(meal);
				
			}
			retreat.setMeals(meals);
			
		}
		
		return retreat;
	}
	
	public List<Retreat> retreatListAll(){
		
		//return retreatRepo.findAll();
		return retreatRepo.findAllOrderByRetreatIdDesc();
	}
	
	public Retreat getEmptyRetreat(){
		
		Retreat retreat = new Retreat();
		
		List<Person> persons = new ArrayList<Person>();
		List<Meal> meals = new ArrayList<Meal>();
		
		for(int i=0;i<6;i++){
			Meal meal = new Meal();
			meal.setCount((short)0);
			meals.add(meal);
			
		}
		
		retreat.setPersons(persons);
		retreat.setMeals(meals);
		
		return retreat;
		
		
	}
	
	public String getProperties(String key){
		
		return env.getProperty(key, "");
	}

}
