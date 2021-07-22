package org.opoo.apps.security.webapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.Assert;
import org.springframework.security.ui.savedrequest.SavedRequest;
import org.springframework.security.util.PortResolver;


@SuppressWarnings("unchecked")
public class AppsSavedRequest extends SavedRequestWrapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3792986201001788221L;

	private static final Log log = LogFactory.getLog(AppsSavedRequest.class);

	private final static Iterator emptyIterator = new Iterator(){
		public boolean hasNext() {
			return false;
		}

		public Object next() {
			return null;
		}

		public void remove() {
		}
	};
	
	
	private final FormLogin formLogin; 
    
    private final boolean hasTargetUrl;
	public AppsSavedRequest(SavedRequest savedRequest, FormLogin formLogin) {
		super(savedRequest);
		Assert.notNull(formLogin, "FormLogin is required.");
		this.formLogin = formLogin;
		this.hasTargetUrl = formLogin.getTargetUrl() != null;
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#doesRequestMatch(javax.servlet.http.HttpServletRequest, org.springframework.security.util.PortResolver)
	 */
	@Override
	public boolean doesRequestMatch(HttpServletRequest request, PortResolver portResolver) {
		return super.doesRequestMatch(request, portResolver);
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getContextPath()
	 */
	@Override
	public String getContextPath() {
		return super.getContextPath();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getCookies()
	 */
	@Override
	public List getCookies() {
		if(hasTargetUrl){
			return null;
		}
		return super.getCookies();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getFullRequestUrl()
	 */
	@Override
	public String getFullRequestUrl() {
		String url = super.getFullRequestUrl();
		if(log.isDebugEnabled()){
			log.debug("完整路径：" + url);
		}
		return url;
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getHeaderNames()
	 */
	@Override
	public Iterator getHeaderNames() {
		if(hasTargetUrl){
			return emptyIterator;
		}
		return super.getHeaderNames();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getHeaderValues(java.lang.String)
	 */
	@Override
	public Iterator getHeaderValues(String name) {
		if(hasTargetUrl){
			return emptyIterator;
		}
		return super.getHeaderValues(name);
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getLocales()
	 */
	@Override
	public Iterator getLocales() {
		if(hasTargetUrl){
			return emptyIterator;
		}
		return super.getLocales();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getMethod()
	 */
	@Override
	public String getMethod() {
		if(hasTargetUrl){
			return "GET";
		}
		return super.getMethod();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getParameterMap()
	 */
	@Override
	public Map getParameterMap() {
		if(hasTargetUrl){
			return new HashMap();
		}
		return super.getParameterMap();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getParameterNames()
	 */
	@Override
	public Iterator getParameterNames() {
		if(hasTargetUrl){
			return emptyIterator;
		}
		return super.getParameterNames();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) {
		if(hasTargetUrl){
			return null;
		}
		return super.getParameterValues(name);
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getPathInfo()
	 */
	@Override
	public String getPathInfo() {
		if(hasTargetUrl){
			return null;
		}
		return super.getPathInfo();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getQueryString()
	 */
	@Override
	public String getQueryString() {
		if(hasTargetUrl){
			return null;
		}
		return super.getQueryString();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getRequestURI()
	 */
	@Override
	public String getRequestURI() {
		if(hasTargetUrl){
			return super.getContextPath() + formLogin.getTargetUrl();
		}
		return super.getRequestURI();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getRequestUrl()
	 */
	@Override
	public String getRequestUrl() {
		return super.getRequestUrl();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getRequestURL()
	 */
	@Override
	public String getRequestURL() {
		if(hasTargetUrl){
			//http://localhost:8080/spring-security-samples-contacts-2.0.4/s2/index.jsp
			String url = super.getRequestURL();
			int index = url.indexOf("://");
			index = url.indexOf("/", index);
			
			url = url.substring(0, index);
			url += super.getContextPath() + formLogin.getTargetUrl();
			
			log.debug("请求 RequestURL：" + url);
			return url;
		}
		return super.getRequestURL();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getScheme()
	 */
	@Override
	public String getScheme() {
		return super.getScheme();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getServerName()
	 */
	@Override
	public String getServerName() {
		return super.getServerName();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getServerPort()
	 */
	@Override
	public int getServerPort() {
		return super.getServerPort();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#getServletPath()
	 */
	@Override
	public String getServletPath() {
		if(hasTargetUrl){
			return formLogin.getTargetUrl();
		}
		return super.getServletPath();
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.webapp.SavedRequestWrapper#toString()
	 */
	@Override
	public String toString() {
		return "AppsSavedRequest[" + getFullRequestUrl() + "]";
	}
}
