package org.opoo.apps.file.openoffice;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.AppsGlobals;
import org.springframework.beans.factory.InitializingBean;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class LifecycleSocketXConnectionProvider extends SocketXConnectionProvider implements InitializingBean {
	public static final String OPEN_OFFICE_HOST = "attachments.openoffice.host";
	public static final String OPEN_OFFICE_PORT = "attachments.openoffice.port";
	
    public LifecycleSocketXConnectionProvider(){
    	super();
    }
    
    public LifecycleSocketXConnectionProvider(String host){
    	super(host);
    }
    public LifecycleSocketXConnectionProvider(int port){
    	super(port);
    }
    
    public LifecycleSocketXConnectionProvider(String host, int port){
    	super(host, port);
    }
 
	public void afterPropertiesSet() throws Exception {
		String host = AppsGlobals.getSetupProperty(OPEN_OFFICE_HOST);
		String port = AppsGlobals.getSetupProperty(OPEN_OFFICE_PORT);
		if(StringUtils.isNotBlank(host)){
			super.setHost(host);
		}
		if(StringUtils.isNotBlank(port)){
			super.setPort(Integer.parseInt(port));
		}
	}
}
