/*
 * Created on 29/11/2004
 *
 */
package br.com.relato;

/**
 * @author daniel
 */
public interface Constants {

	public static final String HIBERNATE_CONFIG_FILE=
		"/WEB-INF/hibernate.properties";
	public static final String APP_CONFIG_FILE=
		"/WEB-INF/appconf.properties";
	public static final String HIBERNATE_FACTORY_KEY= "br.com.relato.extranet.factory";
	public static final String USER_KEY= "br.com.relato.extranet.user";
	public static final String MENU_KEY= "br.com.relato.extranet.menu";
	public static final String MENU_KEY_EXTRANET= "br.com.relato.extranet.menu.upload";
	public static final String MENU_IMG_KEY= "br.com.relato.extranet.menuimg";
	public static final String SECURITY_SERVICE_KEY= "br.com.relato.extranet.security";
	
	public static final String GERENCIADOR_URL ="/he/gerenciadorconteudo.jsp";
	public static final String ENCERRA_URL ="/he/gerenciadorconteudo.jsp";
	
	public static final String LOGIN_EXTRA_PAGE ="/loginextranet.jsp";
	public static final String LOGIN_HTML_PAGE ="/login.jsp";
	
	public static final String EXTRANET_INDEX_PAGE ="/extranet/index.jsp";
	public static final String HTML_INDEX_PAGE ="/siteadmin/home.jsp";
	
	public static final String SITE_INDEX_PAGE ="http://kanaflex.relato.com.br";
	
	public static final String ACCESS_DENIED ="/accessDenied.jsp";
}
