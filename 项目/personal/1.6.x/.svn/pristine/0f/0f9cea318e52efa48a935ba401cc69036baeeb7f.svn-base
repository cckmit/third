package org.opoo.apps.license.client.mina;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsHome;
import org.opoo.apps.license.DefaultLicenseProvider;

import com.jivesoftware.license.LicenseProvider;

public class LicenseClientImpl /*extends JFrame*/ implements LicenseClient,ClientIoListener {
	//private static final long serialVersionUID = 2383050660119238586L;

	private static final Log log = LogFactory.getLog(LicenseClientImpl.class);
	
	private ScheduledExecutorService scheduledExecutorService;// = Executors.newScheduledThreadPool(5);
	private ClientIoHandler handler;
	
	private int transactionId = 1;
	private String sessionId;
	private String encodedLicense;
	
	private LicenseProvider provider;
//	private InetSocketAddress serverAddress;
//	private boolean useSSL = false;
	
	public LicenseClientImpl(LicenseProvider provider, InetSocketAddress serverAddress){
		//super("License Client");
		
		this.scheduledExecutorService = Executors.newScheduledThreadPool(2);
		this.provider = provider;
//		this.serverAddress = serverAddress;
//		this.useSSL = useSSL;
		this.handler = new ClientIoHandler(serverAddress, this);
		//init();
	}
	
	
	
	
//	protected InetSocketAddress getServerAddress(){
//		if(serverAddress == null){
//			String addr = AppsGlobals.getSetupProperty("license.serviceAddress");
//			if(StringUtils.isNotBlank(addr)){
//				try {
//					serverAddress = NetUtils.translateSocketAddress(addr);
//				} catch (UnknownHostException e) {
//					//
//				}
//			}
//		}
//		if(serverAddress == null){
//			int magicNumber = MinaServerAddressProvider.MINAP_MAGIC_NUMBER;
//			if(useSSL){
//				magicNumber = MinaServerAddressProvider.MINAP_SSL_MAGIC_NUMBER;
//			}
//			serverAddress = MinaServerAddressProvider.getServerAddress(magicNumber);
//		}
//		if(serverAddress == null){
//			throw new LicenseException("can not found als mina server address.");
//		}
//		return serverAddress;
//	}
	

	
//	private void init() {
//		JButton loginButton = new JButton("Connect");
//		loginButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				connect();			
//			}
//		});
//		JButton showButton = new JButton("Show License");
//		showButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(LicenseClientImpl.this, getEncodedLicense());
//			}
//		});
//		
//		JButton awyButton = new JButton("Away");
//		awyButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				stop();
//			}
//		});
//		
//		
//		JPanel right = new JPanel();
//        right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
//        right.add(loginButton);
//        right.add(showButton);
//        right.add(awyButton);
//        
//        getContentPane().add(right);
//        
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//	}




	public void send(String msg){
		System.out.println(">> " + msg);
		handler.sendMessage(msg);
	}
	
	void sendVER(){
		send("VER " + (transactionId++) + " ALSP1 CVR0");
	}
	
	void sendCVR(){
		ClientIoMessage out = new ClientIoMessage();
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
	
	
	public void processVER(ClientIoMessage msg){
		System.out.println("使用协议：" + msg.getArguments()[0]);
		sendCVR();
	}
	public void processCVR(ClientIoMessage msg){
		if(msg.getArguments().length >= 3){
			sessionId = msg.getArguments()[2];
			System.out.println("Session id : " + sessionId);
			sendGET();
		}else{
			connect();
		}
	}
	public void processGET(ClientIoMessage msg){
		if(msg.getArguments().length > 0){
			encodedLicense = msg.getArguments()[0];
		}else{
			connect();
		}
	}
	
	public void processPNG(ClientIoMessage msg){
		String s = msg.getArguments()[0];
		final int se = Integer.parseInt(s);
		Runnable runnable = new Runnable(){
			public void run() {
				sendPNG();
			}
		};
		scheduledExecutorService.schedule(runnable, se, TimeUnit.SECONDS);
	}
	public void processCHK(ClientIoMessage msg){
		
	}
	
	
	public void connect(){
//		if(!handler.isConnected()){
			handler.connect();
//			log.debug("ssssssss");
//		}
	}
	
	
	public void onException(Throwable e) {
		log.error(e.getMessage());
//		JOptionPane.showMessageDialog(null, e.getMessage());
		encodedLicense = null;
	}

	public void onMessage(ClientIoMessage msg){
		try {
			Method method = LicenseClientImpl.class.getMethod("process" + msg.getCommand(), ClientIoMessage.class);
			method.invoke(this, msg);
		} catch (Exception e) {
			onException(e);
		} 
	}

	public void sessionClosed() {
		log.debug("Session closed");
	}

	public void sessionOpened() {
		log.debug("Session opened");
		sendVER();
	}

	public String getEncodedLicense() {
		int count = 0;
		while(encodedLicense == null && count < 3){
			log.debug("try #" + count);
			connect();
			count++;
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				//
			}
		}
		return encodedLicense;
	}

	public void start() {
		sendPNG();
	}

	public void stop() {
		encodedLicense = null;
		try {
			sendAWY();
			handler.disconnect();
			scheduledExecutorService.shutdownNow();
		} catch (Exception e) {
			//
		}		
	}

	public boolean isAlive() {
		return handler.isConnected();
	}

	public void check() {
		sendCHK();
	}
	
	public static void main(String[] args){
		InetSocketAddress address = new InetSocketAddress("localhost", 50555);
		LicenseClientImpl client = new LicenseClientImpl(new DefaultLicenseProvider(), address);
		//impl.getEncodedLicense();
		//client.pack();
        //client.setVisible(true);
		String string = client.getEncodedLicense();
		System.out.println(string);
		string = client.getEncodedLicense();
		System.out.println(string);
	}
}
