package org.opoo.apps.license.client.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@Deprecated
@WebService(portName = "LicenseServicePort", serviceName = "LicenseService",
        targetNamespace = "http://apps.opoo.org/license/webservices")
public interface LicenseWS {

	@WebMethod
	int logout(@WebParam(name="productId") String productId,
			@WebParam(name = "instanceId") String instanceId);

	@WebMethod
	String getLicense(@WebParam(name="productId") String productId,
			@WebParam(name = "instanceId") String instanceId);

	@WebMethod
	int validate(@WebParam(name="productId") String productId,
			@WebParam(name = "instanceId") String instanceId,
			@WebParam(name = "validationString") String validationString);
}
