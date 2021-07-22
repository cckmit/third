package org.opoo.apps.license.net;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;


public class DummyServerAddressProvider implements ServerAddressProvider{
	
	private static int DEFAULT_PORT = 50555;
	private InetSocketAddress serverAddress;
	
	public DummyServerAddressProvider(String hostname, int port){
		serverAddress = new InetSocketAddress(hostname, port);
	}
	
	/**
	 * hostname:port
	 * @param address
	 */
	public DummyServerAddressProvider(String address){
		try {
			serverAddress = NetUtils.translateSocketAddress(address, DEFAULT_PORT);
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public InetSocketAddress getServerAddress() {
		return serverAddress;
	}
	

}
