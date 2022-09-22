package br.com.relato.extranet.menu;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * @author Daniel
 *
 */
public class MenuCreateExtra {
	Map submenus = new TreeMap();
	static String dir;
	String name;
	String path;
	
	public MenuCreateExtra add(String name, String path){
		MenuCreateExtra m = (MenuCreateExtra)submenus.get(name);
		if(m != null)
			return m;
		m = new MenuCreateExtra();
		m.name = name;
		m.path = path;
		submenus.put(name, m);		
		return m;
	}
	
	public void genText(StringBuffer sb, int level) {
		for(int i = 0; i < level;++i)
			sb.append('\t');
		if(submenus.isEmpty()){
		
			sb.append("<menu label=\"")
			  .append(name)
			  .append("\" path=\"")
			  .append(path);
			
			sb.append("\"/>\n");
		}else{
			sb.append("<menu label=\"")
			  .append(name)
			  .append("\" path=\"")
			  .append(path);
			
			sb.append("\">\n");

			for(Iterator i = submenus.entrySet().iterator(); i.hasNext();) {
			  	Map.Entry entry = (Entry)i.next();
			  	MenuCreateExtra m = (MenuCreateExtra)entry.getValue();
			  	m.genText(sb, level + 1);
			  }
			for(int i = 0; i < level;++i)
				sb.append('\t');
			  sb.append("</menu>\n");
		 }		 
	}
	
	public String getText() {
		StringBuffer sb = new StringBuffer();
		for(Iterator i = submenus.entrySet().iterator(); i.hasNext();) {
		  Map.Entry entry = (Entry)i.next();
		  MenuCreateExtra m = (MenuCreateExtra)entry.getValue();
		  m.genText(sb, 2);
		}
		return sb.toString();
	} 
	

	public void recAdd(String menu, String path) {
		add(this, menu, path);
	}
	
	public static void add(MenuCreateExtra root, String menu, String path) {
		String[] split = menu.split("/");
		String menupath = getDir() + menu.substring(0, menu.lastIndexOf("/") + 1);
		MenuCreateExtra cur = root;
		for(int i = 0; i < split.length - 1; ++i){
			menupath = getDir() + menu.substring(0, menu.indexOf(split[i]) + split[i].length() + 1);
			cur = cur.add(split[i], menupath);
		}
		cur.add(split[split.length - 1], getDir() + path);
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
		MenuCreateExtra.dir = dir;
	}
}
