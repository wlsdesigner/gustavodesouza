/*
 * Created on 01/11/2004
 */
package br.com.relato.extranet.model;

import java.util.Date;

/**
 * @author Daniel
 */
public class Usuario {
	private Integer id;
	private String nome;
	private String login;
	private String pass;
	private String email;
	private String ativo;
	private String admin;
	private String editor;
	private String publicador;
	private Date dataultimaatualizacao;
	private Integer idiprincipal;
	
	
	
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the dataultimaatualizacao.
	 */
	public Date getDataultimaatualizacao() {
		return dataultimaatualizacao;
	}
	/**
	 * @param dataultimaatualizacao The dataultimaatualizacao to set.
	 */
	public void setDataultimaatualizacao(Date dataultimaatualizacao) {
		this.dataultimaatualizacao = dataultimaatualizacao;
	}
	/**
	 * @return Returns the idiprincipal.
	 */
	public Integer getIdiprincipal() {
		return idiprincipal;
	}
	/**
	 * @param idiprincipal The idiprincipal to set.
	 */
	public void setIdiprincipal(Integer idiprincipal) {
		this.idiprincipal = idiprincipal;
	}
	/**
	 * @return Returns the editor.
	 */
	public String getEditor() {
		return editor;
	}
	/**
	 * @param editor The editor to set.
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	/**
	 * @return Returns the publicador.
	 */
	public String getPublicador() {
		return publicador;
	}
	/**
	 * @param publicador The publicador to set.
	 */
	public void setPublicador(String publicador) {
		this.publicador = publicador;
	}
	/**
	 * @return Returns the admin.
	 */
	public String getAdmin() {
		return admin;
	}
	/**
	 * @param admin The admin to set.
	 */
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	/**
	 * @return Returns the ativo.
	 */
	public String getAtivo() {
		return ativo;
	}
	/**
	 * @param ativo The ativo to set.
	 */
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	/**
	 * @return Returns the login.
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login The login to set.
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return Returns the nome.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome The nome to set.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return Returns the pass.
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * @param pass The pass to set.
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * @return Returns the id.
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}
