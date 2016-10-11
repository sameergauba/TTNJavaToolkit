package com.ttnd.toolkit.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import com.ttnd.toolkit.generator.ExcelGenerator;
import com.ttnd.toolkit.generator.impl.ExcelGeneratorImpl;

public class ExcelGeneratorTest {

	@Test
	public void createExcelTest() throws IOException{
		ExcelGenerator e = new ExcelGeneratorImpl();
		e.setFileName(""+"generator.xls");
		List<String> headerRow = new ArrayList<String>();
		headerRow.add("Employee No");
		headerRow.add("Employee Name");
		headerRow.add("Employee Address");

		List<String> firstRow = new ArrayList<String>();
		firstRow.add("1111");
		firstRow.add("Gautam");
		firstRow.add("India");

		List<List<String>> recordToAdd = new ArrayList<List<String>>();
		recordToAdd.add(headerRow);
		recordToAdd.add(firstRow);
		e.createExcel(recordToAdd);
		e.addToExcelFile();
	}
	
	@Test
	public void createExcelMultiSheetsTest() throws IOException{
		ExcelGenerator e = new ExcelGeneratorImpl();
		e.setFileName(""+"generator.xls");
		List<String> headerRow = new ArrayList<String>();
		headerRow.add("Employee No");
		headerRow.add("Employee Name");
		headerRow.add("Employee Address");

		List<String> firstRow = new ArrayList<String>();
		firstRow.add("1111");
		firstRow.add("Gautam");
		firstRow.add("India");

		List<List<String>> recordToAdd = new ArrayList<List<String>>();
		recordToAdd.add(headerRow);
		recordToAdd.add(firstRow);
		
		e.setSheetName("Sheet");
		
		e.createExcel(recordToAdd);
		e.createExcel(recordToAdd);
		e.addToExcelFile();
	}
	
	@Test(expected = NullPointerException.class)
	public void dataListNullTest() throws FileNotFoundException{
		ExcelGenerator e = new ExcelGeneratorImpl();
		e.createExcel(null);
	}
	
	@Test
	public void setterGetterTest(){
		ExcelGenerator e = new ExcelGeneratorImpl();
		e.setSheet(e.getWorkbook().createSheet());
		e.setSheetName(e.getSheetName());
		e.setWorkbook(e.getWorkbook());
	}
	
	@AfterClass
	public static void destroy() {
		String directoryPath = "";
		directoryPath += "generator.xls";
		File f = new File(directoryPath);
		if (f.exists()) {
			f.delete();
		}
	}

}
