package org.opoo.apps.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;



public class SetRequestCharacterEncodingFilter implements Filter {
	private static final Log log = LogFactory.getLog(SetRequestCharacterEncodingFilter.class);

    private String cachedEncoding = null;
    private boolean valid = false;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Sets the character encoding to be used for any content passing out of this filter.
     */ 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException
    {
        // Only do this if the application is setup, otherwise will have exceptions being thrown
        // until Jive home is specified
        if (AppsGlobals.isSetup()) {
            String encoding = AppsGlobals.getCharacterEncoding();
            if (cachedEncoding == null || !cachedEncoding.equals(encoding)) {
                cachedEncoding = encoding;
                if (!AppsGlobals.isValidCharacterEncoding(encoding)) {
                    log.error("Invalid character encoding: " + encoding + ", not setting an encoding.");
                }
                else {
                    valid = true;
                }
            }
            if (valid) {
                request.setCharacterEncoding(encoding);
            }
        }



        chain.doFilter(request, response);
    }

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
