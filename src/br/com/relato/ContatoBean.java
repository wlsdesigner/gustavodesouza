package br.com.relato;

import java.util.ArrayList;
import java.util.Date;

import com.netstructure.sql.Connection;
import com.netstructure.sql.Query;
import com.netstructure.sql.Statement;

public class ContatoBean {

	private String nome;
	private String empresa;
	private String endereco;
	private String municipio;
	private String estado;
	private String pais;
	private String email;
	private String ddd;
	private String fone;
	private String assunto;
	private String mensagem;

	
	private Connection con;
    private Query qry;
    private Statement stmt;
    private String cSql;

    private String cPK;
	private boolean debug = false;

	// constructor
	public ContatoBean() {

		nome = "";
		empresa = "";
		endereco = "";
		municipio = "";
		estado = "";
		pais = "";
		email = "";
		ddd = "";
		fone = "";
		assunto = "";
		mensagem = "";
		
	
		con = new Connection();
		con.setDriver("org.gjt.mm.mysql.Driver");
		con.setURI("jdbc:mysql:///neorelato");
		con.setUser("root");
		con.setPassword("netsucesso");
        con.openConnection();

        qry = con.createQuery();
        stmt = con.createStatement();

        cSql = "";
        cPK = "";

	}

	public void setNome( String valor ) {
		this.nome = valor;
	}
	public void setEmpresa( String valor ) {
		this.empresa = valor;
	}
	public void setEndereco( String valor ) {
		this.endereco = valor;
	}
	public void setMunicipio( String valor ) {
		this.municipio = valor;
	}
	public void setEstado( String valor ) {
		this.estado = valor;
	}
	public void setPais( String valor ) {
		this.pais = valor;
	}
	public void setDdd( String valor ) {
		this.ddd = valor;
	}
	public void setFone( String valor ) {
		this.fone = valor;
	}
	public void setEmail( String valor ) {
		this.email = valor;
	}
	public void setAssunto( String valor ) {
		this.assunto = valor;
	}
	public void setMensagem( String valor ) {
		this.mensagem = valor;
	}

	public String getNome() {
		return this.nome;
	}


	private int proximoId() {

		int nPK = 1;
		qry.command("select max(id) from contato");

		if (qry.open()) {
			if (!qry.eof()) {
				nPK = qry.getInt(1);
				nPK ++;
			}
			qry.close();
		}
		return nPK;
	}

	public void salvaContato() {

		int i;
		int idNovo = 0;
				
		
		String campos[] = {"id", "nome", "empresa", "endereco", "municipio", "estado", "pais", "ddd", "fone", "email" ,"assunto", "mensagem", "data" };
        ArrayList prm = new ArrayList();

        for(i=0; i < campos.length; i++) prm.add("?");
        if (prm.size() == 0) {
            return;
        }
        String prmt[] = (String[])prm.toArray(new String[prm.size()]);

		stmt.insert("contato").fields(campos).values(prmt);
		stmt.buildParams();

		idNovo=proximoId();
		stmt.setParamInt(1, idNovo);
		stmt.setParamString(2, nome);
		stmt.setParamString(3, empresa);
		stmt.setParamString(4, endereco);
		stmt.setParamString(5, municipio);
		stmt.setParamString(6, estado);
		stmt.setParamString(7, pais);
		stmt.setParamString(8, ddd);
		stmt.setParamString(9, fone);
		stmt.setParamString(10, email);
		stmt.setParamString(11, assunto);
		stmt.setParamString(12, mensagem);
		stmt.setParamDate(13, new Date());

		if (!stmt.execute()) {
			if (debug)
				System.out.println("Nao foi possivel atualizar o conteudo");
		}
	}

}
