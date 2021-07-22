package org.opoo.apps.license.client.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.contrib.ssl.AuthSSLProtocolSocketFactory;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsHome;
import org.opoo.apps.license.AppsLicenseException;
import org.opoo.apps.license.AppsLicenseLog;
import org.springframework.util.ResourceUtils;

/**
 * HttpClient SSL client auth.
 * @author lcj
 *
 */
public class HttpsClientLicenseV2Rest implements LicenseV2Rest {
	private static boolean DEBUG = Boolean.getBoolean("license.httpclient.ssl.debug");
	private static final Log log = !DEBUG ? AppsLicenseLog.LOG : LogFactory.getLog(HttpClientLicenseV2Rest.class);

	private MultiThreadedHttpConnectionManager httpConnectionManager;
	private HttpClient httpClient;// = new HttpClient();
	private String serviceURL;
	
	public HttpsClientLicenseV2Rest(String serviceURL) {
		super();
		this.serviceURL = serviceURL;
		try {
			HttpClientSSL.init();
			this.httpConnectionManager = new MultiThreadedHttpConnectionManager();
			this.httpClient = new HttpClient(httpConnectionManager);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String login(String productId, String instanceId) {
		GetMethod m = new GetMethod(serviceURL + "/login?productId=" + productId + "&instanceId=" + instanceId);
		try {
			httpClient.executeMethod(m);
			if (m.getStatusCode() == 200) {
				return m.getResponseBodyAsString();
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			m.releaseConnection();
		}
		return null;
	}

	public void logoff(String sessionId) {
		GetMethod m = new GetMethod(serviceURL + "/logoff?sessionId=" + sessionId);
		try {
			httpClient.executeMethod(m);
			int statusCode = m.getStatusCode();
			if(statusCode != 200 && statusCode != 204/* No Content*/){
				log.error("logoff error, status code is " + statusCode);
			}
		} catch (Exception e) {
			log.error("logoff error", e);
		}finally{
			m.releaseConnection();
		}
	}

	public String getLicense(String sessionId) {
		GetMethod m = new GetMethod(serviceURL + "/get?sessionId=" + sessionId);
		try {
			httpClient.executeMethod(m);
			if (m.getStatusCode() == 200) {
				return m.getResponseBodyAsString();
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			m.releaseConnection();
		}
		return null;
	}

	public void validate(String sessionId, String validationString) {
		GetMethod m = new GetMethod(serviceURL + "/validate?sessionId=" + sessionId + "&validationString=" + validationString);
		try {
			httpClient.executeMethod(m);
			int statusCode = m.getStatusCode();
			if(statusCode != 200 && statusCode != 204/* No Content*/){
				throw new AppsLicenseException("validate error, status code is " + statusCode);
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally{
			m.releaseConnection();
		}
	}

	public String getVersion() {
		GetMethod m = new GetMethod(serviceURL + "/version");
		try {
			httpClient.executeMethod(m);
			if (m.getStatusCode() == 200) {
				return m.getResponseBodyAsString();
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			m.releaseConnection();
		}
		return null;
	}
	
	public void shutdown() {
		if(httpConnectionManager != null){
			httpConnectionManager.shutdown();
		}
	}
	
	public static class HttpClientSSL{
		public static void init() throws Exception{
			Properties props = loadConfig();
			//String keystoreFile = "org/opoo/apps/license/client/ws/ssl-client.p12";
			//String truststoreFile = "org/opoo/apps/license/client/ws/ssl-client.truststore";
			String keystoreFile = props.getProperty("keystore.file");
			String keystoreType = props.getProperty("keystore.type");
			String keystorePassword = props.getProperty("keystore.password");
			String truststoreFile = props.getProperty("truststore.file");
			String truststoreType = props.getProperty("truststore.type");
			String truststorePassword = props.getProperty("truststore.password");
			String port = props.getProperty("ssl.port");
			String scheme = props.getProperty("ssl.scheme");
			URL keystoreURL = ResourceUtils.getURL(keystoreFile);
			URL truststoreURL = ResourceUtils.getURL(truststoreFile);
			
			ProtocolSocketFactory factory = new AuthSSLProtocolSocketFactory(keystoreURL, keystorePassword, keystoreType, truststoreURL, truststorePassword, truststoreType);
			Protocol authhttps = new Protocol(scheme, factory, Integer.parseInt(port)/*58443*/);
			Protocol.registerProtocol(scheme, authhttps);
		}
		
		static Properties loadConfig() throws FileNotFoundException, IOException{
			Properties props = new Properties();
			InputStream stream = null;
			try{
				//测试、调试时设置该属性（license.httpclient.ssl.debug）为true，
				//否则因为AppsHome没有初始化回出现问题。
				if(!DEBUG){
					File file = new File(AppsHome.getCrypto(), "license-client-ssl.properties");
					if(file.exists()){
						log.debug("Read SSL config from : " + file);
						stream = new FileInputStream(file);
					}
				}
				
				if(stream == null){
					URL url = ResourceUtils.getURL("classpath:org/opoo/apps/license/client/ws/license-client-ssl.properties");
					//stream = ClassLoader.getSystemResourceAsStream("org/opoo/apps/license/client/ws/license-client-ssl.properties");
					stream = url.openStream();
				}
				props.load(stream);
			}finally{
				IOUtils.closeQuietly(stream);
			}
			return props;
		}
	}
}
