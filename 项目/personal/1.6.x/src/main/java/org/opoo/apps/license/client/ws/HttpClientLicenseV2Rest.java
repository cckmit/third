package org.opoo.apps.license.client.ws;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.license.AppsLicenseException;

public class HttpClientLicenseV2Rest implements LicenseV2Rest {
	private static final Log log = LogFactory.getLog(HttpClientLicenseV2Rest.class);
	
	private Signer signer;
	private MultiThreadedHttpConnectionManager httpConnectionManager;
	private HttpClient httpClient;// = new HttpClient(new MultiThreadedHttpConnectionManager());
	private String serviceURL;
	
	public HttpClientLicenseV2Rest(String serviceURL) {
		super();
		this.serviceURL = serviceURL;
		this.signer = new Signer();
		this.httpConnectionManager = new MultiThreadedHttpConnectionManager();
		this.httpClient = new HttpClient(httpConnectionManager);
	}
	
	void addSignatureAndTimestamp(GetMethod m, String fingerprint){
		long time = System.currentTimeMillis();
		fingerprint += "&" + time;
		String signature = signer.sign(fingerprint);
		m.setRequestHeader("X-Apps-Signature", signature);
		m.setRequestHeader("X-Apps-Timestamp", time+"");
	}

	public String login(String productId, String instanceId) {
		GetMethod m = new GetMethod(serviceURL + "/login?productId=" + productId + "&instanceId=" + instanceId);
		addSignatureAndTimestamp(m, productId + "&" + instanceId);
		try {
			httpClient.executeMethod(m);
			if (m.getStatusCode() == 200) {
				return m.getResponseBodyAsString();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public void logoff(String sessionId) {
		GetMethod m = new GetMethod(serviceURL + "/logoff?sessionId=" + sessionId);
		addSignatureAndTimestamp(m, sessionId);
		try {
			httpClient.executeMethod(m);
			int statusCode = m.getStatusCode();
			if(statusCode != 200 && statusCode != 204/* No Content*/){
				log.error("logoff error, status code is " + statusCode);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getLicense(String sessionId) {
		GetMethod m = new GetMethod(serviceURL + "/get?sessionId=" + sessionId);
		addSignatureAndTimestamp(m, sessionId);
		try {
			httpClient.executeMethod(m);
			if (m.getStatusCode() == 200) {
				return m.getResponseBodyAsString();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public void validate(String sessionId, String validationString) {
		GetMethod m = new GetMethod(serviceURL + "/validate?sessionId=" + sessionId + "&validationString=" + validationString);
		addSignatureAndTimestamp(m, sessionId+ "&" + validationString);
		try {
			httpClient.executeMethod(m);
			int statusCode = m.getStatusCode();
			if(statusCode != 200 && statusCode != 204/* No Content*/){
				throw new AppsLicenseException("validate error, status code is " + statusCode);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getVersion() {
		GetMethod m = new GetMethod(serviceURL + "/version");
		//addSignatureAndTimestamp(m, "version");
		try {
			httpClient.executeMethod(m);
			if (m.getStatusCode() == 200) {
				return m.getResponseBodyAsString();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	
	public static class Signer{
		private Signature sig;
		public Signer(){
			try{
				String privateKey = "3082014c0201003082012c06072a8648ce3804013082011f02818100fd7f53811d75122952df4a9c2eece4e7f611b7523cef4400c31e3f80b6512669455d402251fb593d8d58fabfc5f5ba30f6cb9b556cd7813b801d346ff26660b76b9950a5a49f9fe8047b1022c24fbba9d7feb7c61bf83b57e7c6a8a6150f04fb83f6d3c51ec3023554135a169132f675f3ae2b61d72aeff22203199dd14801c70215009760508f15230bccb292b982a2eb840bf0581cf502818100f7e1a085d69b3ddecbbcab5c36b857b97994afbbfa3aea82f9574c0b3d0782675159578ebad4594fe67107108180b449167123e84c281613b7cf09328cc8a6e13c167a8b547c8d28e0a3ae1e2bb3a675916ea37f0bfa213562f1fb627a01243bcca4f1bea8519089a883dfe15ae59f06928b665e807b552564014c3bfecf492a0417021500938b88abc59a41d8d3cfec2942a5ca3a25664b47";
				KeyFactory keyFactory = KeyFactory.getInstance("DSA");
				PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(Hex.decodeHex(privateKey.toCharArray()));
		        PrivateKey privKey = keyFactory.generatePrivate(privKeySpec);
		        //System.out.println(privKey);
		        
		        sig = Signature.getInstance("SHA1withDSA");
		        sig.initSign(privKey);
			}catch(Exception e){
				log.error("init signer error", e);
			}
		}
		
		public String sign(String fingerprint){
			if(sig == null){
				throw new AppsLicenseException("can not sign request, sig object null");
			}
			try {
				sig.update(fingerprint.getBytes("utf-8"));
				byte[] sign = sig.sign();
		        String signature = new String(Hex.encodeHex(sign));
				return signature;
			} catch (Exception e) {
				throw new AppsLicenseException(e);
			}
		}
	}


	public void shutdown() {
		if(httpConnectionManager != null){
			httpConnectionManager.shutdown();
		}
	}
}
