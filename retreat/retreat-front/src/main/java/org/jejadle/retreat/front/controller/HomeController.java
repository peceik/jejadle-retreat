package org.jejadle.retreat.front.controller;

import java.util.Arrays;

import org.jejadle.retreat.core.model.Retreat;
import org.jejadle.retreat.core.repository.DeptRepository;
import org.jejadle.retreat.core.repository.ExceptTypeRepository;
import org.jejadle.retreat.core.service.RetreatService;
import org.jejadle.retreat.core.vo.RetreatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	public String home(Model model) {
		// System.out.println("test");
		return "redirect:/public/";
	}

	@RequestMapping("/login")
	public String login() {
		logger.debug("login");

		return "login";
	}

}
