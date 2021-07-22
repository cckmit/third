package org.opoo.apps.license.loader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.license.AppsLicenseLog;
import org.opoo.apps.license.client.ws.HttpsClientLicenseV2Rest;
import org.opoo.apps.license.client.ws.LicenseV2;
import org.opoo.apps.license.client.ws.WsUrlProvider;

public class WsLicenseLoaderV2RestSSL extends WsLicenseLoaderV2WS {
	private static final Log log = AppsLicenseLog.LOG;
	private HttpsClientLicenseV2Rest httpsClientLicenseV2Rest;
	@Override
	public String getServiceURL() {
		if(StringUtils.isBlank(serviceURL)){
			serviceURL = AppsGlobals.getSetupProperty("license.restURL");
		}
		if(StringUtils.isBlank(serviceURL)){
			serviceURL = WsUrlProvider.getServiceURL(WsUrlProvider.RESTFUL_SSL_WS_MAGIC_NUMBER);
		}
		return serviceURL;
	}

	@Override
	protected LicenseV2 getService() {
		if(service == null){
			String url = getServiceURL();
			if(StringUtils.isBlank(url)){
				log.error("ServiceURL not found.");
				return null;
			}
			httpsClientLicenseV2Rest = new HttpsClientLicenseV2Rest(serviceURL);//WsUtils.createService(url, LicenseWSV2.class);
			service = httpsClientLicenseV2Rest;
		}
		return service;
	}

	@Override
	public void stopListen() {
		super.stopListen();
		if(httpsClientLicenseV2Rest != null){
			httpsClientLicenseV2Rest.shutdown();
			httpsClientLicenseV2Rest = null;
		}
	}
}
