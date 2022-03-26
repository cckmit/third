import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static utils.CommonUtils.createHeadersList;
import static utils.SignHolder.clearSign;
import static utils.SignHolder.takeSign;

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