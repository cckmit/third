package org.opoo.apps.json;

/**
 * 
 * @author lcj
 *
 */
public interface JSONSerializer {
	
	String toJSON(Object object);
	
	Object fromJSON(String json);
	
}
