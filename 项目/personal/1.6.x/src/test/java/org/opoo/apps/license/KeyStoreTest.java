package org.opoo.apps.license;


import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.security.cert.Certificate;

import org.junit.After;
import org.junit.Before;

public class KeyStoreTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	public void testCreateKeyStore() throws KeyStoreException{
		KeyStore instance = KeyStore.getInstance("jks");
	}
}
