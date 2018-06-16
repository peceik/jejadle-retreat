package org.jejadle.retreat.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {
	
	/**
	 * 
	 * @param listColumnTitleMap (full excel column) 전체 Excel  컬럼
	 * @param resultList 결과 목록 (result data)
	 * @param excelColumns 선택된 Excel 컬럼(user selected column)
	 * @param fileName 결과 파일명 (excel file name)
	 * @return
	 */
	
	public static Map<String, Object> getExcelDataAndTitles(
					LinkedHashMap<String, String> listColumnTitleMap
					,List<Map<String, String>> resultList
					,List<String> excelColumns
					,String fileName){
		
		List<String> excelTitles = new ArrayList<String>();
		
		for(String key:excelColumns){
			excelTitles.add(listColumnTitleMap.get(key));
		}
		
		
		//순서에 맞는 결과 출력
		//column filtering
		List<LinkedHashMap<String, String>> excelResults= new ArrayList<LinkedHashMap<String, String>>();
		for(Map<String, String> map:resultList){
			LinkedHashMap<String, String> result = new LinkedHashMap<String, String>(); 
			for(String key:excelColumns){
				//출력결과에 없을 수도 있으므로 
				if(map.containsKey(key)){
					result.put(key, map.get(key));
				}
			}
			excelResults.add(result);
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("excelResults", excelResults);
		model.put("titles", excelTitles);
		model.put("fileName", fileName);
		
		return model;
		
	}
	
	/**
	 * 모든 컬럼을 Excel 파일로 만들어 준다.
	 * @param listColumnTitleMap
	 * @param resultList
	 * @param fileName
	 * @return
	 */
	
	public static Map<String, Object> getExcelDataAndTitles(
			LinkedHashMap<String, String> listColumnTitleMap
			,List<Map<String, String>> resultList			
			,String fileName){
		
		List<String> excelColumns = new ArrayList<String>();
		
		Iterator<String> it = listColumnTitleMap.keySet().iterator();
		
		while(it.hasNext()){
			String key = it.next();
			excelColumns.add(key);
		}
		
		return getExcelDataAndTitles(listColumnTitleMap, resultList, excelColumns, fileName);
	}
	
	

}
