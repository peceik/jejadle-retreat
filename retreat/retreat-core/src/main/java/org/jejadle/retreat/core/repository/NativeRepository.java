package org.jejadle.retreat.core.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.velocity.VelocityContext;
import org.jejadle.retreat.core.util.JPAUtil;
import org.jejadle.retreat.core.util.NativeQueryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * sql 을 사용하는 경우
 */
@Repository
public class NativeRepository {

    @PersistenceContext
    private EntityManager em;
    
    private static final Logger logger = LoggerFactory.getLogger(NativeRepository.class);

    
    public List<Object[]> getNativeQueryResult(String queryName){
    	
    	return getNativeQueryResult(queryName, null);
    }
    
    public List<Object[]> getNativeQueryResult(String queryName, Map<String, String> paramMap){
    	
    	Query query = em.createNamedQuery(queryName);
    	
    	if(paramMap!=null){
    		for(String key: paramMap.keySet()){
        		query.setParameter(key, paramMap.get(key));
        	}
    	}
    	return query.getResultList();
    	
    }
    
    public List<Object[]> getSqlResult(String sql){
    	Query query = em.createNativeQuery(sql);
    	
    	return query.getResultList();
    	
    }
    
    /**
     * 
     * @param queryName
     * @param paramMap
     * @param columnMap
     * @return
     */
    
    public List<Map<String, String>> getNativeQueryResultMap(String queryName
    		, Map<String, String> paramMap, Map<Integer, String> columnMap){
    	
    	List<Object[]> objList = getNativeQueryResult(queryName, paramMap);
    	List<Map<String, String>> list = NativeQueryUtil.objectToStringMap(objList, columnMap);
    	
    	return list;
    	
    }
    
    public List<Map<String, String>> getNativeQueryResultMap(String queryName
    		,Map<String, String> paramMap, String columns){
    	
    	List<Object[]> objList = getNativeQueryResult(queryName, paramMap);
    	List<Map<String, String>> list = NativeQueryUtil.objectToStringMap(objList, columns);
    	
    	return list;
    	
    }
    
    /**
     * 
     * @param queryName
     * @param context 조건 정보가 담긴 context
     * @return
     */
    
    public List<Map<String, String>> getNativeQueryResultMap(String queryName
    		,VelocityContext context){
    	
    	//조건에 맞는 Query를 만든다.
    	String sql =  JPAUtil.makeSQL(em, context,queryName);
    	
    	//query 에서 column 정보를 추출한다. 
    	Map<Integer, String> columns = NativeQueryUtil.getColumnMap(sql);
    	
    	List<Object[]> objList = getSqlResult(sql);
    	
    	List<Map<String, String>> list = NativeQueryUtil.objectToStringMap(objList, columns);
    	
    	return list;
    	
    }
    
    
    public List<Map<String, String>> getNativeQueryResultMap(String queryName
    		,Map<String, Object> params){
    	
    	VelocityContext context = new VelocityContext();
    	
    	Iterator it = params.entrySet().iterator();
    	
    	while(it.hasNext()){
    		String key = String.valueOf(it.next());
    		context.put(key, params.get(key));
    	}
    	
    	return getNativeQueryResultMap(queryName, context);
    }
    
    /**
     * native query를 insert, update 실행한다.
     * @param queryName
     * @param context
     * @return
     */
    
    
    public int executeNativeUpdate(String queryName,VelocityContext context){
    	String sql =  JPAUtil.makeSQL(em, context,queryName);
    	
    	return em.createNativeQuery(sql).executeUpdate();
    	
    }
    
    
}
