package br.com.relato.extranet.prospects;

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
import br.com.relato.extranet.model.Prospect;

/**
 * @author daniel
 */
public class ControlRecords extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	final String errorPage = "/extranet/errorpage.jsp";
	final String sucessPage = "/extranet/prospects/gerenciador.jsp";
	
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
			
			if (sucessPage != null) {
				putMessage(request, action);
				RequestDispatcher dispatcher=
					request.getRequestDispatcher(sucessPage);
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

	/*
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| idiprospect | int(11)      |      | PRI | NULL    | auto_increment |
| nmscompleto | varchar(60)  | YES  |     | NULL    |                |
| cdsemail    | varchar(120) | YES  |     | NULL    |                |
| dssendereco | varchar(120) | YES  |     | NULL    |                |
| nmsbairro   | varchar(30)  | YES  |     | NULL    |                |
| nmscidade   | varchar(30)  | YES  |     | NULL    |                |
| nmsestado   | char(2)      | YES  |     | NULL    |                |
| nmspais     | varchar(30)  | YES  |     | NULL    |                |
| cdscep      | varchar(9)   | YES  |     | NULL    |                |
| cdstelefone | varchar(20)  | YES  |     | NULL    |                |
| nmsempresa  | varchar(120) | YES  |     | NULL    |                |
| ativo       | char(1)      | YES  |     | S       |                |
+-------------+--------------+------+-----+---------+----------------+

	 */
	private void remove(HttpServletRequest request, Session session) throws HibernateException {
		String id = getParameter(request, "id");
		List list = session.find("from Prospect c where c.id = ?", id, Hibernate.STRING);
		if ( list.size() != 0 ){
			Prospect c = (Prospect)list.get(0);
			session.delete(c);			
		}
	}

	private void modify(HttpServletRequest request, Session session) throws HibernateException {
		String id = getParameter(request, "id");
		List list = session.find("from Prospect c where c.id = ?", id, Hibernate.STRING);
		if ( list.size() != 0 ){
			Prospect c = (Prospect)list.get(0);
			c.setNome(getParameter(request, "nome"));
			c.setEmail(getParameter(request, "email"));
			c.setEndereco(getParameter(request, "endereco"));
			c.setBairro(getParameter(request, "bairro"));
			c.setCidade(getParameter(request, "cidade"));
			c.setEstado(getParameter(request, "estado"));
			c.setPais(getParameter(request, "pais"));
			c.setCep(getParameter(request, "cep"));
			c.setTelefone(getParameter(request, "telefone"));
			c.setEmpresa(getParameter(request, "empresa"));
			c.setAtivo(getParameter(request, "ativo"));
			c.setDescadastre(null != getParameter(request, "descadastre") ? getParameter(request, "descadastre") : "N");
			session.update(c);		
		}		
	}

	private void add(HttpServletRequest request, Session session) throws HibernateException {
		Prospect c = new Prospect();
		c.setNome(getParameter(request, "nome"));
		c.setEmail(getParameter(request, "email"));
		c.setEndereco(getParameter(request, "endereco"));
		c.setBairro(getParameter(request, "bairro"));
		c.setCidade(getParameter(request, "cidade"));
		c.setEstado(getParameter(request, "estado"));
		c.setPais(getParameter(request, "pais"));
		c.setCep(getParameter(request, "cep"));
		c.setTelefone(getParameter(request, "telefone"));
		c.setEmpresa(getParameter(request, "empresa"));
		c.setAtivo(getParameter(request, "ativo"));
		c.setDescadastre(null != getParameter(request, "descadastre") ? getParameter(request, "descadastre") : "N");
		session.save(c);			
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