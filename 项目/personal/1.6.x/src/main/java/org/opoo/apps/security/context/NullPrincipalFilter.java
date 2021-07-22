package org.opoo.apps.security.context;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.ui.SpringSecurityFilter;


/**
 * 此过滤器是为了解决Spring Security在Sun Java System Application Server 8.x中出现
 * java.lang.ClassCastException UsernamePasswordAuthenticationToken 而创建的。
 * 
 * 
 * 参见： http://ozgwei.blogspot.com/2008/01/how-to-make-acegi-work-on-sun.html
 * 
 * 在spring security的配置中，将这个过滤器配置在最后一个。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class NullPrincipalFilter extends SpringSecurityFilter {

	@Override
	protected void doFilterHttp(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new NullPrincipalHttpServletRequestWrapper(request), response);
	}

	public int getOrder() {
		return Integer.MAX_VALUE - 100;
	}

	
	
	public static class NullPrincipalHttpServletRequestWrapper extends
			HttpServletRequestWrapper {
		public NullPrincipalHttpServletRequestWrapper(HttpServletRequest aReq) {
			super(aReq);
		}

		@Override
		public Principal getUserPrincipal() {
			return null;
		}
	} 
}
