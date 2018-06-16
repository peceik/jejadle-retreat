package org.jejadle.retreat.front.advice;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;




@Component("excelDownloadView")
public class ExcelReportView extends AbstractExcelView {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelReportView.class);
	
	/**
	 * AbstractExcelView 는 deprecated 된 class 이지만 
	 * 기존 Excel 에서는 파일이 깨져서 나오므로 이렇게 사용
	 */
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//workbook = new HSSFWorkbook();
		
		String fileName=(String) model.get("fileName");
		fileName=URLEncoder.encode(fileName,"UTF-8");

		response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+".xls\"");
		
		//데이터 형태가 List<Map<String, String>> 로 입력이 되어야 함 

		@SuppressWarnings("unchecked")
		List<Map<String, String>> results = (List<Map<String, String>>) model.get("excelResults");
		
		@SuppressWarnings("unchecked")
		List<String> titles = (List<String>) model.get("titles");
		
		@SuppressWarnings("unchecked")
		Set<String> autoMerges = (Set<String>) model.get("autoMerges");

		// create excel xls sheet
		Sheet sheet = workbook.createSheet("Sheet");
		
		
		CellStyle styleHeadr = workbook.createCellStyle();
		styleHeadr.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		styleHeadr.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styleHeadr.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeadr.setBorderRight(HSSFCellStyle.BORDER_THIN);              //테두리 설정   
		styleHeadr.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
		styleHeadr.setBorderTop(HSSFCellStyle.BORDER_THIN);   
		
		CellStyle styleCont = workbook.createCellStyle();
		styleCont.setBorderRight(HSSFCellStyle.BORDER_THIN);              //테두리 설정   
		styleCont.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
		styleCont.setBorderTop(HSSFCellStyle.BORDER_THIN);   
		styleCont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleCont.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// create header row
		Row header = sheet.createRow(0);
		int idx=0;
		for(String title:titles){
			Cell cell = header.createCell(idx);
			cell.setCellStyle(styleHeadr);
			cell.setCellValue(title);
			
			idx++;
		}
		
		/*
		header.createCell(0).setCellValue("");
		header.createCell(1).setCellValue("Name");
		header.createCell(2).setCellValue("Date");
		*/

		// Create data cells
		int rowCount = 1;
		Map<String, String> tmpMap = new HashMap<String, String>();
		for (Map<String, String> map : results) {
			Row row = sheet.createRow(rowCount++);
			int cellCount = 0;
			/*
			 * 시간되면 한번 사용해 볼것 
			 * map.forEach((k,v)->{
			 * row.createCell(cellCount).setCellValue(course.getId());
			 * cellCount++; });
			 * 
			 */
			/*
			for (Map.Entry<String, String> entry : map.entrySet()) {
				// System.out.println("Item : " + entry.getKey() + " Count : " +
				// entry.getValue());
				row.createCell(cellCount).setCellValue(entry.getValue());
				cellCount++;
			}
			*/
			
			for(String key:map.keySet()){
				Cell cell =row.createCell(cellCount);
				
				cell.setCellStyle(styleCont);
				//데이터 타입에 따라  cell을 달리 표기
				logger.debug("key:{}", key);
				if(!"phone".equals(key) && NumberUtils.isNumber(map.get(key))){
					cell.setCellValue(NumberUtils.toDouble(map.get(key)));
				}else{
					cell.setCellValue(map.get(key));
				}
				
				
				if( autoMerges!=null && autoMerges.contains(key) && map.get(key).equals(tmpMap.get(key))){
					//System.out.println("merge");
					sheet.addMergedRegion(new CellRangeAddress(row.getRowNum()-1, row.getRowNum(), cellCount, cellCount));
				}else{
					
				}
				
				cellCount++;
				tmpMap.put(key, map.get(key));
				
			}
			
		}
	}

}
