package org.opoo.apps.license.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public abstract class NetUtils {
	
	/**
	 * 
	 * @param sAddr
	 * @return
	 * @throws IOException
	 */
	public static InetAddress translateAddress(String sAddr) throws IOException {
		//System.out.println("原始地址：" + sAddr);
		if (sAddr == null)
			throw new IllegalArgumentException("address cannot be null");
		InetAddress addr;
		if (sAddr.equals("localhost")) {
			addr = InetAddress.getLocalHost();
		} else {
			addr = InetAddress.getByName(sAddr);
			byte abIP[] = addr.getAddress();
			int nIP = (abIP[0] & 0xff) << 24 | (abIP[1] & 0xff) << 16 | (abIP[2] & 0xff) << 8 | abIP[3] & 0xff;
			if (nIP == 0 || nIP == 0x7f000001)
				addr = InetAddress.getLocalHost();
		}
		//System.out.println("地址：" + addr);
		return addr;
	}
	
	/**
	 * 
	 * @param sAddr
	 * @param defaultPort
	 * @return
	 * @throws UnknownHostException
	 */
	public static InetSocketAddress translateSocketAddress(String sAddr, int defaultPort) throws UnknownHostException {
		if (sAddr == null)
			throw new IllegalArgumentException("address cannot be null");
		int iIndex = sAddr.lastIndexOf(':');
		int iPort = defaultPort;
		if (iIndex != -1) {
			iPort = Integer.parseInt(sAddr.substring(iIndex + 1));
			sAddr = sAddr.substring(0, iIndex);
		}
		if (iPort <= 0) {
			throw new IllegalArgumentException("port number illegal \"" + iPort + "\"");
		}
		InetSocketAddress addr = new InetSocketAddress(sAddr, iPort);
		if (addr.getAddress() == null)
			throw new UnknownHostException("could not resolve address \"" + sAddr + "\"");
		else
			return addr;
	}
	
	/**
	 * 
	 * @param sAddr
	 * @return
	 * @throws UnknownHostException
	 */
	public static InetSocketAddress translateSocketAddress(String sAddr) throws UnknownHostException {
		return translateSocketAddress(sAddr, -1);
	}
	
}
