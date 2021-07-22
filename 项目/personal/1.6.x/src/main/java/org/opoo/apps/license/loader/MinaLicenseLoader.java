package org.opoo.apps.license.loader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.license.AppsLicenseLog;
import org.opoo.apps.license.DefaultLicenseProvider;
import org.opoo.apps.license.client.mina.LicenseClient;
import org.opoo.apps.license.client.mina.LicenseClientImpl;
import org.opoo.apps.license.client.mina.MinaServerAddressProvider;
import org.opoo.apps.license.net.NetUtils;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.LicenseInitializationException;
import com.jivesoftware.license.LicenseManager;
import com.jivesoftware.license.LicenseProvider;
import com.jivesoftware.license.io.LicenseReader;

public class MinaLicenseLoader implements LicenseLoader {
	private static final Log log = AppsLicenseLog.LOG;
	private LicenseClient client;
	private InetSocketAddress serverAddress;
	private boolean useSSL = false;
	
	public MinaLicenseLoader(final LicenseProvider provider, final InetSocketAddress serverAddress){
		client = new LicenseClientImpl(provider, getServerAddress());
	}
	
	protected InetSocketAddress getServerAddress() {
		if (serverAddress == null) {
			String addr = AppsGlobals.getSetupProperty("license.serverAddress");
			if (StringUtils.isNotBlank(addr)) {
				try {
					serverAddress = NetUtils.translateSocketAddress(addr);
				} catch (UnknownHostException e) {
					//
				}
			}
		}
		if (serverAddress == null) {
			int magicNumber = MinaServerAddressProvider.MINAP_MAGIC_NUMBER;
			if (useSSL) {
				magicNumber = MinaServerAddressProvider.MINAP_SSL_MAGIC_NUMBER;
			}
			serverAddress = MinaServerAddressProvider.getServerAddress(magicNumber);
		}
		if (serverAddress == null) {
			throw new LicenseException("can not found als mina server address.");
		}
		//System.out.println("Find server address from multicast: " + serverAddress);
		return serverAddress;
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
				//System.out.println(exceptions);
			    throw new LicenseInitializationException("License Validation Failed", exceptions);
			}
			//System.out.println(provider.getVersion());
			// Make sure we report the right version when displayed, not the license version
			l.setVersion(provider.getVersion());
			
			return l;
		} catch (LicenseException e) {
			log.debug(e.getMessage(), e);
		} catch (IOException e) {
			log.debug(e.getMessage(), e);
		} catch(Exception e){
			//e.printStackTrace();
			log.debug(e.getMessage(), e);
		}
		return null;
	}
	
	
	private Reader getLicenseReader(LicenseProvider provider){
		String message = client.getEncodedLicense();
		//System.out.println("==============" + message);
		if(message == null){
			log.error("get license response null.");
			return null;
		}else{
			//log.debug("response: " + message);
		}
		
		return new StringReader(message);
	}

	public void check(LicenseProvider provider, License license, CheckCallback callback) {
		if(client == null || client.getEncodedLicense() == null){
			callback.checkFailed();
		}else{
			callback.checkSuccess();
		}
		//client.check();
	}

	public void startListen(LicenseProvider provider) {
		if(client != null){
			client.start();
		}
	}

	public void stopListen() {
		if(client != null){
			client.stop();
		}
	}
	
	public static void main(String[] args){
		InetSocketAddress address = new InetSocketAddress("localhost", 50555);
		System.out.println(address.getHostName() + ":::" + address.getPort());
	
		DefaultLicenseProvider provider = new DefaultLicenseProvider();
		MinaLicenseLoader loader = new MinaLicenseLoader(provider, null/*new InetSocketAddress("localhost", 50555)*/);
		System.out.println("................");
		System.out.println("................" + loader.loadLicense(provider));
	}
	
}
