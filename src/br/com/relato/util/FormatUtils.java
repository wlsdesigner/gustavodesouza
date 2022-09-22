package br.com.relato.util;

import java.util.*;
import java.math.BigDecimal;
import java.text.*;



public class FormatUtils {

	public static Locale pt_BR = new Locale("pt", "BR");
	public static NumberFormat df = NumberFormat.getCurrencyInstance(pt_BR);
	public static NumberFormat dfn = NumberFormat.getNumberInstance(pt_BR);
	public static DateFormat df1 = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("en", "US"));   
	public static DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM, pt_BR );
	
	public static String dateformat(String date){
		Date data = Cast.toDate(date);
		if (date != null)
			return df2.format(date);
		return "";
	}
	
	public static String dateformat(Date date){
		if (date != null)
			return df2.format(date);
		return "";
	}
	public static String formataValor( double value ) {
		String valor = df.format(Cast.toBigDecimal(value).setScale(2,BigDecimal.ROUND_HALF_EVEN));
		return valor.replaceAll("R\\$ ", "");
	}
	
	public static String formataValor( Object value ) {
		String valor = df.format(Cast.toBigDecimal(value).setScale(2,BigDecimal.ROUND_HALF_EVEN));
		return valor.replaceAll("R\\$ ", "");
	}
	
	public static String formataValor( BigDecimal value ) {
		String valor = df.format(Cast.toBigDecimal(value).setScale(2,BigDecimal.ROUND_HALF_EVEN));
		return valor.replaceAll("R\\$ ", "");
	}
	
	public static String formataValor1( double value ) {
		dfn.setMinimumFractionDigits(0);
		String valor = dfn.format(Cast.toBigDecimal(value).setScale(0,BigDecimal.ROUND_HALF_EVEN));
		return valor.replaceAll("\\.","").replaceAll("\\,",".");
	}
	
	public static String formataValor1( Object value ) {
		dfn.setMinimumFractionDigits(0);
		String valor = dfn.format(Cast.toBigDecimal(value).setScale(0,BigDecimal.ROUND_HALF_EVEN));
		return valor.replaceAll("\\.","").replaceAll("\\,",".");
	}
	
	public static String formataValor3( Object value ) {
		dfn.setMinimumFractionDigits(3);
		dfn.setMaximumFractionDigits(3);
		String valor = dfn.format(Cast.toBigDecimal(value).setScale(3,BigDecimal.ROUND_HALF_EVEN));
		return valor;
	}
	
	public static String formataValor4( BigDecimal value ) {
		dfn.setMinimumFractionDigits(4);
		String valor = dfn.format(Cast.toBigDecimal(value).setScale(4,BigDecimal.ROUND_HALF_EVEN));
		return valor;
	}
	
	public static int diferencaEmDias(Date d1, Date d2) {
			long m1 = d1.getTime();
			long m2 = d2.getTime(); 
			
			return (int) ((m2 - m1) / (24*60*60*1000));
	}
	
	public static String adicionaBarrasExpressaoData(String dataMicrosiga) {
			//01234567
			//yyyyMMdd ( transforma a data microsiga colocando barras Exemplo: 20050617 -> 17/06/2005
			String dataBarras = dataMicrosiga.substring(6,8) + "/" +
	                            dataMicrosiga.substring(4,6) + "/" +
	                            dataMicrosiga.substring(0,4);
	        return dataBarras;
	}                        
		
		
	public static String retiraBarrasExpressaoData(String dataMicrosiga) {
			//01234567
			//yyyyMMdd ( transforma a data microsiga colocando barras Exemplo: 20050617 -> 17/06/2005
			String dataBarras = dataMicrosiga.substring(6,10) +
								dataMicrosiga.substring(3,5) + 		
								dataMicrosiga.substring(0,2) ;
	        return dataBarras;
	 }                        
	
	public static String mudaCor(String cClasseLinha){
	    if (cClasseLinha.equals("tableRowOdd")) 
	    	cClasseLinha = "tableRowEven"; 
	    else 
	    	cClasseLinha = "tableRowOdd";
	    return cClasseLinha;
	}	
	
	public static Date tresMesesAntes() {
		return tresMesesAntes(new Date());
	} 

	public static Date tresMesesAntes(Date dtRef) {

	    Calendar cldr = java.util.Calendar.getInstance();
		
		if ( dtRef != null ) cldr.setTime(dtRef);
	    cldr.add(Calendar.MONTH, -3);
	    //System.out.println( "Primeiro dia do mês atual = " + cldr.getTime());
		Date dtRet = cldr.getTime();
		return dtRet;
	}

	public static String retiraBarrasExpressaoDataTraco(String dataMicrosiga) {
		//01234567
		//yyyyMMdd ( transforma a data microsiga colocando barras Exemplo: 20050617 -> 17/06/2005
		String dataBarras = dataMicrosiga.substring(6,10) + "-" +
							dataMicrosiga.substring(3,5) + "-" +
							dataMicrosiga.substring(0,2) ;
	    return dataBarras;
	}                        

	public static String retiraExpressaoData(String dataMicrosiga) {
		//01234567
		//yyyyMMdd ( transforma a data microsiga colocando barras Exemplo: 20050617 -> 17/06/2005
		String dataBarras = dataMicrosiga.substring(0,4) + "-" +
							dataMicrosiga.substring(4,6) + "-" +
							dataMicrosiga.substring(6,8) ;
	    return dataBarras;
	}   


}
