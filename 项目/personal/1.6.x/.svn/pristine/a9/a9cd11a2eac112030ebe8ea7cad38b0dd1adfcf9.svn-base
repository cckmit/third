package org.opoo.apps.license.client.ws;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.license.multicast.MulticastAccessor;
import org.opoo.apps.license.ws.ServiceUrlProvider;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class LicenseServiceUrlProvider extends MulticastAccessor implements ServiceUrlProvider  {

	private static final Log log = LogFactory.getLog(LicenseServiceUrlProvider.class);
	public static final int MAGIC = 0x7833533;//0x74657374;//
	
	public static final int DEFAULT_RECEIVE_SO_TIMEOUT = 5000;
	public static final int DEFAULT_RECEIVE_MAX_TRY = 3;

	private int soTimeout = DEFAULT_RECEIVE_SO_TIMEOUT;
	private int maxTry = DEFAULT_RECEIVE_MAX_TRY;

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public int getMaxTry() {
		return maxTry;
	}

	public void setMaxTry(int maxTry) {
		this.maxTry = maxTry;
	}

	public String getServiceURL(){
		try {
			return getServiceURLInCallback();
		} catch (Exception e) {
			log.error("can not find serviceURL: " + e.getMessage());
		}
		return null;
	}
	
	private String getServiceURLInCallback() throws IOException{
		return (String)execute(new SocketCallback(){
			public Object doInSocket(MulticastSocket socket) throws IOException {
				socket.setSoTimeout(soTimeout);
				
				for(int i = 0 ; i < maxTry ; i++){
					log.debug("try #" + (i+1));
					
					try {
						String serviceURL = receiveServiceURL(socket);
						if(serviceURL != null){
							return serviceURL;
						}
					}catch(SocketTimeoutException e){
						throw new IOException("ALS multicast not found.", e);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
				return null;
			}
		});
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
			in.close();
			
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
			//System.out.println(serviceURL);
			
			
			long ck = getChecksum(serviceURL.getBytes());

			//System.out.println(ck);
			if(log.isDebugEnabled()){
				log.debug("Recived serviceURL: " + serviceURL);
				log.debug("checksum: " + ck);
			}
			
			if(ck != checksum){
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

	
	private Listener listener;
	/**
	 * 测试目的。
	 * 
	 * @throws IOException
	 */
	public void startReceiveMulticastPacket() throws IOException{
		if(listener == null || !listener.isAlive()){
			listener = new Listener();
		}
		if(listener.isRunning()){
			log.warn("监听器正在运行");
		}else{
			listener.start();
		}
	}
	
	/**
	 * 测试目的。
	 * 
	 * @throws IOException
	 */
	public void stopReceiveMulticastPacket(){
		if(listener != null && listener.isAlive() && listener.isRunning()){
			log.debug("请求停止监听");
			listener.doStop();
		}else{
			log.warn("监听器未运行，不必调用停止。");
		}
	}
	
	
	class Listener extends Thread{
		private boolean running = false;
		@Override
		public void run(){
			running = true;
			try {
				execute(new SocketCallback(){
					public Object doInSocket(MulticastSocket socket) throws IOException {
						socket.setSoTimeout(10000);
						while(running){
							String serviceURL = receiveServiceURL(socket);
							if(serviceURL != null){
								System.out.println("收到的服务地址：" + serviceURL);
							}else{
								System.err.println("未收到服务地址");
							}
						}
						return null;
					}
				});
			} catch (Exception e) {
				log.error("接收数据出错", e);
				running = false;
			}
		}
		
		public void doStop(){
			running = false;
		}
		
		public boolean isRunning(){
			return running;
		}
	}
	
	public static LicenseServiceUrlProvider createDefaultServiceUrlProvider() throws Exception{
		LicenseServiceUrlProvider provider = new LicenseServiceUrlProvider();
		provider.setGroup(AppsGlobals.getSetupProperty("license.multicast.group", DEFAULT_ADDR_GROUP));
		provider.setLocalAddr(AppsGlobals.getSetupProperty("license.multicast.localAddr"));
		provider.setMaxTry(AppsGlobals.getSetupProperty("license.multicast.receive.maxTry", LicenseServiceUrlProvider.DEFAULT_RECEIVE_MAX_TRY));
		provider.setSoTimeout(AppsGlobals.getSetupProperty("license.multicast.receive.soTimeout", LicenseServiceUrlProvider.DEFAULT_RECEIVE_SO_TIMEOUT));
		provider.setTtl(AppsGlobals.getSetupProperty("license.multicast.ttl", DEFAULT_TTL));
		provider.afterPropertiesSet();
		
		return provider;
	}
	

	public static void main2(String[] args) throws Exception{
		LicenseServiceUrlProvider provider = createDefaultServiceUrlProvider();
		
		String url = provider.getServiceURL();
		System.out.println("发现的服务地址是：" + url);
		provider.startReceiveMulticastPacket();
		
		Thread.sleep(4000);
		provider.stopReceiveMulticastPacket();
	}
	
	public static void main(String[] args) throws Exception {
		MulticastAccessor accessor = new MulticastAccessor();
		accessor.setGroup("238.2.0.1:19124");
		accessor.setSoTimeout(3000);
		accessor.afterPropertiesSet();
		accessor.execute(new SocketCallback(){
			public Object doInSocket(MulticastSocket socket) throws IOException {
				byte[] buffer = new byte[100];
				
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				System.out.println(packet.getAddress());
				
				// Receive a datagram packet.
				socket.receive(packet);
				
				System.out.println(packet.getAddress().getHostAddress());
				System.out.println(packet.getSocketAddress().getClass());
				InetSocketAddress addr = (InetSocketAddress) packet.getSocketAddress();
				System.out.println(addr.getAddress().getHostAddress());
				System.out.println(addr.getPort());
				
				return null;
			}});
	}
}
