/*
 * Created on 27/01/2005
 */
package br.com.relato.portal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.relato.criteria.SQLExpression;
import br.com.relato.criteria.SearchCriteria;

/**
 * @author daniel
 */
public class GetPensamento {

	public static List get(String args[]){
		SearchCriteria search = new SearchCriteria();
		search.addField("idipensamento");
		search.addField("nmsdescricao");
		search.addTable("pensamento");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "nmsautor", args[0]);
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "cdipadrao", args[1]);
		
		List list = search.execute();
		if ( list.size() != 0 ){
			List row = (List)list.get(0);
			return row;
		}
		return null;
	}

	public static String getDescricao(String args[]){
		SearchCriteria search = new SearchCriteria();
		search.addField("nmsdescricao");
		search.addTable("pensamento");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "nmsautor", args[0]);
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "cdipadrao", args[1]);
		
		List list = search.execute();
		if ( list.size() != 0 ){
			return (String)list.get(0);
		}
		return "";
	}

	public static List getCodigos(String autor){
		SearchCriteria search = new SearchCriteria();
		search.addField("cdipadrao");
		search.addField("nmsdescricao");
		search.addTable("pensamento");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "nmsautor", autor);
		search.addOrder("cdipadrao");
		
		List list = search.execute();
		if ( list.size() != 0 )
			return list;
		return null;
	}		
	
	public static List getAutor(){
		SearchCriteria search = new SearchCriteria();
		search.addField("distinct(nmsautor)");
		search.addTable("pensamento");
		search.addOrder("nmsautor");
		
		List list = search.execute();
		if ( list.size() != 0 )
			return list;
		return null;
	}	
	
	public static String getDescricao(String id){
		SearchCriteria search = new SearchCriteria();
		search.addField("Description");
		search.addTable("calendario");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "EventID", id);
		
		List list = search.execute();
		if ( list.size() != 0 )
			return (String)list.get(0);
		return "Nenhum pensamento encontrado!";
	}
	
	public static List getAll(String id){
		SearchCriteria search = new SearchCriteria();
		search.addField("calendario.idipensamento");
		search.addField("nmsautor");
		search.addField("nmsdescricao");
		search.addField("Sdate");
		search.addField("cdipadrao");
		search.addTable("calendario");
		search.addInnerJoin("pensamento", "calendario", "idipensamento");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "EventID", id);
		
		List list = search.execute();
		if ( list.size() != 0 )
			return list;
		return null;
	}	
	
	final static String INIT = "<font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">\n";
	final static String HALF = "\n<br></font><font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\"><strong><div align=\"right\">\n";
	final static String END = "\n</div></strong></font>";
	
	final static String DEFAULT_PEN_1 = "<font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">" +
		" Hay que endurecer pero sin perder la ternura jamas.<br> " +
		"</font><font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">" +
		"<strong>Ernesto Che Guevara</strong></font>";

	final static String DEFAULT_PEN_2 = " Hay que endurecer pero sin perder la ternura jamas. Ernesto Che Guevara";
	
	public static String getPensamento(boolean mode){
		SearchCriteria search = new SearchCriteria();
		search.addField("idipensamento");
		search.addTable("calendario");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "Sdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		List list = search.execute();
		if ( list.size() != 0 ){
			search.clear();
			search.addField("nmsautor");
			search.addField("nmsdescricao");
			search.addTable("pensamento");
			search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "idipensamento", String.valueOf(list.get(0)));
			List ver = search.execute();
			if ( ver.size() != 0 ){
				List row = (List)ver.get(0);
				if ( mode ){
					return INIT + row.get(1) + HALF + row.get(0) + END; 
				}else{
					return row.get(1) + "." + row.get(0);
				}
			}
		}
		
		if ( mode )
			return DEFAULT_PEN_1;
		else
			return DEFAULT_PEN_2;
	}
	
}
