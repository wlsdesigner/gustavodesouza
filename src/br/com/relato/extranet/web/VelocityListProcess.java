package br.com.relato.extranet.web;

import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityListProcess{
	static VelocityEngine ve = new VelocityEngine();
	static VelocityContext context = new VelocityContext();
	public static void main(String args[]) throws Exception
	{
		ve.init();

		Template t = ve.getTemplate("templates/template1.vm");
		
		Map map = new TreeMap();
		map.put("teste", "teste1");
		map.put("teste2", "teste3");
		map.put("teste4", "teste5");
		context.put("lista", map.entrySet().iterator());
		
		StringWriter writer = new StringWriter();
		t.merge(context, writer);

		System.out.println(writer.toString());
	}
}

/*		ArrayList list = new ArrayList();
list.add("Item 1");
list.add("Item 2");
list.add("Item 3");
list.add("Item 4");
list.add("Item 5");

context.put("lista", list);*/