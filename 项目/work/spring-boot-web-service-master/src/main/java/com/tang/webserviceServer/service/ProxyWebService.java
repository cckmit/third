package com.tang.webserviceServer.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "ProxyWebService", targetNamespace = "http://service.webserviceServer.tang.com")
public interface ProxyWebService {
    String proxyMethod(@WebParam String data) throws Exception;;
}
