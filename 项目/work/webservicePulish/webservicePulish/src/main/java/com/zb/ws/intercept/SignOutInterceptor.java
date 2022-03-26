package com.zb.ws.intercept;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.zb.ws.utils.CommonUtils.createHeadersList;
import static com.zb.ws.utils.SignHolder.clearSign;
import static com.zb.ws.utils.SignHolder.takeSign;


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