package org.jejadle.retreat.front.controller;

import java.text.DecimalFormat;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.jejadle.retreat.core.model.Retreat;
import org.jejadle.retreat.core.repository.DeptRepository;
import org.jejadle.retreat.core.repository.ExceptTypeRepository;
import org.jejadle.retreat.core.service.RetreatService;
import org.jejadle.retreat.core.service.SMSService;
import org.jejadle.retreat.core.vo.RetreatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/public")
public class RetreatController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(RetreatController.class);
	
	@Autowired
	//@Qualifier("deptRepository")
	DeptRepository deptRepo;
	
	@Autowired
	ExceptTypeRepository exceptTypeRepo;
	
	@Autowired
	RetreatService retreatService;
	
	@Autowired
	SMSService smsService;

	@RequestMapping({"/", "/apply"})
	public String home(Model model) {
		
		
		logger.info("user:{}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		logger.info("name:{}", SecurityContextHolder.getContext().getAuthentication().getName());
		
		
		
		model.addAttribute("persons", Arrays.asList("1","2","3","4","5","6","7"));
		model.addAttribute("depts", deptRepo.findByIsApplyTrue());
		model.addAttribute("exceptTypes", exceptTypeRepo.findAll());
		
		model.addAttribute("account", retreatService.getProperties("retreat.account"));
		
				
		model.addAttribute("retreat", retreatService.getEmptyRetreat());

		return "apply";
		//return "view";
	}
	
	@RequestMapping("/save")
	public String save(Model model
			,@ModelAttribute RetreatVO retreatVo
			,@RequestParam(name="isSendSMS", required=false, defaultValue="no") String isSendSMS
			,HttpSession session
			) {
		
		/*
		logger.info("retreat:{}", retreatVo);
		logger.info("length:{}", retreatVo.getPersons().size());
		
		for(PersonVO person:retreatVo.getPersons()){
			logger.info("person:{}", person);
		}
		
		for(String cont:retreatVo.getMeals()){
			logger.info("meals:{}", cont);
		}
		*/
		
		logger.info("retreatVo:{}", retreatVo);
		
		Retreat retreat = retreatService.save(retreatVo);
		
		//NumberFormat nf = NumberFormat.getCurrencyInstance();
		DecimalFormat df = new DecimalFormat("#,###");

		
		
		//String content="제자들교회"+retreat.getRegName()+" 님 수련회비 "+df.format(retreat.getTotalAmount())+" 103-01904-26301 시티은행 (예금주 : 정정일)";
		//시티은행: 162-02195-266-01(예금주: 박혜영)
		String account=retreatService.getProperties("retreat.account").replace("예금주:", "");
		
		
		String content="제자들교회 "+retreat.getRegName()+"님 수련회비 "+retreat.getTotalAmount()+"원 "+account;
		
		String senderName=SecurityContextHolder.getContext().getAuthentication().getName();
		
		session.setAttribute("smsResult", "");
		//관리자는 sms 발송하지 않음
		if("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())){
			String smsResult=smsService.sendMessage(senderName, content, retreat.getPhone(), retreat.getRetreatId());
			session.setAttribute("smsResult", smsResult);
		}else{
			//관리자가 sms 발송을 선택한 경우에만 발송
			if("yes".equals(isSendSMS)){
				String smsResult = smsService.sendMessage(senderName, content, retreat.getPhone(), retreat.getRetreatId());
				session.setAttribute("smsResult", smsResult);
			}
		}
		
		//User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    //String name = user.getUsername();
		
		
		//retreat.getRegName()
		logger.info("retreat:{}", retreat.getRetreatId());
		logger.info("retreat.phone:{}", retreat.getPhone());
		
		return "redirect:/public/apply-view?id="+retreat.getRetreatId();
	}
	
	@RequestMapping("/apply-view")
	public String applyView(Model model
			,@RequestParam(name="id", required=true) int retreatId) {
		
		model.addAttribute("retreat", retreatService.getRetreat(retreatId));
		model.addAttribute("account", retreatService.getProperties("retreat.account"));

		return "applyView";
	}
	
	
	
	@RequestMapping("/login")
    public String login() {
    	logger.debug("login");
    	
        return "login";
    }
	
	@RequestMapping("/intro")
    public String intro() {
    	logger.debug("login");
    	
        return "intro";
    }

}
