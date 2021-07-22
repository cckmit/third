package org.opoo.apps.module;


/**
 * Ä£¿é¼àÌýÆ÷½Ó¿Ú¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface ModuleListener {
	/**
	 * 
	 * @param name
	 * @param module
	 */
	void moduleCreated(String name, Module<?> module);

	/**
	 * 
	 * @param name
	 * @param module
	 */
	void moduleDestroyed(String name, Module<?> module);
}
