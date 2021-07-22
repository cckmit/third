/*
 * $Id: RFSDaoAuthenticationProvider.java 6215 2013-05-24 06:19:33Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security.webapp;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.security.User;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;
import org.springframework.security.userdetails.UserDetails;

import com.octo.captcha.Captcha;

/**
 * �Զ����DaoAuthenticationProvider.
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class RFSDaoAuthenticationProvider extends DaoAuthenticationProvider {
	private static final Log log = LogFactory.getLog(RFSDaoAuthenticationProvider.class);
	
	private static class CaptchaValidateResult{
		boolean skip = false;
		RFSWebAuthenticationDetails details;
		CaptchaValidateResult(RFSWebAuthenticationDetails details){
			this.details = details;
		}
		CaptchaValidateResult(RFSWebAuthenticationDetails details, boolean skipValidating){
			this.details = details;
			this.skip = skipValidating;
		}
		
		void resetDetailsRequireCaptcha(){
			//���û��飬����Ҫ���
			this.details.setRequireCaptcha(skip);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.providers.dao.AbstractUserDetailsAuthenticationProvider#authenticate(org.springframework.security.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		if(log.isDebugEnabled()){
			log.debug("authenticate ...");
		}
		return super.authenticate(authentication);
	}
	
	/**
	 * �û��������롢��֤�롢��¼������У�顣
	 * ͨ�������������ʱ���û���һ�����ڣ������Ѿ������ݿ��в�ѯ�����ˡ�
	 */
	protected void additionalAuthenticationChecks(UserDetails userDetails,	UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		log.debug("additionalAuthenticationChecks ...");
		
		//�Ƿ��Ѿ��������֤���У�鹤����
		CaptchaValidateResult validateResult = validateCaptchaIfRequired(userDetails, authentication);
		
		passwordHashedAuthenticationChecks(userDetails, authentication);
		
		if(validateResult != null){
			validateResult.resetDetailsRequireCaptcha();
		}
	}

	/**
	 * �ж��Ƿ���Ҫ�����֤�롣
	 * 
	 * @param userDetails
	 * @param authentication
	 * @return
	 */
	protected boolean isRequireValidateCaptcha(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication){
		Object authDetails = authentication.getDetails();
		if(authDetails instanceof RFSWebAuthenticationDetails){
			//ʼ��ʹ����֤��
			boolean alwaysUseCaptcha = LoginUtils.alwaysUseCaptcha();
			//��Ҫʱʹ����֤�룺��¼���Դ�������3
			boolean useCaptchaIfRequired = (LoginUtils.useCaptcha() && userDetails instanceof User && ((User)userDetails).getTryLoginCount() >= 3);
			//�����ǰsession�ĵ�¼�����������ָ����������Ҫ������֤��
			boolean isSessionLoginErrorCountExceed = ((RFSWebAuthenticationDetails)authDetails).isSessionLoginErrorCountExceed(); 
			
			//�Ƿ���Ҫ������֤��
			if(alwaysUseCaptcha || useCaptchaIfRequired || isSessionLoginErrorCountExceed){
				if(log.isDebugEnabled()){
					String msg = String.format("��Ҫ�����֤�룺alwaysUseCaptcha=%s, useCaptchaIfRequired=%s, isSessionLoginErrorCountExceed=%s", 
						alwaysUseCaptcha, useCaptchaIfRequired, isSessionLoginErrorCountExceed);
					log.debug(msg);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * �����֤�롣
	 * @param userDetails
	 * @param authentication
	 * @return �����Ƿ��Ѿ��������֤��ı�־��ע�⣬����ֵ�����岻��ֵ��֤�Ƿ�ͨ���ˣ�����ֻ�Ƿ���֤�ˣ����û����֤
	 * ��Ҫ�ں������ú�����֤��־��
	 */
	protected CaptchaValidateResult validateCaptcha(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication){
		RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails) authentication.getDetails();
		
		Captcha captcha = details.getBackendCaptcha();
		details.setBackendCaptcha(null);

		//��̨û����֤����Ϣ������ҳ��չʾʱ��̫�������ߺ�̨������ڴ档
		//��ʱ��Ҫ�ڵ�¼����ת����֤��������棬�ٴ�������֤��
		if(captcha == null){
			//�����˼��
			return new CaptchaValidateResult(details, true);
		}
		
		//��̨��֤�벻Ϊ�գ���У����֤��
		String captchaResponse = details.getParameterMap().get(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
		details.getParameterMap().remove(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
		
		if(StringUtils.isBlank(captchaResponse)){
			throw new CaptchaInvalidException("��������֤�롣");
		} 
		if(!captcha.validateResponse(captchaResponse)){
			throw new CaptchaInvalidException("��֤���������");
		}
		
		return new CaptchaValidateResult(details);
	}
	
	/**
	 * 
	 * @param userDetails
	 * @param authentication
	 * @return
	 */
	protected CaptchaValidateResult validateCaptchaIfRequired(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication){
		if(isRequireValidateCaptcha(userDetails, authentication)){
			return validateCaptcha(userDetails, authentication);
		}
		return null;
	}
	

	/**
	 * �û��������롢��֤�롢��¼������У�顣
	 * ͨ�������������ʱ���û���һ�����ڣ������Ѿ������ݿ��в�ѯ�����ˡ�
	 * @deprecated
	 */
	protected void additionalAuthenticationChecks_BAK(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if(log.isDebugEnabled()){
			log.debug("additionalAuthenticationChecks ...");
		}
		Object authDetails = authentication.getDetails();
		
		if(authDetails instanceof RFSWebAuthenticationDetails){
			boolean alwaysUseCaptcha = LoginUtils.alwaysUseCaptcha();
			boolean useCaptchaIfRequired = (LoginUtils.useCaptcha() && userDetails instanceof User && ((User)userDetails).getTryLoginCount() >= 3);
			//�����ǰsession�ĵ�¼�����������ָ����������Ҫ������֤��
			boolean isSessionLoginErrorCountExceed = ((RFSWebAuthenticationDetails)authDetails).isSessionLoginErrorCountExceed(); 
			
			//�Ƿ���Ҫ������֤��
			if(alwaysUseCaptcha || useCaptchaIfRequired || isSessionLoginErrorCountExceed){
				RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails)  authDetails;//authentication.getDetails();
				log.debug(alwaysUseCaptcha? "ʼ�ս�����֤��У��..." : "�û����Ե�½��������3��������֤��У��...");
				
				Captcha captcha = details.getBackendCaptcha();
				details.setBackendCaptcha(null);

//				//ʼ����֤ʱ���������֤��
//				if((alwaysUseCaptcha || isSessionLoginErrorCountExceed) && captcha == null){
//					throw new CaptchaInvalidException("��������֤�롣");
//				}
				
				//�����̨û��������֤�룬�ҵ�¼�������࣬���������������û��ĵ�һ�ε�¼��
				//�����ȼ���û������룬���ͨ�����º������֤�룬���򷵻ص�¼ҳ��
				if(captcha == null){
					try {
						passwordHashedAuthenticationChecks(userDetails, authentication);
						details.setRequireCaptcha(true);
						return;
					} catch (BadCredentialsException e) {
						log.debug(e.getMessage(), e);
						throw new CaptchaInvalidException("���Ѿ�����ʹ�ô�����û�������������˶�εĵ�¼��");
					}
				}
				
//				//�����̨û����֤����Ϣ��Ҳ����
//				if(captcha == null){
//					log.debug("��̨��֤�����Ϊ�գ�����session�Ѿ����ڡ�");
//					throw new CaptchaInvalidException("��������֤�롣");
//				}
				
				
				//��̨��֤�벻Ϊ�գ���У����֤��
				String captchaResponse = details.getParameterMap().get(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
				details.getParameterMap().remove(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
				
				if(StringUtils.isBlank(captchaResponse)){
					throw new CaptchaInvalidException("��������֤�롣");
				} 
				if(!captcha.validateResponse(captchaResponse)){
					throw new CaptchaInvalidException("��֤���������");
				}
				
				//��֤��ͨ������У������
				try {
					passwordHashedAuthenticationChecks(userDetails, authentication);
					details.setRequireCaptcha(false);//������Ҫ�º���֤
					return;
				} catch (BadCredentialsException e) {
					log.debug(e.getMessage(), e);
					throw new CaptchaInvalidException("�û������������");
				}
			}
		}
		
		
		
//		//���û����Ե�¼��������3��ʱ�ż����֤�룬���򲻼��
//		if(LoginUtils.useCaptcha() 
//				&& userDetails instanceof User 
//				&& ((User)userDetails).getTryLoginCount() > 3 /*AppsGlobals.getProperty("login.maxTryCount", 3)*/
//				&& authentication.getDetails() instanceof RFSWebAuthenticationDetails){
//			RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails)  authentication.getDetails();
//			
//			log.debug("�û����Ե�½��������3��������֤��У��...");
//			
//			Captcha captcha = details.getBackendCaptcha();
//			details.setBackendCaptcha(null);
//			
//			//�����̨û��������֤�룬�ҵ�¼�������࣬���������������û��ĵ�һ�ε�¼��
//			//�����ȼ���û������룬���ͨ�����º������֤�룬���򷵻ص�¼ҳ��
//			if(captcha == null){
//				try {
//					passwordHashedAuthenticationChecks(userDetails, authentication);
//					details.setRequireCaptcha(true);
//					return;
//				} catch (BadCredentialsException e) {
//					log.debug(e.getMessage(), e);
//					throw new CaptchaInvalidException("���Ѿ�����ʹ�ô�����û�������������˶�εĵ�¼��");
//				}
//			}
//			
//			//��̨��֤�벻Ϊ�գ���У����֤��
//			String captchaResponse = details.getParameterMap().get(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
//			details.getParameterMap().remove(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
//			
//			if(StringUtils.isBlank(captchaResponse)){
//				throw new CaptchaInvalidException("��������֤�롣");
//			} 
//			if(!captcha.validateResponse(captchaResponse)){
//				throw new CaptchaInvalidException("��֤���������");
//			}
//			
//			//��֤��ͨ������У������
//			try {
//				passwordHashedAuthenticationChecks(userDetails, authentication);
//				details.setRequireCaptcha(false);//������Ҫ�º���֤
//				return;
//			} catch (BadCredentialsException e) {
//				log.debug(e.getMessage(), e);
//				throw new CaptchaInvalidException("�û������������");
//			}
//		}
		
//		if(LoginUtils.usePasswordHash()){
			passwordHashedAuthenticationChecks(userDetails, authentication);
//		}else{
//			super.additionalAuthenticationChecks(userDetails, authentication);
//		}
	}

	
	/**
	 * ����û���������
	 * @param userDetails
	 * @param authentication
	 * @throws AuthenticationException
	 */
	protected void passwordHashedAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if(authentication.getDetails() instanceof RFSWebAuthenticationDetails){
			RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails) authentication.getDetails();
			//���ǰ̨�Ѿ����ܻ��ߺ�̨����Ҫ��������
			if(details.isPasswordHashed() || LoginUtils.usePasswordHash()){
				if(log.isDebugEnabled()){
					String msg = String.format("����ǰ̨�Ѽ��ܻ��߱���ʹ�����룬����Ҫʹ�ü�������У����ơ�(isPasswordHashed=%s, usePasswordHash=%s)", 
							details.isPasswordHashed(), LoginUtils.usePasswordHash());
					log.debug(msg);
				}
				
				String presentedPassword = authentication.getCredentials().toString();
				String passwordFromDB = LoginUtils.encodePassword(userDetails.getPassword(), details.getBackendChallenge());
				//remove backend challenge
				details.setBackendChallenge(null);
				if(passwordFromDB.equals(presentedPassword)){
					log.debug("��������У��ɹ���" + presentedPassword);
					//�����뱣����authenticationʱ��Ӧ��ȡ��ǰ̨���ܹ���
					resetCredentials(authentication, userDetails.getPassword());
					return;
				}else{
					throw new RFSBadCredentialsException("�û������������");
				}
			}
		}
		
		//��������ԭʼ��У��
		if(log.isDebugEnabled()){
			log.debug("���� super.additionalAuthenticationChecks()��֤�û������� ...");
		}
		super.additionalAuthenticationChecks(userDetails, authentication);
	}
	
	private static void resetCredentials(UsernamePasswordAuthenticationToken authentication, Object credentials){
		try {
			Field field = UsernamePasswordAuthenticationToken.class.getDeclaredField("credentials");
			field.setAccessible(true);
			field.set(authentication, credentials);
		} catch (Exception e) {
			log.error("���� UsernamePasswordAuthenticationToken.credentials ����", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.providers.dao.AbstractUserDetailsAuthenticationProvider#createSuccessAuthentication(java.lang.Object, org.springframework.security.Authentication, org.springframework.security.userdetails.UserDetails)
	 */
	@Override
	protected Authentication createSuccessAuthentication(Object principal,
			Authentication authentication, UserDetails user) {
		log.debug("createSuccessAuthentication");
		return super.createSuccessAuthentication(principal, authentication, user);
	}

	public static void main(String[] args) throws Exception{
		Field field = UsernamePasswordAuthenticationToken.class.getDeclaredField("credentials");
		System.out.println(field);
		field.setAccessible(true);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("r", "r");
		System.out.println(token.getCredentials());
		field.set(token, "s");
		System.out.println(token.getCredentials());
		resetCredentials(token, "sssssssssssssssssssss");
		System.out.println(token.getCredentials());
	}
}
