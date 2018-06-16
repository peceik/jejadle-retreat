package org.jejadle.retreat.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@EnableTransactionManagement
//"org.jejadle.retreat.core.config" 에서 설정한 jpa (JpaConfig.java)정보를 같이 사용한다.
//scan package 를 추가
@ComponentScan({"org.jejadle.retreat.core.service","org.jejadle.retreat.front", "org.jejadle.retreat.core.repository", "org.jejadle.retreat.core.config"})
@EnableJpaRepositories(basePackages = {"org.jejadle.retreat.core.repository","org.jejadle.retreat.core.model"})
public class RetreatApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 SpringApplication.run(RetreatApplication.class, args);

	}

}
