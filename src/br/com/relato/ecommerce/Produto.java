package br.com.relato.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.relato.util.DbAccess;

public class Produto {
	
	private int idiproduto;
	private String nmscompleto;
	private String dssdescricao;
	private int iditipoproduto;
	private double vldvenda;
	private String opsativo;
	private String nmsimagem;
	private String nmstipoproduto;
	
	public Produto(){
		this.idiproduto = -1;
		this.nmscompleto = "";
		this.dssdescricao = "";
		this.iditipoproduto = -1;
		this.vldvenda = 0d;
		this.opsativo = "";
	}
	
	

	public Produto(int idiproduto){
		Connection conn = DbAccess.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql =" select idiproduto,nmscompleto,dssdescricao,iditipoproduto," +
					" vldvenda,opsativo from produto where idiproduto = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idiproduto);
			rs = pstmt.executeQuery();
			if(rs.next()){
				this.idiproduto = rs.getInt(1);
				this.nmscompleto = rs.getString(2);
				this.dssdescricao = rs.getString(3);
				this.iditipoproduto = rs.getInt(4);
				this.vldvenda = rs.getDouble(5);
				this.opsativo = rs.getString(6);
			}else{
				this.idiproduto = -1;
				this.nmscompleto = "";
				this.dssdescricao = "";
				this.iditipoproduto = -1;
				this.vldvenda = 0d;
				this.opsativo = "";
			}
		}catch(SQLException s){
			s.printStackTrace();
			this.idiproduto = -1;
			this.nmscompleto = "";
			this.dssdescricao = "";
			this.iditipoproduto = -1;
			this.vldvenda = 0d;
			this.opsativo = "";
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
				this.idiproduto = -1;
				this.nmscompleto = "";
				this.dssdescricao = "";
				this.iditipoproduto = -1;
				this.vldvenda = 0d;
				this.opsativo = "";
			}
			
		}

	}

	
	public Produto(int idiproduto, String nmscompleto, String dssdescricao,
			int iditipoproduto, double vldvenda, String opsativo) {
		super();
		this.idiproduto = idiproduto;
		this.nmscompleto = nmscompleto;
		this.dssdescricao = dssdescricao;
		this.iditipoproduto = iditipoproduto;
		this.vldvenda = vldvenda;
		this.opsativo = opsativo;
	}
	
	public Produto(int idiproduto, String nmscompleto, String dssdescricao,
			int iditipoproduto, double vldvenda, String opsativo,
			String nmsimagem, String nmstipoproduto) {
		super();
		this.idiproduto = idiproduto;
		this.nmscompleto = nmscompleto;
		this.dssdescricao = dssdescricao;
		this.iditipoproduto = iditipoproduto;
		this.vldvenda = vldvenda;
		this.opsativo = opsativo;
		this.nmsimagem = nmsimagem;
		this.nmstipoproduto = nmstipoproduto;
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
	 * @return the nmscompleto
	 */
	public String getNmscompleto() {
		return nmscompleto;
	}
	/**
	 * @param nmscompleto the nmscompleto to set
	 */
	public void setNmscompleto(String nmscompleto) {
		this.nmscompleto = nmscompleto;
	}
	/**
	 * @return the dssdescricao
	 */
	public String getDssdescricao() {
		return dssdescricao;
	}
	/**
	 * @param dssdescricao the dssdescricao to set
	 */
	public void setDssdescricao(String dssdescricao) {
		this.dssdescricao = dssdescricao;
	}
	/**
	 * @return the iditipoproduto
	 */
	public int getIditipoproduto() {
		return iditipoproduto;
	}
	/**
	 * @param iditipoproduto the iditipoproduto to set
	 */
	public void setIditipoproduto(int iditipoproduto) {
		this.iditipoproduto = iditipoproduto;
	}
	/**
	 * @return the vldvenda
	 */
	public double getVldvenda() {
		return vldvenda;
	}
	/**
	 * @param vldvenda the vldvenda to set
	 */
	public void setVldvenda(double vldvenda) {
		this.vldvenda = vldvenda;
	}
	/**
	 * @return the opsativo
	 */
	public String getOpsativo() {
		return opsativo;
	}
	/**
	 * @param opsativo the opsativo to set
	 */
	public void setOpsativo(String opsativo) {
		this.opsativo = opsativo;
	}

	public String getNmsimagem() {
		return nmsimagem;
	}



	public void setNmsimagem(String nmsimagem) {
		this.nmsimagem = nmsimagem;
	}



	public String getNmstipoproduto() {
		return nmstipoproduto;
	}



	public void setNmstipoproduto(String nmstipoproduto) {
		this.nmstipoproduto = nmstipoproduto;
	}

	public static Produto getProduto(int idiproduto){
		Connection conn = DbAccess.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql =" select produto.idiproduto," +
					" produto.nmscompleto,produto.dssdescricao," +
					" produto.iditipoproduto, produto.vldvenda,produto.opsativo," +
					" produto.nmsimagem," +
					" tipoproduto.nmscompleto " +
					" from produto " +
					" left outer join tipoproduto on tipoproduto.iditipoproduto = produto.iditipoproduto " +
					" where idiproduto = ? ";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idiproduto);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return new Produto(rs.getInt(1),rs.getString(2),rs.getString(3),
						(null != rs.getObject(4) ? rs.getInt(4) : -1),rs.getDouble(5),rs.getString(6),
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
	
	public List getListaTiposProdutos(){
		Connection conn = DbAccess.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		String sql =" select tipoproduto.iditipoproduto, tipoproduto.nmscompleto" +
					" from tipoproduto " +
					" order by 2";
					
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				List row = new ArrayList();
				row.add(rs.getObject(1));
				row.add(rs.getObject(2));
				lista.add(row);
			}
		}catch(SQLException s){
			s.printStackTrace();
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
			}
			
		}
		return lista;
	}
	public List getListaProdutos(){
		return getListaProdutos("","","");
	}
	public List getListaProdutos(String nomeproduto){
		return getListaProdutos("",nomeproduto,"");
	}
	
	public List getListaProdutos(String tipoproduto, String nomeproduto){
		return getListaProdutos(tipoproduto,nomeproduto,"");
	}
	
	public List getListaProdutos(String tipoproduto,String nomeproduto, String ordenacao){
		return getListaProdutos(tipoproduto,nomeproduto,ordenacao,"");
	}
	 
	public List getListaProdutos(String tipoproduto,String nomeproduto, String ordenacao, String promocao){
		Connection conn = DbAccess.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		String sql =" select produto.idiproduto, " +
					" produto.nmscompleto,produto.dssdescricao," +
					" produto.iditipoproduto, produto.vldvenda,produto.opsativo," +
					" produto.nmsimagem," +
					" tipoproduto.nmscompleto " +
					" from produto " +
					" left outer join tipoproduto on tipoproduto.iditipoproduto = produto.iditipoproduto " +
					" where opsativo = 'S' ";
		if(!"".equals(tipoproduto)){
			sql+=" and tipoproduto.iditipoproduto in ("+tipoproduto+")";
		}
		if(!"".equals(nomeproduto)){
			sql+=" and upper(produto.nmscompleto) like '%"+nomeproduto.toUpperCase().trim()+"%'";
		}
		if(!"".equals(promocao)){
			sql+=" and opspromocao = '"+promocao+"'";
		}
		 
		if(!"".equals(ordenacao)){
			sql+=" order by 8,"+ordenacao;
		}else{
			sql+=" order by 8,2";
		}
					
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				lista.add(new Produto(rs.getInt(1),rs.getString(2),rs.getString(3),
						(null != rs.getObject(4) ? rs.getInt(4) : -1),rs.getDouble(5),rs.getString(6),
						(null != rs.getObject(7) ? rs.getString(7) : ""),(null != rs.getObject(8) ? rs.getString(8) : "")));
				
			}
		}catch(SQLException s){
			s.printStackTrace();
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
			}
			
		}
		return lista;
	}
		
}
