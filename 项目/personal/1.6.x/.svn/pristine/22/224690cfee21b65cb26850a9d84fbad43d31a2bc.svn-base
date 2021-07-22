package org.opoo.apps.security.webapp;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


public class FormLogin implements Serializable{
	public static final String SESSION_KEY = "_APPS_CURRENT_FORM_LOGIN";
	
	private static final long serialVersionUID = 5635781215790694978L;

	//private String pattern;
	private String loginProcessingUrl;
//	private String defaultTargetUrl;
	//private boolean alwaysUseDefaultTarget;
	private String loginPage;
	private String authenticationFailureUrl;
	private String targetUrl;
	//private boolean forGetOnly;
	
	
//	/**
//	 * @return the pattern
//	 */
//	public String getPattern() {
//		return pattern;
//	}
//	/**
//	 * @param pattern the pattern to set
//	 */
//	public void setPattern(String pattern) {
//		this.pattern = pattern;
//	}
	
	/**
	 * @return the loginProcessingUrl
	 */
	public String getLoginProcessingUrl() {
		return loginProcessingUrl;
	}
	/**
	 * @param loginProcessingUrl the loginProcessingUrl to set
	 */
	public void setLoginProcessingUrl(String loginProcessingUrl) {
		this.loginProcessingUrl = loginProcessingUrl;
	}
//	/**
//	 * @return the defaultTargetUrl
//	 */
//	public String getDefaultTargetUrl() {
//		return defaultTargetUrl;
//	}
//	/**
//	 * @param defaultTargetUrl the defaultTargetUrl to set
//	 */
//	public void setDefaultTargetUrl(String defaultTargetUrl) {
//		this.defaultTargetUrl = defaultTargetUrl;
//	}
//	/**
//	 * @return the alwaysUseDefaultTarget
//	 */
//	public boolean isAlwaysUseDefaultTarget() {
//		return alwaysUseDefaultTarget;
//	}
//	/**
//	 * @param alwaysUseDefaultTarget the alwaysUseDefaultTarget to set
//	 */
//	public void setAlwaysUseDefaultTarget(boolean alwaysUseDefaultTarget) {
//		this.alwaysUseDefaultTarget = alwaysUseDefaultTarget;
//	}
	/**
	 * @return the loginPage
	 */
	public String getLoginPage() {
		return loginPage;
	}
	/**
	 * @param loginPage the loginPage to set
	 */
	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	/**
	 * @return the authenticationFailureUrl
	 */
	public String getAuthenticationFailureUrl() {
		return authenticationFailureUrl;
	}
	/**
	 * @param authenticationFailureUrl the authenticationFailureUrl to set
	 */
	public void setAuthenticationFailureUrl(String authenticationFailureUrl) {
		this.authenticationFailureUrl = authenticationFailureUrl;
	}
	
	/**
	 * @return the targetUrl
	 */
	public String getTargetUrl() {
		return targetUrl;
	}
	/**
	 * @param targetUrl the targetUrl to set
	 */
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	
	public String toString(){
		return new ToStringBuilder(this).append("loginPage", loginPage)
			.append("loginProcessingUrl", loginProcessingUrl)
			.append("authenticationFailureUrl", authenticationFailureUrl)
			.append("targetUrl", targetUrl).toString();
	}
}
