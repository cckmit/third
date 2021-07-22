package org.opoo.apps.license.client.ws;


public interface LicenseV2 {
	
	String login(String productId, String instanceId);
	
	void logoff(String sessionId);
	
	String getLicense(String sessionId);
	
	void validate(String sessionId, String validationString);
	
	String getVersion();
}
