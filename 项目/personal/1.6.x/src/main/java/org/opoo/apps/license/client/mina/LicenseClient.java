package org.opoo.apps.license.client.mina;

public interface LicenseClient {
	
	String getEncodedLicense();
	
	void start();
	
	void stop();
	
	void check();
	
	boolean isAlive();
}
