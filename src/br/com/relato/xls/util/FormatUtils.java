package br.com.relato.xls.util;

import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.hlt.util.Formatos;

public class FormatUtils {

	/**
	 * @param args
	 */
	public static Locale pt_BR = new Locale("pt", "BR");
	public static NumberFormat df = NumberFormat.getCurrencyInstance(pt_BR);
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	static DateFormat df1 = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("en", "US"));   
	static DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM, pt_BR );
	
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
	
	
    
	public static String expressaoPrimeiroDiaMes() {
		return expressaoPrimeiroDiaMes(new Date());
	}
	
	public static String expressaoPrimeiroDiaMes(Date dtRef) {
	
        Calendar cldr = java.util.Calendar.getInstance();
		if ( dtRef != null ) cldr.setTime(dtRef);
        cldr.set(Calendar.DAY_OF_MONTH, 1);
        //System.out.println( "Primeiro dia do mês atual = " + cldr.getTime());
		Date dtRet = cldr.getTime();
		return formatter.format(dtRet);
	}
	
	public static String expressaoUltimoDiaMes() {
		return expressaoUltimoDiaMes(new Date());
	}

	public static String expressaoUltimoDiaMes(Date dtRef) {
	
        Calendar cldr = java.util.Calendar.getInstance();
		if ( dtRef != null ) cldr.setTime(dtRef);
		cldr.add(Calendar.MONTH, +1);
		cldr.set(Calendar.DAY_OF_MONTH, 1);
		cldr.add(Calendar.DAY_OF_MONTH, -1);
        //System.out.println( "Primeiro dia do mês atual = " + cldr.getTime());
		Date dtRet = cldr.getTime();
		return formatter.format(dtRet);
	}
	
	public static String dateformat(Date date){
		if (date != null)
			return df2.format(date);
		return "";
	}
	
	public static Date converteStringMicrosigaData( String dataBarras ) {
		return Formatos.parseStringToDate(adicionaBarrasExpressaoData(dataBarras ));
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
