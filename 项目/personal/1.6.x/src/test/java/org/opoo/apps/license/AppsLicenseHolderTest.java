package org.opoo.apps.license;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppsLicenseHolderTest {

	@Test
	public void testGetAppsLicense() throws InterruptedException {
		System.setProperty("license.hasp.enabled", "true");
		//fail("Not yet implemented");
		assertNotNull(AppsLicenseHolder.getAppsLicense());
		assertNotNull(AppsLicenseHolder.getAppsLicense());
		AppsLicenseManager.getInstance().getAppsLicense();
		while(true){
			Thread.sleep(1000L);
			assertNotNull(AppsLicenseManager.getInstance().getAppsLicense());
		}
	}

}
