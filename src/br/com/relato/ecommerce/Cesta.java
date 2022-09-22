package br.com.relato.ecommerce;

import java.util.Iterator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.sql.*;
import br.com.relato.util.*;

public class Cesta {
	
	private int idipedido;
	private int cdipedido;
	private int idicliente;
	private Date dhdinclusao;
	private double vldpedido;
	private double vlddesconto;
	private double vldapagar;
	private List itens;

	public Cesta() {
		this.idipedido = -1;
		this.cdipedido = -1;
		this.idicliente = -1;
		this.dhdinclusao = new Date();
		this.vldpedido = 0d;
		this.vlddesconto = 0d;
		this.vldapagar = 0d;
		this.itens = new ArrayList();
	}
	
	public Cesta(int idipedido, int cdipedido, int idicliente,
			Date dhdinclusao, double vldpedido, double vlddesconto,
			double vldapagar, List itens) {
		super();
		this.idipedido = idipedido;
		this.cdipedido = cdipedido;
		this.idicliente = idicliente;
		this.dhdinclusao = dhdinclusao;
		this.vldpedido = vldpedido;
		this.vlddesconto = vlddesconto;
		this.vldapagar = vldapagar;
		this.itens = itens;
	}
	/**
	 * @return the idipedido
	 */
	public int getIdipedido() {
		return idipedido;
	}
	/**
	 * @param idipedido the idipedido to set
	 */
	public void setIdipedido(int idipedido) {
		this.idipedido = idipedido;
	}
	/**
	 * @return the cdipedido
	 */
	public int getCdipedido() {
		return cdipedido;
	}
	/**
	 * @param cdipedido the cdipedido to set
	 */
	public void setCdipedido(int cdipedido) {
		this.cdipedido = cdipedido;
	}
	/**
	 * @return the idicliente
	 */
	public int getIdicliente() {
		return idicliente;
	}
	/**
	 * @param idicliente the idicliente to set
	 */
	public void setIdicliente(int idicliente) {
		this.idicliente = idicliente;
	}
	/**
	 * @return the dhdinclusao
	 */
	public Date getDhdinclusao() {
		return dhdinclusao;
	}
	/**
	 * @param dhdinclusao the dhdinclusao to set
	 */
	public void setDhdinclusao(Date dhdinclusao) {
		this.dhdinclusao = dhdinclusao;
	}
	/**
	 * @return the vldpedido
	 */
	public double getVldpedido() {
		return vldpedido;
	}
	/**
	 * @param vldpedido the vldpedido to set
	 */
	public void setVldpedido(double vldpedido) {
		this.vldpedido = vldpedido;
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
	 * @return the vldapagar
	 */
	public double getVldapagar() {
		return vldapagar;
	}
	/**
	 * @param vldapagar the vldapagar to set
	 */
	public void setVldapagar(double vldapagar) {
		this.vldapagar = vldapagar;
	}
	/**
	 * @return the itens
	 */
	public List getItens() {
		return itens;
	}
	/**
	 * @param itens the itens to set
	 */
	public void setItens(List itens) {
		this.itens = itens;
	}
	
	public boolean searchItemLista(int idiproduto){
		if(this.itens.size() > 0){
			Iterator it = this.itens.iterator();
			while(it.hasNext()){
				ItemPedido ip = (ItemPedido)it.next();
				int idiproditem = ip.getIdiproduto();
				if(idiproduto == idiproditem){
					return true;
				}
			}
		}
		return false;
	}
	
	public void zeraCesta(){
		this.idipedido = -1;
		this.cdipedido = -1;
		this.idicliente = -1;
		this.dhdinclusao = new Date();
		this.vldpedido = 0d;
		this.vlddesconto = 0d;
		this.vldapagar = 0d;
		this.itens = new ArrayList();
	}
	
	public int gravaPedido(){
		Connection conn = DbAccess.getConnection();
		int idipedidonovo = 0;
		int idiitempedidonovo = 0;
		if(null != conn){
			String insertQuery = "insert into pedido (idipedido,cdipedido,idicliente,dhdinclusao,vldpedido,vlddesconto,vldapagar)values(?,?,?,?,?,?,?)";
			try{
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select max(idipedido) from pedido");
				if(rs.next()){
					idipedidonovo = rs.getInt(1);
				}
				idipedidonovo++;
				PreparedStatement pstmt = conn.prepareStatement(insertQuery);
				pstmt.setInt(1, idipedidonovo);
				pstmt.setInt(2, idipedidonovo);
				pstmt.setInt(3, this.idicliente);
				pstmt.setDate(4, new java.sql.Date(this.dhdinclusao.getTime()));
				pstmt.setDouble(5,this.vldpedido);
				pstmt.setDouble(6,this.vlddesconto);
				pstmt.setDouble(7,this.vldapagar);
				pstmt.executeUpdate();
				atualizaSeqTable(conn, "pedido_seq", idipedidonovo);	
				if(this.itens.size() > 0){
					Iterator it = this.itens.iterator();
					insertQuery = "insert into itempedido(idiitempedido,idipedido,idiproduto,qtidesejada,vlditem,pcddesconto,vlddesconto,vldtotal)values(?,?,?,?,?,?,?,?)";
					PreparedStatement pstmt2 = conn.prepareStatement(insertQuery);
					while(it.hasNext()){
						ItemPedido ip = (ItemPedido)it.next();
						try{
							rs = stmt.executeQuery("select max(idiitempedido) from itempedido");
							if(rs.next()){
								idiitempedidonovo= rs.getInt(1);
							}
							idiitempedidonovo++;
							
							pstmt2.setInt(1, idiitempedidonovo);
							pstmt2.setInt(2, idipedidonovo);
							pstmt2.setInt(3, ip.getIdiproduto());
							pstmt2.setInt(4, ip.getQtidesejada());
							pstmt2.setDouble(5,ip.getVlditem());
							pstmt2.setDouble(6,ip.getPcddesconto());
							pstmt2.setDouble(7,ip.getVlddesconto());
							pstmt2.setDouble(8,ip.getVldtotal());
							pstmt2.executeUpdate();
							atualizaSeqTable(conn, "itempedido_seq", idiitempedidonovo);
						}catch(SQLException s2){
							System.out.println("Erro ao incluir item");
							s2.printStackTrace();
							return -1;
						}
					}
					
					if(null != pstmt2){
						pstmt2.close();
					}
				}
				
				
				
				if(null != pstmt){
					pstmt.close();
				}
				if(null != stmt){
					stmt.close();
				}
				if(null != rs){
					rs.close();
				}
				zeraCesta();
				return idipedidonovo;
			}catch(SQLException s){
				System.out.println("Erro ao incluir pedido");
				s.printStackTrace();
				return -1;
			}finally{
				DbAccess.closeConnection(conn);
			}
		}else{
			System.out.println("Erro ao pegar conexão");
			return -1;
		}
	}
	
	public void addItemLista(int idiproduto, int qtidesejada,
			double vlditem, double pcddesconto, double vlddesconto,
			double vldtotal){
		/*
		boolean existe = false;
		if(this.itens.size() > 0){
			int i = itens.size();
			for(int x=0;x<i;x++){
				ItemPedido ip = (ItemPedido)this.itens.get(x);
				int idiproditem = ip.getIdiproduto();
				if(idiproduto == idiproditem){
					this.vldpedido += vlditem;
					this.vldapagar += vldtotal;
					this.vlddesconto += vlddesconto;
					ip.setQtidesejada((ip.getQtidesejada()+qtidesejada));
					ip.setVlditem((ip.getVlditem()+vlditem));
					ip.setPcddesconto((ip.getPcddesconto()+pcddesconto));
					ip.setVlddesconto((ip.getVlddesconto()+vlddesconto));
					ip.setVldtotal((ip.getVldtotal()+vldtotal));
					existe = true;
					break;
				}
			}
		}
		
		if(!existe){
			ItemPedido ip = new ItemPedido(idiproduto,qtidesejada,vlditem,pcddesconto,vlddesconto,vldtotal);
			this.vldpedido += ip.getVlditem();
			this.vldapagar += ip.getVldtotal();
			this.vlddesconto += ip.getVlddesconto();
			this.itens.add(ip);
		}

		*/
		if(!searchItemLista(idiproduto)){
			ItemPedido ip = new ItemPedido(idiproduto,qtidesejada,vlditem,pcddesconto,vlddesconto,vldtotal);
			this.vldpedido += ip.getVlditem();
			this.vldapagar += ip.getVldtotal();
			this.vlddesconto += ip.getVlddesconto();
			this.itens.add(ip);
		}
		
		
	}
	
	public static void atualizaSeqTable(Connection conn, String table, int seq){
		try{
			String updateQuery = "update "+table+" set next_value = ?";
			PreparedStatement pstmtU = conn.prepareStatement(updateQuery);
			pstmtU.setInt(1, seq);
			pstmtU.executeUpdate();
			if(null != pstmtU){
				pstmtU.close();
			}
		}catch(SQLException s2){
			System.out.println("Erro ao atualizar seq");
			s2.printStackTrace();
		}
	}
	
	public void addItemLista(int idiproduto, int qtidesejada){
		boolean existe = false;
		if(this.itens.size() > 0){
			int i = itens.size();
			for(int x=0;x<i;x++){
				ItemPedido ip = (ItemPedido)this.itens.get(x);
				int idiproditem = ip.getIdiproduto();
				if(idiproduto == idiproditem){
					//this.vlddesconto += vlddesconto;
					ip.setQtidesejada(qtidesejada);
					existe = true;
					break;
				}
			}
		}
		
		if(!existe){
			ItemPedido ip = new ItemPedido(idiproduto,qtidesejada);
			this.vldpedido += ip.getVlditem();
			this.vldapagar += ip.getVldtotal();
			this.vlddesconto += ip.getVlddesconto();
			this.itens.add(ip);
		}
	}

	public void removeItemLista(int idiproduto){
		if(this.itens.size() > 0){
			int i = itens.size();
			for(int x=0;x<i;x++){
				ItemPedido ip = (ItemPedido)this.itens.get(x);
				int idiproditem = ip.getIdiproduto();
				if(idiproduto == idiproditem){
					this.vldpedido -= ip.getVlditem();
					this.vldapagar -= ip.getVldtotal();
					this.vlddesconto -= ip.getVlddesconto();
					this.itens.remove(x);
					break;
				}
			}
		}
	}

}
