package org.opoo.apps.web.struts2.action.admin.setup;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.AppsGlobals;

import com.opensymphony.xwork2.ActionSupport;

public class OpenOfficeConvertersSetupAction extends SetupActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9015376219054013361L;
	public static final String OPEN_OFFICE_HOST = "attachments.openoffice.host";
	public static final String OPEN_OFFICE_PORT = "attachments.openoffice.port";
	
	private String host;
	private int port;
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	
	public String doDefault(){
		host = AppsGlobals.getSetupProperty(OPEN_OFFICE_HOST, "localhost");
		port = AppsGlobals.getSetupProperty(OPEN_OFFICE_PORT, 8100);
		return ActionSupport.INPUT;
	}
	
	public String execute(){
		if(port <= 0){
			port = 8100;
		}
		if(StringUtils.isBlank(host)){
			host = "localhost";
		}
		
		AppsGlobals.setSetupProperty(OPEN_OFFICE_HOST, host);
		AppsGlobals.setSetupProperty(OPEN_OFFICE_PORT, String.valueOf(port));
		
		return "next";
	}
}
