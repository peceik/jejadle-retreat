package org.jejadle.retreat.front;

import javax.transaction.Transactional;

import org.jejadle.retreat.core.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class initData implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(initData.class);

	@Autowired
    private InitService initService;
	

	
	@Override	
	@Transactional
	public void run(String...  strings) throws Exception {
		//부서가 없는 경우 입력
		initService.initData();
			
	}
	
	
	
	
	
}
