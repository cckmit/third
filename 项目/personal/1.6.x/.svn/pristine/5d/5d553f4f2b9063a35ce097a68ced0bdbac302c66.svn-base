package org.opoo.apps.license.client.ws;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.zip.Adler32;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.license.ws.ServiceUrlProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class LicenseServiceUrlProvider20100708 implements ServiceUrlProvider, InitializingBean  {

	private static final Log log = LogFactory.getLog(LicenseServiceUrlProvider20100708.class);
	
	private static final int MAGIC = 0x7833533;//0x74657374;//
	
	public static final String DEFAULT_ADDR_LOCAL = "localhost";
	public static final int DEFAULT_PORT = 9000;
	public static final String DEFAULT_IP_GROUP = "237.0.0.2";
	public static final String DEFAULT_ADDR_GROUP = "237.0.0.2:9000";
	public static final int DEFAULT_TTL = 4;
	public static final int DEFAULT_SO_TIMEOUT = 5000;
	public static final int DEFAULT_MAX_TRY = 3;
	
	private static final Adler32 adler32 = new Adler32();
	
	private String multicastIface;
	private String multicastGroup;
	private int ttl = DEFAULT_TTL;
	private int soTimeout = DEFAULT_SO_TIMEOUT;
	private int maxTry = DEFAULT_MAX_TRY;
	
	
	/**
	 * @private
	 */
	private InetAddress interfaceAddress;
	/**
	 * @private
	 */
	private InetSocketAddress groupAddress;

	public String getMulticastIface() {
		return multicastIface;
	}

	public void setMulticastIface(String multicastIface) {
		this.multicastIface = multicastIface;
	}

	public String getMulticastGroup() {
		return multicastGroup;
	}

	public void setMulticastGroup(String multicastGroup) {
		this.multicastGroup = multicastGroup;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public void afterPropertiesSet() throws Exception {
		InetAddress ifaceAddr = translateAddress(multicastIface != null ? multicastIface : DEFAULT_ADDR_LOCAL);
	    InetSocketAddress groupAddr = translateSocketAddress(multicastGroup != null ? multicastGroup : DEFAULT_ADDR_GROUP);

		if (ifaceAddr != null && ifaceAddr.isMulticastAddress()) {
			throw new IllegalArgumentException("Interface address " + ifaceAddr
					+ " is multi-cast; it must be an IP address bound to a physical interface");
		}
		if (!groupAddr.getAddress().isMulticastAddress()) {
			throw new IllegalArgumentException("Multicast address " + groupAddr
					+ " is not multi-cast; it must be in the range 224.0.0.0 to 239.255.255.255");
		}
		if (ttl < 0 || ttl > 255) {
			throw new IllegalArgumentException("TTL " + ttl + " is out of range; it must be in the range 1 to 255");
		}
		
		
		
		Assert.notNull(ifaceAddr, "Network interface address cannot be null.");
		Assert.notNull(groupAddr, "Multicast group address cannot be null.");
		interfaceAddress = ifaceAddr;
		groupAddress = groupAddr;
	}

	protected static InetAddress translateAddress(String sAddr) throws IOException {
		System.out.println("原始地址：" + sAddr);
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
		System.out.println("地址：" + addr);
		return addr;
	}
	
	protected static InetSocketAddress translateSocketAddress(String sAddr) throws UnknownHostException {
		if (sAddr == null)
			throw new IllegalArgumentException("address cannot be null");
		int iIndex = sAddr.lastIndexOf(':');
		int iPort = DEFAULT_PORT;
		if (iIndex != -1) {
			iPort = Integer.parseInt(sAddr.substring(iIndex + 1));
			sAddr = sAddr.substring(0, iIndex);
		}
		InetSocketAddress addr = new InetSocketAddress(sAddr, iPort);
		if (addr.getAddress() == null)
			throw new UnknownHostException("could not resolve address \"" + sAddr + "\"");
		else
			return addr;
	}
	
	
	public String getServiceURL() {
		MulticastSocket socket = null; 
		try {
			socket = new MulticastSocket(groupAddress.getPort());
			socket.setInterface(interfaceAddress);
			socket.setTimeToLive(ttl);
			socket.joinGroup(groupAddress.getAddress());
			socket.setSoTimeout(soTimeout);
			
			for(int i = 0 ; i < maxTry ; i++){
				log.debug("try #" + (i+1));
				
				try {
					String serviceURL = receiveServiceURL(socket);
					if(serviceURL != null){
						return serviceURL;
					}
				}catch(SocketTimeoutException e){
					throw new Exception("ALS multicast not found.", e);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}finally{
			if(socket != null){
				try {
					socket.leaveGroup(groupAddress.getAddress());
				} catch (Exception e) {
					//ignore
				}
				
				try {
					socket.close();
				} catch (Exception e) {
					//ignore
				}
			}
		}
		return null;
	}
	
	private String receiveServiceURL(MulticastSocket socket) throws IOException{
		byte[] buffer = new byte[100];
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

		// Receive a datagram packet.
		socket.receive(packet);
		
		int length = packet.getLength();
		
		//至少有一个int值
		if(length <= 4){
			return null;
		}
		
		byte[] bytes = packet.getData();
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
		int first = in.readInt();
		if(first == MAGIC){
			String serverInfo = in.readUTF();
			long time = in.readLong();
			String serviceURL = in.readUTF();
			long checksum = in.readLong();
			
			long dif = System.currentTimeMillis() - time;
			if(dif > 0){
				if(log.isInfoEnabled()){
					String msg = String.format("Receive %s bytes from %s(%s) send %s ms ago.", length, packet.getAddress().getHostAddress(), serverInfo, dif);
					log.info(msg);
				}
			}else{
				if(log.isDebugEnabled()){
					String msg = String.format("Receive %s bytes from %s(%s)", length, packet.getAddress().getHostAddress(), serverInfo);
					log.info(msg);
				}
			}
			
			if(StringUtils.isBlank(serviceURL)){
				return null;
			}
			
			adler32.update(serviceURL.getBytes());
			if(adler32.getValue() != checksum){
				log.warn("The serviceURL checksum failed.");
				return null;
			}
			return serviceURL;
		}else{
			if(log.isDebugEnabled()){
				String msg = String.format("Receive %s bytes from an unknown multicast application(%s).", length, packet.getAddress().getHostAddress());
				log.debug(msg);
			}
		}
		
		return null;
	}
	
	
	public static void main(String[] args) throws Exception{
		LicenseServiceUrlProvider20100708 provider = new LicenseServiceUrlProvider20100708();
		provider.afterPropertiesSet();
		
		String url = provider.getServiceURL();
		System.out.println(url);
	}
}
