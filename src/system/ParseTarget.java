package system;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author daniel
 */
public class ParseTarget {
	final static String DEFFAULT_VALUE = "no";
	final static String DEFFAULT_VALUE_TOP = "10";
	final static String DEFFAULT_WIDTH = "600";
	final static String DEFFAULT_HEIGHT = "400";
	
	public static Map parse(String target){
		Map map = new HashMap(); 
		
		map.put("width", getValue("width", target));
		map.put("height", getValue("height", target));
		map.put("left", getValue("left", target));
		map.put("top", getValue("top", target));
		map.put("toolbar", getValue("toolbar", target));
		map.put("location", getValue("location", target));
		map.put("directories", getValue("directories", target));
		map.put("status", getValue("status", target));
		map.put("menubar", getValue("menubar", target));
		map.put("scrollbars", getValue("scrollbars", target));
		map.put("resizable", getValue("resizable", target));
		map.put("copyhistory", getValue("copyhistory", target));
		
		return map;
	}

	public static String getValue(String field, String target) {
		if ( target.indexOf(field) == -1 ){
			return getDefault(field);
		}else{
			String value = ""; 
			for ( int i = target.indexOf(field); i <= target.length(); i ++ ){
				if ( target.length() <= (i+field.length()+1) || StringUtils.isEmpty(target.substring((i+field.length()+1), (i+field.length()+2)).trim()) )
					break;
				
				value += target.substring(i+field.length()+1, i+field.length()+2);
			}
			
			if ( StringUtils.isEmpty(value) )
				return getDefault(field);
			else
				return value;
			
		}
	}

	static String getDefault(String field) {
		if ( "width".equals(field) )
			return DEFFAULT_WIDTH;
		else if ( "height".equals(field) )
			return DEFFAULT_HEIGHT;
		else if ( "left".equals(field) )
			return DEFFAULT_VALUE_TOP;
		else if ( "top".equals(field) )
			return DEFFAULT_VALUE_TOP;
		else
			return DEFFAULT_VALUE;
	}
	
	
}
