package org.opoo.apps.license.multicast;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;

public class MagicMulticastAccessor extends MulticastAccessor {
	private static final Log log = LogFactory.getLog(MagicMulticastAccessor.class);
	public static final int DEFAULT_RECEIVE_SO_TIMEOUT = 5000;
	public static final int DEFAULT_RECEIVE_MAX_TRY = 10;
	private int maxTry = DEFAULT_RECEIVE_MAX_TRY;

	public int getMaxTry() {
		return maxTry;
	}

	public void setMaxTry(int maxTry) {
		this.maxTry = maxTry;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if(getSoTimeout() < 0){
			setSoTimeout(DEFAULT_RECEIVE_SO_TIMEOUT);
		}
	}

	public Object execute(final int magicNumber, final MagicPacketCallback callback) throws IOException{
		return execute(new SocketCallback(){
			public Object doInSocket(MulticastSocket socket) throws IOException {
//				socket.setSoTimeout(soTimeout);
				
//				for(int i = 0 ; i < maxTry ; i++){
//					log.debug("try #" + (i+1));
					
					try {
						Object result = execute(socket, magicNumber, callback);
						if(result != null){
							return result;
						}
					}catch(SocketTimeoutException e){
						throw new IOException("ALS multicast not found.", e);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
//				}
				return null;
			}
		});
	}
	
	private Object execute(MulticastSocket socket, int magicNumber, MagicPacketCallback callback) throws IOException{
		DatagramPacket packet = null;
		int i = 0;
		//尝试10次读取特定magicNumber的广播数据
		while(i < maxTry){
			byte[] buffer = new byte[100];
			packet = new DatagramPacket(buffer, buffer.length);
			// Receive a datagram packet.
			socket.receive(packet);
			
			if(isMagicPacket(packet, magicNumber)){
				break;
			}else{
				if(log.isDebugEnabled()){
					String msg = String.format("Receive %s bytes but not for magic '%s'.", packet.getLength(), magicNumber);
					log.debug(msg);
				}
				packet = null;
				i++;
			}
		}
		
		if(packet != null){
			return callback.doInMagicPacket(socket, packet);
		}
		return null;
	}
	
	private static boolean isMagicPacket(DatagramPacket packet, int magicNumber){
		int length = packet.getLength();
		
		//至少有一个int值
		if(length <= 4){
			return false;
		}
		
		byte[] bytes = packet.getData();
		int magic = bytesToInt(bytes);
		return magic == magicNumber;
	}
	
	
	public static interface MagicPacketCallback{
		Object doInMagicPacket(MulticastSocket socket, DatagramPacket packet) throws IOException;
	}
	
	public static int bytesToInt(byte[] bytes){
		if(bytes.length < 4){
			throw new IllegalArgumentException();
		}
		byte[] bs = new byte[4];
		if(bytes.length == 4){
			bs = bytes;
		}else{
			System.arraycopy(bytes, 0, bs, 0, 4);
		}
		int result = 0;
		for(int i = 0 ; i < 4 ; i++){
			int x = bs[i] & 0xFF;
			result |= x << (4 * 2 * (3 - i));
		}
		return result;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static MagicMulticastAccessor buildDefaultInstance() throws Exception{
		MagicMulticastAccessor mma = new MagicMulticastAccessor();
		mma.setGroup(AppsGlobals.getSetupProperty("license.multicast.group", DEFAULT_ADDR_GROUP));
		mma.setLocalAddr(AppsGlobals.getSetupProperty("license.multicast.localAddr"));
		mma.setMaxTry(AppsGlobals.getSetupProperty("license.multicast.receive.maxTry", DEFAULT_RECEIVE_MAX_TRY));
		mma.setSoTimeout(AppsGlobals.getSetupProperty("license.multicast.receive.soTimeout", DEFAULT_RECEIVE_SO_TIMEOUT));
		mma.setTtl(AppsGlobals.getSetupProperty("license.multicast.ttl", DEFAULT_TTL));
		mma.afterPropertiesSet();
		return mma;
	}
	
	public static MagicMulticastAccessor createInstance(Properties props){
		MagicMulticastAccessor mma = new MagicMulticastAccessor();
		mma.setGroup(props.getProperty("license.multicast.group", DEFAULT_ADDR_GROUP));
		mma.setLocalAddr(props.getProperty("license.multicast.localAddr"));
		mma.setMaxTry(Integer.parseInt(props.getProperty("license.multicast.receive.maxTry", DEFAULT_RECEIVE_MAX_TRY + "")));
		mma.setSoTimeout(Integer.parseInt(props.getProperty("license.multicast.receive.soTimeout", DEFAULT_RECEIVE_SO_TIMEOUT + "")));
		mma.setTtl(Integer.parseInt(props.getProperty("license.multicast.ttl", DEFAULT_TTL + "")));
		try {
			mma.afterPropertiesSet();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return mma;
	}
	
	

	public static void main(String[] args) throws Exception{
//		ByteArrayOutputStream oss = new ByteArrayOutputStream();
//		DataOutputStream os = new DataOutputStream(oss);
//		os.writeInt(0x43210000);
//		System.out.println(oss.toByteArray().length);
//		for(byte b: oss.toByteArray()){
////			System.out.println(b);
//			System.out.println(Integer.toHexString(0xFF & b));
//		}
//		System.out.println("ssssssssssssssssssssssssssssssssssss");
//		byte[] bs = oss.toByteArray();
//		int result = 0;
//		for(int i = 0 ; i < 4 ; i++){
//			int x = bs[i] & 0xFF;
//			result |= x << (4 * 2 * (3 - i));
//		}
//		System.out.println(Integer.toHexString(result));
//		System.out.println("ssss--> " + bytesToInt(bs));
//		System.out.println("ssss--> " + Integer.toHexString(bytesToInt(bs)));
//		
//		System.out.println(Integer.toHexString(bs[1] << (4 * 4) | bs[0] << (4*6)));
//		System.out.println(Integer.toHexString(43210000));
//		
//		DataInputStream in = new DataInputStream(new ByteArrayInputStream(oss.toByteArray()));
//		int i = in.readInt();
//		System.out.println(i);
		
		MagicMulticastAccessor accessor = new MagicMulticastAccessor();
		accessor.afterPropertiesSet();
		accessor.execute(0x7833537, new MagicPacketCallback(){
			public Object doInMagicPacket(MulticastSocket socket, DatagramPacket packet) throws IOException {
				System.out.println(packet.getLength());
				ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
				DataInputStream dis = new DataInputStream(bis);
				dis.readInt();
				String serverInfo = dis.readUTF();
				long time = dis.readLong();
				String serviceURL = dis.readUTF();
				
				System.out.println(serverInfo);
				System.out.println(new java.util.Date(time));
				System.out.println(serviceURL);
				return null;
			}});
		
	}
}
