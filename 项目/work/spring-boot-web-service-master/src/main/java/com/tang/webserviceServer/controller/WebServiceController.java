package com.tang.webserviceServer.controller;


import com.tang.webserviceServer.config.SignInterceptor;
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
@RequestMapping("/wsinter")
public class WebServiceController
{


    @Autowired
    SignInterceptor signInterceptor;

    @RequestMapping("/payResponse")
    public AjaxResult payResponse(@RequestBody Request request) throws Exception
    {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:9070/sjjr/zjjgBankWebService?wsdl");
        client = CommonUtils.buildClient(client,getSign(request),signInterceptor);
        Object[] data = client.invoke(Constants.WsMethod.SUB_ACCOUNT_PAY_FEEDBACK, new Object[]{EncryptTools.SM4Encode(convertToXml(request))});
        Response res = (Response)convertXmlStrToObject(Response.class,EncryptTools.SM4Decode((String)data[0]));
        if(validateSign(res,takeSign())){
            return AjaxResult.success(res);
        }else{
            throw new Fault(new Exception("签名校验失败！"));
        }
    }




}
