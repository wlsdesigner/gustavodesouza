package br.com.relato.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import br.com.relato.util.DbAccess;

public class Cliente {
	
	int idicliente; 
	int cdipadrao;
	String nmscompleto;
	String nmsendereco; 
	String cdsnumero;
	String nmscomplemento; 
	String nmsbairro;
	String nmscidade;
	String cdsestado; 
	String cdscep;
	String cdstelefone; 
	String cdsfax;
	String cdscelular; 
	String nmsemail;
	String nmssenha;
	String opsenviaemail; 
	String cdstipocliente; 
	String cdscpfcnpj; 
	String cdsrgie;
	Date dtdaniversario; 
	Date dhdinclusao; 
	Date dhdatualizacao;
	
	public int getIdicliente() {
		return idicliente;
	}
	public void setIdicliente(int idicliente) {
		this.idicliente = idicliente;
	}
	public int getCdipadrao() {
		return cdipadrao;
	}
	public void setCdipadrao(int cdipadrao) {
		this.cdipadrao = cdipadrao;
	}
	public String getNmscompleto() {
		return nmscompleto;
	}
	public void setNmscompleto(String nmscompleto) {
		this.nmscompleto = nmscompleto;
	}
	public String getNmsendereco() {
		return nmsendereco;
	}
	public void setNmsendereco(String nmsendereco) {
		this.nmsendereco = nmsendereco;
	}
	public String getCdsnumero() {
		return cdsnumero;
	}
	public void setCdsnumero(String cdsnumero) {
		this.cdsnumero = cdsnumero;
	}
	public String getNmscomplemento() {
		return nmscomplemento;
	}
	public void setNmscomplemento(String nmscomplemento) {
		this.nmscomplemento = nmscomplemento;
	}
	public String getNmsbairro() {
		return nmsbairro;
	}
	public void setNmsbairro(String nmsbairro) {
		this.nmsbairro = nmsbairro;
	}
	public String getNmscidade() {
		return nmscidade;
	}
	public void setNmscidade(String nmscidade) {
		this.nmscidade = nmscidade;
	}
	public String getCdsestado() {
		return cdsestado;
	}
	public void setCdsestado(String cdsestado) {
		this.cdsestado = cdsestado;
	}
	public String getCdscep() {
		return cdscep;
	}
	public void setCdscep(String cdscep) {
		this.cdscep = cdscep;
	}
	public String getCdstelefone() {
		return cdstelefone;
	}
	public void setCdstelefone(String cdstelefone) {
		this.cdstelefone = cdstelefone;
	}
	public String getCdsfax() {
		return cdsfax;
	}
	public void setCdsfax(String cdsfax) {
		this.cdsfax = cdsfax;
	}
	public String getCdscelular() {
		return cdscelular;
	}
	public void setCdscelular(String cdscelular) {
		this.cdscelular = cdscelular;
	}
	public String getNmsemail() {
		return nmsemail;
	}
	public void setNmsemail(String nmsemail) {
		this.nmsemail = nmsemail;
	}
	public String getNmssenha() {
		return nmssenha;
	}
	public void setNmssenha(String nmssenha) {
		this.nmssenha = nmssenha;
	}
	public String getOpsenviaemail() {
		return opsenviaemail;
	}
	public void setOpsenviaemail(String opsenviaemail) {
		this.opsenviaemail = opsenviaemail;
	}
	public String getCdstipocliente() {
		return cdstipocliente;
	}
	public void setCdstipocliente(String cdstipocliente) {
		this.cdstipocliente = cdstipocliente;
	}
	public String getCdscpfcnpj() {
		return cdscpfcnpj;
	}
	public void setCdscpfcnpj(String cdscpfcnpj) {
		this.cdscpfcnpj = cdscpfcnpj;
	}
	public String getCdsrgie() {
		return cdsrgie;
	}
	public void setCdsrgie(String cdsrgie) {
		this.cdsrgie = cdsrgie;
	}
	public Date getDtdaniversario() {
		return dtdaniversario;
	}
	public void setDtdaniversario(Date dtdaniversario) {
		this.dtdaniversario = dtdaniversario;
	}
	public Date getDhdinclusao() {
		return dhdinclusao;
	}
	public void setDhdinclusao(Date dhdinclusao) {
		this.dhdinclusao = dhdinclusao;
	}
	public Date getDhdatualizacao() {
		return dhdatualizacao;
	}
	public void setDhdatualizacao(Date dhdatualizacao) {
		this.dhdatualizacao = dhdatualizacao;
	}
	
	public Cliente(int idicliente, int cdipadrao, String nmscompleto,
			String nmsendereco, String cdsnumero, String nmscomplemento,
			String nmsbairro, String nmscidade, String cdsestado,
			String cdscep, String cdstelefone, String cdsfax,
			String cdscelular, String nmsemail, String nmssenha,
			String opsenviaemail, String cdstipocliente, String cdscpfcnpj,
			String cdsrgie, Date dtdaniversario, Date dhdinclusao,
			Date dhdatualizacao) {
		super();
		this.idicliente = idicliente;
		this.cdipadrao = cdipadrao;
		this.nmscompleto = nmscompleto;
		this.nmsendereco = nmsendereco;
		this.cdsnumero = cdsnumero;
		this.nmscomplemento = nmscomplemento;
		this.nmsbairro = nmsbairro;
		this.nmscidade = nmscidade;
		this.cdsestado = cdsestado;
		this.cdscep = cdscep;
		this.cdstelefone = cdstelefone;
		this.cdsfax = cdsfax;
		this.cdscelular = cdscelular;
		this.nmsemail = nmsemail;
		this.nmssenha = nmssenha;
		this.opsenviaemail = opsenviaemail;
		this.cdstipocliente = cdstipocliente;
		this.cdscpfcnpj = cdscpfcnpj;
		this.cdsrgie = cdsrgie;
		this.dtdaniversario = dtdaniversario;
		this.dhdinclusao = dhdinclusao;
		this.dhdatualizacao = dhdatualizacao;
	}

	public static int validaLogin(String login, String senha){
		if(!"".equals(login.trim()) && !"".equals(senha.trim())){
			Connection conn = DbAccess.getConnection();
			String sql = "select idicliente, nmsemail, nmssenha from cliente where nmsemail = ? ";
			if(null != conn){
				try{
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, login.trim());
					ResultSet rs = pstmt.executeQuery();
					if(rs.next()){
						if((rs.getString(3).trim()).equals(senha)){
							System.out.println("Ok senha correta!\n");
							int idcliente = rs.getInt(1);
							if(null != rs){
								rs.close();
							}
							if(null != pstmt){
								pstmt.close();
							}
							DbAccess.closeConnection(conn);
							return idcliente;
						}else{
							System.out.println("Senha incorreta!\n");
							if(null != rs){
								rs.close();
							}
							if(null != pstmt){
								pstmt.close();
							}
							DbAccess.closeConnection(conn);
							return -1;
						}
					}else{
						System.out.println("Não existe cliente com este login\n");
						if(null != rs){
							rs.close();
						}
						if(null != pstmt){
							pstmt.close();
						}
						DbAccess.closeConnection(conn);
						return -1;
					}
				}catch(SQLException s){
					System.out.println("Erro ao pesquisar cliente\n");
					s.printStackTrace();
					return -1;
				}
			}else{
				System.out.println("Erro ao pesquisar cliente\n");
				return -1;
			}
		}else{
			System.out.println("Erro na passagem de parametros!\nFalta login e ou senha!");
			return -1;
		}
	}

	public boolean pesquisaLogin(String login){
		if(!"".equals(login.trim())){
			Connection conn = DbAccess.getConnection();
			String sql = "select * from cliente where nmsemail = ? ";
			if(null != conn){
				try{
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, login.trim());
					ResultSet rs = pstmt.executeQuery();
					if(rs.next()){
						System.out.println("Já existe cliente com este login\n");
						
						if(null != rs){
							rs.close();
						}
						if(null != pstmt){
							pstmt.close();
						}
						
						DbAccess.closeConnection(conn);
						return true;
					}else{
						System.out.println("OK não existe cliente com este login\n");
						if(null != rs){
							rs.close();
						}
						if(null != pstmt){
							pstmt.close();
						}
						DbAccess.closeConnection(conn);
						return false;
					}
				}catch(SQLException s){
					System.out.println("Erro ao pesquisar cliente\n");
					s.printStackTrace();
				}
			}else{
				System.out.println("Erro ao pesquisar cliente\n");
				return true;
			}
		}else{
			System.out.println("Erro na passagem de parametros!\nFalta login!");
			return true;
		}
		return false;
	}
	
	public boolean gravaCliente(){
		Connection conn = DbAccess.getConnection();
		int idiclientenovo = 0;
		if(null != conn){
			String insertQuery ="insert into cliente(idicliente,cdipadrao,nmscompleto,nmsendereco,cdsnumero," +
								"nmscomplemento,nmsbairro,nmscidade,cdsestado," +
								"cdscep,cdstelefone,cdsfax,cdscelular,nmsemail,nmssenha,opsenviaemail,cdstipocliente,cdscpfcnpj,cdsrgie," +
								"dtdaniversario,dhdinclusao,dhdatualizacao) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try{
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select max(idicliente) from cliente");
				if(rs.next()){
					idiclientenovo = rs.getInt(1);
				}
				idiclientenovo++;
				PreparedStatement pstmt = conn.prepareStatement(insertQuery);
				pstmt.setInt(1, idiclientenovo);
				pstmt.setInt(2, idiclientenovo);
				pstmt.setString(3, this.nmscompleto);
				pstmt.setString(4, this.nmsendereco);
				pstmt.setString(5, this.cdsnumero);
				pstmt.setString(6, this.nmscomplemento);
				pstmt.setString(7, this.nmsbairro);
				pstmt.setString(8, this.nmscidade);
				pstmt.setString(9, this.cdsestado);
				pstmt.setString(10, this.cdscep);
				pstmt.setString(11, this.cdstelefone);
				pstmt.setString(12, this.cdsfax);
				pstmt.setString(13, this.cdscelular);
				pstmt.setString(14, this.nmsemail);
				pstmt.setString(15, this.nmssenha);
				pstmt.setString(16, this.opsenviaemail);
				pstmt.setString(17, this.cdstipocliente);
				pstmt.setString(18, this.cdscpfcnpj);
				pstmt.setString(19, this.cdsrgie);
				pstmt.setDate(20, new java.sql.Date(this.dtdaniversario.getTime()));
				pstmt.setDate(21, new java.sql.Date(this.dhdinclusao.getTime()));
				pstmt.setDate(22, new java.sql.Date(this.dhdatualizacao.getTime()));
				pstmt.execute();
				
				Cesta.atualizaSeqTable(conn, "cliente_seq", idiclientenovo);
				
				if(null != pstmt){
					pstmt.close();
				}
				if(null != stmt){
					stmt.close();
				}
				if(null != rs){
					rs.close();
				}
				this.idicliente = idiclientenovo;
				return true;
			}catch(SQLException s){
				System.out.println("Erro ao incluir cliente\n");
				s.printStackTrace();
				return false;
			}finally{
				DbAccess.closeConnection(conn);
			}
		}else{
			System.out.println("Erro ao pegar conexão\n");
			return false;
		}
	}



}
