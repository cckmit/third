package com.tang.webserviceServer.service;

import com.tang.webserviceServer.pojo.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "TestDemo", targetNamespace = "http://service.webserviceServer.tang.com")
public interface TestService {
    @WebMethod
    String test(@WebParam String data);
}
