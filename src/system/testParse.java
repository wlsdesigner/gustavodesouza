/*
 * Created on 23/11/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package system;

import junit.framework.TestCase;

/**
 * @author daniel
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class testParse extends TestCase {

	public void teste(){
		System.out.println(ParseTarget.getValue("height", "teste;targetfeatures=width=800 left=1 top=1 status=yes scrollbars=yes resizable=yes height=600"));
	}
	
}
