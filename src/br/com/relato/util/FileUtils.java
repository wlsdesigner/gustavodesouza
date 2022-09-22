/*
 * Created on 10/12/2004
 */
package br.com.relato.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author daniel
 *
 */
public class FileUtils {

	public static List getFiles(String path, boolean subfolder){
		List list = new ArrayList();
		processFolders(path, list);
		return list;
	}
	
	static void processFolders(String path, List list){
		File directory = new File(path);
		if ( directory.exists() ){
			File[] files = directory.listFiles();
			for( int i=0; i < files.length; i ++ ){
				list.add(files[i]);
				if ( files[i].isDirectory() ){
					processFolders(files[i].getPath(), list);
				}
			}
		}
	}
	
	public static String putWriteCharsDirectories(String path, boolean dir){
		String arquivo = "";
		
		String[] diretorios = path.split("/");
		for ( int i = 0; i < diretorios.length; i++ ){
			if ( i == ( diretorios.length - 1) && dir )
				arquivo += " " + diretorios[i];
			else if ( i == ( diretorios.length - 1) )
				arquivo += diretorios[i];
			else
				arquivo += " " + diretorios[i] + "/";
		}
		return arquivo;
	}
	
	public static String stripWhiteCharsDirectories(String path, boolean dir){
		String arquivo = "";
		
		String[] diretorios = path.split("/");
		for ( int i = 0; i < diretorios.length; i++ ){
			if( i == ( diretorios.length - 1) && dir )
				arquivo += diretorios[i].substring(1);
			else if ( diretorios[i].indexOf(" ") == 0)
				arquivo += diretorios[i].substring(1) + "/";
			else
				arquivo += diretorios[i];
		}
		return arquivo;
	}	
}
