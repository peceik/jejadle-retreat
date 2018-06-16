package org.jejadle.retreat.front.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jejadle.retreat.core.model.Dept;
import org.jejadle.retreat.core.model.Retreat;
import org.jejadle.retreat.core.service.DeptService;
import org.jejadle.retreat.core.service.RetreatService;
import org.jejadle.retreat.core.service.StateService;
import org.jejadle.retreat.core.vo.RetreatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetreatRestController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(RetreatRestController.class);
	
	@Autowired
	RetreatService retreatService;
	
	@Autowired
	DeptService deptService;
	
	@Autowired
	StateService stateService;
	
	@RequestMapping("/public/api/preview")
	public Map<String, Object> save(Model model, @ModelAttribute RetreatVO retreatVo) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Retreat retreat = retreatService.preview(retreatVo);
		
		result.put("stayAmount", retreat.getStayAmount());
		result.put("foodAmount", retreat.getFoodAmount());
		result.put("offeringAmount", retreat.getOfferingAmount());
		result.put("exceptAmount", retreat.getExceptAmount());
		result.put("discountAmount", retreat.getDiscountAmount());
		result.put("totalAmount", retreat.getTotalAmount());
		
		result.put("foodCount", retreat.getFoodCount());
		result.put("stayCount", retreat.getStayCount());
		result.put("exceptAllCount", retreat.getExceptAllCount());
		result.put("exceptPartCount", retreat.getExceptPartCount());
		
		
		return result;
	}
	
	
	@RequestMapping("/public/api/check")
	public Map<String, Object> check(Model model) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("check", "ok");
		
		
		
		return result;
	}
	
	@RequestMapping("/public/api/queryTest")
	public Map<String, Object> query(Model model) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("result", stateService.getResultList("retreat.getDept"));
		
		return result;
	}
	
	@RequestMapping("/public/api/getDept")
	public List<Dept> getDept(@RequestParam(name="sex")String sex,@RequestParam(name="birthYear") String birthYear){
		logger.debug("test");
		return deptService.findDept(sex, birthYear);
		
	}
	
	
	
}
