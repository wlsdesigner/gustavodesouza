/*
 * Created on 06/04/2005
 */
package br.com.relato;

import java.io.IOException;
import java.util.Properties;

import br.com.relato.service.ResourceLocatorService;

/**
 * @author Administrator
 */
public class ConstantsApp {
	private static Properties props;
	
	public static String getParametro(String key){
		if ( props == null ){
			try {
				props = ResourceLocatorService.getInstance()
					.getResourceProperties(Constants.APP_CONFIG_FILE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props.getProperty(key);
	}

}
