package org.opoo.apps.license.hasp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.jivesoftware.license.License;

public class HaspRO implements Serializable {
	private static final long serialVersionUID = -4968811497085872075L;
	
	private License.Version version;
	private int allowedUserCount = -1;
	private int numClusterMembers = 1;
	public HaspRO() {
		super();
	}
	public License.Version getVersion() {
		return version;
	}
	public void setVersion(License.Version version) {
		this.version = version;
	}
	public int getAllowedUserCount() {
		return allowedUserCount;
	}
	public void setAllowedUserCount(int allowedUserCount) {
		this.allowedUserCount = allowedUserCount;
	}
	public int getNumClusterMembers() {
		return numClusterMembers;
	}
	public void setNumClusterMembers(int numClusterMembers) {
		this.numClusterMembers = numClusterMembers;
	}

	public void read(DataInput input) throws IOException{
		int major = input.readInt();
		int minor = input.readInt();
		int build = input.readInt();
		
		allowedUserCount = input.readInt();
		numClusterMembers = input.readInt();
		
		if(major < 0){
			major = 1;
		}
		if(minor < 0){
			minor = 0;
		}
		if(build < 0){
			build = 0;
		}
		this.setVersion(new License.Version(major, minor, build));
		
		if(allowedUserCount == 0){
			allowedUserCount = -1;
		}
		
		if(numClusterMembers == 0){
			numClusterMembers = 1;
		}
	}
	
	public void write(DataOutput output) throws IOException{
		License.Version version = getVersion();
		if(version == null){
			version = new License.Version(1, 0, 0);
		}
		output.writeInt(version.getMajor());
		output.writeInt(version.getMinor());
		output.writeInt(version.getRevision());
		output.writeInt(allowedUserCount);
		output.writeInt(numClusterMembers);
	}
	
	public byte[] toBytes() throws IOException{
//		ByteArrayOutputStream streamRaw = new ByteArrayOutputStream();
//      DataOutputStream streamData = new DataOutputStream(streamRaw);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(os);
		write(stream);
		byte[] data = os.toByteArray();
		IOUtils.closeQuietly(stream);
		IOUtils.closeQuietly(os);
		return data;
	}
	
	public void fromBytes(byte[] data) throws IOException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		DataInputStream stream = new DataInputStream(in);
		read(stream);
		IOUtils.closeQuietly(stream);
		IOUtils.closeQuietly(in);
	}
	
	public static void main(String[] args) throws IOException{
		HaspRO m = new HaspRO();
		m.setVersion(new License.Version(2, 0, 0));
		m.setAllowedUserCount(0);
		m.setNumClusterMembers(0);
		
		byte[] data = m.toBytes();
		for(byte b: data){
			System.out.print("0x" + Integer.toHexString(b) + ", ");
		}
		System.out.println();
		
		m = new HaspRO();
		m.fromBytes(data);
		System.out.println(m.getVersion());
		System.out.println(m.getAllowedUserCount());
		System.out.println(m.getNumClusterMembers());
		
		byte[] bs = new byte[112];
		for(int i = 0 ; i < 112 ; i++){
			bs[i] = 0;
		}
		System.arraycopy(data, 0, bs, 0, data.length);
		
		System.out.println(bs.length);
		System.out.println(new String(Base64.encodeBase64(bs)));
	}
}
