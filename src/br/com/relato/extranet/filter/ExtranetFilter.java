/*
 * Created on 01/11/2004
 */
package br.com.relato.extranet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.relato.Constants;
import br.com.relato.InvalidRequestException;
import br.com.relato.extranet.Permission;
import br.com.relato.extranet.model.Usuario;

/**
 * @author Daniel
 */
public class ExtranetFilter implements Filter {
	final String URL_LOGIN = "/";
	FilterConfig filterConfig;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Iniciando filtro de segurança extranet");
		this.filterConfig = filterConfig;
	}
	
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {

			HttpServletRequest hrequest = (HttpServletRequest)request;
			HttpServletResponse httpresponse = (HttpServletResponse) response;
		
			Usuario u = (Usuario)hrequest.getSession().getAttribute(Constants.USER_KEY);
			if(u == null) {
				httpresponse.sendRedirect(URL_LOGIN);
				return;
			} 			
			
			try{
				Permission.checkPermission(u, hrequest);
				String context= hrequest.getContextPath();
				String url = hrequest.getRequestURI().toLowerCase();
				String path = url.substring(context.length());
				
			}catch( InvalidRequestException e ){
				hrequest.getSession().setAttribute("errologin", e.getMessage());
				hrequest.getSession().setAttribute(Constants.USER_KEY, null);				
				httpresponse.sendRedirect(URL_LOGIN);
				return;
			}
			
			chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
