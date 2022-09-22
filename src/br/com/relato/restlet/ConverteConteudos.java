package br.com.relato.restlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.relato.util.DbAccess;

public class ConverteConteudos {

	/**
	 * @param args
	 */
	
	public void converteMenus(){
		String query = 	" select id, link " +
						" from menu " +
						" order by 1";
		System.out.println("Inicio converteMenus");
		try{
			Connection c = DbAccess.getConnection("",br.com.relato.ConstantsApp.getParametro("site.database"));
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				int id = rs.getInt(1);
				String link = rs.getString(2).trim();
				
				String partelink = "/site/";
				if(link.contains("index.jsp")){
					System.out.println("link::'"+link+"'");
					if(link.contains("index.jsp?u=")){
						//String url = partelink.substring(link.indexOf("index.jsp?u=")+11, link.length());
						String url = link.substring(link.indexOf("index.jsp?u=")+12, link.length());
						partelink = partelink+url;
					}else{
						partelink="#";
					}
					String upd = " update menu set link = ? where id = ?";
					PreparedStatement ps = c.prepareStatement(upd);
					System.out.println("partelink::'"+partelink+"'");
					ps.setString(1, partelink);
					ps.setInt(2, id);
					ps.executeUpdate();
					if(null!=ps){
						ps.close();
						ps = null;
					}
				}
			}
			if(null!=rs){
				rs.close();
				rs = null;
			}
			if(null!=st){
				st.close();
				st = null;
			}
			if(null!=c){
				c.close();
				c = null;
			}
		}catch(SQLException s){
			s.printStackTrace();
		}
		System.out.println("Finaliza converteMenus");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConverteConteudos cc = new ConverteConteudos();
		cc.converteMenus();
	}

}
