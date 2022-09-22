/*
 * Created on 08/07/2004
 *
 */
package com.relato.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Kumpera
 *
 */
public class UpdateStatement {
	String sql;
	public UpdateStatement(String sql) {
		this.sql = sql;
	}
	
	public int execute() throws SQLException {
		Connection con= PoolFilter.getConnection();
		Statement stmt= null;
		try {
			stmt = con.createStatement();
			return stmt.executeUpdate(sql);
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

}
