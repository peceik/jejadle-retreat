package org.jejadle.retreat.core.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.jejadle.retreat.core.repository.DeptRepository;
import org.jejadle.retreat.core.repository.ExceptTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


@Component
public class InitService {
	
	@Autowired
	DeptRepository deptRepo;

	@PersistenceContext
    private EntityManager em;

	@Autowired
	ExceptTypeRepository exceptTypeRepo;
	
	@Autowired
    private ResourceLoader resourceLoader;
	
	private static final Logger logger = LoggerFactory.getLogger(InitService.class);
	
	
	
	public void initData() throws Exception{
		
		logger.info("init data checking");
		logger.info("부서정보 {} 건", deptRepo.count());
		logger.info("예외자 정보 {} 건", exceptTypeRepo.count());
		
		if(deptRepo.count()<25){
			insertDept();
			logger.info("부서정보 {} 건", deptRepo.count());
			logger.info("dept insert complete");
		}
		
		
		//예외 정보가 없는 경우 입력
		if(exceptTypeRepo.count()<1){
			insertExceptType();
			logger.info("예외자 정보 {} 건", exceptTypeRepo.count());
			logger.info("dept insert complete");
		}
		
	}
	
	public void insertDept()throws Exception{
		
		logger.info("insert Dept");
		
		
		//Resource resource = resourceLoader.getResource("classpath:dept.sql");
	
		File somethingFile = resourceToFile("classpath:dept.sql");
		/*
		try(BufferedReader br = new BufferedReader(new FileReader(somethingFile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        // process the line.
		    	
		    	logger.debug(line);
		    	em.createNativeQuery(line).executeUpdate();
		    }
		    // line is not visible here.
		}
		*/
		executeUpdateSqlFile(somethingFile);
		
		
	}
	
	
	public void insertExceptType()throws Exception{
		logger.info("insert Except");
		
		//Resource resource = resourceLoader.getResource("classpath:except_type.sql");
		File somethingFile = resourceToFile("classpath:except_type.sql");
		/*
		try(BufferedReader br = new BufferedReader(new FileReader(somethingFile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        // process the line.
		    	
		    	logger.debug(line);
		    	em.createNativeQuery(line).executeUpdate();
		    }
		    // line is not visible here.
		}*/
		executeUpdateSqlFile(somethingFile);
		
	}
	
	public void executeUpdateSqlFile(File file)throws Exception{
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        // process the line.
		    	
		    	logger.debug(line);
		    	em.createNativeQuery(line).executeUpdate();
		    }
		    // line is not visible here.
		}
		
	}
	
	/**
	 * 
	 * spring boot 에서는 jar 에서 파일을 찾아서 처리할수 없으므로 일시적으로 파일을 만들어서 처리한다.
	 * 
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	
	public File resourceToFile(String resourceName)throws Exception{
		
		Resource resource = resourceLoader.getResource(resourceName);
		
		InputStream inputStream = resource.getInputStream();
        File file = File.createTempFile("temp", ".txt");
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
            //FileUtils.copyI
        	
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        
        return file;
		
		
	}
	
	
	
	

}
