package com.tang.webserviceServer.service;

import com.tang.webserviceServer.pojo.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;

@WebService(name = "ServerServiceDemo", targetNamespace = "http://service.webserviceServer.tang.com")
public interface ServerServiceDemo {
    @WebMethod
    String queryJgzh(@WebParam String var1) throws GeneralSecurityException;

    @WebMethod
    String queryJgzzh(@WebParam String var1);

    @WebMethod
    String detailJgzh(@WebParam String var1);

    @WebMethod
    String detailJgzzh(@WebParam String var1);

    @WebMethod
    String matchJgzzh(@WebParam String var1);

    @WebMethod
    String payJgzzh(@WebParam String var1);

    @WebMethod
    User getUserById(@WebParam Integer var1);

    @WebMethod
    Integer insertUser(@WebParam String var1);
}
