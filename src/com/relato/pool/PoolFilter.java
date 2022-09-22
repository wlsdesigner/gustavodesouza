/*
 * Created on 08/07/2004
 *
 */
package com.relato.pool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;
import br.com.relato.Constants;
import br.com.relato.EntryPoint;
import br.com.relato.extranet.model.Historicodownload;
import br.com.relato.extranet.model.Histupdoc;
import br.com.relato.extranet.model.Usuario;
import br.com.relato.extranet.model.Prospect;
import br.com.relato.service.ResourceLocatorService;

/**
 * @author Daniel
 *
 */
public class PoolFilter implements Filter {
	private static Object lock = new Object();
	private FilterConfig config;
	
	public void init(FilterConfig config) throws ServletException {
		synchronized (lock) {
			this.config = config;
			
			ServletContext sc= config.getServletContext();
			if (sc.getAttribute(Constants.HIBERNATE_FACTORY_KEY) != null)
				return;
			try {
				ResourceLocatorService.init(sc);
				
				Properties props= new Properties();
				InputStream in=
					config.getServletContext().getResourceAsStream(
						Constants.HIBERNATE_CONFIG_FILE);
				props.load(in);
				in.close();

				Configuration conf = new Configuration();
				conf.addClass(Usuario.class);
				conf.addClass(Histupdoc.class);
				conf.addClass(Historicodownload.class);
				conf.addClass(Prospect.class);
				conf.addProperties(props);
				sc.setAttribute(
					Constants.HIBERNATE_FACTORY_KEY,
					conf.buildSessionFactory());
			} catch (IOException ioe) {
				throw new ServletException(ioe);
			} catch (HibernateException e) {
				throw new ServletException(e);
			}
		}
	}

	protected SessionFactory getSessionFactory() {
		return (SessionFactory)config.getServletContext().getAttribute(
			Constants.HIBERNATE_FACTORY_KEY);
	}	
	
	public static Connection getConnection(){
		return EntryPoint.getConnection();
	}

	public void doFilter(
		ServletRequest request,
		ServletResponse response,
		FilterChain chain)
		throws IOException, ServletException {
		
		Session session= null;
		Transaction transaction= null;
		try {
			SessionFactory factory= getSessionFactory();

			session = factory.openSession();
			transaction= session.beginTransaction();			

			HttpServletRequest hrequest = (HttpServletRequest)request;
			EntryPoint.register(hrequest);
			EntryPoint.register(hrequest.getSession());			
			EntryPoint.register(session);
			
			if (session != null) {
				synchronized (session) {
					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}

			transaction.commit();
			transaction= null;			
			
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (HibernateException e) {
					System.out.println("erro dando rollback na transação");
				}
			}
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException e) {
					System.out.println("erro fechando sessão");
				}
			}
			EntryPoint.unregister();
		}
	}

	public void destroy() {
		synchronized (lock) {
			SessionFactory factory= getSessionFactory();
			try {
				factory.close();
			} catch (HibernateException e) {
				System.out.println("erro destroindo servlet");
			}
		}
	}

}
