package com.tang.webserviceServer.service;

import org.springframework.stereotype.Component;

import javax.jws.WebParam;
import javax.jws.WebService;

@Component
@WebService(name = "TestDemo", targetNamespace = "http://service.webserviceServer.tang.com",
        endpointInterface = "com.tang.webserviceServer.service.TestService")
public class TestDemoImpl implements TestService{
    @Override
    public String test(@WebParam String data) {
        return data+"哈哈啊";
    }
}
