package com.tang.webserviceServer.service;

import com.tang.webserviceServer.pojo.ProxyRequest;
import com.tang.webserviceServer.pojo.masterAccountStatus.Request;
import com.tang.webserviceServer.pojo.masterAccountStatus.Response;
import com.tang.webserviceServer.utils.EncryptTools;
import org.apache.cxf.interceptor.Fault;
import org.springframework.stereotype.Component;

import javax.jws.WebParam;
import javax.jws.WebService;

import static com.tang.webserviceServer.utils.ProxyOtherWebService.invokeOtherWebService;
import static com.tang.webserviceServer.utils.RSAUtil.getSign;
import static com.tang.webserviceServer.utils.RSAUtil.validateSign;
import static com.tang.webserviceServer.utils.SignHolder.setSign;
import static com.tang.webserviceServer.utils.SignHolder.takeSign;
import static com.tang.webserviceServer.utils.XmlUtil.convertXmlStrToObject;

@Component
@WebService(name = "ProxyWebService", targetNamespace = "http://service.webserviceServer.tang.com",
        endpointInterface = "com.tang.webserviceServer.service.ProxyWebService")
public class ProxyWebServiceImpl implements ProxyWebService{


    public String proxyMethod(@WebParam String data) throws Exception {
        String decodeData =  EncryptTools.SM4Decode(data);
        ProxyRequest objectRequest = (ProxyRequest)convertXmlStrToObject(ProxyRequest.class, decodeData);
        if(validateSign(objectRequest,takeSign())){
            String result=(String)invokeOtherWebService(decodeData,"http://localhost:9070/sjjr/zjjgBankWebService?wsdl");
            Response objectResponse = (Response) convertXmlStrToObject(Response.class,result);
            setSign(getSign(objectResponse));
            System.out.println("-----"+EncryptTools.SM4Encode(result));
            return EncryptTools.SM4Encode(result);
        }else{
            throw new Fault(new Exception("签名校验失败！"));
        }

    }
}
