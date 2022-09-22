/*
 * Created on 19/11/2004
 */
package br.com.relato.extranet.web;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import javazoom.upload.MultipartFormDataRequest;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import br.com.relato.ConstantsApp;
import br.com.relato.EntryPoint;
import br.com.relato.extranet.Permission;
import br.com.relato.extranet.model.Histupdoc;
import br.com.relato.extranet.model.Usuario;

/**
 * @author daniel
 */
public class DeleteUpload {
	
	public static String delete(String id, String type){
		Transaction tx = null;
		try{
			Session session = EntryPoint.getHbmsession();
			tx = session.beginTransaction();
			
			Usuario user = EntryPoint.getUsuario();
			List list = session.find("from Histupdoc h where h.idihistupdoc = ?", new Integer(id), Hibernate.INTEGER);
			if ( list.size() != 0 ){
				Histupdoc h = (Histupdoc)list.get(0);
				
				session.delete(h);
				session.flush();
				
				File fileDelete = new File(ConstantsApp.getParametro("extranet.documentos")+h.getNmsarquivo());
				fileDelete.delete();

			}
			tx.commit();
		}catch(Exception e){
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
			return "Algum erro aconteceu ao deletar o arquivo";
		}

		return "Arquivo deletado com sucesso!!";
	}
	
	public static String delete(String id, String type, String opspublico){
		Transaction tx = null;
		try{
			Session session = EntryPoint.getHbmsession();
			tx = session.beginTransaction();
			
			Usuario user = EntryPoint.getUsuario();
			List list = session.find("from Histupdoc h where h.idihistupdoc = ? ", new Integer(id), Hibernate.INTEGER);
			if ( list.size() != 0 ){
				Histupdoc h = (Histupdoc)list.get(0);
				
				session.delete(h);
				session.flush();
				String path = "";
				if("S".equals(opspublico)){
					path = ConstantsApp.getParametro("internet.documentos");
				}else{
					path = ConstantsApp.getParametro("extranet.documentos");
				}
				File fileDelete = new File(path+h.getNmsarquivo());
				fileDelete.delete();

			}
			tx.commit();
		}catch(Exception e){
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
			return "Algum erro aconteceu ao deletar o arquivo";
		}

		return "Arquivo deletado com sucesso!!";
	}

	
	public static String add(MultipartFormDataRequest mrequest){
		Transaction tx = null;
		try{
			String arquivo = mrequest.getParameter("nomearquivo");
			
			Session session = EntryPoint.getHbmsession();
			tx = session.beginTransaction();
			
			Usuario user = EntryPoint.getUsuario();
			Histupdoc h = new Histupdoc();
			
			h.setDhdupload(new Date());
			h.setDhdultimaatualizacao(new Date());
			h.setDssobservacoes(mrequest.getParameter("descricao"));
			h.setIdiprincipal(user.getId());
			h.setIdiusuarioinc(user.getId());
			h.setNmsassunto(mrequest.getParameter("assunto"));
			h.setNmsarquivo(arquivo.substring(arquivo.lastIndexOf("\\")+1));
			if ( !StringUtils.isEmpty(mrequest.getParameter("usuarioprop")) )
				h.setIdiusuario(new Integer(mrequest.getParameter("usuarioprop")));
			if ( !StringUtils.isEmpty(mrequest.getParameter("opsarquivopublico")) )
				h.setOpsarquivopublico(mrequest.getParameter("opsarquivopublico"));
				
			session.save(h);
			session.flush();
			
			tx.commit();
		}catch(Exception e){
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "Algum erro aconteceu ao inserir o arquivo";
		}

		return "Arquivo inserido com sucesso!!";
	}
}
