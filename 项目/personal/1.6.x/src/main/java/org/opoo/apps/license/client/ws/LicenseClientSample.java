package org.opoo.apps.license.client.ws;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.springframework.util.StopWatch;

/**
 * @author lcj
 * @deprecated
 */
public class LicenseClientSample {
	
	public static void main(String[] args){
		StopWatch sw = new StopWatch();
		sw.start("创建工厂");
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(LicenseWS.class);
		factory.setAddress("http://localhost:8080/ws-sample/services/license");
		LicenseWS service = (LicenseWS) factory.create();
		
		sw.stop();
		sw.start("设置拦截器");
		
		InvocationHandler handler = Proxy.getInvocationHandler(service);
		System.out.println(handler instanceof JaxWsClientProxy);
		JaxWsClientProxy a = (JaxWsClientProxy) handler;
		Client client = a.getClient();
		Endpoint endpoint = client.getEndpoint();
		
		Map<String,Object> outProps = new HashMap<String,Object>();
		// how to configure the properties is outlined below;
		
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.TIMESTAMP 
				+ " " + WSHandlerConstants.SIGNATURE
				+ " " + WSHandlerConstants.ENCRYPT);
		outProps.put(WSHandlerConstants.USER, "client");
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientCallbackHandler.class.getName());
		outProps.put(WSHandlerConstants.SIG_PROP_FILE, "client_sign.properties");
		outProps.put(WSHandlerConstants.ENCRYPTION_USER, "server");
		outProps.put(WSHandlerConstants.ENC_PROP_FILE, "client_sign.properties");
		
		//outProps.put(WSHandlerConstants.SIGNATURE_PARTS, "Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp;{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");
		//outProps.put(WSHandlerConstants.ENCRYPTION_PARTS, "{Element}{http://www.w3.org/2000/09/xmldsig#}Signature;{Content}{http://schemas.xmlsoap.org/soap/envelope/}Body");
		//outProps.put(WSHandlerConstants.ENC_SYM_ALGO, "http://www.w3.org/2001/04/xmlenc#tripledes-cbc");

		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
		endpoint.getOutInterceptors().add(wssOut);
		endpoint.getOutInterceptors().add(new SAAJOutInterceptor());
		endpoint.getOutInterceptors().add(new LoggingOutInterceptor());
		
		
		Map<String,Object> inProps = new HashMap<String,Object>();
		inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.TIMESTAMP 
				+ " " + WSHandlerConstants.SIGNATURE
				+ " " + WSHandlerConstants.ENCRYPT);
		inProps.put(WSHandlerConstants.SIG_PROP_FILE, "client_sign.properties");
		inProps.put(WSHandlerConstants.DEC_PROP_FILE, "client_sign.properties");
		inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientCallbackHandler.class.getName());
		
		WSS4JInInterceptor inInterceptor = new WSS4JInInterceptor(inProps);
		endpoint.getInInterceptors().add(new SAAJInInterceptor());
		endpoint.getInInterceptors().add(inInterceptor);
		endpoint.getInInterceptors().add(new LoggingInInterceptor());
		
		sw.stop();
		
		System.out.println(service);
		
		sw.start("调用Service");
		String license = service.getLicense("opoo-apps", "abcde");
		sw.stop();
		
		System.out.println(license);
		System.out.println(sw.prettyPrint());
	}
	
	public static class ClientCallbackHandler implements CallbackHandler{
		private static final Map<String,String> pwd = new HashMap<String, String>();
		static{
			pwd.put("client", "clientpass");
		}
		public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
			WSPasswordCallback callback = (WSPasswordCallback) callbacks[0];
			System.out.println(this + " --- " + callback.getIdentifier());
			callback.setPassword(pwd.get(callback.getIdentifier()));
		}
	}
}
