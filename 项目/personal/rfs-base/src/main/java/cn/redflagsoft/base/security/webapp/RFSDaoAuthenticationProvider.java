/*
 * $Id: RFSDaoAuthenticationProvider.java 6215 2013-05-24 06:19:33Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
 * 自定义的DaoAuthenticationProvider.
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
			//如果没检查，则需要检查
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
	 * 用户名、密码、验证码、登录次数等校验。
	 * 通常调用这个方法时，用户就一定存在，而且已经从数据库中查询出来了。
	 */
	protected void additionalAuthenticationChecks(UserDetails userDetails,	UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		log.debug("additionalAuthenticationChecks ...");
		
		//是否已经完成了验证码的校验工作？
		CaptchaValidateResult validateResult = validateCaptchaIfRequired(userDetails, authentication);
		
		passwordHashedAuthenticationChecks(userDetails, authentication);
		
		if(validateResult != null){
			validateResult.resetDetailsRequireCaptcha();
		}
	}

	/**
	 * 判断是否需要检查验证码。
	 * 
	 * @param userDetails
	 * @param authentication
	 * @return
	 */
	protected boolean isRequireValidateCaptcha(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication){
		Object authDetails = authentication.getDetails();
		if(authDetails instanceof RFSWebAuthenticationDetails){
			//始终使用验证码
			boolean alwaysUseCaptcha = LoginUtils.alwaysUseCaptcha();
			//必要时使用验证码：登录尝试次数大于3
			boolean useCaptchaIfRequired = (LoginUtils.useCaptcha() && userDetails instanceof User && ((User)userDetails).getTryLoginCount() >= 3);
			//如果当前session的登录错误次数大于指定次数，则要检验验证码
			boolean isSessionLoginErrorCountExceed = ((RFSWebAuthenticationDetails)authDetails).isSessionLoginErrorCountExceed(); 
			
			//是否需要检验验证码
			if(alwaysUseCaptcha || useCaptchaIfRequired || isSessionLoginErrorCountExceed){
				if(log.isDebugEnabled()){
					String msg = String.format("需要检查验证码：alwaysUseCaptcha=%s, useCaptchaIfRequired=%s, isSessionLoginErrorCountExceed=%s", 
						alwaysUseCaptcha, useCaptchaIfRequired, isSessionLoginErrorCountExceed);
					log.debug(msg);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 检查验证码。
	 * @param userDetails
	 * @param authentication
	 * @return 返回是否已经检查了验证码的标志。注意，返回值的意义不是值验证是否通过了，而是只是否验证了，如果没有验证
	 * 需要在后面设置后置验证标志。
	 */
	protected CaptchaValidateResult validateCaptcha(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication){
		RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails) authentication.getDetails();
		
		Captcha captcha = details.getBackendCaptcha();
		details.setBackendCaptcha(null);

		//后台没有验证码信息，例如页面展示时间太长，或者后台清理过内存。
		//此时需要在登录后跳转到验证码输入界面，再次输入验证码
		if(captcha == null){
			//跳过了检查
			return new CaptchaValidateResult(details, true);
		}
		
		//后台验证码不为空，则校验验证码
		String captchaResponse = details.getParameterMap().get(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
		details.getParameterMap().remove(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
		
		if(StringUtils.isBlank(captchaResponse)){
			throw new CaptchaInvalidException("请输入验证码。");
		} 
		if(!captcha.validateResponse(captchaResponse)){
			throw new CaptchaInvalidException("验证码输入错误。");
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
	 * 用户名、密码、验证码、登录次数等校验。
	 * 通常调用这个方法时，用户就一定存在，而且已经从数据库中查询出来了。
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
			//如果当前session的登录错误次数大于指定次数，则要检验验证码
			boolean isSessionLoginErrorCountExceed = ((RFSWebAuthenticationDetails)authDetails).isSessionLoginErrorCountExceed(); 
			
			//是否需要检验验证码
			if(alwaysUseCaptcha || useCaptchaIfRequired || isSessionLoginErrorCountExceed){
				RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails)  authDetails;//authentication.getDetails();
				log.debug(alwaysUseCaptcha? "始终进行验证码校验..." : "用户尝试登陆次数大于3，进行验证码校验...");
				
				Captcha captcha = details.getBackendCaptcha();
				details.setBackendCaptcha(null);

//				//始终验证时必须包含验证码
//				if((alwaysUseCaptcha || isSessionLoginErrorCountExceed) && captcha == null){
//					throw new CaptchaInvalidException("请输入验证码。");
//				}
				
				//如果后台没有生成验证码，且登录次数过多，可能是由于这是用户的第一次登录，
				//所有先检查用户名密码，如果通过则事后检验验证码，否则返回登录页面
				if(captcha == null){
					try {
						passwordHashedAuthenticationChecks(userDetails, authentication);
						details.setRequireCaptcha(true);
						return;
					} catch (BadCredentialsException e) {
						log.debug(e.getMessage(), e);
						throw new CaptchaInvalidException("您已经尝试使用错误的用户名或密码进行了多次的登录。");
					}
				}
				
//				//如果后台没有验证码信息则也报错
//				if(captcha == null){
//					log.debug("后台验证码对象为空，可能session已经过期。");
//					throw new CaptchaInvalidException("请输入验证码。");
//				}
				
				
				//后台验证码不为空，则校验验证码
				String captchaResponse = details.getParameterMap().get(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
				details.getParameterMap().remove(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
				
				if(StringUtils.isBlank(captchaResponse)){
					throw new CaptchaInvalidException("请输入验证码。");
				} 
				if(!captcha.validateResponse(captchaResponse)){
					throw new CaptchaInvalidException("验证码输入错误。");
				}
				
				//验证码通过，则校验密码
				try {
					passwordHashedAuthenticationChecks(userDetails, authentication);
					details.setRequireCaptcha(false);//不再需要事后验证
					return;
				} catch (BadCredentialsException e) {
					log.debug(e.getMessage(), e);
					throw new CaptchaInvalidException("用户名或密码错误。");
				}
			}
		}
		
		
		
//		//当用户尝试登录次数大于3次时才检查验证码，否则不检查
//		if(LoginUtils.useCaptcha() 
//				&& userDetails instanceof User 
//				&& ((User)userDetails).getTryLoginCount() > 3 /*AppsGlobals.getProperty("login.maxTryCount", 3)*/
//				&& authentication.getDetails() instanceof RFSWebAuthenticationDetails){
//			RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails)  authentication.getDetails();
//			
//			log.debug("用户尝试登陆次数大于3，进行验证码校验...");
//			
//			Captcha captcha = details.getBackendCaptcha();
//			details.setBackendCaptcha(null);
//			
//			//如果后台没有生成验证码，且登录次数过多，可能是由于这是用户的第一次登录，
//			//所有先检查用户名密码，如果通过则事后检验验证码，否则返回登录页面
//			if(captcha == null){
//				try {
//					passwordHashedAuthenticationChecks(userDetails, authentication);
//					details.setRequireCaptcha(true);
//					return;
//				} catch (BadCredentialsException e) {
//					log.debug(e.getMessage(), e);
//					throw new CaptchaInvalidException("您已经尝试使用错误的用户名或密码进行了多次的登录。");
//				}
//			}
//			
//			//后台验证码不为空，则校验验证码
//			String captchaResponse = details.getParameterMap().get(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
//			details.getParameterMap().remove(RFSWebAuthenticationDetails.LOGIN_FORM_CAPTCHA_KEY);
//			
//			if(StringUtils.isBlank(captchaResponse)){
//				throw new CaptchaInvalidException("请输入验证码。");
//			} 
//			if(!captcha.validateResponse(captchaResponse)){
//				throw new CaptchaInvalidException("验证码输入错误。");
//			}
//			
//			//验证码通过，则校验密码
//			try {
//				passwordHashedAuthenticationChecks(userDetails, authentication);
//				details.setRequireCaptcha(false);//不再需要事后验证
//				return;
//			} catch (BadCredentialsException e) {
//				log.debug(e.getMessage(), e);
//				throw new CaptchaInvalidException("用户名或密码错误。");
//			}
//		}
		
//		if(LoginUtils.usePasswordHash()){
			passwordHashedAuthenticationChecks(userDetails, authentication);
//		}else{
//			super.additionalAuthenticationChecks(userDetails, authentication);
//		}
	}

	
	/**
	 * 检查用户名和密码
	 * @param userDetails
	 * @param authentication
	 * @throws AuthenticationException
	 */
	protected void passwordHashedAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if(authentication.getDetails() instanceof RFSWebAuthenticationDetails){
			RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails) authentication.getDetails();
			//如果前台已经加密或者后台配置要求必须加密
			if(details.isPasswordHashed() || LoginUtils.usePasswordHash()){
				if(log.isDebugEnabled()){
					String msg = String.format("密码前台已加密或者必须使用密码，所以要使用加密密码校验机制。(isPasswordHashed=%s, usePasswordHash=%s)", 
							details.isPasswordHashed(), LoginUtils.usePasswordHash());
					log.debug(msg);
				}
				
				String presentedPassword = authentication.getCredentials().toString();
				String passwordFromDB = LoginUtils.encodePassword(userDetails.getPassword(), details.getBackendChallenge());
				//remove backend challenge
				details.setBackendChallenge(null);
				if(passwordFromDB.equals(presentedPassword)){
					log.debug("加密密码校验成功：" + presentedPassword);
					//将密码保存在authentication时，应该取非前台加密过的
					resetCredentials(authentication, userDetails.getPassword());
					return;
				}else{
					throw new RFSBadCredentialsException("用户名或密码错误。");
				}
			}
		}
		
		//其它调用原始的校验
		if(log.isDebugEnabled()){
			log.debug("调用 super.additionalAuthenticationChecks()认证用户和密码 ...");
		}
		super.additionalAuthenticationChecks(userDetails, authentication);
	}
	
	private static void resetCredentials(UsernamePasswordAuthenticationToken authentication, Object credentials){
		try {
			Field field = UsernamePasswordAuthenticationToken.class.getDeclaredField("credentials");
			field.setAccessible(true);
			field.set(authentication, credentials);
		} catch (Exception e) {
			log.error("重设 UsernamePasswordAuthenticationToken.credentials 出错", e);
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
