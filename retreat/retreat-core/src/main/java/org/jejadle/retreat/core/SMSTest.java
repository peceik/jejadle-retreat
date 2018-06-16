package org.jejadle.retreat.core;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jejadle.retreat.core.service.SMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SMSTest implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(RetreatProcess.class);

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	SMSService smsService;
	
	@Override	
	@Transactional
	public void run(String...  strings) throws Exception {
		
		//insertRetreat();
		//logger.info("SMS Test start");
		//smsService.sendMessage("test","예약하신 금액은 100원 입니다.","01072661020");
		//logger.info("SMS Test end");
			
	}

}
