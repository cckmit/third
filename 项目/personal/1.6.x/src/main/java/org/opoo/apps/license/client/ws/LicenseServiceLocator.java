package org.opoo.apps.license.client.ws;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.license.ws.DummyServiceUrlProvider;
import org.opoo.apps.license.ws.ServiceUrlProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class LicenseServiceLocator implements LicenseServiceProvider, InitializingBean{
	private static final Log log = LogFactory.getLog(LicenseServiceLocator.class);
	
	private ServiceUrlProvider serviceUrlProvider;
	private LicenseWS service;
	
	public LicenseServiceLocator(){
	}
	
	public ServiceUrlProvider getServiceUrlProvider() {
		return serviceUrlProvider;
	}

	public void setServiceUrlProvider(ServiceUrlProvider serviceUrlProvider) {
		this.serviceUrlProvider = serviceUrlProvider;
	}



	public LicenseWS getService(){
		if(service == null){
			String serviceURL = serviceUrlProvider.getServiceURL();
			if(serviceURL == null){
				//throw new RuntimeException("无法获取WS的ServiceURL。");
				//log.warn("无法获取WS的ServiceURL。");
				return null;
			}else{
				log.info("ServiceURL: " + serviceURL);
			}
			//service = createService(serviceURL);
			service = WsUtils.createService(serviceURL, LicenseWS.class);
		}
		return service;
	}
	

//	private LicenseWS createService(String serviceURL){
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(LicenseWS.class);
//		factory.setAddress(serviceURL);
//		LicenseWS service = (LicenseWS) factory.create();
//		
//		InvocationHandler handler = Proxy.getInvocationHandler(service);
//		JaxWsClientProxy a = (JaxWsClientProxy) handler;
//		Client client = a.getClient();
//		Endpoint endpoint = client.getEndpoint();
//		
//		addInInterceptors(endpoint.getInInterceptors());
//		addOutInterceptors(endpoint.getOutInterceptors());
//		
//		return service;
//	}
//
//
//	@SuppressWarnings("unchecked")
//	protected void addInInterceptors(List<Interceptor> inInterceptors) {
//		Map<String,Object> inProps = new HashMap<String,Object>();
//		inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.TIMESTAMP 
//				+ " " + WSHandlerConstants.SIGNATURE
//				+ " " + WSHandlerConstants.ENCRYPT);
//		inProps.put(WSHandlerConstants.SIG_PROP_FILE, "org/opoo/apps/license/client/ws/client.properties");
//		inProps.put(WSHandlerConstants.DEC_PROP_FILE, "org/opoo/apps/license/client/ws/client.properties");
//		inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, KeyStorePasswordCallback.class.getName());
//		
//		WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
//		inInterceptors.add(wssIn);
//		inInterceptors.add(new SAAJInInterceptor());
//		if(AppsLicenseLog.LOG.isDebugEnabled()){
//			inInterceptors.add(new LoggingInInterceptor());
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	protected void addOutInterceptors(List<Interceptor> outInterceptors) {
//		Map<String,Object> outProps = new HashMap<String,Object>();
//		// how to configure the properties is outlined below;
//		
//		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.TIMESTAMP 
//				+ " " + WSHandlerConstants.SIGNATURE
//				+ " " + WSHandlerConstants.ENCRYPT);
//		outProps.put(WSHandlerConstants.USER, "al-client");
//		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, KeyStorePasswordCallback.class.getName());
//		outProps.put(WSHandlerConstants.SIG_PROP_FILE, "org/opoo/apps/license/client/ws/client.properties");
//		outProps.put(WSHandlerConstants.ENCRYPTION_USER, "al-server");
//		outProps.put(WSHandlerConstants.ENC_PROP_FILE, "org/opoo/apps/license/client/ws/client.properties");
//		
//		//outProps.put(WSHandlerConstants.SIGNATURE_PARTS, "Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp;{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");
//		//outProps.put(WSHandlerConstants.ENCRYPTION_PARTS, "{Element}{http://www.w3.org/2000/09/xmldsig#}Signature;{Content}{http://schemas.xmlsoap.org/soap/envelope/}Body");
//		//outProps.put(WSHandlerConstants.ENC_SYM_ALGO, "http://www.w3.org/2001/04/xmlenc#tripledes-cbc");
//
//		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
//		outInterceptors.add(wssOut);
//		outInterceptors.add(new SAAJOutInterceptor());
//		if(AppsLicenseLog.LOG.isDebugEnabled()){
//			outInterceptors.add(new LoggingOutInterceptor());
//		}
//	}
//	
	


	public void afterPropertiesSet() throws Exception {
		Assert.notNull(serviceUrlProvider, "ServiceUrlProvider is required.");
	}
	
	
	public static LicenseServiceLocator createDefaultServiceProvider(String serviceURL) throws Exception{
		ServiceUrlProvider sup = null;
		if(serviceURL != null){
			sup = new DummyServiceUrlProvider(serviceURL);
		}else{
			sup = LicenseServiceUrlProvider.createDefaultServiceUrlProvider();
		}
		
		LicenseServiceLocator serviceLocator = new LicenseServiceLocator();
		serviceLocator.setServiceUrlProvider(sup);
		serviceLocator.afterPropertiesSet();
		
		return serviceLocator;
	}
	public static LicenseServiceLocator createDefaultServiceProvider() throws Exception{
		return createDefaultServiceProvider(null);
	}
	
	
	public static void main(String[] args) throws Exception{
		LicenseServiceLocator serviceLocator = createDefaultServiceProvider();
		
		LicenseWS service = serviceLocator.getService();
		System.out.println(service);
		
		String instanceId = "abcdllllllllllllll";
		
		//String license = service.getLicense(instanceId);
		//int status2 = service.validate(instanceId, "sssssssssssssss");
		//int status3 = service.logout("opoo-apps", instanceId);
		//System.out.println("return code: " + status3);
		
		String license = service.getLicense("opoo-apps", instanceId);
		System.out.println(license);
	}
}
