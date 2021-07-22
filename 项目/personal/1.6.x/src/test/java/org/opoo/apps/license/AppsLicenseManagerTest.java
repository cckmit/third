package org.opoo.apps.license;


import junit.framework.TestCase;

public class AppsLicenseManagerTest extends TestCase {
	public void test0(){
//		JiveHome.initialize();
//		JiveLicenseManager.getInstance();
//		AppsLicenseManager.getInstance();
//		
//		boolean initialized = JiveLicenseManager.getInstance().isInitialized();
//		System.out.println(initialized);
//		System.out.println(JiveLicenseManager.getInstance().isFailed());
		
		boolean initialized2 = AppsLicenseManager.getInstance().isInitialized();
		System.out.println(initialized2);
		System.out.println(AppsLicenseManager.getInstance().isFailed());
	}
}
