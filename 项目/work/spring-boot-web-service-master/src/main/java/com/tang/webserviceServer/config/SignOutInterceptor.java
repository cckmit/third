package com.tang.webserviceServer.config;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.tang.webserviceServer.utils.CommonUtils.createHeadersList;
import static com.tang.webserviceServer.utils.SignHolder.clearSign;
import static com.tang.webserviceServer.utils.SignHolder.takeSign;

@Configuration
public class SignOutInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public SignOutInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    @Override
    public void handleMessage(SoapMessage message) {

        List<Header> headers = createHeadersList(takeSign());
        message.getHeaders().addAll(headers);
        clearSign();

    }
}