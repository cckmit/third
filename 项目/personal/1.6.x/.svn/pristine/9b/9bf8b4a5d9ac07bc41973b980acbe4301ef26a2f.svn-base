package org.opoo.apps.license.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.zip.Adler32;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class MulticastAccessor implements InitializingBean{
	private static final Log log = LogFactory.getLog(MulticastAccessor.class);
	
//	public static final int MAGIC = 0x7833533;//0x74657374;//
	public static final String DEFAULT_ADDR_LOCAL = "localhost";
	public static final String DEFAULT_IP_GROUP = "238.2.0.1";
	public static final String DEFAULT_ADDR_GROUP = "238.2.0.1:19123";
	public static final int DEFAULT_PORT = 19123;
	public static final int DEFAULT_TTL = 4;
	
	//public static final int DEFAULT_SO_TIMEOUT = 5000;
	//public static final int DEFAULT_MAX_TRY = 3;

	private String localAddr;
	private String group;
	private int ttl = DEFAULT_TTL;
	private int soTimeout = -1;
	
	/**
	 * @private
	 */
	private InetAddress localAddress;
	/**
	 * @private
	 */
	private InetSocketAddress groupAddress;
	
	public String getLocalAddr() {
		return localAddr;
	}

	public void setLocalAddr(String localAddr) {
		this.localAddr = localAddr;
	}

	public String getGroup() {
		return group;
	}

	/**
	 * 格式  238.2.0.1:19123
	 * @param group 
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	
	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public void afterPropertiesSet() throws Exception {
		InetAddress ifaceAddr = translateAddress(localAddr != null ? localAddr : DEFAULT_ADDR_LOCAL);
	    InetSocketAddress groupAddr = translateSocketAddress(group != null ? group : DEFAULT_ADDR_GROUP);

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
		localAddress = ifaceAddr;
		groupAddress = groupAddr;
		
		if (log.isDebugEnabled()) {
			log.debug("Multicast using local address '" + ifaceAddr + "' and group address '" + groupAddr + "'.");
		}
	}
	
	protected final InetAddress getLocalAddress(){
		return localAddress;
	}
	
	protected final InetSocketAddress getGroupAddress(){
		return groupAddress;
	}
	
	protected MulticastSocket createMulticastSocket() throws IOException{
		MulticastSocket socket = new MulticastSocket(groupAddress.getPort());
		socket.setInterface(localAddress);
		socket.setTimeToLive(ttl);
		socket.joinGroup(groupAddress.getAddress());
		return socket;
	}
	
	protected void closeMulticastSocket(MulticastSocket socket){
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
	
	public Object execute(SocketCallback action) throws IOException{
		MulticastSocket socket = null;
		try{
			socket = createMulticastSocket();
			
			int timeout = getSoTimeout();
			if(timeout > 0){
				socket.setSoTimeout(timeout);
			}
			
			return action.doInSocket(socket);
		}finally{
			closeMulticastSocket(socket);
		}
	}
	
	

	protected static long getChecksum(byte[] data){
		Adler32 adler32 = new Adler32();
		adler32.update(data);
		return adler32.getValue();
	}
	

	protected static InetAddress translateAddress(String sAddr) throws IOException {
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
	
	
	public static interface SocketCallback{
		Object doInSocket(MulticastSocket socket) throws IOException;
	}

	
	public static void main(String[] args) throws Exception{
		MulticastAccessor accessor = new MulticastAccessor();
		accessor.setGroup("238.2.0.1:19124");
		accessor.afterPropertiesSet();
		accessor.execute(new SocketCallback() {
			public Object doInSocket(MulticastSocket socket) throws IOException {
				while(true){
					byte[] buffer = new byte[100];
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					// Receive a datagram packet.
					socket.receive(packet);
					byte[] data = packet.getData();
					System.out.println(new String(data));
				}
			}
		});
	}
}
