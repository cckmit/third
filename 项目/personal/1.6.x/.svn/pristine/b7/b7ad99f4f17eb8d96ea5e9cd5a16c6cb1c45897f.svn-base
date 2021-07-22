package org.opoo.apps.bean.core;

import java.io.Serializable;

import org.opoo.apps.Labelable;
import org.opoo.apps.bean.StringKeyBean;


/**
 * 系统属性的实体里。
 * @author alex
 *
 */
public class Property extends StringKeyBean implements Labelable{
	private static final long serialVersionUID = -2564599785221278120L;
	private String value;
	
	public Property(){
	}
	
	public Property(String name, String value){
		setName(name);
		setValue(value);
	}
	
	public String getName() {
		return getId();
	}
	public void setName(String name) {
		setId(name);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public Serializable getData() {
		return value;
	}

	public String getLabel() {
		return getName();
	}
}
