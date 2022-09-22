/*
 * Created on 29/11/2004
 */
package br.com.relato.extranet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import br.com.relato.InvalidRequestException;
import br.com.relato.extranet.model.Usuario;
import br.com.relato.util.Cast;

/**
 * @author daniel
 */
public class Permission extends LoadSecurity{

	public static boolean checkPermission(Usuario user, HttpServletRequest hrequest){
		if ( user != null && !Cast.toBoolean(user.getAtivo()) )
			return false;
		
		try {
			return checkPath(user, hrequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	static boolean checkPath(Usuario user, HttpServletRequest hrequest) 
		throws IOException, InvalidRequestException{
		String context= hrequest.getContextPath();
		String url = hrequest.getRequestURI().toLowerCase();
		String path = url.substring(context.length());

		if ( path.indexOf(getParametro("extranet.denied.directories")) 
				!= -1){
			if ( user == null )
				return false;
		}
		
		String[] paths = loadHtmlDirectories();
		for ( int i = 0; i < paths.length; i ++ ){
			if ( path.indexOf(paths[i]) != -1){
				if ( user == null )
					return false;
				else{
					if ( !"1".equals(user.getAdmin()) 
						&& !"1".equals(user.getEditor()) 
						&& !"1".equals(user.getPublicador() ) )
						return false;
				}
			}	
		}
		return true;
	}

	static String[] loadHtmlDirectories() throws IOException{
		String param = getParametro("htmleditor.denied.directories");
		return param.split("\\*");
	}
}
