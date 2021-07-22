package org.opoo.apps.dv.connector;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;

public class HttpClientFactory {
    private HttpConnectionManager httpConnectionManager;

    public HttpClient getNewClient() {
        return new HttpClient(httpConnectionManager);
    }

    public void setHttpConnectionManager(HttpConnectionManager httpConnectionManager) {
        this.httpConnectionManager = httpConnectionManager;
    }
}
