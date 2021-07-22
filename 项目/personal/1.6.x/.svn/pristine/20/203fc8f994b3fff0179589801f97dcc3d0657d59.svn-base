package org.opoo.apps.file.openoffice;

import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;

public class SocketXConnectionProvider extends AbstractConnectionProvider {
	
	public static final String DEFAULT_HOST = SocketOpenOfficeConnection.DEFAULT_HOST;
	public static final String DEFAULT_IP = "127.0.0.1";
    public static final int DEFAULT_PORT = SocketOpenOfficeConnection.DEFAULT_PORT;
    
    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    
    public SocketXConnectionProvider(){
    	super();
    }
    
    public SocketXConnectionProvider(String host){
    	this.host = host;
    }
    public SocketXConnectionProvider(int port){
    	this.port = port;
    }
    
    public SocketXConnectionProvider(String host, int port){
    	this.host = host;
    	this.port = port;
    }
    
	public XConnection getConnection() {
		String host = getHost();
		int port = getPort();
		SocketOpenOfficeConnection conn = new SocketOpenOfficeConnection(host, port);
		if(isLocalhost(host)){
			return new LocalConnection(conn);
		}else{
			return new RemoteConnection(conn);
		}
	}

	private boolean isLocalhost(String host){
		return (DEFAULT_HOST.equalsIgnoreCase(host) || DEFAULT_IP.equals(host));
//		//更精确的判断方法
//		
//		try {
//			InetAddress inetaddress = InetAddress.getByName(host);
//			ServerSocket serversocket = new ServerSocket(0, 1, inetaddress);
//			serversocket.close();
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
}
