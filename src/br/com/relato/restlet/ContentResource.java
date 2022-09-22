package br.com.relato.restlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.data.ClientInfo;
import org.restlet.data.MediaType;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import system.ContentManager;
import system.GetMenuJquery;
import system.MenuConstructor;
import system.UriUtil;
import br.com.relato.ConstantsApp;
import br.com.relato.portal.ControlPortal;
import br.com.relato.portal.Novidade;
import br.com.relato.util.Cast;
import br.com.relato.util.SqlHelper;
import br.com.relato.util.StringUtils;

public class ContentResource extends ServerResource {

	int NUMERO_MAX_DESTAQUE = 20;
	
	private String checkNull(Map mapa, Object valor, int lingua){
		String retorno = "";
		List key = new ArrayList();
		key.add(valor);
		key.add(new Integer(lingua));
		if(mapa.containsKey(key)){
			retorno = String.valueOf(mapa.get(key));
		}else{
			key = new ArrayList();
			key.add(valor);
			key.add(new Integer(1));
			if(mapa.containsKey(key)){
				retorno = String.valueOf(mapa.get(key));
			}
		}
		return retorno;
	}

	@Get("html")
	public String getHtmlVelocity() {
		String retorno = "";
		org.restlet.Request req = this.getRequest();
		Map attributes = req.getAttributes();
	    ClientInfo ci = this.getClientInfo();
	    Application a = this.getApplication();
	    Context cont = this.getContext();
	    
	    
	    //Pega a url digitada após /site/
	    String findEvento = (req.getResourceRef().getRemainingPart().trim());
	    MenuConstructor mc = new MenuConstructor();
	    
	    System.out.println("attributes::\n"+attributes);
	    
	    System.out.println("cont.getAttributes():"+cont.getAttributes());
	   // Map<String, Object> linguaAtributo = null!=cont.getAttributes()?cont.getAttributes():new HashMap<String,Object>();
	    //req.setAttributes(attributes);
	    int idLingua = (null!=attributes.get("lingua")? Integer.parseInt(String.valueOf(attributes.get("lingua"))) : 1);
	    if(findEvento.contains("?lingua")){
	    	String lin = findEvento.substring(findEvento.indexOf("?lingua=")+8);
	    	idLingua = Cast.toInt(lin);
	    	System.out.println("entrou no if !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    	attributes.put("lingua", lin);
	    	findEvento = findEvento.substring(0,findEvento.indexOf("?lingua="));
	    }
	    System.out.println("Lingua :-----===========------==========---------========--------"+idLingua);
	    String menuSite = GetMenuJquery.renderVisualizacao(mc, true ,idLingua);
	    //cont.setAttributes(linguaAtributo);
	    
	    req.setAttributes(attributes);
	    
	    Map<String, Object> map = new TreeMap<String, Object>();
		map.put("MENU", menuSite);
		String  Noticias= Novidade.getNovidade(true,idLingua);
		map.put("NOTICIAS",Noticias);
		ContentManager cm = new ContentManager(); 
		String Artigos = cm.getHtmlMarqueeArtigos(idLingua);
		map.put("ARTIGOS",Artigos);
		map.put("TESTE", new String("Kanaflex") );
		Map mapaPortal = ControlPortal.getPortalLingua();
		//System.out.println("Mapa portal \n\n\n"+mapaPortal);
		System.out.println("findEvento == '"+findEvento+"'");
		for(int x=1;x<=NUMERO_MAX_DESTAQUE;x++){
			String titulo = checkNull(mapaPortal,"titulo"+x,idLingua);
			map.put("titulo"+x, titulo);
			String foto = checkNull(mapaPortal,"foto"+x,idLingua);
			map.put("foto"+x, foto);
			String link = checkNull(mapaPortal,"link"+x,idLingua);
			map.put("link"+x, link);
			String texto = checkNull(mapaPortal,"texto"+x,idLingua);
			map.put("texto"+x, texto);
		}

	    //INDEX
	    if(findEvento.equals("/") || findEvento.equals("")){
			try {
				//System.out.println("Mapa velocity \n\n\n"+map);
				TemplateRepresentation tr = new TemplateRepresentation("index.vm", map, MediaType.TEXT_HTML);
				map.put("IMPORTS_PORTAL", "<style type=\"text/css\">.portal_destaque{display:none;}</style>");
				tr.getEngine().setProperty("file.resource.loader.path",br.com.relato.ConstantsApp.getParametro("velocity.restlet"));
				retorno = tr.getText();
			} catch (IOException e) {
				System.out.println("ERRO XXXX AQUI...\n");
				e.printStackTrace();
			}
	    
	    }else if(findEvento.contains("/editor/")){

	    	//R2 - Veja esta parte aqui do IF - Trata do editor...
	    	findEvento = findEvento.substring(findEvento.indexOf("/editor/")+8,findEvento.length());
	    	System.out.println("FindEvento: '"+findEvento+"'");
	    	
	    	findEvento = findEvento.replaceAll("/", "");
	    	cm = new ContentManager();
	    	int idConteudo = -1;
	    	idConteudo = cm.getConteudoUrl(findEvento);
	    	//Creio que aqui possamos colocar valores padrão para novas inclusões...
	    	String conteudo = "Comece a desenvolver sua página clicando aqui !";
	    	String descricao = "";
	    	String keywords = "";
	    	String title = "";
	    	try {
				if(idConteudo != -1){
					conteudo = UriUtil.decodeUri(cm.getContent(idConteudo, idLingua));
					descricao = UriUtil.decodeUri(cm.getDescricao(idConteudo, idLingua));
					keywords = UriUtil.decodeUri(cm.getKeywords(idConteudo, idLingua));
					title = UriUtil.decodeUri(cm.getTitulo(idConteudo, idLingua));
				}
				map.put("CONTEUDO", conteudo);
				map.put("DESCRICAO", descricao);
				map.put("KEYWORDS", keywords);
				map.put("TITLE", title);
				String image_list = "";
				String imagens_dir_path = ConstantsApp.getParametro("gerenciadorconteudos_imgpath");
				File imagesFolder = new File(imagens_dir_path);
				List col = (List)FileUtils.listFiles(imagesFolder, new String[] {"bmp", "jpg", "jpeg", "gif", "png","BMP","JPG","JPEG","GIF","PNG"}, true);
				Collections.sort(col);
				for (Iterator it = col.iterator(); it.hasNext();) {
					File file = (File) it.next();
					
					if ( it.hasNext() )
						image_list += "{title:\""+ file.getName() + "\", value:\"/images/"+file.getName()+"\"},";
				    else
				    	image_list += "{title:\""+ file.getName() + "\", value:\"/images/"+file.getName()+"\"},";
				}
				map.put("IMAGE_LIST", image_list);
				
				TemplateRepresentation tr = new TemplateRepresentation("tinymce.vm", map, MediaType.TEXT_HTML);
				tr.getEngine().setProperty("file.resource.loader.path",br.com.relato.ConstantsApp.getParametro("velocity.restlet"));
				retorno = tr.getText();
			} catch (IOException e) {
				System.out.println("ERRO YYYY AQUI...\n");
				e.printStackTrace();
			}

	    	
	    }else if(findEvento.contains("/__editar")){
			try {
				//System.out.println("Mapa velocity \n\n\n"+map);
				TemplateRepresentation tr = new TemplateRepresentation("index.vm", map, MediaType.TEXT_HTML);
				map.put("IMPORTS_PORTAL", "<link rel=\"stylesheet\" href=\"/portal/indexeditar.css\" type=\"text/css\"/><script type=\"text/javascript\" src=\"/portal/indexeditar.js\"></script>");
				tr.getEngine().setProperty("file.resource.loader.path",br.com.relato.ConstantsApp.getParametro("velocity.restlet"));
				retorno = tr.getText();
			} catch (IOException e) {
				System.out.println("ERRO XXXX AQUI...\n");
				e.printStackTrace();
			}
	    
	    }else if(findEvento.contains("/template-")){
	    	System.out.println("TEMPLATE == "+findEvento.lastIndexOf("/template-")+" --- "+findEvento.length());
	    	if(findEvento.lastIndexOf("/template-")+10<findEvento.length()){
	    		String parte = findEvento.substring(findEvento.lastIndexOf("/template-")+10,findEvento.length());
	    		String idtemplate = "";
	    		String conteudotemplate = "";
	    		System.out.println("parte == "+parte);
	    		if(parte.contains("/")){
	    			idtemplate = parte.substring(0,parte.indexOf("/"));
	    			String parteconteudo = parte.substring(parte.indexOf("/"),parte.length());
	    			System.out.println("idtemplate == "+idtemplate);
	    			System.out.println("parteconteudo == "+parteconteudo);
	    			if(parteconteudo.contains("/")){
	    				conteudotemplate = parteconteudo.replaceAll("/", "");
	    			}
	    			System.out.println("conteudotemplate == "+conteudotemplate);
	    			if(!"".equals(idtemplate)){
	    				if("".equals(conteudotemplate)){
	    					try {
	    						//System.out.println("Mapa velocity \n\n\n"+map);
	    						TemplateRepresentation tr = new TemplateRepresentation("/template"+idtemplate+"/index.vm", map, MediaType.TEXT_HTML);
	    						tr.getEngine().setProperty("file.resource.loader.path",br.com.relato.ConstantsApp.getParametro("velocity.restlet"));
	    						retorno = tr.getText();
	    					} catch (IOException e) {
	    						System.out.println("ERRO XXXX AQUI...\n");
	    						e.printStackTrace();
	    					}
	    				}else{
//	    			    	ContentManager cm = new ContentManager();
	    			    	int idConteudo = -1;
	    			    	idConteudo = cm.getConteudoUrl(conteudotemplate);
	    			    	String conteudo = "";
	    			    	String descricao = "";
	    			    	String keywords = "";
	    			    	String title = "";
	    			    	try {
	    						if(idConteudo != -1){
	    							conteudo = UriUtil.decodeUri(cm.getContent(idConteudo, idLingua));
	    							descricao = UriUtil.decodeUri(cm.getDescricao(idConteudo, idLingua));
	    							keywords = UriUtil.decodeUri(cm.getKeywords(idConteudo, idLingua));
	    							title = UriUtil.decodeUri(cm.getTitulo(idConteudo, idLingua));
	    						}
	    						map.put("CONTEUDO", conteudo);
	    						map.put("DESCRICAO", descricao);
	    						map.put("KEYWORDS", keywords);
	    						map.put("TITLE", title);
	    						TemplateRepresentation tr = new TemplateRepresentation("/template"+idtemplate+"/content.vm", map, MediaType.TEXT_HTML);
	    						tr.getEngine().setProperty("file.resource.loader.path",br.com.relato.ConstantsApp.getParametro("velocity.restlet"));
	    						retorno = tr.getText();
	    					} catch (IOException e) {
	    						System.out.println("ERRO YYYY AQUI...\n");
	    						e.printStackTrace();
	    					}

	    				}
	    			}
	    			
	    		}
	    		if(!"".equals(idtemplate)){
	    			
	    		}
	    	}
	    }else if(findEvento.contains("/__pesquisa")){
	    	//CARREGA_CONTEUDO_SITE
	    	System.out.println("PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA PESQUISA ");
	    	String pesquisar = "";
	    	
	    	//R2 - RAFAEL LONTRA favor modificar aqui para o visual da pesquisa ser o ideal... 
	    	String NOT_FOUND = 	"<table border='0' cellspacing='0' cellpadding='0' align='center'>"+
				    			"<tr height='10'> "+
				    			"<td valign='middle'><span style='font-size: 14px;text-align:center;margin:auto;'>" +
				    			"Nenhuma página disponível para pesquisa realizada.</span>" +
				    			"</td>"+
				    			"</tr>"+
				    			"<tr height='10'>"+
				    			"<td valign='middle'>&nbsp;</td>"+
				    			"</tr>"+
				    			"</table>";

	    	String REG_FIND = 	"<table border='0' cellspacing='0' cellpadding='0' align='center' style='margin:12px;'>"+
				    			"<tr height='10'> "+
				    			"<td valign='middle'><span style='font-size: 12px;'>" +
				    			"<strong> __COUNT - __TITLE </strong></span>" +
				    			"</td>"+
				    			"</tr>"+
				    			"<tr height='40'> "+
				    			"<td valign='middle' align='justify'><span style='font-family: verdana; font-size: 10px;'>"+
				    			"<a href='__LINK'>Acessar página.</span></a>"+
				    			"</td>"+
				    			"</tr>"+
				    			"<tr height='10'>"+
				    			"<td valign='middle'>&nbsp;</td>"+
				    			"</tr>"+
				    			"</table>";
	    	String conteudofind = "";
	    	if(findEvento.contains("__param=") && findEvento.contains("&go")){
	    		pesquisar = StringUtils.toUTF8(UriUtil.decodeUri(findEvento.substring(findEvento.indexOf("__param=")+8,findEvento.indexOf("&go")))).replaceAll("\\+", "%");
	    		//System.out.println("pesquisar iso :: '"+StringUtils.toISO(pesquisar)+"'");
	    		//System.out.println("pesquisar utf :: '"+StringUtils.toUTF8(pesquisar)+"'");
	    		String pesquisa = 	" select url, id, titulo from conteudo " +
	    							" WHERE (upper(titulo) like  '%" + UriUtil.encodeUri(pesquisar.trim().toUpperCase()) + "%')" +
	    							" or (upper(texto) like '%" + UriUtil.encodeUri(pesquisar.trim().toUpperCase()) + "%') " +
	    							" order by titulo ";
	    		try{
	    			//System.out.println("Qeury == "+pesquisa);
	    			List<List> listPesquisa = SqlHelper.execute(pesquisa,null);
	    			Iterator<List> itPesquisa = listPesquisa.iterator();
	    			int count = 0;
	    			while(itPesquisa.hasNext()){
	    				count++;
	    				List linhaPesquisa = itPesquisa.next();
	    				String urlPesquisa = null!= linhaPesquisa.get(0)?linhaPesquisa.get(0).toString().trim():"";
	    				String idPesquisa = null!= linhaPesquisa.get(0)?linhaPesquisa.get(0).toString().trim():"";
	    				String tituloPesquisa = null!= linhaPesquisa.get(0)?linhaPesquisa.get(0).toString().trim():"";
	    				//System.out.println("urlPesquisa == '"+urlPesquisa+"' -- idPesquisa == '"+idPesquisa+"' -- tituloPesquisa == '"+tituloPesquisa+"'");
	    				String registro = REG_FIND.replaceAll("__COUNT", String.valueOf(count)).replaceAll("__TITLE", tituloPesquisa).replaceAll("__LINK", (!"".equals(urlPesquisa)?"/site/"+urlPesquisa:"/index.jsp?id="+idPesquisa));
	    				//System.out.println("registro == '"+registro);
	    				conteudofind = conteudofind+registro;
	    			}
	    			if(count==0){
	    				conteudofind = NOT_FOUND;
	    			}
	    		}catch(SQLException s){
	    			s.printStackTrace();
	    		}
	    	}
	    	
	    	try {
				map.put("CONTEUDO", conteudofind);
				map.put("DESCRICAO", "");
				map.put("KEYWORDS", "");
				map.put("TITLE", "Pesquisa de Conteúdo");
				TemplateRepresentation tr = new TemplateRepresentation("content.vm", map, MediaType.TEXT_HTML);
				tr.getEngine().setProperty("file.resource.loader.path",br.com.relato.ConstantsApp.getParametro("velocity.restlet"));
				retorno = tr.getText();
			} catch (IOException e) {
				System.out.println("ERRO YYYY AQUI...\n");
				e.printStackTrace();
			}
	    	
	    }else{
	    	//CARREGA_CONTEUDO_SITE
	    	findEvento = findEvento.replaceAll("/", "");
//	    	ContentManager cm = new ContentManager();
	    	int idConteudo = -1;
	    	idConteudo = cm.getConteudoUrl(findEvento);
	    	String conteudo = "";
	    	String descricao = "";
	    	String keywords = "";
	    	String title = "";
	    	try {
				if(idConteudo != -1){
					conteudo = UriUtil.decodeUri(cm.getContent(idConteudo, idLingua));
					descricao = UriUtil.decodeUri(cm.getDescricao(idConteudo, idLingua));
					keywords = UriUtil.decodeUri(cm.getKeywords(idConteudo, idLingua));
					title = UriUtil.decodeUri(cm.getTitulo(idConteudo, idLingua));
				}
				map.put("CONTEUDO", conteudo);
				map.put("DESCRICAO", descricao);
				map.put("KEYWORDS", keywords);
				map.put("TITLE", title);
				TemplateRepresentation tr = new TemplateRepresentation("content.vm", map, MediaType.TEXT_HTML);
				tr.getEngine().setProperty("file.resource.loader.path",br.com.relato.ConstantsApp.getParametro("velocity.restlet"));
				retorno = tr.getText();
			} catch (IOException e) {
				System.out.println("ERRO YYYY AQUI...\n");
				e.printStackTrace();
			}
	    }

	     
		return retorno;
	}
	/*
	@Post("form:xml|json") 
	public Representation post(Form form) { 
	   //additional data will be in the form 
	} 
	*/
	@Post
	public String getHtmlVelocityPost() {
		System.out.println("ESTOU NO POST...");
		String retorno = "";
		org.restlet.Request req = this.getRequest();
		Map attributes = req.getAttributes();
	    ClientInfo ci = this.getClientInfo();
	    Application a = this.getApplication();
	    Context cont = this.getContext();
	    //Pega a url digitada após /site/
	    String findEvento = (req.getResourceRef().getRemainingPart().trim());
	    MenuConstructor mc = new MenuConstructor();
	    
	    System.out.println("attributes::\n"+attributes);
	    
	    System.out.println("cont.getAttributes():"+cont.getAttributes());
	    
	    int idLingua = (attributes.get("lingua") != null ? Integer.parseInt(String.valueOf(attributes.get("lingua"))) : 1);
	    String menuSite = GetMenuJquery.renderVisualizacao(mc, true ,idLingua);

	    Map<String, Object> map = new TreeMap<String, Object>();
		map.put("MENU", menuSite);
		Map mapaPortal = ControlPortal.getPortalLingua();
		//System.out.println("Mapa portal \n\n\n"+mapaPortal);
		System.out.println("findEvento == '"+findEvento+"'");
		for(int x=1;x<=NUMERO_MAX_DESTAQUE;x++){
			String titulo = checkNull(mapaPortal,"titulo"+x,idLingua);
			map.put("titulo"+x, titulo);
			String foto = checkNull(mapaPortal,"foto"+x,idLingua);
			map.put("foto"+x, foto);
			String link = checkNull(mapaPortal,"link"+x,idLingua);
			map.put("link"+x, link);
			String texto = checkNull(mapaPortal,"texto"+x,idLingua);
			map.put("texto"+x, texto);
		}

		if(findEvento.contains("/__pesquisa")){
	    	//CARREGA_CONTEUDO_SITE
	    	//System.out.println("Estou aqui...");
	    	String pesquisar = "";
	    	String NOT_FOUND = 	"<table border='0' cellspacing='0' cellpadding='0' align='center'>"+
				    			"<tr height='10'> "+
				    			"<td valign='middle'><span style='font-family: verdana; font-size: 11px;'>" +
				    			"<strong>Nenhuma página disponível para pesquisa realizada.</strong></span>" +
				    			"</td>"+
				    			"</tr>"+
				    			"<tr height='10'>"+
				    			"<td valign='middle'>&nbsp;</td>"+
				    			"</tr>"+
				    			"</table>";

	    	String REG_FIND = 	"<table border='0' cellspacing='0' cellpadding='0' align='center'>"+
				    			"<tr height='10'> "+
				    			"<td valign='middle'><span style='font-family: verdana; font-size: 11px;'>" +
				    			"<strong> __COUNT - __TITLE </strong></span>" +
				    			"</td>"+
				    			"</tr>"+
				    			"<tr height='40'> "+
				    			"<td valign='middle' align='justify'><span style='font-family: verdana; font-size: 10px;'>"+
				    			"<a href='__LINK'>Acessar página.</span></a>"+
				    			"</td>"+
				    			"</tr>"+
				    			"<tr height='10'>"+
				    			"<td valign='middle'>&nbsp;</td>"+
				    			"</tr>"+
				    			"</table>";
	    	String conteudofind = "";
	    	if(findEvento.contains("__param=") && findEvento.contains("&go")){
	    		pesquisar = StringUtils.toUTF8(UriUtil.decodeUri(findEvento.substring(findEvento.indexOf("__param=")+8,findEvento.indexOf("&go")))).replaceAll("\\+", "%");
	    		//System.out.println("pesquisar iso :: '"+StringUtils.toISO(pesquisar)+"'");
	    		//System.out.println("pesquisar utf :: '"+StringUtils.toUTF8(pesquisar)+"'");
	    		String pesquisa = 	" select url, id, titulo from conteudo " +
	    							" WHERE (upper(titulo) like  '%" + UriUtil.encodeUri(pesquisar.trim().toUpperCase()) + "%')" +
	    							" or (upper(texto) like '%" + UriUtil.encodeUri(pesquisar.trim().toUpperCase()) + "%') " +
	    							" order by titulo ";
	    		try{
	    			//System.out.println("Qeury == "+pesquisa);
	    			List<List> listPesquisa = SqlHelper.execute(pesquisa,null);
	    			Iterator<List> itPesquisa = listPesquisa.iterator();
	    			int count = 0;
	    			while(itPesquisa.hasNext()){
	    				count++;
	    				List linhaPesquisa = itPesquisa.next();
	    				String urlPesquisa = null!= linhaPesquisa.get(0)?linhaPesquisa.get(0).toString().trim():"";
	    				String idPesquisa = null!= linhaPesquisa.get(0)?linhaPesquisa.get(0).toString().trim():"";
	    				String tituloPesquisa = null!= linhaPesquisa.get(0)?linhaPesquisa.get(0).toString().trim():"";
	    				//System.out.println("urlPesquisa == '"+urlPesquisa+"' -- idPesquisa == '"+idPesquisa+"' -- tituloPesquisa == '"+tituloPesquisa+"'");
	    				String registro = REG_FIND.replaceAll("__COUNT", String.valueOf(count)).replaceAll("__TITLE", tituloPesquisa).replaceAll("__LINK", (!"".equals(urlPesquisa)?"/site/"+urlPesquisa:"/index.jsp?id="+idPesquisa));
	    				//System.out.println("registro == '"+registro);
	    				conteudofind = conteudofind+registro;
	    			}
	    			if(count==0){
	    				conteudofind = NOT_FOUND;
	    			}
	    		}catch(SQLException s){
	    			s.printStackTrace();
	    		}
	    	}
	    	
	    	try {
				map.put("CONTEUDO", conteudofind);
				map.put("DESCRICAO", "");
				map.put("KEYWORDS", "");
				map.put("TITLE", "Pesquisa de Conteúdo");
				TemplateRepresentation tr = new TemplateRepresentation("content.vm", map, MediaType.TEXT_HTML);
				tr.getEngine().setProperty("file.resource.loader.path",br.com.relato.ConstantsApp.getParametro("velocity.restlet"));
				retorno = tr.getText();
			} catch (IOException e) {
				System.out.println("ERRO YYYY AQUI...\n");
				e.printStackTrace();
			}
	    	
	    }else{
	    	//CARREGA_CONTEUDO_SITE
	    	findEvento = findEvento.replaceAll("/", "");
	    	ContentManager cm = new ContentManager();
	    	int idConteudo = -1;
	    	idConteudo = cm.getConteudoUrl(findEvento);
	    	String conteudo = "";
	    	String descricao = "";
	    	String keywords = "";
	    	String title = "";
	    	try {
				if(idConteudo != -1){
					conteudo = UriUtil.decodeUri(cm.getContent(idConteudo, idLingua));
					descricao = UriUtil.decodeUri(cm.getDescricao(idConteudo, idLingua));
					keywords = UriUtil.decodeUri(cm.getKeywords(idConteudo, idLingua));
					title = UriUtil.decodeUri(cm.getTitulo(idConteudo, idLingua));
				}
				map.put("CONTEUDO", conteudo);
				map.put("DESCRICAO", descricao);
				map.put("KEYWORDS", keywords);
				map.put("TITLE", title);
				TemplateRepresentation tr = new TemplateRepresentation("content.vm", map, MediaType.TEXT_HTML);
				tr.getEngine().setProperty("file.resource.loader.path",br.com.relato.ConstantsApp.getParametro("velocity.restlet"));
				retorno = tr.getText();
			} catch (IOException e) {
				System.out.println("ERRO YYYY AQUI...\n");
				e.printStackTrace();
			}
	    }

	     
		return retorno;
	}

}
