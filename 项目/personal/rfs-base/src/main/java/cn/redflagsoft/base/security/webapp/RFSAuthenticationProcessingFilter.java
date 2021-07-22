/*
 * $Id: RFSAuthenticationProcessingFilter.java 6229 2013-06-06 09:00:38Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.security.webapp.TokenAuthenticationException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

/**
 * ������У���token��
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class RFSAuthenticationProcessingFilter extends AuthenticationProcessingFilter {
	private static final Log log = LogFactory.getLog(RFSAuthenticationProcessingFilter.class);
	
	//���Token
	protected void onPreAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException, IOException {
		if(log.isDebugEnabled()){
			log.debug("onPreAuthentication ...");
		}
		super.onPreAuthentication(request, response);
		
		//Ҫ��ʹ��token������֤token
		if(LoginUtils.useToken()){
			boolean isValidToken = LoginUtils.validateToken(request);
			if(!isValidToken){
				throw new TokenAuthenticationException("�����ݴ���");
			}
		}
		
		//����Ҫ�����������ܣ���ǰ̨��֧��JS������Ϊα���
		if(LoginUtils.usePasswordHash() && !LoginUtils.isPasswordHashed(request)){
			log.warn("�������ã����������ǰ̨���ܴ�����js����Ϊ0�������������֧��JavaScipt����Ϊα�����");
			throw new TokenAuthenticationException("���������֧�� JavaScript �ű���");
		}
	}
	

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request)	throws AuthenticationException {
		if(log.isDebugEnabled()){
			log.debug("attemptAuthentication ...");
		}
		return super.attemptAuthentication(request);
	}



	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		AuthenticationDetailsSource source = super.getAuthenticationDetailsSource();
		if(source instanceof WebAuthenticationDetailsSource){
			WebAuthenticationDetailsSource wad = (WebAuthenticationDetailsSource) source;
			wad.setClazz(RFSWebAuthenticationDetails.class);
			log.debug("����WebAuthenticationDetailsSource.clazz: " + RFSWebAuthenticationDetails.class.getName());
		}
	}


	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#onSuccessfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.Authentication)
	 */
	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException {
		super.onSuccessfulAuthentication(request, response, authResult);
//		User user = UserHolder.getNullableUser();
//		System.out.println("---------------------------------" + user);
//		if(user != null){
//			System.out.println("���Ե�½������" + user.getTryLoginCount());
//		}
		
		Object details = authResult.getDetails();
		if(details instanceof RFSWebAuthenticationDetails){
			RFSWebAuthenticationDetails de = (RFSWebAuthenticationDetails) details;
			if(de.isRequireCaptcha()){
				log.debug("�û�������Ҫ��֤����֤...");
				LoginUtils.setRequireCaptcha(request.getSession(), true);
				de.setRequireCaptcha(false);
			}
		}
	}


	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#successfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.Authentication)
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException, ServletException {
		try {
			log.debug("successfulAuthentication, removeSessionLoginErrorCount...");
			LoginUtils.removeSessionLoginErrorCount(request);
			super.successfulAuthentication(request, response, authResult);
		} finally{
			log.debug("successfulAuthentication complete");
		}
	}


	/* (non-Javadoc)
	 * @see org.springframework.security.ui.AbstractProcessingFilter#unsuccessfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.AuthenticationException)
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		try {
			log.debug("unsuccessfulAuthentication, plusSessionLoginErrorCount...");
			LoginUtils.plusSessionLoginErrorCount(request);
			super.unsuccessfulAuthentication(request, response, failed);
		}finally{
			log.debug("unsuccessfulAuthentication complete");
		}
	}


	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilter#obtainPassword(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		//ǰ̨�����ˣ�����Ҫ��ǰ̨���ܣ���ȡ���ܵ�����
		if(LoginUtils.isPasswordHashed(request) || LoginUtils.usePasswordHash()){
		//if(StringUtils.isNotBlank(password) && isAllStar(password)){
        	//password = request.getParameter("hashedPassword");
			return  LoginUtils.getHashedPassword(request);
        }else{
        	return super.obtainPassword(request);
        }
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilter#obtainUsername(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String username = super.obtainUsername(request);
		if(LoginUtils.useUsernameEncrypt()){
			username = LoginUtils.getDecryptText(request.getSession(false), username);
			log.debug("���ܺ���û����ǣ�" + username);
		}
		return username;
	}
	
	/*
	private static boolean isAllStar(String string){
		for(int i = 0 ; i < string.length() ; i++){
			if(string.charAt(i) != '*'){
				return false;
			}
		}
		return true;
	}*/
	
	
}
