package com.tang.webserviceServer.controller;
import com.tang.webserviceServer.config.ClientSignOutInterceptor;
import com.tang.webserviceServer.config.SignInterceptor;
import com.tang.webserviceServer.pojo.AjaxResult;
import com.tang.webserviceServer.pojo.ProxyRequest;
import com.tang.webserviceServer.pojo.subAccountPayFeedback.Request;
import com.tang.webserviceServer.pojo.subAccountPayFeedback.Response;
import com.tang.webserviceServer.utils.CommonUtils;
import com.tang.webserviceServer.utils.Constants;
import com.tang.webserviceServer.utils.EncryptTools;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;

import static com.tang.webserviceServer.utils.RSAUtil.getSign;
import static com.tang.webserviceServer.utils.RSAUtil.validateSign;
import static com.tang.webserviceServer.utils.SignHolder.takeSign;
import static com.tang.webserviceServer.utils.XmlUtil.convertToXml;
import static com.tang.webserviceServer.utils.XmlUtil.convertXmlStrToObject;

@RestController
@RequestMapping("/proxy")
public class InvokeWebService {
    @Autowired
    private SignInterceptor signInterceptor;
    @RequestMapping("/proxyZF")
    public AjaxResult payResponse(@RequestBody ProxyRequest proxyRequest) throws Exception
    {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8087/services/ws/proxy/api?wsdl");
        client = CommonUtils.buildClient(client,getSign(proxyRequest),signInterceptor);
        Object[] data = client.invoke("proxyMethod", new Object[]{EncryptTools.SM4Encode(convertToXml(proxyRequest))});
        Response res = (Response)convertXmlStrToObject(Response.class,EncryptTools.SM4Decode((String)data[0]));
        if(validateSign(res,takeSign())){
            return AjaxResult.success(res);
        }else{
            throw new Fault(new Exception("签名校验失败！"));
        }
    }

    public static void main(String[] args) throws Exception {
//        String temp = "<?xml version='1.0' encoding='UTF-8'?>\n" +
//                "<Request>" +
//                "<xzqh>341881</xzqh>" +
//                "<hzlsh>022020</hzlsh>" +
//                "<hzjg>1</hzjg>" +
//                "</Request>";
        String temp = "<?xml version='1.0'?>"+
                "<Request>" +
                "<xzqh>341881</xzqh>" +
                "<hzjg>1</hzjg>" +
                "</Request>";
        String temp1 = EncryptTools.SM4Encode(temp);
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8087/services/ws/proxy/api?wsdl");
        client.getOutInterceptors().add(new ClientSignOutInterceptor(temp));
        Object[] response = client.invoke(new QName("http://service.webserviceServer.tang.com", "proxyMethod"),
                temp1);
        System.out.println("result is " + response[0]);
    }

//    public static void main(String[] args) throws Exception {
//        String temp = "<?xml version='1.0' encoding='UTF-8'?>\n" +
//                "<Request>\n" +
//                "<xzqh>341881</xzqh>\n" +
//                "<hzlsh>022020</hzlsh>\n" +
//                "<jgzhid>022020</jgzhid>\n" +
//                "<htcode>022020</htcode>\n" +
//                "<hzjg>1</hzjg>\n" +
//                "<qkms>情况描述</qkms>\n" +
//                "</Request>";
//
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://localhost:9070/sjjr/zjjgBankWebService?wsdl");
//        Object[] response = client.invoke(new QName("http://soar.hzfc.com/zjjgBankWebService", "payResponse"),
//                temp);
//        System.out.println("result is " + response[0]);
//    }

    public void lll(){
        Document dom = DOMUtils.createDocument();

        // 1. 创建3个新元素

        Element requestEl = dom.createElement("Request");

        Element xzqhEl = dom.createElement("xzqh");
        Element jgzhidEl = dom.createElement("jgzhid");
        Element htcodeEl = dom.createElement("htcode");
        Element hzjgEl = dom.createElement("hzjg");
        Element qkmsEl = dom.createElement("qkms");

        // 2. 添加元素内容

        xzqhEl.setTextContent("341881");
        jgzhidEl.setTextContent("022020");
        htcodeEl.setTextContent("022020");
        hzjgEl.setTextContent("1");
        qkmsEl.setTextContent("022020");
        // 3. 确定父子关系
        requestEl.appendChild(xzqhEl);
        requestEl.appendChild(jgzhidEl);
        requestEl.appendChild(htcodeEl);
        requestEl.appendChild(hzjgEl);
        requestEl.appendChild(qkmsEl);
    }
}
