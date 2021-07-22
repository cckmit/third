/**
 * 
 */
package org.opoo.apps.license.client.ws;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.zip.Adler32;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.license.multicast.MagicMulticastAccessor.MagicPacketCallback;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ServiceUrlExtractor implements MagicPacketCallback {
	private static final Log log = LogFactory.getLog(ServiceUrlExtractor.class);
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.license.multicast.ServiceInfoMulticastAccessor.MagicPacketCallback#doInMagicPacket(java.net.MulticastSocket, java.net.DatagramPacket)
	 */
	public Object doInMagicPacket(MulticastSocket socket, DatagramPacket packet) throws IOException {
		//System.out.println(packet.getLength());
		ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
		DataInputStream dis = new DataInputStream(bis);
		
		int length = packet.getLength();
		dis.readInt();
		String serverInfo = dis.readUTF();
		long time = dis.readLong();
		String serviceURL = dis.readUTF();
		long checksum = dis.readLong();
		
		dis.close();
		bis.close();
		
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
	}
	
	protected static long getChecksum(byte[] data){
		Adler32 adler32 = new Adler32();
		adler32.update(data);
		return adler32.getValue();
	}
}
