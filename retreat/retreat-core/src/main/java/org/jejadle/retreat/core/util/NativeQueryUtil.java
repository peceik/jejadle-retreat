package org.jejadle.retreat.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NativeQueryUtil {
	
	
	
	/**
	 * JPA Native Query�? ?��?��?���? 반환 값이 List<Object[]> ?��?���? 반환?�� 
	 * 
	 * List<Object[]> --> List<Map<String, Object> ?��?���? �?�? 
	 * @param results
	 * @param indxMap 
	 * ?��?��?�� ?���? column  ?���?
	 * ?��?�� 경우 column ???��?�� index �? ?��?���?
	 * @return
	 */
	

	public static List<Map<String, Object>> objectToMap(List<Object[]> results, Map<Integer, String> indxMap){
		
		
		List<Map<String, Object>> mapResult = new ArrayList<Map<String,Object>>();
		
		for(Object[] objs: results){
			Integer idx  =0;
			Map<String, Object> map = new HashMap<String, Object>();
			for(Object obj: objs){
				map.put(indxMap.getOrDefault(idx, String.valueOf(idx)), obj);
				idx++;
			}
			mapResult.add(map);
		}
		
		return mapResult;
	}
	
	

	/**
	 * JPA Native Query�? ?��?��?���? 반환 값이 List<Object[]> ?��?���? 반환?�� 
	 * 
	 * List<Object[]> --> List<Map<String, String> ?��?���? �?�? 
	 * @param results
	 * @param indxMap 
	 * ?��?��?�� ?���? column  ?���?
	 * ?��?�� 경우 column ???��?�� index �? ?��?���?
	 * @return
	 */
	
	public static List<Map<String, String>> objectToStringMap(List<Object[]> results, Map<Integer, String> indxMap){
		
		
		List<Map<String, String>> mapResult = new ArrayList<Map<String,String>>();
		//System.out.println(indxMap.size());
		if(indxMap.size()==1){
			//결과가 1건인 경우 
			for(Object obj:results){
				Map<String, String> map = new HashMap<String, String>();
				map.put(indxMap.getOrDefault(0, String.valueOf(0)), String.valueOf(obj));
				mapResult.add(map);
			}
			
		}else{
			//결과가 여러건인 경우 
			for(Object[] objs: results){
				Integer idx  =0;
				Map<String, String> map = new HashMap<String, String>();
				for(Object obj: objs){
					map.put(indxMap.getOrDefault(idx, String.valueOf(idx)), String.valueOf(obj));
					idx++;
				}
				mapResult.add(map);
			}
		}
		return mapResult;
	}
	
	/**
	 * JPA Native Query�? ?��?��?���? 반환 값이 List<Object[]> ?��?���? 반환?�� 
	 * 
	 * List<Object[]> --> List<Map<String, String> ?��?���? �?�? 
	 * @param results
	 * @param columns  
	 * ?��?��?�� ?���? column  ?���?
	 * 
	 * @return
	 */
	

	public static List<Map<String, String>> objectToStringMap(List<Object[]> results, List<String> columns){
		
		Map<Integer, String> idxMap = new HashMap<Integer, String>();
		for(int i=0;i<columns.size();i++){
			String column=columns.get(i).trim();
			idxMap.put(i, column);
		}
		
		return objectToStringMap(results, idxMap);
	}
	
	
	/**
	 * JPA Native Query�? ?��?��?���? 반환 값이 List<Object[]> ?��?���? 반환?�� 
	 * 
	 * List<Object[]> --> List<Map<String, String> ?��?���? �?�? 
	 * @param results
	 * @param columns String 문자?�� column1, column2  
	 * ?��?��?�� ?���? column  ?���?
	 * 
	 * @return
	 */
	

	public static List<Map<String, String>> objectToStringMap(List<Object[]> results, String columns){
		
		
		Map<Integer, String> idxMap = new HashMap<Integer, String>();
		
		StringTokenizer st = new StringTokenizer(columns, ",");
		
		Integer idx =0;
		while(st.hasMoreTokens()){
			String column = st.nextToken().trim();
			idxMap.put(idx, column);
			idx++;
		}
		
		
		return objectToStringMap(results, idxMap);
	}
	
	public static Map<Integer, String> getColumnMap(String sql){
		
		Map<Integer, String> columns = new HashMap<Integer, String>();
		
		String regex = "\\__.+?\\s";		
		
		
		Pattern pattern = Pattern.compile(regex);		
		Matcher match = pattern.matcher(sql);
		int idx=0;
		while(match.find()) 
		{
			String fromSlice  = match.group().trim();
			fromSlice=fromSlice.replace("__", "");
			fromSlice=fromSlice.replace(",", "");
			//System.out.println(fromSlice);
			columns.put(idx, fromSlice);
			idx++;
		}
		
		return columns;
		
	}

}
