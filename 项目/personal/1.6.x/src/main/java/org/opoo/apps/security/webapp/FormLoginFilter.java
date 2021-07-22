package org.opoo.apps.security.webapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

public class FormLoginFilter extends AuthenticationProcessingFilter {
	private static final Log log = LogFactory.getLog(FormLoginFilter.class);
	
	private boolean alwaysUseDefaultTargetUrl = false;
	private boolean tokenRequired = false;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
		validateFormLogin(request);
		return super.attemptAuthentication(request);
	}
	
	/**
	 * 检查验证码，表单 TOKEN 等。
	 *
	 * @param request
	 * @throws AuthenticationException
	 */
	protected void validateFormLogin(HttpServletRequest request) throws AuthenticationException{
		if(tokenRequired && !WebUtils.validateToken(request)){
			throw new TokenAuthenticationException("表单验证错误。");
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setServerSideRedirect(false);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#setServerSideRedirect(boolean)
	 */
	@Override
	public void setServerSideRedirect(boolean serverSideRedirect) {
		if(serverSideRedirect){
			throw new UnsupportedOperationException("不能改变这个属性，必须使用 URL 转发模式。");
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#determineFailureUrl(javax.servlet.http.HttpServletRequest, org.springframework.security.AuthenticationException)
	 */
	@Override
	protected String determineFailureUrl(HttpServletRequest request, AuthenticationException failed) {
		String url = super.determineFailureUrl(request, failed);
		//如果使用默认，则不必处理ru
		if(!alwaysUseDefaultTargetUrl && url != null){
			String ru = getTargetUrlResolver().determineTargetUrl(null, request, 
					SecurityContextHolder.getContext().getAuthentication());
			//String ru = getTargetUrl(request);
			log.warn("处理失败，RU: " + ru);
			if(StringUtils.hasText(ru)){
				try {
					ru = URLEncoder.encode(ru, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new IllegalStateException("UTF-8 not supported. Shouldn't be possible");
				}
				
				url += url.indexOf('?') == -1 ? "?" : "&";
				url += WebUtils.TARGET_URL_PARAM + "=" + ru;
			}
		}
		log.warn("处理失败，转向URL: " + url);
		return url;
	}

//	/* (non-Javadoc)
//	 * @see org.springframework.security.ui.AbstractProcessingFilter#determineTargetUrl(javax.servlet.http.HttpServletRequest)
//	 */
//	@Override
//	protected String determineTargetUrl(HttpServletRequest request) {
//        // Don't attempt to obtain the url from the saved request if alwaysUsedefaultTargetUrl is set
//    	String targetUrl = alwaysUseDefaultTargetUrl ? null : getTargetUrl(request);
//    		//determineTargetUrl(getSavedRequest(request), request, SecurityContextHolder.getContext().getAuthentication());
//
//        if (targetUrl == null) {
//            targetUrl = getDefaultTargetUrl();
//        }else{
//        	
//			try {
//				targetUrl = URLDecoder.decode(targetUrl, "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				throw new IllegalStateException("UTF-8 not supported. Shouldn't be possible");
//			}
//        	log.debug("Redirect to " + targetUrl);
//        }
//
//        return targetUrl;
//	}
	
	
//	private String getTargetUrl(HttpServletRequest request){
//		//TODO
//		return getTargetUrlResolver().determineTargetUrl(null, request, SecurityContextHolder.getContext().getAuthentication());
//		//return WebUtils.getTargetUrl(request);
//	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#setAlwaysUseDefaultTargetUrl(boolean)
	 */
	@Override
	public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
		super.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
		this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
	}

	/**
	 * @return the tokenRequired
	 */
	public boolean isTokenRequired() {
		return tokenRequired;
	}

	/**
	 * @param tokenRequired the tokenRequired to set
	 */
	public void setTokenRequired(boolean tokenRequired) {
		this.tokenRequired = tokenRequired;
	}
}
