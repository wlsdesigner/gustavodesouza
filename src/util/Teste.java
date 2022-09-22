package util;

import javax.servlet.http.*;
import java.util.*;

public class Teste extends HttpServletRequestWrapper {

public Teste(HttpServletRequest arg) {
	super(arg);
	if ( arg.getParameter("data") == null ){
		Map map = arg.getParameterMap();
		Iterator i = map.entrySet().iterator();
		String url = "content.jsp?";
		int count = 1;
		while ( i.hasNext() ){
			Map.Entry entry = (Map.Entry)i.next();
			String value = "";
			if ( "msg".equals((String)entry.getKey()) )
				value = "lalala: " + entry.getValue();
			else
				value = (String)entry.getValue();
			if ( count == 1 )
				url += entry.getKey() + "=" + value;
			else
				url += "?" + entry.getKey() + "=" + value;
			count ++;
		}
		url += "&data=true";
		//response.sendRedirect(url);	
		return;
	}
}

private Map paramValues = new TreeMap();

public void setParameterValues(String paramName, String [] paramValues) {
	if (paramValues==null || paramValues.length==0)
		throw new RuntimeException( "ilegal Values for parameter name= "+paramName);
	else
		this.paramValues.put(paramName,paramValues);
}

public void setParameter(String paramName, String paramValue) {
	this.paramValues.put(paramName,paramValue);
}

public String getParameter(String paramName) {
	String [] allValues = (String[]) this.paramValues.get(paramName);
	return allValues==null ? null : (allValues)[0];
}

public String [] getParameterValues(String paramName) {
	return (String[]) this.paramValues.get(paramName);
}

}
