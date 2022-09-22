package br.com.relato.extranet.menu; 

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class Menu implements Comparable, Serializable {
	public int id;
	public String label;
	public String path;
	public String length;
	public Map children;
	public boolean collapsed;

	public int compareTo(Object value) {
		if(value instanceof Menu) {
			String me = getLabel();
			String it = ((Menu)value).getLabel();
			
			return me.compareTo(it);
		}
		return -1;
	}	
	
	public Object clone() {
		Menu m = new Menu();
		m.id = id;
		m.label = label;
		m.path = path;
		m.length = length;
		m.collapsed = collapsed;
		return m;		
	}

	public Menu() {
		this(-1, null, null);
	}

	public Menu(int id, String label) {
		this(id, label, null);
	}

	public Menu(int id, String label, String action) {
		this.id = id;
		this.label = label;
		this.length = length == null ? null : length.trim();
		this.path = path == null ? null : path.trim();
		this.collapsed = true;
	}

	public boolean hasChildren() {
		return children == null ? false : !children.isEmpty();
	}

	public boolean hasPath() {
		return path != null && path.length() > 0;
	}
	
	public boolean hasLength() {
		return length != null && length.length() > 0;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public Iterator iterator() {
		if (children == null) {
			return new Iterator() {
			    public boolean hasNext() {
			    	return false;
			    }

			    public Object next() {
			    	throw new NoSuchElementException("no more elements");
			    }

			    public void remove() {
			    	throw new UnsupportedOperationException("remove not suported");
			    }
			};
		}

		return children.entrySet().iterator();
	} 

	public void addChild(Menu item) {
		if (children == null)
			children = new TreeMap();
		children.put(item.getLabel(), item);
	}

	public void reset() {
		if ( children != null )
			children.clear();
	}	
	
	public boolean isRoot() {
		return id == -1;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param i
	 */
	public void setId(int i) {
		id = i;
	}

	/**
	 * @param string
	 */
	public void setLabel(String string) {
		label = string;
	}

	/**
	 * @param b
	 */
	public void setCollapsed(boolean b) {
		collapsed = b;
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
}