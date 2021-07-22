package org.opoo.apps.license.client.ws;

import static org.junit.Assert.*;

import org.junit.Test;

public class WsUtilsTest {

	@Test
	public void test() {
		LicenseWS ws = WsUtils.createService("http://192.168.18.8/als/services/license", LicenseWS.class);
		String license2 = ws.getLicense("touchstone", "00000000000000000000");
		System.out.println(license2);
	}

}
