package org.opoo.apps.security.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.util.StringUtils;


/**
 * 对符合指定后缀的 URL 都验证。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AppsAuthenticationProcessingFilter extends AuthenticationProcessingFilter {

	/**
	 * 解决是否需要验证的问题。
	 * @see org.springframework.security.ui.AbstractProcessingFilter#requiresAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything after the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }
        
        String contextPath = request.getContextPath();
        
        FormLogin formLogin = WebUtils.getFormLogin(request);
        System.out.println("===================>FormLogin in session: " + formLogin);
		if(formLogin != null && formLogin.getLoginProcessingUrl() != null){
			String url = formLogin.getLoginProcessingUrl();
			if(StringUtils.hasText(url) && uri.endsWith(contextPath + url)){
				return true;
    		}
		}
        
//        for(String filterProcessesUrl: filterProcessesUrls){
//        	if(uri.endsWith(path + filterProcessesUrl)){
//        		return true;
//        	}
//        }
//        return false;

        
        String filterProcessesUrl = getFilterProcessesUrl();
                
        if ("".equals(contextPath)) {
            return uri.endsWith(filterProcessesUrl);
        }

        return uri.endsWith(contextPath + filterProcessesUrl);
	}
	
	
    /* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setTargetUrlResolver(new AppsTargetUrlResolverImpl());
	}


	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#determineFailureUrl(javax.servlet.http.HttpServletRequest, org.springframework.security.AuthenticationException)
	 */
	@Override
	protected String determineFailureUrl(HttpServletRequest request, AuthenticationException failed) {
		FormLogin formLogin = WebUtils.getFormLogin(request);
		if(formLogin != null && formLogin.getAuthenticationFailureUrl() != null){
			System.out.println("=============>formLogin.getAuthenticationFailureUrl: " + formLogin.getAuthenticationFailureUrl());
			return formLogin.getAuthenticationFailureUrl();
		}
		
		return super.determineFailureUrl(request, failed);
	}


	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#determineTargetUrl(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String determineTargetUrl(HttpServletRequest request) {
		return super.determineTargetUrl(request);
	}


	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilter#getOrder()
	 */
	@Override
	public int getOrder() {
		return super.getOrder() - 20;
	}





//	/**
//	 * @param filterProcessesUrls the filterProcessesUrls to set
//	 */
//	public void setFilterProcessesUrls(List<String> filterProcessesUrls) {
//		List<String> list = new ArrayList<String>();
//		String filterProcessesUrl = super.getFilterProcessesUrl();
//		if(filterProcessesUrl != null){
//			list.add(filterProcessesUrl);
//		}
//		if(filterProcessesUrls == null){
//			list.addAll(filterProcessesUrls);
//		}
//		this.filterProcessesUrls = list.isEmpty() ?  null : filterProcessesUrls;
//	}
//	
//	/**
//	 * @return the filterProcessesUrlSuffix
//	 */
//	public String getFilterProcessesUrlSuffix() {
//		return filterProcessesUrlSuffix;
//	}
//
//
//	/**
//	 * @param filterProcessesUrlSuffix the filterProcessesUrlSuffix to set
//	 */
//	public void setFilterProcessesUrlSuffix(String filterProcessesUrlSuffix) {
//		this.filterProcessesUrlSuffix = filterProcessesUrlSuffix;
//	}
	
	
	
}
