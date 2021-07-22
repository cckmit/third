package org.opoo.apps.security;

import java.io.Serializable;
import java.util.Map;

import org.opoo.apps.Labelable;
import org.opoo.ndao.Domain;

/**
 * ÓÃ»§×é¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class Group implements Domain<Long>, Labelable {
	private static final long serialVersionUID = 3165059417917898155L;
	private Long id;
	private String name;
	private Map<String, String> properties;
	
	public Group(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Group(String name) {
		super();
		this.name = name;
	}
	public Group() {
		super();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public Serializable getData() {
		return id;
	}
	
	public String getLabel() {
		return name;
	}
	
	public String toString(){
		return super.toString() + ":" + id;
	}
}
