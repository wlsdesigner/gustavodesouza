package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.relato.pool.PoolFilter;
import com.relato.pool.QueryCommand;
import com.relato.pool.RowIterator;
import com.relato.pool.RowIteratorWithResult;
import com.relato.pool.UpdateStatement;

public class MenuConstructor {
	private final boolean debug= false;
	private ArrayList itens= new ArrayList();
	private ArrayList aManut= new ArrayList();
	private String sqlQuery;
	private String tableName= "?";
	private short count;
	private short countManut;

	private int tipolingua=1;
	
	// constructor
	public MenuConstructor() {
	}
	
	public void setTableName(String tableName) {
		String old = this.tableName;
		this.tableName = tableName;
		if(!tableName.equals(old))
			leItens();
	}

	public void inicializaMenu() {
		itens.clear();
		itens.add(new MenuItem(1, "Home", "http://localhost", "", 0, 1));
		count= 1;
		aManut.clear();
		countManut= 0;

	}

	// adiciona novo item de menu CALCULANDO o id  (VEJA ABAIXO VERSAO INFORMANDO o id)
	public void addItem(
		String name,
		String link,
		String target,
		int parentId,
		int order) {

		int id= getNextId();
		int newOrder;

		if (order <= 0)
			newOrder= getNextOrder(parentId);
		else
			newOrder= order;

		itens.add(new MenuItem(id, name, link, target, parentId, newOrder));
		count++;

		if (debug)
			System.out.println(
				"Id calculado: "
					+ id
					+ " / Name = "
					+ name
					+ " / Link = "
					+ link
					+ " / Target = "
					+ target
					+ " / parentId = "
					+ parentId
					+ " / order = "
					+ order);

	}
	//adiciona novo item de menu INFORMANDO o id (VEJA ABAIXO VERSAO CALCULANDO o id)
	public void addItem(
		int id,
		String name,
		String link,
		String target,
		int parentId,
		int order) {
		

		int newOrder;

		if (order <= 0)
			newOrder= getNextOrder(parentId);
		else
			newOrder= order;

		itens.add(new MenuItem(id, name, link, target, parentId, newOrder));
		count++;

		if (debug)
			System.out.println(
				"Id informado: "
					+ id
					+ " / Name = "
					+ name
					+ " / Link = "
					+ link
					+ " / Target = "
					+ target
					+ " / parentId = "
					+ parentId
					+ " / order = "
					+ order);

	}

	public MenuItem getItem(int Id) {

		MenuItem mi= null;
		int menuId;

		for (int i= 0; i < count; i++) {

			menuId= ((MenuItem)itens.get(i)).menuId;
			if (Id == menuId) {
				mi= (MenuItem)itens.get(i);
				break;
			}
		}
		return mi;
	}

	public void saveItem(
		int Id,
		String menuName,
		String menuLink,
		String menuTarget,
		int menuOrder) {

		MenuItem mi= null;
		int menuId;

		for (int i= 0; i < count; i++) {

			menuId= ((MenuItem)itens.get(i)).menuId;
			if (Id == menuId) {
				mi= (MenuItem)itens.get(i);

				mi.name= menuName;
				mi.link= menuLink;
				mi.target= menuTarget;
				mi.order= menuOrder;
				mi.language = 1;
				break;
			}
		}
	}

	public int getNextId() { 
		

		int id= 0;
		int idAtual;
		for (int i= 0; i < count; i++) {

			idAtual= ((MenuItem)itens.get(i)).menuId;
			if (idAtual > id) {
				id= idAtual;
			}
		}
		id++;
		return id;
	}

	private int getNextIdManut() {

		int id= 0;
		int idAtual;
		for (int i= 0; i < aManut.size(); i++) {

			idAtual= ((MenuItem)aManut.get(i)).menuId;
			if (idAtual > id) {
				id= idAtual;
			}
		}
		id++;
		return id;
	}

	private int getNextOrder(int parentId) {

		int order= 0;
		int orderAtual;
		int parent= 0;
		for (int i= 0; i < count; i++) {

			parent= ((MenuItem)itens.get(i)).parentId;
			if (parent == parentId) {
				orderAtual= ((MenuItem)itens.get(i)).order;
				if (orderAtual > order) {
					order= orderAtual;
				}
			}
		}
		order++;
		return order;
	}

	private int getNextOrderManut(int parentId) {

		int order= 0;
		int orderAtual;
		int parent= 0;
		for (int i= 0; i < count; i++) {

			parent= ((MenuItem)aManut.get(i)).parentId;
			if (parent == parentId) {
				orderAtual= ((MenuItem)aManut.get(i)).order;
				if (orderAtual > order) {
					order= orderAtual;
				}
			}
		}
		order++;
		return order;
	}

	public boolean hasChildren(int parentId) {

		boolean bOk= false;
		int parent= -1;
		for (int i= 0; i < itens.size(); i++) {
			parent= ((MenuItem)itens.get(i)).parentId;
			if (parent == parentId) {
				bOk= true;
				break;
			}
		}
		return bOk;
	}

	public boolean hasChildrenManut(int parentId) {

		boolean bOk= false;
		int parent= -1;
		for (int i= 0; i < aManut.size(); i++) {
			parent= ((MenuItem)aManut.get(i)).parentId;
			if (parent == parentId) {
				bOk= true;
				break;
			}
		}
		return bOk;
	}

	public void removeItem(int menuId) {

		int idAtual;
		for (int i= 0; i < count; i++) {

			idAtual= ((MenuItem)itens.get(i)).menuId;
			if (idAtual == menuId) {
				itens.remove(i);
				count--;
			}
		}
	}

	public int getCount() {
		return count;
	}

	public ArrayList getItens() {

		// ordena depois devolve
		ordenaItens();
		return itens;
	}

	public ArrayList getItensManut() {

		ArrayList aParents;
		int parent;
		int id;
		int newOrder;
		int i;
		int menuId;
		Integer p;

		// ordena depois devolve
		ordenaItens();

		// adiciona na copia todos os items da original
		aManut.clear();
		aManut.addAll(itens);
		countManut= count;

		// faz um looping no original e descobre quais sao os parents
		aParents= getAllParents();
		for (i= 0; i < aParents.size(); i++) {

			parent= ((Integer)aParents.get(i)).intValue();
			id= getNextIdManut();
			newOrder= getNextOrderManut(parent);

			aManut.add(
				new MenuItem(
					id,
					"_novo_item",
					new Integer(parent).toString(),
					"",
					parent,
					newOrder));
			countManut++;
		}
		// agora, faz um looping no original pegando os que nao sao parents mas poderao (poderiam) ser
		for (i= 0; i < itens.size(); i++) {

			menuId= ((MenuItem)itens.get(i)).menuId; 
			p= new Integer(menuId);
			if (!aParents.contains(p)) {
				id= getNextIdManut();
				newOrder= getNextOrderManut(menuId);
				aManut.add(
					new MenuItem(
						id,
						"_novo_item",
						new Integer(menuId).toString(),
						"",
						menuId,
						newOrder));
				countManut++;

				if (debug)
					System.out.println("NOT parent: " + menuId);

			}
		}

		// ordena depois devolve
		ordenaItensManut();

		return aManut;
	}

	private ArrayList getAllParents() {

		int parent;
		Integer p;
		ArrayList aParents= new ArrayList();

		for (int i= 0; i < itens.size(); i++) {

			parent= ((MenuItem)itens.get(i)).parentId;
			p= new Integer(parent);
			if (!aParents.contains(p))
				aParents.add(p);
			if (debug)
				System.out.println("parent: " + parent);

		}
		return aParents;
	}

	private void ordenaItens() {

		// Organiza lista em ordem de: parentId + order
		Collections.sort(itens, new Comparator() {
			public int compare(Object o1, Object o2) {

				int par1= ((MenuItem)o1).parentId;
				int par2= ((MenuItem)o2).parentId;

				if (par1 == par2) {

					int ord1= ((MenuItem)o1).order;
					int ord2= ((MenuItem)o2).order;
					return (ord1 - ord2);

				} else {
					return (par1 - par2);
				}

			}
		});

	}

	private void ordenaItensManut() {

		// Organiza lista em ordem de: parentId + order
		Collections.sort(aManut, new Comparator() {
			public int compare(Object o1, Object o2) {

				int par1= ((MenuItem)o1).parentId;
				int par2= ((MenuItem)o2).parentId;

				if (par1 == par2) {

					int ord1= ((MenuItem)o1).order;
					int ord2= ((MenuItem)o2).order;
					return (ord1 - ord2);

				} else {
					return (par1 - par2);
				}

			}
		});
	}

	public void leItens() {
		itens.clear();
		count= 0;

		sqlQuery=
			"select id, name, link, target, parentid, showorder from menu"
				+ " order by parentid, showorder ";

		QueryCommand cmd= new QueryCommand(sqlQuery);
		try {
			cmd.execute(new RowIterator() {
				public void iterate(ResultSet rs) throws SQLException {
					addItem(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getInt(6));
				}
			});
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
		}
	}

	public boolean salvaItens() {

		ordenaItens(); // ordena itens antes de salvar

		boolean bOk= true;

		if (debug)
			System.out.println("Vou salvar os items");

		MenuItem mi= null;
		int menuId;
		int i;
		String listaIds;

		StringBuffer bf= new StringBuffer();

		for (i= 0; i < count; i++) {

			mi= (MenuItem)itens.get(i);

			// adiciona na lista de Ids para uso abaixo
			if (i > 0)
				bf.append(", ");
			bf.append(mi.menuId);

			if (debug)
				System.out.println("Vou salvar o item: " + mi.menuId);

			if (!salvaEsseItem(mi.menuId,
				mi.name,
				mi.link,
				mi.target,
				mi.parentId,
				mi.order,
				mi.language)) {

				if (debug)
					System.out.println(
						"Nao foi possivel salvar o item: " + mi.menuId);

				bOk= false;
				break;
			}
		}

		listaIds= bf.toString();

		// verifica se houve exclusoes
		if ((listaIds != null) && (listaIds.trim().length() > 0)) {

			if (debug)
				System.out.println("Lista de Ids: " + listaIds);

			sqlQuery=
				"select id from menu"
					+ " where id not in ( "
					+ listaIds
					+ " ) ";

			QueryCommand cmd= new QueryCommand(sqlQuery);
			try {
				cmd.execute(new RowIterator() {
					public void iterate(ResultSet rs) throws SQLException {
						excluiEsseItem(rs.getInt(1));
					}
				});
			} catch (SQLException e) {
				//TODO não sei como esse erro tem que ser tratado
				e.printStackTrace();
				bOk= false;
			}
		}

		return bOk;
	}

	private void excluiEsseItem(int id) {
		String sql= "delete from menu where id = " + id;
		UpdateStatement stmt= new UpdateStatement(sql);
		try {
			stmt.execute();
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
		}
	}

	private boolean salvaEsseItem(
		int id,
		String name,
		String link,
		String target,
		int parent,
		int order) {

		boolean bAchou= false;

		sqlQuery= "select id from menu where id = " + id;
		QueryCommand cmd= new QueryCommand(sqlQuery);
		try {
			Boolean b= (Boolean)cmd.executeWithResult(new RowIteratorWithResult() {
				public Object iterate(ResultSet rs) throws SQLException {
					return Boolean.TRUE;
				}
			});
			if (b != null)
				bAchou= b.booleanValue();
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
			bAchou= false;
		}

		if (bAchou)
			return atualizaEsseItem(id, name, link, target, parent, order);
		else
			return incluiEsseItem(id, name, link, target, parent, order);

	}

	private boolean incluiEsseItem(
		int id,
		String name,
		String link,
		String target,
		int parent,
		int order) {
		PreparedStatement stmt= null;
		try {
			Connection con= PoolFilter.getConnection();

			stmt=
				con.prepareStatement(
					"insert into menu "
						+ "(Id, Name, Link, Target, ParentId, ShowOrder) values(?,?,?,?,?,?)");
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setString(3, link);
			stmt.setString(4, target);
			stmt.setInt(5, parent);
			stmt.setInt(6, order);
			stmt.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
			return false;
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

	private boolean atualizaEsseItem(
		int id,
		String name,
		String link,
		String target,
		int parent,
		int order) {

		PreparedStatement stmt= null;
		try {
			Connection con= PoolFilter.getConnection();
			stmt=
				con.prepareStatement(
					"update menu "
						+ "set Name = ?, Link = ?, Target = ?, ParentId = ?, ShowOrder = ? where id = ?");
			stmt.setString(1, name);
			stmt.setString(2, link);
			stmt.setString(3, target);
			stmt.setInt(4, parent);
			stmt.setInt(5, order);
			stmt.setInt(6, id);
			stmt.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
			return false;
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
  
	//RAFAEL LINGUA
	public void saveItem(
			int Id,
			String menuName,
			String menuLink,
			String menuTarget,
			int menuOrder,
			int lingua) {

		MenuItem mi= null;
		int menuId;
		int tipoLingua;
		
		boolean found = false;
		for (int i= 0; i < count; i++) {

			menuId= ((MenuItem)itens.get(i)).menuId;
			tipoLingua = ((MenuItem)itens.get(i)).language;
			if (Id == menuId && lingua == tipoLingua) {
				mi= (MenuItem)itens.get(i);

				mi.name= menuName;
				mi.link= menuLink;
				mi.target= menuTarget;
				mi.order= menuOrder;
				mi.language = lingua;
				found = true;
				break;
			}
			
		}
		
		if(!found){
			addItem(Id,menuName,menuLink,menuTarget,0,menuOrder,lingua);
		}
		
	}

	
	public void setTableName(String tableName, int lingua) {
		String old = this.tableName;
		int oldlang = this.tipolingua;
		this.tableName = tableName;
		this.tipolingua = lingua;
		if(!tableName.equals(old) || tipolingua != oldlang)
			leItens(tipolingua);
	}
	
	public void leItens(int lingua) {
		itens.clear();
		count= 0;
		
		System.out.println("Lendo itens da lingua == "+lingua);
		
		sqlQuery=	" select id, ";
		if(1 == lingua){
			sqlQuery+= " name ";
		}else if(2 == lingua){
			sqlQuery+= " if(name2 is not null, name2, name) ";
		}else if(3 == lingua){
			sqlQuery+= " if(name3 is not null, name3, name) ";
		}else if(4 == lingua){
			sqlQuery+= " if(name4 is not null, name4, name) ";
		}else{
			sqlQuery+= " name ";
		}
					
					
		sqlQuery+=	", link, target, parentid, showorder from menu" + 
					
					" order by parentid, showorder ";

		QueryCommand cmd= new QueryCommand(sqlQuery);
		try {
			cmd.execute(new RowIterator() {
				public void iterate(ResultSet rs) throws SQLException {
					addItem(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getInt(6));
				}
			});
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
		}
	}

	
	public boolean salvaItens(int lingua) {

		ordenaItens(); // ordena itens antes de salvar

		boolean bOk= true;

		if (debug)
			System.out.println("Vou salvar os items");

		MenuItem mi= null;
		int menuId;
		int i;
		String listaIds;

		StringBuffer bf= new StringBuffer();

		for (i= 0; i < count; i++) {

			mi= (MenuItem)itens.get(i);

			// adiciona na lista de Ids para uso abaixo
			if (i > 0)
				bf.append(", ");
			bf.append(mi.menuId);

			if (debug)
				System.out.println("Vou salvar o item: " + mi.menuId);

			if (!salvaEsseItem(mi.menuId,
				mi.name,
				mi.link,
				mi.target,
				mi.parentId,
				mi.order)) {

				if (debug)
					System.out.println(
						"Nao foi possivel salvar o item: " + mi.menuId);

				bOk= false;
				break;
			}
		}

		listaIds= bf.toString();

		// verifica se houve exclusoes
		if ((listaIds != null) && (listaIds.trim().length() > 0)) {

			if (debug)
				System.out.println("Lista de Ids: " + listaIds);

			sqlQuery=
				"select id from menu"
					+ " where id not in ( "
					+ listaIds
					+ " ) ";

			QueryCommand cmd= new QueryCommand(sqlQuery);
			try {
				cmd.execute(new RowIterator() {
					public void iterate(ResultSet rs) throws SQLException {
						excluiEsseItem(rs.getInt(1));
					}
				});
			} catch (SQLException e) {
				//TODO não sei como esse erro tem que ser tratado
				e.printStackTrace();
				bOk= false;
			}
		}

		return bOk;
	}

	private boolean salvaEsseItem(
		int id,
		String name,
		String link,
		String target,
		int parent,
		int order, int lingua) {

		boolean bAchou= false;

		sqlQuery= "select id from menu where id = " + id;
		QueryCommand cmd= new QueryCommand(sqlQuery);
		try {
			Boolean b= (Boolean)cmd.executeWithResult(new RowIteratorWithResult() {
				public Object iterate(ResultSet rs) throws SQLException {
					return Boolean.TRUE;
				}
			});
			if (b != null)
				bAchou= b.booleanValue();
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
			bAchou= false;
		}

		if (bAchou)
			return atualizaEsseItem(id, name, link, target, parent, order,lingua);
		else
			return incluiEsseItem(id, name, link, target, parent, order);

	}

	private boolean atualizaEsseItem(
		int id,
		String name,
		String link,
		String target,
		int parent,
		int order, int lingua) {

		PreparedStatement stmt= null;
		try {
			Connection con= PoolFilter.getConnection();
			String update =	" update menu ";
			
			if(1 == lingua){
				update +="set Name = ?";
			}else if(2 == lingua){
				update +="set Name2 = ?";
			}else if(3 == lingua){
				update +="set Name3 = ?";
			}else if(4 == lingua){
				update +="set Name4 = ?";
			}
			
			update +=	" , Link = ?, Target = ?, ParentId = ?, ShowOrder = ? where id = ?";
			
			stmt=con.prepareStatement(update);
			stmt.setString(1, name);
			stmt.setString(2, link);
			stmt.setString(3, target);
			stmt.setInt(4, parent);
			stmt.setInt(5, order);
			stmt.setInt(6, id);
			stmt.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			//TODO não sei como esse erro tem que ser tratado
			e.printStackTrace();
			return false;
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

	public void addItem(
		int id,
		String name,
		String link,
		String target,
		int parentId,
		int order,
		int lingua) {
		

		int newOrder;

		if (order <= 0)
			newOrder= getNextOrder(parentId);
		else
			newOrder= order;

		itens.add(new MenuItem(id, name, link, target, parentId, newOrder,lingua));
		count++;

		if (debug)
			System.out.println(
				"Id informado: "
					+ id
					+ " / Name = "
					+ name
					+ " / Link = "
					+ link
					+ " / Target = "
					+ target
					+ " / parentId = "
					+ parentId
					+ " / order = "
					+ order);

	}

	public MenuItem getItem(int Id, int lingua) {

		MenuItem mi= null;
		int menuId;
		int menuLang;
		for (int i= 0; i < count; i++) {

			menuId= ((MenuItem)itens.get(i)).menuId;
			menuLang = ((MenuItem)itens.get(i)).language;
			if (Id == menuId && lingua == menuLang) {
				mi= (MenuItem)itens.get(i);
				break;
			}
		}
		return mi;
	}

	public void geraDadosExemplo() {
 
		itens.clear();
		count= 0;
		/*addItem("familia", "linktop1", "", 0, 0);
		addItem("bancos", "linktop2", "", 0, 0);
		addItem("clientes", "linktop3", "", 0, 0);
		addItem("amigos", "linktop4", "", 0, 0);
		addItem("bradesco", "http://www.bradesco.com.br", "", 2, 0);
		addItem("itau", "http://www.itau.com.br", "", 2, 0);
		addItem("hsbc", "http://www.hsbc.com.br", "", 2, 0);
		addItem("jo", "http://www.jo.org", "", 1, 0);
		addItem("rafael", "http://www.rafael.org", "", 1, 0);
		addItem("deborah", "http://www.deborah.org", "", 1, 0);
		addItem("beatriz", "http://www.beatriz.org", "", 1, 0);
		addItem("Mangalarga", "http://www.cavalomangalarga.com.br", "", 3, 0);
		addItem("Faxexpress", "http://www.faxexpress.com.br", "", 3, 0);
		addItem("Hmv", "http://www.hmv.com.br", "", 3, 0);
		addItem("Sodic", "http://www.sodic.com.br", "", 3, 0);
		addItem("Apdata", "http://www.apdata.com.br", "", 3, 0);
		addItem("Jo?o", "http://www.joao.com.br", "", 4, 0);
		addItem("Jos?", "http://www.jose.com.br", "", 4, 0);
		addItem("Arnaldo", "http://www.arnaldo.com.br", "", 4, 0);
		addItem("Francisco", "http://www.fran.com.br", "", 4, 0);
		addItem("Eleonora", "http://www.eleonora.com.br", "", 4, 0);*/

	}
	//TODO rafael verificar os parent ids pois acho que deu pau
	public void showItens() {

		MenuItem mi;
		for (int i= 0; i < count; i++) {

			mi= (MenuItem)itens.get(i);
			System.out.println(
				"Menu: "
					+ mi.menuId
					+ " Nome: "
					+ mi.name
					+ " Link: "
					+ mi.link
					+ " Target: "
					+ mi.target
					+ " Parent: "
					+ mi.parentId
					+ " Order: "
					+ mi.order);

		} 
	}

	public class MenuItem {

		public int menuId;
		public String name;
		public String link;
		public String target;
		public int parentId;
		public int order;
		public int language;
		
		//constructor
		public MenuItem(
			int menuId,
			String name,
			String link,
			String target,
			int parentId,
			int order) {

			this.menuId= menuId;
			this.name= name;
			this.link= link;
			this.target= target;
			this.parentId= parentId;
			this.order= order;
			this.language = 1;

		}
		
		//RAFAEL LINGUA
		public MenuItem(
				int menuId,
				String name,
				String link,
				String target,
				int parentId,
				int order, 
				int language) {

				this.menuId= menuId;
				this.name= name;
				this.link= link;
				this.target= target;
				this.parentId= parentId;
				this.order= order;
				this.language = language;

		}
	}

	/*
	public static void main( String args[] ) {
	
	    MenuConstructor mc = new MenuConstructor();
		mc.geraDadosExemplo();
	    mc.showItens();
	
	}
	*/
	/*
	
	 CREATE TABLE menu_v ( Id int(11) primary key not null, Name varchar(255) not null, Link varchar(255) not null, ParentId int(11) null, ShowOrder  int(11) null);
		
		alter table menu add column Target varchar(10) default '' after Link;
	 */

}
