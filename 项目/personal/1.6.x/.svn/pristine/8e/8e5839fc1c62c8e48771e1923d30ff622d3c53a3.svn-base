package org.opoo.apps.license.loader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.license.AppsLicenseLog;
import org.opoo.apps.license.DefaultLicenseProvider;
import org.opoo.apps.license.client.ws.LicenseServiceLocator;
import org.opoo.apps.license.client.ws.LicenseServiceProvider;
import org.opoo.apps.license.client.ws.LicenseWS;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.LicenseInitializationException;
import com.jivesoftware.license.LicenseManager;
import com.jivesoftware.license.LicenseProvider;
import com.jivesoftware.license.io.LicenseReader;

/**
 * 
 * @author lcj
 * @deprecated 将在2.0版本中移除。
 */
public class NetworkLicenseLoader implements LicenseLoader {
	private static final Log log = AppsLicenseLog.LOG;
	
	private static final int WS_INVOKE_SUCCESS = 0;
	private static final int WS_CHECK_FAILED = -1;
	private int lastValidateStatus = WS_INVOKE_SUCCESS;
	private Timer listenTimer;
	
	private LicenseServiceProvider licenseServiceProvider;
	private LicenseWS service;
	private String serviceURL;
	
	public String getServiceURL() {
		if(StringUtils.isBlank(serviceURL)){
			serviceURL = AppsGlobals.getSetupProperty("license.serviceURL");
		}
		return serviceURL;
	}
	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
	public void setService(LicenseWS service) {
		this.service = service;
	}
	
	public void setLicenseServiceProvider(LicenseServiceProvider licenseServiceProvider) {
		this.licenseServiceProvider = licenseServiceProvider;
	}
	
	
	protected LicenseServiceProvider getLicenseServiceProvider(){
		if(licenseServiceProvider == null){
			try {
				licenseServiceProvider = LicenseServiceLocator.createDefaultServiceProvider(getServiceURL());
			} catch (Exception e) {
				log.error("cannot create LicenseServiceProvider.", e);
				return null;
			}
		}
		return licenseServiceProvider;
	}
	
	protected LicenseWS getService(){
		if(service == null){
			LicenseServiceProvider licenseServiceProvider = getLicenseServiceProvider();
			if(licenseServiceProvider == null){
				log.error("LicenseServiceProvider not found.");
				return null;
			}
			
			service = licenseServiceProvider.getService();
		}
		return service;
	}
	

	public License loadLicense(LicenseProvider provider) {
		try {
			//runtime exception must be catch
			Reader reader = getLicenseReader(provider);
			if(reader == null){
				return null;
			}
			
			
			LicenseReader licenseReader = new LicenseReader();
			License l = licenseReader.read(reader);
			
			//System.out.println(l);

			// Validate against all of the validators
			// if there are errors return all of them.
			Collection<LicenseException> exceptions = LicenseManager.validate(provider, l);

			// Throw an initialization exception if there were validation errors.
			if (!exceptions.isEmpty()) {
			    throw new LicenseInitializationException("License Validation Failed", exceptions);
			}

			// Make sure we report the right version when displayed, not the license version
			l.setVersion(provider.getVersion());
			
			return l;
		} catch (LicenseException e) {
			log.debug(e.getMessage(), e);
		} catch (IOException e) {
			log.debug(e.getMessage(), e);
		} catch(Exception e){
			log.debug(e.getMessage(), e);
		}
		return null;
	}
	

	
	private Reader getLicenseReader(LicenseProvider provider){
		LicenseWS service = getService();
		if(service == null){
			log.error("service is null.");
			return null;
		}
		
		//call webservice, maybe runtime exception throwed
		String message = service.getLicense(provider.getName(), AppsHome.getNodeID().toString());
		if(message == null){
			log.error("get license response null.");
			return null;
		}else{
//			log.debug("response: " + message);
		}
		
		int p = message.indexOf('|');
		if(p == -1){
			log.error("get license response format error");
			return null;
		}
		
		int code = WS_INVOKE_SUCCESS;
		try{
			code = Integer.parseInt(message.substring(0, p));
		}catch(Exception e){
			log.error("get license response code format error: " + message.substring(0, p));
			//code = -1;
			return null;
		}
		
		if(code != WS_INVOKE_SUCCESS){
			log.error("get license invoke failed: (" + code + ") " + message.substring(p + 1));
			return null;
		}
		
		return new StringReader(message.substring(p + 1));
	}
	
	private void intervalCheck(LicenseProvider provider) {
		LicenseWS service = getService();
		if(service == null){
			log.error("intervalCheck service is null.");
			lastValidateStatus = WS_CHECK_FAILED;
			return;
		}
		try{
			lastValidateStatus = service.validate(provider.getName(), AppsHome.getNodeID().toString(), "" + System.currentTimeMillis());
			if(log.isDebugEnabled()){
				log.debug("interval check status: " + lastValidateStatus);
			}
		}catch(Exception e){
			log.debug("call webservice validate failed", e);
			lastValidateStatus = WS_CHECK_FAILED;
			return;
		}
	}

	public void check(LicenseProvider provider, License license, CheckCallback callback){
		if(lastValidateStatus != WS_INVOKE_SUCCESS){
			callback.checkFailed();
		}else{
			callback.checkSuccess();
		}
	}

	public void startListen(final LicenseProvider provider) {
		if(listenTimer != null){
			try{
				listenTimer.cancel();
			}catch(Exception e){
				//ignore
			}
		}
		
		listenTimer = new Timer("NetworkLicenseLoader$ListenTimer");
		listenTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				intervalCheck(provider);
			}}, 52000L, 61000L);
	}

	public void stopListen() {
		if(listenTimer != null){
			try{
				log.debug("Cancel timer 'NetworkLicenseLoader$ListenTimer'.");
				listenTimer.cancel();
			}catch(Exception e){
				//ignore
			}
		}
	}

	public static void main(String[] args){
		NetworkLicenseLoader loader = new NetworkLicenseLoader();
		loader.setServiceURL("http://192.168.18.21:8080/opoo-apps/services/license");
		License license = loader.loadLicense(new DefaultLicenseProvider());
		System.out.println(license);
		
		
		//loader.startListen();
		
//		String s = "30|PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPGxpY2Vuc2UgaWQ9Ijk0ODY5MjUwNCIgbmFtZT0iY29tbW9uLWxpY2Vuc2UiIGVkaXRpb249InN0YW5kYXJkLW5ldHdvcmsiIGNyZWF0aW9uRGF0ZT0iMDcvMTMvMjAxMCIgdmVyc2lvbj0iMi4wLjAgR0EiIHR5cGU9IkNPTU1FUkNJQUwiPjxjbGllbnQgbmFtZT0iQUxDOjNhNWI4MjhlLTM2ZjctNGM3Mi04ZmRjLWUxYTkyYjg5NWUwMCIgY29tcGFueT0iUmVkRmxhZ1NvZnQuQ04iLz48bW9kdWxlIG5hbWU9ImNvcmUiLz48bW9kdWxlIG5hbWU9ImJhc2UiLz48cHJvcGVydHkgbmFtZT0ibnVtQ2x1c3Rlck1lbWJlcnMiPjE8L3Byb3BlcnR5Pjxwcm9wZXJ0eSBuYW1lPSJiYWNraGFzcC1pZCI+OTQ4NjkyNTA0PC9wcm9wZXJ0eT48cHJvcGVydHkgbmFtZT0iYmFja2hhc3AtdHlwZSI+SEFTUC1ITDwvcHJvcGVydHk+PHByb3BlcnR5IG5hbWU9ImV4cGlyYXRpb25EYXRlIj4wNy8xNS8yMDEwPC9wcm9wZXJ0eT48cHJvcGVydHkgbmFtZT0iZ3JhbnRlZElQIj4xMjcuMC4wLjE8L3Byb3BlcnR5Pjxwcm9wZXJ0eSBuYW1lPSJiYWNraGFzcCI+dHJ1ZTwvcHJvcGVydHk+PHByb3BlcnR5IG5hbWU9InJlcG9ydFVSTCI+aHR0cDovL3JlcG9ydC5yZWRmbGFnc29mdC5jbi9yZXBvcnQuanNwYTwvcHJvcGVydHk+PHByb3BlcnR5IG5hbWU9Im5ldHdvcmsiPnRydWU8L3Byb3BlcnR5PjxzaWduYXR1cmU+MzAyZDAyMTQzOWU3M2QzYTc1NDVkOWIxMjM0NGMzZmVhMGNkZTBiMGQ2NTE1ZTY0MDIxNTAwOTIzNmFmNWQ5NDIyZjc4MTQ3ZjI1OWMyY2RjY2ZjNjYzYzZkMTI5ZDwvc2lnbmF0dXJlPjwvbGljZW5zZT4=";
//		String[] split = s.split("[|]");
//		System.out.println(split.length);
//		StringBuffer sb = new StringBuffer(s);
//		StringTokenizer tokenizer = new StringTokenizer(s, "|");
//		int tokens = tokenizer.countTokens();
//		System.out.println(tokens);
//		int of = s.indexOf('|');
//		System.out.println(of);
//		System.out.println(s.substring(0, of));
//		System.out.println(s.substring(of + 1));
	}
}
