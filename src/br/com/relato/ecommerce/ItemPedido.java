package br.com.relato.ecommerce;

import java.sql.*;

import br.com.relato.*;
import br.com.relato.util.DbAccess;

public class ItemPedido {

	private int idiitempedido;
	private int idiproduto;
	private int qtidesejada;
	private double vlditem;
	private double pcddesconto;
	private double vlddesconto;
	private double vldtotal;
	
	private Produto produto;
	
	public ItemPedido(int idiitempedido, int idiproduto, int qtidesejada,
			double vlditem, double pcddesconto, double vlddesconto,
			double vldtotal,Produto produto) {
		super();
		this.idiitempedido = idiitempedido;
		this.idiproduto = idiproduto;
		this.qtidesejada = qtidesejada;
		this.vlditem = vlditem;
		this.pcddesconto = pcddesconto;
		this.vlddesconto = vlddesconto;
		this.vldtotal = vldtotal;
		this.produto = produto; 
	}

	public ItemPedido(int idiproduto, int qtidesejada,
			double vlditem, double pcddesconto, double vlddesconto,
			double vldtotal) {
		super();
		this.idiitempedido = -1;
		this.idiproduto = idiproduto;
		this.qtidesejada = qtidesejada;
		this.vlditem = vlditem;
		this.pcddesconto = pcddesconto;
		this.vlddesconto = vlddesconto;
		this.vldtotal = vldtotal;
		this.produto = findProduto(idiproduto); 
	}
	
	public ItemPedido(int idiproduto, int qtidesejada) {
		super();
		this.produto = findProduto(idiproduto);
		this.idiitempedido = -1;
		this.idiproduto = idiproduto;
		this.qtidesejada = qtidesejada;
		this.vlditem = this.produto.getVldvenda();
		this.pcddesconto = 0;
		this.vlddesconto = 0;
		this.vldtotal = this.vlditem * this.qtidesejada;
	}

	/**
	 * @return the idiitempedido
	 */
	public int getIdiitempedido() {
		return idiitempedido;
	}

	/**
	 * @param idiitempedido the idiitempedido to set
	 */
	public void setIdiitempedido(int idiitempedido) {
		this.idiitempedido = idiitempedido;
	}

	/**
	 * @return the idiproduto
	 */
	public int getIdiproduto() {
		return idiproduto;
	}

	/**
	 * @param idiproduto the idiproduto to set
	 */
	public void setIdiproduto(int idiproduto) {
		this.idiproduto = idiproduto;
	}

	/**
	 * @return the qtidesejada
	 */
	public int getQtidesejada() {
		return qtidesejada;
	}

	/**
	 * @param qtidesejada the qtidesejada to set
	 */
	public void setQtidesejada(int qtidesejada) {
		this.qtidesejada = qtidesejada;
	}

	/**
	 * @return the vlditem
	 */
	public double getVlditem() {
		return vlditem;
	}

	/**
	 * @param vlditem the vlditem to set
	 */
	public void setVlditem(double vlditem) {
		this.vlditem = vlditem;
	}

	/**
	 * @return the pcddesconto
	 */
	public double getPcddesconto() {
		return pcddesconto;
	}

	/**
	 * @param pcddesconto the pcddesconto to set
	 */
	public void setPcddesconto(double pcddesconto) {
		this.pcddesconto = pcddesconto;
	}

	/**
	 * @return the vlddesconto
	 */
	public double getVlddesconto() {
		return vlddesconto;
	}

	/**
	 * @param vlddesconto the vlddesconto to set
	 */
	public void setVlddesconto(double vlddesconto) {
		this.vlddesconto = vlddesconto;
	}

	/**
	 * @return the vldtotal
	 */
	public double getVldtotal() {
		return vldtotal;
	}

	/**
	 * @param vldtotal the vldtotal to set
	 */
	public void setVldtotal(double vldtotal) {
		this.vldtotal = vldtotal;
	}
	
	public void setProduto(Produto produto){
		this.produto = produto;
	}

	public Produto getProduto(){
		return this.produto;
	}
	
	private Produto findProduto(int idiproduto){
		Connection conn = DbAccess.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql =/*" select idiproduto,nmscompleto,dssdescricao,iditipoproduto," +
					" vldvenda,opsativo from produto where idiproduto = ?";*/
			" select produto.idiproduto," +
			" produto.nmscompleto,produto.dssdescricao," +
			" produto.iditipoproduto, produto.vldvenda,produto.opsativo," +
			" produto.nmsimagem," +
			" tipoproduto.nmscompleto " +
			" from produto " +
			" left outer join tipoproduto on tipoproduto.iditipoproduto = produto.iditipoproduto " +
			" where produto.idiproduto = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idiproduto);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return new Produto(rs.getInt(1),rs.getString(2),rs.getString(3),
						rs.getInt(4),rs.getDouble(5),rs.getString(6),
						(null != rs.getObject(7) ? rs.getString(7) : ""),(null != rs.getObject(8) ? rs.getString(8) : ""));
			}else{
				return new Produto();
			}
		}catch(SQLException s){
			s.printStackTrace();
			return new Produto();
		}finally{
			try{
				if(null != rs){
					rs.close();
				}
				if(null != pstmt){
					pstmt.close();
				}
				DbAccess.closeConnection(conn);
			}catch(SQLException s){
				s.printStackTrace();
				return new Produto();
			}
			
		}
	}
	
}
