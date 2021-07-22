package org.opoo.apps.license.application.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@Deprecated
@WebService(targetNamespace = "http://server.application.license.apps.opoo.org/", name = "LicenseService")
public interface LicenseWS {

  @WebResult(name = "return", targetNamespace = "")
  @WebMethod
  public boolean validateLicense(
      @WebParam(name = "arg0", targetNamespace = "")
      java.lang.String licenseString
  );
}
