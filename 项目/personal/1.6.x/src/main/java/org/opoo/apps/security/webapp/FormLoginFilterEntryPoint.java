package org.opoo.apps.security.webapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.savedrequest.SavedRequest;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint;
import org.springframework.util.StringUtils;

public class FormLoginFilterEntryPoint extends AuthenticationProcessingFilterEntryPoint {
	private static final Log log = LogFactory.getLog(FormLoginFilterEntryPoint.class);

	private FormLoginMap formLoginMap;
	private boolean alwaysUseDefaultTargetUrl = false;

	/**
	 * @return the alwaysUseDefaultTargetUrl
	 */
	public boolean isAlwaysUseDefaultTargetUrl() {
		return alwaysUseDefaultTargetUrl;
	}

	/**
	 * @param alwaysUseDefaultTargetUrl the alwaysUseDefaultTargetUrl to set
	 */
	public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
		this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
	}

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
		prepareFormLogin(request, response, authException);
		super.commence(request, response, authException);
	}
	
	/**
	 * 准备验证码，表单 Token 等。
	 * 表单Token 是一种防止用户通过外部程序尝试登录破解用户账号的方法。
	 * 一般是产生随机数放置在表单的隐藏字段中，后台检验这个数。
	 * 
	 * @param request
	 * @param response
	 * @param authException
	 * @throws IOException
	 * @throws ServletException
	 */
    protected void prepareFormLogin(ServletRequest request, ServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
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
    
    
    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
    	SavedRequest savedRequest = getAndRemoveSavedRequest(request);
    	String targetUrl = null;
    	String loginPage = null;
    	if(savedRequest != null){
    		String url = savedRequest.getRequestUrl();
    		System.out.println(url);
    		System.out.println(savedRequest.getFullRequestUrl());
    		System.out.println(request.getQueryString());
    		System.out.println(savedRequest.getQueryString());
    		
    		if(formLoginMap != null){
    			FormLogin formLogin = formLoginMap.lookupFormLogin(url);
    			if(formLogin != null){
    				if(log.isDebugEnabled()){
    					log.debug("Using login page '" + formLogin.getLoginPage() + "' for '" + url + "'.");
    				}
    				loginPage = formLogin.getLoginPage();
    				targetUrl = formLogin.getTargetUrl();
    			}
    		}
    		if(targetUrl == null){
    			targetUrl = url;
    		}
    	}
    	
    	if(loginPage == null){
    		loginPage = super.determineUrlToUseForThisRequest(request, response, exception);    		
    	}
    	
    	if(targetUrl != null && "GET".equalsIgnoreCase(request.getMethod()) && !alwaysUseDefaultTargetUrl){
//			if(targetUrl == null){
//				StringBuffer u = request.getRequestURL();
//				String query = request.getQueryString();
//				if(query != null){
//					u.append("?").append(query);
//				}
//				targetUrl = u.toString();
//			}
			
			int index = loginPage.indexOf("?");
			String loginUrl = index == -1 ? loginPage : loginPage.substring(0, index);
			if(!targetUrl.contains(loginUrl)){
				//System.out.println("TargetURL----> " + targetUrl);
				try {
					targetUrl = URLEncoder.encode(response.encodeURL(targetUrl), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new IllegalStateException("UTF-8 not supported. Shouldn't be possible");
				}
				//System.out.println("TargetURL----> " + targetUrl);
				loginPage += (loginPage.indexOf('?') == -1 ? "?" : "&");
				loginPage += WebUtils.TARGET_URL_PARAM + "=" + targetUrl;
				
			}else{
				log.warn("目标 URL 包含登录页面，将引起递归调用，忽略目标 URL： " + targetUrl);
			}
		}
    	
    	return loginPage;
    }
	protected String determineUrlToUseForThisRequest3(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
    	SavedRequest savedRequest = getSavedRequest(request);
    	if(savedRequest != null && formLoginMap != null && !(savedRequest instanceof AppsSavedRequest)){
    		String url = getRequestUrl(request);//fi.getRequestUrl();
			
			FormLogin formLogin = formLoginMap.lookupFormLogin(url);
			if(formLogin != null){
				if(log.isDebugEnabled()){
					log.debug("Using login page '" + formLogin.getLoginPage() + "' for '" + url + "'.");
				}
				
				savedRequest = new AppsSavedRequest(savedRequest, formLogin);
				request.getSession().setAttribute(AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY, savedRequest);
			}
    	}
    	
    	return super.determineUrlToUseForThisRequest(request, response, exception);
    }
    
	/* 
	 * 目的是将 targetURL 在这里指定。
	 * (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint#determineUrlToUseForThisRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.AuthenticationException)
	 */
	protected String determineUrlToUseForThisRequest2(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		String loginPage = null;
		String targetUrl = null;
//		String url = getRequestUrl(request);//fi.getRequestUrl();
		if(formLoginMap != null){
			//FilterInvocation fi = new FilterInvocation(request, response, null);
			String url = getRequestUrl(request);//fi.getRequestUrl();
			
			FormLogin formLogin = formLoginMap.lookupFormLogin(url);
			if(formLogin != null){
				
				if(log.isDebugEnabled()){
					log.debug("Using login page '" + formLogin.getLoginPage() + "' for '" + url + "'.");
				}
				//放进Session
//				SessionUtils.setFormLogin(request, formLogin);
				loginPage = formLogin.getLoginPage();
				
				if(StringUtils.hasText(formLogin.getTargetUrl())){
					targetUrl = formLogin.getTargetUrl();
				}
			}
		}
		if(loginPage == null){
			loginPage = super.determineUrlToUseForThisRequest(request, response, exception);
		}
		
		if(request.getMethod().equals("GET") && !alwaysUseDefaultTargetUrl){
			if(targetUrl == null){
				StringBuffer u = request.getRequestURL();
				String query = request.getQueryString();
				if(query != null){
					u.append("?").append(query);
				}
				targetUrl = u.toString();
			}
			
			int index = loginPage.indexOf("?");
			String loginUrl = index == -1 ? loginPage : loginPage.substring(0, index);
			if(!targetUrl.contains(loginUrl)){
				try {
					targetUrl = URLEncoder.encode(targetUrl, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new IllegalStateException("UTF-8 not supported. Shouldn't be possible");
				}
			
				loginPage += (loginPage.indexOf('?') == -1 ? "?" : "&");
				loginPage += WebUtils.TARGET_URL_PARAM + "=" + targetUrl;
				
			}else{
				log.warn("目标 URL 包含登录页面，将引起递归调用，忽略目标 URL： " + targetUrl);
			}
		}
		
		
		//不保存 SavedRequest 对象。
		//getAndRemoveSavedRequest(request);
		
		return loginPage;
		
		//return super.determineUrlToUseForThisRequest(request, response, exception);
	}
	
	
	protected static SavedRequest getSavedRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        SavedRequest savedRequest = (SavedRequest) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY);
        
		return savedRequest;
 	}
	
	protected static SavedRequest getAndRemoveSavedRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        SavedRequest savedRequest = (SavedRequest) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY);
        session.removeAttribute(AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY);
		return savedRequest;
 	}
	
	
	
   

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setServerSideRedirect(false);
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint#setServerSideRedirect(boolean)
	 */
	@Override
	public void setServerSideRedirect(boolean serverSideRedirect) {
		if(serverSideRedirect){
			throw new UnsupportedOperationException("不能改变这个属性，必须使用 URL 转发模式。");
		}
	}

}
