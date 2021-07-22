package org.opoo.apps.license.client.mina;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.DisposableBean;

public class ClientIoHandler extends IoHandlerAdapter implements DisposableBean{
	public static final int CONNECT_TIMEOUT = 3000;

	private ClientIoListener listener;
	private SocketConnector connector;
	private SocketAddress serverAddress;
    private IoSession session;
    
	public ClientIoHandler(SocketAddress serverAddress, ClientIoListener listener) {
		this.listener = listener;
		this.serverAddress = serverAddress;
		
		// Create TCP/IP connector.
        connector = new NioSocketConnector();

        // Set connect timeout.
        connector.setConnectTimeoutMillis(30*1000L);
        
        connector.getFilterChain().addLast("mdc", new MdcInjectionFilter());
//      connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        
        TextLineCodecFactory cf = new TextLineCodecFactory(Charset.forName("UTF-8"));
        cf.setDecoderMaxLineLength(2048);
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(cf));
        
        connector.setHandler(this);
	}
	
	/**
	 * 
	 * @return
	 */
    public boolean isConnected() {
        return (session != null && session.isConnected());
    }

    /**
     * 
     */
    public void connect() {
        ConnectFuture connectFuture = connector.connect(serverAddress);
        connectFuture.awaitUninterruptibly(CONNECT_TIMEOUT);
        try {
            session = connectFuture.getSession();
        }
        catch (RuntimeIoException e) {
        	listener.onException(e);
        }
    }

    public void disconnect() {
        if (session != null) {
            session.close(true).awaitUninterruptibly(CONNECT_TIMEOUT);
            session = null;
        }
    }

	public void destroy() throws Exception {
		disconnect();
		connector.dispose();
	}
	
	
	public void sendMessage(String msg){
		if (session == null) {
            //noinspection ThrowableInstanceNeverThrown
			listener.onException(new Throwable("not connected"));
        } else {
            session.write(msg);
        }
	}


	@Override
	public void sessionOpened(IoSession session) throws Exception {
		listener.sessionOpened();
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		listener.sessionClosed();
	}


	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		listener.onException(cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		//System.out.println("<<" + message);
		System.out.println("<< " + message);
		ClientIoMessage msg = new ClientIoMessage((String)message);
		listener.onMessage(msg);
	}

	
	public static void main(String[] args) throws Exception{
		InetSocketAddress address = new InetSocketAddress("localhost", 50555);
		ClientIoHandler ioHandler = new ClientIoHandler(address, new ClientIoListener() {
			public void onException(Throwable e) {
				System.out.println(e);
			}

			public void onMessage(ClientIoMessage msg) {
			}

			public void sessionClosed() {
			}

			public void sessionOpened() {
			}
		});
		ioHandler.connect();
		ioHandler.sendMessage("VER 1 ASP1 CVR0");
		
		System.out.println("asdasd");
		Thread.sleep(3000L);
		System.out.println("asdasd");
		ioHandler.destroy();
	}
}
