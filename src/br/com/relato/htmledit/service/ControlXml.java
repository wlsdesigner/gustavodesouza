/*
 * Created on 25/10/2004
 */
package br.com.relato.htmledit.service;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.jdom.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Daniel
 */
public class ControlXml extends Dataview{
	private Document doc;
	private int state;
	org.jdom.Element mensagem = new org.jdom.Element("msg");
	
	public ControlXml(Document doc){
		this.doc = doc;
		setMsg(mensagem);
	}
	
	public void parseXml(){
		Element elem = doc.getDocumentElement();
		String id = elem.getAttribute("id");
		String linguaConteudo = (null != elem.getAttribute("lingua") ? elem.getAttribute("lingua") : "" );
		//R2 ver aqui o parametro...
		System.out.println("R2 veja a lingua aqui: '"+linguaConteudo+"'");
		setId(id);
		state = Integer.parseInt(elem.getAttribute("command"));
		setState(state);
		
		if ( state == State.ATUALIZA ){
			atualiza(elem);
		}else if ( state == State.DELETE ){
			delete();
		}else if ( state == State.FIND ){
			if("".equals(linguaConteudo)){
				find();
			}else{
				find(Integer.parseInt(linguaConteudo)); 
			}
		}else if ( state == State.TEMPLATE ){
			findTemplate();
		}
	}

	public void generateOutXml(ServletOutputStream out) throws IOException{
        org.jdom.Document doc = new org.jdom.Document();
        
        org.jdom.Element requestmessage = new org.jdom.Element("request-message");
        org.jdom.Element component = new org.jdom.Element("component");

        component.setAttribute("state", String.valueOf(getState()));
        requestmessage.addContent(component);
        
        if ( state != State.TEMPLATE ){        	
	        component.setAttribute("id", getId());
	        component.setAttribute("titulo", getTitulo());
	        component.setAttribute("tipo", getTipo());
	        component.setAttribute("replica", getReplica());
	        component.setAttribute("ativo", getAtivo());
	        component.setAttribute("keywords", getKeywords());
	        component.setAttribute("descricao", getDescricao());
	        requestmessage.addContent(mensagem);
	        
	        if ( State.ATUALIZA == getState() ){
	        	component.setAttribute("lingua", getLingua());
	        }
	        
	        
	        if ( State.ATUALIZA != getState() )
	        	component.setAttribute("conteudo", getConteudo());
        }else{
        	component.setAttribute("conteudo", getConteudo());
        }
        
        doc.setRootElement(requestmessage);
       
        XMLOutputter fmt = new XMLOutputter();
        fmt.output(doc, out);
        
		out.flush();
	}

}
