package br.com.relato.util;


import java.io.UnsupportedEncodingException;

public class StringUtils {

	static final String UNDERLINE = "_";
	static final String a = "a";
	static final String e = "e";
	static final String i = "i";
	static final String o = "o";
	static final String u = "u";
	static final String c = "c";
	static final String n = "n";
	
	static final String ESPACO = " ";
	
	static final String cAcento1 = "�";
	static final String nAcento1 = "�";
	
	static final String aAcento1 = "�";
	static final String aAcento2 = "�";
	static final String aAcento3 = "�";
	static final String aAcento4 = "�";
	static final String aAcento5 = "�";
	static final String eAcento5 = "�";
	static final String iAcento5 = "�";
	static final String oAcento5 = "�";
	static final String uAcento5 = "�";
	
	static final String eAcento1 = "�";
	static final String eAcento2 = "�";
	static final String eAcento3 = "�";
	
	static final String iAcento1 = "�";
	static final String iAcento2 = "�";
	static final String iAcento3 = "�";
	
	static final String oAcento1 = "�";
	static final String oAcento2 = "�";
	static final String oAcento3 = "�";
	static final String oAcento4 = "�";
	
	static final String uAcento1 = "�";
	static final String uAcento2 = "�";
	static final String uAcento3 = "�";
	
	public static String toSiteUrl(String text){
		String url = null!=text?text.trim().toLowerCase():"";
		System.out.println("URL ORIGINAL::'"+url+"'");
		url = url.replaceAll(aAcento1, a);
		url = url.replaceAll(aAcento2, a);
		url = url.replaceAll(aAcento3, a);
		url = url.replaceAll(aAcento4, a);
		url = url.replaceAll(aAcento5, a);
		url = url.replaceAll(eAcento1, e);
		url = url.replaceAll(eAcento2, e);
		url = url.replaceAll(eAcento3, e);
		url = url.replaceAll(eAcento5, e);
		url = url.replaceAll(iAcento1, i);
		url = url.replaceAll(iAcento2, i);
		url = url.replaceAll(iAcento3, i);
		url = url.replaceAll(iAcento5, i);
		url = url.replaceAll(oAcento1, o);
		url = url.replaceAll(oAcento2, o);
		url = url.replaceAll(oAcento3, o);
		url = url.replaceAll(oAcento4, o);
		url = url.replaceAll(oAcento5, o);
		url = url.replaceAll(uAcento1, u);
		url = url.replaceAll(uAcento2, u); 
		url = url.replaceAll(uAcento3, u);
		url = url.replaceAll(uAcento5, u);
		url = url.replaceAll(cAcento1, c);
		url = url.replaceAll(nAcento1, n);

		//url = url.replaceAll(ESPACO, UNDERLINE);
		//Substitui o que n�o � caractere nem n�mero. 
		for(int x = 0;x<url.length();x++){
			if(!Character.isLetterOrDigit(url.charAt(x))){
				String replace = String.valueOf(url.charAt(x));
				url = url.replace(replace, UNDERLINE);
			}
		}
		//Retira acentos
		System.out.println("URL SITE::'"+url+"'");
		return url;
	}
	
	public static void main(String[] args) {
		String urlSemFormato = "Sobre..,, o Empres�o de S�o Paulo � Macap� !!";
		urlSemFormato = toSiteUrl(urlSemFormato);
	}
	
	public static String toUTF8(String isoString) {   
		String utf8String = null;   
	    if (null != isoString && !isoString.equals(""))   
	    {   
	        try   
	        {   
	            byte[] stringBytesISO = isoString.getBytes("ISO-8859-1");   
	            utf8String = new String(stringBytesISO, "UTF-8");   
	        }   
	        catch(UnsupportedEncodingException e)   
	        {   
	            // Mostra exce��o mas devolve a mesma String   
	            System.out.println("UnsupportedEncodingException: " + e.getMessage());   
	            utf8String = isoString;   
	        }   
	    }   
	    else   
	    {   
	        utf8String = isoString;   
	    }   
	    return utf8String;   
	//	return isoString;   
	} 

	public static String toISO(String isoString) {   
		String utf8String = null;   
	    if (null != isoString && !isoString.equals(""))   
	    {   
	        try   
	        {   
	            byte[] stringBytesISO = isoString.getBytes("UTF-8");   
	            utf8String = new String(stringBytesISO, "ISO-8859-1");   
	        }   
	        catch(UnsupportedEncodingException e)   
	        {   
	            // Mostra exce��o mas devolve a mesma String   
	            System.out.println("UnsupportedEncodingException: " + e.getMessage());   
	            utf8String = isoString;   
	        }   
	    }   
	    else   
	    {   
	        utf8String = isoString;   
	    }   
	//    return utf8String;   
	    
		return isoString;  
	} 
	

}
