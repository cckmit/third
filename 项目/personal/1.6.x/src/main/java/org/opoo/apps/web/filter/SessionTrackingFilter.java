package org.opoo.apps.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Associates the IP address used to create a session as an attribute of
 * the session itself. Used for tracking session information through
 * termination and to help identify potential anomolies in CDN behaviors.
 */
public final class SessionTrackingFilter implements Filter {

    public static final String SESSION_SIP_KEY = "_apps.session.sourceIP";
    public static final String SESSION_RESOURCE_KEY = "_apps.session.resource";

    private static final Log log = LogFactory.getLog(SessionTrackingFilter.class);

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest)servletRequest;
        final HttpSession session = request.getSession(false);


        if(session != null) {
            final String sipValue = (String)session.getAttribute(SESSION_SIP_KEY);

            //this filter hasn't processed the session yet, do so
            if(sipValue == null) {
                final String sip = request.getRemoteAddr();
                final String uri = request.getRequestURI();

                session.setAttribute(SESSION_SIP_KEY, sip);
                session.setAttribute(SESSION_RESOURCE_KEY, uri);

                log.info(String.format("Created: ID=%s;TO=%s;SIP=%s;RES=%s",
                    session.getId(), session.getMaxInactiveInterval(), sip, uri));
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        //no-op
    }

    public void destroy() {
        //no-op
    }
}