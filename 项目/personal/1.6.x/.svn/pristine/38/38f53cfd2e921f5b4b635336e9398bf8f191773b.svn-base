package org.opoo.apps.license.client.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(portName = "LicenseServiceV2Port", serviceName = "LicenseServiceV2",
        targetNamespace = "http://apps.opoo.org/license/webservices")
public interface LicenseV2WS extends LicenseV2{
	@WebMethod
	String login(@WebParam(name="productId") String productId,
			@WebParam(name = "instanceId") String instanceId);
	
	@WebMethod
	void logoff(@WebParam(name="sessionId") String sessionId);
	
	@WebMethod
	String getLicense(@WebParam(name="sessionId") String sessionId);
	
	@WebMethod
	void validate(@WebParam(name="sessionId") String sessionId, 
			@WebParam(name = "validationString")String validationString);
	
	@WebMethod
	String getVersion();
}
