package org.opoo.apps.web.struts2;

public interface AppsActionExceptionHandler {
	/**
	 * 
	 * @param action
	 * @param e
	 * @throws Exception 
	 */
	void handle(AbstractAppsAction action, Exception e) throws Exception;
}
