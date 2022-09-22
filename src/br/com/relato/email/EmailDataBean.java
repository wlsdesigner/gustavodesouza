package br.com.relato.email;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;

import br.com.relato.criteria.SearchCriteria;

import br.com.relato.EntryPoint;

public class EmailDataBean {

	public static List processEmail() {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addField("distinct(cdsemail)");
		criteria.addTable("usuario");
		criteria.addFreeExpression("cdsemail is not null and cdsemail != '' ");
		
		Connection con = getConnection();
		
		List list = new ArrayList();
		try{
			ResultSet rs = execute(criteria.getQuery(), con);
			if ( rs.next() ){
				do{
					String email = rs.getString(1);
					try{
						if ( !StringUtils.isEmpty(email) )
							list.add(new InternetAddress(email));
					}catch(AddressException e){
						System.out.println("-------- Erro ao inserir email: " + email);
					}
				}while( rs.next() );
			}

			//closeConnection(con);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}

	static ResultSet execute(String query, Connection con ){
		if ( con != null ){
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	static Connection getConnection(){
		try {
			/*Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql:///siteap", 
					"root", "netsucesso");
					*/
			Connection con = EntryPoint.getConnection();
			return con;
		} /*catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}*/ catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static void closeConnection(Connection con){
		if( con != null )
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}