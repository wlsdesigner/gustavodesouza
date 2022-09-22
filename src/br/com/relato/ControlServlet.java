/*
 * Created on 25/11/2004
 */
package br.com.relato;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author daniel
 */
public abstract class ControlServlet extends HttpServlet{
	private String errorPage;
	private String sucessPage;
	
	protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException;
	

	private void sendToErrorPage(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
			RequestDispatcher dispatcher= request.getRequestDispatcher(errorPage);
			dispatcher.forward(request, response);
	}	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		try{
			processRequest(request, response);
			if (sucessPage != null) {
				RequestDispatcher dispatcher=
					request.getRequestDispatcher(sucessPage);
				dispatcher.forward(request, response);
			}
		}catch(InvalidRequestException e){
			if (e.getCause() != null) {
				e.getCause().printStackTrace();
				request.setAttribute("exception", e.getCause());
			}
			if (e.getMessage() != null)
				request.setAttribute("erro", e.getMessage());
			sendToErrorPage(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request, response);
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
	
	public void setErrorPage(String string) {
		errorPage= string;
	}

	public void setSucessPage(String string) {
		sucessPage= string;
	}
}
