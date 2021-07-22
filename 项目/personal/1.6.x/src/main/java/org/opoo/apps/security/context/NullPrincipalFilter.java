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
 * �˹�������Ϊ�˽��Spring Security��Sun Java System Application Server 8.x�г���
 * java.lang.ClassCastException UsernamePasswordAuthenticationToken �������ġ�
 * 
 * 
 * �μ��� http://ozgwei.blogspot.com/2008/01/how-to-make-acegi-work-on-sun.html
 * 
 * ��spring security�������У���������������������һ����
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
