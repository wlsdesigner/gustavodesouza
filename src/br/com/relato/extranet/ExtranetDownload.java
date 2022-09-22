/*
 * Created on 02/12/2004
 */
package br.com.relato.extranet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import br.com.relato.ConstantsApp;
import br.com.relato.EntryPoint;
import br.com.relato.extranet.model.Historicodownload;
import br.com.relato.extranet.model.Histupdoc;

/**
 * @author daniel
*/
public class ExtranetDownload extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws IOException{
		
		String nomearquivo = procuraNomeArquivo(request.getParameter("id"));
		String internet = (null != request.getParameter("internet") ? request.getParameter("internet") : "");
		String path = "";
		if("S".equals(internet)){
			path = ConstantsApp.getParametro("internet.documentos"); 
		}else{
			path = ConstantsApp.getParametro("extranet.documentos");
		}
		
		File arquivo = new File(path+nomearquivo);
		
		if ( !arquivo.exists() ){
			mandaErro(request, response);
			return;
		}

		writeHistoric(request.getParameter("id"));
		response.setContentType("application/octet-stream");
		response.setHeader(
				"Content-Disposition",
				"attachment; filename=\""+nomearquivo+"\"");

		FileInputStream in = new FileInputStream(arquivo);
		try {
			byte[] buffer = new byte[1000];
			OutputStream output = response.getOutputStream();
			int c;
			while ((c = in.read(buffer)) > 0) {
				output.write(buffer, 0, c);
			}
		}finally{
			in.close();
		}
	}
	
	private void mandaErro(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter w = response.getWriter();
		w.print("Arquivo não encontrado!!");
		w.flush();
		w.close();
	}

	void writeHistoric(String id){
		try {
			Session session = EntryPoint.getHbmsession();
			Transaction tx = session.beginTransaction();
			try{
				Historicodownload h = new Historicodownload();
				h.setDhdbaixa(new Date());
				h.setIdihistupdoc(new Integer(id));
				h.setIdiusuario(EntryPoint.getUsuario().getId());
				session.save(h);				
				session.flush();
				tx.commit();	
			}catch(Exception e){
				tx.rollback();
				e.printStackTrace();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}	
	
	String procuraNomeArquivo(String id){
		List list;
		try {
			list = EntryPoint.getHbmsession().find("from Histupdoc h where h.idihistupdoc = ?", 
						id, Hibernate.STRING);
			if ( list.size() != 0 ){
				Histupdoc h = (Histupdoc)list.get(0);
				return h.getNmsarquivo();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return "";
	}

	protected void doGet(HttpServletRequest request, 
				HttpServletResponse response) 
				throws ServletException, IOException{
		doPost(request, response);
	}
}
