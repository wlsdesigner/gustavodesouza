/*
 * Created on 30/11/2004
 */
package br.com.relato.security;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.relato.Constants;
import br.com.relato.EntryPoint;

/**
 * @author daniel
 */
public class Logout extends HttpServlet{
	
	public Logout() {
	}

	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
		
	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		
		EntryPoint.getSession().setAttribute(Constants.USER_KEY, null);
		EntryPoint.getSession().invalidate();
		if ( request.getParameter("page") != null ){
			response.sendRedirect(request.getParameter("page"));
			return;
		}
		
		response.sendRedirect("/");

		//RAFAEL TESTE
		/*
		response.sendRedirect(Constants.SITE_INDEX_PAGE);
		response.encodeRedirectURL(Constants.SITE_INDEX_PAGE);
		*/
	}
}