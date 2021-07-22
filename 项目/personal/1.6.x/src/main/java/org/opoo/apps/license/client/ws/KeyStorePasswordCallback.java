package org.opoo.apps.license.client.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class KeyStorePasswordCallback implements CallbackHandler {
	private Map<String, String> passwords = new HashMap<String, String>();

	public KeyStorePasswordCallback() {
		passwords.put("client", "clientpass");
		passwords.put("alc", "alcw78z");
		passwords.put("al-client", "alcw78z");
	}

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
			String pass = passwords.get(pc.getIdentifier());
			if (pass != null) {
				// System.out.println(this + " --- " + pc.getIdentifier());
				pc.setPassword(pass);
				return;
			}
		}
	}

	public Map<String, String> getPasswords() {
		return passwords;
	}

	public void setPasswords(Map<String, String> passwords) {
		this.passwords = passwords;
	}
}
