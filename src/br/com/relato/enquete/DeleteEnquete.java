/*
 * Created on 19/11/2004
 */
package br.com.relato.enquete;

import system.ContentManager;
/**
 * @author daniel
 */
public class DeleteEnquete {
	 
	public static String delete(String id){
		ContentManager cm = new ContentManager();
		int idi = -1;
		try{
			idi = Integer.parseInt(id);
		}catch(Exception e){
			return "Algum erro aconteceu ao deletar o registro";
		}
		cm.excluiConteudo(idi);
		return "Conteúdo deletado com sucesso!!";
	}
}
