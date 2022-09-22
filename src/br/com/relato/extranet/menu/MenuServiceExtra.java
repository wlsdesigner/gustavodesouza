package br.com.relato.extranet.menu;

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
public class MenuServiceExtra {
	final boolean DEBUG_ME = false;
	private MenuExtra menu = null;
	List folders = new ArrayList();
	int id = -1;

	public void print(MenuExtra m, int l) {
		for (int i = 0; i < l; ++i)
			System.out.print(' ');
		System.out.println(m.getLabel() + " " + m.getPath());
		for (Iterator i = m.iterator(); i.hasNext();) {
			Map.Entry entry = (Map.Entry)i.next();
			MenuExtra m2 = (MenuExtra)entry.getValue();
			print(m2, l + 1);
		}
	}

	void loadDoc(MenuExtra menu, Element elem) {
		menu.setLabel(elem.getAttribute("label"));
		menu.setPath(elem.getAttribute("path"));
		
		if ("false".equals(elem.getAttribute("collapsed")))
			menu.setCollapsed(false);

		menu.setId(id++);
		for (Element el = next(elem.getFirstChild());
			el != null;
			el = next(el.getNextSibling())) {
			MenuExtra m = new MenuExtra();
			m.setLabel(String.valueOf(id));
			menu.addChild(m);
			loadDoc(m, el);
		}
	}

	public MenuServiceExtra(List list){
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
		this.menu = new MenuExtra();
		this.menu.reset();
		loadDoc(this.menu, next(elem.getFirstChild()));
	}
	
	Element next(Node n) {
		while (n != null && !(n instanceof Element))
			n = n.getNextSibling();
		return (Element)n;
	}

	void addChilds(MenuExtra menu, MenuExtra res) {
		if (DEBUG_ME)
			System.out.println("gerando " + menu.getLabel());
		Iterator i = menu.iterator(); 
		while (i.hasNext()) {
			Map.Entry entry = (Map.Entry)i.next();
			MenuExtra m = (MenuExtra)entry.getValue();
			MenuExtra r = (MenuExtra)m.clone();

			addChilds(m, r);
			if (r.hasChildren() || r.hasPath()){
				res.addChild(r);
			}
		}
	}

	public void setFolders(List list){
		this.folders = list;
	}
	
	public synchronized MenuExtra getMenu() {
		try {
			create(folders);
		} catch (Exception e) {
			e.printStackTrace();
		}

		MenuExtra res = (MenuExtra)menu.clone();
		addChilds(menu, res);
		if (res != null)
			res.setCollapsed(false);
		//print(res, 0);
		return res;
	}

	File build(List list) throws IOException{
		MenuCreateExtra root =  new MenuCreateExtra();
		root.setDir("/extranet/"); 
		
		Iterator i = list.iterator(); 
		while ( i.hasNext()){
			String path = (String)i.next();
			String title = ((String)path).substring(((String)path).indexOf("extranet/")+9);
			String p = ((String)path).substring(((String)path).indexOf("extranet/")+9);
			if(title == null)
				continue;
			root.recAdd(title, p);
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		sb.append("		<menu-list>\n");
		sb.append("			<menu label=\"Extranet\" path=\"/extranet/\">\n");
		sb.append(root.getText());
		sb.append("</menu>\n");
		sb.append("</menu-list>");
		
		File resultFile = File.createTempFile("menu", "tmp");

		FileWriter wr = new FileWriter(resultFile);
		wr.write(sb.toString());
		//System.out.println(sb.toString());
		wr.flush();
		wr.close();
			
		resultFile.deleteOnExit();
		return resultFile;
	}

	public static void main(String args[]){
		List list = new ArrayList();
		list.add("/www/im/webapps/ROOT/extranet/aptos/vilaolimpia/marsaut/");
		list.add("/www/im/webapps/ROOT/extranet/aptos/vilaolimpia/marsaut/");
		list.add("/www/im/webapps/ROOT/extranet/aptos/vilaolimpia/marsaut/");
		list.add("/www/im/webapps/ROOT/extranet/aptos/zonapaulista/");
		list.add("/www/im/webapps/ROOT/extranet/casas/vilasanta/");
		list.add("/www/im/webapps/ROOT/extranet/casas/vilamoscone/");
		
		MenuServiceExtra m = new MenuServiceExtra(list);
		MenuExtra a = m.getMenu();
		m.print(a, 0);
	}
}
