/*
 * Created on 11/02/2005
 */
package br.com.relato.extranet.users;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import net.sf.hibernate.Session;

import br.com.relato.EntryPoint;
import br.com.relato.extranet.InvalidRequestException;
import br.com.relato.extranet.model.Usuario;

/**
 * @author daniel
 */
public class ControlUsers extends HttpServlet{
	final String errorPage = "/extranet/errorpage.jsp";
	final String sucessPage = "/siteadmin/users/gerenciadorusers.jsp";
	final String sucessPage2 = "/siteadmin/menu.jsp"; // Para ser chamado quando o gerenciador não é chamado
	
	
	
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		String action = getParameter(request, "action");
		Transaction tx = null;
		try{
			Session session = EntryPoint.getHbmsession();
			tx = session.beginTransaction();
			
			if ( "add".equals(action) )
				add(request, session);
			else if ( "modify".equals(action) )
				modify(request, session);
			else if ( "delete".equals(action) )
				remove(request, session);
			else{
				request.setAttribute("erro", "Nenhuma ação encontrada!!");
				sendToErrorPage(request, response);
				tx.rollback();
				return;
			}
			
			String destinoSucesso = (getParameter(request, "usarsucesspage2") != null && 
					getParameter(request, "usarsucesspage2").equals("true")) ? sucessPage2 : sucessPage;
			if (destinoSucesso != null) {
				putMessage(request, action);
				System.out.println("Redirecionando::"+destinoSucesso);
				RequestDispatcher dispatcher=
					request.getRequestDispatcher(destinoSucesso);
				dispatcher.forward(request, response);
			}
			
			session.flush();
			tx.commit();
		}catch(InvalidRequestException e){
			if ( e.getCause() != null ){
				e.getCause().printStackTrace();
				request.setAttribute("exception", e.getCause());
			}
			if ( e.getMessage() != null )
				request.setAttribute("erro", e.getMessage());
			sendToErrorPage(request, response);
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
		} catch (HibernateException e) {
				if ( e.getCause() != null ){
					e.getCause().printStackTrace();
					request.setAttribute("exception", e.getCause());
				}
				if ( e.getMessage() != null )
					request.setAttribute("erro", e.getMessage());
				sendToErrorPage(request, response);
				try {
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}				
		}
	}
	
	private void putMessage(HttpServletRequest request, String action) {
		String msg = "";
		if ( "add".equals(action) )
			msg = "Registro incluído com sucesso!!";
		else if ( "modify".equals(action) )
			msg = "Registro alterado com sucesso!!";
		else if ( "delete".equals(action) )
			msg = "Registro excluído com sucesso!!";
		request.getSession().setAttribute("msg", msg);
	}

	private void remove(HttpServletRequest request, Session session) throws HibernateException {
		String id = getParameter(request, "id");
		List list = session.find("from Usuario u where u.id = ?", id, Hibernate.STRING);
		if ( list.size() != 0 ){
			Usuario u = (Usuario)list.get(0);
			session.delete(u);			
		}
	}

	private void modify(HttpServletRequest request, Session session) throws HibernateException {
		String id = getParameter(request, "id");
		List list = session.find("from Usuario u where u.id = ?", id, Hibernate.STRING);
		if ( list.size() != 0 ){
			Usuario u = (Usuario)list.get(0);
			u.setAdmin(getParameter(request, "admin"));
			u.setAtivo(getParameter(request, "ativo"));
			u.setDataultimaatualizacao(new Date());
			u.setEditor(getParameter(request, "editor"));
			u.setPublicador(getParameter(request, "publicador"));
			u.setLogin(getParameter(request, "login"));
			u.setNome(getParameter(request, "nome"));
			// Alternado por Felipe para nunca mostrar as senhas, seja usuário ou administrador
			if(!getParameter(request, "pass").trim().equals("")) 
				u.setPass(getParameter(request, "pass"));
			u.setEmail(getParameter(request, "email"));
			session.update(u);		
		}		
	}

	private void add(HttpServletRequest request, Session session) throws HibernateException {
		Usuario u = new Usuario();
		u.setAdmin(getParameter(request, "admin"));
		u.setAtivo(getParameter(request, "ativo"));
		u.setDataultimaatualizacao(new Date());
		u.setEditor(getParameter(request, "editor"));
		u.setPublicador(getParameter(request, "publicador"));
		u.setLogin(getParameter(request, "login"));
		u.setNome(getParameter(request, "nome"));
		u.setPass(getParameter(request, "pass"));	
		u.setEmail(getParameter(request, "email"));
		session.save(u);			
	}

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	
	private void sendToErrorPage(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
			RequestDispatcher dispatcher= request.getRequestDispatcher(errorPage);
			System.out.println("Erro Redirecionando::"+errorPage);
			dispatcher.forward(request, response);
	}		
	
	public static String getParameter(HttpServletRequest request, String param) {
		HttpSession session= request.getSession();
		String value= null;
		if (request.getParameter(param) != null) {
			value= request.getParameter(param);
			session.setAttribute(param, value);
		} else
			value= (String)session.getAttribute(param);
		return value;
	}
	
}
