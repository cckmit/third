package org.opoo.apps.license.loader;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseProvider;

public interface LicenseLoader {

	License loadLicense(LicenseProvider provider);
	
	void check(LicenseProvider provider, License license, CheckCallback callback);
	
	void startListen(LicenseProvider provider);
	
	void stopListen();
}
