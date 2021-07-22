package org.opoo.apps.license.client.mina;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.license.DefaultLicenseProvider;
import org.opoo.apps.license.net.NetUtils;

import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.LicenseProvider;

public class LicenseClientImplBAK implements LicenseClient {
	private ScheduledExecutorService executor;// = Executors.newScheduledThreadPool(5);
	
	private int transactionId = 1;
	private IoSession session;
	private NioSocketConnector connector;
	
	private String sessionId;
	private String encodedLicense;
	
	private LicenseProvider provider;
	private InetSocketAddress serverAddress;
	private boolean useSSL = false;
	private boolean alive = false;
	
	public LicenseClientImplBAK(LicenseProvider provider, InetSocketAddress serverAddress){
		this.provider = provider;
		this.serverAddress = serverAddress;
		this.executor = Executors.newScheduledThreadPool(2);
		
		// Create TCP/IP connector.
        connector = new NioSocketConnector();

        // Set connect timeout.
        connector.setConnectTimeoutMillis(30*1000L);
        
        connector.getFilterChain().addLast("mdc", new MdcInjectionFilter());
//        connector.getFilterChain().addLast("codec",
//                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        
        TextLineCodecFactory cf = new TextLineCodecFactory(Charset.forName("UTF-8"));
        cf.setDecoderMaxLineLength(2048);
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(cf));
        
        connector.setHandler(new LicenseClientHandler());
//        ConnectFuture cf = connector.connect(getServerAddress());

        // Wait for the connection attempt to be finished.
//        cf.awaitUninterruptibly();
//        cf.getSession().getCloseFuture().awaitUninterruptibly();
//        
//        session = cf.getSession();
        
//        connector.dispose();
        ConnectFuture future1 = connector.connect(getServerAddress());
        future1.awaitUninterruptibly();
        if (!future1.isConnected()) {
        	throw new LicenseException("Mina socket connect failed.");
        }
        session = future1.getSession();
        login();
        this.alive = true;
	}
	
	protected InetSocketAddress getServerAddress(){
		if(serverAddress == null){
			String addr = AppsGlobals.getSetupProperty("license.serviceURL");
			if(StringUtils.isNotBlank(addr)){
				try {
					serverAddress = NetUtils.translateSocketAddress(addr);
				} catch (UnknownHostException e) {
					//
				}
			}
		}
		if(serverAddress == null){
			int magicNumber = MinaServerAddressProvider.MINAP_MAGIC_NUMBER;
			if(useSSL){
				magicNumber = MinaServerAddressProvider.MINAP_SSL_MAGIC_NUMBER;
			}
			serverAddress = MinaServerAddressProvider.getServerAddress(magicNumber);
		}
		if(serverAddress == null){
			throw new LicenseException("can not found als mina server address.");
		}
		return serverAddress;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public String getEncodedLicense() {
		if(alive){
			return encodedLicense;
		}else{
			return null;
		}
	}

	public void start() {
		if(alive && session.isConnected()){
			sendPNG();
		}
	}

	public void stop() {
		if(alive){
			alive = false;
			executor.shutdown();
			if (session != null) {
				if (session.isConnected()) {
					sendAWY();
					// Wait until the chat ends.
					//session.getCloseFuture().awaitUninterruptibly();
				}
				session.close(true);
			}
			connector.dispose();
		}
	}

	public void check() {
		if(alive){
			sendCHK();
		}
	}
	
	public void send(String msg){
		session.write(msg);
		System.out.println(">> " + msg);
	}
	
	public void login(){
		sendVER();
	}
	
	void sendVER(){
		send("VER " + (transactionId++) + " ALSP1 CVR0");
	}
	
	void sendCVR(){
		Message out = new Message();
		out.setCommand("CVR");
		out.setTransactionId(transactionId++);
		List<String> args = new ArrayList<String>();
		args.add(System.getProperty("os.name").replace(' ', '_'));
		args.add(System.getProperty("os.version"));
		args.add(System.getProperty("os.arch"));
		args.add("ALC-" + provider.getVersion().toString().replace(" ", ""));
		args.add(provider.getName());
		args.add(AppsHome.getNodeID().toString());
		out.setArguments(args.toArray(new String[args.size()]));
		send(out.toString());
	}
	void sendGET(){
		send("GET " + (transactionId++) + " 0");
	}
	void sendCHK(){
		send("CHK " + (transactionId++) + " " + System.currentTimeMillis());
	}
	void sendAWY(){
		send("AWY " + (transactionId++) + " 0");
	}
	void sendPNG(){
		send("PNG " + (transactionId++) + " 0");
	}
	
	
	public void processVER(IoSession session, Message msg){
		System.out.println("使用协议：" + msg.getArguments()[0]);
		sendCVR();
	}
	public void processCVR(IoSession session, Message msg){
		if(msg.getArguments().length >= 3){
			sessionId = msg.getArguments()[2];
			System.out.println("Session id : " + sessionId);
			sendGET();
		}else{
			login();
		}
	}
	public void processGET(IoSession session, Message msg){
		if(msg.getArguments().length > 0){
			encodedLicense = msg.getArguments()[0];
		}else{
			login();
		}
	}
	
	public void processPNG(IoSession session, Message msg){
		String s = msg.getArguments()[0];
		final int se = Integer.parseInt(s);
		Runnable runnable = new Runnable(){
			public void run() {
				sendPNG();
			}
		};
		executor.schedule(runnable, se, TimeUnit.SECONDS);
	}
	public void processCHK(IoSession session, Message msg){
		
	}
	
	
	class LicenseClientHandler extends IoHandlerAdapter{
		@Override
		public void sessionCreated(IoSession session) throws Exception {
			super.sessionCreated(session);
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception {
			super.sessionOpened(session);
			//send(session, "VER " + (tid++) + " ALSP1 CVR0");
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {
			super.sessionClosed(session);
		}

		@Override
		public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
			super.sessionIdle(session, status);
		}

		@Override
		public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
			super.exceptionCaught(session, cause);
			LicenseClientImplBAK.this.stop();
		}

		@Override
		public void messageReceived(IoSession session, Object message) throws Exception {
			super.messageReceived(session, message);
			System.out.println("<< " + message);
			Message msg = new Message((String)message);
			lookupMethod(msg).invoke(LicenseClientImplBAK.this, session, msg);
		}
		
		private Method lookupMethod(Message msg) throws SecurityException, NoSuchMethodException{
			return LicenseClientImplBAK.class.getMethod("process" + msg.getCommand(), IoSession.class, Message.class);
		}

		@Override
		public void messageSent(IoSession session, Object message) throws Exception {
			super.messageSent(session, message);
		}
	}
	
	
	public static class Message{
		private String[] arguments;
		private int transactionId;
		private String command;
		public Message(){
		}
		public Message(String input){
			parseMessage(input);
		}
		public String[] getArguments() {
			return arguments;
		}
		public void setArguments(String[] arguments) {
			this.arguments = arguments;
		}
		public int getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(int transactionId) {
			this.transactionId = transactionId;
		}
		public String getCommand() {
			return command;
		}
		public void setCommand(String command) {
			this.command = command;
		}
		
		public String toString(){
			StringBuffer sb = new StringBuffer();
			sb.append(command).append(" ").append(transactionId);
			for(String arg: arguments){
				sb.append(" ").append(arg);
			}
			return sb.toString();
		}
		
		private void parseMessage(String message) {
			if(StringUtils.isBlank(message)){
				throw new IllegalArgumentException();
			}
			message = message.trim();
			if(message.length() < 7){
				throw new IllegalArgumentException();
			}
			
			StringTokenizer st = new StringTokenizer(message, " ");
			int tokenCount = st.countTokens();
			if(tokenCount < 3){
				throw new IllegalArgumentException();
			}
			
			//System.out.println("Message token count: " + tokenCount);
			
			this.command = st.nextToken();
			this.transactionId = -1;
			this.arguments = new String[tokenCount - 2];
			
			if(command.length() != 3){
				throw new IllegalArgumentException("command invalid: " + command);
			}
			
			try {
				transactionId = Integer.parseInt(st.nextToken());
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("transaction id error", e);
			}
			if(transactionId < 1){
				throw new IllegalArgumentException("transaction id invalid: " + transactionId);
			}
			
			int index = 0;
			while(st.hasMoreTokens()){
				arguments[index++] = st.nextToken();
			}
		}
	}

	
	private static LicenseClientImplBAK client;
	public static void main(String[] args) throws Exception{
		Runnable runnable = new Runnable(){
			public void run() {
				client = new LicenseClientImplBAK(new DefaultLicenseProvider(), new InetSocketAddress("localhost", 50555));
			}
		};
		new Thread(runnable).start();
		System.out.println("............");
		//System.out.println(client.getEncodedLicense());
		Thread.sleep(5000L);
		if(client != null){
			client.stop();
		}
	}
}
