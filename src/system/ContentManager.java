package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.relato.EntryPoint;
import br.com.relato.util.StringUtils;

import com.relato.pool.PoolFilter;
import com.relato.pool.QueryCommand;
import com.relato.pool.RowIterator;
import com.relato.pool.RowIteratorWithResult;
import com.relato.pool.UpdateStatement;

public class ContentManager {
	private static final boolean DEBUG= false;

	private Map cache= new HashMap();

	static class Conteudo {
		final int id;
		String titulo;
		String content; // texto
		String ativo;
		String tipo;
		String replica;
		String keywords;
		String descricao;
		String url;

		public Conteudo(int id, boolean ativa) throws SQLException {
			this.id= id;

			String query = "select titulo, texto, ativo, tipo, replicacao,keywords,descricao,url from conteudo where id = "
							+ id; 
			if ( ativa )
				query = "select titulo, texto, ativo, tipo, replicacao,keywords,descricao,url from conteudo where id = "
					+ id + " and ativo = 'S'";
			
			QueryCommand cmd=
				new QueryCommand(query);
			cmd.execute(new RowIterator() {
				public void iterate(ResultSet rs) throws SQLException {
					titulo= rs.getString(1);
					content= rs.getString(2);
					ativo= rs.getString(3);
					tipo= rs.getString(4);
					replica= rs.getString(5);
					keywords= rs.getString(6);
					descricao= rs.getString(7);
					url= rs.getString(8);
				}
			});
			checkAfterCreate();
		}

		public Conteudo(
			int id,
			String titulo,
			String content,
			String ativo,
			String tipo,
			String replica) {
			this.id= id;
			this.titulo= titulo;
			this.content= content; // texto
			this.ativo= ativo;
			this.tipo= tipo;
			this.replica= replica;
			this.keywords= "";
			this.descricao= "";
			this.url= "";
			checkAfterCreate();
		}

		public Conteudo(
			int id,
			String titulo,
			String content,
			String ativo,
			String tipo,
			String replica,
			String keywords,
			String descricao,
			String url) {
			this.id= id;
			this.titulo= titulo;
			this.content= content; // texto
			this.ativo= ativo;
			this.tipo= tipo;
			this.replica= replica;
			this.keywords= keywords;
			this.descricao= descricao;
			this.url = url;
			checkAfterCreate();
		}
		
		void checkAfterCreate() {
			if(titulo == null)
				titulo = "";
			if(content == null)
				content = "";
			if(ativo == null)
				ativo = "S";
			if(tipo == null)
				tipo = "C";
			if(replica == null)
				replica = "N";
			if(keywords == null)
				keywords = "";
			if(descricao == null)
				descricao = "";
			if(url == null)
				url = "";

		}
	}

	
	
	/**
	 * @param id
	 * @return
	 */
	private Conteudo getConteudo(int id) {
		Integer i= new Integer(id);
		Conteudo c= (Conteudo)cache.get(i);
		if (c == null) {
			try {
				c= new Conteudo(id, false);
			} catch (SQLException e) {
				c= new Conteudo(id, "", "", "S", "C","N");
			}
			cache.put(i, c);
		}
		return c;
	}

	private Conteudo getConteudoIndex(int id) {
		Integer i= new Integer(id);
		Conteudo c= (Conteudo)cache.get(i);
		if (c == null) {
			try {
				c= new Conteudo(id, true);
			} catch (SQLException e) {
				c= new Conteudo(id, "", "", "S", "C","N");
			}
			cache.put(i, c);
		}
		return c;
	}
	
	
	/**
	 * INGLES
	 *
	 */
	
	static class Conteudo2 {
		final int id;
		String titulo;
		String content; // texto
		String ativo;
		String tipo;
		String replica;
		String keywords;
		String descricao;
		String url;
		
		public Conteudo2(int id, boolean ativa) throws SQLException {
			this.id= id;

			String query = "select if(titulo2 is not null,titulo2,titulo), if(texto2 is not null,texto2,texto) , ativo, tipo, replicacao,keywords,descricao,url from conteudo where id = "
							+ id; 
			if ( ativa )
				query = "select if(titulo2 is not null,titulo2,titulo), if(texto2 is not null,texto2,texto) , ativo, tipo, replicacao,keywords,descricao,url from conteudo where id = "
					+ id + " and ativo = 'S'";
			
			QueryCommand cmd=
				new QueryCommand(query);
			cmd.execute(new RowIterator() {
				public void iterate(ResultSet rs) throws SQLException {
					titulo= rs.getString(1);
					content= rs.getString(2); 
					ativo= rs.getString(3);
					tipo= rs.getString(4);
					replica= rs.getString(5);
					keywords= rs.getString(6);
					descricao= rs.getString(7);
					url= rs.getString(8);
				}
			});
			checkAfterCreate();
		}

		public Conteudo2(
			int id,
			String titulo,
			String content,
			String ativo,
			String tipo,
			String replica) {
			this.id= id;
			this.titulo= titulo;
			this.content= content; // texto
			this.ativo= ativo;
			this.tipo= tipo;
			this.replica= replica;
			this.keywords= "";
			this.descricao= "";
			this.url= "";
			checkAfterCreate();
		}

		public Conteudo2(
			int id,
			String titulo,
			String content,
			String ativo,
			String tipo,
			String replica,
			String keywords,
			String descricao,
			String url) {
			this.id= id;
			this.titulo= titulo;
			this.content= content; // texto
			this.ativo= ativo;
			this.tipo= tipo;
			this.replica= replica;
			this.keywords= keywords;
			this.descricao= descricao;
			this.url = url;
			checkAfterCreate();
		}
		
		void checkAfterCreate() {
			if(titulo == null)
				titulo = "";
			if(content == null)
				content = "";
			if(ativo == null)
				ativo = "S";
			if(tipo == null)
				tipo = "C";
			if(replica == null)
				replica = "N";
			if(keywords == null)
				keywords = "";
			if(descricao == null)
				descricao = "";
			if(url == null)
				url = "";

		}
	}

	
	
	/**
	 * @param id
	 * @return
	 */
	private Conteudo2 getConteudo2(int id) {
		Integer i= new Integer(id);
		Conteudo2 c2= (Conteudo2)cache.get(i);
		if (c2 == null) {
			try {
				c2= new Conteudo2(id, false);
			} catch (SQLException e) {
				c2= new Conteudo2(id, "", "", "S", "C","N");
			}
			cache.put(i, c2);
		}
		return c2;
	}

	private Conteudo2 getConteudoIndex2(int id) {
		Integer i= new Integer(id);
		Conteudo2 c2= (Conteudo2)cache.get(i);
		if (c2 == null) {
			try {
				c2= new Conteudo2(id, true);
			} catch (SQLException e) {
				c2= new Conteudo2(id, "", "", "S", "C","N");
			}
			cache.put(i, c2);
		}
		return c2;
	}
	
	/**
	 * ESPANHOL
	 *
	 */
	
	static class Conteudo3 {
		final int id;
		String titulo;
		String content; // texto
		String ativo;
		String tipo;
		String replica;
		String keywords;
		String descricao;
		String url;
		
		public Conteudo3(int id, boolean ativa) throws SQLException {
			this.id= id;

			String query = "select if(titulo3 is not null,titulo3,titulo), if(texto3 is not null,texto3,texto) , ativo, tipo, replicacao,keywords,descricao,url from conteudo where id = "
							+ id; 
			if ( ativa )
				query = "select if(titulo3 is not null,titulo3,titulo), if(texto3 is not null,texto3,texto) , ativo, tipo, replicacao,keywords,descricao,url from conteudo where id = "
					+ id + " and ativo = 'S'";
			
			QueryCommand cmd=
				new QueryCommand(query);
			cmd.execute(new RowIterator() {
				public void iterate(ResultSet rs) throws SQLException {
					titulo= rs.getString(1);
					content= rs.getString(2);
					ativo= rs.getString(3);
					tipo= rs.getString(4);
					replica= rs.getString(5);
					keywords= rs.getString(6);
					descricao= rs.getString(7);
					url= rs.getString(8);
				}
			});
			checkAfterCreate();
		}

		public Conteudo3(
			int id,
			String titulo,
			String content,
			String ativo,
			String tipo,
			String replica) {
			this.id= id;
			this.titulo= titulo;
			this.content= content; // texto
			this.ativo= ativo;
			this.tipo= tipo;
			this.replica= replica;
			this.keywords= "";
			this.descricao= "";
			this.url= "";
			checkAfterCreate();
		}

		public Conteudo3(
			int id,
			String titulo,
			String content,
			String ativo,
			String tipo,
			String replica,
			String keywords,
			String descricao,
			String url) {
			this.id= id;
			this.titulo= titulo;
			this.content= content; // texto
			this.ativo= ativo;
			this.tipo= tipo;
			this.replica= replica;
			this.keywords= keywords;
			this.descricao= descricao;
			this.url = url;
			checkAfterCreate();
		}
		void checkAfterCreate() {
			if(titulo == null)
				titulo = "";
			if(content == null)
				content = "";
			if(ativo == null)
				ativo = "S";
			if(tipo == null)
				tipo = "C";
			if(replica == null)
				replica = "N";
			if(keywords == null)
				keywords = "";
			if(descricao == null)
				descricao = "";
			if(url == null)
				url = "";

		}
	}

	
	
	/**
	 * @param id
	 * @return
	 */
	private Conteudo3 getConteudo3(int id) {
		Integer i= new Integer(id);
		Conteudo3 c3= (Conteudo3)cache.get(i);
		if (c3 == null) {
			try {
				c3= new Conteudo3(id, false);
			} catch (SQLException e) {
				c3= new Conteudo3(id, "", "", "S", "C","N");
			}
			cache.put(i, c3);
		}
		return c3;
	}

	private Conteudo3 getConteudoIndex3(int id) {
		Integer i= new Integer(id);
		Conteudo3 c3= (Conteudo3)cache.get(i);
		if (c3 == null) {
			try {
				c3= new Conteudo3(id, true);
			} catch (SQLException e) {
				c3= new Conteudo3(id, "", "", "S", "C","N");
			}
			cache.put(i, c3);
		}
		return c3;
	}
	
	
	/**
	 * OUTRO
	 *
	 */
	
	static class Conteudo4 {
		final int id;
		String titulo;
		String content; // texto
		String ativo;
		String tipo;
		String replica;
		String keywords;
		String descricao;
		String url;
		
		public Conteudo4(int id, boolean ativa) throws SQLException {
			this.id= id;

			String query = "select if(titulo4 is not null,titulo4,titulo), if(texto4 is not null,texto4,texto) , ativo, tipo, replicacao,keywords,descricao,url from conteudo where id = "
							+ id; 
			if ( ativa )
				query = "select if(titulo4 is not null,titulo4,titulo), if(texto4 is not null,texto4,texto) , ativo, tipo, replicacao,keywords,descricao,url from conteudo where id = "
					+ id + " and ativo = 'S'";
			
			QueryCommand cmd=
				new QueryCommand(query);
			cmd.execute(new RowIterator() {
				public void iterate(ResultSet rs) throws SQLException {
					titulo= rs.getString(1);
					content= rs.getString(2);
					ativo= rs.getString(3);
					tipo= rs.getString(4);
					replica= rs.getString(5);
					keywords= rs.getString(6);
					descricao= rs.getString(7);
					url= rs.getString(8);
				}
			});
			checkAfterCreate();
		}

		public Conteudo4(
			int id,
			String titulo,
			String content,
			String ativo,
			String tipo,
			String replica) {
			this.id= id;
			this.titulo= titulo;
			this.content= content; // texto
			this.ativo= ativo;
			this.tipo= tipo;
			this.replica= replica;
			this.keywords= "";
			this.descricao= "";
			this.url= "";
			checkAfterCreate();
		}

		public Conteudo4(
			int id,
			String titulo,
			String content,
			String ativo,
			String tipo,
			String replica,
			String keywords,
			String descricao,
			String url) {
			this.id= id;
			this.titulo= titulo;
			this.content= content; // texto
			this.ativo= ativo;
			this.tipo= tipo;
			this.replica= replica;
			this.keywords= keywords;
			this.descricao= descricao;
			this.url = url;
			checkAfterCreate();
		}	
		void checkAfterCreate() {
			if(titulo == null)
				titulo = "";
			if(content == null)
				content = "";
			if(ativo == null)
				ativo = "S";
			if(tipo == null)
				tipo = "C";
			if(replica == null)
				replica = "N";
			if(keywords == null)
				keywords = "";
			if(descricao == null)
				descricao = "";
			if(url == null)
				url = "";

		}
	}

	
	
	/**
	 * @param id
	 * @return
	 */
	private Conteudo4 getConteudo4(int id) {
		Integer i= new Integer(id);
		Conteudo4 c4= (Conteudo4)cache.get(i);
		if (c4 == null) {
			try {
				c4= new Conteudo4(id, false);
			} catch (SQLException e) {
				c4= new Conteudo4(id, "", "", "S", "C","N");
			}
			cache.put(i, c4);
		}
		return c4;
	}

	private Conteudo4 getConteudoIndex4(int id) {
		Integer i= new Integer(id);
		Conteudo4 c4= (Conteudo4)cache.get(i);
		if (c4 == null) {
			try {
				c4= new Conteudo4(id, true);
			} catch (SQLException e) {
				c4= new Conteudo4(id, "", "", "S", "C","N");
			}
			cache.put(i, c4);
		}
		return c4;
	}
	
	// constructor
	public ContentManager() {}

	public String getContentIndex(int id) {
		Conteudo c= getConteudoIndex(id);
		return c.content;
	}	
	public String getContent(int id) {
		Conteudo c= getConteudo(id);
		return c.content;
	}
	
	public String getTituloIndex(int id) {
		Conteudo c= getConteudoIndex(id);
		return c.titulo;
	}

	public String getTitulo(int id) {
		Conteudo c= getConteudo(id);
		return c.titulo;
	}

	public String getTipo(int id) {
		Conteudo c= getConteudo(id);
		return c.tipo;
	}

	public String getAtivo(int id) {
		Conteudo c= getConteudo(id);
		return c.ativo;
	}

	public String getReplica(int id) {
		Conteudo c= getConteudo(id);
		return c.replica;
	}

	public String getKeywords(int id) {
		Conteudo c= getConteudo(id);
		return c.keywords;
	}

	public String getDescricao(int id) {
		Conteudo c= getConteudo(id);
		return c.descricao;
	}

	public String getUrl(int id) {
		Conteudo c= getConteudo(id);
		return c.url;
	}
	/*	private void alimentaConteudo(int id) {
			sqlQuery= "select titulo, texto, ativo, tipo from conteudo where id = " + id;
			Query qry= getQuery();
			qry.command(sqlQuery);
			titulo= "";
			content= ""; // texto
			ativo= "S";
			tipo= "C";

			if (qry.open()) {
				if (!qry.eof()) {
					titulo= qry.getString(1);
					content= qry.getString(2); // texto
					ativo= qry.getString(3); // Sim ou Nao
					tipo= qry.getString(4); // Conteudo ou Promocao
				}
				qry.close();
			}
		}*/

	private int proximoId() {

		QueryCommand cmd= new QueryCommand("select max(id) + 1 from conteudo");
		Integer id= null;
		try {
			Number num = (Number)cmd.executeSingleResult();
			id= new Integer(num.intValue());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (id == null)
			id= new Integer(1);

		return id.intValue();
	}

	private boolean registroNovo(int id) {
		boolean retVal = false;
		QueryCommand cmd=
			new QueryCommand("select ativo from conteudo where id = " + id);

		try {
			retVal = cmd.executeSingleResult() == null;
			//System.out.println("Registro novo = " + retVal);
		} catch (SQLException e) {
			e.printStackTrace();
			retVal = false;
		}
		return retVal;
	}


	public String getContentIndex(int id, int lingua) {
		if(1 == lingua){
			Conteudo c= getConteudoIndex(id);
			return c.content;
		}else if(2 == lingua){
			Conteudo2 c2= getConteudoIndex2(id);
			return c2.content;
		}else if(3 == lingua){
			Conteudo3 c3= getConteudoIndex3(id);
			return c3.content;
		}else if(4 == lingua){
			Conteudo4 c4= getConteudoIndex4(id);
			return c4.content;
		}else{
			Conteudo c= getConteudoIndex(id);
			return c.content;
		}
	}	
	
	public String getContent(int id, int lingua) {
		if(1 == lingua){
			Conteudo c= getConteudo(id);
			return c.content;
		}else if(2 == lingua){
			Conteudo2 c2= getConteudo2(id);
			return c2.content;
		}else if(3 == lingua){
			Conteudo3 c3= getConteudo3(id);
			return c3.content; 
		}else if(4 == lingua){
			Conteudo4 c4= getConteudo4(id);
			return c4.content;
		}else{
			Conteudo c= getConteudo(id);
			return c.content;
		}
	}
	public String getTitulo(int id, int lingua) {
		if(1 == lingua){
			Conteudo c= getConteudo(id);
			return c.titulo;
		}else if(2 == lingua){
			Conteudo2 c2= getConteudo2(id);
			return c2.titulo;
		}else if(3 == lingua){
			Conteudo3 c3= getConteudo3(id);
			return c3.titulo;
		}else if(4 == lingua){
			Conteudo4 c4= getConteudo4(id);
			return c4.titulo;
		}else{
			Conteudo c= getConteudo(id);
			return c.titulo;
		}
	}

	public String getTipo(int id, int lingua) {
		if(1 == lingua){
			Conteudo c= getConteudo(id);
			return c.tipo;
		}else if(2 == lingua){
			Conteudo2 c2= getConteudo2(id);
			return c2.tipo;
		}else if(3 == lingua){
			Conteudo3 c3= getConteudo3(id);
			return c3.tipo;
		}else if(4 == lingua){
			Conteudo4 c4= getConteudo4(id);
			return c4.tipo;
		}else{
			Conteudo c= getConteudo(id);
			return c.tipo;
		}
	}

	public String getAtivo(int id, int lingua) {
		if(1 == lingua){
			Conteudo c= getConteudo(id);
			return c.ativo;
		}else if(2 == lingua){
			Conteudo2 c2= getConteudo2(id);
			return c2.ativo;
		}else if(3 == lingua){
			Conteudo3 c3= getConteudo3(id);
			return c3.ativo;
		}else if(4 == lingua){
			Conteudo4 c4= getConteudo4(id);
			return c4.ativo;
		}else{
			Conteudo c= getConteudo(id);
			return c.ativo;
		}
	}

	public String getReplica(int id, int lingua) {
		if(1 == lingua){
			Conteudo c= getConteudo(id);
			return c.replica;
		}else if(2 == lingua){
			Conteudo2 c2= getConteudo2(id);
			return c2.replica;
		}else if(3 == lingua){
			Conteudo3 c3= getConteudo3(id);
			return c3.replica;
		}else if(4 == lingua){
			Conteudo4 c4= getConteudo4(id);
			return c4.replica;
		}else{
			Conteudo c= getConteudo(id);
			return c.replica;
		}
	}	

	public String getKeywords(int id, int lingua) {
		if(1 == lingua){
			Conteudo c= getConteudo(id);
			return c.keywords;
		}else if(2 == lingua){
			Conteudo2 c2= getConteudo2(id);
			return c2.keywords;
		}else if(3 == lingua){
			Conteudo3 c3= getConteudo3(id);
			return c3.keywords;
		}else if(4 == lingua){
			Conteudo4 c4= getConteudo4(id);
			return c4.keywords;
		}else{
			Conteudo c= getConteudo(id);
			return c.keywords;
		}
	}	

	public String getDescricao(int id, int lingua) {
		if(1 == lingua){
			Conteudo c= getConteudo(id);
			return c.descricao;
		}else if(2 == lingua){
			Conteudo2 c2= getConteudo2(id);
			return c2.descricao;
		}else if(3 == lingua){
			Conteudo3 c3= getConteudo3(id);
			return c3.descricao;
		}else if(4 == lingua){
			Conteudo4 c4= getConteudo4(id);
			return c4.descricao;
		}else{
			Conteudo c= getConteudo(id);
			return c.descricao;
		}
	}	

	public String getUrl(int id, int lingua) {
		if(1 == lingua){
			Conteudo c= getConteudo(id);
			return c.url;
		}else if(2 == lingua){
			Conteudo2 c2= getConteudo2(id);
			return c2.url;
		}else if(3 == lingua){
			Conteudo3 c3= getConteudo3(id);
			return c3.url;
		}else if(4 == lingua){
			Conteudo4 c4= getConteudo4(id);
			return c4.url;
		}else{
			Conteudo c= getConteudo(id);
			return c.url;
		}
	}	

	public int setConteudo(
			int id,
			String cTitulo,
			String cConteudo,
			String conteudoAtivo,
			String tipoConteudo,
			String replicaConteudo, 
			int lingua) {
		return setConteudo(id, cTitulo, cConteudo, conteudoAtivo, tipoConteudo, replicaConteudo, lingua, "", "", "");
	}
	
	public int setConteudo(
		int id,
		String cTitulo,
		String cConteudo,
		String conteudoAtivo,
		String tipoConteudo,
		String replicaConteudo, 
		int lingua,
		String keyWords,
		String descricao,
		String url) {
		
		url = StringUtils.toSiteUrl(cTitulo);
		PreparedStatement stmt= null;
		try {
			Connection con= PoolFilter.getConnection();

			if (id > -1 && !registroNovo(id)) {
				//System.out.println("vou dar update");
				if(!existeUrl(url,id,con)){
					String update = " update conteudo "+
									" set ";
					if(1==lingua){
						update +="titulo = ?, texto = ?";
					}else if(2==lingua){
						update +="titulo2 = ?, texto2 = ?";
					}else if(3==lingua){
						update +="titulo3 = ?, texto3 = ?";
					}else if(4==lingua){
						update +="titulo4 = ?, texto4 = ?";
					}else{
						update +="titulo = ?, texto = ?";
					}
					update +=		", ativo = ?, tipo = ?, "+
									" usuarioultalt = ?, "+
									" datahoraultalt = ?, replicacao = ?," +
									" keywords = ?, descricao = ?, url = ? where id = ?";
					
					stmt=con.prepareStatement(update);
					stmt.setInt(5, EntryPoint.getUser());
					stmt.setTimestamp(6, new Timestamp(new Date().getTime()));
					stmt.setString(7, replicaConteudo);
					stmt.setString(8, keyWords);
					stmt.setString(9, descricao);
					stmt.setString(10, url);
					stmt.setInt(11, id);
				}else{
					id = -99;
				}
			} else {
				//System.out.println("vou dar insert");
				if(!existeUrl(url,id,con)){
				
					if (id < 0)
						id= proximoId();
					
					String insert =	" insert into "+
									" conteudo set  ";
					if(1==lingua){
						insert +=" titulo = ?, texto = ?";
					}else if(2==lingua){
						insert +=" titulo2 = ?, texto2 = ?";
					}else if(3==lingua){
						insert +=" titulo3 = ?, texto3 = ?";
					}else if(4==lingua){
						insert +=" titulo4 = ?, texto4 = ?";
					}else{
						insert +=" titulo = ?, texto = ?";
					}
					insert +=		" , ativo = ?, tipo = ?, Id = ?, "+
									" usuariocriacao = ?, datahoracriacao = ?, usuarioultalt = ?, "+
									" datahoraultalt = ?, replicacao = ?, keywords = ?, descricao = ?, url = ? ";
					
					stmt=con.prepareStatement(insert);
					
					stmt.setInt(5, id);
					stmt.setInt(6, EntryPoint.getUser());
					stmt.setTimestamp(7, new Timestamp(new Date().getTime()));
					stmt.setInt(8, EntryPoint.getUser());
					stmt.setTimestamp(9, new Timestamp(new Date().getTime()));
					stmt.setString(10, replicaConteudo);
					stmt.setString(11, keyWords);
					stmt.setString(12, descricao);
					stmt.setString(13, url);
				}else{
					id = -99;
				}
			}
			if(id != -99){
				stmt.setString(1, cTitulo);
				stmt.setString(2, cConteudo);
				
				if (EntryPoint.isEditor())
					stmt.setString(3, "N");
				else
					stmt.setString(3, conteudoAtivo);
				stmt.setString(4, tipoConteudo);
				
				cache.remove(new Integer(id));
				stmt.executeUpdate();
			}
			//System.out.println("retornando id "+id);
			return id;
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
			return -1;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean existeUrl(String url, int id, Connection con){
		boolean exists = false;
		String qryurl = " select * " +
						" from conteudo " +
						" where url = ? ";
		System.out.println("existeUrl :: "+url+" -- id ::"+id);
		if(id>0){
			qryurl += " and id <> ?";
		}
		
		if(null!=con){
			try{
				PreparedStatement ps = con.prepareStatement(qryurl);
				ps.setString(1, url);
				if(id>0){
					ps.setInt(2, id);
				}

				ResultSet rs = ps.executeQuery();

				if(rs.next()){
					exists = true;
				}
				
				if(null != rs){
					rs.close();
					rs = null;
				}
				
				if(null != ps){
					ps.close();
					ps = null;
				}

			}catch(SQLException s){
				
			}
		}
		
		return exists;
	}
	
	public int setConteudo(
			int id,
			String cTitulo,
			String cConteudo,
			String conteudoAtivo,
			String tipoConteudo,
			String replicaConteudo) {
		return setConteudo(id, cTitulo, cConteudo, conteudoAtivo, tipoConteudo, replicaConteudo, "", "","");
	}
	
	public int setConteudo(
		int id,
		String cTitulo,
		String cConteudo,
		String conteudoAtivo,
		String tipoConteudo,
		String replicaConteudo,
		String keyWords,
		String descricao,
		String url) {
		url = StringUtils.toSiteUrl(cTitulo);
		PreparedStatement stmt= null;
		try {
			Connection con= PoolFilter.getConnection();

			if (id > -1 && !registroNovo(id)) {
				//System.out.println("vou dar update");
				if(!existeUrl(url,id,con)){
					stmt=
						con.prepareStatement(
							"update conteudo "
								+ " set titulo = ?, texto = ?, ativo = ?, tipo = ?, "
								+ " usuarioultalt = ?, "
								+ "datahoraultalt = ?, replicacao = ?, keywords = ?, descricao = ?, url = ? where id = ?");
					stmt.setInt(5, EntryPoint.getUser());
					stmt.setTimestamp(6, new Timestamp(new Date().getTime()));
					stmt.setString(7, replicaConteudo);
					stmt.setString(8, keyWords);
					stmt.setString(9, descricao);
					stmt.setString(10, url);
					stmt.setInt(11, id);
				}else{
					id = -99;
				}
			} else {
				//System.out.println("vou dar insert");
				if(!existeUrl(url,id,con)){
					if (id < 0)
						id= proximoId();
					stmt=
						con.prepareStatement(
							"insert into "
								+ "conteudo set titulo = ?, texto = ?, ativo = ?, tipo = ?, Id = ?, "
								+ "usuariocriacao = ?, datahoracriacao = ?, usuarioultalt = ?, "
								+ "datahoraultalt = ?, replicacao = ?, keywords = ?, descricao = ?, url = ?");
					stmt.setInt(5, id);
					stmt.setInt(6, EntryPoint.getUser());
					stmt.setTimestamp(7, new Timestamp(new Date().getTime()));
					stmt.setInt(8, EntryPoint.getUser());
					stmt.setTimestamp(9, new Timestamp(new Date().getTime()));
					stmt.setString(10, replicaConteudo);
					stmt.setString(11, keyWords);
					stmt.setString(12, descricao);
					stmt.setString(13, url);
				}else{
					id = -99;
				}
			}

			if(id != -99){
				stmt.setString(1, cTitulo);
				stmt.setString(2, cConteudo);
				if (EntryPoint.isEditor())
					stmt.setString(3, "N");
				else
					stmt.setString(3, conteudoAtivo);
				stmt.setString(4, tipoConteudo);
				cache.remove(new Integer(id));
				stmt.executeUpdate();
			}
			//System.out.println("retornando id "+id);
			return id;
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
			return -1;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	} 

	public boolean excluiConteudo(int id) {

		UpdateStatement stmt=
			new UpdateStatement("delete from conteudo where id = " + id);
		try {
			cache.remove(new Integer(id));
			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getTemplate(int id) {
		QueryCommand cmd=
			new QueryCommand("select conteudo from modelo where idimodelo = " + id);
		final StringBuffer template = new StringBuffer();
		try {
			cmd.execute(new RowIterator() {
				public void iterate(ResultSet rs) throws SQLException {
					template.append(rs.getString(1));
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return template.toString();
	}	
	
	public int[] getIdsPromo() {
		final ArrayList ar= new ArrayList();
		QueryCommand cmd=
			new QueryCommand("select id from conteudo where tipo = 'P'");
		try {
			cmd.execute(new RowIterator() {
				public void iterate(ResultSet rs) throws SQLException {
					ar.add(new Integer(rs.getInt(1)));
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
			ar.clear();
		}
		if (ar.isEmpty())
			ar.add(new Integer(-1));

		int[] ids= new int[ar.size()];
		for (int i= 0; i < ids.length; ++i)
			ids[i]= ((Integer)ar.get(i)).intValue();
		return ids;
	}

	public int getConteudoUrl(String url) {
		final ArrayList ar= new ArrayList();
		QueryCommand cmd=
			new QueryCommand("select id from conteudo where url = '"+url+"'");
		try {
			cmd.execute(new RowIterator() {
				public void iterate(ResultSet rs) throws SQLException {
					ar.add(new Integer(rs.getInt(1)));
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
			ar.clear();
		}
		if (ar.isEmpty())
			ar.add(new Integer(-1));
		return ((Integer)ar.get(0)).intValue();
		
	}
	/*
	 * O codigo abaixo demonstra o codigo que deve ser gerado para o componente marquee
	 *
	 *
	'<a href="http://www.bb.com.br" target="_blank">Clique aqui para ir ao site do Banco do Brasil</a>',
	'<a href="http://www.bradesco.com.br" target="_blank">Clique aqui para ir ao site do Banco Bradesco</a>',
	'<a href="http://www.itau.com.br" target="_blank">Clique aqui para ir ao site do Banco Itaï¿½</a>',
	'<a href="http://www.hsbc.com.br" target="_blank">Clique aqui para ir ao site do Banco HSBC</a>',
	'<a href="http://www.sudameris.com.br" target="_blank">Clique aqui para ir ao site do Banco Sudameris</a>'
	*/
	public String getHtmlMarqueeArtigos() {
		final StringBuffer bf=
			new StringBuffer();
		Boolean anyContent = null;
		QueryCommand cmd= new QueryCommand("select id, titulo from conteudo where tipo = 'A' order by id desc");
		try {
			final int maxRegs=10;
			anyContent = (Boolean)cmd.executeWithResult(new RowIteratorWithResult() {
				int qtde = 0;
				public Object iterate(ResultSet rs) throws SQLException {
					if (qtde >= maxRegs)
						return Boolean.TRUE;
					if (qtde > 0){
						bf.append(",");
						bf.append("\n");
					}	
					bf
						.append("'<a href=\"index.jsp?conteudo=")
						.append(rs.getInt(1))
						.append("\">")
						.append(rs.getString(2))
						.append("</a>'");
					++qtde;

					if (qtde == 0)
						return null;
					else
						return Boolean.TRUE;
				}});

		} catch (SQLException e) {
			e.printStackTrace();
			anyContent = Boolean.FALSE;
			bf.setLength(0);
		}

		if(anyContent == null || !anyContent.booleanValue()) {
			// adiciona minimo de 2 conteudos para nao dar problema com o componente
			//TODO aahhh?!?!? Como assim se tiver menos 2 da problema?
			bf.append("'<a href=\"index.jsp\">Artigos</a>', '<a href=\"index.jsp\">Novidades</a>'");
		}
		//System.out.println("BUffer == "+bf.toString());
		return bf.toString();
	}

	public String getHtmlMarqueeNoticias() {
		final StringBuffer bf= new StringBuffer();
		//bf.append(
			//"<br><font face=\"Verdana, Arial, Helvetica\" color=\"#000000\" size=\"1\">");

		Boolean anyContent = null;
		QueryCommand cmd= new QueryCommand("select id, titulo from conteudo where tipo = 'N' and ativo = 'S' order by id desc");
		try {
			final int maxRegs=10;
			anyContent = (Boolean)cmd.executeWithResult(new RowIteratorWithResult() {
				int qtde = 0;
				public Object iterate(ResultSet rs) throws SQLException {
					if (qtde >= maxRegs)
						return Boolean.TRUE;
					if (qtde > 0){
						bf.append(",");
						bf.append("\n");
					}
					bf
						.append("'<a href=\"index.jsp?conteudo=")
						.append(rs.getInt(1))
						.append("\">")
						.append(rs.getString(2))
						.append("</a>'");
					++qtde;

					if (qtde == 0)
						return null;
					else
						return Boolean.TRUE;
				}});

		} catch (SQLException e) {
			e.printStackTrace();
			anyContent = Boolean.FALSE;
			bf.setLength(0);
		}

		if(anyContent == null || !anyContent.booleanValue()) {
			// adiciona minimo de 2 conteudos para nao dar problema com o componente
			//TODO aahhh?!?!? Como assim se tiver menos 2 da problema?
			bf.append("'<a href=\"index.jsp\">Notícias</a>', '<a href=\"index.jsp\">Notícias</a>'");
		}
		//System.out.println("BUffer == "+bf.toString());
		return bf.toString();
	}

	public String getHtmlMarqueeArtigos(int lingua) {
		final StringBuffer bf=
			new StringBuffer();
		Boolean anyContent = null;
//		String querymarquee = "select id, ";
		String querymarquee = "select Url, ";
		if(1==lingua){
			querymarquee +=	"if(titulo is not null, titulo, 'Artigo') ";
		}else if(2==lingua){
			querymarquee +=	"if(titulo2 is not null, titulo2, titulo) ";
		}else if(3==lingua){
			querymarquee +=	"if(titulo3 is not null, titulo3, titulo) ";
		}else if(4==lingua){
			querymarquee +=	"if(titulo4 is not null, titulo4, titulo) ";
		}else{
			querymarquee +=	"if(titulo is not null, titulo, 'Artigo') ";
		}
		
		querymarquee +=	" from conteudo where tipo = 'A' order by id desc";
		
		
		QueryCommand cmd= new QueryCommand(querymarquee);
		try {
			final int maxRegs=10;
			anyContent = (Boolean)cmd.executeWithResult(new RowIteratorWithResult() {
				int qtde = 0;
				public Object iterate(ResultSet rs) throws SQLException {
					if (qtde >= maxRegs)
						return Boolean.TRUE;
					if (qtde > 0){
						bf.append(",");
						bf.append("\n");
					}	
					bf
//						.append("'<a href=\"index.jsp?conteudo=")
						.append("'<a href=\"/site/")
						.append(rs.getString(1))
						.append("\">")
						.append(rs.getString(2))
						.append("</a>'");
					++qtde;

					if (qtde == 0)
						return null;
					else
						return Boolean.TRUE;
				}});

		} catch (SQLException e) {
			e.printStackTrace();
			anyContent = Boolean.FALSE;
			bf.setLength(0);
		}

		if(anyContent == null || !anyContent.booleanValue()) {
			// adiciona minimo de 2 conteudos para nao dar problema com o componente
			//TODO aahhh?!?!? Como assim se tiver menos 2 da problema?
			bf.append("'<a href=\"index.jsp\">Artigos</a>', '<a href=\"index.jsp\">Novidades</a>'");
		}
		//System.out.println("BUffer == "+bf.toString());
		return bf.toString();
	}

	public String getHtmlMarqueeNoticias(int lingua) {
		final StringBuffer bf= new StringBuffer();
		//bf.append(
			//"<br><font face=\"Verdana, Arial, Helvetica\" color=\"#000000\" size=\"1\">");

		Boolean anyContent = null;
		
		String querymarquee = "select id, ";
		if(1==lingua){
			querymarquee +=	"if(titulo is not null, titulo, 'Artigo') ";
		}else if(2==lingua){
			querymarquee +=	"if(titulo2 is not null, titulo2, titulo) ";
		}else if(3==lingua){
			querymarquee +=	"if(titulo3 is not null, titulo3, titulo) ";
		}else if(4==lingua){
			querymarquee +=	"if(titulo4 is not null, titulo4, titulo) ";
		}else{
			querymarquee +=	"if(titulo is not null, titulo, 'Artigo') ";
		}
		
		querymarquee +=	" from conteudo where tipo = 'N' and ativo = 'S' order by id desc";

		QueryCommand cmd= new QueryCommand(querymarquee);
		try {
			final int maxRegs=10;
			anyContent = (Boolean)cmd.executeWithResult(new RowIteratorWithResult() {
				int qtde = 0;
				public Object iterate(ResultSet rs) throws SQLException {
					if (qtde >= maxRegs)
						return Boolean.TRUE;
					if (qtde > 0){
						bf.append(",");
						bf.append("\n");
					}
					bf
						.append("'<a href=\"index.jsp?conteudo=")
						.append(rs.getInt(1))
						.append("\">")
						.append(rs.getString(2))
						.append("</a>'");
					++qtde;

					if (qtde == 0)
						return null;
					else
						return Boolean.TRUE;
				}});

		} catch (SQLException e) {
			e.printStackTrace();
			anyContent = Boolean.FALSE;
			bf.setLength(0);
		}

		if(anyContent == null || !anyContent.booleanValue()) {
			// adiciona minimo de 2 conteudos para nao dar problema com o componente
			//TODO aahhh?!?!? Como assim se tiver menos 2 da problema?
			bf.append("'<a href=\"index.jsp\">Notícias</a>', '<a href=\"index.jsp\">Notícias</a>'");
		}
		//System.out.println("BUffer == "+bf.toString());
		return bf.toString();
	}

	 
	public String getNoticias(int lingua) {
		final StringBuffer bf= new StringBuffer();

		Boolean anyContent = null;
		
		String querymarquee = "select id, ";
		if(1==lingua){
			querymarquee +=	"if(titulo is not null, titulo, 'Artigo') ";
		}else if(2==lingua){
			querymarquee +=	"if(titulo2 is not null, titulo2, titulo) ";
		}else if(3==lingua){
			querymarquee +=	"if(titulo3 is not null, titulo3, titulo) ";
		}else if(4==lingua){
			querymarquee +=	"if(titulo4 is not null, titulo4, titulo) ";
		}else{
			querymarquee +=	"if(titulo is not null, titulo, 'Artigo') ";
		}
		
		querymarquee +=	" from conteudo where tipo = 'N' and ativo = 'S' order by id desc";
		System.out.println("query not:: "+querymarquee);
		QueryCommand cmd= new QueryCommand(querymarquee);
		try {
			final int maxRegs=10;
			anyContent = (Boolean)cmd.executeWithResult(new RowIteratorWithResult() {
				int qtde = 0;
				public Object iterate(ResultSet rs) throws SQLException {
					if (qtde >= maxRegs)
						return Boolean.TRUE;
					
					if (qtde > 0){
						bf.append(",");
						bf.append("\n");
					}
					
					System.out.println(" not:: "+rs.getString(2));
					
					bf
					.append("[")	
					.append("'")
					.append("index.jsp?conteudo=")
					.append(rs.getInt(1))
					.append("'")
					.append(",")
					.append("'")
					.append(rs.getString(2))
					.append("'")
					.append("]");
					++qtde;

					if (qtde == 0)
						return null;
					else
						return Boolean.TRUE;
				}});

		} catch (SQLException e) {
			e.printStackTrace();
			anyContent = Boolean.FALSE;
			bf.setLength(0);
		}
		System.out.println("return not:: '"+bf.toString()+"'");
		return bf.toString();
	}

	
}
