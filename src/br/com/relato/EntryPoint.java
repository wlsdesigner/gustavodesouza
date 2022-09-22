/*
 * Created on 19/11/2004
 *
 */
package br.com.relato;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import br.com.relato.extranet.model.Usuario;

/**
 * @author daniel
 */
public final class EntryPoint {
	private Session sessionHbm;
	private HttpSession session;
	private HttpServletRequest request;
	
	static ThreadLocal local = new ThreadLocal();

	EntryPoint() { }
	
	private static EntryPoint get() {
		EntryPoint ep = (EntryPoint) local.get();
		if(ep == null) {
			ep = new EntryPoint();
			local.set(ep);
		}
		return ep;
		
	}
	
	public static void register(HttpSession session) {
		get().session = session;
	}

	public static void register(Session sessionHbm) {
		get().sessionHbm = sessionHbm;
	}	
	
	public static void register(HttpServletRequest request) {
		get().request = request;
	}

	public static void unregister() {
		local.set(null);
	}
	
	public static HttpServletRequest getRequest() {
		EntryPoint ep = (EntryPoint) local.get();
		if(ep == null)
			return null;
		return ep.request;
	}
	
	public static HttpSession getSession() {
		EntryPoint ep = (EntryPoint) local.get();
		if(ep == null)
			return null;
		return ep.session;
	}

	public static Session getHbmsession() {
		EntryPoint ep = (EntryPoint) local.get();
		return ep.sessionHbm;
	}	

	public static Connection getConnection(){
		try {
			return getHbmsession().connection();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
// ************************ Usuario do contexto **************************** 	

	public static Usuario getUsuario() {
		Usuario u = (Usuario)getRequest().getSession().getAttribute(Constants.USER_KEY);
		if ( u != null )
			return u;
		return null;
	}	
	
	public static int getUser() {
		if ( getUsuario() == null )
			return 0;
		return getUsuario().getId().intValue();
	}
	
	private static int getIdiUser(String name){
		if ( getUsuario() == null )
			return 0;
			return getUsuario().getId().intValue();
	} 

	public static String getNomeUsuario(){
		if ( getUsuario() == null )
			return "";
		return getUsuario().getNome();
	}
	
	public static int getEditor(){
		if ( getUsuario() == null )
			return 0;
		return getUsuario().getId().intValue();
	}
	
	public static boolean isEditor(){
		Usuario user = getUsuario();
		if ( getUsuario() == null )
			return false;
		int i = Integer.parseInt(getUsuario().getEditor());
		if ( i == 1 && "1".equals(user.getAtivo()))
			return true;
		return false;
	}	
	
	public static String getNameUser() {
		if ( getUsuario() == null )
			return "";
		return getUsuario().getNome();
	}	

	public static ServletContext getContext() {
		return getSession().getServletContext();
	}

	public static boolean isAdmin(){
		Usuario user = getUsuario();
		if ( user == null )
			return false; 
		
		if ( "1".equals(user.getAdmin()))//&& "1".equals(user.getAtivo()) // Felipe modificou
		//&& "1".equals(user.getEditor()) && "1".equals(user.getPublicador() ) )
	
				return true;
		return false;
	}	 
	 
	public static boolean isManager(){
		Usuario user = getUsuario();
		if ( user == null )
			return false;
		
		if ( !"1".equals(user.getAdmin()) 
				&& !"1".equals(user.getEditor()) 
				&& !"1".equals(user.getPublicador() ) )
				return false;
		return true;
	}
	
	
	
	
}
