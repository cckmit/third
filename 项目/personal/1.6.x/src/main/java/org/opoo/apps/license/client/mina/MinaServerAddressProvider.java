package org.opoo.apps.license.client.mina;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.license.multicast.MagicMulticastAccessor;
import org.opoo.apps.license.net.ServerAddressProvider;

import com.jivesoftware.license.LicenseException;


public class MinaServerAddressProvider implements ServerAddressProvider {
	private static final Log log = LogFactory.getLog(MinaServerAddressProvider.class);
	
	public static final int MINAP_MAGIC_NUMBER = 0x7833537;
	public static final int MINAP_SSL_MAGIC_NUMBER = 0x7833539;
	
	private MagicMulticastAccessor magicMulticastAccessor;
	private MagicMulticastAccessor.MagicPacketCallback magicPacketCallback = new ServerAddressExtractor();
	private int minapMagicNumber;
	
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

	public int getMinapMagicNumber() {
		return minapMagicNumber;
	}

	public void setMinapMagicNumber(int minapMagicNumber) {
		this.minapMagicNumber = minapMagicNumber;
	}

	private String getServerAddressURI() {
		try {
			return (String) magicMulticastAccessor.execute(minapMagicNumber, magicPacketCallback);
		} catch (Exception e) {
			log.error("can not find mina protocol server address for magic number " + minapMagicNumber, e);
		}
		return null;
	}
	
	public static MinaServerAddressProvider createInstance(int magicNumber) throws Exception{
		MagicMulticastAccessor a = MagicMulticastAccessor.buildDefaultInstance();
		MinaServerAddressProvider p = new MinaServerAddressProvider();
		p.setMagicMulticastAccessor(a);
		p.setMinapMagicNumber(magicNumber);
		return p;
	}
	
	public static MinaServerAddressProvider createInstance(Properties props, int magicNumber){
		MagicMulticastAccessor a = MagicMulticastAccessor.createInstance(props);
		MinaServerAddressProvider p = new MinaServerAddressProvider();
		p.setMagicMulticastAccessor(a);
		p.setMinapMagicNumber(magicNumber);
		return p;
	}

	public static InetSocketAddress getServerAddress(int magicNumber){
		try {
			return createInstance(magicNumber).getServerAddress();
		} catch (Exception e) {
			throw new LicenseException(e);
		}
	}
	
	public InetSocketAddress getServerAddress() {
		String uri = getServerAddressURI();
		if(StringUtils.isBlank(uri)){
			return null;
		}
		
		try {
			URI u = new URI(uri);
			return new InetSocketAddress(u.getHost(), u.getPort());
		} catch (URISyntaxException e) {
			if(log.isDebugEnabled()){
				log.error("解析Mina服务器地址错误：" + uri, e);
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception{
		//AppsGlobals.setSetupProperty("license.multicast.group", "238.2.0.1:19124");
		String s = "lcps://192.168.18.5:50556";
		URI uri = new URI(s);
		System.out.println(uri);
		System.out.println(uri.getHost());
		System.out.println(uri.getPort());
		System.out.println(uri.getScheme());
		InetSocketAddress addr = new InetSocketAddress(InetAddress.getLocalHost(), 50556);
		System.out.println(addr);
		
		InetSocketAddress address = MinaServerAddressProvider.getServerAddress(MinaServerAddressProvider.MINAP_MAGIC_NUMBER);
		System.out.println(address);
	}
}
