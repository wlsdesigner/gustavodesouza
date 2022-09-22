/*
 * Created on 24/11/2004
 */
package system;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author 	Rafael Lima
 * @since	01/10/2010
 */
public class GetMenuJquery {
	
	public static String renderVisualizacao(MenuConstructor mc){
		return renderVisualizacao(mc, false);
	}	
		
	public static String renderVisualizacao(MenuConstructor mc, boolean portal){
		StringBuffer bf = new StringBuffer();
		StringBuffer content = new StringBuffer();
		String linhaMenu = "";
		
		int parentMenu = -1; 
		MenuConstructor.MenuItem mi;
		mc.setTableName("menu");
		ArrayList itens = mc.getItens();

		boolean aumenta = false;
		boolean dirty = false;
		for (int i=0; i<itens.size(); i++) {
			mi = (MenuConstructor.MenuItem) itens.get(i);
			
			if (parentMenu != mi.parentId) {

				//if ( aumenta )
				//	bf.append("itemwidth=__item").append("\n");
				if ( content.length() != 0 )
					bf.append(content.toString());
				
				if (parentMenu != -1) {
					if (parentMenu == 9999999)
						content.append("aI(\"text=&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;;\"").append(");").append("\n"); ;

					bf.append("}").append("\n"); 
					linhaMenu += bf.toString(); 
					bf.setLength(0);
				}
				
				parentMenu = mi.parentId;
				bf.append("with(milonic=new menuname('m").append(parentMenu).append("')){ ").append("\n");
				
				if (parentMenu == 0) {
		            bf.append("style=BigTransparent;").append("\n");
	    			//bf.append("top=__top;").append("\n");
	    			//bf.append("left=__left;").append("\n");
	    			//bf.append("itemwidth=__item").append("\n");
		        	bf.append("orientation=\"horizontal\";").append("\n");
		        	bf.append("position=\"relative\";").append("\n");
					bf.append("alwaysvisible=1;").append("\n");
					bf.append("screenposition=\"midle\";").append("\n");					
					//bf.append("overflow='scroll';").append("\n");
					bf.append("left=\"offset=0\";").append("\n");
					
				} else {
		            bf.append("style=BigTransparent;").append("\n");
					bf.append("overflow='scroll'").append("\n");		
				}
				aumenta = false;
				dirty = false;
				content.setLength(0);
			} 
			
			if ( mi.name.length() < 23 ){
				if ( !dirty )
					aumenta = true;
			}else{
				aumenta = false;
				dirty = true;
			}
			
			if ( portal ){
				content.append("aI(\"text=").append(mi.name).append(";");
				if (( mi.link.trim().length() > 0 ) && ( !mi.link.trim().equals("nolink"))) {
					if (mc.hasChildren(mi.menuId)) 
						content.append("showmenu=").append("m").append(mi.menuId);
					else 	
						content.append("url=").append(mi.link).append(";").append((mi.target.trim().length() > 0 ?  "target="+mi.target.trim()+";" :""));			
				} else {
					content.append("showmenu=").append("m").append(mi.menuId);
				}
	
			}else{
				content.append("aI(\"text=").append(mi.name).append(";");
				if (( mi.link.trim().length() > 0 ) && ( !mi.link.trim().equals("nolink"))) {
					if (mc.hasChildren(mi.menuId)) 
						content.append("showmenu=")
							.append("m")
							.append(mi.menuId);
				}else
					content.append("showmenu=").append("m").append(mi.menuId);
			}
			
			content.append("\");").append("\n");
		}
		
		if ( content.length() != 0 ){
			bf.append(content.toString());
			content.setLength(0);
		}
		
		if (bf.length() > 0) {
			bf.append("}").append("\n"); 
			linhaMenu += bf.toString(); 
			bf.setLength(0);
		}
		
		return linhaMenu;
	}

	public static String renderEdicao(MenuConstructor mc){
		StringBuffer sb = new StringBuffer();
		String urlManut = "germenu_form.jsp?acao=";	
		String menuName = "";	
		String linhaMenu = "";
		StringBuffer bf = new StringBuffer();
		int parentMenu = -1;  
		MenuConstructor.MenuItem mi;

		mc.setTableName("menu");	
		ArrayList itens = mc.getItensManut();
		for (int i=0; i<itens.size(); i++) {
			mi = (MenuConstructor.MenuItem) itens.get(i);
			
			if (parentMenu != mi.parentId) {
				if (parentMenu != -1) {
		 
					if (parentMenu == 9999999)
						bf.append("aI(\"text=&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;;\"").append(");").append("\n"); ;
		
					bf.append("}").append("\n"); 
					linhaMenu += bf.toString(); 
					bf.setLength(0);
				}
				
				parentMenu = mi.parentId;
				bf.append("with(milonic=new menuname('m").append(parentMenu).append("')){ ").append("\n");
				bf.append("itemwidth=\"159\"").append("\n");			
				if (parentMenu == 0) {
		            bf.append("style=BigTransparent;").append("\n");
			        bf.append("bgimage=\"bg_menu.jpg\"").append("\n");
					bf.append("left=30;").append("\n");
		    		bf.append("top=137;").append("\n");
		        	//bf.append("align='center';").append("\n");
					bf.append("alwaysvisible=1;").append("\n");
					bf.append("overflow='scroll';").append("\n");
				} else {
		            bf.append("style=BigTransparent;").append("\n");
			        bf.append("bgimage=\"bg_menu.jpg\"").append("\n");
		        	//bf.append("align='center';").append("\n");
					bf.append("overflow='scroll'").append("\n");		
				}
			
			} 
			menuName=mi.name;
			bf.append("aI(\"text=").append(menuName).append(";");

			if (( mi.link.trim().length() > 0 ) && ( !mi.link.trim().equals("nolink"))) {
			
				if (mc.hasChildrenManut(mi.menuId)) {
					bf.append("url=").append(urlManut);
			        if ( "_novo_item".equals(menuName))
			            bf.append("novo").append("&id=").append(mi.link).append(";");
			        else
			            bf.append("altera").append("&id=").append(mi.menuId).append(";");

					bf.append("showmenu=").append("m").append(mi.menuId);
				} else { 	
					bf.append("url=").append(urlManut);			
			        if ( "_novo_item".equals(menuName))
			            bf.append("novo").append("&id=").append(mi.link).append(";");
			        else
			            bf.append("altera").append("&id=").append(mi.menuId).append(";");
				}	
			} else {
				bf.append("showmenu=").append("m").append(mi.menuId);
			}

			bf.append("\");").append("\n");
		
		}
		if (bf.length() > 0) {
			bf.append("}").append("\n"); 
			linhaMenu += bf.toString();  
			bf.setLength(0);
		}
		
		return linhaMenu;
	}
	
	//SUPORTE MULTI-LINGUA RAFAEL LIMA
	
	public static String renderVisualizacao(MenuConstructor mc, int lingua){
		return renderVisualizacao(mc, false, lingua);
	}	
	
	public static String alimentaSubItem(MenuConstructor mc,ArrayList<MenuConstructor.MenuItem> subitens,int menuId){
		StringBuffer subitem = new StringBuffer();
		Iterator<MenuConstructor.MenuItem> itsubmenu = subitens.iterator();
		MenuConstructor.MenuItem mchild;
		int menuChild = -1;
		subitem.append("<div><ul>");
		while(itsubmenu.hasNext()){
			mchild = itsubmenu.next();
			menuChild = mchild.parentId;
			String nome = null!= mchild.name ? mchild.name.trim() : "-";
			String link = null!= mchild.link ? mchild.link.trim() : "#";
			String target = null!= mchild.target ? mchild.target.trim() : "";
			if(menuId == menuChild){
				subitem.append("<li>");
				if (mc.hasChildren(mchild.menuId)){
					subitem.append("<a href=\""+link+"\" ");
					if(!"".equals(target)){
						subitem.append(" target=\"_blank\"");
					}
					subitem.append(" class=\"parent\"><span>"+nome+"</span></a>\n");
					//Método que preenche sub-itens.
					subitem.append(alimentaSubItem(mc,subitens,mchild.menuId));
				}else{
					subitem.append("<a href=\""+link+"\" ");
					if(!"".equals(target)){
						subitem.append(" target=\"_blank\"");
					}
					subitem.append("><span>"+nome+"</span></a>\n");

				}
				subitem.append("</li>");
			}
		}
		subitem.append("</ul></div>");
		return subitem.toString();
	}
	
	//TESTE R2
	public static String alimentaSubItemr2(MenuConstructor mc,ArrayList<MenuConstructor.MenuItem> subitens,int menuId,int lingua){
		StringBuffer subitem = new StringBuffer();
		Iterator<MenuConstructor.MenuItem> itsubmenu = subitens.iterator();
		MenuConstructor.MenuItem mchild;
		int menuChild = -1;
		subitem.append("<div><ul>");
		while(itsubmenu.hasNext()){
			mchild = itsubmenu.next();
			menuChild = mchild.parentId;
			String nome = null!= mchild.name ? mchild.name.trim() : "-";
			String link = null!= mchild.link ? mchild.link.trim() : "#";
			String target = null!= mchild.target ? mchild.target.trim() : "";
			if(menuId == menuChild){
				subitem.append("<li>");
				if (mc.hasChildren(mchild.menuId)){
					subitem.append("<a href=\""+"/siteadmin/menu/germenu_visu.jsp?menuId="+mchild.menuId+"&lingua="+lingua+"\" target=_self ");
					if(!"".equals(target)){
						subitem.append(" target=\"_blank\"");
					}
					subitem.append(" class=\"parent\"><span>"+nome+"</span></a>\n");
					//Método que preenche sub-itens.
					subitem.append(alimentaSubItemr2(mc,subitens,mchild.menuId,lingua));
				}else{
					subitem.append("<a href=\""+"/siteadmin/menu/germenu_visu.jsp?menuId="+mchild.menuId+"&lingua="+lingua+"\" target=_self ");
					if(!"".equals(target)){
						subitem.append(" target=\"_blank\"");
					}
					subitem.append("><span>"+nome+"</span></a>\n");

				}
				subitem.append("</li>");
			}
		}
		subitem.append("</ul></div>");
		return subitem.toString();
	}
	
	//Nova versao JQuery
	public static String renderVisualizacao(MenuConstructor mc, boolean portal, int lingua){
		StringBuffer content = new StringBuffer();
		MenuConstructor.MenuItem mi;
		mc.setTableName("menu",lingua);
		ArrayList<MenuConstructor.MenuItem> itens = mc.getItens();
		Iterator<MenuConstructor.MenuItem> itmenu = itens.iterator();
		//ArrayList<MenuConstructor.MenuItem> subitens = mc.getItens();
		ArrayList<MenuConstructor.MenuItem> subitens = new ArrayList();
		//id
		//name
		//link
		//target
		//parentid
		//showorder
		
		if(itmenu.hasNext()){
			content.append("\n<div id=\"menu\">\n");
			content.append("<ul class=\"menu\">\n");
			while(itmenu.hasNext()){
				mi = itmenu.next();
				String nome = null!= mi.name ? mi.name.trim() : "-";
				String link = null!= mi.link ? mi.link.trim() : "#";
				String target = null!= mi.target ? mi.target.trim() : "";
				int ligacaoid = mi.parentId;
				//se a ligacaoid eh 0 = menus principais
				if(ligacaoid == 0){
					//Pesquisa se existe sub-menu para item atual.
					content.append("<li>");
					if (mc.hasChildren(mi.menuId)){
						content.append("<a href=\""+link+"\"");
						//Método que preenche sub-itens.
						if(!"".equals(target)){
							content.append(" target=\"_blank\"");
						}
						content.append(" class=\"parent\"><span>"+nome+"</span></a>\n");

						content.append(alimentaSubItem(mc,subitens,mi.menuId));
					}else{
						content.append("<a href=\""+link+"\"");
						if(!"".equals(target)){
							content.append(" target=\"_blank\"");
						}
						content.append("><span>"+nome+"</span></a>\n");
					}
					content.append("</li>");
				}
			}
			content.append("</ul>\n");			
			content.append("</div>\n");
		}
		//System.out.println("content = "+content.toString());
		return content.toString();
	}
	
	//TESTE R2
	public static String renderVisualizacaor2(MenuConstructor mc, boolean portal, int lingua){
		StringBuffer content = new StringBuffer();
		MenuConstructor.MenuItem mi;
		mc.setTableName("menu",lingua);
		ArrayList<MenuConstructor.MenuItem> itens = mc.getItens();
		Iterator<MenuConstructor.MenuItem> itmenu = itens.iterator();
		ArrayList<MenuConstructor.MenuItem> subitens = mc.getItens();
		//id
		//name
		//link
		//target
		//parentid
		//showorder
		
		if(itmenu.hasNext()){
			content.append("\n<div id=\"menu\">\n");
			content.append("<ul class=\"menu\">\n");
			while(itmenu.hasNext()){
				mi = itmenu.next();
				String nome = null!= mi.name ? mi.name.trim() : "-";
				String link = null!= mi.link ? mi.link.trim() : "#";
				String target = null!= mi.target ? mi.target.trim() : "";
				int ligacaoid = mi.parentId;
				//se a ligacaoid eh 0 = menus principais
				if(ligacaoid == 0){
					//Pesquisa se existe sub-menu para item atual.
					content.append("<li>");
					if (mc.hasChildren(mi.menuId)){
						content.append("<a href=\""+"/siteadmin/menu/germenu_visu.jsp?menuId="+mi.menuId+"&lingua="+lingua+"\" target=_self");
						//Método que preenche sub-itens.
						if(!"".equals(target)){
							content.append(" target=\"_blank\"");
						}
						content.append(" class=\"parent\"><span>"+nome+"</span></a>\n");

						content.append(alimentaSubItemr2(mc,subitens,mi.menuId,lingua));
					}else{
						content.append("<a href=\""+"/siteadmin/menu/germenu_visu.jsp?menuId="+mi.menuId+"&lingua="+lingua+"\" target=_self");
						if(!"".equals(target)){
							content.append(" target=\"_blank\"");
						}
						content.append("><span>"+nome+"</span></a>\n");
					}
					content.append("</li>");
				}
			}
			content.append("</ul>\n");			
			content.append("</div>\n");
		}
		//System.out.println("content = "+content.toString());
		return content.toString();
	}

	//Nova versao JQuery
	public static String renderVisualizacao2(MenuConstructor mc, boolean portal, int lingua){
		StringBuffer content = new StringBuffer();
		MenuConstructor.MenuItem mi;
		mc.setTableName("menu",lingua);
		ArrayList<MenuConstructor.MenuItem> itens = mc.getItens();
		Iterator<MenuConstructor.MenuItem> itmenu = itens.iterator();
		ArrayList<MenuConstructor.MenuItem> subitens = mc.getItens();
		//id
		//name
		//link
		//target
		//parentid
		//showorder
		
		if(itmenu.hasNext()){
			content.append("\n<div id=\"topnav\" >\n");
			content.append("<ul class=\"menu\">\n");
			while(itmenu.hasNext()){
				mi = itmenu.next();
				String nome = null!= mi.name ? mi.name.trim() : "-";
				String link = null!= mi.link ? mi.link.trim() : "#";
				String target = null!= mi.target ? mi.target.trim() : "";
				int ligacaoid = mi.parentId;
				//se a ligacaoid eh 0 = menus principais
				if(ligacaoid == 0){
					//Pesquisa se existe sub-menu para item atual.
					content.append("<li>");
					if (mc.hasChildren(mi.menuId)){
						content.append("<a href=\""+link+"\"");
						//Método que preenche sub-itens.
						if(!"".equals(target)){
							content.append(" target=\"_blank\"");
						}
						content.append(" class=\"parent\"><span>"+nome+"</span></a>\n");

						content.append(alimentaSubItem(mc,subitens,mi.menuId));
					}else{
						content.append("<a href=\""+link+"\"");
						if(!"".equals(target)){
							content.append(" target=\"_blank\"");
						}
						content.append("><span>"+nome+"</span></a>\n");
					}
					content.append("</li>");
				}
			}
			content.append("</ul>\n");			
			content.append("</div>\n");
		}
		
		return content.toString();
	}
	
	public static String renderVisualizacaoEditor(MenuConstructor mc, boolean portal, int lingua){
		StringBuffer bf = new StringBuffer();
		StringBuffer content = new StringBuffer();
		String linhaMenu = "";
		
		int parentMenu = -1;
		MenuConstructor.MenuItem mi;
		mc.setTableName("menu", lingua);
		ArrayList itens = mc.getItens();

		boolean aumenta = false;
		boolean dirty = false;
		for (int i=0; i<itens.size(); i++) {
			mi = (MenuConstructor.MenuItem) itens.get(i);
			if(1 == mi.language){
				if (parentMenu != mi.parentId) {
	
					if ( aumenta )
						bf.append("itemwidth=__item").append("\n");
					if ( content.length() != 0 )
						bf.append(content.toString());
					
					if (parentMenu != -1) {
						if (parentMenu == 9999999)
							content.append("aI(\"text=&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;;\"").append(");").append("\n"); ;
	
						bf.append("}").append("\n"); 
						linhaMenu += bf.toString(); 
						bf.setLength(0);
					}
					
					parentMenu = mi.parentId;
					bf.append("with(milonic=new menuname('m").append(parentMenu).append("')){ ").append("\n");
					
					if (parentMenu == 0) {
			            bf.append("style=BigTransparent;").append("\n");
		    			bf.append("top=__top;").append("\n");
		    			bf.append("left=__left;").append("\n");
		    			bf.append("itemwidth=__item").append("\n");
			        	//bf.append("align='center';").append("\n");
						bf.append("alwaysvisible=1;").append("\n");
						bf.append("overflow='scroll';").append("\n");
					} else {
			            bf.append("style=BigTransparent;").append("\n");
						bf.append("overflow='scroll'").append("\n");		
					}
					aumenta = false;
					dirty = false;
					content.setLength(0);
				} 
				
				if ( mi.name.length() < 23 ){
					if ( !dirty )
						aumenta = true;
				}else{
					aumenta = false;
					dirty = true;
				}
				
				if ( portal ){
					content.append("aI(\"text=").append(mi.name).append(";");
					if (( mi.link.trim().length() > 0 ) && ( !mi.link.trim().equals("nolink"))) {
						if (mc.hasChildren(mi.menuId)){ 
							content.append("showmenu=").append("m").append(mi.menuId);
						}else{ 	
							String link = mi.link.trim();
							link = link.replaceAll("<%=idLingua%>", String.valueOf(lingua));
							content.append("url=").append(link).append(";").append((mi.target.trim().length() > 0 ?  "target="+mi.target.trim()+";" :""));
						}
					} else {
						content.append("showmenu=").append("m").append(mi.menuId);
					}
		
				}else{
					content.append("aI(\"text=").append(mi.name).append(";");
					if (( mi.link.trim().length() > 0 ) && ( !mi.link.trim().equals("nolink"))) {
						if (mc.hasChildren(mi.menuId)) 
							content.append("showmenu=")
								.append("m")
								.append(mi.menuId);
					}else
						content.append("showmenu=").append("m").append(mi.menuId);
				}
				
				content.append("\");").append("\n");
			}
		}
		
		if ( content.length() != 0 ){
			bf.append(content.toString());
			content.setLength(0);
		}
		
		if (bf.length() > 0) {
			bf.append("}").append("\n"); 
			linhaMenu += bf.toString(); 
			bf.setLength(0);
		}
		System.out.println("linhamenu"+linhaMenu);
		return linhaMenu;
	}
	
	public static String renderEdicao(MenuConstructor mc, int lingua){
		//StringBuffer sb = new StringBuffer();
		String urlManut = "germenu_form.jsp?acao=";	
		String menuName = "";	
		//String linhaMenu = "";
		//StringBuffer bf = new StringBuffer();
		int parentMenu = -1;  
		//MenuConstructor.MenuItem mi;
		
		
		StringBuffer content = new StringBuffer();
		MenuConstructor.MenuItem mi;
		mc.setTableName("menu",lingua);
		//ArrayList itens = mc.getItensManut();
		//ArrayList<MenuConstructor.MenuItem> itens = mc.getItens();
		ArrayList<MenuConstructor.MenuItem> itens = mc.getItensManut();
		Iterator<MenuConstructor.MenuItem> itmenu = itens.iterator();
		for(Iterator it=itens.iterator();it.hasNext();){
			MenuConstructor.MenuItem dado_da_lista = (MenuConstructor.MenuItem)it.next();
			System.out.println("MenuItem name = "+dado_da_lista.name+" link = "+dado_da_lista.link+" target = "+dado_da_lista.target+" menuId = "+dado_da_lista.menuId+" parentId = "+dado_da_lista.parentId);
		}
		//ArrayList<MenuConstructor.MenuItem> subitens = mc.getItensManut();
		Iterator<MenuConstructor.MenuItem> subchildren = itens.iterator();
			
			/*
			
			if(subchildren.hasNext()){
				MenuConstructor.MenuItem dado_da_lista = (MenuConstructor.MenuItem)subchildren.next();
				while(subchildren.hasNext()){
					subchildren.next();
					if(dado_da_lista.parentId != 0){
						System.out.println("Sub Menu = "+dado_da_lista.name);
						
					}
				}
			}
			
			*/
		if(itmenu.hasNext()){
			content.append("\n<div id=\"menu\">\n");
			content.append("<ul class=\"menu\">\n");
			while(itmenu.hasNext()){
				mi = itmenu.next();
				String nome = null!= mi.name ? mi.name.trim() : "-";
				String link = null!= mi.link ? mi.link.trim() : "#";
				String target = null!= mi.target ? mi.target.trim() : "";
				int ligacaoid = mi.parentId;
				
				//se a ligacaoid eh 0 = menus principais
				if(ligacaoid == 0){
					//Pesquisa se existe sub-menu para item atual.
					content.append("<li>");
							//bf.append("url=").append(urlManut);
							//if ( "_novo_item".equals(menuName))
					if (mc.hasChildrenManut(mi.menuId)) {
							if ( "_novo_item".equals(nome)){
								content.append("<a href=\""+urlManut+"novo&id="+mi.menuId+"\"");
								content.append(" class=\"parent\"><span>"+nome+"</span></a>\n");}
							else{
								content.append("<a href=\""+urlManut+"altera&id="+mi.menuId+"\"");
								content.append(" class=\"parent\"><span>"+nome+"</span></a>\n");
							}
							System.out.println("entrou no submenu manut "+mi.menuId);
							content.append(alimentaSubItemManut(mc,itens,mi.menuId));
							
					}else{
						if ( "_novo_item".equals(nome)){
							content.append("<a href=\""+urlManut+"novo&id=0"+"\"");
							content.append(" class=\"parent\"><span>"+nome+"</span></a>\n");}
						else{
							content.append("<a href=\""+urlManut+"altera&id="+mi.menuId+"\"");
							content.append(" class=\"parent\"><span>"+nome+"</span></a>\n");
						}
					}
					content.append("</li>");
				}
			}
			content.append("</ul>\n");			
			content.append("</div>\n");
		}
		System.out.println("content = "+content.toString());
		return content.toString();
		
		
		
	 	/*
	 	
	 	StringBuffer sb = new StringBuffer();
		//String urlManut = "germenu_form.jsp?acao=";	
		//String menuName = "";	
		String linhaMenu = "";
		StringBuffer bf = new StringBuffer();
		//int parentMenu = -1;  
		MenuConstructor.MenuItem mi;
		   
		   
		   
		mc.setTableName("menu",lingua);	
		ArrayList itens = mc.getItensManut();
		for (int i=0; i<itens.size(); i++) {
			mi = (MenuConstructor.MenuItem) itens.get(i);
			
			if (parentMenu != mi.parentId) {
				if (parentMenu != -1) {
		 
					if (parentMenu == 9999999)
						bf.append("aI(\"text=&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;;\"").append(");").append("\n"); ;
		
					bf.append("}").append("\n"); 
					linhaMenu += bf.toString(); 
					bf.setLength(0);
				}
				
				parentMenu = mi.parentId;
				bf.append("with(milonic=new menuname('m").append(parentMenu).append("')){ ").append("\n");
				bf.append("itemwidth=\"159\"").append("\n");			
				if (parentMenu == 0) {
		            bf.append("style=BigTransparent;").append("\n");
			        bf.append("bgimage=\"bg_menu.jpg\"").append("\n");
					bf.append("left=30;").append("\n");
		    		bf.append("top=137;").append("\n");
		        	//bf.append("align='center';").append("\n");
					bf.append("alwaysvisible=1;").append("\n");
					bf.append("overflow='scroll';").append("\n");
				} else {
		            bf.append("style=BigTransparent;").append("\n");
			        bf.append("bgimage=\"bg_menu.jpg\"").append("\n");
		        	//bf.append("align='center';").append("\n");
					bf.append("overflow='scroll'").append("\n");		
				}
			
			} 
			menuName=mi.name;
			bf.append("aI(\"text=").append(menuName).append(";");
			System.out.println("MenuName = "+menuName);
		
			if (( mi.link.trim().length() > 0 ) && ( !mi.link.trim().equals("nolink"))) {
				System.out.println(" ENTROU NO IF mi.link.trim().length() = "+ mi.link.trim().length()+" "+mi.link.trim());
				if (mc.hasChildrenManut(mi.menuId)) {
					bf.append("url=").append(urlManut);
			        if ( "_novo_item".equals(menuName))
			            bf.append("novo").append("&id=").append(mi.link).append(";");
			        else
			            bf.append("altera").append("&id=").append(mi.menuId).append(";");

					bf.append("showmenu=").append("m").append(mi.menuId);
				} else { 	
					bf.append("url=").append(urlManut);			
			        if ( "_novo_item".equals(menuName))
			            bf.append("novo").append("&id=").append(mi.link).append(";");
			        else
			            bf.append("altera").append("&id=").append(mi.menuId).append(";");
				}	
			} else {
				bf.append("showmenu=").append("m").append(mi.menuId);
				System.out.println("ENTROU NO ELSE"+mi.menuId);
			}

			bf.append("\");").append("\n");
		
		}
		if (bf.length() > 0) {
			bf.append("}").append("\n"); 
			linhaMenu += bf.toString();  
			bf.setLength(0);
		}
		
		return linhaMenu;
		*/
	}
	
	public static String alimentaSubItemManut(MenuConstructor mc,ArrayList<MenuConstructor.MenuItem> subitens,int menuId){
		StringBuffer subitem = new StringBuffer();
		Iterator<MenuConstructor.MenuItem> itsubmenu = subitens.iterator();
		MenuConstructor.MenuItem mchild;
		int menuChild = -1;
		String urlManut = "germenu_form.jsp?acao=";	
		subitem.append("<div><ul>");
		System.out.println("entrou no submenu manut 2 "+menuId);
		MenuConstructor.MenuItem mi;
//		while(itsubmenu.hasNext()){
		for (int i=0; i<subitens.size(); i++) {
			mi = (MenuConstructor.MenuItem) subitens.get(i);
		
			mchild = mi;
			menuChild = mchild.parentId;
			System.out.println("menuChild "+mchild.menuId);
			String nome = null!= mchild.name ? mchild.name.trim() : "-";
			String link = null!= mchild.link ? mchild.link.trim() : "#";
			String target = null!= mchild.target ? mchild.target.trim() : "";
			System.out.println("menuId = "+menuId+" menuChild = "+menuChild);
			if(menuId == menuChild){
				subitem.append("<li>");
					if (mc.hasChildren(mchild.menuId)){
						if ( "_novo_item".equals(nome)){
						subitem.append("<a href=\""+urlManut+"novo&id="+mchild.parentId+"\" ");
						subitem.append(" class=\"parent\"><span>"+nome+"</span></a>\n");
						//Método que preenche sub-itens.
					}else{
						subitem.append("<a href=\""+urlManut+"altera&id="+mchild.parentId+"\" ");
						subitem.append(" class=\"parent\"><span>"+nome+"</span></a>\n");
					}
					subitem.append(alimentaSubItemManut(mc,subitens,mchild.parentId));
				}else{
					if ( "_novo_item".equals(nome)){
						subitem.append("<a href=\""+urlManut+"novo&id="+mchild.parentId+"\" ");
						subitem.append("><span>"+nome+"</span></a>\n");
					}else{
						subitem.append("<a href=\""+urlManut+"altera&id="+mchild.parentId+"\" ");
						subitem.append("><span>"+nome+"</span></a>\n");
					}

				}
				subitem.append("</li>");
			}
		}
		subitem.append("</ul></div>");
		return subitem.toString();
	}
	
	
	
}