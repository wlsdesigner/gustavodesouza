/*
 * Created on 01/11/2004
 */
package br.com.relato.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

import org.apache.commons.lang.StringUtils;

import br.com.relato.Constants;
import br.com.relato.EntryPoint;
import br.com.relato.extranet.InvalidRequestException;
import br.com.relato.extranet.model.Usuario;

/**
 * @author Daniel
 */
public class Login extends HttpServlet{
	
	public Login() {
	}

	protected void doPost(			
		HttpServletRequest request,
		HttpServletResponse response) 
			throws ServletException, IOException {
		
		String usuario = request.getParameter("user");
		String senha = request.getParameter("pass");
		String context = request.getParameter("context");
		
		try{
			if (StringUtils.isEmpty(usuario) 
					|| StringUtils.isEmpty(senha) 
					|| StringUtils.isEmpty(context))
				throw new InvalidRequestException("Nem todos os campos foram preenchidos");
	
			String query = " from Usuario u where u.login = ? and u.pass = ?";
			List list = new ArrayList();
			try {
				list = EntryPoint.getHbmsession().find(query, 
							new Object[]{usuario, senha}, 
							new Type[]{Hibernate.STRING, Hibernate.STRING});
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			if ( list.size() != 0 ){
				Usuario user = (Usuario)list.get(0);
				request.getSession().setAttribute(Constants.USER_KEY, user);
				if ( "extranet".equals(context) )
					response.sendRedirect(Constants.EXTRANET_INDEX_PAGE);
				else if ( "htmleditor".equals(context) )
					response.sendRedirect(Constants.HTML_INDEX_PAGE);
				else
					throw new InvalidRequestException("Nenhum contexto informado");
			}else
				throw new InvalidRequestException("Usuario ou senha inválido");
		}catch( InvalidRequestException e ){
			request.setAttribute("erro", e.getMessage());
			request.getSession().setAttribute(Constants.USER_KEY, null);
			if ( "extranet".equals(context) )
				send(request, response, Constants.LOGIN_EXTRA_PAGE);
			//else send(request, response, Constants.LOGIN_HTML_PAGE); Felipe mudou para a linha de baixo
			else send(request, response, Constants.ACCESS_DENIED);
		}
	}

	public void send(HttpServletRequest request, HttpServletResponse response, String page) 
			throws ServletException, IOException{
		RequestDispatcher dispatcher= request.getRequestDispatcher(page);
		dispatcher.forward(request, response);

	}
}
