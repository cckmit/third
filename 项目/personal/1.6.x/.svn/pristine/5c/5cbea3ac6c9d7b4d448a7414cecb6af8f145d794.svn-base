package org.opoo.apps.security.webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.ui.savedrequest.SavedRequest;
import org.springframework.security.util.PortResolver;

@SuppressWarnings("unchecked")
public class SavedRequestWrapper extends SavedRequest {
	
	private static class NoneHttpServletRequest implements HttpServletRequest{
		private static Enumeration emtpyEnumeration = new Enumeration(){
			public boolean hasMoreElements() {
				return false;
			}
			public Object nextElement() {
				return null;
			}
        };
      

		public String getAuthType() {
			return null;
		}

		public String getContextPath() {
			return null;
		}

		public Cookie[] getCookies() {
			return null;
		}

		public long getDateHeader(String name) {
			return 0;
		}

		public String getHeader(String name) {
			return null;
		}

		public Enumeration getHeaderNames() {
			return emtpyEnumeration;
		}

		public Enumeration getHeaders(String name) {
			return emtpyEnumeration;
		}

		public int getIntHeader(String name) {
			return 0;
		}

		public String getMethod() {
			return null;
		}

		public String getPathInfo() {
			return null;
		}

		public String getPathTranslated() {
			return null;
		}

		public String getQueryString() {
			return null;
		}

		public String getRemoteUser() {
			return null;
		}

		public String getRequestURI() {
			return null;
		}

		public StringBuffer getRequestURL() {
			return new StringBuffer();
		}

		public String getRequestedSessionId() {
			return null;
		}

		public String getServletPath() {
			return null;
		}

		public HttpSession getSession() {
			return null;
		}

		public HttpSession getSession(boolean create) {
			return null;
		}

		public Principal getUserPrincipal() {
			return null;
		}

		public boolean isRequestedSessionIdFromCookie() {
			return false;
		}

		public boolean isRequestedSessionIdFromURL() {
			return false;
		}

		public boolean isRequestedSessionIdFromUrl() {
			return false;
		}

		public boolean isRequestedSessionIdValid() {
			return false;
		}

		public boolean isUserInRole(String role) {
			return false;
		}

		public Object getAttribute(String name) {
			return null;
		}

		public Enumeration getAttributeNames() {
			return null;
		}

		public String getCharacterEncoding() {
			return null;
		}

		public int getContentLength() {
			return 0;
		}

		public String getContentType() {
			return null;
		}

		public ServletInputStream getInputStream() throws IOException {
			return null;
		}

		public String getLocalAddr() {
			return null;
		}

		public String getLocalName() {
			return null;
		}

		public int getLocalPort() {
			return 0;
		}

		public Locale getLocale() {
			return null;
		}

		public Enumeration getLocales() {
			return emtpyEnumeration;
		}

		public String getParameter(String name) {
			return null;
		}

		public Map getParameterMap() {
			return new HashMap();
		}

		public Enumeration getParameterNames() {
			return emtpyEnumeration;
		}

		public String[] getParameterValues(String name) {
			return null;
		}

		public String getProtocol() {
			return null;
		}

		public BufferedReader getReader() throws IOException {
			return null;
		}

		public String getRealPath(String path) {
			return null;
		}

		public String getRemoteAddr() {
			return null;
		}

		public String getRemoteHost() {
			return null;
		}

		public int getRemotePort() {
			return 0;
		}

		public RequestDispatcher getRequestDispatcher(String path) {
			return null;
		}

		public String getScheme() {
			return null;
		}

		public String getServerName() {
			return null;
		}

		public int getServerPort() {
			return 0;
		}

		public boolean isSecure() {
			return false;
		}

		public void removeAttribute(String name) {
			
		}

		public void setAttribute(String name, Object o) {
			
		}

		public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
			
		}
	}
	
	private static class NonePortResolver implements PortResolver{
		public int getServerPort(ServletRequest request) {
			return 0;
		}
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7360481011692932204L;
	private final SavedRequest savedRequest;

	public SavedRequestWrapper(SavedRequest savedRequest) {
		super(new NoneHttpServletRequest(), new NonePortResolver());
		this.savedRequest = savedRequest;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#doesRequestMatch(javax.servlet.http.HttpServletRequest, org.springframework.security.util.PortResolver)
	 */
	@Override
	public boolean doesRequestMatch(HttpServletRequest request, PortResolver portResolver) {
		return savedRequest.doesRequestMatch(request, portResolver);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getContextPath()
	 */
	@Override
	public String getContextPath() {
		return savedRequest.getContextPath();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getCookies()
	 */
	@Override
	public List getCookies() {
		return savedRequest.getCookies();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getFullRequestUrl()
	 */
	@Override
	public String getFullRequestUrl() {
		return savedRequest.getFullRequestUrl();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getHeaderNames()
	 */
	@Override
	public Iterator getHeaderNames() {
		return savedRequest.getHeaderNames();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getHeaderValues(java.lang.String)
	 */
	@Override
	public Iterator getHeaderValues(String name) {
		return savedRequest.getHeaderValues(name);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getLocales()
	 */
	@Override
	public Iterator getLocales() {
		return savedRequest.getLocales();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getMethod()
	 */
	@Override
	public String getMethod() {
		return savedRequest.getMethod();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getParameterMap()
	 */
	@Override
	public Map getParameterMap() {
		return savedRequest.getParameterMap();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getParameterNames()
	 */
	@Override
	public Iterator getParameterNames() {
		return savedRequest.getParameterNames();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) {
		return savedRequest.getParameterValues(name);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getPathInfo()
	 */
	@Override
	public String getPathInfo() {
		return savedRequest.getPathInfo();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getQueryString()
	 */
	@Override
	public String getQueryString() {
		return savedRequest.getQueryString();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getRequestURI()
	 */
	@Override
	public String getRequestURI() {
		return savedRequest.getRequestURI();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getRequestUrl()
	 */
	@Override
	public String getRequestUrl() {
		return savedRequest.getRequestUrl();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getRequestURL()
	 */
	@Override
	public String getRequestURL() {
		return savedRequest.getRequestURL();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getScheme()
	 */
	@Override
	public String getScheme() {
		return savedRequest.getScheme();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getServerName()
	 */
	@Override
	public String getServerName() {
		return savedRequest.getServerName();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getServerPort()
	 */
	@Override
	public int getServerPort() {
		return savedRequest.getServerPort();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#getServletPath()
	 */
	@Override
	public String getServletPath() {
		return savedRequest.getServletPath();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.savedrequest.SavedRequest#toString()
	 */
	@Override
	public String toString() {
		return "SavedRequestWrapper[" + getFullRequestUrl() + "]";
	}
	
	
	
	public static void main(String[] args){
		SavedRequestWrapper asr = new SavedRequestWrapper(null);
		System.out.println(asr.getClass());
	}
}
