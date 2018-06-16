package org.jejadle.retreat.core.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.support.ClasspathScanningPersistenceUnitPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JpaConfig {

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setDataSource(dataSource);

		// Classpath scanning of @Component, @Service, etc annotated class
		entityManagerFactory.setPackagesToScan("org.jejadle.retreat.core");

		// Vendor adapter
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

		// Hibernate properties
		Properties additionalProperties = new Properties();
		additionalProperties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		additionalProperties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
		additionalProperties.put("hibernate.format_sql",
				env.getProperty("spring.jpa.properties.hibernate.format_sql", "true"));

		additionalProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		additionalProperties.setProperty("hibernate.ejb.naming_strategy", env.getProperty("spring.jpa.hibernate.naming-strategy"));

		entityManagerFactory.setJpaProperties(additionalProperties);

		entityManagerFactory.setPersistenceUnitPostProcessors(mappingProcessfor());

		return entityManagerFactory;
	}

	@Bean
	public ClasspathScanningPersistenceUnitPostProcessor mappingProcessfor() {
		ClasspathScanningPersistenceUnitPostProcessor csp = new ClasspathScanningPersistenceUnitPostProcessor(
				"org.jejadle.retreat.mapping");
		
		
		csp.setMappingFileNamePattern("**/*Mapping.xml");
		return csp;
	}

}
