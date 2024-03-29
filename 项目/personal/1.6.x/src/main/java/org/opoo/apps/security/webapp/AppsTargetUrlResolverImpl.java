package org.opoo.apps.security.webapp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.Authentication;
import org.springframework.security.ui.TargetUrlResolver;
import org.springframework.security.ui.savedrequest.SavedRequest;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * 
 */
public class AppsTargetUrlResolverImpl implements TargetUrlResolver {
	public static String DEFAULT_TARGET_PARAMETER = "spring-security-redirect";

	/* SEC-213 */
	private String targetUrlParameter = DEFAULT_TARGET_PARAMETER;

	/**
	 * If <code>true</code>, will only use <code>SavedRequest</code> to
	 * determine the target URL on successful authentication if the request that
	 * caused the authentication request was a GET. It will then return null for
	 * a POST/PUT request. Defaults to false.
	 */
	private boolean justUseSavedRequestOnGet = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.acegisecurity.ui.TargetUrlResolver#determineTargetUrl(org.acegisecurity
	 * .ui.savedrequest.SavedRequest, javax.servlet.http.HttpServletRequest,
	 * org.acegisecurity.Authentication)
	 */
	public String determineTargetUrl(SavedRequest savedRequest, HttpServletRequest currentRequest, Authentication auth) {

		String targetUrl = currentRequest.getParameter(targetUrlParameter);

		if (StringUtils.hasText(targetUrl)) {
			try {
				return URLDecoder.decode(targetUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("UTF-8 not supported. Shouldn't be possible");
			}
		}

		if (savedRequest != null) {
			if (!justUseSavedRequestOnGet || savedRequest.getMethod().equals("GET")) {
				FormLogin formLogin = WebUtils.getAndRemoveFormLogin(currentRequest);
				if(formLogin != null && formLogin.getTargetUrl() != null){
					return formLogin.getTargetUrl();
				}
				
				targetUrl = savedRequest.getFullRequestUrl();
			}
		}

		return targetUrl;
	}

	

	/**
	 * @return <code>true</code> if just GET request will be used to determine
	 *         target URLs, <code>false</code> otherwise.
	 */
	protected boolean isJustUseSavedRequestOnGet() {
		return justUseSavedRequestOnGet;
	}

	/**
	 * @param justUseSavedRequestOnGet
	 *            set to <code>true</code> if just GET request will be used to
	 *            determine target URLs, <code>false</code> otherwise.
	 */
	public void setJustUseSavedRequestOnGet(boolean justUseSavedRequestOnGet) {
		this.justUseSavedRequestOnGet = justUseSavedRequestOnGet;
	}

	/**
	 * Before checking the SavedRequest, the current request will be checked for
	 * this parameter and the value used as the target URL if resent.
	 * 
	 * @param targetUrlParameter
	 *            the name of the parameter containing the encoded target URL.
	 *            Defaults to "redirect".
	 */
	public void setTargetUrlParameter(String targetUrlParameter) {
		Assert.hasText("targetUrlParamete canot be null or empty");
		this.targetUrlParameter = targetUrlParameter;
	}
}
