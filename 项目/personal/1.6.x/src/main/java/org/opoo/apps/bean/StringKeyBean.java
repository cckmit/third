package org.opoo.apps.bean;

import org.opoo.ndao.domain.StringKeyDomain;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class StringKeyBean extends SerializableEntity<String> implements StringKeyDomain
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1691924172731115876L;

	private String id;
	
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return getId();
	}

	public void setKey(String id) {
		setId(id);
	}
}
