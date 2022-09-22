package br.com.relato.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.relato.ConstantsApp;
public class DbAccess {

	
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql:///"+(ConstantsApp.getParametro("site.database")).trim(),"root", "netsucesso");
			//Connection con = EntryPoint.getConnection();
			return con;
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnection(String ip, String database){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+database,"root", "netsucesso");
			//Connection con = EntryPoint.getConnection();
			return con;
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void closeConnection(Connection con){
		if( con != null )
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
