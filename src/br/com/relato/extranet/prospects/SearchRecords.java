/*
 * Created on 11/02/2005
 */
package br.com.relato.extranet.prospects;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.relato.criteria.SQLExpression;
import br.com.relato.criteria.SearchCriteria;

/**
 * @author daniel
 */
public class SearchRecords { 
	final static String DEFAULT_VALUE = "NmsCompleto";
	
	public static List execute(String action, String field, String value){
		SearchCriteria criteria = new SearchCriteria();
		
		if ( StringUtils.isEmpty(field) )
			field = DEFAULT_VALUE;
		
		if ( !StringUtils.isEmpty(value) )
			value = parseField(field, value);
		
		criteria.addField(new String[]{"IdiProspect", "NmsCompleto", "CdsEmail",
				"dssendereco", "nmsbairro", "nmscidade", "nmsestado", "nmspais",
				"cdscep", "cdstelefone","nmsempresa","opsativo","opsdescadastre"});
		criteria.addTable("prospect");
		criteria.addExpression(action, field, value);
		criteria.addOrder("idiprospect limit 500");
		
		return criteria.execute();

	}

	private static String parseField(String field, String value) {
		if ( field.equals("opseditor") 
				|| field.equals("opspublicador") 
				|| field.equals("opsadministrador")
				|| field.equals("opsativo")){
			if ( "sim".equals( value.toLowerCase() ) )
				return "1";
			else if ( "nao".equals( value.toLowerCase() )
					|| "não".equals( value.toLowerCase() ))
				return "0";
		}
		
		return value;
	}
	
	public static List execute(){
		SearchCriteria criteria = new SearchCriteria();
		
		criteria.addField(new String[]{"IdiProspect", "NmsCompleto"});
		criteria.addTable("prospect");
		//criteria.addExpression(SQLExpression.SQL_DIFIRENTE, "OpsAdministrador", "1");
		criteria.addOrder("NmsCompleto limit 500");
		
		return criteria.execute();

	}	
}