/*
 * Created on 11/01/2005
 *
 */
package br.com.relato.htmledit.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import br.com.relato.htmledit.menu.Menu;

/**
 * @author daniel
 *
 */
public class ReplicacaoDatabases {
	final static String REPLICACAO_URL = "WEB-INF/replicacao.xml";
	
	public static List getDatabases() throws ParserConfigurationException, SAXException, IOException{
		InputStream in = new FileInputStream(REPLICACAO_URL);
		DocumentBuilderFactory builderFactory =
			DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringElementContentWhitespace(true);
		builderFactory.setIgnoringComments(true);
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(in);
		
		Element elem = doc.getDocumentElement();
		loadDoc(next(elem.getFirstChild()));		
		
		
		return null;
	}

	static void loadDoc(Element elem) {
		System.out.println(elem.getNodeName() + " ---- " + elem.getAttribute("name"));
		
		System.out.println(elem.getNodeName() + " ---- " + elem.getAttribute("name"));
		/*for (Element el = next(elem.getFirstChild());
			el != null;
			el = next(el.getNextSibling())) {
			
			loadDoc(el);
		}*/
	}	
	
	static Element next(Node n) {
		while (n != null && !(n instanceof Element))
			n = n.getNextSibling();
		return (Element)n;
	}	
	
	public static void main(String args[]){
		try {
			getDatabases();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
