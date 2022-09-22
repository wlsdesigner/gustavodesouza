/*
 * Created on 16/11/2004
 */
package br.com.relato.htmledit.util;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.relato.EntryPoint;
import br.com.relato.criteria.SQLExpression;
import br.com.relato.criteria.SearchCriteria;
import br.com.relato.util.Cast;

/**
 * @author Daniel
 */
public class SearchConteudo {
	final static String DEFAULT_VALUE = "texto";
	
	public static List execute(String action, String field, String value){
		SearchCriteria criteria = new SearchCriteria();

		if ( StringUtils.isEmpty(field) )
			field = DEFAULT_VALUE;
		
		value = parseField(field, value);
		
		criteria.addField("id");
		criteria.addField("titulo");
		criteria.addField("tipo");
		criteria.addField("criacao.cdspadrao");
		criteria.addField("datahoracriacao");
		criteria.addField("alteracao.cdspadrao");
		criteria.addField("datahoraultalt"); 
		criteria.addField("replicacao");
		criteria.addField("ativo");
		criteria.addTable("conteudo");
		criteria.addLeftJoin("usuario", "conteudo", new String[]{"idiusuario", "usuariocriacao"}, "criacao");
		criteria.addLeftJoin("usuario", "conteudo", new String[]{"idiusuario", "usuarioultalt"}, "alteracao");
		criteria.addExpression(action, field, value);
		
		if ( EntryPoint.isEditor() )
			criteria.addExpression(SQLExpression.SQL_IGUAL, "usuariocriacao", String.valueOf(EntryPoint.getUser()));
		
		criteria.addOrder("id");
		return criteria.execute();

	}

	private static String parseField(String field, String value) {
		if ( field.equals("datahoracriacao") || field.equals("datahoraultalt")){
			try{
				return new SimpleDateFormat("yyyy-MM-dd").format(Cast.toDate(value));
			}catch(Exception e){
				return value;
			}
		}else if(field.equals("tipo")){
			if( value.substring(0,1).equals("c") || value.substring(0,1).equals("C"))
				return "C";
			else if( value.substring(0,1).equals("n") || value.substring(0,1).equals("N"))
				return "N";
			else if( value.substring(0,1).equals("a") || value.substring(0,1).equals("A"))
				return "A";			
		}else if(field.equals("replicacao")){
			if( value.substring(0,1).equals("s") || value.substring(0,1).equals("S"))
				return "S";
			else if( value.substring(0,1).equals("n") || value.substring(0,1).equals("N"))
				return "N";
		}
			
		return value;
	}
}
