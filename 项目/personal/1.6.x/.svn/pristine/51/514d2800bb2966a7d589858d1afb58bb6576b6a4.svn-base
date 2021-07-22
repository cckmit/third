package org.opoo.apps.ws.types;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="Criterion")
public class WSCriterion{
	private String sql;
	private Object[] values;

	public WSCriterion() {
		super();
	}

	public WSCriterion(String sql, Object[] values) {
		super();
		this.sql = sql;
		this.values = values;
	}
	
	public WSCriterion(String sql) {
		super();
		this.sql = sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
	public Object[] getValues() {
		return values;
	}
	
	public String toString(){
		return sql;
	}
	public String getSql() {
		return sql;
	}
}
