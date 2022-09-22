/*
 * Created on 16/11/2004
 */
package br.com.relato.criteria;

import org.apache.commons.lang.StringUtils;

/**
 * @author Daniel
 */
public class SQLExpression {

	public static final int SQL_IGUAL = 1;
	public static final int SQL_MENOR = 2;
	public static final int SQL_MENOR_IGUAL = 3;
	public static final int SQL_MAIOR = 4;
	public static final int SQL_MAIOR_IGUAL = 5;
	public static final int SQL_DIFIRENTE = 6;
	public static final int SQL_CONTENHA = 7;
	public static final int SQL_NCONTENHA = 8;
	public static final int SQL_INICIE = 9;
	public static final int SQL_TERMINE = 10;
	public static final int SQL_IN = 11;
	public static final int SQL_NOT_IN = 12;

	public static String getOperador(int act, String value) {
		return getOperador(String.valueOf(act), value); 
	}	
	
	public static String getOperador(String act, String value) {
		int action = 7;
		try{
			action = Integer.parseInt(act);
		}catch(Exception e){
			;
		}
		
		if ( action == SQL_IGUAL)
			return " = '" + value + "'";
		else if ( action == SQL_MENOR)
			return " < '" + value + "'";
		else if ( action == SQL_MENOR_IGUAL)
			return " <= '" + value + "'";
		else if ( action == SQL_MAIOR)
			return "> '" + value + "'";
		else if ( action == SQL_MAIOR_IGUAL)
			return ">= '" + value + "'";
		else if ( action == SQL_DIFIRENTE)
			return "!= '" + value + "'";
		else if ( action == SQL_CONTENHA)
			return " like  '%" + value + "%'";
		else if ( action == SQL_NCONTENHA)
			return " not like  '%" + value + "%'";
		else if ( action == SQL_INICIE)
			return " like '" + value + "%'";
		else if ( action == SQL_TERMINE)
			return " like '%" + value + "'";
		
		return " = '" + value + "'";
	}	

	public static String getOperador(int action, String[] values) {
		String q = "";
		if ( action == SQL_IN)
			q = " in ( '";
		else if ( action == SQL_NOT_IN)
			q = " not in ( '";

		for ( int i = 0; i < values.length; i ++ ){
			if ( (i+1) == values.length)
				q += values[i];
			else
				q += values[i] + "','";
		}
			
		return q + "')";
	}
	
	public static String getInterval(String field, String valor1, String valor2) {
		if ( !StringUtils.isEmpty(valor1) && !StringUtils.isEmpty(valor2) )
			return field + getOperador(SQL_MAIOR_IGUAL, valor1) + " and "
				+ field + getOperador(SQL_MENOR_IGUAL, valor2);
		else if (!StringUtils.isEmpty(valor1))
			return field + getOperador(SQL_IGUAL, valor1);
		else if (!StringUtils.isEmpty(valor2))
			return field + getOperador(SQL_IGUAL, valor2);
					
		return "";
	}

}
