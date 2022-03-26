package com.tang.webserviceServer.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;

public class ProxyOtherWebService {
    public static Object invokeOtherWebService(String data,String wsdlUrl) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlUrl);
        Object[] response = client.invoke(new QName("http://soar.hzfc.com/zjjgBankWebService", "payResponse"),
                data);
        System.out.println("result is " + response[0]);
        return response[0];

    }
}
