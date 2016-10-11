package com.ttnd.toolkit.generator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface ExcelGenerator {

	/**
	 * @return the name of the excel file which is generated.
	 */
	String getFileName();

	/**
	 * @param fileName
	 *            the name of the excel file to be generated
	 */
	void setFileName(String fileName);

	/**
	 * @param workbook
	 *            the workbook to which new generated sheets will be added after
	 *            data is written to them.
	 */
	void setWorkbook(HSSFWorkbook workbook);

	/**
	 * @param sheet
	 *            the sheet to which data will be written and then added to the
	 *            workbook.
	 */
	void setSheet(HSSFSheet sheet);

	/**
	 * @param sheetName
	 *            the name of the sheets that will be added to the workbook. An
	 *            integer is added to this name which increments as the sheets
	 *            are added.
	 */
	void setSheetName(String sheetName);

	/**
	 * @return the name of the sheets added to workbook
	 */
	String getSheetName();

	/**
	 * @return the workbook to which sheets are added after writing the data to
	 *         them.
	 */
	HSSFWorkbook getWorkbook();

	/**
	 * Writes the data, given in the parameter by the user, on the sheet
	 * returned by getSheet() method of this class
	 * 
	 * @param dataList
	 *            The list of the lists of strings, where each list of string
	 *            represent the data in each column of the excel sheet.
	 */
	void createExcel(List<List<String>> dataList) throws FileNotFoundException;

	/**
	 * Writes the data from the workbook created to a file with filename as set
	 * by the user in the field fileName.
	 * @throws IOException 
	 */
	void addToExcelFile() throws IOException;

}