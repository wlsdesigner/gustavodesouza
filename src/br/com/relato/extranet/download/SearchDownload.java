/*
 * Created on 29/03/2005
 */
package br.com.relato.extranet.download;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.relato.criteria.SearchCriteria;
import br.com.relato.util.Cast;
import br.com.relato.util.SqlHelper;

/**
 * @author Administrator
 */
public class SearchDownload {
	final static String DEFAULT_VALUE = "idihistoricodownload";
	
	public static List execute(String action, String field, String value){
		SearchCriteria criteria = new SearchCriteria();

		if ( StringUtils.isEmpty(field) )
			field = DEFAULT_VALUE;
		
		value = parseField(field, value);
		
		criteria.addField("idihistoricodownload");
		criteria.addField("usuario.CdsPadrao");
		criteria.addField("usuario.NmsCompleto");
		criteria.addField("NmsArquivo");
		criteria.addField("dhdbaixa");
		criteria.addTable("historicodownload");
		criteria.addLeftJoin("usuario", "historicodownload", "idiusuario");
		criteria.addLeftJoin("histupdoc", "historicodownload", "idihistupdoc");
		criteria.addExpression(action, field, value);
		
		criteria.addOrder("idihistoricodownload");
		return criteria.execute();

	}

	private static String parseField(String field, String value) {
		if ( field.equals("dhdbaixa") ){
			try{
				return new SimpleDateFormat("yyyy-MM-dd").format(Cast.toDate(value));
			}catch(Exception e){
				return value;
			}
		}

		if ( field.equals("usuario.idiusuario") ){
			try{
				List list = SqlHelper.execute("select idiusuario from usuario " +
						"where nmscompleto like ? or cdspadrao like ? or idiusuario = ?", 
						new String[] {"%"+value+"%", "%"+value+"%", value});
				if ( list.size() != 0 && list.get(0) != null)
					return String.valueOf(list.get(0)); 
			}catch(Exception e){
				return value;
			}
		}		

		if ( field.equals("histupdoc.idihistupdoc") ){
			try{
				List list = SqlHelper.execute("select idihistupdoc from histupdoc " +
						"where NmsArquivo like ? or idihistupdoc = ?", 
						new String[] {"%"+value+"%", value});
				if ( list.size() != 0 && list.get(0) != null)
					return String.valueOf(list.get(0));
			}catch(Exception e){
				return value;
			}
		}		
		return value;
	}
}
