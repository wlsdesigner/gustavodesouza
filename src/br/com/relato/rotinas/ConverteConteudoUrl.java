package br.com.relato.rotinas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.relato.util.DbAccess;
import br.com.relato.util.StringUtils;

public class ConverteConteudoUrl {
	
	public void converteTituloUrl(){
		Connection c = DbAccess.getConnection("192.168.0.147","neorelato");
		Map<Integer, String> mapaUrls = new LinkedHashMap<Integer, String>();
		if(c != null){
			String select = " select id,titulo from conteudo ";
			try{
				
				Statement st = c.createStatement();
				ResultSet rs = st.executeQuery(select);
				
				while(rs.next()){
					int id = rs.getInt(1);
					String tit = rs.getString(2);
					String url = StringUtils.toSiteUrl(tit);
					mapaUrls.put(id, url);
				}
				
				if(null!=rs){
					rs.close();
					rs = null;
				}
				if(null!=st){
					st.close();
					st = null;
				}
				
			}catch(SQLException s){
				
			}
			
			Iterator<Integer> itIds = mapaUrls.keySet().iterator();
			while(itIds.hasNext()){
				Integer idConteudo = itIds.next();
				String novaUrl = mapaUrls.get(idConteudo);
				String update = " update conteudo set url = '"+novaUrl+"' where id = "+idConteudo;
				try{
					Statement stu = c.createStatement();
					stu.executeUpdate(update);
					if(null!=stu){
						stu.close();
						stu = null;
					}
				}catch(SQLException s){
					
				}
				
			}
			
			String selmenu =" select id, link " +
							" from menu " +
							" where link like '%urlconteudo=%' " +
							" order by id ";
			
			try{
				Statement st = c.createStatement();
				ResultSet rs = st.executeQuery(selmenu);
				
				while(rs.next()){
					int id = rs.getInt(1);
					String link = null != rs.getString(2) ? rs.getString(2).trim() : "";
					String idConteudo = "";
					System.out.println("Link :: "+link);
					if(link.lastIndexOf("=") > -1){
						idConteudo = link.substring(link.lastIndexOf("=")+1).trim();
						System.out.println("IdConteudo :: "+idConteudo);
						try{
							String novoLink = link.substring(0,link.lastIndexOf("?")).trim();
							novoLink += "?u="+idConteudo;
							System.out.println("NovoLink :: "+novoLink);
							
							String updatemenu = " update menu set link = '"+novoLink+"' where id = "+id;
							try{
								Statement stu = c.createStatement();
								stu.executeUpdate(updatemenu);
								if(null!=stu){
									stu.close();
									stu = null;
								}
							}catch(SQLException s){
								
							}
							
						}catch(Exception e){
							
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
			}catch(SQLException s){
				
			}
		}
		
		DbAccess.closeConnection(c);
		
	}

	public void converteMenuUrl(){
		Connection c = DbAccess.getConnection("192.168.0.147","neorelato");
		Map<Integer, String> mapaUrls = new LinkedHashMap<Integer, String>();
		if(c != null){
			String select = " select * from menu where link like '%conteudo=%' order by id ";
			try{
				
				Statement st = c.createStatement();
				ResultSet rs = st.executeQuery(select);
				
				while(rs.next()){
					int id = rs.getInt(1);
					String tit = rs.getString(2);
					String url = StringUtils.toSiteUrl(tit);
					mapaUrls.put(id, url);
				}
				
				if(null!=rs){
					rs.close();
					rs = null;
				}
				if(null!=st){
					st.close();
					st = null;
				}
				
			}catch(SQLException s){
				
			}
			
			Iterator<Integer> itIds = mapaUrls.keySet().iterator();
			while(itIds.hasNext()){
				Integer idConteudo = itIds.next();
				String novaUrl = mapaUrls.get(idConteudo);
				String update = " update conteudo set url = '"+novaUrl+"' where id = "+idConteudo;
				try{
					Statement stu = c.createStatement();
					stu.executeUpdate(update);
					if(null!=stu){
						stu.close();
						stu = null;
					}
				}catch(SQLException s){
					
				}
				
			}
			
		}
		
		DbAccess.closeConnection(c);
		
	}

	
//
	
	public static void main(String[] args) {
		System.out.println("Iniciando rotina de conversão...");
		ConverteConteudoUrl ccu = new ConverteConteudoUrl();
		ccu.converteTituloUrl();
		System.out.println("Finalizando rotina de conversão...");
	}


}
