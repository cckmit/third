package com.zb.ws.intercept;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

import static com.zb.ws.utils.SignHolder.setSign;

@Configuration
public class SignInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public SignInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    @Override
    public void handleMessage(SoapMessage message) {
        List<Header> headers = message.getHeaders();
        for (Header header : headers) {
            Element auth = (Element)header.getObject();
            NodeList nodeList = auth.getElementsByTagName("sign");
            if(null != nodeList && nodeList.getLength() != 0){
                setSign(nodeList.item(0).getTextContent());
                return;
            }
        }
        message.get(AbstractHTTPDestination.HTTP_REQUEST);

        throw new Fault(new Exception("请求未包含签名！"));

    }
}