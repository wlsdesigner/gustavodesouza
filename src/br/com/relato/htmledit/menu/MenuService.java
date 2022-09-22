package br.com.relato.htmledit.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Daniel
 *
 */
public class MenuService {
	final boolean DEBUG_ME = false;
	private Menu menu = null;
	List folders = new ArrayList();
	int id = -1;

	public void print(Menu m, int l) {
		for (int i = 0; i < l; ++i)
			System.out.print(' ');
		System.out.println(m.getLabel() + " " + m.getPath());
		for (Iterator i = m.iterator(); i.hasNext();) {
			Map.Entry entry = (Map.Entry)i.next();
			Menu m2 = (Menu)entry.getValue();
			print(m2, l + 1);
		}
	}

	void loadDoc(Menu menu, Element elem) {
		menu.setLabel(elem.getAttribute("label"));
		menu.setPath(elem.getAttribute("path"));
		menu.setLength(elem.getAttribute("length"));
		
		if ("false".equals(elem.getAttribute("collapsed")))
			menu.setCollapsed(false);

		menu.setId(id++);
		for (Element el = next(elem.getFirstChild());
			el != null;
			el = next(el.getNextSibling())) {
			Menu m = new Menu();
			m.setLabel(String.valueOf(id));
			menu.addChild(m);
			loadDoc(m, el);
		}
	}

	public MenuService(List list){
		setFolders(list);
	}
	
	void create(List list) throws ParserConfigurationException, SAXException, IOException{
		InputStream in = new FileInputStream(build(list));
		DocumentBuilderFactory builderFactory =
			DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringElementContentWhitespace(true);
		builderFactory.setIgnoringComments(true);
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(in);
		
		Element elem = doc.getDocumentElement();
		this.menu = new Menu();
		this.menu.reset();
		loadDoc(this.menu, next(elem.getFirstChild()));
	}
	
	Element next(Node n) {
		while (n != null && !(n instanceof Element))
			n = n.getNextSibling();
		return (Element)n;
	}

	void addChilds(Menu menu, Menu res) {
		if (DEBUG_ME)
			System.out.println("gerando " + menu.getLabel());
		Iterator i = menu.iterator(); 
		while (i.hasNext()) {
			Map.Entry entry = (Map.Entry)i.next();
			Menu m = (Menu)entry.getValue();
			Menu r = (Menu)m.clone();

			addChilds(m, r);
			if (r.hasChildren() || r.hasLength()){
				res.addChild(r);
			}
		}
	}

	public void setFolders(List list){
		this.folders = list;
	}
	
	public synchronized Menu getMenu() {
		try {
			create(folders);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Menu res = (Menu)menu.clone();
		addChilds(menu, res);
		if (res != null)
			res.setCollapsed(false);
		//print(res, 0);
		return res;
	}

	File build(List list) throws IOException{
		MenuCreate root =  new MenuCreate();
		root.setDir("/imagens/"); 
		
		Iterator i = list.iterator(); 
		while ( i.hasNext()){
			List row = (List)i.next();
			String path = (String)row.get(0);
			String title = ((String)path).substring(((String)path).indexOf("imagens/")+8);
			String p = ((String)path).substring(((String)path).indexOf("imagens/")+8);
			String length = (String)row.get(1);
			if(title == null)
				continue;
			root.recAdd(title, p, length);
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		sb.append("		<menu-list>\n");
		sb.append("			<menu label=\"Imagens\" path=\"/imagens/\">\n");
		sb.append(root.getText());
		sb.append("</menu>\n");
		sb.append("</menu-list>");
		
		File resultFile = new File("menu.tmp");

		FileWriter wr = new FileWriter(resultFile);
		wr.write(sb.toString());
		
		wr.flush();
		wr.close();
			
		resultFile.deleteOnExit();
		return resultFile;
	}

	public static void main(String args[]){
		List list = new ArrayList();
		List row = new ArrayList();
			row.add("/www/im/webapps/ROOT/imagens/angel2.gif");
			row.add("158888 kb");
		list.add(row);
		List row1 = new ArrayList();
			row1.add("/www/im/webapps/ROOT/imagens/aptos/vilaolimpia/marsaut/c.jpg");
			row1.add("388 kb");			
		list.add(row1);
		List row2 = new ArrayList();
			row2.add("/www/im/webapps/ROOT/imagens/aptos/vilaolimpia/marsaut/c2.jpg");
			row2.add("434388 kb");			
		list.add(row2);
		List row3 = new ArrayList();
			row3.add("/www/im/webapps/ROOT/imagens/aptos/vilaolimpia/marsaut/banheiro.jpg");
			row3.add("434388 kb");			
    	list.add(row3);
		List row4 = new ArrayList();
			row4.add("/www/im/webapps/ROOT/imagens/aptos/zonapaulista/1.jpg");
			row4.add("434388 kb");			
		list.add(row4);
		List row5 = new ArrayList();
			row5.add("/www/im/webapps/ROOT/imagens/casas/vilasanta/taquaras.jpg");
			row5.add("434388 kb");			
		list.add(row5);
		List row6 = new ArrayList();
			row6.add("/www/im/webapps/ROOT/imagens/casas/vilamoscone/1.jpg");
			row6.add("434388 kb");			
		list.add(row6);
		
		MenuService m = new MenuService(list);
		//m.setFolders(list);
		Menu a = m.getMenu();
	}
}
