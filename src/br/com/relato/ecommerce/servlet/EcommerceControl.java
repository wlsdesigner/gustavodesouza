package br.com.relato.ecommerce.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.jdom.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.com.relato.ecommerce.*;

/*
 * Created on 26/02/2008
 */


/**
 * @author Rafael Lima
 */

public class EcommerceControl {
	
	private Document doc;
	private org.jdom.Document docX;
	private int state;
	org.jdom.Element mensagem = new org.jdom.Element("msg");
	
	public EcommerceControl(Document doc){
		this.doc = doc;
	}
	
	public void parseXml(){
		Element elem = doc.getDocumentElement();
		this.state = Integer.parseInt(elem.getAttribute("command"));
		
		if ( state == ConstantsEcommerce.PESQUISA_PRODUTO){
			System.out.println("PESQUISA");
			pesquisa(elem);
		}/*else if ( state == ConstantsEcommerce.REMOVE){
			delete(elem);
		}else if ( state == ConstantsEcommerce.ATUALIZA ){
			atualiza(elem);
		}else if ( state == State.TEMPLATE ){
			findTemplate();
		}*/
	}

	public void pesquisa(Element elem){
		int idproduto = Integer.parseInt(elem.getAttribute("idiproduto"));
		System.out.println("id = "+idproduto);
		Produto p = new Produto(idproduto); 
		this.docX = new org.jdom.Document();
		org.jdom.Element produto = new org.jdom.Element("produto");
        org.jdom.Element produtorow = new org.jdom.Element("produtorow");
        produto.addContent(produtorow);
        System.out.println("xxx  111"+String.valueOf(p.getIdiproduto()));
        produtorow.setAttribute("idiproduto",String.valueOf(p.getIdiproduto()));
        System.out.println("xxx  222"+String.valueOf(p.getIditipoproduto()));
        produtorow.setAttribute("iditipoproduto",String.valueOf(p.getIditipoproduto()));
        System.out.println("xxx  333"+String.valueOf(p.getVldvenda()));
        produtorow.setAttribute("vldvenda",String.valueOf(p.getVldvenda()));
        System.out.println("xxx  444"+String.valueOf(p.getDssdescricao()));
        produtorow.setAttribute("dssdescricao",String.valueOf(p.getDssdescricao()));
        System.out.println("xxx  555"+String.valueOf(p.getNmscompleto()));
        produtorow.setAttribute("nmscompleto",String.valueOf(p.getNmscompleto()));
        System.out.println("xxx  666"+String.valueOf(p.getOpsativo()));
        produtorow.setAttribute("opsativo",String.valueOf(p.getOpsativo()));
        System.out.println("xxx  777");
        produto.addContent(mensagem);
        System.out.println("xxx  888");
        this.docX.setRootElement(produto); 
        System.out.println("xxx  999");
	}
	public void generateOutXml(ServletOutputStream out) throws IOException{
        /*
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
       */
		System.out.println("INO");
        XMLOutputter fmt = new XMLOutputter();
        fmt.output(this.docX, out);
        System.out.println("FUI");
		out.flush();
	}
	
}
