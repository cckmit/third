package org.opoo.apps.dv.connector;

import org.apache.commons.httpclient.HttpClient;

public class DummyHttpClientFactory extends HttpClientFactory {

	@Override
	public HttpClient getNewClient() {
		return new HttpClient();
	}

}
