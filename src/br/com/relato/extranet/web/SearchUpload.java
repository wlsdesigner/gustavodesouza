/*
 * Created on 16/11/2004
 */
package br.com.relato.extranet.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.relato.Constants;
import br.com.relato.EntryPoint;
import br.com.relato.criteria.SQLExpression;
import br.com.relato.criteria.SearchCriteria;
import br.com.relato.util.Cast;

/**
 * @author Daniel
 */
public class SearchUpload {
	final static String DEFAULT_VALUE = "nmsarquivo";
	
	public static List execute(String action, String field, String value, String type){
		SearchCriteria criteria = new SearchCriteria();

		if ( StringUtils.isEmpty(field) )
			field = DEFAULT_VALUE;
		
		value = parseField(field, value);
		
		criteria.addField("idihistupdoc");
		criteria.addField("nmsarquivo");
		criteria.addField("dssobservacoes");
		criteria.addField("inclusao.cdspadrao");
		criteria.addField("dhdupload");
		criteria.addTable("histupdoc");
		criteria.addLeftJoin("usuario", "histupdoc", new String[]{"idiusuario", "idiusuarioinc"}, "inclusao");
		criteria.addExpression(action, field, value);
		
		if ( !Cast.toBoolean(EntryPoint.getUsuario().getAdmin()) )
			criteria.addExpression(SQLExpression.SQL_IGUAL, 
					"idiusuarioprop is null or idiusuarioprop", 
					EntryPoint.getUsuario().getId().toString());
		
		criteria.addOrder("idihistupdoc");
		return criteria.execute();

	}

	public static List execute(String action, String field, String value, String type, String opspublico){
		SearchCriteria criteria = new SearchCriteria();

		if ( StringUtils.isEmpty(field) )
			field = DEFAULT_VALUE;
		
		value = parseField(field, value);
		
		criteria.addField("idihistupdoc");
		criteria.addField("nmsarquivo");
		criteria.addField("dssobservacoes");
		criteria.addField("inclusao.cdspadrao");
		criteria.addField("dhdupload");
		criteria.addTable("histupdoc");
		criteria.addLeftJoin("usuario", "histupdoc", new String[]{"idiusuario", "idiusuarioinc"}, "inclusao");
		criteria.addExpression(action, field, value);
		
		if(!"".equals(opspublico))
			criteria.addExpression(SQLExpression.SQL_IGUAL,"OpsArquivoPublico", opspublico);
		
		if ( !Cast.toBoolean(EntryPoint.getUsuario().getAdmin()) )
			criteria.addExpression(SQLExpression.SQL_IGUAL, 
					"idiusuarioprop is null or idiusuarioprop", 
					EntryPoint.getUsuario().getId().toString());
		
		criteria.addOrder("idihistupdoc");
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
