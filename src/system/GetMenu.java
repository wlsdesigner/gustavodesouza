/*
 * Created on 24/11/2004
 */
package system;

import java.util.ArrayList;

/**
 * @author daniel
 *
 */
public class GetMenu {
	
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
					bf.append("orientation=\"vertical\";").append("\n");
			        bf.append("position=\"relative\";").append("\n");
			        bf.append("screenposition=\"middle\";").append("\n");
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

	//RAFAEL LINGUA
	
	public static String renderVisualizacao(MenuConstructor mc, int lingua){
		return renderVisualizacao(mc, false, lingua);
	}	
		
	public static String renderVisualizacao(MenuConstructor mc, boolean portal, int lingua){
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
					bf.append("orientation=\"vertical\";").append("\n");
			        bf.append("position=\"relative\";").append("\n");
			        bf.append("screenposition=\"middle\";").append("\n");
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
				/*
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
				*/
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
		
		return linhaMenu;
	}
	
	public static String renderEdicao(MenuConstructor mc, int lingua){
		StringBuffer sb = new StringBuffer();
		String urlManut = "germenu_form.jsp?acao=";	
		String menuName = "";	
		String linhaMenu = "";
		StringBuffer bf = new StringBuffer();
		int parentMenu = -1;  
		MenuConstructor.MenuItem mi;

		mc.setTableName("menu",lingua);	
		ArrayList itens = mc.getItensManut();
		for (int i=0; i<itens.size(); i++) {
			mi = (MenuConstructor.MenuItem) itens.get(i);
			
			
			if(1 == mi.language){
				System.out.println("menu =="+mi.name+" - lingua =="+mi.language); 
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
		}
		if (bf.length() > 0) {
			bf.append("}").append("\n"); 
			linhaMenu += bf.toString();  
			bf.setLength(0);
		}
		
		return linhaMenu;
	}

}
