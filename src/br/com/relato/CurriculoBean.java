package br.com.relato;

import java.util.ArrayList;
import java.util.Date;

import java.sql.*;


public class CurriculoBean {

	private String idicurriculo;
	private String nmscompleto ;
	private String nmsemail;
	private String dssendereco;
	private String nmsbairro;
	private String nmscidade;
	private String nmsestado;
	private String nmspais;
	private String cdscep;
	private String cdstelefone;
	private String nmscargo;
	private String nmsarquivo;
	private String nmssexo ;
	private String cdsidade;
	private String nmsestadocivil;

	private Connection con;
    //private Query qry;
    private PreparedStatement stmt;
    private String cSql;

    private String cPK;
	private boolean debug = false;

	// constructor
	public CurriculoBean() {
		
		idicurriculo = "";
		nmscompleto     = "";
		nmsemail = "";
		dssendereco = "";
		nmsbairro = "";
		nmscidade = "";
		nmsestado = "";
		nmspais = "";
		cdscep = "";
		cdstelefone = "";
		nmscargo = "";
		nmsestadocivil = "";
		cdsidade = "";
		nmssexo = "";
		nmscargo = "";
		nmsarquivo = "";
	
		con = getConnection();

		cSql = "";
        cPK = "";

	}

	public void setNome( String valor ) {
		this.nmscompleto = valor;
	}
	public void setCargo( String valor ) {
		this.nmscargo = valor;
	}
	public void setEndereco( String valor ) {
		this.dssendereco = valor;
	}
	public void setBairro( String valor ) {
		this.nmsbairro = valor;
	}
	public void setCidade( String valor ) {
		this.nmscidade = valor;
	}
	public void setEstado( String valor ) {
		this.nmsestado = valor;
	}
	public void setCep( String valor ) {
		this.cdscep = valor;
	}
	public void setPais( String valor ) {
		this.nmspais = valor;
	}
	public void setFone( String valor ) {
		this.cdstelefone = valor;
	}
	public void setEmail( String valor ) {
		this.nmsemail = valor;
	}
	public void setArquivo( String valor ) {
		this.nmsarquivo = valor;
	}
	public void setSexo( String valor ) {
		this.nmssexo = valor;
	}
	public void setEstadoCivil( String valor ) {
		this.nmsestadocivil= valor;
	}
	public void setIdade( String valor ) {
		this.cdsidade = valor;
	}
	public String getNome() {
		return this.nmscompleto;
	}


	private int proximoId() {
		Statement stm;
		try {
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select max(id) from curriculos");
			return rs.getInt(1); 
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void salvaContato() {

		int i;
		int idNovo = 0;
				
		
		String query = "insert into curriculos (" +
				"nmscompleto, " +
				"nmsemail, " +
				"dssendereco, " +
				"nmsbairro, " +
				"nmscidade, " +
				"nmsestado, " +
				"nmspais, " +
				"cdscep, " +
				"cdstelefone, " +
				"nmscargo, " +
				"nmsarquivo, " +
				"nmsestadocivil, " +
				"cdsidade, " +
				"nmssexo, " +
				"dtdinclusao) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, this.nmscompleto);
			stmt.setString(2, this.nmsemail);
			stmt.setString(3, this.dssendereco);
			stmt.setString(4, this.nmsbairro);
			stmt.setString(5, this.nmscidade);
			stmt.setString(6, this.nmsestado);
			stmt.setString(7, this.nmspais);
			stmt.setString(8, this.cdscep);
			stmt.setString(9,this.cdstelefone);
			stmt.setString(10,this.nmscargo);
			stmt.setString(11,this.nmsarquivo);
			stmt.setString(12,this.nmsestadocivil);
			stmt.setString(13,this.cdsidade);
			stmt.setString(14,this.nmssexo);
			stmt.setObject(15, new Date());
			stmt.execute();
			this.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//idNovo=proximoId();
		//stmt.setInt(1, idNovo);
	}
	
	Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql:///neorelato", 
					"root", "netsucesso");
					
			//Connection con = EntryPoint.getConnection();
			return con;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	void closeConnection(Connection con){
		if( con != null )
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
