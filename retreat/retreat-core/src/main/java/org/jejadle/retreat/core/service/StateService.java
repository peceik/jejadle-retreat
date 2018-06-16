package org.jejadle.retreat.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jejadle.retreat.core.repository.NativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {
	
	@Autowired
	NativeRepository nativeRepo;
	
	/**
	 * 
	 * @param queryName
	 * @param param
	 * @return
	 */
	public List<Map<String, String>> getResultList(String queryName, Map<String, Object> param){
		
		return nativeRepo.getNativeQueryResultMap(queryName, param); 
		
	}
	
	public List<Map<String, String>> getResultList(String queryName){
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		return nativeRepo.getNativeQueryResultMap(queryName, param); 
		
	}

}
