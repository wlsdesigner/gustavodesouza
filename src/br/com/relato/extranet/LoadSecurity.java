/*
 * Created on 28/03/2005
 */
package br.com.relato.extranet;

import java.io.IOException;
import java.util.Properties;

import br.com.relato.service.ResourceLocatorService;

/**
 * @author Administrator
 */
public class LoadSecurity {
	static Properties queries;
	static final String QUERIES_RESOURCE ="/WEB-INF/security.properties";
	
	protected static String getParametro(String key) throws IOException {
		if(queries == null) {
			System.out.println("CARREGANDO OS PARAMETROS");
			queries = ResourceLocatorService
				.getInstance()
				.getResourceProperties(QUERIES_RESOURCE);
			if(queries == null)
				throw new IOException("não foi possivel carregar os parametros");
		}
		return queries.getProperty(key);	
	}
		
	public static void reloadQueries() {
		queries = null;
	}
}