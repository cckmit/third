package org.opoo.apps.license.client.ws;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.opoo.apps.license.AppsLicenseLog;

public class WsUtils {
	
	@SuppressWarnings("unchecked")
	public static <T> T createService(String serviceURL, Class<T> serviceClass){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(serviceClass);
		factory.setAddress(serviceURL);
		T service = (T) factory.create();
		
		InvocationHandler handler = Proxy.getInvocationHandler(service);
		JaxWsClientProxy a = (JaxWsClientProxy) handler;
		Client client = a.getClient();
		Endpoint endpoint = client.getEndpoint();
		
		addInInterceptors(endpoint.getInInterceptors());
		addOutInterceptors(endpoint.getOutInterceptors());
		
		return service;
	}


	@SuppressWarnings({ "rawtypes" })
	static void addInInterceptors(List<Interceptor> inInterceptors) {
		Map<String,Object> inProps = new HashMap<String,Object>();
		inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.TIMESTAMP 
				+ " " + WSHandlerConstants.SIGNATURE
				+ " " + WSHandlerConstants.ENCRYPT);
		//配置多用途，服务器的公钥、客户端的私钥都存储在同一个keystore中
		inProps.put(WSHandlerConstants.SIG_PROP_FILE, "org/opoo/apps/license/client/ws/client.properties");
		inProps.put(WSHandlerConstants.DEC_PROP_FILE, "org/opoo/apps/license/client/ws/client.properties");
		inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, KeyStorePasswordCallback.class.getName());
		
		WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
		inInterceptors.add(wssIn);
		inInterceptors.add(new SAAJInInterceptor());
		if(AppsLicenseLog.LOG.isDebugEnabled()){
			inInterceptors.add(new LoggingInInterceptor());
		}
	}

	@SuppressWarnings("rawtypes")
	static void addOutInterceptors(List<Interceptor> outInterceptors) {
		Map<String,Object> outProps = new HashMap<String,Object>();
		// how to configure the properties is outlined below;
		
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.TIMESTAMP 
				+ " " + WSHandlerConstants.SIGNATURE
				+ " " + WSHandlerConstants.ENCRYPT);
		outProps.put(WSHandlerConstants.USER, "al-client");
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, KeyStorePasswordCallback.class.getName());
		outProps.put(WSHandlerConstants.SIG_PROP_FILE, "org/opoo/apps/license/client/ws/client.properties");
		outProps.put(WSHandlerConstants.ENCRYPTION_USER, "al-server");
		outProps.put(WSHandlerConstants.ENC_PROP_FILE, "org/opoo/apps/license/client/ws/client.properties");
		
		//outProps.put(WSHandlerConstants.SIGNATURE_PARTS, "Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp;{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");
		//outProps.put(WSHandlerConstants.ENCRYPTION_PARTS, "{Element}{http://www.w3.org/2000/09/xmldsig#}Signature;{Content}{http://schemas.xmlsoap.org/soap/envelope/}Body");
		//outProps.put(WSHandlerConstants.ENC_SYM_ALGO, "http://www.w3.org/2001/04/xmlenc#tripledes-cbc");

		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
		outInterceptors.add(wssOut);
		outInterceptors.add(new SAAJOutInterceptor());
		if(AppsLicenseLog.LOG.isDebugEnabled()){
			outInterceptors.add(new LoggingOutInterceptor());
		}
	}
	
	
	public static void main(String[] args){
//		LicenseWSV2 service = ServiceUtils.createService("http://localhost:8080/opoo-apps/services/licenseV2", LicenseWSV2.class);
//		System.out.println(service.getVersion());
//		String sessionId = service.login("touchstone", "0000-0000-0000-000000");
//		
//		System.out.println("SessionId: " + sessionId);
//		String license = service.getLicense(sessionId);
//		
//		System.out.println("Encoded license string: " + license);
		
		LicenseV2WS licenseWSV2 = WsUtils.createService("http://192.168.18.8/als/services/licenseV2", LicenseV2WS.class);
		String version = licenseWSV2.getVersion();
		System.out.println(version);
		
//		LicenseWS ws = WsUtils.createService("http://192.168.18.8/als/services/license", LicenseWS.class);
//		String license2 = ws.getLicense("touchstone", "00000000000000000000");
//		System.out.println(license2);
	}
}
