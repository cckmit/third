package org.opoo.apps.license.application.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.opoo.apps.license.AppsLicenseManager.AppsLicenseProperty;
import org.opoo.apps.license.io.AppsLicenseWriter;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;

@Deprecated
public class LicenseWSClient {
	private static final Log log = LogFactory.getLog(LicenseWSClient.class);
	private Map<String, LicenseWS> services = new HashMap<String, LicenseWS>(1);
	
//	public LicenseWS getService() {
//		/*
//		if (webservice != null) {
//			return webservice;
//		} else {
//			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//			factory.setServiceClass(LicenseWS.class);
//			factory.setAddress(getServiceURL());
//			return (LicenseWS) factory.create();
//		}*/
//		return getService(getServiceURL());
//	}
	
	private LicenseWS getService(String serviceURL) {
		LicenseWS service = services.get(serviceURL);
		if(service == null){
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(LicenseWS.class);
			factory.setAddress(serviceURL);
			service = (LicenseWS) factory.create();
			if(service != null){
				services.put(serviceURL, service);
			}else{
				throw new LicenseException("无法获取 LicenseWS。");
			}
		}
		return service;
	}
	

//	public String getServiceURL() {
//		return "http://localhost:8080/opoo-apps/services/license";
//	}
	
	public boolean validate(License license) throws LicenseException{
		String validateURL = license.getProperties().get(AppsLicenseProperty.validateURL.name());
		String signedLicense = null;
		LicenseWS service = null;
		List<String> errors = new ArrayList<String>();
		int tryCount = 0;

		if (StringUtils.isBlank(validateURL)) {
			throw new LicenseException("当前 License 不是有效的开发版授权。");
		}

		try {
			signedLicense = AppsLicenseWriter.encodeSignedLicense(license, -1);
			//log.debug("已签名的 License 编码：" + signedLicense);
		} catch (IOException e) {
			throw new LicenseException("准备 License 校验数据失败。", e);
		}

		service = getService(validateURL);
		boolean success = false;

		do {
			success = submitValidate(signedLicense, service, validateURL, errors);

			if (success) {
				break;
			}

			tryCount++;
			retrySleep(getValidateRetryPause());
			log.warn("Retrying remote validate by webservice (retry #" + tryCount + ")...");
		} while (tryCount < getValidateMaxRetries());

		if (!errors.isEmpty()) {
			for (String error : errors) {
				log.error(error);
			}
		}

		return success;
	}
	
	
	public static boolean submitValidate(String signedLicenseString, LicenseWS service, 
			String serviceURL, List<String> errors) {
		boolean b = false;
		try {
			b = service.validateLicense(signedLicenseString);
			log.debug("Received response: " + b);
		} catch (SOAPFaultException soapFault) {
			log.error("SOAPFaultException: " + soapFault);
			Throwable cause = soapFault.getCause();
			String soapFailureMessage = cause.getMessage();
			if (soapFailureMessage == null)
				soapFailureMessage = soapFault.getMessage();

			if (soapFailureMessage.toLowerCase().contains("connection refused")) {
				String errorMessage = "Cannot reach " + serviceURL;
				errors.add(errorMessage);
			} else {
				errors.add(soapFailureMessage);
			}
		}catch (Exception exception) {
			log.error(exception);
			Throwable cause = exception.getCause();
			String internalServerError;
			if (cause != null)
				internalServerError = "Internal error - '" + cause.getMessage() + "'";
			else
				internalServerError = "Internal error - '" + exception.getMessage() + "'";
			errors.add(internalServerError);
		}
		return b;
	}
	
	private int getValidateMaxRetries(){
		return 3;
		//return AppsGlobals.getProperty("license.ws.validate.retries", 20);
	}

	private int getValidateRetryPause() {
		return 1;
		//return AppsGlobals.getProperty("license.ws.validate.retries.pause", 900);
	}
	
	private void retrySleep(long seconds) {
		try {
			Thread.sleep(seconds * 1000L);
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) throws Exception{
		String licenseString = "";//"PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPGxpY2Vuc2UgaWQ9IjE5ODEyMzAiIG5hbWU9Im1pbGVzdG9uZSIgZWRpdGlvbj0ic3RhbmRhcmQiIGNyZWF0aW9uRGF0ZT0iMDUvMjEvMjAxMCIgdmVyc2lvbj0iMS4wLjAgREVWIiB0eXBlPSJDT01NRVJDSUFMIj48Y2xpZW50IG5hbWU9Isvvw/q0qyIgY29tcGFueT0iye7b2srQuuzG7NDFz6K8vMr109DP3rmry74iLz48bW9kdWxlIG5hbWU9ImNvcmUiLz48bW9kdWxlIG5hbWU9ImJhc2UiLz48cHJvcGVydHkgbmFtZT0ibnVtQ2x1c3Rlck1lbWJlcnMiPjE8L3Byb3BlcnR5Pjxwcm9wZXJ0eSBuYW1lPSJleHBpcmF0aW9uRGF0ZSI+MDcvMTUvMjAxMDwvcHJvcGVydHk+PHByb3BlcnR5IG5hbWU9InJlcG9ydFVybCI+aHR0cDovL3JlcG9ydC5yZWRmbGFnc29mdC5jbi9yZXBvcnQuanNwYTwvcHJvcGVydHk+PHByb3BlcnR5IG5hbWU9ImdyYW50ZWRJUCI+MTkyLjE2OC4xOC4xMDg8L3Byb3BlcnR5Pjxwcm9wZXJ0eSBuYW1lPSJ2YWxpZGF0ZVVSTCI+aHR0cDovL2xvY2FsaG9zdDo4MDgwL29wb28tYXBwcy9zZXJ2aWNlcy9saWNlbnNlPC9wcm9wZXJ0eT48cHJvcGVydHkgbmFtZT0iZGV2TW9kZSI+dHJ1ZTwvcHJvcGVydHk+PHNpZ25hdHVyZT4zMDJjMDIxNDc5NDRhM2Y3MmZmNDI2MWJjYTQzNGVkNjkxNGFjN2FkM2Y0YWI4ZjIwMjE0MzJlOGRhOWJkZDkxMGEwZTc4ODUyYmQ3YTMxNjM3NDA4OGE3ZDgwMjwvc2lnbmF0dXJlPjwvbGljZW5zZT4=";
	
		LicenseWSClient client = new LicenseWSClient();
		LicenseWS service = client.getService("http://localhost:8080/opoo-apps/services/license");
		boolean valid = service.validateLicense(licenseString);
		System.out.println(valid);
		
		
//		URL wsdlURL = new URL("http://localhost:8080/opoo-apps/services/license?wsdl");
//		QName SERVICE_NAME = new QName("http://impl.server.application.license.apps.opoo.org/", "LicenseServiceImplService");
//		Service service = Service.create(wsdlURL, SERVICE_NAME);
//		LicenseWS client = service.getPort(LicenseWS.class);
		
	}
}
