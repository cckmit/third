package com.tang.webserviceServer.controller.noEncode;


import com.tang.webserviceServer.config.SignInterceptor;
import com.tang.webserviceServer.factory.JaxWsDynamicClientLocalFactory;
import com.tang.webserviceServer.pojo.AjaxResult;
import com.tang.webserviceServer.pojo.subAccountPayFeedback.Request;
import com.tang.webserviceServer.pojo.subAccountPayFeedback.Response;
import com.tang.webserviceServer.utils.CommonUtils;
import com.tang.webserviceServer.utils.Constants;
import com.tang.webserviceServer.utils.EncryptTools;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tang.webserviceServer.utils.RSAUtil.getSign;
import static com.tang.webserviceServer.utils.RSAUtil.validateSign;
import static com.tang.webserviceServer.utils.SignHolder.takeSign;
import static com.tang.webserviceServer.utils.XmlUtil.convertToXml;
import static com.tang.webserviceServer.utils.XmlUtil.convertXmlStrToObject;


/**
 * 通用请求处理
 *
 * @author hzzfxx
 */
@RestController
@RequestMapping("/noEncode")
public class WebService_noController
{
    @Autowired
    SignInterceptor signInterceptor;

        @RequestMapping("/fetchInfo")
    public String fetchInfo(String msg) throws Exception
    {        JaxWsDynamicClientLocalFactory dcf = JaxWsDynamicClientLocalFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/spi/fetchInfo?wsdl");
        Object[] data = client.invoke("fetchInfo", msg);
        System.out.println(data[0]);
        return data[0].toString();
    }




}
