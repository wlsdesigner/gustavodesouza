/*
 * Created on 16/11/2004
 */
package br.com.relato.criteria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.relato.util.SqlHelper;

/**
 * @author Daniel
 */
public class SearchCriteria {
	private String query;
	private String expression = "";
	private String innerjoin = "";
	private String leftjoin = "";
	private String table;
	private List fields = new ArrayList();
	private List order = new ArrayList();
	private List group = new ArrayList();
	
	public SearchCriteria(){ 
 
	}

	public void addField(String field){
		fields.add(field);
	}

	public void addField(String[] fields){
		for ( int i = 0; i < fields.length; i ++ )
			this.fields.add(fields[i]);
	}	
	
	public void addINExpression( String field, String[] values, boolean not ){
		if ( StringUtils.isEmpty(field) || values.length == 0)
			return;

		putWhere();
		if ( not )
			expression += field + SQLExpression.getOperador( SQLExpression.SQL_IN , values );
		else
			expression += field + SQLExpression.getOperador( SQLExpression.SQL_NOT_IN , values );
	}	
	
	public void addIntervalExpression( String field, String valor1, String valor2 ){
		if ( StringUtils.isEmpty(field) )
			return;

		if ( StringUtils.isEmpty(valor1) && StringUtils.isEmpty(valor2) )
			return;
		
		putWhere();
		expression += SQLExpression.getInterval( field, valor1, valor2 ) ;			
	}	
	
	public void addExpression(int action, String field, String valor){
		addExpression(String.valueOf(action), field, valor);	
	}	

	public void addFreeExpression(String value){
		putWhere();
		expression += value;			
	}	
	
	public void addExpression(String action, String field, String valor){
		if ( StringUtils.isEmpty(field) || StringUtils.isEmpty(valor) )
			return;
		
		putWhere();
		expression += field + SQLExpression.getOperador(action, valor);			
	}	

	void putWhere(){
		if ( expression.indexOf("where") == -1 )
			expression += " where ";
		else
			expression += " and ";		
	}
	
	public void addInnerJoin(String tablejoin, String tableinner, String pk){
		innerjoin += " inner join " + tablejoin + " on "+ tablejoin 
			+ "."+pk+" = "+tableinner+"."+pk;
	}

	public void addInnerJoin(String tablejoin, String tableinner, String pk, String as){
		innerjoin += " inner join " + tablejoin + " as " + as + " on "+ tablejoin 
			+ "."+pk+" = "+tableinner+"."+pk;
	}	
	
	public void addLeftJoin(String tablejoin, String tableinner, String pk){
		leftjoin += " left outer join " + tablejoin + " on "+ tablejoin 
			+ "."+pk+" = "+tableinner+"."+pk;
	}	

	public void addLeftJoin(String tablejoin, String tableinner, String pk, String as){
		leftjoin += " left outer join " + tablejoin + " as "+ as +" on "+ tablejoin 
			+ "."+pk+" = "+tableinner+"."+pk;
	}	

	public void addInnerJoin(String tablejoin, String tableinner, String[] pks, String as){
		leftjoin += " inner join " + tablejoin + " as " + as + " on "+ as 
			+ "."+pks[0]+" = "+tableinner+"."+pks[1];
	}	
	
	public void addLeftJoin(String tablejoin, String tableinner, String[] pks, String as){
		leftjoin += " left outer join " + tablejoin + " as " + as + " on "+ as 
			+ "."+pks[0]+" = "+tableinner+"."+pks[1];
	}

	public void addGroup(String field){
		group.add(field);
	}	

	public void addGroup(String[] fields){
		for ( int i = 0; i < fields.length; i ++ )
			this.group.add(fields[i]);
	}	
	
	public void addOrder(String field){
		order.add(field);
	}	

	public void addOrder(String[] fields){
		for ( int i = 0; i < fields.length; i ++ )
			this.order.add(fields[i]);
	}
	
	private String createQuery() {
		query = " select ";
		if ( fields.size() != 0 ){
			Iterator i = fields.iterator();
			while( i.hasNext() ){
				query += i.next();
				if ( i.hasNext() )
					query += ", ";
			}
		}else
			query += " * ";	
			
		query += " from " + getTable() + " "; 
		query += innerjoin;
		query += leftjoin;
		query += expression;

		if ( group.size() != 0 ){
			query += " group by ";
			Iterator igroup = group.iterator();
			while( igroup.hasNext() ){
				query += igroup.next();
				if ( igroup.hasNext() )
					query += ", ";
			}		
		}		
		
		if ( order.size() != 0 ){
			query += " order by ";
			Iterator iorder = order.iterator();
			while( iorder.hasNext() ){
				query += iorder.next();
				if ( iorder.hasNext() )
					query += ", ";
			}		
		}
		
		//System.out.println("@@@@@@@@@@@@@@@@@@@@ query2 = " + query);
		return query;
	}	
	
	public Iterator iterate(){
		return execute().iterator();
	}
	
	public List execute(){
		try{
			return SqlHelper.execute(getQuery(), null);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return new ArrayList();
	}

	public void clear(){
		query = "";
		expression = "";
		innerjoin = "";
		leftjoin = "";
		addTable("");
		fields.clear();
		order.clear();			
	}
	
	public String getTable() {
		return table;
	}
	
	public void addTable(String table) {
		this.table = table;
	}

	public String getQuery() {
		return createQuery();
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
