package com.relato.pool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author daniel
 *  
 */
public class SimplePoolFilter implements Filter {
	private static Object lock= new Object();
	private static BasicDataSource dataSource;

	private static final ThreadLocal localCon= new ThreadLocal();

	private int getInt(FilterConfig config, String prop, int def) {
		if(config.getInitParameter(prop) != null)
			return Integer.parseInt(config.getInitParameter(prop));
		else
			return def;
	}

	public void init(FilterConfig config) throws ServletException {
		synchronized(lock) {
			if(dataSource != null)
				return;
			dataSource= new BasicDataSource();
			dataSource.setDriverClassName(config
					.getInitParameter("driver-class"));
			dataSource.setUrl(config.getInitParameter("url"));
			dataSource.setUsername(config.getInitParameter("username"));
			dataSource.setPassword(config.getInitParameter("password"));

			dataSource.setMaxActive(getInt(config, "max-active", 10));
			dataSource.setMaxIdle(getInt(config, "max-idle", 2));
			dataSource.setMaxIdle(getInt(config, "max-wait", 10000));

			if(config.getInitParameter("validation-query") != null)
				dataSource.setValidationQuery(config
						.getInitParameter("validation-query"));
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection con= (Connection)localCon.get();
		if(con != null)
			return con;
		con= dataSource.getConnection();
		localCon.set(con);
		return con;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		}finally {
			Connection con= (Connection)localCon.get();
			localCon.set(null);
			if(con != null)
				try {
					if(!con.isClosed())
						con.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public void destroy() {
		synchronized(lock) {
			if(dataSource == null)
				return;
			try {
				dataSource.close();
				dataSource= null;
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
