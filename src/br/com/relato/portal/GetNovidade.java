/*
 * Created on 17/11/2004
 */
package br.com.relato.portal;

import java.util.List;

import br.com.relato.criteria.SQLExpression;
import br.com.relato.criteria.SearchCriteria;

/**
 * @author Daniel
 */
public class GetNovidade {

	public static List get(String args[]){
		SearchCriteria search = new SearchCriteria();
		search.addField("idibiblia");
		search.addField("DssVersiculo");
		search.addTable("biblia");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "nmslivro", args[0]);
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "CdiCapitulo", args[1]);
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "CdiVersiculo", args[2]);
		
		List list = search.execute();
		if ( list.size() != 0 ){
			List row = (List)list.get(0);
			return row;
		}
		return null;
	}

	public static String getDescricao(String id){
		SearchCriteria search = new SearchCriteria();
		search.addField("Description");
		search.addTable("novidade");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "EventID", id);
		
		List list = search.execute();
		if ( list.size() != 0 )
			return (String)list.get(0);
		return "Nenhuma novidade encontrada!";
	}
	
	public static List getAll(String id){
		SearchCriteria search = new SearchCriteria();
		search.addField("Description");
		search.addField("Sdate");
		search.addField("nmstitulo");
		search.addTable("novidade");
		search.addExpression(String.valueOf(SQLExpression.SQL_IGUAL), "EventID", id);
		
		List list = search.execute();
		if ( list.size() != 0 )
			return list;
		return null;
	}	
}
