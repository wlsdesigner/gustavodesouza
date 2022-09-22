/*
 * Created on 08/07/2004
 *
 */
package com.relato.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Kumpera
 *
 */
public class QueryCommand {
	private final String sql;
	public QueryCommand(final String sql) {
		this.sql= sql;
	}

	public void execute(RowIterator iterator) throws SQLException {
		Connection con= PoolFilter.getConnection();
		Statement stmt= null;
		ResultSet rs= null;
		try {
			stmt= con.createStatement();
			rs= stmt.executeQuery(sql);
			while (rs.next()) {
				iterator.iterate(rs);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
	}

	public Object executeWithResult(RowIteratorWithResult iterator)
		throws SQLException {
		Connection con= PoolFilter.getConnection();
		Statement stmt= null;
		ResultSet rs= null;
		try {
			stmt= con.createStatement();
			rs= stmt.executeQuery(sql);
			Object obj = null;
			while(rs.next()) {
				obj = iterator.iterate(rs);
				if(obj == null)
					break;
			}
			return obj;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
	}


	public Object executeSingleResult() throws SQLException {
		Connection con= PoolFilter.getConnection();
		Statement stmt= null;
		ResultSet rs= null;
		try {
			stmt= con.createStatement();
			rs= stmt.executeQuery(sql);
			if(rs.next()) {
				return rs.getObject(1);
			}
			return null;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
	}
}