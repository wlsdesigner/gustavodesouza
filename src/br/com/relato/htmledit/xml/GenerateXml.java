/*
 * Created on 25/10/2004
 */
package br.com.relato.htmledit.xml;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Daniel
 */
public class GenerateXml{
		
	public GenerateXml(){
		
	}
	
	public void create(OutputStreamWriter out) throws IOException{
		out.write("<?xml version=\"1.0\">");
		out.write("<page>");
	}

	public void body(OutputStreamWriter out) throws IOException{
		out.write("<id>1</id>");
	}		
	
	public void finish(OutputStreamWriter out) throws IOException{
		out.write("</page>");
	}
}
