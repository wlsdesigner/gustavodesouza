package br.com.relato.extranet.menu;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * @author Daniel
 *
 */
public class MenuCreate {
	Map submenus = new TreeMap();
	static String dir;
	String name;
	String length;
	String path;
	
	public MenuCreate add(String name, String path, String length){
		MenuCreate m = (MenuCreate)submenus.get(name);
		if(m != null)
			return m;
		m = new MenuCreate();
		m.name = name;
		m.length = length;
		m.path = path;
		submenus.put(name, m);		
		return m;
	}
	
	public void genText(StringBuffer sb, int level) {
		for(int i = 0; i < level;++i)
			sb.append('\t');
		if(submenus.isEmpty() && notFile(name)){
			sb.append("<menu label=\"")
			  .append(name)
			  .append("\" path=\"")
			  .append(path);
			
			if ( length == null )
				sb.append("\"/>\n");
			else{
				sb.append("\" length=\"")
					.append(length)
				  .append("\"/>\n");
			}
		}else{
			sb.append("<menu label=\"")
			  .append(name)
			  .append("\" path=\"")
			  .append(path);
			
			if(length == null)
				sb.append("\">\n");
			else
				sb.append("\" length=\"")
				.append(length)
				 .append("\">\n");

			  for(Iterator i = submenus.entrySet().iterator(); i.hasNext();) {
			  	Map.Entry entry = (Entry)i.next();
			  	MenuCreate m = (MenuCreate)entry.getValue();
			  	m.genText(sb, level + 1);
			  }
			for(int i = 0; i < level;++i)
				sb.append('\t');
			  sb.append("</menu>\n");
		 }		 
	}
	
	private boolean notFile(String name2) {
		if ( name2.indexOf(".") != -1)
			return true;
		return false;
	}

	public String getText() {
		StringBuffer sb = new StringBuffer();
		for(Iterator i = submenus.entrySet().iterator(); i.hasNext();) {
		  Map.Entry entry = (Entry)i.next();
		  MenuCreate m = (MenuCreate)entry.getValue();
		  m.genText(sb, 2);
		}
		return sb.toString();
	} 
	

	public void recAdd(String menu, String path, String length) {
		add(this, menu, path, length);
	}
	
	public static void add(MenuCreate root, String menu, String path, String length) {
		String[] split = menu.split("/");
		String menupath = getDir() + menu.substring(0, menu.lastIndexOf("/") + 1);
		MenuCreate cur = root;
		
		for(int i = 0; i < split.length - 1; ++i){
			menupath = getDir() + menu.substring(0, menu.indexOf(split[i]) + split[i].length() + 1);
			cur = cur.add(split[i], menupath, null);
		}
		if ( length == null ){
			menupath = getDir() + path;
			cur = cur.add(split[split.length - 1], menupath, null);
		}else
			cur.add(split[split.length - 1], path, length);
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public Map getSubmenus() {
		return submenus;
	}


	/**
	 * @return Returns the length.
	 */
	public String getLength() {
		return length;
	}
	/**
	 * @param length The length to set.
	 */
	public void setLength(String length) {
		this.length = length;
	}
	/**
	 * @return Returns the path.
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param map
	 */
	public void setSubmenus(Map map) {
		submenus = map;
	}

	/**
	 * @return Returns the dir.
	 */
	public static String getDir() {
		return dir;
	}
	/**
	 * @param dir The dir to set.
	 */
	public void setDir(String dir) {
		MenuCreate.dir = dir;
	}
}
