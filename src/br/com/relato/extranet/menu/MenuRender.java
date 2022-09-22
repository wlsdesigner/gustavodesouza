package br.com.relato.extranet.menu;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import br.com.relato.util.FileUtils;

/**
 * @author Daniel
 *
 */ 
public class MenuRender implements Serializable {
	private static final String spspace = "  ";
	private static final String nbspace = "&nbsp;&nbsp;&nbsp;";
	private static final String crlf = "\r\n";

	private static final String targetFrame	= "body";
	private static final String scriptFunction = "changeMenu";
	private static final String imageDir = "img/";
	private static final String imagePrefix = "img";
	private static final String optionPrefix = "opc";
	private static final String imagePlus = "ico_plus.png";
	private static final String imageMinus = "ico_minus.png";
	private static final String imageDot = "ico_folder.png";
	private static final String imageSelected = "file_selected.png";
	//private int id;

	static void writeSpaces(Writer out, String sp, int count) throws IOException {
		while(count-- > 0){
			out.write(sp);
		}
	}
	
	public static void render(Menu menu, Writer out, int level) throws IOException {
		String localId = String.valueOf(menu.getId() + 1);
		String imageName = imagePrefix + localId;
		// Icone que servira de click para expansão e collapsagem
		boolean hasref = menu.hasLength();
		String path = menu.getPath();
		String length = menu.getLength();
		String menuLabel = menu.getLabel();
	
		if ( !hasref ){
			writeSpaces(out, spspace,level);
			writeSpaces(out, nbspace,level);
			out.write("<img id=\"");
			out.write(imageName);
			out.write("\" onclick=\"");
			out.write(scriptFunction); // Função a ser chamada caso seja clicado na tela
			out.write("('");
			out.write(imageName);
			out.write("', '");
			out.write(optionPrefix); // Prefixo do item
			out.write(localId); // Identificador do item
			out.write("')\" src=\"");
			out.write(imageDir); // Diretorio de imagens		
					
			if (menu.hasChildren()) { // Se possui filho
				if (menu.isCollapsed())
					out.write(imagePlus); // Imagem colapsado
				else
				out.write(imageMinus); // Imagem expandido
			}else{
				out.write(imageDot); // Ponto que representa um item de chamada
			}
			// Propriedades da imagem
			out.write("\" border=\"0\">");
			out.write(crlf);
				
			// Identação
			writeSpaces(out, spspace, level);
			
			// Label utilizado no menu		
			if ( menu.getId() != -1 ){
				path = FileUtils.stripWhiteCharsDirectories(path, true);
				if ( path.indexOf("extranet") == 0 )
					path = path.replaceAll("extranet", "/extranet/");
				else if ( path.indexOf("imagens") == 0 )
					path = path.replaceAll("imagens", "/imagens/");
			}
				
			out.write("<span name='");
			out.write(path);
			out.write("' style=\"cursor: pointer;\" onclick=\"clickFolder(this)\">");
			out.write("<font face='verdana' size='1' title='");
			out.write(menuLabel);
			out.write("'>");
			out.write(menuLabel);
			out.write("</font>");
			out.write("</span>");
			// Final de referencia

			out.write("<br>");
			out.write(crlf);
			writeChildren(menu, out, level, localId);
		}else{
			path = FileUtils.stripWhiteCharsDirectories(path, false);
			if ( path.indexOf("extranet") == 0 )
				path = path.replaceAll("extranet", "/extranet/");
			else if ( path.indexOf("imagens") == 0 )
				path = path.replaceAll("imagens", "/imagens/");
			
			out.write("<div onClick=\"doSelect(this)\" style=\"cursor: pointer;\" name=\""+path+"\" name2=\""+path+"\">");
			writeSpaces(out, spspace,level);
			writeSpaces(out, nbspace,level);
			out.write("<img src=\"img/image.gif\" width=16 height=16>");
			out.write("<font face='verdana' size='1' title='"+menuLabel+"'>");
			out.write(menuLabel);
			out.write(" - ( ");
			out.write(length);
			out.write(") ");
			out.write("</font></div>");
			//writeAction(menu, out, imageName, action);			
		}
	}
	
	/**
	 * @param menu
	 * @param out
	 * @param level
	 * @param localId
	 * @throws IOException
	 */
	private static void writeChildren(Menu menu, Writer out, int level, String localId) throws IOException {
		// Se tem filho cria lista com os mesmos por recursividade
		if (menu.hasChildren()) {
			// Monta divisoria
			writeSpaces(out, spspace,level);
			out.write("<div id=\"");
			out.write(optionPrefix);
			out.write(localId);
			out.write("\"");
			// Se estiver colapsado nao o apresenta
			if (menu.isCollapsed()) {
				out.write(" style=\"display:none\"");
			}
			out.write("\">");
			out.write(crlf);
			// Loop recursivo
			Iterator i = menu.iterator();
			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry)i.next();
				render((Menu)entry.getValue(), out, level+1);
			}
			writeSpaces(out, spspace,level);
	
			out.write("</div>");
			out.write(crlf);
		}
	}

	/**
	 * @param menu
	 * @param out
	 * @param imageName
	 * @param action
	 * @throws IOException
	 */
	private static void writeAction(Menu menu, Writer out, String imageName, String action) throws IOException {
		// Se tem açãoo		
		if (menu.hasPath()) {
			out.write("<a href=\"");
			out.write(action);
			out.write("\"");
			out.write(" onmouseover=\"changeImage('");
			out.write(imageName);
			out.write("','");
			out.write(imageDir);
			out.write(imageSelected);
			out.write("')\" onmouseout=\"changeImage('");
			out.write(imageName);
			out.write("','");
			out.write(imageDir);
			out.write(imageDot);
			out.write("')\"");
			out.write(" target=\"");
			out.write(targetFrame);
			out.write("\">");
		}
	}

	public static void render(Menu menu, Writer out) throws IOException {
		render(menu, out, 0);
	}

}
//<div onClick="doSelect(this)" name="Casas/casa.jpg" name2="Casas/casa.jpg">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="he_imagens/popups/image.gif" width=16 height=16><font face='verdana' size='1' title='banheiro OK.jpg'>banheiro OK.jpg</font></div>
