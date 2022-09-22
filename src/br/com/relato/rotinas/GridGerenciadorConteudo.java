package br.com.relato.rotinas;


import java.sql.SQLException;
import br.com.relato.util.SqlHelper;
import java.util.*;

import system.UriUtil;

import br.com.relato.util.Cast;
import br.com.relato.util.DbAccess;
import br.com.relato.util.StringUtils;

public class GridGerenciadorConteudo {
	
		String query = "";
		public int qtdReg = 0;
		public String pagina = "";
		public String campo2 = "";
		public String operacao2 = "";
		public String valor2 = "";
		public String lingua2 = "";
		
		public GridGerenciadorConteudo(){
			
		}
		
		
		public List pesquisaConteudo(String campo, String operacao, String valor, String lingua, String paginaAtual, int NUM_REG_PAG){ 
			System.out.println("\n campo ==="+campo+"\n operacao="+operacao+"\n valor="+valor+"\n lingua="+lingua);
			Map<String, List<List>> mapaPaginado = new LinkedHashMap<String, List<List>>();
			 query =" select " +
					" conteudo.Id, " +
					" conteudo.Titulo, " +
					" conteudo.tipo, " +
					" criacao.cdspadrao, " +
					" conteudo.datahoracriacao, " +
					" alteracao.cdspadrao, " +
					" conteudo.datahoraultalt, " +
					" conteudo.replicacao, " +
					" conteudo.ativo, " +
					" conteudo.Url " +
					" from conteudo " +
					" left OUTER JOIN usuario as criacao on criacao.idiusuario = conteudo.usuariocriacao " +
					" left OUTER JOIN usuario as alteracao on alteracao.idiusuario = conteudo.usuarioultalt " +
					" where 1=1 ";
			 
			
			 if(campo.equals("id")){
				 if(!operacao.equals("") && !valor.equals("")){
					 if(operacao.equals("1")){
						 query += " and conteudo.Id ='"+valor+"'";
					 }else if(operacao.equals("2")){
						 query += " and conteudo.Id <'"+valor+"'";
					 }else if(operacao.equals("3")){
						 query += " and conteudo.Id <='"+valor+"'";
					 }else if(operacao.equals("4")){
						 query += " and conteudo.Id >'"+valor+"'";
					 }else if(operacao.equals("5")){
						 query += " and conteudo.Id >='"+valor+"'";
					 }else if(operacao.equals("6")){
						 query += " and conteudo.Id <>'"+valor+"'";
					 }else if(operacao.equals("7")){
						 query += " and conteudo.Id LIKE '%"+valor+"%'";
					 }else if(operacao.equals("8")){
						 query += " and conteudo.Id not LIKE '%"+valor+"%'";
					 }else if(operacao.equals("9")){
						 query += " and conteudo.Id  LIKE '"+valor+"%'";
					 }else if(operacao.equals("10")){
						 query += " and conteudo.Id  LIKE '%"+valor+"'";
					 }
					 
				 }else  if(!valor.equals("")){
					 query += " and conteudo.Id ='"+valor+"'"; 
				 }
			 }
			 if(campo.equals("titulo")){
				 if(!operacao.equals("") && !valor.equals("")){
					 if(operacao.equals("1")){
						 query += " and conteudo.Titulo ='"+valor+"'";
					 }else if(operacao.equals("6")){
						 query += " and conteudo.Titulo <>'"+valor+"'";
					 }else if(operacao.equals("7")){
						 query += " and conteudo.Titulo LIKE '%"+valor+"%'";
					 }else if(operacao.equals("8")){
						 query += " and conteudo.Titulo not LIKE '%"+valor+"%'";
					 }else if(operacao.equals("9")){
						 query += " and conteudo.Titulo  LIKE '"+valor+"%'";
					 }else if(operacao.equals("10")){
						 query += " and conteudo.Titulo  LIKE '%"+valor+"'";
					 }
					 
				 }else  if(!valor.equals("")){
					 query += " and conteudo.Titulo ="+valor; 
				 }
			 }
			 if(campo.equals("texto")){
				// valor = StringUtils.toISO(valor);
				 System.out.println("valo === "+valor);
				 if(!operacao.equals("") && !valor.equals("")){
					 if(operacao.equals("1")){
						 query += " and conteudo.Texto ='"+valor+"'";
					 }else if(operacao.equals("6")){
						 query += " and conteudo.Texto <>'"+valor+"'";
					 }else if(operacao.equals("7")){
						 query += " and conteudo.Texto LIKE '%"+valor+"%'";
					 }else if(operacao.equals("8")){ 
						 query += " and conteudo.Texto not LIKE '%"+valor+"%'";
					 }else if(operacao.equals("9")){
						 query += " and conteudo.Texto  LIKE '"+valor+"%'";
					 }else if(operacao.equals("10")){
						 query += " and conteudo.Texto  LIKE '%"+valor+"'";
					 }
					 
				 }else  if(!valor.equals("")){
					 query += " and conteudo.Texto LIKE '%"+valor+"%'"; 
				 }
			 }
			 if(campo.equals("texto")){
				 if(!valor.equals("conteudo")){
					 query += " and conteudo.tipo = 'C'"; 
				 }else if(!valor.equals("noticia")){
					 query += " and conteudo.tipo = 'N'"; 
				 }else if(!valor.equals("agenda")){
					 query += " and conteudo.tipo = 'A'"; 
				 }
			 }
			 if(campo.equals("cdspadrao")){
				 if(!operacao.equals("") && !valor.equals("")){
					 if(operacao.equals("1")){
						 query += " and criacao.cdspadrao ='"+valor+"'";
					 }else if(operacao.equals("6")){
						 query += " and criacao.cdspadrao <>'"+valor+"'";
					 }else if(operacao.equals("7")){
						 query += " and criacao.cdspadrao LIKE '%"+valor+"%'";
					 }else if(operacao.equals("8")){
						 query += " and criacao.cdspadrao not LIKE '%"+valor+"%'";
					 }else if(operacao.equals("9")){
						 query += " and criacao.cdspadrao  LIKE '"+valor+"%'";
					 }else if(operacao.equals("10")){
						 query += " and criacao.cdspadrao  LIKE '%"+valor+"'";
					 }
					 
				 }else  if(!valor.equals("")){
					 query += " and criacao.cdspadrao in ('"+valor+"')"; 
				 }
			 }
			 if(campo.equals("datahoracriacao")){
				 if(!operacao.equals("") && !valor.equals("")){
					 if(operacao.equals("1")){
						 query += " and conteudo.datahoracriacao ='"+valor+"'";
					 }else if(operacao.equals("2")){
						 query += " and conteudo.datahoracriacao <'"+valor+"'";
					 }else if(operacao.equals("3")){
						 query += " and conteudo.datahoracriacao <='"+valor+"'";
					 }else if(operacao.equals("4")){
						 query += " and conteudo.datahoracriacao >'"+valor+"'";
					 }else if(operacao.equals("5")){
						 query += " and conteudo.datahoracriacao >='"+valor+"'";
					 }else if(operacao.equals("6")){
						 query += " and conteudo.datahoracriacao <>'"+valor+"'";
					 }else if(operacao.equals("7")){
						 query += " and conteudo.datahoracriacao LIKE '%"+valor+"%'";
					 }else if(operacao.equals("8")){
						 query += " and conteudo.datahoracriacao not LIKE '%"+valor+"%'";
					 }else if(operacao.equals("9")){
						 query += " and conteudo.datahoracriacao  LIKE '"+valor+"%'";
					 }else if(operacao.equals("10")){
						 query += " and conteudo.datahoracriacao  LIKE '%"+valor+"'";
					 }
					 
				 }else  if(!valor.equals("")){
					 query += " and conteudo.datahoracriacao ='"+valor+"'"; 
				 }
			 }
			 
			 if(campo.equals("alteracao.cdspadrao")){
				 if(!operacao.equals("") && !valor.equals("")){
					 if(operacao.equals("1")){
						 query += " and alteracao.cdspadrao ='"+valor+"'";
					 }else if(operacao.equals("6")){
						 query += " and alteracao.cdspadrao <>'"+valor+"'";
					 }else if(operacao.equals("7")){
						 query += " and alteracao.cdspadrao LIKE '%"+valor+"%'";
					 }else if(operacao.equals("8")){
						 query += " and alteracao.cdspadrao not LIKE '%"+valor+"%'";
					 }else if(operacao.equals("9")){
						 query += " and alteracao.cdspadrao  LIKE '"+valor+"%'";
					 }else if(operacao.equals("10")){
						 query += " and alteracao.cdspadrao  LIKE '%"+valor+"'";
					 }
					 
				 }else  if(!valor.equals("")){
					 query += " and alteracao.cdspadrao ='"+valor+"'"; 
				 }
			 }
			 
			 if(campo.equals("datahoraultalt")){
				 if(!operacao.equals("") && !valor.equals("")){
					 if(operacao.equals("1")){
						 query += " and conteudo.datahoraultalt ='"+valor+"'";
					 }else if(operacao.equals("2")){
						 query += " and conteudo.datahoraultalt <'"+valor+"'";
					 }else if(operacao.equals("3")){
						 query += " and conteudo.datahoraultalt <='"+valor+"'";
					 }else if(operacao.equals("4")){
						 query += " and conteudo.datahoraultalt >'"+valor+"'";
					 }else if(operacao.equals("5")){
						 query += " and conteudo.datahoraultalt >='"+valor+"'";
					 }else if(operacao.equals("6")){
						 query += " and conteudo.datahoraultalt <>'"+valor+"'";
					 }else if(operacao.equals("7")){
						 query += " and conteudo.datahoraultalt LIKE '%"+valor+"%'";
					 }else if(operacao.equals("8")){
						 query += " and conteudo.datahoraultalt not LIKE '%"+valor+"%'";
					 }else if(operacao.equals("9")){
						 query += " and conteudo.datahoraultalt  LIKE '"+valor+"%'";
					 }else if(operacao.equals("10")){
						 query += " and conteudo.datahoraultalt  LIKE '%"+valor+"'";
					 }
					 
				 }else  if(!valor.equals("")){
					 query += " and conteudo.datahoraultalt ='"+valor+"'";  
				 }
			 }
			 
			 query +=	"  ORDER BY 1,2,3 ";
			
			 
			System.out.println("\nquery ==="+query);
			
			if("".equals( paginaAtual ) ){
				paginaAtual = "1";
			}

			try{
				List<List> listaConteudos = SqlHelper.execute(query, null);
				qtdReg = 0;
				System.out.println("\n\n\n 03 ="+listaConteudos);
				
				if(!listaConteudos.isEmpty()){
					qtdReg = listaConteudos.size();
					pagina = paginaAtual;
					//armazena a pesquisa atual
					campo2 = campo;
					operacao2 = operacao;
					valor2 = valor;
					lingua2 = lingua; 
					//fim
					Iterator<List> itConteudos = listaConteudos.iterator();
					int contadorreg = 1;
					int contadorpag = 1;
					List<List> listaPaginada = new ArrayList<List>();
					while(itConteudos.hasNext()){
						listaPaginada.add(itConteudos.next());
						contadorreg++;
						if(contadorreg > NUM_REG_PAG){
							contadorreg = 1;
							mapaPaginado.put(Cast.toString(contadorpag), listaPaginada);
							listaPaginada = new ArrayList<List>();
							contadorpag++;
						}
					}
					mapaPaginado.put(Cast.toString(contadorpag), listaPaginada);
				}
				return mapaPaginado.containsKey(paginaAtual)?mapaPaginado.get(paginaAtual):new ArrayList<List>();
			}catch(SQLException e){
				return new ArrayList<List>();
				//e.printStackTrace();
			}
		}
		
		
		public static void main(String[] args) {

		}


}
