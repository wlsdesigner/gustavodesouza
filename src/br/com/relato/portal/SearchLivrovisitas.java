/*
 * Created on 09/05/2005
 */
package br.com.relato.portal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.relato.util.SqlHelper;

/**
 * @author Administrator
 */
public class SearchLivrovisitas {

	public static Iterator getContent(){
		String query = "select nmscompleto, " +
		" nmsemail, dssmensagem, DATE_FORMAT(dhdcadastro, '%d/%m/%Y - %T') " + 
		" from livrovisitas where cdsstatus = 'A' " +
		" order by dhdcadastro desc " +
		" LIMIT 50 ";

		List list = new ArrayList();
		try{
			list = SqlHelper.execute(query, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list.iterator();
	}
	
	public static List getAll(){
		String query = "select idilivrovisitas, nmscompleto, " +
		" nmsemail, dssmensagem, DATE_FORMAT(dhdcadastro, '%d/%m/%Y - %T'), " + 
		" cdsstatus from livrovisitas " +
		" order by dhdcadastro desc " +
		" LIMIT 50 ";

		List list = new ArrayList();
		try{
			return SqlHelper.execute(query, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
}
