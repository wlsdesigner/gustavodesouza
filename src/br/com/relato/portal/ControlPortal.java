/*
 * Created on 29/10/2004
 */
package br.com.relato.portal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import br.com.relato.util.SqlHelper;

/**
 * @author Daniel
 */
public class ControlPortal {

	public static Map getPortal(){
		Map map = new HashMap();
		try{
			List list = SqlHelper.execute("select * from portal", null);
			Iterator i = list.iterator();
			while ( i.hasNext() ){
				List row = (List)i.next();
				map.put(String.valueOf(row.get(0)), String.valueOf(row.get(1)));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map getPortalLingua(){
		Map map = new HashMap();
		try{
			List list = SqlHelper.execute("select * from portal", null);
			Iterator i = list.iterator();
			while ( i.hasNext() ){
				List row = (List)i.next();
				List key = new ArrayList();
				key.add(null != row.get(0) ? String.valueOf(row.get(0)) : "");
				key.add( (null != row.get(2)? Integer.valueOf(String.valueOf(row.get(2))): new Integer(1)));
				map.put(key, null != row.get(1) ? String.valueOf(row.get(1)) : "" );
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return map;
	}

}
