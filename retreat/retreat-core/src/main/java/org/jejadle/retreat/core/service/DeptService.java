package org.jejadle.retreat.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.jejadle.retreat.core.model.Dept;
import org.jejadle.retreat.core.repository.DeptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class DeptService {
	
	@Autowired
	DeptRepository deptRepo;

	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(DeptService.class);
	
	List<Dept> sourceList = null;
	
	
	
	@Transactional
	public List<Dept> findDept(String sex, String birthYear){
		
		
		
		//List<Dept> sourceList = deptRepo.findAll();
		//cash 적용
		/*
		if(sourceList==null){
			sourceList = deptRepo.findOrderBySort();
		}
		*/
		sourceList = deptRepo.findOrderBySort();
		
		//복사
		List<Dept> resultList = new ArrayList<Dept>();
		resultList.addAll(sourceList);
		
		String age = getAge(birthYear);
		String ageArea = getAgeArea(birthYear);
		
		logger.debug("age:{}", age);
		
		//성별
		for(Dept dept:sourceList){
			
			//logger.info("{},{} ,{}",dept.getSex(), dept.getName() ,dept.getSex().indexOf(sex));
			if(!"".equals(sex) 
					&& dept.getSex().indexOf(sex)<0){
				resultList.remove(dept);
			}
			//연령과 연령대가 맞지 않으면 remove
			if(!"".equals(age) 
					&&( dept.getAges().indexOf(age)<0 && dept.getAges().indexOf(ageArea)<0) ){
				resultList.remove(dept);
			}
			
		}
		return resultList;
		
	}
	
	public String getAge(String birthYear){
		
		Calendar current = Calendar.getInstance();
		try{
		    int currentYear  = current.get(Calendar.YEAR);
	        int thisYear = Integer.parseInt(birthYear);
	        int age = currentYear-thisYear+1;
	        
	        //Max 값 설정
	        if(age>99){
	        	age=99;
	        }
	        
	        //Min 값 설정 
	        if(age<1){
	        	age=1;
	        }
    
			return String.format("%02d", age);
		}catch(Exception e){
			
			return "";
			
		}
		
	}
	
	public String getAgeArea(String birthYear){
		
		String age = getAge(birthYear);
		
		if("".equals(age)){
			return age;
		}
		
		return age.substring(0,1)+"@";
		
	}
	


}
