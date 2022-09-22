/*
 * Created on 16/11/2004
 */
package br.com.relato.enquete;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.relato.EntryPoint;
import br.com.relato.criteria.SQLExpression;
import br.com.relato.criteria.SearchCriteria;
import br.com.relato.util.Cast;

/**
 * @author Daniel
 */
public class SearchEnquete {
	final static String DEFAULT_VALUE = "cdspergunta";
	
	public static List execute(String action, String field, String value){
		SearchCriteria criteria = new SearchCriteria();

		if ( StringUtils.isEmpty(field) )
			field = DEFAULT_VALUE;
		
		value = parseField(field, value);
		
		criteria.addField("idienquete");
		criteria.addField("cdspergunta");
		criteria.addField("opsativo");
		criteria.addTable("enquete");
		criteria.addExpression(action, field, value);
		
		criteria.addOrder("idienquete");
		return criteria.execute();

	}

	private static String parseField(String field, String value) {
		if ( field.equals("datahoracriacao") || field.equals("datahoraultalt")){
			try{
				return new SimpleDateFormat("yyyy-MM-dd").format(Cast.toDate(value));
			}catch(Exception e){
				return value;
			}
		
		}
			
		return value;
	}

}
