package com.tang.webserviceServer.config;

import com.tang.webserviceServer.pojo.masterAccountStatus.Request;
import com.tang.webserviceServer.utils.RSAUtil;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import java.security.GeneralSecurityException;
import java.util.List;

import static com.tang.webserviceServer.utils.XmlUtil.convertXmlStrToObject;

public class ClientSignOutInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz1zqQHtHvKczHh58ePiRNgOyiHEx6lZDPlvwBTaHmkNlQyyJ06SIlMU1pmGKxILjT7n06nxG7LlFVUN5MkW/jwF39/+drkHM5B0kh+hPQygFjRq81yxvLwolt+Vq7h+CTU0Z1wkFABcTeQQldZkJlTpyx0c3+jq0o47wIFjq5fwIDAQAB";
    private  String data;
    public ClientSignOutInterceptor(String data) {
        //准备发送阶段
        super(Phase.PREPARE_SEND);
        this.data = data;
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {

        Document dom = DOMUtils.createDocument();

        // 1. 创建3个新元素
        Element rootEl = dom.createElement("root");
        Element requestEl = dom.createElement("sign");
        Element userNameEl = dom.createElement("userName");
        String key = null;
//        Request objectRequest = (Request)convertXmlStrToObject(Request.class, data);
        try {
            key = RSAUtil.encrypt(data,publicKey);
//            key = "d91a12e94823ee6478f90b520b6fc1f6a8208498e4c78a3bcd982292ae6825f4";
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        userNameEl.setTextContent(key);
        requestEl.appendChild(userNameEl);
        rootEl.appendChild(requestEl);
        System.out.println(requestEl.toString());
// 4.把新元素，放入header中

        Header header = new Header(new QName("http://service.webserviceServer.tang.com"), rootEl);
        List<Header> headers = soapMessage.getHeaders();
        Element auth = (Element)header.getObject();
        NodeList nodeList = auth.getElementsByTagName("sign");
        System.out.println(nodeList.getLength());
        headers.add(header);
    }
}
