/*
 * Created on 10/12/2004
 */
package br.com.relato.util;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author daniel
 */
public class FileTest extends TestCase {

	public void test(){
	/*	List list = FileUtils.getFiles("extranet/", true);
		Iterator i = list.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}*/
		
		String teste = FileUtils.putWriteCharsDirectories("/extranet", false);
		System.out.println(teste);
		System.out.println(FileUtils.stripWhiteCharsDirectories(teste, false));
	}
	
}
