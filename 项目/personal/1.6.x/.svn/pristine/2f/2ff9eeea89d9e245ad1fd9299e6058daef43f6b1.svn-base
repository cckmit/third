package org.opoo.apps.web.struts2.action.admin.setup;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.lifecycle.ApplicationState;

import com.opensymphony.xwork2.ActionSupport;

public class SetupFinishAction extends SetupActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -427172921106782114L;
	private static final Log log = LogFactory.getLog(SetupFinishAction.class);
	
	public String execute()
    {
        UUID instanceUuid = null;
        try
        {
            String currentId = AppsGlobals.getProperty("appsInstanceId", null);
            if(null == currentId)
            {
                log.info("Generating new instance UUID.");
                instanceUuid = UUID.randomUUID();
                String s = instanceUuid.toString();
                AppsGlobals.setProperty("appsInstanceId", s);
                log.info("New instance UUID persisted.");
            } else
            {
                log.info("Instance UUID already present, skipping generation.");
                instanceUuid = UUID.fromString(currentId);
            }
        }
        catch(Exception ex)
        {
            String message = "Unexpected error building node and instance UUIDs.";
            log.warn(message, ex);
            addActionError(message);
            return ActionSupport.ERROR;
        }
        AppsGlobals.setSetupProperty("setup", "true");
        Application.setApplicationState(ApplicationState.SETUP_STARTED, ApplicationState.SETUP_COMPLETE);
        Application.setApplicationState(ApplicationState.SETUP_COMPLETE, ApplicationState.RESTART_REQUIRED);
        //license = licenseManager.getLicense();
        log.info("Application setup complete. Application restart required.");
        cleanupSession();
        return ActionSupport.SUCCESS;
    }


    private void cleanupSession()
    {
        Map session = getSession();
        Set set = session.keySet();
        for(Object o: set){
        	String key = (String) o;
        	if(key.startsWith("apps.")){
        		session.remove(key);
        	}
        }
    }

}
