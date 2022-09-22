package br.com.relato.extranet.model;

import java.io.Serializable;

public class Histupdoc implements Serializable, Cloneable {
	public Histupdoc() {
	}
 	private java.lang.Integer idihistupdoc;

	public void setIdihistupdoc(java.lang.Integer idihistupdoc) {
		this.idihistupdoc = idihistupdoc;
	}
	
	public java.lang.Integer getIdihistupdoc() {
		return this.idihistupdoc;
	}


 	private java.lang.Integer idiusuarioinc;

	public void setIdiusuarioinc(java.lang.Integer idiusuarioinc) {
		this.idiusuarioinc = idiusuarioinc;
	}
	
	public java.lang.Integer getIdiusuarioinc() {
		return this.idiusuarioinc;
	}


  	private java.util.Date dhdupload;

	public void setDhdupload(java.util.Date dhdupload) {
		this.dhdupload = dhdupload;
	}
	
	public java.util.Date getDhdupload() {
		return this.dhdupload;
	}


  	private java.lang.String nmsassunto;

	public void setNmsassunto(java.lang.String nmsassunto) {
		this.nmsassunto = nmsassunto;
	}
	
	public java.lang.String getNmsassunto() {
		return this.nmsassunto;
	}


  	private java.lang.String dssobservacoes;

	public void setDssobservacoes(java.lang.String dssobservacoes) {
		this.dssobservacoes = dssobservacoes;
	}
	
	public java.lang.String getDssobservacoes() {
		return this.dssobservacoes;
	}


  	private java.lang.String nmsarquivo;

	public void setNmsarquivo(java.lang.String nmsarquivo) {
		this.nmsarquivo = nmsarquivo;
	}
	
	public java.lang.String getNmsarquivo() {
		return this.nmsarquivo;
	}


  	private java.lang.Integer idiusuario;

	public void setIdiusuario(java.lang.Integer idiusuario) {
		this.idiusuario = idiusuario;
	}
	
	public java.lang.Integer getIdiusuario() {
		return this.idiusuario;
	}


  	private java.util.Date dhdultimaatualizacao;

	public void setDhdultimaatualizacao(java.util.Date dhdultimaatualizacao) {
		this.dhdultimaatualizacao = dhdultimaatualizacao;
	}
	
	public java.util.Date getDhdultimaatualizacao() {
		return this.dhdultimaatualizacao;
	}


  	private java.lang.Integer idiprincipal;

	public void setIdiprincipal(java.lang.Integer idiprincipal) {
		this.idiprincipal = idiprincipal;
	}
	
	public java.lang.Integer getIdiprincipal() {
		return this.idiprincipal;
	}

	private java.lang.String opsarquivopublico;

	public void setOpsarquivopublico(java.lang.String opsarquivopublico) {
		this.opsarquivopublico = opsarquivopublico;
	}

	public java.lang.String getOpsarquivopublico() {
		return this.opsarquivopublico;
	}
 

	public Object clone() {
		try {
			return super.clone();
		} catch(CloneNotSupportedException e) {
			throw new Error(e);
		}
	}
}