/*
 * Created on 28/03/2005
 */
package br.com.relato.security;

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
 * @author Administrator
 */
public class SecurityFilter implements Filter {
	FilterConfig filterConfig;
	private static final String URL_LOGIN = "/";
	
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Iniciando filtro de segurança");
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
			
			try{
				if ( !Permission.checkPermission(u, hrequest) )
					throw new InvalidRequestException("Sem permissão!!");
				
			}catch( InvalidRequestException e ){
				System.out.println("Acesso negado!!");
				hrequest.getSession().setAttribute("errologin", e.getMessage());
				hrequest.getSession().setAttribute(Constants.USER_KEY, null);
				httpresponse.sendRedirect(Constants.ACCESS_DENIED);
				return;
			}
			
			chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
