/*
 * Created on 03/12/2004
 */
package br.com.relato.extranet.web;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import br.com.relato.EntryPoint;
import br.com.relato.extranet.ExtranetConstants;
import br.com.relato.extranet.Permission;
import br.com.relato.extranet.model.Usuario;
import br.com.relato.service.ResourceLocatorService;

/**
 * @author daniel
 */
public class ExtranetUtils {
	static Properties props;
	
	static void loadProps() {
		ResourceLocatorService resource = ResourceLocatorService.getInstance();
		try {
			props = resource.getResourceProperties("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void reloadProps() {
		props = null;
	}
}
