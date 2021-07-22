package org.opoo.apps.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.web.RichClientChecker;

public abstract class AbstractFilter implements Filter {
	protected final Log log = LogFactory.getLog(getClass());
	protected final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();

	private RichClientChecker richClientChecker;

	public void destroy() {
		richClientChecker = null;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		richClientChecker = RichClientChecker.Holder.getRichClientChecker(filterConfig.getServletContext());
	}
	

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		doFilterInternal(request, response, chain);		
	}


	/**
	 * Same contract as for <code>doFilter</code>, but guaranteed to be
	 * just invoked once per request. Provides HttpServletRequest and
	 * HttpServletResponse arguments instead of the default ServletRequest
	 * and ServletResponse ones.
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	protected abstract void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException;

	protected boolean isRichClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (richClientChecker == null) {
			return false;
		}
		return richClientChecker.isRichClient(request, response);
	}

	protected void sendToRichClient(HttpServletRequest request, HttpServletResponse response, int status,
			String location) throws IOException {
		if (richClientChecker != null) {
			richClientChecker.sendToClient(request, response, status, location);
		}
	}
}
