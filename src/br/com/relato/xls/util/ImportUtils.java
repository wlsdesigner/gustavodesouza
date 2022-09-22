package br.com.relato.xls.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import br.com.dgs.business.IncluiBordero;
import br.com.neorelato.sql.Table;
//import br.com.relato.teste.ImportUtils;
import br.com.relato.util.Cast;

public class ImportUtils
{
	public static Table getExcel(String Endereco, List Campos){
		try {
			
			/************************************************************************************
			 * Este método busca em um arquivo excel, que esteja no endereço passado a função,	*
			 * todas as colunas da primeira linha do arquivo excel, que tenham mesmo texto		*
			 * passados na lista de campos, e retorna estes dados dentro de uma Table.	 		*
			 ************************************************************************************/
			/*
			System.out.println("path == "+Endereco);
			System.out.println("class loader path == "+ (ImportUtils.class.getClassLoader()));
			System.out.println("class loader test x 1 path == "+ (ImportUtils.class.getClassLoader().getResourceAsStream("imprime.jsp")));
			System.out.println("class loader test x 2 path == "+ (ImportUtils.class.getClassLoader().getResourceAsStream("web.xml")));
			System.out.println("class loader test x 3 path == "+ (ImportUtils.class.getClassLoader().getResourceAsStream("/imprime.jsp")));
			System.out.println("class loader test x 4 path == "+ (ImportUtils.class.getClassLoader().getResourceAsStream("/web.xml")));
			System.out.println("class loader test y 5 path == "+ (ImportUtils.class.getResourceAsStream(Endereco)));
			System.out.println("class loader test y 6 path == "+ (ImportUtils.class.getResourceAsStream("imprime.jsp")));
			System.out.println("class loader test y 7 path == "+ (ImportUtils.class.getResourceAsStream("web.xml")));
			System.out.println("class loader test y 8 path == "+ (ImportUtils.class.getResourceAsStream("/imprime.jsp")));
			System.out.println("class loader test y 9 path == "+ (ImportUtils.class.getResourceAsStream("/web.xml")));
			System.out.println("complete path == "+ (ImportUtils.class.getClassLoader().getResourceAsStream(Endereco)) );
			*/
			InputStream input = ImportUtils.class.getClassLoader().getResourceAsStream(Endereco);
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int startRow = sheet.getFirstRowNum();
			int endRow = sheet.getLastRowNum();
			int i=startRow;
			Table tbresultado = new Table();
			Map colmap = new HashMap();
			Object col[] = new Object[0];
			List lstCampos = Campos;
			
			if (i>=0){
				
				HSSFRow row = (HSSFRow) sheet.getRow(i);
				if (row != null){
					int startCell = row.getFirstCellNum();
					int endCell = row.getLastCellNum();
					short j = (short)startCell;
					HSSFCell cell = row.getCell((short)j);
					col = new Object[endCell];
					while( j < endCell ) {
						cell = row.getCell((short)j);
						if (cell != null){
							if(lstCampos.size() > 0){
								for(int x=0;x<lstCampos.size();x++){
									switch (cell.getCellType() ) {
										case HSSFCell.CELL_TYPE_STRING:
											if (cell.getStringCellValue().equals(((String)lstCampos.get(x)))){
												tbresultado.addColumn(((String)lstCampos.get(x)));
												colmap.put(((String)lstCampos.get(x)), new Integer((int)cell.getCellNum()));
												
												/************************************************************************
												 * Teste de Algumas variaveis, pode ser usado para debug				*
												 ************************************************************************
												 
												 System.out.println("________________________________________________");
												 System.out.println("Cell RS Map");
												 System.out.println("Num Cell: "+cell.getCellNum());
												 System.out.println("Val Cell: "+cell.getStringCellValue());
												 System.out.println("Map Num: "+colmap.get(((String)lstCampos.get(x))));
												 System.out.println("------------------------------------------------");
												 
												 ***********************************************************************/
									
											}
										break;
									}
								}
							}
						}
						j++;
					}
					i++;
					while( i <= endRow ) {
					row = (HSSFRow) sheet.getRow(i);
					col = new Object[endCell];
						if (row != null){
							startCell = row.getFirstCellNum();
							endCell = row.getLastCellNum();
							j = (short)startCell;
							cell = row.getCell((short)j);
					
							
							while( j < endCell ) {
								cell = row.getCell((short)j);
								if (null != cell){
									if(lstCampos.size() > 0){
										for(int x=0;x<lstCampos.size();x++){
											switch (cell.getCellType() ) {
												case HSSFCell.CELL_TYPE_STRING:
													if (cell.getCellNum() == Cast.toShort(colmap.get((String)lstCampos.get(x)))){
													col[Cast.toShort(colmap.get((String)lstCampos.get(x)))]=cell.getStringCellValue();
											
													/************************************************************************
													 * Teste de Algumas variaveis, pode ser usado para debug.				*
													 ************************************************************************
													 
													 System.out.println("________________________________________________");
													 System.out.println("Cell RS Obj");
													 System.out.println("Num Cell: "+cell.getCellNum());
													 System.out.println("Num Map: "+colmap.get((String)lstCampos.get(x)));
													 System.out.println("Val Cell: "+cell.getStringCellValue());
													 System.out.println("------------------------------------------------");
													
													 ************************************************************************/
													}
												break;
												case HSSFCell.CELL_TYPE_NUMERIC:
													if (cell.getCellNum() == Cast.toShort(colmap.get((String)lstCampos.get(x)))){
													col[Cast.toShort(colmap.get((String)lstCampos.get(x)))]=(new Integer(Cast.toInt(cell.getNumericCellValue())));
											
													/************************************************************************
													 * Teste de Algumas variaveis, pode ser usado para debug.				*
													 ************************************************************************
													 
													 System.out.println("________________________________________________");
													 System.out.println("Cell RS Obj");
													 System.out.println("Num Cell: "+cell.getCellNum());
													 System.out.println("Num Map: "+colmap.get((String)lstCampos.get(x)));
													 System.out.println("Val Cell: "+cell.getStringCellValue());
													 System.out.println("------------------------------------------------");
													
													 ************************************************************************/
													}
												break;
											}
										}
									}
								}
							j++;
							}
						if (col.length > 0)
							tbresultado.addRow(col);
						}					
					i++;
					}
				}				
				/****************************************************
				 * Impressão da TABLE com todas colunas do Excel.	*
				 ****************************************************
				
				 tbresultado.first();
				 String nomecolunas[] = tbresultado.getColumnNames();
				 int j = 0;
				 while ((tbresultado.rowCount()-1) > j && !tbresultado.eof()) {
					System.out.println("------REGISTRO: "+j+"------");
					for (i=0; i<tbresultado.colCount(); i++) {
						System.out.println(nomecolunas[i]+" = "+
						tbresultado.getObject(nomecolunas[i]));
					}
					System.out.println("--------------------------");
					tbresultado.next();
					j++;
				 }
				 
				 ****************************************************/				
			}
			return tbresultado;
		}catch( IOException ex ) {
			System.out.println("Deu erro no ImportUtils");
			ex.printStackTrace();
			return new Table();
		}
	}
}