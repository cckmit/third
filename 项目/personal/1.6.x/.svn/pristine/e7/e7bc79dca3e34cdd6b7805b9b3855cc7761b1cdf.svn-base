package org.opoo.apps.security.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint;


/**
 * 可为不同的URL指定不同的登录页面。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AppsAuthenticationProcessingFilterEntryPoint extends AuthenticationProcessingFilterEntryPoint {
	private static final Log log = LogFactory.getLog(AppsAuthenticationProcessingFilterEntryPoint.class);

	private FormLoginMap formLoginMap;



	/**
	 * @return the formLoginMap
	 */
	public FormLoginMap getFormLoginMap() {
		return formLoginMap;
	}

	/**
	 * @param formLoginMap the formLoginMap to set
	 */
	public void setFormLoginMap(FormLoginMap formLoginMap) {
		this.formLoginMap = formLoginMap;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint#commence(javax.servlet.ServletRequest, javax.servlet.ServletResponse, org.springframework.security.AuthenticationException)
	 */
	@Override
	public void commence(ServletRequest request, ServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		super.commence(request, response, authException);
	}
	
    /**
     * Obtains the web application-specific fragment of the URL.
     *
     * @return the URL, excluding any server name, context path or servlet path
     */
    private static String buildRequestUrl(String servletPath, String requestURI, String contextPath, String pathInfo,
        String queryString) {

        String uri = servletPath;

        if (uri == null) {
            uri = requestURI;
            uri = uri.substring(contextPath.length());
        }

        return uri + ((pathInfo == null) ? "" : pathInfo) + ((queryString == null) ? "" : ("?" + queryString));
    }
    
    public static String getRequestUrl(HttpServletRequest r) {
        return buildRequestUrl(r.getServletPath(), r.getRequestURI(), r.getContextPath(), r.getPathInfo(),
            r.getQueryString());
    }

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint#determineUrlToUseForThisRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.AuthenticationException)
	 */
	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		if(formLoginMap != null){
			//FilterInvocation fi = new FilterInvocation(request, response, null);
			String url = getRequestUrl(request);//fi.getRequestUrl();
			
			FormLogin formLogin = formLoginMap.lookupFormLogin(url);
			if(formLogin != null){
				
				if(log.isDebugEnabled()){
					log.debug("Using login page '" + formLogin.getLoginPage() + "' for '" + url + "'.");
				}
				//放进Session
				WebUtils.setFormLogin(request, formLogin);
				return formLogin.getLoginPage();
			}
		}
		
		return super.determineUrlToUseForThisRequest(request, response, exception);
	}
	
	
   

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
	}
    
}
