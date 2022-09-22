/*
 * Created on 29/03/2005
 */
package br.com.relato.extranet.model;

import java.util.Date;

/**
 * @author Administrator
 */
public class Historicodownload {
	private Integer idihistoricodownload;
	private Integer idiusuario;
	private Integer idihistupdoc;
	private Date dhdbaixa;

	
	/**
	 * @return Returns the idihistupdoc.
	 */
	public Integer getIdihistupdoc() {
		return idihistupdoc;
	}
	/**
	 * @param idihistupdoc The idihistupdoc to set.
	 */
	public void setIdihistupdoc(Integer idihistupdoc) {
		this.idihistupdoc = idihistupdoc;
	}
	/**
	 * @return Returns the idiusuario.
	 */
	public Integer getIdiusuario() {
		return idiusuario;
	}
	/**
	 * @param idiusuario The idiusuario to set.
	 */
	public void setIdiusuario(Integer idiusuario) {
		this.idiusuario = idiusuario;
	}
	/**
	 * @return Returns the dhdbaixa.
	 */
	public Date getDhdbaixa() {
		return dhdbaixa;
	}
	/**
	 * @param dhdbaixa The dhdbaixa to set.
	 */
	public void setDhdbaixa(Date dhdbaixa) {
		this.dhdbaixa = dhdbaixa;
	}
	/**
	 * @return Returns the idihistoricodownload.
	 */
	public Integer getIdihistoricodownload() {
		return idihistoricodownload;
	}
	/**
	 * @param idihistoricodownload The idihistoricodownload to set.
	 */
	public void setIdihistoricodownload(Integer idihistoricodownload) {
		this.idihistoricodownload = idihistoricodownload;
	}
}
