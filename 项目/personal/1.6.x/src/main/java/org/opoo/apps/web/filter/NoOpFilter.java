package org.opoo.apps.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class NoOpFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
