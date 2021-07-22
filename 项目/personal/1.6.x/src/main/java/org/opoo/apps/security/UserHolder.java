package org.opoo.apps.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetailsService;


/**
 * 
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class UserHolder {
	private static final Log log = LogFactory.getLog(UserHolder.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	private static AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	
	private static UserDetailsService userDetailsService;// = Application.getContext().getUserManager();
	
//	static{
//		if(userDetailsService == null){
//			throw new IllegalArgumentException("��ȫ���������޷��ҵ��ʵ���UserManager���޷�������Ӧ���û���Ϣ��");
//		}
//	}
	
	
	private static UserDetailsService getUserDetailsService() throws RuntimeException{
		if(userDetailsService == null){
			userDetailsService = Application.getContext().getUserManager();
		}
		return userDetailsService;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static final User getUser(){
		User user = getNullableUser();
		if(user == null){
			throw new AccessDeniedException("�Ҳ�����Ч��¼�û���");
		}
		return user;
	}
	
	/**
	 * 
	 * @return
	 */
	public static final User getNullableUser(){
		try{
			return getNullableUser2();
		}catch(Exception e){
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	private static final User getNullableUser2(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			if(authenticationTrustResolver.isAnonymous(auth)){
				log.debug("�������ʣ�user����Ϊnull");
				return null;
			}
			
			Object principal = auth.getPrincipal();
			if(principal instanceof User){
				if(IS_DEBUG_ENABLED){
					log.debug("����֤�з����û�:" + ((User)principal).getUsername());
				}
				return (User) principal;
			}else if(principal instanceof String){
				if(IS_DEBUG_ENABLED){
					log.debug("Load user from service: " + principal);
				}
				return (User) getUserDetailsService().loadUserByUsername((String) principal);
			}
		}
		return null;
	}
	
	
	public static void updateUserLoginInfo(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			if(authenticationTrustResolver.isAnonymous(auth)){
				log.debug("�������ʣ�����updateUserLoginInfo");
				return;
			}
			
//			Object principal = auth.getPrincipal();
//			if(principal instanceof MultableUser){
//				if(IS_DEBUG_ENABLED){
//					log.debug("����֤�з����û�, updateUserLoginInfo:" + ((User)principal).getUsername());
//				}
//				User user = 
//			}
		}
	}
	
	public static void main(String[] args){
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("alex", "123"));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getPrincipal() instanceof String);
		System.out.println(UserHolder.getNullableUser());
	}
}
