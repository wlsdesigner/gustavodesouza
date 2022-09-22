/*
 * Created on 25/10/2004
 * Modified on 12/08/2007
 */
package br.com.relato.htmledit.service;

import org.w3c.dom.Element;

import br.com.relato.htmleditor.xml.model.BodyMessage;

import system.ContentManager;

/**
 * @author Daniel
 * 
 * @version Rafael
 */
public class Dataview extends BodyMessage{
	ContentManager cm = new ContentManager();
	org.jdom.Element mensagem;
	
	public void setMsg(org.jdom.Element mensagem){
		this.mensagem = mensagem;
	}
	
	/**
	 * Método utilizado para salvar dados pelo editor de conteúdos (he.jsp)
	 */
	public void atualiza(Element elem){
		String titulo = elem.getAttribute("titulo");
		String tipoConteudo = elem.getAttribute("tipo");
		String conteudoAtivo = elem.getAttribute("ativo");
		String replicaConteudo = elem.getAttribute("replica");
		String keywordsConteudo = null!= elem.getAttribute("keywords")?elem.getAttribute("keywords"):"";
		String descricaoConteudo = null!= elem.getAttribute("descricao")?elem.getAttribute("descricao"):"";
		
		String linguaConteudo = (null != elem.getAttribute("lingua") ? elem.getAttribute("lingua") : "" );
		
		String dados = "";
		if(elem.getFirstChild() != null)
			dados = elem.getFirstChild().getNodeValue();
		
		int newId = -1;
		
		if("".equals(linguaConteudo)){
			newId = cm.setConteudo(transformId(getId()), titulo, dados, conteudoAtivo, tipoConteudo, replicaConteudo, keywordsConteudo, descricaoConteudo,"");
		}else{
			newId = cm.setConteudo(transformId(getId()), titulo, dados, conteudoAtivo, tipoConteudo, replicaConteudo, Integer.parseInt(linguaConteudo), keywordsConteudo, descricaoConteudo,"");
		}
		
		if ( newId == -1 )
			mensagem.setAttribute("msg", "Algum erro aconteceu ao atualizar o conteúdo!!");
		else if ( newId == -99 )
			mensagem.setAttribute("msg", "Título já existe em outro conteúdo, favor alterar!");
		else
			mensagem.setAttribute("msg", "Conteúdo atualizado com sucesso!!");
		 
		setId(String.valueOf(newId));
		if("".equals(linguaConteudo)){
			updateMessage(1);
		}else{
			updateMessage(Integer.parseInt(linguaConteudo));
		}
	}

	/**
	 * Método utilizado para apagar dados pelo editor de conteúdos (he.jsp)
	 */
	public void delete(){
		if (cm.excluiConteudo(transformId(getId())))
			mensagem.setAttribute("msg1", "Conteúdo deletado com sucesso!!");
		else
			mensagem.setAttribute("msg1", "Algum erro aconteceu ao deletar o conteúdo!!");
		
		updateMessage();
	}

	public void find(){
		updateMessage();
	}	

	int transformId(String id){
		int idConteudo = -1;
		try {
			idConteudo = Integer.parseInt(id);
		} catch (Exception e) {
			idConteudo = -1;
		}
		return idConteudo;
	}

	private void updateMessage() {
		setTitulo(cm.getTitulo(transformId(getId())));
		setTipo(cm.getTipo(transformId(getId())));
		setConteudo(cm.getContent(transformId(getId())));
		setAtivo(cm.getAtivo(transformId(getId())));
		setReplica(cm.getReplica(transformId(getId())));
		setKeywords(cm.getKeywords(transformId(getId())));
		setDescricao(cm.getDescricao(transformId(getId())));
	}
	
	public void findTemplate(){
		setConteudo(cm.getTemplate(transformId(getId())));
	}
	
	//RAFAEL LINGUA - 20071208
	public void find(int lingua){
		updateMessage(lingua);
	}	
	
	public void updateMessage(int lingua){
		setTitulo(cm.getTitulo(transformId(getId()),lingua));
		setTipo(cm.getTipo(transformId(getId()),lingua));
		setConteudo(cm.getContent(transformId(getId()),lingua));
		setAtivo(cm.getAtivo(transformId(getId()),lingua));
		setReplica(cm.getReplica(transformId(getId()),lingua));
		setKeywords(cm.getKeywords(transformId(getId()),lingua));
		setDescricao(cm.getDescricao(transformId(getId()),lingua));
		setLingua(String.valueOf(lingua));
	}
	
			
}
