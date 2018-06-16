package org.jejadle.retreat.front.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.jejadle.retreat.core.model.Retreat;
import org.jejadle.retreat.core.repository.DeptRepository;
import org.jejadle.retreat.core.repository.ExceptTypeRepository;
import org.jejadle.retreat.core.service.RetreatService;
import org.jejadle.retreat.core.service.StateService;
import org.jejadle.retreat.core.util.Utils;
import org.jejadle.retreat.front.advice.ExcelReportView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	//@Qualifier("deptRepository")
	DeptRepository deptRepo;
	
	@Autowired
	ExceptTypeRepository exceptTypeRepo;
	
	@Autowired
	RetreatService retreatService;
	
	@Autowired
	StateService stateService;
	
	Map<String, LinkedHashMap<String, String>> excelTitleMap;
	
	/*
	@RequestMapping({"/", "/apply"})
	public String home(Model model) {
		
		model.addAttribute("persons", Arrays.asList("1","2","3","4","5","6","7"));
		model.addAttribute("depts", deptRepo.findAll());
		model.addAttribute("exceptTypes", exceptTypeRepo.findAll());

		return "apply";
	}
	*/
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		model.addAttribute("retreatList", retreatService.retreatListAll());

		return "list";
	}
	
	@RequestMapping("/view")
	public String view(Model model
			,@RequestParam(name="id", required=true) int retreatId) {
		
		model.addAttribute("depts", deptRepo.findAll());
		model.addAttribute("exceptTypes", exceptTypeRepo.findAll());
		model.addAttribute("persons", Arrays.asList("1","2","3","4","5","6","7"));
		model.addAttribute("account", retreatService.getProperties("retreat.account"));
		
		Retreat retreat =retreatService.getRetreat(retreatId);
		
		logger.info("retreat:{}", retreat);
		logger.info("retreat.person:{}", retreat.getPersons());
		logger.info("retreat.meals:{}", retreat.getMeals());
		//logger.info("retreat.person.stays:{}", retreat.getPersons().get(0).getStays().get(0).get);
		
		model.addAttribute("retreat", retreat);

		//return "view";
		return "apply";
	}
	
	
	@RequestMapping("/state")
	public String state(Model model) {
		
		return "state";
	}
	
	@RequestMapping("/excel-data")
	public ModelAndView excelData(
			@RequestParam(name="dataType", defaultValue="", required=false ) String dataType 
    		,@RequestParam(name="fileName", defaultValue="", required=false ) String fileName) {
		
		ModelAndView mav = new ModelAndView(new ExcelReportView());
		
		mav.addObject("excelResults", stateService.getResultList(dataType));
    	
    	//filename
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
    
    	
    	
    	//mav.addObject("fileName",ExcelfileName);
    	
    	//column
    	//List<String> titles =excelTitleMap.get(dataType);
    	
    	//mav.addObject("titles",titles);
    	
    	//auto merge column 
    	
    	Set<String> autoMerges = new HashSet<String>();
    	//autoMerges.add("calc_dt");
    	
    	mav.addObject("autoMerges", autoMerges);
    	
    	
    	
    	mav.addAllObjects(Utils.getExcelDataAndTitles(excelTitleMap.get(dataType), stateService.getResultList(dataType), "retreat-"+fileName+sdf.format(Calendar.getInstance().getTime())));
    	
        return mav;
	}
	
	@PostConstruct
    public void init() {
		excelTitleMap = new HashMap<String, LinkedHashMap<String, String>>();
        //map.put("aaa", aaaObj);
		
		//개인별등록내역
		LinkedHashMap<String, String> listColumnTitleMap1 = new LinkedHashMap<>();
		
		listColumnTitleMap1.put("id", "id");
		listColumnTitleMap1.put("name", "이름");
    	listColumnTitleMap1.put("dept_name", "부서");
    	listColumnTitleMap1.put("phone", "전화번호");
    	listColumnTitleMap1.put("transfer_type", "교통수단");
    	listColumnTitleMap1.put("except_type", "면제사유");
    	listColumnTitleMap1.put("transfer1", "교통1");
    	listColumnTitleMap1.put("transfer2", "교통2");
    	listColumnTitleMap1.put("stay1", "숙박1");
    	listColumnTitleMap1.put("stay2", "숙박2");
    	listColumnTitleMap1.put("option1", "에어콘");
		
		excelTitleMap.put("retreat.lisePerPerson", listColumnTitleMap1);
		
		//가족별등록내역
		LinkedHashMap<String, String> listColumnTitleMap2 = new LinkedHashMap<>();
		//List<String> title2 = new ArrayList<String>();
		listColumnTitleMap2.put("id", "id");
		listColumnTitleMap2.put("reg_name", "이름");
		listColumnTitleMap2.put("stay_amount", "숙박비");
		listColumnTitleMap2.put("food_amount", "식비");
		listColumnTitleMap2.put("except_amount", "면제금액");
		listColumnTitleMap2.put("discount_amount", "할인금액");
		listColumnTitleMap2.put("offering_amount", "나눔헌금");
		listColumnTitleMap2.put("total_amount", "총액");
		listColumnTitleMap2.put("reg_date", "등록일시");
		listColumnTitleMap2.put("discount1", "미리등록할인");
		listColumnTitleMap2.put("discount2", "새가족할인");
		listColumnTitleMap2.put("m1_cnt", "식사1");
		listColumnTitleMap2.put("m2_cnt", "식사2");
		listColumnTitleMap2.put("m3_cnt", "식사3");
		listColumnTitleMap2.put("m4_cnt", "식사4");
		listColumnTitleMap2.put("m5_cnt", "식사5");
		listColumnTitleMap2.put("m6_cnt", "식사6");
		listColumnTitleMap2.put("program1", "프로그램");
		listColumnTitleMap2.put("remark", "비고");

		
		excelTitleMap.put("retreat.amountListPerFamily", listColumnTitleMap2);
		
		//부서별등록내역
		//List<String> title3 = new ArrayList<String>();
		
		LinkedHashMap<String, String> listColumnTitleMap3 = new LinkedHashMap<>();
		
		listColumnTitleMap3.put("name", "부서명");
		listColumnTitleMap3.put("cnt", "인원");
		
		//title3.add("부서명");
		//title3.add("인원");
		
		excelTitleMap.put("retreat.countPerDept", listColumnTitleMap3);
		
		//교통수단별등록내역
		//List<String> title4 = new ArrayList<String>();
		
		LinkedHashMap<String, String> listColumnTitleMap4 = new LinkedHashMap<>();
		
		listColumnTitleMap4.put("transferType", "수송수단");
		listColumnTitleMap4.put("name", "부서");
		listColumnTitleMap4.put("cnt", "인원");
		
		
		excelTitleMap.put("retreat.countPerTransfer", listColumnTitleMap4);
		
		//전체숙박인원
		LinkedHashMap<String, String> listColumnTitleMap5 = new LinkedHashMap<>();
		
		listColumnTitleMap5.put("stay_date", "숙박일시");
		listColumnTitleMap5.put("cnt", "인원");
		
		
		excelTitleMap.put("retreat.countPerStay", listColumnTitleMap5);
		
		//전체식판
		LinkedHashMap<String, String> listColumnTitleMap6 = new LinkedHashMap<>();
						
		listColumnTitleMap6.put("meal_date", "식사일");
		listColumnTitleMap6.put("meal_type", "식사종류");
		listColumnTitleMap6.put("cnt", "개수");
		
		
		excelTitleMap.put("retreat.countPerFood", listColumnTitleMap6);
		
    }
	
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String retreatDelete(Model model
			,@RequestParam(name="id", required=true) int retreatId) {
		
		retreatService.delete(retreatId);
		
		return "redirect:/admin/list";
	}
	
	
	

}
