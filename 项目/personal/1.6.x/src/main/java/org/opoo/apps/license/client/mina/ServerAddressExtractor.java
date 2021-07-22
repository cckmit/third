package org.opoo.apps.license.client.mina;

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

public class ServerAddressExtractor implements MagicPacketCallback{
	private static final Log log = LogFactory.getLog(ServerAddressExtractor.class);
	
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
		String serverAddress = dis.readUTF();
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
		
		if(StringUtils.isBlank(serverAddress)){
			return null;
		}
		
		long ck = getChecksum(serverAddress.getBytes());

		//System.out.println(ck);
		if(log.isDebugEnabled()){
			log.debug("Recived serverAddress: " + serverAddress);
			log.debug("checksum: " + ck);
		}
		
		if(ck != checksum){
			log.warn("The serverAddress checksum failed.");
			return null;
		}
		return serverAddress;
	}
	
	protected static long getChecksum(byte[] data){
		Adler32 adler32 = new Adler32();
		adler32.update(data);
		return adler32.getValue();
	}
}
