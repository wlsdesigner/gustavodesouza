package br.com.relato.ecommerce.servlet;

/*
 * Created on 26/02/2008
 * @author Rafael Lima
 */

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.com.relato.htmledit.service.ControlXml;

/**
 * @author Daniel
 */
public class EcommerceServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request,
						HttpServletResponse response){
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			response.setContentType("text/xml ; charset = UTF-8");
			ServletOutputStream out = response.getOutputStream();
			EcommerceControl control = new EcommerceControl(builder.parse(request.getInputStream()));			
			control.parseXml();
			control.generateOutXml(out);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response){

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			response.setContentType("text/xml ; charset = UTF-8");
			ServletOutputStream out = response.getOutputStream();
			EcommerceControl control = new EcommerceControl(builder.parse(request.getInputStream()));			
			control.parseXml();
			control.generateOutXml(out);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		


}


}
