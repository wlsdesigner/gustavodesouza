/*
 * Created on 17/11/2004
 */
package br.com.relato.portal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.relato.criteria.SQLExpression;
import br.com.relato.criteria.SearchCriteria;

/**
 * @author Daniel
 */
public class Novidade { 

	final static String INIT = "<font size=\"2\" ><strong>";
	final static String HALF = "</strong><br></font><font size=\"2\" >";
	final static String END = "</font>";
	
	final static String DEFAULT_NOVIDADE = "";

	public static String getNovidade(boolean mode){
		SearchCriteria search = new SearchCriteria();
		search.addField("nmstitulo");
		search.addField("Description");
		search.addTable("novidade");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "Sdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		List list = search.execute();
		if ( list.size() != 0 ){
			List row = (List)list.get(0);
			if ( mode ){
				return INIT + row.get(0) + HALF + row.get(1) + END; 
			}else{
				return row.get(0)+ ". " + row.get(1);
			}
		}
		
		return DEFAULT_NOVIDADE;
	}
	
	public static String getNovidade(boolean mode, int lingua){
		SearchCriteria search = new SearchCriteria();
		search.addField("nmstitulo");
		search.addField("Description");
		search.addTable("novidade");
		search.addExpression(String.valueOf(SQLExpression.SQL_MAIOR_IGUAL), "Sdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		//.SQL_IGUAL
		List list = search.execute();
		System.out.println("Consulta de novidades:: Lista:\n"+list);
		if ( list.size() != 0 ){
			List row = (List)list.get(0);
			if ( mode ){
				return INIT + row.get(0) + HALF + row.get(1) + END; 
			}else{
				return row.get(0)+ ". " + row.get(1);
			}
		}
		
		return DEFAULT_NOVIDADE;
	}
}	
