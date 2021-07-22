package org.opoo.apps.ws.types;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="Order")
public class WSOrder{
	private String name;
	private boolean asc = true;
	
	public WSOrder(String name) {
		super();
		this.name = name;
	}
	public WSOrder(String name, boolean asc) {
		super();
		this.name = name;
		this.asc = asc;
	}
	public WSOrder() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAsc() {
		return asc;
	}
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
}
