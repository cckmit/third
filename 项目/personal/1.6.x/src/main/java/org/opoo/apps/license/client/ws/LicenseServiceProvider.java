package org.opoo.apps.license.client.ws;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public interface LicenseServiceProvider {

	/**
	 * 
	 * @return null if error
	 */
	LicenseWS getService();
}
