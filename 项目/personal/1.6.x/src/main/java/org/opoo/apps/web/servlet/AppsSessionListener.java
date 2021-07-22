package org.opoo.apps.web.servlet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.web.filter.SessionTrackingFilter;


public class AppsSessionListener implements HttpSessionListener {

    private static final Log log = LogFactory.getLog(AppsSessionListener.class);
    private int counter = 0;

    public void sessionCreated(HttpSessionEvent event) {
        counter++;
        log.debug(counter + " active sessions.");
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        counter--;
        if(event != null) {
            if(log.isInfoEnabled()) {
                final HttpSession session = event.getSession();
                final String remoteAddr = (String)session.getAttribute(SessionTrackingFilter.SESSION_SIP_KEY);
                final String resource = (String)session.getAttribute(SessionTrackingFilter.SESSION_RESOURCE_KEY);

                log.debug(String.format("Evicted: ID=%s;created=%s;last=%s;SIP=%s;RES=%s;",
                        session.getId(), session.getCreationTime(), session.getLastAccessedTime(),
                        remoteAddr, resource));
            }
        }
        log.debug(counter + " active sessions.");
    }
}