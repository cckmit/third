package org.opoo.apps.license.client.ws;

import org.junit.Test;

public class HttpsClientLicenseV2RestTest {
	@Test
	public void test() {
		System.setProperty("license.httpclient.ssl.debug", "true");
		HttpsClientLicenseV2Rest rest = new HttpsClientLicenseV2Rest("lichttps://192.168.18.5:58443/als/services/restSSL");
		String version = rest.getVersion();
		System.out.println(version);
//		String sessionId = rest.login("opoo-apps", "abc-test");
//		System.out.println(sessionId);
//		String license = rest.getLicense(sessionId);
//		System.out.println(license);
		rest.validate("opoo-apps-fake-sessionid", "ssss");
	}
}
