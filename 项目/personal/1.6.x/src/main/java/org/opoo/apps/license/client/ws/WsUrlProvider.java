package org.opoo.apps.license.client.ws;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.license.multicast.MagicMulticastAccessor;
import org.opoo.apps.license.ws.ServiceUrlProvider;

import com.jivesoftware.license.LicenseException;

public class WsUrlProvider implements ServiceUrlProvider {
	private static final Log log = LogFactory.getLog(WsUrlProvider.class);
	
	public static final int VERSION1_MAGIC_NUMBER = 0x7833533;
	public static final int VERSION2_MAGIC_NUMBER = 0x7833535;
	public static final int RESTFUL_WS_MAGIC_NUMBER = 0x7833541;
	public static final int RESTFUL_SSL_WS_MAGIC_NUMBER = 0x7833543;
	
	private MagicMulticastAccessor magicMulticastAccessor;
	private MagicMulticastAccessor.MagicPacketCallback magicPacketCallback = new ServiceUrlExtractor();
	private int versionMagicNumber;
	
	public MagicMulticastAccessor getMagicMulticastAccessor() {
		return magicMulticastAccessor;
	}

	public void setMagicMulticastAccessor(MagicMulticastAccessor magicMulticastAccessor) {
		this.magicMulticastAccessor = magicMulticastAccessor;
	}

	public MagicMulticastAccessor.MagicPacketCallback getMagicPacketCallback() {
		return magicPacketCallback;
	}

	public void setMagicPacketCallback(MagicMulticastAccessor.MagicPacketCallback magicPacketCallback) {
		this.magicPacketCallback = magicPacketCallback;
	}

	public int getVersionMagicNumber() {
		return versionMagicNumber;
	}

	public void setVersionMagicNumber(int versionMagicNumber) {
		this.versionMagicNumber = versionMagicNumber;
	}

	public String getServiceURL() {
		try {
			return (String) magicMulticastAccessor.execute(versionMagicNumber, magicPacketCallback);
		} catch (Exception e) {
			log.error("can not find serviceURL for magic number " + versionMagicNumber, e);
		}
		return null;
	}
	
	public static ServiceUrlProvider createInstance(int magicNumber) throws Exception{
		MagicMulticastAccessor a = MagicMulticastAccessor.buildDefaultInstance();
		WsUrlProvider p = new WsUrlProvider();
		p.setMagicMulticastAccessor(a);
		p.setVersionMagicNumber(magicNumber);
		return p;
	}
	
	public static ServiceUrlProvider createInstance(Properties props, int magicNumber){
		MagicMulticastAccessor a = MagicMulticastAccessor.createInstance(props);
		WsUrlProvider p = new WsUrlProvider();
		p.setMagicMulticastAccessor(a);
		p.setVersionMagicNumber(magicNumber);
		return p;
	}

	public static String getServiceURL(int versionMagicNumber){
		try {
			return createInstance(versionMagicNumber).getServiceURL();
		} catch (Exception e) {
			throw new LicenseException(e);
		}
	}
	
	
	public static void main(String[] args){
		//AppsGlobals.setSetupProperty("license.multicast.group", "238.2.0.1:19124");
		String serviceURL = WsUrlProvider.getServiceURL(VERSION2_MAGIC_NUMBER);
		System.out.println(serviceURL);
	}
}
